package com.tch.domain.protocols;

/**
 * Created by hugolew on 2016/4/7.
 */
public class APIResult<T> {
    private APIResultCode code;
    private String msg;
    private T data;

    public APIResult() {
    }
    public APIResult(APIResultCode apiResultCode){
        this.code=apiResultCode;
        this.msg=apiResultCode.getMsg();

    }
    public APIResult(APIResultCode code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public APIResultCode getCode() {
        return code;
    }

    public void setCode(APIResultCode code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "APIResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
