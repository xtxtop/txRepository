package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.Date;

public class CarIllegalVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 4749250697558548842L;

	//违章ID
	private String illegalId;
	//违章时间
	private String illegalTime;
	//违章地点
	private String illegalLocation;
	//违章类型 (1、未系安全带2、压禁止标线 3、违停4、闯红灯5、不服从指挥6、超速行驶7、未设警告标志8、未停车让行9、未保持车距10、未按道行驶)
	private String illegalType;
	//罚款金额
	private Double illegalFines;
	//处理状态（0、未处理，1、处理中，2、已处理，默认0）
	private String processingStatus;
	//处理机构
	private String processingAgency;
	//违章内容
	private String illegalDetail;
	//违章扣分
	private Integer pointsDeduction;
	//品牌
	private String carBrandName;
	//车型
	private String carModelName;
	// 车牌号
	private String carPlateNo;
	
	public String getIllegalId() {
		return illegalId;
	}
	public void setIllegalId(String illegalId) {
		this.illegalId = illegalId;
	}
	public String getIllegalTime() {
		return illegalTime;
	}
	public void setIllegalTime(String illegalTime) {
		this.illegalTime = illegalTime;
	}
	public String getIllegalLocation() {
		return illegalLocation;
	}
	public void setIllegalLocation(String illegalLocation) {
		this.illegalLocation = illegalLocation;
	}
	public String getIllegalType() {
		return illegalType;
	}
	public void setIllegalType(String illegalType) {
		this.illegalType = illegalType;
	}
	public Double getIllegalFines() {
		return illegalFines;
	}
	public void setIllegalFines(Double illegalFines) {
		this.illegalFines = illegalFines;
	}
	public String getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	public String getProcessingAgency() {
		return processingAgency;
	}
	public void setProcessingAgency(String processingAgency) {
		this.processingAgency = processingAgency;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CarIllegalVo [illegalId=" + illegalId + ", illegalTime=" + illegalTime + ", illegalLocation="
				+ illegalLocation + ", illegalType=" + illegalType + ", illegalFines=" + illegalFines
				+ ", processingStatus=" + processingStatus + ", processingAgency=" + processingAgency + "]";
	}
	public String getIllegalDetail() {
		return illegalDetail;
	}
	public void setIllegalDetail(String illegalDetail) {
		this.illegalDetail = illegalDetail;
	}
	public Integer getPointsDeduction() {
		return pointsDeduction;
	}
	public void setPointsDeduction(Integer pointsDeduction) {
		this.pointsDeduction = pointsDeduction;
	}
	public String getCarBrandName() {
		return carBrandName;
	}
	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	
	
}
