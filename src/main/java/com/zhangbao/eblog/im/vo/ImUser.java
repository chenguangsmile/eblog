package com.zhangbao.eblog.im.vo;

import lombok.Data;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-19:09
 */
@Data
public class ImUser {

    public static final String ONLINE_STATUS = "online";
    public static final String HIDE_STATUS = "hide";

    private String username;
    private Long id;
    private String status;
    private String sign;
    private String avatar;

}
