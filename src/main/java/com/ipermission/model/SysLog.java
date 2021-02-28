package com.ipermission.model;

import java.util.Date;

public class SysLog {
    private Integer id;

    private Integer type;

    private Integer targetId;

    private String operator;

    private Date operaterTime;

    private String operaterIp;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(Date operaterTime) {
        this.operaterTime = operaterTime;
    }

    public String getOperaterIp() {
        return operaterIp;
    }

    public void setOperaterIp(String operaterIp) {
        this.operaterIp = operaterIp == null ? null : operaterIp.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}