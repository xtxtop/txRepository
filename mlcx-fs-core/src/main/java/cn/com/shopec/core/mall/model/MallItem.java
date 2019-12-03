package cn.com.shopec.core.mall.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 积分商城商品
 * 
 * @author 许瑞伟
 *
 */
public class MallItem extends Entity<String> {

	private static final long serialVersionUID = 1L;

	// 商品编号
	private String itemNo;

	// 所属分类
	private String sortNo;

	// 分类名称
	private String sortName;

	// 商品名称
	private String itemName;

	// 商品名称
	private String picUrl;

	// 描述
	private String content;

	// 兑换积分
	private Integer points;

	// 兑换积分
	private Integer num;

	// 上下架状态(0:上架，1:下架)
	private Integer status;

	// 兑换积分
	private Date createTime;

	// 记账时间 时间范围起（查询用）
	private Date createTimeStart;

	// 记账时间 时间范围止（查询用）
	private Date createTimeEnd;

	@Override
	public String getPK() {
		return itemNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

}
