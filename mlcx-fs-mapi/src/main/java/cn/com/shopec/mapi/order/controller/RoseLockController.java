package cn.com.shopec.mapi.order.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.service.LockOrderService;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * app升锁
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/app/roseLock")
public class RoseLockController extends BaseController {

	@Resource
	private LockOrderService lockOrderService;

	/**
	 * 升锁
	 * 
	 * @param memberNo
	 *            会员编号
	 * @param lockid
	 *            地锁编号
	 */
	@RequestMapping("/roseLock")
	@ResponseBody
	public ResultInfo<OrderSimpleVo> roseLock(String orderNo) {
		ResultInfo<OrderSimpleVo> resultInfo = lockOrderService.roseLock(orderNo);
		return resultInfo;
	}
}
