package cn.com.shopec.core.monitor.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 警告表 数据实体类
 */
public class Warning extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//警告编号
	private String warningNo;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//级别 0一级1二级2三级
	private Integer warningLevel;
	//警告类别（具体见字典表）
	private String warningType;
	//警告子类别（具体见字典表）
	private String warningSubType;
	//警告时间
	private Date warningTime;
	//警告时间 时间范围起（查询用）
	private Date warningTimeStart;
	//警告时间 时间范围止（查询用）
	private Date warningTimeEnd;	
	//警告内容
	private String warningContent;
	//相关场站编号
	private String parkNo;
	//相关场站名称
	private String parkName;
	//相关车辆牌号
	private String carPlateNo;
	//相关会员编号
	private String memberNo;
	//相关会员名称
	private String memberName;
	//相关订单编号
	private String orderNo;
	//是否需要人工关闭（0、不需人工关闭，1、需人工关闭，默认0）
	private Integer isNeedManualClosed;
	//是否关闭（0、未关闭，1、已关闭，默认0）
	private Integer isClosed;
	//关闭时间
	private Date closedTime;
	//关闭时间 时间范围起（查询用）
	private Date closedTimeStart;
	//关闭时间 时间范围止（查询用）
	private Date closedTimeEnd;	
	//关闭操作的备注
	private String closedMemo;
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
	//非订单监控地图上关闭警告的标记
	private String flag;
	//低电压标记
	private Integer type;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return warningNo;
	}
	
	public String getWarningNo(){
		return warningNo;
	}
	
	public void setWarningNo(String warningNo){
		this.warningNo = warningNo;
	}
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public Integer getWarningLevel(){
		return warningLevel;
	}
	
	public void setWarningLevel(Integer warningLevel){
		this.warningLevel = warningLevel;
	}
	
	public String getWarningType(){
		return warningType;
	}
	
	public void setWarningType(String warningType){
		this.warningType = warningType;
	}
	
	public String getWarningSubType(){
		return warningSubType;
	}
	
	public void setWarningSubType(String warningSubType){
		this.warningSubType = warningSubType;
	}
	
	public Date getWarningTime(){
		return warningTime;
	}
	
	public void setWarningTime(Date warningTime){
		this.warningTime = warningTime;
	}
	
	public Date getWarningTimeStart(){
		return warningTimeStart;
	}
	
	public void setWarningTimeStart(Date warningTimeStart){
		this.warningTimeStart = warningTimeStart;
	}
	
	public Date getWarningTimeEnd(){
		return warningTimeEnd;
	}
	
	public void setWarningTimeEnd(Date warningTimeEnd){
		this.warningTimeEnd = warningTimeEnd;
	}	
	
	public String getWarningContent(){
		return warningContent;
	}
	
	public void setWarningContent(String warningContent){
		this.warningContent = warningContent;
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
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Integer getIsNeedManualClosed(){
		return isNeedManualClosed;
	}
	
	public void setIsNeedManualClosed(Integer isNeedManualClosed){
		this.isNeedManualClosed = isNeedManualClosed;
	}
	
	public Integer getIsClosed(){
		return isClosed;
	}
	
	public void setIsClosed(Integer isClosed){
		this.isClosed = isClosed;
	}
	
	public Date getClosedTime(){
		return closedTime;
	}
	
	public void setClosedTime(Date closedTime){
		this.closedTime = closedTime;
	}
	
	public Date getClosedTimeStart(){
		return closedTimeStart;
	}
	
	public void setClosedTimeStart(Date closedTimeStart){
		this.closedTimeStart = closedTimeStart;
	}
	
	public Date getClosedTimeEnd(){
		return closedTimeEnd;
	}
	
	public void setClosedTimeEnd(Date closedTimeEnd){
		this.closedTimeEnd = closedTimeEnd;
	}	
	
	public String getClosedMemo(){
		return closedMemo;
	}
	
	public void setClosedMemo(String closedMemo){
		this.closedMemo = closedMemo;
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
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	

	@Override
	public String toString() {
		return "Warning ["
		 + "warningNo = " + warningNo + ", cityId = " + cityId + ", cityName = " + cityName + ", warningLevel = " + warningLevel
		 + ", warningType = " + warningType + ", warningSubType = " + warningSubType + ", warningTime = " + warningTime + ", warningTimeStart = " + warningTimeStart + ", warningTimeEnd = " + warningTimeEnd + ", warningContent = " + warningContent
		 + ", parkNo = " + parkNo + ", parkName = " + parkName + ", carPlateNo = " + carPlateNo + ", memberNo = " + memberNo
		 + ", memberName = " + memberName + ", orderNo = " + orderNo + ", isNeedManualClosed = " + isNeedManualClosed + ", isClosed = " + isClosed
		 + ", closedTime = " + closedTime + ", closedTimeStart = " + closedTimeStart + ", closedTimeEnd = " + closedTimeEnd + ", closedMemo = " + closedMemo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
}
