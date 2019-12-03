package cn.com.shopec.mapi.worker.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.customer.model.CustomerFeedback;
import cn.com.shopec.core.customer.service.CustomerFeedbackService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.CheckPlanService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.customer.vo.CustomerFeedbackVo;
import cn.com.shopec.mapi.worker.vo.CheckForUpdateVo;
import cn.com.shopec.mapi.worker.vo.WorkerEmp;
import cn.com.shopec.mapi.worker.vo.WorkerPark;

@Controller
@RequestMapping("/checkForUpdate")
public class CheckForUpdate extends BaseController {

	@Resource
	private SysParamService sysParamService;
	

	public static final String android_version= "ANDROIDVERSION";//安卓版本：v1.0.1
	public static final String android_md5= "ANDROIDMD5";//安卓版本：v1.0.1
 
	public static final String android_update_url= "ANDROIDURL";//更新链接:http://abc.com/update.apk 

	public static final String android_build= "ANDROIDBUILD";//biild版本：2

	public static final String android_force= "ANDROIDFORCE";//是否强制更新：true

	public static final String iOS_version = "IOSVERSION";//ios版本号
	
	public static final String change_log = "CHANGELOG";//更新内容
	
	public static final String ad_file_size = "ADFILESIZE";//文件大小

	public static final String worker_version= "WORKERVERSION";//调度安卓版本：v1.0.1
 
	public static final String worker_update_url= "WORKERURL";//更新链接:http://abc.com/update.apk 

	public static final String worker_build= "WORKERBUILD";//biild版本：2

	public static final String worker_force= "WORKERFORCE";//是否强制更新：true
	
	public static final String app_name = "APP_NAME";	//APP名称

	
	/**
	 * 安卓、ios，app自动更新
	 * */
	@RequestMapping("/checkForUpdate")
	@ResponseBody
	public ResultInfo<CheckForUpdateVo> checkForUpdate() {

	    CheckForUpdateVo checkForUpdateVo = new CheckForUpdateVo();
		ResultInfo<CheckForUpdateVo> resultInfo = new ResultInfo<CheckForUpdateVo>();
		 

		SysParam sysADV = sysParamService.getByParamKey(android_version);
		SysParam sysADU = sysParamService.getByParamKey(android_update_url);
		SysParam sysADB = sysParamService.getByParamKey(android_build);
		SysParam sysADF = sysParamService.getByParamKey(android_force);
		SysParam sysIOSV = sysParamService.getByParamKey(iOS_version);
		SysParam sysCHangeLog = sysParamService.getByParamKey(change_log);
		SysParam sysADFileSize = sysParamService.getByParamKey(ad_file_size);
		SysParam appName = sysParamService.getByParamKey(app_name);
		SysParam appMd5 = sysParamService.getByParamKey(android_md5);
		String name= appName.getParamValue();
	    String version= sysADV.getParamValue();
	    String changelog= sysCHangeLog.getParamValue();
	    String update_url= sysADU.getParamValue();
	    int build = Integer.parseInt(sysADB.getParamValue());
		boolean force = (sysADF != null && sysADF.getParamValue() != null  && "true".equalsIgnoreCase(sysADF.getParamValue()) ) ? true : false;

	    //double iOSversion= Double.parseDouble(sysIOSV.getParamValue());
		String iOSversion = sysIOSV.getParamValue();
		double adFileSize=Double.parseDouble(sysADFileSize.getParamValue());
		checkForUpdateVo.setName(name);
		if(appMd5 != null && !"".equals(appMd5)){
			checkForUpdateVo.setMd5(appMd5.getParamValue());
		}else{
			checkForUpdateVo.setMd5("0");
		}
		
		checkForUpdateVo.setVersion(version);
		checkForUpdateVo.setChangelog(changelog);
		checkForUpdateVo.setUpdate_url(update_url);
		checkForUpdateVo.setBuild(build);
		checkForUpdateVo.setForce(force);
		
		checkForUpdateVo.setiOSversion(iOSversion);
		checkForUpdateVo.setAdFileSize(adFileSize);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(checkForUpdateVo);
		resultInfo.setMsg("获取信息成功");
		
		return resultInfo;
	}
	

	@RequestMapping("/workerCheckForUpdate")
	@ResponseBody
	public ResultInfo<CheckForUpdateVo> workerCheckForUpdate() {

	    CheckForUpdateVo checkForUpdateVo = new CheckForUpdateVo();
		ResultInfo<CheckForUpdateVo> resultInfo = new ResultInfo<CheckForUpdateVo>();
		
		SysParam sysWorkerADV = sysParamService.getByParamKey(worker_version);
		SysParam sysWorkerADU = sysParamService.getByParamKey(worker_update_url);
		SysParam sysWorkerADB = sysParamService.getByParamKey(worker_build);
		SysParam sysWorkerADF = sysParamService.getByParamKey(worker_force);


		String name= "App名称：天津公众智通-调度端";
	    String version= sysWorkerADV.getParamValue();
	    String changelog= "最新版本";
	    String update_url= sysWorkerADU.getParamValue();
	    int build = Integer.parseInt(sysWorkerADB.getParamValue());
	    boolean force = (sysWorkerADF != null && sysWorkerADF.getParamValue() != null  && "true".equalsIgnoreCase(sysWorkerADF.getParamValue()) ) ? true : false;
 
		checkForUpdateVo.setName(name);
		checkForUpdateVo.setVersion(version);
		checkForUpdateVo.setChangelog(changelog);
		checkForUpdateVo.setUpdate_url(update_url);
		checkForUpdateVo.setBuild(build);
		checkForUpdateVo.setForce(force);
		 
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(checkForUpdateVo);
		resultInfo.setMsg("天津公众智通-调度端");
		
		
		return resultInfo;
	}
	
}
