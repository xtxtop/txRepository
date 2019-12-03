package cn.com.shopec.mgt.mlparking.controller;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CParkBilling;
import cn.com.shopec.core.mlparking.service.CParkBillingService;
import cn.com.shopec.mgt.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("parkBilling")
public class ParkBillingController extends BaseController {
    @Resource
    private CParkBillingService parkBillingService;

    @RequestMapping("getParkBillingList")
    public String getParkBillingList() {
        return "mlpark/parkBilling_list";
    }

    //列表展示
    @RequestMapping("toParkBillingList")
    @ResponseBody
    public PageFinder<CParkBilling> toParkBillingList(@ModelAttribute("parkBilling") CParkBilling parkBilling,
                                                      Query query){
        Query q = new Query(query.getPageNo(), query.getPageSize(), parkBilling);
        return parkBillingService.getCParkBillingPagedList(q);
    }

    //新增页面
    @RequestMapping("addParkBilling")
    public String addParkBilling() {
        return "mlpark/parkBilling_add";
    }

    @RequestMapping("toAddParkBilling")
    @ResponseBody
    public ResultInfo<CParkBilling> toAddParkBilling(@ModelAttribute("CParkBilling") CParkBilling parkBilling) {
        return parkBillingService.addCParkBilling(parkBilling, getOperator());
    }

    @RequestMapping("editParkBilling")
    public String editParkBilling(ModelMap model, String parkBillingNo) {
        CParkBilling parkBilling = parkBillingService.getCParkBilling(parkBillingNo);
        model.put("parkBilling", parkBilling);
        return "mlpark/parkBilling_edit";
    }

    @RequestMapping("toEditParkBilling")
    @ResponseBody
    public ResultInfo<CParkBilling> toEditParkBilling(@ModelAttribute("parkBilling") CParkBilling parkBilling) {
        return parkBillingService.updateCParkBilling(parkBilling, getOperator());
    }

    @RequestMapping("toupdateStatus")
    @ResponseBody
    public ResultInfo<CParkBilling> toupdateStatus(@RequestParam String parkBillingNo, @RequestParam Integer status) {
        return parkBillingService.updateStatus(parkBillingNo, status, getOperator());
    }

}
