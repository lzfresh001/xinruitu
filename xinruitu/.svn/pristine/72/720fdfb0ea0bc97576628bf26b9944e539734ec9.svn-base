package com.yx.xinruitu.entity.msg.strategy;

import com.yx.xinruitu.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 扫描带参数二维码
 */
public class ScanMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "扫码成功");
    }
}
