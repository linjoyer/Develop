package com.tch.domain.protocols.tchapi;

/**
 * Created by shz on 2017/10/23.
 * 输入请求子类
 */
public class Bus {
    //操作类型
    String  act;
    //操作类型说明
    String  des;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "act='" + act + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
