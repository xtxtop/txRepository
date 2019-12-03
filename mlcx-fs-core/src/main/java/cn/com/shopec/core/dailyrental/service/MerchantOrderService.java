package cn.com.shopec.core.dailyrental.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.MerchantOrder;

/**
 * 商家订单表 服务接口类
 */
public interface MerchantOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MerchantOrder的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MerchantOrder> getMerchantOrderList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MerchantOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MerchantOrder> getMerchantOrderPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MerchantOrder对象
	 * @param id 主键id
	 * @return
	 */
	public MerchantOrder getMerchantOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组MerchantOrder对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MerchantOrder> getMerchantOrderByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MerchantOrder记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantOrder> delMerchantOrder(String id, Operator operator);
	
	/**
	 * 新增一条MerchantOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchantOrder 新增的MerchantOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantOrder> addMerchantOrder(MerchantOrder merchantOrder, Operator operator);
	
	/**
	 * 根据主键，更新一条MerchantOrder记录
	 * @param merchantOrder 更新的MerchantOrder数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantOrder> updateMerchantOrder(MerchantOrder merchantOrder, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为MerchantOrder对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MerchantOrder obj);
	
	/**
	 * 商品订单分页
	 * 
	 * @param q
	 * @return
	 */
	public PageFinder<Map<String, Object>> getOrderMapPagedList(Query q);
	
	/**
	 * 获取换车订单信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getChangeMerchantOrder(String id);
	
	/**
	 * 商家交车处理订单
	 * 
	 * @param merchantOrder
	 * @return
	 */
	ResultInfo<MerchantOrder> giveMerchantOrder(MerchantOrder merchantOrder, Operator operator);
	
	/**
	 * 商家还车处理订单
	 * 
	 * @param merchantOrder
	 * @return
	 */
	ResultInfo<MerchantOrder> checkMerchantOrder(MerchantOrder merchantOrder, Date actualTakeTime, Double overTimeAmount,Operator operator);
	/**
	 * 根据订单号查找最新的商家订单
	 * @param orderNo
	 * @return
	 */
	public MerchantOrder getOrderDayNo(String orderNo);
	/**
	 * 根据订单号查找拒单的商家订单
	 * @param orderNo
	 * @return
	 */
	public MerchantOrder getOrderDayByOrderNo(String orderNo);
	
	/**
	 * 财务对账
	 * 
	 * @param merchantId
	 * @return
	 */
	public List<Map<String, Object>> balanceOfAccount(String merchantId);
		
	/**
	 * 根据商家id查找商家的订单
	 * @param orderNo
	 * @return
	 */
	public String getMerhcantOrderNo(String merchantId);
}
