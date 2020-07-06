package com.zhangbao.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.Post;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    /**
     * @param id
     * @param rank 0表示取消，1表示操作
     * @param field
     * @return
     */
    @ResponseBody
    @PostMapping("/jie-set")
    public Result admin(Long id, Integer rank,String field){

        Post post = postService.getById(id);
        Assert.notNull(post,"该帖子已被删除");
        if("delete".equals(field)){
            postService.removeById(id);
        }else if("stick".equals(field)){
            post.setLevel(rank);//置顶
        }else if("status".equals(field)){
            post.setRecommend(rank==1);//加精（精华）
        }
        postService.updateById(post);
        return Result.succ();
    }

}
