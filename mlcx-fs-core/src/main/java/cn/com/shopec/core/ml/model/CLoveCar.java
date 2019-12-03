package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * CLoveCar 数据实体类
 */
public class CLoveCar extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 爱车主键
	private String loveCarNo;
	// 会员编号
	private String memberNo;
	// 车辆品牌
	private String vehicleBrand;
	// 车辆型号
	private String vehicleModel;
	// 行驶证
	private String drivingLicense;
	// 行驶证副本
	private String drivingLicenseCopy;
	// 审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private Integer censorStatus;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 修改时间
	private Date updateTime;
	// 修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 修改时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人id
	private String operatorId;
	// 操作人类型
	private Integer operatorType;
	//审核备注
	private String censorMemo;

	public String getCensorMemo() {
		return censorMemo;
	}

	public void setCensorMemo(String censorMemo) {
		this.censorMemo = censorMemo;
	}
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return loveCarNo;
	}

	public String getLoveCarNo() {
		return loveCarNo;
	}

	public void setLoveCarNo(String loveCarNo) {
		this.loveCarNo = loveCarNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getDrivingLicenseCopy() {
		return drivingLicenseCopy;
	}

	public void setDrivingLicenseCopy(String drivingLicenseCopy) {
		this.drivingLicenseCopy = drivingLicenseCopy;
	}

	public Integer getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
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
		return "CLoveCar [" + "loveCarNo = " + loveCarNo + ", memberNo = " + memberNo + ", vehicleBrand = "
				+ vehicleBrand + ", vehicleModel = " + vehicleModel + ", drivingLicense = " + drivingLicense
				+ ", drivingLicenseCopy = " + drivingLicenseCopy + ", censorStatus = " + censorStatus
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = "
				+ operatorType + "]";
	}

}
