package cn.com.shopec.core.marketing.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.DepositZhimaReduction;

/**
 * 芝麻分减免押金表 数据库处理类
 */
public interface DepositZhimaReductionDao extends BaseDao<DepositZhimaReduction,String> {
	
	/**
	 *  得到已启用并且芝麻分小于等于入参最接近的那一条的数据
	 * @param zhimaScore
	 * @return
	 */
	public DepositZhimaReduction getClosestReductionByParameter(int zhimaScore);
}
