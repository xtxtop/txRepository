package cn.com.shopec.mgt.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.ECException;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SmsTemplate;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SmsTemplateService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/smsTemplate")
public class SmsTemplateControlle extends BaseController {
	@Resource
	public SmsTemplateService smsTemplateService;

	@Resource
	private DataDictItemService dataDictItemService;
	/**
	 * 进入商城短信模板页面
	 * 
	 * @return
	 */
	@RequestMapping("/smsTemplate")
	public String smsTemplate(ModelMap modelMap) {
//		List<DataDictItem> smsTemplates = dataDictItemService.getDataDictItemListByCatCode("SMS_TEMPLATE");//短信模板类型
//		modelMap.put("smsTemplateTypeList",smsTemplates );
		return "/system/smsTemplate_list";
	}

	/**
	 * 查询商城短信模板信息（分页）
	 */
	@RequestMapping("querySmsTemplate")
	@ResponseBody
	public PageFinder<SmsTemplate> querySmsTemplate(@ModelAttribute("smsTemplate") SmsTemplate smsTemplate,
			Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), smsTemplate);
		return smsTemplateService.getSmsTemplatePagedList(q);
	}
	/**
	 * 进入商城短信模板添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSmsTemplateAdd")
	public String toSmsTemplateAdd(ModelMap modelMap) {
//		List<DataDictItem> smsTemplates = dataDictItemService.getDataDictItemListByCatCode("SMS_TEMPLATE");//短信模板类型
//		modelMap.put("smsTemplateTypeList",smsTemplates );
		return "/system/smsTemplate_add";
	}
	/**
	 * 添加短信模板
	 */
	@RequestMapping("addSmsTemplate")
	@ResponseBody
	public ResultInfo<SmsTemplate> addSmsTemplate(@ModelAttribute("smsTemplate") SmsTemplate smsTemplate) {
		// 操作人
		Operator op = getOperator();
		ResultInfo<SmsTemplate> resultInfo = new ResultInfo<SmsTemplate>();
		if(smsTemplateService.getSmsTemplateByTemplateType(smsTemplate.getTemplateType())!=null){
			resultInfo.setCode(Constant.NO_AUTHORITY);
		}else{
			resultInfo = smsTemplateService.addSmsTemplate(smsTemplate, op);	
		}
		return resultInfo;

	}
	/**
	 * 进入商城短信模板编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSmsTemplateEdit")
	public String toSmsTemplateAdd(@RequestParam("templateId")String templateId,ModelMap modelMap) {
//		List<DataDictItem> smsTemplates = dataDictItemService.getDataDictItemListByCatCode("SMS_TEMPLATE");//短信模板类型
//		modelMap.put("smsTemplateTypeList",smsTemplates );
		SmsTemplate st=smsTemplateService.getSmsTemplate(templateId);
		modelMap.put("smsTemplate", st);
		return "/system/smsTemplate_edit";
	}
	/**
	 * 编辑短信模板
	 */
	@RequestMapping("editSmsTemplate")
	@ResponseBody
	public ResultInfo<SmsTemplate> editSmsTemplate(@ModelAttribute("smsTemplate") SmsTemplate smsTemplate) {
		// 操作人
		Operator op = getOperator();
		ResultInfo<SmsTemplate> resultInfo = null;
		resultInfo = smsTemplateService.updateSmsTemplate(smsTemplate, op);
		return resultInfo;

	}

	/**
	 * 删除商城短信模板
	 */
	@RequestMapping("deleteSmsTemplate")
	@ResponseBody
	public ResultInfo<SmsTemplate> deleteSmsTemplate(@RequestParam("emplateId") String emplateId) {
		ResultInfo<SmsTemplate> resultInfo= smsTemplateService.delSmsTemplate(emplateId, getOperator());
		return resultInfo;
	}

	/**
	 * 根据主键，查询一条商城短信模板
	 */
	@RequestMapping("getSmsTemplate")
	@ResponseBody
	public SmsTemplate getSmsTemplate(@RequestParam("emplateId") String emplateId) {

		return smsTemplateService.getSmsTemplate(emplateId);
	}

}
