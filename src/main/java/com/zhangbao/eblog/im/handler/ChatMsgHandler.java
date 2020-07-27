package com.zhangbao.eblog.im.handler;

import cn.hutool.json.JSONUtil;
import com.zhangbao.eblog.common.lang.Consts;
import com.zhangbao.eblog.config.ApplicationContextRegister;
import com.zhangbao.eblog.im.handler.filter.ExcludeMineChannelContextFilter;
import com.zhangbao.eblog.im.message.ChatImMessage;
import com.zhangbao.eblog.im.message.ChatOutMessage;
import com.zhangbao.eblog.im.vo.ImMessage;
import com.zhangbao.eblog.im.vo.ImTo;
import com.zhangbao.eblog.im.vo.ImUser;
import com.zhangbao.eblog.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

import java.util.Date;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-17:45
 */
public class ChatMsgHandler implements MsgHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMsgHandler.class);

    ChatService chatService = ApplicationContextRegister.getBean(ChatService.class);

    @Override
    public void handler(String data, WsRequest wsRequest, ChannelContext channelContext) {
        ChatImMessage chatImMessage = JSONUtil.toBean(data, ChatImMessage.class);
        ImUser mine = chatImMessage.getMine();
        ImTo to = chatImMessage.getTo();

        ImMessage imMessage = new ImMessage();
        imMessage.setAvatar(mine.getAvatar());
        imMessage.setContent(mine.getContent());
        imMessage.setUsername(mine.getUsername());
        imMessage.setFromid(mine.getId());
        imMessage.setMine(false);

        imMessage.setId(Consts.IM_GROUP_ID);
        imMessage.setTimestamp(new Date());
        imMessage.setType(to.getType());

        ChatOutMessage outMessage = new ChatOutMessage();
        outMessage.setEmit("chatMessage");
        outMessage.setData(imMessage);

        String result = JSONUtil.toJsonStr(outMessage);
        LOGGER.error("群聊消息 -- > {}",result);

        //发送
        WsResponse wsResponse = WsResponse.fromText(result, "utf-8");

        ExcludeMineChannelContextFilter filter = new ExcludeMineChannelContextFilter();
        filter.setCurrentChannelContext(channelContext);

        Tio.sendToGroup(channelContext.getTioConfig(), Consts.IM_GROUP_NAME,wsResponse,filter);

        //保存群聊信息
        chatService.setGroupHistoryMsg(imMessage);

    }
}
