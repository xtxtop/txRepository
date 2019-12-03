
package cn.com.shopec.quartz.utils;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.uploadpkg.service.DeviceUploadpkgLogService;

/**
 * 车辆上下线日志清理
 * 
 * @author 许瑞伟
 * @date 2017年7月25日
 */
public class ClearDeviceUploadpkgLogQuartz {

	private Log logger = LogFactory.getLog(ClearDeviceUploadpkgLogQuartz.class);

	private static final String ZERO = "0";

	@Resource
	private QuartzService quartzService;

	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private DeviceUploadpkgLogService deviceUploadpkgLogService;
	
	public void quartzStart() throws Exception {
		String day = sysParamService.getByParamKey("DeviceUploadpkgLogDayParam").getParamValue();
		if(day == null){
			day = "3";
		}
		deviceUploadpkgLogService.delDeviceUploadpkgLog(Integer.parseInt(day));
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
			logger.info("--------车辆上下线日志清理，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------车辆上下线日志清理定时任务完成...");
		} catch (Exception e) {
			logger.error("--------车辆上下线日志清理出错，错误信息：" + e.getMessage(), e);
		}
	}

}
