package cn.com.shopec.mgt.system.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 业务参数管理
 * 
 * @author 许瑞伟
 *
 */
@Controller
@RequestMapping("businessParam")
public class BusinessParamController extends BaseController {

	@Resource
	private SysParamService sysParamService;

	/**
	 * 显示系统参数页
	 */
	@RequestMapping("toParam")
	public String toParam() {
		return "system/businessParam";
	}

	/**
	 * 更新系统参数
	 */
	@RequestMapping("updateParamValue")
	@ResponseBody
	public ResultInfo<SysParam> updateParamValue(SysParam sysParam) {
		// 操作人
		Operator op = getOperator();
		if (op != null) {
			sysParam.setOperatorId(op.getOperatorId());
			sysParam.setOperatorType(op.getOperatorType());
		}
		sysParam.setUpdateTime(new Date());
		return sysParamService.updateParamValue(sysParam);
	}

	/**
	 * 分页展示系统参数
	 */
	@RequestMapping("getParamList")
	@ResponseBody
	public ResultInfo<List<SysParam>> getParamList(
			@ModelAttribute("sysParam") SysParam sysParam) {
		Query q = new Query(sysParam);
		return sysParamService.getParamList(q);
	}

	/**
	 * 获取系统参数
	 */
	@RequestMapping("getParam")
	@ResponseBody
	public ResultInfo<SysParam> getParam(String paramId) {
		return sysParamService.getParam(paramId);
	}

}
