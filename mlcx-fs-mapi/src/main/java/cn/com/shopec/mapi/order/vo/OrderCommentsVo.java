package cn.com.shopec.mapi.order.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * OrderComments 数据实体类
 */
public class OrderCommentsVo{
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//每一星级的提示头显示内容
	private String titleContent;
	//评分项1
	private String scoreItem1;
	//评分项2
	private String scoreItem2;
	//评分项3）
	private String scoreItem3;
	//评分项4
	private String scoreItem4;
	//评分项5（预留）
	private String scoreItem5;
	//评分项6（预留）
	private String scoreItem6;
	
	//评分项1id
	private String scoreItem1Id;
	//评分项2id
	private String scoreItem2Id;
	//评分项3id
	private String scoreItem3Id;
	//评分项4）id
	private String scoreItem4Id;
	//评分项5id（预留）
	private String scoreItem5Id;
	//评分项6id（预留）
	private String scoreItem6Id;
	//综合评分
	private String score;
	//评语内容
	private String content;

	//其实说法
	private String remarks;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public String getTitleContent() {
		return titleContent;
	}

	public void setTitleContent(String titleContent) {
		this.titleContent = titleContent;
	}

	public String getScoreItem1() {
		return scoreItem1;
	}

	public void setScoreItem1(String scoreItem1) {
		this.scoreItem1 = scoreItem1;
	}

	public String getScoreItem2() {
		return scoreItem2;
	}

	public void setScoreItem2(String scoreItem2) {
		this.scoreItem2 = scoreItem2;
	}

	public String getScoreItem3() {
		return scoreItem3;
	}

	public void setScoreItem3(String scoreItem3) {
		this.scoreItem3 = scoreItem3;
	}

	public String getScoreItem4() {
		return scoreItem4;
	}

	public void setScoreItem4(String scoreItem4) {
		this.scoreItem4 = scoreItem4;
	}

	public String getScoreItem5() {
		return scoreItem5;
	}

	public void setScoreItem5(String scoreItem5) {
		this.scoreItem5 = scoreItem5;
	}

	public String getScoreItem6() {
		return scoreItem6;
	}

	public void setScoreItem6(String scoreItem6) {
		this.scoreItem6 = scoreItem6;
	}

	public String getScoreItem1Id() {
		return scoreItem1Id;
	}

	public void setScoreItem1Id(String scoreItem1Id) {
		this.scoreItem1Id = scoreItem1Id;
	}

	public String getScoreItem2Id() {
		return scoreItem2Id;
	}

	public void setScoreItem2Id(String scoreItem2Id) {
		this.scoreItem2Id = scoreItem2Id;
	}

	public String getScoreItem3Id() {
		return scoreItem3Id;
	}

	public void setScoreItem3Id(String scoreItem3Id) {
		this.scoreItem3Id = scoreItem3Id;
	}

	public String getScoreItem4Id() {
		return scoreItem4Id;
	}

	public void setScoreItem4Id(String scoreItem4Id) {
		this.scoreItem4Id = scoreItem4Id;
	}

	public String getScoreItem5Id() {
		return scoreItem5Id;
	}

	public void setScoreItem5Id(String scoreItem5Id) {
		this.scoreItem5Id = scoreItem5Id;
	}

	public String getScoreItem6Id() {
		return scoreItem6Id;
	}

	public void setScoreItem6Id(String scoreItem6Id) {
		this.scoreItem6Id = scoreItem6Id;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "OrderCommentsVo [titleContent=" + titleContent + ", scoreItem1=" + scoreItem1 + ", scoreItem2="
				+ scoreItem2 + ", scoreItem3=" + scoreItem3 + ", scoreItem4=" + scoreItem4 + ", scoreItem5="
				+ scoreItem5 + ", scoreItem6=" + scoreItem6 + ", scoreItem1Id=" + scoreItem1Id + ", scoreItem2Id="
				+ scoreItem2Id + ", scoreItem3Id=" + scoreItem3Id + ", scoreItem4Id=" + scoreItem4Id + ", scoreItem5Id="
				+ scoreItem5Id + ", scoreItem6Id=" + scoreItem6Id + ", score=" + score + ", content=" + content
				+ ", remarks=" + remarks + "]";
	}

}
