package cn.com.shopec.mgt.ml.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.service.CJoinService;
import cn.com.shopec.core.ml.vo.JoinVo;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @author daiyuanabo
 * @category 申请加盟
 *
 */
@Controller
@RequestMapping("join")
public class JoinController extends BaseController{

	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CJoinService cJoinServices;
	/**
	 * 进入加盟列表
	 */
	@RequestMapping("/getJoin")
	public String getJoin(Model model){
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("APPLY_JOIN");//申请加盟
		model.addAttribute("cities", cities);
		return  "ml/join_list";
	}
	/**
	 * 获取加盟列表
	 * @param joinVo
	 * @param query
	 * @return
	 */
	@RequestMapping("joinList")
	@ResponseBody
	public PageFinder<JoinVo> joinList(JoinVo joinVo,Query query){
		return cJoinServices.getJoin(new Query(query.getPageNo(),query.getPageSize(),joinVo));
	}
	/**
	 * 进入加盟详情
	 * @param joinNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/getJoinNo")
	public String getJoinNo(String joinNo,Model model){
		model.addAttribute("join", cJoinServices.getJoinNo(joinNo));
		return "ml/join_view";
	}
}
