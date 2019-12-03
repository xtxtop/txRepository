package cn.com.shopec.core.car.vo;

import java.util.List;

public class CarWkSVo {

	//已上线的数量
		private Integer carUpNum;
		//已下线的数量
		private Integer carDownNum;
		//低电量的数量
		private Integer carDownpower;
		//车辆列表
		private List<CarWkVo> carwkvos;
		public Integer getCarUpNum() {
			return carUpNum;
		}
		public void setCarUpNum(Integer carUpNum) {
			this.carUpNum = carUpNum;
		}
		public Integer getCarDownNum() {
			return carDownNum;
		}
		public void setCarDownNum(Integer carDownNum) {
			this.carDownNum = carDownNum;
		}
		public Integer getCarDownpower() {
			return carDownpower;
		}
		public void setCarDownpower(Integer carDownpower) {
			this.carDownpower = carDownpower;
		}
		public List<CarWkVo> getCarwkvos() {
			return carwkvos;
		}
		public void setCarwkvos(List<CarWkVo> carwkvos) {
			this.carwkvos = carwkvos;
		}
	
}
