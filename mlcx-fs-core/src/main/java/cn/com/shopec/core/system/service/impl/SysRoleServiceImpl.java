package cn.com.shopec.core.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.dao.SysPermissionDao;
import cn.com.shopec.core.system.dao.SysRoleDao;
import cn.com.shopec.core.system.dao.SysRolePermRelDao;
import cn.com.shopec.core.system.dao.SysUserRoleRelDao;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysRolePermRel;
import cn.com.shopec.core.system.service.SysRoleService;


/**
 * SysRole 服务实现类
 */
@Service
public class SysRoleServiceImpl implements SysRoleService{
	
	private Logger logger = Logger.getLogger(SysRoleServiceImpl.class);
	
	@Resource
	private SysRoleDao sysRoleDao;
	
	@Resource
	private SysRolePermRelDao sysRolePermRelDao;
	
	@Resource
	private	SysUserRoleRelDao sysUserRoleRelDao;
	
	private ResultInfo<SysRole> resultInfo = new ResultInfo<SysRole>();
	
	@Resource
	private	SysPermissionDao sysPermissionDao;
	
	@Override
	public PageFinder<SysRole> pageList(Query q) {
		PageFinder<SysRole> sysRolePage = new PageFinder<SysRole>();
		sysRolePage.setData(sysRoleDao.pageList(q));
		sysRolePage.setRowCount(sysRoleDao.count(q));
		return sysRolePage;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultInfo<SysRole> addOrEditSysRole(SysRole sysRole) {
		try {
			if (sysRole != null) {
				//添加
				if (sysRole.getRoleId() == null || sysRole.getRoleId().trim().equals("")) {
					sysRole.setRoleId(this.generatePK());
					sysRole.setIsDeleted(0);
					sysRole.setCreateTime(new Date());
					sysRole.setUpdateTime(new Date());
					sysRoleDao.add(sysRole);					
				} else {
					//修改
					sysRole.setUpdateTime(new Date());
					sysRoleDao.update(sysRole);
					sysRolePermRelDao.deleteByRoleId(sysRole.getRoleId());
				}
				for(int i=0; i<sysRole.getPermissionIds().length;i++){
					SysRolePermRel sysRolePermRel = new SysRolePermRel();
					sysRolePermRel.setPermId(sysRole.getPermissionIds()[i]);
					sysRolePermRel.setRoleId(sysRole.getRoleId());
					sysRolePermRelDao.add(sysRolePermRel);
					//判断选中子类时，将父类也添加
					SysPermission sysPerm=sysPermissionDao.get(sysRole.getPermissionIds()[i]);
					if(!sysPerm.getParentId().equals("0")){
						if(getParentIS(sysRole.getPermissionIds(),sysPerm.getParentId())){
							SysRolePermRel sysRolePermRel1 = new SysRolePermRel();
							sysRolePermRel1.setPermId(sysPerm.getParentId());
							sysRolePermRel1.setRoleId(sysRole.getRoleId());
							sysRolePermRelDao.add(sysRolePermRel1);
						}
					}
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sysRole);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("系统角色不能为空！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("--------保存失败，错误信息：" + e.getMessage(), e);
			
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("保存失败，原因：角色名称冲突或其他未知原因！");
		}
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<SysRole> delete(String roleId, Operator operator) {
		try{
			sysRolePermRelDao.deleteByRoleId(roleId);
			sysUserRoleRelDao.deleteByRoleId(roleId);
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			SysRole sysRole = new SysRole();
			sysRole.setRoleId(roleId);
			sysRole.setIsDeleted(Constant.DEL_STATE_YES);
			sysRole.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				sysRole.setOperatorType(operator.getOperatorType());
				sysRole.setOperatorId(operator.getOperatorId());
			}
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count= sysRoleDao.update(sysRole);
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
	public List<SysRole> queryAll(Query q) {
		return sysRoleDao.queryAll(q);
	}

	@Override
	public SysRole detail(String roleId) {
		return sysRoleDao.get(roleId);
	}
	
	@Override
	public ResultInfo<SysRole> batchDelete(String[] sysRoleIds, Operator operator) {		
		ResultInfo<SysRole> resultInfo = new ResultInfo<SysRole>();
		for(String id:sysRoleIds){
			resultInfo = delete(id, operator);
		}
		return resultInfo;
	}

	@Override
	public SysRole getRoleByName(String roleName) {
		SysRole sysRole=sysRoleDao.getRoleByName(roleName);
		return sysRole;
	}

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void addOrUpdateSysRole(SysRole sysRole)throws Exception {
    	try {
    		if (sysRole.getRoleId() == null || "".equals(sysRole.getRoleId())) {
    			sysRole.setRoleId(this.generatePK());
    			sysRole.setIsDeleted(0);
    			sysRole.setCreateTime(new Date());
    			sysRole.setUpdateTime(new Date());
    			sysRoleDao.add(sysRole);
    		} else {
    			//修改
    			sysRole.setUpdateTime(new Date());
    			Assert.isTrue(sysRoleDao.update(sysRole)==1,"更新角色失败！");
    			//按角色删除权限关联表数据，重新写入
    			int result = sysRolePermRelDao.deleteByRoleId(sysRole.getRoleId());
    			System.out.println(result);
    		}
    		List<String> ids = new ArrayList<String>(Arrays.asList(sysRole.getPermissionIds()));
    		//查询二级菜单集合中的父ID
    		List<String> parentIds = sysPermissionDao.getParentIdByChildrenIds(sysRole.getPermissionIds());
    		List<String> parentIds1=new ArrayList<String>();
    		for(String par:parentIds){
    			Integer tag=0;
    			for(String id:ids){
    				if(par.equals(id)){
    					tag=1;
    				}
    			}
    			if(tag==0){
    				parentIds1.add(par);
    			}
    		}
    		ids.addAll(parentIds1);
    		//添加角色权限关联数据
    		for(String key : ids){
    			SysRolePermRel sysRolePermRel = new SysRolePermRel();
    			sysRolePermRel.setPermId(key);
    			sysRolePermRel.setRoleId(sysRole.getRoleId());
    			sysRolePermRel.setCreateTime(new Date());
    			sysRolePermRelDao.add(sysRolePermRel);
    		}
		} catch (Exception e) {
			e.printStackTrace();
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
    }

    private String generatePK() {
    	return String.valueOf(System.nanoTime());
    }
    
    //判断当前菜单的父类菜单id是否在数组中
	public boolean getParentIS(String[] ids,String id) {
		boolean tag=true;
		for(int i=0;i<ids.length;i++){
			if(ids[i].equals(id)){
				 tag=false;
			}
		}
		return tag;
	}
}
