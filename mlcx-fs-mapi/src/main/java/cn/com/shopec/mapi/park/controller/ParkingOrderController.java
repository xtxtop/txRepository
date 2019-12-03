package cn.com.shopec.mapi.park.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.ml.model.CLoveCar;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.service.CLoveCarService;
import cn.com.shopec.core.mlparking.model.CParkBilling;
import cn.com.shopec.core.mlparking.model.CParkLock;
import cn.com.shopec.core.mlparking.model.CParkOrder;
import cn.com.shopec.core.mlparking.service.CParkBillingService;
import cn.com.shopec.core.mlparking.service.CParkLockService;
import cn.com.shopec.core.mlparking.service.CParkOrderService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.core.mlparking.vo.LockOrder;
import cn.com.shopec.core.mlparking.vo.OrderInfo;
import cn.com.shopec.core.mlparking.vo.ParkingLockVo;
import cn.com.shopec.core.mlparking.vo.ParkingVo;
import cn.com.shopec.mapi.park.controller.vo.ImmediatelyStop;
import cn.com.shopec.mapi.park.controller.vo.ImmediatelyStopNoSpace;
import cn.com.shopec.mapi.park.controller.vo.ParkLockOrder;

/**
 * @author daiyuanbao
 * @category 地锁停车
 *
 */
@Controller
@RequestMapping("/app/parkORorder")
public class ParkingOrderController {

