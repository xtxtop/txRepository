package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** 
 * 金豆消费明细返回实体类
 */
public class GoldBeansConsumerVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1626195977960269410L;
	//会员账户总金豆数
	private String glodBeansTotalNum;
	//长goldBeansNum所需天数
	private String days;
	//days天长goldBeansNum
	private String glodBeansNum;
	//明细对象
	private List<GoldBeansConsumerDetail> consumerDetailList;
	
	public String getGlodBeansTotalNum() {
		return glodBeansTotalNum;
	}
	public void setGlodBeansTotalNum(String glodBeansTotalNum) {
		this.glodBeansTotalNum = glodBeansTotalNum;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getGlodBeansNum() {
		return glodBeansNum;
	}
	public void setGlodBeansNum(String glodBeansNum) {
		this.glodBeansNum = glodBeansNum;
	}
	public List<GoldBeansConsumerDetail> getConsumerDetailList() {
		return consumerDetailList;
	}
	public void setConsumerDetailList(List<GoldBeansConsumerDetail> consumerDetailList) {
		this.consumerDetailList = consumerDetailList;
	}

}
