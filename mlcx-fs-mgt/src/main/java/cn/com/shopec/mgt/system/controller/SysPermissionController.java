package cn.com.shopec.mgt.system.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysOpLog;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.service.SysPermissionService;
import cn.com.shopec.core.system.service.SysRolePermRelService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/sysPermission")
public class SysPermissionController extends BaseController {
	@Resource
	public SysPermissionService sysPermissionService;
	@Resource
	public SysRolePermRelService sysRolePermRelService;
	/*
	 * 显示系统权限列表页
	 */
	@RequestMapping("/mainPage")
	public String sysPermission() {
		return "system/sysPermission";
	}

	/*
	 * 添加或修改系统权限
	 */
	@RequestMapping("/addOrEditSysPermission")
	@ResponseBody
	public ResultInfo<SysPermission> addOrEditSysPermission(SysPermission sysPermission) {
		//操作人
		Operator op = getOperator();
		if(op != null){
			sysPermission.setOperatorId(op.getOperatorId());
			sysPermission.setOperatorType(op.getOperatorType());
		}
		ResultInfo<SysPermission> resultInfo = sysPermissionService.addOrEditSysPermission(sysPermission);
		//系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
		//SystemType
		//操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
		//OpType
		//模块名称(比如登陆，就写登陆。操作权限管理就写权限管理，具体到每一项。)
		//ModuleName
		//操作人
		//OperatorUserName
		//操作人类型与id
		//Operator
		//用户本次操作记录到系统日志表
		try{
			SysOpLog sysOpLog = new SysOpLog();
			sysOpLog.setSystemType(0);// 系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
			sysOpLog.setOpType("O");// 操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
			sysOpLog.setModuleName("权限");// 模块名称
			sysOpLog.setBizObjType("权限");// 业务对象（跟业务对象有关的操作时，可记录到该字段，如修改订单，则业务对象类型为订单，具体见编码见字典）
			sysOpLog.setBizObjId(sysPermission.getPermId());// 业务对象id（跟业务对象有关的操作，可记录该字段，对应具体业务对象的id值）
			sysOpLog.setOperatorUserName(getLoginSysUser().getUserName());// 操作人用户名（根据操作人类型会对应不同的用户名）
			sysOpLog.setOperatorId(getLoginSysUser().getUserId());// 操作人id（根据操作人类型会对应不同的表记录）
			sysOpLog.setOperatorType(1);// 操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
			sysOpLog.setLogMsg(getLoginSysUser().getUserName()+"对"+sysPermission.getPermName()+"权限进行添加（修改）操作");// 日志信息（简单信息）

			// 日志添加
			opLogService.add(sysOpLog, getOperator());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    //add(0, "U", "添加与修改"+sysPermission.getPermName()+"权限操作",getLoginSysUser().getUserName(),getOperator());//传参见以上注解
		return resultInfo;		
	}
	
	/*
	 * 删除系统权限
	 */
	@RequestMapping("deleteSysPermission")
	@ResponseBody
	public ResultInfo<SysPermission> deleteSysPermission(@RequestParam("permissionId") String permissionId) {
		return sysPermissionService.delete(permissionId);
	}
	
	/*
	 * 分页展示系统权限
	 */
	@RequestMapping("getSysPermissionList")
	@ResponseBody
	public PageFinder<SysPermission> getSysUserList(@ModelAttribute("sysPermission") SysPermission sysPermission,
			Query query) {				
		Query q = new Query(query.getPageNo(),query.getPageSize(),sysPermission);
		return sysPermissionService.pageList(q);
		
	}
	/*
	 * 批量删除系统权限
	 */
	@RequestMapping("batchDelete")
	@ResponseBody
	public ResultInfo<SysPermission> batchDelete(@RequestParam("sysPermissionIds") String [] sysPermissionIds) {
		return sysPermissionService.batchDelete(sysPermissionIds);
	}
	
	/*
	 * 全部权限（页面树形展示）
	 */
	@RequestMapping("list")
	@ResponseBody
	public List<Map<String, Object>> list(@ModelAttribute("sysPermission") SysPermission sysPermission,String type) {
		Query q = new Query(sysPermission);
		List<SysPermission> sysPermissionList = sysPermissionService.list(q);
		Collections.sort(sysPermissionList, new ComparatorSysPermission());
		List<Map<String, Object>> resultItemCatList=new ArrayList<Map<String, Object>>();
		if(!"1".equals(type)){
			//添加一级菜单
			Map<String, Object> view=new HashMap<String, Object>();
			view.put("id", "0");
			view.put("text", "一级菜单");
			view.put("ranking", "0");
			view.put("parent", "#");
			resultItemCatList.add(view);
		}
		for(SysPermission temp:sysPermissionList){
			Map<String, Object> viewSysPermission=new HashMap<String, Object>();
			viewSysPermission.put("id", temp.getPermId());
			viewSysPermission.put("text", temp.getPermName());
			viewSysPermission.put("ranking", temp.getRanking());
			if(temp.getParentId() == null || temp.getParentId().equals("0")){
				viewSysPermission.put("parent", "#");
			}else{
				viewSysPermission.put("parent", temp.getParentId());
			}
			resultItemCatList.add(viewSysPermission);
		}
		return resultItemCatList;
	}
	/*
	 * 根据角色id获取权限列表
	 * 
	 */
	@RequestMapping("getSysPermissionByRoleId")
	@ResponseBody
	public List<SysPermission> getSysPermissionByRoleId(String[] roleId) {
		return sysRolePermRelService.getAllRolePermissions(roleId);
	}
	/*
	 * 类型排序
	 */
	class ComparatorSysPermission implements Comparator<SysPermission> {

		@Override
		public int compare(SysPermission obj1, SysPermission obj2) {
			if (obj1.getRanking() != null && obj2.getRanking() != null) {
				return obj1.getRanking().compareTo(obj2.getRanking());
			} else {
				return 1;
			}
		}

	}
	
	/*
	 * 详情
	 */
	@RequestMapping("detail")
	@ResponseBody
	public  SysPermission detail(@RequestParam("permId") String permId) {
		return sysPermissionService.detail(permId);
	}
}
