package cn.com.shopec.core.member.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberBalance;

/**
 * MemberBalanceService 服务接口类
 */
public interface MemberBalanceService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MemberBalance的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<MemberBalance> getMemberBalanceList(Query q);

	/**
	 * 根据查询条件，分页查询并返回MemberBalance的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<MemberBalance> getMemberBalancePagedList(Query q);

	/**
	 * 根据主键，查询并返回一个MemberBalance对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public MemberBalance getMemberBalance(String memberNo);

	/**
	 * 根据主键数组，查询并返回一组MemberBalance对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MemberBalance> getMemberBalanceByIds(String[] ids);

	/**
	 * 新增一条MemberBalance记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param MemberBalance
	 *            新增的Member数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<MemberBalance> addMemberBalance(MemberBalance memberBalance, Operator operator);

	/**
	 * 根据主键，更新一条MemberBalance记录
	 * 
	 * @param MemberBalance
	 *            更新的Member数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<MemberBalance> updateMemberBalance(MemberBalance member, Operator operator);

	/**
	 * 为MemberBalance对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MemberBalance memberBalance);
}
