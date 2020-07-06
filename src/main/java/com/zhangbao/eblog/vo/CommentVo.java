package com.zhangbao.eblog.vo;

import com.zhangbao.eblog.entity.Comment;
import lombok.Data;

@Data
public class CommentVo extends Comment {
    private Long authorId;
    private String authorName;
    private String authorAvatar;

}
