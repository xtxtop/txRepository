package cn.com.shopec.mgt.ml.controller;

import java.util.Date;
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
import cn.com.shopec.core.ml.service.CLabelService;
import cn.com.shopec.mgt.common.BaseController;


/**
 * @author 代元宝
 * @category 标签
 *
 */
@Controller
@RequestMapping("label")
public class LabelController extends BaseController{
	
	@Resource
	private CLabelService clabelLabelServiceImpl;
	
	/**
	 * 进入标签列表首页
	 * @return
	 */
	@RequestMapping("/getLabel")
	public String getLabel(){
		return "ml/label_list";
	}
	/**
	 * 获取标签列表
	 * @param cLabel
	 * @param query
	 * @return
	 */
	@RequestMapping("/pageListLabel")
	@ResponseBody
	public PageFinder<CLabel>  pageListLabel(@ModelAttribute("cLabel") CLabel cLabel,Query query){
		return clabelLabelServiceImpl.getCLabelPagedList(new Query(query.getPageNo(),query.getPageSize(),cLabel));
	}
	/**
	 * 查看详情
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/tolabelView")
	public String tolabelView(String labelId,Model model){
		 CLabel label=clabelLabelServiceImpl.getCLabel(labelId);
		model.addAttribute("label",label);
		return "ml/label_view";
	}
	
	/**
	 * 跳转新增标签页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddlabel")
	public String toAddlabel(Model model){
		return "ml/label_add";
	}
	
	//新增标签
		@RequestMapping("/addlabel")
		@ResponseBody
		public ResultInfo<CLabel> addlabel(@ModelAttribute("cLabel") CLabel cLabel){
			ResultInfo<CLabel> resultInfo = new ResultInfo<CLabel>();
			CLabel c = new CLabel();
			c.setLabelName(cLabel.getLabelName());
			c.setLabelType(cLabel.getLabelType());
			List<CLabel> label =clabelLabelServiceImpl.getCLabelList(new Query(c));
			if(label != null && label.size()>0){
				resultInfo.setCode("0");
				resultInfo.setMsg("标签名称不能重复");
				return resultInfo;
			}
			return clabelLabelServiceImpl.addCLabel(cLabel, getOperator());
		}
	
	/**
	 * 启用停用
	 * @param stationNo
	 * @param isAvailable
	 * @return
	 */
	@RequestMapping("/uplabel")
	@ResponseBody
	public ResultInfo<CLabel> uplabel(String labelId,String enableStatus){
		CLabel c = new CLabel();
		c.setEnableStatus(Integer.parseInt(enableStatus));
		c.setLabelId(labelId);
		return clabelLabelServiceImpl.updateCLabel(c, getOperator());
	}
	/**
	 * 跳转编辑标签页面
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/tolabelEdit")
	public String tolabelEdit(String labelId,Model model){
		//获取充电站信息
		CLabel cLabel=clabelLabelServiceImpl.getCLabel(labelId);
		model.addAttribute("cLabel",cLabel);
		return "ml/label_edit";
	}
	/**
	 * 编辑标签
	 * @param label
	 * @return
	 */
	@RequestMapping("/editlabel")
	@ResponseBody
	public ResultInfo<CLabel> editlabel(@ModelAttribute("cLabel") CLabel cLabel){
		return clabelLabelServiceImpl.updateCLabel(cLabel, getOperator());
	}
	/**
	 * 删除标签
	 * @param labelId
	 * @return
	 */
	@RequestMapping("/delLabel")
	@ResponseBody
	public ResultInfo<CLabel> delLabel(String labelId){
		return clabelLabelServiceImpl.delCLabel(labelId, getOperator());
	}
	/**
	 * 验证标签状态
	 * @param labelId
	 * @return
	 */
	@RequestMapping("/verifyLabel")
	@ResponseBody
	public ResultInfo<CLabel> verifyLabel(String labelId){
		ResultInfo<CLabel> resultInfo = new ResultInfo<CLabel>();
		CLabel cLabel = clabelLabelServiceImpl.getCLabel(labelId);
		if(cLabel.getEnableStatus()==1){
			resultInfo.setCode("0");
			resultInfo.setMsg("标签正在使用不能进行删除");
		}else{
			resultInfo.setCode("1");
		}
		return resultInfo;
	}
}
