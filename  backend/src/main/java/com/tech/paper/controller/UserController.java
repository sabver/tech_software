package com.tech.paper.controller;

import com.tech.paper.annotation.JwtIgnore;
import com.tech.paper.domain.Audience;
import com.tech.paper.jsonobj.Message;
import com.tech.paper.domain.User;
import com.tech.paper.service.UserService;
import com.tech.paper.util.JwtTokenUtil;
import com.tech.paper.util.LoginMsg;
import com.tech.paper.util.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private Audience audience;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @JwtIgnore
    public Message login(HttpServletResponse response, String account, String password) {
        log.info("login");
        log.info(audience.toString());
        Message result = new Message();
        result.setStatus(MessageStatus.SUCCESS);
        result.setMessage("login success");
        User user = userService.login(account,password);
        log.info(user.toString());
        // 创建token
        String token = JwtTokenUtil.createJWT(user.getId().toString(), account, "normal", audience);
        LoginMsg loginMsg = new LoginMsg();
        loginMsg.setToken(JwtTokenUtil.TOKEN_PREFIX + token);
        loginMsg.setUser(user);
        result.setData(loginMsg);
        log.info("### 登录成功, token={} ###", JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token放在响应头 这里我直接拿
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        return result;
    }

    @PostMapping("/register")
    @JwtIgnore
    public Message register(String account, String password, String nickname, String researchTheme){
        log.info(account);
        log.info(password);
        log.info(nickname);
        log.info(researchTheme);
        Message result = new Message();
        result.setStatus(MessageStatus.SUCCESS);
        result.setMessage("register success");
        result.setData(userService.register(account, password, nickname, researchTheme));
        return result;
    }

    @GetMapping("/users")
    public Message userList() {
        log.info("### 查询所有用户列表 ###");
        Message result = new Message();
        result.setStatus(MessageStatus.SUCCESS);
        result.setMessage("查询成功");
        return result;
    }



}
