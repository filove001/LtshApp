package com.ltsh.app.chat.entity.req;

/**
 * Created by Random on 2018/1/31.
 */

public class PageReq extends BaseReq {
    /**
     * 当前页
     */
    private Integer pageNumber;
    /**
     * 显示条数
     */
    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
