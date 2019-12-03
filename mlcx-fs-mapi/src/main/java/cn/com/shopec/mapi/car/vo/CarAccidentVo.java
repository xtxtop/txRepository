package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.Date;

public class CarAccidentVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8323913763972743638L;

	//事故id
	private String accidentId;
	//记录事故时间
	private String recordAccidentTime;
	//事故地点
	private String accidentLocation;
	//事故等级（1、一般事故，2、轻微事故，3、重大事故，4、特大事故）
	private String accidentLevel;
	//保险公司
	private String insuranceCompany;
	//保险进度（0、未处理，1、处理中，2、已处理，默认0）
	private String accidentStatus;
	public String getAccidentId() {
		return accidentId;
	}
	public void setAccidentId(String accidentId) {
		this.accidentId = accidentId;
	}
	
	public String getRecordAccidentTime() {
		return recordAccidentTime;
	}
	public void setRecordAccidentTime(String recordAccidentTime) {
		this.recordAccidentTime = recordAccidentTime;
	}
	public String getAccidentLocation() {
		return accidentLocation;
	}
	public void setAccidentLocation(String accidentLocation) {
		this.accidentLocation = accidentLocation;
	}
	
	public String getAccidentLevel() {
		return accidentLevel;
	}
	public void setAccidentLevel(String accidentLevel) {
		this.accidentLevel = accidentLevel;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	
	public String getAccidentStatus() {
		return accidentStatus;
	}
	public void setAccidentStatus(String accidentStatus) {
		this.accidentStatus = accidentStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CarAccidentVo [accidentId=" + accidentId + ", recordAccidentTime=" + recordAccidentTime
				+ ", accidentLocation=" + accidentLocation + ", accidentLevel=" + accidentLevel + ", insuranceCompany="
				+ insuranceCompany + ", accidentStatus=" + accidentStatus + "]";
	}
	
	
		
}
