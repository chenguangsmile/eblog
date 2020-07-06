package com.zhangbao.eblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.UserMessage;
import com.zhangbao.eblog.entity.UserMessageVo;
import com.zhangbao.eblog.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhangbao.eblog.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@RestController
@RequestMapping("/user-message")
public class UserMessageController extends BaseController {

    @Autowired
    UserMessageService userMessageService;

    @GetMapping("/")
    public String message(){

        IPage<UserMessageVo> iPage = userMessageService.paging(getPage(),new QueryWrapper<UserMessage>()
            .lambda().eq(UserMessage::getToUserId,getUserId())
            .orderByDesc(UserMessage::getCreated)
        );
        req.setAttribute("pageData",iPage);
        return "user/message";
    }

}
