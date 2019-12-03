package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.vo.CouponVo;

public interface DailyRentalDikouOrderDayService {
	
	public List<CouponVo> caculateCouponDikouAmount(OrderDay orderDay);
	
}
