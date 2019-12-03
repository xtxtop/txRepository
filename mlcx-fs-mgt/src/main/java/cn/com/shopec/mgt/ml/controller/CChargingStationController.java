package cn.com.shopec.mgt.ml.controller;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.service.CChargingStationCollectionService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.vo.ChargingCollectionVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/chargingCollection")
public class CChargingStationController {
    @Resource
    private CChargingStationCollectionService cChargingStationCollectionService;
    @Resource
    private ChargingStationService chargingStationService;

    @RequestMapping("/getChargingInfo")
    @ResponseBody
    public PageFinder<ChargingCollectionVo> getCollectionInfo(@ModelAttribute("ChargingCollectionVo") ChargingCollectionVo chargingCollectionVo, Query query) {
        Query q = new Query(query.getPageNo(), query.getPageSize(), chargingCollectionVo);
        return cChargingStationCollectionService.getCollectionInfo(q);
    }
    @RequestMapping("/getChargingList")
    public String getChargingList(ModelMap model) {
        List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query());
        model.put("csList", csList);
        return "ml/chargingCollection_list";
    }
}
