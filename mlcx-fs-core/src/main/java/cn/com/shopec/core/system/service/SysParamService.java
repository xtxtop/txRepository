package cn.com.shopec.core.system.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysParam;

/**
 * SysParam 服务接口类
 */
public interface SysParamService extends BaseService {

	public ResultInfo<SysParam> addOrEditSysParam(SysParam sysParam);

	public ResultInfo<SysParam> delete(String sysParamId, Operator operator);

	/**
	 * 根据id获得系统参数
	 * 
	 * @param sysParamId
	 * @return
	 */
	public SysParam detail(String sysParamId);

	public PageFinder<SysParam> pageList(Query q);

	/**
	 * 批量删除
	 * 
	 * @param sysUserIds
	 * @return
	 */
	public ResultInfo<SysParam> batchDelete(String[] sysParamIds, Operator operator);

	/**
	 * 根据key得到参数
	 */
	public SysParam getByParamKey(String key);
	
	/**
	 * 根据key，取得参数的值
	 * @param key
	 * @return
	 */
	public String getParamValueByParamKey(String key);

	/**
	 * 根据参数名查找一条信息
	 */
	public SysParam getByParamName(String paramNames);
	
	public String getVersion();
	

	/**
	 * 获取参数
	 * 
	 * @param paramId
	 * @return
	 */
	public ResultInfo<SysParam> getParam(String paramId);

	/**
	 * 获取参数列表
	 * 
	 * @param q
	 * @return
	 */
	public ResultInfo<List<SysParam>> getParamList(Query q);

	/**
	 * 更新参数值
	 * 
	 * @param sysParameter
	 * @return
	 */
	public ResultInfo<SysParam> updateParamValue(SysParam sysParam);
}
