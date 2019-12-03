package cn.com.shopec.mapi.marketing.vo;


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
//	//城市id(具体数值来自数据字典)
//	private Integer cityId;
//	//城市名称
//	private String cityName;
	//销售价
	private Double price;
	//套餐时长
	private Integer minutes;
	//有效天数
	private Integer availableDays;
//	//上下架状态（0下架，1上架）
//	private Integer isAvailable;	
//	//审核状态（0、未审核，1、已审核，2、审核不通过）
//	private Integer cencorStatus;
	/*Auto generated properties end*/
//	//套餐的有效截止时间
	private String avaliableDate;
	//套餐每日抵扣上限
	private Integer deductionCeiling;
	
	//套餐金额	买家收到可用金额
	private Double packAmount;
	
	//套餐类型
	private Integer type;
	
	//是否还可购买
	private Integer isBuyPackage;
	
	
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
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsBuyPackage() {
		return isBuyPackage;
	}

	public void setIsBuyPackage(Integer isBuyPackage) {
		this.isBuyPackage = isBuyPackage;
	}

	public Double getPackAmount() {
		return packAmount;
	}

	public void setPackAmount(Double packAmount) {
		this.packAmount = packAmount;
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
	
//	public Integer getCityId(){
//		return cityId;
//	}
//	
//	public void setCityId(Integer cityId){
//		this.cityId = cityId;
//	}
//	
//	public String getCityName(){
//		return cityName;
//	}
//	
//	public void setCityName(String cityName){
//		this.cityName = cityName;
//	}
//	
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
	
//	public Integer getIsAvailable(){
//		return isAvailable;
//	}
//	
//	public void setIsAvailable(Integer isAvailable){
//		this.isAvailable = isAvailable;
//	}
//	
//	public Integer getCencorStatus(){
//		return cencorStatus;
//	}
//	
//	public void setCencorStatus(Integer cencorStatus){
//		this.cencorStatus = cencorStatus;
//	}
	/*Auto generated methods end*/

	

//	public String getAvaliableDate() {
//		return avaliableDate;
//	}
//
//	public void setAvaliableDate(String avaliableDate) {
//		this.avaliableDate = avaliableDate;
//	}

	public Integer getDeductionCeiling() {
		return deductionCeiling;
	}

	public void setDeductionCeiling(Integer deductionCeiling) {
		this.deductionCeiling = deductionCeiling;
	}

	public String getAvaliableDate() {
		return avaliableDate;
	}

	public void setAvaliableDate(String avaliableDate) {
		this.avaliableDate = avaliableDate;
	}

//	@Override
//	public String toString() {
//		return "PricingPackVo [packageNo=" + packageNo + ", packageName=" + packageName + ", cityId=" + cityId
//				+ ", cityName=" + cityName + ", price=" + price + ", minutes=" + minutes + ", availableDays="
//				+ availableDays + ", isAvailable=" + isAvailable + ", cencorStatus=" + cencorStatus + "]";
//	}
//	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	

}
