package cn.com.shopec.core.car.service.impl;

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
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.car.dao.CarFaultDao;
import cn.com.shopec.core.car.model.CarFault;
import cn.com.shopec.core.car.service.CarFaultService;

/**
 * CarFault 服务实现类
 */
@Service
public class CarFaultServiceImpl implements CarFaultService {

	private static final Log log = LogFactory.getLog(CarFaultServiceImpl.class);
	
	@Resource
	private CarFaultDao carFaultDao;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private MemberDao memberDao;
	

	/**
	 * 根据查询条件，查询并返回CarFault的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarFault> getCarFaultList(Query q) {
		List<CarFault> list = null;
		try {
			//直接调用Dao方法进行查询
			list = carFaultDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarFault>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CarFault的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarFault> getCarFaultPagedList(Query q) {
		PageFinder<CarFault> page = new PageFinder<CarFault>();
		
		List<CarFault> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carFaultDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = carFaultDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarFault>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CarFault对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarFault getCarFault(String id) {
		CarFault obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carFaultDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarFault对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarFault> getCarFaultByIds(String[] ids) {
		List<CarFault> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = carFaultDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarFault>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CarFault记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarFault> delCarFault(String id, Operator operator) {
		ResultInfo<CarFault> resultInfo = new ResultInfo<CarFault>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = carFaultDao.delete(id);
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
	 * 新增一条CarFault记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carFault 新增的CarFault数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarFault> addCarFault(CarFault carFault, Operator operator) {
		ResultInfo<CarFault> resultInfo = new ResultInfo<CarFault>();
		
		if (carFault == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carFault = " + carFault);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carFault.getFaultId() == null) {
					carFault.setFaultId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					carFault.setOperatorType(operator.getOperatorType());
					carFault.setOperatorId(operator.getOperatorId());
				}
				//用车人
				if(carFault.getDriverId()!=null&&carFault.getDriverId().length()!=0){
					Member member=memberDao.get(carFault.getDriverId());
					if(member!=null){
						if(member.getMemberName()!=null){
							carFault.setDriverName(member.getMemberName());
						}
					}
					
				}
				//城市
				if(carFault.getCityId()!=null){
					DataDictItem dataDictItem=dataDictItemService.getDataDictItem(carFault.getCityId().toString());
					carFault.setCityName(dataDictItem.getItemValue());
				}
				//型号
				if(carFault.getCarModelId()!=null&&carFault.getCarModelId().length()!=0){
					DataDictItem dataDictItem=dataDictItemService.getDataDictItem(carFault.getCarModelId());
					carFault.setCarModelName(dataDictItem.getItemValue());
				}
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				carFault.setCreateTime(now);
				carFault.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(carFault);
				
				//调用Dao执行插入操作
				carFaultDao.add(carFault);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carFault);
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
	 * 根据主键，更新一条CarFault记录
	 * @param carFault 更新的CarFault数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarFault> updateCarFault(CarFault carFault, Operator operator) {
		ResultInfo<CarFault> resultInfo = new ResultInfo<CarFault>();
		
		if (carFault == null || carFault.getFaultId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carFault = " + carFault);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carFault.setOperatorType(operator.getOperatorType());
					carFault.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				carFault.setUpdateTime(new Date());
				//用车人
				if(carFault.getDriverId()!=null&&carFault.getDriverId().length()!=0){
					Member member=memberDao.get(carFault.getDriverId());
					carFault.setDriverName(member.getMemberName());
				}
				//城市
				if(carFault.getCityId()!=null){
					DataDictItem dataDictItem=dataDictItemService.getDataDictItem(carFault.getCityId().toString());
					carFault.setCityName(dataDictItem.getItemValue());
				}
				//型号
				if(carFault.getCarModelId()!=null&&carFault.getCarModelId().length()!=0){
					DataDictItem dataDictItem=dataDictItemService.getDataDictItem(carFault.getCarModelId());
					carFault.setCarModelName(dataDictItem.getItemValue());
				}
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = carFaultDao.update(carFault);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carFault);
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
	 * 为CarFault对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarFault obj) {
		if (obj != null) {
		}
	}

	/**
	 * 根据查询条件，分页查询并返回CarFault的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarFault> getCarMalfunctionPageList(Query q) {
        PageFinder<CarFault> page = new PageFinder<CarFault>();
		
		List<CarFault> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carFaultDao.getCarMalfunctionList(q);
			//调用dao统计满足条件的记录总数
			rowCount = carFaultDao.getCarMalfunctionCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarFault>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}

}
