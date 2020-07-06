package com.zhangbao.eblog.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.Comment;
import com.zhangbao.eblog.entity.Post;
import com.zhangbao.eblog.entity.User;
import com.zhangbao.eblog.entity.UserMessage;
import com.zhangbao.eblog.mapper.PostMapper;
import com.zhangbao.eblog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbao.eblog.util.RedisUtil;
import com.zhangbao.eblog.util.UserUtils;
import com.zhangbao.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CommentService commentService;
    @Autowired
    UserMessageService userMessageService;
    @Autowired
    UserService userService;
    @Autowired
    WebsocketService websocketService;

    @Override
    public IPage<PostVo> paging(Page page, Long categoryId,Long userId, Integer level, Boolean recommend, String order) {
        if(level == null) level= -1;

        QueryWrapper queryWrapper = new QueryWrapper<Post>()
                .eq(categoryId != null,"category_id",categoryId)
                .eq(userId != null,"user_id",userId)
                .eq(level == 0,"level",0)
                .gt(level > 0,"level",0)
                .eq("p.del_flag",0)
                .orderByDesc(order != null,order)
                ;
        if(page!=null){

            return baseMapper.pagingByWrapper(page,queryWrapper);
        }else {
            IPage<PostVo> iPage = new Page<>();
            List<PostVo> list = baseMapper.pagingByWrapper(queryWrapper);
            iPage.setRecords(list);
            return iPage;
        }
    }

    @Override
    public IPage<PostVo> pagingByMap(Page page, Map<String, Object> map) {
        if(page!=null){

            return page.setRecords(baseMapper.pagingByMap(page,map));
        }else {
            IPage<PostVo> iPage = new Page<>();
            List<PostVo> list = baseMapper.pagingByMap(map);
            iPage.setRecords(list);
            return iPage;
        }
    }

    @Override
    public PostVo selectOnePost(QueryWrapper<Post> wrapper) {
        return baseMapper.selectOnePost(wrapper);
    }

    @Override
    public void initWeekRank() {
        //获取7天内发布的文章
        List<Post> posts = baseMapper.selectList(new QueryWrapper<Post>()
                .gt("created", DateUtil.lastWeek())
                .eq("del_flag",0)
                .select("id,title,comment_count,view_count,user_id,created")
        );
        //初始化文章总评论量
        for (Post post : posts) {
            String key = "day:rank:"+DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);
            redisUtil.zSet(key,post.getId(),post.getCommentCount());
            //设置过期时间,七天后自动过期，7-(now-created)
            long day = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
            long expireTime = (7-day)*24*60*1000;//有效时间
            redisUtil.expire(key,expireTime);

            //缓存文章的基本信息（id,标题，评论数量，作者）
            this.hashCachePostIdAndTiel(post,expireTime);
        }

        //做并集
        this.zUnionAndStoreLast7DayForWeekRank();
    }

    /**
     * 新增评论数量
     * @param postId
     * @param isIncr
     */
    @Override
    public void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr) {
        String key = "day:rank:"+DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        redisUtil.zIncrementScore(key,postId,isIncr?1:-1);
        Post post = this.getById(postId);
        //设置过期时间,七天后自动过期，7-(now-created)
        long day = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
        long expireTime = (7-day)*24*60*1000;//有效时间
        this.hashCachePostIdAndTiel(post,expireTime);

        //重新做并集
        this.zUnionAndStoreLast7DayForWeekRank();

    }

    @Override
    public void putViewCount(PostVo one) {
        String key = "rank:post:"+one.getId();
        //1判断缓存中有没有
        Integer viewCount = (Integer) redisUtil.hget(key,"post:viewCount");
        //没有则从实体类中获取
        if(viewCount!=null){
            one.setViewCount(viewCount+1);
        }else {
            one.setViewCount(one.getViewCount()+1);
        }
        //缓存到redis中
        redisUtil.hset(key,"post:viewCount",one.getViewCount());
    }

    @Override
    public Result commentReply(Post post, String content) {
        Comment comment = new Comment();
        comment.preInsert();
        comment.setContent(content);
        comment.setPostId(post.getId());
        comment.setUserId(UserUtils.getUserId());
        commentService.save(comment);
        post.setCommentCount(post.getCommentCount()+1);
        this.updateById(post);

        //本周热议数量加1
        this.incrCommentCountAndUnionForWeekRank(post.getId(),true);

        //通知被@的人，有人回复了你的评论,不用通知自己发的文章
        if(content.startsWith("@")&&comment.getUserId()!=post.getUserId()){
            String username = content.substring(1, content.indexOf(" "));
            User one = userService.getOne(new QueryWrapper<User>()
                    .lambda().eq(User::getUsername, username)
                    .eq(User::getDelFlag, 0)
            );
            if(one!=null){
                UserMessage userMessage = new UserMessage();
                userMessage.preInsert();
                userMessage.setFromUserId(UserUtils.getUserId());
                userMessage.setPostId(post.getId());
                userMessage.setToUserId(one.getId());
                userMessage.setCommentId(comment.getId());
                userMessage.setContent(comment.getContent());
                userMessage.setType(2);//回复被@的人
                userMessageService.save(userMessage);

                //即时通知
                websocketService.sendMsgCountToUser(userMessage.getToUserId());
            }
        }else if(comment.getUserId()!=post.getUserId()){//通知作者，有人评论了你的文章，不用通知自己发的文章
            UserMessage userMessage = new UserMessage();
            userMessage.preInsert();
            userMessage.setPostId(post.getId());
            userMessage.setCommentId(comment.getId());
            userMessage.setToUserId(post.getUserId());
            userMessage.setContent(comment.getContent());
            userMessage.setFromUserId(UserUtils.getUserId());
            userMessage.setType(1);//对文章的评论
            userMessageService.save(userMessage);
            //即时通知
            websocketService.sendMsgCountToUser(userMessage.getToUserId());
        }



        return Result.succ().action("/post/detail/"+post.getId());
    }

    /**
     * 合并本周每日评论数量
     * day:rank:20200620 val1 score     day:rank:20200620 val2 score        。。。
     * day:rank:20200621 val2 score     day:rank:20200621 val4 score        。。。
     * day:rank:20200622 val3 score     day:rank:20200622 val1 score        。。。
     * ...
     * 合并后
     * week:rank val1 score  val2 score  val3 score
     * redis查看命令：ZREVRANGE week:rank 0 -1 withscores
     */
    private void zUnionAndStoreLast7DayForWeekRank() {
        String destKey = "week:rank";//
        String key = "day:rank:"+DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);

        List<String> otherKeys = new ArrayList<>();
        for (int i = -6; i <0 ; i++) {
            String format = "day:rank:"+DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);
            otherKeys.add(format);
        }

        redisUtil.zUnionAndStore(key,otherKeys,destKey);
    }

    /**
     * 缓存文章的基本信息
     * @param post
     * @param expireTime
     */
    private void hashCachePostIdAndTiel(Post post, long expireTime) {
        String key = "rank:post:"+post.getId();
        boolean hasKey = redisUtil.hasKey(key);
        if(!hasKey){
            redisUtil.hset(key,"post:id",post.getId(),expireTime);
            redisUtil.hset(key,"post:title",post.getTitle());
            redisUtil.hset(key,"post:commentCount",post.getCommentCount());
            redisUtil.hset(key,"post:viewCount",post.getViewCount());
        }
    }
}
