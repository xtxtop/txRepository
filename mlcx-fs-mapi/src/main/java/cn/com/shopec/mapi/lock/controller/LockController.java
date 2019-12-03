package cn.com.shopec.mapi.lock.controller;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.service.LockOrderService;
import cn.com.shopec.mapi.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller @RequestMapping("/app/lock") public class LockController extends BaseController {
	@Resource private LockOrderService lockOrderService;

	/**
	 * 升锁
	 */
	@RequestMapping("/roseLock") @ResponseBody public ResultInfo<OrderSimpleVo> roseLock(String orderNo) {
		ResultInfo<OrderSimpleVo> resultInfo = lockOrderService.roseLock(orderNo);
		return resultInfo;
	}
}
