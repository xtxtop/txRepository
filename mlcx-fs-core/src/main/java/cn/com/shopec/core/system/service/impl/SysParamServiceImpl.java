package cn.com.shopec.core.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.dao.SysParamDao;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamCacheService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * SysParam 服务实现类
 */
@Service
public class SysParamServiceImpl implements SysParamService {

	private final static String VERSIONPARAM = "SYSTEM_CURRENT_VERSION";
	
	@Resource
	private SysParamCacheService sysParamCacheService;
	
	@Resource
	private SysParamDao sysParamDao;


	@Override
	@Transactional
	public ResultInfo<SysParam> addOrEditSysParam(SysParam sysParam) {
		ResultInfo<SysParam> resultInfo = new ResultInfo<SysParam>();
		try {
			if (sysParam != null) {
				// 添加系统参数
				if (sysParam.getParamId() == null || sysParam.getParamId().trim().equals("")) {
					sysParam.setParamId(this.generatePK());
					sysParam.setIsDeleted(0);
					sysParam.setCreateTime(new Date());
					sysParam.setUpdateTime(new Date());
					sysParamDao.add(sysParam);
				} else {
					// 修改系统参数
					sysParam.setUpdateTime(new Date());
					sysParamDao.update(sysParam);
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sysParam);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("保存失败，系统参数不能为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("保存失败，参数名和参数值都必须是唯一的！");
			try{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}catch(Exception ne){
				return resultInfo;
			}
		}
		return resultInfo;
		
		
	}

	@Override
	@Transactional
	public ResultInfo<SysParam> delete(String sysParamId, Operator operator) {
		ResultInfo<SysParam> resultInfo = new ResultInfo<SysParam>();
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			SysParam sysParam = new SysParam();
			sysParam.setParamId(sysParamId);
			sysParam.setIsDeleted(Constant.DEL_STATE_YES);
			sysParam.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				sysParam.setOperatorType(operator.getOperatorType());
				sysParam.setOperatorId(operator.getOperatorId());
			}
			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = sysParamDao.update(sysParam);
			if (count == 1) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未找到此系统参数，删除失败！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}

	@Override
	public PageFinder<SysParam> pageList(Query q) {
		PageFinder<SysParam> sysParamPage = new PageFinder<SysParam>();
/*		sysParamPage.setData(sysParamDao.pageList1(q));
		sysParamPage.setRowCount(sysParamDao.count1(q));*/
		return sysParamPage;
	}

	@Override
	public ResultInfo<SysParam> batchDelete(String[] sysParamIds, Operator operator) {
		ResultInfo<SysParam> resultInfo = new ResultInfo<SysParam>();
		for (String id : sysParamIds) {
			resultInfo = delete(id, operator);
		}
		return resultInfo;
	}

	@Override
	public SysParam detail(String sysParamId) {
		SysParam sysParam = sysParamDao.get(sysParamId);
		return sysParam;
	}

	/**
	 * 根据key得到参数
	 */
	@Override
	public SysParam getByParamKey(String key) {
		if(key == null) {
			return null;
		}
		SysParam sysParam = sysParamCacheService.getSysParamFromCacheByParamKey(key); //直接从缓存中获取SysParam对象
		if(sysParam == null) { //缓存中不存在，则尝试从数据库获取
			sysParam = sysParamDao.getByParamKey(key);
			
			if(sysParam != null) { //数据库中能取得到
				sysParamCacheService.putSysParamToCache(sysParam); //存入缓存中
			}
		}
		return sysParam;
	}
	
	/**
	 * 根据key，取得参数的值
	 * @param key
	 * @return
	 */
	public String getParamValueByParamKey(String key) {
		SysParam sysParam = getByParamKey(key);
		if(sysParam != null) {
			return sysParam.getParamValue();
		} else {
			return null;
		}
	}

	@Override
	public SysParam getByParamName(String paramName) {

		return sysParamDao.getByParamName(paramName);
	}

	@Override
	public String getVersion()
	{
		try
		{
			SysParam sysParam = getByParamKey(VERSIONPARAM);
			
			return sysParam.getParamValue();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(System.nanoTime());
	}
	

	@Override
	public ResultInfo<SysParam> getParam(String paramId) {
		ResultInfo<SysParam> result = new ResultInfo<>();
		SysParam data = sysParamDao.get(paramId);
		result.setCode(Constant.SECCUESS);
		result.setData(data);
		return result;
	}

	@Override
	public ResultInfo<List<SysParam>> getParamList(Query q) {
		ResultInfo<List<SysParam>> result = new ResultInfo<>();
		List<SysParam> data = sysParamDao.queryAll(q);
		result.setCode(Constant.SECCUESS);
		result.setData(data);
		return result;
	}

	@Override
	@Transactional
	public ResultInfo<SysParam> updateParamValue(SysParam sysParam) {
		ResultInfo<SysParam> result = new ResultInfo<>();
		int ret = sysParamDao.update(sysParam);
		if (ret == 1) {
			result.setCode(Constant.SECCUESS);
			result.setData(sysParam);
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("未找到此系统参数，更新失败！");
		}
		return result;
	}

}
