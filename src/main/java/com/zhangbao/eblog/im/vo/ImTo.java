package com.zhangbao.eblog.im.vo;

import lombok.Data;

/**
 * 群组信息
 * @author Sunny
 * @create 2020-07-2020/7/24-15:54
 */
@Data
public class ImTo {
    private String username;
    private Long id;
    private String type;//聊天类型，一般分friend和group两种，group即群聊
    private String avatar;
    private Integer members;
}
