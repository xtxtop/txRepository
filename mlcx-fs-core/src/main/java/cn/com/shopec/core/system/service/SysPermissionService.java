package cn.com.shopec.core.system.service;



import java.util.List;
import java.util.Map;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysPermission;


/**
 * SysPermission 服务接口类
 */
public interface SysPermissionService extends BaseService{
	
	public PageFinder<SysPermission> pageList(Query q);
	
	public ResultInfo<SysPermission> addOrEditSysPermission(SysPermission sysPermission);	
	
	/**
	 * 根据id获得系统权限
	 * @param permissionId
	 * @return
	 */
	public SysPermission detail(String permissionId);

	public ResultInfo<SysPermission> delete(String permissionId);
	/**
	 * 批量删除
	 * @param sysPermissionIds
	 * @return
	 */
	public ResultInfo<SysPermission> batchDelete(String[] sysPermissionIds);
	
	public List<SysPermission> list(Query q);
	
	/**
	 * 查询对应角色的菜单资源
	 * @param roleId
	 * @return
	 */
	public List<Map<String, Object>> getMenuList(String[] roleId);
}
