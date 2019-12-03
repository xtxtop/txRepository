package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 兑换码记录表 数据实体类
 */
public class RedeemRecord extends Entity<String> {

	private static final long serialVersionUID = 311191967191931856L;
	
	private String id;
	private String redCode;
	private String memberNo;
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRedCode() {
		return redCode;
	}
	public void setRedCode(String redCode) {
		this.redCode = redCode;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "RedeemRecord [id=" + id + ", redCode=" + redCode + ", memberNo=" + memberNo + ", createTime="
				+ createTime + "]";
	}
	
}
