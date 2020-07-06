package com.zhangbao.eblog.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.User;
import com.zhangbao.eblog.mapper.UserMapper;
import com.zhangbao.eblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbao.eblog.shiro.EasyTypeToken;
import com.zhangbao.eblog.shiro.LoginType;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result register(User user) {
        int count = this.count(new QueryWrapper<User>()
                .lambda().eq(User::getEmail, user.getEmail())
                .or()
                .eq(User::getUsername, user.getUsername())
        );
        if(count > 0){
            return Result.fail("用户名或邮箱已被占用");
        }
        User temp = new User();
        temp.setUsername(user.getUsername());
        temp.setEmail(user.getEmail());
        temp.setAvatar("/res/images/avatar/default.png");
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.preInsert();
        temp.setGender("0");
        this.save(temp);

        return Result.succ();
    }

    @Override
    public User login(EasyTypeToken easyTypeToken) {
        User user = this.getOne(new QueryWrapper<User>().eq("username", easyTypeToken.getUsername()));

        if(easyTypeToken.getType().equals(LoginType.PASSWORD)){

        }
        return null;
    }
}
