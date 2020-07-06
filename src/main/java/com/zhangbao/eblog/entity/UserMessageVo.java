package com.zhangbao.eblog.entity;

import lombok.Data;

@Data
public class UserMessageVo extends UserMessage {

    private String fromUserName;
    private String toUserName;
    private String postTitle;
    private String commentContent;

}
