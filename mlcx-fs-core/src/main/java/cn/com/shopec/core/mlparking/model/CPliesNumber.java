package cn.com.shopec.core.mlparking.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 楼层表 数据实体类
 */
public class CPliesNumber extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//车位层数id
	private String parkingPliesNo;
	//停车场id
	private String parkingNo;
	//层数
	private String pliesNumber;
	//总车位数
	private Integer parkingSpaceNumber;
	//剩余车位数
	private Integer surplusSpaceNumber;
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
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	//停车场车位分布类型(1.地下 2.地面 3.楼层)
	private Integer spaceType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public Integer getSpaceType() {
		return spaceType;
	}

	public void setSpaceType(Integer spaceType) {
		this.spaceType = spaceType;
	}

	@Override
	public String getPK(){
		return parkingPliesNo;
	}
	
	public String getParkingPliesNo(){
		return parkingPliesNo;
	}
	
	public void setParkingPliesNo(String parkingPliesNo){
		this.parkingPliesNo = parkingPliesNo;
	}
	
	public String getParkingNo(){
		return parkingNo;
	}
	
	public void setParkingNo(String parkingNo){
		this.parkingNo = parkingNo;
	}
	
	public String getPliesNumber(){
		return pliesNumber;
	}
	
	public void setPliesNumber(String pliesNumber){
		this.pliesNumber = pliesNumber;
	}
	
	public Integer getParkingSpaceNumber(){
		return parkingSpaceNumber;
	}
	
	public void setParkingSpaceNumber(Integer parkingSpaceNumber){
		this.parkingSpaceNumber = parkingSpaceNumber;
	}
	
	public Integer getSurplusSpaceNumber(){
		return surplusSpaceNumber;
	}
	
	public void setSurplusSpaceNumber(Integer surplusSpaceNumber){
		this.surplusSpaceNumber = surplusSpaceNumber;
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
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CPliesNumber [parkingPliesNo=" + parkingPliesNo
				+ ", parkingNo=" + parkingNo + ", pliesNumber=" + pliesNumber
				+ ", parkingSpaceNumber=" + parkingSpaceNumber
				+ ", surplusSpaceNumber=" + surplusSpaceNumber
				+ ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorId=" + operatorId + ", operatorType="
				+ operatorType + ", spaceType=" + spaceType + "]";
	}
}
