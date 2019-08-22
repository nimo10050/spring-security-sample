package com.example.demo.config.handler;

import com.example.demo.common.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 */
@Component
public class GoAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * {"code":500,"message":"登录失败：Bad credentials","data":null}
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(CommonResult.failed("登录失败：" + exception.getMessage())));
        response.getWriter().flush();
    }
}
