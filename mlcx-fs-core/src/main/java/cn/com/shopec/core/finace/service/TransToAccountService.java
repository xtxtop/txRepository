package cn.com.shopec.core.finace.service;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.TransToAccount;

/**
 * 押金退款表 服务接口类
 */
public interface TransToAccountService extends BaseService {

	
	/**
	 * 根据主键，查询并返回一个TransToAccount对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public TransToAccount getTransToAccount(String id);


	/**
	 * 新增一条TransToAccount记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param transToAccount
	 *            新增的TransToAccount数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<TransToAccount> addTransToAccount(TransToAccount transToAccount, Operator operator);

	/**
	 * 根据主键，更新一条TransToAccount记录
	 * 
	 * @param transToAccount
	 *            更新的TransToAccount数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<TransToAccount> updateTransToAccount(TransToAccount transToAccount, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为DepositRefund对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(DepositRefund obj);

	

}
