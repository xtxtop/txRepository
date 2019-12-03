package cn.com.shopec.mapi.marketing.vo;

import java.util.List;

public class InformVo {
	//信息的list集合
	private List<SendMessageVo> sendMessageList;
	//广告的list集合
	private List<AdvertVo> advertList;
	public List<SendMessageVo> getSendMessageList() {
		return sendMessageList;
	}
	public void setSendMessageList(List<SendMessageVo> sendMessageList) {
		this.sendMessageList = sendMessageList;
	}
	public List<AdvertVo> getAdvertList() {
		return advertList;
	}
	public void setAdvertList(List<AdvertVo> advertList) {
		this.advertList = advertList;
	}
	@Override
	public String toString() {
		return "InformVo [sendMessageList=" + sendMessageList + ", advertList=" + advertList + "]";
	}
	
}
