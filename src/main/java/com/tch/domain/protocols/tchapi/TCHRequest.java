package com.tch.domain.protocols.tchapi;

import java.util.List;

/**
 * Created by shz on 2017/10/23.
 * 前后端交互API类。
 * 前端请求后端
 */
public class TCHRequest<T> {
    List<Bus> bus;//请求类型
    List<Pages> pages;//分页
    T datas;//数据

    public TCHRequest() {
    }

    public List<Bus> getBus() {
        return bus;
    }

    public void setBus(List<Bus> bus) {
        this.bus = bus;
    }

    public List<Pages> getPages() {
        return pages;
    }

    public void setPages(List<Pages> pages) {
        this.pages = pages;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "TCHRequest{" +
                "bus=" + bus +
                ", pages=" + pages +
                ", datas=" + datas +
                '}';
    }
}




