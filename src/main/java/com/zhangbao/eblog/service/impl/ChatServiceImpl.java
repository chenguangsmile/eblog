package com.zhangbao.eblog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhangbao.eblog.entity.User;
import com.zhangbao.eblog.im.vo.ImUser;
import com.zhangbao.eblog.service.ChatService;
import com.zhangbao.eblog.util.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-19:16
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Override
    public ImUser getCurrentUser() {

        User user = UserUtils.getUser();

        ImUser imUser = new ImUser();

        if(user!=null){
            //登录用户
            imUser.setId(user.getId());
            imUser.setAvatar(user.getAvatar());
            imUser.setSign(user.getSign());
            imUser.setStatus(ImUser.ONLINE_STATUS);
            imUser.setUsername(user.getUsername());
        }else {
            //匿名用户
            imUser.setAvatar("http://tp2.sinaimg.cn/5488749285/50/5719808192/1");
            Long randomId = (Long)SecurityUtils.getSubject().getSession().getAttribute("imUserId");
            imUser.setId(randomId==null? RandomUtil.randomLong():randomId);

            SecurityUtils.getSubject().getSession().setAttribute("imUserId",imUser.getId());

            imUser.setUsername("匿名用户");
            imUser.setStatus(ImUser.HIDE_STATUS);
            imUser.setSign("Never give up!");
        }
        return imUser;
    }
}
