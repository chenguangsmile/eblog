package com.zhangbao.eblog.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.zhangbao.eblog.template.PostsTemplate;
import com.zhangbao.eblog.template.RankTemplate;
import com.zhangbao.eblog.template.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    PostsTemplate postsTemplate;
    @Autowired
    RankTemplate rankTemplate;
    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);
        configuration.setSharedVariable("hots", rankTemplate);
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
