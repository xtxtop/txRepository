package cn.com.shopec.mgt.ml.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CLabel;
import cn.com.shopec.core.ml.model.CMatching;
import cn.com.shopec.core.ml.service.CMatchingService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 
 * @author daiyuanbao
 * @category 配套服务
 *
 */
@Controller
@RequestMapping("/matching")
public class MatchingController extends BaseController{

	@Resource
	private  CMatchingService cMatchingServiceImpl;
	/**
	 * 进入配套服务列表首页
	 * @return
	 */
	@RequestMapping("/getMatching")
	public String getMatching(){
		return "ml/matching_list";
	}
	/**
	 * 获取配套服务列表
	 * @param CMatching
	 * @param query
	 * @return
	 */
	@RequestMapping("/pageListMatching")
	@ResponseBody
	public PageFinder<CMatching>  pageListMatching(@ModelAttribute("CMatching") CMatching CMatching,Query query){
		return cMatchingServiceImpl.getCMatchingPagedList(new Query(query.getPageNo(),query.getPageSize(),CMatching));
	}
	/**
	 * 查看详情
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toMatchingView")
	public String toMatchingView(String matchingId,Model model){
		 CMatching matching=cMatchingServiceImpl.getCMatching(matchingId);
		model.addAttribute("CMatching",matching);
		return "ml/matching_view";
	}
	
	/**
	 * 跳转新增配套服务页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddMatching")
	public String toAddMatching(Model model){
		return "ml/matching_add";
	}
	
	//新增配套服务
		@RequestMapping("/addMatching")
		@ResponseBody
		public ResultInfo<CMatching> addMatching(@ModelAttribute("CMatching") CMatching CMatching){
			ResultInfo<CMatching> resultInfo = new ResultInfo<CMatching>();
			CMatching c = new CMatching();
			c.setMatchingName(CMatching.getMatchingName());
			c.setMatchingType(CMatching.getMatchingType());
			List<CMatching> Matching =cMatchingServiceImpl.getCMatchingList(new Query(c));
			if(Matching != null && Matching.size()>0){
				resultInfo.setCode("0");
				resultInfo.setMsg("配套服务名称不能重复");
				return resultInfo;
			}
			return cMatchingServiceImpl.addCMatching(CMatching, getOperator());
		}
	
	/**
	 * 启用停用
	 * @param stationNo
	 * @param isAvailable
	 * @return
	 */
	@RequestMapping("/upMatching")
	@ResponseBody
	public ResultInfo<CMatching> upMatching(String matchingId,String enableStatus){
		CMatching c = new CMatching();
		c.setEnableStatus(Integer.parseInt(enableStatus));
		c.setMatchingId(matchingId);
		return cMatchingServiceImpl.updateCMatching(c, getOperator());
	}
	/**
	 * 跳转编辑配套服务页面
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toMatchingEdit")
	public String toMatchingEdit(String matchingId,Model model){
		//获取充电站信息
		CMatching matching=cMatchingServiceImpl.getCMatching(matchingId);
		model.addAttribute("CMatching",matching);
		return "ml/matching_edit";
	}
	/**
	 * 编辑配套服务
	 * @param Matching
	 * @return
	 */
	@RequestMapping("/editMatching")
	@ResponseBody
	public ResultInfo<CMatching> editMatching(@ModelAttribute("CMatching") CMatching CMatching){
		return cMatchingServiceImpl.updateCMatching(CMatching, getOperator());
	}
	/**
	 * 删除周边配套
	 * @param labelId
	 * @return
	 */
	@RequestMapping("/delMatching")
	@ResponseBody
	public ResultInfo<CMatching> delMatching(String matchingId){
		return cMatchingServiceImpl.delCMatching(matchingId, getOperator());
	}
	/**
	 * 验证周边配套状态
	 * @param labelId
	 * @return
	 */
	@RequestMapping("/verifyMatching")
	@ResponseBody
	public ResultInfo<CMatching> verifyMatching(String matchingId){
		ResultInfo<CMatching> resultInfo = new ResultInfo<CMatching>();
		CMatching matching = cMatchingServiceImpl.getCMatching(matchingId);
		if(matching.getEnableStatus()==1){
			resultInfo.setCode("0");
			resultInfo.setMsg("周边配套正在使用不能进行删除");
		}else{
			resultInfo.setCode("1");
		}
		return resultInfo;
	}
}
