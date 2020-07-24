package com.zhangbao.eblog.im.handler;

import cn.hutool.json.JSONUtil;
import com.zhangbao.eblog.common.lang.Consts;
import com.zhangbao.eblog.im.vo.ChatImMessage;
import com.zhangbao.eblog.im.vo.ImTo;
import com.zhangbao.eblog.im.vo.ImUser;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-17:45
 */
public class ChatMsgHandler implements MsgHandler {
    @Override
    public void handler(String data, WsRequest wsRequest, ChannelContext channelContext) {
        ChatImMessage chatImMessage = JSONUtil.toBean(data, ChatImMessage.class);
        ImUser imUser = chatImMessage.getImUser();
        ImTo imTo = chatImMessage.getImTo();

        //发送
        WsResponse wsResponse = WsResponse.fromText("", "utf-8");
        Tio.sendToGroup(channelContext.getTioConfig(), Consts.IM_GROUP_NAME,wsResponse);

    }
}
