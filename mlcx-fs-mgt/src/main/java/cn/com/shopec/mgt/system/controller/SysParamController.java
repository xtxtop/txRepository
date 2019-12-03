package cn.com.shopec.mgt.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("sysParam")
public class SysParamController extends BaseController {

	@Resource
	private SysParamService sysParamService;

	/*
	 * 显示系统参数页
	 */
	@RequestMapping("mainPage")
	public String pageSysParam() {
		return "system/sysParam";
	}

	/*
	 * 根据参数名查找一条信息
	 */
	@RequestMapping("getByParamName")
	@ResponseBody
	public ResultInfo<SysParam> getByParamName(String paramName) {
		ResultInfo<SysParam> resultInfo = new ResultInfo<SysParam>();
		SysParam sysParam = sysParamService.getByParamName(paramName);
		if (sysParam == null) {
			resultInfo.setCode("2");
		} else {
			resultInfo.setCode("1");
		}
		return resultInfo;
	}

	/*
	 * 添加系统参数页
	 */
	@RequestMapping("toAddSysParam")
	public String toAddSysParam() {
		return "system/sysParam/addSysParam";
	}

	/*
	 * 添加或修改系统参数
	 */
	@RequestMapping("addOrEditSysParam")
	@ResponseBody
	public ResultInfo<SysParam> addOrEditSysParam(SysParam sysParam) {
		// 操作人
		Operator op = getOperator();
		if (op != null) {
			sysParam.setOperatorId(op.getOperatorId());
			sysParam.setOperatorType(op.getOperatorType());
		}
		ResultInfo<SysParam> resultInfo = sysParamService.addOrEditSysParam(sysParam);
		return resultInfo;
	}

	/*
	 * 删除系统参数
	 */
	@RequestMapping("deleteSysParam")
	@ResponseBody
	public ResultInfo<SysParam> deleteSysParam(@RequestParam("sysParamId") String sysParamId) {
		return sysParamService.delete(sysParamId, getOperator());
	}

	/*
	 * 分页展示系统参数
	 */
	@RequestMapping("getSysParamList")
	@ResponseBody
	public PageFinder<SysParam> getSysParamList(@ModelAttribute("sysParam") SysParam sysParam, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), sysParam);
		return sysParamService.pageList(q);

	}

	/*
	 * 批量删除系统参数组
	 */
	@RequestMapping("batchDelete")
	@ResponseBody
	public ResultInfo<SysParam> batchDelete(@RequestParam("sysParamIds") String[] sysParamIds) {
		return sysParamService.batchDelete(sysParamIds, getOperator());
	}

	/*
	 * 详情
	 */
	@RequestMapping("detail")
	@ResponseBody
	public SysParam detail(@RequestParam("paramId") String paramId) {
		return sysParamService.detail(paramId);
	}

}
