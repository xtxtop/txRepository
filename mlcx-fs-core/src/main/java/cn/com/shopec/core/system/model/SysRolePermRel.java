package cn.com.shopec.core.system.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;



/**
 * SysRolePermRel 数据库实体类
 */
public class SysRolePermRel extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//角色id（对应t_sys_role表主键）
	private String roleId;
	//权限id（对应t_sys_permission表主键）
	private String permId;
	//创建时间
	private Date createTime;
	
	@Override
	public String getPK(){
		return permId;
	}
	
	public String getRoleId(){
		return roleId;
	}
	
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	public String getPermId(){
		return permId;
	}
	
	public void setPermId(String permId){
		this.permId = permId;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
}
