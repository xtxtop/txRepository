package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * CarRedPacket 数据实体类
 */
public class CarRedPacket extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	private String carRedPacketId;
	//车牌号
	private String carPlateNo;
	//目标场站编号
	private String parkNo;
	//目标场站名称
	private String parkName;
	//红包金额
	private Double redPacketAmount;
	//是否限制订单金额满多少钱可领取（0.否 1.是）
	private Integer isOrderAmountLimit;
	//满足领取红包的订单金额最小值（IS_ORDER_AMOUNT_LIMIT为1时可用）
	private Double orderAmountLimit;
	//车辆使用状态（0.空闲1.预占 2.订单中 3.已结束）
	private Integer carUserageStatus;
	//红包下发状态（0.未下发 1.已下发）
	private Integer redPacketSendStatus;
	//红包车状态(0、已添加、1.已生效2.进行中3.已完结)
	private Integer carRedPacketStatus;
	//红包设定人id
	private String redPacketOperatorId;
	//红包设定人姓名
	private String redPacketOperatorName;
	private String orderNo;
	//领取红包是否需要充电（0.不需要 1.需要）
	private Integer isCharge;
	//创建日期
	private Date createTime;
	//创建日期 时间范围起（查询用）
	private Date createTimeStart;
	//创建日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新日期
	private Date updateTime;
	//更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	//更新日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	//可用状态（1、可用，0、不可用，默认0）
	private Integer isAvailable;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return carRedPacketId;
	}
	
	public String getCarRedPacketId(){
		return carRedPacketId;
	}
	
	public void setCarRedPacketId(String carRedPacketId){
		this.carRedPacketId = carRedPacketId;
	}
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
	}
	
	public String getParkNo(){
		return parkNo;
	}
	
	public void setParkNo(String parkNo){
		this.parkNo = parkNo;
	}
	
	public String getParkName(){
		return parkName;
	}
	
	public void setParkName(String parkName){
		this.parkName = parkName;
	}
	
	public Double getRedPacketAmount(){
		return redPacketAmount;
	}
	
	public void setRedPacketAmount(Double redPacketAmount){
		this.redPacketAmount = redPacketAmount;
	}
	
	public Integer getIsOrderAmountLimit(){
		return isOrderAmountLimit;
	}
	
	public void setIsOrderAmountLimit(Integer isOrderAmountLimit){
		this.isOrderAmountLimit = isOrderAmountLimit;
	}
	
	public Double getOrderAmountLimit(){
		return orderAmountLimit;
	}
	
	public void setOrderAmountLimit(Double orderAmountLimit){
		this.orderAmountLimit = orderAmountLimit;
	}
	
	public Integer getCarUserageStatus(){
		return carUserageStatus;
	}
	
	public void setCarUserageStatus(Integer carUserageStatus){
		this.carUserageStatus = carUserageStatus;
	}
	
	public Integer getRedPacketSendStatus(){
		return redPacketSendStatus;
	}
	
	public void setRedPacketSendStatus(Integer redPacketSendStatus){
		this.redPacketSendStatus = redPacketSendStatus;
	}
	
	public Integer getCarRedPacketStatus(){
		return carRedPacketStatus;
	}
	
	public void setCarRedPacketStatus(Integer carRedPacketStatus){
		this.carRedPacketStatus = carRedPacketStatus;
	}
	
	public String getRedPacketOperatorId(){
		return redPacketOperatorId;
	}
	
	public void setRedPacketOperatorId(String redPacketOperatorId){
		this.redPacketOperatorId = redPacketOperatorId;
	}
	
	public String getRedPacketOperatorName(){
		return redPacketOperatorName;
	}
	
	public void setRedPacketOperatorName(String redPacketOperatorName){
		this.redPacketOperatorName = redPacketOperatorName;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Integer getIsCharge(){
		return isCharge;
	}
	
	public void setIsCharge(Integer isCharge){
		this.isCharge = isCharge;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CarRedPacket ["
		 + "carRedPacketId = " + carRedPacketId + ", carPlateNo = " + carPlateNo + ", parkNo = " + parkNo + ", parkName = " + parkName
		 + ", redPacketAmount = " + redPacketAmount + ", isOrderAmountLimit = " + isOrderAmountLimit + ", orderAmountLimit = " + orderAmountLimit + ", carUserageStatus = " + carUserageStatus
		 + ", redPacketSendStatus = " + redPacketSendStatus + ", carRedPacketStatus = " + carRedPacketStatus + ", redPacketOperatorId = " + redPacketOperatorId + ", redPacketOperatorName = " + redPacketOperatorName
		 + ", orderNo = " + orderNo + ", isCharge = " + isCharge + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
}
