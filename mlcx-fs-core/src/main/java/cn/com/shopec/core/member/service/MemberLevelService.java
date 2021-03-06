package cn.com.shopec.core.member.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberLevel;

/**
 * 会员等级表 服务接口类
 */
public interface MemberLevelService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MemberLevel的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MemberLevel> getMemberLevelList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MemberLevel的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MemberLevel> getMemberLevelPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MemberLevel对象
	 * @param id 主键id
	 * @return
	 */
	public MemberLevel getMemberLevel(String id);

	/**
	 * 根据主键数组，查询并返回一组MemberLevel对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MemberLevel> getMemberLevelByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MemberLevel记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberLevel> delMemberLevel(String id, Operator operator);
	
	/**
	 * 新增一条MemberLevel记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param memberLevel 新增的MemberLevel数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberLevel> addMemberLevel(MemberLevel memberLevel, Operator operator);
	
	/**
	 * 根据主键，更新一条MemberLevel记录
	 * @param memberLevel 更新的MemberLevel数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberLevel> updateMemberLevel(MemberLevel memberLevel, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为MemberLevel对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MemberLevel obj);
/**
 * @author zln
 * 会员获取当前积分所在等级的分值
 * */
		public Integer getNowLevelPoints(int memberPoint);
		/**
		 * @author zln
		 * 会员获取当前积分下一等级的分值
		 * */
public Integer getNextLevelPoints(int memberPoint);

public MemberLevel getMemberLevelNex(int nextLevelPoint);
		
}
