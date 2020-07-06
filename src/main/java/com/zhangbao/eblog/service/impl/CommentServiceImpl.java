package com.zhangbao.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.Comment;
import com.zhangbao.eblog.mapper.CommentMapper;
import com.zhangbao.eblog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbao.eblog.vo.CommentVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public IPage<CommentVo> paging(Page page, Long postId, Long userId, String order) {
        return baseMapper.paging(page,new QueryWrapper<Comment>()
            .eq(postId!=null,"post_id",postId)
            .eq(userId!=null,"user_id",userId)
            .orderByDesc("created")
        );
    }
}
