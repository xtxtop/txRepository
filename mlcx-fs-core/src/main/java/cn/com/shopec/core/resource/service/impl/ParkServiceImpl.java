package cn.com.shopec.core.resource.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAndStatus;
import cn.com.shopec.core.car.model.CarOrParkVo;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.model.CarVoAround;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.car.vo.ParkDotVo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CarRedPacket;
import cn.com.shopec.core.marketing.model.CarRedPacketVo;
import cn.com.shopec.core.marketing.service.CarRedPacketService;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.dao.ParkStatusDao;
import cn.com.shopec.core.resource.dao.ParkingSpaceDao;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkCompanyRel;
import cn.com.shopec.core.resource.model.ParkDayVo;
import cn.com.shopec.core.resource.model.ParkLocation;
import cn.com.shopec.core.resource.model.ParkLocationNs;
import cn.com.shopec.core.resource.model.ParkRegion;
import cn.com.shopec.core.resource.model.ParkSearch;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.model.ParkVOAround;
import cn.com.shopec.core.resource.model.ParkingSpace;
import cn.com.shopec.core.resource.model.WorkerOrderParkVo;
import cn.com.shopec.core.resource.model.WorkerRegion;
import cn.com.shopec.core.resource.service.ParkCompanyRelService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkingSpaceService;
import cn.com.shopec.core.resource.vo.ParkCarNumVo;
import cn.com.shopec.core.scheduling.dao.WorkerOrderDao;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.search.common.SolrDocConstants;
import cn.com.shopec.core.search.model.ParkSearchCondition;
import cn.com.shopec.core.search.model.SearchCondition;
import cn.com.shopec.core.search.model.SearchResult;
import cn.com.shopec.core.search.model.SolrDoc;
import cn.com.shopec.core.search.service.BusinessIndexService;
import cn.com.shopec.core.search.service.ParkIndexService;
import cn.com.shopec.core.search.service.ParkSearchService;
import cn.com.shopec.core.search.service.SearchService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;

/**
 * Park 服务实现类
 */
@Service
public class ParkServiceImpl implements ParkService {

	private static final Log log = LogFactory.getLog(ParkServiceImpl.class);

	private static final double CURRENT_PARK_SEARCH_RADIUS = 1.0; // 当前站点的检索半径（单位：km）

	@Value("${image_path}")
	private String imgPath;

	@Resource
	private ParkDao parkDao;

	@Resource
	private WorkerOrderDao workerOrderDao;

	@Resource
	public SysRegionService sysRegionService;

	@Resource
	private DataDictItemService dataDictItemService;

	@Resource
	private MemberService memberService;

	@Resource
	private ParkStatusDao parkStatusDao;
	@Resource
	private SearchService searchService;
	@Resource
	private CarDao carDao;
	@Resource
	private ParkIndexService parkIndexService;
	@Resource
	private BusinessIndexService businessIndexService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private ParkingSpaceService parkingSpaceService;
	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private ParkSearchService parkSearchService;
	@Resource
	private ParkingSpaceDao parkingSpaceDao;
	@Resource
	private ParkCompanyRelService parkCompanyRelService;

