package com.yx.xinruitu.entity.msg.strategy;

import com.yx.xinruitu.entity.msg.chain.AbstractTextChain;
import com.yx.xinruitu.entity.msg.chain.BaiduTextChain;
import com.yx.xinruitu.entity.msg.chain.TencentTextChain;

import java.util.Map;

/**
 * 文字消息
 */
public class TextMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        // 拼装责任链
        AbstractTextChain tencentChain = new TencentTextChain();
        AbstractTextChain baiduChain = new BaiduTextChain();
        tencentChain.setNext(baiduChain);
        // 交给责任链执行
        return tencentChain.sendMsg(requestMap);
    }
}
