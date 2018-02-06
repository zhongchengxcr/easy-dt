package com.zc.distributed.transaction.reliable.message.controller;

import com.zc.distributed.transaction.reliable.message.dto.Page;
import com.zc.distributed.transaction.reliable.message.dto.Result;
import com.zc.distributed.transaction.reliable.message.dto.TransactionMessage;
import com.zc.distributed.transaction.reliable.message.service.ReliableMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:18
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.ne
 * @version 1.0.0
 */
@RestController()
@RequestMapping("/dt/reliable/message")
public class ReliableMessageController {

    @Resource
    private ReliableMessageService reliableMessageService;

    @PutMapping("/")
    public Result<String> preSaveMessage(@RequestBody TransactionMessage transactionMessage) {
        String messageId = reliableMessageService.preSaveMessage(transactionMessage);
        return Result.success(messageId);
    }

    @PostMapping("/confirm/{messageId}")
    public Result<String> confirmAndSendMessage(@PathVariable String messageId) {
        reliableMessageService.confirmAndSendMessage(messageId);
        return Result.success(null);
    }

    @PostMapping("/confirm")
    public Result<String> saveAndSendMessage(@RequestBody TransactionMessage transactionMessage) {
        String messageId = reliableMessageService.saveAndSendMessage(transactionMessage);
        return Result.success(messageId);
    }


    @PostMapping("/send")
    public Result<String> sendMessage(@RequestBody TransactionMessage transactionMessage) {
        reliableMessageService.sendMessage(transactionMessage);
        return Result.success(null);
    }


    @PostMapping("/rsend")
    public Result<String> reSendMessage(@RequestBody TransactionMessage transactionMessage) {
        reliableMessageService.reSendMessage(transactionMessage);
        return Result.success(null);
    }


    @PostMapping("/rsend/{messageId}")
    public Result<String> reSendMessageByMessageId(@PathVariable String messageId) {
        reliableMessageService.reSendMessageByMessageId(messageId);
        return Result.success(null);
    }

    @PostMapping("/dead/{messageId}")
    public Result<String> setMessageToDead(@PathVariable String messageId) {
        reliableMessageService.setMessageToDead(messageId);
        return Result.success(null);
    }


    @GetMapping("/{messageId}")
    public Result<TransactionMessage> getMessageByMessageId(@PathVariable String messageId) {
        TransactionMessage transactionMessage = reliableMessageService.getMessageByMessageId(messageId);
        return Result.success(transactionMessage);
    }


    @DeleteMapping("/{messageId}")
    public Result<String> deleteMessageByMessageId(@PathVariable String messageId) {
        reliableMessageService.deleteMessageByMessageId(messageId);
        return Result.success(null);
    }


    @PostMapping("/rsend/all/{queueName}/{batchSize}")
    public Result<String> reSendAllDeadMessageByQueueName(@PathVariable String queueName, @PathVariable int batchSize) {
        reliableMessageService.reSendAllDeadMessageByQueueName(queueName, batchSize);
        return Result.success(null);
    }


    @PostMapping("/page/{pageNum}/{pageCount}")
    public Result<Page<TransactionMessage>> reSendAllDeadMessageByQueueName(@RequestBody TransactionMessage transactionMessage, @PathVariable int pageNum, @PathVariable int pageCount) {
        Page<TransactionMessage> page = reliableMessageService.page(transactionMessage, pageNum, pageCount);
        return Result.success(page);
    }


}
