package cn.com.shopec.core.ml.service.impl;

import cn.com.shopec.common.component.MlToken;
import cn.com.shopec.common.utils.HttpUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.dao.COrderDao;
import cn.com.shopec.core.ml.service.COrderService;
import cn.com.shopec.core.ml.vo.OrderDetailVo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service public class COrderServiceImpl implements COrderService {
	@Resource private COrderDao cOrderDao;

	@Override public List<OrderSimpleVo> searchOrdersByVo(Integer pageNo, Integer pageSize, OrderSimpleVo vo) {
		List<OrderSimpleVo> lst = cOrderDao.searchOrderList(new Query(pageNo, pageSize, vo));
		return lst == null ? new ArrayList<>() : lst;
	}

	@Override public OrderDetailVo findOrderDetailByVo(OrderDetailVo vo) {
		return cOrderDao.findOrderDetailByVo(new Query(vo));
	}

	@Override public JSONObject issueCdzCmd(String url, Map<String, String> param) {
		try {
			JSONObject json=null;
			boolean tag=false;
			next: while (true){
				issue:{//下发请求
					param.put("token", MlToken.getToken());
					HttpUtils httpUtils = HttpUtils.getInstance(url);
					byte[] datas = httpUtils.post(JSONObject.fromObject(param).toString().getBytes("utf-8"));
					json = JSONObject.fromObject(httpUtils.bt2string(datas));
					if(tag){
						return json;
					}
				}
				verify:{//验证token
					if(json==null||"token码超时错误".equals(json.opt("nr"))||"Token码错误".equals(json.opt("nr"))){
						MlToken.upToken();
						tag=true;
						continue next;
					}else break next;
				}
			}
			return json;
		} catch (Exception e) {
			return null;
		}
	}
}
