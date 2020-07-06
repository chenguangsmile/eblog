package com.zhangbao.eblog.schedules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbao.eblog.entity.Post;
import com.zhangbao.eblog.service.PostService;
import com.zhangbao.eblog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ViewCountSyncTask {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PostService postService;

    @Scheduled(cron = "0/5 * * * * *")//每分钟
    public void tesk(){
        Set<String> keys = redisTemplate.keys("rank:post:*");
        List<String> ids = new ArrayList<>();
        for (String key : keys) {
            String postId = key.substring("rank:post:".length());
            ids.add(postId);
        }
        List<Post> posts = postService.list(new QueryWrapper<Post>().in("id", ids));
        posts.stream().forEach(post -> {
            String key  = "rank:post:"+post.getId();
            Integer viewCount = (Integer) redisUtil.hget(key, "post:viewCount");
            if(viewCount!=null){
                post.setViewCount(viewCount);
            }
        });
        if(posts.isEmpty()) return;
        boolean isSuc = postService.updateBatchById(posts);
        if(isSuc){
            for (String id : ids) {
                redisUtil.hdel("rank:post:"+id,"post:viewCount");
                System.out.println(id+"----------------->同步成功");
            }
        }

    }

}
