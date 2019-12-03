package cn.com.shopec.mapi.finace.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.MemberDepositRefund;
import cn.com.shopec.core.finace.model.RefundGroundsVo;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.DepositPayService;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.finace.vo.DepositListVo;
import cn.com.shopec.mapi.finace.vo.DepositRefundListVo;
import cn.com.shopec.mapi.order.vo.OrderVOTrip;
/**
 * 押金（保证金）查看
 * */
@Controller
@RequestMapping("/app/deposit")
public class DepositController extends BaseController{
	
	@Resource
	private DepositOrderService depositOrderService;
	
	@Resource
	private DepositPayService depositPayService;
	
	@Resource
	private SysParamService sysParamService;
	@Resource
	private DepositRefundService  depositRefundService;
	@Resource
	private DataDictItemService  dataDictItemService;

	
	
	
	/**获取当前用户的保证金使用情况
	 */
	@RequestMapping("/myDepositDetail")
	@ResponseBody
	public ResultInfo<Deposit> myDepositDetail(String memberNo,String addrRegion){
		ResultInfo<Deposit> resultInfo=new ResultInfo<Deposit>();
		resultInfo=depositOrderService.getDepositByMemberNo(memberNo,addrRegion);
		List<RefundGroundsVo> refundGroundsVoList = new ArrayList<RefundGroundsVo>();
		List<DataDictItem> list = dataDictItemService.getDataDictItemListByCatCode("REFUND_GROUNDS");//
		if(list != null){
			for (DataDictItem dataDictItem : list) {
				RefundGroundsVo fg= new RefundGroundsVo();
				fg.setRefundGrounds(dataDictItem.getItemValue());
				refundGroundsVoList.add(fg);
			}
			if(resultInfo.getData() != null){
				resultInfo.getData().setRefundGroundsListVo(refundGroundsVoList);
			}
			
		}
		
		return resultInfo;
	}
	
	
	/**点击押金退回 页面   跳转到 退回押金页面
	 */
	@RequestMapping("/myDepositList")
	@ResponseBody
	public ResultInfo<DepositListVo> myDepositList(String memberNo,String addrRegion){
		ResultInfo<DepositListVo> result=new ResultInfo<DepositListVo>();
		try {
			result =depositListVo(result, memberNo,addrRegion);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	 ResultInfo<DepositListVo> depositListVo(ResultInfo<DepositListVo> result,String memberNo,String addrRegion) throws ParseException{
		ResultInfo<DepositListVo> resultInfo=new ResultInfo<DepositListVo>();
		SysParam sysp = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
		DepositListVo depositListVo=new DepositListVo();
			ResultInfo<Deposit> deposit = depositOrderService.getDepositByMemberNo(memberNo,addrRegion); 
			if(deposit.getData() != null){
				Deposit obj=deposit.getData();
				if(obj==null){
					obj = new Deposit();
			//		resultInfo.getData().setMemberNo(memberNo);
				}else{
					if(obj.getAvailableAmount()==null){
						depositListVo.setAvailableAmount(0d);
					}else{
						depositListVo.setAvailableAmount(obj.getAvailableAmount());
					}
				}
			}
			
			//查出 有没有 退款记录
			DepositRefund dRSearchs=new DepositRefund();
			dRSearchs.setMemberNo(memberNo);
			List<DepositRefundListVo> drl=new ArrayList<DepositRefundListVo>();
			List<DepositRefund> lists=depositRefundService.getDepositRefundList(new Query(dRSearchs));
			if(lists != null && lists.size()>0){
				for (DepositRefund depositRefund : lists) {
					DepositRefundListVo de= new DepositRefundListVo();
					de.setCencorStatus(depositRefund.getCencorStatus());
					if(depositRefund.getRefundStatus()==1){
						de.setCencorStatus(4);
					}
					de.setRefundAmount(depositRefund.getRefundAmount());
					de.setStringApplyTime(ECDateUtils.formatStringDate(depositRefund.getApplyTime()));
					if(depositRefund.getCencorMemo() != null && !"".equals(depositRefund.getCencorMemo())){
						de.setCencorMemo(depositRefund.getCencorMemo());
					}else{
						de.setCencorMemo("");
					}
					if(depositRefund.getRefundGrounds() != null && !"".equals(depositRefund.getRefundGrounds())){
						de.setRefundGrounds(depositRefund.getRefundGrounds());
					}
					
					drl.add(de);
				}
				depositListVo.setDepositRefunds(drl);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("没有退款记录");
			}
			resultInfo.setData(depositListVo);
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;

	}
	
	
	/**申请保证金退款
	 */
	@RequestMapping("/myDepositRefundApply")
	@ResponseBody
	public ResultInfo<MemberDepositRefund> myDepositRefundApply(String memberNo,String refundGrounds,String refundGroundsMemo){
		ResultInfo<MemberDepositRefund> resultInfo=new ResultInfo<MemberDepositRefund>();
		resultInfo=depositOrderService.getDepositRefundByMemberNo(memberNo,refundGrounds,refundGroundsMemo);
		return resultInfo;
	}
	
	/**判断是否可以申请退款押金
	 */
	@RequestMapping("/IsDepositRefundApply")
	@ResponseBody
	public ResultInfo<MemberDepositRefund> IsDepositRefundApply(String memberNo){
		ResultInfo<MemberDepositRefund> resultInfo=new ResultInfo<MemberDepositRefund>();
		resultInfo=depositOrderService.getIsDepositRefundByMemberNo(memberNo);
		return resultInfo;
	}
}
