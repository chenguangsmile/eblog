package com.zhangbao.eblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbao.eblog.entity.UserMessageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    IPage<UserMessageVo> paging(Page page, @Param(Constants.WRAPPER) LambdaQueryWrapper<UserMessage> userMessageQueryWrapper);

    @Transactional
    @Update("update m_user_message set status=1 ${ew.customSqlSegment}")
    void updateToReaded(@Param(Constants.WRAPPER)QueryWrapper<UserMessage> id);
}
