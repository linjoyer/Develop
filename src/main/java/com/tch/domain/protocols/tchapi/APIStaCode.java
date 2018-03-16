package com.tch.domain.protocols.tchapi;

/**
 * Created by shz on 2017/10/23.
 * 返回状态码
 */
public enum APIStaCode {
    SUCCESS("0","成功")
    ,ERROR("-1","系统错误")
    ,SYSTEMERROR("-99","未知错误，请联系管理员")
    ;

    APIStaCode(String cod, String des) {
        this.cod = cod;
        this.des = des;
    }

    private String cod;
    private String des;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
