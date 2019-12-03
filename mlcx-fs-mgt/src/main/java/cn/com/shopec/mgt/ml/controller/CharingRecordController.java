package cn.com.shopec.mgt.ml.controller;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingRecord;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.service.ChargingRecordService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.vo.ChargingRecordVo;
import cn.com.shopec.mgt.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("chargingRecord")
public class CharingRecordController extends BaseController {

    @Resource
    private ChargingRecordService chargingRecordService;
    @Resource
    private ChargingStationService chargingStationService;

    //获取列表页
    @RequestMapping("/pageListchargingRecord")
    @ResponseBody
    public PageFinder<ChargingRecordVo> pageListchargingRecord(@ModelAttribute("ChargingRecordVo") ChargingRecordVo chargingRecordVo, Query query) {
        Query q = new Query(query.getPageNo(), query.getPageSize(), chargingRecordVo);
        return chargingRecordService.getChargingRecordVoPagedList(q);
    }

    @RequestMapping("/getChargingRecordList")
    public String getChargingList(ModelMap model) {
        List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query());
        model.put("csList", csList);
        return "ml/chargingRecord_list";
    }

    @RequestMapping("toCharingRecordView")
    public String toCharingRecordView(String recordNo, Model model) {
        ChargingRecord chargingRecord = chargingRecordService.getChargingRecord(recordNo);
        model.addAttribute("ChargingRecord", chargingRecord);
        return "ml/chargingRecord_view";
    }
}
