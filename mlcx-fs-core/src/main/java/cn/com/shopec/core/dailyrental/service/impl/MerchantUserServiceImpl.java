package cn.com.shopec.core.dailyrental.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import cn.com.shopec.core.dailyrental.dao.MerchantUserDao;
import cn.com.shopec.core.dailyrental.model.MerchantUser;
import cn.com.shopec.core.dailyrental.service.MerchantUserService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * MerchantUser 服务实现类
 */
@Service
public class MerchantUserServiceImpl implements MerchantUserService {

	private static final Log log = LogFactory.getLog(MerchantUserServiceImpl.class);
	
	@Resource
	private MerchantUserDao merchantUserDao;
	@Resource
	private PrimarykeyService primarykeyService;
	

	/**
	 * 根据查询条件，查询并返回MerchantUser的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantUser> getMerchantUserList(Query q) {
		List<MerchantUser> list = null;
		try {
			//直接调用Dao方法进行查询
			list = merchantUserDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantUser>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回MerchantUser的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MerchantUser> getMerchantUserPagedList(Query q) {
		PageFinder<MerchantUser> page = new PageFinder<MerchantUser>();
		
		List<MerchantUser> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantUserDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantUserDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantUser>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个MerchantUser对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MerchantUser getMerchantUser(String id) {
		MerchantUser obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = merchantUserDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MerchantUser对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantUser> getMerchantUserByIds(String[] ids) {
		List<MerchantUser> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = merchantUserDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantUser>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MerchantUser记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantUser> delMerchantUser(String id, Operator operator) {
		ResultInfo<MerchantUser> resultInfo = new ResultInfo<MerchantUser>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = merchantUserDao.delete(id);
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
	 * 新增一条MerchantUser记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchantUser 新增的MerchantUser数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantUser> addMerchantUser(MerchantUser merchantUser, Operator operator) {
		ResultInfo<MerchantUser> resultInfo = new ResultInfo<MerchantUser>();
		
		if (merchantUser == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantUser = " + merchantUser);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (merchantUser.getUserNo() == null) {
					merchantUser.setUserNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					merchantUser.setOperatorType(operator.getOperatorType());
					merchantUser.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				merchantUser.setCreateTime(now);
				merchantUser.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(merchantUser);
				
				//调用Dao执行插入操作
				merchantUserDao.add(merchantUser);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantUser);
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
	 * 根据主键，更新一条MerchantUser记录
	 * @param merchantUser 更新的MerchantUser数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantUser> updateMerchantUser(MerchantUser merchantUser, Operator operator) {
		ResultInfo<MerchantUser> resultInfo = new ResultInfo<MerchantUser>();
		
		if (merchantUser == null || merchantUser.getUserNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantUser = " + merchantUser);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					merchantUser.setOperatorType(operator.getOperatorType());
					merchantUser.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				merchantUser.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantUserDao.update(merchantUser);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchantUser);
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
	 * 根据商家ID和会员手机查询商家用户
	 * @param merchantId
	 * @param mobile
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public MerchantUser getByMobilePhone(String merchantId, String mobilePhone,String password) {
		
		MerchantUser obj = null;
		if (StringUtils.isBlank(mobilePhone)) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " mobilePhone = " + mobilePhone);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = merchantUserDao.getByMobilePhone(merchantId, mobilePhone,password); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
	
	/**
	 * 根据token查询商家用户
	 * @param token
	 * @param mobilePhone
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public MerchantUser getByToken(String token,String userNo) {
		
		MerchantUser obj = null;
		if (StringUtils.isBlank(token) && StringUtils.isBlank(userNo)) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " token = " + token + " & userNo = " + userNo);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = merchantUserDao.getByToken(token,userNo); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
	
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK() {
		return primarykeyService.getValueByBizType(PrimarykeyConstant.franchiseeType)+"";
	}
	
	/**
	 * 为MerchantUser对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MerchantUser obj) {
		if (obj != null) {
		    if (obj.getIsBlacklist() == null) {
		    	obj.setIsBlacklist(0);
		    }
		    if (obj.getCensorStatus() == null) {
		    	obj.setCensorStatus(0);
		    }
		}
	}

	@Override
	public ResultInfo<MerchantUser> updateMerchantUserByMerchantId(MerchantUser merchantUser) {
ResultInfo<MerchantUser> resultInfo = new ResultInfo<MerchantUser>();
		
		if (merchantUser == null || merchantUser.getMerchantId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantUser = " + merchantUser);
		} else {
			try {
				//设置更新时间为当前时间
				merchantUser.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantUserDao.updateMerchantUserByMerchantId(merchantUser);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchantUser);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}
		return resultInfo;
	}
	
}
