package cn.com.shopec.mapi.worker.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarOnlineLog;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarOnlineLogService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.customer.model.CustomerFeedback;
import cn.com.shopec.core.customer.service.CustomerFeedbackService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.mapi.car.vo.CarOnLineVo;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.customer.vo.CustomerFeedbackVo;
import cn.com.shopec.mapi.worker.vo.WorkerEmp;
import cn.com.shopec.mapi.worker.vo.WorkerPark;

@Controller
@RequestMapping("/workerApp/worker")
public class WorkerController extends BaseController {

	@Resource
	private WorkerService workerService;
	
	@Resource
	private ParkService parkService;
	
	@Resource
	private CustomerFeedbackService customerFeedbackService;
	
	@Resource
	private CarService carService;
	
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private CarOnlineLogService carOnlineLogService;
	
	@Value("${image_path}")
	private String imgPath;
	/**
	 * 登录
	 * */
	@RequestMapping("/login")
	@ResponseBody
	public ResultInfo<Worker> login(Worker worker) {
		String passwordN=ECMd5Utils.encryptMD5(worker.getPassword());
		ResultInfo<Worker> resultInfo = new ResultInfo<Worker>();
		if(workerService.getWorkerByEmpNo(worker.getEmpNo())!=null){
			Worker w=workerService.getWorkerByEmpNo(worker.getEmpNo());
			if(w.getPassword().equals(passwordN)){
				//setLoginMember(request, m);// 将登录信息放到session
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(w);
				resultInfo.setMsg(MemberConstant.login_success_msg);
			}else{
				resultInfo.setCode(MemberConstant.password_code);
				resultInfo.setMsg(MemberConstant.login_fail_msg);
			}
		}else{
			resultInfo.setCode(MemberConstant.phone_code);
			resultInfo.setMsg(MemberConstant.login_fail_msg);
		}
		return resultInfo;
	}
	
