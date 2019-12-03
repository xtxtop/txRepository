package cn.com.shopec.core.system.dao;


import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserRoleRel;


/**
 * SysUserRoleRel 数据库处理类
 */
public interface SysUserRoleRelDao extends BaseDao<SysUserRoleRel,String>{
	
	/**
	 * 删除对象(组合主键)
	 * @param roleId,userId
	 */
	public int delete(String roleId,String userId);
	
	/**
	 * 得到某个对象(组合主键)
	 * @param roleId,userId
	 */
	public SysUserRoleRel get(String roleId,String userId);
	
	/**
	 * 得到某个对象
	 * @param userId
	 */
	public List<SysUserRoleRel> getByUserId(String userId);
	
	/**
	 * 根据角色id删除角色用户关联表记录
	 * @param roleId
	 */
	public int deleteByRoleId(String roleId);
	
	/**
	 * 根据用户id删除角色用户关联表记录
	 * @param userId
	 */
	public int deleteByUserId(String userId);
	
	/**
	 * 根据用户id得到角色列表
	 * @param userId
	 */	
	public List<SysRole> getAllUserRole(String userId);
	/**
	 * 根据角色id获取用户列表*/
	public List<SysUser> getAllUser(String roleId);
	
}
