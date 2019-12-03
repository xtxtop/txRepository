package cn.com.shopec.core.pay.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.unoin.DemoBase;
import cn.com.shopec.common.pay.unoin.sdk.AcpService;
import cn.com.shopec.common.pay.unoin.sdk.LogUtil;
import cn.com.shopec.common.pay.unoin.sdk.SDKConfig;
import cn.com.shopec.common.pay.unoin.sdk.SDKConstants;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.pay.UnionPayService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 银联手机控件服务实现类
 * 
 * @author 许瑞伟
 *
 */
@Service
public class UnionPayServiceImpl implements UnionPayService {

	private static final Log log = LogFactory.getLog(UnionPayServiceImpl.class);

	@Resource
	private DepositOrderService depositOrderService;

	@Resource
	private MemberService memberService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private PaymentRecordService paymentRecordService;

	@Override
	public ResultInfo<String> getPreTN(DepositOrder order) {
		ResultInfo<String> result = new ResultInfo<>();
		String orderNo = order.getDepositOrderNo();
		if (ECStringUtils.isBlank(orderNo)) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号不能为空");
			return result;
		}
		Double Amount = order.getPayableAmount() * 100;
		String txnAmt = Amount.intValue()+"";
		if (ECStringUtils.isBlank(txnAmt)) {
			result.setCode(Constant.FAIL);
			result.setMsg("金额不能为空");
			return result;
		}
		if (ECStringUtils.isBlank(SDKConfig.getConfig().getMerId())) {
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		}
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String merId = SDKConfig.getConfig().getMerId();

		Map<String, String> contentData = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", DemoBase.version); // 版本号 全渠道默认值
		contentData.put("encoding", DemoBase.encoding); // 字符集编码
														// 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); // 签名方法
		// contentData.put("txnType", "01"); //交易类型 01:消费
		contentData.put("txnType", "02"); // 交易类型 02:预授权
		contentData.put("txnSubType", "01"); // 交易子类 01：消费
		contentData.put("bizType", "000201"); // 填写000201
		contentData.put("channelType", "08"); // 渠道类型 08手机

		/*** 商户接入参数 ***/
		contentData.put("merId", merId); // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		contentData.put("accessType", "0"); // 接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构
											// 2：平台商户）
		contentData.put("orderId", orderNo); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01"); // 账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
		contentData.put("txnAmt", txnAmt); // 交易金额 单位为分，不能带小数点
		contentData.put("currencyCode", "156"); // 境内商户固定 156 人民币

		// 请求方保留域，
		// 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
		// 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
		// 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
		// contentData.put("reqReserved", "透传信息1|透传信息2|透传信息3");
		// 2. 内容可能出现&={}[]"'符号时：
		// 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
		// 2) 如果对账文件没有显示要求，可做一下base64（如下）。
		// 注意控制数据长度，实际传输的数据长度不能超过1024位。
		// 查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved),
		// DemoBase.encoding);解base64后再对数据做后续解析。
		// contentData.put("reqReserved",
		// Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));

