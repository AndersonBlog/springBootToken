package com.wu.springBootToken.service;

import com.wu.springBootToken.entity.User;
import com.wu.springBootToken.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author WUAN4
 * @create 2019-10-15 5:19 PM
 */
@Service("UserService")
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUser(String username, String password) {
        return userMapper.findByUserNameAndPasswd(username,password);
    }

    public User finduserbyId(String userId) {
        System.out.println(userId + "***************************");
        return userMapper.findUserById(userId);
    }
}
