package com.zhangbao.eblog.config;

import cn.hutool.core.map.MapUtil;
import com.zhangbao.eblog.shiro.AuthFilter;
import com.zhangbao.eblog.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        log.error("------------------> securityManager 注入成功");
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 配置登录的url和登录成功的url
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/");
        // 配置未授权跳转页面
        filterFactoryBean.setUnauthorizedUrl("/error/403");

        filterFactoryBean.setFilters(MapUtil.of("auth",authFilter()));

        Map<String, String> map = new LinkedHashMap<>();
        map.put("/user/home","auth");
        map.put("/user/set","auth");
        map.put("/user/upload","auth");
        map.put("/user/message","auth");

        map.put("/post/collection/","auth");



        map.put("/**","anon");

        filterFactoryBean.setFilterChainDefinitionMap(map);

        return filterFactoryBean;


    }

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }
}
