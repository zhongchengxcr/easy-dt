package com.zc.distributed.transaction.reliable.message.dto;

import java.util.List;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/28 下午3:44
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class Page<T> {
    private int pageNum;
    private int pageCount;
    private int totalPageNum;
    private List<T> list;

    public int getPageNum() {
        return pageNum;
    }

    public Page setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Page setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public Page setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public Page setList(List<T> list) {
        this.list = list;
        return this;
    }
}
