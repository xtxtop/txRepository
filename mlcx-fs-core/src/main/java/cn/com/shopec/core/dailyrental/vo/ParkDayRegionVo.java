package cn.com.shopec.core.dailyrental.vo;


import java.util.List;

import cn.com.shopec.core.common.Entity;


/** 
 * ParkDay 数据实体类
 */
public class ParkDayRegionVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//区域名称
	private String addrRegion3Name;
	//该区域场站列表
	private List<ParkDayAroundVO> parkList;
	/*Customized properties start*/
	public String getAddrRegion3Name() {
		return addrRegion3Name;
	}
	public void setAddrRegion3Name(String addrRegion3Name) {
		this.addrRegion3Name = addrRegion3Name;
	}
	public List<ParkDayAroundVO> getParkList() {
		return parkList;
	}
	public void setParkList(List<ParkDayAroundVO> parkList) {
		this.parkList = parkList;
	}
	

	
	
}
