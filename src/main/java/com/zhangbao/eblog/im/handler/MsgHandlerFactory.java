package com.zhangbao.eblog.im.handler;

import com.zhangbao.eblog.common.lang.Consts;
import com.zhangbao.eblog.im.server.ImServerStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-17:54
 */
public class MsgHandlerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgHandlerFactory.class);

    private static Map<String,MsgHandler> handlerMap = new HashMap<>();

    public static void  init(){
        handlerMap.put(Consts.IM_MESS_TYPE_CHAT,new ChatMsgHandler());
        handlerMap.put(Consts.IM_MESS_TYPE_PING,new PingMsgHandler());
        LOGGER.error("handler init !!!");
    }

    public static MsgHandler getMsgHandler(String type){
        MsgHandler msgHandler = handlerMap.get(type);
        return msgHandler;
    }

}
