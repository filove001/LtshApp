package com.ltsh.app.chat.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * Created by Random on 2017/10/24.
 */

public class BaseEntity implements Serializable{
    /**
     * 主键
     */
    private Integer id;
    /**
     * 创建用户id
     */
    private Integer createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改用户
     */
    private Integer updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 所属人
     */
    private Integer belongsTo;

    public Integer getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Integer belongsTo) {
        this.belongsTo = belongsTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
