package com.wu.springBootToken.controller;

import com.alibaba.fastjson.JSONObject;
import com.wu.springBootToken.annotation.UserLoginToken;
import com.wu.springBootToken.entity.User;
import com.wu.springBootToken.service.TokenService;
import com.wu.springBootToken.service.UserService;
import com.wu.springBootToken.util.ResponseTemplate;
import com.wu.springBootToken.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述:
 *
 * @author WUAN4
 * @create 2019-10-15 5:07 PM
 */

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @GetMapping("/wlecome")
    public String welcome() {
        return "welcome TokenController";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object login(User user, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        logger.info("用户名username为:" + user.getUsername() + "密码password为:" + user.getPassword());

        User userSearch = userService.getUser(user.getUsername(), user.getPassword());
        if (userSearch != null) {
            String token = tokenService.getToken(userSearch);
            jsonObject.put("message", "用户登录成功!");
            jsonObject.put("token", token);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");

        } else {
            jsonObject.put("message", "登陆失败, 当前用户不存在，请重新输入！");
            return ResponseTemplate.builder().code(500).message("账号或密码错误！").data(jsonObject).build();
        }
        return ResponseTemplate.builder().code(200).message("用户登录成功").data(jsonObject).build();
    }

    @UserLoginToken
    @RequestMapping(value = "getMessage", method = RequestMethod.GET)
    public Object getMessage() {
        logger.info("**************测试start**************");
        return ResponseTemplate.builder().code(200).message("访问成功").data("携带Token，成功访问").build();
    }
}
