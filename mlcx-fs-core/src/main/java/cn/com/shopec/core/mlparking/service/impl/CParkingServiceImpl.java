package cn.com.shopec.core.mlparking.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.dao.AdvertMengLongDao;
import cn.com.shopec.core.ml.dao.CDocDao;
import cn.com.shopec.core.ml.model.CDoc;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.AdvertVo;
import cn.com.shopec.core.ml.vo.BannerVo;
import cn.com.shopec.core.mlparking.dao.CParkingDao;
import cn.com.shopec.core.mlparking.dao.CPliesNumberDao;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.model.CPliesNumber;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.core.mlparking.vo.CParkingDetailVo;
import cn.com.shopec.core.mlparking.vo.CParkingVo;
import cn.com.shopec.core.mlparking.vo.ParkingVo;

/**
 * 停车场 服务实现类
 */
@Service
public class CParkingServiceImpl implements CParkingService {

	private static final Log log = LogFactory.getLog(CParkingServiceImpl.class);

	@Resource
	private CParkingDao cParkingDao;
	@Resource
	private CDocDao docDao;
	@Resource
	private CPliesNumberDao pliesNumberDao;
	@Resource
	private AdvertMengLongDao advertMengLongDao;
	@Value("${image_path}")
	private String imgPath;

	/**
	 * 根据查询条件，查询并返回CParking的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CParking> getCParkingList(Query q) {
		List<CParking> list = null;
		try {
			// 已删除的不查出
			CParking cParking = (CParking) q.getQ();
			if (cParking != null) {
				cParking.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = cParkingDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParking>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CParking的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CParking> getCParkingPagedList(Query q) {
		PageFinder<CParking> page = new PageFinder<CParking>();

		List<CParking> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = cParkingDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cParkingDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParking>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CParking对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CParking getCParking(String id) {
		CParking obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cParkingDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CParking对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CParking> getCParkingByIds(String[] ids) {
		List<CParking> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = cParkingDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParking>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CParking记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParking> delCParking(String id, Operator operator) {
		ResultInfo<CParking> resultInfo = new ResultInfo<CParking>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			CParking cParking = new CParking();
			cParking.setParkingNo(id);
			cParking.setIsDeleted(Constant.DEL_STATE_YES);
			cParking.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				cParking.setOperatorType(operator.getOperatorType());
				cParking.setOperatorId(operator.getOperatorId());
			}

			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = cParkingDao.update(cParking);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(cParking);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条CParking记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cParking
	 *            新增的CParking数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParking> addCParking(CParking cParking, Operator operator, String parkingUrl,
			String[] spaceNumber,String  ground_spaceNumber_add,String [] under_spaceNumber_add) {
		ResultInfo<CParking> resultInfo = new ResultInfo<CParking>();

		if (cParking == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParking = " + cParking);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cParking.getParkingNo() == null) {
					cParking.setParkingNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cParking.setOperatorType(operator.getOperatorType());
					cParking.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				cParking.setCreateTime(now);
				cParking.setUpdateTime(now);
				cParking.setSurplusSpaceNumber(cParking.getParkingSpaceNumber());// 剩余车位
				cParking.setIsDeleted(1);// 删除状态
				cParking.setParkingStatus(0);// 停用状态
				cParking.setIsPublic(1);// 是否开放
				// 填充默认值
				this.fillDefaultValues(cParking);
				if (ground_spaceNumber_add != null&&!"".equals(ground_spaceNumber_add)) {
					cParking.setGroundNumber(1);
				}
				// 调用Dao执行插入操作
				cParkingDao.add(cParking);
				// 将图片保存在 c-doc表
				CDoc c = new CDoc();
				c.setDocNo("DAP" + String.valueOf(new Date().getTime()));
				c.setDocName("停车场详情(图片)");// 附件名称
				c.setFileName(parkingUrl);// 文件名
				c.setFileType(0);// 类型0图片 1视频 2文件
				c.setPosition(1);// 图片位置 0 列表,1详情
				c.setFileUrl(parkingUrl);// 图片路径
				c.setBizType(1);// 1 充电站2.停车场
				c.setBizId(cParking.getParkingNo());// 充电站编号
				c.setIsDeleted(0);// 删除状态 0未删除 1删除
				c.setOperatorType(operator.getOperatorType());
				c.setOperatorId(operator.getOperatorId());
				c.setCreateTime(now);
				c.setUpdateTime(now);
				docDao.add(c);// 保存图片
				// 保存层数分布
				List<CPliesNumber> list = new ArrayList<CPliesNumber>();
				if (spaceNumber != null&&spaceNumber.length>0) {
					boolean flag=false;
					ok:
					for (int i = 1; i <= spaceNumber.length; i++) {
						CPliesNumber pn = new CPliesNumber();
						String str[]=new String[2];
						if(cParking.getPliesNumber()==1){
							str=spaceNumber;
							flag=true;
						}else{
							str=spaceNumber[i-1].split(",");
						}
						pn.setParkingPliesNo("N" + String.valueOf(new Date().getTime()) + (int) (Math.random() * 999999));
						pn.setParkingNo(cParking.getParkingNo());
						pn.setPliesNumber(str[0]);
						pn.setParkingSpaceNumber(Integer.parseInt(str[1]));
						pn.setSurplusSpaceNumber(Integer.parseInt(str[1]));
						pn.setOperatorType(operator.getOperatorType());
						pn.setOperatorId(operator.getOperatorId());
						pn.setCreateTime(now);
						pn.setUpdateTime(now);
						pn.setSpaceType(3);
						list.add(pn);
						if(flag){
							break ok;
						}
					}
				}
				if (ground_spaceNumber_add != null&&!"".equals(ground_spaceNumber_add)) {
						cParking.setGroundNumber(1);
						CPliesNumber pn = new CPliesNumber();
						String str[]=ground_spaceNumber_add.split(",");
						pn.setParkingPliesNo("N" + String.valueOf(new Date().getTime()) + (int) (Math.random() * 999999));
						pn.setParkingNo(cParking.getParkingNo());
						pn.setPliesNumber(str[0]);
						pn.setParkingSpaceNumber(Integer.parseInt(str[1]));
						pn.setSurplusSpaceNumber(Integer.parseInt(str[1]));
						pn.setOperatorType(operator.getOperatorType());
						pn.setOperatorId(operator.getOperatorId());
						pn.setCreateTime(now);
						pn.setUpdateTime(now);
						pn.setSpaceType(2);
						list.add(pn);
				}
				if (under_spaceNumber_add != null&&under_spaceNumber_add.length>0) {
					boolean flags=false;
					ok1:
					for (int i = 1; i <= under_spaceNumber_add.length; i++) {
						CPliesNumber pn = new CPliesNumber();
						String str[]=new String[2];
						if(cParking.getUndergroundNumber()==1){
							str=under_spaceNumber_add;
							flags=true;
						}else{
							str=under_spaceNumber_add[i-1].split(",");
						}
						pn.setParkingPliesNo("N" + String.valueOf(new Date().getTime()) + (int) (Math.random() * 999999));
						pn.setParkingNo(cParking.getParkingNo());
						pn.setPliesNumber(str[0]);
						pn.setParkingSpaceNumber(Integer.parseInt(str[1]));
						pn.setSurplusSpaceNumber(Integer.parseInt(str[1]));
						pn.setOperatorType(operator.getOperatorType());
						pn.setOperatorId(operator.getOperatorId());
						pn.setCreateTime(now);
						pn.setUpdateTime(now);
						pn.setSpaceType(1);
						list.add(pn);
						if(flags){
							break ok1;
						}
					}
				}
				if(list!=null){
					pliesNumberDao.addTwoVo(list);// 保存层数分布
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cParking);
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
	 * 根据主键，更新一条CParking记录
	 * 
	 * @param cParking
	 *            更新的CParking数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParking> updateCParking(CParking cParking, Operator operator, String parkingUrl,
			String[] spaceNumber,String  ground_spaceNumber_add,String [] under_spaceNumber_add) {
		ResultInfo<CParking> resultInfo = new ResultInfo<CParking>();

		if (cParking == null || cParking.getParkingNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParking = " + cParking);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cParking.setOperatorType(operator.getOperatorType());
					cParking.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				cParking.setUpdateTime(new Date());
				if (ground_spaceNumber_add != null&&!"".equals(ground_spaceNumber_add)) {
					cParking.setGroundNumber(1);
				}
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cParkingDao.update(cParking);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cParking);
				if (!"".equals(parkingUrl)) {
					// 更新保存在 c-doc表中的图片
					CDoc c = new CDoc();
					c.setFileName(parkingUrl);// 文件名
					c.setFileUrl(parkingUrl);// 图片路径
					// 获取图片主键
					String docNo = docDao.getCDocNo(cParking.getParkingNo());
					c.setDocNo(docNo);// 图片主键
					docDao.update(c);
				}
				
				// 保存层数分布
				//List<CPliesNumber> list = new ArrayList<CPliesNumber>();
				if (spaceNumber != null&&spaceNumber.length>0) {
					boolean f=true;
					//判断楼层数据是否全部修改
					for (int i = 1; i <= spaceNumber.length; i++) {
						String str[]=spaceNumber[i-1].split(",");
						if(str.length==4||str[0].indexOf("N")==0){
							f=false;
							break;
						}
					}
					if(f){
						//删除多层分布
						pliesNumberDao.deleteParkingNo(cParking.getParkingNo(),3);	
					}
					boolean flags=false;
					for (int i = 1; i <= spaceNumber.length; i++) {
						CPliesNumber pn = new CPliesNumber();
						String str[]=new String[spaceNumber.length];
						if(cParking.getPliesNumber()==1){
							str=spaceNumber;
							flags=true;
						}else{
							str=spaceNumber[i-1].split(",");
						}
						if(str.length==4){
							pn.setParkingPliesNo(str[0]);
							pn.setParkingNo(cParking.getParkingNo());
							pn.setPliesNumber(str[1]);
							pn.setParkingSpaceNumber(Integer.parseInt(str[2]));
							pn.setSurplusSpaceNumber(Integer.parseInt(str[3]));
							pn.setOperatorType(operator.getOperatorType());
							pn.setOperatorId(operator.getOperatorId());
							pn.setCreateTime(new Date());
							pn.setUpdateTime(new Date());
							pn.setSpaceType(3);
							pliesNumberDao.update(pn);
						}else{
							pn.setParkingPliesNo("N" + String.valueOf(new Date().getTime()) + (int) (Math.random() * 999999));
							pn.setParkingNo(cParking.getParkingNo());
							pn.setPliesNumber(str[0]);
							pn.setParkingSpaceNumber(Integer.parseInt(str[1]));
							pn.setSurplusSpaceNumber(Integer.parseInt(str[2]));
							pn.setOperatorType(operator.getOperatorType());
							pn.setOperatorId(operator.getOperatorId());
							pn.setCreateTime(new Date());
							pn.setUpdateTime(new Date());
							pn.setSpaceType(3);
							pliesNumberDao.add(pn);
						}
						
						if(flags){
							break;
						}
					}
				}
				if (ground_spaceNumber_add != null&&!"".equals(ground_spaceNumber_add)) {
						boolean f=true;
						CPliesNumber pn = new CPliesNumber();
						String str[]=ground_spaceNumber_add.split(",");
						//判断楼层数据是否全部修改
						if(str.length==4||str[0].indexOf("N")==0){
							f=false;
						}
						if(f){
							//删除多层分布
							pliesNumberDao.deleteParkingNo(cParking.getParkingNo(),2);	
						}
						if(str.length==4){
							pn.setParkingPliesNo(str[0]);
							pn.setParkingNo(cParking.getParkingNo());
							pn.setPliesNumber(str[1]);
							pn.setParkingSpaceNumber(Integer.parseInt(str[2]));
							pn.setSurplusSpaceNumber(Integer.parseInt(str[3]));
							pn.setOperatorType(operator.getOperatorType());
							pn.setOperatorId(operator.getOperatorId());
							pn.setCreateTime(new Date());
							pn.setUpdateTime(new Date());
							pn.setSpaceType(2);
							pliesNumberDao.update(pn);
						}else{
							pn.setParkingPliesNo("N" + String.valueOf(new Date().getTime()) + (int) (Math.random() * 999999));
							pn.setParkingNo(cParking.getParkingNo());
							pn.setPliesNumber(str[0]);
							pn.setParkingSpaceNumber(Integer.parseInt(str[1]));
							pn.setSurplusSpaceNumber(Integer.parseInt(str[2]));
							pn.setOperatorType(operator.getOperatorType());
							pn.setOperatorId(operator.getOperatorId());
							pn.setCreateTime(new Date());
							pn.setUpdateTime(new Date());
							pn.setSpaceType(2);
							pliesNumberDao.add(pn);
						}
					
				}
				if (under_spaceNumber_add != null&&under_spaceNumber_add.length>0) {
					boolean f=true;
					//判断楼层数据是否全部修改
					for (int i = 1; i <= under_spaceNumber_add.length; i++) {
						String str[]=under_spaceNumber_add[i-1].split(",");
						if(str.length==4||str[0].indexOf("N")==0){
							f=false;
							break;
						}
					}
					if(f){
						//删除多层分布
						pliesNumberDao.deleteParkingNo(cParking.getParkingNo(),1);	
					}
					boolean flag=false;
					for (int i = 1; i <= under_spaceNumber_add.length; i++) {
						CPliesNumber pn = new CPliesNumber();
						String str[]=new String[under_spaceNumber_add.length];
						if(cParking.getUndergroundNumber()==1){
							str=under_spaceNumber_add;
							flag=true;
						}else{
							str=under_spaceNumber_add[i-1].split(",");
						}
						if(str.length==4){
							pn.setParkingPliesNo(str[0]);
							pn.setParkingNo(cParking.getParkingNo());
							pn.setPliesNumber(str[1]);
							pn.setParkingSpaceNumber(Integer.parseInt(str[2]));
							pn.setSurplusSpaceNumber(Integer.parseInt(str[3]));
							pn.setOperatorType(operator.getOperatorType());
							pn.setOperatorId(operator.getOperatorId());
							pn.setCreateTime(new Date());
							pn.setUpdateTime(new Date());
							pn.setSpaceType(1);
							pliesNumberDao.update(pn);
						}else{
							pn.setParkingPliesNo("N" + String.valueOf(new Date().getTime()) + (int) (Math.random() * 999999));
							pn.setParkingNo(cParking.getParkingNo());
							pn.setPliesNumber(str[0]);
							pn.setParkingSpaceNumber(Integer.parseInt(str[1]));
							pn.setSurplusSpaceNumber(Integer.parseInt(str[2]));
							pn.setOperatorType(operator.getOperatorType());
							pn.setOperatorId(operator.getOperatorId());
							pn.setCreateTime(new Date());
							pn.setUpdateTime(new Date());
							pn.setSpaceType(1);
							pliesNumberDao.add(pn);
						}
						
						if(flag){
							break;
						}
					}
				}
			/*	if(list!=null){
					pliesNumberDao.addTwoVo(list);// 保存层数分布
				}*/
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
		return "P" + String.valueOf(System.nanoTime()) + (int) (Math.random() * 9999);
	}

	/**
	 * 为CParking对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CParking obj) {
		if (obj != null) {
		}
	}

	@Override
	@Transactional
	public PageFinder<ParkingVo> getParkList(Query q) {
		PageFinder<ParkingVo> page = new PageFinder<ParkingVo>();

		List<ParkingVo> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			ParkingVo cParking = (ParkingVo) q.getQ();
			if (cParking != null) {
				cParking.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = cParkingDao.getParkList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cParkingDao.getParkListCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkingVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public ParkingVo getParkListNo(String id) {
		ParkingVo obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cParkingDao.getParkListNo(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	@Override
	public PageFinder<CParkingVo> pageCparkingList(Query q) {
		PageFinder<CParkingVo> page = new PageFinder<CParkingVo>();

		List<CParkingVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = cParkingDao.pageCparkingVoList(q);
			// 调用dao统计满足条件的记录总数
			//rowCount = cParkingDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkingVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(0);

		return page;
	}

	@Override
	public CParkingDetailVo getParkingDetailVo(Query q) {
		CParkingDetailVo parkingDetailVo = cParkingDao.getParkingDetailVo(q);
		List<BannerVo> topBannerList = advertMengLongDao.searchBannerByPosAndType_two(1, Constant.TYPE_16, imgPath);
		if (topBannerList.size() > 0) {
			parkingDetailVo.setTopBanner(topBannerList);
		}
		//详情图片
		List<BannerVo> detailsUrl = advertMengLongDao.searchBannerByPosAndType_two(3, Constant.TYPE_16, imgPath);
		if (detailsUrl.size() > 0) {
			parkingDetailVo.setDetailsUrl(detailsUrl.get(0).getAdvertPicUrl());
		}
		List<AdvertVo> sysParamList = advertMengLongDao.selectAdvertByTypeAndPosition(8, 5, 3);// 停车场顶部系统参数
		List<AdvertTextVo> lst = new ArrayList<AdvertTextVo>();
		if (sysParamList.size() > 0) {
			for (AdvertVo ad : sysParamList) {
				AdvertTextVo text = new AdvertTextVo();
				text.setAdvertName(ad.getAdvertName());
				lst.add(text);
			}
			parkingDetailVo.setSysParamList(lst);
		}
		return parkingDetailVo;
	}

	/**
	 * 停车场模糊搜索
	 */
	@Override
	public PageFinder<CParkingVo> searchParkVoList(Query q) {
		PageFinder<CParkingVo> page = new PageFinder<CParkingVo>();

		List<CParkingVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = cParkingDao.pageCparkingVoList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cParkingDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkingVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}
}
