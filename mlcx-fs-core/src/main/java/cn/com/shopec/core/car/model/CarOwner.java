package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 车主表 数据实体类
 */
public class CarOwner extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//车主id
	private String ownerId;
	//名称
	private String ownerName;
	//全称
	private String ownerFullName;
	//类型（1、公司，2、个人）
	private Integer ownerType;
	//联系人
	private String contactPerson;
	//联系人电话
	private String contactTel;
	//联系人手机号
	private String contactPhone;
	//联系人邮箱
	private String contactEmail;
	//身份证号
	private String idCardNo;
	//工商营业执照号
	private String bizLicenseNo;
	//组织机构代码证
	private String organizationCode;
	//税务登记证号
	private String taxRegistration;
	//可用状态（0、停用，1、可用，默认1）
	private Integer isAvailable;
	//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private Integer cencorStatus;
	//审核人id
	private String cencorId;
	//审核时间
	private Date cencorTime;
	//审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;	
	//审核备注
	private String cencorMemo;
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
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return ownerId;
	}
	
	public String getOwnerId(){
		return ownerId;
	}
	
	public void setOwnerId(String ownerId){
		this.ownerId = ownerId;
	}
	
	public String getOwnerName(){
		return ownerName;
	}
	
	public void setOwnerName(String ownerName){
		this.ownerName = ownerName;
	}
	
	public String getOwnerFullName(){
		return ownerFullName;
	}
	
	public void setOwnerFullName(String ownerFullName){
		this.ownerFullName = ownerFullName;
	}
	
	public Integer getOwnerType(){
		return ownerType;
	}
	
	public void setOwnerType(Integer ownerType){
		this.ownerType = ownerType;
	}
	
	public String getContactPerson(){
		return contactPerson;
	}
	
	public void setContactPerson(String contactPerson){
		this.contactPerson = contactPerson;
	}
	
	public String getContactTel(){
		return contactTel;
	}
	
	public void setContactTel(String contactTel){
		this.contactTel = contactTel;
	}
	
	public String getContactPhone(){
		return contactPhone;
	}
	
	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}
	
	public String getContactEmail(){
		return contactEmail;
	}
	
	public void setContactEmail(String contactEmail){
		this.contactEmail = contactEmail;
	}
	
	public String getIdCardNo(){
		return idCardNo;
	}
	
	public void setIdCardNo(String idCardNo){
		this.idCardNo = idCardNo;
	}
	
	public String getBizLicenseNo(){
		return bizLicenseNo;
	}
	
	public void setBizLicenseNo(String bizLicenseNo){
		this.bizLicenseNo = bizLicenseNo;
	}
	
	public String getOrganizationCode(){
		return organizationCode;
	}
	
	public void setOrganizationCode(String organizationCode){
		this.organizationCode = organizationCode;
	}
	
	public String getTaxRegistration(){
		return taxRegistration;
	}
	
	public void setTaxRegistration(String taxRegistration){
		this.taxRegistration = taxRegistration;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public Integer getCencorStatus(){
		return cencorStatus;
	}
	
	public void setCencorStatus(Integer cencorStatus){
		this.cencorStatus = cencorStatus;
	}
	
	public String getCencorId(){
		return cencorId;
	}
	
	public void setCencorId(String cencorId){
		this.cencorId = cencorId;
	}
	
	public Date getCencorTime(){
		return cencorTime;
	}
	
	public void setCencorTime(Date cencorTime){
		this.cencorTime = cencorTime;
	}
	
	public Date getCencorTimeStart(){
		return cencorTimeStart;
	}
	
	public void setCencorTimeStart(Date cencorTimeStart){
		this.cencorTimeStart = cencorTimeStart;
	}
	
	public Date getCencorTimeEnd(){
		return cencorTimeEnd;
	}
	
	public void setCencorTimeEnd(Date cencorTimeEnd){
		this.cencorTimeEnd = cencorTimeEnd;
	}	
	
	public String getCencorMemo(){
		return cencorMemo;
	}
	
	public void setCencorMemo(String cencorMemo){
		this.cencorMemo = cencorMemo;
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
		return "CarOwner ["
		 + "ownerId = " + ownerId + ", ownerName = " + ownerName + ", ownerFullName = " + ownerFullName + ", ownerType = " + ownerType
		 + ", contactPerson = " + contactPerson + ", contactTel = " + contactTel + ", contactPhone = " + contactPhone + ", contactEmail = " + contactEmail
		 + ", idCardNo = " + idCardNo + ", bizLicenseNo = " + bizLicenseNo + ", organizationCode = " + organizationCode + ", taxRegistration = " + taxRegistration
		 + ", isAvailable = " + isAvailable + ", cencorStatus = " + cencorStatus + ", cencorId = " + cencorId + ", cencorTime = " + cencorTime + ", cencorTimeStart = " + cencorTimeStart + ", cencorTimeEnd = " + cencorTimeEnd
		 + ", cencorMemo = " + cencorMemo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId
		+"]";
	}
}
