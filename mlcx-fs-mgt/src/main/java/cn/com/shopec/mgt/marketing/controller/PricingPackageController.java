package cn.com.shopec.mgt.marketing.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("pricingPackage")
public class PricingPackageController extends BaseController {
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 套餐产品列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingPackageList")
	public String toPricingPackageList() {
		return "marketing/pricingPackage_list";
	}

	/**
	 * 首页 待办事项 点击 套餐产品列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingPackageListTodo")
	public String toPricingPackageListTodo(ModelMap ModelMap) {
		Integer censorStatus = 0;
		ModelMap.addAttribute("censorStatus", censorStatus);
		return "marketing/pricingPackage_list";
	}

	/**
	 * 套餐产品列表分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListPricingPackage")
	@ResponseBody
	public PageFinder<PricingPackage> pageListPricingPackage(
			@ModelAttribute("pricingPackage") PricingPackage pricingPackage, Query query) {
		pricingPackage.setIsRecharge(0);//查询非系统数据
		pricingPackage.setIsDeleted(0);
		Query q = new Query(query.getPageNo(), query.getPageSize(), pricingPackage);
		return pricingPackageService.getPricingPackagePagedList(q);
	}

	/**
	 * 套餐产品详情
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingPackageView")
	public String toPricingPackageView(String packageNo, ModelMap modelMap) {
		PricingPackage pricingPackage = pricingPackageService.getPricingPackage(packageNo);
		modelMap.put("pricingPackage", pricingPackage);
		SysUser sysUser = sysUserService.detail(pricingPackage.getOperatorId());
		modelMap.put("sysUser", sysUser);
		return "marketing/pricingPackage_view";
	}

	/**
	 * 套餐产品增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddPricingPackage")
	public String toAddPricingPackage(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/pricingPackage_add";
	}

	/**
	 * 套餐产品编辑页
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingPackageEditView")
	public String toPricingPackageEditView(String packageNo, ModelMap modelMap) {
		PricingPackage pricingPackage = pricingPackageService.getPricingPackage(packageNo);
		modelMap.put("pricingPackage", pricingPackage);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		SysUser sysUser = sysUserService.detail(pricingPackage.getOperatorId());
		modelMap.put("sysUser", sysUser);
		return "marketing/pricingPackage_edit";
	}

	/**
	 * 套餐产品审核页
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingPackageCencorView")
	public String toPricingPackageCencorView(String packageNo, ModelMap modelMap) {
		PricingPackage pricingPackage = pricingPackageService.getPricingPackage(packageNo);
		modelMap.put("pricingPackage", pricingPackage);
		SysUser sysUser = sysUserService.detail(pricingPackage.getOperatorId());
		modelMap.put("sysUser", sysUser);
		return "marketing/pricingPackage_censor";
	}

	/**
	 * 增加提交
	 * 
	 * @return
	 */
	@RequestMapping("/addPricingPackage")
	@ResponseBody
	public ResultInfo<PricingPackage> addPricingPackage(
			@ModelAttribute("pricingPackage") PricingPackage pricingPackage) {
		ResultInfo<PricingPackage> resultInfo = new ResultInfo<PricingPackage>();
		if (pricingPackage!=null&&new Integer(3).equals(pricingPackage.getPackageType())) {
			PricingPackage queryPackage = new PricingPackage();
			queryPackage.setPackageType(4);
			queryPackage.setIsAvailable(1);
			List<PricingPackage> packageList = pricingPackageService.getPricingPackageList(new Query(queryPackage));
			if (packageList.size()>0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("订单分享类套餐只能添加一个");
				return resultInfo;
			}
		}
		if (pricingPackage.getCityId() != null && pricingPackage.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(pricingPackage.getCityId());
			if (dataDictItem != null) {
				pricingPackage.setCityName(dataDictItem.getItemValue());
			}
		}
		pricingPackage.setAvailabelUpdateTime(new Date());
		PricingPackage pack = judgeIsExistPricingPackageName(pricingPackage.getPackageName());
		if (pack!=null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("套餐名称重复");
		} else {
			resultInfo = pricingPackageService.addPricingPackage(pricingPackage, getOperator());
		}
		return resultInfo;
	}