	/**
	 * 修改密码
	 * */
	@RequestMapping("/workerForgetPassword")
	@ResponseBody
	public ResultInfo<Worker> workerForgetPassword(String empNo,String oldPassword,String password) {
		ResultInfo<Worker> resultInfo=new ResultInfo<Worker>();

		String passwordO = ECMd5Utils.encryptMD5(oldPassword);
		String passwordN = ECMd5Utils.encryptMD5(password);
		
		if(workerService.getWorkerByEmpNo(empNo)!=null){
			Worker w=workerService.getWorkerByEmpNo(empNo);
			if(w.getPassword().equals(passwordO)){
				 w.setPassword(passwordN);
				 workerService.updateWorker(w, getOperator());
				 resultInfo.setCode(Constant.SECCUESS);
				 resultInfo.setData(w);
				 resultInfo.setMsg(MemberConstant.success_update_psd_msg);
			}else{
				resultInfo.setCode(MemberConstant.password_code);
				resultInfo.setMsg(MemberConstant.login_fail_msg);
			}
		}else{
			resultInfo.setCode(MemberConstant.phone_code);
			resultInfo.setMsg(MemberConstant.login_fail_msg);
		}
	 
		return resultInfo;
	}
	/**
	 * 侧边栏菜单显示
	 * */
	@RequestMapping("/sidebarMenu")
	@ResponseBody
	public ResultInfo<WorkerEmp> sidebarMenu(String workerNo) {
		ResultInfo<WorkerEmp> result = new ResultInfo<WorkerEmp>();
		Worker worker=workerService.getWorker(workerNo);
		return workerToEmpVo(result,worker);
	}
	/**
	 * 进入问题反馈页面，显示场站地址和当前时间
	 * */
	@RequestMapping("/toDispatcherFeedback")
	@ResponseBody
	public ResultInfo<WorkerPark> toDispatcherFeedback(String parkNo) {
		ResultInfo<WorkerPark> result = new ResultInfo<WorkerPark>();
		Park park=parkService.getPark(parkNo);
		return workerParkToVo(result,park);
	}
	/**
	 *问题反馈（添加）
	 * */
	@RequestMapping("/addDispatcherFeedback")
	@ResponseBody
	public ResultInfo<CustomerFeedbackVo> addDispatcherFeedback(CustomerFeedback customerFeedback) {
		ResultInfo<CustomerFeedbackVo> result =new ResultInfo<CustomerFeedbackVo>();
		if(customerFeedback.getPhotoUrl1()!=null&&!customerFeedback.getPhotoUrl1().equals("")){
			customerFeedback.setPhotoUrl1(customerFeedback.getPhotoUrl1().substring(imgPath.length()+1));
		}
		if(customerFeedback.getPhotoUrl2()!=null&&!customerFeedback.getPhotoUrl2().equals("")){
			customerFeedback.setPhotoUrl2(customerFeedback.getPhotoUrl2().substring(imgPath.length()+1));
		}
		if(customerFeedback.getPhotoUrl3()!=null&&!customerFeedback.getPhotoUrl3().equals("")){
			customerFeedback.setPhotoUrl3(customerFeedback.getPhotoUrl3().substring(imgPath.length()+1));
		}
		Worker worker=getLoginWorker();
		if(worker!=null){
			worker=workerService.getWorker(worker.getWorkerNo());
			customerFeedback.setMemberName(worker.getWorkerName());
			customerFeedback.setMobilePhone(worker.getMobilePhone());
		}
		customerFeedback.setFeedbackType(1);//反馈类型（问题反馈：1）
		customerFeedback.setCustomerType(2);//反馈人类型（2：调度员）
		return customerFeedbackToVo(result,customerFeedbackService.addCustomerFeedback(customerFeedback, getOperator()).getData());
	}
	/**
	 * 调度端可以做车辆的上下线操作
	 * */
	@RequestMapping("/onLineOrOffLineCar")
	@ResponseBody
	public ResultInfo<CarOnLineVo> onLineOrOffLineCar(String carNo,Integer onLineOrOffLine,String onOffLineReason,String carOnLineOrOffPicUrl1,String carOnLineOrOffPicUrl2,String carOnLineOrOffPicUrl3,String workerNo) {
		ResultInfo<CarOnLineVo> resultInfo=new ResultInfo<CarOnLineVo>();
		ResultInfo<Car> result=new ResultInfo<Car>();
		Car car=carService.getCar(carNo);
		if(car!=null){
			if(carOnLineOrOffPicUrl1!=null&&!carOnLineOrOffPicUrl1.equals("")){
				car.setCarOnLineOrOffPicUrl1(carOnLineOrOffPicUrl1.substring(imgPath.length()+1));
			}
			if(carOnLineOrOffPicUrl2!=null&&!carOnLineOrOffPicUrl2.equals("")){
				car.setCarOnLineOrOffPicUrl2(carOnLineOrOffPicUrl2.substring(imgPath.length()+1));
			}
			if(carOnLineOrOffPicUrl3!=null&&!carOnLineOrOffPicUrl3.equals("")){
				car.setCarOnLineOrOffPicUrl3(carOnLineOrOffPicUrl3.substring(imgPath.length()+1));
			}
			Worker worker = null;
			if(workerNo != null){
				worker = workerService.getWorker(workerNo);
			}
			if(onLineOrOffLine.equals(1)){//上线操作
				Car carOn=new Car();
				carOn.setCarNo(car.getCarNo());
				carOn.setOnlineStatus(onLineOrOffLine);
				carOn.setOnOffLineReason(onOffLineReason);
				if(car.getCarOnLineOrOffPicUrl1()!=null&&!car.getCarOnLineOrOffPicUrl1().equals("")){
					carOn.setCarOnLineOrOffPicUrl1(car.getCarOnLineOrOffPicUrl1());
				}
				if(car.getCarOnLineOrOffPicUrl2()!=null&&!car.getCarOnLineOrOffPicUrl2().equals("")){
					carOn.setCarOnLineOrOffPicUrl2(car.getCarOnLineOrOffPicUrl2());
				}
				if(car.getCarOnLineOrOffPicUrl3()!=null&&!car.getCarOnLineOrOffPicUrl3().equals("")){
					carOn.setCarOnLineOrOffPicUrl3(car.getCarOnLineOrOffPicUrl3());
				}
				if(workerNo != null){
					carOn.setWorkerNo(workerNo);
					carOn.setCarTestType(2);
					if(worker != null){
						carOn.setWorkerName(worker.getWorkerName());
					}
				}
				result=carService.updateCar(carOn, getOperator1());
				resultInfo.setCode(result.getCode());
				resultInfo.setMsg(result.getMsg());
				
				if(result.getCode()=="1"){
					Car carResult=result.getData();
					CarOnLineVo cov=new CarOnLineVo();
					cov.setCarNo(carResult.getCarNo());
					cov.setCarPlateNo(carResult.getCarPlateNo());
					cov.setOnLineStatus(carResult.getOnlineStatus());
					cov.setUserageStatus(carResult.getUserageStatus());
					resultInfo.setData(cov);
					resultInfo.setMsg(CarConstant.on_line_msg);
				}
				
				//向 车辆上下线 日志表添加数据
				if(resultInfo.getCode()=="1"){
						CarOnlineLog carOnlineLog =new CarOnlineLog();
						carOnlineLog.setCarNo(car.getCarNo());
						carOnlineLog.setCarPlateNo(car.getCarPlateNo());
						carOnlineLog.setOpType(1);
						carOnlineLog.setMemo(onOffLineReason);
						 Operator operator=new Operator();
						 operator.setOperatorId(car.getOperatorId());
						 operator.setOperatorType(car.getOperatorType());
						carOnlineLogService.addCarOnlineLog(carOnlineLog, operator);
					
					
				}
			}else if(onLineOrOffLine.equals(0)){//下线操作，判断该车辆的使用状态，空闲车辆可以下线
				CarStatus cs=carStatusService.getCarStatus(carNo);
				if(cs.getUserageStatus().equals(0)){
					Car carOn=new Car();
					carOn.setCarNo(car.getCarNo());
					carOn.setOnlineStatus(onLineOrOffLine);
					carOn.setOnOffLineReason(onOffLineReason);
					if(car.getCarOnLineOrOffPicUrl1()!=null&&!car.getCarOnLineOrOffPicUrl1().equals("")){
						carOn.setCarOnLineOrOffPicUrl1(car.getCarOnLineOrOffPicUrl1());
					}
					if(car.getCarOnLineOrOffPicUrl2()!=null&&!car.getCarOnLineOrOffPicUrl2().equals("")){
						carOn.setCarOnLineOrOffPicUrl2(car.getCarOnLineOrOffPicUrl2());
					}
					if(car.getCarOnLineOrOffPicUrl3()!=null&&!car.getCarOnLineOrOffPicUrl3().equals("")){
						carOn.setCarOnLineOrOffPicUrl3(car.getCarOnLineOrOffPicUrl3());
					}
					if(workerNo != null){
						carOn.setWorkerNo(workerNo);
						carOn.setCarTestType(2);
						if(worker != null){
							carOn.setWorkerName(worker.getWorkerName());
						}
					}
					result=carService.updateCar(carOn, getOperator1());
					resultInfo.setCode(result.getCode());
					resultInfo.setMsg(result.getMsg());
					if(result.getCode()=="1"){
						Car carResult=result.getData();
						CarOnLineVo cov=new CarOnLineVo();
						cov.setCarNo(carResult.getCarNo());
						cov.setCarPlateNo(carResult.getCarPlateNo());
						cov.setOnLineStatus(carResult.getOnlineStatus());
						cov.setUserageStatus(cs.getUserageStatus());
						resultInfo.setData(cov);
						resultInfo.setMsg(CarConstant.off_line_msg);
					}
					
					//向 车辆上下线 日志表添加数据
					if(resultInfo.getCode()=="1"){
							CarOnlineLog carOnlineLog =new CarOnlineLog();
							carOnlineLog.setCarNo(car.getCarNo());
							carOnlineLog.setCarPlateNo(car.getCarPlateNo());
							carOnlineLog.setOpType(0);
							carOnlineLog.setMemo(onOffLineReason);
							 Operator operator=new Operator();
							 operator.setOperatorId(car.getOperatorId());
							 operator.setOperatorType(car.getOperatorType());
							carOnlineLogService.addCarOnlineLog(carOnlineLog, operator);
						
						
					}
				}else{
					resultInfo.setCode(CarConstant.car_no_free);
					resultInfo.setMsg(CarConstant.car_no_free_msg);
				}
			}
		}else{
			resultInfo.setCode(CarConstant.fail_code);
			resultInfo.setMsg(CarConstant.no_car_exsit_msg);
		}
		return resultInfo;
	}
	 /**
	 * 方法说明:将按特定worker对象转换成自定义对象
	 */
	 ResultInfo<WorkerEmp> workerToEmpVo(ResultInfo<WorkerEmp> result,Worker worker){
		 if(worker!=null){
			 WorkerEmp we = new WorkerEmp();
			 we.setEmpNo(worker.getEmpNo());
			 we.setWorkerNo(worker.getWorkerNo());
			 result.setData(we);
			 result.setCode(CarConstant.success_code);
			 result.setMsg(""); 
		 }else{
			 result.setCode(CarConstant.fail_code);
			 result.setMsg(CarConstant.fail_msg); 
		 }
			
		return result;
	}
	 /**
		 * 方法说明:将按特定worker对象转换成自定义对象
		 */
		 ResultInfo<WorkerPark> workerParkToVo(ResultInfo<WorkerPark> result,Park park){
			 if(park!=null){
				 WorkerPark we = new WorkerPark();
				 String address="";
				 if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
					address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
				 }else{
					address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
				 }
				 we.setAddress(address);
				 we.setNowTime(new Date());
				 we.setParkNo(park.getParkNo());
				 result.setData(we);
				 result.setCode(CarConstant.success_code);
				 result.setMsg(""); 
			 }else{
				 result.setCode(CarConstant.fail_code);
				 result.setMsg(CarConstant.fail_msg); 
			 }
				
