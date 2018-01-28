package com.zc.distributed.transaction.reliable.message.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zc.distributed.transaction.reliable.message.dto.TransactionMessage;
import com.zc.distributed.transaction.reliable.message.repository.MongoMessageRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:11
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@RestController
public class ReliableMessageController {

    @Resource
    private MongoMessageRepository mongoMessageRepository;

    @GetMapping("/message/{id}")
    public TransactionMessage get(@PathVariable("id") String messageId) {
        return mongoMessageRepository.getByMessageId(messageId);
    }

    @PostMapping("/message")
    public String set(@RequestBody TransactionMessage transactionMessage) {
        return mongoMessageRepository.saveMessage(transactionMessage);
    }
}
