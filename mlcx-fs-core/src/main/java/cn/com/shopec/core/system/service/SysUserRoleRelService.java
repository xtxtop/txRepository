package cn.com.shopec.core.system.service;


import java.util.List;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserRoleRel;


/**
 * SysUserRoleRel 服务接口类
 */
public interface SysUserRoleRelService extends BaseService{	
	public ResultInfo<SysUserRoleRel> delete(String roleId,String userId);
	
	public ResultInfo<SysUserRoleRel> add(SysUserRoleRel sysUserRoleRel);
	
	public List<SysUserRoleRel> queryAll(Query q);
	public List<SysUserRoleRel> getByUserId(String userId);
	
	/**
	 * 根据角色id删除角色用户关联表记录
	 * @param roleId
	 */
	public ResultInfo<SysUserRoleRel> deleteByRoleId(String roleId);
	/**
	 * 根据用户id删除角色用户关联表记录
	 * @param roleId
	 */
	
	public ResultInfo<SysUserRoleRel> deleteByUserId(String userId);
	/**
	 * 根据用户id得到角色列表
	 * @param userId
	 */	
	public List<SysRole> getAllUserRole(String userId);
	/**
	 * 根据角色id获取用户列表
	 */
	public List<SysUser> getAllUser(String roleId);
}
