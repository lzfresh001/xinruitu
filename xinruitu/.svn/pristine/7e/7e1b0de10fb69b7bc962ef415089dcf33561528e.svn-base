package com.yx.xinruitu.entity.msg.strategy;

import com.yx.xinruitu.util.wechat.SendUtil;

import java.util.Map;

/**
 * 图片消息
 */
public class ImageMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是图片消息！");
    }
}
