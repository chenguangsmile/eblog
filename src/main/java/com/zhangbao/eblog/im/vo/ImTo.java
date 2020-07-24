package com.zhangbao.eblog.im.vo;

import lombok.Data;

/**
 * @author Sunny
 * @create 2020-07-2020/7/24-15:54
 */
@Data
public class ImTo {
    private String username;
    private Long id;
    private String type;
    private String avatar;
    private Integer members;
}
