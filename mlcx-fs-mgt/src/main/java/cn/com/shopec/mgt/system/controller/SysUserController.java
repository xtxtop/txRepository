package cn.com.shopec.mgt.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserCity;
import cn.com.shopec.core.system.model.SysUserRoleIds;
import cn.com.shopec.core.system.service.SysRoleService;
import cn.com.shopec.core.system.service.SysUserRoleRelService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("sysUser")
public class SysUserController extends BaseController {
	
	@Resource
	public SysUserService sysUserService;
	@Resource
	public SysRoleService sysRoleService;
	@Resource
	public SysUserRoleRelService sysUserRoleRelService;
	/*
	 * 显示系统用户列表页
	 */
	@RequestMapping("mainPage")
	public String sysUser() {
		return "system/sysUser";
	}
	/*
	 * 当前用户修改密码页面
	 */
	@RequestMapping("updatePassword")
	public String updatePassword(ModelMap model) {
		SysUser user=getLoginSysUser();
		if(user!=null){
			model.addAttribute("user", user);
			return "system/updatePassword";	
		}else{
			return "redirect:toLogin.do";
		}
		
	}
	/*
	 * 修改密码操作
	 */
	@RequestMapping("editPassword")
	@ResponseBody
	public ResultInfo<SysUser> editPassword(SysUser sysUser,String oldPassword) {
		ResultInfo<SysUser> resultInfo=new ResultInfo<SysUser>();
		if(getLoginSysUser()!=null){
			SysUser user=sysUserService.detail(sysUser.getUserId());
			if(user.getPassword().equals(ECMd5Utils.crypt(oldPassword))){
				user.setPassword(sysUser.getPassword());
				resultInfo=sysUserService.addOrEditSysUser(user);
			}else{
				resultInfo.setCode("3");
				resultInfo.setMsg("原密码输入不正确，请重新输入！");
			}
			
		}else{
			resultInfo.setCode("2");
			resultInfo.setMsg("登录超时，请重新登录！");
		}
		return resultInfo;
		
	}
	/*
	 * 添加或修改系统用户
	 */
	@RequestMapping("addOrEditSysUser")
	@ResponseBody
	public ResultInfo<SysUser> addOrEditSysUser(@ModelAttribute("sysUser")SysUserRoleIds sysUserRoleIds) {
		//操作人
		Operator op = getOperator();
		SysUser sysUser = new SysUser();
		sysUser.setUserId(sysUserRoleIds.getUserId());
		sysUser.setUserName(sysUserRoleIds.getUserName());
		sysUser.setRealName(sysUserRoleIds.getRealName());
		sysUser.setEmail(sysUserRoleIds.getEmail());
		sysUser.setIsAvailable(sysUserRoleIds.getIsAvailable());
		sysUser.setMemo(sysUserRoleIds.getMemo());
		sysUser.setPassword(sysUserRoleIds.getPassword());
		sysUser.setMobilePhone(sysUserRoleIds.getMobilePhone());
		sysUser.setTelPhone(sysUserRoleIds.getTelPhone());
		sysUser.setIdentification(sysUserRoleIds.getIdentification());
		
		String[] roleIds = sysUserRoleIds.getSysRole();
		List<SysRole> sysRoles = new ArrayList<SysRole>();
		for(int i=0;i<roleIds.length;i++){
			SysRole sysRole = new SysRole();
			sysRole.setRoleId(roleIds[i]);
			sysRoles.add(sysRole);
		}
		sysUser.setSysRole(sysRoles);
		if(sysUserRoleIds.getIdentification() != null && sysUserRoleIds.getIdentification() == 3 && sysUserRoleIds.getCityIds() != null){
			String[] cityIds = sysUserRoleIds.getCityIds();
			List<SysUserCity> sysUserCitys = new ArrayList<SysUserCity>();
			for(int i=0;i<cityIds.length;i++){
				SysUserCity sysUserCity = new SysUserCity();
				sysUserCity.setCityId(cityIds[i]);
				sysUserCitys.add(sysUserCity);
			}
			sysUser.setSysUserCitys(sysUserCitys);
		}
		
		if(op != null){
			sysUser.setOperatorId(op.getOperatorId());
			sysUser.setOperatorType(op.getOperatorType());
		}
		ResultInfo<SysUser> resultInfo = null;
		resultInfo = sysUserService.addOrEditSysUser(sysUser);		
		return resultInfo;		
	}
	
	/*
	 * 删除系统用户
	 */
	@RequestMapping("deleteSysUser")
	@ResponseBody
	public ResultInfo<SysUser> deleteSysUser(@RequestParam("userId") String userId) {
		return sysUserService.delete(userId,getOperator());
	}
	
	/*
	 * 分页展示系统用户
	 */
	@RequestMapping("getSysUserList")
	@ResponseBody
	public PageFinder<SysUser> getSysUserList(@ModelAttribute("sysUser") SysUser sysUser,
			Query query) {	
		Query q = new Query(query.getPageNo(),query.getPageSize(),sysUser);
		return sysUserService.pageList2(q);
		
	}
	/*
	 * 根据角色获取用户列表
	 */
	@RequestMapping("getSysUserListByRole")
	@ResponseBody
	public List<SysUser> getSysUserListByRole(@RequestParam("roleName") String roleName) {	
		SysRole sysRole=sysRoleService.getRoleByName(roleName);
		return sysUserRoleRelService.getAllUser(sysRole.getRoleId());
	}
	
	/*
	 * 批量删除系统参数组
	 */
	@RequestMapping("batchDelete")
	@ResponseBody
	public ResultInfo<SysUser> batchDelete(@RequestParam("sysUserIds") String [] sysUserIds) {
		return sysUserService.batchDelete(sysUserIds,getOperator());
	}
	
	/*
	 * 详情
	 */
	@RequestMapping("detail")
	@ResponseBody
	public SysUser detail(@RequestParam("userId") String userId) {
		return sysUserService.detail(userId);
	}
}
