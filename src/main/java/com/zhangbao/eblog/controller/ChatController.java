package com.zhangbao.eblog.controller;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import com.zhangbao.eblog.common.lang.Consts;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.im.vo.ImUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-18:52
 */
@RestController
@RequestMapping("/chat")
public class ChatController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @PostMapping("/getMineAndGroup")
    @ResponseBody
    public Result getMineAndGroup(){

        //自定义群聊
        Map<String,Object> map = Maps.newHashMap();
        map.put("name","小豹群聊");
        map.put("type","group");
        map.put("avatar","http://tp2.sinaimg.cn/5488749285/50/5719808192/1");
        map.put("id", Consts.IM_GROUP_ID);
        map.put("members",2);

        ImUser imUser = chatService.getCurrentUser();

        return Result.succ(MapUtil.builder().put("group",map).put("mine",imUser).map());
    }

    @ResponseBody
    @PostMapping("/getGroupHistoryMsg")
    public Result getGroupHistoryMsg(String id,String type){
        List<Object> msgList = chatService.getGroupHistoryMsg(20);
        return Result.succ(msgList);
    }

}
