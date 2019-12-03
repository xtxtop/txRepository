package cn.com.shopec.mapi.member.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.LRUMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.google.gson.Gson;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.authentication.AuthenticationService;
import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.sendmsg.SendMsgService;
import cn.com.shopec.common.utils.AESCipher;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.common.utils.GenerateInvitationCodeUtil;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.zmxy.ZhimaUtil;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.marketing.model.Accounts;
import cn.com.shopec.core.marketing.model.AppLocation;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.GoldBeansConsumerDetails;
import cn.com.shopec.core.marketing.model.GoldBeansSetting;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.model.RedeemCode;
import cn.com.shopec.core.marketing.service.AccountsService;
import cn.com.shopec.core.marketing.service.AppLocationService;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.GoldBeansConsumerDetailsService;
import cn.com.shopec.core.marketing.service.GoldBeansSettingService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.marketing.service.RedeemCodeService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberGlodBeans;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberZhimaScore;
import cn.com.shopec.core.member.service.CompanyPersonService;
import cn.com.shopec.core.member.service.MemberGlodBeansService;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberPointsRecordService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.member.service.MemberZhimaScoreService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mapi.common.TokenGeneratorUtil;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.marketing.vo.CouponVo;
import cn.com.shopec.mapi.member.vo.AccountsVo;
import cn.com.shopec.mapi.member.vo.GoldBeansConsumerDetail;
import cn.com.shopec.mapi.member.vo.GoldBeansConsumerVo;
import cn.com.shopec.mapi.member.vo.InviteVo;
import cn.com.shopec.mapi.member.vo.MemberCensorVo;
import cn.com.shopec.mapi.member.vo.MemberPointVo;
import cn.com.shopec.mapi.member.vo.MemberStatusVO;
import cn.com.shopec.mapi.member.vo.MemberVo;
import cn.com.shopec.mapi.member.vo.PapersVo;
import cn.com.shopec.mapi.order.vo.PricingPackOrderVO;
import cn.com.shopec.mapi.resource.vo.AppLocationVOSearchBaiDu;

import cn.com.shopec.mapi.resource.vo.AppLocationVOSearchBaiDu.AppLocationVOBaiDu.AddressComponent;

@Controller
@RequestMapping("/app/member")
public class MemberController extends BaseController {
	// 创建日志文件
	private static Logger log = Logger.getLogger(MemberController.class);
	@Resource
	private MemberService memberService;

	
	@Resource
	private SendMsgService msgService;

	@Resource
	private MemberPointsRecordService memberPointsRecordService;

	@Resource
	private MemberLevelService memberLevelService;

	@Resource
	private CommonCacheUtil cacheUtil;

	@Resource
	private DepositOrderService depositOrderService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private PricingPackageService pricingPackageService;

	@Resource
	private CompanyPersonService companyPersonService;
	@Resource
	private DepositOrderDao depositOrderDao;
	@Resource
	private AccountsService accountsService;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private RedeemCodeService redeemCodeService;
	@Resource
	private AuthenticationService authenticationService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private  MemberZhimaScoreService memberZhimaScoreService;
	@Resource
	private GoldBeansConsumerDetailsService consumerDetailsService;
	@Resource
	private MemberGlodBeansService memberGlodBeansService;
	@Resource
	private GoldBeansSettingService goldBeansSettingService;
	@Resource
	private SysRegionService sysRegionService;
	@Resource
	private AppLocationService appLocationService;
	
	@Value("${image_path}")
	private String imgPath;
	@Value("${appkey}")
	private String appkey;
	@Value("${ak}")
	private String ak;
	@Value("${share_img_path}")
	private String image;
	@Value("${triggerEvent_path}")
	private String trigger;
	 @Value("${tpl_regist_id}")
	 private String tplId;
	
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;

	
	/**
	 * 获取金豆消费明细
	 * @param memberNo
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("getGlodBeansConsumeDetail")
	@ResponseBody
	public ResultInfo<GoldBeansConsumerVo> getGlodBeansConsumeDetail(String memberNo, @RequestParam("pageNo") int pageNo) {
		ResultInfo<GoldBeansConsumerVo> result = new ResultInfo<>(); 
		if(memberNo!=null&&!"".equals(memberNo)){
			GoldBeansConsumerVo consumerVo = new GoldBeansConsumerVo();
			List<GoldBeansConsumerDetail> consumerDetailList = new ArrayList<>();
			MemberGlodBeans memberGlodBeans =memberGlodBeansService.getMemberGlodBeans(memberNo);
			if(memberGlodBeans!=null){
				consumerVo.setGlodBeansTotalNum(memberGlodBeans.getGoldBeansNum()+"");
			}else{
				consumerVo.setGlodBeansTotalNum("0");
			}
			List<GoldBeansSetting> glodBeansSettingList = goldBeansSettingService.getGoldBeansSettingList(new Query());
			if(glodBeansSettingList.size()>0){
				GoldBeansSetting glodBeansSetting = glodBeansSettingList.get(0);
				consumerVo.setDays(glodBeansSetting.getDays()+"");
				consumerVo.setGlodBeansNum(glodBeansSetting.getGoldBeansNum()+"");
			}
			GoldBeansConsumerDetails consumerDetailForQuery = new GoldBeansConsumerDetails();
			consumerDetailForQuery.setMenberNo(memberNo);
			Query q = new Query(pageNo,10,consumerDetailForQuery);
			PageFinder<GoldBeansConsumerDetails> pageFinderList = consumerDetailsService.getGoldBeansConsumerDetailsPagedList(q);
			if(pageFinderList.getData()!=null){
				List<GoldBeansConsumerDetails> detailsList = pageFinderList.getData();
				for(GoldBeansConsumerDetails detail:detailsList){
					GoldBeansConsumerDetail temp = new GoldBeansConsumerDetail();
					temp.setConsumerBeansAmount(detail.getConsumerBeansAmount());
					temp.setOrderNo(detail.getOrderNo());
					temp.setConsumerTime(ECDateUtils.formatStringTime(detail.getConsumerTime()));
					consumerDetailList.add(temp);
				}
				consumerVo.setConsumerDetailList(consumerDetailList);
				result.setCode(Constant.SECCUESS);
				result.setData(consumerVo);
			}else{
				result.setCode(Constant.SECCUESS);
				result.setData(consumerVo);
			}
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("参数错误");
		}
		return result;
	}
	
	
	/**
	 * 获取 余额充值使用的明细列表
	 * 
	 * @param MemberNo
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("/getAccountsList")
	@ResponseBody
	public ResultInfo<List<AccountsVo>> getAccountsList(String memberNo, @RequestParam("pageNo") int pageNo) {
		ResultInfo<List<AccountsVo>> result = new ResultInfo<List<AccountsVo>>();
		if (memberNo != null && memberNo.trim().length() > 0) {
			Accounts account = new Accounts();
			account.setMemberNo(memberNo);
			int pageSize = 10;
			// 根据系统参数 查找对应参数来获取
			SysParam sysParamOrder = sysParamService.getByParamKey(CarConstant.orderPageSize);
			if (sysParamOrder != null) {
				pageSize = Integer.parseInt(sysParamOrder.getParamValue());
			}
			Query q = new Query(pageNo, pageSize, account);
			try {
				PageFinder<Accounts> finder = accountsService
						.getAccountsPagedList(new Query(pageNo, pageSize, account));
				if (null != finder && finder.getData() != null && finder.getData().size() > 0) {
					List<AccountsVo> datas = new ArrayList<AccountsVo>();
					for (Accounts item : finder.getData()) {
						AccountsVo current = new AccountsVo();
						// BeanUtils.copyProperties(current,item);
						current.setAccountBeforeMoney(item.getAccountBeforeMoney());
						current.setAccountMoney(item.getAccountMoney());
						current.setAccountNo(item.getAccountNo());
						current.setAccountOverMoney(item.getAccountOverMoney());
						current.setAccountType(item.getAccountType());
						current.setBusinessNo(item.getBusinessNo());
						current.setBusinessType(item.getBusinessType());
						current.setAccountTime(ECDateUtils.formatDate(item.getAccountTime()));
						current.setMemberNo(item.getMemberNo());
						datas.add(current);
					}
					result.setData(datas);
					result.setCode(OrderConstant.success_code);
					result.setMsg("查询成功");
				} else {
					result.setCode(OrderConstant.fail_code);
					result.setMsg("您还没有交易明细噢，快去充值用车吧！");
				}
			} catch (Exception e) {
				result.setCode(OrderConstant.fail_code);
				result.setMsg("查询出现了异常，请稍后再试");
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 我的套餐 获取可用套餐列表 查询条件：memberNo
	 */
	@RequestMapping("/availablePackageList")
	@ResponseBody
	public ResultInfo<List<PricingPackOrderVO>> availablePackageList(String memberNo,
			@RequestParam("pageNo") int pageNo) throws ParseException {
		int pageSize = 10;
		ResultInfo<List<PricingPackOrderVO>> results = new ResultInfo<List<PricingPackOrderVO>>();
		if (memberNo != null && memberNo.trim().length() > 0) {
			// 根据系统参数 查找对应参数来获取
			SysParam sysParamOrder = sysParamService.getByParamKey(CarConstant.orderPageSize);
			if (sysParamOrder != null) {
				pageSize = Integer.parseInt(sysParamOrder.getParamValue());
			}
			ResultInfo<List<PricingPackOrder>> list = memberService.availablePackageList(memberNo, pageNo, pageSize);
			results = pricingPackOrderVO(results, list);
		} else {
			results.setCode(CarConstant.fail_code);
			results.setMsg(CarConstant.fail_paramters);
		}

		return results;

	}

