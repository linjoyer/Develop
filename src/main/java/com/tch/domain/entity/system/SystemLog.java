package com.tch.domain.entity.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by shz on 2017/10/25.
 */
@Entity
public class SystemLog implements Serializable{
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue
    private Long ID;
    private Long userid;//管理员id
    private String username;//管理员姓名
    private Date createdate;//日期
    private String content;//日志内容
    private String operation;//操作(主要是"添加"、"修改"、"删除")

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
