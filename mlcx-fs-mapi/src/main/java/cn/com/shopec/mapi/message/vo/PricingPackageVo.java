package cn.com.shopec.mapi.message.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 套餐产品表 数据实体类
 */
public class PricingPackageVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//套餐编号
	private String packageNo;
	//套餐名称
	private String packageName;
	//城市id(具体数值来自数据字典)
	private String cityId;
	//城市名称
	private String cityName;
	//销售价
	private Double price;
	//套餐时长
	private Integer minutes;
	//有效天数
	private Integer availableDays;
	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return packageNo;
	}
	

	public String getPackageNo(){
		return packageNo;
	}
	
	public void setPackageNo(String packageNo){
		this.packageNo = packageNo;
	}
	
	public String getPackageName(){
		return packageName;
	}
	
	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
	
	
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public Double getPrice(){
		return price;
	}
	
	public void setPrice(Double price){
		this.price = price;
	}
	
	public Integer getMinutes(){
		return minutes;
	}
	
	public void setMinutes(Integer minutes){
		this.minutes = minutes;
	}
	
	public Integer getAvailableDays(){
		return availableDays;
	}
	
	public void setAvailableDays(Integer availableDays){
		this.availableDays = availableDays;
	}
	
	
	@Override
	public String toString() {
		return "PricingPackage [packageNo=" + packageNo + ", packageName=" + packageName + ", cityId=" + cityId
				+ ", cityName=" + cityName + ", price=" + price + ", minutes=" + minutes + ", availableDays="
				+ availableDays+"]";
	}

	
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	
}
