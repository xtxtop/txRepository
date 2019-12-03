package cn.com.shopec.core.dailyrental.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.dao.ParkDayDao;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.search.common.SolrDocConstants;
import cn.com.shopec.core.dailyrental.search.model.SearchCondition;
import cn.com.shopec.core.dailyrental.search.model.SearchResult;
import cn.com.shopec.core.dailyrental.search.model.SolrDoc;
import cn.com.shopec.core.dailyrental.search.service.DayBusinessIndexService;
import cn.com.shopec.core.dailyrental.search.service.DaySearchService;
import cn.com.shopec.core.dailyrental.service.ParkDayService;
import cn.com.shopec.core.dailyrental.vo.ParkDayAroundVO;
import cn.com.shopec.core.dailyrental.vo.ParkDayRegionVo;
import cn.com.shopec.core.system.dao.SysRegionDao;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictItemService;

/**
 * 商家门店 服务实现类
 */
@Service
public class ParkDayServiceImpl implements ParkDayService {

	private static final Log log = LogFactory.getLog(ParkDayServiceImpl.class);
	
	@Resource
	private ParkDayDao parkDayDao;
	
	@Resource
	private DaySearchService searchService;

	@Resource
	private SysRegionDao sysRegionDao;
	@Resource
	private DayBusinessIndexService businessIndexService;

	@Resource
	private DataDictItemService dataDictItemService;		

