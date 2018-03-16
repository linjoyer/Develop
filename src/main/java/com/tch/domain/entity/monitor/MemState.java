package com.tch.domain.entity.monitor;

import java.io.Serializable;

/**
 * 
* <p>Title:MemState </p>
* <p>Description: 查看内存使用情况</p>
* @author shz
* @create 2018-03-12
 */
public class MemState implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -1412473355088780549L;


	/**
	 * 总计内存，M
	 */
    private String total;

    /**
	 *已使用多少，M
	 */
    private String used;
    
    /**
	 * 未使用，M
	 */
    private String free;
    
    /**
     * 已使用百分比%
     */
    private String usePer;

    
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getUsePer() {
		return usePer;
	}

	public void setUsePer(String usePer) {
		this.usePer = usePer;
	}

	
   
}
