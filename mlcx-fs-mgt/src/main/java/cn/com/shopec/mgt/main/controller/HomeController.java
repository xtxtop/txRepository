package cn.com.shopec.mgt.main.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.common.utils.RankingStr;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.model.MemberOneDayVO;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.OrderStrikeBalanceService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.system.model.SysOpLog;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserRoleRel;
import cn.com.shopec.core.system.service.SysOpLogService;
import cn.com.shopec.core.system.service.SysPermissionService;
import cn.com.shopec.core.system.service.SysRolePermRelService;
import cn.com.shopec.core.system.service.SysRoleService;
import cn.com.shopec.core.system.service.SysUserRoleRelService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.car.vo.CarVo;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.common.ECMgtConstant;
import cn.com.shopec.mgt.common.PermissionListSingleton;
import cn.com.shopec.mgt.common.SessionUtil;

@Controller
@RequestMapping
public class HomeController extends BaseController {

	@Resource
	public SysUserService sysUserService;
	@Resource
	public SysRoleService sysRoleService;
	@Resource
	private SysUserRoleRelService sysUserRoleRelService;
	@Resource
	private SysPermissionService sysPermissionService;
	@Resource
	private SysRolePermRelService sysRolePermRelService;
	@Resource
	private SysOpLogService sysOpLogService;
	@Resource
	private MemberService memberService;
	@Resource
	private OrderService orderService;
	@Resource
	private CarService carService;
	@Resource
	private DepositRefundService depositRefundService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private WarningService warningService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private OrderStrikeBalanceService orderStrikeBalanceService;
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private ParkService parkService;

	@RequestMapping({ "index" })
	public String toIndexPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		SysUser sysUser = getLoginSysUser();
		if (null != sysUser) {
			sysUser = sysUserService.getSysUser(sysUser.getUserName(), sysUser.getPassword());
			modelMap.put("sysUser", sysUser);
			List<SysRole> role = (List<SysRole>) SessionUtil.getDataFromSession(request,ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_ROLE, SysRole.class);
			modelMap.put("sysRole", role);

			if (null == sysUser) {
				return "redirect:toLogin.do";
			}
		} else {
			return "redirect:toLogin.do";
		}

		// 加载首页面板相关数据（会员、车辆、订单、电桩）
		// 准备请求参数，查询当前天从0时0分开始到请求时间为止的数据
		/*
		 * try { String startTime =
		 * ECDateUtils.formatDate(ECDateUtils.getCurrentDateStartTime()); String
		 * endTime = ECDateUtils.formatDate(ECDateUtils.getCurrentDateTime());
		 * //会员数据 memberService.getIndexValue(startTime,endTime,1);
		 * //当天开始时间，请求截止时间，查询全部数据 } catch (ParseException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		modelMap.put("roleAdminTag",getLoginSysUserRoleAdmin()); // 订单总数
		return "main/index";
	}

	/**
	 * 获取会员实时数据
	 * 
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping({ "getRealTimeMeberNum" })
	@ResponseBody
	public ResultInfo<MemberOneDayVO> getRealTimeMeberNum() throws JSONException {
		ResultInfo<MemberOneDayVO> result = new ResultInfo<>();
		try {
			String startTime = ECDateUtils.formatDate(ECDateUtils.getCurrentDateStartTime());
			String endTime  = ECDateUtils.formatDate(ECDateUtils.getCurrentDateTime());
			//会员数据
			MemberOneDayVO vo = memberService.getIndexValue(startTime,endTime);  	//当天开始时间，请求截止时间，查询全部数据
			result.setCode(Constant.SECCUESS);
			result.setData(vo);
		} catch (ParseException e) {
			result.setCode(Constant.FAIL);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取车辆实时监控数据：闲置中车辆，调度中车辆，订单中车辆，上线车辆，上线车辆，下线车辆
	 * 
	 * @return
	 */
	@RequestMapping("getRealTimeCarInfo")
	@ResponseBody
	public ResultInfo<CarVo> getRealTimeCarInfo() {
		ResultInfo<CarVo> result = new ResultInfo<>();
		CarVo carVo = new CarVo();
		Car car = new Car();
		car.setOnlineStatus(1);
		int onlineCarNum = carService.countCar(new Query(car));
		carVo.setOnlineCarNum(onlineCarNum);//上线车辆总数
		
		int carTotalNum = carService.countCar(new Query(new Car()));
		carVo.setOffloneCarNum(carTotalNum - onlineCarNum);//下线车辆总数
		
		CarStatus carStatus = new CarStatus();
		carStatus.setUserageStatus(2);
		int inOrderNum = carStatusService.count(new Query(carStatus));
		carStatus.setUserageStatus(1);
		//预占中车辆总数
		int yuOrderNum = carStatusService.count(new Query(carStatus));
		carVo.setInOrderCarNum(inOrderNum+yuOrderNum);//订单中车辆总数
		
		carStatus.setUserageStatus(3);
		int inWorkerOrderNum = carStatusService.count(new Query(carStatus));
		carVo.setInWorkerOrderCarNum(inWorkerOrderNum);//调度中车辆总数
		
		//上线的空闲车辆总数
		carVo.setFreeCarNum(onlineCarNum - inOrderNum-inWorkerOrderNum);
		
		result.setCode(Constant.SECCUESS);
		result.setData(carVo);
		return result;
	}

