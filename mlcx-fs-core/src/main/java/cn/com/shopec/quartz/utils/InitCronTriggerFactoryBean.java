package cn.com.shopec.quartz.utils;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

public class InitCronTriggerFactoryBean extends CronTriggerFactoryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Resource
	private SysParamService sysParamService;
	
	private String key;
	
	public void setKey(String key)
	{
		this.key = key;
		
		setCronExpression(getCronExpressionFromDB());
	}

	private String getCronExpressionFromDB()
	{
		if(StringUtils.isEmpty(key))
			return "0 0 0 * * ?";
		
		SysParam sysParam = new SysParam();
		
		try
		{
			sysParam = sysParamService.getByParamKey(key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if(sysParam != null && !StringUtils.isEmpty(sysParam.getParamValue()))
			return sysParam.getParamValue();

		return "0 0 0 * * ?";
	}
	
}
