package cn.com.shopec.core.member.model;

import java.io.Serializable;

/**
 * 当日会员统计数据实体
 * @author admin
 *
 */
public class MemberOneDayVO implements Serializable{
	
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	private Integer newRegisterNum;		//新注册数
	private Integer newRealViewNum;		//新认证
	public Integer getNewRegisterNum() {
		return newRegisterNum;
	}
	public void setNewRegisterNum(Integer newRegisterNum) {
		this.newRegisterNum = newRegisterNum;
	}
	public Integer getNewRealViewNum() {
		return newRealViewNum;
	}
	public void setNewRealViewNum(Integer newRealViewNum) {
		this.newRealViewNum = newRealViewNum;
	}
	@Override
	public String toString() {
		return "MemberOneDayVO [newRegisterNum=" + newRegisterNum + ", newRealViewNum=" + newRealViewNum + "]";
	}
	
	

	
}
