package cn.com.shopec.core.ml.vo;

import cn.com.shopec.core.mlorder.model.ChargeOrder;
import cn.com.shopec.core.mlorder.model.LockOrder;

import java.text.SimpleDateFormat;

public class OrderSimpleVo {
	private String orderNo;//订单号
	private String orderStatus;//订单状态（0进行中，1待支付，2待评价，3已完成，4未结算，5已取消）
	private String orderTime;//开始时间
	private String stationName;//站名
	private String memberNo;//会员号
	private String tp;//订单类型（1地锁，2充电）
	public OrderSimpleVo(){}
	public OrderSimpleVo(LockOrder order) {
		this.orderNo = order.getOrderNo();
		this.orderTime = new SimpleDateFormat("YYYY-MM-dd' 'HH:mm").format(order.getOrderStartTime());
		this.stationName = order.getStationName();
		this.memberNo = order.getMemberName();
		this.tp = "1";
	}
	public OrderSimpleVo(ChargeOrder order) {
		this.orderNo = order.getOrderNo();
		this.orderTime = new SimpleDateFormat("YYYY-MM-dd' 'HH:mm").format(order.getOrderStartTime());
		this.stationName = order.getStationName();
		this.memberNo = order.getMemberName();
		this.tp = "1";
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
}
