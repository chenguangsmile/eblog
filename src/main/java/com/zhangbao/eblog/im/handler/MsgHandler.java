package com.zhangbao.eblog.im.handler;

import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-17:45
 */
public interface MsgHandler {
    void handler(String data, WsRequest wsRequest, ChannelContext channelContext);
}
