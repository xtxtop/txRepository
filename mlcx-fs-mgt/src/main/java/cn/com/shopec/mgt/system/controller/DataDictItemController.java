package cn.com.shopec.mgt.system.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 
 * @author machao 2016-9-24
 *
 */
@Controller
@RequestMapping("dataDictItem")
public class DataDictItemController extends BaseController {
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;

	/**
	 * 数据字典项显示页面
	 * 
	 * @return
	 */
	@RequestMapping("dataDictItemPage")
	public String dataDictPage(Model model) {
		List<DataDictCat> dataDictCatList = dataDictCatService.getDataDictCatList(new Query());
		model.addAttribute("dataDictCatList", dataDictCatList);
		return "/system/dataDictItem";
	}

	/**
	 * 数据字典项查询
	 * 
	 * @param dataDictCatItem
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("dataDictItemPageList")
	@ResponseBody
	public PageFinder<DataDictItem> dataDictItemPageList(@ModelAttribute("DataDictItem") DataDictItem dataDictCatItem, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		Query q = new Query(pageNo, pageSize, dataDictCatItem);
		return dataDictItemService.getDataDictItemPagedList(q);
	}

	/**
	 *数据字典项增加
	 * 
	 * @param dataDictCatItem
	 * @return
	 */
	@RequestMapping("addDataDictItem")
	@ResponseBody
	public ResultInfo<DataDictItem> addDataDictItem(@ModelAttribute("DataDictItem") DataDictItem dataDictItem) {
		ResultInfo<DataDictItem> resultInfo = new ResultInfo<DataDictItem>();
		DataDictItem  dataDictItemss=new DataDictItem();
		dataDictItemss.setDataDictCatCode(dataDictItem.getDataDictCatCode());
		dataDictItemss.setParentItemId(dataDictItem.getParentItemId());
		dataDictItemss.setItemValue(dataDictItem.getItemValue());
		Query q=new Query(dataDictItemss);
		List<DataDictItem> dataDictItems=dataDictItemService.getDataDictItemList(q);
		if(dataDictItems != null && dataDictItems.size()>0 ){
			resultInfo.setCode(Constant.FAIL);
			 resultInfo.setMsg("数据字典项值有对应的重复");
		}else{
			resultInfo= dataDictItemService.addDataDictItem(dataDictItem, getOperator());
		}
		return resultInfo;
		 
		
	}
	/**
	 * 数据字典项修改
	 * 
	 * @param dataDictCatItem
	 * @return
	 */
	@RequestMapping("editDataDictItem")
	@ResponseBody
	public ResultInfo<DataDictItem> editDataDictItem(@ModelAttribute("DataDictItem") DataDictItem dataDictItem) {
		ResultInfo<DataDictItem> resultInfo = new ResultInfo<DataDictItem>();
		DataDictItem  dataDictItemss=new DataDictItem();
		dataDictItemss.setDataDictCatCode(dataDictItem.getDataDictCatCode());
		dataDictItemss.setParentItemId(dataDictItem.getParentItemId());
		dataDictItemss.setItemValue(dataDictItem.getItemValue());
		Query q=new Query(dataDictItemss);
		List<DataDictItem> dataDictItems=dataDictItemService.getDataDictItemList(q);
		if(dataDictItems != null && dataDictItems.size()>0 &&  !dataDictItems.get(0).getDataDictItemId().equals(dataDictItem.getDataDictItemId())){
			resultInfo.setCode(Constant.FAIL);
			 resultInfo.setMsg("数据字典项值有对应的重复");
		}else{
			resultInfo= dataDictItemService.updateDataDictItem(dataDictItem, getOperator());
		}
		return resultInfo;
		
	}

	/**
	 * 数据字典项添加页面
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("toAddDataDictItem")
	public String toAddDataDictItem(Model model) {
		List<DataDictCat> dataDictCatList = dataDictCatService.getDataDictCatList(new Query());
		List<DataDictItem> dataDictItemList = dataDictItemService.getDataDictCatCode();
		model.addAttribute("dataDictCatList", dataDictCatList);
		model.addAttribute("dataDictItemList",dataDictItemList);
		return "system/addDataDictItem";
	}
	
	
	/**
	 * 根据父级  查出值
	 * */
	@RequestMapping("getModleByBrand")
	@ResponseBody
	public ResultInfo<List<DataDictItem>> getModleByBrand(String parentCatCode){
		ResultInfo<List<DataDictItem>> res = new ResultInfo<>();
		List<DataDictItem> items =dataDictItemService.getModleByBrand(parentCatCode);
		res.setCode(Constant.SECCUESS);
		res.setData(items);
		return res;
	}
	
	/**
	 * 数据字典项编辑页面
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("toEditDataDictItem")
	public String toEditDataDictItem(@RequestParam("dataDictItemId") String dataDictItemId,Model model) {
		DataDictItem dataDictItem = dataDictItemService.getDataDictItem(dataDictItemId);
		List<DataDictCat> dataDictCatList = dataDictCatService.getDataDictCatList(new Query());
		List<DataDictItem> dataDictItemList = dataDictItemService.getDataDictCatCode();
		//根据 父级 查出 对应的
		if(dataDictItem != null && dataDictItem.getParentItemId() != null ){
			DataDictItem dataDictItemp = dataDictItemService.getDataDictItem(dataDictItem.getParentItemId());
				model.addAttribute("dataDictItemp", dataDictItemp);
				if(dataDictItemp != null && dataDictItemp.getDataDictCatCode() !=null  ){
					DataDictItem dataDictItempb=new DataDictItem();
					dataDictItempb.setDataDictCatCode(dataDictItemp.getDataDictCatCode());
					Query q=new Query(dataDictItempb);
					List<DataDictItem> carBrands = dataDictItemService.getDataDictItemList(q);
					model.addAttribute("carBrands", carBrands);
				}
				
			}
		
		model.addAttribute("dataDictItem", dataDictItem);
		model.addAttribute("dataDictCatList", dataDictCatList);
		model.addAttribute("dataDictItemList",dataDictItemList);
		return "system/editDataDictItem";
	}
	/**
	 * 数据字典项详情页面
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("toViewDataDictItem")
	public String toViewDataDicItem(@RequestParam("dataDictItemId") String dataDictItemId,Model model) {
		DataDictItem dataDictItem = dataDictItemService.getDataDictItem(dataDictItemId);
		if(dataDictItem != null && dataDictItem.getParentItemId() != null ){
			DataDictItem dataDictItemp = dataDictItemService.getDataDictItem(dataDictItem.getParentItemId());
				model.addAttribute("dataDictItemp", dataDictItemp);
			}
		
		model.addAttribute("dataDictItem", dataDictItem);
		return "system/viewDataDictItem";
	}
	/**
	 * 数据字典项删除
	 * 
	 * @param dataDictCatId
	 * @return
	 */
	@RequestMapping("delDataDictItem")
	@ResponseBody
	public ResultInfo<DataDictItem> delDataDictItem(@RequestParam("dataDictItemId") String dataDictItemId) {
		return dataDictItemService.delDataDictItem(dataDictItemId, getOperator());
	}
}
