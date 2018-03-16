package com.tch.domain.entity.monitor;

import java.io.Serializable;

/**
 * 
* <p>Title:ProcessState </p>
* <p>Description: 进程状态</p>
* @author shz
* @create 2018-03-12
 */
public class ProcessState implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2913111613773445949L;

	/**
	 * %CPU
	 */
    private String cpuPer;

    /**
	 * %MEM
	 */
    private String memPer;

	public String getCpuPer() {
		return cpuPer;
	}

	public void setCpuPer(String cpuPer) {
		this.cpuPer = cpuPer;
	}


	public String getMemPer() {
		return memPer;
	}

	public void setMemPer(String memPer) {
		this.memPer = memPer;
	}

   
}
