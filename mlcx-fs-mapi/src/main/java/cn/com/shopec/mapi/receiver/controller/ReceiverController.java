package cn.com.shopec.mapi.receiver.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.ml.model.MonitorDataCocurrent;
import cn.com.shopec.core.ml.model.MonitorDataInterflow;
import cn.com.shopec.core.ml.service.MonitorDataCocurrentService;
import cn.com.shopec.core.ml.service.MonitorDataInterflowService;
import cn.com.shopec.core.mlorder.service.ChargeOrderService;
import cn.com.shopec.core.mlorder.service.LockOrderService;
import cn.com.shopec.mapi.common.controller.BaseController;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app/receiver")
public class ReceiverController extends BaseController {
	private static final Log log = LogFactory.getLog(ReceiverController.class);
	@Resource
	private ChargeOrderService chargeOrderService;
	@Resource
	private LockOrderService lockOrderService;
	@Resource
	private MonitorDataInterflowService monitorDataInterflowServiceImpl;
	@Resource
	private MonitorDataCocurrentService monitorDataCocurrentServiceImpl;

	/**
	 * 接收通知
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/receiver")
	@ResponseBody
	public ResultInfo<Object> receiver(@RequestBody String json) {
		ResultInfo<Object> resultInfo = new ResultInfo<>();
		try {
			String receiverOrder = null;
			String receiverLock = null;
			JSONObject jsonobject = JSONObject.fromObject(json);
			next: while (true) {
				if (jsonobject.containsKey("tp")) {
					if (null == receiverOrder)
						try {
							String tp = jsonobject.get("tp").toString();
							receiverOrder = jsonobject.get("data").toString();

							switch (tp) {
							case "cdjl": {
								log.info("------拔枪后上报的json字符串数据：--------" + receiverOrder);
								System.out.println("--------拔枪后上报的json字符串数据：----------" + receiverOrder);
								chargeOrderService.changeReceiverOrder(receiverOrder);
								resultInfo.setCode(Constant.SECCUESS);
								resultInfo.setMsg("成功接收数据");
								break next;
							}
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							receiverLock = "";
							break next;
						}
				} else if (jsonobject.containsKey("LockId")) {
					// 地锁回调
					if (null == receiverLock) {
						try {
							receiverLock = jsonobject.toString();
							log.info("--------升锁后上报的json字符串数据：---------" + receiverLock);
							System.out.println("----------拔枪后上报的json字符串数据：----------" + receiverLock);
							lockOrderService.changeReceiverOrder(receiverLock);
							resultInfo.setCode(Constant.SECCUESS);
							resultInfo.setMsg("成功接收数据");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							receiverLock = "";
							break next;
						}
					} else {
						break next;
					}

				} else {
					log.info("----------上报的json格式数据有误------------");
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("上报的json格式数据有误，更新订单数据出错");
				}
			}
		} catch (NullPointerException e) {
			log.info("未知请求数据");
			log.info(json);
		} catch (Exception e) {
			log.info(e);
		}
		return resultInfo;
	}

	// 接收检测数据
	@RequestMapping("/monitoringData")
	@ResponseBody
	public ResultInfo<Object> monitoringData(@RequestBody String json) {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		System.out.println(json);
		try {
			JSONObject jsonobject = JSONObject.fromObject(json);
			if ("jl".equals(jsonobject.get("tp").toString())) {
				MonitorDataInterflow monitorDataInterflow = (MonitorDataInterflow) JSONObject
						.toBean(JSONObject.fromObject(jsonobject.get("data").toString()), MonitorDataInterflow.class);
				monitorDataInterflowServiceImpl.addMonitorDataInterflow(monitorDataInterflow, getOperator());
			} else {
				MonitorDataCocurrent monitorDataCocurrent = (MonitorDataCocurrent) JSONObject
						.toBean(JSONObject.fromObject(jsonobject.get("data").toString()), MonitorDataCocurrent.class);
				monitorDataCocurrentServiceImpl.addMonitorDataCocurrent(monitorDataCocurrent, getOperator());
			}
			resultInfo.setCode("1");
			resultInfo.setMsg("同步数据成功");
		} catch (NullPointerException e) {
			log.info("未知请求数据");
			log.info(json);
			resultInfo.setData(json);
			resultInfo.setCode("0");
			resultInfo.setMsg("同步数据异常");
		} catch (Exception e) {
			log.info(e);
		}
		return resultInfo;
	}
}
