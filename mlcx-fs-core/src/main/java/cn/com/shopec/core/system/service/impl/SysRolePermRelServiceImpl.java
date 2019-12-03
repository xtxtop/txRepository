package cn.com.shopec.core.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECSessionUtil;
import cn.com.shopec.core.system.dao.SysRolePermRelDao;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysRolePermRel;
import cn.com.shopec.core.system.service.SysRolePermRelService;




/**
 * SysRolePermRel 服务实现类
 */
@Service
public class SysRolePermRelServiceImpl implements SysRolePermRelService{
	
	@Resource
	private SysRolePermRelDao sysRolePermRelDao;

	private ResultInfo<SysRolePermRel> resultInfo = new ResultInfo<SysRolePermRel>();
	
	@Override
	@Transactional
	public ResultInfo<SysRolePermRel> deleteByRoleId(String roleIds) {

		try{
			int count=sysRolePermRelDao.deleteByRoleId(roleIds);
			if(count==1){
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未找到此角色，删除失败！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;

	}
	
	@Override
	@Transactional
	public ResultInfo<SysRolePermRel> add(SysRolePermRel sysRolePermRel) {
		try {
			if (sysRolePermRel != null) {
				sysRolePermRel.setCreateTime(new Date());
				sysRolePermRelDao.add(sysRolePermRel);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sysRolePermRel);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("系统用户关联角色不能为空！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}

	@Override
	public ResultInfo<SysRolePermRel> deleteByPermId(String permId) {
		try{
			int count=sysRolePermRelDao.deleteByPermId(permId);
			if(count==1){
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未找到此权限，删除失败！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}
	@Override
	public List<SysPermission> getAllRolePermissions(String[] roleIds){
		return sysRolePermRelDao.getAllRolePermissions(roleIds);
	};
	
	@Override
	public List<SysPermission> getMenuList(String[] roleId){
		return sysRolePermRelDao.getMenuList(roleId);
	}

	@Override
	public List<SysPermission> getMenuList2(String[] roleId){
		return sysRolePermRelDao.getMenuList2(roleId);
	}
	
	@Override
	public String getHandleByModel(String moduleName) throws Exception {
		HttpSession httpSession = ECSessionUtil.getSession();
		List<SysRole> role = (List<SysRole>)httpSession.getAttribute("SESSION_KEY_OF_LOGIN_SYS_ROLE");
		String[] roleIds = new String[role.size()];
		for(int i=0; i<role.size(); i++){
			roleIds[i] = role.get(i).getRoleId();
		}
		SysPermission record = new SysPermission();
		record.setModuleName(moduleName);
		List<SysPermission> sysPermissions = sysRolePermRelDao.selectHandleByModel(roleIds, record);
		String handles = "";
		if (sysPermissions != null && sysPermissions.size() > 0) {
			for (SysPermission sysPermission : sysPermissions) {
				handles = sysPermission.getMemo() + "+" + handles;
			}
			handles = handles.substring(0, handles.length()-1);
		}
		return handles;
	};
}
