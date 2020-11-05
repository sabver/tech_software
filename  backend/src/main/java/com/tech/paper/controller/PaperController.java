package com.tech.paper.controller;

import com.tech.paper.annotation.JwtIgnore;
import com.tech.paper.domain.Keywords;
import com.tech.paper.domain.Paper;
import com.tech.paper.domain.User;
import com.tech.paper.jsonobj.Detail;
import com.tech.paper.jsonobj.Message;
import com.tech.paper.exception.ServiceException;
import com.tech.paper.service.PaperService;
import com.tech.paper.service.UserService;
import com.tech.paper.util.CommonUtil;
import com.tech.paper.util.MessageStatus;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {

    private static final Logger log = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperService paperService;

    @Autowired
    private UserService userService;

    @RequestMapping("/keywords")
    @JwtIgnore
    public Message keywords(String likeStr,Integer topN) {
        Message message = new Message();
        List<Keywords> keywords = paperService.queryKeywords(likeStr,topN);
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        message.setData(keywords);
        return message;
    }

    @RequestMapping("/build")
    @JwtIgnore
    public Message buildKeywords() throws IOException {
        Message message = new Message();
        paperService.buildKeywords();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        return message;
    }

    @RequestMapping("/findAll")
    public Message findAll(Integer curPage,Integer pageSize) throws ServiceException {
        Message message = new Message();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        message.setData(paperService.queryByPage(curPage,pageSize));
        return message;
    }

    @RequestMapping("/find")
    public Message find(String keyword, Integer curPage, Integer pageSize) throws ServiceException,IOException, ParseException {
        Message message = new Message();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        message.setData(paperService.queryByKeywords(keyword,curPage,pageSize));
        return message;
    }

    @RequestMapping("/findMy")
    public Message findMy(HttpServletRequest request,Integer curPage,Integer pageSize) throws  ServiceException,IOException, ParseException{
        Message message = new Message();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        Long userId = Long.valueOf(request.getAttribute(CommonUtil.TOKEN_USER_ID_KEY).toString());
        User user = userService.getUser(userId);
        String theme = user.getResearchTheme();
        log.info("theme:"+theme);
        message.setData(paperService.queryByKeywords(theme,curPage,pageSize));
        return  message;
    }

    @RequestMapping("/detail")
    public Message find(HttpServletRequest request,Long id) throws ServiceException{
        Message message = new Message();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        Long userId = Long.valueOf(request.getAttribute(CommonUtil.TOKEN_USER_ID_KEY).toString());
        log.info("userId:"+userId);
        log.info("paperId:"+id);
        boolean isCollect = paperService.isUserCollectPaper(userId,id);
        Detail detail = new Detail();
        detail.setPaper(paperService.queryPaperById(id));
        detail.setCollect(isCollect);
        message.setData(detail);
        return message;
    }

    @RequestMapping("/collect")
    public Message collect(HttpServletRequest request,Long id) throws ServiceException {
        Message message = new Message();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        Long userId = Long.valueOf(request.getAttribute(CommonUtil.TOKEN_USER_ID_KEY).toString());
        paperService.collect(userId,id);
        return message;
    }

    @RequestMapping("/nocollect")
    public Message nocollect(HttpServletRequest request,Long id) throws ServiceException {
        Message message = new Message();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");
        Long userId = Long.valueOf(request.getAttribute(CommonUtil.TOKEN_USER_ID_KEY).toString());
        paperService.nocollect(userId,id);
        return message;
    }

    @RequestMapping("/collectList")
    public Message collectList(HttpServletRequest request,Integer curPage,Integer pageSize){
        Message message = new Message();
        message.setStatus(MessageStatus.SUCCESS);
        message.setMessage("success");

        return message;
    }

}
