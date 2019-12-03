package cn.com.shopec.core.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.finace.model.DepositOrder;

/**
 * 银联手机控件服务接口
 * 
 * @author 许瑞伟
 *
 */
public interface UnionPayService {
	
	/**
	 * 获取预授权TN流水号
	 * 
	 * @param req
	 * @param resp
	 */
	public ResultInfo<String> getPreTN(DepositOrder order);
	
	/**
	 * 预授权后台回调
	 * 
	 * @param req
	 * @param resp
	 */
	public void backPreRcv(HttpServletRequest req, HttpServletResponse resp);
	
	/**
	 * 查询订单状态(预授权)
	 * 
	 * @param req
	 * @param resp
	 */
	public ResultInfo<String> queryOrderStatus(HttpServletRequest req, HttpServletResponse resp);
	
	/**
	 * 预授权撤销
	 * 
	 * @param req
	 * @param resp
	 */
	public ResultInfo<String> backPreTrans(String orderNo);
	
	/**
	 * 预授权完成
	 * 
	 * @param req
	 * @param resp
	 */
	public ResultInfo<String> finalPreTrans(String orderNo);
	
	/**
	 * 预授权完成撤销
	 * 
	 * @param req
	 * @param resp
	 */
	public ResultInfo<String> finalAndBackPreTrans(String orderNo);
	
	/**
	 * 查询订单状态（预授权撤销和完成撤销）
	 * 
	 * @param order
	 */
	public ResultInfo<String> queryOrderStatus(DepositOrder order);

}
