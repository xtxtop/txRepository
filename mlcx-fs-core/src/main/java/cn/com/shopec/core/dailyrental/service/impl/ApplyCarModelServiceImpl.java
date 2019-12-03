package cn.com.shopec.core.dailyrental.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.dao.ApplyCarModelDao;
import cn.com.shopec.core.dailyrental.model.ApplyCarModel;
import cn.com.shopec.core.dailyrental.service.ApplyCarModelService;

/**
 * ApplyCarModel 服务实现类
 */
@Service
public class ApplyCarModelServiceImpl implements ApplyCarModelService {

	private static final Log log = LogFactory.getLog(ApplyCarModelServiceImpl.class);
	
	@Resource
	private ApplyCarModelDao applyCarModelDao;
	

	/**
	 * 根据查询条件，查询并返回ApplyCarModel的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ApplyCarModel> getApplyCarModelList(Query q) {
		List<ApplyCarModel> list = null;
		try {
			//直接调用Dao方法进行查询
			list = applyCarModelDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ApplyCarModel>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回ApplyCarModel的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ApplyCarModel> getApplyCarModelPagedList(Query q) {
		PageFinder<ApplyCarModel> page = new PageFinder<ApplyCarModel>();
		
		List<ApplyCarModel> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = applyCarModelDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = applyCarModelDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ApplyCarModel>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个ApplyCarModel对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ApplyCarModel getApplyCarModel(String id) {
		ApplyCarModel obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = applyCarModelDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ApplyCarModel对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ApplyCarModel> getApplyCarModelByIds(String[] ids) {
		List<ApplyCarModel> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = applyCarModelDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ApplyCarModel>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的ApplyCarModel记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ApplyCarModel> delApplyCarModel(String id, Operator operator) {
		ResultInfo<ApplyCarModel> resultInfo = new ResultInfo<ApplyCarModel>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = applyCarModelDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			}		  
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条ApplyCarModel记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param applyCarModel 新增的ApplyCarModel数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ApplyCarModel> addApplyCarModel(ApplyCarModel applyCarModel, Operator operator) {
		ResultInfo<ApplyCarModel> resultInfo = new ResultInfo<ApplyCarModel>();
		
		if (applyCarModel == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " applyCarModel = " + applyCarModel);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (applyCarModel.getApplyCarModelId() == null) {
					applyCarModel.setApplyCarModelId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					applyCarModel.setOperatorType(operator.getOperatorType());
					applyCarModel.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				applyCarModel.setCreateTime(now);
				applyCarModel.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(applyCarModel);
				
				//调用Dao执行插入操作
				applyCarModelDao.add(applyCarModel);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(applyCarModel);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}	
		}
		
		return resultInfo;
	}			
	
	/**
	 * 根据主键，更新一条ApplyCarModel记录
	 * @param applyCarModel 更新的ApplyCarModel数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ApplyCarModel> updateApplyCarModel(ApplyCarModel applyCarModel, Operator operator) {
		ResultInfo<ApplyCarModel> resultInfo = new ResultInfo<ApplyCarModel>();
		
		if (applyCarModel == null || applyCarModel.getApplyCarModelId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " applyCarModel = " + applyCarModel);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					applyCarModel.setOperatorType(operator.getOperatorType());
					applyCarModel.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				applyCarModel.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = applyCarModelDao.update(applyCarModel);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(applyCarModel);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}
		
		return resultInfo;
	}	
	
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(100000));
	}
	
	/**
	 * 为ApplyCarModel对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ApplyCarModel obj) {
		if (obj != null) {
			if(obj.getIsAgree()==null){
				obj.setIsAgree(0);
			}
			if(obj.getIsDelete()==null){
				obj.setIsDelete(0);
			}
		}
	}

}
