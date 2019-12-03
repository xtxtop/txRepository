package cn.com.shopec.mgt.mall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mall.model.MallItem;
import cn.com.shopec.core.mall.model.MallItemSort;
import cn.com.shopec.core.mall.service.MallItemService;
import cn.com.shopec.core.mall.service.MallItemSortService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("mall")
public class MallItemController extends BaseController {

	@Resource
	private MallItemService mallItemService;

	@Resource
	private MallItemSortService mallItemSortService;

	/**
	 * 积分商城商品列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toItemList")
	public String toItemList() {
		return "mall/item_list";
	}

	/**
	 * 积分商城商品列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListItem")
	@ResponseBody
	public PageFinder<MallItem> pageListItem(@ModelAttribute("item") MallItem item, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), item);
		return mallItemService.getMallItemPagedList(q);
	}

	/**
	 * 积分商城商品详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toItemView")
	public String toItemView(String itemNo, ModelMap modelMap) {
		MallItem item = mallItemService.getMallItem(itemNo);
		modelMap.put("item", item);
		return "mall/item_view";
	}

	/**
	 * 积分商城商品增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddItem")
	public String toAddItem() {
		return "mall/item_add";
	}

	/**
	 * 积分商城商品添加
	 * 
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResultInfo<MallItem> addItem(@ModelAttribute("mallItem") MallItem item) {
		ResultInfo<MallItem> result = mallItemService.addMallItem(item);
		return result;
	}

	/**
	 * 积分商城商品编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateItem")
	public String toUpdateItem(String itemNo, ModelMap model) {
		MallItem item = mallItemService.getMallItem(itemNo);
		model.addAttribute("item", item);
		return "mall/item_edit";
	}

	/**
	 * 积分商城商品编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateItem")
	@ResponseBody
	public ResultInfo<MallItem> updatItem(@ModelAttribute("item") MallItem item) {
		ResultInfo<MallItem> result = mallItemService.updateMallItem(item);
		return result;
	}

	/**
	 * 积分商城商品分类列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toItemSortList")
	public String toItemSortList() {
		return "mall/itemSort_list";
	}

	/**
	 * 积分商城商品分类列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListItemSort")
	@ResponseBody
	public PageFinder<MallItemSort> pageListItemSort(@ModelAttribute("item") MallItemSort itemSort, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), itemSort);
		return mallItemSortService.getMallItemSortPagedList(q);
	}

	/**
	 * 积分商城商品分类详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toItemSortView")
	public String toItemSortView(String sortNo, ModelMap modelMap) {
		MallItemSort itemSort = mallItemSortService.getMallItemSort(sortNo);
		modelMap.put("itemSort", itemSort);
		return "mall/itemSort_view";
	}

	/**
	 * 积分商城商品分类增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddItemSort")
	public String toAddItemSort() {
		return "mall/itemSort_add";
	}

	/**
	 * 积分商城商品分类添加
	 * 
	 * @return
	 */
	@RequestMapping("/addItemSort")
	@ResponseBody
	public ResultInfo<MallItemSort> addItemSort(@ModelAttribute("itemSort") MallItemSort itemSort) {
		ResultInfo<MallItemSort> result = mallItemSortService.addMallItemSort(itemSort);
		return result;
	}

	/**s
	 * 积分商城商品分类编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateItemSort")
	public String toUpdateItemSort(String sortNo, ModelMap model) {
		MallItemSort itemSort = mallItemSortService.getMallItemSort(sortNo);
		model.addAttribute("itemSort", itemSort);
		return "mall/itemSort_edit";
	}

	/**
	 * 积分商城商品分类编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateItemSort")
	@ResponseBody
	public ResultInfo<MallItemSort> updateItemSort(@ModelAttribute("itemSort") MallItemSort itemSort) {
		ResultInfo<MallItemSort> result = mallItemSortService.updateMallItemSort(itemSort);
		return result;
	}

	/*
	 * 全部商品分类（页面树形展示）
	 */
	@RequestMapping("itemSortTree")
	@ResponseBody
	public List<Map<String, Object>> itemSortTree(String type, String selectedId) {
		Query q = new Query(1, 10000, new MallItemSort());
		List<MallItemSort> itemSortList = mallItemSortService.getMallItemSortList(q);
		List<Map<String, Object>> resultItemCatList = new ArrayList<Map<String, Object>>();
		if (!"1".equals(type)) {
			// 添加一级分类
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", "0");
			map.put("text", "一级分类");
			map.put("parent", "#");
			resultItemCatList.add(map);
		}
		for (MallItemSort temp : itemSortList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", temp.getSortNo());
			map.put("text", temp.getSortName());
			if (temp.getParentSortNo() == null || temp.getParentSortNo().trim().length() == 0 || temp.getParentSortNo().equals("0")) {
				map.put("parent", "#");
			} else {
				map.put("parent", temp.getParentSortNo());
			}
			if(temp.getSortNo().equals(selectedId)){
				Map<String,Object> stateMap = new HashMap<String, Object>();
				stateMap.put("selected", true);
				map.put("state", stateMap);
			}
			resultItemCatList.add(map);
		}
		return resultItemCatList;
	}

}
