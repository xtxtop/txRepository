package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;
import java.util.List;

/** 
 * Member 数据实体类
 */
public class MemberCensorVo implements Serializable {
	
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4867863665810166774L;
	//会员编号
	private String memberNo;
	//姓名
	private String memberName;
	//审核状态（认证状态）（0、未审核/未认证，  1、已审核/已认证，    2、待审核/待认证，    3、未通过                          默认0）
	private Integer censorStatus;
	//身份证号
	private String idCard;
	//驾驶证照片url1
	private String driverLicensePhotoUrl1;
	//身份证照片url
	private String idCardPhotoUrl;
	//审核备注
	private String cencorMemo;
	//是否需要其他证件认证
	private  Integer isPapers;
	//用户芝麻信用分
	private  Integer creditScore;
	//其他证件名称集合
	private List<PapersVo> papers; 
	public String getCencorMemo() {
		return cencorMemo;
	}
	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getCensorStatus() {
		return censorStatus;
	}
	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
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
	public String getIdCardPhotoUrl() {
		return idCardPhotoUrl;
	}
	public void setIdCardPhotoUrl(String idCardPhotoUrl) {
		this.idCardPhotoUrl = idCardPhotoUrl;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "MemberCensorVo [memberNo=" + memberNo + ", memberName=" + memberName + ", censorStatus=" + censorStatus
				+ ", idCard=" + idCard + ", driverLicensePhotoUrl1=" + driverLicensePhotoUrl1 + ", idCardPhotoUrl="
				+ idCardPhotoUrl + "]";
	}
	public Integer getIsPapers() {
		return isPapers;
	}
	public void setIsPapers(Integer isPapers) {
		this.isPapers = isPapers;
	}
	public List<PapersVo> getPapers() {
		return papers;
	}
	public void setPapers(List<PapersVo> papers) {
		this.papers = papers;
	}
	public Integer getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}
}
