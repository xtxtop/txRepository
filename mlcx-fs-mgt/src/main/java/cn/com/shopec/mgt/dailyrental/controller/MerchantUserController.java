package cn.com.shopec.mgt.dailyrental.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.MerchantUser;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.core.dailyrental.service.MerchantUserService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("merchantUser")
public class MerchantUserController extends BaseController {

	@Resource
	public MerchantService  merchantService;
	@Resource
	public MerchantUserService merchantUserService;
	@Resource
	private DataDictItemService dataDictItemService;
	/*
	 * 显示商家用户列表页
	 */
	@RequestMapping("toMerchantUserList")
	public String toMerchantUserList(ModelMap map) {
		List<Merchant> merchants = merchantService.getMerchantList(new Query());// 省
		map.put("merchants", merchants);
		return "dailyrental/merchant/merchant_user_list";
	}

	/*
	 * 显示商家用户列表页
	 */
	@RequestMapping("pageListMerchantUser")
	@ResponseBody
	public PageFinder<MerchantUser> pageListMerchantUser(@ModelAttribute("merchantUser") MerchantUser merchantUser, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), merchantUser);
		return merchantUserService.getMerchantUserPagedList(q);
	}

	/*
	 * 显示商家用户增加页
	 */
	@RequestMapping("toMerchantUserAdd")
	public String toMerchantUserAdd(ModelMap map) {
		List<Merchant> merchants = merchantService.getMerchantList(new Query());// 省
		map.put("merchants", merchants);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		map.put("cities", cities);
		return "dailyrental/merchant/merchant_user_add";
	}

	/*
	 * 显示商家用户修改页
	 */
	@RequestMapping("toMerchantUserEdit")
	public String toMerchantUserEdit(String userNo, ModelMap map) {
		MerchantUser merchantUser = merchantUserService.getMerchantUser(userNo);
		map.put("merchantUser", merchantUser);
		List<Merchant> merchants = merchantService.getMerchantList(new Query());// 省
		map.put("merchants", merchants);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		map.put("cities", cities);
		return "dailyrental/merchant/merchant_user_edit";
	}
	/*
	 * 获取商家用户对象
	 */
	@RequestMapping("getMerchantUser")
	@ResponseBody
	public ResultInfo<MerchantUser> getMerchantUser(String userNo) {
		ResultInfo<MerchantUser> result = new ResultInfo<MerchantUser>();
		MerchantUser merchantUser = null;
		if(userNo!=null&&!"".equals(userNo)){
			merchantUser = merchantUserService.getMerchantUser(userNo);
			result.setCode(Constant.SECCUESS);
			result.setData(merchantUser);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("商家用户不存在");
		}
		return result;
	}
	/*
	 * 新增或修改商家用户
	 */
	@RequestMapping("editMerchantUser")
	@ResponseBody
	public ResultInfo<MerchantUser> editMerchants(@ModelAttribute("merchantUser") MerchantUser merchantUser) {
		ResultInfo<MerchantUser> resultInfo = new ResultInfo<MerchantUser>();
		if(merchantUser.getCityId()!=null){
			// 城市
			DataDictItem citie = dataDictItemService.getDataDictItem(merchantUser.getCityId());
			if (citie != null) {
				merchantUser.setCityName(citie.getItemValue());
			}
		}
		if (merchantUser.getUserNo() != null && merchantUser.getUserNo().length() != 0) {
			if(merchantUser.getCensorStatus()!=null){
				merchantUser.setCencorTime(ECDateUtils.getCurrentDateTime());
			}
			resultInfo = merchantUserService.updateMerchantUser(merchantUser, getOperator());
		} else {
			resultInfo = merchantUserService.addMerchantUser(merchantUser, getOperator());
		}
		return resultInfo;
	}

	/*
	 * 验证商家电话是否重复
	 */
	@RequestMapping("checkMerchantUserPhone")
	@ResponseBody
	public ResultInfo<String> checkMerchantUserPhone(String mobilePhone,String userNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		MerchantUser merchantUser = merchantUserService.getByMobilePhone(null, mobilePhone, null);
		if (merchantUser!=null) {
			if(userNo!=null&&userNo.equals(merchantUser.getUserNo())){
				resultInfo.setCode(Constant.NO + "");
			}else{
				resultInfo.setCode(Constant.YES + "");
			}
		} else {
			resultInfo.setCode(Constant.NO + "");
		}
		return resultInfo;
	}

}
