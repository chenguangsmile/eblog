package com.zhangbao.eblog.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.entity.UserMessage;
import com.zhangbao.eblog.entity.UserMessageVo;
import com.zhangbao.eblog.mapper.UserMessageMapper;
import com.zhangbao.eblog.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

    @Override
    public IPage<UserMessageVo> paging(Page page, LambdaQueryWrapper<UserMessage> userMessageQueryWrapper) {
        return this.baseMapper.paging(page,userMessageQueryWrapper);
    }

    @Override
    public void updateToReaded(List<Long> ids) {
        if(ids.isEmpty()) return;

        baseMapper.updateToReaded(new QueryWrapper<UserMessage>()
            .in("id",ids)
        );

    }
}
