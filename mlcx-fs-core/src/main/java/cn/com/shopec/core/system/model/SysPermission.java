package cn.com.shopec.core.system.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;



/**
 * SysPermission 数据库实体类
 */
public class SysPermission extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//权限id
	private String permId;
	//权限名称
	private String permName;
	//权限点唯一编号
	private String permCode;
	//权限所属的功能模块名称
	private String moduleName;
	//权限类型（0、访问路径url，1、页面标签，默认0）
	private Integer permType;
	//权限资源（当perm_type为0时，此字段为系统某功能的url，当perm_type为1时，此字段为页面标签的编号）
	private String permResource;
	//备注
	private String memo;
	//是否可用（1、可用，0、不可用，默认1）
	private Integer isAvailable;
	//权限是否与菜单有关（1、有关，0、无关，且只有perm_type为0才有意义，默认0）
	private Integer isMenu;
	//是否显示在菜单中（1、显示，0、不显示，且仅当is_menu为1时才有效，默认0）
	private Integer isMenuShow;
	//菜单名称（仅当is_menu为1时有效）
	private String menuName;
	//层级，1为最小，一般当is_menu为1时才有意义）
	private Integer level;
	//父节点id
	private String parentId;
	//兄弟结点间的排序顺序，数字小的优先，0为最小值
	private Integer ranking;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	//操作人id（根据操作人类型会对应不同的表记录）
	private String operatorId;
	//图标名称
	private String iconName;
	
	@Override
	public String getPK(){
		return permId;
	}
	
	public String getPermId(){
		return permId;
	}
	
	public void setPermId(String permId){
		this.permId = permId;
	}
	public String getPermName(){
		return permName;
	}
	
	public void setPermName(String permName){
		this.permName = permName;
	}
	public String getPermCode(){
		return permCode;
	}
	
	public void setPermCode(String permCode){
		this.permCode = permCode;
	}
	public String getModuleName(){
		return moduleName;
	}
	
	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}
	public Integer getPermType(){
		return permType;
	}
	
	public void setPermType(Integer permType){
		this.permType = permType;
	}
	public String getPermResource(){
		return permResource;
	}
	
	public void setPermResource(String permResource){
		this.permResource = permResource;
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
	
	public Integer getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}

	public Integer getIsMenuShow() {
		return isMenuShow;
	}

	public void setIsMenuShow(Integer isMenuShow) {
		this.isMenuShow = isMenuShow;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
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

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
}
