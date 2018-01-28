package com.zc.distributed.transaction.reliable.message.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:18
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class ReliableMessageException extends Exception {

    public static final String MESSAGE_IS_NULL = "2001";

    public static final String CONSUMER_QUEUE_IS_NULL = "2002";

    public static final String DOMAIN_IS_NULL = "2003";

    public static final String BODY_IS_NULL = "2004";

    public static final String MESSAGE_NOT_FOUND = "2005";

    public static final String MESSAGE_ID_IS_NULL = "2006";

    public static final String QUEUE_NAME_IS_NULL = "2007";

    public static final String ILLEGAL_BATH_SIZE = "2008";

    public static final String UNKNOWN_ERR = "9999";

    private static Map<String, String> ERR_MESSAGE_SOURCE = new HashMap<>();

    static {
        ERR_MESSAGE_SOURCE.put(MESSAGE_IS_NULL, "message is null");
        ERR_MESSAGE_SOURCE.put(CONSUMER_QUEUE_IS_NULL, "message consumer queue is empty");
        ERR_MESSAGE_SOURCE.put(DOMAIN_IS_NULL, "message domain is empty");
        ERR_MESSAGE_SOURCE.put(BODY_IS_NULL, "message body is empty");
        ERR_MESSAGE_SOURCE.put(MESSAGE_NOT_FOUND, "message not found");
        ERR_MESSAGE_SOURCE.put(UNKNOWN_ERR, "service exception");
        ERR_MESSAGE_SOURCE.put(MESSAGE_ID_IS_NULL, "message id is empty");
        ERR_MESSAGE_SOURCE.put(QUEUE_NAME_IS_NULL, "queue name is empty");
        ERR_MESSAGE_SOURCE.put(ILLEGAL_BATH_SIZE, "illegal bath size");
    }

    private String code;
    private String message;

    public ReliableMessageException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ReliableMessageException(String code) {
        super(ERR_MESSAGE_SOURCE.get(code));
        this.code = code;
        this.message = ERR_MESSAGE_SOURCE.get(code);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
