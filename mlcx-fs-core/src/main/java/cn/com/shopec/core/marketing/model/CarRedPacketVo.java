package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * CarRedPacket 数据实体类
 */
public class CarRedPacketVo  {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//领取红包是否需要充电（0.不需要 1.需要）
		private Integer isCharge;
		//是否限制订单金额满多少钱可领取（0.否 1.是）
		private Integer isOrderAmountLimit;
		//满足领取红包的订单金额最小值（IS_ORDER_AMOUNT_LIMIT为1时可用）
		private Double orderAmountLimit;
		public Integer getIsCharge() {
			return isCharge;
		}
		public void setIsCharge(Integer isCharge) {
			this.isCharge = isCharge;
		}
		public Integer getIsOrderAmountLimit() {
			return isOrderAmountLimit;
		}
		public void setIsOrderAmountLimit(Integer isOrderAmountLimit) {
			this.isOrderAmountLimit = isOrderAmountLimit;
		}
		public Double getOrderAmountLimit() {
			return orderAmountLimit;
		}
		public void setOrderAmountLimit(Double orderAmountLimit) {
			this.orderAmountLimit = orderAmountLimit;
		}
	
	
	
	
	
}
