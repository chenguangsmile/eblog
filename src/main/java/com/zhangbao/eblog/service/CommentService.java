package com.zhangbao.eblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangbao.eblog.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
public interface CommentService extends IService<Comment> {

    IPage<CommentVo> paging(Page page, Long postId, Long userId, String order);
}
