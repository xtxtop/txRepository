
package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 自动匹配集团会员
 * 
 * @author gm
 * @date 2016年11月8日
 */
public class MemberCompanQuartz {

	private Log logger = LogFactory.getLog(MemberCompanQuartz.class);

	private static final String ZERO = "0";

	@Resource
	private MemberService memberService;
	
	public void quartzStart() throws Exception {
		Query q = new Query();
		memberService.getMemberCompany(q);
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
			logger.info("--------自动匹配集团会员，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------自动匹配集团会员定时任务完成...");
		} catch (Exception e) {
			logger.error("--------自动匹配集团会员任务出错，错误信息：" + e.getMessage(), e);
		}
	}

}
