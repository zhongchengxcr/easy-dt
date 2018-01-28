package com.zc.distributed.transaction.reliable.message.repository;

import com.zc.distributed.transaction.reliable.message.dto.TransactionMessage;

import java.util.List;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:37
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface MessageRepository {


    TransactionMessage getByMessageId(String messageId);

    String saveMessage(TransactionMessage message);

    int updateByMessageId(TransactionMessage message);

    int deleteByMessageId(String message);

    List<TransactionMessage> selectMultiple(TransactionMessage message,int skip, int limit);

    long count(TransactionMessage message);

}
