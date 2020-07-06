package com.zhangbao.eblog.vo;

import com.zhangbao.eblog.entity.Post;
import lombok.Data;

@Data
public class PostVo extends Post {

    private Long authorId;
    private String authorName;
    private String authorAvatar;

    private String categoryName;
}
