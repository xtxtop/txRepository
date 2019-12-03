package cn.com.shopec.core.dailyrental.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDao;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDateDao;
import cn.com.shopec.core.dailyrental.model.CarInventory;
import cn.com.shopec.core.dailyrental.model.CarInventoryDate;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.MerchantInventoryDate;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.service.CarInventoryDateService;
import cn.com.shopec.core.dailyrental.service.CarInventoryService;
import cn.com.shopec.core.dailyrental.service.MerchantInventoryDateService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 商家库存日历表 服务实现类
 */
@Service
public class MerchantInventoryDateServiceImpl implements MerchantInventoryDateService {

	private static final Log log = LogFactory.getLog(MerchantInventoryDateServiceImpl.class);

	@Resource
	private MerchantInventoryDao merchantInventoryDao;

	@Resource
	private MerchantInventoryDateDao merchantInventoryDateDao;

	@Resource
	private CarInventoryService carInventoryService;
	@Resource
	private SysParamService sysParamService;

	@Resource
	private CarInventoryDateService carInventoryDateService;
	/**
	 * 根据查询条件，查询并返回MerchantInventoryDate的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantInventoryDate> getMerchantInventoryDateList(Query q) {
		List<MerchantInventoryDate> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = merchantInventoryDateDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventoryDate>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回MerchantInventoryDate的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MerchantInventoryDate> getMerchantInventoryDatePagedList(Query q) {
		PageFinder<MerchantInventoryDate> page = new PageFinder<MerchantInventoryDate>();

		List<MerchantInventoryDate> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = merchantInventoryDateDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = merchantInventoryDateDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventoryDate>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个MerchantInventoryDate对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MerchantInventoryDate getMerchantInventoryDate(String id) {
		MerchantInventoryDate obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = merchantInventoryDateDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MerchantInventoryDate对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantInventoryDate> getMerchantInventoryDateByIds(String[] ids) {
		List<MerchantInventoryDate> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = merchantInventoryDateDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventoryDate>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的MerchantInventoryDate记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantInventoryDate> delMerchantInventoryDate(String id, Operator operator) {
		ResultInfo<MerchantInventoryDate> resultInfo = new ResultInfo<MerchantInventoryDate>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = merchantInventoryDateDao.delete(id);
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
	 * 新增一条MerchantInventoryDate记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param merchantInventoryDate
	 *            新增的MerchantInventoryDate数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantInventoryDate> addMerchantInventoryDate(MerchantInventoryDate merchantInventoryDate,
			Operator operator) {
		ResultInfo<MerchantInventoryDate> resultInfo = new ResultInfo<MerchantInventoryDate>();

		if (merchantInventoryDate == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantInventoryDate = " + merchantInventoryDate);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (merchantInventoryDate.getInventoryDateId() == null || merchantInventoryDate.getInventoryDateId().trim().length() == 0) {
					merchantInventoryDate.setInventoryDateId(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					merchantInventoryDate.setOperatorType(operator.getOperatorType());
					merchantInventoryDate.setOperatorId(operator.getOperatorId());
				}
				MerchantInventoryDate queryInventoryDate = new MerchantInventoryDate();
				queryInventoryDate.setMerInventoryId(merchantInventoryDate.getMerInventoryId());
				queryInventoryDate.setInventoryDate(merchantInventoryDate.getInventoryDate());
				List<MerchantInventoryDate> merchantInventoryDateList = getMerchantInventoryDateList(new Query(queryInventoryDate));
				if(merchantInventoryDateList != null && merchantInventoryDateList.size() > 0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("该日历库存已存在，不能添加");
					return resultInfo;
				}
				MerchantInventory inventory = merchantInventoryDao.get(merchantInventoryDate.getMerInventoryId());
				if(inventory == null){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("库存不存在");
					return resultInfo;
				}
				//已有的总库存数
				double existInventoryTotal = inventory.getInventoryDay();
				//最大可设置的可用库存数
				SysParam param = sysParamService.getByParamKey("inventoryRatio");
				Double inventoryRatio = 0.0;
				if (param!=null&&param.getParamValue()!=null) {
					inventoryRatio = Double.valueOf(param.getParamValue());
				}
				Double ratio = ECCalculateUtils.div(inventoryRatio, 100);
				Double d = ECCalculateUtils.mul(existInventoryTotal,ECCalculateUtils.add(1, ratio));
				int tempAvailableInventory = d.intValue();
				//设置的可用库存数
				int availableInventory = merchantInventoryDate.getAvailableInventory();
			
				if(availableInventory>tempAvailableInventory){
					resultInfo.setCode(Constant.FAIL);
					
					resultInfo.setMsg("可用库存设置不能超过总库存的"+inventoryRatio+"%");
					return resultInfo;
				}
				merchantInventoryDate.setCarModelId(inventory.getCarModelId());
				merchantInventoryDate.setMerchantId(inventory.getMerchantId());
				merchantInventoryDate.setLeasedQuantity(0);
				merchantInventoryDate.setAvailableInventory(availableInventory);
				merchantInventoryDate.setInventoryTotal(inventory.getInventoryDay());
				CarInventoryDate queryCarInventoryDate = new CarInventoryDate();
				queryCarInventoryDate.setCarModelId(inventory.getCarModelId());
				queryCarInventoryDate.setInventoryDate(merchantInventoryDate.getInventoryDate());
				List<CarInventoryDate> carInventoryDateList = carInventoryDateService.getCarInventoryDateList(new Query(queryCarInventoryDate));
				if(carInventoryDateList != null && carInventoryDateList.size() > 1){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("该日历平台库存存在多笔记录");
					return resultInfo;
				}				
				CarInventoryDate carInventoryDate;
				if(carInventoryDateList == null || carInventoryDateList.size() == 0){
					CarInventory queryCarInventory = new CarInventory();
					queryCarInventory.setCarModelId(inventory.getCarModelId());
					queryCarInventory.setCityId(inventory.getCityId());
					List<CarInventory> carInventoryList = carInventoryService.getCarInventoryList(new Query(queryCarInventory));
					if(carInventoryList == null){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("平台车型库存不在存在");
						return resultInfo;
					}
					if(carInventoryList.size() > 1){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("平台车型库存存在多笔记录");
						return resultInfo;
					}
					CarInventory carInventory = carInventoryList.get(0);
					carInventoryDate = new CarInventoryDate();
					carInventoryDate.setCarInventoryId(carInventory.getCarInventoryId());
					carInventoryDate.setCarModelId(carInventory.getCarModelId());
					carInventoryDate.setInventoryDate(merchantInventoryDate.getInventoryDate());
					carInventoryDate.setInventoryTotal(carInventory.getInventoryTotal());
					carInventoryDate.setLeasedQuantity(0);
					carInventoryDate.setReserveQuantity(0);
					carInventoryDate.setAvailableInventory(availableInventory);				
					carInventoryDateService.addCarInventoryDate(carInventoryDate, operator);
				}else{
					carInventoryDate = carInventoryDateList.get(0);
					carInventoryDate.setAvailableInventory(availableInventory);
					carInventoryDateService.updateCarInventoryDate(carInventoryDate, operator);
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				merchantInventoryDate.setCreateTime(now);
				merchantInventoryDate.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(merchantInventoryDate);

				// 调用Dao执行插入操作
				merchantInventoryDateDao.add(merchantInventoryDate);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantInventoryDate);
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
	 * 根据主键，更新一条MerchantInventoryDate记录
	 * 
	 * @param merchantInventoryDate
	 *            更新的MerchantInventoryDate数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantInventoryDate> updateMerchantInventoryDate(MerchantInventoryDate merchantInventoryDate,
			Operator operator) {
		ResultInfo<MerchantInventoryDate> resultInfo = new ResultInfo<MerchantInventoryDate>();

		if (merchantInventoryDate == null || merchantInventoryDate.getInventoryDateId() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantInventoryDate = " + merchantInventoryDate);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					merchantInventoryDate.setOperatorType(operator.getOperatorType());
					merchantInventoryDate.setOperatorId(operator.getOperatorId());
				}
				MerchantInventoryDate queryMerchantInventoryDate = merchantInventoryDateDao.get(merchantInventoryDate.getInventoryDateId());
				if(queryMerchantInventoryDate == null){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("该日历库存不存在");
					return resultInfo;
				}				
				CarInventoryDate queryCarInventoryDate = new CarInventoryDate();
				queryCarInventoryDate.setCarModelId(queryMerchantInventoryDate.getCarModelId());
				queryCarInventoryDate.setInventoryDate(queryMerchantInventoryDate.getInventoryDate());
				List<CarInventoryDate> carInventoryDateList = carInventoryDateService.getCarInventoryDateList(new Query(queryCarInventoryDate));
				if(carInventoryDateList != null && carInventoryDateList.size() > 1){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("该日历平台库存存在多笔记录");
					return resultInfo;
				}	
				//已有的总库存数
				double existInventoryTotal = queryMerchantInventoryDate.getInventoryTotal();
				//最大可设置的可用库存数
				SysParam param = sysParamService.getByParamKey("inventoryRatio");
				Double inventoryRatio = 0.0;
				if (param!=null&&param.getParamValue()!=null) {
					inventoryRatio = Double.valueOf(param.getParamValue());
				}
				Double ratio = ECCalculateUtils.div(inventoryRatio, 100);
				Double d = ECCalculateUtils.mul(existInventoryTotal,ECCalculateUtils.add(1, ratio));
				int tempAvailableInventory = d.intValue();
				//设置的可用库存数
				int availableInventory = merchantInventoryDate.getAvailableInventory();
				if(availableInventory>tempAvailableInventory){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("可用库存设置不能超过总库存的"+inventoryRatio+"%");
					return resultInfo;
				}
				CarInventoryDate carInventoryDate;
				if(carInventoryDateList == null || carInventoryDateList.size() == 0){
					MerchantInventory merchantInventory = merchantInventoryDao.get(queryMerchantInventoryDate.getMerInventoryId());
					CarInventory queryCarInventory = new CarInventory();
					queryCarInventory.setCarModelId(queryMerchantInventoryDate.getCarModelId());
					queryCarInventory.setCityId(merchantInventory.getCityId());
					List<CarInventory> carInventoryList = carInventoryService.getCarInventoryList(new Query(queryCarInventory));
					if(carInventoryList == null){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("平台车型库存不在存在");
						return resultInfo;
					}
					if(carInventoryList.size() > 1){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("平台车型库存存在多笔记录");
						return resultInfo;
					}
					CarInventory carInventory = carInventoryList.get(0);
					carInventoryDate = new CarInventoryDate();
					carInventoryDate.setCarInventoryId(carInventory.getCarInventoryId());
					carInventoryDate.setCarModelId(carInventory.getCarModelId());
					carInventoryDate.setInventoryTotal(carInventory.getInventoryTotal());
					carInventoryDate.setInventoryDate(queryMerchantInventoryDate.getInventoryDate());
					carInventory.setLeasedQuantity(0);
					carInventory.setReserveQuantity(0);
					carInventoryDate.setAvailableInventory(carInventory.getInventoryTotal()+availableInventory);				
					carInventoryDateService.addCarInventoryDate(carInventoryDate, operator);
				}else{
					carInventoryDate = carInventoryDateList.get(0);
					carInventoryDate.setAvailableInventory(availableInventory);
					carInventoryDateService.updateCarInventoryDate(carInventoryDate, operator);
				}
				// 设置更新时间为当前时间
				merchantInventoryDate.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantInventoryDateDao.update(merchantInventoryDate);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchantInventoryDate);
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
	 * 根据商家ID,车型,日期段 追加预占库存
	 */
	@Override
	@Transactional
	public ResultInfo<MerchantInventoryDate> superaddleasedQuantity(MerchantInventoryDate merchantInventoryDate,OrderDay orderDay,
			Operator operator) {
		ResultInfo<MerchantInventoryDate> resultInfo = new ResultInfo<MerchantInventoryDate>();
		// 传入参数无效时直接返回失败结果
		if (merchantInventoryDate == null || merchantInventoryDate.getInventoryDateStart() == null
				|| merchantInventoryDate.getInventoryDateEnd() == null 
				|| StringUtils.isBlank(merchantInventoryDate.getCarModelId())
				|| StringUtils.isBlank(merchantInventoryDate.getMerchantId())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
		} else {
			try {

				List<MerchantInventory> inventorys = merchantInventoryDao.getInventoryByMerchantAndCarModelAndCityId(merchantInventoryDate.getMerchantId(),merchantInventoryDate.getCarModelId(),orderDay.getCityId());
				if(inventorys == null || inventorys.size()==0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("还没有商家库存记录！");
					return resultInfo;
				}else if(inventorys != null && inventorys.size() > 1){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("商家库存存在多笔记录！");
					return resultInfo;
				}
				int days = (int)ECDateUtils.getDistanceDays(merchantInventoryDate.getInventoryDateStart(), merchantInventoryDate.getInventoryDateEnd()) + 1;
				if(days>0){
					if(orderDay.getOrderDuration()<days){
						days = days - 1;
					}
				}
				MerchantInventoryDate queryInventoryDate = new MerchantInventoryDate();
				queryInventoryDate.setMerInventoryId(inventorys.get(0).getMerInventoryId());
				queryInventoryDate.setInventoryDateStart(merchantInventoryDate.getInventoryDateStart());
				queryInventoryDate.setInventoryDateEnd(merchantInventoryDate.getInventoryDateEnd());
				queryInventoryDate.setCarModelId(merchantInventoryDate.getCarModelId());
				queryInventoryDate.setMerchantId(merchantInventoryDate.getMerchantId());
				List<MerchantInventoryDate> merchantInventoryDateList = getMerchantInventoryDateList(new Query(queryInventoryDate));
				
				for(int i = 0; i < days; i++){
					Date date = ECDateUtils.parseDate(merchantInventoryDate.getInventoryDateStart());
			    	Calendar calendar = Calendar.getInstance();
			    	calendar.setTime(date);
			    	calendar.add(Calendar.DAY_OF_MONTH, i);
					String dateStr = ECDateUtils.formatDate(calendar.getTime(), ECDateUtils.Format_Date);
					MerchantInventoryDate existMapInventory = null;
					if(merchantInventoryDateList != null){
						for(MerchantInventoryDate d : merchantInventoryDateList){
							if(dateStr.equals(d.getInventoryDate())){
								existMapInventory = d;
								break;
							}
						}
					}
					//増加不存在的数据
					if(existMapInventory==null){
						MerchantInventoryDate a = new MerchantInventoryDate();
						a.setInventoryDateId(this.generatePK());
						a.setCarModelId(merchantInventoryDate.getCarModelId());
						a.setMerchantId(merchantInventoryDate.getMerchantId());
						a.setMerInventoryId(inventorys.get(0).getMerInventoryId());
						a.setInventoryTotal(inventorys.get(0).getInventoryDay());
						a.setLeasedQuantity(1);
						a.setAvailableInventory(inventorys.get(0).getInventoryDay()-1);
						a.setInventoryDate(dateStr);
						// 设置创建时间和更新时间为当前时间
						Date now = new Date();
						a.setCreateTime(now);
						a.setUpdateTime(now);
						// 填充默认值
						this.fillDefaultValues(a);
						// 调用Dao执行插入操作
						merchantInventoryDateDao.add(a);
					}else{
						//修改存在的数据。
						merchantInventoryDateDao.addLeasedQuantity(existMapInventory.getInventoryDateId());
					}
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantInventoryDate);
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
	 * 根据商家ID,车型,日期段 追加预占库存
	 * @param merchantInventoryDate 更新的MerchantInventoryDate数据和carInventory和carInventoryDate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Override
	@Transactional
	public ResultInfo<MerchantInventoryDate> superaddleasedQuantityForChangeCar(MerchantInventoryDate merchantInventoryDate,OrderDay orderDay,
			Operator operator) {
		ResultInfo<MerchantInventoryDate> resultInfo = new ResultInfo<MerchantInventoryDate>();
		// 传入参数无效时直接返回失败结果
		if (merchantInventoryDate == null || merchantInventoryDate.getInventoryDateStart() == null
				|| merchantInventoryDate.getInventoryDateEnd() == null 
				|| StringUtils.isBlank(merchantInventoryDate.getCarModelId())
				|| StringUtils.isBlank(merchantInventoryDate.getMerchantId())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
		} else {
			try {
				MerchantInventoryDate queryInventoryDate = new MerchantInventoryDate();
				queryInventoryDate.setInventoryDateStart(merchantInventoryDate.getInventoryDateStart());
				queryInventoryDate.setInventoryDateEnd(merchantInventoryDate.getInventoryDateEnd());
				queryInventoryDate.setCarModelId(merchantInventoryDate.getCarModelId());
				queryInventoryDate.setMerchantId(merchantInventoryDate.getMerchantId());
				List<MerchantInventoryDate> merchantInventoryDateList = getMerchantInventoryDateList(new Query(queryInventoryDate));

				List<MerchantInventory> inventorys = merchantInventoryDao.getInventoryByMerchantAndCarModelAndCityId(merchantInventoryDate.getMerchantId(),merchantInventoryDate.getCarModelId(),orderDay.getCityId());
				if(inventorys == null || inventorys.size()==0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("还没有商家库存记录！");
					return resultInfo;
				}else if(inventorys != null && inventorys.size() > 1){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("商家库存存在多笔记录！");
					return resultInfo;
				}
				int days = (int)ECDateUtils.getDistanceDays(merchantInventoryDate.getInventoryDateStart(), merchantInventoryDate.getInventoryDateEnd()) + 1;
				if(days>0){
					if(orderDay.getOrderDuration()<days){
						days = days - 1;
					}
				}
				for(int i = 0; i < days; i++){
					Date date = ECDateUtils.parseDate(merchantInventoryDate.getInventoryDateStart());
			    	Calendar calendar = Calendar.getInstance();
			    	calendar.setTime(date);
			    	calendar.add(Calendar.DAY_OF_MONTH, i);
					String dateStr = ECDateUtils.formatDate(calendar.getTime(), ECDateUtils.Format_Date);
					MerchantInventoryDate existMapInventory = null;
					if(merchantInventoryDateList != null){
						for(MerchantInventoryDate d : merchantInventoryDateList){
							if(dateStr.equals(d.getInventoryDate())){
								existMapInventory = d;
								break;
							}
						}
					}
					//増加不存在的数据
					if(existMapInventory==null){
						MerchantInventoryDate a = new MerchantInventoryDate();
						a.setInventoryDateId(this.generatePK());
						a.setCarModelId(merchantInventoryDate.getCarModelId());
						a.setMerchantId(merchantInventoryDate.getMerchantId());
						a.setMerInventoryId(inventorys.get(0).getMerInventoryId());
						a.setInventoryTotal(inventorys.get(0).getInventoryDay());
						a.setLeasedQuantity(1);
						a.setAvailableInventory(inventorys.get(0).getInventoryDay()-1);
						a.setInventoryDate(dateStr);
						// 设置创建时间和更新时间为当前时间
						Date now = new Date();
						a.setCreateTime(now);
						a.setUpdateTime(now);
						// 填充默认值
						this.fillDefaultValues(a);
						// 调用Dao执行插入操作
						merchantInventoryDateDao.add(a);
					}else{
						//修改存在的数据。
						merchantInventoryDateDao.addLeasedQuantity(existMapInventory.getInventoryDateId());
					}
				}
				CarInventory carInventory = carInventoryService.getCarInventoryByCarModelId(merchantInventoryDate.getCarModelId());
				//修改车辆总库存数据。
				CarInventory carInventoryUpdate = new CarInventory();
				carInventoryUpdate.setCarInventoryId(carInventory.getCarInventoryId());
				carInventoryUpdate.setCarModelId(merchantInventoryDate.getCarModelId());
				carInventoryUpdate.setAvailableInventory(carInventory.getAvailableInventory()-1);
				carInventoryService.updateCarInventory(carInventoryUpdate, null);
				
				//修改车辆库存日历数据。
				CarInventoryDate cidQ = new CarInventoryDate();
				cidQ.setCarInventoryId(carInventory.getCarInventoryId());
				Query q = new Query(cidQ);
				//全部车辆库存日历
				List<CarInventoryDate> inventoryDateList = carInventoryDateService.getCarInventoryDateList(q);
				//起始时间全部日期
				List<String> allDates = ECDateUtils.collectLocalDates(ECDateUtils.formatDate(orderDay.getAppointmentTakeTime(),"yyyy-MM-dd"), ECDateUtils.formatDate(orderDay.getAppointmentReturnTime(),"yyyy-MM-dd"));
				//处理要设置的自定义日期
				if(orderDay.getOrderDuration()<allDates.size()){
					allDates.remove(allDates.size()-1);
				}
				for(String dateStr1:allDates){
					//全部日期
					Date date1 = ECDateUtils.parseDate(dateStr1);
					//自定义日期
					Date flagInventoryDate = null;
					for(CarInventoryDate carInventoryDate:inventoryDateList){
						Date inventoryDate = ECDateUtils.parseDate(carInventoryDate.getInventoryDate()); 
						if(date1.compareTo(inventoryDate)==0){
							CarInventoryDate carInventoryDateForUpdate = new CarInventoryDate();
							carInventoryDateForUpdate.setInventoryDateId(carInventoryDate.getInventoryDateId());
							carInventoryDateForUpdate.setCarInventoryId(carInventoryDate.getCarInventoryId());
							carInventoryDateForUpdate.setCarModelId(carInventoryDate.getCarModelId());
							carInventoryDateForUpdate.setAvailableInventory(carInventoryDate.getAvailableInventory().intValue()-1);
							carInventoryDateForUpdate.setLeasedQuantity(carInventoryDate.getLeasedQuantity().intValue()+1);
							carInventoryDateService.updateCarInventoryDate(carInventoryDateForUpdate, null);
							flagInventoryDate = inventoryDate;
							break;
						}
					}
					//库存日期未添加对应日期库存记录
					if(flagInventoryDate==null){
						CarInventoryDate carInventoryDateForUpdate = new CarInventoryDate();
						carInventoryDateForUpdate.setCarInventoryId(carInventory.getCarInventoryId());
						carInventoryDateForUpdate.setCarModelId(carInventory.getCarModelId());
						//库存总数
						int inventoryTotalDate = carInventory.getInventoryTotal();
						//已预订数量
						int reserveQuantityDate = 0;
						//已租借数量
						int leasedQuantityDate = 0;
						//可用库存总数
						int availableInventoryDate = inventoryTotalDate;
						carInventoryDateForUpdate.setInventoryTotal(inventoryTotalDate);
						carInventoryDateForUpdate.setInventoryDate(dateStr1);
						carInventoryDateForUpdate.setAvailableInventory(availableInventoryDate-1);
						carInventoryDateForUpdate.setReserveQuantity(reserveQuantityDate);
						carInventoryDateForUpdate.setLeasedQuantity(leasedQuantityDate+1);
						carInventoryDateService.addCarInventoryDate(carInventoryDateForUpdate, null);
					}
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantInventoryDate);
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
	 * 根据商家ID,车型,日期段 释放预占库存
	 */
	@Override
	@Transactional
	public ResultInfo<MerchantInventoryDate> releaseLeasedQuantity(MerchantInventoryDate merchantInventoryDate,OrderDay orderDay,
			Operator operator) {
		ResultInfo<MerchantInventoryDate> resultInfo = new ResultInfo<MerchantInventoryDate>();
		// 传入参数无效时直接返回失败结果
		if (merchantInventoryDate == null || merchantInventoryDate.getInventoryDateStart() == null
				|| merchantInventoryDate.getInventoryDateEnd() == null 
				|| StringUtils.isBlank(merchantInventoryDate.getCarModelId())
				|| StringUtils.isBlank(merchantInventoryDate.getMerchantId())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
		} else {
			try {
				int days = (int)ECDateUtils.getDistanceDays(merchantInventoryDate.getInventoryDateStart(), merchantInventoryDate.getInventoryDateEnd()) + 1;
				if(days>0){
					if(orderDay.getOrderDuration()<days){
						Date inventoryDateEndTemp = ECDateUtils.parseDate(merchantInventoryDate.getInventoryDateEnd());
						Date inventoryDateEndDate = ECDateUtils.getDateBefore(inventoryDateEndTemp, 1);
						merchantInventoryDate.setInventoryDateEnd(ECDateUtils.formatDate(inventoryDateEndDate,"yyyy-MM-dd"));
					}
				}
				MerchantInventory merchantInventoryForQuery = new MerchantInventory();
				merchantInventoryForQuery.setCarModelId(merchantInventoryDate.getCarModelId());
				merchantInventoryForQuery.setMerchantId(merchantInventoryDate.getMerchantId());
				merchantInventoryForQuery.setCityId(orderDay.getCityId());
				MerchantInventory merchantInventory = merchantInventoryDao.getMerchantInventory(merchantInventoryForQuery);
				if (merchantInventory!=null) {
					//修改商家库存数据。
					SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd"); 
					MerchantInventoryDate updateInventoryDate = new MerchantInventoryDate();
					updateInventoryDate.setMerInventoryId(merchantInventory.getMerInventoryId());
					updateInventoryDate.setInventoryDateStart(myFmt.format(new Date()));
					updateInventoryDate.setInventoryDateEnd(merchantInventoryDate.getInventoryDateEnd());
					updateInventoryDate.setCarModelId(merchantInventoryDate.getCarModelId());
					updateInventoryDate.setMerchantId(merchantInventoryDate.getMerchantId());
					updateInventoryDate.setUpdateTime(ECDateUtils.getCurrentDateTime());
					merchantInventoryDateDao.releaseLeasedQuantity(updateInventoryDate);
				}
				CarInventory carInventory = carInventoryService.getCarInventoryByCarModelIdAndCity(merchantInventoryDate.getCarModelId(),orderDay.getCityId());
				//修改车辆总库存数据。
				CarInventory carInventoryUpdate = new CarInventory();
				carInventoryUpdate.setCarInventoryId(carInventory.getCarInventoryId());
				carInventoryUpdate.setCarModelId(merchantInventoryDate.getCarModelId());
				carInventoryUpdate.setAvailableInventory(carInventory.getAvailableInventory()+1);
				carInventoryService.updateCarInventory(carInventoryUpdate, null);
				
				//修改车辆库存日历数据。
				CarInventoryDate carInventoryDate = new CarInventoryDate();
				carInventoryDate.setCarInventoryId(carInventory.getCarInventoryId());
				carInventoryDate.setCarModelId(merchantInventoryDate.getCarModelId());
				carInventoryDate.setInventoryDateStart(ECDateUtils.getCurrentDate());
				Date inventoryDateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(merchantInventoryDate.getInventoryDateEnd());
				carInventoryDate.setInventoryDateEnd(inventoryDateEnd);
				carInventoryDate.setUpdateTime(ECDateUtils.getCurrentDateTime());
				carInventoryDateService.releaseLeasedQuantity(carInventoryDate, operator);
				
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantInventoryDate);
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
	 * 根据商家ID,车型,日期段 释放预占库存
	 */
	@Override
	@Transactional
	public ResultInfo<MerchantInventoryDate> releaseLeasedQuantityForDelayOrderDay(MerchantInventoryDate merchantInventoryDate,OrderDay orderDay,
			Operator operator) {
		ResultInfo<MerchantInventoryDate> resultInfo = new ResultInfo<MerchantInventoryDate>();
		// 传入参数无效时直接返回失败结果
		if (merchantInventoryDate == null || merchantInventoryDate.getInventoryDateStart() == null
				|| merchantInventoryDate.getInventoryDateEnd() == null 
				|| StringUtils.isBlank(merchantInventoryDate.getCarModelId())
				|| StringUtils.isBlank(merchantInventoryDate.getMerchantId())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
		} else {
			List<String> allDates = ECDateUtils.collectLocalDates(ECDateUtils.formatDate(orderDay.getAppointmentTakeTime(),"yyyy-MM-dd"), ECDateUtils.formatDate(orderDay.getAppointmentReturnTime(),"yyyy-MM-dd"));
			Date startTime = ECDateUtils.parseDate(merchantInventoryDate.getInventoryDateStart(), "yyyy-MM-dd");
			//处理要设置的自定义日期
			if(orderDay.getOrderDuration()==allDates.size()){
				startTime = ECDateUtils.getDateAfter(startTime, 1);
			}else{
				Date finishTime = ECDateUtils.getDateBefore(ECDateUtils.parseDate(merchantInventoryDate.getInventoryDateEnd()), 1);
				merchantInventoryDate.setInventoryDateEnd(ECDateUtils.formatDate(finishTime, "yyyy-MM-dd"));
			}
			try {
				MerchantInventory merchantInventoryForQuery = new MerchantInventory();
				merchantInventoryForQuery.setCarModelId(merchantInventoryDate.getCarModelId());
				merchantInventoryForQuery.setMerchantId(merchantInventoryDate.getMerchantId());
				merchantInventoryForQuery.setCityId(orderDay.getCityId());
				MerchantInventory merchantInventory = merchantInventoryDao.getMerchantInventory(merchantInventoryForQuery);
				if (merchantInventory!=null) {
					//修改商家库存数据。
					MerchantInventoryDate updateInventoryDate = new MerchantInventoryDate();
					updateInventoryDate.setMerInventoryId(merchantInventory.getMerInventoryId());
					updateInventoryDate.setInventoryDateStart(ECDateUtils.formatDate(startTime, "yyyy-MM-dd"));
					updateInventoryDate.setInventoryDateEnd(merchantInventoryDate.getInventoryDateEnd());
					updateInventoryDate.setCarModelId(merchantInventoryDate.getCarModelId());
					updateInventoryDate.setMerchantId(merchantInventoryDate.getMerchantId());
					updateInventoryDate.setUpdateTime(ECDateUtils.getCurrentDateTime());
					merchantInventoryDateDao.releaseLeasedQuantity(updateInventoryDate);
				}
				
				CarInventory carInventory = carInventoryService.getCarInventoryByCarModelIdAndCity(merchantInventoryDate.getCarModelId(), orderDay.getCityId());
				if (carInventory!=null) {
					//修改车辆库存日历数据。
					CarInventoryDate carInventoryDate = new CarInventoryDate();
					carInventoryDate.setCarInventoryId(carInventory.getCarInventoryId());
					carInventoryDate.setCarModelId(merchantInventoryDate.getCarModelId());
					carInventoryDate.setInventoryDateStart(startTime);
					Date inventoryDateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(merchantInventoryDate.getInventoryDateEnd());
					carInventoryDate.setInventoryDateEnd(inventoryDateEnd);
					carInventoryDate.setUpdateTime(ECDateUtils.getCurrentDateTime());
					carInventoryDateService.releaseLeasedQuantity(carInventoryDate, operator);
				}
				
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantInventoryDate);
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
	 * 
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}

	/**
	 * 为MerchantInventoryDate对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(MerchantInventoryDate obj) {
		if (obj != null) {
		}
	}

	@Override
	@Transactional
	public ResultInfo<String> editMerchantInventoryDate(String jsonData) {
		ResultInfo<String> result = new ResultInfo<>();
		List<MerchantInventoryDate> list = JsonUtils.parse2ListObject(jsonData, MerchantInventoryDate.class);
		if (list == null) {
			result.setCode(Constant.FAIL);
			result.setMsg("参数格式不对");
			return result;
		}
		String merInventoryId = null;
		String date = null;
		for (MerchantInventoryDate inventoryDate : list) {
			if (inventoryDate.getMerInventoryId() == null || inventoryDate.getMerInventoryId().trim().length() == 0) {
				result.setCode(Constant.FAIL);
				result.setMsg("商家库存编号不能为空");
				return result;
			}
			if (inventoryDate.getInventoryDate() == null || inventoryDate.getInventoryDate().trim().length() == 0) {
				result.setCode(Constant.FAIL);
				result.setMsg("日期不能为空");
				return result;
			}
			if (inventoryDate.getInventoryTotal() == null) {
				result.setCode(Constant.FAIL);
				result.setMsg("库存不能为空");
				return result;
			}
			if (merInventoryId == null) {
				merInventoryId = inventoryDate.getMerInventoryId();
			}
			if (date == null) {
				date = inventoryDate.getInventoryDate().substring(0, 7) + "___";
			}
		}
		MerchantInventory inventory = merchantInventoryDao.get(merInventoryId);
		MerchantInventoryDate merchantInventoryDate = new MerchantInventoryDate();
		merchantInventoryDate.setMerInventoryId(merInventoryId);
		merchantInventoryDate.setInventoryDate(date);
		Query q = new Query(merchantInventoryDate);
		List<MerchantInventoryDate> queryList = getMerchantInventoryDateList(q);
		for (MerchantInventoryDate inventoryDate : list) {
			inventoryDate.setCarModelId(inventory.getCarModelId());
			inventoryDate.setMerchantId(inventory.getMerchantId());
			inventoryDate.setCreateTime(new Date());
			for (MerchantInventoryDate queryInventoryDate : queryList) {
				if(inventoryDate.getInventoryDate().equals(queryInventoryDate.getInventoryDate())){
					inventoryDate.setInventoryDateId(queryInventoryDate.getInventoryDateId());
					int leasedQuantity = queryInventoryDate.getLeasedQuantity() == null? 0 : queryInventoryDate.getLeasedQuantity();
					inventoryDate.setAvailableInventory(inventoryDate.getInventoryTotal()-leasedQuantity);
					break;
				}
			}
			if(inventoryDate.getAvailableInventory() == null){
				inventoryDate.setAvailableInventory(inventoryDate.getInventoryTotal());
			}
			if(inventoryDate.getInventoryDateId() == null){
				inventoryDate.setInventoryDateId(generatePK());
			}
			if(inventoryDate.getLeasedQuantity() == null){
				inventoryDate.setLeasedQuantity(inventoryDate.getInventoryTotal() - inventoryDate.getAvailableInventory());
			}
		}
		merchantInventoryDateDao.deleteByDate(merInventoryId, date);
		merchantInventoryDateDao.addBatch(list);
		result.setCode(Constant.SECCUESS);
		return result;
	}

	@Override
	public ResultInfo<String> delMerchantInventoryDate(String merInventoryId, String date) {
		ResultInfo<String> result = new ResultInfo<>();
		merchantInventoryDateDao.deleteByDate(merInventoryId, date);
		result.setCode(Constant.SECCUESS);
		result.setMsg("ok");
		return result;
	}

	@Override
	public List<MerchantInventoryDate> getMerchantInventoryDateListByQ(Query q) {
		List<MerchantInventoryDate> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = merchantInventoryDateDao.getMerchantInventoryDateList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventoryDate>(0) : list;
		return list;
	}

	@Override
	public int updateMerchantInventoryDateByQ(MerchantInventoryDate merchantInventoryDate) {
		return merchantInventoryDateDao.updateMerchantInventoryDate(merchantInventoryDate);
	}

}
