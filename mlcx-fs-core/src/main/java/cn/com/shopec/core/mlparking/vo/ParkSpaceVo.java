package cn.com.shopec.core.mlparking.vo;

import java.util.List;

/**
 * 停车场楼层模型
 * 
 * @author Administrator
 *
 */
public class ParkSpaceVo {
	// 某层的总车位数
	private String parkTotalAnyNumber;
	// 某层的空闲车位数
	private String parkSpaceAnyNumber;
	//楼层数
	private String spaceName;
	//地锁列表
	private List<Lock> lockList;

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}


	public List<Lock> getLockList() {
		return lockList;
	}

	public void setLockList(List<Lock> lockList) {
		this.lockList = lockList;
	}

	public String getParkTotalAnyNumber() {
		return parkTotalAnyNumber;
	}

	public void setParkTotalAnyNumber(String parkTotalAnyNumber) {
		this.parkTotalAnyNumber = parkTotalAnyNumber;
	}

	public String getParkSpaceAnyNumber() {
		return parkSpaceAnyNumber;
	}

	public void setParkSpaceAnyNumber(String parkSpaceAnyNumber) {
		this.parkSpaceAnyNumber = parkSpaceAnyNumber;
	}


}
