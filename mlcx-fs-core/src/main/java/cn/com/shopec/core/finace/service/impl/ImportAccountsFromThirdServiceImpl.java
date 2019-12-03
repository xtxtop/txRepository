package cn.com.shopec.core.finace.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.pay.wxPay.CommonUtil;
import cn.com.shopec.common.pay.wxPay.PayCommonUtil;
import cn.com.shopec.common.pay.wxPay.TenpayUtil;
import cn.com.shopec.common.pay.wxPay.WxpayConfig;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.core.finace.common.ImportAccountsConstant;
import cn.com.shopec.core.finace.dao.FinaceTestDao;
import cn.com.shopec.core.finace.model.FinaceTest;
import cn.com.shopec.core.finace.service.FinaceTestService;
import cn.com.shopec.core.finace.service.ImportAccountsFromThirdService;

/**
 * 从第三方倒入对账单明细
 * 
 * @author admin
 *
 */

@Service
public class ImportAccountsFromThirdServiceImpl implements ImportAccountsFromThirdService {

	private static final Log log = LogFactory.getLog(InvoiceServiceImpl.class);
	@Resource
	private FinaceTestDao finaceDao;

	@Resource
	private FinaceTestService finaceTestService;

	/**
	 * 支付宝账单倒入
	 */
	@Override
	public ResultInfo<FinaceTest> importFromAlipay(String time) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		if (null != time && time.trim().length() > 0) {
			// 判断指定时间的账单是否已有记录
/*			Long result = 0L;
			try {
				String startTime = ECDateUtils
						.formatStringTime(ECDateUtils.getCurrentDateStartTime(ECDateUtils.parseDate(time)));
				String endTime = ECDateUtils
						.formatStringTime(ECDateUtils.getCurrentDateEndTime(ECDateUtils.parseDate(time)));
				String method = "alipay";
				result = finaceDao.getCountByTime(method, startTime, endTime);
				if (null != result && result > 0) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(ImportAccountsConstant.fail_date_is_existence);
					log.info(ImportAccountsConstant.fail_date_is_existence);
					return resultInfo;
				} else {*/
					FinaceTest finaceTest = new FinaceTest();
					finaceTest.toString();
					String billingUrl = getAccountsFromAlipay(time);
					if (billingUrl == null) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(ImportAccountsConstant.fail_alipay);
						log.info(ImportAccountsConstant.fail_alipay);
						return resultInfo;
					} else {
						return saveAccountsFromAlipay(billingUrl, time);
					}
/*				}
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
		}
		resultInfo.setCode(Constant.FAIL);
		log.info(ImportAccountsConstant.fail_date_parameter);
		resultInfo.setMsg(ImportAccountsConstant.fail_date_parameter);
		return resultInfo;
	}

	/**
	 * 连接支付宝获取账单
	 * 
	 * @param time
	 */
	private String getAccountsFromAlipay(String time) {
		String appId = AlipayConfig.appId; // 支付宝分配给开发者的应用ID
		String openUrl = "https://openapi.alipay.com/gateway.do"; // 正式环境HTTPS请求地址
		String privateKey = AlipayConfig.rsa_private;
		String publicKey = AlipayConfig.rsa_public;
		AlipayClient alipayClient = new DefaultAlipayClient(openUrl, appId, privateKey, "json", "UTF-8", publicKey,
				"RSA");
		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		request.setBizContent("{" + "    \"bill_type\":\"trade\"," + "    \"bill_date\":\""+time+"\"" + // bill_date
				"  }");
		AlipayDataDataserviceBillDownloadurlQueryResponse response;
		try {
			response = alipayClient.execute(request);
			if (response.isSuccess()) {
				log.info(ImportAccountsConstant.success_alipay);
				if ("10000".equals(response.getCode())) { // 查询成功
					String billingUrl = response.getBillDownloadUrl();
					log.info("查询成功，查询时间为" + time + ";下载地址为：" + billingUrl);
					// 请求下载地址下载账单然后倒入到系统表
					return billingUrl;
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 存储支付宝账单
	 * 
	 * @param url
	 * @param time
	 */
	@SuppressWarnings("deprecation")
	private ResultInfo<FinaceTest> saveAccountsFromAlipay(String url, String time) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		List<String> data = null;
		String fileName = time + ".zip";
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        ServletContext servletContext = webApplicationContext.getServletContext(); 
		String filePath = servletContext.getRealPath("/") + "download" + File.separator + "alipayAccounts";
		try {// 下载支付宝账单文件(zip格式)
			HttpURLRequestUtil.downLoadFromUrl(url, fileName, filePath);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(ImportAccountsConstant.fail_alipay_download);
			log.info(ImportAccountsConstant.fail_alipay_download);
			e.printStackTrace();
			return resultInfo;
		}
		
		try {// 解析支付宝账单文件(zip格式)
			data = readFileFromAlipay(filePath + File.separator + fileName);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);

			resultInfo.setMsg(ImportAccountsConstant.fail_alipay_decompression);
			log.info(ImportAccountsConstant.fail_alipay_decompression);
			e.printStackTrace();
			return resultInfo;
		}
		//存储数据
		List<FinaceTest> finaceTests = finaceTestService.getFinaceTestFromAlipayData(data);
		if(finaceTests != null && finaceTests.size() > 0){
			return finaceTestService.addFinaceTests(finaceTests);
		}else{
			resultInfo.setMsg(ImportAccountsConstant.fail_alipay_data_empty);
			log.info(ImportAccountsConstant.fail_alipay_data_empty);
			return resultInfo;
		}
	}

	/**
	 * 读取支付宝业务明细账单的文件
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	private List<String> readFileFromAlipay(String filePath) throws Exception {
		ZipInputStream zipInputStream = null;
		BufferedReader bufferedReader = null;
		try {
			ZipFile zipFile = new ZipFile(filePath, Charset.forName("gbk"));

			zipInputStream = new ZipInputStream(new FileInputStream(filePath), Charset.forName("gbk"));// 输入源zip路径
			ZipEntry zipEntry;
			List<String> dataList = new ArrayList<String>();
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				if (!zipEntry.isDirectory() && zipEntry.getName() != null
						&& zipEntry.getName().indexOf("业务明细.csv") != -1) {
					long size = zipEntry.getSize();
					if (size > 0) {
						bufferedReader = new BufferedReader(
								new InputStreamReader(zipFile.getInputStream(zipEntry), "gbk"));
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							dataList.add(line);
						}
					}
				}
			}
			return dataList;
		} finally {
			if (zipInputStream != null) {
				zipInputStream.closeEntry();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}

	/**
	 * 微信账单倒入
	 */
	@Override
	public ResultInfo<FinaceTest> importFromWChart(String time) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		if (null != time && time.trim().length() > 0) {
/*			// 判断指定时间的账单是否已有记录
			try {
				Long result = 0L;
				String startTime = ECDateUtils
						.formatStringTime(ECDateUtils.getCurrentDateStartTime(ECDateUtils.parseDate(time)));
				String endTime = ECDateUtils
						.formatStringTime(ECDateUtils.getCurrentDateEndTime(ECDateUtils.parseDate(time)));
				String method = "weixin"; // 这里的类型与倒入的时候规则想匹配
				result = finaceDao.getCountByTime(method, startTime, endTime);
				if (null != result && result > 0) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(ImportAccountsConstant.fail_date_is_existence);
					return resultInfo;
				} else {*/
					// 格式化时间参数
					String wtime = time.replaceAll("-", "");
					String returnInfo = getAccountsFromWeChart(wtime);
					return saveAccountsFromWeChart(returnInfo, time);
/*				}

			} catch (ParseException e) {
				e.printStackTrace();
			}*/
		}
		resultInfo.setCode(Constant.FAIL);
		resultInfo.setMsg(ImportAccountsConstant.fail_date_parameter);
		log.info(ImportAccountsConstant.fail_date_parameter);
		return resultInfo;
	}

	/**
	 * 从微信下载交易账单并倒入
	 * 
	 * @param time
	 */
	private String getAccountsFromWeChart(String time) {
		// 生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", WxpayConfig.appID); // 公众账号ID
		packageParams.put("mch_id", WxpayConfig.mchID); // 商户号
		packageParams.put("bill_date", time); // 对账单日期
		packageParams.put("bill_type", "ALL"); // 账单类型
		packageParams.put("nonce_str", nonce_str); // 随机字符串
		String sign = PayCommonUtil.createSign("UTF-8", packageParams);// 生成签名
		packageParams.put("sign", sign);
		String requestXml = PayCommonUtil.getRequestXml(packageParams);// 生成Xml格式的字符串
		String downloadbillURL = "https://api.mch.weixin.qq.com/pay/downloadbill";
		return CommonUtil.httpsRequest(downloadbillURL, "POST", requestXml);// 以post请求的方式调用统一下单接口
		/**
		 * 成功时，数据以文本表格的方式返回，第一行为表头，后面各行为对应的字段内容，字段内容跟查询订单或退款结果一致，具体字段说明可查阅相应接口。
		 * 第一行为表头，根据请求下载的对账单类型不同而不同(由bill_type决定),目前有：当日成功支付的订单
		 * 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,代金券或立减优惠金额,商品名称,商户数据包,手续费,费率
		 */
	}

	/**
	 * 存储微信账单
	 * 
	 * @param dataString
	 * @param time
	 * @return
	 */
	private ResultInfo<FinaceTest> saveAccountsFromWeChart(String dataString, String time) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		try {
			DocumentHelper.parseText(dataString); // 校验是否xml，微信返回xml代表调用失败，调用正确则会返回数据文本
			resultInfo.setMsg(ImportAccountsConstant.fail_wechart);
			log.info(ImportAccountsConstant.fail_wechart);
		} catch (DocumentException e) {
			log.info(ImportAccountsConstant.success_wechart);
			//存储数据
			List<FinaceTest> finaceTests = finaceTestService.getFinaceTestFromWeChart(dataString, time);
			if(finaceTests != null && finaceTests.size() > 0){
				return finaceTestService.addFinaceTests(finaceTests);
			}else{
				resultInfo.setMsg(ImportAccountsConstant.fail_wechart_data_empty);
				log.info(ImportAccountsConstant.fail_wechart_data_empty);
			}
		}
		resultInfo.setCode(Constant.FAIL);
		return resultInfo;
	}
}
