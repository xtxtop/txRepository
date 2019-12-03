package cn.com.shopec.core.mlorder.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.vo.OrderDetailVo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.model.ChargeOrder;

/**
 * 充电订单表 服务接口类
 */
public interface ChargeOrderService extends BaseService {
	/**
	 * 根据查询条件，查询并返回ChargeOrder的列表
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<ChargeOrder> getChargeOrderList(Query q);

	/**
	 * 根据查询条件，分页查询并返回ChargeOrder的分页结果
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<ChargeOrder> getChargeOrderPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个ChargeOrder对象
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	public ChargeOrder getChargeOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组ChargeOrder对象
	 *
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ChargeOrder> getChargeOrderByIds(String[] ids);

	/**
	 * 根据主键，删除特定的ChargeOrder记录
	 *
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargeOrder> delChargeOrder(String id, Operator operator);

	/**
	 * 新增一条ChargeOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 *
	 * @param chargeOrder
	 *            新增的ChargeOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargeOrder> addChargeOrder(ChargeOrder chargeOrder, Operator operator);

	/**
	 * 根据主键，更新一条ChargeOrder记录
	 *
	 * @param chargeOrder
	 *            更新的ChargeOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargeOrder> updateChargeOrder(ChargeOrder chargeOrder, Operator operator);

	public ResultInfo<ChargeOrder> updateChargeOrder_two(ChargeOrder chargeOrder, Operator operator);

	/**
	 * 生成主键
	 *
	 * @return
	 */
	public String generatePK();

	/**
	 * 为ChargeOrder对象设置默认值
	 *
	 * @param obj
	 */
	public void fillDefaultValues(ChargeOrder obj);

	/**
	 * 停止充电
	 */
	ResultInfo<OrderDetailVo> stopChargeOrder(String orderNo);

	/**
	 * 开始充电
	 */
	ResultInfo<OrderSimpleVo> startChargeOrder(String memberNo, String chargingGunNo, String deviceTp);

	// void changeReceiverOrder(ReceiverOrderVo receiverOrder);
	void changeReceiverOrder(String receiverOrder);

}
