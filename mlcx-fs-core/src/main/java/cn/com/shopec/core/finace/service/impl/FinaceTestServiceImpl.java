package cn.com.shopec.core.finace.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.FinaceTestDao;
import cn.com.shopec.core.finace.model.FinaceTest;
import cn.com.shopec.core.finace.service.FinaceTestService;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * FinaceTest 服务实现类
 */
@Service
public class FinaceTestServiceImpl implements FinaceTestService {

	private static final Log log = LogFactory.getLog(FinaceTestServiceImpl.class);
	
	@Resource
	private FinaceTestDao finaceTestDao;
	

	/**
	 * 根据查询条件，查询并返回FinaceTest的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FinaceTest> getFinaceTestList(Query q) {
		List<FinaceTest> list = null;
		try {
			//直接调用Dao方法进行查询
			list = finaceTestDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FinaceTest>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回FinaceTest的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<FinaceTest> getFinaceTestPagedList(Query q) {
		PageFinder<FinaceTest> page = new PageFinder<FinaceTest>();
		
		List<FinaceTest> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = finaceTestDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = finaceTestDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FinaceTest>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个FinaceTest对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public FinaceTest getFinaceTest(String id) {
		FinaceTest obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = finaceTestDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组FinaceTest对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FinaceTest> getFinaceTestByIds(String[] ids) {
		List<FinaceTest> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = finaceTestDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FinaceTest>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的FinaceTest记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<FinaceTest> delFinaceTest(String id, Operator operator) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = finaceTestDao.delete(id);
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
	 * 新增一条FinaceTest记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param finaceTest 新增的FinaceTest数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<FinaceTest> addFinaceTest(FinaceTest finaceTest, Operator operator) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		
		if (finaceTest == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " finaceTest = " + finaceTest);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (finaceTest.getId() == null) {
					finaceTest.setId(this.generatePK());
				}
				
				//设置创建时间和更新时间为当前时间
				
				//填充默认值
				this.fillDefaultValues(finaceTest);
				
				//调用Dao执行插入操作
				finaceTestDao.add(finaceTest);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(finaceTest);
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
	 * 根据主键，更新一条FinaceTest记录
	 * @param finaceTest 更新的FinaceTest数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<FinaceTest> updateFinaceTest(FinaceTest finaceTest, Operator operator) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		
		if (finaceTest == null || finaceTest.getId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " finaceTest = " + finaceTest);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				
				
				//设置更新时间为当前时间
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = finaceTestDao.update(finaceTest);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(finaceTest);
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
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为FinaceTest对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(FinaceTest obj) {
		if (obj != null) {
		}
	}

//	@Override
//	public ResultInfo<FinaceTest> importInvoice(MultipartFile multipartFile, Operator operator) {
//		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
//		Sheet[] sheet = null;
//		jxl.Workbook rwb = null;
//		try {
//			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//					.getRequest();
//			try {
//				String resImgPath = request.getSession().getServletContext().getRealPath("/");
//				String filePath = resImgPath + "/xls/";
//				File file = new File(filePath);
//				if (!file.exists() && !file.isDirectory()) {
//					file.mkdirs();
//				}
//				String filenameReal = filePath + System.currentTimeMillis() + "导入缓存文件.xls";
//
//				File logoSaveFile = new File(filenameReal);
//				multipartFile.transferTo(logoSaveFile);
//
//				FileInputStream fis = new FileInputStream(logoSaveFile);
//				rwb = Workbook.getWorkbook(fis);
//				sheet = rwb.getSheets();
//			} catch (Exception e) {
//				throw new XlsImportException("文件操作异常！");
//			}
//			for (int i = 0; i < sheet.length; i++) {
//				Sheet rs = rwb.getSheet(i);
//				for (int j = 0; j < rs.getRows(); j++) {
//					if (j >= 1){
//						try {
//							//微信订单号，商户订单号，交易类型，商品名称，创建时间(交易时间)，完成时间，对方账户，
//							//订单金额(总金额)，商家实收（总金额），退款批次号（微信退款单号），服务费（手续费），备注
//							//payflowNo,bizObjNo,bizObjType,productName,createTime,finishTime
//							//payMemberDetail,orderAmount,saleRealAmount,refundPayNo,serviceFee
//							//memo
//							Cell[] cells = rs.getRow(j);
//							String payflowNo="";
//							payflowNo=cells[5].getContents().trim().substring(1);
//							String bizObjNo = "";
//							bizObjNo=cells[6].getContents().trim().substring(1);
//							String bizObjType="";
//							bizObjType=cells[9].getContents().trim().substring(1);
//							
//							String productName = "";
//							productName=cells[20].getContents().trim().substring(1);
//							String createTime = "";
//							createTime=cells[0].getContents().trim().substring(1);
//							String finishTime = "";
//							finishTime=cells[0].getContents().trim().substring(1);
//							
//							
//							String payMemberDetail = "";
//							payMemberDetail=cells[7].getContents().trim().substring(1);
//							
//							String orderAmount = "";
//							orderAmount=cells[12].getContents().trim().substring(1);
//							String saleRealAmount = "";
//							saleRealAmount=cells[12].getContents().trim().substring(1);
//							String refundPayNo = "";
//							refundPayNo=cells[15].getContents().trim().substring(1);
//							String serviceFee = "";
//							serviceFee=cells[22].getContents().trim().substring(1);
//							
//							
//							FinaceTest ft=new FinaceTest();
//							ft.setBizObjNo(bizObjNo);
//							if(bizObjType.equals("SUCCESS")){
//								bizObjType="交易";
//							}else if(bizObjType.equals("REFUND")){
//								bizObjType="退款";
//								payflowNo=cells[14].getContents().trim().substring(1);
//								orderAmount=cells[16].getContents().trim().substring(1);
//								saleRealAmount=cells[16].getContents().trim().substring(1);
//							}
//							ft.setBizObjType(bizObjType);
//							Date createTime1=ECDateUtils.stringTimeToDateTime(createTime);
//							ft.setCreateTime(createTime1);
//							Date finishTime1=ECDateUtils.stringTimeToDateTime(finishTime);
//							ft.setFinishTime(finishTime1);
//							ft.setOrderAmount(Double.parseDouble(orderAmount));
//							ft.setPayflowNo(payflowNo);
//							ft.setPayflowNo(payflowNo);
//							ft.setPayMemberDetail(payMemberDetail);
//							ft.setProductName(productName);
//							ft.setRefundPayNo(refundPayNo);
//							ft.setSaleRealAmount(Double.parseDouble(saleRealAmount));
//							ft.setServiceFee(Double.parseDouble(serviceFee));
//							addFinaceTest(ft, null);
//						} catch (Exception e) {
//							e.printStackTrace();
//							resultInfo.setCode(Constant.FAIL);
//							if (e instanceof XlsImportException)
//								throw new XlsImportException(((XlsImportException) e).getErrorMsg());
//							else
//								throw new XlsImportException("第" + (j + 1) + "行出错！数据有误！");
//						}
//					}
//					
//				}
//
//			}
//			resultInfo.setData(null);
//			resultInfo.setCode(Constant.SECCUESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultInfo;
//	}
	@Override
	public ResultInfo<FinaceTest> importInvoice(MultipartFile multipartFile, Operator operator) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		Sheet[] sheet = null;
		jxl.Workbook rwb = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			try {
				String resImgPath = request.getSession().getServletContext().getRealPath("/");
				String filePath = resImgPath + "/xls/";
				File file = new File(filePath);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				String filenameReal = filePath + System.currentTimeMillis() + "导入缓存文件.xls";

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
					if (j >= 1){
						try {
							//支付宝交易号，商户订单号，业务类型，商品名称，创建时间，完成时间，对方账户，
							//订单金额，商家实收，退款批次号，服务费，备注
							//payflowNo,bizObjNo,bizObjType,productName,createTime,finishTime
							//payMemberDetail,orderAmount,saleRealAmount,refundPayNo,serviceFee
							//memo
							Cell[] cells = rs.getRow(j);
							String payflowNo="";
							payflowNo=cells[0].getContents().trim();
							String bizObjNo = "";
							bizObjNo=cells[1].getContents().trim();
							String bizObjType="";
							bizObjType=cells[2].getContents().trim();
							
							String productName = "";
							productName=cells[3].getContents().trim();
							String createTime = "";
							createTime=cells[4].getContents().trim();
							String finishTime = "";
							finishTime=cells[5].getContents().trim();
							
							
							String payMemberDetail = "";
							payMemberDetail=cells[10].getContents().trim();
							
							String orderAmount = "";
							orderAmount=cells[11].getContents().trim();
							String saleRealAmount = "";
							saleRealAmount=cells[12].getContents().trim();
							String refundPayNo = "";
							refundPayNo=cells[21].getContents().trim();
							String serviceFee = "";
							serviceFee=cells[22].getContents().trim();
							String memo = "";
							memo=cells[24].getContents().trim();
							
							FinaceTest ft=new FinaceTest();
							ft.setBizObjNo(bizObjNo);
							ft.setBizObjType(bizObjType);
							
							 if (cells[4].getType() == CellType.DATE) { //手动填写模板文件时为 date 类型，其他情况有可能不是date类型
								 DateCell dc = (DateCell) cells[4];
			                        Date date = dc.getDate();
			                        long time = (date.getTime() / 1000) - 60 * 60 * 8;
			                        date.setTime(time * 1000);
			                        SimpleDateFormat sdf = new SimpleDateFormat(
			                                "yyyy-MM-dd HH:mm:ss");
			                        String sDate = sdf.format(date);
//			                        nextLine[j] = sDate;
			                        createTime = sDate;
			                        Date createTime1=ECDateUtils.stringTimeToDateTime(createTime);
									ft.setCreateTime(createTime1);
			                    } 
							 if (cells[5].getType() == CellType.DATE) { //手动填写模板文件时为 date 类型，其他情况有可能不是date类型
			                        DateCell dc = (DateCell) cells[5];
			                        Date date = dc.getDate();
			                        long time = (date.getTime() / 1000) - 60 * 60 * 8;
			                        date.setTime(time * 1000);
			                        SimpleDateFormat sdf = new SimpleDateFormat(
			                                "yyyy-MM-dd HH:mm:ss");
			                        String sDate = sdf.format(date);
//			                        nextLine[j] = sDate;
			                        finishTime = sDate;
			                        Date finishTime1=ECDateUtils.stringTimeToDateTime(finishTime);
									ft.setFinishTime(finishTime1);
			                    }
							ft.setMemo(memo);
							ft.setOrderAmount(Double.parseDouble(orderAmount));
							ft.setPayflowNo(payflowNo);
							ft.setPayflowNo(payflowNo);
							ft.setPayMemberDetail(payMemberDetail);
							ft.setProductName(productName);
							ft.setRefundPayNo(refundPayNo);
							ft.setSaleRealAmount(Double.parseDouble(saleRealAmount));
							ft.setServiceFee(Double.parseDouble(serviceFee));
							addFinaceTest(ft, null);
						} catch (Exception e) {
							e.printStackTrace();
							resultInfo.setCode(Constant.FAIL);
							if (e instanceof XlsImportException)
								throw new XlsImportException(((XlsImportException) e).getErrorMsg());
							else
								throw new XlsImportException("第" + (j + 1) + "行出错！数据有误！");
						}
					}
					
				}

			}
			resultInfo.setData(null);
			resultInfo.setCode(Constant.SECCUESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<FinaceTest> addFinaceTests(List<FinaceTest> finaceTests) {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		if (finaceTests == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS );
			log.info(Constant.ERR_MSG_INVALID_ARG + " finaceTest = " + finaceTests);
		} else {
			for (FinaceTest finaceTest : finaceTests) {
				//忽略保存已经存在的流水单号的数据
				if(finaceTestDao.getByFlowNo(finaceTest.getPayflowNo()) != null){
					continue;
				}
				resultInfo = addFinaceTest(finaceTest, null);
				if(Constant.FAIL.equals(resultInfo.getCode())){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultInfo;
				}
			}
			resultInfo.setCode(Constant.SECCUESS);
		}
		return resultInfo;
	}
	/**
	 * 将读取的支付宝账单文件信息转换成finaceTest类集合
	 */
	public List<FinaceTest> getFinaceTestFromAlipayData(List<String> dataList){
		if (dataList == null || dataList.size() <= 9) {
			// 忽略开始的标题行,账号行,起始终止时间行,分割线行,数据的行头共5行;结束的分割线行,交易合计行,退款合计行,导出时间行共4行
			return null;
		}
		int dataBeginIndex = 5, dataEndIndex = dataList.size() - 4;// 实体数据起始行和结束行
		List<FinaceTest> finaceTests = new ArrayList<FinaceTest>();
		for (int i = dataBeginIndex; i < dataEndIndex; i++) {
			String[] data = dataList.get(i).split(",");
			FinaceTest finaceTest = new FinaceTest();
			finaceTest.setPayflowNo(data[0].trim());
			finaceTest.setBizObjNo(data[1].trim());
			finaceTest.setBizObjType(data[2].trim());
			finaceTest.setProductName(data[3].trim());
			finaceTest.setCreateTime(ECDateUtils.parseDate(data[4].trim(), ECDateUtils.Format_DateTime));
			finaceTest.setFinishTime(ECDateUtils.parseDate(data[5].trim(), ECDateUtils.Format_DateTime));
			// 忽略门店编号,门店名称,操作员,终端号
			finaceTest.setPayMemberDetail(data[10].trim());
			finaceTest.setOrderAmount(Double.valueOf(data[11].trim()));
			finaceTest.setSaleRealAmount(Double.valueOf(data[12].trim()));
			// 忽略支付宝红包,集分宝,支付宝优惠,商家优惠,券核销金额,券名称,商家红包消费金额,卡消费金额
			finaceTest.setRefundPayNo(data[21].trim());
			finaceTest.setServiceFee(Double.valueOf(data[22].trim()));
			// 忽略分润
			finaceTest.setMemo(data[24].trim());
			finaceTest.setPayMethod("alipay");
			finaceTests.add(finaceTest);
		}
		return finaceTests;
	}
	
