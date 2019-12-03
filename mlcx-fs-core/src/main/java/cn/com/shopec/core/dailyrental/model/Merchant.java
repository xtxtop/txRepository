package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 租赁商家表 数据实体类
 */
public class Merchant extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//租赁商id
	private String merchantId;
	//租赁商名称
	private String merchantName;
	//联系人
	private String cantactPerson;
	//联系电话
	private String mobilePhone;
	//联系邮箱
	private String eMail;
	//一级行政区（省）id
	private String addrRegion1Id;
	//一级行政区（省）名称
	private String addrRegion1Name;
	//二级行政区（市/直辖市区县）id
	private String addrRegion2Id;
	//二级行政区（市/直辖市区县）名称
	private String addrRegion2Name;
	//三级行政区（区县）id
	private String addrRegion3Id;
	//三级行政区（区县）名称
	private String addrRegion3Name;
	//地址街道
	private String addrStreet;
	//是否支持异地还车(0、不支持1、支持，默认0)
	private Integer isOffsiteReturncar;
	//可用状态（0，不可用、1，可用，默认0）
	private Integer isAvailable;
	//审核状态（0、未审核，1、已审核，2、未通过，默认0）
	private Integer cencorStatus;
	//审核时间
	private Date cencorTime;
	//审核备注
	private String cencorMemo;	
	//是否删除是否删除（0,未删除，1、已删除）
	private Integer isDelete;
	//对账周期(1、5天，2、10天，3、15天，4、一个月（自然月）
	private Integer reconciliationCycle;
	//分润比(商家占总金额比例)
	private Double profitRatio;
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
		return merchantId;
	}
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getMerchantName(){
		return merchantName;
	}
	
	public void setMerchantName(String merchantName){
		this.merchantName = merchantName;
	}
	
	public String getCantactPerson(){
		return cantactPerson;
	}
	
	public void setCantactPerson(String cantactPerson){
		this.cantactPerson = cantactPerson;
	}
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getAddrRegion1Id(){
		return addrRegion1Id;
	}
	
	public void setAddrRegion1Id(String addrRegion1Id){
		this.addrRegion1Id = addrRegion1Id;
	}
	
	public String getAddrRegion1Name(){
		return addrRegion1Name;
	}
	
	public void setAddrRegion1Name(String addrRegion1Name){
		this.addrRegion1Name = addrRegion1Name;
	}
	
	public String getAddrRegion2Id(){
		return addrRegion2Id;
	}
	
	public void setAddrRegion2Id(String addrRegion2Id){
		this.addrRegion2Id = addrRegion2Id;
	}
	
	public String getAddrRegion2Name(){
		return addrRegion2Name;
	}
	
	public void setAddrRegion2Name(String addrRegion2Name){
		this.addrRegion2Name = addrRegion2Name;
	}
	
	public String getAddrRegion3Id(){
		return addrRegion3Id;
	}
	
	public void setAddrRegion3Id(String addrRegion3Id){
		this.addrRegion3Id = addrRegion3Id;
	}
	
	public String getAddrRegion3Name(){
		return addrRegion3Name;
	}
	
	public void setAddrRegion3Name(String addrRegion3Name){
		this.addrRegion3Name = addrRegion3Name;
	}
	
	public String getAddrStreet(){
		return addrStreet;
	}
	
	public void setAddrStreet(String addrStreet){
		this.addrStreet = addrStreet;
	}
	
	public Integer getIsOffsiteReturncar(){
		return isOffsiteReturncar;
	}
	
	public void setIsOffsiteReturncar(Integer isOffsiteReturncar){
		this.isOffsiteReturncar = isOffsiteReturncar;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getCencorStatus() {
		return cencorStatus;
	}

	public void setCencorStatus(Integer cencorStatus) {
		this.cencorStatus = cencorStatus;
	}

	public Date getCencorTime() {
		return cencorTime;
	}

	public void setCencorTime(Date cencorTime) {
		this.cencorTime = cencorTime;
	}

	public String getCencorMemo() {
		return cencorMemo;
	}

	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
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
	
	public Integer getReconciliationCycle() {
		return reconciliationCycle;
	}

	public void setReconciliationCycle(Integer reconciliationCycle) {
		this.reconciliationCycle = reconciliationCycle;
	}

	public Double getProfitRatio() {
		return profitRatio;
	}

	public void setProfitRatio(Double profitRatio) {
		this.profitRatio = profitRatio;
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
		return "Merchant ["
		 + "merchantId = " + merchantId + ", merchantName = " + merchantName + ", cantactPerson = " + cantactPerson + ", mobilePhone = " + mobilePhone
		 + ", eMail = " + eMail + ", addrRegion1Id = " + addrRegion1Id + ", addrRegion1Name = " + addrRegion1Name + ", addrRegion2Id = " + addrRegion2Id
		 + ", addrRegion2Name = " + addrRegion2Name + ", addrRegion3Id = " + addrRegion3Id + ", addrRegion3Name = " + addrRegion3Name + ", addrStreet = " + addrStreet
		 + ", isOffsiteReturncar = " + isOffsiteReturncar + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId
		 + ", operatorType = " + operatorType
		+"]";
	}
}
