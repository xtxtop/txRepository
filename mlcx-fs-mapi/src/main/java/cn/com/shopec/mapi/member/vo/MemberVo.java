package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/** 
 * Member 数据实体类
 */
public class MemberVo implements Serializable {
	
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4867863665810166774L;
	//会员编号
	private String memberNo;
	//头像照片url
	private String memberPhotoUrl;
	//姓名
	private String memberName;
	//昵称
	private String memberNick;
	//手机号
	private String mobilePhone;
	//套餐总时长
	private Integer totalPackageMinutes;
	//套餐总可用时长
	private Integer totalPackageAvailableMinutes;
	//会员级别id
	private String memberLevelId;
	//审核状态（认证状态）（0、未审核/未认证，  1、已审核/已认证，    2、待审核/待认证，    3、未通过                          默认0）
	private Integer censorStatus;
	//押金状态
	private Integer depositStatus;
	//会员级别名称
	private String levelName;
	//下一等级所需积分
	private int nextLevelPoint;
	//会员下个级别名称
	private String nexLevelName;
	//token
	private String token;
	//剩余押金
	private Double depositAmount;
	//会员当前积分
	private Integer memberPoint;
	//可用套餐数量
	private Integer  packageCount;
	//身份证号
	private String idCard;
	//驾驶证照片url1
	private String driverLicensePhotoUrl1;
	//驾驶证照片url2
	private String driverLicensePhotoUrl2;
	//身份证照片url
	private String idCardPhotoUrl;
	//我的余额
	private Double memberBalance;
	
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberPhotoUrl() {
		return memberPhotoUrl;
	}
	public void setMemberPhotoUrl(String memberPhotoUrl) {
		this.memberPhotoUrl = memberPhotoUrl;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberNick() {
		return memberNick;
	}
	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getTotalPackageMinutes() {
		return totalPackageMinutes;
	}
	public void setTotalPackageMinutes(Integer totalPackageMinutes) {
		this.totalPackageMinutes = totalPackageMinutes;
	}
	public Integer getTotalPackageAvailableMinutes() {
		return totalPackageAvailableMinutes;
	}
	public void setTotalPackageAvailableMinutes(Integer totalPackageAvailableMinutes) {
		this.totalPackageAvailableMinutes = totalPackageAvailableMinutes;
	}
	public String getMemberLevelId() {
		return memberLevelId;
	}
	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}
	public Integer getCensorStatus() {
		return censorStatus;
	}
	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}
	public Integer getDepositStatus() {
		return depositStatus;
	}
	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	@Override
	public String toString() {
		return "MemberVo [memberNo=" + memberNo + ", memberPhotoUrl=" + memberPhotoUrl + ", memberName=" + memberName
				+ ", memberNick=" + memberNick + ", mobilePhone=" + mobilePhone + ", totalPackageMinutes="
				+ totalPackageMinutes + ", totalPackageAvailableMinutes=" + totalPackageAvailableMinutes
				+ ", memberLevelId=" + memberLevelId + ", censorStatus=" + censorStatus + ", depositStatus=" + depositStatus + "]";
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getNexLevelName() {
		return nexLevelName;
	}
	public void setNexLevelName(String nexLevelName) {
		this.nexLevelName = nexLevelName;
	}
	public int getNextLevelPoint() {
		return nextLevelPoint;
	}
	public void setNextLevelPoint(int nextLevelPoint) {
		this.nextLevelPoint = nextLevelPoint;
	}
	public Double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Integer getMemberPoint() {
		return memberPoint;
	}
	public void setMemberPoint(Integer memberPoint) {
		this.memberPoint = memberPoint;
	}
	public Integer getPackageCount() {
		return packageCount;
	}
	public void setPackageCount(Integer packageCount) {
		this.packageCount = packageCount;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getDriverLicensePhotoUrl1() {
		return driverLicensePhotoUrl1;
	}
	public void setDriverLicensePhotoUrl1(String driverLicensePhotoUrl1) {
		this.driverLicensePhotoUrl1 = driverLicensePhotoUrl1;
	}
	public String getDriverLicensePhotoUrl2() {
		return driverLicensePhotoUrl2;
	}
	public void setDriverLicensePhotoUrl2(String driverLicensePhotoUrl2) {
		this.driverLicensePhotoUrl2 = driverLicensePhotoUrl2;
	}
	public String getIdCardPhotoUrl() {
		return idCardPhotoUrl;
	}
	public void setIdCardPhotoUrl(String idCardPhotoUrl) {
		this.idCardPhotoUrl = idCardPhotoUrl;
	}
	public Double getMemberBalance() {
		return memberBalance;
	}
	public void setMemberBalance(Double memberBalance) {
		this.memberBalance = memberBalance;
	}
	
	
	
}
