package cn.com.shopec.mgt.marketing.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.GoldBeansSetting;
import cn.com.shopec.core.marketing.service.GoldBeansSettingService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("goldbeans")
public class GoldBeansSettingController extends BaseController {
	@Resource
	private GoldBeansSettingService goldBeansSettingService;

	/**
	 * 金豆详情页面
	 * 
	 * @return
	 */
	@RequestMapping("toGoldbeansSettingView")
	public String toGoldbeansView(String goldBeansId, ModelMap modelMap) {
		GoldBeansSetting goldBeansSetting = goldBeansSettingService.getGoldBeansSetting(goldBeansId);
		modelMap.put("goldBeansSetting", goldBeansSetting);
		return "marketing/gold_beans_setting_view";
	}

	/**
	 * 金豆增加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddGoldbeansSetting")
	public String toAddGoldbeansSetting(ModelMap modelMap) {
		List<GoldBeansSetting> lists = goldBeansSettingService.getGoldBeansSettingList(new Query());
		if(lists.size()>0){
			modelMap.put("goldBeansSetting", lists.get(0));
			return "marketing/gold_beans_setting_view";
		}else{
			return "marketing/gold_beans_setting_add";
		}
	}

	/**
	 * 金豆添加
	 * 
	 * @return
	 */
	@RequestMapping("addGoldbeansSetting")
	@ResponseBody
	public ResultInfo<GoldBeansSetting> addGoldbeansSetting(GoldBeansSetting goldBeansSetting) {
		List<GoldBeansSetting> lists = goldBeansSettingService.getGoldBeansSettingList(new Query());
		if(lists.size()>0){
			 ResultInfo<GoldBeansSetting> result = new ResultInfo<>();
			 result.setCode(Constant.FAIL);
			 result.setMsg("已经设置了");
			 return result;
		}else{
			ResultInfo<GoldBeansSetting> result = goldBeansSettingService.addGoldBeansSetting(goldBeansSetting);
			return result;
		}
	}

	/**
	 * 金豆编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("toUpdateGoldbeansSetting")
	public String toUpdateGoldbeansSetting(String goldBeansId, ModelMap modelMap) {
		GoldBeansSetting goldBeansSetting = goldBeansSettingService.getGoldBeansSetting(goldBeansId);
		modelMap.put("goldBeansSetting", goldBeansSetting);
		return "marketing/gold_beans_setting_edit";
	}

	/**
	 * 金豆编辑提交
	 * 
	 * @return
	 */
	@RequestMapping("updateGoldbeansSetting")
	@ResponseBody
	public ResultInfo<GoldBeansSetting> updateGoldbeansSetting(GoldBeansSetting goldBeansSetting) {
		ResultInfo<GoldBeansSetting> result = goldBeansSettingService.updateGoldBeansSetting(goldBeansSetting);
		return result;
	}
	/**
	 * 删除金豆设置
	 * 
	 * @return
	 */
	@RequestMapping("deleteGoldbeansSetting")
	@ResponseBody
	public ResultInfo<GoldBeansSetting> deleteGoldbeansSetting(String goldBeansId) {
		ResultInfo<GoldBeansSetting> result = goldBeansSettingService.delGoldBeansSetting(goldBeansId);
		return result;
	}

}
