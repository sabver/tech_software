package com.tech.paper.controller;

import com.tech.paper.jsonobj.Message;
import com.tech.paper.exception.ServiceException;
import com.tech.paper.util.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理 Exception 异常
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Message exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        log.error("服务错误:", e);
        return new Message(MessageStatus.FAILURE, "Service Error");
    }

    /**
     *
     * @param httpServletRequest
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = IOException.class)
    public Message ioExcetionHandler(HttpServletRequest httpServletRequest, IOException e){
        log.error("IO错误:", e);
        return new Message(MessageStatus.FAILURE,"IO Error");
    }

    /**
     * 处理 BusinessException 异常
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public Message businessExceptionHandler(HttpServletRequest httpServletRequest, ServiceException e) {
        log.info("业务异常 status:" + e.getStatus() + " message:" + e.getMsg());
        return new Message(e.getStatus(), e.getMsg());
    }



}

