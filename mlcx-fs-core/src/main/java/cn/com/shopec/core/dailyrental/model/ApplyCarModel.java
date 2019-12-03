package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * ApplyCarModel 数据实体类
 */
public class ApplyCarModel extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//车型id
	private String applyCarModelId;
	//品牌名称
	private String carBrandName;
	//车型年代名称
	private String carPeriodName;
	//车系名称
	private String carSeriesName;
	//适用类型(1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。)
	private String carType;
	//排量
	private String displacement;
	//变速箱1、手动，2、自动3、手自一体
	private Integer gearBox;
	//申请人id
	private String applyId;
	//申请人姓名
	private String applyName;
	//是否同意（0 否，1 是）
	private Integer isAgree;
	//是否删除
	private Integer isDelete;
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
	//操作类型
	private Integer operatorType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return applyCarModelId;
	}
	
	public String getApplyCarModelId(){
		return applyCarModelId;
	}
	
	public void setApplyCarModelId(String applyCarModelId){
		this.applyCarModelId = applyCarModelId;
	}
	
	public String getCarBrandName(){
		return carBrandName;
	}
	
	public void setCarBrandName(String carBrandName){
		this.carBrandName = carBrandName;
	}
	
	public String getCarPeriodName(){
		return carPeriodName;
	}
	
	public void setCarPeriodName(String carPeriodName){
		this.carPeriodName = carPeriodName;
	}
	
	public String getCarSeriesName(){
		return carSeriesName;
	}
	
	public void setCarSeriesName(String carSeriesName){
		this.carSeriesName = carSeriesName;
	}
	
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getDisplacement(){
		return displacement;
	}
	
	public void setDisplacement(String displacement){
		this.displacement = displacement;
	}
	
	public Integer getGearBox(){
		return gearBox;
	}
	
	public void setGearBox(Integer gearBox){
		this.gearBox = gearBox;
	}
	
	public String getApplyId(){
		return applyId;
	}
	
	public void setApplyId(String applyId){
		this.applyId = applyId;
	}
	
	public String getApplyName(){
		return applyName;
	}
	
	public void setApplyName(String applyName){
		this.applyName = applyName;
	}
	
	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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
		return "ApplyCarModel ["
		 + "applyCarModelId = " + applyCarModelId + ", carBrandName = " + carBrandName + ", carPeriodName = " + carPeriodName + ", carSeriesName = " + carSeriesName
		 + ", carType = " + carType + ", displacement = " + displacement + ", gearBox = " + gearBox + ", applyId = " + applyId
		 + ", applyName = " + applyName + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId
		 + ", operatorType = " + operatorType
		+"]";
	}
}
