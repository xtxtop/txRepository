package cn.com.shopec.mapi.marketing.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 公告表表 数据实体类
 */
public class NoticeVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	
		//公告编号
		private String noticeNo;
		//公告类型（1、活动公告告）
		private Integer nioticeType;
		//公告名称
		private String nioticeName;
		//公告排名
		private Integer ranking;
		//公告图片
		private String nioticePicUrl;
		//跳转链接
		private String linkUrl;
		//内容
		private String text1;
		
		public String getNoticeNo() {
			return noticeNo;
		}
		public void setNoticeNo(String noticeNo) {
			this.noticeNo = noticeNo;
		}
		public Integer getNioticeType() {
			return nioticeType;
		}
		public void setNioticeType(Integer nioticeType) {
			this.nioticeType = nioticeType;
		}
		public String getNioticeName() {
			return nioticeName;
		}
		public void setNioticeName(String nioticeName) {
			this.nioticeName = nioticeName;
		}
		public Integer getRanking() {
			return ranking;
		}
		public void setRanking(Integer ranking) {
			this.ranking = ranking;
		}
		public String getNioticePicUrl() {
			return nioticePicUrl;
		}
		public void setNioticePicUrl(String nioticePicUrl) {
			this.nioticePicUrl = nioticePicUrl;
		}
		public String getLinkUrl() {
			return linkUrl;
		}
		public void setLinkUrl(String linkUrl) {
			this.linkUrl = linkUrl;
		}
		public String getText1() {
			return text1;
		}
		public void setText1(String text1) {
			this.text1 = text1;
		}
		@Override
		public String toString() {
			return "NoticeVo [noticeNo=" + noticeNo + ", nioticeType=" + nioticeType + ", nioticeName=" + nioticeName
					+ ", ranking=" + ranking + ", nioticePicUrl=" + nioticePicUrl + ", linkUrl=" + linkUrl + ", text1="
					+ text1 + "]";
		}
	
		
		
		
	
	
	
	
	
}
