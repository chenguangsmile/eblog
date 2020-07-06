package com.zhangbao.eblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbao.eblog.vo.PostVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
public interface PostMapper extends BaseMapper<Post> {

    IPage<PostVo> pagingByWrapper(Page page, @Param(Constants.WRAPPER)QueryWrapper queryWrapper);
    List<PostVo> pagingByWrapper(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);
    List<PostVo> pagingByMap(Page page, @Param("map")Map<String, Object> map);
    List<PostVo> pagingByMap(@Param("map")Map<String, Object> map);

    PostVo selectOnePost(@Param(Constants.WRAPPER)QueryWrapper<Post> wrapper);

}
