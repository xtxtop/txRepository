package cn.com.shopec.mgt.system.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.system.controller.SysRegionController.ComparatorMap;

@Controller
@RequestMapping("sysRegion")
public class SysRegionController extends BaseController {
	
	@Resource
	public SysRegionService sysRegionService;

	/*
	 * 显示系统地区管理
	 */
	@RequestMapping("mainPage")
	public String sysUser() {
		return "system/sysRegion";
	}
	/**
	 * 地址 省
	 * 
	 * @param order
	 * @return string
	 */
	@RequestMapping("/getProvince")
	@ResponseBody
	public List<SysRegion> getProvince(String provinceName) {
		List<SysRegion> plists=new ArrayList<SysRegion>();
		if("请选择".equals(provinceName)){
			plists = sysRegionService.getProvices();// 省
		}else{
			plists = sysRegionService.getProvince(provinceName);
		}
		return plists;
	}
	/**
	 * 地址 市
	 * 
	 * @param order
	 * @return string
	 */
	@RequestMapping("getCitys")
	@ResponseBody
	public List<SysRegion> getCitys(String id) {
		List<SysRegion> plists = sysRegionService.getCitys(id);
		return plists;
	}

	/**
	 * 地址 县区
	 * 
	 * @param order
	 * @return string
	 */
	@RequestMapping("getCountrys")
	@ResponseBody
	public List<SysRegion> getCountrys(String id) {
		List<SysRegion> plists = sysRegionService.getCountrys(id);
		return plists;
	}
	/*
	 * 添加或修改系统地区管理
	 */
	@RequestMapping("addOrEditSysRegion")
	@ResponseBody
	public ResultInfo<SysRegion> addOrEditSysRegion(@ModelAttribute("sysRegion")SysRegion sysRegion) {		
		//操作人
		Operator op = getOperator();
		if(op != null){
			sysRegion.setOperatorId(op.getOperatorId());
			sysRegion.setOperatorType(op.getOperatorType());
		}
		ResultInfo<SysRegion> resultInfo = sysRegionService.addOrEditSysRegion(sysRegion);
		return resultInfo;		
	}
	
	/*
	 * 显示系统地区信息
	 */
	@RequestMapping("detail")
	@ResponseBody
	public SysRegion detail(@RequestParam("regionId") String regionId) {
		return sysRegionService.detail(regionId);
	}
	
	/*
	 * 删除系统地区
	 */
	@RequestMapping("deleteSysRegion")
	@ResponseBody
	public ResultInfo<SysRegion> deleteSysRegion(@RequestParam("regionId") String regionId) {
		return sysRegionService.delete(regionId,getOperator());
	}
	
	/*
	 * 分页展示系统地区
	 */
	@RequestMapping("getSysRegionList")
	@ResponseBody
	public PageFinder<SysRegion> getSysRegionList(@ModelAttribute("sysRegion") SysRegion sysRegion,
			Query query) {		
		//sysRegion.setLevel(1);
		Query q = new Query(query.getPageNo(),query.getPageSize(),sysRegion);
		return sysRegionService.pageList(q);
		
	}
	
	/*
	 * 批量删除系统地区
	 */
	@RequestMapping("batchDelete")
	@ResponseBody
	public ResultInfo<SysRegion> batchDelete(@RequestParam("sysRegionIds") String [] sysRegionIds) {
		return sysRegionService.batchDelete(sysRegionIds,getOperator());
	}
	/*
	 * 获得所有的省级(1级)
	 */
	@RequestMapping("getProvinces")
	@ResponseBody
	public List<SysRegion> getProvinces(ModelMap model, HttpServletRequest request) {
		SysRegion sysRegion = new SysRegion();
		sysRegion.setIsDeleted(0);
		sysRegion.setLevel(1);
		Query q = new Query(sysRegion);
		return sysRegionService.list(q);
	}
	/*
	 * 全部商品类型（页面树形展示）
	 */
	@RequestMapping("tree")
	@ResponseBody
	public List<Map<String, Object>> tree(@ModelAttribute("sysRegion")SysRegion sysRegion) {
		sysRegion.setIsDeleted(0);
		Query q = new Query(sysRegion);
		List<SysRegion> sysRegionList=sysRegionService.list(q);
		List<Map<String, Object>> resultSysRegionList=new ArrayList<Map<String, Object>>();
		resultSysRegionList = getAllNodes(sysRegionList); 
		Collections.sort(resultSysRegionList, new ComparatorMap());
		return resultSysRegionList;
	}
	
