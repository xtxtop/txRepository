
package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.car.model.Car;

/**
 * 场站信息VO对象
 * @author fly
 *
 */

public class ParkVOSearchBaiDu  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
		//返回 状态 0 成功
		private Integer status;
		private List<ParkVOBaidu> result;
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public List<ParkVOBaidu> getResult() {
			return result;
		}
		public void setResult(List<ParkVOBaidu> result) {
			this.result = result;
		}
		@Override
		public String toString() {
			return "ParkVOSearchBaiDu [status=" + status + ", result=" + result + "]";
		}
		
		
		public class ParkVOBaidu{
			//poi名称
			private String name;
			private LongitudeAndLatitudeBaidu  location;
			//城市 
			private String city;
			//县区
			private String district;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			
			
			public LongitudeAndLatitudeBaidu getLocation() {
				return location;
			}
			public void setLocation(LongitudeAndLatitudeBaidu location) {
				this.location = location;
			}


			public String getCity() {
				return city;
			}
			public void setCity(String city) {
				this.city = city;
			}


			public String getDistrict() {
				return district;
			}
			public void setDistrict(String district) {
				this.district = district;
			}


			public  class LongitudeAndLatitudeBaidu{
				private String  lng; //经度
				
				private String lat; //维度

				public String getLng() {
					return lng;
				}

				public void setLng(String lng) {
					this.lng = lng;
				}

				public String getLat() {
					return lat;
				}

				public void setLat(String lat) {
					this.lat = lat;
				}

			}
			
		}
		
	
}
