package cn.com.shopec.core.member.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberPointsRecord;

/**
 * 会员积分记录表 服务接口类
 */
public interface MemberPointsRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MemberPointsRecord的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MemberPointsRecord> getMemberPointsRecordList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MemberPointsRecord的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MemberPointsRecord> getMemberPointsRecordPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MemberPointsRecord对象
	 * @param id 主键id
	 * @return
	 */
	public MemberPointsRecord getMemberPointsRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组MemberPointsRecord对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MemberPointsRecord> getMemberPointsRecordByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MemberPointsRecord记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberPointsRecord> delMemberPointsRecord(String id, Operator operator);
	
	/**
	 * 新增一条MemberPointsRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param memberPointsRecord 新增的MemberPointsRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberPointsRecord> addMemberPointsRecord(MemberPointsRecord memberPointsRecord, Operator operator);
	
	/**
	 * 根据主键，更新一条MemberPointsRecord记录
	 * @param memberPointsRecord 更新的MemberPointsRecord数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberPointsRecord> updateMemberPointsRecord(MemberPointsRecord memberPointsRecord, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为MemberPointsRecord对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MemberPointsRecord obj);
/**
 * 根据会员编号获取会员当前的积分总数
 * */
		public int getPointsByMemberNo(String  memberNo);
		
}
