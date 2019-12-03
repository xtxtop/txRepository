package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 集团表 数据实体类
 */
public class Company extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//集团id
	private String companyId;
	//集团名称
	private String companyName;
	//城市ID（从数据字段来）
	private String cityId;
	//城市名称
	private String cityName;
	//联系人
	private String contactPerson;
	//联系电话
	private String contactTel;
	//邮箱
	private String email;
	//审核状态（1、未审核2、待审核3、已审核4、不通过）
	private Integer cencorStatus;
	//审核时间
	private Date cencorTime;
	//审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;	
	//状态（1、启用，2、停用）
	private Integer companyStatus;
	//启用停用备注
	private String companyMemo;
	//启用时间
	private Date enableTime;
	//启用时间 时间范围起（查询用）
	private Date enableTimeStart;
	//启用时间 时间范围止（查询用）
	private Date enableTimeEnd;	
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
	//操作人类型
	private Integer operatorType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return companyId;
	}
	
	public String getCompanyId(){
		return companyId;
	}
	
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}
	
	public String getCompanyName(){
		return companyName;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
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
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public Integer getCencorStatus(){
		return cencorStatus;
	}
	
	public void setCencorStatus(Integer cencorStatus){
		this.cencorStatus = cencorStatus;
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
	
	public Integer getCompanyStatus(){
		return companyStatus;
	}
	
	public void setCompanyStatus(Integer companyStatus){
		this.companyStatus = companyStatus;
	}
	
	public String getCompanyMemo(){
		return companyMemo;
	}
	
	public void setCompanyMemo(String companyMemo){
		this.companyMemo = companyMemo;
	}
	
	public Date getEnableTime(){
		return enableTime;
	}
	
	public void setEnableTime(Date enableTime){
		this.enableTime = enableTime;
	}
	
	public Date getEnableTimeStart(){
		return enableTimeStart;
	}
	
	public void setEnableTimeStart(Date enableTimeStart){
		this.enableTimeStart = enableTimeStart;
	}
	
	public Date getEnableTimeEnd(){
		return enableTimeEnd;
	}
	
	public void setEnableTimeEnd(Date enableTimeEnd){
		this.enableTimeEnd = enableTimeEnd;
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
		return "Company ["
		 + "companyId = " + companyId + ", companyName = " + companyName + ", cityId = " + cityId + ", cityName = " + cityName
		 + ", contactPerson = " + contactPerson + ", contactTel = " + contactTel + ", email = " + email + ", cencorStatus = " + cencorStatus
		 + ", cencorTime = " + cencorTime + ", cencorTimeStart = " + cencorTimeStart + ", cencorTimeEnd = " + cencorTimeEnd + ", companyStatus = " + companyStatus + ", companyMemo = " + companyMemo + ", enableTime = " + enableTime + ", enableTimeStart = " + enableTimeStart + ", enableTimeEnd = " + enableTimeEnd
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
