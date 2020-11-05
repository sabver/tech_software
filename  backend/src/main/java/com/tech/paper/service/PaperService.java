package com.tech.paper.service;

import com.tech.paper.domain.Keywords;
import com.tech.paper.domain.Paper;
import com.tech.paper.exception.ServiceException;
import org.apache.lucene.queryparser.classic.ParseException;

import javax.xml.ws.Service;
import java.io.IOException;
import java.util.List;

public interface PaperService {
    @Deprecated
    public List<Keywords> queryKeywords(String likeStr,int topN) throws ServiceException;

    /**
     * 全文检索
     * @param keyword 查询关键词
     * @param curPage 当前第几页
     * @param pageSize 一页多少条
     * @return
     * @throws ServiceException
     */
    public List<Paper> queryByKeywords(String keyword,Integer curPage,Integer pageSize) throws ServiceException, IOException, ParseException;

    /**
     * 查询最新的paper列表
     * @param curPage 当前第几页
     * @param pageSize 一页多少条
     * @return
     * @throws ServiceException
     */
    public List<Paper> queryByPage(Integer curPage,Integer pageSize) throws ServiceException;

    /**
     * 查询用户的收藏列表
     * @param userId
     * @param curPage
     * @param pageSize
     * @return
     * @throws ServiceException
     */
    public List<Paper> queryCollect(Long userId,Integer curPage,Integer pageSize) throws ServiceException;

    @Deprecated
    public void buildKeywords() throws ServiceException,IOException;

    /**
     * 详情查询
     * @param id
     * @return
     * @throws ServiceException
     */
    public Paper queryPaperById(Long id) throws ServiceException;

    public boolean isUserCollectPaper(Long userId,Long paperId) throws  ServiceException;

    public void collect(Long userId, Long paperId) throws ServiceException;

    public void nocollect(Long userId, Long paperId) throws ServiceException;

}
