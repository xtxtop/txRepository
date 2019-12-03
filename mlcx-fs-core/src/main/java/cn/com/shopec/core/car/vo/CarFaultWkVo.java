package cn.com.shopec.core.car.vo;

import java.util.List;

public class CarFaultWkVo {


		//已下线的数量
		private Integer carDownNum;
		//不在线的数量
		private Integer isOnlineNum;
		//车辆列表
		private List<CarWkVo> carwkvos;
		
		public Integer getCarDownNum() {
			return carDownNum;
		}
		public void setCarDownNum(Integer carDownNum) {
			this.carDownNum = carDownNum;
		}
	
		public List<CarWkVo> getCarwkvos() {
			return carwkvos;
		}
		public void setCarwkvos(List<CarWkVo> carwkvos) {
			this.carwkvos = carwkvos;
		}
		public Integer getIsOnlineNum() {
			return isOnlineNum;
		}
		public void setIsOnlineNum(Integer isOnlineNum) {
			this.isOnlineNum = isOnlineNum;
		}
	
}
