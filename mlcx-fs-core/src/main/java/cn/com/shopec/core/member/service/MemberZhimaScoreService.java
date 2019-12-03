package cn.com.shopec.core.member.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberZhimaScore;

/**
 * 会员信用分 服务接口类
 */
public interface MemberZhimaScoreService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MemberZhimaScore的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MemberZhimaScore> getMemberZhimaScoreList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MemberZhimaScore的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MemberZhimaScore> getMemberZhimaScorePagedList(Query q);
	/**
	 * 根据主键，查询并返回一个MemberZhimaScore对象
	 * @param id 主键id
	 * @return
	 */
	public MemberZhimaScore getMemberZhimaScore(String id);

	/**
	 * 根据主键数组，查询并返回一组MemberZhimaScore对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MemberZhimaScore> getMemberZhimaScoreByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MemberZhimaScore记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberZhimaScore> delMemberZhimaScore(String id, Operator operator);
	
	/**
	 * 新增一条MemberZhimaScore记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param memberZhimaScore 新增的MemberZhimaScore数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberZhimaScore> addMemberZhimaScore(MemberZhimaScore memberZhimaScore, Operator operator);
	
	/**
	 * 根据主键，更新一条MemberZhimaScore记录
	 * @param memberZhimaScore 更新的MemberZhimaScore数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MemberZhimaScore> updateMemberZhimaScore(MemberZhimaScore memberZhimaScore, Operator operator);
	
	/**
	 * 生成transactionId
	 * @return
	 */
	String generateTransactionId();

	/**
	 * 生成主键
	 * @return
	 */
	String generatePK();

	/**
	 * 调用芝麻接口重新重新信用分，并且维护到数据库中
	 * @param memberZhimaScoreNo
	 * @param addrRegion
	 * @return
	 * @throws Exception 
	 */
	ResultInfo<MemberZhimaScore> getScoreForZhiMa(MemberZhimaScore memberZhimaScore) throws Exception;
}
