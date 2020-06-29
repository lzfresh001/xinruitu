package com.yx.xinruitu.entity.msg.strategy;

import com.yx.xinruitu.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 关注
 */
public class SubscribeMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        String username=requestMap.get("username");
        return SendUtil.sendTextMsg(requestMap, "感谢关注，您的账号为:"+username+",<br/><a href='http://117.34.125.147/user/bind?username="+username+"'>点击此处完善人员信息！</a>");
    }
}
