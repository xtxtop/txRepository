package cn.com.shopec.core.system.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;



/**
 * SysRole 数据库实体类
 */
public class SysRole extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//角色id
	private String roleId;
	//角色名称
	private String roleName;
	//备注
	private String memo;
	//账户是否可用（1、可用，0、不可用，默认1）
	private Integer isAvailable;
	//是否已删除（逻辑删除，1、已删除，0、未删除，默认0）
	private Integer isDeleted;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	//操作人id（根据操作人类型会对应不同的表记录）
	private String operatorId;
	//权限集合id
	private String[] permissionIds;
	//权限集合
	private List<SysPermission> perList;
	
	@Override
	public String getPK(){
		return roleId;
	}
	
	public String getRoleId(){
		return roleId;
	}
	
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	public String getRoleName(){
		return roleName;
	}
	
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}

	public String[] getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	public List<SysPermission> getPerList() {
		return perList;
	}

	public void setPerList(List<SysPermission> perList) {
		this.perList = perList;
	}
	
}
