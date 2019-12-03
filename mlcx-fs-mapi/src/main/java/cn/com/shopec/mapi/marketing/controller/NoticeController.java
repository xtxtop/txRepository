package cn.com.shopec.mapi.marketing.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.common.MarketingContant;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.model.Notice;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.core.marketing.service.NoticeService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.marketing.vo.AdvertVo;
import cn.com.shopec.mapi.marketing.vo.NoticeVo;

@Controller
@RequestMapping("/app/notice")
public class NoticeController extends BaseController{
	@Value("${image_path}")
	private String imgPath;
	@Resource
	private NoticeService noticeService;
	
	@Resource
	private CarService carService;
		/**
		 *获取活动列表信息
		 *type 客户端不传   商家端传1
		 *
		 * */
		@RequestMapping("/noticePageList")
		@ResponseBody
		public ResultInfo<List<NoticeVo>> advertPageList(Integer type) {
			Notice notice=new Notice();
			notice.setIsAvailable(1);
			if(type ==null){
				notice.setNoticeMemberType(0);;
			}else{
				notice.setNoticeMemberType(type);
			}
			notice.setCensorStatus(1);
			notice.setIsAvailable(1);
			notice.setIsDeleted(0);
			Query q=new Query(1,10,notice);
			PageFinder<Notice> pf= noticeService.getNoticePagedList(q);
			ResultInfo<List<NoticeVo>> result=new ResultInfo<List<NoticeVo>>();
			return  noticeToVo(result,pf.getData());
		}
		/**
		 * 方法说明:将按特定条件查询的记录转换成自定vo对象
		 */
		 ResultInfo<List<NoticeVo>> noticeToVo(ResultInfo<List<NoticeVo>> result,List<Notice> noticeList){
		
			if (noticeList!=null&&noticeList.size()>0) {
				List<NoticeVo> voList = new ArrayList<NoticeVo>();
				for (Notice notice : noticeList) {
					NoticeVo noticeVo = new NoticeVo();
					noticeVo.setNioticeName(notice.getNoticeName());
					noticeVo.setNoticeNo(notice.getNoticeNo());
					noticeVo.setNioticePicUrl(imgPath + "/" +notice.getNoticePicUrl());
					noticeVo.setNioticeType(notice.getNoticeType());
					 if(notice.getExternalLinkUrl() != null && !"".equals(notice.getExternalLinkUrl())){
						 noticeVo.setLinkUrl(notice.getExternalLinkUrl());
					 }else{
						 noticeVo.setLinkUrl(notice.getLinkUrl());
					 }
					
					 noticeVo.setRanking(notice.getRanking());
					voList.add(noticeVo);
				}
				result.setData(voList);
				result.setCode(MarketingContant.success_code);
				result.setMsg("");
			}else {
				result.setCode(MarketingContant.fail_code);
				result.setMsg("暂无公告");
			}
			return result;
		}
		
}
