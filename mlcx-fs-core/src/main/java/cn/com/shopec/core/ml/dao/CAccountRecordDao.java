package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CAccountRecord;
import cn.com.shopec.core.ml.vo.AccountRecordVo;

/**
 * CAccountRecord 数据库处理类
 */
public interface CAccountRecordDao extends BaseDao<CAccountRecord, String> {
	// 获取充值记录
	List<CAccountRecord> getAccountRecord(Query q);

	Long getAccountRecordCount(Query q);

	Long getAccountRecord_No(String id);

	/**
	 * 某会员的全部充值记录
	 * 
	 * @param memberNo
	 * @return
	 */
	List<AccountRecordVo> getAccoutRecordByMemberNo(Query q);
}
