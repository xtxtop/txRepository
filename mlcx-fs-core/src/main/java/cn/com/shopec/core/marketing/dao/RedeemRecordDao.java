package cn.com.shopec.core.marketing.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.RedeemRecord;


/**
 * RedeemCode 数据库处理类
 */
public interface RedeemRecordDao extends BaseDao<RedeemRecord,String> {

	List<RedeemRecord> getForRedeem(String redCode, String memberNo);

	
	
}
