package cn.com.shopec.core.ml.service.impl;

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
import cn.com.shopec.core.ml.dao.AdvertMengLongDao;
import cn.com.shopec.core.ml.dao.CLoveCarDao;
import cn.com.shopec.core.ml.model.CLoveCar;
import cn.com.shopec.core.ml.service.CLoveCarService;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * CLoveCar 服务实现类
 */
@Service
public class CLoveCarServiceImpl implements CLoveCarService {

	private static final Log log = LogFactory.getLog(CLoveCarServiceImpl.class);

	private static final Integer CENSOR_STATUS_DEFAULT = 0;

	@Resource
	private CLoveCarDao cLoveCarDao;

	@Resource
	private AdvertMengLongDao advertMengLongDao;

	/**
	 * 根据查询条件，查询并返回CLoveCar的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CLoveCar> getCLoveCarList(Query q) {
		List<CLoveCar> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = cLoveCarDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CLoveCar>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CLoveCar的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CLoveCar> getCLoveCarPagedList(Query q) {
		PageFinder<CLoveCar> page = new PageFinder<CLoveCar>();

		List<CLoveCar> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = cLoveCarDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cLoveCarDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CLoveCar>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CLoveCar对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CLoveCar getCLoveCar(String id) {
		CLoveCar obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cLoveCarDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CLoveCar对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CLoveCar> getCLoveCarByIds(String[] ids) {
		List<CLoveCar> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = cLoveCarDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CLoveCar>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CLoveCar记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CLoveCar> delCLoveCar(String id, Operator operator) {
		ResultInfo<CLoveCar> resultInfo = new ResultInfo<CLoveCar>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = cLoveCarDao.delete(id);
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
	 * 新增一条CLoveCar记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cLoveCar
	 *            新增的CLoveCar数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultInfo<CLoveCar> addCLoveCar(CLoveCar cLoveCar, Operator operator) {
		ResultInfo<CLoveCar> resultInfo = new ResultInfo<CLoveCar>();

		if (cLoveCar == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG + "传入的参数为空");
			log.info(Constant.ERR_MSG_INVALID_ARG + " cLoveCar = " + cLoveCar);
		} else {

			CLoveCar search = new CLoveCar();
			search.setMemberNo(cLoveCar.getMemberNo());
			search.setVehicleBrand(cLoveCar.getVehicleBrand());
			search.setVehicleModel(cLoveCar.getVehicleModel());
			List<CLoveCar> list = cLoveCarDao.queryAll(new Query(search));
			if (list != null && list.size() > 0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("改型号和品牌的车已经存在，不需要再次添加");
				return resultInfo;
			}
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cLoveCar.getLoveCarNo() == null) {
					cLoveCar.setLoveCarNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cLoveCar.setOperatorType(operator.getOperatorType());
					cLoveCar.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				cLoveCar.setCreateTime(now);
				cLoveCar.setUpdateTime(now);
				// 填充默认值
				this.fillDefaultValues(cLoveCar);
				// 调用Dao执行插入操作
				String licence = cLoveCar.getDrivingLicense();
				String newLicence = licence.substring(34);
				cLoveCar.setDrivingLicense(newLicence);// 驾驶证正面
				String licenceCopy = cLoveCar.getDrivingLicenseCopy();
				String newLicenceCopy = licenceCopy.substring(34);
				cLoveCar.setDrivingLicenseCopy(newLicenceCopy);// 驾驶证反面
				cLoveCarDao.add(cLoveCar);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cLoveCar);
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
	 * 根据主键，更新一条CLoveCar记录
	 * 
	 * @param cLoveCar
	 *            更新的CLoveCar数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CLoveCar> updateCLoveCar(CLoveCar cLoveCar, Operator operator) {
		ResultInfo<CLoveCar> resultInfo = new ResultInfo<CLoveCar>();

		if (cLoveCar == null || cLoveCar.getLoveCarNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cLoveCar = " + cLoveCar);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cLoveCar.setOperatorType(operator.getOperatorType());
					cLoveCar.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				cLoveCar.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cLoveCarDao.update(cLoveCar);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cLoveCar);
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
		return Long.valueOf(new Date().getTime() + new Random().nextInt(1000000)).toString();
	}

	/**
	 * 为CLoveCar对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CLoveCar obj) {
		if (obj != null) {
			obj.setCensorStatus(CENSOR_STATUS_DEFAULT);
		}
	}

	@Override
	public ResultInfo toInsertLoveCar(CLoveCar cLoveCar, Operator operator) {
		ResultInfo<CLoveCar> resultInfo = new ResultInfo<CLoveCar>();
		if (cLoveCar == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cLoveCar = " + cLoveCar);
			return resultInfo;
		}
		CLoveCar search = new CLoveCar();
		search.setMemberNo(cLoveCar.getMemberNo());
		search.setVehicleBrand(cLoveCar.getVehicleBrand());
		search.setVehicleModel(cLoveCar.getVehicleModel());
		List<CLoveCar> list = cLoveCarDao.queryAll(new Query(search));
		if (list != null && list.size() > 0) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_DATA_ALEARD_EXISTS);
			return resultInfo;
		}
		try {
			// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
			if (cLoveCar.getLoveCarNo() == null) {
				cLoveCar.setLoveCarNo(this.generatePK());
			}
			// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
			if (operator != null) {
				cLoveCar.setOperatorType(operator.getOperatorType());
				cLoveCar.setOperatorId(operator.getOperatorId());
			}
			Date now = new Date();
			cLoveCar.setCreateTime(now);
			cLoveCar.setUpdateTime(now);
			// 填充默认值
			this.fillDefaultValues(cLoveCar);
			cLoveCarDao.add(cLoveCar);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg("添加成功");
		} catch (Exception e) {
			log.error(e);
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	@Override
	public List<BannerVo> getLoveCarBannerList(Integer pos, Integer type, String imgPath) {
		List<BannerVo> list = null;
		if (null == pos || null == type || null == imgPath) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " params is null or empty array.");
		} else {
			try {
				list = advertMengLongDao.searchBannerByPosAndType_two(pos, type, imgPath);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<BannerVo>(0) : list;

		return list;
	}
	//爱车信息
	public List<CLoveCar> getLaveCar(String memberNo){
		List<CLoveCar> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = cLoveCarDao.getLaveCar(memberNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CLoveCar>(0) : list;
		return list;
	}
}