	/**
	 * 根据查询条件，查询并返回ParkDay的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkDay> getParkDayList(Query q) {
		List<ParkDay> list = null;
		try {
			//直接调用Dao方法进行查询
			list = parkDayDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkDay>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回ParkDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ParkDay> getParkDayPagedList(Query q) {
		PageFinder<ParkDay> page = new PageFinder<ParkDay>();
		
		List<ParkDay> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = parkDayDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = parkDayDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkDay>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据查询条件，分页查询并返回ParkDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ParkDay> getParkDayPagedListForMgt(Query q) {
		PageFinder<ParkDay> page = new PageFinder<ParkDay>();
		
		List<ParkDay> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = parkDayDao.pageListForMgt(q);
			if(list!=null){
				for(ParkDay park:list){
					park.setAddrStreet(park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet());
				}
			}
			//调用dao统计满足条件的记录总数
			rowCount = parkDayDao.countForMgt(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkDay>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个ParkDay对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ParkDay getParkDay(String id) {
		ParkDay obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = parkDayDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ParkDay对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkDay> getParkDayByIds(String[] ids) {
		List<ParkDay> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = parkDayDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkDay>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的ParkDay记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkDay> delParkDay(String id, Operator operator) {
		ResultInfo<ParkDay> resultInfo = new ResultInfo<ParkDay>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = parkDayDao.delete(id);
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
	 * 新增一条ParkDay记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param parkDay 新增的ParkDay数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkDay> addParkDay(ParkDay parkDay, Operator operator) {
		ResultInfo<ParkDay> resultInfo = new ResultInfo<ParkDay>();
		
		if (parkDay == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkDay = " + parkDay);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (parkDay.getParkId() == null) {
					parkDay.setParkId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					parkDay.setOperatorType(operator.getOperatorType());
					parkDay.setOperatorId(operator.getOperatorId());
				}
				
				if(parkDay.getAddrRegion1Name() != null){
					SysRegion sysRegion = sysRegionDao.getRegionIdByRegionName(parkDay.getAddrRegion1Name());
					if(sysRegion != null){
						parkDay.setAddrRegion1Id(sysRegion.getRegionId());
					}
				}
				
				if(parkDay.getAddrRegion2Name() != null){
					SysRegion sysRegion = sysRegionDao.getRegionIdByRegionName(parkDay.getAddrRegion2Name());
					if(sysRegion != null){
						parkDay.setAddrRegion2Id(sysRegion.getRegionId());
					}
					List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
					for(DataDictItem item:cities){
						if(item.getItemValue().trim().equals(parkDay.getAddrRegion2Name())){
							parkDay.setCityId(item.getDataDictItemId());
							break;
						}
					}
				}

				if(parkDay.getAddrRegion3Name() != null){
					List<SysRegion> list = sysRegionDao.getListByRegionName(parkDay.getAddrRegion3Name());
					if(list != null){
						for(SysRegion sysRegion : list){
							if(sysRegion.getParentId().equals(parkDay.getAddrRegion2Id())){
								parkDay.setAddrRegion3Id(sysRegion.getRegionId());
								break;
							}
						}
					}
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				parkDay.setCreateTime(now);
				parkDay.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(parkDay);
				
				//调用Dao执行插入操作
				parkDayDao.add(parkDay);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(parkDay);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}	
		}
		
		return resultInfo;
	}			
	
	/**
	 * 根据主键，更新一条ParkDay记录
	 * @param parkDay 更新的ParkDay数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkDay> updateParkDay(ParkDay parkDay, Operator operator) {
		ResultInfo<ParkDay> resultInfo = new ResultInfo<ParkDay>();
		
		if (parkDay == null || parkDay.getParkId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkDay = " + parkDay);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					parkDay.setOperatorType(operator.getOperatorType());
					parkDay.setOperatorId(operator.getOperatorId());
				}
				
				if(parkDay.getAddrRegion1Name() != null){
					SysRegion sysRegion = sysRegionDao.getRegionIdByRegionName(parkDay.getAddrRegion1Name());
					if(sysRegion != null){
						parkDay.setAddrRegion1Id(sysRegion.getRegionId());
					}
				}
				
				if(parkDay.getAddrRegion2Name() != null){
					SysRegion sysRegion = sysRegionDao.getRegionIdByRegionName(parkDay.getAddrRegion2Name());
					if(sysRegion != null){
						parkDay.setAddrRegion2Id(sysRegion.getRegionId());
					}
					List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
					for(DataDictItem item:cities){
						if(item.getItemValue().trim().equals(parkDay.getAddrRegion2Name())){
							parkDay.setCityId(item.getDataDictItemId());
							break;
						}
					}
				}

				if(parkDay.getAddrRegion3Name() != null){
					List<SysRegion> list = sysRegionDao.getListByRegionName(parkDay.getAddrRegion3Name());
					if(list != null){
						for(SysRegion sysRegion : list){
							if(sysRegion.getParentId().equals(parkDay.getAddrRegion2Id())){
								parkDay.setAddrRegion3Id(sysRegion.getRegionId());
								break;
							}
						}
					}
				}
				
				//设置更新时间为当前时间
				parkDay.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = parkDayDao.update(parkDay);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(parkDay);
			} catch (Exception e) {
				e.printStackTrace();
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
	 * 为ParkDay对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ParkDay obj) {
		if (obj != null) {
			if(obj.getIsVailable()==null){
				obj.setIsVailable(0);
			}
		}
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
	public ResultInfo<ParkDay> changeParkDayState(ParkDay parkDay, Operator operator) {
		ResultInfo<ParkDay> resultInfo = new ResultInfo<ParkDay>();
		if (parkDay == null || parkDay.getParkId() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkDay = " + parkDay);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					parkDay.setOperatorType(operator.getOperatorType());
					parkDay.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				parkDay.setUpdateTime(new Date());
				// 场站状态（0、停用，1、启用，默认0）
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = parkDayDao.update(parkDay);
				if (count > 0) {
					if (parkDay.getIsVailable() != null) {// 门店启停用时门店搜索引擎索引中
						businessIndexService.saveOrUpdatePark(parkDay.getParkId());
					}
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(parkDay);
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
	public List<ParkDayAroundVO> getParkDayListByAround(String longitude, String latitude,String cityId) {
		List<ParkDayAroundVO> list = new ArrayList<ParkDayAroundVO>();
		try {
		//list = parkDayDao.getParkDayListByAround(cityId);
			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setDataType(1);	//type1 场站   2车辆   3区域
			searchCondition.setIsAvailable(1);// 上线，启用
			if(longitude != null  && !"".equals(longitude)){
				searchCondition.setLongitude(Double.valueOf(longitude));
				searchCondition.setLatitude(Double.valueOf(latitude));
			}
			searchCondition.setCityId(cityId);
			SearchResult searchResult = searchService.searchParkDayAround(searchCondition);// 在solr中查询
			List<SolrDoc> searchData = searchResult == null ? null : searchResult.getDatas();
			if (searchData != null  && searchData.size()>0 ) {
				for (int i = 0; i < searchData.size(); i++) {
					if (searchData.get(i).getDataType().intValue() == SolrDocConstants.DATA_TYPE_PARK){
						ParkDayAroundVO parkDayAroundVO = this.convertParkDayVOAround(searchData.get(i));// solrDoc转换ParkVOAround
						if(i==0){
							parkDayAroundVO.setIsNear("1");
						}else{
							parkDayAroundVO.setIsNear("0");
						}
						ParkDay pdy=getParkDay(parkDayAroundVO.getParkId());
						if(pdy != null){
							parkDayAroundVO.setBusinessHours(pdy.getBusinessHours());
						}
						list.add(parkDayAroundVO);
						
					}
				}
			}
		
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkDayAroundVO>(0) : list;
		return list;
	}

	/**
	 * SolrDoc对象转换为ParkVOAround对象
	 * 
	 * @param solrDoc
	 * @return
	 */
	
