package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 集团人员名单表 数据实体类
 */
public class CompanyPerson extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//人员id
	private String id;
	//姓名
	private String name;
	//城市id（具体来自数据字典表）
	private String cityId;
	//城市名称
	private String cityName;
	//集团id
	private String companyId;
	//集团名称
	private String companyName;
	//人员类型（1-员工，0-非员工）
	private Integer personType;
	//会员编号
	private String memberNo;
	//手机号
	private String mobilePhone;
	//身份证号
	private String idCardNo;
	//导入时间
	private Date importTime;
	//导入时间 时间范围起（查询用）
	private Date importTimeStart;
	//导入时间 时间范围止（查询用）
	private Date importTimeEnd;	
	//注册状态（0、未注册，1、已注册，默认0）
	private Integer registerStatus;
	//是否删除（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
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
		return id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
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
	
	public Integer getPersonType(){
		return personType;
	}
	
	public void setPersonType(Integer personType){
		this.personType = personType;
	}
	
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public String getIdCardNo(){
		return idCardNo;
	}
	
	public void setIdCardNo(String idCardNo){
		this.idCardNo = idCardNo;
	}
	
	public Date getImportTime(){
		return importTime;
	}
	
	public void setImportTime(Date importTime){
		this.importTime = importTime;
	}
	
	public Date getImportTimeStart(){
		return importTimeStart;
	}
	
	public void setImportTimeStart(Date importTimeStart){
		this.importTimeStart = importTimeStart;
	}
	
	public Date getImportTimeEnd(){
		return importTimeEnd;
	}
	
	public void setImportTimeEnd(Date importTimeEnd){
		this.importTimeEnd = importTimeEnd;
	}	
	
	public Integer getRegisterStatus(){
		return registerStatus;
	}
	
	public void setRegisterStatus(Integer registerStatus){
		this.registerStatus = registerStatus;
	}
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
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
	@Override
	public String toString() {
		return "CompanyPerson [id=" + id + ", name=" + name + ", cityId=" + cityId + ", cityName=" + cityName
				+ ", companyId=" + companyId + ", companyName=" + companyName + ", personType=" + personType
				+ ", memberNo=" + memberNo + ", mobilePhone=" + mobilePhone + ", idCardNo=" + idCardNo + ", importTime="
				+ importTime + ", importTimeStart=" + importTimeStart + ", importTimeEnd=" + importTimeEnd
				+ ", registerStatus=" + registerStatus + ", isDeleted=" + isDeleted + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorType=" + operatorType + ", operatorId=" + operatorId + "]";
	}
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	
}
