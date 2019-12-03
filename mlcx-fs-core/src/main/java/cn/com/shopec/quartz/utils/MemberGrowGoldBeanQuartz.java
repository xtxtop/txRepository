
package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.marketing.model.GoldBeansSetting;
import cn.com.shopec.core.marketing.service.GoldBeansSettingService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberGlodBeans;
import cn.com.shopec.core.member.service.MemberGlodBeansService;
import cn.com.shopec.core.member.service.MemberService;

/**
 * 金豆
 * 
 * @author machao
 * @date 2017年9月20日
 */
public class MemberGrowGoldBeanQuartz {

	private Log logger = LogFactory.getLog(MemberGrowGoldBeanQuartz.class);

	private static final String ZERO = "0";

	@Resource
	private GoldBeansSettingService goldBeansSettingService;
	@Resource
	private MemberGlodBeansService memberGoldBeansService;
	@Resource
	private MemberService memberService;
	@Resource
	private DepositOrderService depositOrderService;

	public void quartzStart() throws Exception {
		List<GoldBeansSetting> goldBeansSettingList = goldBeansSettingService.getGoldBeansSettingList(new Query());
		if(goldBeansSettingList.size()>0){
			GoldBeansSetting goldBeansSetting = goldBeansSettingList.get(0);
			List<Member> memberList = memberService.getMemberList(new Query());
			if(memberList.size()>0){
				for(Member member:memberList){
					//审核通过的会员
					if(member.getCensorStatus()==1){
						//未交
						int depositStatus = 0;
						//判断会员的押金状态
						ResultInfo<Deposit> rDeposit = depositOrderService.getDepositByMemberNo(member.getMemberNo(),null);
						if (rDeposit.getCode().equals("1")) {
							if(rDeposit.getData()!=null){
								depositStatus = rDeposit.getData().getDepositStatus();
							}
						}
						
						if(depositStatus==1){
							DepositOrder depositOrderForQuery = new DepositOrder();
							depositOrderForQuery.setMemberNo(member.getMemberNo());
							List<DepositOrder> depositOrderList = depositOrderService.getDepositOrderList(new Query(depositOrderForQuery));
							
							DepositOrder depositOrder = depositOrderList.get(0);
							//最新的押金支付时间
							Date depositOrderPayTime = depositOrder.getPaymentTime();
							MemberGlodBeans mGlodBeans = memberGoldBeansService.getMemberGlodBeans(member.getMemberNo());
							Date compareDateTime;
							//会员金豆表已经有的会员根据上次更新时间和当前时间判断是否满足涨豆的条件
							if(mGlodBeans!=null){
								Date lastAddBeanTime = mGlodBeans.getUpdateTime();
								if(depositOrderPayTime.compareTo(lastAddBeanTime)>0){
									compareDateTime = depositOrderPayTime;
								}else{
									compareDateTime = lastAddBeanTime;
								}
								Date nowTime = ECDateUtils.getCurrentDateTime();
								int diffDays = ECDateUtils.differDays2(nowTime, compareDateTime).intValue();
								int days = goldBeansSetting.getDays();
								if(diffDays>=days){
									int addGlodBeanNum = goldBeansSetting.getGoldBeansNum();
									int beforeGlodBeanNum = mGlodBeans.getGoldBeansNum().intValue();
									int afterGlodBeanNum = beforeGlodBeanNum+addGlodBeanNum;
									
									MemberGlodBeans glodBeansForUpdate = new MemberGlodBeans();
									glodBeansForUpdate.setMemberNo(mGlodBeans.getMemberNo());
									glodBeansForUpdate.setGoldBeansNum(afterGlodBeanNum);
									memberGoldBeansService.updateMemberGlodBeans(glodBeansForUpdate);
								}
							//会员金豆表没有的会员根据审核通过的时间和当前时间判断是否满足涨豆的条件
							}else{
								Date lastAddBeanTime = member.getCencorTime();
								if(depositOrderPayTime.compareTo(lastAddBeanTime)>0){
									compareDateTime = depositOrderPayTime;
								}else{
									compareDateTime = lastAddBeanTime;
								}
								Date nowTime = ECDateUtils.getCurrentDateTime();
								int diffDays = ECDateUtils.differDays2(nowTime, compareDateTime).intValue();
								int days = goldBeansSetting.getDays();
								if(diffDays>=days){
									int addGlodBeanNum = goldBeansSetting.getGoldBeansNum();
									int beforeGlodBeanNum = 0;
									int afterGlodBeanNum = beforeGlodBeanNum+addGlodBeanNum;
									
									MemberGlodBeans glodBeansForAdd = new MemberGlodBeans();
									glodBeansForAdd.setMemberNo(member.getMemberNo());
									glodBeansForAdd.setGoldBeansNum(afterGlodBeanNum);
									memberGoldBeansService.addMemberGlodBeans(glodBeansForAdd);
								}
							}
						}
					}
				}
			}
		}else{
			logger.info("---没有金豆配置---");
		}
	}

	protected void execute(String arg0) throws Exception {
		// 定时任务开关，0：关 1：开
		String quartzSwitch = ZERO;

		try {
			quartzSwitch = arg0;
		} catch (Exception e) {
			logger.error("---读取定时任务开关信息出错，错误信息：" + e.getMessage(), e);
			return;
		}

		// 定时任务开关配置为0时，则关闭
		if (StringUtils.isEmpty(quartzSwitch) || quartzSwitch.equals(ZERO)) {
			logger.info("---定时任务为关闭状态...");
			return;
		}

		try {
			logger.info("--------扫描会员表和会员金豆表，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描扫描会员表和会员金豆表定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描扫描会员表和会员金豆表，错误信息：" + e.getMessage(), e);
		}
	}

}
