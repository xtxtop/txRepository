package cn.com.shopec.mapi.station.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CLoveCar;
import cn.com.shopec.core.ml.service.CLoveCarService;
import cn.com.shopec.core.ml.vo.BannerVo;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * 添加爱车接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/app/loveCar")
public class LoveCarController extends BaseController {

	@Resource
	private CLoveCarService loveCarService;

	@Value("${image_path}")
	private String imgPath;

	/**
	 * 添加爱车顶部banner
	 * 
	 * @return
	 */
	@RequestMapping("/carLoveBanner")
	@ResponseBody
	public ResultInfo<List<BannerVo>> carLoveBanner() {
		ResultInfo<List<BannerVo>> result = new ResultInfo<List<BannerVo>>();
		List<BannerVo> loveCarBannerList = loveCarService.getLoveCarBannerList(1, Constant.TYPE_14, imgPath);
		if (loveCarBannerList.size() > 0) {
			result.setCode(Constant.SECCUESS);
			result.setMsg("成功返回数据");
			result.setData(loveCarBannerList);
			return result;
		} else {
			result.setCode(Constant.OTHER);
			result.setMsg("添加爱车顶部banner暂无数据");
			return result;
		}
	}

	/**
	 * 添加爱车提交信息
	 */
	@RequestMapping("/submitCarLove")
	@ResponseBody
	public ResultInfo<CLoveCar> submitCarLove(CLoveCar loveCar) {
		ResultInfo<CLoveCar> resultInfo = new ResultInfo<CLoveCar>();
		if (StringUtils.isEmpty(loveCar.getMemberNo()) || StringUtils.isEmpty(loveCar.getDrivingLicense())
				|| StringUtils.isEmpty(loveCar.getDrivingLicenseCopy())
				|| StringUtils.isEmpty(loveCar.getVehicleBrand()) || StringUtils.isEmpty(loveCar.getVehicleModel())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG + "会员编号、车辆品牌、型号、行驶证等参数有为空的");
			return resultInfo;
		} else if (!StringUtils.isEmpty(loveCar.getMemberNo()) && !StringUtils.isEmpty(loveCar.getVehicleModel())) {
			CLoveCar car = new CLoveCar();
			car.setMemberNo(loveCar.getMemberNo());
			car.setVehicleModel(loveCar.getVehicleModel());
			List<CLoveCar> lst = loveCarService.getCLoveCarList(new Query(car));
			if (lst.size() > 0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG + "同型号的车辆不能再次添加");
				return resultInfo;
			}
		}
		ResultInfo<CLoveCar> result=new ResultInfo<CLoveCar>();
		if(loveCar.getLoveCarNo()!=null&&!StringUtils.isEmpty(loveCar.getLoveCarNo())){
			result = loveCarService.updateCLoveCar(loveCar, getOperator());
		}else{
			result = loveCarService.addCLoveCar(loveCar, getOperator());
		}
		return result;
	}

}
