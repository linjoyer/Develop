package com.tch.domain.protocols.tchapi;

/**
 * Created by shz on 2017/10/23.
 * 返回状态
 */
public class Sta {
    //状态编码
    APIStaCode  apiStaCode;
    String cod;
    //状态说明
    String  des;

    public Sta() {
    }

    public Sta(APIStaCode apiStaCode, String des) {
        this.apiStaCode = apiStaCode;
        this.cod=apiStaCode.getCod();
        this.des = des;
    }

    public Sta(APIStaCode cod) {
        this.cod = cod.getCod();
        this.des = cod.getDes();
    }
    public APIStaCode getApiStaCode() {
        return apiStaCode;
    }

    public void setApiStaCode(APIStaCode apiStaCode) {
        this.apiStaCode = apiStaCode;
    }

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

    @Override
    public String toString() {
        return "Sta{" +
                "apiStaCode=" + apiStaCode +
                ", cod='" + cod + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
