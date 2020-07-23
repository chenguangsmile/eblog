package com.zhangbao.eblog.config;

import com.zhangbao.eblog.im.handler.MsgHandlerFactory;
import com.zhangbao.eblog.im.server.ImServerStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-16:50
 */
@Configuration
public class ImServerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImServerConfig.class);

    @Value("${im.server.port}")
    private int imPort;

    @Bean
    public ImServerStarter imServerStarter(){
        try {
            //启动tio服务
            ImServerStarter imServerStarter = new ImServerStarter(imPort);
            imServerStarter.start();
            //初始化消息服务类别
            MsgHandlerFactory.init();

            return imServerStarter;
        } catch (IOException e) {
            LOGGER.error("tio 服务启动失败 - {}",e.getMessage());
        }

        return null;
    }



}
