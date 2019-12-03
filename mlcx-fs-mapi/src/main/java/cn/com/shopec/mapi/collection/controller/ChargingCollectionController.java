package cn.com.shopec.mapi.collection.controller;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.ml.model.CChargingStationCollection;
import cn.com.shopec.core.ml.service.CChargingStationCollectionService;
import cn.com.shopec.mapi.common.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@RequestMapping("/app/chargingCollection")
public class ChargingCollectionController extends BaseController {
    @Resource
    private CChargingStationCollectionService cChargingStationCollectionService;

    @RequestMapping("/collection")
    @ResponseBody
    public ResultInfo collection(@RequestParam String memberNo, @RequestParam String stationNo) {
        ResultInfo resultInfo = new ResultInfo();
        if (StringUtils.isEmpty(memberNo) || StringUtils.isEmpty(stationNo)) {
            resultInfo.setCode(Constant.FAIL);
            resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
            return resultInfo;
        }
        CChargingStationCollection cChargingStationCollection = new CChargingStationCollection();
        cChargingStationCollection.setStationNo(stationNo);
        cChargingStationCollection.setMemberNo(memberNo);
        return cChargingStationCollectionService.addStationCollection(resultInfo, cChargingStationCollection, getOperator());
    }
}
