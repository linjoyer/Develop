package com.tch.domain.protocols.tchapi;

/**
 * Created by shz on 2017/10/23.
 * 分页
 */
public class Pages {
    //请求页码,pageno
    String  pno;
    //每页大小,pagesize
    String  psize;
    //总大小,totalsize
    String  tsize;
    //响应当前页码数，pagenum
    String  pnum;

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize = psize;
    }

    public String getTsize() {
        return tsize;
    }

    public void setTsize(String tsize) {
        this.tsize = tsize;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    @Override
    public String toString() {
        return "Pages{" +
                "pno='" + pno + '\'' +
                ", psize='" + psize + '\'' +
                ", tsize='" + tsize + '\'' +
                ", pnum='" + pnum + '\'' +
                '}';
    }
}
