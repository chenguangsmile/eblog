package com.zhangbao.eblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.User;
import com.zhangbao.eblog.service.*;
import com.zhangbao.eblog.util.UploadUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sunny
 * @create 2020-06-2020/6/16-12:57
 */
public class BaseController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    UploadUtil uploadUtil;
    @Autowired
    UserCollectionService userCollectionService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserMessageService userMessageService;

    public Page getPage(){
        int pageNum = ServletRequestUtils.getIntParameter(req, "pageNum", 1);
        int pageSize = ServletRequestUtils.getIntParameter(req, "pageSize", 2);
        return new Page(pageNum,pageSize);
    }

    protected User getUser(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
    protected Long getUserId(){
        return getUser().getId();
    }
}
