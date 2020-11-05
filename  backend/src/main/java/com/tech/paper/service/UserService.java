package com.tech.paper.service;

import com.tech.paper.domain.User;
import com.tech.paper.exception.ServiceException;

public interface UserService {
    User login(String account, String password) throws ServiceException;
    User register(String account,String password,String nickname,String researchTheme) throws ServiceException;
    User getUser(Long userId) throws ServiceException;
}
