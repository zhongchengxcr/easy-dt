package com.zc.distributed.transaction.reliable.message.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:15
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class TransactionMessage implements Serializable {

    private String messageId;

    private String domain;

    private Object messageBody;

    private String consumerQueue;

    private Integer messageSendTimes;

    private String status;

    private Boolean dead;

    private String ext1;

    private String ext2;

    private String ext3;

    private Date createTime;

    private Date updateTime;

    public String getMessageId() {
        return messageId;
    }

    public TransactionMessage setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public TransactionMessage setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public Object getMessageBody() {
        return messageBody;
    }

    public TransactionMessage setMessageBody(Object messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public String getConsumerQueue() {
        return consumerQueue;
    }

    public TransactionMessage setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue;
        return this;
    }

    public Integer getMessageSendTimes() {
        return messageSendTimes;
    }

    public TransactionMessage setMessageSendTimes(Integer messageSendTimes) {
        this.messageSendTimes = messageSendTimes;
        return this;
    }

    public Boolean getDead() {
        return dead;
    }

    public TransactionMessage setDead(Boolean dead) {
        this.dead = dead;
        return this;
    }

    public String getExt1() {
        return ext1;
    }

    public TransactionMessage setExt1(String ext1) {
        this.ext1 = ext1;
        return this;
    }

    public String getExt2() {
        return ext2;
    }

    public TransactionMessage setExt2(String ext2) {
        this.ext2 = ext2;
        return this;
    }

    public String getExt3() {
        return ext3;
    }

    public TransactionMessage setExt3(String ext3) {
        this.ext3 = ext3;
        return this;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public TransactionMessage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public TransactionMessage setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TransactionMessage setStatus(String status) {
        this.status = status;
        return this;
    }

    public void addSendTimes() {
        this.messageSendTimes += 1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionMessage{");
        sb.append("messageId='").append(messageId).append('\'');
        sb.append(", domain='").append(domain).append('\'');
        sb.append(", messageBody=").append(messageBody);
        sb.append(", consumerQueue='").append(consumerQueue).append('\'');
        sb.append(", messageSendTimes=").append(messageSendTimes);
        sb.append(", dead='").append(dead).append('\'');
        sb.append(", ext1='").append(ext1).append('\'');
        sb.append(", ext2='").append(ext2).append('\'');
        sb.append(", ext3='").append(ext3).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
