package cn.com.shopec.mgt.ml.controller;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.model.CLoveCar;
import cn.com.shopec.core.ml.service.CLoveCarService;
import cn.com.shopec.mgt.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/cLoveCar")
public class LoveCarController extends BaseController {

    @Resource
    private CLoveCarService cLoveCarService;

    @RequestMapping("/toLoveCarList")
    public String toLoveCarList() {
        return "ml/loveCar_list";
    }

    @RequestMapping("/pageListLoveCar")
    @ResponseBody
    public PageFinder<CLoveCar> pageListLoveCar(@ModelAttribute("cLoveCar") CLoveCar cLoveCar, Query query) {
        Query q = new Query(query.getPageNo(), query.getPageSize(), cLoveCar);
        return cLoveCarService.getCLoveCarPagedList(q);
    }

    @RequestMapping("/toLoveCarView")
    public String toLoveCarView(String loveCarNo, Model model) {
        CLoveCar cLoveCar = cLoveCarService.getCLoveCar(loveCarNo);
        model.addAttribute("cLoveCar", cLoveCar);
        return "ml/loveCar_view";
    }

    @RequestMapping("/toLoveCarEdit")
    public String toLoveCarEdit(String loveCarNo, Model model) {
        CLoveCar cLoveCar = cLoveCarService.getCLoveCar(loveCarNo);
        model.addAttribute("cLoveCar", cLoveCar);
        return "ml/loveCar_edit";
    }


    @RequestMapping("/toLoveCarUpdate")
    @ResponseBody
    public ResultInfo toLoveCarUpdate(@ModelAttribute("cLoveCar") CLoveCar cLoveCar) {
        return cLoveCarService.updateCLoveCar(cLoveCar, getOperator());
    }

    @RequestMapping("/toLoveCarCensorView")
    public String toLoveCarCensorView(String loveCarNo, Model model) {
        CLoveCar cLoveCar = cLoveCarService.getCLoveCar(loveCarNo);
        model.addAttribute("cLoveCar", cLoveCar);
        return "ml/loveCar_censor";
    }

    @RequestMapping("/censorLoveCar")
    @ResponseBody
    public ResultInfo<CLoveCar> censorLoveCar(@ModelAttribute("cLoveCar") CLoveCar cLoveCar) {
        return cLoveCarService.updateCLoveCar(cLoveCar, getOperator());
    }

}
