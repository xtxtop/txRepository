package cn.com.shopec.mapi.station.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.service.ChargingPileService;
import cn.com.shopec.core.ml.vo.LockVo;
import cn.com.shopec.core.ml.vo.PileVo;
import cn.com.shopec.core.ml.vo.TerminalDetailsVo;
import cn.com.shopec.core.mlorder.model.LockOrder;
import cn.com.shopec.core.mlorder.service.LockOrderService;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * 充电桩接口
 */
@Controller
@RequestMapping("/app/pile")
public class ChargingPileController extends BaseController {
	@Resource
	private ChargingPileService chargingPileService;
	@Resource
	private LockOrderService lockOrderService;

	/**
	 * 获取充电桩列表
	 */
	@RequestMapping("/chargingPileList")
	@ResponseBody
	public ResultInfo<List<PileVo>> chargingPileList(String stationNo, String pageNo, String pageSize,String memberNo) {
		ResultInfo<List<PileVo>> resultInfo = new ResultInfo<>();
		if (stationNo == null || stationNo.length() < 1) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		} else try {
			PileVo search = new PileVo();
			search.setStationNo(stationNo);
			Query q = new Query(Integer.parseInt(pageNo), Integer.parseInt(pageSize), search);
			List<PileVo> datas = chargingPileService.pagePileQuery(q);
			if(datas!=null&&datas.size()>0){
				for(PileVo p:datas){
					for(LockVo l:p.getLocks()){
						//获取会员订单信息
						LockOrder orderMember = lockOrderService.getOrderMember(memberNo,l.getParkingLockNo());
						if(orderMember!=null){
							l.setType(1);
							l.setOrderNo(orderMember.getOrderNo());
						}else{
							l.setType(2);
						}
					}
				}
				
			}
		
			if (datas.isEmpty()) {
				resultInfo.setCode(Constant.OTHER);
				resultInfo.setMsg("无数据");
			} else {
				resultInfo.setData(datas);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("查询成功");
			}
		} catch (NumberFormatException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		}
		return resultInfo;
	}

	/**
	 * 获取终端详情
	 */
	@RequestMapping("/terminalDetails")
	@ResponseBody
	public ResultInfo<TerminalDetailsVo> terminalDetails(
			@RequestParam(value = "parkingLockNo", required = false) String parkingLockNo, String chargingPileNo) {
		ResultInfo<TerminalDetailsVo> resultInfo = new ResultInfo<>();
		if (chargingPileNo == null || chargingPileNo.length() < 1) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		} else {
			TerminalDetailsVo datas = chargingPileService.getTerminalDetails(chargingPileNo, parkingLockNo);
			if (datas == null) {
				resultInfo.setCode(Constant.OTHER);
				resultInfo.setMsg("无数据");
			} else {
				resultInfo.setData(datas);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("查询成功");
			}
		}
		return resultInfo;
	}
}