	/**
	 * 编辑提交
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingPackageCencor")
	@ResponseBody
	public ResultInfo<PricingPackage> toPricingPackageCencor(@ModelAttribute("pricingPackage") PricingPackage pricingPackage) {
		ResultInfo<PricingPackage> resultInfo = new ResultInfo<>();
		if (pricingPackage.getCityId() != null && pricingPackage.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(pricingPackage.getCityId());
			if (dataDictItem != null) {
				pricingPackage.setCityName(dataDictItem.getItemValue());
			}
		}
		// 根据上下架修改时间
		if (pricingPackage.getIsAvailable() != null) {
			pricingPackage.setAvailabelUpdateTime(new Date());

		}
		// 根据审核修改时间
		if (pricingPackage.getCencorStatus() != null) {
			pricingPackage.setCencorTime(new Date());
		}
		PricingPackage pack = judgeIsExistPricingPackageName(pricingPackage.getPackageName());
		if (pack!=null&&!pack.getPackageNo().equals(pricingPackage.getPackageNo())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("套餐名称重复");
		} else {
			resultInfo = pricingPackageService.updatePricingPackage(pricingPackage, getOperator());
		}
		// 编辑之后 还需要审核 改变审核状态 为未审核
		if ("1".equals(resultInfo.getCode())) {
			PricingPackage pricingPackagess = new PricingPackage();
			pricingPackagess.setPackageNo(pricingPackage.getPackageNo());
			pricingPackagess.setCencorStatus(0);
			pricingPackageService.updatePricingPackage(pricingPackagess, getOperator());
		}
		return resultInfo;
	}

	/**
	 * 审核提交 上/下架 提交
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdatePricingPackage")
	@ResponseBody
	public ResultInfo<PricingPackage> toUpdatePricingPackage(
			@ModelAttribute("pricingPackage") PricingPackage pricingPackage) {
		ResultInfo<PricingPackage> resultInfo = null;
		if (pricingPackage.getCityId() != null && pricingPackage.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(pricingPackage.getCityId());
			if (dataDictItem != null) {
				pricingPackage.setCityName(dataDictItem.getItemValue());
			}
		}
		// 根据上下架修改时间
		if (pricingPackage.getIsAvailable() != null) {
			pricingPackage.setAvailabelUpdateTime(new Date());

		}
		// 根据审核修改时间
		if (pricingPackage.getCencorStatus() != null) {
			pricingPackage.setCencorTime(new Date());
		}

		resultInfo = pricingPackageService.updatePricingPackage(pricingPackage, getOperator());

		return resultInfo;
	}
	
	@RequestMapping("deletePricingPackage")
	@ResponseBody
	public ResultInfo<PricingPackage> deletePricingPackage(PricingPackage pricingPackage) {
		pricingPackage.setIsDeleted(1);
		return pricingPackageService.updatePricingPackage(pricingPackage, getOperator());
	}
	/**
	 * 返回对象
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingPackage")
	@ResponseBody
	public PricingPackage pageListPricingPackage(String packageNo) {
		PricingPackage pricingPackage = null;
		if (packageNo != null && packageNo.length() != 0) {
			pricingPackage = pricingPackageService.getPricingPackage(packageNo);
		}
		return pricingPackage;
	}

	/**
	 * 判断套餐名称是否重复
	 * 
	 * @return
	 */
	private PricingPackage judgeIsExistPricingPackageName(String packageName) {
		PricingPackage pricingPackage = null;
		packageName = packageName.trim();
		if (packageName != null && packageName.length() != 0) {
			pricingPackage = pricingPackageService.getPricingPackageByPackageName(packageName);
		}
		return pricingPackage;
	}
}