			return result;
		}
		 /**
			 * 方法说明:将按特定worker对象转换成自定义对象
			 */
			 ResultInfo<CustomerFeedbackVo> customerFeedbackToVo(ResultInfo<CustomerFeedbackVo> result,CustomerFeedback customerFeedback){
				 if(customerFeedback!=null){
					 CustomerFeedbackVo cfbv = new CustomerFeedbackVo();
					 cfbv.setContent(customerFeedback.getContent());
					 cfbv.setCustomerType(customerFeedback.getCustomerType());
					 cfbv.setFeedbackNo(customerFeedback.getFeedbackNo());
					 cfbv.setMemberName(customerFeedback.getMemberName());
					 cfbv.setMemberNo(customerFeedback.getMemberNo());
					 cfbv.setMobilePhone(customerFeedback.getMobilePhone());
					 cfbv.setPhotoUrl1(imgPath+"/"+customerFeedback.getPhotoUrl1());
					 cfbv.setPhotoUrl2(imgPath+"/"+customerFeedback.getPhotoUrl2());
					 cfbv.setPhotoUrl3(imgPath+"/"+customerFeedback.getPhotoUrl3());
					 result.setData(cfbv);
					 result.setCode(CarConstant.success_code);
					 result.setMsg(""); 
				 }else{
					 result.setCode(CarConstant.fail_code);
					 result.setMsg(CarConstant.fail_msg); 
				 }
					
				return result;
			}
}
