package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CarIllegalListVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 4749250697558548842L;

	//违章集合对象
	private  List<CarIllegalVo> carIllegalVos;
	//违章个数
	private Integer illegalSize;
	//违章未处理 个数
	private Integer illStatusSize;
	public List<CarIllegalVo> getCarIllegalVos() {
		return carIllegalVos;
	}
	public void setCarIllegalVos(List<CarIllegalVo> carIllegalVos) {
		this.carIllegalVos = carIllegalVos;
	}
	public Integer getIllegalSize() {
		return illegalSize;
	}
	public void setIllegalSize(Integer illegalSize) {
		this.illegalSize = illegalSize;
	}
	public Integer getIllStatusSize() {
		return illStatusSize;
	}
	public void setIllStatusSize(Integer illStatusSize) {
		this.illStatusSize = illStatusSize;
	}
	@Override
	public String toString() {
		return "CarIllegalListVo [carIllegalVos=" + carIllegalVos + ", illegalSize=" + illegalSize + ", illStatusSize="
				+ illStatusSize + "]";
	}
	
	
	
}
