package com.zhangbao.eblog.search.repository;

import com.zhangbao.eblog.search.model.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ElasticsearchCrudRepository<PostDocument,Long> {
}
