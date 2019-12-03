package cn.com.shopec.core.mall.service.impl;
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

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mall.dao.MallItemSortDao;
import cn.com.shopec.core.mall.model.MallItemSort;
import cn.com.shopec.core.mall.service.MallItemSortService;



/**
 * MallItemSort 服务实现类
 */
@Service
public class MallItemSortServiceImpl implements MallItemSortService {

	private static final Log log = LogFactory.getLog(MallItemSortServiceImpl.class);
	
	@Resource
	private MallItemSortDao mallItemSortDao;
	

	/**
	 * 根据查询条件，查询并返回MallItemSort的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MallItemSort> getMallItemSortList(Query q) {
		List<MallItemSort> list = null;
		try {
			//直接调用Dao方法进行查询
			list = mallItemSortDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MallItemSort>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回MallItemSort的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MallItemSort> getMallItemSortPagedList(Query q) {
		PageFinder<MallItemSort> page = new PageFinder<MallItemSort>();
		
		List<MallItemSort> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = mallItemSortDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = mallItemSortDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MallItemSort>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个MallItemSort对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MallItemSort getMallItemSort(String id) {
		MallItemSort obj = null;
		if (id == null) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = mallItemSortDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MallItemSort对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MallItemSort> getMallItemSortByIds(String[] ids) {
		List<MallItemSort> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = mallItemSortDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MallItemSort>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MallItemSort记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MallItemSort> delMallItemSort(String id) {
		ResultInfo<MallItemSort> resultInfo = new ResultInfo<MallItemSort>();
		if (id == null) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = mallItemSortDao.delete(id);
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
	 * 新增一条MallItemSort记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param mallItemSort 新增的MallItemSort数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MallItemSort> addMallItemSort(MallItemSort mallItemSort) {
		ResultInfo<MallItemSort> resultInfo = new ResultInfo<MallItemSort>();
		
		if (mallItemSort == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " mallItemSort = " + mallItemSort);
		} else {
			try {
				int level = 1;
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (mallItemSort.getSortNo() == null) {
					mallItemSort.setSortNo(this.generatePK());
				}
				if(mallItemSort.getParentSortNo() == null || mallItemSort.getParentSortNo().trim().length() == 0 || mallItemSort.getParentSortNo().equals("0")){					
					mallItemSort.setParentSortName("");
				}else{
					MallItemSort parentItemSort = mallItemSortDao.get(mallItemSort.getParentSortNo());
					if(parentItemSort != null){
						mallItemSort.setParentSortName(parentItemSort.getSortName());
						level = parentItemSort.getSortLevel() + 1;
					}else{
						mallItemSort.setParentSortName("");
					}
				}
				if(level > 3){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("分类级别不能大于3级,添加失败");
					return resultInfo;
				}
				mallItemSort.setSortLevel(level);
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				mallItemSort.setCreateTime(now);
				
				//填充默认值
				this.fillDefaultValues(mallItemSort);
				
				//调用Dao执行插入操作
				mallItemSortDao.add(mallItemSort);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(mallItemSort);
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
	 * 根据主键，更新一条MallItemSort记录
	 * @param mallItemSort 更新的MallItemSort数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MallItemSort> updateMallItemSort(MallItemSort mallItemSort) {
		ResultInfo<MallItemSort> resultInfo = new ResultInfo<MallItemSort>();
		
		if (mallItemSort == null || mallItemSort.getSortNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " mallItemSort = " + mallItemSort);
		} else {
			try {
				int level = 1;
				if(mallItemSort.getParentSortNo() == null || mallItemSort.getParentSortNo().trim().length() == 0 || mallItemSort.getParentSortNo().equals("0")){					
					mallItemSort.setParentSortName("");
				}else{
					MallItemSort parentItemSort = mallItemSortDao.get(mallItemSort.getParentSortNo());
					if(parentItemSort != null){
						mallItemSort.setParentSortName(parentItemSort.getSortName());
						level = parentItemSort.getSortLevel() + 1;
					}else{
						mallItemSort.setParentSortName("");
					}
				}
				if(level > 3){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("分类级别不许大于3级,更新失败");
					return resultInfo;
				}
				mallItemSort.setSortLevel(level);
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = mallItemSortDao.update(mallItemSort);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(mallItemSort);
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
		return String.valueOf(new Date().getTime() * 10000 + new Random().nextInt(10000));
	}
	
	/**
	 * 为MallItemSort对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MallItemSort obj) {
		if (obj != null) {
		}
	}

}
