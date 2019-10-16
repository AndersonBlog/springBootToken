package com.wu.springBootToken.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wu.springBootToken.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述:
 *
 * @author WUAN4
 * @create 2019-10-15 5:19 PM
 */
@Service("TokenService")
public class TokenService {
    public String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//Token一小时有效
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getId()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
