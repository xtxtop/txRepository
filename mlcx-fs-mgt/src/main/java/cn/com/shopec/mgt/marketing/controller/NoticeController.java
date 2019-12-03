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
import cn.com.shopec.core.marketing.model.Notice;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.core.marketing.service.NoticeService;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController {
	

	@Value("${res_path}")
	private String resPath;

	@Value("${file_path}")
	private String filePath;
	@Resource
	private  NoticeService noticeService;
	
	/**
	 * 公告列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toNoticeList")
	public String toAdvertList() {
		return "marketing/notice_list";
	}

	/**
	 * 公告列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListNotice")
	@ResponseBody
	public PageFinder<Notice> pageListAdvert(@ModelAttribute("notice")Notice notice, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), notice);
		return noticeService.getNoticePagedLists(q);
	}

	/**
	 * 公告详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toNoticeView")
	public String toNoticeView(String noticeNo, ModelMap modelMap) {
		Notice notice = noticeService.getNotice(noticeNo);
		modelMap.put("notice", notice);
		return "marketing/notice_view";
	}

	/**
	 * 活动增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddNotice")
	public String toAddNotice() {
		return "marketing/notice_add";
	}

	/**
	 * 公告添加
	 * 
	 * @return
	 */
	@RequestMapping("/addNotice")
	@ResponseBody
	public ResultInfo<Notice> addAdvert(@ModelAttribute("notice") Notice notice) {
		notice.setAvailableUpdateTime(new Date());
		notice.setCensorStatus(0);// 未审核
		notice.setIsAvailable(1);
		notice.setIsDeleted(0);
		notice.setLinkUrl(filePath);
		ResultInfo<Notice> result = noticeService.addNotice(notice, getOperator());
		generateHtml(notice.getNoticeNo());
		return result;
	}

	/**
	 * 公告编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateNotice")
	public String toUpdateNotice(String noticeNo, ModelMap model) {
		Notice notice = noticeService.getNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "marketing/notice_edit";
	}

	/**
	 * 活动编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateNotice")
	@ResponseBody
	public ResultInfo<Notice> updateNotice(@ModelAttribute("notice") Notice notice) {
		ResultInfo<Notice> result = noticeService.updateNotice(notice, getOperator());
		generateHtml(notice.getNoticeNo());
		return result;
	}

	/**
	 * 公告审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toNoticeCensorView")
	public String toNoticeCensorView(String noticeNo, ModelMap model) {
		Notice notice = noticeService.getNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "marketing/notice_censor";
	}

	/**
	 * 公告审核提交
	 * 
	 * @return
	 */
	@RequestMapping("/censorNotice")
	@ResponseBody
	public ResultInfo<Notice> censorNotice(@ModelAttribute("notice") Notice notice) {
		notice.setCensorTime(new Date());
		notice.setCensorId(getOperator().getOperatorId());
		return noticeService.updateNotice(notice, getOperator());
	}
//
	/**
	 * 公告更新启用状态
	 */
	@RequestMapping("/delNotice")
	@ResponseBody
	public ResultInfo<Notice> delNotice(String noticeNo, Integer isAvailable) {
		Notice notice = new Notice();
		notice.setNoticeNo(noticeNo);
		notice.setIsAvailable(isAvailable);
		return noticeService.updateNotice(notice, getOperator());
	}
//
	private void generateHtml(String noticeNo) {
		try {
			if(noticeNo == null){
				return;
			}
			Notice notice = noticeService.getNotice(noticeNo);
			File fileDir = new File(resPath + "/advert/" + noticeNo.substring(0, 2));
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			PrintStream printStream = new PrintStream(new FileOutputStream(resPath + "/advert/"
					+ noticeNo.substring(0, 2) + "/" + noticeNo.substring(2, noticeNo.length()) + ".html"));
			StringBuffer buf = new StringBuffer();
			buf.append("<html>");
			buf.append("<head>");
			buf.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/>");
			buf.append("</head>");
			buf.append("<body>");
			buf.append("<div style=\"text-align: center;font-size: 24px;\">");
			buf.append(notice.getNoticeName());
			buf.append("</div>");
			buf.append("<div style=\"text-align: center;font-size: 16px;\">");
			buf.append(ECDateUtils.formatDate(notice.getCreateTime()));
			buf.append("</div>");
			buf.append("<div style=\"width:100%;clear:both;\">");
			buf.append(notice.getText1());
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
