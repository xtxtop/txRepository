package cn.com.shopec.core.finace.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositOrderForMgt;
import cn.com.shopec.core.finace.model.MemberDepositRefund;

/**
 * 押金支付订单表 服务接口类
 */
public interface DepositOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DepositOrder的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<DepositOrder> getDepositOrderList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回DepositOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<DepositOrder> getDepositOrderPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个DepositOrder对象
	 * @param id 主键id
	 * @return
	 */
	public DepositOrder getDepositOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组DepositOrder对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DepositOrder> getDepositOrderByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的DepositOrder记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DepositOrder> delDepositOrder(String id, Operator operator);
	
	/**
	 * 新增一条DepositOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param depositOrder 新增的DepositOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DepositOrder> addDepositOrder(DepositOrder depositOrder, Operator operator);
	
	/**
	 * 根据主键，更新一条DepositOrder记录
	 * @param depositOrder 更新的DepositOrder数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DepositOrder> updateDepositOrder(DepositOrder depositOrder, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
	/**
	 * 为DepositOrder对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DepositOrder obj);
	/**
	 * 获取会员的剩余押金@author zln
	 * */
	public Double getAmountByMemberNo(String memberNo);

	/**
	 * 根据会员编号查询保证金信息
	 * @param memberNo
	 * @return
	 */
	public DepositOrder getMemberDepositOrder(String memberNo);
	
	
	/**
	 * 根据会员查询会员当前保证金的使用情况
	 * @param memberNo
	 * @return
	 */
	public ResultInfo<Deposit> getDepositByMemberNo(String memberNo,String addrRegion);
	
	/**
	 * 会员申请保证金退款
	 * @param memberNo
	 * @param refundGroundsMemo 
	 * @param refundGrounds 
	 * @return
	 */
	public ResultInfo<MemberDepositRefund> getDepositRefundByMemberNo(String memberNo, String refundGrounds, String refundGroundsMemo);

	/**
	 * 判断是否可以申请提现押金
	 * @param memberNo
	 * @return
	 */
	public ResultInfo<MemberDepositRefund> getIsDepositRefundByMemberNo(String memberNo);

	
	/**
	 * 得到会员应付金额（计算地区，信用分）
	 * @param memberNo
	 * @param addrRegion
	 * @return
	 */
	public Double getMemberPayableDeposit(String memberNo, String addrRegion);
	
	/**
	 * 押金缴纳列表
	 * @param q
	 * @return
	 */
	public PageFinder<DepositOrderForMgt> getMemberDepositList(Query q);

}
