package cn.com.shopec.mapi.requestHandle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.mapi.common.controller.BaseController;

@Controller
@RequestMapping
public class RequestHandleController extends BaseController{
	
	/**
	 * 接口请求响应
	 * @param type
	 * @return
	 */
	@RequestMapping("returnResult")
	@ResponseBody
	public ResultInfo<String> requestResult(String type) {
		ResultInfo<String> result=new ResultInfo<String>();
		if("1".equals(type)){
			result.setCode(Constant.FAIL);
			result.setMsg("请求时间失效");
		}else if("2".equals(type)){
			result.setCode(Constant.FAIL);
			result.setMsg("验证签名失败");
		}else if("3".equals(type)){
			result.setCode(Constant.FAIL);
			result.setMsg("token失效");
		}else if("4".equals(type)){
			result.setCode(Constant.FAIL);
			result.setMsg("正在还车中，请稍后");
		}else if("5".equals(type)){
			result.setCode("-500");
			result.setMsg("您的账号在其他设备登录");
		}
		return result;
	}
	
	
}
