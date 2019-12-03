package cn.com.shopec.mgt.marketing.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("advert")
public class AdvertController extends BaseController {
	@Resource
	private AdvertService advertService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private CompanyService companyService;

	@Value("${res_path}")
	private String resPath;

	@Value("${file_path}")
	private String filePath;
	
	
	/**
	 * 活动列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdvertList")
	public String toAdvertList() {
		return "marketing/advert_list";
	}

	/**
	 * 活动列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListAdvert")
	@ResponseBody
	public PageFinder<Advert> pageListAdvert(@ModelAttribute("advert") Advert advert, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), advert);
		return advertService.getAdvertPagedList(q);
	}

	/**
	 * 活动详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdvertView")
	public String toAdvertView(String advertNo, ModelMap modelMap) {
		Advert advert = advertService.getAdvert(advertNo);
		 if(advert.getExternalLinkUrl() != null && !"".equals(advert.getExternalLinkUrl())  && advert.getJumpType()==2){
			 advert.setLinkUrl(advert.getExternalLinkUrl());
		 }else{
			 advert.setLinkUrl(advert.getLinkUrl());
		 }
		modelMap.put("advert", advert);
		return "marketing/advert_view";
	}

	/**
	 * 活动增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddAdvert")
	public String toAddAdvert() {
		return "marketing/advert_add";
	}

	/**
	 * 活动添加
	 * 
	 * @return
	 */
	@RequestMapping("/addAdvert")
	@ResponseBody
	public ResultInfo<Advert> addAdvert(@ModelAttribute("advert") Advert advert) {
		advert.setAvailableUpdateTime(new Date());
		advert.setCensorStatus(0);// 未审核
		advert.setIsAvailable(1);
		advert.setLinkUrl(filePath);
		ResultInfo<Advert> result = advertService.addAdvert(advert, getOperator());
		generateHtml(advert.getAdvertNo());
		return result;
	}

	/**
	 * 活动编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateAdvert")
	public String toUpdateAdvert(String advertNo, ModelMap model) {
		Advert advert = advertService.getAdvert(advertNo);
		model.addAttribute("advert", advert);
		return "marketing/advert_edit";
	}

	/**
	 * 活动编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateAdvert")
	@ResponseBody
	public ResultInfo<Advert> updateAdvert(@ModelAttribute("advert") Advert advert) {
		ResultInfo<Advert> result = advertService.updateAdvert(advert, getOperator());
		generateHtml(advert.getAdvertNo());
		return result;
	}

	/**
	 * 活动审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdvertCensorView")
	public String toAdvertCensorView(String advertNo, ModelMap model) {
		Advert advert = advertService.getAdvert(advertNo);
		model.addAttribute("advert", advert);
		return "marketing/advert_censor";
	}

	/**
	 * 活动审核提交
	 * 
	 * @return
	 */
	@RequestMapping("/censorAdvert")
	@ResponseBody
	public ResultInfo<Advert> censorAdvert(@ModelAttribute("advert") Advert advert) {
		advert.setCensorTime(new Date());
		advert.setCensorId(getOperator().getOperatorId());
		return advertService.updateAdvert(advert, getOperator());
	}

	/**
	 * 活动更新启用状态
	 */
	@RequestMapping("/delAdvert")
	@ResponseBody
	public ResultInfo<Advert> delAdvert(String advertNo, Integer isAvailable) {
		Advert advert = new Advert();
		advert.setAdvertNo(advertNo);
		advert.setIsAvailable(isAvailable);
		return advertService.updateAdvert(advert, getOperator());
	}

	private void generateHtml(String advertNo) {
		try {
			if(advertNo == null){
				return;
			}
			Advert advert = advertService.getAdvert(advertNo);
			File fileDir = new File(resPath + "/advert/" + advertNo.substring(0, 2));
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			PrintStream printStream = new PrintStream(new FileOutputStream(resPath + "/advert/"
					+ advertNo.substring(0, 2) + "/" + advertNo.substring(2, advertNo.length()) + ".html"));
			StringBuffer buf = new StringBuffer();
			buf.append("<html>");
			buf.append("<head>");
			buf.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/>");
			buf.append("</head>");
			buf.append("<body>");
			buf.append("<div style=\"text-align: center;font-size: 24px;\">");
			buf.append(advert.getAdvertName());
			buf.append("</div>");
			buf.append("<div style=\"text-align: center;font-size: 16px;\">");
			buf.append(ECDateUtils.formatDate(advert.getCreateTime()));
			buf.append("</div>");
			buf.append("<div style=\"width:100%;clear:both;\">");
			buf.append(advert.getText1());
			buf.append("</div>");
			buf.append("</body>");
			buf.append("</html>");
			printStream.write(buf.toString().getBytes("utf-8"));
			printStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
