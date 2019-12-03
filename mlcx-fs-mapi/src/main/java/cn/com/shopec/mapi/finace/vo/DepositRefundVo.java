package cn.com.shopec.mapi.finace.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 押金退款表 数据实体类
 */
public class DepositRefundVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//退款编号
	private String refundNo;
	//押金支付编号
	private String depositOrderNo;
	//退款金额
	private Double refundAmount;
	//客户编号
	private String memberNo;
	//客户名称
	private String memberName;
	//客户手机号
	private String mobilePhone;
	//退款状态（0、未退款，1、已退款，默认0）
	private Integer refundStatus;
	//申请时间
	private Date applyTime;
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getDepositOrderNo() {
		return depositOrderNo;
	}
	public void setDepositOrderNo(String depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	@Override
	public String toString() {
		return "DepositRefundVo [refundNo=" + refundNo + ", depositOrderNo=" + depositOrderNo + ", refundAmount="
				+ refundAmount + ", memberNo=" + memberNo + ", memberName=" + memberName + ", mobilePhone="
				+ mobilePhone + ", refundStatus=" + refundStatus + ", applyTime=" + applyTime + "]";
	}
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	

}
