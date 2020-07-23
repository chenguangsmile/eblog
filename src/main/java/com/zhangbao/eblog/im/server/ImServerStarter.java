package com.zhangbao.eblog.im.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.server.ServerTioConfig;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.io.IOException;

/**
 * 启动tio服务
 * @author Sunny
 * @create 2020-07-2020/7/23-16:48
 */
public class ImServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImServerStarter.class);

    private WsServerStarter starter;

    public ImServerStarter(int port) throws IOException {

        IWsMsgHandler handler = new ImWsMsgHandler();
        starter = new WsServerStarter(port,handler);

        ServerTioConfig serverTioConfig = starter.getServerTioConfig();
        //设置心跳，如果没有消息则关闭链接
        serverTioConfig.setHeartbeatTimeout(500000);
    }

    public void start() throws IOException {
        starter.start();
        LOGGER.error("tio server start success!!!");
    }

}
