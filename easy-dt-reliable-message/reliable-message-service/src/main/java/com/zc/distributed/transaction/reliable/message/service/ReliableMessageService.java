package com.zc.distributed.transaction.reliable.message.service;

import com.zc.distributed.transaction.reliable.message.dto.Page;
import com.zc.distributed.transaction.reliable.message.dto.TransactionMessage;
import com.zc.distributed.transaction.reliable.message.exception.ReliableMessageException;

import java.util.List;
import java.util.Map;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:13
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface ReliableMessageService {

    /**
     * 预存储消息
     *
     * @param transactionMessage 消息
     * @return 消息id
     * @throws ReliableMessageException e
     */
    String preSaveMessage(TransactionMessage transactionMessage) throws ReliableMessageException;


    /**
     * 确认并发送消息
     *
     * @param messageId 消息id
     * @throws ReliableMessageException e
     */
    void confirmAndSendMessage(String messageId) throws ReliableMessageException;


    /**
     * 存储并发送
     *
     * @param transactionMessage 消息
     * @return 消息id
     * @throws ReliableMessageException e
     */
    String saveAndSendMessage(TransactionMessage transactionMessage) throws ReliableMessageException;


    /**
     * 直接发送消息
     *
     * @param transactionMessage 消息
     * @throws ReliableMessageException e
     */
    void sendMessage(TransactionMessage transactionMessage) throws ReliableMessageException;


    /**
     * 重发消息
     *
     * @param transactionMessage 消息
     * @throws ReliableMessageException e
     */
    void reSendMessage(TransactionMessage transactionMessage) throws ReliableMessageException;


    /**
     * 根据messageId重发消息
     *
     * @param messageId 消息id
     * @throws ReliableMessageException e
     */
    void reSendMessageByMessageId(String messageId) throws ReliableMessageException;

    /**
     * 将消息标记为死亡消息.
     *
     * @param messageId 消息id
     * @throws ReliableMessageException e
     */
    void setMessageToDead(String messageId) throws ReliableMessageException;


    /**
     * 根据消息ID获取消息
     *
     * @param messageId 消息id
     * @return 消息
     * @throws ReliableMessageException e
     */
    TransactionMessage getMessageByMessageId(String messageId) throws ReliableMessageException;

    /**
     * 根据消息ID删除消息
     *
     * @param messageId 消息id
     * @throws ReliableMessageException e
     */
    void deleteMessageByMessageId(String messageId) throws ReliableMessageException;


    /**
     * 重发某个消息队列中的全部已死亡的消息.
     *
     * @param queueName 队列名称
     * @param batchSize size
     * @throws ReliableMessageException e
     */
    void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws ReliableMessageException;


    /**
     *
     *
     * @param transactionMessagePage 参数
     * @return 消息列表
     * @throws ReliableMessageException e
     */

    /**
     * 分页获取消息
     * @param transactionMessage 参数
     * @param pageNum 每页多少条
     * @param pageCount 目标页
     * @return 列表
     * @throws ReliableMessageException e
     */
    Page<TransactionMessage> page(TransactionMessage transactionMessage, int pageNum, int pageCount) throws ReliableMessageException;
}
