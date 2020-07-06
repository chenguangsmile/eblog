package com.zhangbao.eblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangbao.eblog.vo.PostVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
public interface PostService extends IService<Post> {

    IPage<PostVo> paging(Page page, Long categoryId,Long userId, Integer level, Boolean recommend, String order);
    IPage<PostVo> pagingByMap(Page page, Map<String, Object> map);

    PostVo selectOnePost(QueryWrapper<Post> wrapper);

    void initWeekRank();

    void incrCommentCountAndUnionForWeekRank(long postId,boolean isIncr);

    void putViewCount(PostVo one);

    Result commentReply(Post postId,String content);
}
