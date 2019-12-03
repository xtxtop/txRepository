package cn.com.shopec.core.finace.service.impl;

import java.io.File;
import java.io.FileInputStream;
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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.InvoiceDao;
import cn.com.shopec.core.finace.model.Invoice;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.order.dao.OrderDao;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 发票申请表 服务实现类
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

	private static final Log log = LogFactory.getLog(InvoiceServiceImpl.class);
	
	@Resource
	private InvoiceDao invoiceDao;
	@Resource
	private OrderDao orderDao;
	

	/**
	 * 根据查询条件，查询并返回Invoice的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Invoice> getInvoiceList(Query q) {
		List<Invoice> list = null;
		try {
			//直接调用Dao方法进行查询
			list = invoiceDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Invoice>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回Invoice的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Invoice> getInvoicePagedList(Query q) {
		PageFinder<Invoice> page = new PageFinder<Invoice>();
		
		List<Invoice> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = invoiceDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = invoiceDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Invoice>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个Invoice对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Invoice getInvoice(String id) {
		Invoice obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = invoiceDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Invoice对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Invoice> getInvoiceByIds(String[] ids) {
		List<Invoice> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = invoiceDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Invoice>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的Invoice记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Invoice> delInvoice(String id, Operator operator) {
		ResultInfo<Invoice> resultInfo = new ResultInfo<Invoice>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = invoiceDao.delete(id);
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
	 * 新增一条Invoice记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param invoice 新增的Invoice数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Invoice> addInvoice(Invoice invoice, Operator operator) {
		ResultInfo<Invoice> resultInfo = new ResultInfo<Invoice>();
		
		if (invoice == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " invoice = " + invoice);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (invoice.getInvoiceId() == null) {
					invoice.setInvoiceId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					invoice.setOperatorType(operator.getOperatorType());
					invoice.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				invoice.setCreateTime(now);
				invoice.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(invoice);
				
				//调用Dao执行插入操作
				invoiceDao.add(invoice);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(invoice);
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
	 * 根据主键，更新一条Invoice记录
	 * @param invoice 更新的Invoice数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Invoice> updateInvoice(Invoice invoice, Operator operator) {
		ResultInfo<Invoice> resultInfo = new ResultInfo<Invoice>();
		
		if (invoice == null || invoice.getInvoiceId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " invoice = " + invoice);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					invoice.setOperatorType(operator.getOperatorType());
					invoice.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				invoice.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = invoiceDao.update(invoice);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(invoice);
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
	 * 为Invoice对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Invoice obj) {
		if (obj != null) {
		}
	}

	@Override
	public List<Invoice> getInvoiceListByMemberMonth(Query q) {
		List<Invoice> list = null;
		try {
			//直接调用Dao方法进行查询
			list = invoiceDao.getInvoiceListByMemberMonth(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Invoice>(0) : list;
		return list; 
		
	}

	@Override
	public PageFinder<Invoice> getMoreMonthMyInvoice(Query q) {
		PageFinder<Invoice> page = new PageFinder<Invoice>();
		
		List<Invoice> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = invoiceDao.getMoreMonthMyInvoice(q);
			//调用dao统计满足条件的记录总数
			rowCount = invoiceDao.countMoreInvoice(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Invoice>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}

	@Override
	public ResultInfo<Invoice> importInvoice(MultipartFile multipartFile, Operator operator)throws Exception {
		ResultInfo<Invoice> resultInfo = new ResultInfo<Invoice>();
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
							//开票抬头，开票类型，开票金额，订单号，订单时间，开票时间，开票状态，发票号，纳税人认定通知书
							//invoiceTitle,invoiceType,invoiceAmount,bizObjId
							//orderTime,invoiceTime,invoiceStatus,invoiceNo,
							//taxpayerNoticePic
							Cell[] cells = rs.getRow(j);
							String invoiceId="";
							invoiceId=cells[5].getContents().trim();
							String invoiceNo = "";
							invoiceNo=cells[6].getContents().trim();
							String taxpayerNo="";
							taxpayerNo=cells[9].getContents().trim();
							String accountBank = "";
							accountBank=cells[10].getContents().trim();
							String accountNo = "";
							accountNo=cells[11].getContents().trim();
							String fax = "";
							fax=cells[14].getContents().trim();
							String postcode = "";
							postcode=cells[15].getContents().trim();
							//是否已开过发票
							Invoice inA=getInvoice(invoiceId);
							if(inA!=null&&inA.getInvoiceStatus()==1){
//								resultInfo.setCode(Constant.FAIL);
//								resultInfo.setMsg("发票编号为:"+invoiceId+"已开过发票！");
//								return resultInfo;
							}else{
							inA.setInvoiceId(invoiceId);
							inA.setInvoiceNo(invoiceNo);
							inA.setTaxpayerNo(taxpayerNo);
							inA.setAccountBank(accountBank);
							inA.setAccountNo(accountNo);
							inA.setFax(fax);
							inA.setPostcode(postcode);
							if(invoiceNo!=null&&!invoiceNo.equals("")){
								inA.setInvoiceStatus(1);
							}
							inA.setInvoiceOperatorId(operator.getOperatorId());
							inA.setInvoiceOperatorName(operator.getOperatorUserName());
							inA.setInvoiceTime(new Date());
							if(invoiceNo!=null&&!invoiceNo.equals("")){
							updateInvoice(inA, operator);
							}
							}
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
			if (e instanceof XlsImportException)
				throw new XlsImportException(((XlsImportException) e).getErrorMsg());
			else
				throw new XlsImportException("数据有误！");
		}
		return resultInfo;
	}

	@Override
	public List<Invoice> getInvoiceByOrderNo(String orderNo) {
		List<Invoice> list=invoiceDao.getInvoiceByOrderNo(orderNo);
		return list;
	}

	@Override
	public Long count(Query q) {
		return invoiceDao.count(q);
	}

	@Override
	public Invoice getInvoiceDetailByOrderNo(String orderNo) {
		return invoiceDao.getInvoiceDetailByOrderNo(orderNo);
	}


}