	/**
	 * 我的套餐 获取不可用（包含失效）套餐列表 查询条件：memberNo
	 */
	@RequestMapping("/disablePackageList")
	@ResponseBody
	public ResultInfo<List<PricingPackOrderVO>> disablePackageList(String memberNo, @RequestParam("pageNo") int pageNo)
			throws ParseException {
		int pageSize = 10;
		ResultInfo<List<PricingPackOrderVO>> results = new ResultInfo<List<PricingPackOrderVO>>();
		if (memberNo != null && memberNo.trim().length() > 0) {
			// 根据系统参数 查找对应参数来获取
			SysParam sysParamOrder = sysParamService.getByParamKey(CarConstant.orderPageSize);
			if (sysParamOrder != null) {
				pageSize = Integer.parseInt(sysParamOrder.getParamValue());
			}
			ResultInfo<List<PricingPackOrder>> list = memberService.disablePackageList(memberNo, pageNo, pageSize);
			results = pricingPackOrderVO(results, list);
		} else {
			results.setCode(CarConstant.fail_code);
			results.setMsg(CarConstant.fail_paramters);
		}
		return results;

	}

	/**
	 * 将按照 特定的 转换成VO对象 返回给 手机端
	 * 
	 * @param results
	 * @param list
	 * @return
	 */
	private ResultInfo<List<PricingPackOrderVO>> pricingPackOrderVO(ResultInfo<List<PricingPackOrderVO>> results,
			ResultInfo<List<PricingPackOrder>> list) {
		if (list.getData() != null && list.getData().size() > 0) {
			List<PricingPackOrderVO> pricingPackOrderVOS = new ArrayList<PricingPackOrderVO>();
			List<PricingPackOrder> listp = list.getData();
			for (PricingPackOrder ppo : listp) {
				PricingPackOrderVO ppv = new PricingPackOrderVO();
				ppv.setPackOrderNo(ppo.getPackOrderNo());
				ppv.setPackageName(ppo.getPackageName());
				ppv.setPackAmount(ppo.getPackAmount());
				ppv.setPackMinute(ppo.getPackMinute());
				ppv.setResidueMinute(ppo.getPackMinute() - ppo.getUserdMinute());
				PricingPackage pricingPackage = pricingPackageService.getPricingPackage(ppo.getPackageId());
				if (pricingPackage != null) {
					ppv.setDeductionCeiling(pricingPackage.getDeductionCeiling());
				}
				ppv.setVailableTime2(ECDateUtils.formatStringDate(ppo.getVailableTime2()));
				ppv.setIsAvailable(ppo.getIsAvailable());
				pricingPackOrderVOS.add(ppv);
			}
			results.setCode("1");
			results.setData(pricingPackOrderVOS);
		} else {
			results.setCode("0");
			results.setMsg("没有套餐");
		}
		return results;
	}

	/**
	 * 我的优惠卷 获取可用优惠卷：memberNo
	 */
	@RequestMapping("/couponList")
	@ResponseBody
	public ResultInfo<List<CouponVo>> couponList(String memberNo, @RequestParam("pageNo") int pageNo,Integer type)
			throws ParseException {
		int pageSize = 10;
		ResultInfo<List<CouponVo>> results = new ResultInfo<List<CouponVo>>();
		if (memberNo != null && memberNo.trim().length() > 0) {
			// 根据系统参数 查找对应参数来获取
			SysParam sysParamOrder = sysParamService.getByParamKey(CarConstant.orderPageSize);
			if (sysParamOrder != null) {
				pageSize = Integer.parseInt(sysParamOrder.getParamValue());
			}
			ResultInfo<List<Coupon>> list = memberService.memberCouponList(memberNo, pageNo, pageSize,type);
			results = couponListVO(results, list);
		} else {
			results.setCode(CarConstant.fail_code);
			results.setMsg(CarConstant.fail_paramters);
		}

		return results;

	}

	/**
	 * 将按照 特定的 转换成VO对象 返回给 手机端
	 * 
	 * @param results
	 * @param list
	 * @return
	 */
	private ResultInfo<List<CouponVo>> couponListVO(ResultInfo<List<CouponVo>> results, ResultInfo<List<Coupon>> list) {
		if (list.getData() != null && list.getData().size() > 0) {
			List<CouponVo> cpVOS = new ArrayList<CouponVo>();
			List<Coupon> listp = list.getData();
			for (Coupon cps : listp) {
				CouponVo cop = new CouponVo();
				cop.setCouponNo(cps.getCouponNo());
				cop.setTitle(cps.getTitle());
				if (cps.getDiscount() != null) {
					cop.setAmount(cps.getDiscount());
				} else {
					cop.setAmount(cps.getDiscountAmount());
				}
				cop.setVailableTime2(ECDateUtils.formatDate(cps.getVailableTime2()));
				cop.setCouponMethod(cps.getCouponMethod());
				Double m = 0d;
				String memo = "";
				CouponPlan couponPlan = couponPlanService.getCouponPlan(cps.getPlanNo());
				if (couponPlan != null) {
					if (cps.getCouponMethod() == 1) {// 打折
						m = couponPlan.getDiscountMaxAmount();
						memo = "折扣封顶" + m + ",限已开通城市";
					} else {// 满减
						m = couponPlan.getConsumeAmount();
						memo = "满" + m + "可用,限已开通城市";
					}

					if (couponPlan.getDiscount() != null) {
						cop.setDiscount(couponPlan.getDiscount());
					} else {
						cop.setDiscount(0d);
					}
				}

				cop.setMemo(memo);

				cpVOS.add(cop);
			}
			results.setCode("1");
			results.setData(cpVOS);
		} else {
			results.setCode("0");
			results.setMsg("没有优惠卷可用");
		}
		return results;
	}

	// 兑换优惠卷
	@RequestMapping("/getMyChangeCoupon")
	@ResponseBody
	public ResultInfo<RedeemCode> getMyDetailInvoice(String memberNo, String redCode) {
		ResultInfo<RedeemCode> result = new ResultInfo<RedeemCode>();
		if (redCode != null) {
			Operator operator = new Operator();
			result = redeemCodeService.redeemCoupon(redCode, memberNo, operator);
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("兑换失败");
		}

		return result;

	}
	