	/**
	  * 将读取的微信成功订单账单文件信息转换成finaceTest类集合
	 */
	public List<FinaceTest> getFinaceTestFromWeChart(String dataString, String time) {
		if (dataString == null || dataString.equals("") || time == null || time.equals("")) {
			return null;
		}
		int titleEndIndex = dataString.indexOf("`");
		if (titleEndIndex != -1) {
			dataString = dataString.substring(titleEndIndex, dataString.length());
		}
		String title = "总交易单数,总交易额,总退款金额,总企业红包退款金额,手续费总金额";
		titleEndIndex = dataString.indexOf(title);
		if (titleEndIndex != -1) {
			dataString = dataString.substring(0, titleEndIndex);
		}

		dataString = dataString.substring(1).replaceAll("`" + time, "\r\n" + time);
		String[] rowDatas = dataString.split("\r\n");
		List<FinaceTest> finaceTests = new ArrayList<FinaceTest>();
		//交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,商品名称,商户数据包,手续费,费率
		for (int i = 0; i < rowDatas.length; i++) {
			String[] cells = rowDatas[i].split(",`");
			FinaceTest finaceTest = new FinaceTest();
			finaceTest.setCreateTime(ECDateUtils.parseDate(cells[0].trim(), ECDateUtils.Format_DateTime));
			finaceTest.setFinishTime(ECDateUtils.parseDate(cells[0].trim(), ECDateUtils.Format_DateTime));
			finaceTest.setPayflowNo(cells[5].trim());
			finaceTest.setBizObjNo(cells[6].trim());
			finaceTest.setPayMemberDetail(cells[7].trim());
			if(cells[9].trim().equals("SUCCESS")){
				finaceTest.setBizObjType("交易");
				finaceTest.setOrderAmount(Double.valueOf(cells[12].trim()));//订单金额
				finaceTest.setSaleRealAmount(Double.valueOf(cells[12].trim()));
			}else {//REFUND,RECHARGE_REFUND
				finaceTest.setBizObjType("退款");
				finaceTest.setOrderAmount(Double.valueOf("-" + cells[16].trim()));//退款金额
				finaceTest.setSaleRealAmount(Double.valueOf("-" + cells[16].trim()));
			}
			finaceTest.setProductName(cells[14].trim());
			finaceTest.setServiceFee(Double.valueOf(cells[16].trim()));
			finaceTest.setPayMethod("weixin");
			// finaceTest.setRefundPayNo;
			// finaceTest.setMemo();
			finaceTests.add(finaceTest);
		}
		return finaceTests;
	}

