package cn.com.shopec.core.monitor.service.impl;

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
import cn.com.shopec.common.utils.ECUuidGenUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.monitor.dao.CarTrackDao;
import cn.com.shopec.core.monitor.model.CarTrack;
import cn.com.shopec.core.monitor.service.CarTrackService;

/**
 * 车辆轨迹表 服务实现类
 */
@Service
public class CarTrackServiceImpl implements CarTrackService {

	private static final Log log = LogFactory.getLog(CarTrackServiceImpl.class);
	
	@Resource
	private CarTrackDao carTrackDao;
	

	/**
	 * 根据查询条件，查询并返回CarTrack的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarTrack> getCarTrackList(Query q) {
		List<CarTrack> list = null;
		try {
			//直接调用Dao方法进行查询
			list = carTrackDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarTrack>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CarTrack的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarTrack> getCarTrackPagedList(Query q) {
		PageFinder<CarTrack> page = new PageFinder<CarTrack>();
		
		List<CarTrack> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carTrackDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = carTrackDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarTrack>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CarTrack对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarTrack getCarTrack(String id) {
		CarTrack obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carTrackDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarTrack对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarTrack> getCarTrackByIds(String[] ids) {
		List<CarTrack> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = carTrackDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarTrack>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CarTrack记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarTrack> delCarTrack(String id) {
		ResultInfo<CarTrack> resultInfo = new ResultInfo<CarTrack>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = carTrackDao.delete(id);
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
	 * 新增一条CarTrack记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carTrack 新增的CarTrack数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarTrack> addCarTrack(CarTrack carTrack) {
		ResultInfo<CarTrack> resultInfo = new ResultInfo<CarTrack>();
		
		if (carTrack == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carTrack = " + carTrack);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carTrack.getTrackId() == null) {
					carTrack.setTrackId(this.generatePK());
				}
				
				//设置创建时间为当前时间
				if(carTrack.getCreateTime() == null) {
					Date now = new Date();
					carTrack.setCreateTime(now);
				}
				
				//填充默认值
				this.fillDefaultValues(carTrack);
				
				//调用Dao执行插入操作
				carTrackDao.add(carTrack);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carTrack);
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
	 * 根据主键，更新一条CarTrack记录
	 * @param carTrack 更新的CarTrack数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarTrack> updateCarTrack(CarTrack carTrack) {
		ResultInfo<CarTrack> resultInfo = new ResultInfo<CarTrack>();
		
		if (carTrack == null || carTrack.getTrackId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carTrack = " + carTrack);
		} else {
			try {
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = carTrackDao.update(carTrack);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carTrack);
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
		return ECUuidGenUtils.genUUID();
	}
	
	/**
	 * 为CarTrack对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarTrack obj) {
		if (obj != null) {
		}
	}

	/**
	 * 添加一组车辆轨迹数据
	 * @param carTracks
	 * @return
	 */
	@Transactional
	public ResultInfo<String> addCarTracks(List<CarTrack> carTracks) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		resultInfo.setCode(Constant.FAIL);
		if(carTracks == null || carTracks.isEmpty()) {
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carTracks = " + carTracks);
			return resultInfo;
		}
		
		try {
			for(CarTrack carTrack : carTracks) {
				this.addCarTrack(carTrack);
			}
			
			resultInfo.setCode(Constant.SECCUESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		
		return resultInfo;
	}
}
