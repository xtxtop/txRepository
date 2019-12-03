package cn.com.shopec.core.ml.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
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
import cn.com.shopec.core.ml.dao.CLabelDao;
import cn.com.shopec.core.ml.dao.CMatchingDao;
import cn.com.shopec.core.ml.dao.ChargingStationDao;
import cn.com.shopec.core.ml.model.CDoc;
import cn.com.shopec.core.ml.model.CLabel;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.service.CDocService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.vo.CLabelVo;
import cn.com.shopec.core.ml.vo.ChargingStationDetailsVo;
import cn.com.shopec.core.ml.vo.ChargingStationVo;

/**
 * 充电站 服务实现类
 */
@Service
public class ChargingStationServiceImpl implements ChargingStationService {
	private static final Log log = LogFactory.getLog(ChargingStationServiceImpl.class);
	@Resource
	private ChargingStationDao chargingStationDao;
	@Resource
	private CLabelDao cLabelDao;
	@Resource
	private CMatchingDao cMatchingDao;
	@Resource
	private AdvertMengLongDao advertMengLongDao;
	@Resource
	private CDocService CDocServiceImpl;
	@Value("${image_path}")
	private String imgPath;

	/**
	 * 根据查询条件，查询并返回ChargingStation的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargingStation> getChargingStationList(Query q) {
		List<ChargingStation> list = null;
		try {
			// 已删除的不查出
			ChargingStation chargingStation = (ChargingStation) q.getQ();
			if (chargingStation != null) {
				chargingStation.setIsDeleted(Constant.DEL_STATE_NO);
			}
			// 直接调用Dao方法进行查询
			list = chargingStationDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingStation>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回ChargingStation的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ChargingStation> getChargingStationPagedList(Query q) {
		PageFinder<ChargingStation> page = new PageFinder<ChargingStation>();
		List<ChargingStation> list = null;
		long rowCount = 0L;
		try {
			// 已删除的不查出
			ChargingStation chargingStation = (ChargingStation) q.getQ();
			if (chargingStation != null) {
				chargingStation.setIsDeleted(Constant.DEL_STATE_NO);
			}
			// 调用dao查询满足条件的分页数据
			list = chargingStationDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = chargingStationDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingStation>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个ChargingStation对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChargingStation getChargingStation(String id) {
		ChargingStation obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = chargingStationDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ChargingStation对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargingStation> getChargingStationByIds(String[] ids) {
		List<ChargingStation> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = chargingStationDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingStation>(0) : list;
		return list;
	}

	/**
	 * 根据主键，删除特定的ChargingStation记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Override
	@Transactional
	public ResultInfo<ChargingStation> delChargingStation(String id, Operator operator) {
		ResultInfo<ChargingStation> resultInfo = new ResultInfo<ChargingStation>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			ChargingStation chargingStation = new ChargingStation();
			chargingStation.setStationNo(id);
			chargingStation.setIsDeleted(Constant.DEL_STATE_YES);
			chargingStation.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				chargingStation.setOperatorType(operator.getOperatorType());
				chargingStation.setOperatorId(operator.getOperatorId());
			}
			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = chargingStationDao.update(chargingStation);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(chargingStation);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条ChargingStation记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param chargingStation
	 *            新增的ChargingStation数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Override
	@Transactional
	public ResultInfo<ChargingStation> addChargingStation(ChargingStation chargingStation, Operator operator) {
		ResultInfo<ChargingStation> resultInfo = new ResultInfo<ChargingStation>();
		if (chargingStation == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargingStation = " + chargingStation);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (chargingStation.getStationNo() == null) {
					chargingStation.setStationNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					chargingStation.setOperatorType(operator.getOperatorType());
					chargingStation.setOperatorId(operator.getOperatorId());
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				chargingStation.setCreateTime(now);
				chargingStation.setUpdateTime(now);
				// 填充默认值
				this.fillDefaultValues(chargingStation);
				// 调用Dao执行插入操作
				chargingStationDao.add(chargingStation);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(chargingStation);
				// 将图片保存在 c-doc表
				CDoc c = new CDoc();
				c.setDocName("充电站详情(图片)");// 附件名称
				c.setFileName(chargingStation.getStationUrl());// 文件名
				c.setFileType(0);// 类型0图片 1视频 2文件
				c.setPosition(1);// 图片位置 0 列表,1详情
				c.setFileUrl(chargingStation.getStationUrl());// 图片路径
				c.setBizType(1);// 1 充电站
				c.setBizId(chargingStation.getStationNo());// 充电站编号
				c.setIsDeleted(0);// 删除状态 0未删除 1删除
				CDocServiceImpl.addCDoc(c, operator);// 保存
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
	 * 根据主键，更新一条ChargingStation记录
	 * 
	 * @param chargingStation
	 *            更新的ChargingStation数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Override
	@Transactional
	public ResultInfo<ChargingStation> updateChargingStation(ChargingStation chargingStation, Operator operator) {
		ResultInfo<ChargingStation> resultInfo = new ResultInfo<ChargingStation>();
		if (chargingStation == null || chargingStation.getStationNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargingStation = " + chargingStation);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					chargingStation.setOperatorType(operator.getOperatorType());
					chargingStation.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				chargingStation.setUpdateTime(new Date());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = chargingStationDao.update(chargingStation);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(chargingStation);
				// 更新保存在 c-doc表中的图片
				CDoc c = new CDoc();
				c.setFileName(chargingStation.getStationUrl());// 文件名
				c.setFileUrl(chargingStation.getStationUrl());// 图片路径
				// 获取图片主键
				String docNo = CDocServiceImpl.getCDocNo(chargingStation.getStationNo());
				c.setDocNo(docNo);// 图片主键
				CDocServiceImpl.updateCDoc(c, operator);
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
	@Override
	public String generatePK() {
		return "CDZ" + String.valueOf(new Date().getTime());
	}

	/**
	 * 为ChargingStation对象设置默认值
	 * 
	 * @param obj
	 */
	@Override
	public void fillDefaultValues(ChargingStation obj) {
		if (obj != null) {
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(0);
			}
			if (obj.getIsDeleted() == null) {
				obj.setIsDeleted(0);
			}
		}
	}

	/**
	 * 根据查询条件，分页查询并返回ChargingStationVo的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ChargingStationVo> getChargingStationVoList(Query q) {
		PageFinder<ChargingStationVo> page = new PageFinder<ChargingStationVo>();
		List<ChargingStationVo> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = chargingStationDao.getChargingStationVoList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = chargingStationDao.getChargingStationVoListCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingStationVo>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个ChargingStationVo对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChargingStationVo getChargingStationVoList_NO(String id) {
		ChargingStationVo obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = chargingStationDao.getChargingStationVoList_NO(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 获取充电站列表数据
	 */
	@Override
	public List<ChargingStationVo> pageChargingStationVoList(String longitude, String latitude, String cityId,
			String[] labelList, String pageNo, String pageSize) {
		ChargingStationVo station = new ChargingStationVo();
		// 定位距离
		if (null != longitude && null != latitude) {
			station.setLongitude(longitude);// 经度
			station.setLatitude(latitude);// 纬度
		}

		String[] array1 = null;// 重新组装标签数组
		String[] array2 = null;// 筛选离我最近、价格最低的id
		if (labelList != null && labelList.length > 0) {
			array1 = new String[labelList.length];// 重新组装标签数组
			array2 = new String[labelList.length];// 筛选离我最近、价格最低的id
		}

		// 标签选择的参数
		if (null != labelList && labelList.length > 0) {
			for (int i = 0; i < labelList.length; i++) {
				if ("1".equals(labelList[i]) || "2".equals(labelList[i]) || "3".equals(labelList[i])
						|| "4".equals(labelList[i])) {
					array2[i] = labelList[i];
				} else {
					array1[i] = labelList[i];
				}
			}
			int a = 0;
			String[] b = {};
			List<String> lst = new ArrayList<>();
			for (int j = 0; j < array1.length; j++) {
				if (null != array1[j]) {
					a += 1;
					lst.add(array1[j]);
				}
			}
			if (0 == a) {
				station.setLabelArray(null);
			} else {
				station.setLabelArray(lst.toArray(b));
			}
		}

		if (array2 != null && array2.length > 0) {
			for (int i = 0; i < array2.length; i++) {
				// 类型排序的参数
				if (null != array2[i] && !"".equals(array2[i])) {
					if ("3".equals(array2[i])) {
						station.setStationType(1);// 精品站
					} else if ("4".equals(array2[i])) {
						station.setStationType(2);// 超级站
					} else if ("2".equals(array2[i])) {
						station.setElectricPrice(1.0);// 价格最低
					} else if ("1".equals(array2[i])) {
						station.setLongitude(longitude);// 经度
						station.setLatitude(latitude);// 纬度
					}
				}
			}
			// 如果同时选取"离我最近"和"价格最低",只能选其"离我最近"
			if (null != station.getElectricPrice() && null != station.getLongitude() && null != station.getLatitude()) {
				station.setElectricPrice(null);
				station.setLongitude(longitude);// 经度
				station.setLatitude(latitude);// 纬度
			}
		}

		// 运营城市id
		if (!StringUtils.isBlank(cityId)) {
			station.setOperatingCityNo(cityId);// 运营城市id
		}
		Query q = null;
		if (null != pageNo && null != pageSize) {
			q = new Query(Integer.valueOf(pageNo), Integer.valueOf(pageSize), station);
		} else {
			q = new Query(station);
		}

		// 调用dao查询满足条件的分页数据
		List<ChargingStationVo> list = chargingStationDao.pageChargingStationVoList(q);
		return list;
	}

	@Override
	public ChargingStationDetailsVo getChargingStationDatils(ChargingStationDetailsVo search) {
		List<ChargingStationDetailsVo> lst = chargingStationDao.getChargingStationDatils(new Query(search));
		ChargingStationDetailsVo data = null;
		if (lst != null && lst.size() > 0) {
			data = lst.get(0);
			Object labels = data.getLabels();
			String[] array = (labels == null ? "" : labels.toString()).split(",");
			List<CLabelVo> li = new ArrayList<>();
			if (array.length > 0) {
				for (int i = 0; i < array.length; i++) {
					CLabel cl = cLabelDao.get(array[i]);
					CLabelVo cv = new CLabelVo();
					cv.setLabelId(cl.getLabelId());
					cv.setLabelName(cl.getLabelName());
					li.add(cv);
				}
				data.setLabelList(li);
			}
			data.setLabels(labels == null ? new JSONArray() : cLabelDao.searchInLabelNos(labels.toString().split(",")));
			Object matchs = data.getMatchList();
			data.setMatchList(matchs == null ? new JSONArray()
					: cMatchingDao.searchInMatchingNos(matchs.toString().split(","), imgPath));
			data.setTopBanner(advertMengLongDao.searchBannerByPosAndType_two(3, 15, imgPath));// 充电站详情多张图片
			// data.setStationUrl(imgPath + "/" + data.getStationUrl());//
			// 顶部充电站详情的图片
			data.setCentreBanner(advertMengLongDao.searchBannerByPosAndType_two(4, 15, imgPath));
		}
		return data;
	}

	/**
	 * 充电站的类型（1、精品站；2、超级站）
	 */
	@Override
	public List<ChargingStation> getTypeSort() {

		return chargingStationDao.getTypeSort();
	}

	public List<ChargingStationVo> getChargingStationVoListByCollection(Query q) {
		List<ChargingStationVo> list = chargingStationDao.pageChargingStationVoListBycollection(q);
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ChargingStationVo> searchStationVoList(Query q) {
		PageFinder<ChargingStationVo> page = new PageFinder<ChargingStationVo>();
		List<ChargingStationVo> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = chargingStationDao.pageChargingStationVoList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = chargingStationDao.getChargingStationVoListCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingStationVo>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

}
