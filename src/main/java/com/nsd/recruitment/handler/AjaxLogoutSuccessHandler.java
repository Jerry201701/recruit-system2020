package com.nsd.recruitment.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nsd.recruitment.common.GenericResponse;
import com.nsd.recruitment.common.ServiceError;
import com.nsd.recruitment.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: jamesluozhiwei
 * @description: 登出成功
 */
@Component
@Slf4j
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //没有logout不走这里、若使用security的formLogin则自己添加业务实现（移除token等等）
        PrintWriter out = response.getWriter();
        out.write(JSONObject.toJSONString(GenericResponse.response(ServiceError.NORMAL)));
        out.flush();
        out.close();
        //response.getWriter().write(JSON.toJSONString(GenericResponse.response(ServiceError.NORMAL)));
    }

}