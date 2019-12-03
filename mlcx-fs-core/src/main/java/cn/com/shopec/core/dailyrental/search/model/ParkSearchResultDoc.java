package cn.com.shopec.core.dailyrental.search.model;

import java.io.Serializable;

/**
 * 场站搜索结果文档
 *
 */
public class ParkSearchResultDoc implements Serializable {

	private static final long serialVersionUID = 3215635435238663711L;
	
	private String docId; //文档在solr中的id

	private String parkNo; //场站编号
	
	private String parkName; //场站名称
	
	private String parkLongitude; //场站经度
	
	private String parkLatitude; //场站纬度
	
	private String parkAddress; //场站地址
	
	private Integer elecFenceRadius; //电子围栏半径（单位：米）
	
	private Double parkDistance; //场站距离

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkLongitude() {
		return parkLongitude;
	}

	public void setParkLongitude(String parkLongitude) {
		this.parkLongitude = parkLongitude;
	}

	public String getParkLatitude() {
		return parkLatitude;
	}

	public void setParkLatitude(String parkLatitude) {
		this.parkLatitude = parkLatitude;
	}

	public String getParkAddress() {
		return parkAddress;
	}

	public void setParkAddress(String parkAddress) {
		this.parkAddress = parkAddress;
	}

	public Integer getElecFenceRadius() {
		return elecFenceRadius;
	}
	
	public void setElecFenceRadius(Integer elecFenceRadius) {
		this.elecFenceRadius = elecFenceRadius;
	}

	public Double getParkDistance() {
		return parkDistance;
	}

	public void setParkDistance(Double parkDistance) {
		this.parkDistance = parkDistance;
	}

	@Override
	public String toString() {
		return "ParkSearchResultDoc [docId=" + docId + ", parkNo=" + parkNo + ", parkName=" + parkName
				+ ", parkLongitude=" + parkLongitude + ", parkLatitude=" + parkLatitude + ", parkAddress=" + parkAddress
				+ ", elecFenceRadius=" + elecFenceRadius + ", parkDistance=" + parkDistance + "]";
	}
	

}
