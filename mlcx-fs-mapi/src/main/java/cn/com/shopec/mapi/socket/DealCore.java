package cn.com.shopec.mapi.socket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.ml.model.CLoveCar;
import cn.com.shopec.core.ml.service.CLoveCarService;
import cn.com.shopec.core.mlparking.model.CParkOrder;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.service.CParkOrderService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.mapi.common.controller.BaseController;
import net.sf.json.JSONObject;

public class DealCore extends BaseController {

	private static Logger logger = Logger.getLogger(DealCore.class);

	private static BaseController bc;

	// 车辆进场，判断车辆来源，是否开闸
	public String uploadcarinfo(MemberService memberService, CLoveCarService cLoveCarService,
			CParkOrderService cParkOrderService, CParkingService cParkingService, JSONObject jsStr,
			StringBuilder sendMsg) throws ParseException {

		String carNo = jsStr.getString("carNo").trim();
		String startTime = jsStr.getString("startTime").trim();
		String parkid = jsStr.getString("parkid").trim();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 停车场站
		CParking cp = cParkingService.getCParking(parkid);
		if (cp != null) {
			CLoveCar cl = new CLoveCar();
			cl.setVehicleBrand(carNo);
			// 会员绑定的车辆表查询有无车辆信息
			List<CLoveCar> clList = cLoveCarService.getCLoveCarList(new Query(cl));
			//
			if (clList != null && clList.size() > 0) {
				CLoveCar clc = clList.get(0);
				// String memberNo = clc.getMemberNo();
				Member member = memberService.getMember(clc.getMemberNo());
				CParkOrder co = new CParkOrder();
				co.setCarNo(carNo);
				co.setOrderStatus(0);
				List<CParkOrder> coList = cParkOrderService.getCParkOrderList(new Query(co));
				if (coList != null && coList.size() > 0) {
					// 平台预约用户更新进闸时间
					CParkOrder cpo = coList.get(0);
					cpo.setEntryTime(formatter.parse(startTime));
					ResultInfo<CParkOrder> res = cParkOrderService.updateCParkOrder(cpo);
					if (res.getCode().equals(Constant.SECCUESS)) {
						sendMsg.append("{\"code\":\"0\",\"carNo\":\"" + carNo + "\",\"msg\":\"" + "可以开闸,预约用户" + "\"}");
					}
				} else {
					// 平台临时用户,新增停车订单
					CParkOrder cpo1 = new CParkOrder();
					cpo1.setCarNo(carNo);
					cpo1.setEntryTime(formatter.parse(startTime));
					cpo1.setMemberNo(clc.getMemberNo());
					cpo1.setMemberName(member.getMemberName());
					cpo1.setMobilePhone(member.getMobilePhone());
					cpo1.setOrderStatus(0);
					cpo1.setOrderSource(2);
					cpo1.setParkingNo(parkid);
					cpo1.setParkingName(cp.getParkingName());
					cpo1.setParkType(0);
					ResultInfo<CParkOrder> resultInfo = cParkOrderService.addCParkOrder(cpo1);
					if (resultInfo.getCode().equals(Constant.SECCUESS)) {
						sendMsg.append("{\"code\":\"0\",\"carNo\":\"" + carNo + "\",\"msg\":\"" + "可以开闸,平台用户" + "\"}");
					}
				}
			} else {
				// 非平台车辆
				sendMsg.append("{\"code\":\"1\",\"carNo\":\"" + carNo + "\",\"msg\":\"" + "不能开闸,非平台车辆" + "\"}");
			}
		} else {
			sendMsg.append("{\"code\":\"1\",\"carNo\":\"" + carNo + "\",\"msg\":\"" + "车场不存在" + "\"}");
		}

		return sendMsg.toString();
	}

	public String leavecarinfo(MemberService memberService, CLoveCarService cLoveCarService,
			CParkOrderService cParkOrderService, CParkingService cParkingService, JSONObject jsStr,
			StringBuilder sendMsg) {
		String carNo = jsStr.getString("carNo").trim();
		String startTime = jsStr.getString("leaveTime").trim();
		String parkid = jsStr.getString("parkid").trim();

		return sendMsg.toString();
	}

}
