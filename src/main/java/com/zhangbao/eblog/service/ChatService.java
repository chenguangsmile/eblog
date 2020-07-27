package com.zhangbao.eblog.service;

import com.zhangbao.eblog.im.vo.ImMessage;
import com.zhangbao.eblog.im.vo.ImUser;

import java.util.List;

/**
 * @author Sunny
 * @create 2020-07-2020/7/23-19:16
 */
public interface ChatService {
    ImUser getCurrentUser();

    void setGroupHistoryMsg(ImMessage imMessage);

    List<Object> getGroupHistoryMsg(int count);

}
