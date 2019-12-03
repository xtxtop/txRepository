package cn.com.shopec.core.device.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.common.utils.JExcelUtil;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.common.DeviceConstant;
import cn.com.shopec.core.device.dao.DeviceDao;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Device 服务实现类
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Log log = LogFactory.getLog(DeviceServiceImpl.class);

	@Resource
	private DeviceDao deviceDao;

	@Resource
	private CarDao carDao;

	@Resource
	private DataDictItemService dataDictItemService;
	
	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private PrimarykeyService primarykeyService;

	/**
	 * 根据查询条件，查询并返回Device的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Device> getDeviceList(Query q) {
		List<Device> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = deviceDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Device>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回Device的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Device> getDevicePagedList(Query q,Integer roleAdminTag) {
		PageFinder<Device> page = new PageFinder<Device>();
		List<Device> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			long onlineTimeThreshol = getOnlineTimeThreshol();
			Calendar calendar = Calendar.getInstance();
		     calendar.add(Calendar.MINUTE, -(int) (onlineTimeThreshol/60000));
		     Date  dt= calendar.getTime();
		     if(q.getQ() != null){
					Device  de= (Device)q.getQ();
					if(de.getIsOnline() != null && !"".equals(de.getIsOnline()) && de.getIsOnline()==1){
						de.setLastReportingTimeStart(dt);
						}
					if(de.getIsOnline() != null && !"".equals(de.getIsOnline()) && de.getIsOnline()==2){
						de.setLastReportingTimeEnd(dt);
					}
		     }
			list = deviceDao.pageListDate(q);
			for(Device d:list){
				d.setRoleAdminTag(roleAdminTag);
				if(d.getLastReportingTime() != null){
					long timeDifference = new Date().getTime() - d.getLastReportingTime().getTime();
					if(onlineTimeThreshol >= timeDifference){
						d.setIsOnline(1);//在线
					}else{
						d.setIsOnline(2);//不在线
					}
				}
				
				Car c= carDao.get(d.getCarNo());
				if(c != null){
					d.setCarBrandName(c.getCarBrandName());
					d.setCarSeriesName(c.getCarModelName());
				}
				
			}
			
//			if(q.getQ() != null){
//				Device  d= (Device)q.getQ();
//				if(d.getIsOnline() != null && !"".equals(d.getIsOnline()) && d.getIsOnline()==1){
//					for (Device ds : list) {
//						if(ds.getIsOnline() != null && !"".equals(ds.getIsOnline()) && ds.getIsOnline()==1){
//							listdo.add(ds);
//						}
//						
//					}
//					page.setData(listdo);
//					return page;
//				}else if(d.getIsOnline() != null && !"".equals(d.getIsOnline()) && d.getIsOnline()==2){
//					for (Device ds : list) {
//						if(ds.getIsOnline() != null && !"".equals(ds.getIsOnline()) && ds.getIsOnline()==2){
//							listTo.add(ds);
//						}
//						
//					}
//					page.setData(listTo);
//					return page;
//				}
//					
//				
//				
//			}
			// 调用dao统计满足条件的记录总数
			rowCount = deviceDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Device>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	
	/**
	 * 根据查询条件，分页查询并返回Device的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Device> pageListDeviceByCar(Query q,Integer roleAdminTag) {
		PageFinder<Device> page = new PageFinder<Device>();
		List<Device> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			long onlineTimeThreshol = getOnlineTimeThreshol();
			Calendar calendar = Calendar.getInstance();
		     calendar.add(Calendar.MINUTE, -(int) (onlineTimeThreshol/60000));
		     Date  dt= calendar.getTime();
		     if(q.getQ() != null){
					Device  de= (Device)q.getQ();
					if(de.getIsOnline() != null && !"".equals(de.getIsOnline()) && de.getIsOnline()==1){
						de.setLastReportingTimeStart(dt);
						}
					if(de.getIsOnline() != null && !"".equals(de.getIsOnline()) && de.getIsOnline()==2){
						de.setLastReportingTimeEnd(dt);
					}
		     }
			list = deviceDao.pageListDeviceByCar(q);
			for(Device d:list){
				d.setRoleAdminTag(roleAdminTag);
				if(d.getLastReportingTime() != null){
					long timeDifference = new Date().getTime() - d.getLastReportingTime().getTime();
					if(onlineTimeThreshol >= timeDifference){
						d.setIsOnline(1);//在线
					}else{
						d.setIsOnline(2);//不在线
					}
				}
			}
			// 调用dao统计满足条件的记录总数
			rowCount = deviceDao.countByCar(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Device>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}
	
	
	
	/**
	 * 得到系统参数中的终端设备是否在线的阀值
	 * @return
	 */
	private long getOnlineTimeThreshol() {
		String paramValue = sysParamService.getParamValueByParamKey(DeviceConstant.device_online_time_threshol);
		if (paramValue != null && !paramValue.equals("")) {
			try {
				return Integer.parseInt(paramValue) * 60000;//将获取的分钟值转换为毫秒数
			} catch (NumberFormatException e) {
				log.error(e.getMessage(), e);
			}
		}
		return  120000;//默认两分钟,即120000毫秒
	}

	/**
	 * 根据主键，查询并返回一个Device对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Device getDevice(String id) {
		Device obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = deviceDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Device对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Device> getDeviceByIds(String[] ids) {
		List<Device> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = deviceDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Device>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的Device记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Device> delDevice(String id, Operator operator) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = deviceDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条Device记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param device
	 *            新增的Device数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Device> addDevice(Device device, Operator operator) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();

		if (device == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " device = " + device);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (device.getTerminalDeviceNo() == null) {
					device.setTerminalDeviceNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					device.setOperatorType(operator.getOperatorType());
					device.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				device.setCreateTime(now);
				device.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(device);

				// 调用Dao执行插入操作
				deviceDao.add(device);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(device);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 根据主键，更新一条Device记录
	 * 
	 * @param device
	 *            更新的Device数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Device> updateDevice(Device device, Operator operator) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();

		if (device == null || device.getTerminalDeviceNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " device = " + device);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					device.setOperatorType(operator.getOperatorType());
					device.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				device.setUpdateTime(new Date());
				//信号强度等级值(1、非常差 ，2、差，3、一般，4、好  5、非常好)(>=0 到 < 7  非常差 >=7 到 < 13 差 >=13 到 < 19 一般 >=19 到 < 25 好 >=25 到 <= 31  非常好)
				if(device.getSignalStrength()!=null){
					if(device.getSignalStrength()>=0&&device.getSignalStrength()<7){
						device.setSignalStrengthLevel(1);
					}else if(device.getSignalStrength()>=7&&device.getSignalStrength()<13){
						device.setSignalStrengthLevel(2);
					}else if(device.getSignalStrength()>=13&&device.getSignalStrength()<19){
						device.setSignalStrengthLevel(3);
					}else if(device.getSignalStrength()>=19&&device.getSignalStrength()<25){
						device.setSignalStrengthLevel(4);
					}else if(device.getSignalStrength()>=25&&device.getSignalStrength()<=31){
						device.setSignalStrengthLevel(5);
					}
				}
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = deviceDao.update(device);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(device);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return primarykeyService.getValueByBizType(PrimarykeyConstant.deviceType)+"";
	}

	/**
	 * 为Device对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Device obj) {
		if (obj != null) {
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(1);
			}
			if(obj.getDeviceStatus()==null){
				obj.setDeviceStatus(1);
			}
			if(obj.getVersionType()==null){
				obj.setVersionType("0");
			}
		}
	}

	@Override
	public ResultInfo<Device> importDeviceInfo(MultipartFile multipartFile, Operator operator) throws Exception {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		resultInfo.setCode(Constant.SECCUESS);
		Sheet[] sheet = null;
		jxl.Workbook rwb = null;
		Device device = new Device();
		try {
			/**
			 * 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
			 */
			if (operator != null) {
				device.setOperatorType(operator.getOperatorType());
				device.setOperatorId(operator.getOperatorId());
			}
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			try {
				String resImgPath = request.getSession().getServletContext().getRealPath("/");
				String filePath = resImgPath + "/xls/";
				File file = new File(filePath);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				String filenameReal = filePath + System.currentTimeMillis() + "device.xls";
				File logoSaveFile = new File(filenameReal);
				multipartFile.transferTo(logoSaveFile);

				FileInputStream fis = new FileInputStream(logoSaveFile);
				rwb = Workbook.getWorkbook(fis);
				sheet = rwb.getSheets();
			} catch (Exception e) {
				throw new XlsImportException("文件操作异常！");
			}
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				for (int j = 0; j < rs.getRows(); j++) {
					if (j == 0)
						continue;
					try {
						Cell[] cells = rs.getRow(j);

						String deviceSn = "";// 终端序列号
						String brandName = "";//设备品牌
						String deviceModel = "";//设备型号
						String macAddr = "";//MAC地址
						String simCardNo = "";// SIM卡号
						String cityName = "";// 城市名称
//						String versionNumber = "";//版本号
						

						String carPlateNo = "";// 车牌号
						String carModelName = "";// 车辆型号
						String carBrandName = "";// 车辆品牌
					    String carOwnerName;//车主名称
						

						deviceSn = cells[0].getContents().trim();
						brandName = cells[1].getContents().trim();
						deviceModel = cells[2].getContents().trim();
						macAddr = cells[3].getContents().trim();
						simCardNo = cells[4].getContents().trim();
						cityName = cells[5].getContents().trim();
//						versionNumber = cells[6].getContents().trim();
						
						if (deviceSn.trim().equals("") || brandName.trim().equals("")|| simCardNo.trim().equals("")
								|| deviceModel.trim().equals("") || macAddr.trim().equals("")|| simCardNo.trim().equals("")
								|| cityName.trim().equals("") ) {
							throw new XlsImportException("第" + (j + 1) + "行出错！ 缺失必填数据！");
						}
						//查询设备序列号在库中是否已存在
						Device d = getDeviceByDeviceSn(deviceSn);
						if(d!=null){
							throw new XlsImportException("第" + (j + 1) + "行出错！ 设备序列号已存在！");
						}else{
							// 数据准备
							//设置城市id
							List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
							for (DataDictItem item : cities) {
								if (cityName.contains(item.getItemValue())) {
									device.setCityId(item.getDataDictItemId());
								}
							}
							//设置品牌id
							List<DataDictItem> deviceBrands = dataDictItemService.getDataDictItemListByCatCode("DEVICE_BRAND");
							for (DataDictItem item : deviceBrands) {
								if (brandName.equals(item.getItemValue())) {
									device.setBrandId(item.getDataDictItemId());
								}
							}
							if(device.getBrandId()==null||"".equals(device.getBrandId())){
								throw new XlsImportException("第" + (j + 1) + "行出错！设备品牌不存在！");
							}
							//设置型号id
							List<DataDictItem> deviceModels = dataDictItemService.getDataDictItemListByCatCode("DEVICE_MODEL");
							for (DataDictItem item : deviceModels) {
								if (deviceModel.equals(item.getItemValue())) {
									device.setDeviceModelId(item.getDataDictItemId());
								}
							}
							if(device.getDeviceModelId()==null||"".equals(device.getDeviceModelId())){
								throw new XlsImportException("第" + (j + 1) + "行出错！设备型号不存在！");
							}
//						终端序列号	品牌	型号	MAC地址	SIM卡号	城市	版本号
							
							
							
							//判断 	MAC地址 重复
							Device dm = deviceDao.getDeviceMac(macAddr);
							if(dm != null){
								throw new XlsImportException("第" + (j + 1) + "行出错！Mac地址已存在！");
							}   
							//判断 	sim卡号地址 重复
							Device ds = deviceDao.getDeviceSim(simCardNo);
							if(ds != null){
								throw new XlsImportException("第" + (j + 1) + "行出错！SiM卡号已存在！");
							} 
							
							  String regex="^\\d+$";
							  if(simCardNo.matches(regex)==false){
								  throw new XlsImportException("第" + (j + 1) + "行出错！SiM卡号只能是数字！");
							  }
//							String a="/^[0-9]*$/";
//							if(simCardNo.matches(a)){
//								
//							}else{
//								throw new XlsImportException("第" + (j + 1) + "行出错！SiM卡号只能是数字！");
//							}
							
							device.setDeviceSn(deviceSn);
							device.setBrandName(brandName);
							device.setDeviceModel(deviceModel);
							device.setMacAddr(macAddr);
							device.setSimCardNo(simCardNo);
							device.setCityName(cityName);
//							device.setVersionNumber(versionNumber);
							//终端状态默认离线
							device.setDeviceStatus(4);
							device.setTerminalDeviceNo(this.generatePK());
							// 添加设备
							resultInfo = this.addDevice(device, operator);
						}

					} catch (Exception e) {
						if (e instanceof XlsImportException)
							throw new XlsImportException(((XlsImportException) e).getErrorMsg());
						else
							throw new XlsImportException("第" + (j + 1) + "行出错！数据有误！");
					}

				}

			}
			resultInfo.setData(device);
		} catch (Exception e) {
			if (e instanceof XlsImportException)
				throw new XlsImportException(((XlsImportException) e).getErrorMsg());
			else
				throw new XlsImportException("数据有误！");
		}
		return resultInfo;
	}

	@Override
	public String exportDeviceInfo() throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		String[] header = { "终端编号", "SIM卡号", "城市", "绑定车辆", "绑定时间", "终端状态", "信号强度" };
		list.add(header);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String resImgPath = request.getSession().getServletContext().getRealPath("/");
		String filePath = resImgPath + "/device/";
		File file = new File(filePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String filenameReal = filePath + System.currentTimeMillis() + "device.xls";
		String filename = JExcelUtil.exportExcel(list, filenameReal);
		return filename;
	}

	/**
	 * 根据设备序列号，查询并返回一个Device对象
	 * 
	 * @param deviceSn
	 *            设备序列号
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Device getDeviceByDeviceSn(String deviceSn) {
		Device obj = null;
		if (deviceSn == null || deviceSn.length() == 0) { // 传入的参数无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " deviceSn = " + deviceSn);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = deviceDao.getByDeviceSn(deviceSn);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}
	/**
	 * 根据设备序列号，查询并返回一个Device对象
	 * 
	 * @param deviceSn
	 *            设备序列号
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Device getDeviceByCarNo(String carNo) {
		Device obj = null;
		if (carNo == null || carNo.length() == 0) { // 传入的参数无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " caNo = " + carNo);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = deviceDao.getDeviceByCarNo(carNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	@Override
	public Device getDeviceCarPlateNo(String carPlateNo) {
		// TODO Auto-generated method stub
		return deviceDao.getDeviceCarPlateNo(carPlateNo);
	}

	@Override
	public int updateDeviceCar(Device d) {
		return deviceDao.updateDeviceCar(d);
		
	}
}
