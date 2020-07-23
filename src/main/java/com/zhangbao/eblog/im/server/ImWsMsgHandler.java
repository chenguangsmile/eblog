package com.zhangbao.eblog.im.server;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.zhangbao.eblog.im.handler.MsgHandler;
import com.zhangbao.eblog.im.handler.MsgHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Map;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-16:58
 */
public class ImWsMsgHandler implements IWsMsgHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImWsMsgHandler.class);

    /**
     * 握手
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

        return httpResponse;
    }

    /**
     * 握手后
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

    }

    /**
     * 接收字节方法
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 接收字符方法
     * @param wsRequest
     * @param text：{"data":"","type":""}
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        LOGGER.error("接收消息 -- {}",text);

        Map map = JSONUtil.toBean(text, Map.class);
        String type = MapUtil.getStr(map, "type");
        String data = MapUtil.getStr(map, "data");

        //根据类型获取对应的处理器
        MsgHandler msgHandler = MsgHandlerFactory.getMsgHandler(type);
        //处理消息

        return null;
    }

    /**
     * 链接关闭
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }
}
