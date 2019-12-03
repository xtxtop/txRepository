package cn.com.shopec.core.finace.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.pay.aliPay.AlipayNotify;
import cn.com.shopec.common.pay.aliPay.AlipaySubmit;
import cn.com.shopec.common.pay.wxPay.GetWxOrderno;
import cn.com.shopec.common.pay.wxPay.PayCommonUtil;
import cn.com.shopec.common.pay.wxPay.TenpayUtil;
import cn.com.shopec.common.pay.wxPay.WxpayConfig;
import cn.com.shopec.common.pay.wxPay.XMLUtil;
import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECIPUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.dao.DepositRefundDao;
import cn.com.shopec.core.finace.dao.PaymentRecordDao;
import cn.com.shopec.core.finace.dao.TransToAccountDao;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.model.TransToAccount;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 押金退款表 服务实现类
 */
@Service
public class DepositRefundServiceImpl implements DepositRefundService {

	private static final Log log = LogFactory.getLog(DepositRefundServiceImpl.class);

	@Resource
	private DepositRefundDao depositRefundDao;

	@Resource
	private DepositOrderDao depositOrderDao;

	@Resource
	private PaymentRecordDao paymentRecordDao;

	@Resource
	private TransToAccountDao transToAccountDao;
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;
	@Resource
	private MemberDao memberDao;

	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 根据查询条件，查询并返回DepositRefund的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DepositRefund> getDepositRefundList(Query q) {
		List<DepositRefund> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = depositRefundDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositRefund>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回DepositRefund的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<DepositRefund> getDepositRefundPagedList(Query q) {
		PageFinder<DepositRefund> page = new PageFinder<DepositRefund>();

		List<DepositRefund> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = depositRefundDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = depositRefundDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositRefund>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个DepositRefund对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public DepositRefund getDepositRefund(String id) {
		DepositRefund obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = depositRefundDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组DepositRefund对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DepositRefund> getDepositRefundByIds(String[] ids) {
		List<DepositRefund> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = depositRefundDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositRefund>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的DepositRefund记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositRefund> delDepositRefund(String id, Operator operator) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = depositRefundDao.delete(id);
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
	 * 新增一条DepositRefund记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param depositRefund
	 *            新增的DepositRefund数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositRefund> addDepositRefund(DepositRefund depositRefund, Operator operator) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();

		if (depositRefund == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " depositRefund = " + depositRefund);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (depositRefund.getRefundNo() == null) {
					depositRefund.setRefundNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					depositRefund.setOperatorType(operator.getOperatorType());
					depositRefund.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				depositRefund.setCreateTime(now);
				// 申请时间
				depositRefund.setApplyTime(now);
				depositRefund.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(depositRefund);

				// 调用Dao执行插入操作
				depositRefundDao.add(depositRefund);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(depositRefund);
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
	 * 根据主键，更新一条DepositRefund记录
	 * 
	 * @param depositRefund
	 *            更新的DepositRefund数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositRefund> updateDepositRefund(DepositRefund depositRefund, Operator operator) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();

		if (depositRefund == null || depositRefund.getRefundNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " depositRefund = " + depositRefund);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					depositRefund.setOperatorType(operator.getOperatorType());
					depositRefund.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				depositRefund.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = depositRefundDao.update(depositRefund);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(depositRefund);
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
	 * 
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}

	/**
	 * 为DepositRefund对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(DepositRefund obj) {
		if (obj != null) {
			if (obj.getRefundStatus() == null) {
				obj.setRefundStatus(0);
			}
			if (obj.getCencorStatus() == null) {
				obj.setCencorStatus(0);
			}
		}
	}

	/**
	 * 支付宝退款请求方法
	 * 
	 * @param depositRefundNo
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultInfo<Object> toDepositRefundPay(String depositRefundNo, HttpServletResponse response, String memo)
			throws Exception {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		DepositRefund dr = depositRefundDao.get(depositRefundNo);
		// 根据退款记录查询押金支付记录中的支付宝交易号
		DepositOrder dOrder = depositOrderDao.get(dr.getDepositOrderNo());
		if (dr.getRefundMethod().equals(1)) {// 支付宝退款

			// 批次号，必填，格式：当天日期[8位]+序列号[3至24位]，如：201603081000001
			String nowTime = ECDateUtils.formatStringTimeWXALIPAY(new Date());
			String batch_no = nowTime + dr.getRefundNo();

			// 退款笔数，必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
			String batch_num = 1 + "";

			// 退款详细数据，必填，格式（支付宝交易号^退款金额^备注），多笔请用#隔开
			String detail_data = dOrder.getPaymentFlowNo() + "^" + dr.getRefundAmount() + "^" + memo;

			// 把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", AlipayConfig.refundService);
			sParaTemp.put("partner", AlipayConfig.partner);
			sParaTemp.put("notify_url", AlipayConfig.refund_notify_url);
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			System.err.println(AlipayConfig.refund_notify_url);
			sParaTemp.put("seller_user_id", AlipayConfig.seller_user_id);
			sParaTemp.put("refund_date", AlipayConfig.refund_date);
			sParaTemp.put("batch_no", batch_no);
			sParaTemp.put("batch_num", batch_num);
			sParaTemp.put("detail_data", detail_data);

			// 建立请求
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
			System.err.println(sHtmlText);
			resultInfo.setData(sHtmlText);
		}
		return resultInfo;
	}

	/**
	 * 支付宝退款结果异步回调请求方法
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	public void depositRefundAlipayUpdate(HttpServletRequest request, HttpServletResponse response,Operator operator) throws Exception {
		log.info("退款回调：serverAlirefund");
		String ip = request.getHeader("x-forwarded-for");// 用户公网ip
		log.info(ip);
		if (null == ip)
			ip = request.getRemoteAddr();
		log.info(ip);
		String phoneNo = "";
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}

		log.info(params.toString());
		log.info("Alirefund return GET data end...");
		// 批次号
		String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"), "UTF-8");
		log.info("alirefund batch_no:" + batch_no);

		// 批量退款数据中转账成功的笔数
		String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"), "UTF-8");
		log.info("alirefund success_num:" + success_num);

		// 批量退款数据中的详细信息
		String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"), "UTF-8");
		log.info("alirefund result_details:" + result_details);

		boolean verify_result = AlipayNotify.verifyRefund(params);
		log.info("alirefund notify params : " + params.toString());
		log.info("alirefund verify_result:" + verify_result);

		String refundNo = batch_no.substring(8);
		DepositRefund dr = depositRefundDao.get(refundNo);
		if (verify_result) {// 验证成功
			log.info("alirefund verify pass go on");
			log.info(result_details);
			// 判断处理结果信息，如果处理成功则更新表记录，否则返回失败通知
			if (result_details.indexOf("SUCCESS") > -1 && success_num.equals("1")) {
				// 对保证金退款记录表进行修改，对保证金支付表进行修改
				if (dr != null) {
					dr.setRefundFlowNo(batch_no);// 退款流水号
					dr.setRefundTime(new Date());
					dr.setRefundStatus(1);// 退款状态1.已退款
					dr.setUpdateTime(dr.getRefundTime());
					if (operator!=null) {
						dr.setOperatorId(operator.getOperatorId());
					}
					depositRefundDao.update(dr);
					DepositOrder dOrder = depositOrderDao.get(dr.getDepositOrderNo());
					if (dOrder != null) {
						dOrder.setRemainAmount(ECNumberUtils
								.roundDoubleWithScale(dOrder.getRemainAmount() - dOrder.getFrozenAmount(), 2));
						if (dOrder.getRefundAmount() == null) {
							dOrder.setRefundAmount(0d);
						}
						dOrder.setRefundAmount(ECNumberUtils
								.roundDoubleWithScale(dOrder.getRefundAmount() + dOrder.getFrozenAmount(), 2));
						;
						dOrder.setFrozenAmount(0d);
						dOrder.setUpdateTime(new Date());
						depositOrderDao.update(dOrder);
						Member member = memberDao.get(dOrder.getMemberNo());
						if (member != null) {
							phoneNo = member.getMobilePhone();
						}
					}
				}
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				PrintWriter out = response.getWriter();
				out.println("success");
				out.flush();
				out.close();
				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				boolean sate = false;
				String censorStatus = "成功";
				try {

					// 聚合
					// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
					// validCode, tplId);
					// 聚通达,浪驰
					sate = sendMsgCommonInterfaceService.sendMsgPost(phoneNo, censorStatus, MsgConstant.MEMBERDEPOSIT);
					// 叮咚云
					// sate =
					// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
					// validCode, "","1"); //1代表 验证码类短信
					System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				// 退款失败，更新状态信息
				if (dr != null) {
					dr.setRefundStatus(2); // 退款状态 2.退款失败
					dr.setUpdateTime(new Date());
					dr.setRefundFailInfo(result_details);
					depositRefundDao.update(dr);
				}
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				PrintWriter out = response.getWriter();
				out.println("success");
				out.flush();
				out.close();
				boolean sate = false;

			}
			//////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			if (dr != null) {
				dr.setRefundStatus(2);// 退款状态2.退款失败
				dr.setUpdateTime(new Date());
				dr.setRefundFailInfo("验证失败，" + result_details);
				depositRefundDao.update(dr);
			}
			log.info("alirefund verify fail");
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
			out.println("success");
			out.flush();
			out.close();

		}

	}

	/*
	 * 
	 * 微信转账操作
	 */

	@Override
	public ResultInfo<DepositRefund> payRefundWXZM(DepositRefund dRefund, DepositOrder dOrder,
			PaymentRecord paymentRecord, HttpServletRequest request) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();

		// 公众账号appid
		String mch_appid = WxpayConfig.appID;
		// 微信支付分配的商户号
		String mchid = WxpayConfig.mchID;
		
		Integer trade_type = dOrder.getPaymentMethod();
		
		if(trade_type==4){
			mch_appid = WxpayConfig.h5_appID;
			mchid = WxpayConfig.h5_mchID;
		}
		
		
		
		// 随机字符串
		String nonce_str = TenpayUtil.buildRandom(4) + "";
		// 商户订单号
		String partner_trade_no = "";
		// 商户appid下，某用户的openid
		String openid = paymentRecord.getBuyerId();
		// NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）
		// OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
		String check_name = "NO_CHECK";
		// 企业付款金额，单位为分企业付款金额，单位为分
		Double amount = dOrder.getDepositAmount() * 100;// 单位：分
		// 企业付款操作说明信息。必填
		String desc = "weixintuikuan";
		// 企业付款操作说明信息。必填。
		String spbill_create_ip = ECIPUtils.getClientIp(request);

		String amounts = amount.toString();

		
//		if (dOrder.getPartTradeFlowNo() != null) {
//			partner_trade_no = dOrder.getPartTradeFlowNo();
//		} else {
//			partner_trade_no = dOrder.getDepositOrderNo();
//		}
		
		partner_trade_no=dRefund.getRefundNo();
		
		
		
		try {
			Map map = wxPayRefundRequestWXZM(mch_appid, mchid, nonce_str, partner_trade_no, openid, check_name, amounts,
					desc, spbill_create_ip,trade_type);
			if (map != null) {
				String return_code = (String) map.get("return_code");
				String result_code = (String) map.get("result_code");
				log.info("WXRefundRes,return_code=" + return_code + ",result_code=" + result_code);
				if (return_code.contains("SUCCESS")) {
					// trade_state=(String) map.get("trade_state");
					String refundNo = (String) map.get("out_refund_no");
					String refund_id = (String) map.get("refund_id");
					if (result_code.contains("SUCCESS")) {
						/*
						 * SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
						 * REVOKED—已撤销（刷卡支付） USERPAYING--用户支付中
						 * PAYERROR--支付失败(其他原因，如银行返回失败)
						 */

						// String refundNo=out_refund_no.substring(8);
						DepositRefund dr = depositRefundDao.get(refundNo.substring(8));
						if (dr != null) {
							dr.setRefundFlowNo(refund_id);// 退款流水号
							dr.setRefundTime(new Date());
							dr.setRefundStatus(1);// 退款状态1.已退款
							dr.setUpdateTime(dr.getRefundTime());
							depositRefundDao.update(dr);
							DepositOrder dO = depositOrderDao.get(dr.getDepositOrderNo());
							if (dO != null) {
								dO.setRemainAmount(ECNumberUtils
										.roundDoubleWithScale(dO.getRemainAmount() - dO.getFrozenAmount(), 2));
								if (dO.getRefundAmount() == null) {
									dO.setRefundAmount(0d);
								}
								dO.setRefundAmount(ECNumberUtils
										.roundDoubleWithScale(dO.getRefundAmount() + dO.getFrozenAmount(), 2));
								;
								dO.setFrozenAmount(0d);
								dO.setUpdateTime(new Date());
								depositOrderDao.update(dO);
								
								//押金支付成功后，设置会员表的已支付押金金额为0
								Member member = memberDao.get(dO.getMemberNo());
								if (member!=null) {
									Double payableDepositAmount = 0.0;
									Deposit deposit = depositOrderDao.getDepositByMemberNo(member.getMemberNo());
									if (deposit!=null) {
										if (deposit.getAvailableAmount().compareTo(0.0)>0) {
											payableDepositAmount = deposit.getAvailableAmount();
										}
									}
									Member memberForUpdate = new Member();
									memberForUpdate.setMemberNo(member.getMemberNo());
									memberForUpdate.setPayableDepositAmount(payableDepositAmount);
									memberDao.update(memberForUpdate);
								}
							}
						}
						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setData(dr);
					} else {
						String err_code_des = (String) map.get("err_code_des");
						if(refundNo != null){
							DepositRefund dr = depositRefundDao.get(refundNo.substring(8));
							if (dr != null) {
								dr.setRefundStatus(2);// 退款状态2.退款失败
								dr.setUpdateTime(new Date());
								depositRefundDao.update(dr);
							}
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg(err_code_des);
						}else{
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("微信退款失败！");
						}
						
						
					}
				} else {
					if (dRefund != null) {
						dRefund.setRefundStatus(2);// 退款状态2.退款失败
						dRefund.setUpdateTime(new Date());
						depositRefundDao.update(dRefund);
					}
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("微信退款失败！");
				}
			} else {
				if (dRefund != null) {
					dRefund.setRefundStatus(2);// 退款状态2.退款失败
					dRefund.setUpdateTime(new Date());
					depositRefundDao.update(dRefund);
				}
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("微信退款失败！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (dRefund != null) {
				dRefund.setRefundStatus(2);// 退款状态2.退款失败
				dRefund.setUpdateTime(new Date());
				depositRefundDao.update(dRefund);
			}
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("微信退款失败！");
		}
		return resultInfo;
	}

	public Map wxPayRefundRequestWXZM(String mch_appid, String mchid, String nonce_str, String partner_trade_no,
			String openid, String check_name, String amounts, String desc, String spbill_create_ip,Integer trade_type) throws Exception {
		amounts = amounts.substring(0, amounts.indexOf("."));
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("mch_appid", mch_appid);
		packageParams.put("mchid", mchid);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("partner_trade_no", partner_trade_no);
		packageParams.put("openid", openid);
		packageParams.put("check_name", check_name);
		packageParams.put("amount", amounts);
		packageParams.put("desc", desc);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		
		// 签名
		String sign = PayCommonUtil.createSign("UTF-8", packageParams);
		packageParams.put("sign", sign);

		String reuqestXml = getRequestXml(packageParams);
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		String pahtCert = getPath() + "cert/apiclient_cert.p12";
		if(trade_type==4){
			pahtCert = getPath() + "cert/h5/apiclient_cert.p12";
		}
		File file = new File(pahtCert);
		FileInputStream instream = new FileInputStream(file);//// 放退款证书的路径
		try {
			keyStore.load(instream, mchid.toCharArray());
		} finally {
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchid.toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {

			HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");// 转账接口

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
	// 微信退款
	@Override
	public ResultInfo<DepositRefund> payRefundWX(DepositRefund dRefund, DepositOrder dOrder,Operator operator) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();
		String transaction_id = dOrder.getPaymentFlowNo();
		// String out_trade_no = dOrder.getDepositOrderNo();
		String out_trade_no = dOrder.getPartTradeFlowNo();
		Double totalFee = dOrder.getDepositAmount() * 100;// 单位：分
		Double refundFee = dOrder.getFrozenAmount() * 100;
		String op_user_id = dOrder.getMemberNo();
		String nowTime = ECDateUtils.formatStringTimeWXALIPAY(new Date());
		String out_refund_no = nowTime + dRefund.getRefundNo();
		String total_fee = totalFee.toString();
		String refund_fee = refundFee.toString();
		String trade_state;
		String phoneNo = "";
		Integer trade_type = dOrder.getPaymentMethod();
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

						// String refundNo=out_refund_no.substring(8);
						DepositRefund dr = depositRefundDao.get(refundNo.substring(8));
						if (dr != null) {
							dr.setRefundFlowNo(refund_id);// 退款流水号
							dr.setRefundTime(new Date());
							dr.setRefundStatus(1);// 退款状态1.已退款
							dr.setUpdateTime(dr.getRefundTime());
							if (operator!=null) {
								dr.setRefundOperatorId(operator.getOperatorId());
							}
							depositRefundDao.update(dr);
							DepositOrder dO = depositOrderDao.get(dr.getDepositOrderNo());
							if (dO != null) {
								dO.setRemainAmount(ECNumberUtils
										.roundDoubleWithScale(dO.getRemainAmount() - dO.getFrozenAmount(), 2));
								if (dO.getRefundAmount() == null) {
									dO.setRefundAmount(0d);
								}
								dO.setRefundAmount(ECNumberUtils
										.roundDoubleWithScale(dO.getRefundAmount() + dO.getFrozenAmount(), 2));
								;
								dO.setFrozenAmount(0d);
								dO.setUpdateTime(new Date());
								depositOrderDao.update(dO);
								
								//押金支付成功后，设置会员表的已支付押金金额为0
								Member member = memberDao.get(dO.getMemberNo());
								if (member!=null) {
									Double payableDepositAmount = 0.0;
									Deposit deposit = depositOrderDao.getDepositByMemberNo(member.getMemberNo());
									if (deposit!=null) {
										if (deposit.getAvailableAmount().compareTo(0.0)>0) {
											payableDepositAmount = deposit.getAvailableAmount();
										}
									}
									Member memberForUpdate = new Member();
									memberForUpdate.setMemberNo(member.getMemberNo());
									memberForUpdate.setPayableDepositAmount(payableDepositAmount);
									memberDao.update(memberForUpdate);
								}
							}
						}
						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setData(dr);
						log.info("returned SUCCESS");
						boolean sate = false;
						String censorStatus = "成功";
						try {

							// 聚合
							// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
							// validCode, tplId);
							// 聚通达,浪驰
							sate = sendMsgCommonInterfaceService.sendMsgPost(phoneNo, censorStatus,
									MsgConstant.MEMBERDEPOSIT);
							// 叮咚云
							// sate =
							// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
							// validCode, "","1"); //1代表 验证码类短信
							System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						String err_code_des = (String) map.get("err_code_des");
						if (refundNo != null) {
							DepositRefund dr = depositRefundDao.get(refundNo.substring(8));
							if (dr != null) {
								dr.setRefundFailInfo(err_code_des);
								dr.setRefundStatus(2);// 退款状态2.退款失败
								dr.setUpdateTime(new Date());
								if (operator!=null) {
									dr.setRefundOperatorId(operator.getOperatorId());
								}
								depositRefundDao.update(dr);
							}
						}
						if (dRefund != null) {
							dRefund.setRefundFailInfo(err_code_des);
							dRefund.setRefundStatus(2);// 退款状态2.退款失败
							dRefund.setUpdateTime(new Date());
							if (operator!=null) {
								dRefund.setRefundOperatorId(operator.getOperatorId());
							}
							depositRefundDao.update(dRefund);
						}

						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(err_code_des);
					}
				} else {
					if (dRefund != null) {
						String err_code_des = (String) map.get("err_code_des");
						dRefund.setRefundFailInfo(err_code_des);
						dRefund.setRefundStatus(2);// 退款状态2.退款失败
						dRefund.setUpdateTime(new Date());
						if (operator!=null) {
							dRefund.setRefundOperatorId(operator.getOperatorId());
						}
						depositRefundDao.update(dRefund);
					}
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("微信退款失败！");
				}
			} else {
				if (dRefund != null) {
					String err_code_des = (String) map.get("err_code_des");
					dRefund.setRefundFailInfo(err_code_des);
					dRefund.setRefundStatus(2);// 退款状态2.退款失败
					dRefund.setUpdateTime(new Date());
					if (operator!=null) {
						dRefund.setRefundOperatorId(operator.getOperatorId());
					}
					depositRefundDao.update(dRefund);
				}
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("微信退款失败！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (dRefund != null) {
				dRefund.setRefundStatus(2);// 退款状态2.退款失败
				dRefund.setUpdateTime(new Date());
				if (operator!=null) {
					dRefund.setRefundOperatorId(operator.getOperatorId());
				}
				depositRefundDao.update(dRefund);
			}
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("微信退款失败！");
			log.info("returned FAIL");
		}
		return resultInfo;
	}

	public Map wxPayRefundRequest(String transaction_id, String out_trade_no, String out_refund_no, String total_fee,
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

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String getRequestXml(SortedMap<String, String> parameters) {
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

	public static String getPath() throws UnsupportedEncodingException {
		String configPath = GetWxOrderno.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

	@Override
	public ResultInfo<DepositRefund> depositRefundPayMemo(DepositRefund dRefund,Operator operator) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();
		if (dRefund != null) {
			dRefund.setRefundFlowNo("XX" + dRefund.getRefundNo());// 退款流水号
			dRefund.setRefundTime(new Date());
			dRefund.setRefundStatus(1);// 退款状态1.已退款
			dRefund.setRefundMethod(4);
			dRefund.setUpdateTime(new Date());
			if (operator!=null) {
				dRefund.setOperatorId(operator.getOperatorId());
			}
			depositRefundDao.update(dRefund);
			DepositOrder dO = depositOrderDao.get(dRefund.getDepositOrderNo());
			if (dO != null) {
				dO.setRemainAmount(dO.getRemainAmount() - dO.getFrozenAmount());
				dO.setFrozenAmount(0d);
				dO.setUpdateTime(new Date());
				depositOrderDao.update(dO);
			}
			//押金支付成功后，设置会员表的已支付押金金额为0
			Member member = memberDao.get(dO.getMemberNo());
			if (member!=null) {
				Double payableDepositAmount = 0.0;
				Deposit deposit = depositOrderDao.getDepositByMemberNo(member.getMemberNo());
				if (deposit!=null) {
					if (deposit.getAvailableAmount().compareTo(0.0)>0) {
						payableDepositAmount = deposit.getAvailableAmount();
					}
				}
				Member memberForUpdate = new Member();
				memberForUpdate.setMemberNo(member.getMemberNo());
				memberForUpdate.setPayableDepositAmount(payableDepositAmount);
				memberDao.update(memberForUpdate);
			}
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(dRefund);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;

	}

	/**
	 * 根据押金单号查询当前订单是否支持退款
	 */
	@Override
	public ResultInfo<String> signOrder(String depositRefundNo, Map<String, String> tradeInfo) {

		ResultInfo<String> resultInfo = new ResultInfo<String>();
		// 获取当前押金退款申请对象
		DepositRefund current = this.getDepositRefund(depositRefundNo);
		if (null != current) {
			// 获取当前押金支付对象
			DepositOrder order = depositOrderDao.get(current.getDepositOrderNo());
			if (null != order) {
				// 根据支付单号与退款申请单号请求第三方平台（支付宝或微信）核验订单有效期
				if (order.getPaymentMethod() == 1) { // 支付方式（1、支付宝、2、微信）
					resultInfo = checkFromAlipay(order.getPaymentFlowNo(), order.getDepositOrderNo(), tradeInfo); // 支付宝交易流水号，本系统唯一支付订单号
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("订单数据异常，无法查询");
				}
			}else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("订单数据异常，无法查询");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("押金退款申请信息数据异常，无法查询");
		}
		return resultInfo;
	}

	/**
	 * 支付宝单笔交易状态查询
	 * 
	 * @param paymentFlowNo
	 * @param depositOrderNo
	 */
	private ResultInfo<String> checkFromAlipay(String paymentFlowNo, String depositOrderNo,
			Map<String, String> tradeInfo) {
		ResultInfo<String> result = new ResultInfo<String>();
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("trade_no", paymentFlowNo);
		sParaTemp.put("out_trade_no", depositOrderNo);
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

	/**
	 * 支付宝转账方法
	 */
	@Override
	public ResultInfo<TransToAccount> transAccount(String depositRefundNo, String remark, String payeeId,
			String payeeAccount, Operator operator) {

		ResultInfo<TransToAccount> resultInfo = new ResultInfo<TransToAccount>();

		// 获取当前押金退款申请对象
		DepositRefund current = this.getDepositRefund(depositRefundNo);
		if (null != current && current.getDepositOrderNo() != null && current.getRefundStatus() == 0) {
			// 获取当前押金支付对象
			PaymentRecord record = paymentRecordDao.getByDRefundNo(depositRefundNo);
			if (null != record) {
				Double refundAmount = current.getRefundAmount(); // 金额
				payeeId = payeeId == null ? record.getBuyerId() : payeeId; // 收款方支付宝账户(userId)
				payeeAccount = payeeAccount == null ? record.getBuyerAccount() : payeeAccount; // 收款方支付宝账户(邮箱、手机）
				String accountNo = (payeeId != null && !payeeId.equals("")) ? payeeId : payeeAccount; // userId优先，否则为账号

				// String nowTime = ECDateUtils.formatStringTime(new Date());
				String appId = AlipayConfig.appId;
				String privateKey = AlipayConfig.rsa_private;
				String publicKey = AlipayConfig.rsa_public;
				String transId = this.generatePK();
				String outBizNo = "TKZZ" + current.getDepositOrderNo(); // 支付宝平台需要的商家转账订单号，用TKZZ+押金订单号
				String payeeType = (payeeId != null && !payeeId.equals("")) ? "ALIPAY_USERID" : "ALIPAY_LOGONID";
				
				String r="";
				SysParam spmAlipay= sysParamService.getByParamKey("aliPaySignType");
				if(spmAlipay != null && "2".equals(spmAlipay.getParamValue())){
						r="RSA2";
				}else{
					r="RSA";
				}
				AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId,
						privateKey, "json", "UTF-8", publicKey, r);
				AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
				request.setBizContent("{" + "\"out_biz_no\":\"" + outBizNo + "\"," + "\"payee_type\":\"" + payeeType
						+ "\"," + "\"payee_account\":\"" + accountNo + "\"," + "\"amount\":\"" + refundAmount + "\","
						+ "\"remark\":\"" + remark + "\"" + "}");
				try {
					AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request); // 转账
					if (response.isSuccess()) { // 调用转账接口成功
						TransToAccount account = new TransToAccount();
						account.setOperatorId(operator.getOperatorId());
						account.setTransNo(transId);
						account.setDepositOrderNo(current.getDepositOrderNo());
						account.setThirdTransOrderId(response.getOrderId());
						account.setRefundNo(depositRefundNo);
						account.setTransAmount(refundAmount);
						account.setTransTime(ECDateUtils.parseDate(response.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
						account.setTransType(1); // 1 支付宝 2 微信
						account.setTransInfo(response.getBody());
						if ("10000".equals(response.getCode())) {
							// 转账成功 进行业务逻辑处理
							account.setTransResult(1); // 1 成功 0 失败

							// 押金退款申请单与押金订单状态修改
							DepositRefund drForUpdate = new DepositRefund();
							drForUpdate.setRefundNo(current.getRefundNo());
							drForUpdate.setRefundFlowNo(transId);// 退款流水号，此处为转账单号
							drForUpdate
									.setRefundTime(ECDateUtils.parseDate(response.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
							drForUpdate.setRefundStatus(1);// 退款状态 1.已退款
							drForUpdate.setUpdateTime(drForUpdate.getRefundTime());
							drForUpdate.setRefundOperatorId(operator.getOperatorId());
							depositRefundDao.update(drForUpdate);
							DepositOrder dOrder = depositOrderDao.get(current.getDepositOrderNo());
							if (dOrder != null) {
								dOrder.setRemainAmount(ECNumberUtils
										.roundDoubleWithScale(dOrder.getRemainAmount() - dOrder.getFrozenAmount(), 2));
								if (dOrder.getRefundAmount() == null) {
									dOrder.setRefundAmount(0d);
								}
								dOrder.setRefundAmount(ECNumberUtils
										.roundDoubleWithScale(dOrder.getRefundAmount() + dOrder.getFrozenAmount(), 2));
								;
								dOrder.setFrozenAmount(0d);
								dOrder.setUpdateTime(new Date());
								depositOrderDao.update(dOrder);
								
								//押金支付成功后，设置会员表的已支付押金金额为0
								Member member = memberDao.get(dOrder.getMemberNo());
								if (member!=null) {
									Double payableDepositAmount = 0.0;
									Deposit deposit = depositOrderDao.getDepositByMemberNo(member.getMemberNo());
									if (deposit!=null) {
										if (deposit.getAvailableAmount().compareTo(0.0)>0) {
											payableDepositAmount = deposit.getAvailableAmount();
										}
									}
									Member memberForUpdate = new Member();
									memberForUpdate.setMemberNo(member.getMemberNo());
									memberForUpdate.setPayableDepositAmount(payableDepositAmount);
									memberDao.update(memberForUpdate);
								}
							}

							resultInfo.setCode(Constant.SECCUESS);
							resultInfo.setMsg("转账成功");

						} else {
							// 转账失败 记录错误信息
							account.setTransResult(0); // 1 成功 0 失败
							// 调取查询转账账单接口
							AlipayFundTransOrderQueryRequest getTransInfo = new AlipayFundTransOrderQueryRequest();
							request.setBizContent("{" + "\"out_biz_no\":\"" + outBizNo + "\"" + "}");
							try {
								AlipayFundTransOrderQueryResponse getInfoResponse = alipayClient.execute(getTransInfo);
								if (getInfoResponse.isSuccess()) {
									if ("10000".equals(getInfoResponse.getCode())) {
										current.setRefundFailInfo(
												getInfoResponse.getErrorCode() + getInfoResponse.getFailReason());
									}
								} else {
									log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"
											+ getInfoResponse.getBody());
								}
							} catch (AlipayApiException e) {
								log.error(e.getMessage(), e);
							}
							// 调取查询转账订单状态接口 获取转账情况
							// 押金退款失败，更新状态信息
							DepositRefund drForUpdate = new DepositRefund();
							drForUpdate.setRefundNo(current.getRefundNo());
							drForUpdate.setRefundStatus(2); // 退款状态 2.退款失败
							drForUpdate.setUpdateTime(new Date());
							drForUpdate.setRefundOperatorId(operator.getOperatorId());
							drForUpdate.setRefundFailInfo(current.getRefundFailInfo());
							// 此处需要加转账失败原因到字段，尚未开发
							depositRefundDao.update(drForUpdate);
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("转账失败");

							log.error("trans fail, transNo is:" + transId + "the out_biz_no is " + outBizNo
									+ "the error code is:" + response.getCode() + ";the error info is :"
									+ response.getSubCode());
						}

						transToAccountDao.add(account);
						resultInfo.setData(account);
					} else {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("转账请求失败，失败原因为" + response.getBody());
					}

					log.info(response.getMsg());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("转账失败,数据异常--押金支付订单");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("转账失败,数据异常--押金退款申请订单");
		}
		return resultInfo;
	}

	@Override
	public Integer getTodoIndexValue() {
		return depositRefundDao.countDepositRefundCensorStatus();
	}

	@Override
	public ResultInfo<DepositRefund> toDepositRefundAvoidPwdPay(String depositRefundNo,Operator operator,String remark){

		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();

		if (null != depositRefundNo && !"".equals(depositRefundNo)) {
			DepositRefund depositRefund = depositRefundDao.get(depositRefundNo);
			//获取当前押金支付对象
			DepositOrder depositOrder = depositOrderDao.get(depositRefund.getDepositOrderNo());
			if (null != depositOrder) {
				Double refundAmount = depositRefund.getRefundAmount(); // 金额
				String appId = AlipayConfig.appId;
				String privateKey = AlipayConfig.rsa_private;
				String publicKey = AlipayConfig.rsa_public;
				String outBizNo = depositOrder.getPartTradeFlowNo(); // 订单支付时传入的商户订单号
				String paymentFlowNo = depositOrder.getPaymentFlowNo();//支付宝交易号

				String r="";
				SysParam spmAlipay= sysParamService.getByParamKey("aliPaySignType");
				if(spmAlipay != null && "2".equals(spmAlipay.getParamValue())){
						r="RSA2";
				}else{
					r="RSA";
				}
				AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId,
						privateKey, "json", "UTF-8", publicKey, r);
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
							DepositRefund refundForUpdate = new DepositRefund();
							refundForUpdate.setRefundNo(depositRefund.getRefundNo());
							refundForUpdate.setRefundTime(new Date());
							refundForUpdate.setRefundStatus(1);// 退款状态1.已退款
							refundForUpdate.setUpdateTime(new Date());
							if(depositRefund!=null){
								refundForUpdate.setRefundFlowNo("XS" + depositRefund.getRefundNo());
							}
							if (operator!=null) {
								refundForUpdate.setRefundOperatorId(operator.getOperatorId());;
							}
							refundForUpdate.setRefundAmount(refundAmount);
							refundForUpdate.setRefundMemo("后台人工退款");
							depositRefundDao.update(refundForUpdate);
							log.info("押金退款表表更新"+depositRefund.getRefundNo());
							
							DepositOrder depositOrderForUpdate = new DepositOrder();
							depositOrderForUpdate.setDepositOrderNo(depositOrder.getDepositOrderNo());
							depositOrderForUpdate.setRemainAmount(0d);
							depositOrderForUpdate.setRefundAmount(depositRefund.getRefundAmount());
							depositOrderForUpdate.setFrozenAmount(0d);
							depositOrderForUpdate.setIsAvailable(0);
							depositOrderForUpdate.setUpdateTime(new Date());
							depositOrderDao.update(depositOrderForUpdate);
							log.info("押金支付表更新"+depositOrder.getDepositOrderNo());
							//押金支付成功后，设置会员表的已支付押金金额为0
							Member member = memberDao.get(depositOrder.getMemberNo());
							if (member!=null) {
								Double payableDepositAmount = 0.0;
								Deposit deposit = depositOrderDao.getDepositByMemberNo(member.getMemberNo());
								if (deposit!=null) {
									if (deposit.getAvailableAmount().compareTo(0.0)>0) {
										payableDepositAmount = deposit.getAvailableAmount();
									}
								}
								Member memberForUpdate = new Member();
								memberForUpdate.setMemberNo(member.getMemberNo());
								memberForUpdate.setPayableDepositAmount(payableDepositAmount);
								memberDao.update(memberForUpdate);
							}
							resultInfo.setData(depositRefund);
							resultInfo.setCode(Constant.SECCUESS);
							resultInfo.setMsg("退款成功");
						} else {
							// 退款失败
							// 调取查询支付宝交易退款接口
							AlipayTradeFastpayRefundQueryRequest getTransInfo = new AlipayTradeFastpayRefundQueryRequest();
							request.setBizContent("{" + "\"out_trade_no\":\"" + outBizNo + "\"" + "}");
							try {
								AlipayTradeFastpayRefundQueryResponse getInfoResponse = alipayClient.execute(getTransInfo);
								if (getInfoResponse.isSuccess()) {
									if (!"10000".equals(getInfoResponse.getCode())) {
										depositRefund.setRefundFailInfo(getInfoResponse.getRefundReason());
										log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"+ getInfoResponse.getRefundReason());
									}
								} else {
									depositRefund.setRefundFailInfo(getInfoResponse.getRefundReason());
									log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"+ getInfoResponse.getRefundReason());
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
							depositRefund.setRefundStatus(2);// 退款状态2.退款失败
							depositRefund.setUpdateTime(new Date());
							if (operator!=null) {
								depositRefund.setRefundOperatorId(operator.getOperatorId());;
							}
							depositRefundDao.update(depositRefund);
							
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setData(depositRefund);
							resultInfo.setMsg("退款失败");
						}
					} else {
						depositRefund.setRefundFailInfo(response.getMsg());
						depositRefund.setRefundStatus(2);// 退款状态2.退款失败
						depositRefund.setUpdateTime(new Date());
						if (operator!=null) {
							depositRefund.setRefundOperatorId(operator.getOperatorId());;
						}
						depositRefundDao.update(depositRefund);
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setData(depositRefund);
						resultInfo.setMsg("退款请求失败，失败原因为:" + response.getMsg());
					}

					log.info(response.getMsg());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else {
				depositRefund.setRefundFailInfo("退款失败,押金支付订单数据异常");
				depositRefund.setRefundStatus(2);// 退款状态2.退款失败
				depositRefund.setUpdateTime(new Date());
				if (operator!=null) {
					depositRefund.setRefundOperatorId(operator.getOperatorId());;
				}
				depositRefundDao.update(depositRefund);
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setData(depositRefund);
				resultInfo.setMsg("退款失败,数据异常--押金支付订单");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("退款失败,退款编号不存在");
		}
		return resultInfo;
	
	}

}
