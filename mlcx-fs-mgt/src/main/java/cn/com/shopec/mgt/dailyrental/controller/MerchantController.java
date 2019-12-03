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
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.MerchantUser;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.core.dailyrental.service.MerchantUserService;
import cn.com.shopec.core.dailyrental.service.ParkDayService;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("merchant")
public class MerchantController extends BaseController {

	@Resource
	public MerchantService  merchantService;
	@Resource
	public SysRegionService sysRegionService;
	@Resource
	public ParkDayService parkDayService;
	@Resource
	public MerchantUserService  merchantUserService;
	
	/*
	 * 显示租赁商列表页
	 */
	@RequestMapping("toMerchantList")
	public String toMerchantList(ModelMap map) {
		return "dailyrental/merchant/merchant_list";
	}

	/*
	 * 显示租赁商列表页
	 */
	@RequestMapping("pageListMerchant")
	@ResponseBody
	public PageFinder<Merchant> pageListMerchant(@ModelAttribute("merchant") Merchant merchant, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), merchant);
		return merchantService.getMerchantPagedList(q);
	}

	/*
	 * 显示租赁商增加页
	 */
	@RequestMapping("toMerchantAdd")
	public String toMerchantAdd(ModelMap map) {
		List<SysRegion> provinces = sysRegionService.getProvices();// 省
		map.put("provinces", provinces);
		return "dailyrental/merchant/merchant_add";
	}

	/*
	 * 显示租赁商修改页
	 */
	@RequestMapping("toMerchantEdit")
	public String toMerchantEdit(String merchantId, ModelMap map) {
		List<SysRegion> provinces = sysRegionService.getProvices();// 省
		map.put("provinces", provinces);
		Merchant merchant = merchantService.getMerchant(merchantId);
		List<SysRegion> cities = sysRegionService.getCitys(merchant.getAddrRegion1Id());//对应省份下的城市
		map.put("cities", cities);
		List<SysRegion> districts = sysRegionService.getCountrys(merchant.getAddrRegion2Id());//对应市下的区域
		map.put("districts", districts);
		map.put("merchant", merchant);
		return "dailyrental/merchant/merchant_edit";
	}
	/*
	 * 获取租赁商对象
	 */
	@RequestMapping("getMerchant")
	@ResponseBody
	public ResultInfo<Merchant> getMerchant(String merchantId) {
		ResultInfo<Merchant> result = new ResultInfo<Merchant>();
		Merchant merchant = null;
		if(merchantId!=null&&!"".equals(merchantId)){
			merchant = merchantService.getMerchant(merchantId);
			result.setCode(Constant.SECCUESS);
			result.setData(merchant);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("商家不存在");
		}
		return result;
	}
	/*
	 * 新增或修改租赁商
	 */
	@RequestMapping("editMerchants")
	@ResponseBody
	public ResultInfo<Merchant> editMerchants(@ModelAttribute("merchant") Merchant merchant) {
		ResultInfo<Merchant> resultInfo = new ResultInfo<Merchant>();
		if (merchant.getMerchantId() != null && merchant.getMerchantId().length() != 0) {
			if(merchant.getCencorStatus()!=null){
				merchant.setCencorTime(ECDateUtils.getCurrentDateTime());
			}
			//商家停用时，停用门店（修改数据库和solr）
			if(merchant.getIsAvailable()!=null&&merchant.getIsAvailable()==0){
				ParkDay parkDaySearch = new ParkDay();
				parkDaySearch.setMerchantId(merchant.getMerchantId());
				List<ParkDay> parkDayList = parkDayService.getParkDayList(new Query(parkDaySearch));
				for(ParkDay parkDay:parkDayList){
					ParkDay parkDayForUpdate = new ParkDay();
					parkDayForUpdate.setParkId(parkDay.getParkId());
					parkDayForUpdate.setIsVailable(0);
					parkDayService.changeParkDayState(parkDayForUpdate, getOperator());
				}
				//修改商家库存
				MerchantInventory merchantInventory = new MerchantInventory();
				merchantInventory.setMerchantId(merchant.getMerchantId());
				resultInfo = merchantService.merchantInventoryOffLine(merchantInventory);
				//停用商家用户
				MerchantUser merchantUserForUpdate = new MerchantUser();
				merchantUserForUpdate.setMerchantId(merchant.getMerchantId());
				merchantUserForUpdate.setBlacklistMemo("商家停用");
				merchantUserForUpdate.setIsBlacklist(1);
				merchantUserService.updateMerchantUserByMerchantId(merchantUserForUpdate);
			}
			resultInfo = merchantService.updateMerchantForMgt(merchant, getOperator());
		} else {
			resultInfo = merchantService.addMerchant(merchant, getOperator());
		}
		return resultInfo;
	}

	/*
	 * 删除租赁商
	 */
	@RequestMapping("delMerchant")
	@ResponseBody
	public ResultInfo<Merchant> delMerchant(String merchantId) {

		return merchantService.delMerchant(merchantId, getOperator());
	}

	/*
	 * 验证租赁商名称是否重复
	 */
	@RequestMapping("checkMerchantName")
	@ResponseBody
	public ResultInfo<String> checkMerchantName(String merchantName,String merchantId) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		Merchant merchantQ = new Merchant();
		merchantQ.setMerchantName(merchantName);
		List<Merchant> list = merchantService.getMerchantList(new Query(merchantQ));
		if (list.size() > 0) {
			if(merchantId!=null&&!"".equals(merchantId)){
				Merchant merchant = list.get(0);
				if(merchant!=null&&merchantId.equals(merchant.getMerchantId())){
					resultInfo.setCode(Constant.NO + "");
				}else{
					resultInfo.setCode(Constant.YES + "");
				}
			}else{
				resultInfo.setCode(Constant.YES + "");
			}
		} else {
			resultInfo.setCode(Constant.NO + "");
		}
		return resultInfo;
	}

}
