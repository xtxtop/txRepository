package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 车辆上下线统计 数据实体类
 */
public class CarOnlineCount extends Entity<String> {

	private static final long serialVersionUID = 1l;

	// 车辆上下线统计主键
	private String carOnlineCountNo;
	// 上线数量
	private Integer onlineNum;
	// 下线数量
	private Integer offlineNum;
	// 创建时间
	private Date createTime;

	public String getCarOnlineCountNo() {
		return carOnlineCountNo;
	}

	public void setCarOnlineCountNo(String carOnlineCountNo) {
		this.carOnlineCountNo = carOnlineCountNo;
	}

	public Integer getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(Integer onlineNum) {
		this.onlineNum = onlineNum;
	}

	public Integer getOfflineNum() {
		return offlineNum;
	}

	public void setOfflineNum(Integer offlineNum) {
		this.offlineNum = offlineNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
