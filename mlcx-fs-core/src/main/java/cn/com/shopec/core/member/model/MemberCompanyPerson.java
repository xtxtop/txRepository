package cn.com.shopec.core.member.model;
 
import cn.com.shopec.core.common.Entity;

/** 
 * 会员集团人员名单表 数据实体类
 */
public class MemberCompanyPerson extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//会员编号
	private String memberNo;
	//集团id
	private String companyId; 
	//手机号
	private String mobilePhone; 
	
	/*Auto generated methods start*/
	 
	public String getCompanyId(){
		return companyId;
	}
	
	public void setCompanyId(String companyId){
		this.companyId = companyId;
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
	  
}
