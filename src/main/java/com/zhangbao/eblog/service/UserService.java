package com.zhangbao.eblog.service;

import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangbao.eblog.shiro.EasyTypeToken;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
public interface UserService extends IService<User> {

    Result register(User user);

    User login(EasyTypeToken easyTypeToken);
}
