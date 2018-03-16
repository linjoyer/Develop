package com.tch.domain.protocols.tchapi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shz on 2017/10/23.
 * 返回数据格式API
 * 不要问我为什么返回的是List，我也不知道。
 */
public class TCHResult<T> {
    List<Sta> sta;
    T ext;
    List<Pages> pages;
    List<Fields> fields;
    T datas;

    public TCHResult() {}

    public TCHResult(Sta sta, T ext, Pages pages, List<Fields> fields, T datas) {
        //处理成List
        List<Sta> tempsta=new ArrayList<Sta>();
        tempsta.add(sta);
        this.sta = tempsta;

        //处理成List
        List<Pages> temppages=new ArrayList<Pages>();
        temppages.add(pages);
        this.pages = temppages;
        this.ext=ext;
        this.fields = fields;
        this.datas = datas;
    }

    public List<Sta> getSta() {
        return sta;
    }

    public void setSta(List<Sta> sta) {
        this.sta = sta;
    }

    public T getExt() {
        return ext;
    }

    public void setExt(T ext) {
        this.ext = ext;
    }

    public List<Pages> getPages() {
        return pages;
    }

    public void setPages(List<Pages> pages) {
        this.pages = pages;
    }

    public List<Fields> getFields() {
        return fields;
    }

    public void setFields(List<Fields> fields) {
        this.fields = fields;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "TCHResult{" +
                "sta=" + sta +
                ", ext=" + ext +
                ", pages=" + pages +
                ", fields=" + fields +
                ", datas=" + datas +
                '}';
    }
}
