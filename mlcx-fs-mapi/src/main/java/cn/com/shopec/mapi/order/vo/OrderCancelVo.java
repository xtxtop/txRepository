package cn.com.shopec.mapi.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.com.shopec.mapi.resource.vo.ParkVOCarStatus;

public class OrderCancelVo  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	//配置的取消上限次数
	private String sum;
	//剩余的取消
	private String cancelSum;
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getCancelSum() {
		return cancelSum;
	}
	public void setCancelSum(String cancelSum) {
		this.cancelSum = cancelSum;
	}
	
	

	
}
