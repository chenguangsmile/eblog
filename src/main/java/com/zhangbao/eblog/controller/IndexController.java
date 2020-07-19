package com.zhangbao.eblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sunny
 * @create 2020-06-2020/6/16-19:35
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping({"", "/", "/index"})
    public String index () {
        //1分页信息，2分类，3用户，4置顶，5精选，6排序
        IPage<PostVo> iPage  = postService.paging(getPage(),null,null,null,null,"created");

        req.setAttribute("pageData",iPage);
        req.setAttribute("currentCategoryId",0);
        return "index";
    }

    @RequestMapping("/search")
    public String search(String q){

        req.setAttribute("q",q);
        req.setAttribute("pageData",null);
        return "search";
    }
}