	@Resource
	private CParkingService cParkingService;
	@Resource
	private CParkOrderService cParkOrderService;
	@Resource
	private CParkLockService cParkLockService;
	@Resource
	private MemberService memberService;
	@Resource
	private AdvertMengLongService advertMengLongService;
	@Resource
	private CParkBillingService parkBillingService;
	@Resource
	private CLoveCarService loveCarService;
	@Value("${image_path}")
	private String imagePath;
	/**
	 * 预约停车--地锁
	 * 
	 * @param parkNO
	 * @param memberNo
	 * @param lon
	 * @param lat
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/parkingReservation")
	@ResponseBody
	public ResultInfo<Object> appointmentStopPark(String parkNo,
			String memberNo, Integer pageNo, Integer pageSize) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		try {
			// 验证分页
			if (pageNo == null || pageSize == null) {
				info.setCode(Constant.FAIL);
				info.setMsg("无效的参数");
				return info;
			}
			// 验证场站编号
			if (StringUtils.isEmpty(parkNo)) {
				info.setCode(Constant.FAIL);
				info.setMsg("无效的参数");
				return info;
			}
			// 验证会员信息
			ResultInfo<Object> resultInfoMemberNo = cParkLockService
					.resultInfoMemberNo(memberNo, 2);
			if (resultInfoMemberNo != null
					&& Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
				// 获取地锁列表
				ResultInfo<Object> parkingReservation = cParkLockService
						.parkingReservation(pageNo, pageSize, parkNo);
				if (parkingReservation != null
						&& Constant.SECCUESS.equals(parkingReservation
								.getCode())) {
					return parkingReservation;
				} else {
					info.setMsg(parkingReservation.getMsg());
					info.setCode(parkingReservation.getCode());
					return info;
				}
			} else {
				info.setMsg(resultInfoMemberNo.getMsg());
				info.setCode(resultInfoMemberNo.getCode());
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	/**
	 * 验证订单
	 * 
	 * @param memberNo
	 *            停车场编号
	 * @return
	 */
	@RequestMapping("/verifyOrder")
	@ResponseBody
	public ResultInfo<Object> verifyOrder(String memberNo) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		try {
			// 验证会员信息
			ResultInfo<Object> resultInfoMemberNo = cParkLockService
					.resultInfoMemberNo(memberNo, 1);
			if (resultInfoMemberNo != null
					&& Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
				// 验证是否有添加车辆
				List<CLoveCar> laveCar = loveCarService.getLaveCar(memberNo);
				if (laveCar != null&&laveCar.size()>0) {
					info.setMsg("当前用户暂无订单");
					info.setCode(Constant.SECCUESS);
					return info;
				}else{
					info.setMsg("请添加您的爱车!");
					info.setCode("11");
					return info;
				}

			} else {
				info.setData(resultInfoMemberNo.getData());
				info.setMsg(resultInfoMemberNo.getMsg());
				info.setCode(resultInfoMemberNo.getCode());
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	/**
	 * 立即停车--地锁
	 * 
	 * @param memberNo
	 * @param parkNo
	 * @return
	 */
	@RequestMapping("/immediatelyStopNoSpace")
	@ResponseBody
	public ResultInfo<ImmediatelyStopNoSpace> immediatelyStopNoSpace(
			String memberNo, String parkNo) {
		ResultInfo<ImmediatelyStopNoSpace> info = new ResultInfo<ImmediatelyStopNoSpace>();
		try {
			// 验证会员信息
			ResultInfo<Object> resultInfoMemberNo = cParkLockService
					.resultInfoMemberNo(memberNo, 2);
			if (resultInfoMemberNo != null
					&& Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
				if (StringUtils.isEmpty(parkNo)) {// 验证停车场
					info.setMsg("参数错误");
					info.setCode(Constant.FAIL);// 返回0失败
					return info;
				}
				// 获取场站信息
				ParkingVo cParking = cParkingService.getParkListNo(parkNo);
				if (cParking != null) {
					// 计费规则
					CParkBilling cParkBilling = parkBillingService
							.getCParkBilling(cParking.getBillingSchemeNo());
					ImmediatelyStopNoSpace is = new ImmediatelyStopNoSpace();
					is.setParkNo(parkNo);
					is.setParkName(cParking.getParkingName());
					is.setFreeTime(cParkBilling.getFreeTime());
					is.setBillingNo(cParkBilling.getParkBillingNo());
					is.setTimeNum("收费时间段:" + cParking.getBusinessHours());
					int time = 60 % cParkBilling.getUnitTime() == 0 ? 60 / cParkBilling
							.getUnitTime()
							: 60 / cParkBilling.getUnitTime() + 1;
					is.setOvertimePrice("收费标准:"
							+ ECCalculateUtils.mul(time,
									cParkBilling.getOvertimePrice()) + "/小时");
					is.setCappingPrice("备注:连续24小时最高收费"
							+ cParkBilling.getCappingPrice() + "元");
					is.setMemberNo(memberNo);
					info.setData(is);
					info.setCode(Constant.SECCUESS);
					info.setMsg("查询成功");
					return info;
				} else {
					info.setCode(Constant.OTHER);
					info.setMsg("暂无停车场信息");
					return info;
				}
			} else {
				info.setMsg(resultInfoMemberNo.getMsg());
				info.setCode(resultInfoMemberNo.getCode());
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	/**
	 * 降锁并生成订单
	 * 
	 * @param memberNo
	 *            会员编号
	 * @param lockNo
	 *            地锁编号
	 * @param source
	 *            来源
	 * @param spaceNo
	 *            车位号
	 * @param parkNo
	 *            停车场编号
	 * 
	 * @return
	 */
	@RequestMapping("/dropLock")
	@ResponseBody
	public ResultInfo<Object> dropLock(String memberNo, Integer source,
			String lon, String lat, String spaceNo, String parkNo) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		try {
			// 判断用户是否存在预约后进行降锁
			CParkOrder orderOver = cParkOrderService.getOrderOver(memberNo);
			boolean flag = false;// 是否验证地锁被预约
			int type = 1;// 判断是是否验证订单信息
			String orderNo = null;
			if (orderOver != null) {
				// 获取地锁信息
				ParkingLockVo cParkLock = cParkLockService.getLock(parkNo,
						spaceNo);
				ParkingLockVo parkLock = cParkLockService.getParkLock(orderOver
						.getParkLockNo());
				if (cParkLock != null) {
					if (parkLock.getSpaceNo().equals(spaceNo)) {
						if (orderOver.getAppointmentTime() != null
								&& orderOver.getEntryTime() == null) {
							type = 2;
							flag = true;
							orderNo = orderOver.getParkOrderNo();
						}
					}
				} else {
					info.setMsg("暂无当前车位信息");
					info.setCode(Constant.OTHER);// 返回 3 空数据
					return info;
				}
			}
			// 验证会员信息
			ResultInfo<Object> resultInfoMemberNo = cParkLockService
					.resultInfoMemberNo(memberNo, type);
			if (resultInfoMemberNo != null
					&& Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
				if (StringUtils.isEmpty(parkNo)) {// 验证停车场
					info.setMsg("参数错误");
					info.setCode(Constant.FAIL);// 返回 3 失败
					return info;
				}
				if (StringUtils.isEmpty(spaceNo)) {// 验证车位号
					info.setMsg("参数错误");
					info.setCode(Constant.FAIL);// 返回 3 失败
					return info;
				}
				// 验证来源
				if (source == null) {
					info.setCode(Constant.FAIL);
					info.setMsg("参数错误!");
					return info;
				}
				// 进行降锁处理
				ResultInfo<LockOrder> setOrder = cParkOrderService.setOrder(
						memberNo, source, lon, lat, spaceNo, parkNo, flag,
						orderNo);
				if (setOrder != null
						&& Constant.SECCUESS.equals(setOrder.getCode())) {
					info.setData(setOrder.getData());
					info.setMsg(setOrder.getMsg());
					info.setCode(Constant.SECCUESS);// 返回 1成功
					return info;
				} else {
					info.setMsg(setOrder.getMsg());
					info.setCode(setOrder.getCode());
					return info;
				}
			} else {
				info.setMsg(resultInfoMemberNo.getMsg());
				info.setCode(resultInfoMemberNo.getCode());
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	/**
	 * 获取订单列表
	 * 
	 * @param memberNo
	 *            订单编号
	 * @return
	 */
	@RequestMapping("/getOrder")
	@ResponseBody
	public ResultInfo<List<ParkLockOrder>> getOrder(String memberNo,
			Integer pageNo, Integer pageSize) {
		ResultInfo<List<ParkLockOrder>> info = new ResultInfo<List<ParkLockOrder>>();
		try {
			// 验证会员信息
			ResultInfo<Object> resultInfoMemberNo = cParkLockService
					.resultInfoMemberNo(memberNo, 2);
			if (resultInfoMemberNo != null
					&& Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
				if (pageNo == null || pageSize == null) {
					info.setCode(Constant.FAIL);
					info.setMsg("参数异常!");
					return info;
				} else {
					CParkOrder po = new CParkOrder();
					po.setMemberNo(memberNo);
					List<CParkOrder> cParkOrderList = cParkOrderService
							.getCParkOrderList(new Query(pageNo, pageSize, po));
					if (cParkOrderList != null && cParkOrderList.size() > 0) {
						List<ParkLockOrder> orderList = new ArrayList<ParkLockOrder>();
						for (CParkOrder p : cParkOrderList) {
							ParkLockOrder plo = new ParkLockOrder(p);
							if (p.getParkType() == 1) {// 地锁加入车位号
								CParkLock cParkLock = cParkLockService
										.getCParkLock(p.getParkLockNo());
								plo.setSpaceNo(cParkLock.getSpaceNo());
							}
							orderList.add(plo);
						}
						info.setCode(Constant.SECCUESS);
						info.setMsg("查询成功!");
						info.setData(orderList);
						return info;
					} else {
						info.setCode(Constant.OTHER);
						info.setMsg("暂无订单信息!");
						return info;
					}
				}
			} else {
				info.setMsg(resultInfoMemberNo.getMsg());
				info.setCode(resultInfoMemberNo.getCode());
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	/**
	 * 获取订单详情
	 * 
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/getOrderInfo")
	@ResponseBody
	public ResultInfo<OrderInfo> getOrderInfo(String orderNo) {
		ResultInfo<OrderInfo> info = new ResultInfo<OrderInfo>();
		try {
			if (StringUtils.isEmpty(orderNo)) {
				info.setCode(Constant.FAIL);
				info.setMsg("参数异常!");
				return info;
			}
			OrderInfo orderInfo = cParkOrderService.getOrderInfo(orderNo);
			if (orderInfo != null) {
				if (orderInfo.getEndTime() != null
						&& !"".equals(orderInfo.getEndTime())) {
					// 总时间小于免费时长
					if (ECCalculateUtils.lt(orderInfo.getDuration(),
							orderInfo.getGratisTime())) {
						orderInfo.setOverstepTime(0);// 超时时长
					} else {
						orderInfo.setOverstepTime((int) ECCalculateUtils.sub(
								orderInfo.getDuration(),
								orderInfo.getGratisTime()));
					}
				}
				if (orderInfo.getEntryTime() == null
						&& StringUtils.isEmpty(orderInfo.getEntryTime())) {
					orderInfo.setGratisTime(0);
				}
				info.setCode(Constant.SECCUESS);
				info.setData(orderInfo);
				info.setMsg("查询成功!");
				return info;
			} else {
				info.setCode(Constant.OTHER);
				info.setMsg("暂无订单信息!");
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	/**
	 * 升锁
	 * 
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/upLock")
	@ResponseBody
	public ResultInfo<Object> upLock(String orderNo) {
		return cParkOrderService.upLock(orderNo);
	}

	/**
	 * 预约停车-并生成订单
	 * 
	 * @param memberNo
	 * @param lockNo
	 * @param source
	 * @return
	 */
	@RequestMapping("/appointment")
	@ResponseBody
	public ResultInfo<Object> appointment(String memberNo, String parkNo,
			String spaceNo, Integer source) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		try {
			// 验证会员信息
			ResultInfo<Object> resultInfoMemberNo = cParkLockService
					.resultInfoMemberNo(memberNo, 1);
			if (resultInfoMemberNo != null
					&& Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
				// 验证来源
				if (source == null) {
					info.setCode(Constant.FAIL);
					info.setMsg("参数错误!");
					return info;
				}
				// 进行预约处理
				ResultInfo<LockOrder> setOrder = cParkOrderService
						.setOrderAppointment(memberNo, source, parkNo, spaceNo);
				if (setOrder != null
						&& Constant.SECCUESS.equals(setOrder.getCode())) {
					info.setData(setOrder.getData());
					info.setMsg(setOrder.getMsg());
					info.setCode(Constant.SECCUESS);// 返回 1成功
					return info;
				} else {
					info.setMsg(setOrder.getMsg());
					info.setCode(setOrder.getCode());
					return info;
				}

			} else {
				info.setMsg(resultInfoMemberNo.getMsg());
				info.setCode(resultInfoMemberNo.getCode());
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	/**
	 * 取消预约
	 * 
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/cancelAppointment")
	@ResponseBody
	public ResultInfo<Object> cancelAppointment(String orderNo) {
		return cParkOrderService.cancelAppointment(orderNo);
	}

	/**
	 * 验证添加爱车
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("/getLoveCar")
	@ResponseBody
	public ResultInfo<Object> getLoveCar(String memberNo) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		try {
			// 验证会员信息
			ResultInfo<Object> resultInfoMemberNo = cParkLockService
					.resultInfoMemberNo(memberNo, 2);
			if (resultInfoMemberNo != null
					&& Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
				// 验证是否有添加车辆
				List<CLoveCar> laveCar = loveCarService.getLaveCar(memberNo);
				if (laveCar != null && laveCar.size() > 0) {
					for(CLoveCar c:laveCar){
						c.setDrivingLicense(imagePath+"/"+c.getDrivingLicense());
						c.setDrivingLicenseCopy(imagePath+"/"+c.getDrivingLicenseCopy());
					}
					info.setData(laveCar);
					info.setMsg("查询成功");
					info.setCode(Constant.SECCUESS);
					return info;
				} else {
					info.setMsg("请添加您的爱车!");
					info.setCode("11");
					return info;
				}
			} else {
				info.setMsg(resultInfoMemberNo.getMsg());
				info.setCode(resultInfoMemberNo.getCode());
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}
/*	@RequestMapping("/verifyBalance")
	@ResponseBody
	public */
}
