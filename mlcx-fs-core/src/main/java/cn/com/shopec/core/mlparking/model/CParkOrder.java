package cn.com.shopec.core.mlparking.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 停车订单表 数据实体类
 */
public class CParkOrder extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单编号
	private String parkOrderNo;
	//车牌号
	private String carNo;
	//订单状态(0进行中，1待支付，2待评价，3已完成)
	private Integer orderStatus;
	//预约时间
	private Date appointmentTime;
	//预约时间 时间范围起（查询用）
	private Date appointmentTimeStart;
	//预约时间 时间范围止（查询用）
	private Date appointmentTimeEnd;	
	//进闸时间
	private Date entryTime;
	//进闸时间 时间范围起（查询用）
	private Date entryTimeStart;
	//进闸时间 时间范围止（查询用）
	private Date entryTimeEnd;	
	//出闸时间
	private Date departureTime;
	//出闸时间 时间范围起（查询用）
	private Date departureTimeStart;
	//出闸时间 时间范围止（查询用）
	private Date departureTimeEnd;	
	//总时长
	private Integer totalTime;
	//预约时长
	private Integer appointmentTimeTotal;
	//预约免费时长
	private Integer appointmentFreeTotal;
	//总金额
	private Double totalMoney;
	//预约金额
	private Double appointmentMoney;
	//停车金额
	private Double parkMoney;
	//余额抵扣金额
	private Double discountAmount;
	//支付状态(0.未支付 1.已支付)
	private Integer payStatus;
	//支付类型(0.支付宝 1.微信)
	private Integer payType;
	//未付金额
	private Double nopayAmount;
	//支付时间
	private Date paymentTime;
	//支付时间 时间范围起（查询用）
	private Date paymentTimeStart;
	//支付时间 时间范围止（查询用）
	private Date paymentTimeEnd;	
	//支付流水
	private String paymentFlowNo;
	//订单来源（0、ios 1、安卓）
	private Integer orderSource;
	//备注
	private String orderMemo;
	//结束类型0、正常结束1、后台结束2.未结束
	private Integer finishType;
	//结束理由
	private String finishReason;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型(0、系统自动操作，1、平台人员操作)
	private Integer operatorType;
	//操作人
	private String operatorId;
	//停车场编号
	private String parkingNo;
	//停车场名称
	private String parkingName;
	//会员id
	private String memberNo;
	//手机号
	private String mobilePhone;
	//会员名称
	private String memberName;
	//停车场类型(0.闸机 1.地锁,2无设备)
	private Integer parkType;
	//地锁编号
	private String parkLockNo;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public String getParkLockNo() {
		return parkLockNo;
	}

	public void setParkLockNo(String parkLockNo) {
		this.parkLockNo = parkLockNo;
	}

	public Integer getParkType() {
		return parkType;
	}

	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}

	
	public String getParkOrderNo(){
		return parkOrderNo;
	}
	
	public void setParkOrderNo(String parkOrderNo){
		this.parkOrderNo = parkOrderNo;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public void setCarNo(String carNo){
		this.carNo = carNo;
	}
	
	public Integer getOrderStatus(){
		return orderStatus;
	}
	
	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
	}
	
	public Date getAppointmentTime(){
		return appointmentTime;
	}
	
	public void setAppointmentTime(Date appointmentTime){
		this.appointmentTime = appointmentTime;
	}
	
	public Date getAppointmentTimeStart(){
		return appointmentTimeStart;
	}
	
	public void setAppointmentTimeStart(Date appointmentTimeStart){
		this.appointmentTimeStart = appointmentTimeStart;
	}
	
	public Date getAppointmentTimeEnd(){
		return appointmentTimeEnd;
	}
	
	public void setAppointmentTimeEnd(Date appointmentTimeEnd){
		this.appointmentTimeEnd = appointmentTimeEnd;
	}	
	
	public Date getEntryTime(){
		return entryTime;
	}
	
	public void setEntryTime(Date entryTime){
		this.entryTime = entryTime;
	}
	
	public Date getEntryTimeStart(){
		return entryTimeStart;
	}
	
	public void setEntryTimeStart(Date entryTimeStart){
		this.entryTimeStart = entryTimeStart;
	}
	
	public Date getEntryTimeEnd(){
		return entryTimeEnd;
	}
	
	public void setEntryTimeEnd(Date entryTimeEnd){
		this.entryTimeEnd = entryTimeEnd;
	}	
	
	public Date getDepartureTime(){
		return departureTime;
	}
	
	public void setDepartureTime(Date departureTime){
		this.departureTime = departureTime;
	}
	
	public Date getDepartureTimeStart(){
		return departureTimeStart;
	}
	
	public void setDepartureTimeStart(Date departureTimeStart){
		this.departureTimeStart = departureTimeStart;
	}
	
	public Date getDepartureTimeEnd(){
		return departureTimeEnd;
	}
	
	public void setDepartureTimeEnd(Date departureTimeEnd){
		this.departureTimeEnd = departureTimeEnd;
	}	
	
	public Integer getTotalTime(){
		return totalTime;
	}
	
	public void setTotalTime(Integer totalTime){
		this.totalTime = totalTime;
	}
	
	public Integer getAppointmentTimeTotal(){
		return appointmentTimeTotal;
	}
	
	public void setAppointmentTimeTotal(Integer appointmentTimeTotal){
		this.appointmentTimeTotal = appointmentTimeTotal;
	}
	
	public Integer getAppointmentFreeTotal(){
		return appointmentFreeTotal;
	}
	
	public void setAppointmentFreeTotal(Integer appointmentFreeTotal){
		this.appointmentFreeTotal = appointmentFreeTotal;
	}
	
	public Double getTotalMoney(){
		return totalMoney;
	}
	
	public void setTotalMoney(Double totalMoney){
		this.totalMoney = totalMoney;
	}
	
	public Double getAppointmentMoney(){
		return appointmentMoney;
	}
	
	public void setAppointmentMoney(Double appointmentMoney){
		this.appointmentMoney = appointmentMoney;
	}
	
	public Double getParkMoney(){
		return parkMoney;
	}
	
	public void setParkMoney(Double parkMoney){
		this.parkMoney = parkMoney;
	}
	
	public Double getDiscountAmount(){
		return discountAmount;
	}
	
	public void setDiscountAmount(Double discountAmount){
		this.discountAmount = discountAmount;
	}
	
	public Integer getPayStatus(){
		return payStatus;
	}
	
	public void setPayStatus(Integer payStatus){
		this.payStatus = payStatus;
	}
	
	public Integer getPayType(){
		return payType;
	}
	
	public void setPayType(Integer payType){
		this.payType = payType;
	}
	
	public Double getNopayAmount(){
		return nopayAmount;
	}
	
	public void setNopayAmount(Double nopayAmount){
		this.nopayAmount = nopayAmount;
	}
	
	public Date getPaymentTime(){
		return paymentTime;
	}
	
	public void setPaymentTime(Date paymentTime){
		this.paymentTime = paymentTime;
	}
	
	public Date getPaymentTimeStart(){
		return paymentTimeStart;
	}
	
	public void setPaymentTimeStart(Date paymentTimeStart){
		this.paymentTimeStart = paymentTimeStart;
	}
	
	public Date getPaymentTimeEnd(){
		return paymentTimeEnd;
	}
	
	public void setPaymentTimeEnd(Date paymentTimeEnd){
		this.paymentTimeEnd = paymentTimeEnd;
	}	
	
	public String getPaymentFlowNo(){
		return paymentFlowNo;
	}
	
	public void setPaymentFlowNo(String paymentFlowNo){
		this.paymentFlowNo = paymentFlowNo;
	}
	
	public Integer getOrderSource(){
		return orderSource;
	}
	
	public void setOrderSource(Integer orderSource){
		this.orderSource = orderSource;
	}
	
	public String getOrderMemo(){
		return orderMemo;
	}
	
	public void setOrderMemo(String orderMemo){
		this.orderMemo = orderMemo;
	}
	
	public Integer getFinishType(){
		return finishType;
	}
	
	public void setFinishType(Integer finishType){
		this.finishType = finishType;
	}
	
	public String getFinishReason(){
		return finishReason;
	}
	
	public void setFinishReason(String finishReason){
		this.finishReason = finishReason;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public String getParkingNo(){
		return parkingNo;
	}
	
	public void setParkingNo(String parkingNo){
		this.parkingNo = parkingNo;
	}
	
	public String getParkingName(){
		return parkingName;
	}
	
	public void setParkingName(String parkingName){
		this.parkingName = parkingName;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CParkOrder [parkOrderNo=" + parkOrderNo + ", carNo=" + carNo
				+ ", orderStatus=" + orderStatus + ", appointmentTime="
				+ appointmentTime + ", appointmentTimeStart="
				+ appointmentTimeStart + ", appointmentTimeEnd="
				+ appointmentTimeEnd + ", entryTime=" + entryTime
				+ ", entryTimeStart=" + entryTimeStart + ", entryTimeEnd="
				+ entryTimeEnd + ", departureTime=" + departureTime
				+ ", departureTimeStart=" + departureTimeStart
				+ ", departureTimeEnd=" + departureTimeEnd + ", totalTime="
				+ totalTime + ", appointmentTimeTotal=" + appointmentTimeTotal
				+ ", appointmentFreeTotal=" + appointmentFreeTotal
				+ ", totalMoney=" + totalMoney + ", appointmentMoney="
				+ appointmentMoney + ", parkMoney=" + parkMoney
				+ ", discountAmount=" + discountAmount + ", payStatus="
				+ payStatus + ", payType=" + payType + ", nopayAmount="
				+ nopayAmount + ", paymentTime=" + paymentTime
				+ ", paymentTimeStart=" + paymentTimeStart
				+ ", paymentTimeEnd=" + paymentTimeEnd + ", paymentFlowNo="
				+ paymentFlowNo + ", orderSource=" + orderSource
				+ ", orderMemo=" + orderMemo + ", finishType=" + finishType
				+ ", finishReason=" + finishReason + ", createTime="
				+ createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorType="
				+ operatorType + ", operatorId=" + operatorId + ", parkingNo="
				+ parkingNo + ", parkingName=" + parkingName + ", memberNo="
				+ memberNo + ", mobilePhone=" + mobilePhone + ", memberName="
				+ memberName + ", parkType=" + parkType + ", parkLockNo="
				+ parkLockNo + "]";
	}
}
