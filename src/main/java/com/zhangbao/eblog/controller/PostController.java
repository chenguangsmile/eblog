package com.zhangbao.eblog.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.common.lang.ResultData;
import com.zhangbao.eblog.entity.*;
import com.zhangbao.eblog.util.ValidationUtil;
import com.zhangbao.eblog.vo.CommentVo;
import com.zhangbao.eblog.vo.PostVo;
import javafx.geometry.Pos;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Controller
@RequestMapping("/post")
public class PostController extends BaseController {


    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable(name = "id")Long id){
        int pageNum = ServletRequestUtils.getIntParameter(req, "pageNum", 1);

        req.setAttribute("currentCategoryId",id);
        req.setAttribute("pageNum",pageNum);
        return "post/category";
    }

    @GetMapping("/detail/{id:\\d*}")
    public String detail(@PathVariable(name = "id")Long id){

        PostVo  one = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id",id).eq("p.del_flag",0));
        Assert.notNull(one,"文章已被删除");
        //分页评论信息，文章id，用户id，排序
        IPage<CommentVo> ipage = commentService.paging(getPage(),one.getId(),null,"created");
        //添加阅读量
        postService.putViewCount(one);

        req.setAttribute("currentCategoryId",one.getCategoryId());
        req.setAttribute("post",one);
        req.setAttribute("pageData",ipage);
        return "post/detail";
    }

    @ResponseBody
    @PostMapping("/collection/find")
    public Result collectionFind(Long pid){

        int count = userCollectionService.count(new QueryWrapper<UserCollection>()
                .lambda().eq(UserCollection::getUserId, getUserId())
                .eq(UserCollection::getPostId,pid)
                .eq(UserCollection::getDelFlag, 0)
                .eq(UserCollection::getStatus,0)
        );

        return Result.succ(MapUtil.of("collection",count>0));
    }

    /**
     * 收藏
     * @param pid
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/add")
    public Result collectionAdd(Long pid){

        Post post = postService.getById(pid);
        Assert.isTrue(post!=null,"该帖子已删除");

        UserCollection one = userCollectionService.getOne(new QueryWrapper<UserCollection>()
                .lambda().eq(UserCollection::getUserId, getUserId())
                .eq(UserCollection::getPostId, pid)
                .eq(UserCollection::getDelFlag, 0)
        );
        if(one!=null){
            if(one.getStatus()==0){
                return  Result.fail("该帖子已收藏");
            }else if(one.getStatus()==1){
                UserCollection userCollection = new UserCollection().setStatus(0);
                userCollectionService.update(userCollection,new QueryWrapper<UserCollection>()
                    .lambda().eq(UserCollection::getUserId,getUserId())
                    .eq(UserCollection::getPostId,pid)
                );
                return Result.succ();
            }
        }

        UserCollection userCollection = new UserCollection();
        userCollection.preInsert();
        userCollection.setUserId(getUserId());
        userCollection.setPostId(pid);
        userCollection.setPostUserId(post.getUserId());
        userCollectionService.save(userCollection);
        return Result.succ();
    }

    /**
     * 取消收藏
     * @param pid
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/remove")
    public Result collectionRemove(Long pid){

        Post post = postService.getById(pid);
        Assert.isTrue(post!=null,"该帖子已删除");

        UserCollection userCollection = new UserCollection();
        userCollection.setStatus(1);//删除标记

        userCollectionService.update(userCollection,new QueryWrapper<UserCollection>()
            .lambda().eq(UserCollection::getUserId,getUserId())
            .eq(UserCollection::getPostId,pid)
        );

        return Result.succ();
    }

    @GetMapping("/edit")
    public String edit(){

        String id = req.getParameter("id");
        if(StrUtil.isNotEmpty(id)){
            Post post = postService.getById(id);
            Assert.isTrue(post!=null,"该帖子已删除");
            Assert.isTrue(post.getUserId()==getUserId(),"没权限操作此文章");
            req.setAttribute("post",post);
        }

        List<Category> list = categoryService.list(new QueryWrapper<Category>()
                .lambda().eq(Category::getDelFlag, 0)
        );
        req.setAttribute("categories",list);

        return "post/edit";
    }

    @ResponseBody
    @PostMapping("/edit/submit")
    public Result editSubmit(Post post){
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(post);
        if(validResult.hasErrors()){
            return Result.fail(validResult.getErrors());
        }

        if(post.getId()==null){
            post.preInsert();
            post.setUserId(getUserId());
            postService.save(post);
        }else {
            Post temPost = postService.getById(post.getId());
            Assert.isTrue(temPost.getUserId()==getUserId(),"没有权限编辑此文章");

            temPost.setTitle(post.getTitle());
            temPost.setContent(post.getContent());
            temPost.setCategoryId(post.getCategoryId());
            temPost.setModified(new Date());
            postService.updateById(temPost);
        }


        return Result.succ().action("/post/detail/"+post.getId());
    }

    @ResponseBody
    @PostMapping("/remove")
    public Result remove(Long id){

        Post post = postService.getById(id);
        Assert.notNull(post,"该帖子已被删除");
        Assert.isTrue(post.getUserId()==getUserId(),"无权限删除此文章");


        postService.removeById(id);
        userMessageService.removeByMap(MapUtil.of("post_id",id));
        userCollectionService.removeByMap(MapUtil.of("post_id",id));
        return Result.succ();
    }

    /**
     * 评论文章
     * @param jid
     * @param content
     * @return
     */
    @ResponseBody
    @PostMapping("/reply")
    public Result reply(Long jid,String content){

        Assert.notNull(jid,"找不到对应文章");
        Assert.hasLength(content,"评论不能为空");

        Post post = postService.getById(jid);
        Assert.notNull(post,"该帖子已被删除");

        Result result = postService.commentReply(post,content);

        return result;
    }
    @ResponseBody
    @PostMapping("/jieda-delete")
    public Result delete(Long id){

        Assert.notNull(id,"评论id不能为空");
        Comment comment = commentService.getById(id);
        Assert.notNull(comment,"评论已删除");
        if(!comment.getUserId().equals(getUserId())){
            return Result.fail("不是你发表的评论");
        }
        commentService.removeById(id);
        Post post = postService.getById(comment.getPostId());
        post.setCommentCount(post.getCommentCount()-1);
        postService.updateById(post);
        //评论减一
        postService.incrCommentCountAndUnionForWeekRank(comment.getPostId(),false);

        return Result.succ();
    }

}
