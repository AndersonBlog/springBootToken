package com.wu.springBootToken.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wu.springBootToken.annotation.PassToken;
import com.wu.springBootToken.annotation.UserLoginToken;
import com.wu.springBootToken.entity.User;
import com.wu.springBootToken.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 描述:
 *
 * @author WUAN4
 * @create 2019-10-15 3:32 PM
 */

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从http请求取出token
        String token = request.getHeader("token");
        log.info("获取到的token为：{}", token);
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                //执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                //获取token中的user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    log.info("Token非法");
                    throw new RuntimeException("401,Token非法");
                }
                User user = userService.finduserbyId(userId);
                if (user == null) {
                    log.info("用户不存在，请重新登录!");
                    throw new RuntimeException("用户不存在，请重新登录!");
                }
                //验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    log.info("401,Token非法");
                    throw new RuntimeException("401,Token非法");
                }
                return true;
            }
        }
        return true;
    }
}
