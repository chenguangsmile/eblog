package com.zhangbao.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbao.eblog.entity.UserMessage;
import com.zhangbao.eblog.service.UserMessageService;
import com.zhangbao.eblog.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WebsocketServiceImpl implements WebsocketService {

    @Autowired
    UserMessageService userMessageService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @Override
    public void sendMsgCountToUser(Long toUserId) {
        int count = userMessageService.count(new QueryWrapper<UserMessage>()
                .lambda().eq(UserMessage::getToUserId, toUserId)
                .eq(UserMessage::getStatus,0)
                .eq(UserMessage::getDelFlag, 0)
        );
        ///user/7/messCount
        simpMessagingTemplate.convertAndSendToUser(toUserId.toString(),"/messCount",count);
    }
}