	@Override
	public ResultInfo<FinaceTest> importTransferOfAccount(MultipartFile multipartFile) throws Exception {
		ResultInfo<FinaceTest> resultInfo = new ResultInfo<FinaceTest>();
		resultInfo.setCode(Constant.SECCUESS);
		Sheet[] sheet = null;
		jxl.Workbook rwb = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			try {
				String resImgPath = request.getSession().getServletContext().getRealPath("/");
				String filePath = resImgPath + "/xls/";
				File file = new File(filePath);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				String filenameReal = filePath + System.currentTimeMillis() + "finaceTest.xls";
				File saveFile = new File(filenameReal);
				multipartFile.transferTo(saveFile);

				FileInputStream fis = new FileInputStream(saveFile);
				rwb = Workbook.getWorkbook(fis);
				sheet = rwb.getSheets();
			} catch (Exception e) {
				throw new XlsImportException("文件操作异常！");
			}
	        Sheet rs = rwb.getSheet(0);
	        
			for (int j = 0; j < rs.getRows(); j++) {
				if (j >= 1){
					try {
						//支付宝交易号，商户订单号，业务类型，商品名称，创建时间，完成时间，对方账户，
						//订单金额)，商家实收(支出金额），退款批次号（无），服务费（无），备注
						//payflowNo,bizObjNo,bizObjType,productName,createTime,finishTime
						//payMemberDetail,orderAmount,saleRealAmount,refundPayNo,serviceFee
						//memo
						Cell[] cells = rs.getRow(j);
						String payflowNo="";
						payflowNo=cells[0].getContents().trim();
						
						if(payflowNo == null || payflowNo.equals("")) {
							break;
						}
						
						FinaceTest finaceTest = finaceTestDao.getByFlowNo(payflowNo);
						
						if(finaceTest != null){
							continue;
						}
						
						String bizObjNo = "";
						bizObjNo=cells[1].getContents().trim();
						String bizObjType="";
						bizObjType=cells[2].getContents().trim();
						
						String productName = "";
						String createTime = "";
						createTime=cells[4].getContents().trim();
						String finishTime = "";
						finishTime=cells[5].getContents().trim();
						
						
						String payMemberDetail = "";
						payMemberDetail=cells[6].getContents().trim();
						
						String orderAmount = "";
						orderAmount=cells[7].getContents().trim();
						String saleRealAmount = "";
						saleRealAmount=cells[8].getContents().trim();
//						String refundPayNo = "";
//						refundPayNo=cells[21].getContents().trim();
						String serviceFee = "0";
						String memo = "";
						
						//memo=cells[11].getContents().trim();
						
						FinaceTest ft=new FinaceTest();
						ft.setBizObjNo(bizObjNo);
						ft.setBizObjType(bizObjType);
						ft.setPayMethod("alipay"); 
						
						 if (cells[4].getType() == CellType.DATE) { //手动填写模板文件时为 date 类型，其他情况有可能不是date类型
							 DateCell dc = (DateCell) cells[4];
		                        Date date = dc.getDate();
		                        long time = (date.getTime() / 1000) - 60 * 60 * 8;
		                        date.setTime(time * 1000);
		                        SimpleDateFormat sdf = new SimpleDateFormat(
		                                "yyyy-MM-dd HH:mm:ss");
		                        String sDate = sdf.format(date);
//		                        nextLine[j] = sDate;
		                        createTime = sDate;
		                        Date createTime1=ECDateUtils.stringTimeToDateTime(createTime);
								ft.setCreateTime(createTime1);
		                    } 
						 if (cells[5].getType() == CellType.DATE) { //手动填写模板文件时为 date 类型，其他情况有可能不是date类型
		                        DateCell dc = (DateCell) cells[5];
		                        Date date = dc.getDate();
		                        long time = (date.getTime() / 1000) - 60 * 60 * 8;
		                        date.setTime(time * 1000);
		                        SimpleDateFormat sdf = new SimpleDateFormat(
		                                "yyyy-MM-dd HH:mm:ss");
		                        String sDate = sdf.format(date);
//		                        nextLine[j] = sDate;
		                        finishTime = sDate;
		                        Date finishTime1=ECDateUtils.stringTimeToDateTime(finishTime);
								ft.setFinishTime(finishTime1);
		                    }
						ft.setMemo(memo);
						ft.setOrderAmount(Double.parseDouble(orderAmount));
						ft.setPayflowNo(payflowNo);
						//ft.setPayflowNo(payflowNo);
						ft.setPayMemberDetail(payMemberDetail);
						ft.setProductName(productName);
						//ft.setRefundPayNo(refundPayNo);
						ft.setSaleRealAmount(Double.parseDouble(saleRealAmount));
						ft.setServiceFee(Double.parseDouble(serviceFee));
						addFinaceTest(ft, null);
					} catch (Exception e) {
						e.printStackTrace();
//						resultInfo.setCode(Constant.FAIL);
						if (e instanceof XlsImportException)
							throw new XlsImportException(((XlsImportException) e).getErrorMsg());
						else
							throw new XlsImportException("第" + (j + 1) + "行出错！数据有误！");
					}
				}
				
			}
		} catch (Exception e) {
			if (e instanceof XlsImportException)
				throw new XlsImportException(((XlsImportException) e).getErrorMsg());
			else
				throw new XlsImportException("数据有误！");
		}
		return resultInfo;
	}
}
