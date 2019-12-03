package cn.com.shopec.core.resource.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.common.Entity;

/** 
 * ParkSearch 数据实体类
 */
public class ParkSearch extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	
	//场站名称
	private String parkName;
	//场站类型（1、管理类，2、使用类，默认1）
	private Integer parkType;
	//坐标经度
	private String longitude;
	//坐标纬度
	private String latitude;
	//会员编号
	private String memberNo;
	//点击是否付费（0 不点击  1 点击）
	private Integer isPayment;
	//点击是否限制网店（0 不点击 1 点击）
	private Integer limit;
	//集团iD
	private String companyId;
	public Integer getParkType() {
		return parkType;
	}
	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Integer getIsPayment() {
		return isPayment;
	}
	public void setIsPayment(Integer isPayment) {
		this.isPayment = isPayment;
	}
	
	
	@Override
	public String toString() {
		return "ParkSearch [parkName=" + parkName + ", parkType=" + parkType + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", memberNo=" + memberNo + ", isPayment=" + isPayment + ", limit=" + limit
				+ ", companyId=" + companyId + "]";
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	
	
	
}
