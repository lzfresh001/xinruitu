package com.yx.xinruitu.entity.msg.chain;

import com.yx.xinruitu.util.wechat.WeChatConstant;

import java.util.Map;

public abstract class AbstractTextChain {

    protected AbstractTextChain next;
    private String[] keywords;

    public AbstractTextChain(String... keywords) {
        this.keywords = keywords;
    }

    public void setNext(AbstractTextChain next) {
        this.next = next;
    }

    public String sendMsg(Map<String,String> requestMap) {
        for (String keyword : this.keywords) {
            if (requestMap.get(WeChatConstant.CONTENT).contains(keyword)) {
                return send(requestMap);
            }
        }
        return next != null ? next.sendMsg(requestMap) : "";
    }

    abstract protected String send(Map<String, String> requestMap);
}
