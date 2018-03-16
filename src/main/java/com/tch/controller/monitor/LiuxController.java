package com.tch.controller.monitor;

import ch.ethz.ssh2.Connection;
import com.tch.common.utils.CtrCommond;
import com.tch.common.utils.HostUtils;
import com.tch.controller.base.BaseController;
import com.tch.domain.protocols.APIResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 监控linux服务器情况
 *
 * @author shz
 * @create 2018-03-12 10:30
 **/
@Controller
@RequestMapping("/linux")
public class LiuxController extends BaseController{
    @GetMapping("gotoLinux")
    public String gotoLinux(Model model){
        return "monitor/LinuxMsg";
    }
    @RequestMapping("/all")
    @ResponseBody
    public APIResult getAllmsg(){
        Connection conn = CtrCommond.getConn("192.168.16.233", "tchapp", 22, "apptch");
        StringBuilder stb=new StringBuilder();
        stb.append("</br>CPU型号信息："+ HostUtils.getCpuModel(conn));
        stb.append("</br>物理CPU个数："+HostUtils.getCpuNum(conn));
        stb.append("</br>每个CPU核数量："+HostUtils.getCpuPerCores(conn));
        stb.append("</br>系统运行天数："+HostUtils.getSystemDays(conn));
        stb.append("</br>系统版本信息："+HostUtils.getSystemRelease(conn));
        stb.append("</br>系统版本详细信息："+HostUtils.getSystemUname(conn));
        stb.append("</br>cpu Idle使用率："+HostUtils.getCpuState(conn).getIdle());
        stb.append("</br>磁盘已使用G："+HostUtils.getDfInfo(conn).getUsed());
        stb.append("</br>磁盘IO状态："+HostUtils.getDiskIoState(conn).getRkBS());
        stb.append("</br>内存已使用百分比："+HostUtils.getMemState(conn).getUsePer());
        stb.append("</br>网络吞吐率rxbyt："+HostUtils.getNetIoState(conn).getRxbyt());
        stb.append("</br>系统一分钟负载："+HostUtils.getSysLoadState(conn).getOneLoad());
        stb.append("</br>系统TCP active："+HostUtils.getTcpState(conn).getActive());
//        stb.append</br>("进程2696内存使用率："+HostUtils.getProcessState(conn, "2696").getMemPer());
        stb.append("</br>系统已加载内核模块："+HostUtils.getLsmodInfo(conn));
        stb.append("</br>系统密码文件修改时间："+HostUtils.getPasswdFileInfo(conn));
        stb.append("</br>系统rpc服务开放状态："+HostUtils.getRpcinfo(conn));
        stb.append("</br>当前系统任务计划："+HostUtils.getCrontab(conn));
        return success(stb);
    }
}
