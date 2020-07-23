package com.zhangbao.eblog.search.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sunny
 * @create 2020-07-2020/7/21-18:09
 */
@Data
@AllArgsConstructor
public class PostMqIndexMessage implements Serializable {

    public static final String CREATE_UPDATE = "create_update";
    public static final String DELETE = "delete";

    private Long postId;
    private String type;

}
