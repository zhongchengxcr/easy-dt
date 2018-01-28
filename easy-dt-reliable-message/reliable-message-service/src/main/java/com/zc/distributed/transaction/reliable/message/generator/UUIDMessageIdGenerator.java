package com.zc.distributed.transaction.reliable.message.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/28 下午12:47
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class UUIDMessageIdGenerator implements MessageIdGenerator {

    @Override
    public String generator() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}