		// 后台通知地址（需设置为外网能访问 http
		// https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
		// 后台通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 网关支付产品接口规范 消费交易 商户通知
		// 注意:1.需设置为外网能访问，否则收不到通知 2.http https均可 3.收单后台通知后需要10秒内返回http200或302状态码
		// 4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5
		// 分钟后会再次通知。
		// 5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d
		// 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", DemoBase.backUrl);

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, DemoBase.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestAppUrl, DemoBase.encoding); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, DemoBase.encoding)) {
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				if (("00").equals(respCode)) {
					// 成功,获取tn号
					String tn = rspData.get("tn");
					result.setData(tn);
					result.setCode(Constant.SECCUESS);
					result.setMsg("获取流水号成功");
					return result;
					// TODO
				} else {
					// 其他应答码为失败请排查原因或做失败处理
					// TODO
				}
			} else {
				LogUtil.writeErrorLog("验证签名失败");
				// TODO 检查验证签名失败的原因
			}
		} else {
			// 未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		// String reqMessage = DemoBase.genHtmlResult(reqData);
		// String rspMessage = DemoBase.genHtmlResult(rspData);
		// resp.getWriter().write("请求报文:<br/>"+reqMessage+"<br/>" +
		// "应答报文:</br>"+rspMessage+"");
		result.setCode(Constant.FAIL);
		result.setMsg("获取流水号失败");
		return result;
	}

	@Override
	public void backPreRcv(HttpServletRequest req, HttpServletResponse resp) {
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);
		LogUtil.printRequestLog(reqParam);

		// 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(reqParam, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			// 验签失败，需解决验签问题

		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			// 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

			String orderNo = reqParam.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
			String queryId = reqParam.get("queryId"); // 预授权交易的流水号，供后续查询用
			String merId = reqParam.get("merId"); // 商户号
			String txnTime = reqParam.get("txnTime"); // 交易时间
			String respCode = reqParam.get("respCode");
			String txnType = reqParam.get("txnType");
			// 判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
			if (respCode.equals("00") && txnType.equals("02")) { // 预授权
				req.setAttribute("merId", merId);
				req.setAttribute("queryId", queryId);
				req.setAttribute("orderNo", orderNo);
				req.setAttribute("payTime", txnTime);
//				queryOrderStatus(req, resp);
			}
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				// System.out.println("ServletUtil类247行 temp数据的键=="+en+"
				// 值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

	@Override
	public ResultInfo<String> queryOrderStatus(HttpServletRequest req, HttpServletResponse resp) {
		String merId = (String) req.getAttribute("merId");
		String queryId = (String) req.getAttribute("queryId");
		String orderNo = (String) req.getAttribute("orderNo");
		String payTime = (String) req.getAttribute("payTime");
		String trigger = (String) req.getAttribute("trigger");
		// String txnTime = req.getParameter("txnTime");

		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		Map<String, String> data = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		data.put("version", DemoBase.version); // 版本号
		data.put("encoding", DemoBase.encoding); // 字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); // 签名方法
		data.put("txnType", "00"); // 交易类型 00-默认
		data.put("txnSubType", "00"); // 交易子类型 默认00
		data.put("bizType", "000201"); // 业务类型

		/*** 商户接入参数 ***/
		data.put("merId", merId); // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改

		/*** 要调通交易以下字段必须修改 ***/
		data.put("orderId", queryId); // ****商户订单号，每次发交易测试需修改为被查询的交易的订单号
		data.put("txnTime", txnTime); // ****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

		/** 请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文-------------> **/

		Map<String, String> reqData = AcpService.sign(data, DemoBase.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getSingleQueryUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																// acpsdk.singleQueryUrl
		Map<String, String> rspData = AcpService.post(reqData, url, DemoBase.encoding); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, DemoBase.encoding)) {
				LogUtil.writeLog("验证签名成功");
				if (("00").equals(rspData.get("respCode"))) {// 如果查询交易成功
					String origRespCode = rspData.get("origRespCode");
					if (("00").equals(origRespCode)) {
						// 交易成功，更新商户订单状态
//						updateDepositOrderStatus(orderNo, payTime, queryId, trigger);
					} else if (("03").equals(origRespCode) || ("04").equals(origRespCode)
							|| ("05").equals(origRespCode)) {
						// 订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
						// TODO
					} else {
						// 其他应答码为交易失败
						// TODO
					}
				} else if (("34").equals(rspData.get("respCode"))) {
					// 订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准

				} else {// 查询交易本身失败，如应答码10/11检查查询报文是否正确
						// TODO
				}
			} else {
				LogUtil.writeErrorLog("验证签名失败");
				// TODO 检查验证签名失败的原因
			}
		} else {
			// 未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		return null;
	}

	/**
	 * 更新订单状态
	 * 
	 * @param orderNo
	 * @param payTime
	 * @param queryId
	 * @param trigger
	 */
	private void updateDepositOrderStatus(String orderNo, String payTime, String queryId,  String trigger) {
		DepositOrder order = depositOrderService.getDepositOrder(orderNo);
		if (order == null || order.getPayStatus().intValue() != 0) {
			return;
		}
		order.setPayStatus(1);// 已支付
		order.setPaymentMethod(3);// 支付方式（1.支付宝，2.微信,3.银联）
		if (payTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date;
			try {
				date = sdf.parse(payTime);
				order.setPaymentTime(date);
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
		} else {
			order.setPaymentTime(new Date());
		}
		order.setRemainAmount(ECNumberUtils.roundDoubleWithScale(order.getDepositAmount(), 2));
		order.setPaymentFlowNo(queryId);
		order.setPartTradeFlowNo(queryId); // 设置内部支付流水号编号
		order.setIsAvailable(1);
		depositOrderService.updateDepositOrder(order, null);
		// 押金支付成功后，设置会员表的应支付押金金额为dOrder.getPayableAmount()
		Member member = memberService.getMember(order.getMemberNo());
		if (member != null) {
			Member memberForUpdate = new Member();
			memberForUpdate.setMemberNo(member.getMemberNo());
			memberForUpdate.setPayableDepositAmount(order.getPayableAmount());
			memberService.updateMember(memberForUpdate, null);
		}

		/*
		 * 支付记录表添加数据
		 */
		PaymentRecord pr = new PaymentRecord();
		pr.setBizObjType(3);// 押金
		pr.setBizObjNo(order.getDepositOrderNo());
		pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
		pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
		pr.setPayStatus(1);
		pr.setPayType(3);
		pr.setPayFlowNo(queryId);	//外部支付流水号
		pr.setPartTradeFlowNo(queryId);	//内部支付流水号
		pr.setPaidTime(new Date());
		paymentRecordService.addPaymentRecord(pr, null);
		// 如果是邀请优惠押金配置
		SysParam param = sysParamService.getByParamKey("preferential");
		if (param != null && "2".equals(param.getParamValue()) && member != null) {
			String url = "";
			try {
				url = trigger + "marketingEvent/eventHandler?eventNo=4&memberNo=" + member.getMemberNo();
				HttpURLRequestUtil.doMsgGet(url);
				if (ECStringUtils.isBlank(member.getRefereeId())) {
					url = trigger + "marketingEvent/eventHandler?eventNo=3&memberNo=" + member.getRefereeId();
					HttpURLRequestUtil.doMsgGet(url);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public ResultInfo<String> backPreTrans(String orderNo) {
		ResultInfo<String> result = new ResultInfo<>();
		if (ECStringUtils.isBlank(orderNo)) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号不能为空");
			return result;
		}
		DepositOrder order = depositOrderService.getDepositOrder(orderNo);
		if (order == null) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号不存在");
			return result;
		}
		if (ECStringUtils.isBlank(SDKConfig.getConfig().getMerId())) {
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		}
		String orderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(order.getPaymentTime());
		Double Amount = order.getPayableAmount() * 100;
		String txnAmt = Amount.intValue()+"";
		String origQryId = order.getPaymentFlowNo();
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);            //版本号
		data.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "32");                        //交易类型 32-预授权撤销
		data.put("txnSubType", "00");                     //交易子类型  默认00
		data.put("bizType", "000201");                    //业务类型
		data.put("channelType", "08");                    //渠道类型，07-PC，08-手机
		
		/***商户接入参数***/
		data.put("merId", SDKConfig.getConfig().getMerId());             			  //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                      //接入类型，商户接入固定填0，不需修改	
		data.put("orderId", orderId);       			  //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费		
		data.put("txnTime", txnTime);   				  //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("txnAmt", txnAmt);                       //【撤销金额】，消费撤销时必须和原消费金额相同	
		data.put("currencyCode", "156");                  //交易币种(境内商户一般是156 人民币)
		data.put("backUrl", DemoBase.backUrl);            //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费撤销交易 商户通知,其他说明同消费交易的商户通知
		
		/***要调通交易以下字段必须修改***/
		data.put("origQryId", origQryId);   			  //【原始交易流水号】，原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
			
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData  = AcpService.sign(data,DemoBase.encoding);     //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getBackRequestUrl();						     //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String,String> rspData = AcpService.post(reqData,url,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
//					queryOrderStatus(order);
					result.setCode(Constant.SECCUESS);
					result.setMsg("预授权撤销成功");
					return result;
				}else if(("03").equals(respCode)||
						 ("04").equals(respCode)||
						 ("05").equals(respCode)){
					//后续需发起交易状态查询交易确定交易状态
					//TODO
				}else{
					//其他应答码为失败请排查原因
					//TODO
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		result.setCode(Constant.FAIL);
		result.setMsg("预授权撤销失败");
		return result;
	}

	@Override
	public ResultInfo<String> finalPreTrans(String orderNo) {
		ResultInfo<String> result = new ResultInfo<>();
		if (ECStringUtils.isBlank(orderNo)) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号不能为空");
			return result;
		}
		DepositOrder order = depositOrderService.getDepositOrder(orderNo);
		if (order == null) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号不存在");
			return result;
		}
		if (ECStringUtils.isBlank(SDKConfig.getConfig().getMerId())) {
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		}
		String orderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(order.getPaymentTime());
		Double Amount = order.getPayableAmount() * 100;
		String txnAmt = Amount.intValue()+"";
		String origQryId = order.getPaymentFlowNo();
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);            //版本号
		data.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "03");                        //交易类型 32-预授权完成交易
		data.put("txnSubType", "00");                     //交易子类型  默认00
		data.put("bizType", "000201");                    //业务类型
		data.put("channelType", "08");                    //渠道类型，07-PC，08-手机
		
		/***商户接入参数***/
		data.put("merId", SDKConfig.getConfig().getMerId());             			  //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                      //接入类型，商户接入固定填0，不需修改	
		data.put("orderId", orderId);       			  //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费		
		data.put("txnTime", txnTime);   				  //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("txnAmt", txnAmt);                       //【撤销金额】，消费撤销时必须和原消费金额相同	
		data.put("currencyCode", "156");                  //交易币种(境内商户一般是156 人民币)
		data.put("backUrl", DemoBase.backUrl);            //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费撤销交易 商户通知,其他说明同消费交易的商户通知
		
		/***要调通交易以下字段必须修改***/
		data.put("origQryId", origQryId);   			  //【原始交易流水号】，原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
			
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData  = AcpService.sign(data,DemoBase.encoding);     //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getBackRequestUrl();						     //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String,String> rspData = AcpService.post(reqData,url,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
					//TODO
					result.setCode(Constant.SECCUESS);
					result.setMsg("预授权完成成功");
					return result;
				}else if(("03").equals(respCode)||
						 ("04").equals(respCode)||
						 ("05").equals(respCode)){
					//后续需发起交易状态查询交易确定交易状态
					//TODO
				}else{
					//其他应答码为失败请排查原因
					//TODO
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		result.setCode(Constant.FAIL);
		result.setMsg("预授权完成交易失败");
		return result;
	}

	@Override
	public ResultInfo<String> finalAndBackPreTrans(String orderNo) {
		ResultInfo<String> result = new ResultInfo<>();
		if (ECStringUtils.isBlank(orderNo)) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号不能为空");
			return result;
		}
		DepositOrder order = depositOrderService.getDepositOrder(orderNo);
		if (order == null) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号不存在");
			return result;
		}
		if (ECStringUtils.isBlank(SDKConfig.getConfig().getMerId())) {
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		}
		String orderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(order.getPaymentTime());
		Double Amount = order.getPayableAmount() * 100;
		String txnAmt = Amount.intValue()+"";
		String origQryId = order.getPaymentFlowNo();
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);            //版本号
		data.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "33");                        //交易类型 33-预授权完成撤销
		data.put("txnSubType", "00");                     //交易子类型  默认00
		data.put("bizType", "000201");                    //业务类型
		data.put("channelType", "08");                    //渠道类型，07-PC，08-手机
		
		/***商户接入参数***/
		data.put("merId", SDKConfig.getConfig().getMerId());             			  //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                      //接入类型，商户接入固定填0，不需修改	
		data.put("orderId", orderId);       			  //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费		
		data.put("txnTime", txnTime);   				  //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("txnAmt", txnAmt);                       //【撤销金额】，消费撤销时必须和原消费金额相同	
		data.put("currencyCode", "156");                  //交易币种(境内商户一般是156 人民币)
		data.put("backUrl", DemoBase.backUrl);            //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费撤销交易 商户通知,其他说明同消费交易的商户通知
		
		/***要调通交易以下字段必须修改***/
		data.put("origQryId", origQryId);   			  //【原始交易流水号】，原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
			
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData  = AcpService.sign(data,DemoBase.encoding);     //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getBackRequestUrl();						     //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String,String> rspData = AcpService.post(reqData,url,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
//					queryOrderStatus(order);
					result.setCode(Constant.SECCUESS);
					result.setMsg("预授权完成撤销成功");
					return result;
				}else if(("03").equals(respCode)||
						 ("04").equals(respCode)||
						 ("05").equals(respCode)){
					//后续需发起交易状态查询交易确定交易状态
					//TODO
				}else{
					//其他应答码为失败请排查原因
					//TODO
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		result.setCode(Constant.FAIL);
		result.setMsg("预授权完成撤销失败");
		return result;
	}

	@Override
	public ResultInfo<String> queryOrderStatus(DepositOrder order) {
		String queryId = order.getPaymentFlowNo();
		String orderNo = order.getDepositOrderNo();
		// String txnTime = req.getParameter("txnTime");

		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		Map<String, String> data = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		data.put("version", DemoBase.version); // 版本号
		data.put("encoding", DemoBase.encoding); // 字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); // 签名方法
		data.put("txnType", "00"); // 交易类型 00-默认
		data.put("txnSubType", "00"); // 交易子类型 默认00
		data.put("bizType", "000201"); // 业务类型

		/*** 商户接入参数 ***/
		data.put("merId", SDKConfig.getConfig().getMerId()); // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改

		/*** 要调通交易以下字段必须修改 ***/
		data.put("orderId", queryId); // ****商户订单号，每次发交易测试需修改为被查询的交易的订单号
		data.put("txnTime", txnTime); // ****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

		/** 请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文-------------> **/

		Map<String, String> reqData = AcpService.sign(data, DemoBase.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getSingleQueryUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																// acpsdk.singleQueryUrl
		Map<String, String> rspData = AcpService.post(reqData, url, DemoBase.encoding); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, DemoBase.encoding)) {
				LogUtil.writeLog("验证签名成功");
				if (("00").equals(rspData.get("respCode"))) {// 如果查询交易成功
					String origRespCode = rspData.get("origRespCode");
					String txnType = rspData.get("txnType");
					if (("00").equals(origRespCode) && (txnType.equals("32") || txnType.equals("33"))) { // 预授权撤销和完成撤销
						// 交易成功，更新商户订单状态
//						updateDepositOrderStatus(orderNo);
					} else if (("03").equals(origRespCode) || ("04").equals(origRespCode)
							|| ("05").equals(origRespCode)) {
						// 订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
						// TODO
					} else {
						// 其他应答码为交易失败
						// TODO
					}
				} else if (("34").equals(rspData.get("respCode"))) {
					// 订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准

				} else {// 查询交易本身失败，如应答码10/11检查查询报文是否正确
						// TODO
				}
			} else {
				LogUtil.writeErrorLog("验证签名失败");
				// TODO 检查验证签名失败的原因
			}
		} else {
			// 未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		return null;
	}
	
	/**
	 * 撤销更新订单状态
	 * 
	 * @param orderNo
	 * @param payTime
	 * @param queryId
	 * @param trigger
	 */
	private void updateDepositOrderStatus(String orderNo) {
		DepositOrder order = depositOrderService.getDepositOrder(orderNo);
		if (order == null || order.getPayStatus().intValue() != 1) {
			return;
		}
		order.setPayStatus(0);// 未支付
		order.setIsAvailable(0);
		depositOrderService.updateDepositOrder(order, null);
	}
	
}
