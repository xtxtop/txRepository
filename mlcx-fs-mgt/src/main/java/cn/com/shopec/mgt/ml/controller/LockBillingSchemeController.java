package cn.com.shopec.mgt.ml.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.LockBillingScheme;
import cn.com.shopec.core.ml.service.LockBillingSchemeService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @category 充电桩
 */
@Controller
@RequestMapping("lockBillingScheme")
public class LockBillingSchemeController extends BaseController {

	@Resource
	private LockBillingSchemeService lockBillingSchemeService;

	@RequestMapping("/getLockBillingSchemeList")
	public String getChargingStation(ModelMap model) {
		return "ml/lockBillingScheme_list";
	}

	// 列表展示
	@RequestMapping("/pageListlockBillingScheme")
	@ResponseBody
	public PageFinder<LockBillingScheme> pageListlockBillingScheme(
			@ModelAttribute("LockBillingScheme") LockBillingScheme lockBillingScheme, Query query)
			throws ParseException {
		Query q = new Query(query.getPageNo(), query.getPageSize(), lockBillingScheme);
		return lockBillingSchemeService.getLockBillingSchemePagedList(q);
	}

	// 新增页面
	@RequestMapping("/toAddlockBillingScheme")
	public String toAddlockBillingScheme(ModelMap model) {
		return "ml/lockBillingScheme_add";
	}

	// 新增充电桩操作
	@RequestMapping("/doaddlockBillingScheme")
	@ResponseBody
	public ResultInfo<LockBillingScheme> doaddlockBillingScheme(
			@ModelAttribute("LockBillingScheme") LockBillingScheme lockBillingScheme) {
		return lockBillingSchemeService.addLockBillingScheme(lockBillingScheme, getOperator());
	}

	// 编辑页面
	@RequestMapping("/toEditlockBillingScheme")
	public String toEditlockBillingScheme(ModelMap model, String lockBillingSchemeNo) {
		LockBillingScheme cp = lockBillingSchemeService.getLockBillingScheme(lockBillingSchemeNo);
		model.put("cp", cp);
		return "ml/lockBillingScheme_edit";
	}

	// 编辑充电桩操作
	@RequestMapping("/doeditlockBillingScheme")
	@ResponseBody
	public ResultInfo<LockBillingScheme> doeditlockBillingScheme(
			@ModelAttribute("LockBillingScheme") LockBillingScheme lockBillingScheme) {
		return lockBillingSchemeService.updateLockBillingScheme(lockBillingScheme, getOperator());
	}
}
