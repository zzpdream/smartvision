package com.mws.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - base
 *
 * @author xingkong1221
 * @date 2015-11-16
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8532794912326373561L;

    /** 编号 */
    private Integer id;

    /** 开始时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /**
     * 获取编号
     *
     * @return 编号
     */
    @Id
    @GeneratedValue
    @JsonView(BaseView.class)
    public Integer getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, columnDefinition = "datetime DEFAULT NULL COMMENT '创建时间'")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '更新时间'")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
