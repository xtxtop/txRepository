package cn.com.shopec.mapi.member.controller;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.EmptyUtils;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.vo.BannerVo;
import cn.com.shopec.mapi.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图接口
 */
@Controller @RequestMapping("/app/advert") public class AdvertMengLongController extends BaseController {


	@Resource private AdvertMengLongService advertMengLongService;

	/**
	 * 获取banner列表
	 */
	@RequestMapping("/banner") @ResponseBody public ResultInfo<List<BannerVo>> banner(String type, String pos) {
		return advertMengLongService.getBannerByPos(type, EmptyUtils.isEmpty(pos)?"1":pos);
	}
}
