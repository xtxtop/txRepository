package cn.com.shopec.core.system.dao;


import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.SysRole;


/**
 * SysRole 数据库处理类
 */
public interface SysRoleDao extends BaseDao<SysRole,String>{

	public SysRole getRoleByName(@Param("roleName") String roleName);
	
}
