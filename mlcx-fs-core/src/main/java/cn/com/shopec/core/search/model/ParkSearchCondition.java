package cn.com.shopec.core.search.model;

import java.io.Serializable;

/**
 * 场站搜索条件
 *
 */
public class ParkSearchCondition implements Serializable {
	
	private static final long serialVersionUID = 1224030798197927763L;

	private String parkNo; //场站编号

	private Double posLongitude; //位置经度
	
	private Double posLatitude; //位置纬度
	
	private Double radius; //半径（单位为km）
	
	private boolean isDescOrder; //是否逆序排序
	
	//片区id
	private String id;

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

	public Double getPosLongitude() {
		return posLongitude;
	}

	public void setPosLongitude(Double posLongitude) {
		this.posLongitude = posLongitude;
	}

	public Double getPosLatitude() {
		return posLatitude;
	}

	public void setPosLatitude(Double posLatitude) {
		this.posLatitude = posLatitude;
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

	@Override
	public String toString() {
		return "ParkSearchCondition [parkNo=" + parkNo + ", posLongitude=" + posLongitude + ", posLatitude="
				+ posLatitude + ", radius=" + radius + ", isDescOrder=" + isDescOrder + "]";
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
