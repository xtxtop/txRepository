package cn.com.shopec.core.dailyrental.search.model;

import java.io.Serializable;

/**
 * 搜索条件
 *
 */
public class SearchCondition implements Serializable {

	private static final long serialVersionUID = -9133208235135170139L;

	private String dataNo; // 编号

	private Double longitude; // 位置经度

	private Double latitude; // 位置纬度

	private Double radius; // 半径（单位为km）

	private boolean isDescOrder; // 是否逆序排序

	private Integer dataType;// 类型（1.场站 2车辆 3.区域）
	
	private String cityId;//城市id
	
	private String merchantId;//商家id

	private Integer isAvailable;// 场站启停，车辆上下线状态（0、停用，下线，1、启用，上线）

	private Integer carStatus;// 车辆使用状态（0，空闲、1，已预占、2，订单中、3，调度中）

	private Double power;// 车辆电量

	private Integer isInPark; // 车辆是否在场站内(0 否 1 是)

	private Integer isView;// 场站是否可见（0 显示 1 隐藏）

	private Integer dataName;// 场站名称、车牌号
	
	private Integer starRow;//开始行
	
	private Integer rowNum;//返回多少行
	
	public String getDataNo() {
		return dataNo;
	}

	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public boolean isDescOrder() {
		return isDescOrder;
	}

	public void setDescOrder(boolean isDescOrder) {
		this.isDescOrder = isDescOrder;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Integer getIsInPark() {
		return isInPark;
	}

	public void setIsInPark(Integer isInPark) {
		this.isInPark = isInPark;
	}

	public Integer getDataName() {
		return dataName;
	}

	public void setDataName(Integer dataName) {
		this.dataName = dataName;
	}

	public Integer getStarRow() {
		return starRow;
	}

	public void setStarRow(Integer starRow) {
		this.starRow = starRow;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}
