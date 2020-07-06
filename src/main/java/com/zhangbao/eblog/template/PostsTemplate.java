package com.zhangbao.eblog.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.common.template.DirectiveHandler;
import com.zhangbao.eblog.common.template.TemplateDirective;
import com.zhangbao.eblog.service.PostService;
import com.zhangbao.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsTemplate extends TemplateDirective {

    @Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        Integer level = handler.getInteger("level");
        Integer pageNum = handler.getInteger("pageNum", 1);
        Integer pageSize = handler.getInteger("pageSize", 2);
        Long categoryId = handler.getLong("categoryId");
        IPage<PostVo> page = postService.paging(new Page(pageNum, pageSize), categoryId, null, level, null, "created");
        handler.put(RESULTS,page).render();
    }
}
