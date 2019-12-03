package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/** 
 * Member 数据实体类
 */
public class MemberPointVo implements Serializable {
	
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4867863665810166774L;
	//会员编号
	private String memberNo;
	//会员当前积分
	private int memberPoint;
	//当前等级所需积分
	private int nowLevelPoint;
	//下一等级所需积分
	private int nextLevelPoint;
	//当前等级名称
	private String levelName;
	//下一个等级名称
	private String nexeLevelName;
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public int getMemberPoint() {
		return memberPoint;
	}
	public void setMemberPoint(int memberPoint) {
		this.memberPoint = memberPoint;
	}
	public int getNowLevelPoint() {
		return nowLevelPoint;
	}
	public void setNowLevelPoint(int nowLevelPoint) {
		this.nowLevelPoint = nowLevelPoint;
	}
	public int getNextLevelPoint() {
		return nextLevelPoint;
	}
	public void setNextLevelPoint(int nextLevelPoint) {
		this.nextLevelPoint = nextLevelPoint;
	}
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	@Override
	public String toString() {
		return "MemberPointVo [memberNo=" + memberNo + ", memberPoint=" + memberPoint + ", nowLevelPoint="
				+ nowLevelPoint + ", nextLevelPoint=" + nextLevelPoint + "]";
	}
	public String getNexeLevelName() {
		return nexeLevelName;
	}
	public void setNexeLevelName(String nexeLevelName) {
		this.nexeLevelName = nexeLevelName;
	}
	
	
}
