package com.zhangbao.eblog.im.vo;

import lombok.Data;

import java.util.Date;

/**
 * 接收消息
 * @author Sunny
 * @create 2020-07-2020/7/27-9:59
 */
@Data
public class ImMessage {

    private String userName;
    private String avatar;
    private String type;//聊天窗口来源类型
    private String content;
    private Long cid;//消息id，可不传。除非你要对消息进行一些操作（如撤回）
    private Boolean mine;
    private Long fromid;
    private Date timestamp;
    private Long id;//消息来源id，（如果是私聊，则是用户id，如果是群聊，则是群组id）


}
