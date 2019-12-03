package cn.com.shopec.core.system.dao;


import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysPermission;


/**
 * SysPermission 数据库处理类
 */
public interface SysPermissionDao extends BaseDao<SysPermission,String>{
	
	public Long count1(Query q);
	
	public List<SysPermission> pageList1(Query q);

    //根据二级菜单ID获取父ID
    List<String> getParentIdByChildrenIds(String[] ids);

}
