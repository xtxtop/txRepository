package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CReporting;
import cn.com.shopec.core.ml.vo.ReportingVo;

/**
 * CReporting 数据库处理类
 */
public interface CReportingDao extends BaseDao<CReporting,String> {
	//违停 列表
	List<ReportingVo> getReporting(Query q);
	long getReportingCount(Query q);
	//详情
	ReportingVo getReportingNo(String id);
}
