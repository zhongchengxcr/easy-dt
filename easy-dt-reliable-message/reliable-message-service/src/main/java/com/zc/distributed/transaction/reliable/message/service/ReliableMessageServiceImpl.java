package com.zc.distributed.transaction.reliable.message.service;

import com.zc.distributed.transaction.reliable.message.dto.Page;
import com.zc.distributed.transaction.reliable.message.dto.TransactionMessage;
import com.zc.distributed.transaction.reliable.message.exception.ReliableMessageException;
import com.zc.distributed.transaction.reliable.message.generator.MessageIdGenerator;
import com.zc.distributed.transaction.reliable.message.mq.TransactionMessageSender;
import com.zc.distributed.transaction.reliable.message.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:44
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class ReliableMessageServiceImpl implements ReliableMessageService {

    private MessageRepository messageRepository;

    private MessageIdGenerator messageIdGenerator;

    private TransactionMessageSender messageSender;

    @Value("${message.max.send.times}")
    private Integer maxTimes;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ReliableMessageServiceImpl(MessageRepository messageRepository, MessageIdGenerator messageIdGenerator, TransactionMessageSender messageSender) {
        this.messageRepository = messageRepository;
        this.messageIdGenerator = messageIdGenerator;
        this.messageSender = messageSender;
    }

    @Override
    public String preSaveMessage(TransactionMessage transactionMessage) throws ReliableMessageException {
        this.messageCheck(transactionMessage);
        String messageId = messageIdGenerator.generator();
        transactionMessage.setMessageId(messageId);
        transactionMessage.setStatus(MessageStatusEnum.WAITING_CONFIRM.name());
        transactionMessage.setDead(false);
        transactionMessage.setMessageSendTimes(0);
        transactionMessage.setCreateTime(new Date());
        transactionMessage.setUpdateTime(new Date());
        messageRepository.saveMessage(transactionMessage);
        return messageId;
    }


    @Override
    public void confirmAndSendMessage(String messageId) throws ReliableMessageException {
        this.messageIdCheck(messageId);
        final TransactionMessage transactionMessage = messageRepository.getByMessageId(messageId);
        if (transactionMessage == null) {
            throw new ReliableMessageException(ReliableMessageException.MESSAGE_NOT_FOUND);
        }
        messageSender.send(transactionMessage);
        transactionMessage.setStatus(MessageStatusEnum.SENDING.name());
        transactionMessage.setUpdateTime(new Date());

        int n = messageRepository.updateByMessageId(transactionMessage);
        logger.info("update message num :{}", n);
    }

    @Override
    public String saveAndSendMessage(TransactionMessage transactionMessage) throws ReliableMessageException {
        this.messageCheck(transactionMessage);
        String messageId = messageIdGenerator.generator();
        transactionMessage.setMessageId(messageId);
        transactionMessage.setStatus(MessageStatusEnum.SENDING.name());
        transactionMessage.setDead(false);
        transactionMessage.setMessageSendTimes(0);
        transactionMessage.setCreateTime(new Date());
        transactionMessage.setUpdateTime(new Date());
        messageRepository.saveMessage(transactionMessage);
        messageSender.send(transactionMessage);
        return messageId;
    }

    @Override
    public void sendMessage(TransactionMessage transactionMessage) throws ReliableMessageException {
        this.messageCheck(transactionMessage);
        messageSender.send(transactionMessage);
    }

    @Override
    public void reSendMessage(final TransactionMessage transactionMessage) throws ReliableMessageException {
        this.messageCheck(transactionMessage);

        transactionMessage.addSendTimes();
        transactionMessage.setUpdateTime(new Date());
        int n = messageRepository.updateByMessageId(transactionMessage);
        if (n == 0) {
            logger.info("reSend message not fount : {}", transactionMessage.toString());
        }

        messageSender.send(transactionMessage);

    }

    @Override
    public void reSendMessageByMessageId(String messageId) throws ReliableMessageException {
        this.messageIdCheck(messageId);
        final TransactionMessage transactionMessage = messageRepository.getByMessageId(messageId);
        if (transactionMessage == null) {
            throw new ReliableMessageException(ReliableMessageException.MESSAGE_NOT_FOUND);
        }

        transactionMessage.addSendTimes();
        transactionMessage.setUpdateTime(new Date());

        if (transactionMessage.getMessageSendTimes() >= maxTimes) {
            transactionMessage.setDead(true);
        }
        messageRepository.updateByMessageId(transactionMessage);
        messageSender.send(transactionMessage);

    }

    @Override
    public void setMessageToDead(String messageId) throws ReliableMessageException {
        this.messageIdCheck(messageId);
        final TransactionMessage transactionMessage = messageRepository.getByMessageId(messageId);
        if (transactionMessage == null) {
            throw new ReliableMessageException(ReliableMessageException.MESSAGE_NOT_FOUND);
        }

        transactionMessage.setUpdateTime(new Date());
        transactionMessage.setDead(true);
        messageRepository.updateByMessageId(transactionMessage);
    }

    @Override
    public TransactionMessage getMessageByMessageId(String messageId) throws ReliableMessageException {
        this.messageIdCheck(messageId);
        return messageRepository.getByMessageId(messageId);
    }

    @Override
    public void deleteMessageByMessageId(String messageId) throws ReliableMessageException {
        this.messageIdCheck(messageId);
        int n = messageRepository.deleteByMessageId(messageId);
        logger.info("delete message , message id :{} , num :{}", messageId, n);
    }

    @Override
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws ReliableMessageException {

        if (batchSize <= 0) {
            throw new ReliableMessageException(ReliableMessageException.ILLEGAL_BATH_SIZE);
        }

        if (!StringUtils.isEmpty(queueName)) {
            throw new ReliableMessageException(ReliableMessageException.QUEUE_NAME_IS_NULL);
        }

        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setConsumerQueue(queueName);

        List<TransactionMessage> messageList = messageRepository.selectMultiple(transactionMessage, 0, batchSize);

        if (messageList == null || messageList.size() == 0) {
            return;
        }

        for (TransactionMessage message : messageList) {
            message.addSendTimes();
            message.setUpdateTime(new Date());

            if (message.getMessageSendTimes() >= maxTimes) {
                message.setDead(true);
            }
            messageRepository.updateByMessageId(message);
            messageSender.send(message);
        }

    }

    @SuppressWarnings("all")
    @Override
    public Page<TransactionMessage> page(TransactionMessage transactionMessage, int pageNum, int pageCount) throws ReliableMessageException {
        long totalCount = messageRepository.count(transactionMessage);
        int lastPageNum = (int) (totalCount % pageCount);
        int totalPageCount = (int) (totalCount / pageCount);
        if (pageNum > totalPageCount) {
            return null;
        }
        if (lastPageNum != 0) {
            totalPageCount += 1;
        }
        int skip = (pageNum - 1) * pageCount;

        List<TransactionMessage> messageList = messageRepository.selectMultiple(transactionMessage, skip, pageCount);

        return new Page<TransactionMessage>()
                .setPageNum(pageNum)
                .setPageCount(pageCount)
                .setTotalPageNum(totalPageCount)
                .setList(messageList);
    }


    private void messageCheck(TransactionMessage transactionMessage) throws ReliableMessageException {
        if (transactionMessage == null) {
            throw new ReliableMessageException(ReliableMessageException.MESSAGE_IS_NULL);
        }

        if (StringUtils.isEmpty(transactionMessage.getDomain())) {
            throw new ReliableMessageException(ReliableMessageException.DOMAIN_IS_NULL);
        }

        if (StringUtils.isEmpty(transactionMessage.getMessageBody())) {
            throw new ReliableMessageException(ReliableMessageException.BODY_IS_NULL);
        }

        if (StringUtils.isEmpty(transactionMessage.getConsumerQueue())) {
            throw new ReliableMessageException(ReliableMessageException.CONSUMER_QUEUE_IS_NULL);
        }
    }


    private void messageIdCheck(String messageId) throws ReliableMessageException {
        if (StringUtils.isEmpty(messageId)) {
            throw new ReliableMessageException(ReliableMessageException.MESSAGE_ID_IS_NULL);
        }
    }
}
