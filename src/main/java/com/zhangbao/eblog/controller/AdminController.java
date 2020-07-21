package com.zhangbao.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.Post;
import com.zhangbao.eblog.service.SearchService;
import com.zhangbao.eblog.vo.PostVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private SearchService searchService;

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

    @ResponseBody
    @PostMapping("/initEsData")
    public Result initEsData(){

        int size = 10000;
        int total = 0;
        Page page = new Page();
        page.setSize(size);
        Map<String,Object> map = Maps.newHashMap();
        for (int i = 1; i < 1000; i++) {
            page.setCurrent(i);
            IPage<PostVo> iPage = postService.pagingByMap(page, map);
            int num = searchService.initEsData(iPage.getRecords());
            total += num;
            if(iPage.getTotal()<size){
                break;
            }
        }
        return Result.succ("ES索引初始化成功，共 "+total+" 条记录！");
    }

}
