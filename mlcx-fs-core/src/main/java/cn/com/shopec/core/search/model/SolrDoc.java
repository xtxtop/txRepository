package cn.com.shopec.core.search.model;

import java.io.Serializable;
import java.util.Date;

/**
 * solr实体类文档
 *
 */
public class SolrDoc implements Serializable {

	private static final long serialVersionUID = 3215635435238663711L;

	private String docId; // 文档在solr中的id

	private String dataNo; // 编号

	private String dataName; // 名称

	private Integer dataType; // 类型（1 场站 2 车辆）

	private String carModelName;// 车型名称

	private Integer isAvailable;// 场站启停，车辆上下线状态（0、停用，下线，1、启用，上线）

	private Integer carStatus;// 车辆使用状态（0，空闲、1，已预占、2，订单中、3，调度中）

	private Integer isInPark; // 车辆是否在场站内
	
	//剩余车位
	private Integer parkingSpaces;

	private String companyId;// 集团id

	private Double power;// 车辆剩余电量

	private Integer isView; // 场站是否可见
	
	private Integer parkType;//场站类型（1、管理类，2、使用类，默认1）

	private String longitude; // 经度

	private String latitude; // 纬度

	private String address; // 地址

	private Integer elecFenceRadius; // 电子围栏半径（单位：米）

	private Double distance; // 距离

	private String parkLocation;

	private String parkGeo;

	private Date indexedTime;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDataNo() {
		return dataNo;
	}

	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getElecFenceRadius() {
		return elecFenceRadius;
	}

	public void setElecFenceRadius(Integer elecFenceRadius) {
		this.elecFenceRadius = elecFenceRadius;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
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

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public Integer getIsInPark() {
		return isInPark;
	}

	public void setIsInPark(Integer isInPark) {
		this.isInPark = isInPark;
	}

	public String getParkLocation() {
		return parkLocation;
	}

	public void setParkLocation(String parkLocation) {
		this.parkLocation = parkLocation;
	}

	public String getParkGeo() {
		return parkGeo;
	}

	public void setParkGeo(String parkGeo) {
		this.parkGeo = parkGeo;
	}

	public Date getIndexedTime() {
		return indexedTime;
	}

	public void setIndexedTime(Date indexedTime) {
		this.indexedTime = indexedTime;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getParkType() {
		return parkType;
	}

	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}

	public Integer getParkingSpaces() {
		return parkingSpaces;
	}

	public void setParkingSpaces(Integer parkingSpaces) {
		this.parkingSpaces = parkingSpaces;
	}
	
	
}
