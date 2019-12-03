package cn.com.shopec.mgt.dailyrental.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.ApplyCarModel;
import cn.com.shopec.core.dailyrental.service.ApplyCarModelService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("applyCarModel")
public class ApplyCarModelController extends BaseController {

	@Resource
	public ApplyCarModelService applyCarModelService;
	
	/*
	 * 显示车辆型号列表页
	 */
	@RequestMapping("toApplyCarModelList")
	public String mainPage() {
		return "dailyrental/car/apply_car_model_list";
	}
	/*
	 * 确认还车修改商家订单状态
	 */
	@RequestMapping("pageListApplyCarModel")
	@ResponseBody
	public PageFinder<ApplyCarModel> pageListApplyCarModel(@ModelAttribute("ApplyCarModel") ApplyCarModel applyCarModel, Query query) {
		if (applyCarModel.getIsDelete()==null) {
			applyCarModel.setIsDelete(0);
		}
		Query q = new Query(query.getPageNo(), query.getPageSize(), applyCarModel);
		return applyCarModelService.getApplyCarModelPagedList(q);
	}
	
	/*
	 * 同意商家车型添加申请
	 */
	@RequestMapping("agreeApplyCarModel")
	@ResponseBody
	public ResultInfo<ApplyCarModel> agreeApplyCarModel(String applyCarModelId) {
		ResultInfo<ApplyCarModel> result = new ResultInfo<ApplyCarModel>();
		if (applyCarModelId==null || "".equals(applyCarModelId)) {
			result.setCode(Constant.FAIL);
			result.setMsg("参数错误");
		}
		ApplyCarModel applyCarModel = new ApplyCarModel();
		applyCarModel.setApplyCarModelId(applyCarModelId);
		applyCarModel.setIsAgree(1);
		return applyCarModelService.updateApplyCarModel(applyCarModel, getOperator());
	}
	/*
	 * 同意商家车型添加申请
	 */
	@RequestMapping("deleteApplyCarModel")
	@ResponseBody
	public ResultInfo<ApplyCarModel> deleteApplyCarModel(String applyCarModelId) {
		ResultInfo<ApplyCarModel> result = new ResultInfo<ApplyCarModel>();
		if (applyCarModelId==null || "".equals(applyCarModelId)) {
			result.setCode(Constant.FAIL);
			result.setMsg("参数错误");
		}
		ApplyCarModel applyCarModel = new ApplyCarModel();
		applyCarModel.setApplyCarModelId(applyCarModelId);
		applyCarModel.setIsDelete(1);
		return applyCarModelService.updateApplyCarModel(applyCarModel, getOperator());
	}
	
}
