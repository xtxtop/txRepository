package cn.com.shopec.core.franchisee.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 加盟商结算表 数据实体类
 */
public class FranchiseeSettle extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//加盟商结算编号
	private String franchiseeSettleNo;
	//加盟商编号
	private String franchiseeNo;
	//加盟商名称
	private String franchiseeName;
	//订单编号
	private String orderNo;
	//总订单金额
	private Double orderAmount;
	//总订单数
	private Double orderCount;
	//分润总金额
	private Double shareAmount;
	//车辆产生分润订单数
	private Double carshareOrderCount;
	//车辆产生分润订单总金额
	private Double carshareOrderAmount;
	//车辆产生分润总金额
	private Double carShareAmount;
	//场站产生分润订单数
	private Double parkshareOrderCount;
	//场站产生分润订单总金额
	private Double parkshareOrderAmount;
	//场站产生分润总金额
	private Double parkShareAmount;
	//结算周期
	private String settleDay;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	
	@Override
	public String getPK(){
		return franchiseeSettleNo;
	}
	
	public String getFranchiseeSettleNo(){
		return franchiseeSettleNo;
	}
	
	public void setFranchiseeSettleNo(String franchiseeSettleNo){
		this.franchiseeSettleNo = franchiseeSettleNo;
	}
	
	public String getFranchiseeNo(){
		return franchiseeNo;
	}
	
	public void setFranchiseeNo(String franchiseeNo){
		this.franchiseeNo = franchiseeNo;
	}
	
	public String getFranchiseeName(){
		return franchiseeName;
	}
	
	public void setFranchiseeName(String franchiseeName){
		this.franchiseeName = franchiseeName;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
	}
	
	public Double getOrderCount(){
		return orderCount;
	}
	
	public void setOrderCount(Double orderCount){
		this.orderCount = orderCount;
	}
	
	public Double getCarshareOrderCount(){
		return carshareOrderCount;
	}
	
	public void setCarshareOrderCount(Double carshareOrderCount){
		this.carshareOrderCount = carshareOrderCount;
	}
	
	public Double getCarshareOrderAmount(){
		return carshareOrderAmount;
	}
	
	public void setCarshareOrderAmount(Double carshareOrderAmount){
		this.carshareOrderAmount = carshareOrderAmount;
	}
	
	public Double getCarShareAmount(){
		return carShareAmount;
	}
	
	public void setCarShareAmount(Double carShareAmount){
		this.carShareAmount = carShareAmount;
	}
	
	public Double getParkshareOrderCount(){
		return parkshareOrderCount;
	}
	
	public void setParkshareOrderCount(Double parkshareOrderCount){
		this.parkshareOrderCount = parkshareOrderCount;
	}
	
	public Double getParkshareOrderAmount(){
		return parkshareOrderAmount;
	}
	
	public void setParkshareOrderAmount(Double parkshareOrderAmount){
		this.parkshareOrderAmount = parkshareOrderAmount;
	}
	
	public Double getParkShareAmount(){
		return parkShareAmount;
	}
	
	public void setParkShareAmount(Double parkShareAmount){
		this.parkShareAmount = parkShareAmount;
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
	
	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}
	

	public Double getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(Double shareAmount) {
		this.shareAmount = shareAmount;
	}

	@Override
	public String toString() {
		return "FranchiseeSettle ["
		 + "franchiseeSettleNo = " + franchiseeSettleNo + ", franchiseeNo = " + franchiseeNo + ", franchiseeName = " + franchiseeName + ", orderNo = " + orderNo
		 + ", orderAmount = " + orderAmount + ",shareAmount="+shareAmount+", orderCount = " + orderCount + ", carshareOrderCount = " + carshareOrderCount + ", carshareOrderAmount = " + carshareOrderAmount
		 + ", carShareAmount = " + carShareAmount + ", parkshareOrderCount = " + parkshareOrderCount + ", parkshareOrderAmount = " + parkshareOrderAmount + ", parkShareAmount = " + parkShareAmount + ",settleDay="+settleDay
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