	@RequestMapping("/getTriggerEvent")
	@ResponseBody
	public ResultInfo<RedeemCode> getTriggerEvent(String memberNo, String type) {
		ResultInfo<RedeemCode> result = new ResultInfo<RedeemCode>();
		//http://182.92.194.70:9090/
		String url="";
		String eventNo="";
		if (type != null && !"".equals(type)) {
			if("1".equals(type)){//注册
				eventNo="1";
			}else if("2".equals(type)){//登录
				eventNo="2";
			}
			url=trigger+"marketingEvent/eventHandler?eventNo="+eventNo+"&memberNo="+memberNo;
			 try {
				String result1 =HttpURLRequestUtil.doMsgGet(url);
				result.setCode("1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setCode("1");
				return result;
			}
			
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("");
			return result;

		}
		
		return result;

	}

	/**
	 * 上传会员基础信息 旧接口
	 * 
	 * @param memberNo
	 *            会员编号
	 * @param idCard
	 *            身份证
	 * @param memberName
	 *            会员名称
	 * @return
	 */
	@RequestMapping("/uploadLicense")
	@ResponseBody
	public ResultInfo<String> uploadLicense(String memberNo, String idCard, String memberName) {
		return memberService.uploadLicense(memberNo, idCard, memberName);
	}

	
	// 邀请码分享
				@RequestMapping("/memberInvite")
				@ResponseBody
				public ResultInfo<InviteVo> memberInvite(String memberNo) {
					ResultInfo<InviteVo> result=new ResultInfo<InviteVo>();
					if (memberNo != null) {
						Member member = memberService.getMember(memberNo);
						if(member != null){
							InviteVo io=new InviteVo();
							result.setData(io);
							result.getData().setInvitationCode(member.getInvitationCode());
							result.getData().setUrl(imgPath+"/share_photo/"+"invite_code0.png");
							result.getData().setShareTitle("来自"+member.getMemberName()+"邀请");
							result.getData().setIconUrl(imgPath + "/share_photo/" + "app_icon.png");
							result.getData().setShareUrl(image + "/" + "welcomeJoin.html?refereeId="+member.getMemberNo()+"&invitationCode="+member.getInvitationCode());
							SysParam memos= sysParamService.getByParamKey("memo");
							if(memos != null){
								result.getData().setMemo(memos.getParamValue());
							}else{
								result.getData().setMemo("");
							}
							SysParam ms= sysParamService.getByParamKey("shareContent");
							if(ms != null){
								result.getData().setShareContent(member.getMemberName()+ms.getParamValue());
							}else{
								result.getData().setShareContent(member.getMemberName()+"");
							}
						
							result.getData().setIsFinish("1");
							result.setCode("1");
						}
					} else {
						result.setCode(Constant.FAIL);
						result.setMsg("用户不存在");
					}

					return result;

				}
		
	
	/**
	 * 上传会员基础信息
	 * 
	 * @param memberNo
	 *            会员编号
	 * @param idCard
	 *            身份证
	 * @param memberName
	 *            会员名称
	 * @return
	 */
	@RequestMapping("/authenticationMember")
	@ResponseBody
	public ResultInfo<Map<String, Object>> authenticationMember(String idCard, String memberName, String licenseId) {
		ResultInfo<Map<String, Object>> result = new ResultInfo<Map<String, Object>>();
		Map <String, Object> m = new HashMap<String, Object>();
		try {
			if (licenseId != null && !"".equals(licenseId)) {
				// 驾照认证
				Map<String, Object> data2 = authenticationService.validationCardCar(idCard, licenseId);
				 String msg = (String) data2.get("msg"); 
				 String code = (String) data2.get("code");  
				if (code != null && code.equals("0")) {
					 String enddate = (String) data2.get("enddate");
					result.setCode(MemberConstant.success_code);
					String validCode = ECRandomUtils.getRandomNumberStr(4);
					cacheUtil.set(idCard.trim(), validCode, 600);
					m.put("enddate", enddate);
					m.put("validCode", validCode);
					result.setData(m);
					return result;
				} else {
					result.setCode(MemberConstant.fail_code);
					result.setMsg(msg);
					return result;
				}
				
			} else {
				// 身份证验证
				Map<String, Object> data = authenticationService.validationCardMember(memberName, idCard);
				if (data != null && data.size()>0) {
					result.setCode(MemberConstant.fail_code);
					 String msg = (String) data.get("msg");  
					result.setMsg(msg);
					return result;
				} else {
					result.setCode(MemberConstant.success_code);
					result.setData(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;

	}

	/**
	 * 上传会员基础信息
	 * 
	 * @param memberNo
	 *            会员编号
	 * @param idCard
	 *            身份证
	 * @param memberName
	 *            会员名称
	 * @return
	 */
	@RequestMapping("/uploadLicenseAndUrl")
	@ResponseBody
	public ResultInfo<String> uploadLicenseAndUrl(String memberNo, String idCard, String memberName, String pathUrl1,
			String pathUrl2, String type, String validCode, String licenseId,String paperName,String paperUrl,String enddate) {
		String imgPaths = imgPath;
		ResultInfo<String> result = new ResultInfo<String>();
		// SysParam sysParam=sysParamService.getByParamKey("Authentication");
		// if (sysParam != null && sysParam.getParamValue() != null &&
		// !"".equals(sysParam.getParamValue())) {
		// String[] arr = sysParam.getParamValue().split(",");
		// for (int i = 0; i < arr.length; i++) {
		if(type != null && !"".equals(type)){
			if (type.equals("1")) {
				if (validCode != null && !"".equals(validCode)) {
					String regCode = cacheUtil.get(idCard);
					if (validCode.equals(regCode)) {
						return memberService.uploadLicenseAndUrl(memberNo, idCard, memberName, pathUrl1, pathUrl2, imgPaths,
								licenseId, type,paperName,paperUrl,enddate);
					} else {
						result.setCode(MemberConstant.fail_code);
						result.setMsg("提交失败,请重新认证!");
						return result;
					}
				} else {
					result.setCode(MemberConstant.fail_code);
					result.setMsg("验证码未获取");
					return result;
				}

			}
		}
		

		return memberService.uploadLicenseAndUrl(memberNo, idCard, memberName, pathUrl1, pathUrl2, imgPaths,  licenseId,
				type,paperName,paperUrl,enddate);
	}

	/**
	 * 获取状态（认证状态、交押金状态）
	 */
	@RequestMapping("/getStatus")
	@ResponseBody
	public ResultInfo<MemberStatusVO> getStatus(String memberNo) {

		ResultInfo<MemberStatusVO> result = new ResultInfo<MemberStatusVO>();
		Member member = memberService.getMember(memberNo);
		Query q = new Query();
		DepositOrder dOrder = new DepositOrder();
		dOrder.setMemberNo(memberNo);
		dOrder.setPayStatus(1);// 已支付
		dOrder.setIsAvailable(1);
		q.setQ(dOrder);
		List<DepositOrder> dOrderPay = depositOrderService.getDepositOrderList(q);
		return ToMemberStatusVO(member, dOrderPay, result);
	}

	private ResultInfo<MemberStatusVO> ToMemberStatusVO(Member member, List<DepositOrder> dOrderPay,
			ResultInfo<MemberStatusVO> result) {
		MemberStatusVO vo = new MemberStatusVO();
		result.setCode(MemberConstant.success_code);
		result.setMsg("");
		if (member != null && member.getCensorStatus() != null) {
			vo.setCensorStatus(member.getCensorStatus());
			ResultInfo<Deposit> rDeposit = depositOrderService.getDepositByMemberNo(member.getMemberNo(),null);
			if (rDeposit.getCode().equals("1")) {
				vo.setDepositStatus(rDeposit.getData().getDepositStatus());
			}
			if (member.getIsBlacklist() == 1) {
				vo.setUserIllegal("0");
			} else if (member.getIsBlacklist() == 0) {
				vo.setUserIllegal("1");
			}
		} else {
			vo.setCensorStatus(-1);
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);//默认押金
		if(sysParam != null && sysParam.getParamValue().equals("0")){
			vo.setDepositStatus(1);
		}
		result.setData(vo);
		return result;
	}

	/**
	 * 获取会员的邀请码
	 */
	@RequestMapping("getInvitationCodeByMemberNo")
	@ResponseBody
	public ResultInfo<String> getInvitationCodeByMemberNo(String memberNo) {
		ResultInfo<String> res = new ResultInfo<>();
		if ("".equals(memberNo) || null == memberNo) {
			res.setCode(Constant.FAIL);
			res.setMsg("参数为空");
			return res;
		}
		Member member = memberService.getMember(memberNo);
		if (member == null) {
			res.setCode(Constant.FAIL);
			res.setMsg("会员不存在");
			return res;
		}
		String invitationCode = member.getInvitationCode();
		res.setCode(Constant.SECCUESS);
		res.setData(invitationCode);
		return res;
	}

	/**
	 * 芝麻信用回调地址
	 */
	@RequestMapping("callbackForZhima")
	public String callbackForZhima(String params,String sign, ModelMap model) {
		Map<String,Object> returnParam = new HashMap<String,Object>();
		try {
			Map<String,String> map = ZhimaUtil.parseFromReturn(params,sign);
			
			if(map != null && map.get("open_id") != null  && map.get("state") != null){
				
				MemberZhimaScore memberZhimaScore = new MemberZhimaScore();
				memberZhimaScore.setMemberNo(map.get("state").toString());
				memberZhimaScore.setOpenId(map.get("open_id").toString());
				
				try {
					ResultInfo<MemberZhimaScore>  getScoreResult = memberZhimaScoreService.getScoreForZhiMa(memberZhimaScore);
					if(getScoreResult.getCode().equals(Constant.SECCUESS)){
						memberZhimaScore = getScoreResult.getData();
						if(memberZhimaScore.getScore() != null){
							returnParam.put("code",Constant.SECCUESS);
							returnParam.put("data",memberZhimaScore.getScore());
							returnParam.put("msg", "授权成功");
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//错误信息统一返回"系统错误"
		if(returnParam.get("code") == null || returnParam.get("code").equals(Constant.FAIL)){
			returnParam.put("code",Constant.FAIL);//预设默认值
			returnParam.put("data", 0);
			returnParam.put("msg","系统错误");
		}
		String reuslt = JSONObject.toJSONString(returnParam);
		//reuslt = "jsCallNative("+reuslt+")";
		model.addAttribute("result",reuslt);
		return "/member/zhima_callback_page";
	}
	

	/**
	 * 跳转会员芝麻授权页面
	 * @param memberNo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("getZhimaAuthorizePageUrl")
	@ResponseBody
	public ResultInfo<String> getZhimaAuthorizePageUrl(String memberNo) throws ParseException {
		ResultInfo<String> result = new ResultInfo<String>();
		Member member = memberService.getMember(memberNo);
		if(member != null){
			
			if(member.getMemberName() == null || member.getMemberName().equals("")){
				result.setCode(Constant.FAIL);
				result.setMsg("会员名称为空！");
				return result;
			}
			
			if(member.getIdCard() == null || member.getIdCard().equals("")){
				result.setCode(Constant.FAIL);
				result.setMsg("会员手机号为空！");
				return result;
			}
			try {
				ZhimaAuthInfoAuthqueryResponse response = ZhimaUtil.getOpenId(member.getIdCard(), member.getMemberName());
				if(response.getErrorCode() != null){
					//个人信息暂时无法使用芝麻信用服务
					result.setCode(Constant.FAIL);
					result.setMsg("现个人信息暂时无法使用芝麻信用服务");
					return result;
				}else{
					if(response.getAuthorized() != null && response.getAuthorized().booleanValue() == false){
						//需要授权，返回授权页面
						String url = ZhimaUtil.getAuthorizePageUrl(member.getMemberNo(),member.getIdCard(), member.getMemberName());
						if(url != null && !url.equals("")){
							result.setCode("1");
							result.setData(url);
							return result;
						}else{
							result.setCode(Constant.FAIL);
							result.setMsg("调用芝麻授权失败！");
						}
						
					}else{
						//直接查询信用分
						MemberZhimaScore memberZhimaScore = new MemberZhimaScore();
						memberZhimaScore.setMemberNo(memberNo);
						memberZhimaScore.setOpenId(response.getOpenId());
						ResultInfo<MemberZhimaScore>  getScoreResult = memberZhimaScoreService.getScoreForZhiMa(memberZhimaScore);
						if(getScoreResult.getCode().equals(Constant.SECCUESS)){
							memberZhimaScore = getScoreResult.getData();
							if(memberZhimaScore.getScore() != null){
								result.setCode("2");
								result.setData(""+memberZhimaScore.getScore());
								return result;
							}
						}else{
							result.setCode(Constant.FAIL);
							result.setMsg("获取芝麻信用分失败！");
						}
					}
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg("系统错误！");
			}
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("会员不存在！");
		}
		return result;
	}
	
	/**
	 * 得到会员芝麻分
	 * @param memberNo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("getMemberZhimaScore")
	@ResponseBody
	public ResultInfo<Map<String,Object>> getMemberZhimaScore(String memberNo) throws ParseException {
		ResultInfo<Map<String,Object>> resultInfo = new ResultInfo<Map<String,Object>>();
		Member member = memberService.getMember(memberNo);
		if(member != null){
			resultInfo.setCode(Constant.SECCUESS);
			Map<String,Object> reusltData = new HashMap<String,Object>();
			reusltData.put("authorizeStatus", 0);
			reusltData.put("score", 0);
			MemberZhimaScore memberZhimaScore = memberZhimaScoreService.getMemberZhimaScore(memberNo);
			if(memberZhimaScore != null){
				if(memberZhimaScore.getScore() != null){
					reusltData.put("authorizeStatus", 1);//认证成功
					reusltData.put("score", memberZhimaScore.getScore());
				}
			}
			resultInfo.setData(reusltData);
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("用户不存在！");
		}
		
		return resultInfo;
	}
	
	/**
	 * 获取认证状态
	 */
	@RequestMapping("/getCensorStatus")
	@ResponseBody
	public ResultInfo<Integer> getCensorStatus(String memberNo) {
		return memberService.getCensorStatus(memberNo);
	}

	/**
	 * 查看积分进度
	 */
	@RequestMapping("/integral")
	@ResponseBody
	public ResultInfo<MemberPointVo> integral(String memberNo) {
		ResultInfo<MemberPointVo> resultInfo = new ResultInfo<MemberPointVo>();
		MemberPointVo mp = new MemberPointVo();
		if (memberNo != null && !memberNo.equals("")) {
			mp.setMemberNo(memberNo);
			Member member = memberService.getMember(memberNo);
			// 获取会员的当前积分
			mp.setMemberPoint(member.getMemberPointsValues());
			// mp.setMemberPoint(memberPointsRecordService.getPointsByMemberNo(memberNo));
			// 获取会员当前等级的积分
			mp.setNowLevelPoint(memberLevelService.getNowLevelPoints(mp.getMemberPoint()));
			// 获取会员当前等级的名称
			if (member.getMemberLevelId() != null && !member.getMemberLevelId().equals("")) {
				MemberLevel memberLevel = memberLevelService.getMemberLevel(member.getMemberLevelId());
				if (null != memberLevel && null != memberLevel.getLevelName()
						&& !"".equals(memberLevel.getLevelName())) {
					mp.setLevelName(memberLevel.getLevelName());
					Integer points = memberLevelService.getNextLevelPoints(memberLevel.getUpgradePoint());
					if (null != points) {
						mp.setNextLevelPoint(points.intValue());
						// 根据下个等级的积分 查找出 下个等级名称
						MemberLevel memberLevels = memberLevelService.getMemberLevelNex(mp.getNextLevelPoint());
						if (memberLevels != null) {
							mp.setNexeLevelName(memberLevels.getLevelName());
						}
					} else {
						mp.setNexeLevelName("未知");
					}
				} else {
					mp.setLevelName("未知");
				}
			}

			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(mp);
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
		}
		return resultInfo;
	}

	/**
	 * 申请加盟
	 */
	@RequestMapping("/applyLeague")
	@ResponseBody
	public ResultInfo<String> applyLeague(String memberNo) {
		ResultInfo<String> result = new ResultInfo<>();
		Member member = memberService.getMember(memberNo);
		if (member.getIsJoined() == 0) {
			// 未加盟，那就去修改申请加盟
			member.setIsJoined(2);
			ResultInfo<Member> rs = new ResultInfo<>();
			rs = memberService.updateMember(member, null);
			// 申请成功
			if (rs.getCode().equals("1")) {
				result.setCode(MemberConstant.join_success_code);
				result.setMsg(MemberConstant.join_success_msg);
			} else {
				// 申请不成功
				result.setCode(MemberConstant.fail_code);
				result.setMsg(MemberConstant.fail_msg);
			}
		} else if (member.getIsJoined() == 1) {
			// 已加盟
			result.setCode(MemberConstant.joined_code);
			result.setMsg(MemberConstant.joined_msg);
		} else {
			// 加盟申请中
			result.setCode(MemberConstant.joined_shengqi_code);
			result.setMsg(MemberConstant.joined_shengqi_msg);
		}
		return result;
	}

	/**
	 * 修改昵称
	 */
	@RequestMapping("/updateNickname")
	@ResponseBody
	public ResultInfo<String> updateNickname(String memberNo, String nickName) {
		ResultInfo<String> result = new ResultInfo<>();
		// 通过会员id得到会员对象
		Member member = memberService.getMember(memberNo);
		member.setMemberNick(nickName);
		ResultInfo<Member> rs = memberService.updateMember(member, null);
		if (rs.getData().getMemberNick().equals(nickName)) {
			result.setCode(MemberConstant.success_code);
			result.setMsg("");
			result.setData(nickName);
		} else {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 方法说明：上传头像
	 */
	@RequestMapping("/uploadHeadPhoto")
	@ResponseBody
	public ResultInfo<String> uploadHeadPhoto(String memberNo, String memberPhotoUrl) {
		ResultInfo<Member> result = new ResultInfo<Member>();
		Member member = memberService.getMember(memberNo);
		// app端传来的图片进行截取
		if (memberPhotoUrl != null && !memberPhotoUrl.equals("")) {
			SysParam fd = sysParamService.getByParamKey("is_fastdfs");
			if (fd.getParamValue().equals("1")) {
				member.setMemberPhotoUrl(memberPhotoUrl);
			} else {
				String pathUrl11 = memberPhotoUrl.substring(imgPath.length() + 1);
				member.setMemberPhotoUrl(pathUrl11);
			}

		}
		result = memberService.updateMember(member, getOperator());
		ResultInfo<String> rs = new ResultInfo<String>();
		if (member != null) {
			// 只传送头像地址就行
			rs.setData(imgPath + "/" + result.getData().getMemberPhotoUrl());
			rs.setCode(MemberConstant.success_code);
		} else {
			rs.setCode(MemberConstant.fail_code);
			rs.setMsg(MemberConstant.fail_msg);
		}
		return rs;
	}

	/**
	 * 方法说明：根据会员ID得到会员对象 ，并返回自定义的memberVo对象
	 */
	@RequestMapping("/credential")
	@ResponseBody
	public ResultInfo<MemberVo> getMemberofCredential(String memberNo) {

		// 根据memberIdid得到 member对象
		Member member = memberService.getMember(memberNo);
		ResultInfo<MemberVo> result = new ResultInfo<MemberVo>();

		if (member != null) {
			MemberVo vo = new MemberVo();
			vo.setMemberNo(member.getMemberNo());
			vo.setMemberName(member.getMemberName());
			vo.setCensorStatus(member.getCensorStatus());
			// 获取会员当前等级的名称
			if (member.getMemberLevelId() != null && !member.getMemberLevelId().equals("")) {
				MemberLevel memberLevel = memberLevelService.getMemberLevel(member.getMemberLevelId());
				if (null != memberLevel && null != memberLevel.getLevelName()
						&& !"".equals(memberLevel.getLevelName())) {
					vo.setLevelName(memberLevel.getLevelName());
					// 下个等级的积分
					Integer points = memberLevelService.getNextLevelPoints(memberLevel.getUpgradePoint());
					if (null != points) {
						vo.setNextLevelPoint(points.intValue());
						// 根据下个等级的积分 查找出 下个等级名称
						MemberLevel memberLevels = memberLevelService.getMemberLevelNex(vo.getNextLevelPoint());
						if (memberLevels != null) {
							vo.setNexLevelName(memberLevels.getLevelName());
						} else {
							vo.setNexLevelName("未知");
						}
					} else {
						vo.setNexLevelName("未知");
					}
				} else {
					vo.setLevelName("未知");
				}
			}
			result.setData(vo);
			result.setCode(MemberConstant.success_code);
			result.setMsg("");
		} else {

			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 方法说明:根据会员ID得到会员对象 ，传值memberId
	 */
	@RequestMapping("/personalCenter")
	@ResponseBody
	public ResultInfo<MemberVo> personalCenter(String memberNo) {

		// 根据memberIdid得到 member对象
		Member member = memberService.getMember(memberNo);
		ResultInfo<MemberVo> result = new ResultInfo<MemberVo>();

		if (member != null) {
			MemberVo memberVo = new MemberVo();
			// 判断会员的审核状态（对驾驶证有效期进行校验，超过有效期，则修改审核状态为0，重新上传驾驶证图片）
			if (member.getCensorStatus().equals(1)) {
				if (member.getExpirationDate().getTime() < new Date().getTime()) {
					member.setCensorStatus(0);
					memberService.updateMember(member, null);
				}
			}
			memberVo.setCensorStatus(member.getCensorStatus());
			memberVo.setMemberLevelId(member.getMemberLevelId());
			memberVo.setMemberName(member.getMemberName());
			memberVo.setMemberNick(member.getMemberNick());
			memberVo.setMemberNo(member.getMemberNo());
			memberVo.setMemberPhotoUrl(imgPath + "/" + member.getMemberPhotoUrl());
			memberVo.setMobilePhone(member.getMobilePhone());
			memberVo.setIdCard(member.getIdCard());
			memberVo.setIdCardPhotoUrl(imgPath + "/" + member.getIdCardPhotoUrl());
			memberVo.setDriverLicensePhotoUrl1(imgPath + "/" + member.getDriverLicensePhotoUrl1());
			memberVo.setDriverLicensePhotoUrl2(imgPath + "/" + member.getDriverLicensePhotoUrl2());
			// 获取会员当前等级的名称
			if (member.getMemberLevelId() != null && !member.getMemberLevelId().equals("")) {
				MemberLevel memberLevel = memberLevelService.getMemberLevel(member.getMemberLevelId());
				if (null != memberLevel && null != memberLevel.getLevelName()
						&& !"".equals(memberLevel.getLevelName())) {
					memberVo.setLevelName(memberLevel.getLevelName());
					// 下个等级的积分
					Integer points = memberLevelService.getNextLevelPoints(memberLevel.getUpgradePoint());
					if (null != points) {
						memberVo.setNextLevelPoint(points.intValue());
						// 根据下个等级的积分 查找出 下个等级名称
						MemberLevel memberLevels = memberLevelService.getMemberLevelNex(memberVo.getNextLevelPoint());
						if (memberLevels != null) {
							memberVo.setNexLevelName(memberLevels.getLevelName());
						} else {
							memberVo.setNexLevelName("未知");
						}
					} else {
						memberVo.setNexLevelName("未知");
					}
				} else {
					memberVo.setLevelName("未知");
				}
			}
			// Double residueDeposit =
			// depositOrderService.getAmountByMemberNo(member.getMemberNo());
			Deposit obj = depositOrderDao.getDepositByMemberNo(memberNo);
			// 剩余押金
			if (obj != null && obj.getAvailableAmount() != null) {
				memberVo.setDepositAmount(obj.getAvailableAmount());
			} else {
				memberVo.setDepositAmount(0.0);
			}

			Query q = new Query();
			PricingPackOrder ppo = new PricingPackOrder();
			ppo.setPayStatus(1);// 已经支付的
			ppo.setIsAvailable(1);// 可用
			ppo.setNowTime(new Date());
			ppo.setMemberNo(member.getMemberNo());
			q.setQ(ppo);
			Integer minutes = 0;
			Integer alMinutes = 0;
			List<PricingPackOrder> ppoList = pricingPackOrderService.getPricingPackOrderListByUse(q);
			if (ppoList != null && ppoList.size() > 0) {
				for (PricingPackOrder pp : ppoList) {
					if (pp.getPackMinute() != null) {
						minutes = minutes + pp.getPackMinute();
						alMinutes = alMinutes + pp.getUserdMinute();
					}
				}
				// 套餐总可用时长
				memberVo.setTotalPackageAvailableMinutes(minutes - alMinutes);
				// 套餐总时长
				memberVo.setTotalPackageMinutes(minutes);
				memberVo.setPackageCount(ppoList.size());
			}
			// 我的余额
			Query qr = new Query();
			PricingPackOrder ppor = new PricingPackOrder();
			ppor.setPayStatus(1);// 已经支付的
			ppor.setIsAvailable(1);// 可用
			ppor.setNowTime(new Date());
			ppor.setMemberNo(member.getMemberNo());
			qr.setQ(ppor);
			List<PricingPackOrder> pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
			Double poa = 0.0;
			Double uoa = 0.0;
			for (PricingPackOrder pricingPackOrder : pporList) {
				if (pricingPackOrder.getPackOrderAmount() != null) {
					poa = ECCalculateUtils.add(poa, pricingPackOrder.getPackOrderAmount());
					if (pricingPackOrder.getUseredOrderAmount() != null) {
						uoa = ECCalculateUtils.add(uoa, pricingPackOrder.getUseredOrderAmount());
					}

				}
			}

			memberVo.setMemberBalance(ECCalculateUtils.sub(poa, uoa));
			// 会员当前积分
			memberVo.setMemberPoint(member.getMemberPointsValues());

			result.setData(memberVo);
			result.setCode(MemberConstant.success_code);
			result.setMsg("");
		} else {

			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 注册
	 * 
	 * @param member
	 * @param veCode
	 *            短信验证码
	 * @param invitationCode
	 *            推荐人邀请码
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public ResultInfo<MemberVo> register(Member member, String veCode, String invitationCode,String longitude,String latitude) {

		ResultInfo<MemberVo> rs = new ResultInfo<>();
		// 验证验证码
		if (veCode != null && !veCode.equals("")) {
			// 从缓存中取出验证码
			String regCode = cacheUtil.get("regCode" + member.getMobilePhone());
			if (veCode.equals(regCode)) {
				// 验证手机号是否已经存在
				ResultInfo<String> resultInfo = uniquePhone(member.getMobilePhone());

				if (resultInfo.getCode().equals(MemberConstant.phone_code)) {
					log.info("resultInfo----------------:code" + MemberConstant.phone_code);

					// 查询是否存在集团会员
					CompanyPerson companyPerson = companyPersonService.getCompanyPersonPhone(member.getMobilePhone());

					if (companyPerson != null) {
						member.setMemberType(2);
						member.setCompanyId(companyPerson.getId());
						member.setMemberName(companyPerson.getName());
						member.setIdCard(companyPerson.getIdCardNo());
					}
//					SysParam s7 = sysParamService.getByParamKey("ISDecrypt");
//					String isDecrypt = "";
//					if (s7 != null && s7.getParamValue() != null && !s7.getParamValue().equals("")) {
//						isDecrypt = s7.getParamValue();
//					}
//					if ("1".equals(isDecrypt)) {
						try {
	//						log.info("取密码:-----------------:" + member.getPassword());
							String password = AESCipher.aesDecryptString(member.getPassword());
							member.setPassword(password);
							log.info("password----------------:" + password);
						} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
								| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
								| UnsupportedEncodingException e) {
							// 系统错误
							rs.setCode(MemberConstant.fail_code);
							rs.setMsg("用户名或登录密码错误");
							log.info("前端注册1-----------" + MemberConstant.fail_msg);
							return rs;
						}
//					} else {
//						String password = ECMd5Utils.encryptMD5(member.getPassword());
//						member.setPassword(password);
//						log.info("password----------------:" + password);
//					}
					// member.setCreateTime(new Date());
					// 审核状态（认证状态）（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
					member.setCensorStatus(0);
					// 新注册用户积分值和会员等级设置
					member.setMemberPointsValues(0);
					MemberLevel ml = new MemberLevel();
					ml.setIsAvailable(1);
					ml.setIsDeleted(0);
					List<MemberLevel> mList = memberLevelService.getMemberLevelList(new Query(ml));
					if (mList != null && mList.size() > 0) {
						member.setMemberLevelId(mList.get(0).getMemberLevelId());
					}
					// 根据推荐码查询会员编号作为推荐人id存入会员表
					if (invitationCode != null && !invitationCode.equals("")) {
						Member members = memberService.getInvitationCode(invitationCode);
						if (members != null) {
							member.setRefereeId(members.getMemberNo());
						} else {
							// 邀请码不存在
							rs.setCode("5");
							return rs;
						}
					}
					// 生成会员自己的推荐码
					String memberNo = memberService.generatePK();
					member.setMemberNo(memberNo);
					String memberInvitationCode = GenerateInvitationCodeUtil.toRandomCode(Long.valueOf(memberNo));
					member.setInvitationCode(memberInvitationCode);

					ResultInfo<Member> result = memberService.addMember(member, getOperator());
					 //提交订单向AppLocation 添加数据(注册)
					 if(longitude != null && !"".equals(longitude)  && latitude != null && !"".equals(latitude)){
							AppLocation appLocation = new AppLocation();
							appLocation.setAppLatitude(Double.valueOf(latitude));
							appLocation.setAppLongitude(Double.valueOf(longitude));
							appLocation.setType(3);
							appLocation.setMemberNo(result.getData().getMemberNo());
							 try {
								//根据经纬度反查地址
								 String urlString = "http://api.map.baidu.com/geocoder/v2/?ak="+ak+"&location="+latitude+","+longitude+"&output=json&pois=0";  
								String result1 = HttpURLRequestUtil.doMsgGet(urlString);
								Gson gson = new Gson();
								AppLocationVOSearchBaiDu appLocationVOSearchBaiDu = gson.fromJson(result1, AppLocationVOSearchBaiDu.class);
								if(appLocationVOSearchBaiDu != null && appLocationVOSearchBaiDu.getStatus()==0 && appLocationVOSearchBaiDu.getResult() != null ){
									 appLocation.setAddrStreet( appLocationVOSearchBaiDu.getResult().getFormatted_address());
									AddressComponent addressComponent =appLocationVOSearchBaiDu.getResult().getAddressComponent();
									appLocation.setAddrRegion1Name(addressComponent.getProvince());
									SysRegion sr1=sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
									if(sr1 != null){
										appLocation.setAddrRegion1Id(sr1.getRegionId());
									}
									if(addressComponent.getDistrict()!= null && !"".equals(addressComponent.getDistrict())){
										appLocation.setAddrRegion2Name(addressComponent.getCity());
										SysRegion sr2=sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
										if(sr2 != null){
											appLocation.setAddrRegion2Id(sr2.getRegionId());
										}
										//根据找到的市区匹配数据字典
										DataDictItem ddi =dataDictItemService.getItemValue(addressComponent.getCity(), "CITY");
										if(ddi != null){
											appLocation.setCityId(ddi.getDataDictItemId());
										}
									}
									 if(addressComponent.getDistrict() != null && !"".equals(addressComponent.getDistrict())){
										 SysRegion sr2=sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
										 appLocation.setAddrRegion3Name(addressComponent.getDistrict());
										 SysRegion s= new SysRegion();
										  s.setRegionName(addressComponent.getDistrict());
										 List<SysRegion> sr3=sysRegionService.list(new Query(s));
										 if(sr3 != null && sr3.size()>0){
											 appLocation.setAddrRegion3Id(sr3.get(0).getParentId());
										 }
									 }
									
									appLocationService.addAppLocation(appLocation, getOperator());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					Member mm = result.getData();
					MemberVo vo = new MemberVo();
					vo.setMemberName(mm.getMemberName());
					vo.setMemberNick(mm.getMemberNick());
					vo.setMemberNo(mm.getMemberNo());
					vo.setMobilePhone(mm.getMobilePhone());
					vo.setMemberPhotoUrl(imgPath + "/" + mm.getMemberPhotoUrl());
					// 获取会员当前等级的名称
					if (member.getMemberLevelId() != null && !member.getMemberLevelId().equals("")) {
						MemberLevel memberLevel = memberLevelService.getMemberLevel(member.getMemberLevelId());
						if (null != memberLevel && null != memberLevel.getLevelName()
								&& !"".equals(memberLevel.getLevelName())) {
							vo.setLevelName(memberLevel.getLevelName());
							// 根据下个等级的积分 查找出 下个等级名称
							// 下个等级的积分
							Integer points = memberLevelService.getNextLevelPoints(memberLevel.getUpgradePoint());
							if (null != points) {
								vo.setNextLevelPoint(points.intValue());
								// 根据下个等级的积分 查找出 下个等级名称
								MemberLevel memberLevels = memberLevelService.getMemberLevelNex(vo.getNextLevelPoint());
								if (memberLevels != null) {
									vo.setNexLevelName(memberLevels.getLevelName());
								} else {
									vo.setNexLevelName("未知");
								}
							} else {
								vo.setNexLevelName("未知");
							}
						} else {
							vo.setLevelName("未知");
						}
					}
					rs.setData(vo);
					SysParam ssp=sysParamService.getByParamKey("preferential");
					if(ssp != null && "1".equals(ssp.getParamValue())){
//						if(mm.getRefereeId()!= null && !"".equals(mm.getRefereeId())){
							String url="";
							String url2="";
								 try {
									 url=trigger+"marketingEvent/eventHandler?eventNo=1&memberNo="+mm.getMemberNo();
									 HttpURLRequestUtil.doMsgGet(url);	
									 if(mm.getRefereeId()!= null && !"".equals(mm.getRefereeId())){
											url2=trigger+"marketingEvent/eventHandler?eventNo=3&memberNo="+mm.getRefereeId();
											HttpURLRequestUtil.doMsgGet(url2);
										}
									
									
								} catch (Exception e) {
									e.printStackTrace();
									result.setCode("1");
								}
						}
					//}
				
					rs.setCode(result.getCode());
					rs.setMsg("");
					
				} else if (resultInfo.getCode().equals(MemberConstant.success_code)) {
					// 手机号已存在
					rs.setCode(MemberConstant.phone_code);
					rs.setMsg(MemberConstant.phone_exsit_msg);
				} else if (resultInfo.getCode().equals(MemberConstant.fail_code)) {
					// 系统错误
					rs.setCode(MemberConstant.fail_code);
					rs.setMsg(MemberConstant.fail_msg);
					log.info("前端注册2-----------" + MemberConstant.fail_msg);
				}
			} else {
				// 验证码错误
				rs.setCode(MemberConstant.fail_code_code);
				rs.setMsg(MemberConstant.fail_code_msg);
			}
		} else {
			// 验证码不能为空
			rs.setCode(MemberConstant.null_code_code);
			rs.setMsg(MemberConstant.null_code_msg);
		}
		return rs;
	}

	/**
	 * 判断手机号是否唯一
	 */
	@RequestMapping("/uniquePhone")
	@ResponseBody
	public ResultInfo<String> uniquePhone(String mobilePhone) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		if (mobilePhone != null && !mobilePhone.equals("")) {
			if (memberService.getMemberByPhone(mobilePhone) != null) {
				resultInfo.setCode(MemberConstant.success_code);
				resultInfo.setMsg(MemberConstant.phone_exsit1_msg);
			} else {
				resultInfo.setCode(MemberConstant.phone_code);
				resultInfo.setMsg(MemberConstant.phone_exsit2_msg);
			}
		} else {
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg(MemberConstant.fail_msg);
		}

		return resultInfo;
	}

	/**
	 * 测试叮咚云发送短信
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/ddy")
	@ResponseBody
	public String ddysend() throws Exception {

		boolean result = sendMsgCommonInterfaceService.sendMsgPost("18220561191", "成功了！收到此短信的人是世界上最幸福的人！", "", "2");
		if (result) {
			return "发送成功";
		} else {
			return "发送失败";
		}

	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobilePhone
	 *            手机号
	 * @param time
	 *            时间戳
	 * @param sign
	 *            签名（规则为：手机号+时间戳+key+type 进行加密， 安卓与IOS的key值不同）
	 * @param type
	 *            IOS 1 安卓 2
	 * @param tag
	 *            注册页 1 忘记密码页 2 修改手机号3
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sendVerificationCode")
	@ResponseBody
	public ResultInfo<String> sendVerificationCode(String mobilePhone, String time, String sign, String type,
			String tag, HttpServletRequest request) throws Exception {

		// 定义返回结果集
		ResultInfo<String> resultInfo = new ResultInfo<>();
		// 验证时间戳
		if (null != time && time.trim().length() > 0 && !"".equals(time)) {
			String failTime = sysParamService.getByParamKey(Constant.SYS_FAILURE_TIME).getParamValue();
			Integer sysFailureTime = Integer.parseInt(failTime);
			Long reqTime = new Long(time);
			Integer diffMin = ECDateUtils.differMinutes(new Date(), new Date(reqTime)).intValue();
			Integer resu = Math.abs(diffMin);
			// 时间戳合法
			if (resu < sysFailureTime) {
				// 验证签名
				String result = "";
				// 对key进行验证 数据重新加密 若两次不匹配 说明数据被篡改 直接返回
				if ("1".equals(type)) {
					result = ECMd5Utils.hash(mobilePhone + time + type + Constant.IOS_KEY);
				} else if ("2".equals(type)) {
					result = ECMd5Utils.hash(mobilePhone + time + type + Constant.ANDROID_KEY);
				} else {
					// 返回错误 前台也做校验
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("请求参数异常，为空");
					resultInfo.setData("");
					return resultInfo;
				}

				if (null != sign && sign.equals(result)) {
					// 发送短信验证码方法
					try {
						if (StringUtils.isEmpty(mobilePhone) || StringUtils.isEmpty(tag)) {
							// 返回错误 前台也做校验
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("请求参数异常，为空");
							resultInfo.setData("");
						} else {
							// String remoteAddr = request != null ?
							// request.getRemoteAddr() : "";
							String remoteAddr = request != null ? request.getHeader("REMOTE-HOST") : null;
							if (remoteAddr == null || remoteAddr.equals("")) {
								remoteAddr = request != null ? request.getRemoteAddr() : "";
							}

							boolean isForbidden = IpForbidener.isForbidden(remoteAddr);
							if (!isForbidden) {
								Member currentMb = memberService.getMemberByPhone(mobilePhone);
								// 判断哪个页面传递的信息
								if ("1".equals(tag)) { // 注册页,判断当手机号是否已经注册
									if (null != currentMb) {
										resultInfo.setCode(MemberConstant.fail_code);
										resultInfo.setMsg(MemberConstant.phone_exsit_msg);
									} else {
										// 产生随机数字的验证码
										String validCode = ECRandomUtils.getRandomNumberStr(4);
										System.out
												.println("####### sendVerificationCode, ip:" + remoteAddr + "is Allow");
										boolean sate = false;
										try {
											// 聚合
//											 sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
//											 validCode, tplId);
											// 聚通达,浪驰
											sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
													MsgConstant.REGISTTYPE);
											// 叮咚云
											// sate =
											// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
											// validCode, "","1"); //1代表 验证码类短信
											//阿里云
//											sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
//													tplId);
											System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
										} catch (Exception e) {
											e.printStackTrace();
										}

										// 验证码保存在缓存里面,注册的验证码是120秒有效
										cacheUtil.set("regCode" + mobilePhone.trim(), validCode,
												MemberConstant.regCodeExpire);
										resultInfo.setCode(Constant.SECCUESS);
										resultInfo.setMsg("验证码已发送至您的手机，请注意查收！");
										 if (tag.equals("1")) {
							                  resultInfo.setData(validCode);
										 }
									}
								} else if ("2".equals(tag)) { // 忘记密码页
																// 判断手机号是否存在用户
									if (currentMb != null) {
										// 产生随机数字的验证码
										String validCode = ECRandomUtils.getRandomNumberStr(4);
										System.out
												.println("####### sendVerificationCode, ip:" + remoteAddr + "is Allow");
										boolean sate = false;
										try {
											// 聚合
//											 sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
//											 validCode, tplId);
											// 聚通达,浪驰
											sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
													MsgConstant.FORGETPWDTYPE);
											// 叮咚云
											// sate =
											// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
											// validCode, "","1"); //1代表 验证码类短信
											
											//阿里云
//											sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
//													tplId);
											System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
										} catch (Exception e) {
											e.printStackTrace();
										}

										// 验证码保存在缓存里面,注册的验证码是120秒有效
										cacheUtil.set("regCode" + mobilePhone.trim(), validCode,
												MemberConstant.regCodeExpire);
										resultInfo.setCode(Constant.SECCUESS);
										resultInfo.setMsg("验证码已发送至您的手机，请注意查收！");
										 if (tag.equals("1")) {
							                  resultInfo.setData(validCode);
										 }

									}
									
									else {
										resultInfo.setCode(MemberConstant.fail_code);
										resultInfo.setMsg(MemberConstant.phone_exsit3_msg);
									}
								}else if("3".equals(tag)){
									
										// 产生随机数字的验证码
										String validCode = ECRandomUtils.getRandomNumberStr(4);
										System.out.println("####### sendVerificationCode, ip:" + remoteAddr + "is Allow");
										boolean sate = false;
										try {
											// 聚合
//											 sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
//											 validCode, tplId);
											// 聚通达,浪驰
											sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
													MsgConstant.FORGETPWDTYPE);
											// 叮咚云
											// sate =
											// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
											// validCode, "","1"); //1代表 验证码类短信
											
											//阿里云
//											sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
//													tplId);
											System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
										} catch (Exception e) {
											e.printStackTrace();
										}

										// 验证码保存在缓存里面,注册的验证码是120秒有效
										cacheUtil.set("regCode" + mobilePhone.trim(), validCode,
												MemberConstant.regCodeExpire);
										resultInfo.setCode(Constant.SECCUESS);
										resultInfo.setMsg("验证码已发送至您的手机，请注意查收！");
										 if (tag.equals("1")) {
							                  resultInfo.setData(validCode);
										 }
										

									
								} 
								else {
									resultInfo.setCode(Constant.FAIL);
									resultInfo.setMsg("无法识别哪个业务流程请求验证码！");
									resultInfo.setData("");
								}
							} else {
								System.out.println("####### sendVerificationCode, ip:" + remoteAddr + "is Forbidden");
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("请勿频繁点击发送验证码，请稍后再试！");
								resultInfo.setData("");
							}
						}

					} catch (Exception e) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("程序出现未知异常");
						e.printStackTrace();
					}
				} else {
					// 签名校验失败
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("签名校验失败，请稍后再试");
					resultInfo.setData("");
				}
			} else {
				// 时间戳校验失败
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("请求超时，请重新发起请求");
				resultInfo.setData("");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请求参数异常，时间为空");
			resultInfo.setData("");
		}
		return resultInfo;
	}

	/**
	 * 登录 app传过来的密码已经进过aes和md5加密
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultInfo<MemberVo> login(Member member) {
		ResultInfo<MemberVo> resultInfo = new ResultInfo<MemberVo>();
		// SysParam s7 = sysParamService.getByParamKey("ISDecrypt");
		// String isDecrypt = "";
		// if
		// (s7!=null&&s7.getParamValue()!=null&&!s7.getParamValue().equals(""))
		// {
		// isDecrypt = s7.getParamValue();
		// }
		String passwordN = "";
		// 加密
		try {
			String password = AESCipher.aesDecryptString(member.getPassword());
			member.setPassword(password);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg("用户名或密码错误");
			return resultInfo;
		}
		passwordN = member.getPassword();

		Member mm = memberService.getMemberByPhone(member.getMobilePhone());
		if (mm != null) {
			if (mm.getPassword().equals(passwordN)) {
				String token = TokenGeneratorUtil.tokenGenerator();
				mm.setToken(token);
				mm.setTokenGenerateTime(new Date());
				mm.setClientId(member.getClientId());
				memberService.updateMember(mm, null);
				resultInfo.setCode(Constant.SECCUESS);
				MemberVo vo = new MemberVo();
				vo.setToken(token);
				vo.setMemberName(mm.getMemberName());
				vo.setMemberNick(mm.getMemberNick());
				vo.setMemberNo(mm.getMemberNo());
				vo.setMobilePhone(mm.getMobilePhone());
				vo.setMemberLevelId(mm.getMemberLevelId());
				vo.setMemberPhotoUrl(imgPath + "/" + mm.getMemberPhotoUrl());
				vo.setIdCard(mm.getIdCard());
				vo.setIdCardPhotoUrl(imgPath + "/" + mm.getIdCardPhotoUrl());
				vo.setDriverLicensePhotoUrl1(imgPath + "/" + mm.getDriverLicensePhotoUrl1());
				vo.setDriverLicensePhotoUrl2(imgPath + "/" + mm.getDriverLicensePhotoUrl2());
				// 审核状态（认证状态）（0、未审核/未认证， 1、已审核/已认证， 2、待审核/待认证， 3、未通过 默认0）
				// 判断会员的审核状态（对驾驶证有效期进行校验，超过有效期，则修改审核状态为0，重新上传驾驶证图片）
				if (mm.getCensorStatus().equals(1)) {
					if (mm.getExpirationDate() == null || mm.getExpirationDate().getTime() < new Date().getTime()) {
						mm.setCensorStatus(0);
						memberService.updateMember(mm, null);
					}
				}
				vo.setCensorStatus(mm.getCensorStatus());
				// 获取会员当前等级的名称
				if (mm.getMemberLevelId() != null && !mm.getMemberLevelId().equals("")) {
					MemberLevel memberLevel = memberLevelService.getMemberLevel(mm.getMemberLevelId());
					if (null != memberLevel && null != memberLevel.getLevelName()
							&& !"".equals(memberLevel.getLevelName())) {
						vo.setLevelName(memberLevel.getLevelName());
						// 下个等级的积分
						Integer points = memberLevelService.getNextLevelPoints(memberLevel.getUpgradePoint());
						if (null != points) {
							vo.setNextLevelPoint(points.intValue());
							// 根据下个等级的积分 查找出 下个等级名称
							MemberLevel memberLevels = memberLevelService.getMemberLevelNex(vo.getNextLevelPoint());
							if (memberLevels != null) {
								vo.setNexLevelName(memberLevels.getLevelName());
							} else {
								vo.setNexLevelName("未知");
							}
						} else {
							vo.setNexLevelName("未知");
						}
					} else {
						vo.setLevelName("未知");
					}
				}
				Query q = new Query();
				PricingPackOrder ppo = new PricingPackOrder();
				ppo.setPayStatus(1);// 已经支付的
				ppo.setIsAvailable(1);// 可用
				ppo.setNowTime(new Date());
				ppo.setMemberNo(mm.getMemberNo());
				q.setQ(ppo);
				List<PricingPackOrder> ppoList = pricingPackOrderService.getPricingPackOrderListByUse(q);
				Integer minutes = 0;
				Integer alMinutes = 0;
				for (PricingPackOrder pp : ppoList) {
					if (pp.getPackMinute() != null) {
						minutes = minutes + pp.getPackMinute();
						alMinutes = alMinutes + pp.getUserdMinute();
					}
				}
				// 套餐总可用时长
				vo.setTotalPackageAvailableMinutes(minutes - alMinutes);
				// 套餐总时长
				vo.setTotalPackageMinutes(minutes);
				resultInfo.setData(vo);
				resultInfo.setMsg(MemberConstant.login_success_msg);
			} else {
				resultInfo.setCode(MemberConstant.password_code);
				resultInfo.setMsg(MemberConstant.login_fail_msg);
			}
		} else {
			resultInfo.setCode(MemberConstant.phone_code);
			resultInfo.setMsg(MemberConstant.login_fail_msg);
		}
		return resultInfo;
	}

	/**
	 * 忘记密码，修改密码
	 */
	@RequestMapping("/forgetPassword")
	@ResponseBody
	public ResultInfo<Member> forgetPassword(Member member, String veCode) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		if (member.getMobilePhone() != null && !"".equals(member.getMobilePhone())) {
			Member memberPhone = memberService.getMemberByPhone(member.getMobilePhone());
			if (memberPhone != null) {
				// 从缓存中取出验证码
				String regCode = cacheUtil.get("regCode" + member.getMobilePhone());
				if (veCode != null && !veCode.equals("")) {
					if (veCode.equals(regCode)) {
						if (memberService.getMemberByPhone(member.getMobilePhone()) != null) {
							Member m = memberService.getMemberByPhone(member.getMobilePhone());
							try {
								String password = AESCipher.aesDecryptString(member.getPassword());
								member.setPassword(password);
							} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
									| InvalidAlgorithmParameterException | IllegalBlockSizeException
									| BadPaddingException | UnsupportedEncodingException e) {
								resultInfo.setCode(MemberConstant.fail_code);
								resultInfo.setMsg("用户名或登录密码错误");
								return resultInfo;
							}
							m.setPassword(member.getPassword());

							memberService.updateMember(m, getOperator());
							resultInfo.setCode(MemberConstant.success_code);
							resultInfo.setData(m);
							resultInfo.setMsg(MemberConstant.success_update_psd_msg);
						} else {
							resultInfo.setCode(MemberConstant.fail_code);
							resultInfo.setMsg(MemberConstant.fail_msg);
						}
					} else {
						resultInfo.setCode(MemberConstant.phone_code);
						resultInfo.setMsg(MemberConstant.ver_code_msg);
					}
				}
			} else {
				resultInfo.setCode(MemberConstant.fail_code);
				resultInfo.setMsg(MemberConstant.fail_msg_phone);
			}
		}

		return resultInfo;
	}
	
	

	/**
	 * 修改手机号
	 */
	@RequestMapping("/updatePhone")
	@ResponseBody
	public ResultInfo<Member> updatePhone(Member member, String veCode) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		if (member.getMobilePhone() != null && !"".equals(member.getMobilePhone()) && member.getIdCard() != null && !"".equals(member.getIdCard())) {
			Member memberPhone = memberService.getMemberByPhone(member.getMobilePhone());
			if (memberPhone == null) {
				// 从缓存中取出验证码
				String regCode = cacheUtil.get("regCode" + member.getMobilePhone());
				if (veCode != null && !veCode.equals("")) {
					if (veCode.equals(regCode)) {
						Member mm=new Member();
						mm.setIdCard(member.getIdCard());
						mm.setMemberNo(member.getMemberNo());
						Query q= new Query(mm);
						List<Member> idCardMember =memberService.getMemberList(q);
						if(idCardMember != null && idCardMember.size()>0 ){
							Member mi=new Member();
							mi.setMemberNo(member.getMemberNo());
							mi.setMobilePhone(member.getMobilePhone());
							memberService.updateMember(mi, getOperator());
							resultInfo.setCode(MemberConstant.success_code);
							resultInfo.setMsg("手机号修改成功");
						}else{
							resultInfo.setCode(MemberConstant.fail_code);
							resultInfo.setMsg("该身份证号不存在!");
						}
						
					} else {
						resultInfo.setCode(MemberConstant.phone_code);
						resultInfo.setMsg(MemberConstant.ver_code_msg);
					}
				}else{
					resultInfo.setCode(MemberConstant.fail_code);
					resultInfo.setMsg(MemberConstant.ver_code_msg);
				}
			} else {
				resultInfo.setCode(MemberConstant.fail_code);
				resultInfo.setMsg("手机号只能是唯一，不能重复!");
			}
		}

		return resultInfo;
	}


	/**
	 * 增加查询认证资料信息接口
	 */
	@RequestMapping("/searchMemberCensor")
	@ResponseBody
	public ResultInfo<MemberCensorVo> searchMemberCensor(String memberNo) {
		ResultInfo<MemberCensorVo> resultInfo = new ResultInfo<MemberCensorVo>();
		MemberCensorVo memberCensorVo = new MemberCensorVo();
		resultInfo.setData(memberCensorVo);
		Member member = memberService.getMember(memberNo);
		if (member != null) {
			memberCensorVo.setCensorStatus(member.getCensorStatus());
			if (member.getDriverLicensePhotoUrl1() != null && !"".equals(member.getDriverLicensePhotoUrl1())) {
				memberCensorVo.setDriverLicensePhotoUrl1(imgPath + "/" + member.getDriverLicensePhotoUrl1());
			} else {
				memberCensorVo.setDriverLicensePhotoUrl1(imgPath + "/" + "");
			}
			if (member.getCencorMemo() != null && !"".equals(member.getCencorMemo())) {
				memberCensorVo.setCencorMemo("(" + member.getCencorMemo() + ")");
			}
			if (member.getIdCard() != null && !"".equals(member.getIdCard())) {
				memberCensorVo.setIdCard(member.getIdCard());
			} else {
				memberCensorVo.setIdCard("");
			}

			if (member.getIdCardPhotoUrl() != null && !"".equals(member.getIdCardPhotoUrl())) {
				memberCensorVo.setIdCardPhotoUrl(imgPath + "/" + member.getIdCardPhotoUrl());
			} else {
				memberCensorVo.setIdCardPhotoUrl(imgPath + "/" + "");
			}

			if (member.getMemberName() != null && !"".equals(member.getMemberName())) {
				memberCensorVo.setMemberName(member.getMemberName());
			} else {
				memberCensorVo.setMemberName("");
			}
			 SysParam sys = sysParamService.getByParamKey("isPapers");
			 if(sys != null){
				 memberCensorVo.setIsPapers(Integer.parseInt(sys.getParamValue()));
			 }else{
				 memberCensorVo.setIsPapers(0);
			 }
			 //查询其他证件
			 DataDictItem d=new DataDictItem();
			 d.setDataDictCatCode("OTHER_PAPER");
			 Query q= new Query(d);
			 List<PapersVo> pv=new ArrayList<PapersVo>();
			 List<DataDictItem> di=dataDictItemService.getDataDictItemList(q);
			 if(di != null && di.size()>0){
				 for (DataDictItem dataDictItem : di) {
					 PapersVo ppv=new PapersVo();
					 ppv.setPaperName(dataDictItem.getItemValue());
					 pv.add(ppv);
				}
				 memberCensorVo.setPapers(pv);
			 }
			
			//芝麻信用分
			MemberZhimaScore memberZhimaScore = memberZhimaScoreService.getMemberZhimaScore(memberNo); 
			if(memberZhimaScore != null){
				 memberCensorVo.setCreditScore(memberZhimaScore.getScore());
			}
			
			memberCensorVo.setMemberNo(memberNo);
			resultInfo.setCode("1");
			resultInfo.setMsg("查询成功");
		} else {
			resultInfo.setCode("0");
			resultInfo.setMsg("无用户");
		}

		return resultInfo;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public ResultInfo<Member> updatePassword(String memberNo, String password, String passwordNew,
			String passwordNews) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		Member member = new Member();
		member.setMemberNo(memberNo);
		String passwordNewMD5 = "";
		String passwordMD5 = "";
		// SysParam s7 = sysParamService.getByParamKey("ISDecrypt");
		// String isDecrypt = "";
		// if
		// (s7!=null&&s7.getParamValue()!=null&&!s7.getParamValue().equals(""))
		// {
		// isDecrypt = s7.getParamValue();
		// }
		// if("1".equals(isDecrypt)){
		try {
			passwordMD5 = AESCipher.aesDecryptString(password);
			passwordNewMD5 = AESCipher.aesDecryptString(passwordNew);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg("用户名或登录密码错误");
			return resultInfo;
		}
		member.setPassword(passwordMD5);

		Query q = new Query(member);
		// 先根据 会员号 和旧密码 查找是否匹配
		List<Member> memberp = memberService.getMemberList(q);
		if (memberp != null && memberp.size() > 0) {
			member.setPassword(passwordNewMD5);
			memberService.updateMember(member, getOperator());
			resultInfo.setCode(MemberConstant.success_code);
			resultInfo.setData(member);
			resultInfo.setMsg(MemberConstant.success_update_psd_msg);
		} else {
			resultInfo.setCode(MemberConstant.fail_psd_chek_code);
			resultInfo.setMsg(MemberConstant.fail_psd_chek_msg);
		}

		return resultInfo;
	}

	/**
	 * 上传会员证件照
	 * 
	 * @param memberNo
	 *            会员编号
	 * @param pathUrl1
	 *            驾照url
	 * @param pathUrl2
	 *            身份证url
	 * @return
	 */
	@RequestMapping("/uploadDrivingLicense")
	@ResponseBody
	public ResultInfo<String> uploadDrivingLicense(String memberNo, String pathUrl1, String pathUrl2) {
		Member member = memberService.getMember(memberNo);
		if (pathUrl1 != null && !pathUrl1.equals("")) {
			String pathUrl11 = pathUrl1.substring(imgPath.length() + 1);
			member.setDriverLicensePhotoUrl1(pathUrl11);
		}
		if (pathUrl2 != null && !pathUrl2.equals("")) {
			String pathUrl21 = pathUrl2.substring(imgPath.length() + 1);
			member.setIdCardPhotoUrl(pathUrl21);
			;
		}
		// member.setCensorStatus(2);//待审核
		ResultInfo<Member> rs = memberService.updateMember(member, getOperator());
		ResultInfo<String> result = new ResultInfo<>();
		if (rs.getCode().equals("1")) {
			result.setCode(MemberConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 验证会员证件
	 * 
	 * @param base64ImgStr
	 *            图片的base64字符串
	 * @param type
	 *            类型（2：身份证，5：驾照）
	 * @return
	 */
	@RequestMapping("/validationCard")
	@ResponseBody
	public ResultInfo<Map<String, Object>> validationCard(String imgBase64Str, String type) {
		Map<String, Object> data = authenticationService.validationCard(imgBase64Str, type);
		ResultInfo<Map<String, Object>> result = new ResultInfo<>();
		if (data != null) {
			result.setCode(MemberConstant.success_code);
			result.setData(data);
		} else {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.validation_card_fail_msg);
		}
		return result;
	}

	/**
	 * 校验会员身份证件是否使用过(同一个身份证，不可多次认证)
	 * 
	 * @param idCardNo
	 * @return
	 */
	@RequestMapping("validationIdCard")
	@ResponseBody
	public ResultInfo<String> validationIdCard(String idCardNo) {
		ResultInfo<String> result = new ResultInfo<String>();
		if (idCardNo != null && !"".equals(idCardNo)) {
			Member member = new Member();
			member.setIdCard(idCardNo);
			member.setCensorStatus(1);
			List<Member> memberList = memberService.getMemberList(new Query(member));
			if (memberList.size() > 0) {
				result.setCode(MemberConstant.success_code);
				result.setMsg("此身份证已认证!");
			} else {
				result.setCode(MemberConstant.fail_code);
			}
		} else {
			result.setCode(MemberConstant.fail_code);
			result.setMsg("参数错误");
		}
		return result;
	}
}

class IpForbidener {
	private static LRUMap<String, IpEntry> lruMap = new LRUMap<String, IpEntry>(100, 5000);

	private static int forbiddenTime = Integer
			.parseInt(getString("forbiddenTime") == null ? "18000" : getString("forbiddenTime"));

	private static int minInterval = Integer
			.parseInt(getString("minInterval") == null ? "18000" : getString("minInterval"));

	private static int maxAccessTimes = Integer
			.parseInt(getString("maxAccessTimes") == null ? "8" : getString("maxAccessTimes"));

	private static int minAllowAccessIntever = Integer
			.parseInt(getString("minAllowAccessIntever") == null ? "5000" : getString("minAllowAccessIntever"));

	/** 获取配置文件中数值 */

	public static String getString(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream((getAppPath() + "ipforbidden.properties")));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取应用路径
	 * 
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String getAppPath() throws UnsupportedEncodingException {
		String configPath = IpForbidener.class.getClassLoader().getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

	public static boolean isForbidden(String ip) {
		if (ip == null || ip.equals("")) {
			return true;
		}

		IpEntry ipEntry = lruMap.get(ip);
		Date now = new Date();

		if (ipEntry == null) {
			ipEntry = new IpEntry();
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(1);
			ipEntry.setIp(ip);
			lruMap.put(ip, ipEntry);
			return false;
		}

		if (ipEntry.isForbidden()) {
			if (now.getTime() - ipEntry.getLastAccessTime().getTime() > forbiddenTime) { // 超过解封时间
				// 解封
				ipEntry.setForbidden(false);
				ipEntry.setAccessTimes(1);
				ipEntry.setLastAccessTime(now);
				return false;
			} else {
				return true;
			}
		}

		if (now.getTime() - ipEntry.getLastAccessTime().getTime() < minAllowAccessIntever) {
			// 访问过于频繁，封禁
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(ipEntry.getAccessTimes() + 1);
			if (ipEntry.getAccessTimes() > maxAccessTimes) {
				// 封禁
				ipEntry.setForbidden(true);
			}
			// ipEntry.setForbidden(true);
			return true;
		}

		if (now.getTime() - ipEntry.getLastAccessTime().getTime() > minInterval) {
			// 太久没访问，重新计时
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(1);
			ipEntry.setForbidden(false);
			return false;
		} else {
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(ipEntry.getAccessTimes() + 1);
			if (ipEntry.getAccessTimes() > maxAccessTimes) {
				// 封禁
				ipEntry.setForbidden(true);
				return true;
			} else {
				return false;
			}
		}

	}
}

class IpEntry {
	private String ip;

	private Date lastAccessTime;

	private int accessTimes;

	private boolean isForbidden = false;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getAccessTimes() {
		return accessTimes;
	}

	public void setAccessTimes(int accessTimes) {
		this.accessTimes = accessTimes;
	}

	public boolean isForbidden() {
		return isForbidden;
	}

	public void setForbidden(boolean isForbidden) {
		this.isForbidden = isForbidden;
	}

	@Override
	public String toString() {
		return "IpEntry [ip=" + ip + ", lastAccessTime=" + lastAccessTime + ", accessTimes=" + accessTimes
				+ ", isForbidden=" + isForbidden + "]";
	}

}
