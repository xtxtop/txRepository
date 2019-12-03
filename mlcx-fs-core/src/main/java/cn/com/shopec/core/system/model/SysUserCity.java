package cn.com.shopec.core.system.model;

import cn.com.shopec.core.common.Entity;



/**
 * SysRole 数据库实体类
 */
public class SysUserCity extends Entity<String> {
	
	private static final long serialVersionUID = -6243261007074744542L;
	//用户id
	private String userId;
	
	//城市id
	private String cityId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "SysUserCity [getUserId()=" + getUserId() + ", getCityId()=" + getCityId() + ", getPK()=" + getPK()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