	@Resource
	private CompanyService companyService;
	@Resource
	private CarRedPacketService carRedPacketService;
	@Resource
	private OrderService orderService;
	/**
	 * 根据查询条件，查询并返回Park的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Park> getParkList(Query q) {
		List<Park> list = null;
		try {
			// 已删除的不查出
			Park park = (Park) q.getQ();
			if (park != null) {
				park.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = parkDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，查询并返回Park的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Park> getParkListAndCar(Query q) {
		List<Park> list = null;
		try {
			// 已删除的不查出
			Park park = (Park) q.getQ();
			if (park != null) {
				park.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = parkDao.queryAllAndCar(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回Park的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Park> getParkPagedList(Query q) {
		PageFinder<Park> page = new PageFinder<Park>();

		List<Park> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			Park park = (Park) q.getQ();
			if (park != null) {
				park.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = parkDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = parkDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个Park对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Park getPark(String id) {
		Park obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = parkDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Park对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Park> getParkByIds(String[] ids) {
		List<Park> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = parkDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的Park记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Park> delPark(String id, Operator operator) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			Park park = new Park();
			park.setParkNo(id);
			park.setIsDeleted(Constant.DEL_STATE_YES);
			park.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				park.setOperatorType(operator.getOperatorType());
				park.setOperatorId(operator.getOperatorId());
			}

			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = parkDao.update(park);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(park);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条Park记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param park
	 *            新增的Park数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Park> addPark(Park park, Operator operator) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();

		if (park == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " park = " + park);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (park.getParkNo() == null) {
					park.setParkNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					park.setOperatorType(operator.getOperatorType());
					park.setOperatorId(operator.getOperatorId());
				}
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(park.getCityId());
				if (dataDictItemcity != null) {
					park.setCityName(dataDictItemcity.getItemValue());
				}
				// 片区
				if (park.getRegionId() != null) {
					DataDictItem dy = dataDictItemService.getDataDictItem(park.getRegionId());
					if (dy != null) {
						park.setRegionName(dy.getItemValue());
					}
				}
				// 样式
				DataDictItem dataDictItemStyle = dataDictItemService.getDataDictItem(park.getStyleId());
				if (dataDictItemStyle != null) {
					park.setStyle(dataDictItemStyle.getItemValue());
				}
				// 省市区
				String addrRegion1Name = sysRegionService.detail(park.getAddrRegion1Id()).getRegionName();
				if (addrRegion1Name != null && addrRegion1Name.length() != 0) {
					park.setAddrRegion1Name(addrRegion1Name);
				}
				String addrRegion2Name = sysRegionService.detail(park.getAddrRegion2Id()).getRegionName();
				if (addrRegion2Name != null && addrRegion2Name.length() != 0) {
					park.setAddrRegion2Name(addrRegion2Name);
				}
				if (park.getAddrRegion3Id() != null && park.getAddrRegion3Id().length() != 0) {
					String addrRegion3Name = sysRegionService.detail(park.getAddrRegion3Id()).getRegionName();
					if (addrRegion3Name == null) {
						park.setAddrRegion3Id("");
						park.setAddrRegion3Name("");
					} else {
						park.setAddrRegion3Name(addrRegion3Name);
					}
				} else {
					park.setAddrRegion3Id("");
					park.setAddrRegion3Name("");
				}

				// 设置还车附加费的默认值
				if (park.getServiceFeeBack() == null) {
					park.setServiceFeeBack(0D);
				}

				// 设置取车附加费的默认值
				if (park.getServiceFeeGet() == null) {
					park.setServiceFeeGet(0D);
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				park.setCreateTime(now);
				park.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(park);

				// 调用Dao执行插入操作
				if(!park.getCityName().equals(park.getAddrRegion2Name())){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("选择所在地区城市与所选城市片区不一致！请您重新选择...");
				} else{
					parkDao.add(park);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(park);
				}
				
				// 场站增加成功后，添加场站状态
				if (resultInfo.getCode() == Constant.SECCUESS) {
					ParkStatus parkStatus = new ParkStatus();
					parkStatus.setParkNo(park.getParkNo());
					parkStatus.setParkingSpaces(park.getParkingSpaceNumber());
					parkStatus.setChargerNumber(park.getChargerNumber());
					parkStatusDao.add(parkStatus);
					// 保存场站到搜索引擎索引中
					// parkIndexService.savePark(park);
					// 保存场站和集团的对应关系
					if (park.getCompanyIds() != null && !park.getCompanyIds().equals("")) {
						String[] companyIds = park.getCompanyIds().split(",");
						for (String companyId : companyIds) {
							// 添加场站和集团的对应关系
							ParkCompanyRel pcr = new ParkCompanyRel();
							pcr.setParkId(resultInfo.getData().getParkNo());
							pcr.setCompanyId(companyId);
							parkCompanyRelService.addParkCompanyRel(pcr, operator);
						}
					}
				}

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
	 * 根据主键，更新一条Park记录
	 * 
	 * @param park
	 *            更新的Park数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Park> updatePark(Park park, Operator operator) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();

		if (park == null || park.getParkNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " park = " + park);
		} else {
			if (park.getParkingSpaceNumber() == null) {
				park.setParkingSpaceNumber(0);
			}
			ParkingSpace parkingSpace = new ParkingSpace();
			parkingSpace.setParkNo(park.getParkNo());
			List<ParkingSpace> parkingSpaces = parkingSpaceDao.queryAll(new Query(parkingSpace));
			if (parkingSpaces != null && parkingSpaces.size() > park.getParkingSpaceNumber()) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(ParkConstant.FAIL_PARKSPACENUMBER_SET);
				log.info(Constant.ERR_MSG_INVALID_ARG + " park = " + park);
				return resultInfo;
			}

			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					park.setOperatorType(operator.getOperatorType());
					park.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				park.setUpdateTime(new Date());
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(park.getCityId());
				if (dataDictItemcity != null) {
					park.setCityName(dataDictItemcity.getItemValue());
				}
				// 片区
				if (park.getRegionId() != null) {
					DataDictItem dy = dataDictItemService.getDataDictItem(park.getRegionId());
					if (dy != null) {
						park.setRegionName(dy.getItemValue());
					}
				}

				// 样式
				DataDictItem dataDictItemStyle = dataDictItemService.getDataDictItem(park.getStyleId());
				if (dataDictItemStyle != null) {
					park.setStyle(dataDictItemStyle.getItemValue());
				}
				// 省市区
				if (park.getAddrRegion1Id() != null && park.getAddrRegion1Id().length() != 0) {
					String addrRegion1Name = sysRegionService.detail(park.getAddrRegion1Id()).getRegionName();
					if (addrRegion1Name != null && addrRegion1Name.length() != 0) {
						park.setAddrRegion1Name(addrRegion1Name);
					}
				}
				if (park.getAddrRegion2Id() != null && park.getAddrRegion2Id().length() != 0) {
					String addrRegion2Name = sysRegionService.detail(park.getAddrRegion2Id()).getRegionName();
					if (addrRegion2Name != null && addrRegion2Name.length() != 0) {
						park.setAddrRegion2Name(addrRegion2Name);
					}
				}
				if (park.getAddrRegion3Id() != null && park.getAddrRegion3Id().length() != 0) {
					String addrRegion3Name = sysRegionService.detail(park.getAddrRegion3Id()).getRegionName();
					park.setAddrRegion3Name(addrRegion3Name);
				} else {
					park.setAddrRegion3Id("");
					park.setAddrRegion3Name("");
				}
				
				if(!park.getCityName().equals(park.getAddrRegion2Name())){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("选择所在地区城市与所选城市片区不一致！请您重新去选择...");
					return resultInfo;
				} 

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = parkDao.updateByBusiness(park);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
					if (park.getCompanyIds() != null && !park.getCompanyIds().equals("")) {
						String[] companyIds = park.getCompanyIds().split(",");
						// 先将场站和集团对应表中的相关数据删除
						ParkCompanyRel parkCompanyRelSearch = new ParkCompanyRel();
						parkCompanyRelSearch.setParkId(park.getParkNo());
						List<ParkCompanyRel> parkCompanyRelList = parkCompanyRelService
								.getParkCompanyRelList(new Query(parkCompanyRelSearch));
						if (parkCompanyRelList != null && parkCompanyRelList.size() > 0) {
							for (ParkCompanyRel pcR : parkCompanyRelList) {
								parkCompanyRelService.delParkCompanyRel(pcR.getParkId(), pcR.getCompanyId(), operator);
							}
						}
						for (String companyId : companyIds) {
							// 添加场站和集团的对应关系
							ParkCompanyRel pcr = new ParkCompanyRel();
							pcr.setParkId(park.getParkNo());
							pcr.setCompanyId(companyId);
							parkCompanyRelService.addParkCompanyRel(pcr, operator);
						}
					} else {
						// 先将场站和集团对应表中的相关数据删除
						ParkCompanyRel parkCompanyRelSearch = new ParkCompanyRel();
						parkCompanyRelSearch.setParkId(park.getParkNo());
						List<ParkCompanyRel> parkCompanyRelList = parkCompanyRelService
								.getParkCompanyRelList(new Query(parkCompanyRelSearch));
						if (parkCompanyRelList != null && parkCompanyRelList.size() > 0) {
							for (ParkCompanyRel pcR : parkCompanyRelList) {
								parkCompanyRelService.delParkCompanyRel(pcR.getParkId(), pcR.getCompanyId(), operator);
							}
						}
					}
					businessIndexService.saveOrUpdatePark(park.getParkNo());
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(park);
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
	 * 设置场站状态
	 * 
	 * @param park
	 *            更新的Park数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Park> changeParkState(Park park, Operator operator) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();
		if (park == null || park.getParkNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " park = " + park);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					park.setOperatorType(operator.getOperatorType());
					park.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				park.setUpdateTime(new Date());
				// 场站状态（0、停用，1、启用，默认0）
				park.setAvailableUpdateTime(new Date());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = parkDao.update(park);
				if (count > 0) {
					if (park.getIsAvailable() != null || park.getIsView() != null) {// 场站启停用时或者显示隐藏时则更新场站到搜索引擎索引中
						businessIndexService.saveOrUpdatePark(park.getParkNo());
					}
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(park);
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
		return primarykeyService.getValueByBizType(PrimarykeyConstant.parkType) + "";
	}

	/**
	 * 为Park对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Park obj) {
		if (obj != null) {
			if (obj.getParkType() == null) {
				obj.setParkType(1);
			}
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(0);
			}
			if (obj.getIsDeleted() == null) {
				obj.setIsDeleted(0);
			}
			if (obj.getIsView() == null) {
				obj.setIsView(0);
			}
		}
	}

	/**
	 * @author lj 根据查询条件，分页查询并返回Park的分页结果
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Park> getParkPageList(Query q) {
		PageFinder<Park> page = new PageFinder<Park>();

		List<Park> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			Park park = (Park) q.getQ();
			if (park != null) {
				park.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = parkDao.parkPageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = parkDao.parkCount(q);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * @author lj 根据主键，查询并返回一个Park对象
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Park toParkView(String id) {
		Park obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = parkDao.get(id);
			if (obj.getAddrRegion3Name() != null && obj.getAddrRegion3Name().length() != 0) {
				obj.setAddrStreet(obj.getAddrRegion1Name() + obj.getAddrRegion2Name() + obj.getAddrRegion3Name()
						+ obj.getAddrStreet());
			} else {
				obj.setAddrStreet(obj.getAddrRegion1Name() + obj.getAddrRegion2Name() + obj.getAddrStreet());
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 定位查询周边场站信息列表
	 */
	@Override
	public List<Park> getParkListByAround(String longitude, String latitude, Double distance, String memberNo) {
		List<Park> list = null;
		try {
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%电量
			// 先根据memberNo判断是普通会员还是集团会员
			if (memberNo != null && !memberNo.equals("")) {
				Member member = memberService.getMember(memberNo);
				if (member != null) {
					if (member.getMemberType().equals(1)) {// 普通会员
						// 直接调用Dao方法进行查询
						list = parkDao.getParkListByAround(longitude, latitude, distance);
						for (Park park : list) {
							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
							park.setAvailableCarNum(availableCarNum);
						}
					} else if (member.getMemberType().equals(2)) {// 集团会员
						String companyId = member.getCompanyId();
						// 直接调用Dao方法进行查询
						list = parkDao.getParkListByAround1(longitude, latitude, distance, companyId);
						for (Park park : list) {
							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
							Integer availableCarNum1 = carDao.getCarCountByParkMember1(park.getParkNo(), companyId,
									power);
							park.setAvailableCarNum(availableCarNum + availableCarNum1);
						}
					}
				} else {
					// 直接调用Dao方法进行查询
					list = parkDao.getParkListByAround(longitude, latitude, distance);
					for (Park park : list) {
						// 获取可用车辆数
						Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
						park.setAvailableCarNum(availableCarNum);
					}
				}
			} else {
				// 直接调用Dao方法进行查询
				list = parkDao.getParkListByAround(longitude, latitude, distance);
				for (Park park : list) {
					// 获取可用车辆数
					Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
					park.setAvailableCarNum(availableCarNum);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	/**
	 * 定位查询周边场站信息列表（返回结果中get(car)为车辆，get(park)为场站）
	 */
	@Override
	public Map<String, List<Object>> getCarAndParkByAround(String longitude, String latitude, Double distance,Integer starRow,Integer rowNum,
			String memberNo,String seaTing,String carPlateNo) {
		List<Object> carList = new ArrayList<Object>();
		List<Object> parkList = new ArrayList<Object>();
		try {
			boolean isMember = false;

			SearchCondition searchCondition = new SearchCondition();
			searchService.setDefaultParmForQueryAround(searchCondition);// 设置查询其有效车辆和场站的默认值。
			// double[] bdCoord =
			// ECGeoCoordinateTransformUtil.wgs84tobd09(Double.valueOf(longitude),
			// Double.valueOf(latitude));
			// searchCondition.setLongitude(bdCoord[0]); //经度（百度坐标）
			// searchCondition.setLatitude(bdCoord[1]); //纬度（百度坐标）
			SysParam reTurnCarType = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
			if(reTurnCarType != null && "1".equals(reTurnCarType.getParamValue()) ){
				searchCondition.setSeaTing("");
			}else{
				searchCondition.setSeaTing(seaTing);
			}
			
			searchCondition.setLongitude(Double.valueOf(longitude));
			searchCondition.setLatitude(Double.valueOf(latitude));
			searchCondition.setRadius(distance);// 半径
			if(null !=starRow){
				searchCondition.setStarRow(starRow);
			}
			if(null !=rowNum){
				searchCondition.setRowNum(rowNum);
			}
			
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%
			// 先根据memberNo判断是普通会员还是集团会员
			if (memberNo != null && !memberNo.equals("")) {
				Member member = memberService.getMember(memberNo);
				if (member != null) {
					if (member.getMemberType().equals(2)) {// 集团会员
						String companyId = member.getCompanyId();
						SearchResult searchResult = searchService.searchAround(searchCondition);// 在solr中查询
						List<SolrDoc> searchData = searchResult == null ? null : searchResult.getDatas();
						if (searchData != null) {
							for (SolrDoc solrDoc : searchData) {
								if (solrDoc != null && solrDoc.getDataType() != null) {
									// 集团会员只能看到非集团场站和车辆，以及自身集团场站和车辆（看不到其他集团的场站和车辆）
									if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_CAR) {
										if (solrDoc.getCompanyId() == null
												|| solrDoc.getCompanyId().equals(companyId)) {
											// 判断车辆是否有场站
											CarVoAround ca = this.convertCarVoAround(solrDoc);// solrDoc转换CarVoAround
											if (null != ca && ca.getCarNo() != null) {
												// 判断运营模式 ， 场站+任意点 以及 任意点的情况下
												// 才会进来
												SysParam returnCarType = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
												String returnCar = returnCarType == null ? "1": returnCarType.getParamValue();
												if ("2".equals(returnCar)) { // 场站+任意点
													CarStatus cs = carStatusService.getCarStatus(ca.getCarNo());
													if (null != cs && (cs.getLocationParkNo().trim().length() == 0|| cs.getLocationParkNo() == null)) {
														carList.add(ca);
													}
//													if (null != cs) {
//														carList.add(ca);
//													}
												} else if ("3".equals(returnCar)) { // 任意点，
																					// 场站外的车
													// 逻辑上是否需要判断 车辆是否有所在场站
													carList.add(ca);
												}
											}
										}
									} else if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_PARK) {
										// 获取可用车辆数(非集团车辆数+集团车辆数)
										boolean isAdd = false;
										// 校验场站是否所属集团中是否拥有该会员的集团id
										if (solrDoc.getCompanyId() != null && !solrDoc.getCompanyId().equals("")) {
											String complanId = "," + solrDoc.getCompanyId() + ",";
											isAdd = complanId.indexOf("," + companyId + ",") > -1;
										} else {
											isAdd = true;
										}
										if (isAdd) {
											ParkVOAround parkVOAround = this.convertParkVOAround(solrDoc);// solrDoc转换ParkVOAround
											// 获取可用车辆数
											Integer availableCarNum = carDao
													.getCarCountByParkMemberAndSeaTing(solrDoc.getDataNo(), power,seaTing);
											Integer availableCarNum1 = carDao
													.getCarCountByParkMember1AndSeaTing(solrDoc.getDataNo(), companyId, power,seaTing);
											availableCarNum = availableCarNum == null ? 0 : availableCarNum;
											availableCarNum1 = availableCarNum1 == null ? 0 : availableCarNum1;
											parkVOAround.setCarNum(availableCarNum + availableCarNum1);
											//找出空闲的车位数
											if(parkVOAround.getParkNo() != null && !"".equals(parkVOAround.getParkNo()) ){
												Park park=getPark(parkVOAround.getParkNo());
												if(park != null && park.getParkingSpaceNumber() != null){
													Integer c=park.getParkingSpaceNumber()-parkVOAround.getCarNum();
													if(c >=0){
														parkVOAround.setParkingSpaces(c);
													}else{
														parkVOAround.setParkingSpaces(0);
													}
												}
											}
											parkList.add(parkVOAround);
										}
									}
								}
							}
						}
						isMember = true;
					}
				}
			}
			if (isMember == false) {// 会员外一律返回所属集团为空的数据
				SearchResult searchResult = searchService.searchAround(searchCondition);// 在solr中查询
				List<SolrDoc> searchData = searchResult == null ? null : searchResult.getDatas();
				if (searchData != null) {
					for (SolrDoc solrDoc : searchData) {
						// 普通会员只能看到车辆，场站的所属集团为空的数据
						if (solrDoc != null && solrDoc.getCompanyId() == null || solrDoc.getCompanyId().equals("")) {
							if (solrDoc.getDataType() != null) {
								if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_CAR) {
									// 判断车辆是否有场站
									CarVoAround ca = this.convertCarVoAround(solrDoc);// solrDoc转换CarVoAround
									if (null != ca && ca.getCarNo() != null) {

										// 判断运营模式 ， 场站+任意点 以及 任意点的情况下 才会进来
										SysParam returnCarType = sysParamService
												.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
										String returnCar = returnCarType == null ? "1" : returnCarType.getParamValue();
										if ("2".equals(returnCar)) { // 场站+任意点
																		// 放场站外的车
											CarStatus cs = carStatusService.getCarStatus(ca.getCarNo());
											if (null != cs && (cs.getLocationParkNo().trim().length() == 0
													|| cs.getLocationParkNo() == null)) {
												carList.add(ca);
											}
//											if (null != cs) {
//												carList.add(ca);
//											}
										} else if ("3".equals(returnCar)) { // 任意点，
																			// 场站外的车
											// 逻辑上是否需要判断 车辆是否有所在场站
											carList.add(ca);
										}

									}

								} else if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_PARK) {
									ParkVOAround parkVOAround = this.convertParkVOAround(solrDoc);// solrDoc转换ParkVOAround
									// 获取可用车辆数
									Integer availableCarNum = carDao.getCarCountByParkMemberAndSeaTing(solrDoc.getDataNo(),
											power,seaTing);
									parkVOAround.setCarNum(availableCarNum == null ? 0 : availableCarNum);
									if(parkVOAround.getParkNo() != null && !"".equals(parkVOAround.getParkNo()) ){
										Park park=getPark(parkVOAround.getParkNo());
										if(park != null && park.getParkingSpaceNumber() != null){
											Integer c=park.getParkingSpaceNumber()-parkVOAround.getCarNum();
											if(c >=0){
												parkVOAround.setParkingSpaces(c);
											}else{
												parkVOAround.setParkingSpaces(0);
											}
										}
									}
									parkList.add(parkVOAround);
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表

		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		if (carPlateNo!=null&&!"".equals(carPlateNo)) {
			//对比还车场站是否是红包车目标场站
			CarRedPacket carRedPacketTarget = carRedPacketService.getByCarPlateNo(carPlateNo);
			if (carRedPacketTarget!=null) {
				String carRedPacketTartetParkNoStr = carRedPacketTarget.getParkNo();
				String[] carRedPacketTartetParkNoArr = carRedPacketTartetParkNoStr.split(",");
				for(String targetParkNo:carRedPacketTartetParkNoArr){
					for(Object obj:parkList){
						ParkVOAround parkVo = (ParkVOAround)obj;
						if (parkVo.getParkNo().equals(targetParkNo)) {
							parkVo.setIsRedPacketTargetPark("1");
							break;
						}
					}
				}
			}
		}
		if (parkList.size()>0) { 
			String locationParkNos = carRedPacketService.getCarRedParketLocationParkNos(1);
			if (locationParkNos!=null&&!"".equals(locationParkNos)) {
				String[] parkNos = locationParkNos.split(",");
				for(int i=0;i<parkNos.length;i++){
					for(Object obj:parkList){
						ParkVOAround parkVo = (ParkVOAround)obj;
						if (parkVo.getParkNo().equals(parkNos[i])) {
							parkVo.setIsRedPakcetPark("1");
							break;
						}
					}
				}
			}
		}
		if (carList.size()>0) {
			CarRedPacket queryCarRedPacket = new CarRedPacket();
			queryCarRedPacket.setCarRedPacketStatus(1);
			List<CarRedPacket> carRedPacketList = carRedPacketService.getCarRedPacketList(new Query(queryCarRedPacket));
			if (carRedPacketList.size()>0) {
				for(Object obj:carList){
					CarVoAround carVo = (CarVoAround)obj;
					for(CarRedPacket carRedPacket:carRedPacketList){
						if (carVo.getCarPlateNo().equals(carRedPacket.getCarPlateNo())) {
							carVo.setIsCarRedPakcet("1");
							CarRedPacketVo carRedPacketVo =new CarRedPacketVo();
							carRedPacketVo.setIsCharge(carRedPacket.getIsCharge());
							carRedPacketVo.setIsOrderAmountLimit(carRedPacket.getIsOrderAmountLimit());
							if(carRedPacket.getIsOrderAmountLimit() ==1){
								carRedPacketVo.setOrderAmountLimit(carRedPacket.getOrderAmountLimit());
							}else{
								carRedPacketVo.setOrderAmountLimit(0.0);
							}
							carVo.setCarRedPacketVo(carRedPacketVo);
							break;
						}
					}
				}
			}
		}
		resultMap.put("car", carList);
		resultMap.put("park", parkList);
		return resultMap;
	}

	@Override
	public List<Park> getParkListByTake(Query q, String memberNo, String longitude, String latitude) {
		List<Park> list = null;
		try {
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%电量
			// 直接调用Dao方法进行查询
			// 场站所属多集团情况
			Member member = memberService.getMember(memberNo);
			if (member != null) {
				if (member.getMemberType().equals(1)) {// 普通会员
					list = parkDao.getParkListByTakePT(q);
				} else if (member.getMemberType().equals(2)) {// 集团会员
					String companyId = member.getCompanyId();
					Park parkSearch = (Park) q.getQ();
					parkSearch.setCompanyId(companyId);
					q.setQ(parkSearch);
					list = parkDao.getParkListByTakeJT(q);
				}
			}
			// 先根据memberNo判断是普通会员还是集团会员
			if (memberNo != null && !memberNo.equals("")) {
				member = memberService.getMember(memberNo);
				if (member != null) {
					if (member.getMemberType().equals(1)) {// 普通会员
						for (Park park : list) {
							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
							park.setAvailableCarNum(availableCarNum);
							if (longitude != null && !longitude.equals("") && latitude != null
									&& !latitude.equals("")) {
								Double distance = getDistanceByPoint(longitude, latitude, park.getLongitude(),
										park.getLatitude());
								if (distance != null) {
									park.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
								} else {
									park.setDistance(0d);
								}
							} else {
								park.setDistance(0d);
							}

						}
					} else if (member.getMemberType().equals(2)) {// 集团会员
						String companyId = member.getCompanyId();
						for (Park park : list) {
							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
							Integer availableCarNum1 = carDao.getCarCountByParkMember1(park.getParkNo(), companyId,
									power);
							park.setAvailableCarNum(availableCarNum + availableCarNum1);
							if (longitude != null && !longitude.equals("") && latitude != null
									&& !latitude.equals("")) {
								Double distance = getDistanceByPoint(longitude, latitude, park.getLongitude(),
										park.getLatitude());
								if (distance != null) {
									park.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
								} else {
									park.setDistance(0d);
								}
							} else {
								park.setDistance(0d);
							}
						}
					}
				} else {
					for (Park park : list) {
						// 获取可用车辆数
						Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
						park.setAvailableCarNum(availableCarNum);
						if (longitude != null && !longitude.equals("") && latitude != null && !latitude.equals("")) {
							Double distance = getDistanceByPoint(longitude, latitude, park.getLongitude(),
									park.getLatitude());
							if (distance != null) {
								park.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
							} else {
								park.setDistance(0d);
							}
						} else {
							park.setDistance(0d);
						}
					}
				}
			} else {
				for (Park park : list) {
					// 获取可用车辆数
					Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
					park.setAvailableCarNum(availableCarNum);
					if (longitude != null && !longitude.equals("") && latitude != null && !latitude.equals("")) {
						Double distance = getDistanceByPoint(longitude, latitude, park.getLongitude(),
								park.getLatitude());
						if (distance != null) {
							park.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
						} else {
							park.setDistance(0d);
						}
					} else {
						park.setDistance(0d);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	@Override
	public List<Park> getParkListByReturn(Query q, String longitude, String latitude, String memberNo) {
		List<Park> list = null;
		try {
			// 直接调用Dao方法进行查询
			if (memberNo != null) {
				Member member = memberService.getMember(memberNo);
				if (member != null) {
					if (member.getMemberType().equals(1)) {// 普通会员
						list = parkDao.getParkListByReturnPT(q);
					} else if (member.getMemberType().equals(2)) {// 集团会员
						String companyId = member.getCompanyId();
						Park parkSearch = (Park) q.getQ();
						parkSearch.setCompanyId(companyId);
						q.setQ(parkSearch);
						list = parkDao.getParkListByReturnJT(q);
					}
				} else {
					list = parkDao.getParkListByReturnPT(q);
				}
			} else {
				list = parkDao.getParkListByReturnPT(q);
			}
			if (list != null && list.size() > 0) {
				for (Park p : list) {
					if (longitude != null && !longitude.equals("") && latitude != null && !latitude.equals("")) {
						Double distance = getDistanceByPoint(longitude, latitude, p.getLongitude(), p.getLatitude());
						if (distance != null) {
							p.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
						} else {
							p.setDistance(0d);
						}
					} else {
						p.setDistance(0d);
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	/**
	 * 调度员接口（任务选择（点击前）） zln
	 */
	@Override
	public List<Park> getParkListByWorker(String longitude, String latitude, Double distance) {
		List<Park> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = parkDao.getParkListByWorker(longitude, latitude, distance);
			for (Park park : list) {
				// 获取所有车辆数
				Integer carNum = carDao.getCarCountByPark(park.getParkNo());
				park.setAvailableCarNum(carNum);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	@Override
	public Double getDistanceByPoint(String longitude, String latitude, String longitude2, String latitude2) {
		Double distance = 0d;
		try {

			// 调用dao，根据主键查询
			distance = parkDao.getDistanceByPoint(longitude, latitude, longitude2, latitude2);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return distance;
	}

	@Override
	public ParkLocation getParkByReturnCarNo(String longitude, String latitude) {
		// TODO Auto-generated method stub
		return parkDao.getParkByReturnCarNo(longitude, latitude);
	}

	/**
	 * 根据坐标取车辆当前所在场站的编号（不一定有，如果附近没有场站，则返回空串）
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	@Override
	public String getCurrentParkNoByCarLocation(Double longitude, Double latitude) {
		String parkNo = null;
		if (longitude != null && longitude != 0.0d && latitude != null && latitude != 0.0d) { // 有效位置
			// GPS坐标系（wgs84标准）转为百度坐标系（bd09）
			SearchCondition condition = new SearchCondition();
			double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(longitude, latitude);
			condition.setLongitude(bdCoord[0]); // 经度（百度坐标）
			condition.setLatitude(bdCoord[1]); // 纬度（百度坐标）
			condition.setRadius(CURRENT_PARK_SEARCH_RADIUS);
			SearchResult searchResult = searchService.searchParkByPosition(condition);

			if (searchResult.getTotal() > 0) { // 搜索到附近有站点
				String parkType = sysParamService.getParamValueByParamKey(ParkConstant.PARK_TYPE);
				if ("1".equals(parkType)) { // 多边形场站
					SolrDoc parkDoc = searchResult.getDatas().get(0); // 最近的一个
					parkNo = parkDoc.getDataNo();

				} else {
					int parkElecFenceRadiusDefVal = 0; // 电子围栏的默认半径
					List<SolrDoc> hitParks = searchResult.getDatas(); // 搜索结果中的站点
					for (SolrDoc hitPark : hitParks) {
						Integer parkElecFenceRadius = hitPark.getElecFenceRadius(); // 该场站的电子围栏半径
						if (parkElecFenceRadius == null || parkElecFenceRadius <= 0) { // 电子围栏半径值无效，则取得并使用系统的默认值
							parkElecFenceRadiusDefVal = parkElecFenceRadiusDefVal == 0
									? this.getParkElecFenceRadiusDefaultValue() : parkElecFenceRadiusDefVal;
							parkElecFenceRadius = parkElecFenceRadiusDefVal;
						}

						if (hitPark.getDistance() != null && hitPark.getDistance() < parkElecFenceRadius / 1000.00d) { // 车辆已经进入到该场站的电子围栏半径内
							parkNo = hitPark.getDataNo(); // 可以认为车辆当前位于此场站
							break;
						}
					}

				}

			}
		}
		parkNo = parkNo == null ? "" : parkNo; // 不返回null
		return parkNo;
	}

	/**
	 * 获取场站电子围栏半径的系统默认值（单位：米）
	 * 
	 * @return
	 */
	private int getParkElecFenceRadiusDefaultValue() {
		int radius = 0;
		try {
			// 从系统参数里取得场站的默认电子围栏半径值
			String radiusDefValStr = sysParamService
					.getParamValueByParamKey(ParkConstant.SYS_PARAM_KEY_PARK_ELEC_FENCE_RADIUS_DEF_VAL);
			if (radiusDefValStr == null || radiusDefValStr.equals("")) { // 如果系统参数里也没设置此值，则直接使用常量值。
				radius = ParkConstant.PARK_ELEC_FENCE_RADIUS_DEF_VAL;
			} else {
				radius = Integer.parseInt(radiusDefValStr); // 尝试转为int型
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		radius = radius <= 0 ? ParkConstant.PARK_ELEC_FENCE_RADIUS_DEF_VAL : radius; // 如果根据系统参数得来的值，经转换后的int值无效，则使用常量。
		return radius;
	}

	@Override
	public ResultInfo<List<ParkRegion>> getParkListByCityTake(Query q, String longitude, String latitude, Integer tag,
			String memberNo) {
		// TODO Auto-generated method stub
		ResultInfo<List<ParkRegion>> resultInfo = new ResultInfo<List<ParkRegion>>();
		List<ParkRegion> list = null;
		try {
			// 直接调用Dao方法进行查询
			// 直辖市分出来
			if (tag.equals(1)) {
				list = parkDao.getParkListByCityTake1(q);
			} else {
				list = parkDao.getParkListByCityTake(q);
			}
			if (list != null && list.size() > 0) {
				for (ParkRegion pr : list) {
					Park park = new Park();
					if (tag.equals(1)) {
						if (pr.getAddrRegion3Name() != null && !pr.getAddrRegion3Name().equals("")) {
							park.setAddrRegion2Name(pr.getAddrRegion3Name());
						}
					} else {
						if (pr.getAddrRegion3Name() != null && !pr.getAddrRegion3Name().equals("")) {
							park.setAddrRegion3Name(pr.getAddrRegion3Name());
						}
					}
					park.setIsAvailable(1);
					park.setIsPublic(1);
					park.setIsDeleted(0);
					q.setQ(park);
					List<Park> parkList = getParkListByReturn(q, longitude, latitude, memberNo);
					pr.setParkTakeNum(parkList.size());
					ResultInfo<List<ParkVOAround>> resultInfo1 = new ResultInfo<List<ParkVOAround>>();
					resultInfo1 = parkToVoAround(resultInfo1, parkList, longitude, latitude, memberNo);
					pr.setParkList(resultInfo1.getData());

				}

				resultInfo.setData(list);
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(ParkConstant.no_park);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultInfo;
	}

	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<ParkVOAround>> parkToVoAround(ResultInfo<List<ParkVOAround>> result, List<Park> paList,
			String longitude, String latitude, String memberNo) {

		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
		Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%电量

		if (paList != null && paList.size() > 0) {
			List<ParkVOAround> voList = new ArrayList<ParkVOAround>();
			for (Park p : paList) {
				ParkVOAround po = new ParkVOAround();
				String addressDetail = "";
				if (p.getAddrRegion3Name() != null && p.getAddrRegion3Name().length() != 0) {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrRegion3Name()
							+ p.getAddrStreet();
				} else {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrStreet();
				}
				po.setAddressDetail(addressDetail);
				po.setLatitude(p.getLatitude());
				po.setLongitude(p.getLongitude());
				po.setParkName(p.getParkName());
				po.setParkNo(p.getParkNo());
				po.setParkType(p.getParkType());

				// 先根据memberNo判断是普通会员还是集团会员
				if (memberNo != null && !memberNo.equals("")) {
					Member member = memberService.getMember(memberNo);
					if (member != null) {
						if (member.getMemberType().equals(1)) {// 普通会员

							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
							p.setAvailableCarNum(availableCarNum);

						} else if (member.getMemberType().equals(2)) {// 集团会员
							String companyId = member.getCompanyId();

							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
							Integer availableCarNum1 = carDao.getCarCountByParkMember1(p.getParkNo(), companyId, power);
							p.setAvailableCarNum(availableCarNum + availableCarNum1);

						}
					} else {

						// 获取可用车辆数
						Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
						p.setAvailableCarNum(availableCarNum);

					}
				} else {

					// 获取可用车辆数
					Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
					p.setAvailableCarNum(availableCarNum);

				}

				if (p.getAvailableCarNum() != null) {
					po.setCarNum(p.getAvailableCarNum());
				} else {
					po.setCarNum(0);
				}
				if (p.getDistance() != null) {
					po.setDistance(p.getDistance());
				} else {
					po.setDistance(0d);
				}

				voList.add(po);
			}

			result.setData(voList);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.reminder_msg);
		}
		return result;
	}

	@Override
	public ParkSearchCondition getBaiDuLocation(Double longitude, Double latitude) {
		ParkSearchCondition searchCondition = new ParkSearchCondition();
		if (longitude != null && longitude != 0.0d && latitude != null && latitude != 0.0d) { // 有效位置
			// GPS坐标系（wgs84标准）转为百度坐标系（bd09）
			double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(longitude, latitude);
			searchCondition.setPosLongitude(bdCoord[0]); // 经度（百度坐标）
			searchCondition.setPosLatitude(bdCoord[1]); // 纬度（百度坐标）
			searchCondition.setRadius(CURRENT_PARK_SEARCH_RADIUS);
		}
		return searchCondition;
	}

	@Override
	public ResultInfo<List<WorkerRegion>> getWorkerListByCityTake(String workerNo, Query q, String longitude,
			String latitude, Integer tag) {
		// TODO Auto-generated method stub
		ResultInfo<List<WorkerRegion>> resultInfo = new ResultInfo<List<WorkerRegion>>();
		List<WorkerRegion> list = null;
		try {
			// 直接调用Dao方法进行查询
			// 直辖市分出来
			if (tag.equals(1)) {
				list = parkDao.getWorkerListByCityTake1(workerNo);
			} else {
				list = parkDao.getWorkerListByCityTake(workerNo);
			}
			if (list != null && list.size() > 0) {
				for (WorkerRegion pr : list) {

					// WorkerRegion pr=list.get(0);
					Park park = new Park();
					if (tag.equals(1)) {
						if (pr.getAddrRegion3Name() != null && !pr.getAddrRegion3Name().equals("")) {
							park.setAddrRegion2Name(pr.getAddrRegion3Name());
						}
					} else {
						if (pr.getAddrRegion3Name() != null && !pr.getAddrRegion3Name().equals("")) {
							park.setAddrRegion3Name(pr.getAddrRegion3Name());
						}
					}
					park.setIsAvailable(1);
					park.setIsPublic(1);
					park.setIsDeleted(0);
					q.setQ(park);
					List<Park> parkList = getParkListByReturn(q, longitude, latitude, null);
					ResultInfo<List<ParkVOAround>> resultInfo1 = new ResultInfo<List<ParkVOAround>>();
					resultInfo1 = parkToVoAround2(workerNo, resultInfo1, parkList);
					pr.setParkList(resultInfo1.getData());
					resultInfo.setData(list);
					resultInfo.setCode(Constant.SECCUESS);
				}
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(ParkConstant.no_park);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultInfo;
	}

	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<ParkVOAround>> parkToVoAround(String workerNo, ResultInfo<List<ParkVOAround>> result,
			List<Park> paList) {

		if (paList != null && paList.size() > 0) {
			List<ParkVOAround> voList = new ArrayList<ParkVOAround>();
			for (Park p : paList) {
				ParkVOAround po = new ParkVOAround();
				String addressDetail = "";
				if (p.getAddrRegion3Name() != null && p.getAddrRegion3Name().length() != 0) {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrRegion3Name()
							+ p.getAddrStreet();

					String parkNo = p.getParkNo();
					List<ParkVOAround> list = workerOrderDao.getWorkerOrderListNum(workerNo, p.getAddrRegion1Name(),
							p.getAddrRegion2Name(), p.getAddrRegion3Name());
					for (ParkVOAround parkvoa : list) {
						String parkNo2 = parkvoa.getParkNo();
						if (parkNo2.equals(parkNo)) {
							po.setWorkerTakeNum(parkvoa.getWorkerTakeNum());
						}
					}
				} else {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrStreet();
				}
				po.setAddressDetail(addressDetail);
				po.setLatitude(p.getLatitude());
				po.setLongitude(p.getLongitude());
				po.setParkName(p.getParkName());
				po.setParkNo(p.getParkNo());
				po.setParkType(p.getParkType());
				if (p.getAvailableCarNum() != null) {
					po.setCarNum(p.getAvailableCarNum());
				} else {
					po.setCarNum(0);
				}
				if (p.getDistance() != null) {
					po.setDistance(p.getDistance());
				} else {
					po.setDistance(0d);
				}
				if (po.getWorkerTakeNum() > 0) {
					voList.add(po);
				}
			}
			result.setData(voList);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.reminder_msg);
		}
		return result;
	}

	ResultInfo<List<ParkVOAround>> parkToVoAround2(String workerNo, ResultInfo<List<ParkVOAround>> result,
			List<Park> paList) {

		if (paList != null && paList.size() > 0) {
			List<ParkVOAround> voList = new ArrayList<ParkVOAround>();
			for (Park p : paList) {

				ParkVOAround po = new ParkVOAround();
				String addressDetail = "";
				if (p.getAddrRegion3Name() != null && p.getAddrRegion3Name().length() != 0) {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrRegion3Name()
							+ p.getAddrStreet();
				} else {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrStreet();
				}
				po.setAddressDetail(addressDetail);

				// 获取场站的实时车辆(场站状态的车辆数)
				CarStatus car = new CarStatus();
				car.setLocationParkNo(p.getParkNo());
				List<CarStatus> cars = carStatusService.getCarSpaceShortage(new Query(car));
				if (cars != null && cars.size() > 0) {
					po.setCarNum(cars.get(0).getCarSpaceShortage());
				} else {
					po.setCarNum(0);
				}
				// ParkStatus ps=parkStatusDao.get(p.getParkNo());
				// if(ps!=null){
				// po.setCarNum(ps.getCarNumber());
				// }
				// 获取场站的实时任务量
				List<WorkerOrder> workerOList = workerOrderDao.getWorkerOrderNumByParkNo(p.getParkNo(), workerNo);
				if (workerOList != null && workerOList.size() > 0) {
					ResultInfo<List<WorkerOrderParkVo>> result1 = new ResultInfo<List<WorkerOrderParkVo>>();
					ResultInfo<List<WorkerOrderParkVo>> resultInfo = workerOrderParkToVo1(result1, workerOList);
					po.setWoList(resultInfo.getData());
					po.setWorkerTakeNum(workerOList.size());
				} else {
					po.setWoList(null);
					po.setWorkerTakeNum(0);
				}
				po.setParkName(p.getParkName());
				po.setParkNo(p.getParkNo());
				if (po.getCarNum() > 0 && po.getWorkerTakeNum() > 0) {
					voList.add(po);
				}

			}
			result.setData(voList);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.reminder_msg);
		}
		return result;
	}

	ResultInfo<List<WorkerOrderParkVo>> workerOrderParkToVo1(ResultInfo<List<WorkerOrderParkVo>> result,
			List<WorkerOrder> woList) {
		List<WorkerOrderParkVo> list = new ArrayList<WorkerOrderParkVo>();
		if (woList != null && woList.size() > 0) {
			for (WorkerOrder w : woList) {
				WorkerOrderParkVo or = new WorkerOrderParkVo();
				Car car = carDao.get(w.getCarNo());

				if (car != null) {
					// 型号
					DataDictItem dataDictItemModel = dataDictItemService.getDataDictItem(car.getCarModelId());
					if (dataDictItemModel != null) {
						or.setCarModelName(dataDictItemModel.getItemValue());
					}

					or.setCarBrandName(car.getCarBrandName());
					or.setCarPhotoUrl1(imgPath + "/" + car.getCarPhotoUrl1());
				}
				or.setCarPlateNo(w.getCarPlateNo());
				// 预估里程（暂未确定）
				or.setMileage(0d);
				or.setMemo(w.getMemo());
				or.setWorkerOrderNo(w.getWorkerOrderNo());
				or.setStartParkName(w.getStartParkName());
				or.setTerminalParkName(w.getTerminalParkName());

				list.add(or);
			}

			result.setData(list);
			result.setCode(CarConstant.success_code);
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}

	@Override
	public List<Park> getSearchParkList(String longitude, String latitude, String address, String memberNo,
			Integer isPayment, Integer isLimit,String carPlateNo) {
		List<Park> list = new ArrayList<Park>();
		try {
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%电量
			ParkSearch parkSearch = new ParkSearch();
			parkSearch.setParkName(address);
			parkSearch.setLimit(isLimit);
			parkSearch.setIsPayment(isPayment);
			parkSearch.setMemberNo(memberNo);
			parkSearch.setLongitude(longitude);
			parkSearch.setLatitude(latitude);
			if (memberNo != null && !memberNo.equals("")) {
				Member members = memberService.getMember(memberNo);
				if (members != null && members.getCompanyId() != null && !members.getCompanyId().equals("")) {
					Company company = companyService.getCompany(members.getCompanyId());
					// 判断当前会员所属的集团是否停用，停用的按照普通会员进行查询，否则按照集团会员进行查询
					if (null != company && company.getCompanyStatus() == 1) {
						parkSearch.setCompanyId(members.getCompanyId());
					}
				}
			}
			Query q = new Query(parkSearch);
			list = parkDao.getParkListBySearch(q);

			// 获取距离 和 可用 车辆数
			if (list != null && list.size() > 0) {
				for (Park p : list) {
					if (longitude != null && !longitude.equals("") && latitude != null && !latitude.equals("")) {
						Double distance = getDistanceByPoint(longitude, latitude, p.getLongitude(), p.getLatitude());
						if (distance != null) {
							p.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
						} else {
							p.setDistance(0d);
						}
					} else {
						p.setDistance(0d);
					}

					// 获取可用车辆数
					if (memberNo != null && !memberNo.equals("")) {
						Member member = memberService.getMember(memberNo);
						if (member != null) {
							if (member.getMemberType().equals(1)) {// 普通会员

								// 获取可用车辆数
								Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
								p.setAvailableCarNum(availableCarNum);

							} else if (member.getMemberType().equals(2)) {// 集团会员
								String companyId = member.getCompanyId();

								// 获取可用车辆数
								Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
								Integer availableCarNum1 = carDao.getCarCountByParkMember1(p.getParkNo(), companyId,
										power);
								p.setAvailableCarNum(availableCarNum + availableCarNum1);

							}
						} else {

							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
							p.setAvailableCarNum(availableCarNum);
						}
					} else {
						// 获取可用车辆数
						Integer availableCarNum = carDao.getCarCountByParkMember(p.getParkNo(), power);
						p.setAvailableCarNum(availableCarNum);
					}

				}
				if (list.size()>0) {	
					   Collections.sort(list, new Comparator<Park>() {
						@Override
						public int compare(Park o1, Park o2) {
							// TODO Auto-generated method stub
													
							if (o1.getDistance() > o2.getDistance()) {
								return 1;
							}
							if (o1.getDistance() == o2.getDistance()) {
								return 0;
							}
							return -1;
						}					   
					   });
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		if (list.size()>0) {
			if (carPlateNo!=null&&!"".equals(carPlateNo)) {
				//对比还车场站是否是红包车目标场站
				CarRedPacket carRedPacketTarget = carRedPacketService.getByCarPlateNo(carPlateNo);
				if (carRedPacketTarget!=null) {
					String carRedPacketTartetParkNoStr = carRedPacketTarget.getParkNo();
					String[] carRedPacketTartetParkNoArr = carRedPacketTartetParkNoStr.split(",");
					for(String targetParkNo:carRedPacketTartetParkNoArr){
						for(Park park:list){
							if (park.getParkNo().equals(targetParkNo)) {
								park.setIsRedPacketTargetPark("1");
								break;
							}
						}
					}
				}
			}
			String locationParkNos = carRedPacketService.getCarRedParketLocationParkNos(1);
			if (locationParkNos!=null&&!"".equals(locationParkNos)) {
				String[] parkNos = locationParkNos.split(",");
				for(int i=0;i<parkNos.length;i++){
					for(Park park:list){
						if (park.getParkNo().equals(parkNos[i])) {
							park.setIsRedPakcetPark("1");
							break;
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public Long count(Query q) {
		return parkDao.count(q);
	}

	@Override
	public void updateParkByChargerNumber(Park pa, Operator operator) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();
		if (pa == null || pa.getParkNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " pa = " + pa);
		} else {
			parkDao.update(pa);
		}
	}

	/**
	 * SolrDoc对象转换为CarVoAround对象
	 * 
	 * @param solrDoc
	 * @return
	 */
	private CarVoAround convertCarVoAround(SolrDoc solrDoc) {
		if (solrDoc == null) {
			return null;
		}
		CarVoAround carVoAround = new CarVoAround();
		carVoAround.setCarModelName(solrDoc.getCarModelName());
		carVoAround.setCarNo(solrDoc.getDataNo());
		carVoAround.setCarPlateNo(solrDoc.getDataName());
		carVoAround.setLatitude(solrDoc.getLatitude());
		carVoAround.setPower(solrDoc.getPower());
		carVoAround.setLongitude(solrDoc.getLongitude());
		carVoAround.setAddress(solrDoc.getAddress() == null ? "" : solrDoc.getAddress());
		carVoAround.setDistance(solrDoc.getDistance() == null ? 0 : solrDoc.getDistance());
		return carVoAround;
	}

	/**
	 * SolrDoc对象转换为ParkVOAround对象
	 * 
	 * @param solrDoc
	 * @return
	 */
	private ParkVOAround convertParkVOAround(SolrDoc solrDoc) {
		if (solrDoc == null) {
			return null;
		}
		ParkVOAround parkVOAround = new ParkVOAround();
		parkVOAround.setLatitude(solrDoc.getLatitude());
		parkVOAround.setParkType(solrDoc.getParkType());
		parkVOAround.setLongitude(solrDoc.getLongitude());
		parkVOAround.setParkName(solrDoc.getDataName());
		parkVOAround.setParkNo(solrDoc.getDataNo());
		parkVOAround.setAddressDetail(solrDoc.getAddress() == null ? "" : solrDoc.getAddress());
		parkVOAround.setParkingSpaces(solrDoc.getParkingSpaces() == null ? 0 : solrDoc.getParkingSpaces());
		parkVOAround.setDistance(solrDoc.getDistance() == null ? 0 : solrDoc.getDistance());
		return parkVOAround;
	}

	/**
	 * 定位查询周边场站信息列表（返回最近的场站中有车可用或车辆）
	 */
	@Override
	public CarOrParkVo getNearLimitOne(String longitude, String latitude, Double distance, String memberNo,String seaTing) {

		// 定义返回结果集
		CarOrParkVo result = new CarOrParkVo();

		try {
			boolean isMember = false;

			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setLongitude(Double.valueOf(longitude));
			searchCondition.setLatitude(Double.valueOf(latitude));
			searchCondition.setRadius(distance);// 半径
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%
			searchCondition.setPower(power);// 电量
			SysParam reTurnCarType = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
			if(reTurnCarType != null && "1".equals(reTurnCarType.getParamValue())){
				searchCondition.setSeaTing("");
			}else{
				searchCondition.setSeaTing(seaTing);
			}
			
			searchService.setDefaultParmForQueryAround(searchCondition);// 设置查询其有效车辆和场站的默认值。
			// 先根据memberNo判断是普通会员还是集团会员
			if (memberNo != null && !memberNo.equals("")) {
				Member member = memberService.getMember(memberNo);
				if (member != null) {
					if (member.getMemberType().equals(2)) {// 集团会员
						String companyId = member.getCompanyId();
						SearchResult searchResult = searchService.searchAround(searchCondition);// 在solr中查询
						List<SolrDoc> searchData = searchResult == null ? null : searchResult.getDatas();
						if (searchData != null) {
							for (SolrDoc solrDoc : searchData) {
								if (solrDoc != null && solrDoc.getDataType() != null) {
									// 集团会员只能看到非集团场站和车辆，以及自身集团场站和车辆（看不到其他集团的场站和车辆）
									if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_CAR) {
										if (solrDoc.getCompanyId() == null
												|| solrDoc.getCompanyId().equals(companyId)) {
											// 判断车辆是否有场站
											CarVoAround ca = this.convertCarVoAround(solrDoc);// solrDoc转换CarVoAround
											if (null != ca && ca.getCarNo() != null) {
												// 判断运营模式 ， 场站+任意点 以及 任意点的情况下
												// 才会进来
												SysParam returnCarType = sysParamService
														.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
												String returnCar = returnCarType == null ? "1"
														: returnCarType.getParamValue();
												if ("2".equals(returnCar)) { // 场站+任意点
																				// 放场站外的车
													CarStatus cs = carStatusService.getCarStatus(ca.getCarNo());
													if (null != cs && (cs.getLocationParkNo().trim().length() == 0
															|| cs.getLocationParkNo() == null)) {
														result.setCarVoAround(ca);// 场站外的车，可以直接租用
														break;
													}
//													if (null != cs ) {
//														result.setCarVoAround(ca);
//														break;
//													}
												} else if ("3".equals(returnCar)) { // 任意点，
																					// 场站外的车
													// 逻辑上是否需要判断 车辆是否有所在场站
													result.setCarVoAround(ca);// 场站外的车，可以直接租用
													break;
												}
											}
										}
									} else if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_PARK) {
										// 获取可用车辆数(非集团车辆数+集团车辆数)
										boolean isAdd = false;
										// 校验场站是否所属集团中是否拥有该会员的集团id
										if (solrDoc.getCompanyId() != null && !solrDoc.getCompanyId().equals("")) {
											String complanId = "," + solrDoc.getCompanyId() + ",";
											isAdd = complanId.indexOf("," + companyId + ",") > -1;
										} else {
											isAdd = true;
										}
										if (isAdd) {
											ParkVOAround parkVOAround = this.convertParkVOAround(solrDoc);// solrDoc转换ParkVOAround
											// 获取可用车辆数
											Integer availableCarNum = carDao
													.getCarCountByParkMemberAndSeaTing(solrDoc.getDataNo(), power,seaTing);
											Integer availableCarNum1 = carDao
													.getCarCountByParkMember1AndSeaTing(solrDoc.getDataNo(), companyId, power,seaTing);
											availableCarNum = availableCarNum == null ? 0 : availableCarNum;
											availableCarNum1 = availableCarNum1 == null ? 0 : availableCarNum1;
											parkVOAround.setCarNum(availableCarNum + availableCarNum1);
											if (parkVOAround.getCarNum() > 0) {
												result.setParkVoAround(parkVOAround);
												break;
											}
										}
									}
								}
							}
						}
						isMember = true;
					}
				}
			}
			if (isMember == false) {// 会员外一律返回所属集团为空的数据
				SearchResult searchResult = searchService.searchAround(searchCondition);// 在solr中查询
				List<SolrDoc> searchData = searchResult == null ? null : searchResult.getDatas();
				if (searchData != null) {
					for (SolrDoc solrDoc : searchData) {
						// 普通会员只能看到车辆，场站的所属集团为空的数据
						if (solrDoc != null && solrDoc.getCompanyId() == null || solrDoc.getCompanyId().equals("")) {
							if (solrDoc.getDataType() != null) {
								if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_CAR) {
									// 判断车辆是否有场站
									CarVoAround ca = this.convertCarVoAround(solrDoc);// solrDoc转换CarVoAround
									if (null != ca && ca.getCarNo() != null) {
										// 判断运营模式 ， 场站+任意点 以及 任意点的情况下 才会进来
										SysParam returnCarType = sysParamService
												.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
										String returnCar = returnCarType == null ? "1" : returnCarType.getParamValue();
										if ("2".equals(returnCar)) { // 场站+任意点
																		// 放场站外的车
											CarStatus cs = carStatusService.getCarStatus(ca.getCarNo());
											if (null != cs && (cs.getLocationParkNo().trim().length() == 0
													|| cs.getLocationParkNo() == null)) {
												result.setCarVoAround(ca);// 场站外的车，可以直接租用
												break;
											}
//											if (null != cs) {
//												result.setCarVoAround(ca);
//												break;
//											}
										} else if ("3".equals(returnCar)) { // 任意点，
																			// 场站外的车
											// 逻辑上是否需要判断 车辆是否有所在场站
											result.setCarVoAround(ca);// 场站外的车，可以直接租用
											break;
										}
									}
								} else if (solrDoc.getDataType().intValue() == SolrDocConstants.DATA_TYPE_PARK) {
									ParkVOAround parkVOAround = this.convertParkVOAround(solrDoc);// solrDoc转换ParkVOAround
									// 获取可用车辆数
									Integer availableCarNum = carDao.getCarCountByParkMemberAndSeaTing(solrDoc.getDataNo(),
											power,seaTing);
									parkVOAround.setCarNum(availableCarNum);
									if (availableCarNum > 0) {
										result.setParkVoAround(parkVOAround);
										break;
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (result!=null) {
			ParkVOAround parkVo = result.getParkVoAround();
			if (parkVo!=null) {
				String locationParkNos = carRedPacketService.getCarRedParketLocationParkNos(1);
				if (locationParkNos!=null&&!"".equals(locationParkNos)) {
					String[] parkNos = locationParkNos.split(",");
					for(int i=0;i<parkNos.length;i++){
						if (parkVo.getParkNo().equals(parkNos[i])) {
							parkVo.setIsRedPakcetPark("1");
							break;
						}
					}
				}
			}
			CarVoAround carVo = result.getCarVoAround();
			if (carVo!=null) {
				CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(carVo.getCarPlateNo(),"1");
				if (carRedPacket!=null) {
					carVo.setIsCarRedPakcet("1");
				}
			}
		}
		return result;
	}

	@Override
	public ParkLocationNs getParkByReturnNo(String longitude, String latitude) {
		return parkDao.getParkByReturnNo(longitude, latitude);
	}

	@Override
	public List<ParkDayVo> getParkByCityId(String longitude, String latitude, String cityId) {
		return parkDao.getParkByCityId(longitude, latitude, cityId);
	}

	@Override
	public List<ParkDotVo> dotPark(Query q) {
		List<ParkDotVo> list = null;
		try {
			// 已删除的不查出
			Park park = (Park) q.getQ();
			if (park != null) {
				park.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = parkDao.dotPark(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkDotVo>(0) : list;
		return list;
	}

	@Override
	public List<Park> listPark(String franchiseeNo) {
		List<Park> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = parkDao.listPark(franchiseeNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	@Override
	public List<Park> getMonitorParkList(Query q) {
		List<Park> list = null;
		try {
			// 已删除的不查出
			Park park = (Park) q.getQ();
			if (park != null) {
				park.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = parkDao.getMonitorParkList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Park>(0) : list;
		return list;
	}

	@Override
	public List<ParkCarNumVo> getParkCarNumList(int userageStatus, int isAvailable, int removeStatus) {
		List<ParkCarNumVo> list = null;
		try {
			list = parkDao.getParkCarNumList(userageStatus, isAvailable, removeStatus);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkCarNumVo>(0) : list;
		return list;
	}

}
