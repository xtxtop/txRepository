package cn.com.shopec.core.system.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;



/**
 * SysUserRoleRel 数据库实体类
 */
public class SysUserRoleRel extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//用户id（对应t_sys_user表主键）
	private String userId;
	//角色id（对应t_sys_role表主键）
	private String roleId;
	//创建时间
	private Date createTime;
	
	@Override
	public String getPK(){
		return roleId;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getRoleId(){
		return roleId;
	}
	
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
}
