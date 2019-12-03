package cn.com.shopec.core.ml.service;

import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.ml.vo.OrderDetailVo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface COrderService  extends BaseService {
	List<OrderSimpleVo> searchOrdersByVo(Integer pageNo,Integer pageSize,OrderSimpleVo vo);
	OrderDetailVo findOrderDetailByVo(OrderDetailVo vo);
	JSONObject issueCdzCmd(String url, Map<String,String> param);
}
