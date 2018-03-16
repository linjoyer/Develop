package com.tch.domain.protocols.tchapi;

/**
 * Created by shz on 2017/10/24.
 * 数据域
 */
public class Fields {
    //name.字段名
    String n;
    //type，类型
    String t;
    //view，是否显示
    String v;
    //caption，中文注释
    String c;

    public Fields() {
    }

    public Fields(String n, String t, String v, String c) {
        this.n = n;
        this.t = t;
        this.v = v;
        this.c = c;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "n='" + n + '\'' +
                ", t='" + t + '\'' +
                ", v='" + v + '\'' +
                ", c='" + c + '\'' +
                '}';
    }
}
