package cn.com.shopec.core.member.service.impl;

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
import cn.com.shopec.core.member.dao.MemberPointsRuleDao;
import cn.com.shopec.core.member.model.MemberPointsRule;
import cn.com.shopec.core.member.service.MemberPointsRuleService;

/**
 * 会员积分规则表 服务实现类
 */
@Service
public class MemberPointsRuleServiceImpl implements MemberPointsRuleService {

	private static final Log log = LogFactory.getLog(MemberPointsRuleServiceImpl.class);
	
	@Resource
	private MemberPointsRuleDao memberPointsRuleDao;
	

	/**
	 * 根据查询条件，查询并返回MemberPointsRule的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberPointsRule> getMemberPointsRuleList(Query q) {
		List<MemberPointsRule> list = null;
		try {
			//已删除的不查出
			MemberPointsRule memberPointsRule = (MemberPointsRule)q.getQ();
			if (memberPointsRule != null) {
				memberPointsRule.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = memberPointsRuleDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberPointsRule>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回MemberPointsRule的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MemberPointsRule> getMemberPointsRulePagedList(Query q) {
		PageFinder<MemberPointsRule> page = new PageFinder<MemberPointsRule>();
		
		List<MemberPointsRule> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			MemberPointsRule memberPointsRule = (MemberPointsRule)q.getQ();
			if (memberPointsRule != null) {
				memberPointsRule.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = memberPointsRuleDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = memberPointsRuleDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberPointsRule>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个MemberPointsRule对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MemberPointsRule getMemberPointsRule(String id) {
		MemberPointsRule obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = memberPointsRuleDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MemberPointsRule对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberPointsRule> getMemberPointsRuleByIds(String[] ids) {
		List<MemberPointsRule> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = memberPointsRuleDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberPointsRule>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MemberPointsRule记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MemberPointsRule> delMemberPointsRule(String id, Operator operator) {
		ResultInfo<MemberPointsRule> resultInfo = new ResultInfo<MemberPointsRule>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			MemberPointsRule memberPointsRule = new MemberPointsRule();
			memberPointsRule.setRuleId(id);
			memberPointsRule.setIsDeleted(Constant.DEL_STATE_YES);
			memberPointsRule.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				memberPointsRule.setOperatorType(operator.getOperatorType());
				memberPointsRule.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = memberPointsRuleDao.update(memberPointsRule);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(memberPointsRule);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条MemberPointsRule记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param memberPointsRule 新增的MemberPointsRule数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MemberPointsRule> addMemberPointsRule(MemberPointsRule memberPointsRule, Operator operator) {
		ResultInfo<MemberPointsRule> resultInfo = new ResultInfo<MemberPointsRule>();
		
		if (memberPointsRule == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberPointsRule = " + memberPointsRule);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (memberPointsRule.getRuleId() == null) {
					memberPointsRule.setRuleId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					memberPointsRule.setOperatorType(operator.getOperatorType());
					memberPointsRule.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				memberPointsRule.setCreateTime(now);
				memberPointsRule.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(memberPointsRule);
				
				//调用Dao执行插入操作
				memberPointsRuleDao.add(memberPointsRule);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(memberPointsRule);
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
	 * 根据主键，更新一条MemberPointsRule记录
	 * @param memberPointsRule 更新的MemberPointsRule数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MemberPointsRule> updateMemberPointsRule(MemberPointsRule memberPointsRule, Operator operator) {
		ResultInfo<MemberPointsRule> resultInfo = new ResultInfo<MemberPointsRule>();
		
		if (memberPointsRule == null || memberPointsRule.getRuleId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberPointsRule = " + memberPointsRule);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					memberPointsRule.setOperatorType(operator.getOperatorType());
					memberPointsRule.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				memberPointsRule.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = memberPointsRuleDao.update(memberPointsRule);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(memberPointsRule);
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
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为MemberPointsRule对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MemberPointsRule obj) {
		if (obj != null) {
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(1);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

}
