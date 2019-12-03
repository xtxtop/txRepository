package cn.com.shopec.core.mall.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 积分商城商品分类
 * 
 * @author 许瑞伟
 *
 */
public class MallItemSort extends Entity<String> {

	private static final long serialVersionUID = 1L;

	// 分类编号
	private String sortNo;

	// 父级分类编号
	private String parentSortNo;

	// 父级分类名称
	private String parentSortName;

	// 分类名称
	private String sortName;

	// 分类级别
	private Integer sortLevel;

	// 兑换积分
	private Date createTime;

	// 记账时间 时间范围起（查询用）
	private Date createTimeStart;

	// 记账时间 时间范围止（查询用）
	private Date createTimeEnd;

	@Override
	public String getPK() {
		return sortNo;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getParentSortNo() {
		return parentSortNo;
	}

	public void setParentSortNo(String parentSortNo) {
		this.parentSortNo = parentSortNo;
	}

	public String getParentSortName() {
		return parentSortName;
	}

	public void setParentSortName(String parentSortName) {
		this.parentSortName = parentSortName;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Integer getSortLevel() {
		return sortLevel;
	}

	public void setSortLevel(Integer sortLevel) {
		this.sortLevel = sortLevel;
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
