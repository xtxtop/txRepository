package cn.com.shopec.mgt.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.service.SysRoleService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {
	@Resource
	public SysRoleService sysRoleService;
	
	/*
	 * 显示系统角色列表页
	 */
	@RequestMapping("/mainPage")
	public String sysRole() {
		return "system/sysRole";
	}

	/*
	 * 添加或修改系统用户
	 */
	@RequestMapping("/addOrEditSysRole")
	@ResponseBody
	public synchronized ResultInfo<SysRole> addOrEditSysRole(SysRole sysRole) {
        ResultInfo<SysRole> resultInfo = new ResultInfo<>();
		if (sysRole.getRoleId() == null || "".equals(sysRole.getRoleId())) {
			SysRole querySysRole = sysRoleService.getRoleByName(sysRole.getRoleName());
			if(querySysRole != null){
	            resultInfo.setMsg("角色已经存在");
	            resultInfo.setCode(Constant.FAIL);
	    		return resultInfo;
			}
		} 
		//操作人
		Operator op = getOperator();
		if(op != null){
			sysRole.setOperatorId(op.getOperatorId());
			sysRole.setOperatorType(op.getOperatorType());
		}
        try{
		    sysRoleService.addOrUpdateSysRole(sysRole);
            resultInfo.setCode(Constant.SECCUESS);
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
            resultInfo.setCode(Constant.FAIL);
        }
		return resultInfo;
	}
	
	/*
	 * 删除系统角色
	 */
	@RequestMapping("deleteSysRole")
	@ResponseBody
	public ResultInfo<SysRole> deleteSysRole(@RequestParam("roleId") String roleId) {
		return sysRoleService.delete(roleId,getOperator());
	}
	
	/*
	 * 分页展示系统角色
	 */
	@RequestMapping("getSysRoleList")
	@ResponseBody
	public PageFinder<SysRole> getSysRoleList(@ModelAttribute("SysRole") SysRole sysRole,
			Query query) {				
		Query q = new Query(query.getPageNo(),query.getPageSize(),sysRole);
		return sysRoleService.pageList(q);
		
	}
	
	/*
	 * 查询角色列表
	 */
	@RequestMapping("queryAllSysRole")
	@ResponseBody
	public List<SysRole> queryAllSysRole() {
		SysRole sysRole = new  SysRole();
		sysRole.setIsDeleted(0);
		Query q = new Query(sysRole);
		return sysRoleService.queryAll(q);
	}
	
	/*
	 * 批量删除系统角色
	 */
	@RequestMapping("batchDelete")
	@ResponseBody
	public ResultInfo<SysRole> batchDelete(@RequestParam("sysRoleIds") String [] sysRoleIds) {
		return sysRoleService.batchDelete(sysRoleIds,getOperator());
	}
	
	/*
	 * 详情
	 */
	@RequestMapping("detail")
	@ResponseBody
	public SysRole detail(@RequestParam("roleId") String roleId) {
		return sysRoleService.detail(roleId);
	}
}
