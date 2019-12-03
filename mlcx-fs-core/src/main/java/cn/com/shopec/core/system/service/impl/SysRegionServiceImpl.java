package cn.com.shopec.core.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.dao.SysRegionDao;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.SysRegionService;

/**
 * SysRegion 服务实现类
 */
@Service
public class SysRegionServiceImpl implements SysRegionService {

	@Resource
	private SysRegionDao sysRegionDao;

	private ResultInfo<SysRegion> resultInfo = new ResultInfo<SysRegion>();

	@Override
	public PageFinder<SysRegion> pageList(Query q) {
		PageFinder<SysRegion> sysRegionPage = new PageFinder<SysRegion>();
		sysRegionPage.setData(sysRegionDao.pageList(q));
		sysRegionPage.setRowCount(sysRegionDao.count(q));
		return sysRegionPage;
	}

	@Override
	public ResultInfo<SysRegion> addOrEditSysRegion(SysRegion sysRegion) {
		try {
			if (sysRegion != null) {
				// 添加系统地区
				if (sysRegion.getRegionId() == null || sysRegion.getRegionId().trim().equals("")) {
					sysRegion.setRegionId(this.generatePK());
					sysRegion.setCreateTime(new Date());
					sysRegion.setUpdateTime(new Date());
					sysRegion.setIsDeleted(0);
					sysRegionDao.add(sysRegion);
				} else {
					// 修改系统地区
					sysRegion.setUpdateTime(new Date());
					sysRegionDao.update(sysRegion);
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sysRegion);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("系统地区不能为空！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}

	@Override
	public SysRegion detail(String regionId) {
		SysRegion sysRegion = sysRegionDao.get(regionId);
		return sysRegion;
	}

	@Override
	@Transactional
	public ResultInfo<SysRegion> delete(String regionId, Operator operator) {
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			SysRegion sysRegion = new SysRegion();
			sysRegion.setRegionId(regionId);
			sysRegion.setIsDeleted(Constant.DEL_STATE_YES);
			sysRegion.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				sysRegion.setOperatorType(operator.getOperatorType());
				sysRegion.setOperatorId(operator.getOperatorId());
			}
			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = sysRegionDao.update(sysRegion);
			if (count == 1) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未找到此系统地区，删除失败！");
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
	public ResultInfo<SysRegion> batchDelete(String[] sysRegionIds, Operator operator) {
		ResultInfo<SysRegion> resultInfo = new ResultInfo<SysRegion>();
		for (String id : sysRegionIds) {
			resultInfo = delete(id, operator);
		}
		return resultInfo;
	}

	@Override
	public List<SysRegion> list(Query q) {
		return sysRegionDao.queryAll(q);
	}

	@Override
	public List<Map<String, Object>> getSysRegionTree(Query q, String type) {
		List<SysRegion> sysRegionList = sysRegionDao.queryAll(q);
		List<Map<String, Object>> resultSysRegionList = new ArrayList<Map<String, Object>>();
		if ("1".equals(type)) {
			// 添加一级地区
			Map<String, Object> view = new HashMap<String, Object>();
			view.put("id", "0");
			view.put("text", "一级地区");
			view.put("ranking", "0");
			view.put("parent", "#");
			resultSysRegionList.add(view);
		}
		for (SysRegion temp : sysRegionList) {
			Map<String, Object> viewSysRegion = new HashMap<String, Object>();
			viewSysRegion.put("id", temp.getRegionId());
			viewSysRegion.put("text", temp.getRegionName());
			viewSysRegion.put("ranking", "");
			if (temp.getParentId() == null || temp.getParentId().equals("0")) {
				viewSysRegion.put("parent", "#");
			} else {
				viewSysRegion.put("parent", temp.getParentId());
			}
			resultSysRegionList.add(viewSysRegion);
		}
		return resultSysRegionList;
	}

	@Override
	public List<SysRegion> getProvices() {
		return sysRegionDao.getProvices();
	}

	@Override
	public List<SysRegion> getProvices2() {
		return sysRegionDao.getProvices2();
	}

	@Override
	public List<SysRegion> getProvices3() {
		return sysRegionDao.getProvices3();
	}

	@Override
	public List<SysRegion> getCitys(String id) {
		return sysRegionDao.getCitys(id);

	}
	@Override
	public List<SysRegion> getProvince(String provinceName) {
		return sysRegionDao.getProvince(provinceName);
		
	}

	@Override
	public List<SysRegion> getCountrys(String id) {
		return sysRegionDao.getCountrys(id);
	}

	public String generatePK() {
		return String.valueOf(System.nanoTime());
	}
	
	/**
	 * 根据地址名得到地址id
	 * 
	 * @param addrRegion1Name
	 * @return
	 */
	@Override
	public SysRegion getRegionIdByRegionName(String addrRegionName) {
		return sysRegionDao.getRegionIdByRegionName(addrRegionName);
	}

	@Override
	public List<Map<String, Object>> getMerchantSysRegionTree(Query q) {
		List<SysRegion> sysRegionList = sysRegionDao.queryAll(q);
		List<Map<String, Object>> resultSysRegionList = new ArrayList<Map<String, Object>>();
		for (SysRegion temp : sysRegionList) {
			Map<String, Object> viewSysRegion = new HashMap<String, Object>();
			viewSysRegion.put("regionId", temp.getRegionId());
			viewSysRegion.put("regionName", temp.getRegionName());
		
			if (temp.getParentId() == null || temp.getParentId().equals("0")) {
				viewSysRegion.put("parentId", "#");
			} else {
				viewSysRegion.put("parentId", temp.getParentId());
			}
			resultSysRegionList.add(viewSysRegion);
		}
		return resultSysRegionList;
	}
}
