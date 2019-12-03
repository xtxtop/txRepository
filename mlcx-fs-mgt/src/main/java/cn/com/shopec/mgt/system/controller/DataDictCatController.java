package cn.com.shopec.mgt.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.DataDictCat;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 
 * @author machao 2016-9-23
 *
 */
@Controller
@RequestMapping("dataDictCat")
public class DataDictCatController extends BaseController {
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;

	/**
	 * 数据字典分类显示页面
	 * 
	 * @return
	 */
	@RequestMapping("dataDictPage")
	public String dataDictPage() {
		return "/system/dataDictCat";
	}

	/**
	 * 数据字典分类查询
	 * 
	 * @param dataDictCat
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("dataDictPageList")
	@ResponseBody
	public PageFinder<DataDictCat> dataDictPageList(@ModelAttribute("DataDictCat") DataDictCat dataDictCat, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		Query q = new Query(pageNo, pageSize, dataDictCat);
		return dataDictCatService.getDataDictCatPagedList(q);
	}

	/**
	 * 数据字典分类增加
	 * 
	 * @param dataDictCat
	 * @return
	 */
	@RequestMapping("addDataDictCat")
	@ResponseBody
	public ResultInfo<DataDictCat> addDataDictCat(@ModelAttribute("DataDictCat") DataDictCat dataDictCat) {
		ResultInfo<DataDictCat> resultInfo = new ResultInfo<DataDictCat>();
		DataDictCat dtc =dataDictCatService.getDataDictCat(dataDictCat.getDataDictCatCode());
		if(dtc != null){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("分类编码已经存在");
		}else{
			resultInfo = dataDictCatService.addDataDictCat(dataDictCat, getOperator());
		}
		return resultInfo;
	}
	/**
	 * 数据字典分类修改
	 * 
	 * @param dataDictCat
	 * @return
	 */
	@RequestMapping("editDataDictCat")
	@ResponseBody
	public ResultInfo<DataDictCat> editDataDictCat(@ModelAttribute("DataDictCat") DataDictCat dataDictCat) {
		return dataDictCatService.updateDataDictCat(dataDictCat, getOperator());
	}

	/**
	 * 数据字典分类添加页面
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("toAddDataDictCat")
	public String addDataDictCat(Model model) {
		List<DataDictCat> dataDictCatList = dataDictCatService.getDataDictCatList(new Query());
		model.addAttribute("dataDictCatList", dataDictCatList);
		return "system/addDataDictCat";
	}
	/**
	 * 数据字典分类编辑页面
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("toEditDataDictCat")
	public String toEditDataDictCat(@RequestParam("dataDictCatCode") String dataDictCatCode,Model model) {
		DataDictCat dataDictCat = dataDictCatService.getDataDictCat(dataDictCatCode);
		model.addAttribute("dataDictCat", dataDictCat);
		List<DataDictCat> dataDictCatList = dataDictCatService.getDataDictCatList(new Query());
		model.addAttribute("dataDictCatList", dataDictCatList);
		return "system/editDataDictCat";
	}
	/**
	 * 数据字典分类详情页面
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("toViewDataDictCat")
	public String toViewDataDictCat(@RequestParam("dataDictCatCode") String dataDictCatCode,Model model) {
		DataDictCat dataDictCat = dataDictCatService.getDataDictCat(dataDictCatCode);
		model.addAttribute("dataDictCat", dataDictCat);
		return "system/viewDataDictCat";
	}
	/**
	 * 数据字典分类删除
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("delDataDict")
	@ResponseBody
	public ResultInfo<DataDictCat> delDataDict(@RequestParam("dataDictCatCode") String dataDictCatCode) {
		ResultInfo<DataDictCat> dictCatResultInfo =null;
//		ResultInfo<DataDictItem> dictItemResultInfo =null;
		dictCatResultInfo = dataDictCatService.delDataDictCat(dataDictCatCode, getOperator());
//		if (dictCatResultInfo.getCode() == "1") {
			//dataDictCatCode对应的dataDictItem是否删除，暂不使用
//			DataDictItem dataDictItemQuery = new DataDictItem();
//			dataDictItemQuery.setDataDictCatCode(dataDictCatCode);
//			Query q = new Query(dataDictItemQuery);
//			List<DataDictItem> dictItemList = dataDictItemService.getDataDictItemList(q);
//			for(DataDictItem dictItem:dictItemList){
//				try {
//					dictItemResultInfo =dataDictItemService.delDataDictItem(dictItem.getDataDictItemId(), getOperator());
//				} catch (Exception e) {
//					dictItemResultInfo.setCode(Constant.FAIL);
//				}
//			}
//		}else{
//			dictCatResultInfo.setCode(Constant.SECCUESS);
//		}
		return dictCatResultInfo;
	}
}
