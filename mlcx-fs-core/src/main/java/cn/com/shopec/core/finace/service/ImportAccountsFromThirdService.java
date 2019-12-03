package cn.com.shopec.core.finace.service;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.finace.model.FinaceTest;

/**
 * 账务倒入 服务接口类
 */
public interface ImportAccountsFromThirdService extends BaseService {

	/**
	 * 根据时间，从支付宝倒入账单
	 * @param time 需要倒入的时间的账单日期
	 * @return
	 */
	public ResultInfo<FinaceTest> importFromAlipay(String time);
	
	/**
	 * 根据时间，从微信倒入账单
	 * @param time 需要倒入的时间的账单日期
	 * @return
	 */
	public ResultInfo<FinaceTest> importFromWChart(String time);

}
