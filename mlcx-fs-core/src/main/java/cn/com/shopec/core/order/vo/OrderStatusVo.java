package cn.com.shopec.core.order.vo;

import java.io.Serializable;

import cn.com.shopec.core.marketing.model.CarRedPacketVo;
import cn.com.shopec.core.resource.model.Park;

public class OrderStatusVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	// 订单号
	private String orderNo;
	// 会员编号
	private String memberNo;
	// 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
	private Integer orderStatus;
	//是否打开过车门
	private Integer isOpenDoor;
	// 创建时间(订单下单时间)
	private String createTime;
	// 首次开门时间（取车时间）
	private String openCarDoorTime;
	/**预约后自动计费订单的时间*/
	private String strtChargingTime ;
	/**预约后自动取消订单的时间*/
	private String cancelOrderTime;
	//可续航里程
	private Double rangeMileage;
	//实时计费
	private Double nowAmount;
	//订单时长(分钟数)
	private Integer orderDuration;
	//订单车辆所在的坐标
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	//MAC地址
	private String macAddr;
	//终端序列号
	private String deviceSn;
	//终端设备的微信设备id
	private String wxDeviceId;
	// 车牌号
	private String carPlateNo;
	//deviceKey
	private String deviceKey;
	//系统当前时间（用于app端倒计时）
	private String nowTime;
	//座位数
	private String seaTing;
	//品牌
	private String carBrandName;
	//车型
	private String carModelName;
	//剩余电量百分比(0%~100%)
	private Double power; 
	//车辆图片
	private String carPhotoUrl1;
	//蓝牙名称
	private String blueToothName;
	//预约状态下车辆所属场站对象
	private ParkOrderVO parkOrderVo;
	//超额弹出框
	private Integer warningOrder;
	//是否是红包车
	private String isCarRedPakcet;
	private CarRedPacketVo carRedPacketVo;
	//终端设备名称(1  速锐得 2  领航)
	private Integer deviceType;
	
	
	public ParkOrderVO getParkOrderVo() {
		return parkOrderVo;
	}
	public void setParkOrderVo(ParkOrderVO parkOrderVo) {
		this.parkOrderVo = parkOrderVo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getIsOpenDoor() {
		return isOpenDoor;
	}
	public void setIsOpenDoor(Integer isOpenDoor) {
		this.isOpenDoor = isOpenDoor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOpenCarDoorTime() {
		return openCarDoorTime;
	}
	public void setOpenCarDoorTime(String openCarDoorTime) {
		this.openCarDoorTime = openCarDoorTime;
	}
	public String getStrtChargingTime() {
		return strtChargingTime;
	}
	public void setStrtChargingTime(String strtChargingTime) {
		this.strtChargingTime = strtChargingTime;
	}
	public String getCancelOrderTime() {
		return cancelOrderTime;
	}
	public void setCancelOrderTime(String cancelOrderTime) {
		this.cancelOrderTime = cancelOrderTime;
	}
	public Double getRangeMileage() {
		return rangeMileage;
	}
	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}
	public Double getNowAmount() {
		return nowAmount;
	}
	public void setNowAmount(Double nowAmount) {
		this.nowAmount = nowAmount;
	}
	public Integer getOrderDuration() {
		return orderDuration;
	}
	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	
	public String getWxDeviceId() {
		return wxDeviceId;
	}
	public void setWxDeviceId(String wxDeviceId) {
		this.wxDeviceId = wxDeviceId;
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	
	
	public String getDeviceKey() {
		return deviceKey;
	}
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	

	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	@Override
	public String toString() {
		return "OrderStatusVo [orderNo=" + orderNo + ", memberNo=" + memberNo + ", orderStatus=" + orderStatus
				+ ", isOpenDoor=" + isOpenDoor + ", createTime=" + createTime + ", openCarDoorTime=" + openCarDoorTime
				+ ", strtChargingTime=" + strtChargingTime + ", cancelOrderTime=" + cancelOrderTime + ", rangeMileage="
				+ rangeMileage + ", nowAmount=" + nowAmount + ", orderDuration=" + orderDuration + ", longitude="
				+ longitude + ", latitude=" + latitude + ", macAddr=" + macAddr + ", deviceSn=" + deviceSn + ", wxDeviceId=" + wxDeviceId
				+ ", carPlateNo=" + carPlateNo + ", deviceKey=" + deviceKey + ", nowTime=" + nowTime + ", seaTing="
				+ seaTing + ", carBrandName=" + carBrandName + ", carModelName=" + carModelName + ", power=" + power
				+ ", carPhotoUrl1=" + carPhotoUrl1 + ", parkOrderVo=" + parkOrderVo + "]";
	}
	public String getSeaTing() {
		return seaTing;
	}
	public void setSeaTing(String seaTing) {
		this.seaTing = seaTing;
	}
	public String getCarBrandName() {
		return carBrandName;
	}
	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}
	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
	}
	public String getBlueToothName() {
		return blueToothName;
	}
	public void setBlueToothName(String blueToothName) {
		this.blueToothName = blueToothName;
	}
	public Integer getWarningOrder() {
		return warningOrder;
	}
	public void setWarningOrder(Integer warningOrder) {
		this.warningOrder = warningOrder;
	}
	public String getIsCarRedPakcet() {
		return isCarRedPakcet;
	}
	public void setIsCarRedPakcet(String isCarRedPakcet) {
		this.isCarRedPakcet = isCarRedPakcet;
	}
	public CarRedPacketVo getCarRedPacketVo() {
		return carRedPacketVo;
	}
	public void setCarRedPacketVo(CarRedPacketVo carRedPacketVo) {
		this.carRedPacketVo = carRedPacketVo;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
}
