
package cn.com.shopec.quartz.utils;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 扫描车辆电量
 * 
 * @author machao
 * @date 2016年10月9日
 */
public class ImportAccountsQuartz {

	private Log logger = LogFactory.getLog(ImportAccountsQuartz.class);

	@Resource
	private QuartzService quartzService;

	@Resource
	private SysParamService sysParamService;
	
	
	public void quartzStart() throws Exception {
		logger.info("---定时自动导入账单信息---");
		quartzService.importAccountsPowerQuartz();
		
	}

	protected void execute() throws Exception {
		try {
			logger.info("--------开始自动导入账单信息，开始时间：" + new Date());
			quartzStart();// 执行业务方法
			logger.info("--------自动导入账单信息定时任务结束...");
		} catch (Exception e) {
			logger.error("--------自动导入账单出错，错误信息：" + e.getMessage(), e);
		}
	}

}
