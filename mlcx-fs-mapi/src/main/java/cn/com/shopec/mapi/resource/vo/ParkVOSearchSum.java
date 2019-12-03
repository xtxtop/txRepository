package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.car.model.Car;

/**
 * 场站信息VO对象
 * @author fly
 *
 */

public class ParkVOSearchSum  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
		//场站搜索
		private List<ParkVOSearch> parkVOSearchs;
		
		//可用站点数量
		private Integer parkTakeNum;
		
		public List<ParkVOSearch> getParkVOSearchs() {
			return parkVOSearchs;
		}
		public void setParkVOSearchs(List<ParkVOSearch> parkVOSearchs) {
			this.parkVOSearchs = parkVOSearchs;
		}
		public Integer getParkTakeNum() {
			return parkTakeNum;
		}
		public void setParkTakeNum(Integer parkTakeNum) {
			this.parkTakeNum = parkTakeNum;
		}
		@Override
		public String toString() {
			return "ParkVOSearchSum [parkVOSearchs=" + parkVOSearchs + ", parkTakeNum=" + parkTakeNum + "]";
		}
		
		
	
		
		
		
		
		


	
	
}
