package cn.com.shopec.core.car.model;

import java.io.Serializable;

import cn.com.shopec.core.resource.model.ParkVOAround;

/**
 * 一键用车  返回实体对象
 * @author admin
 *
 */
public class CarOrParkVo implements Serializable {
	/**
	 *
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private CarVoAround carVoAround;//车辆信息
	
	private ParkVOAround parkVoAround;//场站信息

	public CarVoAround getCarVoAround() {
		return carVoAround;
	}

	public void setCarVoAround(CarVoAround carVoAround) {
		this.carVoAround = carVoAround;
	}

	public ParkVOAround getParkVoAround() {
		return parkVoAround;
	}

	public void setParkVoAround(ParkVOAround parkVoAround) {
		this.parkVoAround = parkVoAround;
	}

	@Override
	public String toString() {
		return "CarOrParkVo [" + (carVoAround != null ? "carVoAround=" + carVoAround + ", " : "")
				+ (parkVoAround != null ? "parkVoAround=" + parkVoAround : "") + "]";
	}
	
	
	
}
