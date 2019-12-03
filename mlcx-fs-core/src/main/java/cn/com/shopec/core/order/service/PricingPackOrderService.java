package cn.com.shopec.core.order.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.PricingPackOrder;

/**
 * 套餐订单表 服务接口类
 */
public interface PricingPackOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PricingPackOrder的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<PricingPackOrder> getPricingPackOrderList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回PricingPackOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PricingPackOrder> getPricingPackOrderPagedList(Query q);
	public PageFinder<PricingPackOrder> getPricingPackOrderPagedLists(Query q);
	
	/**
	 * 根据主键，查询并返回一个PricingPackOrder对象
	 * @param id 主键id
	 * @return
	 */
	public PricingPackOrder getPricingPackOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组PricingPackOrder对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PricingPackOrder> getPricingPackOrderByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的PricingPackOrder记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingPackOrder> delPricingPackOrder(String id, Operator operator);
	
	/**
	 * 新增一条PricingPackOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingPackOrder 新增的PricingPackOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingPackOrder> addPricingPackOrder(PricingPackOrder pricingPackOrder, Operator operator);
	
	/**
	 * 根据主键，更新一条PricingPackOrder记录
	 * @param pricingPackOrder 更新的PricingPackOrder数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingPackOrder> updatePricingPackOrder(PricingPackOrder pricingPackOrder, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为PricingPackOrder对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingPackOrder obj);
/**
 * 获取会员可用的套餐列表 zln
 * */
	public List<PricingPackOrder> getPricingPackOrderListByUse(Query q);

	public List<PricingPackOrder> getPricingPackOrderListByDiKou(Query q);
	/**
	 * 根据金额套餐抵扣
	 * @param q
	 * @return
	 */
	public List<PricingPackOrder> getPricingPackOrderAmountListByDiKou(Query q);
	
	/*
	 * 查询赠送套餐报表信息
	 */
	public List<PricingPackOrder> eportFormPackageList(Query q);
	/*
	 * 查询赠送套餐报表信息（全部）
	 */
	public List<PricingPackOrder> eportFormPackageListAll();
	/*
	 * 查询赠送套餐报表信息（月份）
	 */
	public List<PricingPackOrder> eportFormPackageListMonth(Query q);
	/*
	 * 查询赠送套餐报表信息（日）
	 */
	public List<PricingPackOrder> eportFormPackageListDay(Query q);
	/**
	 * 获取对应套餐总销售额
	 */
	public Double getPackageSum(String packageId);
	/**
	 * 获取对应套餐数量
	 */
	public Long getPackageCount(String packageId);
	
	//获取我的余额
	public List<PricingPackOrder> getPricingPackOrderListBypo(Query q);	 
	
	/**
	 * 统计充值
	 */
	public Integer countRecharge();
	
	/**
	 * 手工加余额
	 * @param memberNo
	 * @param amount
	 * @param memo
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public void recharge(String memberNo, Double amount, String memo, Operator operator) throws Exception;
	/**
	 * 手工加余额
	 * @param memberNo
	 * @param amount
	 * @param memo
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public void dikou(String memberNo, Double amount, String memo, Operator operator) throws Exception;
			
}
