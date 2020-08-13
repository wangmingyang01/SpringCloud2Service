package com.chown.pine.service;

import com.chown.pine.db.dao.UserDao;
import com.chown.pine.db.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class QueryService {
    @Resource
    UserDao userDao;

    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
        Integer.parseInt("ddddd");
    }
}
