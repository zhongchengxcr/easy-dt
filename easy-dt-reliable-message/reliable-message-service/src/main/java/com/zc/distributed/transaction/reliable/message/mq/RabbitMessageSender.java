package com.zc.distributed.transaction.reliable.message.mq;

import com.zc.distributed.transaction.reliable.message.dto.TransactionMessage;
import org.springframework.stereotype.Component;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/28 下午1:43
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class RabbitMessageSender extends TransactionMessageSender {
    @Override
    public void send(TransactionMessage s) {

    }
}
