package cn.com.shopec.core.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import cn.com.shopec.core.system.dao.DataDictItemDao;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;

/**
 * 数据字典的明细项表 服务实现类
 */
@Service
public class DataDictItemServiceImpl implements DataDictItemService {

	private static final Log log = LogFactory.getLog(DataDictItemServiceImpl.class);
	
	@Resource
	private DataDictItemDao dataDictItemDao;
	

	/**
	 * 根据查询条件，查询并返回DataDictItem的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DataDictItem> getDataDictItemList(Query q) {
		List<DataDictItem> list = null;
		try {
			//已删除的不查出
			DataDictItem dataDictItem = (DataDictItem)q.getQ();
			if (dataDictItem != null) {
				dataDictItem.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = dataDictItemDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DataDictItem>(0) : list;
		return list; 
	}
	
	
	
	/**
	 * 根据查询条件，分页查询并返回DataDictItem的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<DataDictItem> getDataDictItemPagedList(Query q) {
		PageFinder<DataDictItem> page = new PageFinder<DataDictItem>();
		
		List<DataDictItem> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			DataDictItem dataDictItem = (DataDictItem)q.getQ();
			if (dataDictItem != null) {
				dataDictItem.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = dataDictItemDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = dataDictItemDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DataDictItem>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	
	/**
	 * 根据查询条件，分页查询并返回DataDictItem的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<DataDictItem> getDataDictItemPagedListWorker(Query q) {
		PageFinder<DataDictItem> page = new PageFinder<DataDictItem>();
		
		List<DataDictItem> list = null;
		List<DataDictItem> lists = new ArrayList<DataDictItem>();
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			DataDictItem dataDictItem = (DataDictItem)q.getQ();
			if (dataDictItem != null) {
				dataDictItem.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = dataDictItemDao.pageList(q);
			for (int i = 0; i < list.size(); i++) {
				DataDictItem dd=list.get(i);
				dd.setRegionId(dd.getDataDictItemId());
				dd.setRegionName(dd.getItemValue());
				lists.add(dd);
			}
			//调用dao统计满足条件的记录总数
			rowCount = dataDictItemDao.count(q);
	 	} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		lists = lists == null ? new ArrayList<DataDictItem>(0) : lists;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(lists);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据主键，查询并返回一个DataDictItem对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public DataDictItem getDataDictItem(String id) {
		DataDictItem obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = dataDictItemDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组DataDictItem对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DataDictItem> getDataDictItemByIds(String[] ids) {
		List<DataDictItem> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = dataDictItemDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DataDictItem>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的DataDictItem记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DataDictItem> delDataDictItem(String id, Operator operator) {
		ResultInfo<DataDictItem> resultInfo = new ResultInfo<DataDictItem>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			DataDictItem dataDictItem = new DataDictItem();
			dataDictItem.setDataDictItemId(id);
			dataDictItem.setIsDeleted(Constant.DEL_STATE_YES);
			dataDictItem.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				dataDictItem.setOperatorType(operator.getOperatorType());
				dataDictItem.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = dataDictItemDao.update(dataDictItem);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(dataDictItem);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条DataDictItem记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param dataDictItem 新增的DataDictItem数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DataDictItem> addDataDictItem(DataDictItem dataDictItem, Operator operator) {
		ResultInfo<DataDictItem> resultInfo = new ResultInfo<DataDictItem>();
		
		if (dataDictItem == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " dataDictItem = " + dataDictItem);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (dataDictItem.getDataDictItemId() == null) {
					dataDictItem.setDataDictItemId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					dataDictItem.setOperatorType(operator.getOperatorType());
					dataDictItem.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				dataDictItem.setCreateTime(now);
				dataDictItem.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(dataDictItem);
				
				//调用Dao执行插入操作
				dataDictItemDao.add(dataDictItem);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(dataDictItem);
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
	 * 根据主键，更新一条DataDictItem记录
	 * @param dataDictItem 更新的DataDictItem数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DataDictItem> updateDataDictItem(DataDictItem dataDictItem, Operator operator) {
		ResultInfo<DataDictItem> resultInfo = new ResultInfo<DataDictItem>();
		
		if (dataDictItem == null || dataDictItem.getDataDictItemId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " dataDictItem = " + dataDictItem);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					dataDictItem.setOperatorType(operator.getOperatorType());
					dataDictItem.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				dataDictItem.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = dataDictItemDao.update(dataDictItem);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(dataDictItem);
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
		return String.valueOf(System.nanoTime());
	}
	
	/**
	 * 为DataDictItem对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DataDictItem obj) {
		if (obj != null) {
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(1);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

	/**
	 * 根据数据字典项id，取得数据字典项的值
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getValueOfDataDictItem(String id) {
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return null;
		}
		DataDictItem item = this.getDataDictItem(id); //根据id取的数据字典项对象
		if(item != null) {
			return item.getItemValue(); //取其值
		} else {
			return null;
		}
	}
	
	/**
	 * 根据字典分类编码，取得该分类下所有可用的数据字典项，以list形式返回
	 * @param catCode
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DataDictItem> getDataDictItemListByCatCode(String catCode) {
		if(catCode == null || catCode.length() == 0) { //传入的参数无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " catCode = " + catCode);
			return new ArrayList<DataDictItem>(0);
		}
		DataDictItem condition = new DataDictItem();
		condition.setDataDictCatCode(catCode); //参数，基于分类编码查询
		condition.setIsAvailable(Constant.YES); //参数，当前可用
		Query q = new Query(condition);
		
		List<DataDictItem> res = this.getDataDictItemList(q);
		return res;
	}
	
	/**
	 * 根据字典项的父级id，取得该父级字典项的所有子项，以list形式返回
	 * @param parentId
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DataDictItem> getDataDictItemListByParentItemId(String parentId) {
		if(parentId == null || parentId.length() == 0) { //传入的参数无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " parentId = " + parentId);
			return new ArrayList<DataDictItem>(0);
		}
		DataDictItem condition = new DataDictItem();
		condition.setParentItemId(parentId); //参数，基于父级id查询
		condition.setIsAvailable(Constant.YES); //参数，当前可用
		Query q = new Query(condition);
		
		List<DataDictItem> res = this.getDataDictItemList(q);
		return res;
	}
	/**
	 * 获取数据字典分类代码
	 * @return
	 */
	@Override
	public List<DataDictItem> getDataDictCatCode() {
		return dataDictItemDao.getDataDictCatCode();
	}

	@Override
	public List<DataDictItem> getModleByBrand(String parentCatCode) {
		// TODO Auto-generated method stub
		return dataDictItemDao.getModleByBrand(parentCatCode);
	}



	@Override
	public DataDictItem getItemValue(String cityName,String dataDictCatCode) {
	
		return dataDictItemDao.getItemValue(cityName,dataDictCatCode);
	}
}
