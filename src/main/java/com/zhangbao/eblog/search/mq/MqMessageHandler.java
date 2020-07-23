package com.zhangbao.eblog.search.mq;

import com.zhangbao.eblog.config.RabbitMpConfig;
import com.zhangbao.eblog.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消费队列
 * @author Sunny
 * @create 2020-07-2020/7/21-18:29
 */
@Component
@RabbitListener(queues = RabbitMpConfig.ES_QUEUE)
public class MqMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqMessageHandler.class);

    @Autowired
    private SearchService searchService;

    @RabbitHandler
    public void handler(PostMqIndexMessage message){

        LOGGER.error("mq 收到一条信息： {}",message.toString());

        switch (message.getType()){
            case PostMqIndexMessage.CREATE_UPDATE:
                searchService.createOrUpdateIndex(message);
                break;
            case PostMqIndexMessage.DELETE:
                searchService.removeIndex(message);
                break;
            default:
                LOGGER.error("没找到对应消息类型，请注意！！ --  {}",message.toString());
                break;
        }

    }

}
