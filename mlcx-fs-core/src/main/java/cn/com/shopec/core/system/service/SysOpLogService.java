package cn.com.shopec.core.system.service;


import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysOpLog;


/**
 * SysOpLog 服务接口类
 */
public interface SysOpLogService extends BaseService{
	
	public PageFinder<SysOpLog> pageList(Query q);
	
	public ResultInfo<SysOpLog> add(SysOpLog sysOpLog,Operator operator);
	
	/**
	 * 根据id获得日志信息
	 * @param logId
	 * @return
	 */
	public SysOpLog detail(String logId);
}
