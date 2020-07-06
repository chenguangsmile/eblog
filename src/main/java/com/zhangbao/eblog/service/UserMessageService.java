package com.zhangbao.eblog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangbao.eblog.entity.UserMessageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
public interface UserMessageService extends IService<UserMessage> {

    IPage<UserMessageVo> paging(Page page, LambdaQueryWrapper<UserMessage> userMessageQueryWrapper);

    void updateToReaded(List<Long> ids);
}
