package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.AreaDeposit;

/**
 * 地区应繳押金表 服务接口类
 */
public interface AreaDepositService extends BaseService {

	/**
	 * 根据查询条件，查询并返回AreaDeposit的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<AreaDeposit> getAreaDepositList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回AreaDeposit的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<AreaDeposit> getAreaDepositPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个AreaDeposit对象
	 * @param id 主键id
	 * @return
	 */
	public AreaDeposit getAreaDeposit(String id);

	/**
	 * 根据主键数组，查询并返回一组AreaDeposit对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<AreaDeposit> getAreaDepositByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的AreaDeposit记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AreaDeposit> delAreaDeposit(String id, Operator operator);
	
	/**
	 * 新增一条AreaDeposit记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param areaDeposit 新增的AreaDeposit数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AreaDeposit> addAreaDeposit(AreaDeposit areaDeposit, Operator operator);
	
	/**
	 * 根据主键，更新一条AreaDeposit记录
	 * @param areaDeposit 更新的AreaDeposit数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AreaDeposit> updateAreaDeposit(AreaDeposit areaDeposit, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为AreaDeposit对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(AreaDeposit obj);

}
