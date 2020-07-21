package com.zhangbao.eblog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhangbao.eblog.search.model.PostDocument;
import com.zhangbao.eblog.search.mq.PostMqIndexMessage;
import com.zhangbao.eblog.search.repository.PostRepository;
import com.zhangbao.eblog.service.SearchService;
import com.zhangbao.eblog.vo.PostVo;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    PostRepository postRepository;

    @Override
    public IPage search(Page page, String keyword) {
        //将mybatis plus分页转成 jpa的分页
        Long current = page.getCurrent()-1;
        Long size = page.getSize();
        Pageable pageable = PageRequest.of(current.intValue(),size.intValue());
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "categoryName", "authorName");
        org.springframework.data.domain.Page<PostDocument> search = postRepository.search(matchQueryBuilder, pageable);
        //            输出查询字符串
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder);
        LOGGER.error("elasticsearch查询请求 === {}",searchSourceBuilder.toString());

        //将jpa分页 转成 mybatis plus分页
        IPage<PostDocument> iPage = new Page(current,size,search.getTotalElements());
        iPage.setRecords(search.getContent());

        return iPage;
    }

    @Override
    public int initEsData(List<PostVo> records) {
        if(records==null || records.size()==0){
            return 0;
        }
        List<PostDocument> list = Lists.newArrayList();
        for (PostVo record : records){
            PostDocument postDocument = new PostDocument();
            BeanUtils.copyProperties(record, postDocument);
            list.add(postDocument);
        }
        postRepository.saveAll(list);
        return list.size();
    }

    @Override
    public void createOrUpdate(PostMqIndexMessage message) {

    }
}
