package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/** 
 * Member 数据实体类
 */
public class MemberStatusVO implements Serializable {

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 5635534247668991140L;
	
	private Integer censorStatus;//审核状态（认证状态）（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
	private Integer depositStatus;//押金状态(0:未交押金，1：已交押金)
	private String userIllegal; // 0 黑名单，1合法

	//押金支付编号
	private String depositOrderNo;
	//押金金额
	private Double depositAmount;
	
	public Integer getCensorStatus() {
		return censorStatus;
	}
	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}
	public Integer getDepositStatus() {
		return depositStatus;
	}
	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDepositOrderNo() {
		return depositOrderNo;
	}
	public void setDepositOrderNo(String depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}
	public Double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	public String getUserIllegal() {
		return userIllegal;
	}
	public void setUserIllegal(String userIllegal) {
		this.userIllegal = userIllegal;
	}
	@Override
	public String toString() {
		return "MemberStatusVO [censorStatus=" + censorStatus + ", depositStatus=" + depositStatus 
				+ ", depositOrderNo=" + depositOrderNo + ", depositAmount=" + depositAmount 
				+ "]";
	}
	
}
