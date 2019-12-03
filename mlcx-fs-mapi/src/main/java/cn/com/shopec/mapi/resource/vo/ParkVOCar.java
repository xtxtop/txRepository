package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 场站与车辆信息VO对象
 * @author fly
 *
 */
public class ParkVOCar  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	//场站内车辆信息
	private List<ParkVOAround> ParkVOAround;
	//不在场站内车辆信息
	private List<ParkVOCarStatus> noParkCarStatus;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 

	public List<ParkVOAround> getParkVOAround() {
		return ParkVOAround;
	}


	public void setParkVOAround(List<ParkVOAround> parkVOAround) {
		ParkVOAround = parkVOAround;
	}


	public List<ParkVOCarStatus> getNoParkCarStatus() {
		return noParkCarStatus;
	}


	public void setNoParkCarStatus(List<ParkVOCarStatus> noParkCarStatus) {
		this.noParkCarStatus = noParkCarStatus;
	}


	@Override
	public String toString() {
		return "ParkVOAround [ParkVOAround=" + ParkVOAround + ", noParkCarStatus=" + noParkCarStatus + "]";
	}

	

	
	
	
	
}
