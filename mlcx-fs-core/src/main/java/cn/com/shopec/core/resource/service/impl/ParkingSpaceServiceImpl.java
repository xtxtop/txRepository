package cn.com.shopec.core.resource.service.impl;

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
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.dao.ParkingSpaceDao;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.model.ParkingSpace;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.resource.service.ParkingSpaceService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 车位表 服务实现类
 */
@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

	private static final Log log = LogFactory.getLog(ParkingSpaceServiceImpl.class);
	
	@Resource
	private ParkingSpaceDao parkingSpaceDao;
	@Resource
	private ParkDao parkDao;
	
	@Resource
	private DataDictItemService dataDictItemService;
	
	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private ParkStatusService parkStatusService;
	/**
	 * 根据查询条件，查询并返回ParkingSpace的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkingSpace> getParkingSpaceList(Query q) {
		List<ParkingSpace> list = null;
		try {
			//已删除的不查出
			ParkingSpace parkingSpace = (ParkingSpace)q.getQ();
			if (parkingSpace != null) {
				parkingSpace.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = parkingSpaceDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkingSpace>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回ParkingSpace的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ParkingSpace> getParkingSpacePagedList(Query q) {
		PageFinder<ParkingSpace> page = new PageFinder<ParkingSpace>();
		
		List<ParkingSpace> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			ParkingSpace parkingSpace = (ParkingSpace)q.getQ();
			if (parkingSpace != null) {
				parkingSpace.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = parkingSpaceDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = parkingSpaceDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkingSpace>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * @author lj
	 * 后台
	 * 根据查询条件，分页查询并返回ParkingSpace的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ParkingSpace> getParkingSpacePageList(Query q) {
		PageFinder<ParkingSpace> page = new PageFinder<ParkingSpace>();
		
		List<ParkingSpace> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			ParkingSpace parkingSpace = (ParkingSpace)q.getQ();
			if (parkingSpace != null) {
				parkingSpace.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = parkingSpaceDao.pageParkingList(q);
			for(ParkingSpace parking:list){
				Park park=parkDao.get(parking.getParkNo());
				if(park!=null){
					//场站名称
					parking.setParkName(park.getParkName());
					//所属方类型（1、自有，2、租用）
					parking.setOwnerType(park.getOwnerType());
				}
			}
			//调用dao统计满足条件的记录总数
			rowCount = parkingSpaceDao.parkingCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkingSpace>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据主键，查询并返回一个ParkingSpace对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ParkingSpace getParkingSpace(String id) {
		ParkingSpace obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = parkingSpaceDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ParkingSpace对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkingSpace> getParkingSpaceByIds(String[] ids) {
		List<ParkingSpace> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = parkingSpaceDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkingSpace>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的ParkingSpace记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkingSpace> delParkingSpace(String id, Operator operator) {
		ResultInfo<ParkingSpace> resultInfo = new ResultInfo<ParkingSpace>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			ParkingSpace parkingSpace = new ParkingSpace();
			parkingSpace.setParkingSpaceNo(id);
			parkingSpace.setIsDeleted(Constant.DEL_STATE_YES);
			parkingSpace.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				parkingSpace.setOperatorType(operator.getOperatorType());
				parkingSpace.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = parkingSpaceDao.update(parkingSpace);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(parkingSpace);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条ParkingSpace记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param parkingSpace 新增的ParkingSpace数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkingSpace> addParkingSpace(ParkingSpace parkingSpace, Operator operator) {
		ResultInfo<ParkingSpace> resultInfo = new ResultInfo<ParkingSpace>();
		
		if (parkingSpace == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkingSpace = " + parkingSpace);
		} else {
			resultInfo = checkParkingSpaceNuber(parkingSpace);
			
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (parkingSpace.getParkingSpaceNo() == null) {
					parkingSpace.setParkingSpaceNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					parkingSpace.setOperatorType(operator.getOperatorType());
					parkingSpace.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				parkingSpace.setCreateTime(now);
				parkingSpace.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(parkingSpace);
				//设置城市
				if(parkingSpace.getCityId()!=null&&parkingSpace.getCityId().length()!=0){
					DataDictItem dataDictItem=dataDictItemService.getDataDictItem(parkingSpace.getCityId());
					if(dataDictItem!=null){
						parkingSpace.setCityName(dataDictItem.getItemValue());
					}
				}
				//调用Dao执行插入操作
				parkingSpaceDao.add(parkingSpace);
				//增加车位，对应场站状态增加车位
				ParkStatus parkStatus=parkStatusService.getParkStatus(parkingSpace.getParkNo());
				parkStatus.setParkingSpaces(parkStatus.getParkingSpaces() == null ? 1 : parkStatus.getParkingSpaces()+1);//为空时新增后为1，非空则+1
				parkStatusService.updateParkStatus(parkStatus, operator);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(parkingSpace);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}	
		}
		
		return resultInfo;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultInfo<ParkingSpace> checkParkingSpaceNuber(ParkingSpace parkingSpace) {
		ResultInfo<ParkingSpace> resultInfo = new ResultInfo<ParkingSpace>();
		if(parkingSpace.getParkingSpaceNo()!=null){//在修改时先校验场站是否与原先场站一致，如果一致则忽略场站车位数的校验
			ParkingSpace parkingSpaceTemp = parkingSpaceDao.get(parkingSpace.getParkingSpaceNo());
			if(parkingSpaceTemp == null){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(ParkConstant.FAIL_PARKSPACE_NOEXISTENCE);
				log.info(Constant.ERR_MSG_INVALID_ARG + " parkingSpace = " + parkingSpace);
			}
			String parkNo =  parkingSpaceTemp.getParkNo();
			if(parkNo != null && parkNo.equals(parkingSpace.getParkNo())){
				resultInfo.setCode(Constant.SECCUESS);
				return resultInfo;
			}
		}
		//开始校验场站车位数是否有空余
		Park park = new Park();
		park.setParkNo(parkingSpace.getParkNo());
		park = parkDao.get(parkingSpace.getParkNo());
		if(park == null){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(ParkConstant.FAIL_PARKSPACE_NOEXISTENCE);
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkingSpace = " + parkingSpace);
		}else{
			int parkingSpaceNumber = park.getParkingSpaceNumber() == null ? 0 : park.getParkingSpaceNumber();
			ParkingSpace parkingSpaceTemp = new ParkingSpace();
			parkingSpaceTemp.setParkNo(parkingSpace.getParkNo());
			List<ParkingSpace> parkingSpaces = parkingSpaceDao.queryAll(new Query(parkingSpaceTemp));
			if(parkingSpaces != null && parkingSpaces.size() + 1 > parkingSpaceNumber){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(ParkConstant.FAIL_PARKSPACE_FULL_ADD);
				log.info(Constant.ERR_MSG_INVALID_ARG + " parkingSpace = " + parkingSpace);
			}else{
				resultInfo.setCode(Constant.SECCUESS);
			}
		}
		return resultInfo;
	}			
	
	/**
	 * 根据主键，更新一条ParkingSpace记录
	 * @param parkingSpace 更新的ParkingSpace数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkingSpace> updateParkingSpace(ParkingSpace parkingSpace, Operator operator) {
		ResultInfo<ParkingSpace> resultInfo = new ResultInfo<ParkingSpace>();
		
		if (parkingSpace == null || parkingSpace.getParkingSpaceNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkingSpace = " + parkingSpace);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					parkingSpace.setOperatorType(operator.getOperatorType());
					parkingSpace.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				parkingSpace.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = parkingSpaceDao.update(parkingSpace);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(parkingSpace);
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
	 * @author lj
	 * 后台更新
	 * 根据主键，更新一条ParkingSpace记录
	 * @param parkingSpace 更新的ParkingSpace数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkingSpace> updateParkingSpaces(ParkingSpace parkingSpace, Operator operator) {
		ResultInfo<ParkingSpace> resultInfo = new ResultInfo<ParkingSpace>();
		
		if (parkingSpace == null || parkingSpace.getParkingSpaceNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkingSpace = " + parkingSpace);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					parkingSpace.setOperatorType(operator.getOperatorType());
					parkingSpace.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				parkingSpace.setUpdateTime(new Date());
				//设置城市
				if(parkingSpace.getCityId()!=null&&parkingSpace.getCityId().length()!=0){
					DataDictItem dataDictItem=dataDictItemService.getDataDictItem(parkingSpace.getCityId());
					if(dataDictItem!=null){
					parkingSpace.setCityName(dataDictItem.getItemValue());
					}
				}
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = parkingSpaceDao.update(parkingSpace);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(parkingSpace);
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
		return primarykeyService.getValueByBizType(PrimarykeyConstant.parkingSpaceType)+"";
	}
	
	/**
	 * 为ParkingSpace对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ParkingSpace obj) {
		if (obj != null) {
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(0);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

}
