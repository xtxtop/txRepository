package cn.com.shopec.core.member.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.CompanyPersonDao;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.CompanyPersonService;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 集团人员名单表 服务实现类
 */
@Service
public class CompanyPersonServiceImpl implements CompanyPersonService {

	private static final Log log = LogFactory.getLog(CompanyPersonServiceImpl.class);
	
	@Resource
	private CompanyPersonDao companyPersonDao;
	
	@Resource
	private DataDictItemService dataDictItemService;
	
	@Resource
	private CompanyService companyService;
	
	@Resource
	private MemberService memberService;
	
	/**
	 * 根据查询条件，查询并返回CompanyPerson的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CompanyPerson> getCompanyPersonList(Query q) {
		List<CompanyPerson> list = null;
		try {
			//已删除的不查出
			CompanyPerson companyPerson = (CompanyPerson)q.getQ();
			if (companyPerson != null) {
				companyPerson.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = companyPersonDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CompanyPerson>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CompanyPerson的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CompanyPerson> getCompanyPersonPagedList(Query q) {
		PageFinder<CompanyPerson> page = new PageFinder<CompanyPerson>();
		
		List<CompanyPerson> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			CompanyPerson companyPerson = (CompanyPerson)q.getQ();
			if (companyPerson != null) {
				companyPerson.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = companyPersonDao.pageList(q);
			for(CompanyPerson c:list){
				Member member = memberService.getMemberByPhone(c.getMobilePhone());
				if(member!=null){
					c.setRegisterStatus(1);
				}
			}
			//调用dao统计满足条件的记录总数
			rowCount = companyPersonDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CompanyPerson>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CompanyPerson对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CompanyPerson getCompanyPerson(String id) {
		CompanyPerson obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = companyPersonDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CompanyPerson对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CompanyPerson> getCompanyPersonByIds(String[] ids) {
		List<CompanyPerson> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = companyPersonDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CompanyPerson>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CompanyPerson记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CompanyPerson> delCompanyPerson(String id, Operator operator) {
		ResultInfo<CompanyPerson> resultInfo = new ResultInfo<CompanyPerson>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			CompanyPerson companyPerson = new CompanyPerson();
			companyPerson.setId(id);
			companyPerson.setIsDeleted(Constant.DEL_STATE_YES);
			companyPerson.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				companyPerson.setOperatorType(operator.getOperatorType());
				companyPerson.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = companyPersonDao.update(companyPerson);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(companyPerson);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条CompanyPerson记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param companyPerson 新增的CompanyPerson数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CompanyPerson> addCompanyPerson(CompanyPerson companyPerson, Operator operator) {
		ResultInfo<CompanyPerson> resultInfo = new ResultInfo<CompanyPerson>();
		
		if (companyPerson == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " companyPerson = " + companyPerson);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (companyPerson.getId() == null) {
					companyPerson.setId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					companyPerson.setOperatorType(operator.getOperatorType());
					companyPerson.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				companyPerson.setCreateTime(now);
				companyPerson.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(companyPerson);
				
				//调用Dao执行插入操作
				companyPersonDao.add(companyPerson);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(companyPerson);
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
	 * 根据主键，更新一条CompanyPerson记录
	 * @param companyPerson 更新的CompanyPerson数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CompanyPerson> updateCompanyPerson(CompanyPerson companyPerson, Operator operator) {
		ResultInfo<CompanyPerson> resultInfo = new ResultInfo<CompanyPerson>();
		
		if (companyPerson == null || companyPerson.getId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " companyPerson = " + companyPerson);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					companyPerson.setOperatorType(operator.getOperatorType());
					companyPerson.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				companyPerson.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = companyPersonDao.update(companyPerson);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(companyPerson);
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
	 * 为CompanyPerson对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CompanyPerson obj) {
		if (obj != null) {
		    if (obj.getRegisterStatus() == null) {
		    	obj.setRegisterStatus(0);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

	@Override
	public ResultInfo<CompanyPerson> importCompanyPersonInfo(MultipartFile multipartFile, Operator operator)throws Exception  {
		ResultInfo<CompanyPerson> resultInfo = new ResultInfo<CompanyPerson>();
		resultInfo.setCode(Constant.SECCUESS);
		Sheet[] sheet = null;
		jxl.Workbook rwb = null;
		CompanyPerson companyPerson = new CompanyPerson();
		try {
			/**
			 * 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
			 */
			if (operator != null) {
				companyPerson.setOperatorType(operator.getOperatorType());
				companyPerson.setOperatorId(operator.getOperatorId());
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
				String filenameReal = filePath + System.currentTimeMillis() + "compamyPerson.xls";
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
				for (int j = 0; j < rs.getRows()-1; j++) {
					if (j ==0)
						continue;
					try {
						Cell[] cells = rs.getRow(j);
						String companyName = "";// 集团名称
						String name = "";// 姓名
						String personType = "";// 员工类型
						String mobilePhone = "";// 手机号

						companyName = cells[0].getContents().trim();
						name = cells[1].getContents().trim();
						personType = cells[2].getContents().trim();
						mobilePhone = cells[3].getContents().trim();
						String regex = "^1[3|4|5|7|8][0-9]\\d{4,8}$";
				        if(mobilePhone.length() != 11){
				        	resultInfo.setCode(Constant.FAIL);
				        	resultInfo.setMsg("手机号应为11位数");
				            return resultInfo;
				        }else{
				            Pattern p = Pattern.compile(regex);
				            Matcher m = p.matcher(mobilePhone);
				            boolean isMatch = m.matches();
				            if(isMatch){
				            	resultInfo.setCode(Constant.SECCUESS);
				            } else {
				            	resultInfo.setCode(Constant.FAIL);
					        	resultInfo.setMsg("手机号格式不正确");
				                return resultInfo;
				            }
				        }
						
						if (companyName.trim().equals("")
								|| name.trim().equals("") 
								|| personType.trim().equals("") || mobilePhone.trim().equals("")
								) {
							throw new XlsImportException("第" + (j + 1) + "行出错！ 缺失必填数据！");
						}
//						// 数据准备
						Company company = new Company();
						company.setCompanyName(companyName);
						List<Company> lists = companyService.getCompanyList(new Query(company));
						if(lists!=null&&lists.size()>0){
							companyPerson.setCompanyId(lists.get(0).getCompanyId());
							companyPerson.setCityId(lists.get(0).getCityId());
							companyPerson.setCityName(lists.get(0).getCityName());
						}else{
							throw new XlsImportException("第" + (j + 1) + "行出错！ 集团名称不存在！");
						}
						CompanyPerson cp = new CompanyPerson();
						cp.setIsDeleted(0);
						cp.setMobilePhone(mobilePhone);
						List<CompanyPerson> cpLists = companyPersonDao.pageList(new Query(cp));
						if(cpLists!=null&&cpLists.size()>0){
							throw new XlsImportException("第" + (j + 1) + "行出错！ 手机号重复！");
						}
						companyPerson.setCompanyName(companyName);
						companyPerson.setName(name);
						companyPerson.setMobilePhone(mobilePhone);
						Date date = new Date();
						companyPerson.setImportTime(date);
						Integer pType = 0;//1-员工，0-非员工
						if (personType.equals("员工")) {
							pType = 1;
						} else if (personType.equals("非员工")) {
							pType = 0;
						}
						companyPerson.setPersonType(pType);
						Integer rStatus = 0;// 0、未注册，1、已注册
						Member member = memberService.getMemberByPhone(mobilePhone);
						if(member!=null){
							rStatus=1;
							companyPerson.setRegisterStatus(1);
							companyPerson.setMemberNo(member.getMemberNo());
							//会员表修改类型和集团id
							member.setMemberType(2);//集团类型
							member.setCompanyId(companyPerson.getCompanyId());
							memberService.updateMember(member, operator);
						}else{
								rStatus = 0;
						}
						companyPerson.setRegisterStatus(rStatus);
						companyPerson.setId(this.generatePK());
						// 添加集团员工
						resultInfo = addCompanyPerson(companyPerson, operator);

					} catch (Exception e) {
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


	@Override
	public String exportCompanyPerson()throws Exception  {
		List<String[]> list = new ArrayList<String[]>();
		String[] header = { "城市", "集团名称", "姓名", "类型", "手机号", "注册情况"};
		list.add(header);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String resImgPath = request.getSession().getServletContext().getRealPath("/");
		String filePath = resImgPath + "/companyPerson/";
		File file = new File(filePath);
		if (!file.exists() && !file.isDirectory()){
			file.mkdirs();
		}
		String filenameReal = filePath + System.currentTimeMillis() + "companyPerson.xls";
		String filename = JExcelUtil.exportExcel(list, filenameReal);
		return filename;
	}

	@Override
	public CompanyPerson getCompanyPersonPhone(String mobilePhone) {
		// TODO Auto-generated method stub3
		 
		CompanyPerson obj = null;
		if (mobilePhone == null || mobilePhone.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " mobilePhone = " + mobilePhone);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = companyPersonDao.getCompanyPerson(mobilePhone); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
}
