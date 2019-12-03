package cn.com.shopec.core.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.dao.SysUserRoleRelDao;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserRoleRel;
import cn.com.shopec.core.system.service.SysUserRoleRelService;




/**
 * SysUserRoleRel 服务实现类
 */
@Service
public class SysUserRoleRelServiceImpl implements SysUserRoleRelService{
	
	@Resource
	private SysUserRoleRelDao sysUserRoleRelDao;
	
	private ResultInfo<SysUserRoleRel> resultInfo = new ResultInfo<SysUserRoleRel>();

	@Override
	@Transactional
	public ResultInfo<SysUserRoleRel> delete(String roleId, String userId) {
		try{
			int count=sysUserRoleRelDao.delete(roleId,userId);
			if(count==1){
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未找到此用户，删除失败！");
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
	public List<SysUserRoleRel> getByUserId(String userId) {		
		List<SysUserRoleRel> list = sysUserRoleRelDao.getByUserId(userId);
//		SysUserRoleRel sysUserRoleRel = new SysUserRoleRel();
//		if(list != null && list.size()>0){
//			sysUserRoleRel = list.get(0);
//		}
		return list;
	}
	
	@Override
	@Transactional
	public ResultInfo<SysUserRoleRel> add(SysUserRoleRel sysUserRoleRel) {
		try {
			if (sysUserRoleRel != null) {
				sysUserRoleRel.setCreateTime(new Date());
				sysUserRoleRelDao.add(sysUserRoleRel);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sysUserRoleRel);
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
	public List<SysUserRoleRel> queryAll(Query q) {
		return sysUserRoleRelDao.queryAll(q);
	}
	
	@Override
	@Transactional
	public ResultInfo<SysUserRoleRel> deleteByRoleId(String roleId) {
		try{
			int count=sysUserRoleRelDao.deleteByRoleId(roleId);
			if(count==1){
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未找到此用户，删除失败！");
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
	public ResultInfo<SysUserRoleRel> deleteByUserId(String userId) {
		try{
			int count=sysUserRoleRelDao.deleteByUserId(userId);
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
	public List<SysRole> getAllUserRole(String userId) {		
		return sysUserRoleRelDao.getAllUserRole(userId);
	}

	@Override
	public List<SysUser> getAllUser(String roleId) {
		// TODO Auto-generated method stub
		return sysUserRoleRelDao.getAllUser(roleId);
	}
	
}
