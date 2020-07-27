package com.zhangbao.eblog.im.message;

import com.zhangbao.eblog.im.vo.ImMessage;
import lombok.Data;

/**
 * @author Sunny
 * @create 2020-07-2020/7/27-10:18
 */
@Data
public class ChatOutMessage {
    private String emit;
    private ImMessage data;
}
