package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.vo.PricingPackageStat;
import cn.com.shopec.core.statement.model.Pricing;

/**
 * 套餐产品表 数据库处理类
 */
public interface PricingPackageDao extends BaseDao<PricingPackage,String> {

	List<Pricing> pageListPricing(Query q);

	long countPricing(Query q);

	Integer countPricingPackageCensorStatus();
	
	/**
	 * 充值套餐销售数、销售额和充值额统计 新版本
	 */
	PricingPackageStat getPricingPackageDataDay(String time);
	//统计第一天之前包括第一天的销售数、销售额和充值额
	PricingPackageStat getFirstDayPricingPackageData(String time);
	//统计周第一天之前包括第一天的销售数、销售额和充值额
	PricingPackageStat getWeekFirstDayPricingPackageData(String time);
	//按周统计充值套餐销售量、销售额和充值额
	PricingPackageStat getPricingPackageWeekData(String sTime,String eTime);
	//按月统计充值套餐销售量、销售额和充值额
	PricingPackageStat getPricingPackageDataMonth(String time);
	//按月先统计X轴第一月包括该月之前的销售量、销售额和充值额
	PricingPackageStat getMonthFirstPricingPackageData(String time);
	/**
	 * 查找套餐销售情况（月） 旧版本
	 */
	PricingPackage salePackageEportMonth(String packageNo,String time);
	/**
	 * 查找套餐销售情况（周）
	 */
	PricingPackage salePackageEportWeek(String packageNo,String createTimeStartWeek,String createTimeEndWeek);
	
	/**
	 * 根据套餐产品名称，查询并返回一个PricingPackage对象
	 * @param id 主键id
	 * @return
	 */
	PricingPackage getPricingPackageByPackageName(String packageName);
}
