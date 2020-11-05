package com.tech.paper.interceptor;

import com.tech.paper.annotation.JwtIgnore;
import com.tech.paper.domain.Audience;
import com.tech.paper.exception.ServiceException;
import com.tech.paper.service.impl.InitServiceImpl;
import com.tech.paper.util.CommonUtil;
import com.tech.paper.util.JwtTokenUtil;
import com.tech.paper.util.MessageStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class JwtInterceptor extends HandlerInterceptorAdapter{

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private Audience audience;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        log.info(request.getRequestURI());
        log.info(request.getMethod());
        log.info(HttpMethod.OPTIONS.toString());
        log.info(HttpMethod.OPTIONS.equals(request.getMethod())+"");
        log.info(HttpMethod.OPTIONS.toString().equals(request.getMethod())+"");
        log.info(handler.toString());
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }
        if( HttpMethod.OPTIONS.toString().equals(request.getMethod()) ){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
//        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return true;
//        }
        log.info("获取请求头信息authorization信息");
        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        log.info("## authHeader= {}", authHeader);
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###");
            throw new ServiceException(MessageStatus.NOT_LOGIN,"please login in");
        }
        // 获取token
        final String token = authHeader.substring(7);
        log.info(token);
        if(audience == null){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            audience = (Audience) factory.getBean("audience");
        }
        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        JwtTokenUtil.parseJWT(token, audience.getBase64Secret());
//        log.info(JwtTokenUtil.getUsername(token,audience.getBase64Secret()));
//        log.info(JwtTokenUtil.getRole(token,audience.getBase64Secret()));
//        log.info(JwtTokenUtil.getUserId(token,audience.getBase64Secret()));
        //传递用户id
       request.setAttribute(CommonUtil.TOKEN_USER_ID_KEY,JwtTokenUtil.getUserId(token,audience.getBase64Secret()));
//        request.setAttribute(CommonUtil.TOKEN_USER_ACCOUNT_KEY,JwtTokenUtil.getUsername(token,audience.getBase64Secret()));
        return true;
    }
}
