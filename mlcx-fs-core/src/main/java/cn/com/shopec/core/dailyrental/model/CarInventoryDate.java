package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 车辆库存日历表 数据实体类
 */
public class CarInventoryDate extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//库存日历id
	private String inventoryDateId;
	//车辆库存id
	private String carInventoryId;
	//车型id
	private String carModelId;
	//所属日期
	private String inventoryDate;
	//所属日期 时间范围起（查询用）
	private Date inventoryDateStart;
	//所属日期 时间范围止（查询用）
	private Date inventoryDateEnd;	
	//总库存数量
	private Integer inventoryTotal;
	//已租借数量
	private Integer leasedQuantity;
	//已预订数量
	private Integer reserveQuantity;
	//可用库存数量
	private Integer availableInventory;
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
	//操作人id
	private String operatorId;
	//操作人类型
	private Integer operatorType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return inventoryDateId;
	}
	
	public String getInventoryDateId(){
		return inventoryDateId;
	}
	
	public void setInventoryDateId(String inventoryDateId){
		this.inventoryDateId = inventoryDateId;
	}
	
	public String getCarInventoryId(){
		return carInventoryId;
	}
	
	public void setCarInventoryId(String carInventoryId){
		this.carInventoryId = carInventoryId;
	}
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
	}
	
	public String getInventoryDate(){
		return inventoryDate;
	}
	
	public void setInventoryDate(String inventoryDate){
		this.inventoryDate = inventoryDate;
	}
	
	public Date getInventoryDateStart(){
		return inventoryDateStart;
	}
	
	public void setInventoryDateStart(Date inventoryDateStart){
		this.inventoryDateStart = inventoryDateStart;
	}
	
	public Date getInventoryDateEnd(){
		return inventoryDateEnd;
	}
	
	public void setInventoryDateEnd(Date inventoryDateEnd){
		this.inventoryDateEnd = inventoryDateEnd;
	}	
	
	public Integer getInventoryTotal(){
		return inventoryTotal;
	}
	
	public void setInventoryTotal(Integer inventoryTotal){
		this.inventoryTotal = inventoryTotal;
	}
	
	public Integer getLeasedQuantity(){
		return leasedQuantity;
	}
	
	public void setLeasedQuantity(Integer leasedQuantity){
		this.leasedQuantity = leasedQuantity;
	}
	
	public Integer getReserveQuantity(){
		return reserveQuantity;
	}
	
	public void setReserveQuantity(Integer reserveQuantity){
		this.reserveQuantity = reserveQuantity;
	}
	
	public Integer getAvailableInventory(){
		return availableInventory;
	}
	
	public void setAvailableInventory(Integer availableInventory){
		this.availableInventory = availableInventory;
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
		return "CarInventoryDate ["
		 + "inventoryDateId = " + inventoryDateId + ", carInventoryId = " + carInventoryId + ", carModelId = " + carModelId + ", inventoryDate = " + inventoryDate + ", inventoryDateStart = " + inventoryDateStart + ", inventoryDateEnd = " + inventoryDateEnd
		 + ", inventoryTotal = " + inventoryTotal + ", leasedQuantity = " + leasedQuantity + ", reserveQuantity = " + reserveQuantity + ", availableInventory = " + availableInventory
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
