package cn.com.shopec.core.resource.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * Park 数据实体类
 */
public class ParkRegion extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//区域名称
	private String addrRegion3Name;
	//该区域场站数
	private Integer parkTakeNum;
	//该区域场站列表
	private List<ParkVOAround> parkList;
	/*Customized properties start*/
	public String getAddrRegion3Name() {
		return addrRegion3Name;
	}
	public void setAddrRegion3Name(String addrRegion3Name) {
		this.addrRegion3Name = addrRegion3Name;
	}
	public Integer getParkTakeNum() {
		return parkTakeNum;
	}
	public void setParkTakeNum(Integer parkTakeNum) {
		this.parkTakeNum = parkTakeNum;
	}
	public List<ParkVOAround> getParkList() {
		return parkList;
	}
	public void setParkList(List<ParkVOAround> parkList) {
		this.parkList = parkList;
	}
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	
	
}
