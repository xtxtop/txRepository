package cn.com.shopec.core.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.dao.SysPermissionDao;
import cn.com.shopec.core.system.dao.SysRolePermRelDao;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.service.SysPermissionService;




/**
 * SysPermission 服务实现类
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService{
	
	@Resource
	private SysPermissionDao sysPermissionDao;
	
	@Resource
	private SysRolePermRelDao sysRolePermRelDao;
	
	private ResultInfo<SysPermission> resultInfo = new ResultInfo<SysPermission>();
	
	@Override
	public PageFinder<SysPermission> pageList(Query q) {
		PageFinder<SysPermission> sysPermissionPage = new PageFinder<SysPermission>();
		sysPermissionPage.setData(sysPermissionDao.pageList1(q));
		sysPermissionPage.setRowCount(sysPermissionDao.count1(q));
		return sysPermissionPage;
	}

	@Override
	@Transactional
	public ResultInfo<SysPermission> addOrEditSysPermission(SysPermission sysPermission) {
		try {
			if (sysPermission != null) {
				//添加系统权限
				if (sysPermission.getPermId() == null || sysPermission.getPermId().trim().equals("")) {
					sysPermission.setPermId(this.generatePK());
					sysPermission.setCreateTime(new Date());
					sysPermission.setUpdateTime(new Date());
					sysPermissionDao.add(sysPermission);
				} else {
					//修改系统权限
					sysPermission.setUpdateTime(new Date());
					sysPermissionDao.update(sysPermission);
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sysPermission);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("系统权限不能为空！");
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
	public ResultInfo<SysPermission> delete(String permissionId) {
		try{
			sysRolePermRelDao.deleteByPermId(permissionId);
			int count=sysPermissionDao.delete(permissionId);
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
	public ResultInfo<SysPermission> batchDelete(String[] sysPermissionIds) {		
		try {
			for(String permId:sysPermissionIds){
				sysRolePermRelDao.deleteByPermId(permId);
				sysPermissionDao.delete(permId);
			}
			resultInfo.setCode(Constant.SECCUESS);
		} catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}

	@Override
	public List<SysPermission> list(Query q) {		
		return sysPermissionDao.queryAll(q);
	}

	@Override
	public SysPermission detail(String permissionId) {
		SysPermission sysPermission = sysPermissionDao.get(permissionId);
		return sysPermission;
	}
	
	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(System.nanoTime());
	}
	
	@Override
	public List<Map<String, Object>> getMenuList(String[] roleId) {
		List<SysPermission> sysPermissionList = sysRolePermRelDao.getMenuList(roleId);
		List<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
		// 资源根据层级分组
		List<SysPermission> superResourceList = new ArrayList<SysPermission>();
		List<SysPermission> seResourceList = new ArrayList<SysPermission>();
		List<SysPermission> thResourceList = new ArrayList<SysPermission>();
		if (sysPermissionList != null && sysPermissionList.size() > 0) {
			Collections.sort(sysPermissionList, new ComparatorSysPermission());
			for (SysPermission temp : sysPermissionList) {
				if (temp.getLevel() == 1) {
					superResourceList.add(temp);
				} else if (temp.getLevel() == 2) {
					seResourceList.add(temp);
				} else {
					thResourceList.add(temp);
				}
			}
		}
		//一级菜单
		for(SysPermission one:superResourceList){			
			Map<String, Object> viewSysPermission=new HashMap<String, Object>();					
			viewSysPermission.put("id", one.getPermId());
			viewSysPermission.put("name", one.getMenuName());
			viewSysPermission.put("link", one.getPermResource());
			viewSysPermission.put("sort", one.getRanking());
			viewSysPermission.put("parentId", "#");
			//二级菜单	
			List<Map<String, Object>> twoList=new ArrayList<Map<String, Object>>();
			for(SysPermission two:seResourceList){
				Map<String, Object> towMap=new HashMap<String, Object>();				
				if(two.getParentId().equals(one.getPermId())){
					towMap.put("id", two.getPermId());
					towMap.put("name", two.getMenuName());
					towMap.put("link", two.getPermResource());
					towMap.put("sort", two.getRanking());
					towMap.put("parentId", two.getParentId());					
					twoList.add(towMap);
				}
				//三级菜单
				List<Map<String, Object>> thrList=new ArrayList<Map<String, Object>>();
				for(SysPermission thr:seResourceList){
					Map<String, Object> thrMap=new HashMap<String, Object>();				
					if(thr.getParentId().equals(two.getPermId())){
						thrMap.put("id", thr.getPermId());
						thrMap.put("name", thr.getMenuName());
						thrMap.put("link", thr.getPermResource());
						thrMap.put("sort", thr.getRanking());
						thrMap.put("parentId", thr.getParentId());					
						thrList.add(thrMap);
					}
				}
				viewSysPermission.put("child", thrList);
			}
			viewSysPermission.put("child", twoList);
			resultList.add(viewSysPermission);
		}
		return resultList;

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
	
}
