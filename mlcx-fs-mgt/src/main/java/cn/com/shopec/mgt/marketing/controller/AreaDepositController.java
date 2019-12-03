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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.marketing.model.AreaDeposit;
import cn.com.shopec.core.marketing.service.AreaDepositService;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("areaDeposit")
public class AreaDepositController extends BaseController {
	@Resource
	private AreaDepositService areaDepositService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private CompanyService companyService;
	@Resource
	public SysRegionService sysRegionService;
	
	/**
	 * 城市应繳押金列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAreaDepositList")
	public String toAreaDepositList() {
		return "marketing/area_deposit_list";
	}

	/**
	 * 城市应繳押金列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListAreaDeposit")
	@ResponseBody
	public PageFinder<AreaDeposit> pageListAreaDeposit(@ModelAttribute("areaDeposit") AreaDeposit areaDeposit, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), areaDeposit);
		return areaDepositService.getAreaDepositPagedList(q);
	}

	/**
	 * 城市应繳押金详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAreaDepositView")
	public String toAreaDepositView(String id, ModelMap modelMap) {
		AreaDeposit areaDeposit = areaDepositService.getAreaDeposit(id);
		String addrRegion = "";
		if(areaDeposit.getAddrRegion1Name()!=null){
			addrRegion += areaDeposit.getAddrRegion1Name();
		}
		if(areaDeposit.getAddrRegion2Name()!=null){
			addrRegion += areaDeposit.getAddrRegion2Name();
		}
		if(areaDeposit.getAddrRegion3Name()!=null){
			addrRegion += areaDeposit.getAddrRegion3Name();
		}
		areaDeposit.setAddrRegion(addrRegion);
		modelMap.put("areaDeposit", areaDeposit);
		return "marketing/area_deposit_view";
	}

	/**
	 * 城市应繳押金增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddAreaDeposit")
	public String toAddAreaDeposit(ModelMap modelMap) {
		List<SysRegion> plists = sysRegionService.getProvices();// 省
		List<SysRegion> plists2 = sysRegionService.getCitys("440000");// 市
		List<SysRegion> plists3 = sysRegionService.getCountrys("440300");// 区
		AreaDeposit areaDeposit = new AreaDeposit();
		areaDeposit.setAddrRegion1Id("440000");
		areaDeposit.setAddrRegion2Id("440300");
		areaDeposit.setAddrRegion3Id("440303");
		modelMap.put("plists3", plists3);
		modelMap.put("plists2", plists2);
		modelMap.put("plist", plists);
		modelMap.put("areaDeposit", areaDeposit);
		return "marketing/area_deposit_add";
	}

	/**
	 * 城市应繳押金添加
	 * 
	 * @return
	 */
	@RequestMapping("/addAreaDeposit")
	@ResponseBody
	public ResultInfo<AreaDeposit> addAreaDeposit(@ModelAttribute("areaDeposit") AreaDeposit areaDeposit) {
		// 省市区
		if (areaDeposit.getAddrRegion1Id() != null && areaDeposit.getAddrRegion1Id().length() != 0) {
			String addrRegion1Name = sysRegionService.detail(areaDeposit.getAddrRegion1Id()).getRegionName();
			areaDeposit.setAddrRegion1Name(addrRegion1Name);
		}
		if (areaDeposit.getAddrRegion2Id() != null && areaDeposit.getAddrRegion2Id().length() != 0) {
			String addrRegion2Name = sysRegionService.detail(areaDeposit.getAddrRegion2Id()).getRegionName();
			areaDeposit.setAddrRegion2Name(addrRegion2Name);
		}
		String addrRegion3Name = "";
		if (areaDeposit.getAddrRegion3Id() != null && areaDeposit.getAddrRegion3Id().length() != 0) {
			addrRegion3Name = sysRegionService.detail(areaDeposit.getAddrRegion3Id()).getRegionName();
			addrRegion3Name = addrRegion3Name == null ? "" : addrRegion3Name;
		}
		areaDeposit.setAddrRegion3Name(addrRegion3Name);
		ResultInfo<AreaDeposit> result = areaDepositService.addAreaDeposit(areaDeposit, getOperator());
		return result;
	}

