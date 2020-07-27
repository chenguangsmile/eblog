package com.zhangbao.eblog.im.message;

import com.zhangbao.eblog.im.vo.ImTo;
import com.zhangbao.eblog.im.vo.ImUser;
import lombok.Data;

/**
 * @author Sunny
 * @create 2020-07-2020/7/24-16:30
 */
@Data
public class ChatImMessage {

    private ImUser mine;
    private ImTo to;

}
