package cn.com.shopec.mapi.message.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.marketing.service.SendMessageService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.quartz.utils.MemberOrderQuartz;


/**
 * 驾驶证过期定时任务
 * */
public class DriverLicenseController{
	private Log logger = LogFactory.getLog(DriverLicenseController.class);
	@Resource
	private MemberService memberService;
	@Resource
	private SysParamService sysParamService;
	
	public void execute() throws Exception {
		logger.info("--------扫描驾驶证有效期定时任务开始-------------");
		
		try {
			/**
			 *得到所有会员的驾驶证有效期信息过期的数据，并修改审核状态为未审核
			 */
			Date nowDate=new Date();
			memberService.getDriverLicense(nowDate);
			/**
			 *得到所有会员的驾驶证有效期信息还有一天过期的数据，并发送消息给客户
			 */
			SysParam sysp = sysParamService.getByParamKey(Constant.driver_license_over_date);
			Date nowDateOneDay=ECDateUtils.getDateAfter(nowDate,Integer.parseInt(sysp.getParamValue()));
			memberService.getDriverLicenseOneDay(nowDateOneDay,Integer.parseInt(sysp.getParamValue()));
		} catch (Exception e) {
			logger.error("---扫描驾驶证有效期定时任务出错，错误信息：" + e.getMessage(), e);
			return;
		}
		logger.info("--------扫描驾驶证有效期定时任务结束-------------");
	}
	
	
	
	
	
}
