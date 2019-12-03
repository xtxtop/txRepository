package cn.com.shopec.mapi.myWallet.vo;

import java.util.List;

import cn.com.shopec.core.ml.vo.AccountRecordVo;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * 充值记录模型
 * 
 * @author Administrator
 *
 */
public class CAccRecVo {
	/*// 充值记录列表顶部轮播图
	private List<BannerVo> bannerList;*/
	// 充值记录列表数据
	private AccountRecordVo accountRecordVoList;

/*	public List<BannerVo> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<BannerVo> bannerList) {
		this.bannerList = bannerList;
	}*/

	public AccountRecordVo getAccountRecordVoList() {
		return accountRecordVoList;
	}

	public void setAccountRecordVoList(AccountRecordVo accountRecordVoList) {
		this.accountRecordVoList = accountRecordVoList;
	}

}
