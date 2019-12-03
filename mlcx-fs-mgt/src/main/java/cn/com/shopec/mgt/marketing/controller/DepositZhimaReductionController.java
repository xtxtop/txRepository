package cn.com.shopec.mgt.marketing.controller;

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
import cn.com.shopec.core.marketing.model.DepositZhimaReduction;
import cn.com.shopec.core.marketing.service.DepositZhimaReductionService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 芝麻分扣免押金
 */
@Controller
@RequestMapping("depositZhimaReduction")
public class DepositZhimaReductionController extends BaseController {
	@Resource
	private DepositZhimaReductionService depositZhimaReductionService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
    private SysUserService sysUserService;
	
	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toDepositZhimaReductionList")
	public String toDepositZhimaReductionList() {
		return "marketing/depositZhimaReduction_list";
	}

	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListDepositZhimaReduction")
	@ResponseBody
	public PageFinder<DepositZhimaReduction> pageListDepositZhimaReduction(@ModelAttribute("depositZhimaReduction") DepositZhimaReduction depositZhimaReduction, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), depositZhimaReduction);
		return depositZhimaReductionService.getDepositZhimaReductionPagedList(q);
	}
	
	/**
	 * 跳转详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toDepositZhimaReductionView")
	public String toDepositZhimaReductionView(String id, ModelMap modelMap) {
		DepositZhimaReduction depositZhimaReduction = depositZhimaReductionService.getDepositZhimaReduction(id);
		modelMap.put("depositZhimaReduction", depositZhimaReduction);
		return "marketing/depositZhimaReduction_view";
	}

	/**
	 * 跳转新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddDepositZhimaReduction")
	public String toAddDepositZhimaReduction(ModelMap modelMap) {
		return "marketing/depositZhimaReduction_add";
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping("/addDepositZhimaReduction")
	@ResponseBody
	public ResultInfo<DepositZhimaReduction> addDepositZhimaReduction(@ModelAttribute("depositZhimaReduction") DepositZhimaReduction depositZhimaReduction) {
		if(depositZhimaReduction.getZhimaScore() != null && !depositZhimaReduction.getZhimaScore().equals("")){
			//校验启用唯一性
			DepositZhimaReduction reductionQuery = new DepositZhimaReduction();
			reductionQuery.setZhimaScore(depositZhimaReduction.getZhimaScore());
			List<DepositZhimaReduction> reductionList = depositZhimaReductionService.getDepositZhimaReductionList(new Query(reductionQuery));
			if(reductionList.size() > 0){
				ResultInfo<DepositZhimaReduction> resultInfo = new ResultInfo<DepositZhimaReduction>();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("无法同时添加两个相同的芝麻分扣除押金的设置");
				return resultInfo;
			}
		}
		
		return depositZhimaReductionService.addDepositZhimaReduction(depositZhimaReduction, getOperator());
	}
	
	
	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditDepositZhimaReduction")
	public String toEditDepositZhimaReduction(String id, ModelMap modelMap) {
		DepositZhimaReduction depositZhimaReduction = depositZhimaReductionService.getDepositZhimaReduction(id);
		modelMap.put("depositZhimaReduction", depositZhimaReduction);
		return "marketing/depositZhimaReduction_edit";
	}

	/**
	 * 普通的修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateDepositZhimaReduction")
	@ResponseBody
	public ResultInfo<DepositZhimaReduction> updateDepositZhimaReduction(@ModelAttribute("depositZhimaReduction") DepositZhimaReduction depositZhimaReduction) {
		//处理审核
		/*Operator operator = this.getOperator();
		if(operator != null && depositZhimaReduction.getCensorStatus() != null){
			depositZhimaReduction.setCensorTime(new Date());
			depositZhimaReduction.setCensorId(operator.getOperatorId());
		}*/
		
		if(depositZhimaReduction.getZhimaScore() != null && !depositZhimaReduction.getZhimaScore().equals("")){
			//校验启用唯一性
			DepositZhimaReduction reductionQuery = new DepositZhimaReduction();
			reductionQuery.setZhimaScore(depositZhimaReduction.getZhimaScore());
			List<DepositZhimaReduction> reductionList = depositZhimaReductionService.getDepositZhimaReductionList(new Query(reductionQuery));
			if(reductionList.size() > 0){
				for (DepositZhimaReduction temp : reductionList) {
					if(!temp.getId().equals(depositZhimaReduction.getId())){
						ResultInfo<DepositZhimaReduction> resultInfo = new ResultInfo<DepositZhimaReduction>();
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("已有相同的芝麻分扣除押金的设置");
						return resultInfo;
					}
				}
			}
		}
		
		return depositZhimaReductionService.updateDepositZhimaReduction(depositZhimaReduction, this.getOperator());
	}
	
	
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteDepositZhimaReduction")
	@ResponseBody
	public ResultInfo<DepositZhimaReduction> deleteDepositZhimaReduction(@ModelAttribute("depositZhimaReduction") DepositZhimaReduction depositZhimaReduction) {
		return depositZhimaReductionService.delDepositZhimaReduction(depositZhimaReduction.getId(), getOperator());
	}
	
	
}
