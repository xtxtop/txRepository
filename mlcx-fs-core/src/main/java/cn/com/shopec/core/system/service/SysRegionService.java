package cn.com.shopec.core.system.service;

import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysRegion;

/**
 * SysRegion 服务接口类
 */
public interface SysRegionService extends BaseService {

	public PageFinder<SysRegion> pageList(Query q);

	public ResultInfo<SysRegion> addOrEditSysRegion(SysRegion sysRegion);

	public ResultInfo<SysRegion> delete(String regionId, Operator operator);

	/**
	 * 系统地区信息
	 * 
	 * @param regionId
	 * @return
	 */
	public SysRegion detail(String regionId);

	/**
	 * 批量删除
	 * 
	 * @param sysUserIds
	 * @return
	 */
	public ResultInfo<SysRegion> batchDelete(String[] sysRegionIds, Operator operator);

	public List<SysRegion> list(Query q);

	/**
	 * 地区树
	 * 
	 * @param sysUserIds
	 * @return
	 */
	public List<Map<String, Object>> getSysRegionTree(Query q, String type);

	/**
	 * 三级联动 得到所有省
	 * 
	 * @return
	 */
	public List<SysRegion> getProvices();

	public List<SysRegion> getProvices2();

	public List<SysRegion> getProvices3();

	/**
	 * 得到所有市
	 * 
	 * @param id
	 * @return
	 */
	public List<SysRegion> getCitys(String id);
	/**
	 * 获取省
	 * @param provinceName
	 * @return
	 */
	public List<SysRegion> getProvince(String provinceName);
	/**
	 * 得到所有县区
	 * 
	 * @param id
	 * @return
	 */
	public List<SysRegion> getCountrys(String id);

	/**
	 * 根据地址名得到地址id
	 * 
	 * @param addrRegion1Name
	 * @return
	 */
	public SysRegion getRegionIdByRegionName(String addrRegionName);

	public List<Map<String, Object>> getMerchantSysRegionTree(Query q);
}
