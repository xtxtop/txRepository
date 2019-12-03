package cn.com.shopec.core.order.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 订单结束表 数据实体类
 */
public class OrderFinish extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单编号
	private String orderNo;
	//订单结束类型（0 会员结束 1 客服结束  2  自动结束）
	private Integer finishType;
	//结束原因
	private String finishReason;
	//当前车辆位置
	private String currentCarLocation;
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	//坐标最后上报时间
	private Date lastReportTime;
	//坐标最后上报时间 时间范围起（查询用）
	private Date lastReportTimeStart;
	//坐标最后上报时间 时间范围止（查询用）
	private Date lastReportTimeEnd;	
	//附加费用
	private Double additionFees;
	//附加费用原因
	private String additionReason;
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
	private Integer operatorType;
	private String operatorId;
	//是否故障 （0 否  1 是  ）
	private Integer isCarFault;
	//是否事故（0 否 1 是  ）
	private Integer isCarAccident;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return orderNo;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
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
	
	public String getCurrentCarLocation(){
		return currentCarLocation;
	}
	
	public void setCurrentCarLocation(String currentCarLocation){
		this.currentCarLocation = currentCarLocation;
	}
	
	public Double getLongitude(){
		return longitude;
	}
	
	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}
	
	public Double getLatitude(){
		return latitude;
	}
	
	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}
	
	public Date getLastReportTime(){
		return lastReportTime;
	}
	
	public void setLastReportTime(Date lastReportTime){
		this.lastReportTime = lastReportTime;
	}
	
	public Date getLastReportTimeStart(){
		return lastReportTimeStart;
	}
	
	public void setLastReportTimeStart(Date lastReportTimeStart){
		this.lastReportTimeStart = lastReportTimeStart;
	}
	
	public Date getLastReportTimeEnd(){
		return lastReportTimeEnd;
	}
	
	public void setLastReportTimeEnd(Date lastReportTimeEnd){
		this.lastReportTimeEnd = lastReportTimeEnd;
	}	
	
	public Double getAdditionFees(){
		return additionFees;
	}
	
	public void setAdditionFees(Double additionFees){
		this.additionFees = additionFees;
	}
	
	public String getAdditionReason(){
		return additionReason;
	}
	
	public void setAdditionReason(String additionReason){
		this.additionReason = additionReason;
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
		return "OrderFinish ["
		 + "orderNo = " + orderNo + ", finishType = " + finishType + ", finishReason = " + finishReason + ", currentCarLocation = " + currentCarLocation
		 + ", longitude = " + longitude + ", latitude = " + latitude + ", lastReportTime = " + lastReportTime + ", lastReportTimeStart = " + lastReportTimeStart + ", lastReportTimeEnd = " + lastReportTimeEnd + ", additionFees = " + additionFees
		 + ", additionReason = " + additionReason + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId
		+"]";
	}

	public Integer getIsCarFault() {
		return isCarFault;
	}

	public void setIsCarFault(Integer isCarFault) {
		this.isCarFault = isCarFault;
	}

	public Integer getIsCarAccident() {
		return isCarAccident;
	}

	public void setIsCarAccident(Integer isCarAccident) {
		this.isCarAccident = isCarAccident;
	}
}
