package cn.com.shopec.core.dailyrental.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.pay.aliPay.AlipaySubmit;
import cn.com.shopec.common.pay.wxPay.GetWxOrderno;
import cn.com.shopec.common.pay.wxPay.PayCommonUtil;
import cn.com.shopec.common.pay.wxPay.TenpayUtil;
import cn.com.shopec.common.pay.wxPay.WxpayConfig;
import cn.com.shopec.common.pay.wxPay.XMLUtil;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.dao.DelayOrderDayDao;
import cn.com.shopec.core.dailyrental.dao.OrderDayDao;
import cn.com.shopec.core.dailyrental.model.DelayOrderDay;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 日租订单表 服务实现类
 */
@Service
public class OrderDayServiceImpl implements OrderDayService {

	private static final Log log = LogFactory.getLog(OrderDayServiceImpl.class);
	
	@Resource
	private OrderDayDao orderDayDao;
	@Resource
	private DelayOrderDayDao delayOrderDayDao;
	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 根据查询条件，查询并返回OrderDay的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderDay> getOrderDayList(Query q) {
		List<OrderDay> list = null;
		try {
			//直接调用Dao方法进行查询
			list = orderDayDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderDay>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回OrderDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<OrderDay> getOrderDayPagedList(Query q) {
		PageFinder<OrderDay> page = new PageFinder<OrderDay>();
		
		List<OrderDay> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = orderDayDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = orderDayDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderDay>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据查询条件，分页查询并返回OrderDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<OrderDay> getOrderDayPagedListForMgt(Query q) {
		PageFinder<OrderDay> page = new PageFinder<OrderDay>();
		
		List<OrderDay> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = orderDayDao.pageListForMgt(q);
			//调用dao统计满足条件的记录总数
			rowCount = orderDayDao.countForMgt(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderDay>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据查询条件，分页查询并返回订单退款的分页结果
	 * @param q 查询条件
	 * @return
	 */
//	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PageFinder<OrderDay> getOrderDayRefundPagedList(Query q) {
		PageFinder<OrderDay> page = new PageFinder<OrderDay>();
		
		List<OrderDay> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = orderDayDao.pageListForRefund(q);
			//调用dao统计满足条件的记录总数
			rowCount = orderDayDao.countForRefund(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderDay>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据主键，查询并返回一个OrderDay对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public OrderDay getOrderDay(String id) {
		OrderDay obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = orderDayDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组OrderDay对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderDay> getOrderDayByIds(String[] ids) {
		List<OrderDay> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = orderDayDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderDay>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的OrderDay记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderDay> delOrderDay(String id, Operator operator) {
		ResultInfo<OrderDay> resultInfo = new ResultInfo<OrderDay>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = orderDayDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			}		  
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条OrderDay记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param orderDay 新增的OrderDay数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderDay> addOrderDay(OrderDay orderDay, Operator operator) {
		ResultInfo<OrderDay> resultInfo = new ResultInfo<OrderDay>();
		
		if (orderDay == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderDay = " + orderDay);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (orderDay.getOrderNo() == null) {
					orderDay.setOrderNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					orderDay.setOperatorType(operator.getOperatorType());
					orderDay.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				orderDay.setCreateTime(now);
				orderDay.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(orderDay);
				
				//调用Dao执行插入操作
				orderDayDao.add(orderDay);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(orderDay);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}	
		}
		
		return resultInfo;
	}			
	
	/**
	 * 根据主键，更新一条OrderDay记录
	 * @param orderDay 更新的OrderDay数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderDay> updateOrderDay(OrderDay orderDay, Operator operator) {
		ResultInfo<OrderDay> resultInfo = new ResultInfo<OrderDay>();
		
		if (orderDay == null || orderDay.getOrderNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderDay = " + orderDay);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					orderDay.setOperatorType(operator.getOperatorType());
					orderDay.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				orderDay.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = orderDayDao.update(orderDay);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(orderDay);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}
		
		return resultInfo;
	}	
	
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为OrderDay对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(OrderDay obj) {
		if (obj != null) {
		    if (obj.getOrderStatus() == null) {
		    	obj.setOrderStatus(0);
		    }
		    if (obj.getPayStatus() == null) {
		    	obj.setPayStatus(0);
		    }
		    if (obj.getServiceCharge() == null) {
		    	obj.setServiceCharge(0d);
		    }
		    if (obj.getIsIllegal() == null) {
		    	obj.setIsIllegal(0);
		    }
		    if (obj.getIsFault() == null) {
		    	obj.setIsFault(0);
		    }
		    if (obj.getIsCancel() == null) {
		    	obj.setIsCancel(0);
		    }
		    if (obj.getOrderSource() == null) {
		    	obj.setOrderSource("1");
		    }
		    if (obj.getIsDelayOrder() == null) {
		    	obj.setIsDelayOrder(0);
		    }
		    if (obj.getIsBeforeOrder() == null) {
		    	obj.setIsBeforeOrder(0);
		    }
		}
	}

	@Override
	public int count(Query q) {
		int count = 0;
		try {
			count = orderDayDao.count(q).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}
	
	public int countOrder(String memberNo) {
		int count = 0;
		try {
			count = orderDayDao.countOrder(memberNo).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}
	public int countOrderByCarModelId(String carModelId) {
		int count = 0;
		try {
			count = orderDayDao.countOrderByCarModelId(carModelId).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}

	@Transactional
	public ResultInfo<DelayOrderDay> delayOrderDay(DelayOrderDay delayOrderDay) {
		ResultInfo<DelayOrderDay> result = new ResultInfo<DelayOrderDay>();
		try {
			delayOrderDay.setDelayOrderId(this.generatePK());
			delayOrderDayDao.add(delayOrderDay);
			OrderDay orderDay = new OrderDay();
			orderDay.setOrderNo(delayOrderDay.getOrderNo());
			orderDay.setIsDelayOrder(1);
			orderDay.setPayStatus(0);
			orderDayDao.update(orderDay);
			result.setCode(Constant.SECCUESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return result;
	}
	/**
	 * 根据单号查询当前订单是否支持退款
	 */
	@Override
	public ResultInfo<String> signOrder(String orderNo, Map<String, String> tradeInfo) {

		ResultInfo<String> resultInfo = new ResultInfo<String>();
		// 获取当前押金退款申请对象
		OrderDay order = this.getOrderDay(orderNo);
		if (null != order) {
			// 根据支付单号与退款申请单号请求第三方平台（支付宝或微信）核验订单有效期
			if (order.getPaymentMethod() == 1) { // 支付方式（1、支付宝、2、微信）
				resultInfo = checkFromAlipay(order.getPaymentFlowNo(), order.getOrderNo(), tradeInfo); // 支付宝交易流水号，本系统唯一支付订单号
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("订单数据异常，无法查询");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("订单信息数据异常，无法查询");
		}
		return resultInfo;
	}
	@Override
	public ResultInfo<OrderDay> toOrderDayRefundAvoidPwd(String orderNo,Double refundAmount,Operator operator,String remark){

		ResultInfo<OrderDay> resultInfo = new ResultInfo<OrderDay>();

		if (null != orderNo && !"".equals(orderNo)) {
			OrderDay orderDay = orderDayDao.get(orderNo);
			if (null != orderDay) {

				String appId = AlipayConfig.appId;
				String privateKey = AlipayConfig.rsa_private;
				String publicKey = AlipayConfig.rsa_public;
				String outBizNo = orderDay.getPartTradeFlowNo(); // 订单支付时传入的商户订单号
				String paymentFlowNo = orderDay.getPaymentFlowNo();//支付宝交易号
				SysParam sps=sysParamService.getByParamKey("aliPaySignType");
				String a="RSA";
				if(sps != null && "2".equals(sps.getParamValue())){
					a="RSA2";
				}
				AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId,
						privateKey, "json", "UTF-8", publicKey, a);
							AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
				request.setBizContent("{" + "\"out_trade_no\":\"" + outBizNo + "\"," + "\"trade_no\":\"" + paymentFlowNo
						+ "\"," + "\"refund_amount\":\"" + refundAmount + "\"," + "\"refund_reason\":\"" + remark + "\"}");
				try {
					AlipayTradeRefundResponse  response = alipayClient.execute(request); // 退款
					String outRequestNo = ECRandomUtils.getRandomHexStr(16);
					Map<String, Object> params = new HashMap<>();
			        params.put("appId", appId);
			        params.put("privateKey",privateKey);
			        params.put("publicKey", publicKey);
			        params.put("outBizNo", outBizNo);
			        params.put("paymentFlowNo", paymentFlowNo);
			        params.put("refundAmount", refundAmount);
			        params.put("remark", remark);
			        params.put("out_request_no", outRequestNo);
					//获取退款结果
					log.info("response:"+response);
					if (response.isSuccess()) { // 调用退款接口成功
						if ("10000".equals(response.getCode())) {
							OrderDay orderDayForUpdate = new OrderDay();
							orderDayForUpdate.setOrderNo(orderDay.getOrderNo());
							orderDayForUpdate.setRefundStatus(1);
							orderDayForUpdate.setRefundTime(new Date());
							orderDayDao.update(orderDayForUpdate);
							resultInfo.setCode(Constant.SECCUESS);
							resultInfo.setMsg("退款成功");
						} else {
							OrderDay orderDayForUpdate = new OrderDay();
							orderDayForUpdate.setOrderNo(orderDay.getOrderNo());
							// 退款失败
							// 调取查询支付宝交易退款接口
							AlipayTradeFastpayRefundQueryRequest getTransInfo = new AlipayTradeFastpayRefundQueryRequest();
							request.setBizContent("{" + "\"out_trade_no\":\"" + outBizNo + "\"" + "}");
							try {
								AlipayTradeFastpayRefundQueryResponse getInfoResponse = alipayClient.execute(getTransInfo);
								if (getInfoResponse.isSuccess()) {
									if (!"10000".equals(getInfoResponse.getCode())) {
										orderDayForUpdate.setOrderMemo(getInfoResponse.getRefundReason());
										log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"+ getInfoResponse.getRefundReason());
									}
								} else {
									orderDayForUpdate.setOrderMemo(getInfoResponse.getRefundReason());
									log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"+ getInfoResponse.getRefundReason());
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
							orderDayForUpdate.setRefundStatus(2);// 退款状态2.退款失败
							orderDayForUpdate.setUpdateTime(new Date());
							orderDayForUpdate.setRefundTime(new Date());
							if (operator!=null) {
								orderDayForUpdate.setOperatorId(operator.getOperatorId());;
							}
							orderDayDao.update(orderDayForUpdate);
							
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("退款失败");
						}
					} else {
						OrderDay orderDayForUpdate = new OrderDay();
						orderDayForUpdate.setOrderNo(orderDay.getOrderNo());
						orderDayForUpdate.setOrderMemo(response.getMsg());
						orderDayForUpdate.setRefundStatus(2);// 退款状态2.退款失败
						orderDayForUpdate.setUpdateTime(new Date());
						orderDayForUpdate.setRefundTime(new Date());
						if (operator!=null) {
							orderDayForUpdate.setOperatorId(operator.getOperatorId());;
						}
						orderDayDao.update(orderDayForUpdate);
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("退款请求失败，失败原因为:" + response.getSubMsg());
					}

					log.info(response.getSubMsg());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			
			} 
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("退款失败,退款编号不存在");
		}
		return resultInfo;
	
	}
	/**
	 * 支付宝单笔交易状态查询
	 * 
	 * @param paymentFlowNo
	 * @param depositOrderNo
	 */
	private ResultInfo<String> checkFromAlipay(String paymentFlowNo, String orderNo,
			Map<String, String> tradeInfo) {
		ResultInfo<String> result = new ResultInfo<String>();
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("trade_no", paymentFlowNo);
		sParaTemp.put("out_trade_no", orderNo);
		// 建立请求
		try {
			String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
			log.info("request result info :" + sHtmlText);
			Document doc = null;
			doc = DocumentHelper.parseText(sHtmlText); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			String requestInfo = rootElt.elementTextTrim("is_success");
			if ("T".equals(requestInfo)) {
				Iterator iter = rootElt.elementIterator("response"); // 获取根节点下的子节点alipay
				// 遍历alipay节点
				while (iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					Iterator tradeNode = recordEle.elementIterator("trade"); // 获取子节点response下的子节点trade
					while (tradeNode.hasNext()) {
						result.setCode(Constant.SECCUESS);
						result.setMsg("查询成功");
						Element itemEle = (Element) tradeNode.next();
						String tradeStatus = itemEle.elementTextTrim("trade_status");
						result.setData(tradeStatus);

						String buyerId = itemEle.elementTextTrim("buyer_id");
						String buyerEmail = itemEle.elementTextTrim("buyer_email");

						if (tradeInfo != null) {
							tradeInfo.put("buyer_id", buyerId);
							tradeInfo.put("buyer_email", buyerEmail);
						}
					}
				}

			} else if ("F".equals(requestInfo)) { // 查询结果失败，记录错误信息
				String failInfo = rootElt.elementTextTrim("error");
				result.setCode(Constant.FAIL);
				result.setMsg("查询失败");
				result.setData(failInfo);
				log.info("the message is:" + failInfo);
			}

		} catch (Exception e) {
			log.info("支付宝单笔订单交易查询异常");
			result.setCode("2");
			result.setMsg("查询失败,出现异常");
			e.printStackTrace();
		}
		return result;
	}
	// 微信退款
	@Override
	public ResultInfo<OrderDay> orderDayRefundWX(OrderDay orderDay,Double refundAmount,Operator operator) {
		ResultInfo<OrderDay> resultInfo = new ResultInfo<OrderDay>();
		String transaction_id = orderDay.getPaymentFlowNo();
		String out_trade_no = orderDay.getPartTradeFlowNo();
		Double totalFee = orderDay.getPayableAmount() * 100;// 单位：分
		Double refundFee = refundAmount * 100;
		String op_user_id = orderDay.getMemberNo();
		String nowTime = ECDateUtils.formatStringTimeWXALIPAY(new Date());
		String out_refund_no = nowTime + orderDay.getOrderNo();
		String total_fee = totalFee.toString();
		String refund_fee = refundFee.toString();
		String trade_state;
		String phoneNo = "";
		Integer trade_type = orderDay.getPaymentMethod();
		try {
			Map map = wxPayRefundRequest(transaction_id, out_trade_no, out_refund_no, total_fee, refund_fee, op_user_id,
					trade_type);
			if (map != null) {
				String return_code = (String) map.get("return_code");
				String result_code = (String) map.get("result_code");
				log.info("WXRefundRes,return_code=" + return_code + ",result_code=" + result_code);
				if (return_code.contains("SUCCESS")) {
					trade_state = (String) map.get("trade_state");
					String refundNo = (String) map.get("out_refund_no");
					String refund_id = (String) map.get("refund_id");
					if (result_code.contains("SUCCESS")) {
						/*
						 * SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
						 * REVOKED—已撤销（刷卡支付） USERPAYING--用户支付中
						 * PAYERROR--支付失败(其他原因，如银行返回失败)
						 */
						OrderDay orderDayForUpdate = new OrderDay();
						orderDayForUpdate.setOrderNo(orderDay.getOrderNo());
						orderDayForUpdate.setRefundStatus(1);
						orderDayForUpdate.setRefundTime(new Date());
						orderDayDao.update(orderDayForUpdate);
						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setMsg("退款成功");
						log.info("returned SUCCESS");

					} else {
						String err_code_des = (String) map.get("err_code_des");
						OrderDay orderDayForUpdate = new OrderDay();
						orderDayForUpdate.setOrderNo(orderDay.getOrderNo());
						orderDayForUpdate.setOrderMemo(err_code_des);
						orderDayForUpdate.setRefundStatus(2);// 退款状态2.退款失败
						orderDayForUpdate.setRefundTime(new Date());
						orderDayForUpdate.setUpdateTime(new Date());
						if (operator!=null) {
							orderDayForUpdate.setOperatorId(operator.getOperatorId());;
						}
						orderDayDao.update(orderDayForUpdate);

						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(err_code_des);
					}
				} else {
					String err_code_des = (String) map.get("err_code_des");
					OrderDay orderDayForUpdate = new OrderDay();
					orderDayForUpdate.setOrderNo(orderDay.getOrderNo());
					orderDayForUpdate.setOrderMemo(err_code_des);
					orderDayForUpdate.setRefundStatus(2);// 退款状态2.退款失败
					orderDayForUpdate.setRefundTime(new Date());
					orderDayForUpdate.setUpdateTime(new Date());
					if (operator!=null) {
						orderDayForUpdate.setOperatorId(operator.getOperatorId());;
					}
					orderDayDao.update(orderDayForUpdate);

					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(err_code_des);
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("微信退款失败！");
				}
			} else {
				String err_code_des = (String) map.get("err_code_des");
				OrderDay orderDayForUpdate = new OrderDay();
				orderDayForUpdate.setOrderNo(orderDay.getOrderNo());
				orderDayForUpdate.setOrderMemo(err_code_des);
				orderDayForUpdate.setRefundStatus(2);// 退款状态2.退款失败
				orderDayForUpdate.setRefundTime(new Date());
				orderDayForUpdate.setUpdateTime(new Date());
				if (operator!=null) {
					orderDayForUpdate.setOperatorId(operator.getOperatorId());;
				}
				orderDayDao.update(orderDayForUpdate);

				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(err_code_des);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("微信退款失败！");
			log.info("returned FAIL");
		}
		return resultInfo;
	}
	private Map wxPayRefundRequest(String transaction_id, String out_trade_no, String out_refund_no, String total_fee,
			String refund_fee, String op_user_id, Integer trade_type) throws Exception {
		String appid = WxpayConfig.appID;
		String mch_id = WxpayConfig.mchID;// 邮件里的MCHID
		
		//获取退款资金出账来源
		SysParam refundAccountType = sysParamService.getByParamKey("refund_account_type");
		//默认采用账户余额出账方式
		String refundType = (refundAccountType == null || refundAccountType.getParamValue()==null)?"2":refundAccountType.getParamValue();

		if (trade_type == 3) {
			appid = WxpayConfig.h5_appID;
			mch_id = WxpayConfig.h5_mchID;
		}

		String device_info = "";
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		String nonce_str = strReq;
		total_fee = total_fee.substring(0, total_fee.indexOf("."));
		refund_fee = refund_fee.substring(0, refund_fee.indexOf("."));
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("op_user_id", op_user_id);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		if(refundType.equals("1")){	//未结算资金退款（使用未结算资金退款）
			packageParams.put("refund_account", "REFUND_SOURCE_UNSETTLED_FUNDS");
		}else if(refundType.equals("2")){	//可用余额退款
			packageParams.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");
		}else{
			packageParams.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");
		}
		String sign = PayCommonUtil.createSign("UTF-8", packageParams);
		packageParams.put("sign", sign);
		String reuqestXml = getRequestXml(packageParams);
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		String pahtCert = getPath() + "cert/apiclient_cert.p12";
		if (trade_type == 3) {
			pahtCert = getPath() + "cert/h5/apiclient_cert.p12";
		}
		File file = new File(pahtCert);
		FileInputStream instream = new FileInputStream(file);//// 放退款证书的路径
		try {
			keyStore.load(instream, mch_id.toCharArray());
		} finally {
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {

			HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");// 退款接口

			System.out.println("----------executing request--------" + httpPost.getRequestLine());
			StringEntity reqEntity = new StringEntity(reuqestXml);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			Map map;
			try {
				HttpEntity entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: " + entity.getContentLength());
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "UTF-8"));
					String result = "";
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						System.out.println(text);
						result = result + text;
					}
					map = XMLUtil.doXMLParse(result);
					return map;
				}
				EntityUtils.consume(entity);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			httpclient.close();
		}
		return null;
	}
	private static String getRequestXml(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	private static String getPath() throws UnsupportedEncodingException {
		String configPath = GetWxOrderno.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}
/*
 * 超时未取车的，订单将取消
 * */
	@Override
	public int countCancelAmount(Query q) {
			int count = 0;
			try {
				count = orderDayDao.countCancelAmount(q).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return count;
		}

	@Override
	public List<OrderDay> getOrderDayCancelAmountList(Query q) {
		List<OrderDay> list = null;
		try {
			//直接调用Dao方法进行查询
			list = orderDayDao.getOrderDayCancelAmountList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderDay>(0) : list;
		return list; 
	}
}
