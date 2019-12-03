package cn.com.shopec.core.order.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.CheckAccounts;
import cn.com.shopec.core.finace.model.CheckAccountsView;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderCountVo;
import cn.com.shopec.core.order.vo.OrderAmountDayVo;
import cn.com.shopec.core.order.vo.OrderReportFormVo;
import cn.com.shopec.core.order.vo.OrderReportStatisticalVo;
import cn.com.shopec.core.order.vo.OrderStatisticalVo;
import cn.com.shopec.core.order.vo.OrderStatusVo;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.statement.model.CarRpts;
import cn.com.shopec.core.statement.model.Details;
import cn.com.shopec.core.statement.model.GroupRpts;
import cn.com.shopec.core.statement.model.Money;
import cn.com.shopec.core.statement.model.ParkRpts;
import cn.com.shopec.core.statement.model.Summary;

/**
 * 订单表 服务接口类
 */
public interface OrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Order的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Order> getOrderList(Query q);

	/**
	 * 根据查询条件，查询并返回Order的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Order> queryAllInvoice(Query q);

	/**
	 * 根据查询条件，分页查询并返回Order的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Order> getOrderPagedList(Query q);

	/**
	 * 根据查询条件，分页查询并返回Order的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Order> pageListInvoice(Query q);

	/**
	 * 根据主键，查询并返回一个Order对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public Order getOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组Order对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Order> getOrderByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Order记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Order> delOrder(String id, Operator operator);

	/**
	 * 新增一条Order记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param order
	 *            新增的Order数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Order> addOrder(Order order, Operator operator);

	/**
	 * 根据主键，更新一条Order记录
	 * 
	 * @param order
	 *            更新的Order数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Order> updateOrder(Order order, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Order对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Order obj);

	/**
	 * 行程中的订单信息（包括车辆信息和车辆状态信息）
	 */
	public ResultInfo<Order> getTripOrder(String orderNo);

	/**
	 * 获取取车点的历史记录
	 */
	public List<Park> getOrderByMemberNoTake(String memberNo);

	/**
	 * 取消订单（订单和车辆状态修改）
	 */
	public ResultInfo<Order> cancelOrder(String orderNo);

	/**
	 * 获取还车点的历史记录
	 */
	public List<Park> getOrderByMemberNoReturn(String memberNo);

	/**
	 * 判断当前是否有正在进行中的订单
	 */
	public int judgeNowadayOrder(String memberNO);

	/**
	 * 得到当前正在进行中的订单
	 */
	public Order getNowadayOrderByMemberNo(String memberNo);

	/**
	 * 记录首次开门时间
	 */
	public ResultInfo<String> startOrdeOpenCarDoorTime(String orderNo, Date OpenCarDoorTime);

	/** 记录首次计费时间 */
	public ResultInfo<String> setStartBillingTime(String orderNo, Date startBillingTime);

	/** 记录取车点 */
	public void recordTakeCarLocation(String orderNo);

	/** 记录取车点 */
	public void recordTakeCarLocation(Order order);

	/**
	 * 记录第一次开车门时间及开始计费时间，同时订单状态改为“已计费”
	 * 
	 * @param orderNo
	 * @param datetime
	 * @param memberNo
	 * @return
	 */
	public ResultInfo<String> firsttimeOpenCarDoorAndStartBilling(String orderNo, Date datetime, String memberNo);

	/**
	 * 根据车辆编号，修改订单表，记录第一次开车门时间及开始计费时间，同时订单状态改为“已计费
	 * 
	 * @param carNo
	 * @param datetime
	 * @return
	 */
	public ResultInfo<String> updateFirsttimeOpenCarDoorAndStartBillingByCarNo(String carNo, Date datetime);

	/**
	 * 得到所有开始自动计费的订单；
	 */
	public List<Order> getAllAutoBeginCharging();

	/**
	 * 得到所有自动取消计费的订单
	 * 
	 * @throws ParseException
	 */
	public List<Order> getAllAutoEndCharging() throws ParseException;

	/**
	 * 根据条件统计订单数
	 * 
	 * @param q
	 * @return
	 */
	public int countOrder(Query q);

	/**
	 * 获取会员欠费金额
	 */
	public Double getAmountByMemberNo(String memberNo);

	/**
	 * 获取会员的订单数
	 */
	public Long getOrderNumByMemberNo(String memberNo);

	/**
	 * 查询当前状态为订单中和已完成的最新订单信息
	 * 
	 * @param q
	 * @return
	 */
	public List<Order> getIdleCar(Query q);

	/**
	 * 统计当前状态为订单中和已完成的最新订单信息
	 * 
	 * @param q
	 * @return
	 */
	public int countIdleCar(Query q);

	/**
	 * 订单结算方法
	 * 
	 * @throws ParseException
	 */
	public ResultInfo<Order> orderBalance(Order order, String endParkNo) throws ParseException;

	/**
	 * 行程中订单金额时长动态显示
	 * 
	 * @param <OrderStatusVo>
	 * @throws ParseException
	 */
	public ResultInfo<Order> orderTripView(Order order) throws ParseException;

	// 前台查询我的订单，当前和最近一个月
	public List<Order> getOrderLists(Query q);

	/**
	 * 统计当前驶出的车辆
	 * 
	 * @param q
	 * @return
	 */
	public Long countscCar(Query q);

	/**
	 * 根据车辆编号查询最新订单信息
	 */
	public Order getOrderNowByCarNo(String carNo);

	/**
	 * 获取财务对账列表信息
	 */
	public PageFinder<CheckAccounts> getCheckAccountsPagedList(Query q);

	/**
	 * 导出财务对账列表信息
	 */
	public List<CheckAccounts> getCheckAccountsList(CheckAccounts checkAccounts);

	public ResultInfo<OrderStatusVo> getNowadayOrder(String memberNo, String deviceKey) throws ParseException;

	public PageFinder<CheckAccountsView> getCheckAccountsViewPagedList(Query q);

	/**
	 * 导出财务对账明细信息
	 */
	public List<CheckAccountsView> getCheckAccountsViewList(CheckAccountsView checkAccountsView);

	/**
	 * 财务汇总信息
	 */
	public PageFinder<Summary> getSummaryPagedList(Query q);

	/**
	 * 财务汇总信息导出
	 */
	public List<Summary> getSummaryList(Summary summary);

	/**
	 * 财务明细信息
	 */
	public PageFinder<Details> getDetailsPagedList(Query q);

	/**
	 * 财务明细信息导出
	 */
	public List<Details> getDetailsList(Details details);

	/**
	 * 财务资金信息
	 */
	public ResultInfo<Money> getMoneyDetails(Money money);

	/**
	 * 财务场站汇总信息
	 */
	public PageFinder<ParkRpts> getParkRptsPagedList(Query q);

	/**
	 * 财务场站信息导出
	 */
	public List<ParkRpts> getParkRptsList(ParkRpts parkRpts);

	/**
	 * 财务车辆汇总信息展示
	 */
	public PageFinder<CarRpts> getCarRptsPagedList(Query q);

	/**
	 * 财务车辆汇总导出
	 */
	public List<CarRpts> getCarRptsList(CarRpts carRpts);

	/*
	 * 财务 集团 汇总
	 */

	public PageFinder<GroupRpts> getGroupRptsPagedList(Query q);

	/*
	 * 财务 集团 汇总 导出
	 */

	public List<GroupRpts> getGroupRptsList(GroupRpts groupRpts);

	/**
	 * 根据车辆编号查询车辆据现在时间最近的一笔订单（状态为已完成或者已取消）
	 * 
	 * @param carNo
	 * @return
	 */
	public Order getOrderNewestByCarNo(String carNo);

	/**
	 * 根据请求时间查询出订单的相关数据
	 * 
	 * @param startTime
	 *            当天开始时间 0分0秒
	 * @param endTime
	 *            请求时间
	 * @param open
	 *            是否开启查询隐私数据 1 开启 0 关闭
	 * @return Map<String, Integer>
	 */
	public Map<String, Integer> getIndexValue(String startTime, String endTime, int i);

	public List<Map<String, Object>> getRealTimeInOrderNum();

	/**
	 * 订单运营数据统计-日
	 * 
	 * @param endTime
	 * @param dayParmaeter
	 * @return
	 */
	public OrderCountVo orderOperateCount(Date startTime, Date endTime, int dayParmaeter, List<String> list);

	/**
	 * 订单运营数据统计-周
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public OrderCountVo weekOrderOperateCount(Date startTime, Date endTime, int weekParmaeter, List<String> list);

	/**
	 * 订单运营数据统计-月
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public OrderCountVo monthOrderOperateCount(Date startTime, Date endTime, int memberParmaeter, List<String> list);

	/**
	 * 订单运营数据统计-年
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public OrderCountVo yearOrderOperateCount(Date startTime, Date endTime, int yearParmaeter, List<String> list);

	/**
	 * 客户订单运营数据统计-日
	 * 
	 * @param endTime
	 * @param dayParmaeter
	 * @return
	 */
	public List<OrderCountVo> orderUserOperateCount(Date startTime, Date endTime, int yearParmaeter);

	/**
	 * 客户订单运营数据统计-周
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<OrderCountVo> weekUserOrderOperateCount(Date startTime, Date endTime, int yearParmaeter);

	/**
	 * 客户订单运营数据统计-月
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<OrderCountVo> monthUserOrderOperateCount(Date startTime, Date endTime, int yearParmaeter);

	/**
	 * 客户订单运营数据统计-年
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<OrderCountVo> yearUserOrderOperateCount(Date startTime, Date endTime, int yearParmaeter);

	/**
	 * 查找订单统计数据（报表）
	 */
	public OrderReportFormVo orderNumberNs(Query q);

	public OrderReportFormVo getOrderFirstDayData(Query q);

	public OrderReportFormVo getOrderFirstWeekDayData(Query q);

	/**
	 * 根据条件查找订单统计数据（报表）
	 */
	public OrderReportFormVo orderNumberReport(Query q);

	public OrderAmountDayVo getOrderPayAmountData(String time);// 统计订单付款金额

	public OrderAmountDayVo getOrderAmountAndBalanceData(String time);// 统计订单金额和余额抵扣金额

	public OrderAmountDayVo getOrderPayAmountDataFirst(String time);// 统计订单付款金额
																	// 统计X轴第一天日期之前的数值，包括第一天

	public OrderAmountDayVo getOrderAmountAndBalanceDataFirst(String time);// 统计订单金额和余额抵扣金额
																			// 统计X轴第一天日期之前的数值，包括第一天

	public OrderAmountDayVo getOrderAmountAndBalanceWeekDataFirst(String time);

	public OrderAmountDayVo getOrderPayAmountWeekDataFirst(String time);

	/**
	 * 根据条件查找订单统计数据（周）
	 */
	public OrderReportFormVo orderNumberReportWeek(Query q);

	public OrderReportStatisticalVo orderNumberReportStatWeek(Query q);

	/**
	 * 根据条件查找订单统计数据（每日合计）
	 */
	public OrderReportFormVo orderNumberReportNs(Query q);

	public PageFinder<Order> getOrderPagedListPayableAmount(Query q);

	/**
	 * 得到所有在进行中超过配置的时间的订单
	 * 
	 * @param op
	 * @return
	 */
	public Integer getAllAutoStartOrderCharging();

	/**
	 * 查询收支汇总
	 * 
	 * @param money
	 * @return
	 */
	public ResultInfo<Money> getIncomeTotal(Money money);

	/**
	 * 查询订单会员数
	 * 
	 * @return
	 */
	public Integer countOrderMember();

	/**
	 * 获取交易统计的集合
	 * 
	 * @return
	 */
	Map<String, Object> getTransactionCountMap();

	/**
	 * 获取近10天的订单数
	 * 
	 * @return
	 */
	Map<String, Object> getOrderDay10CountMap();

	/**
	 * 查询超额订单数
	 * 
	 * @return
	 */
	public Integer countExcessOrder();

	/**
	 * 还车后金额套餐抵扣
	 * 
	 * @param order
	 * @param diKouAmount
	 *            待抵扣金额
	 * @return
	 * @throws ParseException
	 */
	public Order diKouPricingPackOrderAmout(Order order, Double diKouAmount) throws ParseException;

	// 查询超额的订单
	public Order getOrderWaring(String memberNo);

	public PageFinder<Order> getOrderPagedListW(Query q);

	/**
	 * 订单汇总 场站汇总----新修改的接口
	 */
	public PageFinder<OrderStatisticalVo> pageListOrderStat(Query q);

	public List<OrderStatisticalVo> getOrderExportStatList(OrderStatisticalVo orderVo);

	public PageFinder<ParkRpts> pageListParkSummary(Query q);

	public List<ParkRpts> listParkSummaryExport(ParkRpts parkRpts);

	// 按周
	public OrderReportStatisticalVo getOrderPayAmountWeekData(Query q);// 统计订单付款金额

	public OrderReportStatisticalVo getOrderAmountAndBalanceWeekData(Query q);// 统计订单金额和余额抵扣金额

	// 订单销售量和订单销售额统计和累计
	public OrderReportStatisticalVo orderNumberReportMonth(Query q);// 按月统计销售量

	public OrderReportStatisticalVo getOrderPayAmountMonthData(String time);// 按月统计订单付款金额

	public OrderReportStatisticalVo getOrderAmountAndBalanceMonthData(String time);// 按月统计订单金额和余额抵扣金额

	public OrderReportStatisticalVo getFirstMonthOrderData(Query q);// 先统计X轴第一月之前的新增订单数、完成和取消订单数

	public OrderReportStatisticalVo getOrderPayAmountFirstMonth(String time);// 统计订单付款金额
																				// 统计X轴第一月日期之前的数值，包括第一月

	public OrderReportStatisticalVo getOrderAmountAndBalanceFirstMonth(String time);// 统计X轴第一月之前的订单金额和余额抵扣额，包括第一月

	Map<String, Object> getCarOrderCountMap();
}