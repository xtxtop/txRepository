package cn.com.shopec.mgt.company.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.order.vo.PricingPackOrderVo;

/**
 * 集团管理
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

	@Resource
	private CompanyService companyService;

	@Resource
	private DataDictItemService dataDictItemService;
	
	@Resource
	private PricingPackageService pricingPackageService;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	/**
	 * 进入集团列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toCompanyList")
	public String toDeviceList() {
		return "company/company_list";
	}

	/**
	 * 查询集团列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("pageListCompany")
	@ResponseBody
	public PageFinder<Company> pageListCompany(Company company,@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, company);
		return companyService.getCompanyPagedList(q);
	}

	/**
	 * 集团列表详情
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping("getCompanyView")
	public String getCompanyView(String companyId, Model model) {
		Company company = companyService.getCompany(companyId);
		model.addAttribute("company", company);
		return "company/company_view";
	}
	/**
	 * 集团审核页面
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping("toCencorCompany")
	public String toCencorCompany(String companyId, Model model) {
		Company company = companyService.getCompany(companyId);
		model.addAttribute("company", company);
		return "company/company_cencor";
	}

	/**
	 * 集团编辑页面
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping("toUpdateCompany")
	public String toUpdateCompany(String companyId, Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		Company company = companyService.getCompany(companyId);
		model.addAttribute("company", company);
		model.addAttribute("cities", cities);
		return "company/company_edit";
	}
	/**
	 * 集团修改
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping("updateCompany")
	@ResponseBody
	public ResultInfo<Company> updateCompany(Company company) {
		ResultInfo<Company> resultInfo = new ResultInfo<Company>();
		Company c1 = new Company();
		c1.setCompanyName(company.getCompanyName());;
		List<Company> list1 = companyService.getCompanyList(new Query(c1));
		if(list1!=null&&list1.size()>0){
			for (Company temp : list1) {
				if(temp != null && temp.getCompanyId() != null && temp.getCompanyId().equals(company.getCompanyId())){
					continue;
				}
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("公司名称已存在！");
				return resultInfo;
			}
		}
		Company c2 = new Company();
		c2.setContactTel(company.getContactTel());
		List<Company> list2 = companyService.getCompanyList(new Query(c2));
		if(list2!=null&&list2.size()>0){
			for (Company temp : list2) {
				if(temp != null && temp.getCompanyId() != null && temp.getCompanyId().equals(company.getCompanyId())){
					continue;
				}
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("电话号码已存在！");
				return resultInfo;
			}
		}
		return companyService.updateCompany(company, getOperator());
	}
	/**
	 * 集团审核
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping("cencorCompany")
	@ResponseBody
	public ResultInfo<Company> cencorCompany(Company company) {
		company.setCencorTime(new Date());
		return companyService.updateCompany(company, getOperator());
	}
	/**
	 * 集团添加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddCompany")
	public String toCompanyDevice(Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		model.addAttribute("cities", cities);
		return "company/company_add";
	}
	/**
	 * 集团添加
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping("addCompany")
	@ResponseBody
	public ResultInfo<Company> addDevice(Company company,Model model) {
		ResultInfo<Company> resultInfo = new ResultInfo<Company>();
		Company c1 = new Company();
		c1.setCompanyName(company.getCompanyName());;
		List<Company> list1 = companyService.getCompanyList(new Query(c1));
		if(list1!=null&&list1.size()>0){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("公司名称已存在！");
			return resultInfo;
		}
		Company c2 = new Company();
		c2.setContactTel(company.getContactTel());
		List<Company> list2 = companyService.getCompanyList(new Query(c2));
		if(list2!=null&&list2.size()>0){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("电话号码已存在！");
			return resultInfo;
		}else{
			return companyService.addCompany(company, getOperator());
		}
	}

	/**
	 * 根据companyId异步获取company
	 * @return
	 */
	@RequestMapping("getCompanyByCompanyId")
	@ResponseBody
	public ResultInfo<Company> getCompanyByCompanyId(String companyId) {
		ResultInfo<Company> resultInfo = new ResultInfo<Company>();
		Company company = companyService.getCompany(companyId);
		if(company!=null){
			resultInfo.setCode("1");
			resultInfo.setData(company);
		}
		return resultInfo;
	}
	
	/**
	 * 集团启用停用
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping("enableCompany")
	@ResponseBody
	public ResultInfo<Company> enableCompany(Company company) {
		company.setEnableTime(new Date());
		return companyService.updateCompany(company, getOperator());
	}
	/**
	 * 集团套餐订单添加页面
	 * @return
	 */
	@RequestMapping("toPricingPackOrderAdd")
	public String toPricingPackOrderAdd(String[] companyIds,Model model){
		List<String> stringList = Arrays.asList(companyIds); 
		StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        } 
		 
		model.addAttribute("companyIds", result.toString());
		PricingPackage pricePackage = new PricingPackage();
		pricePackage.setCencorStatus(1);
		pricePackage.setIsAvailable(1);
		pricePackage.setPackageType(1);//套餐类型，1.赠送类套餐
		List<PricingPackage> pricePackageList =  pricingPackageService.getPricingPackageList(new Query(pricePackage));
		model.addAttribute("pricePackageList", pricePackageList);
		return "company/price_pack_order_add";
	}
	/**
	 * 集团套餐订单添加
	 * 
	 * @param pricePackage
	 * @return
	 */
	@RequestMapping("addPricingPackOrder")
	@ResponseBody
	public ResultInfo<PricingPackOrder> addPricingPackOrder(@ModelAttribute("PricingPackOrderVo") PricingPackOrderVo pricingPackOrderVo) {
		ResultInfo<PricingPackOrder> res = new ResultInfo<PricingPackOrder>();
		PricingPackOrder pricingPackOrder = new PricingPackOrder();
		
		//获取需要添加的集团集合
		List<String> companys = pricingPackOrderVo.getCompanyIds();
		if(null != companys && companys.size()>0){
			for(String companyId:pricingPackOrderVo.getCompanyIds()){
				Query q=new Query();
				Member memberSearch=new Member();
				memberSearch.setMemberType(2);//集团用户
				memberSearch.setCompanyId(companyId);
				q.setQ(memberSearch);
				List<Member> memberList = memberService.getMemberList(q);
				if(null != memberList && memberList.size() > 0){
					for(Member member:memberList){
						if(pricingPackOrderVo!=null&&pricingPackOrderVo.getPackageId()!=null){
							pricingPackOrder.setPackOrderNo(null);
							pricingPackOrder.setMemberNo(member.getMemberNo());
							pricingPackOrder.setMemberName(member.getMemberName());
							pricingPackOrder.setMobilePhone(member.getMobilePhone());
							pricingPackOrder.setPackageId(pricingPackOrderVo.getPackageId());
							pricingPackOrder.setPackageName(pricingPackOrderVo.getPackageName());
							pricingPackOrder.setPackAmount(pricingPackOrderVo.getPackAmount());
							pricingPackOrder.setPackMinute(pricingPackOrderVo.getPackMinute()); 
							
							PricingPackage pricePackage = pricingPackageService.getPricingPackage(pricingPackOrderVo.getPackageId());
							pricingPackOrder.setUserdMinute(0);
							pricingPackOrder.setPaymentMethod(3); //赠送的套餐，支付方式为其他。
							pricingPackOrder.setPayStatus(1);;
							pricingPackOrder.setIsAvailable(1);
							pricingPackOrder.setPayableAmount(pricePackage.getPrice());
							pricingPackOrder.setVailableTime1(new Date());
							pricingPackOrder.setInvoiceStatus(0);
							//有效期的起止时间（当前时间+套餐的有效天数）
							if(pricePackage.getAvailableDays() != null){
								pricingPackOrder.setVailableTime2(ECDateUtils.getDateAfter(pricingPackOrder.getVailableTime1(),pricePackage.getAvailableDays()));
							}
							res=pricingPackOrderService.addPricingPackOrder(pricingPackOrder, getOperator());
						}else{
							res.setCode(Constant.FAIL);
							res.setMsg("套餐数据为空！");
							return res;
						}
					}
				}else{
					res.setCode(Constant.FAIL);
					res.setMsg("当前集团下没有会员！");
					return res;
				}
			}
		}else{
			res.setCode(Constant.FAIL);
			res.setMsg("请求参数异常-集团");
			return res;
		}
		
		return res;
	}
}
