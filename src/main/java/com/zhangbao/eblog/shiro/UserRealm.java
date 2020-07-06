package com.zhangbao.eblog.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbao.eblog.entity.User;
import com.zhangbao.eblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        User user = (User) principalCollection.getPrimaryPrincipal();
        if(user.getUsername().equals("admin")){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        EasyTypeToken easyTypeToken = (EasyTypeToken)token;

        User userInfo = userService.getOne(new QueryWrapper<User>().eq("email", easyTypeToken.getUsername()));
        SecurityUtils.getSubject().getSession().setAttribute("profile",userInfo);
        //账号不存在
        if (userInfo == null) {
            throw new UnknownAccountException("用户不存在");
        }
        //非免密登录则需要验证密码
        if(easyTypeToken.getType().equals(LoginType.PASSWORD)){
            if(userInfo.getPassword().equals(easyTypeToken.getPassword())){
                throw new IncorrectCredentialsException("密码错误");
            }
        }
        // 账号禁用
        if (userInfo.getStatus() != 0) {
            throw new DisabledAccountException("账号已被禁用,请联系管理员");
        }
        userInfo.setLasted(new Date());
        userService.updateById(userInfo);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userInfo,easyTypeToken.getCredentials(),getName());
        return info;
    }
}