	/**
	 * 获取车辆订单数量按时监控数据
	 * 
	 * @return
	 */
	@RequestMapping("getRealTimeInOrderNum")
	@ResponseBody
	public ResultInfo<List<Map<String, Object>>> getRealTimeInOrderNum() {
		ResultInfo<List<Map<String, Object>>> resultInfo = new ResultInfo<List<Map<String, Object>>>();
		List<Map<String, Object>> realTimeInOrderNum = orderService.getRealTimeInOrderNum();
		Map<String, Object> map = realTimeInOrderNum.get(0);
		Map<String, Object> resultMap = new LinkedHashMap<>();
		List<String> keyList = new ArrayList<>();
		for(String key:map.keySet()){
			keyList.add(key);
		}
		String[] keyStr = new String[keyList.size()];
		for(int i=0;i<keyList.size();i++){
			keyStr[i] = keyList.get(i);
		}
		RankingStr.strSort(keyStr);
		for(int i=0;i<keyStr.length;i++){
			resultMap.put(keyStr[i], map.get(keyStr[i]));
		}
		realTimeInOrderNum.remove(0);
		realTimeInOrderNum.add(resultMap);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(realTimeInOrderNum);
		return resultInfo;
	}

	/**
	 * 获取车辆警告信息按时监控数据 onOrderNum，ownFeeNum，carFreeTimeNum，carSpaceShortage
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("getRealTimeWarning")
	@ResponseBody
	public ResultInfo<List<Map<String, Object>>> getRealTimeWarning() throws ParseException {
		String startTime = ECDateUtils.formatDate(ECDateUtils.getCurrentDateStartTime());
		String endTime  = ECDateUtils.formatDate(ECDateUtils.getCurrentDateTime());
		ResultInfo<List<Map<String, Object>>> resultInfo = new ResultInfo<List<Map<String, Object>>>();
		List<Map<String, Object>> realTimeWaringNum = warningService.getRealTimeWarning(startTime,endTime);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(realTimeWaringNum);
		return resultInfo;
	}

	/**
	 * 获取待办事项 （1.认证待审核 2.押金退款待审核 3 调度待审核 4 冲账待审核 5 活动待审核 6 套餐产品待审核）
	 * 
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping({ "indexTodo" })
	@ResponseBody
	public String toTestTodoValue() throws JSONException {
		// 返回结果集
		JSONObject results = new JSONObject();
		// 待审核会员数据
		Integer memberResult = memberService.getTodoIndexValue(); // 认证待审核

		results.put("memberCensorNum", memberResult);

		//押金退款待审核
		Integer depositRefundResult = depositRefundService.getTodoIndexValue(); // .押金退款待审核
		results.put("depositRefundNum", depositRefundResult);

		Integer workerOrderResult = workerOrderService.getTodoIndexValue(); // .调度工单待审核
		results.put("workerOrderNum", workerOrderResult);

		Integer orderStrikeBalanceResult = orderStrikeBalanceService.getTodoIndexValue(); // .订单冲账待审核
		results.put("orderStrikeBalanceNum", orderStrikeBalanceResult);

		Integer pricingPackageResult = pricingPackageService.getTodoIndexValue(); // .套餐产品待审核
		results.put("pricingPackageNum", pricingPackageResult);
		
		Integer warningResult = warningService.getTodoIndexValue(); // .低电压警告
		results.put("warningNum", warningResult);

		Long parkNum = parkService.count(new Query()); // .场站数量

		results.put("parkNum", parkNum);

		return results.toString();
	}

	@RequestMapping({ "toLogin" })
	public String tologin() {
		return "main/black_login";
	}

	@RequestMapping({ "/login" })
	public String login(String loginName, String loginPassword, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		if (loginPassword != null) {
			loginPassword = ECMd5Utils.crypt(loginPassword);
		}
		// 查询用户是否存在
		SysUser sysUser = sysUserService.getSysUser(loginName, loginPassword);
		// 用户被删除则不让登录
		if (sysUser != null && Constant.DEL_STATE_YES != sysUser.getIsDeleted()&&sysUser.getIsAvailable()!=0) {//0:不可用
			
			//系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
			//SystemType
			//操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
			//OpType
			//模块名称(比如登陆，就写登陆。操作权限管理就写权限管理，具体到每一项。)
			//ModuleName
			//操作人
			//OperatorUserName
			//操作人类型与id
			//Operator
			//用户本次操作记录到系统日志表
			// add(1, "o", "登陆",loginName,null);//传参见以上注解
			 try{
					SysOpLog sysOpLog = new SysOpLog();
					sysOpLog.setSystemType(1);// 系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
					sysOpLog.setOpType("O");// 操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
					sysOpLog.setModuleName("登录");// 模块名称
					sysOpLog.setBizObjType("登录");// 业务对象（跟业务对象有关的操作时，可记录到该字段，如修改订单，则业务对象类型为订单，具体见编码见字典）
					sysOpLog.setBizObjId(sysUser.getUserId());// 业务对象id（跟业务对象有关的操作，可记录该字段，对应具体业务对象的id值）
					sysOpLog.setOperatorUserName(sysUser.getUserName());// 操作人用户名（根据操作人类型会对应不同的用户名）
					sysOpLog.setOperatorId(sysUser.getUserId());// 操作人id（根据操作人类型会对应不同的表记录）
					sysOpLog.setOperatorType(1);// 操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
					sysOpLog.setLogMsg(sysUser.getUserName()+"登录后台,登录时间为"+new Date());// 日志信息（简单信息）
					// 日志添加
					opLogService.add(sysOpLog, getOperator());
				}catch(Exception e){
					e.printStackTrace();
				}
			 
			// 加载资源(权限)
			// SysUserRoleRel sysUserRoleRel =
			// sysUserRoleRelService.getByUserId(sysUser.getUserId());
			if (sysUser.getSysRole() != null) {
				List<SysRole> role = sysUser.getSysRole();
				// role保存到session
				SessionUtil.setDataToSession(request, ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_ROLE, role);
				// roleId保存到session
				SessionUtil.setDataToSession(request, ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_ROLEID,role);
				PermissionListSingleton perListSlt = null;
				String[] roleIds = new String[role.size()];
				for(int i=0;i<role.size();i++){
					roleIds[i]=role.get(i).getRoleId();
					//存单例模式
					perListSlt = PermissionListSingleton.getInstance(roleIds[i].toString());
					List<SysPermission> perList = sysRolePermRelService.getAllRolePermissions(roleIds);
					
					// 保存角色权限列表
					perListSlt.setPerList(perList);
					perListSlt.setPerUrlMap(perList);
					
					sysUser.getSysRole().get(i).setPermissionIds(roleIds);
					sysUser.getSysRole().get(i).setPerList(perList);
					
				}
			}
			// sysUser保存到session
			setLoginSysUser(request, sysUser);
			setLoginSysUserRoleAdmin(request, sysUser);
			
			
			return "redirect:index.do";
		} else {
			if(loginName!=null&&loginPassword!=null){
				modelMap.put("errorInfo", "用户名或者密码错误！");
			}else{
				modelMap.put("errorInfo", "");
			}
			return "main/black_login";
		}
	}

	/**
	 * 左菜单
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/leftPage")
	@ResponseBody
	public List<Map<String, Object>> leftPage(HttpServletResponse response, HttpServletRequest request, ModelMap model)
			throws Exception {
		SysUser sysUser = getLoginSysUser();
		if (sysUser == null) {
			return null;
		}
		// 加载资源(权限)
		List<SysUserRoleRel> sysUserRoleRel = sysUserRoleRelService.getByUserId(sysUser.getUserId());
		String[] roleIds = new String[sysUserRoleRel.size()];
		for (int i = 0; i < sysUserRoleRel.size(); i++) {
			roleIds[i] = sysUserRoleRel.get(i).getRoleId();
		}
		return getMenuList(roleIds, request);
	}

	@RequestMapping({ "loginOut" })
	public String loginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
		// SystemType
		// 操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
		// OpType
		// 模块名称(比如登陆，就写登陆。操作权限管理就写权限管理，具体到每一项。)
		// ModuleName
		// 操作人
		// OperatorUserName
		// 操作人类型与id
		// Operator
		// 用户本次操作记录到系统日志表
		SysUser sysUser = (SysUser) session.getAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER);
		if (sysUser != null) {
			String operatorUserName = sysUser.getUserName();
			add(1, "o", "退出", operatorUserName, null);// 传参见以上注解
			try {
				SysOpLog sysOpLog = new SysOpLog();
				sysOpLog.setSystemType(1);// 系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
				sysOpLog.setOpType("O");// 操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
				sysOpLog.setModuleName("退出");// 模块名称
				sysOpLog.setBizObjType("退出");// 业务对象（跟业务对象有关的操作时，可记录到该字段，如修改订单，则业务对象类型为订单，具体见编码见字典）
				sysOpLog.setBizObjId(sysUser.getUserId());// 业务对象id（跟业务对象有关的操作，可记录该字段，对应具体业务对象的id值）
				sysOpLog.setOperatorUserName(sysUser.getUserName());// 操作人用户名（根据操作人类型会对应不同的用户名）
				sysOpLog.setOperatorId(sysUser.getUserId());// 操作人id（根据操作人类型会对应不同的表记录）
				sysOpLog.setOperatorType(1);// 操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
				sysOpLog.setLogMsg(sysUser.getUserName() + "退出后台,退出时间为" + new Date());// 日志信息（简单信息）
				// 日志添加
				opLogService.add(sysOpLog, getOperator());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		removeSessionUser(request);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:toLogin.do";
	}

	public List<Map<String, Object>> getMenuList(String[] roleId, HttpServletRequest request) {
		String basePath = request.getParameter("appPath");
		if (basePath == null) {
			basePath = "";
		}
		List<SysPermission> sysPermissionList = sysRolePermRelService.getMenuList(roleId);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 资源根据层级分组
		List<SysPermission> superResourceList = new ArrayList<SysPermission>();
		List<SysPermission> seResourceList = new ArrayList<SysPermission>();
		List<SysPermission> thResourceList = new ArrayList<SysPermission>();
		if (sysPermissionList != null && sysPermissionList.size() > 0) {
			Collections.sort(sysPermissionList, new ComparatorSysPermission());
			for (SysPermission temp : sysPermissionList) {
				if (temp.getLevel() == 1) {
					superResourceList.add(temp);
				} else if (temp.getLevel() == 2) {
					seResourceList.add(temp);
				} else {
					thResourceList.add(temp);
				}
			}
		}
		// 一级菜单
		for (SysPermission one : superResourceList) {
			Map<String, Object> viewSysPermission = new HashMap<String, Object>();
			viewSysPermission.put("id", one.getPermId());
			viewSysPermission.put("name", one.getMenuName());
			viewSysPermission.put("iconName",one.getIconName());
			viewSysPermission.put("link", basePath + one.getPermResource());
			viewSysPermission.put("sort", one.getRanking());
			viewSysPermission.put("parentId", "#");
			// 二级菜单
			List<Map<String, Object>> twoList = new ArrayList<Map<String, Object>>();
			for (SysPermission two : seResourceList) {
				Map<String, Object> towMap = new HashMap<String, Object>();
				if (two.getParentId().equals(one.getPermId())) {
					towMap.put("id", two.getPermId());
					towMap.put("name", two.getMenuName());
					towMap.put("iconName", two.getIconName());
					towMap.put("link", basePath + two.getPermResource());
					towMap.put("sort", two.getRanking());
					towMap.put("parentId", two.getParentId());
					twoList.add(towMap);
				}
				// 三级菜单
				List<Map<String, Object>> thrList = new ArrayList<Map<String, Object>>();
				for (SysPermission thr : thResourceList) {
					Map<String, Object> thrMap = new HashMap<String, Object>();
					if (thr.getParentId().equals(two.getPermId())) {
						thrMap.put("id", thr.getPermId());
						thrMap.put("name", thr.getMenuName());
						thrMap.put("iconName", thr.getIconName());
						thrMap.put("link", basePath + thr.getPermResource());
						thrMap.put("sort", thr.getRanking());
						thrMap.put("parentId", thr.getParentId());
						thrList.add(thrMap);
					}
				}
				towMap.put("child", thrList);
			}
			viewSysPermission.put("child", twoList);
			resultList.add(viewSysPermission);
		}
		return resultList;

	}

	/*
	 * 类型排序
	 */
	class ComparatorSysPermission implements Comparator<SysPermission> {

