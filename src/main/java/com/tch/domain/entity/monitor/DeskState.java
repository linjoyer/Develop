package com.tch.domain.entity.monitor;

import java.io.Serializable;

/**
 * 
* <p>Title:DeskState </p>
* <p>Description: 查看磁盘大小使用信息</p>
* @author shz
* @create 2018-03-12
 */
public class DeskState implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 879979812204191283L;

    /**
	 *分区大小
	 */
    private String size;
    
    /**
	 * 已使用
	 */
    private String used;
    
    /**
   	 * 已使用百分比
   	 */
    private String usePer;
    

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}


	public String getUsePer() {
		return usePer;
	}

	public void setUsePer(String usePer) {
		this.usePer = usePer;
	}

   
}
