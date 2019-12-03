package cn.com.shopec.core.order.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * ControlPowerLog 数据实体类
 */
public class ControlPowerLog extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//开关动力日志主键
	private String powerLogNo;
	//终端Id
	private String deviceNo;
	//车辆状态
	private Integer carStatus;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//操作人id
	private String operatorId;
	//订单编号
	private String orderNo;
	//操作人类型
	private Integer operatorType;
	//会员号
	private String memberNo;
	//成功失败备注
	private String memo;
	//开启关闭类型（1 开启 0 关闭）
	private Integer cantrolType;
	//开启是否成功（1 成功 2 失败）
	private Integer statusType;
	//会员名称
	private String  memberName;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return powerLogNo;
	}
	
	public String getPowerLogNo(){
		return powerLogNo;
	}
	
	public void setPowerLogNo(String powerLogNo){
		this.powerLogNo = powerLogNo;
	}
	
	public String getDeviceNo(){
		return deviceNo;
	}
	
	public void setDeviceNo(String deviceNo){
		this.deviceNo = deviceNo;
	}
	
	public Integer getCarStatus(){
		return carStatus;
	}
	
	public void setCarStatus(Integer carStatus){
		this.carStatus = carStatus;
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
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public Integer getCantrolType(){
		return cantrolType;
	}
	
	public void setCantrolType(Integer cantrolType){
		this.cantrolType = cantrolType;
	}
	
	public Integer getStatusType(){
		return statusType;
	}
	
	public void setStatusType(Integer statusType){
		this.statusType = statusType;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "ControlPowerLog ["
		 + "powerLogNo = " + powerLogNo + ", deviceNo = " + deviceNo + ", carStatus = " + carStatus + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", operatorId = " + operatorId + ", orderNo = " + orderNo + ", operatorType = " + operatorType + ", memberNo = " + memberNo
		 + ", memo = " + memo + ", cantrolType = " + cantrolType + ", statusType = " + statusType
		+"]";
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}
