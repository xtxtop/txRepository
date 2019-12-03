package cn.com.shopec.mgt.ml.controller;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.COrderEvaluate;
import cn.com.shopec.core.ml.service.COrderEvaluateService;
import cn.com.shopec.mgt.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("orderEvaluate")
public class OrderEvaluateController extends BaseController {
    @Resource
    private COrderEvaluateService orderEvaluateService;

    @RequestMapping("toOrderEvaluate")
    public String toOrderEvaluate() {
        return "ml/orderEvaluate_list";
    }

    @RequestMapping("pageListOrderEvaluate")
    @ResponseBody
    public PageFinder<COrderEvaluate>  pageListOrderEvaluate(@ModelAttribute("cOrderEvaluate")COrderEvaluate cOrderEvaluate, Query query) {
        Query q = new Query(query.getPageNo(), query.getPageSize(), cOrderEvaluate);
        return orderEvaluateService.getCOrderEvaluatePagedList(q);
    }
}
