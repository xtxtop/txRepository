package cn.com.shopec.mapi.myWallet.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.model.CAccountRecord;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.service.CAccountBalanceService;
import cn.com.shopec.core.ml.service.CAccountRecordService;
import cn.com.shopec.core.ml.vo.AccountRecordVo;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.BannerVo;
import cn.com.shopec.core.pay.PayService;
import cn.com.shopec.core.pay.UnionPayService;
import cn.com.shopec.mapi.common.RechargePayUtil;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.myWallet.vo.CAccRecVo;
import cn.com.shopec.mapi.myWallet.vo.WalletVo;

/**
 * app猛龙充电桩我的钱包接口
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/app/myWallet")
public class MyWalletController extends BaseController {
	@Resource
	private CAccountRecordService caccountRecordService;
	@Resource
	private CAccountBalanceService cAccountBalanceService;
	@Resource
	private AdvertMengLongService advertMengLongService;
	@Value("${image_path}")
	private String imgPath;
	@Resource
	private PayService payService;
	@Resource
	private UnionPayService unoinPayService;
	@Value("${triggerEvent_path}")
	private String trigger;
	@Resource
	private RechargePayUtil rechargePayUtil;
	@Resource
	private MemberService memberService;

	/**
	 * 充值记录
	 *
	 * @param memberNo
	 *            会员编号
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页显示条数
	 * @return
	 */
	@RequestMapping("/getAccountRecordList")
	@ResponseBody
	public ResultInfo<List<AccountRecordVo>> getAccountRecordList(@RequestParam(value = "memberNo") String memberNo,
			@RequestParam(value = "pageNo") String pageNo, @RequestParam(value = "pageSize") String pageSize) {
		ResultInfo<List<AccountRecordVo>> result = new ResultInfo<List<AccountRecordVo>>();
	/*	List<BannerVo> bannerVoList = caccountRecordService.getBannerVoList(imgPath);
		if (null != bannerVoList && bannerVoList.size() > 0) {
			cacc.setBannerList(bannerVoList);
		}*/
		CAccountRecord record = new CAccountRecord();
		record.setMemberNo(memberNo);
		List<AccountRecordVo> list = caccountRecordService
				.getAccountRecordByMemberNo(new Query(Integer.valueOf(pageNo), Integer.valueOf(pageSize), record));
		if (null != list && list.size() > 0) {
			result.setCode(Constant.SECCUESS);
			result.setMsg("成功返回数据");
			result.setData(list);
			return result;
		} else {
			result.setCode(Constant.OTHER);
			result.setMsg("充值记录暂无记录");
			return result;
		}
	}

	/**
	 * 我的钱包
	 *
	 * @param memberNo
	 *            会员编号
	 * @return
	 */
	@RequestMapping("/wallet")
	@ResponseBody
	public ResultInfo<WalletVo> wallet(String memberNo) {
		ResultInfo<WalletVo> result = new ResultInfo<>();
		CAccountBalance search = new CAccountBalance();
		search.setMemberNo(memberNo);
		List<CAccountBalance> lst = cAccountBalanceService.getCAccountBalanceList(new Query(search));
		WalletVo wallet = new WalletVo();
		if (lst == null || lst.size() < 1) {
			wallet.setStopBalance(0.0);
			wallet.setChargingBalance(0.0);
		} else {
			CAccountBalance balance = lst.get(0);
			if (null != balance.getStopBalance()) {
				wallet.setStopBalance(ECNumberUtils.roundDown(balance.getStopBalance(), 2));

			} else {
				wallet.setStopBalance(0.0);
			}
			if (null != balance.getChargingBalance()) {
				wallet.setChargingBalance(ECNumberUtils.roundDown(balance.getChargingBalance(), 2));
			} else {
				wallet.setChargingBalance(0.0);
			}
		}
		AdvertMengLong ad = new AdvertMengLong();
		ad.setType(Constant.TYPE_12);// 充电桩类型
		ad.setAdvertPosition("2");// 系统顶部滚动文字
		List<AdvertMengLong> advertList = advertMengLongService.getAdvertList(new Query(ad));
		List<AdvertTextVo> sysParamList = new ArrayList<>();
		if (advertList.size() > 0) {
			for (AdvertMengLong a : advertList) {
				AdvertTextVo avo = new AdvertTextVo();
				avo.setAdvertName(a.getAdvertName());
				sysParamList.add(avo);
			}
			wallet.setAdList(sysParamList);
		}
		result.setData(wallet);
		result.setCode(Constant.SECCUESS);
		result.setMsg("成功返回数据");
		return result;
	}

	/**
	 * 我的钱包充值微信支付接口
	 *
	 * @param memberNo
	 *            会员编号
	 * @param rechargeAccount
	 *            充值金额
	 * @param dealType
	 *            交易方式（1、充电；2、停车）
	 * @param tag:0:安卓，1：ios
	 * @return
	 */
	@RequestMapping("/payMent")
	@ResponseBody
	public ResultInfo<SortedMap<String, Object>> payMent(HttpServletRequest request, HttpServletResponse response,
			String memberNo, Integer tag, String dealType, String rechargeAccount) {
		String orderNo = "";
		// 充电
		if ("1".equals(dealType)) {
			CAccountBalance cab = new CAccountBalance();
			Double chargingBalance = 0.0;
			cab.setMemberNo(memberNo);
			List<CAccountBalance> caccountBalance = cAccountBalanceService.getCAccountBalanceList(new Query(cab));
			if (caccountBalance.size() > 0) {
				chargingBalance = caccountBalance.get(0).getChargingBalance();// 充电余额
			} else {
				cab.setChargingBalance(Double.valueOf(0.0));// 默认充电余额为0
				cab.setStopBalance(Double.valueOf(0.0));// 默认停车余额为0
				cAccountBalanceService.addCAccountBalance(cab, getOperator());// 首次创建会员余额记录
				chargingBalance = Double.valueOf(0.0);
			}
			CAccountRecord cr = new CAccountRecord();// 充值记录表
			if (null != chargingBalance) {
				cr.setOriginalAmount(chargingBalance);// 原始金额
				cr.setBalance(chargingBalance);// 余下金额
			} else {
				chargingBalance = Double.valueOf(0.0);
				cr.setOriginalAmount(chargingBalance);// 原始金额
				cr.setBalance(chargingBalance);// 余下金额
			}
			cr.setMemberNo(memberNo);
			cr.setDealType(1);// 充电
			cr.setType(1);// 充值类型
			cr.setPayStatus(0);// 未充值状态
			cr.setTrnasactionAmount(Double.valueOf(rechargeAccount) / 100);// 充值金额
			Member member = memberService.getMember(memberNo);
			if (null != member) {
				cr.setMenberName(member.getMemberName());
				cr.setMobilePhone(member.getMobilePhone());
			}
			ResultInfo<CAccountRecord> resultInfo = caccountRecordService.addCAccountRecord(cr, getOperator());
			String code = resultInfo.getCode();
			if ("1".equals(code)) {
				orderNo = resultInfo.getData().getId();
			}
		} else if ("2".equals(dealType)) {// 停车
			CAccountBalance cab = new CAccountBalance();
			Double chargingBalance = 0.0;
			cab.setMemberNo(memberNo);
			List<CAccountBalance> caccountBalance = cAccountBalanceService.getCAccountBalanceList(new Query(cab));
			if (caccountBalance.size() > 0) {
				chargingBalance = caccountBalance.get(0).getStopBalance();// 充电余额
			} else {
				cab.setStopBalance(Double.valueOf(0.0));// 停车余额0
				cab.setChargingBalance(Double.valueOf(0.0));// 充电余额0
				cAccountBalanceService.addCAccountBalance(cab, getOperator());// 首次创建会员余额记录
				chargingBalance = Double.valueOf(0.0);
			}
			CAccountRecord cr = new CAccountRecord();// 充值记录表
			if (null != chargingBalance) {
				cr.setOriginalAmount(chargingBalance);// 原始金额
				cr.setBalance(chargingBalance);// 余下金额
			} else {
				chargingBalance = Double.valueOf(0.0);
				cr.setOriginalAmount(chargingBalance);// 原始金额
				cr.setBalance(chargingBalance);// 余下金额
			}
			cr.setMemberNo(memberNo);
			cr.setDealType(2);// 停车
			cr.setType(1);// 充值类型
			cr.setPayStatus(0);// 未充值状态
			cr.setTrnasactionAmount(Double.valueOf(rechargeAccount) / 100);// 充值金额
			Member member = memberService.getMember(memberNo);
			if (null != member) {
				cr.setMenberName(member.getMemberName());
				cr.setMobilePhone(member.getMobilePhone());
			}
			ResultInfo<CAccountRecord> resultInfo = caccountRecordService.addCAccountRecord(cr, getOperator());
			String code = resultInfo.getCode();
			if ("1".equals(code)) {
				orderNo = resultInfo.getData().getId();
			}
		}
		return rechargePayUtil.getCodeUrl(request, response, memberNo, tag, dealType, orderNo, rechargeAccount);
	}

	/**
	 * 接受微信返回给商户后台的信息并修改订单信息
	 */
	@RequestMapping("/wxUpdateOrder")
	@ResponseBody
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response) {
		rechargePayUtil.wxUpdateOrder(request, response, getOperator());
	}

	/**
	 * ） 我的钱包充值支付包支付接口
	 *
	 * @param memberNo
	 *            会员编号
	 * @param rechargeAccount
	 *            充值金额
	 * @param dealType
	 *            交易方式（1、充电；2、停车）
	 * @param tag:0:安卓，1：ios
	 * @return
	 */
	@RequestMapping("/apliyPayMent")
	@ResponseBody
	public ResultInfo<String> apliyPayMent(HttpServletRequest request, HttpServletResponse response, String memberNo,
			Integer tag, String dealType, String rechargeAccount) {
		String orderNo = "";
		// 充电类型
		if ("1".equals(dealType)) {
			CAccountBalance cab = new CAccountBalance();
			Double chargingBalance = 0.0;
			cab.setMemberNo(memberNo);
			List<CAccountBalance> caccountBalance = cAccountBalanceService.getCAccountBalanceList(new Query(cab));
			if (caccountBalance.size() > 0) {
				chargingBalance = caccountBalance.get(0).getChargingBalance();// 充电余额
			} else {
				cab.setChargingBalance(Double.valueOf(0.0));
				cab.setStopBalance(Double.valueOf(0.0));
				cAccountBalanceService.addCAccountBalance(cab, getOperator());// 首次创建会员余额记录
				chargingBalance = Double.valueOf(0);
			}
			CAccountRecord cr = new CAccountRecord();// 充值记录表
			if (null != chargingBalance) {
				cr.setOriginalAmount(chargingBalance);// 原始金额
				cr.setBalance(chargingBalance);// 余下金额
			} else {
				chargingBalance = Double.valueOf(0.0);
				cr.setOriginalAmount(chargingBalance);// 原始金额
				cr.setBalance(chargingBalance);// 余下金额
			}
			cr.setTrnasactionAmount(Double.valueOf(rechargeAccount));// 交易金额
			cr.setMemberNo(memberNo);
			cr.setDealType(1);// 充电
			cr.setType(1);// 充值类型
			cr.setPayStatus(0);// 未支付
			cr.setTrnasactionAmount(Double.valueOf(rechargeAccount));// 充值金额
			Member member = memberService.getMember(memberNo);
			if (null != member) {
				cr.setMenberName(member.getMemberName());
				cr.setMobilePhone(member.getMobilePhone());
			}

			ResultInfo<CAccountRecord> resultInfo = caccountRecordService.addCAccountRecord(cr, getOperator());
			String code = resultInfo.getCode();
			if ("1".equals(code)) {
				orderNo = resultInfo.getData().getId();
			}
		} else if ("2".equals(dealType)) {// 停车类型
			CAccountBalance cab = new CAccountBalance();
			Double stopBalance = 0.0;
			cab.setMemberNo(memberNo);
			List<CAccountBalance> caccountBalance = cAccountBalanceService.getCAccountBalanceList(new Query(cab));
			if (caccountBalance.size() > 0) {
				stopBalance = caccountBalance.get(0).getStopBalance();// 充电余额
			} else {
				cab.setStopBalance(Double.valueOf(0));
				cAccountBalanceService.addCAccountBalance(cab, getOperator());// 首次创建会员余额记录
				stopBalance = Double.valueOf(0);
			}
			CAccountRecord cr = new CAccountRecord();// 充值记录表
			if (null != stopBalance) {
				cr.setOriginalAmount(stopBalance);// 原始金额
				cr.setBalance(stopBalance);// 余下金额
			} else {
				stopBalance = Double.valueOf(0.0);
				cr.setOriginalAmount(stopBalance);// 原始金额
				cr.setBalance(stopBalance);// 余下金额
			}
			cr.setTrnasactionAmount(Double.valueOf(rechargeAccount));// 交易金额
			cr.setMemberNo(memberNo);
			cr.setDealType(2);// 停车
			cr.setType(1);// 充值类型
			cr.setPayStatus(0);// 未支付
			cr.setTrnasactionAmount(Double.valueOf(rechargeAccount));// 充值金额
			Member member = memberService.getMember(memberNo);
			if (null != member) {
				cr.setMenberName(member.getMemberName());
				cr.setMobilePhone(member.getMobilePhone());
			}

			ResultInfo<CAccountRecord> resultInfo = caccountRecordService.addCAccountRecord(cr, getOperator());
			String code = resultInfo.getCode();
			if ("1".equals(code)) {
				orderNo = resultInfo.getData().getId();
			}
		}
		return rechargePayUtil.alipayGetOrderStr(request, response, memberNo, dealType, orderNo, rechargeAccount);
	}

	/**
	 * 支付宝返回给后台的异步通知结果，进行解析并修改订单信息
	 *
	 * @throws Exception
	 */
	@RequestMapping("/alipayUpdateOrder")
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		rechargePayUtil.alipayUpdateOrder(request, response);
	}
}
