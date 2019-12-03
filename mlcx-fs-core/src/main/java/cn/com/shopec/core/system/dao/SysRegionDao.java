package cn.com.shopec.core.system.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.SysRegion;

/**
 * SysRegion 数据库处理类
 */
public interface SysRegionDao extends BaseDao<SysRegion, String> {

	/**
	 * 三级联动 得到所有省
	 * 
	 * @return
	 */
	public List<SysRegion> getProvices();

	public List<SysRegion> getProvices2();

	public List<SysRegion> getProvices3();
	/**
	 * 获取省
	 * @param provinceName
	 * @return
	 */
	public List<SysRegion> getProvince(String provinceName);
	/**
	 * 得到所有市
	 * 
	 * @param id
	 * @return
	 */
	public List<SysRegion> getCitys(String proviceId);

	/**
	 * 得到所有县区
	 * 
	 * @param id
	 * @return
	 */
	public List<SysRegion> getCountrys(String cityId);

	/**
	 * 根据地址名得到地址id
	 * 
	 * @param addrRegion1Name
	 * @return
	 */
	public SysRegion getRegionIdByRegionName(String addrRegionName);

	/**
	 * 根据地址名得到地址id
	 * 
	 * @param addrRegion1Name
	 * @return
	 */
	public List<SysRegion> getListByRegionName(String addrRegionName);
}
