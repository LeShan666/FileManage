package com.zls.service;

import com.zls.dao.UserDao;
import com.zls.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void registUser(User user){

        userDao.save(user);
    }

    public User checkLogin(User user) {

        User user1 = userDao.findUserByUsernameAndUserpass(user.getUsername(), user.getUserpass());
        System.out.println(user1);
        if(user1 == null)
            return null;
        return user1;
    }
}
