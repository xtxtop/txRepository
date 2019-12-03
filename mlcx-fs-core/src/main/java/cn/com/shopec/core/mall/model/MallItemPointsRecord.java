package cn.com.shopec.core.mall.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 积分商城商品兑换记录
 * 
 * @author 许瑞伟
 *
 */
public class MallItemPointsRecord extends Entity<String> {

	private static final long serialVersionUID = 1L;

	// 记录编号
	private String recordNo;

	// 商品编号
	private String itemNo;

	// 商品名称
	private String itemName;

	// 商品名称
	private String picUrl;

	// 兑换积分
	private Integer points;

	// 会员编号
	private String memberNo;

	// 收货电话
	private String phone;

	// 收货地址
	private String address;

	// 签收状态(0:未签收，1:已签收)
	private Integer status;

	// 创建时间
	private Date createTime;

	// 兑换积分
	private Date signInTime;

	// 记账时间 时间范围起（查询用）
	private Date createTimeStart;

	// 记账时间 时间范围止（查询用）
	private Date createTimeEnd;

	@Override
	public String getPK() {
		return recordNo;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
