package com.wu.springBootToken.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:
 *
 * @author WUAN4
 * @create 2019-10-16 2:47 PM
 */

public class TokenUtil {
    public static Object findToken() {
        String token = getRequest().getHeader("token");// 从http请求头中取出token
        if (token == null || token == "") {
            return ResponseTemplate.builder().code(401).message("缺少token").data("缺少token，请重新登录📕").build();
        }
        try {
            String userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            return ResponseTemplate.builder().code(401).message("非法token").data("非法token，请重新登录📕").build();
        }
        return ResponseTemplate.builder().code(200).message("测试成功").data("测试📕").build();
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

}
