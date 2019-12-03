package cn.com.shopec.core.dailyrental.service.impl;

import java.text.SimpleDateFormat;
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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.dao.CarModelDao;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDao;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDateDao;
import cn.com.shopec.core.dailyrental.model.CarInventory;
import cn.com.shopec.core.dailyrental.model.CarInventoryDate;
import cn.com.shopec.core.car.model.CarModel;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.MerchantInventoryDate;
import cn.com.shopec.core.dailyrental.service.CarInventoryDateService;
import cn.com.shopec.core.dailyrental.service.CarInventoryService;
import cn.com.shopec.core.dailyrental.service.MerchantInventoryService;

/**
 * 商家库存表 服务实现类
 */
@Service
public class MerchantInventoryServiceImpl implements MerchantInventoryService {

	private static final Log log = LogFactory.getLog(MerchantInventoryServiceImpl.class);
	
	@Resource
	private MerchantInventoryDao merchantInventoryDao;
	
	@Resource
	private CarInventoryService carInventoryService;

	@Resource
	private CarInventoryDateService carInventoryDateService;
	
	@Resource
	private CarModelDao carModelDao;
	@Resource
	private MerchantInventoryDateDao merchantInventoryDateDao;

	/**
	 * 根据查询条件，查询并返回MerchantInventory的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantInventory> getMerchantInventoryList(Query q) {
		List<MerchantInventory> list = null;
		try {
			//直接调用Dao方法进行查询
			list = merchantInventoryDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventory>(0) : list;
		return list; 
	}
	@Override
	public List<MerchantInventory> getMerchantInventoryCarModel(Query q) {
		List<MerchantInventory> list = null;
		try {
			//直接调用Dao方法进行查询
			list = merchantInventoryDao.getMerchantInventoryCarModel(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventory>(0) : list;
		return list; 
	}
	/**
	 * 根据查询条件，分页查询并返回MerchantInventory的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MerchantInventory> getMerchantInventoryPagedList(Query q) {
		PageFinder<MerchantInventory> page = new PageFinder<MerchantInventory>();
		
		List<MerchantInventory> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantInventoryDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantInventoryDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventory>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个MerchantInventory对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MerchantInventory getMerchantInventory(String id) {
		MerchantInventory obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = merchantInventoryDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MerchantInventory对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantInventory> getMerchantInventoryByIds(String[] ids) {
		List<MerchantInventory> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = merchantInventoryDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventory>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MerchantInventory记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantInventory> delMerchantInventory(String id, Operator operator) {
		ResultInfo<MerchantInventory> resultInfo = new ResultInfo<MerchantInventory>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = merchantInventoryDao.delete(id);
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
	 * 新增一条MerchantInventory记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchantInventory 新增的MerchantInventory数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantInventory> addMerchantInventory(MerchantInventory merchantInventory, Operator operator) {
		ResultInfo<MerchantInventory> resultInfo = new ResultInfo<MerchantInventory>();
		
		if (merchantInventory == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantInventory = " + merchantInventory);
		} else {
			try {
				CarModel queryCarModel = new CarModel();
				queryCarModel.setCarBrandId(merchantInventory.getCarBrandId());
				queryCarModel.setCarSeriesId(merchantInventory.getCarSeriesId());
				queryCarModel.setCarPeriodId(merchantInventory.getCarPeriodId());
				queryCarModel.setCarType(merchantInventory.getCarType());
				
				List<CarModel> carModelList = carModelDao.queryAll(new Query(queryCarModel));
				if(carModelList.size() == 0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("车型不存在");
					return resultInfo;
				}
				MerchantInventory queryMerchantInventory = new MerchantInventory();
				queryMerchantInventory.setMerchantId(merchantInventory.getMerchantId());
				queryMerchantInventory.setCarModelId(carModelList.get(0).getCarModelId());
				queryMerchantInventory.setCityId(merchantInventory.getCityId());
				List<MerchantInventory> merchantInventoryList = getMerchantInventoryList(new Query(queryMerchantInventory));
				if(merchantInventoryList != null && merchantInventoryList.size() > 0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("车型库存已存在");
					return resultInfo;
				}
				CarModel carModel = carModelList.get(0);
				merchantInventory.setCarModelId(carModel.getCarModelId());
				merchantInventory.setCarModelName(carModel.getCarModelName());
				merchantInventory.setCarBrandId(carModel.getCarBrandId());
				merchantInventory.setCarBrandName(carModel.getCarBrandName());
				merchantInventory.setCarSeriesId(carModel.getCarSeriesId());
				merchantInventory.setCarSeriesName(carModel.getCarSeriesName());
				merchantInventory.setCarType(carModel.getCarType());
				merchantInventory.setDisplacement(carModel.getDisplacement());
				merchantInventory.setGearBox(carModel.getGearBox());
				merchantInventory.setBoxType(Integer.parseInt(carModel.getBoxType()));
				merchantInventory.setSeatNumber(carModel.getSeatNumber());
				merchantInventory.setCarModelUrl(carModel.getCarModelUrl());
				merchantInventory.setCityId(merchantInventory.getCityId());
				merchantInventory.setCityName(merchantInventory.getCityName());
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (merchantInventory.getMerInventoryId() == null) {
					merchantInventory.setMerInventoryId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					merchantInventory.setOperatorType(operator.getOperatorType());
					merchantInventory.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				merchantInventory.setCreateTime(now);
				merchantInventory.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(merchantInventory);
				
				//调用Dao执行插入操作
				merchantInventoryDao.add(merchantInventory);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantInventory);
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
	 * 商家车型库存发布
	 * @param merchantInventory
	 * @return
	 */
	public ResultInfo<MerchantInventory> publishMerchantInventory(MerchantInventory merchantInventory){
		ResultInfo<MerchantInventory> resultInfo = new ResultInfo<MerchantInventory>();
		try {
			MerchantInventory queryMerchantInventory = new MerchantInventory();
			queryMerchantInventory.setMerInventoryId(merchantInventory.getMerInventoryId());
			MerchantInventory mInventory = merchantInventoryDao.get(merchantInventory.getMerInventoryId());
			if(mInventory == null ){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("商家车型库存不存在");
				return resultInfo;
			}
			CarModel carModel = carModelDao.get(mInventory.getCarModelId());
			if(carModel == null){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("平台不存在此车型");
				return resultInfo;
			}
			CarInventory queryCarInventory = new CarInventory();
			queryCarInventory.setCarModelId(mInventory.getCarModelId());
			queryCarInventory.setCityId(mInventory.getCityId());
			List<CarInventory> carInventoryList = carInventoryService.getCarInventoryList(new Query(queryCarInventory));
			if(carInventoryList != null && carInventoryList.size() > 1){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("平台车型库存存在多笔记录");
				return resultInfo;
			}
			if(carInventoryList == null || carInventoryList.size() == 0){
				CarInventory carInventory = new CarInventory();
				carInventory.setCarBrandId(carModel.getCarBrandId());
				carInventory.setCarBrandName(carModel.getCarBrandName());
				carInventory.setCarModelId(carModel.getCarModelId());
				carInventory.setCarModelName(carModel.getCarModelName());
				carInventory.setCarSeriesId(carModel.getCarSeriesId());
				carInventory.setCarSeriesName(carModel.getCarSeriesName());
				carInventory.setCarType(carModel.getCarType());
				carInventory.setInventoryTotal(mInventory.getInventoryDay());
				carInventory.setLeasedQuantity(0);
				carInventory.setReserveQuantity(0);
				carInventory.setAvailableInventory(mInventory.getInventoryDay());
				carInventory.setCityId(mInventory.getCityId());
				carInventory.setCityName(mInventory.getCityName());
				carInventoryService.addCarInventory(carInventory, null);
				
				MerchantInventory merchantInventoryForUpdate = new MerchantInventory();
				merchantInventoryForUpdate.setMerInventoryId(merchantInventory.getMerInventoryId());
				merchantInventoryForUpdate.setIsPublish(1);
				merchantInventoryDao.update(merchantInventoryForUpdate);
				
				//修改商家库存数据。
				MerchantInventoryDate updateInventoryDate = new MerchantInventoryDate();
				updateInventoryDate.setCarModelId(mInventory.getCarModelId());
				updateInventoryDate.setMerchantId(mInventory.getMerchantId());
				updateInventoryDate.setMerInventoryId(mInventory.getMerInventoryId());;
				merchantInventoryDateDao.updateMerchantInventoryDateForPublish(updateInventoryDate);
			}else{
				CarInventory carInventory = carInventoryList.get(0);
				int inventoryTotal = carInventory.getInventoryTotal() == null? 0 : carInventory.getInventoryTotal();
				inventoryTotal += mInventory.getInventoryDay();
				carInventory.setInventoryTotal(inventoryTotal);
				carInventory.setAvailableInventory(carInventory.getAvailableInventory()+mInventory.getInventoryDay());
				carInventoryService.updateCarInventory(carInventory, null);
				
				CarInventoryDate carInventoryDate = new CarInventoryDate();
				carInventoryDate.setCarInventoryId(carInventory.getCarInventoryId());
				carInventoryDate.setInventoryTotal(mInventory.getInventoryDay());
				carInventoryDate.setAvailableInventory(mInventory.getInventoryDay());
				carInventoryDateService.updateBatchCarInventoryDate(carInventoryDate, null);
				
				MerchantInventory merchantInventoryForUpdate = new MerchantInventory();
				merchantInventoryForUpdate.setMerInventoryId(merchantInventory.getMerInventoryId());
				merchantInventoryForUpdate.setIsPublish(1);
				merchantInventoryDao.update(merchantInventoryForUpdate);
				
				//修改商家库存数据。
				MerchantInventoryDate updateInventoryDate = new MerchantInventoryDate();
				updateInventoryDate.setCarModelId(mInventory.getCarModelId());
				updateInventoryDate.setMerchantId(mInventory.getMerchantId());
				updateInventoryDate.setMerInventoryId(mInventory.getMerInventoryId());
				merchantInventoryDateDao.updateMerchantInventoryDateForPublish(updateInventoryDate);
			}
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(merchantInventory);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}	
	
		return resultInfo;
	}
	/**
	 * 根据主键，更新一条MerchantInventory记录
	 * @param merchantInventory 更新的MerchantInventory数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantInventory> updateMerchantInventory(MerchantInventory merchantInventory, Operator operator) {
		ResultInfo<MerchantInventory> resultInfo = new ResultInfo<MerchantInventory>();
		
		if (merchantInventory == null || merchantInventory.getMerInventoryId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantInventory = " + merchantInventory);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					merchantInventory.setOperatorType(operator.getOperatorType());
					merchantInventory.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				merchantInventory.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantInventoryDao.update(merchantInventory);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchantInventory);
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
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为MerchantInventory对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MerchantInventory obj) {
		if (obj != null) {
			if(obj.getIsPublish()==null){
				obj.setIsPublish(0);
			}
		}
	}
	/**
	 * 根据车型查找商家库存情况-后台
	 * @param carModelId
	 * @return
	 */
	@Override
	public PageFinder<MerchantInventory> getMerchantInventoryByCarModelId(Query q) {
		PageFinder<MerchantInventory> page = new PageFinder<MerchantInventory>();
		
		List<MerchantInventory> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantInventoryDao.getMerchantInventoryByCarModelId(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantInventoryDao.countForMgt(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantInventory>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}
	/**
	 * 根据车型查找有对应车型库存的商家id
	 * @param carModelId
	 * @return
	 */
	@Override
	public List<String> getMerchantsByCarModelId(String carModelId,String cityId) {
		List<String> list = null;
		if (carModelId == null || carModelId.length() == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " carModelId is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = merchantInventoryDao.getMerchantsByCarModelId(carModelId,cityId);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<String>(0) : list;

		return list;
	}

	@Override
	public Long getMerchantsByCarCount(String carModelId, String merchantId, String cityId) {
		return merchantInventoryDao.getMerchantsByCarCount(carModelId,merchantId,cityId);
	}

	@Override
	public MerchantInventory getMerchantInventory(MerchantInventory merchantInventory) {
		return merchantInventoryDao.getMerchantInventory(merchantInventory);
	}

	@Override
	public ResultInfo<MerchantInventory> updateMerchantInventoryByCarModelId(MerchantInventory merchantInventory) {
		ResultInfo<MerchantInventory> resultInfo = new ResultInfo<MerchantInventory>();
		if (merchantInventory == null || merchantInventory.getCarModelId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantInventory = " + merchantInventory);
		} else {
			try {
				//设置更新时间为当前时间
				merchantInventory.setUpdateTime(new Date());
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantInventoryDao.updateMerchantInventoryByCarModelId(merchantInventory);;			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchantInventory);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}
		
		return resultInfo;
	}

}
