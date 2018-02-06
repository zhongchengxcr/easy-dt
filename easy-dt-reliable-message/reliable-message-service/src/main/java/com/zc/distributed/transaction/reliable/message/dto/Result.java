package com.zc.distributed.transaction.reliable.message.dto;

import com.zc.distributed.transaction.reliable.message.exception.ReliableMessageException;

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
public class Result<T> {

    private static final String SUCCESS_CODE = "0";
    private static final String SUCCESS = "SUCCESS";

    private String code = SUCCESS_CODE;
    private String msg = SUCCESS;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(ReliableMessageException err) {
        return new Result<>(err.getCode(), err.getMessage());
    }

    public static <T> Result<T> error(String code, String message) {
        return new Result<>(code, message);
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
