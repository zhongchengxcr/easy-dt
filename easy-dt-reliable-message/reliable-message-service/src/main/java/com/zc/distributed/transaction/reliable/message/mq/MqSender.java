package com.zc.distributed.transaction.reliable.message.mq;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:41
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface MqSender<T> {

    void send(T t);
}
