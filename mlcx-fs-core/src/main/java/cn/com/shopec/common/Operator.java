package cn.com.shopec.common;

import java.io.Serializable;

/**
 * 通用操作人（泛指一切操作人，包括平台人员、商家人员、买家会员、系统自动操作等）对象
 *
 */
public class Operator implements Serializable {

	private static final long serialVersionUID = 7829098448116216661L;

	private Integer operatorType; //操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）

	private String operatorId; //操作人id（根据操作人类型会对应不同的表记录）

	private String operatorUserName; //操作人用户名（根据操作人类型，会对应不同的字段，一般对应登录名字段，如会员注册无登录名时，可能是邮箱或手机号等）
	
	private String operatorRealName; //操作人的真实名称（根据操作人类型，会对应不同的字段，某些情况下不一定有该值）
	
	private String operatorEmail; //操作人邮箱（某些情况下不一定有该值）
	
	private String operatorMobile; //操作人手机号（某些情况下不一定有该值）
	
	public Operator() {
		
	}
	
	public Operator(Integer operatorType, String operatorId, String operatorUserName, String operatorRealName,
			String operatorEmail, String operatorMobile) {
		super();
		this.operatorType = operatorType;
		this.operatorId = operatorId;
		this.operatorUserName = operatorUserName;
		this.operatorRealName = operatorRealName;
		this.operatorEmail = operatorEmail;
		this.operatorMobile = operatorMobile;
	}

	
	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorUserName() {
		return operatorUserName;
	}

	public void setOperatorUserName(String operatorUserName) {
		this.operatorUserName = operatorUserName;
	}

	public String getOperatorRealName() {
		return operatorRealName;
	}

	public void setOperatorRealName(String operatorRealName) {
		this.operatorRealName = operatorRealName;
	}

	public String getOperatorEmail() {
		return operatorEmail;
	}

	public void setOperatorEmail(String operatorEmail) {
		this.operatorEmail = operatorEmail;
	}

	public String getOperatorMobile() {
		return operatorMobile;
	}

	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}

	@Override
	public String toString() {
		return "Operator [operatorType=" + operatorType + ", operatorId=" + operatorId + ", operatorUserName="
				+ operatorUserName + ", operatorRealName=" + operatorRealName + ", operatorEmail=" + operatorEmail
				+ ", operatorMobile=" + operatorMobile + "]";
	}

	
}
