package com.zhangbao.eblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbao.eblog.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<CommentVo> paging(Page page, @Param(Constants.WRAPPER)QueryWrapper<Comment> orderByDesc);
}
