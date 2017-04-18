package com.mws.web.common.vo;

import java.io.Serializable;

/**
 * Vo - 分页
 *
 * @author xingkong1221
 * @date 2015-10-09
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = -5972983880610450831L;

    /** 默认每页记录数 */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /** 每页记录数 */
    private Integer pageSize;

    /** 页码 */
    private Integer page = 1;

    /**
     * 实例化分页对象
     */
    public Pagination() {
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 实例化分页对象
     *
     * @param pageSize 每页记录数
     */
    public Pagination(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize 每页记录数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取页码
     *
     * @return 页码
     */
    public Integer getPage() {
        if (page < 1) {
            return 1;
        }
        return page;
    }

    /**
     * 设置页码
     *
     * @param page 页码
     */
    public void setPage(Integer page) {
        this.page = page;
    }
}
