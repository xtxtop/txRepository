package cn.com.shopec.core.ml.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CAccountRecord;
import cn.com.shopec.core.ml.vo.AccountRecordVo;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * CAccountRecord 服务接口类
 */
public interface CAccountRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CAccountRecord的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CAccountRecord> getCAccountRecordList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CAccountRecord的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CAccountRecord> getCAccountRecordPagedList(Query q);

	public PageFinder<CAccountRecord> getAccountRecord(Query q);

	/**
	 * 根据主键，查询并返回一个CAccountRecord对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CAccountRecord getCAccountRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组CAccountRecord对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CAccountRecord> getCAccountRecordByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CAccountRecord记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CAccountRecord> delCAccountRecord(String id, Operator operator);

	/**
	 * 新增一条CAccountRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cAccountRecord
	 *            新增的CAccountRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CAccountRecord> addCAccountRecord(CAccountRecord cAccountRecord, Operator operator);

	/**
	 * 根据主键，更新一条CAccountRecord记录
	 * 
	 * @param cAccountRecord
	 *            更新的CAccountRecord数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CAccountRecord> updateCAccountRecord(CAccountRecord cAccountRecord, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CAccountRecord对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CAccountRecord obj);

	public long getAccountRecord_No(String id);

	/**
	 * 获取某会员的全部充值记录
	 * 
	 * @param memerNo
	 * @return
	 */
	public List<AccountRecordVo> getAccountRecordByMemberNo(Query q);

	/**
	 * 获取顶部banner集合
	 * 
	 * @param imgPath
	 * @return
	 */
	public List<BannerVo> getBannerVoList(String imgPath);

}
