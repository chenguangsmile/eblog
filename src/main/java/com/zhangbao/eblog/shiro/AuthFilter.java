package com.zhangbao.eblog.shiro;

import cn.hutool.json.JSONUtil;
import com.zhangbao.eblog.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * shiro过滤器，判断请求是否是ajax
 */
@Slf4j
public class AuthFilter extends UserFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //ajax请求，弹窗显示未登录
        String header = servletRequest.getHeader("X-Requested-With");
        if(header!=null && "XMLHttpRequest".equals(header)){
            boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
            if(!authenticated){
                log.error("not authenticated - ajax请求未登录");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSONUtil.toJsonStr(Result.fail("请先登录")));
            }
        }else {
            //web 请求，重定向到登录页面
            log.error("not authenticated - web请求未登录");
            super.redirectToLogin(request, response);
        }

    }
}
