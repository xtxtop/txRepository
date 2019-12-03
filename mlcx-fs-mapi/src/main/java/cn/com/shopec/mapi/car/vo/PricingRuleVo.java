package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.Date;

public class PricingRuleVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8323913763972743638L;
	
	//设置所有车辆能否在场站外还车
	private Integer returnCarStatus;
	//弹出计费规则
	private  String  pricingRuleBill;
	//弹出计费说明
	private  String  billingInstructions;
	//弹出任一点说明
	private  String  anyPointExplanation;
	public String getPricingRuleBill() {
		return pricingRuleBill;
	}
	public void setPricingRuleBill(String pricingRuleBill) {
		this.pricingRuleBill = pricingRuleBill;
	}
	public String getBillingInstructions() {
		return billingInstructions;
	}
	public void setBillingInstructions(String billingInstructions) {
		this.billingInstructions = billingInstructions;
	}
	public String getAnyPointExplanation() {
		return anyPointExplanation;
	}
	public void setAnyPointExplanation(String anyPointExplanation) {
		this.anyPointExplanation = anyPointExplanation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "PricingRuleVo [pricingRuleBill=" + pricingRuleBill + ", billingInstructions=" + billingInstructions
				+ ", anyPointExplanation=" + anyPointExplanation + "]";
	}
	public Integer getReturnCarStatus() {
		return returnCarStatus;
	}
	public void setReturnCarStatus(Integer returnCarStatus) {
		this.returnCarStatus = returnCarStatus;
	}
	
	
	
}
