package cn.com.shopec.mgt.ml.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 广告控制层
 * 
 * @author Administrator
 */

@Controller
@RequestMapping("advertMengLong")
public class AdvertMengLongController extends BaseController {
	@Resource
	private AdvertMengLongService advertService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	@Value("${res_path}")
	private String resPath;

	@Value("${file_path}")
	private String filePath;

	/**
	 * 活动列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/getAdvertMengLong")
	public String toAdvertList() {
		return "ml/advert/advert_list";
	}

	/**
	 * 活动列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListAdvert")
	@ResponseBody
	public PageFinder<AdvertMengLong> pageListAdvert(@ModelAttribute("advert") AdvertMengLong advert, Query query) {
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
		AdvertMengLong advert = advertService.getAdvert(advertNo);
		modelMap.put("advert", advert);
		return "ml/advert/advert_view";
	}

	/**
	 * 活动增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddAdvert")
	public String toAddAdvert(Model model) {
		DataDictItem dic1 = new DataDictItem();
		dic1.setDataDictCatCode("ADVERT_TYPE");
		List<DataDictItem> dataTypeList = dataDictItemService.getDataDictItemList(new Query(dic1));
		model.addAttribute("dataTypeList", dataTypeList);
		DataDictItem dic2 = new DataDictItem();
		dic2.setDataDictCatCode("ADVERT_POSITION");
		List<DataDictItem> dataPositionList = dataDictItemService.getDataDictItemList(new Query(dic2));
		model.addAttribute("dataPositionList", dataPositionList);
		return "ml/advert/advert_add";
	}

	/**
	 * 活动添加
	 * 
	 * @return
	 */
	@RequestMapping("/addAdvert")
	@ResponseBody
	public ResultInfo<AdvertMengLong> addAdvert(@ModelAttribute("advertMengLong") AdvertMengLong advertMengLong) {
		advertMengLong.setAvailableUpdateTime(new Date());
		advertMengLong.setCensorStatus(0);// 未审核
		advertMengLong.setIsAvailable(0);
		advertMengLong.setFilePath(filePath);
		ResultInfo<AdvertMengLong> result = advertService.addAdvert(advertMengLong, getOperator());
		generateHtml(advertMengLong.getAdvertNo());
		return result;
	}

	/**
	 * 活动编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateAdvert")
	public String toUpdateAdvert(String advertNo, ModelMap model) {
		AdvertMengLong advert = advertService.getAdvert(advertNo);
		model.addAttribute("advert", advert);

		return "ml/advert/advert_edit";
	}

	/**
	 * 活动编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateAdvert")
	@ResponseBody
	public ResultInfo<AdvertMengLong> updateAdvert(@ModelAttribute("advert") AdvertMengLong advert) {
		advert.setCensorStatus(0);
		if (advert.getLinkType() == 0) {
			advert.setText("");
			advert.setCarModelId("");
		} else if (advert.getLinkType() == 1) {
			advert.setCarModelId("");
		} else if (advert.getLinkType() == 2) {
			advert.setText("");
			advert.setLinkUrl("");
		}
		if(advert.getAdvertType()==null){
			
		}else{
			if(advert.getAdvertType()==5){
				advert.setAdvertPicUrl("");
				advert.setLinkUrl("");
				advert.setText("");
			}
		}
		advert.setFilePath(filePath);
		ResultInfo<AdvertMengLong> result = advertService.updateAdverts(advert, getOperator());
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
		AdvertMengLong advert = advertService.getAdvert(advertNo);
		model.addAttribute("advert", advert);
		return "ml/advert/advert_censor";
	}

	/**
	 * 活动审核提交
	 * 
	 * @return
	 */
	@RequestMapping("/censorAdvert")
	@ResponseBody
	public ResultInfo<AdvertMengLong> censorAdvert(@ModelAttribute("advert") AdvertMengLong advert) {
		advert.setCensorTime(new Date());
		advert.setCensorId(getOperator().getOperatorId());
		return advertService.updateAdvert(advert, getOperator());
	}

	/**
	 * 活动更新启用状态
	 */
	@RequestMapping("/delAdvert")
	@ResponseBody
	public ResultInfo<AdvertMengLong> delAdvert(String advertNo, Integer isAvailable) {
		AdvertMengLong advert = new AdvertMengLong();
		advert.setAdvertNo(advertNo);
		advert.setIsAvailable(isAvailable);
		return advertService.updateAdvert(advert, getOperator());
	}

	private void generateHtml(String advertNo) {
		try {
			if (advertNo == null) {
				return;
			}
			AdvertMengLong advert = advertService.getAdvert(advertNo);
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
			buf.append(advert.getText());
			buf.append("</div>");
			buf.append("</body>");
			buf.append("</html>");
			printStream.write(buf.toString().getBytes("utf-8"));
			System.out.println(buf.toString());
			printStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
