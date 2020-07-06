package com.zhangbao.eblog.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbao.eblog.entity.Category;
import com.zhangbao.eblog.service.CategoryService;
import com.zhangbao.eblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @author Sunny
 * @create 2020-06-2020/6/16-21:14
 */
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

    @Autowired
    CategoryService categoryService;
    @Autowired
    PostService postService;

    ServletContext servletContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Category> categories = categoryService.list(new QueryWrapper<Category>()
                .eq("status", 0)
        );
        servletContext.setAttribute("categorys", categories);
        servletContext.setAttribute("currentCategoryId", 0);
        servletContext.setAttribute("base", servletContext.getContextPath());

        //初始化本周热议
        postService.initWeekRank();

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
