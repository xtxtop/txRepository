package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.vo.PricingPackageStat;
import cn.com.shopec.core.statement.model.Pricing;

/**
 * 套餐产品表 服务接口类
 */
public interface PricingPackageService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PricingPackage的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<PricingPackage> getPricingPackageList(Query q);

	/**
	 * 根据查询条件，分页查询并返回PricingPackage的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<PricingPackage> getPricingPackagePagedList(Query q);

	/**
	 * 根据主键，查询并返回一个PricingPackage对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public PricingPackage getPricingPackage(String id);

	/**
	 * 根据套餐产品名称，查询并返回一个PricingPackage对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public PricingPackage getPricingPackageByPackageName(String packageName);

	/**
	 * 根据主键数组，查询并返回一组PricingPackage对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PricingPackage> getPricingPackageByIds(String[] ids);

	/**
	 * 根据主键，删除特定的PricingPackage记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<PricingPackage> delPricingPackage(String id, Operator operator);

	/**
	 * 新增一条PricingPackage记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param pricingPackage
	 *            新增的PricingPackage数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<PricingPackage> addPricingPackage(PricingPackage pricingPackage, Operator operator);

	/**
	 * 根据主键，更新一条PricingPackage记录
	 * 
	 * @param pricingPackage
	 *            更新的PricingPackage数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<PricingPackage> updatePricingPackage(PricingPackage pricingPackage, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为PricingPackage对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(PricingPackage obj);

	/**
	 * 套餐汇总
	 */
	public PageFinder<Pricing> getPricingPagedList(Query q);

	// 套餐产品待审核的汇总
	public Integer getTodoIndexValue();

	/**
	 * 查找套餐销售情况（月）
	 */
	public PricingPackage salePackageEportMonth(String packageNo, String time);

	/**
	 * 查找套餐销售情况（周）
	 */
	public PricingPackage salePackageEportWeek(String packageNo, String createTimeStartWeek, String createTimeEndWeek);
	
	/**
	 * 充值套餐销售数、销售额和充值额统计 新版本
	 */
	//按日统计充值套餐销售量、销售额和充值额
	public PricingPackageStat getPricingPackageDataDay(String time);
	//统计第一天之前包括第一天的销售数、销售额和充值额
	public PricingPackageStat getFirstDayPricingPackageData(String time);
	//统计周第一天之前包括第一天的销售数、销售额和充值额
	public PricingPackageStat getWeekFirstDayPricingPackageData(String time);
	//按周统计充值套餐销售量、销售额和充值额
	public PricingPackageStat getPricingPackageWeekData(String sTime,String eTime);
	//按月统计充值套餐销售量、销售额和充值额
	public PricingPackageStat getPricingPackageDataMonth(String time);
	//按月先统计X轴第一月包括该月之前的销售量、销售额和充值额
	public PricingPackageStat getMonthFirstPricingPackageData(String time);
}