	/**
	 * 城市应繳押金编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateAreaDeposit")
	public String toUpdateAreaDeposit(String id, ModelMap modelMap) {
		
		AreaDeposit areaDeposit = areaDepositService.getAreaDeposit(id);
		List<SysRegion> plists = sysRegionService.getProvices();// 省
		List<SysRegion> plists2 = sysRegionService.getCitys(areaDeposit.getAddrRegion1Id());// 市
		if (areaDeposit.getAddrRegion2Id() != null) {
			List<SysRegion> plists3 = sysRegionService.getCountrys(areaDeposit.getAddrRegion2Id());// 区
			modelMap.put("plists3", plists3);
		}
		modelMap.put("plist", plists);
		modelMap.put("plists2", plists2);
		
		modelMap.addAttribute("areaDeposit", areaDeposit);
		return "marketing/area_deposit_edit";
	}

	/**
	 * 城市应繳押金编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateAreaDeposit")
	@ResponseBody
	public ResultInfo<AreaDeposit> updateAreaDeposit(@ModelAttribute("areaDeposit") AreaDeposit areaDeposit) {
		// 省市区
		if (areaDeposit.getAddrRegion1Id() != null && areaDeposit.getAddrRegion1Id().length() != 0) {
			String addrRegion1Name = sysRegionService.detail(areaDeposit.getAddrRegion1Id()).getRegionName();
			areaDeposit.setAddrRegion1Name(addrRegion1Name);
		}
		if (areaDeposit.getAddrRegion2Id() != null && areaDeposit.getAddrRegion2Id().length() != 0) {
			String addrRegion2Name = sysRegionService.detail(areaDeposit.getAddrRegion2Id()).getRegionName();
			areaDeposit.setAddrRegion2Name(addrRegion2Name);
		}
		if (areaDeposit.getAddrRegion3Id() != null && areaDeposit.getAddrRegion3Id().length() != 0) {
			String	addrRegion3Name = sysRegionService.detail(areaDeposit.getAddrRegion3Id()).getRegionName();
			addrRegion3Name = addrRegion3Name == null ? "" : addrRegion3Name;
			areaDeposit.setAddrRegion3Name(addrRegion3Name);

		}
		
		ResultInfo<AreaDeposit> result = areaDepositService.updateAreaDeposit(areaDeposit, getOperator());
		return result;
	}

	/**
	 * 城市应繳押金审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAreaDepositCensorView")
	public String toAreaDepositCensorView(String id, ModelMap model) {
		AreaDeposit areaDeposit = areaDepositService.getAreaDeposit(id);
		String addrRegion = "";
		if(areaDeposit.getAddrRegion1Name()!=null){
			addrRegion += areaDeposit.getAddrRegion1Name();
		}
		if(areaDeposit.getAddrRegion2Name()!=null){
			addrRegion += areaDeposit.getAddrRegion2Name();
		}
		if(areaDeposit.getAddrRegion3Name()!=null){
			addrRegion += areaDeposit.getAddrRegion3Name();
		}
		areaDeposit.setAddrRegion(addrRegion);
		model.addAttribute("areaDeposit", areaDeposit);
		return "marketing/area_deposit_censor";
	}

	/**
	 * 城市应繳押金审核提交
	 * 
	 * @return
	 */
	@RequestMapping("/censorAreaDeposit")
	@ResponseBody
	public ResultInfo<AreaDeposit> censorAreaDeposit(@ModelAttribute("areaDeposit") AreaDeposit areaDeposit) {
		areaDeposit.setCensorTime(new Date());
		areaDeposit.setCensorId(getOperator().getOperatorId());
		return areaDepositService.updateAreaDeposit(areaDeposit, getOperator());
	}

	
}
