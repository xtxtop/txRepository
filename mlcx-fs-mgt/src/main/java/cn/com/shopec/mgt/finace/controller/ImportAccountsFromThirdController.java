package cn.com.shopec.mgt.finace.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.finace.model.FinaceTest;
import cn.com.shopec.core.finace.service.ImportAccountsFromThirdService;
import cn.com.shopec.core.system.model.DataDictCat;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 账单账务明细倒入控制类（从支付宝、微信倒入）
 * @author admin
 * 
 */
@Controller
@RequestMapping("/importAccounts")
public class ImportAccountsFromThirdController extends BaseController{
	
	@Resource
	ImportAccountsFromThirdService importService;
	
	/**
	 * 支付宝账单倒入
	 * 接口名称：alipay.data.dataservice.bill.downloadurl.query (查询对账单下载地址)
	 * 接口地址：https://doc.open.alipay.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.iX5mPA&apiId=1054&docType=4
	 */
	@RequestMapping("/alipay")
	@ResponseBody
	public ResultInfo<FinaceTest> importFromAlipay(String time){
		//String time = "2018-04-03";
		return importService.importFromAlipay(time);
	}
	
	/**
	 * 微信账单倒入
	 * 接口名称：https://api.mch.weixin.qq.com/pay/downloadbill 
	 * 接口地址：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_6&index=8
	 */
	@RequestMapping("/weChart")
	@ResponseBody
	public ResultInfo<FinaceTest> importFromWeChart(String time){
		//String time = "2017-04-02";	//需要倒入的时间的账单
		return importService.importFromWChart(time);
	}
	
	/*
	 * 进入发票开票列表页面
	 */
	@RequestMapping("/toImportAccountsView")
	public String mainPage(ModelMap modelMap) {
		return "finace/import_accounts_view";
	}
}
