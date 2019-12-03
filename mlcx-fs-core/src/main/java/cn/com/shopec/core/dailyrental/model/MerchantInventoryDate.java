package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 商家库存日历表 数据实体类
 */
public class MerchantInventoryDate extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//库存日历id
	private String inventoryDateId;
	//所属商家id
	private String merchantId;
	//车型id
	private String carModelId;
	//商家库存id
	private String merInventoryId;
	//所属日期
	private String inventoryDate;
	//所属日期 时间范围起（查询用）
	private String inventoryDateStart;
	//所属日期 时间范围止（查询用）
	private String inventoryDateEnd;	
	//总库存数量
	private Integer inventoryTotal;
	//已租用数量
	private Integer leasedQuantity;
	//可用库存数量
	private Integer availableInventory;
	//停用时可用库存数量
	private Integer availableInventoryOfOffline;
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
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
	}
	
	public String getMerInventoryId(){
		return merInventoryId;
	}
	
	public void setMerInventoryId(String merInventoryId){
		this.merInventoryId = merInventoryId;
	}
	
	public String getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getInventoryDateStart(){
		return inventoryDateStart;
	}
	
	public void setInventoryDateStart(String inventoryDateStart){
		this.inventoryDateStart = inventoryDateStart;
	}
	
	public String getInventoryDateEnd(){
		return inventoryDateEnd;
	}
	
	public void setInventoryDateEnd(String inventoryDateEnd){
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
	
	public Integer getAvailableInventory(){
		return availableInventory;
	}
	
	public void setAvailableInventory(Integer availableInventory){
		this.availableInventory = availableInventory;
	}
	
	public Integer getAvailableInventoryOfOffline() {
		return availableInventoryOfOffline;
	}

	public void setAvailableInventoryOfOffline(Integer availableInventoryOfOffline) {
		this.availableInventoryOfOffline = availableInventoryOfOffline;
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
		return "MerchantInventoryDate ["
		 + "inventoryDateId = " + inventoryDateId + ", merchantId = " + merchantId + ", carModelId = " + carModelId + ", merInventoryId = " + merInventoryId
		 + ", inventoryDate = " + inventoryDate + ", inventoryDateStart = " + inventoryDateStart + ", inventoryDateEnd = " + inventoryDateEnd + ", inventoryTotal = " + inventoryTotal + ", leasedQuantity = " + leasedQuantity + ", availableInventory = " + availableInventory
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
