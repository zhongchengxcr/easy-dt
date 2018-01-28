package com.zc.distributed.transaction.reliable.message.service;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/28 下午1:25
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public enum MessageStatusEnum {

    /**
     * 等待确认
     */
    WAITING_CONFIRM("待确认"),

    SENDING("发送中");

    /**
     * 描述
     */
    private String desc;

    MessageStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static MessageStatusEnum getEnum(String name) {
        MessageStatusEnum[] arr = MessageStatusEnum.values();
        for (MessageStatusEnum messageStatusEnum : arr) {
            if (messageStatusEnum.name().equalsIgnoreCase(name)) {
                return messageStatusEnum;
            }
        }
        return null;
    }


}
