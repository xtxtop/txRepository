package cn.com.shopec.mapi.station.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.ml.model.CJoin;
import cn.com.shopec.core.ml.service.CJoinService;
import cn.com.shopec.core.mlparking.service.CParkLockService;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * @author 代元宝
 *@category 申请加盟APP接口
 */
@Controller
@RequestMapping("app/join")
public class JoinController extends BaseController{
	@Resource
	private MemberService memberService;
	@Resource
	private CJoinService cJoinService;
	@Resource
	private CParkLockService cParkLockService;
	/**
	 * 申请加盟
	 * @param join
	 * @return
	 */
	@RequestMapping("/applyJoin")
	@ResponseBody
	public ResultInfo<CJoin> applyJoin(CJoin join){
		ResultInfo<CJoin> info = new ResultInfo<CJoin>();
		if(join.getJoinType()==null){//验证加盟类型
			info.setMsg("参数错误!");
			info.setCode(Constant.FAIL);
			return info;
		}
		//验证会员信息
		ResultInfo<Object> resultInfoMemberNo = cParkLockService
				.resultInfoMemberNo(join.getMemberNo(),2);
		if (resultInfoMemberNo != null && Constant.SECCUESS.equals(resultInfoMemberNo.getCode())) {
			//申请加盟
			ResultInfo<CJoin> addCJoin = cJoinService.addCJoin(join, getOperator());
			if(Constant.SECCUESS.equals(addCJoin.getCode())){
				info.setMsg("申请成功!");
				info.setCode(Constant.SECCUESS);
				return info;
			}else{
				info.setMsg("申请失败!");
				info.setCode(Constant.FAIL);
				return info;
			}
		} else {
			info.setMsg(resultInfoMemberNo.getMsg());
			info.setCode(resultInfoMemberNo.getCode());
			return info;
		}
	}
}
