package cn.com.shopec.core.mlparking.vo;

/**
 * @author daiyuanbao
 * @category 多层分布实体
 *
 */
public class PliesNumberVo {
	private Integer num;//层数
	private Integer parkingSpaceNumbers;//总车位
	private Integer surplusSpaceNumbers;//剩余总车位
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getParkingSpaceNumbers() {
		return parkingSpaceNumbers;
	}
	public void setParkingSpaceNumbers(Integer parkingSpaceNumbers) {
		this.parkingSpaceNumbers = parkingSpaceNumbers;
	}
	public Integer getSurplusSpaceNumbers() {
		return surplusSpaceNumbers;
	}
	public void setSurplusSpaceNumbers(Integer surplusSpaceNumbers) {
		this.surplusSpaceNumbers = surplusSpaceNumbers;
	}
	@Override
	public String toString() {
		return "PliesNumberVo [num=" + num + ", parkingSpaceNumbers="
				+ parkingSpaceNumbers + ", surplusSpaceNumbers="
				+ surplusSpaceNumbers + "]";
	}
	
}
