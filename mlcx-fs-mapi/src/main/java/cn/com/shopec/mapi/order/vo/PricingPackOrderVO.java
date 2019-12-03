package cn.com.shopec.mapi.order.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 套餐订单表 数据实体类
 */
public class PricingPackOrderVO extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//套餐订单编号
	private String packOrderNo;
	//套餐名称
	private String packageName;
	//套餐金额
	private Double packAmount;
	//套餐时长(以分钟为单位，库里只存数值)
	private Integer packMinute;
	//已用时长
	private Integer userdMinute;
	//剩余时长
	private Integer residueMinute;
	//应付金额
	private Double payableAmount;
	//是否可用（0，不可用，1、可用；默认1 2  已过期 3 已使用）
	private Integer isAvailable;
	//有效期限
	private String vailableTime2;
	//套餐每日抵扣上限
	private Integer deductionCeiling;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Double getPackAmount() {
		return packAmount;
	}
	public void setPackAmount(Double packAmount) {
		this.packAmount = packAmount;
	}
	public Integer getPackMinute() {
		return packMinute;
	}
	public void setPackMinute(Integer packMinute) {
		this.packMinute = packMinute;
	}
	public Integer getUserdMinute() {
		return userdMinute;
	}
	public void setUserdMinute(Integer userdMinute) {
		this.userdMinute = userdMinute;
	}
	public Integer getResidueMinute() {
		return residueMinute;
	}
	public void setResidueMinute(Integer residueMinute) {
		this.residueMinute = residueMinute;
	}
	public Double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public Integer getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getVailableTime2() {
		return vailableTime2;
	}
	public void setVailableTime2(String vailableTime2) {
		this.vailableTime2 = vailableTime2;
	}
	public Integer getDeductionCeiling() {
		return deductionCeiling;
	}
	public void setDeductionCeiling(Integer deductionCeiling) {
		this.deductionCeiling = deductionCeiling;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "PricingPackOrderVO [packageName=" + packageName + ", packAmount=" + packAmount + ", packMinute="
				+ packMinute + ", userdMinute=" + userdMinute + ", residueMinute=" + residueMinute + ", payableAmount="
				+ payableAmount + ", isAvailable=" + isAvailable + ", vailableTime2=" + vailableTime2
				+ ", deductionCeiling=" + deductionCeiling + "]";
	}
	public String getPackOrderNo() {
		return packOrderNo;
	}
	public void setPackOrderNo(String packOrderNo) {
		this.packOrderNo = packOrderNo;
	}
	
	
	
	
	
	
	
	
	
}
