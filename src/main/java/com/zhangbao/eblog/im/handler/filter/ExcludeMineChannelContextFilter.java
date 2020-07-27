package com.zhangbao.eblog.im.handler.filter;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.ChannelContextFilter;

/**
 * @author Sunny
 * @create 2020-07-2020/7/27-14:24
 */
@Data
@Slf4j
public class ExcludeMineChannelContextFilter implements ChannelContextFilter {

    private ChannelContext currentChannelContext;

    @Override
    public boolean filter(ChannelContext channelContext) {

        log.error("current channel userId -- > {}",currentChannelContext.userid);
        log.error("receive channel userId -- > {}",channelContext.userid);
        //过滤当前用户，不发送消息
        if(currentChannelContext.userid.equals(channelContext.userid)){
            return false;
        }

        return true;
    }
}