		@Override
		public int compare(SysPermission obj1, SysPermission obj2) {
			if (obj1.getRanking() != null && obj2.getRanking() != null) {
				return obj1.getRanking().compareTo(obj2.getRanking());
			} else {
				return 1;
			}
		}

	}
	

	/**
	 * 查询当前用户的菜单访问权限
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "menuList" })
	@ResponseBody
	public List<Map<String, Object>> menuList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		SysUser sysUser = getLoginSysUser();  
		 

			// 加载资源(权限)
			List<SysUserRoleRel> sysUserRoleRel = sysUserRoleRelService.getByUserId(sysUser.getUserId());
			String[] roleIds = new String[sysUserRoleRel.size()];
			for (int i = 0; i < sysUserRoleRel.size(); i++) {
				roleIds[i] = sysUserRoleRel.get(i).getRoleId();
			}
			
			List<Map<String, Object>> menuList = getMenuList2(roleIds, request); 
			 
		return menuList;
	}

	/**
	 * 内部url权限
	 * @param roleId
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getMenuList2(String[] roleId, HttpServletRequest request) {
		String basePath = request.getParameter("appPath");
		if (basePath == null) {
			basePath = "";
		}
		List<SysPermission> sysPermissionList = sysRolePermRelService.getMenuList(roleId);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 资源根据层级分组
		List<SysPermission> superResourceList = new ArrayList<SysPermission>();
		List<SysPermission> seResourceList = new ArrayList<SysPermission>();
		List<SysPermission> thResourceList = new ArrayList<SysPermission>();
		if (sysPermissionList != null && sysPermissionList.size() > 0) {
			Collections.sort(sysPermissionList, new ComparatorSysPermission());
			for (SysPermission temp : sysPermissionList) {
				if (temp.getLevel() == 1) {
					superResourceList.add(temp);
				} else if (temp.getLevel() == 2) {
					seResourceList.add(temp);
				} else {
					thResourceList.add(temp);
				}
			}
		}
		// 一级菜单
		for (SysPermission one : superResourceList) {
			Map<String, Object> viewSysPermission = new HashMap<String, Object>();
			viewSysPermission.put("id", one.getPermId());
			viewSysPermission.put("name", one.getMenuName());
			viewSysPermission.put("iconName",one.getIconName());
			viewSysPermission.put("link", basePath + one.getPermResource());
			viewSysPermission.put("sort", one.getRanking());
			viewSysPermission.put("parentId", "#");
			// 二级菜单
			List<Map<String, Object>> twoList = new ArrayList<Map<String, Object>>();
			for (SysPermission two : seResourceList) {
				Map<String, Object> towMap = new HashMap<String, Object>();
				if (two.getParentId().equals(one.getPermId())) {
					towMap.put("id", two.getPermId());
					towMap.put("name", two.getMenuName());
					towMap.put("iconName", two.getIconName());
					towMap.put("link", basePath + two.getPermResource());
					towMap.put("sort", two.getRanking());
					towMap.put("parentId", two.getParentId());
					twoList.add(towMap);
				}
				// 三级菜单
				List<Map<String, Object>> thrList = new ArrayList<Map<String, Object>>();
				for (SysPermission thr : seResourceList) {
					Map<String, Object> thrMap = new HashMap<String, Object>();
					if (thr.getParentId().equals(two.getPermId())) {
						thrMap.put("id", thr.getPermId());
						thrMap.put("name", thr.getMenuName());
						thrMap.put("iconName", thr.getIconName());
						thrMap.put("link", thr.getPermResource());
						thrMap.put("sort", thr.getRanking());
						thrMap.put("parentId", thr.getParentId());
						thrList.add(thrMap);
					}
				}
				viewSysPermission.put("child", thrList);
			}
			viewSysPermission.put("child", twoList);
			resultList.add(viewSysPermission);
		}
		return resultList;

	}
}