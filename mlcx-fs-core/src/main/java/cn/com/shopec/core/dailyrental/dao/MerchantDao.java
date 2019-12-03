package cn.com.shopec.core.dailyrental.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.dailyrental.model.Merchant;

/**
 * 租赁商家表 数据库处理类
 */
public interface MerchantDao extends BaseDao<Merchant,String> {
	
	public int updateForMgt(Merchant merchant);
}
