package cn.com.shopec.core.dailyrental.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.dao.CarInventoryDao;
import cn.com.shopec.core.dailyrental.dao.CarInventoryDateDao;
import cn.com.shopec.core.dailyrental.dao.MerchantDao;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDao;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDateDao;
import cn.com.shopec.core.dailyrental.model.CarInventory;
import cn.com.shopec.core.dailyrental.model.CarInventoryDate;
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.MerchantInventoryDate;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.core.system.dao.SysRegionDao;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictItemService;

/**
 * 租赁商家表 服务实现类
 */
@Service
public class MerchantServiceImpl implements MerchantService {

	private static final Log log = LogFactory.getLog(MerchantServiceImpl.class);
	
	@Resource
	private MerchantDao merchantDao;

	@Resource
	private SysRegionDao sysRegionDao;
	@Resource
	private MerchantInventoryDao merchantInventoryDao;
	@Resource
	private DataDictItemService dataDictItemService;
//	@Value("${city}")
//	private String city;
	@Resource
	private MerchantInventoryDateDao merchantInventoryDateDao;
	@Resource
	private CarInventoryDao carInventoryDao;
	@Resource
	private CarInventoryDateDao carInventoryDateDao;
	
	/**
	 * 根据查询条件，查询并返回Merchant的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Merchant> getMerchantList(Query q) {
		List<Merchant> list = null;
		try {
			//直接调用Dao方法进行查询
			list = merchantDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Merchant>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回Merchant的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Merchant> getMerchantPagedList(Query q) {
		PageFinder<Merchant> page = new PageFinder<Merchant>();
		
		List<Merchant> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Merchant>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个Merchant对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Merchant getMerchant(String id) {
		Merchant obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = merchantDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Merchant对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Merchant> getMerchantByIds(String[] ids) {
		List<Merchant> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = merchantDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Merchant>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的Merchant记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Merchant> delMerchant(String id, Operator operator) {
		ResultInfo<Merchant> resultInfo = new ResultInfo<Merchant>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = merchantDao.delete(id);
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
	 * 新增一条Merchant记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchant 新增的Merchant数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Merchant> addMerchant(Merchant merchant, Operator operator) {
		ResultInfo<Merchant> resultInfo = new ResultInfo<Merchant>();
		
		if (merchant == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchant = " + merchant);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (merchant.getMerchantId() == null) {
					merchant.setMerchantId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					merchant.setOperatorType(operator.getOperatorType());
					merchant.setOperatorId(operator.getOperatorId());
				}
				
				if(merchant.getAddrRegion1Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion1Id());
					if(sysRegion != null){
						merchant.setAddrRegion1Name(sysRegion.getRegionName());
					}
				}
				
				if(merchant.getAddrRegion2Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion2Id());
					if(sysRegion != null){
						merchant.setAddrRegion2Name(sysRegion.getRegionName());
					}
				}
				
				if(merchant.getAddrRegion3Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion3Id());
					if(sysRegion != null){
						merchant.setAddrRegion3Name(sysRegion.getRegionName());
					}
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				merchant.setCreateTime(now);
				merchant.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(merchant);
				
				//调用Dao执行插入操作
				merchantDao.add(merchant);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchant);
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
	 * 根据主键，更新一条Merchant记录
	 * @param merchant 更新的Merchant数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Merchant> updateMerchant(Merchant merchant, Operator operator) {
		ResultInfo<Merchant> resultInfo = new ResultInfo<Merchant>();
		
		if (merchant == null || merchant.getMerchantId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchant = " + merchant);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					merchant.setOperatorType(operator.getOperatorType());
					merchant.setOperatorId(operator.getOperatorId());
				}
				
				if(merchant.getAddrRegion1Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion1Id());
					if(sysRegion != null){
						merchant.setAddrRegion1Name(sysRegion.getRegionName());
					}
				}
				
				if(merchant.getAddrRegion2Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion2Id());
					if(sysRegion != null){
						merchant.setAddrRegion2Name(sysRegion.getRegionName());
					}
				}
				
				if(merchant.getAddrRegion3Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion3Id());
					if(sysRegion != null){
						merchant.setAddrRegion3Name(sysRegion.getRegionName());
					}
				}
				
				//设置更新时间为当前时间
				merchant.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantDao.update(merchant);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchant);
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
	 * 根据主键，更新一条Merchant记录
	 * @param merchant 更新的Merchant数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Merchant> updateMerchantForMgt(Merchant merchant, Operator operator) {
		ResultInfo<Merchant> resultInfo = new ResultInfo<Merchant>();
		
		if (merchant == null || merchant.getMerchantId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchant = " + merchant);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					merchant.setOperatorType(operator.getOperatorType());
					merchant.setOperatorId(operator.getOperatorId());
				}
				
				if(merchant.getAddrRegion1Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion1Id());
					if(sysRegion != null){
						merchant.setAddrRegion1Name(sysRegion.getRegionName());
					}
				}
				
				if(merchant.getAddrRegion2Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion2Id());
					if(sysRegion != null){
						merchant.setAddrRegion2Name(sysRegion.getRegionName());
					}
				}
				
				if(merchant.getAddrRegion3Id() != null){
					SysRegion sysRegion = sysRegionDao.get(merchant.getAddrRegion3Id());
					if(sysRegion != null){
						merchant.setAddrRegion3Name(sysRegion.getRegionName());
					}
				}
				
				//设置更新时间为当前时间
				merchant.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantDao.updateForMgt(merchant);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchant);
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
	 * 为Merchant对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Merchant obj) {
		if (obj != null) {
		    if (obj.getIsOffsiteReturncar() == null) {
		    	obj.setIsOffsiteReturncar(0);
		    }
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(0);
		    }
		    if (obj.getCencorStatus() == null) {
		    	obj.setCencorStatus(0);
		    }
		    if (obj.getIsDelete() == null) {
		    	obj.setIsDelete(0);
		    }
		}
	}

	@Override
	public ResultInfo<MerchantInventory> merchantInventoryUpdate(String merchantId, String carModelId,String cityId) {
		ResultInfo<MerchantInventory> result = new ResultInfo<MerchantInventory>();
		if(merchantId==null||"".equals(merchantId)){
			result.setCode(Constant.FAIL);
			result.setMsg("商家编号不能为空");
			return result;
		}
		MerchantInventory merchantInventoryQuery = new MerchantInventory();
		merchantInventoryQuery.setMerchantId(merchantId);
		merchantInventoryQuery.setCarModelId(carModelId);
//		DataDictItem dataDictItem = dataDictItemService.getItemValue(city,"CITY");
//		if(dataDictItem != null){
//			merchantInventoryQuery.setCityId(dataDictItem.getDataDictItemId());
//		}
		merchantInventoryQuery.setCityId(cityId);
		MerchantInventory merchantInventory = merchantInventoryDao.getMerchantInventory(merchantInventoryQuery);
		if(merchantInventory==null){
			result.setCode(Constant.FAIL);
			result.setMsg("商家库存不存在");
			return result;
		}
		MerchantInventory merchantInventoryUpdate = new MerchantInventory();
		merchantInventoryUpdate.setMerInventoryId(merchantInventory.getMerInventoryId());
		merchantInventoryUpdate.setInventoryDay(0);
		int rows = merchantInventoryDao.update(merchantInventoryUpdate);
		if(rows==0){
			result.setCode(Constant.FAIL);
			result.setMsg("商家车型下线失败");
			return result;
		}
		MerchantInventoryDate merchantInventoryDateQuery = new MerchantInventoryDate();
		merchantInventoryDateQuery.setMerInventoryId(merchantInventory.getMerInventoryId());
		List<MerchantInventoryDate> merchantInventoryDateList = merchantInventoryDateDao.getMerchantInventoryDateList(new Query(merchantInventoryDateQuery));
		int size = merchantInventoryDateList.size();
		if(size>0){
			//修改商家库存日历
			MerchantInventoryDate merchantInventoryDateUpdate = new MerchantInventoryDate();
			merchantInventoryDateUpdate.setCarModelId(carModelId);
			merchantInventoryDateUpdate.setMerchantId(merchantId);
			merchantInventoryDateUpdate.setAvailableInventory(0);
			merchantInventoryDateUpdate.setInventoryDateStart(merchantInventoryDateList.get(0).getInventoryDate());
			merchantInventoryDateUpdate.setInventoryDateEnd(merchantInventoryDateList.get(size-1).getInventoryDate());
			rows = merchantInventoryDateDao.updateMerchantInventoryDate(merchantInventoryDateUpdate);
			if(rows==0){
				result.setCode(Constant.FAIL);
				result.setMsg("商家车型下线失败");
				return result;
			}
			//修改车辆库存
			CarInventory carInventory = carInventoryDao.getCarInventoryByCarModelIdAndCity(carModelId,cityId);
			if(carInventory==null){
				result.setCode(Constant.FAIL);
				result.setMsg("商家车型下线失败");
				return result;
			}
			CarInventory carInventoryUpdate = new CarInventory();
			carInventoryUpdate.setCarInventoryId(carInventory.getCarInventoryId());
			carInventoryUpdate.setAvailableInventory(carInventory.getAvailableInventory()-merchantInventory.getInventoryDay());
			rows = carInventoryDao.update(carInventoryUpdate);
			if(rows==0){
				result.setCode(Constant.FAIL);
				result.setMsg("商家车型下线失败");
				return result;
			}
			CarInventoryDate carInventoryDateQuery = new CarInventoryDate();
			carInventoryDateQuery.setCarInventoryId(carInventory.getCarInventoryId());
			List<CarInventoryDate> lists = carInventoryDateDao.queryAll(new Query(carInventoryDateQuery));
			if(lists.size()>0){
				CarInventoryDate carInventoryDateUpdate = new CarInventoryDate();
				carInventoryDateUpdate.setCarInventoryId(carInventory.getCarInventoryId());
				carInventoryDateUpdate.setCarModelId(carModelId);
				carInventoryDateUpdate.setAvailableInventory(0);
				rows = carInventoryDateDao.updateCarInventoryDate(carInventoryDateUpdate);
				if(rows>0){
					result.setCode(Constant.SECCUESS);
					result.setMsg("商家车型下线成功");
					return result;
				}else{
					result.setCode(Constant.FAIL);
					result.setMsg("商家车型下线失败");
					return result;
				}
			}else{
				result.setCode(Constant.SECCUESS);
				result.setMsg("商家车型下线成功");
				return result;
			}
		}else{
			//修改车辆库存
			CarInventory carInventory = carInventoryDao.getCarInventoryByCarModelIdAndCity(carModelId,cityId);
			if(carInventory==null){
				result.setCode(Constant.FAIL);
				result.setMsg("商家车型下线失败");
				return result;
			}
			CarInventory carInventoryUpdate = new CarInventory();
			carInventoryUpdate.setCarInventoryId(carInventory.getCarInventoryId());
			carInventoryUpdate.setAvailableInventory(carInventory.getAvailableInventory()-merchantInventory.getInventoryDay());
			rows = carInventoryDao.update(carInventoryUpdate);
			if(rows==0){
				result.setCode(Constant.FAIL);
				result.setMsg("商家车型下线失败");
				return result;
			}
			CarInventoryDate carInventoryDateQuery = new CarInventoryDate();
			carInventoryDateQuery.setCarInventoryId(carInventory.getCarInventoryId());
			List<CarInventoryDate> lists = carInventoryDateDao.queryAll(new Query(carInventoryDateQuery));
			if(lists.size()>0){
				CarInventoryDate carInventoryDateUpdate = new CarInventoryDate();
				carInventoryDateUpdate.setCarInventoryId(carInventory.getCarInventoryId());
				carInventoryDateUpdate.setCarModelId(carModelId);
				carInventoryDateUpdate.setAvailableInventory(0);
				rows = carInventoryDateDao.updateCarInventoryDate(carInventoryDateUpdate);
				if(rows>0){
					result.setCode(Constant.SECCUESS);
					result.setMsg("商家车型下线成功");
					return result;
				}else{
					result.setCode(Constant.FAIL);
					result.setMsg("商家车型下线失败");
					return result;
				}
			}else{
				result.setCode(Constant.SECCUESS);
				result.setMsg("商家车型下线成功");
				return result;
			}
		}
	}

	@Override
	public ResultInfo<Merchant> merchantInventoryOffLine(MerchantInventory mInventory) {
		ResultInfo<Merchant> result = new ResultInfo<Merchant>();
		MerchantInventory merchantInventoryQuery = new MerchantInventory();
		merchantInventoryQuery.setMerchantId(mInventory.getMerchantId());
		merchantInventoryQuery.setMerInventoryId(mInventory.getMerInventoryId());
		merchantInventoryQuery.setCityId(mInventory.getCityId());
		List<MerchantInventory> merchantInventorys = merchantInventoryDao.queryAll(new Query(merchantInventoryQuery));
		if(merchantInventorys==null){
			result.setCode(Constant.FAIL);
			result.setMsg("商家库存不存在");
			return result;
		}
		MerchantInventory merchantInventoryUpdate = new MerchantInventory();
		merchantInventoryUpdate.setMerchantId(mInventory.getMerchantId());
		merchantInventoryUpdate.setMerInventoryId(mInventory.getMerInventoryId());
		merchantInventoryUpdate.setIsPublish(0);
		merchantInventoryUpdate.setUpdateTime(ECDateUtils.getCurrentDateTime());
		int rows = merchantInventoryDao.update(merchantInventoryUpdate);
		if(rows==0){
			result.setCode(Constant.FAIL);
			result.setMsg("商家车型下线失败");
			return result;
		}
		MerchantInventoryDate merchantInventoryDateQuery = new MerchantInventoryDate();
		merchantInventoryDateQuery.setMerchantId(mInventory.getMerchantId());
		merchantInventoryDateQuery.setMerInventoryId(mInventory.getMerInventoryId());
		List<MerchantInventoryDate> merchantInventoryDateList = merchantInventoryDateDao.getMerchantInventoryDateList(new Query(merchantInventoryDateQuery));
		int size = merchantInventoryDateList.size();
		if(size>0){
			//修改商家库存日历
			for(MerchantInventoryDate temp:merchantInventoryDateList){
				MerchantInventoryDate merchantInventoryDateUpdate = new MerchantInventoryDate();
				merchantInventoryDateUpdate.setAvailableInventoryOfOffline(temp.getAvailableInventory());;
				merchantInventoryDateUpdate.setInventoryDateId(temp.getInventoryDateId());
				merchantInventoryDateUpdate.setAvailableInventory(0);
				rows = merchantInventoryDateDao.update(merchantInventoryDateUpdate);
				if(rows==0){
					result.setCode(Constant.FAIL);
					result.setMsg("商家车型下线失败");
					return result;
				}
				
			}
			for(MerchantInventory merchantInventory:merchantInventorys){
				//修改车辆库存
				CarInventory carInventory = carInventoryDao.getCarInventoryByCarModelIdAndCity(merchantInventory.getCarModelId(),mInventory.getCityId());
				if(carInventory!=null){
					CarInventory carInventoryUpdate = new CarInventory();
					carInventoryUpdate.setCarInventoryId(carInventory.getCarInventoryId());
					int availableInventory = carInventory.getAvailableInventory()-merchantInventory.getInventoryDay();
					if(availableInventory<0){
						availableInventory = 0;
					}
					int inventoryTotal = carInventory.getInventoryTotal()-merchantInventory.getInventoryDay();
					if (inventoryTotal<0) {
						inventoryTotal = 0;
					}
					carInventoryUpdate.setInventoryTotal(inventoryTotal);
					carInventoryUpdate.setAvailableInventory(availableInventory);
					rows = carInventoryDao.update(carInventoryUpdate);
					if(rows==0){
						result.setCode(Constant.FAIL);
						result.setMsg("商家车型下线失败");
						return result;
					}
					CarInventoryDate carInventoryDateQuery = new CarInventoryDate();
					carInventoryDateQuery.setCarInventoryId(carInventory.getCarInventoryId());
					List<CarInventoryDate> lists = carInventoryDateDao.queryAll(new Query(carInventoryDateQuery));
					if(lists.size()>0){
						CarInventoryDate carInventoryDateUpdate = new CarInventoryDate();
						carInventoryDateUpdate.setCarInventoryId(carInventory.getCarInventoryId());
						//sql中为AVAILABLE_INVENTORY-#{availableInventory}，INVENTORY_TOTAL-#{availableInventory}
						carInventoryDateUpdate.setAvailableInventory(merchantInventory.getInventoryDay());
						rows = carInventoryDateDao.updateCarInventoryDate(carInventoryDateUpdate);
						if(rows>0){
							result.setCode(Constant.SECCUESS);
							result.setMsg("商家车型下线成功");
							return result;
						}else{
							result.setCode(Constant.FAIL);
							result.setMsg("商家车型下线失败");
							return result;
						}
					}else{
						result.setCode(Constant.SECCUESS);
						result.setMsg("商家车型下线成功");
						return result;
					}
				}
			}
		}else{
			for(MerchantInventory merchantInventory:merchantInventorys){
				//修改车辆库存
				CarInventory carInventory = carInventoryDao.getCarInventoryByCarModelIdAndCity(merchantInventory.getCarModelId(),mInventory.getCityId());
				if(carInventory!=null){
					CarInventory carInventoryUpdate = new CarInventory();
					carInventoryUpdate.setCarInventoryId(carInventory.getCarInventoryId());
					int availableInventory = carInventory.getAvailableInventory()-merchantInventory.getInventoryDay();
					if(availableInventory<0){
						availableInventory = 0;
					}
					int inventoryTotal = carInventory.getInventoryTotal()-merchantInventory.getInventoryDay();
					if (inventoryTotal<0) {
						inventoryTotal = 0;
					}
					carInventoryUpdate.setInventoryTotal(inventoryTotal);
					carInventoryUpdate.setAvailableInventory(availableInventory);
					rows = carInventoryDao.update(carInventoryUpdate);
					if(rows==0){
						result.setCode(Constant.FAIL);
						result.setMsg("商家车型下线失败");
						return result;
					}
					CarInventoryDate carInventoryDateQuery = new CarInventoryDate();
					carInventoryDateQuery.setCarInventoryId(carInventory.getCarInventoryId());
					List<CarInventoryDate> lists = carInventoryDateDao.queryAll(new Query(carInventoryDateQuery));
					if(lists.size()>0){
						CarInventoryDate carInventoryDateUpdate = new CarInventoryDate();
						carInventoryDateUpdate.setCarInventoryId(carInventory.getCarInventoryId());
						carInventoryDateUpdate.setAvailableInventory(merchantInventory.getInventoryDay());
						rows = carInventoryDateDao.updateCarInventoryDate(carInventoryDateUpdate);
						if(rows>0){
							result.setCode(Constant.SECCUESS);
							result.setMsg("商家车型下线成功");
							return result;
						}else{
							result.setCode(Constant.FAIL);
							result.setMsg("商家车型下线失败");
							return result;
						}
					}else{
						result.setCode(Constant.SECCUESS);
						result.setMsg("商家车型下线成功");
						return result;
					}
				}
			}
		}
		return result;
	}
}