	/*
	 * 全部商品类型（获取所有节点）
	 */	
	private List<Map<String, Object>> getAllNodes(List<SysRegion> sysRegionList){
		List<Map<String, Object>> resultSysRegionList=new ArrayList<Map<String, Object>>();
		for (SysRegion temp : sysRegionList) {  
            if (temp.getParentId()==null ) {  
            	List<Map<String, Object>> children=getChildNodes(sysRegionList,temp.getRegionId());
            	Map<String, Object> viewSysRegion=new HashMap<String, Object>();
            	viewSysRegion.put("id",temp.getRegionId());
            	viewSysRegion.put("text", temp.getRegionName());
            	viewSysRegion.put("ranking", temp.getLevel());

            	viewSysRegion.put("children", children);
            	viewSysRegion.put("regionId", temp.getRegionId());
            	viewSysRegion.put("regionName", temp.getRegionName());
            	viewSysRegion.put("postCode", temp.getPostCode());
            	viewSysRegion.put("createTime", temp.getCreateTime());
            	viewSysRegion.put("updateTime", temp.getUpdateTime());
            	viewSysRegion.put("isAvailable", temp.getIsAvailable());
            	viewSysRegion.put("isDeleted", temp.getIsDeleted());
    			Collections.sort(children, new ComparatorMap());
    			resultSysRegionList.add(viewSysRegion);
            }  
        } 
		return resultSysRegionList;
	}
	
	
	
	/*
	 * 全部商品类型（页面树形展示）
	 */
	@RequestMapping("childTree")
	@ResponseBody
	public List<Map<String, Object>> childTree(@ModelAttribute("sysRegion") SysRegion sysRegion) {
		SysRegion sys = new SysRegion();
		sys.setIsDeleted(0);
		Query q = new Query(sys);
		List<SysRegion> sysRegionList =sysRegionService.list(q);
		List<Map<String, Object>> resultSysRegionList=new ArrayList<Map<String, Object>>();
		resultSysRegionList=getChildNodes(sysRegionList,sysRegion.getRegionId()); 
		Collections.sort(resultSysRegionList, new ComparatorMap());
		return resultSysRegionList;
	}
	
	
	/*
	 * 全部商品类型（根据父ID获取所有子节点）
	 */	
	private List<Map<String, Object>> getChildNodes(List<SysRegion> sysRegionList,String parendId){
		List<Map<String, Object>> resultSysRegionList=new ArrayList<Map<String, Object>>();
		for (SysRegion temp : sysRegionList) {  
            if (temp.getParentId()!=null && temp.getParentId().equals(parendId)) {  
            	List<Map<String, Object>> children=getChildNodes(sysRegionList,temp.getRegionId());
            	Map<String, Object> viewSysRegion=new HashMap<String, Object>();
    			viewSysRegion.put("id", temp.getRegionId());
    			viewSysRegion.put("text", temp.getRegionName());
    			viewSysRegion.put("ranking", temp.getLevel());
    			viewSysRegion.put("children", children);

    			viewSysRegion.put("regionName", temp.getRegionName());
    			viewSysRegion.put("children", children);
    			viewSysRegion.put("regionId", temp.getRegionId());
    			viewSysRegion.put("parentId", temp.getParentId());
    			viewSysRegion.put("isDeleted", temp.getIsDeleted());
    			viewSysRegion.put("postCode", temp.getPostCode());
            	viewSysRegion.put("createTime", temp.getCreateTime());
            	viewSysRegion.put("updateTime", temp.getUpdateTime());
            	viewSysRegion.put("isAvailable", temp.getIsAvailable());
    			Collections.sort(children, new ComparatorMap());
    			resultSysRegionList.add(viewSysRegion);
            }  
        } 
		return resultSysRegionList;
	}
	
	/*
	 * 类型排序
	 */
	class ComparatorMap implements Comparator<Map<String, Object>> {

		@Override
		public int compare(Map<String, Object> obj1, Map<String, Object> obj2) {
			if (obj1.get("ranking") != null && obj2.get("ranking") != null) {
				return ((Integer) obj1.get("ranking")).compareTo((Integer) obj2.get("ranking"));
			} else {
				return 1;
			}
		}

	}
	
	/*
	 * 地区树
	 */
	@RequestMapping("getSysRegionTree")
	@ResponseBody
	public List<Map<String, Object>> getSysRegionTree(HttpServletResponse response,
			HttpServletRequest request, ModelMap model,String type) throws Exception {
		SysRegion sysRegion = new SysRegion();
		Query q = new Query(sysRegion);
		return sysRegionService.getSysRegionTree(q,type);
	}
}
