package cn.com.shopec.core.dailyrental.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.MerchantOrderConfirm;

/**
 * 商家确认账单 数据库处理类
 */
public interface MerchantOrderConfirmDao extends BaseDao<MerchantOrderConfirm,String> {
	
	List<MerchantOrderConfirm> getListByMerchantId(String merchantId);
	
	List<MerchantOrderConfirm>  pageListForMgt(Query q);
}
