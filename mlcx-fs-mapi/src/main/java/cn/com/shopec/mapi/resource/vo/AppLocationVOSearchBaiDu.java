
package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.car.model.Car;

/**
 * 场站信息VO对象
 * @author fly
 *
 */

public class AppLocationVOSearchBaiDu  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
		//返回 状态 0 成功
		private Integer status;
		private AppLocationVOBaiDu result;
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public AppLocationVOBaiDu getResult() {
			return result;
		}
		public void setResult(AppLocationVOBaiDu result) {
			this.result = result;
		}
		@Override
		public String toString() {
			return "ParkVOSearchBaiDu [status=" + status + ", result=" + result + "]";
		}
		
		
		public class AppLocationVOBaiDu{
			//private LongitudeAndLatitudeBaidu  location;
			private AddressComponent addressComponent;
			private String formatted_address;
			
			public AddressComponent getAddressComponent() {
				return addressComponent;
			}
			public void setAddressComponent(AddressComponent addressComponent) {
				this.addressComponent = addressComponent;
			}


			public String getFormatted_address() {
				return formatted_address;
			}
			public void setFormatted_address(String formatted_address) {
				this.formatted_address = formatted_address;
			}


			public  class AddressComponent{
			private String  province; //省份
			private String city; //市区
			private String district;//区
			public String getProvince() {
				return province;
			}
			public void setProvince(String province) {
				this.province = province;
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

			
		}
			
			
		}
		
	
}
