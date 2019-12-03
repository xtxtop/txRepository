package cn.com.shopec.mapi.station.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.ml.model.CJoin;
import cn.com.shopec.core.ml.model.CReporting;
import cn.com.shopec.core.ml.service.CReportingService;
import cn.com.shopec.core.mlparking.service.CParkLockService;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * @author 代元宝
 * @category 违停上报 APP接口
 *
 */
@Controller
@RequestMapping("app/reporting")
public class ReportingController extends BaseController{
	
	@Resource
	private CParkLockService cParkLockService;
	@Resource
	private CReportingService cReportingService;
	/**
	 * 违停上报
	 * @param join
	 * @return
	 */
	@RequestMapping("/failtoStopReporting")
	@ResponseBody
	public ResultInfo<CReporting> failtoStopReporting(CReporting reporting){
		ResultInfo<CReporting> info = new ResultInfo<CReporting>();
		if(reporting.getImg()==null){
			info.setMsg("参数错误!");
			info.setCode(Constant.FAIL);
			return info;
		}
		//验证会员信息
		ResultInfo<Object> resultInfoMemberNo = cParkLockService
				.resultInfoMemberNo(reporting.getMemberNo(),2);
		if (resultInfoMemberNo != null && Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
			//违停上报
			ResultInfo<CReporting> addCReporting = cReportingService.addCReporting(reporting, getOperator());
			if(Constant.SECCUESS.equals(addCReporting.getCode())){
				info.setMsg("上报成功!");
				info.setCode(Constant.SECCUESS);
				return info;
			}else{
				info.setMsg("上报失败!");
				info.setCode(Constant.FAIL);
				return info;
			}
		} else {
			info.setMsg(resultInfoMemberNo.getMsg());
			info.setCode(resultInfoMemberNo.getCode());
			return info;
		}
	}
}
