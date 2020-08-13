package com.chown.pine.controller;

import com.chown.pine.db.dao.UserDao;
import com.chown.pine.db.entity.User;
import com.chown.pine.service.QueryService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class QueryController {
    @Resource
    private UserDao userDao;
    @Resource
    private QueryService queryService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Object queryUser(long id) {
        User user = userDao.queryUserById(id);

        return user.toString();
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public Object delUser(long id) {
        userDao.delUser(id);

        return "delete " + id + " success";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addUser(@ModelAttribute User user) {
        queryService.addUser(user);

        return "success";
    }


}