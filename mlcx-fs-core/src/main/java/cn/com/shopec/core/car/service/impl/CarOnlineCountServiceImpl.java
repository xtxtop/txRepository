package cn.com.shopec.core.car.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.dao.CarOnlineCountDao;
import cn.com.shopec.core.car.model.CarOnlineCount;
import cn.com.shopec.core.car.service.CarOnlineCountService;
import cn.com.shopec.core.system.service.PrimarykeyService;


/**
 * 车辆上下线统计  服务实现类
 */
@Service
public class CarOnlineCountServiceImpl implements CarOnlineCountService {

	private static final Log log = LogFactory.getLog(CarOnlineCountServiceImpl.class);
	
	@Resource
	private CarOnlineCountDao carOnlineCountDao;

	@Resource
	private PrimarykeyService primarykeyService;

	@Override
	public void addCarOnlineCount(CarOnlineCount carOnlineCount) {
		carOnlineCountDao.add(carOnlineCount);
	}
	
	@Override
	public Map<String, Object> getCarLine10CountMap() {
		String dateArray[] = new String[10];
		Object onlineArray[] = new Object[]{0,0,0,0,0,0,0,0,0,0};
		Object offlineArray[] = new Object[]{0,0,0,0,0,0,0,0,0,0};
		Date date = new Date();
		for(int day = -9; day <= 0; day++){
			String d = ECDateUtils.formatDate(ECDateUtils.getDateAfter(date, day), "MM-dd");
			dateArray[day+9] = d;
		}
		List<Map<String, Object>> data = carOnlineCountDao.getCarLineDay10CountMap();
		if(data != null && !data.isEmpty()){
			for(Map<String, Object> d : data){
				for(int i = 0; i < dateArray.length; i++){
					if(dateArray[i].equals((String)d.get("date"))){
						onlineArray[i] = d.get("onlineNum");
						offlineArray[i] = d.get("offlineNum");
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("dateArray", dateArray);
		map.put("onlineArray", onlineArray);
		map.put("offlineArray", offlineArray);
		return map;
	}

}
