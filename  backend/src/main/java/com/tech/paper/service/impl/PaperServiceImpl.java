package com.tech.paper.service.impl;

import com.tech.paper.domain.*;
import com.tech.paper.exception.ServiceException;
import com.tech.paper.jsonobj.PaperTitle;
import com.tech.paper.service.PaperService;
import com.tech.paper.util.CommonUtil;
import com.tech.paper.util.LuceneUtil;
import com.tech.paper.util.MessageStatus;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PaperServiceImpl implements PaperService {

    private static final Logger log = LoggerFactory.getLogger(PaperServiceImpl.class);

    @Autowired
    private Environment env;

    @Resource
    private KeywordsRepository kr;

    @Resource
    private PaperRepository paperRepository;

    @Resource
    private UserPaperRepository userPaperRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public List<Paper> queryByKeywords(String keyword, Integer curPage, Integer pageSize) throws ServiceException,IOException, ParseException {
        if( curPage == null || pageSize == null || CommonUtil.checkNull(keyword)){
            throw new ServiceException(MessageStatus.PARAM_ERROR,"参数错误");
        }
        if( curPage <= 0 ){
            curPage = 1;
        }
        if( pageSize <=0 ){
            pageSize = 10;
        }
        List<Paper> result = new ArrayList<Paper>();
        Directory directory = LuceneUtil.getDirectory(env.getProperty("lucene.filepath"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser queryParser = new MultiFieldQueryParser(new String[]{"title","abs","authors"},LuceneUtil.getAnalyzer());
        Query query = queryParser.parse(keyword);
        //分页逻辑
        //开始加载的index，从0开始的
        Integer startIndex = ( curPage - 1 ) * pageSize;
        Integer endIndex = startIndex+pageSize;
        //index代表已经循环了多少次
        Integer index = 0;
        //这里设定了相似度的前10%的文档
        TopDocs topDocs = searcher.search(query, reader.numDocs()/10);
        log.info("hits number:"+topDocs.totalHits.value+"");
        for (ScoreDoc score : topDocs.scoreDocs) {
            if( index >= startIndex && index <= endIndex){
                Document doc = searcher.doc(score.doc);
                result.add(LuceneUtil.getPaper(doc));
            }
            index++;
            if( index > endIndex ){
                break;
            }
        }
        reader.close();
        directory.close();
        return result;
    }

    @Override
    public List<Paper> queryByPage(Integer curPage, Integer pageSize) throws ServiceException {
        if( curPage == null || pageSize == null ){
            throw new ServiceException(MessageStatus.PARAM_ERROR,"参数错误");
        }
        if( curPage <= 0 ){
            curPage = 1;
        }
        if( pageSize <=0 ){
            pageSize = 10;
        }
        //这里的curPage和数据库的进行对应一下
        Page<Paper> pageResult = paperRepository.findAll(PageRequest.of(curPage-1,pageSize,Sort.by(Sort.Direction.DESC,"id")));
        List<Paper> result = pageResult.toList();
        if( result == null || result.size() == 0 ){
            throw new ServiceException(MessageStatus.EMPTY,"数据为空");
        }
        return result;
    }

    @Deprecated
    @Override
    public List<Keywords> queryKeywords(String likeStr,int topN) throws ServiceException {
        Page<Keywords> pageResult = kr.findByContentLikeOrderByScore(likeStr, PageRequest.of(0,topN));
        List<Keywords> result = pageResult.toList();
        if( result == null || result.size() == 0 ){
            throw new ServiceException(MessageStatus.EMPTY,"数据为空");
        }
        return result;
    }

    @Deprecated
    @Override
    public void buildKeywords() throws ServiceException, IOException {
        Directory directory = FSDirectory.open(Paths.get(env.getProperty("lucene.filepath")));
        Map<String, Integer> map = new HashMap<String, Integer>();
        DirectoryReader reader = DirectoryReader.open(directory);
        for(int i=0;i<reader.numDocs();i++){
            //这里只有一个doc
            Terms terms = reader.getTermVector(i,"abs");

            // 遍历词项
            TermsEnum termsEnum = terms.iterator();
            BytesRef thisTerm = null;

            while ((thisTerm = termsEnum.next()) != null) {
                // 词项
                String termText = thisTerm.utf8ToString();
                // 通过totalTermFreq()方法获取词项频率
                map.put(termText, (int) termsEnum.totalTermFreq());
//                log.info("term:"+termText+" fre:"+(int) termsEnum.totalTermFreq());
            }
        }

        // 按value排序
        List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        reader.close();
        directory.close();

        deleteKeywords();
        saveKeywords(sortedMap);
    }

    @Deprecated
    public void deleteKeywords(){
        kr.deleteAll();
    }

    @Deprecated
    public void saveKeywords(List<Map.Entry<String, Integer>> sortedMap){
        List<Keywords> list = new ArrayList<Keywords>();
        Keywords keywords = null;
        for(int i=0;i<sortedMap.size();i++){
            keywords = new Keywords();
            keywords.setScore(sortedMap.get(i).getValue());
            keywords.setContent(sortedMap.get(i).getKey());
            list.add(keywords);
        }
        kr.saveAll(list);
    }

    @Deprecated
    public String getBigTitleString(List<PaperTitle> list){
        StringBuffer buffer = new StringBuffer();
        for(PaperTitle obj:list){
            buffer.append(obj.getTitle());
        }
        String result = buffer.toString();
        buffer.setLength(0);
        return result;
    }

    @Deprecated
    public void buildKeywordsIndex(String bigTitleString) throws IOException{
        Analyzer analyzer = new StandardAnalyzer();
        Path indexPath = Files.createTempDirectory(env.getProperty("lucene.keywords.filepath"));
        Directory directory = FSDirectory.open(indexPath);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        Document doc = new Document();
        // 新建FieldType,用于指定字段索引时的信息
        FieldType type = new FieldType();
        // 索引时保存文档、词项频率、位置信息、偏移信息
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        type.setStored(true);// 原始字符串全部被保存在索引中
        type.setStoreTermVectors(true);// 存储词项量
        type.setTokenized(true);// 词条化
        doc.add(new Field("title", bigTitleString, type));
        iwriter.addDocument(doc);
        iwriter.close();
        directory.close();
    }

    @Override
    public Paper queryPaperById(Long id) throws ServiceException {
        if( id == null || id <= 0 ){
            throw new ServiceException(MessageStatus.PARAM_ERROR,"参数错误");
        }
        Optional<Paper> paper = paperRepository.findById(id);
        return paper.get();
    }

    @Override
    public boolean isUserCollectPaper(Long userId, Long paperId) throws ServiceException {
        Optional<User> user = userRepository.findById(userId);
        log.info(user.get().toString());
        Optional<Paper> paper = paperRepository.findById(paperId);
        log.info(paper.get().toString());
        List<UserPaper> list = userPaperRepository.findUserPaperByPaperEqualsAndUserEquals(paper.get(),user.get());
        if( list.size() != 0 ){
            return true;
        }
        return false;
    }

    @Override
    public void collect(Long userId, Long paperId) throws ServiceException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Paper> paper = paperRepository.findById(paperId);
        UserPaper userPaper = new UserPaper();
        userPaper.setPaper(paper.get());
        userPaper.setUser(user.get());
        userPaperRepository.save(userPaper);
    }

    @Override
    public void nocollect(Long userId, Long paperId) throws ServiceException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Paper> paper = paperRepository.findById(paperId);
        List<UserPaper> userPaper = userPaperRepository.findUserPaperByPaperEqualsAndUserEquals(paper.get(),user.get());
        userPaperRepository.delete(userPaper.get(0));
    }

    @Override
    public List<Paper> queryCollect(Long userId, Integer curPage, Integer pageSize) throws ServiceException {
        if( curPage == null || pageSize == null || userId == null ){
            throw new ServiceException(MessageStatus.PARAM_ERROR,"参数错误");
        }
        if( curPage <= 0 ){
            curPage = 1;
        }
        if( pageSize <=0 ){
            pageSize = 10;
        }
        List<User> userList = userRepository.findByIdEquals(userId);
        if( userList.size()  == 0 ){
            throw new ServiceException(MessageStatus.FAILURE,userId+" have no user");
        }else if( userList.size() > 1 ){
            throw new ServiceException(MessageStatus.FAILURE,userId+" have some users");
        }
        List<Paper> result = new ArrayList<Paper>();
        Pageable pageable = PageRequest.of(curPage-1,pageSize,Sort.by(Sort.Direction.DESC,"id"));
        Page<UserPaper> userPaperPage = userPaperRepository.findAll(new Specification<UserPaper>() {
            @Override
            public Predicate toPredicate(Root<UserPaper> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("userId").as(Long.class), userId.toString());
                return criteriaBuilder.and(predicate);
            }
        }, pageable);
        List<UserPaper> userPapers = userPaperPage.toList();
        for(UserPaper userPaper:userPapers){
            result.add(userPaper.getPaper());
        }
        return result;
    }
}
