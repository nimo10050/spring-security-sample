package com.example.demo.config.handler;

import com.example.demo.common.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义没有访问权限处理类
 */
@Component
public class GoAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     *
     * @return {"code":403,"message":"没有相关权限","data":"Access is denied"}
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(CommonResult.forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
