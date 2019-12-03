package cn.com.shopec.core.finace.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.httpClient.HttpRequest;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.model.TransToAccount;

/**
 * 押金退款表 服务接口类
 */
public interface DepositRefundService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DepositRefund的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<DepositRefund> getDepositRefundList(Query q);

	/**
	 * 根据查询条件，分页查询并返回DepositRefund的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<DepositRefund> getDepositRefundPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个DepositRefund对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public DepositRefund getDepositRefund(String id);

	/**
	 * 根据主键数组，查询并返回一组DepositRefund对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DepositRefund> getDepositRefundByIds(String[] ids);

	/**
	 * 根据主键，删除特定的DepositRefund记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<DepositRefund> delDepositRefund(String id, Operator operator);

	/**
	 * 新增一条DepositRefund记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param depositRefund
	 *            新增的DepositRefund数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<DepositRefund> addDepositRefund(DepositRefund depositRefund, Operator operator);

	/**
	 * 根据主键，更新一条DepositRefund记录
	 * 
	 * @param depositRefund
	 *            更新的DepositRefund数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<DepositRefund> updateDepositRefund(DepositRefund depositRefund, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为DepositRefund对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(DepositRefund obj);

	/**
	 * 退款操作,支付宝
	 */
	public ResultInfo<Object> toDepositRefundPay(String depositRefundNo, HttpServletResponse response, String memo)
			throws Exception;

	/**
	 * 支付宝免密退款操作
	 */
	public ResultInfo<DepositRefund> toDepositRefundAvoidPwdPay(String depositRefundNo,Operator operator, String remark);
	/** 支付宝退款的回调方法 */
	public void depositRefundAlipayUpdate(HttpServletRequest request, HttpServletResponse response,Operator operator) throws Exception;

	/**
	 * 退款操作微信
	 */
	public ResultInfo<DepositRefund> payRefundWX(DepositRefund dRefund, DepositOrder dOrder,Operator operator);

	/**
	 * 线下退款
	 */
	public ResultInfo<DepositRefund> depositRefundPayMemo(DepositRefund dRefund,Operator operator);

	/**
	 * 验证当前订单是否在退款有效期内 -- 支付宝
	 * 
	 * @param depositRefundNo
	 *            押金单号
	 * @param tradeInfo
	 *            支付平台交易信息           
	 * @return
	 */
	public ResultInfo<String> signOrder(String depositRefundNo, Map<String, String> tradeInfo);
	
	/**
	 * 根据申请退款单单号进行支付宝转账操作
	 * 
	 * @param depositRefundNo	押金单号
	 * @param remark	转账备注
	 * @param payeeId 收款人id
	 * @param payeeAccount 收款人账号           
	 * @param	operator 操作人
	 * @return
	 */
	public ResultInfo<TransToAccount> transAccount(String depositRefundNo,String remark,String payeeId, String payeeAccount,Operator operator);
	
	/**
	 * 转账操作微信
	 */
	 
	public ResultInfo<DepositRefund> payRefundWXZM(DepositRefund dRefund, DepositOrder dOrder,  PaymentRecord paymentRecord, HttpServletRequest request);
	//押金退款待审核
	public Integer getTodoIndexValue();



}
