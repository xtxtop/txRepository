package cn.com.shopec.core.mlparking.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CParkBilling;
import cn.com.shopec.core.mlparking.model.CParkOrder;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.vo.LockOrder;
import cn.com.shopec.core.mlparking.vo.OrderInfo;

/**
 * 停车订单表 服务接口类
 */
public interface CParkOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CParkOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CParkOrder> getCParkOrderList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CParkOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CParkOrder> getCParkOrderPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CParkOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CParkOrder getCParkOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组CParkOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CParkOrder> getCParkOrderByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CParkOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CParkOrder> delCParkOrder(String id, Operator operator);

	/**
	 * 新增一条CParkOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cParkOrder
	 *            新增的CParkOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CParkOrder> addCParkOrder(CParkOrder cParkOrder, Operator operator);

	/**
	 * 根据主键，更新一条CParkOrder记录
	 * 
	 * @param cParkOrder
	 *            更新的CParkOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CParkOrder> updateCParkOrder(CParkOrder cParkOrder, Operator operator);

	public ResultInfo<CParkOrder> updateCParkOrder_two(CParkOrder cParkOrder, CParkBilling parkBilling,
			CParking parking, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CParkOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CParkOrder obj);

	// 查询当前会员订单
	public List<CParkOrder> getOrder(String menberNo);
	//立即--降锁并生成订单
	public ResultInfo<LockOrder> setOrder(String memberNo,Integer source,
			String lon,String lat,String spaceNo,String parkNo,boolean flag,String orderNo);
	//预约--并生成订单
	public ResultInfo<LockOrder> setOrderAppointment(String memberNo,Integer source,String parkNo,String spaceNo);
	//获取订单详情
	public	OrderInfo getOrderInfo(String parkOrderNo);
	//升锁
	public ResultInfo<Object> upLock(String orderNo);
	//取消预约
	public ResultInfo<Object> cancelAppointment(String orderNo);

	public ResultInfo<CParkOrder> updateCParkOrder(CParkOrder cpo);

	public ResultInfo<CParkOrder> addCParkOrder(CParkOrder cpo1);
	//获取未结束订单
	public CParkOrder getOrderOver(String menberNo);
}