	private ParkDayAroundVO convertParkDayVOAround(SolrDoc solrDoc) {
		if (solrDoc == null) {
			return null;
		}
		ParkDayAroundVO parkDayAroundVO = new ParkDayAroundVO();
		parkDayAroundVO.setLatitude(solrDoc.getLatitude());
		parkDayAroundVO.setLongitude(solrDoc.getLongitude());
		parkDayAroundVO.setParkName(solrDoc.getDataName());
		parkDayAroundVO.setParkId(solrDoc.getDataNo());
		parkDayAroundVO.setAddrStreet(solrDoc.getAddress() == null ? "" : solrDoc.getAddress());
		parkDayAroundVO.setDistance(solrDoc.getDistance()==null?0:solrDoc.getDistance());
		return parkDayAroundVO;
	}

	@Override
	public ResultInfo<List<ParkDayRegionVo>> getParkDayListByCityTake(Query q,String longitude, String latitude,Integer tag) {
		ResultInfo<List<ParkDayRegionVo>> resultInfo = new ResultInfo<List<ParkDayRegionVo>>();
		List<ParkDayRegionVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			// 直辖市分出来
			if (tag.equals(1)) {
				list = parkDayDao.getParkDayListByCityTake1(q);
			} else {
				list = parkDayDao.getParkDayListByCityTake(q);
			}
			if (list != null && list.size() > 0) {
				for (ParkDayRegionVo pdv : list) {
					ParkDay pd = new ParkDay();
					if (tag.equals(1)) {
						if (pdv.getAddrRegion3Name() != null && !pdv.getAddrRegion3Name().equals("")) {
							pd.setAddrRegion2Name(pdv.getAddrRegion3Name());
						}
					} else {
						if (pdv.getAddrRegion3Name() != null && !pdv.getAddrRegion3Name().equals("")) {
							pd.setAddrRegion3Name(pdv.getAddrRegion3Name());
						}
					}
					pd.setIsVailable(1);
					q.setQ(pd);
					List<ParkDay> parkList = parkDayDao.queryAll(q);
					if (parkList != null && parkList.size() > 0) {
						for (ParkDay pdy : parkList) {
							if (longitude != null && !longitude.equals("") && latitude != null && !latitude.equals("")) {
								Double distance = getDistanceByPointDay(longitude, latitude, pdy.getLongitude()+"", pdy.getLatitude()+"");
								if (distance != null) {
									pdy.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
								} else {
									pdy.setDistance(0d);
								}
							} else {
								pdy.setDistance(0d);
							}
						}
					}
					ResultInfo<List<ParkDayAroundVO>> resultInfo1 = new ResultInfo<List<ParkDayAroundVO>>();
					resultInfo1 = parkDayToVoAround(resultInfo1, parkList, longitude, latitude);
					pdv.setParkList(resultInfo1.getData());

				}

				resultInfo.setData(list);
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("暂无门店");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultInfo;
	
	}
	
	
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<ParkDayAroundVO>> parkDayToVoAround(ResultInfo<List<ParkDayAroundVO>> result, List<ParkDay> paList,
			String longitude, String latitude) {
		if (paList != null && paList.size() > 0) {
			List<ParkDayAroundVO> voList = new ArrayList<ParkDayAroundVO>();
			for (ParkDay p : paList) {
				ParkDayAroundVO pdo = new ParkDayAroundVO();
				String addressDetail = "";
				if (p.getAddrRegion3Name() != null && p.getAddrRegion3Name().length() != 0) {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrRegion3Name()
							+ p.getAddrStreet();
				} else {
					addressDetail = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrStreet();
				}
				pdo.setAddrStreet(addressDetail);
				pdo.setLatitude(p.getLatitude()+"");
				pdo.setLongitude(p.getLongitude()+"");
				pdo.setParkName(p.getParkName());
				pdo.setParkId(p.getParkId());

					

				
				if (p.getDistance() != null) {
					pdo.setDistance(p.getDistance());
				} else {
					pdo.setDistance(0d);
				}

				voList.add(pdo);
			}

			result.setData(voList);
			result.setCode(Constant.SECCUESS);
			result.setMsg("");
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("暂无门店");
		}
		return result;
	}
	

	@Override
	public Double getDistanceByPointDay(String longitude, String latitude, String longitude2, String latitude2) {
		Double distance = 0d;
		try {
			// 调用dao，根据主键查询
			distance = parkDayDao.getDistanceByPoint(longitude, latitude, longitude2, latitude2);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return distance;
	}

	@Override
	public List<ParkDay> getSearchParkDayList(String longitude, String latitude, String parkName) {
		List<ParkDay> list = new ArrayList<ParkDay>();
		try {
			ParkDay pd = new ParkDay();
			pd.setParkName(parkName);
			pd.setIsVailable(1);
			Query q = new Query(pd);
			list = parkDayDao.getParDaykListBySearch(q);
			// 获取距离 
			if (list != null && list.size() > 0) {
				for (ParkDay pds : list) {
					if (longitude != null && !longitude.equals("") && latitude != null && !latitude.equals("")) {
						Double distance = getDistanceByPointDay(longitude, latitude, pds.getLongitude()+"", pds.getLatitude()+"");
						if (distance != null) {
							pds.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
						} else {
							pds.setDistance(0d);
						}
					} else {
						pds.setDistance(0d);
					}


				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkDay>(0) : list;
		return list;
	}
	/**
	 * 根据加盟商id和经纬度，查找距经纬度所有门店
	 * （距传入经纬度距离最近的门店去第一个）
	 * @param longitude
	 * @param latitude
	 * @param merchantIds
	 * @return
	 */
	@Override
	public List<ParkDay> searchParkDayListByMerchantIds(Double longitude, Double latitude, List<String> merchantIds,String cityId) {
		List<ParkDay> parkDayList = new ArrayList<ParkDay>();
		Map<Double, ParkDay> map = new HashMap<Double, ParkDay>(); 
		SearchCondition condition = new SearchCondition();
		condition.setLongitude(longitude);//经度
		condition.setLatitude(latitude);//纬度
		condition.setCityId(cityId);//城市
		for(String merchantId:merchantIds){
			condition.setMerchantId(merchantId);//商家id
			SearchResult searchResult = searchService.searchParkByPosition(condition);
			if(searchResult!=null){
				if(searchResult.getTotal()>0){
					List<SolrDoc> hitParkDays = searchResult.getDatas(); // 搜索结果中的门店
					for (SolrDoc hitParkDay : hitParkDays) {
						ParkDay parkDay = new ParkDay();
						parkDay.setParkId(hitParkDay.getDataNo());//门店id
						parkDay.setDistance(hitParkDay.getDistance());//搜索点到门店的距离
						parkDay.setMerchantId(hitParkDay.getMerchantId());//商家id
						map.put(hitParkDay.getDistance(), parkDay);
					}
				}
			}
		}
		parkDayList = this.bubbleSort(map);
		return parkDayList;
	}
	//冒泡排序
	private List<ParkDay> bubbleSort(Map<Double, ParkDay> map) {
		// doubel的距离值存入此list返回
		List<ParkDay> resultList = new ArrayList<ParkDay>();
		double a[] = new double[map.size()];
		int k = 0;
		for (double d : map.keySet()) {
			a[k] = d;
			k++;
		}
		// double
		// a[]={49.60,38.25,65,97,76,13,27,49,78,34,12,64,5,4,62,99.6,98,54,56,17,18,23,34,15,35,25,53,51};
		if(a.length>0){
			double temp = 0;
			for (int i = 0; i < a.length - 1; i++) {
				for (int j = 0; j < a.length - 1 - i; j++) {
					if (a[j] > a[j + 1]) {
						temp = a[j];
						a[j] = a[j + 1];
						a[j + 1] = temp;
					}
				}
			}
			for(int i=0;i<a.length;i++){
//			System.out.println(a[i]);
				resultList.add(map.get(a[i]));
			}
		}
		return resultList;
	}
}
