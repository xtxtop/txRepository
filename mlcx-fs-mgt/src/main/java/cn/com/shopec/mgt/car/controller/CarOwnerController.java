package cn.com.shopec.mgt.car.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.core.car.model.CarOwner;
import cn.com.shopec.core.car.service.CarOwnerService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/carOwner")
public class CarOwnerController extends BaseController{
	@Resource
	public CarOwnerService carOwnerService;
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/*
	 * 进入车主列表页面
	 */
	@RequestMapping("/toCarOwnerList")
	public String mainPage() {
		return "car/car_owner_list";
	}
	/*
	 * 显示车主列表信息
	 */
	@RequestMapping("/pageListCarOwner")
	@ResponseBody
	public PageFinder<CarOwner> pageListCar(@ModelAttribute("carOwner") CarOwner carOwner,Query query) {
		carOwner.setIsAvailable(1);
		Query q = new Query(query.getPageNo(),query.getPageSize(),carOwner);
		return carOwnerService.getCarOwnerPagedList(q);
	}
	/*
	 * 进入新增车主页
	 */
	@RequestMapping("/toCarOwnerAdd")
	public String toCarOwnerAdd() {
		return "car/car_owner_add";
	}
	/*
	 * 新增车主提交
	 */
	@RequestMapping("/addCarOwner")
	@ResponseBody
	public ResultInfo<CarOwner> addCarOwner(@ModelAttribute("carOwner") CarOwner carOwner) {
		if(carOwner.getContactPerson()!=null&&!carOwner.getContactPerson().equals("")){
			carOwner.setContactPerson(carOwner.getContactPerson().replace(" ", ""));
		}
		if(carOwner.getIdCardNo()!=null&&!carOwner.getIdCardNo().equals("")){
			carOwner.setIdCardNo(carOwner.getIdCardNo().replace(" ", ""));
		}
		return  carOwnerService.addCarOwner(carOwner, getOperator());
	}
	/*
	 * 进入车主详情页
	 */
	@RequestMapping("/toCarOwnerView")
	public String toCarOwnerView(String ownerId,ModelMap modelMap) {
		CarOwner carOwner=carOwnerService.getCarOwner(ownerId);
		modelMap.put("carOwner", carOwner);
		return "car/car_owner_view";
	}
	/*
	 * 进入车主信息修改页面
	 */
	@RequestMapping("/toCarOwnerEdit")
	public String toCarOwnerEdit(String ownerId,ModelMap modelMap) {
		CarOwner carOwner=carOwnerService.getCarOwner(ownerId);
		modelMap.put("carOwner", carOwner);
		return "car/car_owner_edit";
	}
	/*
	 * 车主信息修改提交
	 */
	@RequestMapping("/editCarOwner")
	@ResponseBody
	public ResultInfo<CarOwner> editCarOwner(@ModelAttribute("carOwner") CarOwner carOwner) {
		if(carOwner.getContactPerson()!=null&&!carOwner.getContactPerson().equals("")){
			carOwner.setContactPerson(carOwner.getContactPerson().replace(" ", ""));
		}
		if(carOwner.getIdCardNo()!=null&&!carOwner.getIdCardNo().equals("")){
			carOwner.setIdCardNo(carOwner.getIdCardNo().replace(" ", ""));
		}
		return  carOwnerService.updateCarOwner(carOwner, getOperator());
	}
	/*
	 * 车主信息修改提交
	 */
	@RequestMapping("/delCarOwner")
	@ResponseBody
	public ResultInfo<CarOwner> delCarOwner(String ownerId) {
		CarOwner carOwner=carOwnerService.getCarOwner(ownerId);
		carOwner.setIsAvailable(0);//将可用状态改为0：不可用
		return  carOwnerService.updateCarOwner(carOwner, getOperator());
	}
	/*
	 * 进入车主信息审核页面
	 */
	@RequestMapping("/toCarOwnerCencor")
	public String toCarOwnerCencor(String ownerId,ModelMap modelMap) {
		CarOwner carOwner=carOwnerService.getCarOwner(ownerId);
		modelMap.put("carOwner", carOwner);
		return "car/car_owner_cencor";
	}
	/*
	 * 车主信息审核提交
	 */
	@RequestMapping("/cencorCarOwner")
	@ResponseBody
	public ResultInfo<CarOwner> cencorCarOwner(@ModelAttribute("carOwner") CarOwner carOwner) {
		return  carOwnerService.updateCarOwner(carOwner, getOperator());
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
			String path=request.getRealPath("/")+"download"+File.separator+"car_owner.xls";
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
	 * 车主信息批量导入
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("importCarOwner")
	@ResponseBody
	public ResultInfo<CarOwner> importItems(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		ResultInfo<CarOwner> resultInfo = new  ResultInfo<CarOwner>();
		if(file != null){
			try {
				resultInfo = carOwnerService.importCarOwner(file, getOperator());
			} catch (XlsImportException e) {
				resultInfo = new ResultInfo<CarOwner>();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(e.getErrorMsg());
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择文件！");
		}
		
		
		return resultInfo;
	}
	
	/*
	 * 判断身份证号的唯一性
	 */
	@RequestMapping("/onlyIdCardNo")
	@ResponseBody
	public String onlyIdCardNo(String idCardNo,String ownerId) {
		CarOwner co=new CarOwner();
		co.setIdCardNo(idCardNo);
		Query q=new Query();
		q.setQ(co);
		Integer tag=0;
		List<CarOwner> list=carOwnerService.getCarOwnerList(q);
		if(list!=null&&list.size()>0){
			for(CarOwner co1:list){
					if(!co1.getOwnerId().equals(ownerId)){
						tag=1;
					}
			}
			if(tag.equals(1)){
				return Constant.SECCUESS;
			}else{
				return Constant.FAIL;
			}
		}else{
			return Constant.FAIL;
		}
	}
	/*
	 * 判断工商营业执照的唯一性
	 */
	@RequestMapping("/onlyBizLicenseNo")
	@ResponseBody
	public String onlyBizLicenseNo(String bizLicenseNo,String ownerId) {
		CarOwner co=new CarOwner();
		co.setBizLicenseNo(bizLicenseNo);
		Query q=new Query();
		q.setQ(co);
		Integer tag=0;
		List<CarOwner> list=carOwnerService.getCarOwnerList(q);
		if(list!=null&&list.size()>0){
				for(CarOwner co1:list){
					if(!co1.getOwnerId().equals(ownerId)){//剔除修改时，当前记录
						tag=1;
					}
				}
				if(tag.equals(1)){
					return Constant.SECCUESS;
				}else{
					return Constant.FAIL;
				}
		}else{
			return Constant.FAIL;
		}
	}
}
