package com.tech.paper.service.impl;

import com.tech.paper.domain.User;
import com.tech.paper.domain.UserRepository;
import com.tech.paper.exception.ServiceException;
import com.tech.paper.service.UserService;
import com.tech.paper.util.CommonUtil;
import com.tech.paper.util.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserRepository userRepository;

    @Override
    public User register(String account, String password, String nickname, String researchTheme) throws ServiceException {
        //查重
        if( CommonUtil.checkNull(nickname) || CommonUtil.checkNull(password)|| CommonUtil.checkNull(nickname)|| CommonUtil.checkNull(researchTheme) ){
            throw new ServiceException(MessageStatus.PARAM_ERROR,"param error");
        }
        List<User> result = userRepository.findByAccountEquals(account);
        if( result.size() > 0 ){
            throw new ServiceException(MessageStatus.FAILURE,"please change a new account");
        }
        User user = new User();
        user.setAccount(account);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setResearchTheme(researchTheme);
        User newUser =  userRepository.save(user);
        return newUser;
    }

    @Override
    public User login(String account, String password) throws ServiceException {
        if( CommonUtil.checkNull(account) || CommonUtil.checkNull(password) ){
            log.info("account:"+account);
            log.info("password:"+password);
            throw new ServiceException(MessageStatus.PARAM_ERROR,"param error");
        }
        List<User> result = userRepository.findByAccountEqualsAndPasswordEquals(account, password);
        if( result.size() == 0 ){
            throw new ServiceException(MessageStatus.FAILURE,"account or password invalidate");
        }
        return result.get(0);
    }

    @Override
    public User getUser(Long userId) throws ServiceException {
        if( userId == null ){
            throw new ServiceException(MessageStatus.PARAM_ERROR,"param error");
        }
        List<User> result = userRepository.findByIdEquals(userId);
        if( result.size()  == 0 ){
            throw new ServiceException(MessageStatus.FAILURE,userId+" have no user");
        }else if( result.size() > 1 ){
            throw new ServiceException(MessageStatus.FAILURE,userId+" have some users");
        }
        return result.get(0);
    }
}
