package cn.com.shopec.core.system.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.model.SysRolePermRel;


/**
 * SysRolePermRel 数据库处理类
 */
public interface SysRolePermRelDao extends BaseDao<SysRolePermRel,String>{
	
	/**
	 * 查询对应角色下所有权限 
	 */
	public List<SysPermission> getAllRolePermissions(@Param("roleIds")String[] roleIds);
	/**
	 * 查询对应角色下所有菜单
	 */
	public List<SysPermission> getMenuList(@Param("roleIds") String[] roleId);

	/**
	 * 查询对应角色下所有Url
	 */
	public List<SysPermission> getMenuList2(@Param("roleIds") String[] roleId);
	
	/**
	 * 根据角色id删除角色权限关联表记录
	 * @param roleId
	 */
	public int deleteByRoleId(String roleId);
	/**
	 * 删除该权限关联的角色关系
	 * @param permId
	 */
	public int deleteByPermId(String permId);
	
	/**
	 * 根据模块名获取操作权限
	 * @param roleId
	 * @param record
	 * @return
	 */
	public List<SysPermission> selectHandleByModel(@Param("roleIds") String[] roleId, @Param("record") SysPermission record);

}
