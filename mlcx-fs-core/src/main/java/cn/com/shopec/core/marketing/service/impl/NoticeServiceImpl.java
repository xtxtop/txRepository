package cn.com.shopec.core.marketing.service.impl;

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
import cn.com.shopec.core.marketing.dao.NoticeDao;
import cn.com.shopec.core.marketing.model.Notice;
import cn.com.shopec.core.marketing.service.NoticeService;


/**
 * Notice 服务实现类
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	private static final Log log = LogFactory.getLog(NoticeServiceImpl.class);
	
	@Resource
	private NoticeDao noticeDao;
	

	/**
	 * 根据查询条件，查询并返回Notice的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Notice> getNoticeList(Query q) {
		List<Notice> list = null;
		try {
			//已删除的不查出
			Notice notice = (Notice)q.getQ();
			if (notice != null) {
				notice.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = noticeDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Notice>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回Notice的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Notice> getNoticePagedList(Query q) {
		PageFinder<Notice> page = new PageFinder<Notice>();
		
		List<Notice> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			Notice notice = (Notice)q.getQ();
			if (notice != null) {
				notice.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = noticeDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = noticeDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Notice>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个Notice对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Notice getNotice(String id) {
		Notice obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = noticeDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Notice对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Notice> getNoticeByIds(String[] ids) {
		List<Notice> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = noticeDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Notice>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的Notice记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Notice> delNotice(String id, Operator operator) {
		ResultInfo<Notice> resultInfo = new ResultInfo<Notice>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			Notice notice = new Notice();
			notice.setNoticeNo(id);
			notice.setIsDeleted(Constant.DEL_STATE_YES);
			notice.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				notice.setOperatorType(operator.getOperatorType());
				notice.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = noticeDao.update(notice);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(notice);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条Notice记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param notice 新增的Notice数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Notice> addNotice(Notice notice, Operator operator) {
		ResultInfo<Notice> resultInfo = new ResultInfo<Notice>();
		
		if (notice == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " notice = " + notice);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (notice.getNoticeNo() == null) {
					String noticeNo = this.generatePK();
					notice.setNoticeNo(noticeNo);
					notice.setNoticeNo(this.generatePK());
					notice.setLinkUrl(notice.getLinkUrl()+"/advert/"+noticeNo.substring(0, 2)+"/"+noticeNo.substring(2, noticeNo.length())+".html");
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					notice.setOperatorType(operator.getOperatorType());
					notice.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				notice.setCreateTime(now);
				notice.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(notice);
				
				//调用Dao执行插入操作
				noticeDao.add(notice);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(notice);
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
	 * 根据主键，更新一条Notice记录
	 * @param notice 更新的Notice数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Notice> updateNotice(Notice notice, Operator operator) {
		ResultInfo<Notice> resultInfo = new ResultInfo<Notice>();
		
		if (notice == null || notice.getNoticeNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " notice = " + notice);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					notice.setOperatorType(operator.getOperatorType());
					notice.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				notice.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = noticeDao.update(notice);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(notice);
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
		return  String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为Notice对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Notice obj) {
		if (obj != null) {
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Notice> getNoticePagedLists(Query q) {
		PageFinder<Notice> page = new PageFinder<Notice>();
		
		List<Notice> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			Notice notice = (Notice)q.getQ();
			if (notice != null) {
				notice.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = noticeDao.pageLists(q);
			//调用dao统计满足条件的记录总数
			rowCount = noticeDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Notice>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	

}
