package cn.com.shopec.core.member.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberBalanceDao;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.SysUserService;

/**
 * Member 服务实现类
 */
@Service
public class MemberBalanceServiceImpl implements MemberBalanceService {

	private static final Log log = LogFactory.getLog(MemberBalanceServiceImpl.class);

	@Resource
	private MemberDao memberDao;

	@Resource
	private MemberBalanceDao memberBalanceDao;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private SysUserService sysUserService;
	
	/**
	 * 根据查询条件，查询并返回Member的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Override
	public List<MemberBalance> getMemberBalanceList(Query q) {
		List<MemberBalance> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = memberBalanceDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberBalance>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回MemberBalance的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Override
	public PageFinder<MemberBalance> getMemberBalancePagedList(Query q) {
		PageFinder<MemberBalance> page = new PageFinder<MemberBalance>();

		List<MemberBalance> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = memberBalanceDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = memberBalanceDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberBalance>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个MemberBalance对象
	 * 
	 * @param memberNo
	 *            主键memberNo
	 * @return
	 */
	@Override
	public MemberBalance getMemberBalance(String memberNo) {
		MemberBalance memberBalance = null;
		if (memberNo == null || memberNo.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberNo = " + memberNo);
			return memberBalance;
		}
		try {
			// 调用dao，根据主键查询
			memberBalance = memberBalanceDao.get(memberNo);
			if (memberBalance != null) {
				Member member = memberService.getMember(memberBalance.getMemberNo());
				if(member != null){
					memberBalance.setMemberName(member.getMemberName());
				}
				SysUser sysUser = sysUserService.detail(memberBalance.getFreezePerson());
				if(sysUser != null){
					memberBalance.setFreezePersonName(sysUser.getUserName());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return memberBalance;
	}

	/**
	 * 根据主键数组，查询并返回一组MemberBalance对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Override
	public List<MemberBalance> getMemberBalanceByIds(String[] ids) {
		List<MemberBalance> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = memberBalanceDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberBalance>(0) : list;
		return list;
	}

	/**
	 * 新增一条MemberBalance记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param MemberBalance
	 *            新增的Member数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Override
	public ResultInfo<MemberBalance> addMemberBalance(MemberBalance memberBalance, Operator operator) {
		ResultInfo<MemberBalance> resultInfo = new ResultInfo<MemberBalance>();

		if (memberBalance == null || memberBalance.getMemberNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberBalance = " + memberBalance);
		} else {
			try {
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					memberBalance.setOperatorType(operator.getOperatorType());
					memberBalance.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				memberBalance.setCreateTime(now);
				memberBalance.setUpdateTime(now);

				//填充默认值
				this.fillDefaultValues(memberBalance);
				
				// 调用Dao执行插入操作
				memberBalanceDao.add(memberBalance);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(memberBalance);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	@Override
	public ResultInfo<MemberBalance> updateMemberBalance(MemberBalance memberBalance, Operator operator) {
		ResultInfo<MemberBalance> resultInfo = new ResultInfo<MemberBalance>();
		if (memberBalance == null || memberBalance.getMemberNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberBalance = " + memberBalance);
		} else {
			try {
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				memberBalance.setUpdateTime(now);
				
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					memberBalance.setOperatorType(operator.getOperatorType());
					memberBalance.setOperatorId(operator.getOperatorId());
					
					//如果解冻/冻结状态不为null 则记录时间和人
					if(memberBalance.getIsFreeze() != null){
						memberBalance.setFreezeTime(now);
						memberBalance.setFreezePerson(operator.getOperatorId());
					}
				}
				
				// 调用Dao执行插入操作
				int result = memberBalanceDao.update(memberBalance);
				if (result > 0) {
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(memberBalance);
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setData(memberBalance);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	@Override
	public void fillDefaultValues(MemberBalance memberBalance) {
		if (memberBalance != null) {
			if (memberBalance.getIsFreeze() == null) {
				memberBalance.setIsFreeze(0);
			}
		}
	}

}
