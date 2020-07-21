package com.zhangbao.eblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.search.mq.PostMqIndexMessage;
import com.zhangbao.eblog.vo.PostVo;

import java.util.List;

public interface SearchService {
    IPage search(Page page, String keyword);

    int initEsData(List<PostVo> records);

    void createOrUpdate(PostMqIndexMessage message);
}
