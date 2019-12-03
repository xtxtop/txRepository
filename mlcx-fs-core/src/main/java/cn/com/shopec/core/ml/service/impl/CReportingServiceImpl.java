package cn.com.shopec.core.ml.service.impl;

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
import cn.com.shopec.core.ml.dao.CDocDao;
import cn.com.shopec.core.ml.dao.CReportingDao;
import cn.com.shopec.core.ml.model.CDoc;
import cn.com.shopec.core.ml.model.CReporting;
import cn.com.shopec.core.ml.service.CReportingService;
import cn.com.shopec.core.ml.vo.JoinVo;
import cn.com.shopec.core.ml.vo.ReportingVo;

/**
 * CReporting 服务实现类
 */
@Service
public class CReportingServiceImpl implements CReportingService {

	private static final Log log = LogFactory.getLog(CReportingServiceImpl.class);
	
	@Resource
	private CReportingDao cReportingDao;
	@Resource
	private CDocDao docDaol;
	

	/**
	 * 根据查询条件，查询并返回CReporting的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CReporting> getCReportingList(Query q) {
		List<CReporting> list = null;
		try {
			//直接调用Dao方法进行查询
			list = cReportingDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CReporting>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CReporting的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CReporting> getCReportingPagedList(Query q) {
		PageFinder<CReporting> page = new PageFinder<CReporting>();
		
		List<CReporting> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = cReportingDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = cReportingDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CReporting>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CReporting对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CReporting getCReporting(String id) {
		CReporting obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = cReportingDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CReporting对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CReporting> getCReportingByIds(String[] ids) {
		List<CReporting> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = cReportingDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CReporting>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CReporting记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CReporting> delCReporting(String id, Operator operator) {
		ResultInfo<CReporting> resultInfo = new ResultInfo<CReporting>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = cReportingDao.delete(id);
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
	 * 新增一条CReporting记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param cReporting 新增的CReporting数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CReporting> addCReporting(CReporting cReporting, Operator operator) {
		ResultInfo<CReporting> resultInfo = new ResultInfo<CReporting>();
		
		if (cReporting == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " cReporting = " + cReporting);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cReporting.getReportingNo() == null) {
					cReporting.setReportingNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cReporting.setOperatorType(operator.getOperatorType());
					cReporting.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				cReporting.setCreateTime(now);
				cReporting.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(cReporting);
				//插入违停图片
				for(int i=0;i<cReporting.getImg().length;i++){
					CDoc c = new CDoc();
					c.setDocNo("DAP"+String.valueOf(new Date().getTime()));
					c.setDocName("违停图片");// 附件名称
					c.setFileName(cReporting.getImg()[i]);// 文件名
					c.setFileType(0);// 类型0图片 1视频 2文件
					c.setPosition(1);// 图片位置 0 列表,1详情
					c.setFileUrl(cReporting.getImg()[i]);// 图片路径
					c.setBizType(2);// 2违停
					c.setBizId(cReporting.getReportingNo());//违停ID
					c.setIsDeleted(0);// 删除状态 0未删除 1删除
					c.setCreateTime(now);
					c.setUpdateTime(now);
					docDaol.add(c);//保存图片
				}
				//调用Dao执行插入操作
				cReportingDao.add(cReporting);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cReporting);
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
	 * 根据主键，更新一条CReporting记录
	 * @param cReporting 更新的CReporting数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CReporting> updateCReporting(CReporting cReporting, Operator operator) {
		ResultInfo<CReporting> resultInfo = new ResultInfo<CReporting>();
		
		if (cReporting == null || cReporting.getReportingNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " cReporting = " + cReporting);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cReporting.setOperatorType(operator.getOperatorType());
					cReporting.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				cReporting.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = cReportingDao.update(cReporting);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cReporting);
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
		return "R"+String.valueOf(new Date().getTime());
	}
	
	/**
	 * 为CReporting对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CReporting obj) {
		if (obj != null) {
		}
	}
	//获取加盟列表
	@Transactional
	public PageFinder<ReportingVo> getReporting(Query q){
		PageFinder<ReportingVo> page = new PageFinder<ReportingVo>();
		
		List<ReportingVo> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = cReportingDao.getReporting(q);
			//调用dao统计满足条件的记录总数
			rowCount = cReportingDao.getReportingCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ReportingVo>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}
	//详情
	@Transactional
	public ReportingVo getReportingNo(String id){
		ReportingVo obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = cReportingDao.getReportingNo(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
}
