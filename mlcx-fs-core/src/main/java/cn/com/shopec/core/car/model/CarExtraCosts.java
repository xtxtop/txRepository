package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 日租车辆附加费用表 数据实体类
 */
public class CarExtraCosts extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 附加费用编号
	private String extraCostsNo;
	// 车型id
	private String carModelId;
	// 车型名称
	private String carModelName;
	// 品牌id
	private String carBrandId;
	// 品牌名称
	private String carBrandName;
	// 城市id（具体来自数据字典表）
	private String cityId;
	// 城市名称
	private String cityName;
	// 保险费用
	private Double insurance;
	// 预授权
	private Double preLicensing;
	// 异地还车费
	private Double remoteCost;
	// 超时服务费（每小时）
	private Double overtimeCharge;
	// 服务费（每单）
	private Double coverCharge;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人id
	private String operatorId;
	private Integer operatorType;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return extraCostsNo;
	}

	public String getExtraCostsNo() {
		return extraCostsNo;
	}

	public void setExtraCostsNo(String extraCostsNo) {
		this.extraCostsNo = extraCostsNo;
	}

	public String getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(String carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(String carBrandId) {
		this.carBrandId = carBrandId;
	}

	public String getCarBrandName() {
		return carBrandName;
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	public Double getPreLicensing() {
		return preLicensing;
	}

	public void setPreLicensing(Double preLicensing) {
		this.preLicensing = preLicensing;
	}

	public Double getRemoteCost() {
		return remoteCost;
	}

	public void setRemoteCost(Double remoteCost) {
		this.remoteCost = remoteCost;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "CarExtraCosts [" + "extraCostsNo = " + extraCostsNo + ", carModelId = " + carModelId
				+ ", carModelName = " + carModelName + ", carBrandId = " + carBrandId + ", carBrandName = "
				+ carBrandName + ", cityId = " + cityId + ", cityName = " + cityName + ", insurance = " + insurance
				+ ", preLicensing = " + preLicensing + ", remoteCost = " + remoteCost + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorId = " + operatorId + ", operatorType = " + operatorType + "]";
	}

	public Double getOvertimeCharge() {
		return overtimeCharge;
	}

	public void setOvertimeCharge(Double overtimeCharge) {
		this.overtimeCharge = overtimeCharge;
	}

	public Double getCoverCharge() {
		return coverCharge;
	}

	public void setCoverCharge(Double coverCharge) {
		this.coverCharge = coverCharge;
	}
}
