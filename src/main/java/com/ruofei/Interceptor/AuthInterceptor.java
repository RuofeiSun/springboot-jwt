package com.ruofei.Interceptor;

import com.ruofei.utils.TokenManager;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: srf
 * @Date: 2020/12/1 14:39
 * @description:权限拦截，验证jwt
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            throw new Exception("无token，请重新登录");
        }
        //jwt验证
        if (!TokenManager.verifyToken(token)){
            throw new Exception("token验证失败");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
