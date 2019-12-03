package cn.com.shopec.mgt.company.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.CompanyPersonService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 集团人员管理
 * 
 * @author wangming
 *
 */
@Controller
@RequestMapping("/companyPerson")
public class CompanyPersonController extends BaseController {

	@Resource
	private CompanyPersonService companyPersonService;

	@Resource
	private MemberService memberService;
	/**
	 * 进入集团人员列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toCompanyPersonList")
	public String toCompanyPersonList() {
		return "company/company_person_list";
	}

	/**
	 * 查询集团人员列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("pageListCompanyPerson")
	@ResponseBody
	public PageFinder<CompanyPerson> pageListCompanyPerson(@ModelAttribute("companyPerson") CompanyPerson companyPerson,@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws ParseException {
		companyPerson.setIsDeleted(0);
		Query q = new Query(pageNo, pageSize, companyPerson);
		return companyPersonService.getCompanyPersonPagedList(q);
	}

	/**
	 * 集团人员列表详情
	 * 
	 * @param companyPersonNo
	 * @return
	 */
	@RequestMapping("toCompanyPersonView")
	public String toCompanyPersonView(@ModelAttribute("companyPersonNo") String companyPersonNo, Model model) {
		CompanyPerson  companyPerson= companyPersonService.getCompanyPerson(companyPersonNo);
		Member member = memberService.getMemberByPhone(companyPerson.getMobilePhone());
		if(member!=null){
			companyPerson.setRegisterStatus(1);
		}
		model.addAttribute("companyPerson", companyPerson);
		return "company/company_person_view";
	}
	/**
	 * 批量导入集团人员信息
	 * @param file
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("importCompanyPerson")
	@ResponseBody
	public ResultInfo<CompanyPerson> importCompanyPerson(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		ResultInfo<CompanyPerson> resultInfo = new  ResultInfo<CompanyPerson>();
		if(file != null){
			try {
				resultInfo = companyPersonService.importCompanyPersonInfo(file, getOperator());
			} catch (XlsImportException e) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(e.getErrorMsg());
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择文件！");
		}
		
		return resultInfo;
	}
	/**
	 * 设备信息模板导出
	 * @param ids
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("exportCompanyPerson")
	public ResponseEntity<byte[]> exportCompanyPerson(Long[] ids, HttpServletRequest request) throws Exception {
		String filename = companyPersonService.exportCompanyPerson();
        File file = new File(filename);  
		byte[] bytes = FileUtils.readFileToByteArray(file);  
        if (file.exists()) {  
            file.delete();  
        }
        HttpHeaders headers = new HttpHeaders();    
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
        headers.setContentDispositionFormData("attachment", new String("companyPerson.xls".getBytes("UTF-8"), "ISO8859-1"));  //解决文件名中文乱码问题  
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);  
	}
	
	
	//导出模板
		@RequestMapping("downloadExcelFile")
		public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response, String filepath,
				String newFileName) throws Exception {
			response.setContentType("multipart/form-data");
			// response.setContentType(request.getSession().getServletContext().getMimeType(newFileName));
			response.setCharacterEncoding("UTF-8");
			// 设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename=" + newFileName);
			// 读取目标文件，通过response将目标文件写到客户端
			String path=request.getRealPath("/")+"download"+File.separator+"companyPerson.xls";
			File ff=new File(path);
		
			InputStream in = new FileInputStream(ff);
			
			OutputStream out = response.getOutputStream();
			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			out.close();
		}
	
	
	/**
	 * 删除集团人员
	 * @param companyPersonNo
	 * @return
	 */
	@RequestMapping("/deleteCompanyPerson")
    @ResponseBody
    public ResultInfo<CompanyPerson> deleteCompanyPerson(String companyPersonNo){
	   	 ResultInfo<CompanyPerson> resultInfo=new ResultInfo<CompanyPerson>();
	   	 CompanyPerson companyPerson = new CompanyPerson();
	   	 companyPerson.setId(companyPersonNo);
	   	 companyPerson.setIsDeleted(1);
	   	 CompanyPerson cP=companyPersonService.getCompanyPerson(companyPersonNo);
	   	 if(cP!=null){
	   		 if(cP.getMobilePhone()!=null&&!cP.getMobilePhone().equals("")){
	   			 //查找会员列表是否存在对应会员，存在则修改会员类型
	   			 Member member=memberService.getMemberByPhone(cP.getMobilePhone());
	   		   	 if(member!=null){
	   		   		 member.setMemberType(1);//普通会员
	   		   		 member.setCompanyId("");
	   		   		 memberService.updateMember(member, getOperator());
	   		   	 }
	   		 }
	   	 }
	   	 resultInfo = companyPersonService.updateCompanyPerson(companyPerson, null);
   	
		 return resultInfo;
		 
    }
}
