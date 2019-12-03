package cn.com.shopec.mapi.myWallet.vo;

import java.util.List;

import cn.com.shopec.core.ml.vo.AdvertTextVo;

public class WalletVo {
	// 充电余额
	private Double chargingBalance;
	// 停车余额
	private Double stopBalance;
	// 滚动文字List
	public List<AdvertTextVo> adList;

	public Double getChargingBalance() {
		return chargingBalance;
	}

	public void setChargingBalance(Double chargingBalance) {
		this.chargingBalance = chargingBalance;
	}

	public Double getStopBalance() {
		return stopBalance;
	}

	public void setStopBalance(Double stopBalance) {
		this.stopBalance = stopBalance;
	}

	public List<AdvertTextVo> getAdList() {
		return adList;
	}

	public void setAdList(List<AdvertTextVo> adList) {
		this.adList = adList;
	}

}
