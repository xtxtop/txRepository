package cn.com.shopec.mapi.park.controller.vo;

import java.text.SimpleDateFormat;

import cn.com.shopec.core.mlparking.model.CParkOrder;

/**
 * @author 代元宝
 * @category 订单列表
 *
 */
public class ParkLockOrder {
	private String parkOrderNo;//订单编号
	private Integer orderStatus;//订单状态(0进行中，1待支付，2待评价，3已完成, 10预约中)
	private String orderTime;//开始时间  
	//停车场编号
	private String parkingNo;
	//停车场名称
	private String parkingName;
	//会员编号
	private String memberNo;
	//停车场类型(0.闸机 1.地锁,2无设备)
	private Integer parkType;
	//车位号
	private String spaceNo;

	public ParkLockOrder(CParkOrder po){
		this.parkOrderNo=po.getParkOrderNo();
		if(po.getAppointmentTime()!=null&&!"".equals(po.getAppointmentTime())){
			this.orderTime=new SimpleDateFormat("YYYY-MM-dd' 'HH:mm").format(po.getAppointmentTime());
		}else{
			this.orderTime=new SimpleDateFormat("YYYY-MM-dd' 'HH:mm").format(po.getEntryTime());
		}

		this.parkingNo=po.getParkingNo();
		this.parkingName=po.getParkingName();
		this.parkType=po.getParkType();
		this.memberNo=po.getMemberNo();
		if(po.getEntryTime()==null&&po.getOrderStatus()==0&&
				po.getParkType()==1){
			this.orderStatus=10;		
		}else{
			this.orderStatus=po.getOrderStatus();
		}
	}
	
	public String getSpaceNo() {
		return spaceNo;
	}

	public void setSpaceNo(String spaceNo) {
		this.spaceNo = spaceNo;
	}
	public String getParkOrderNo() {
		return parkOrderNo;
	}
	public void setParkOrderNo(String parkOrderNo) {
		this.parkOrderNo = parkOrderNo;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getParkingNo() {
		return parkingNo;
	}
	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getParkType() {
		return parkType;
	}
	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}
	
}
