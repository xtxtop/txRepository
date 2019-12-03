package cn.com.shopec.core.map.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作entity对象后，百度api返回结果对象封装
 *
 */
public class TrackResultInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1557918178834959616L;
	//状态码,返回状态，0为成功 
	private Integer status;
	//响应信息 ,对status的中文描述 
	private String message;
	// 本次检索总结果条数
	private Integer total;
	//本页返回的结果条数
	private Integer size;
	//此段轨迹的里程数，单位：米
	private Double distance ;
	//entity对象属性集合
	private List<Points> points = new ArrayList<Points>();
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public List<Points> getPoints() {
		return points;
	}
	public void setPoints(List<Points> points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return "TrackResultInfo [status=" + status + ", message=" + message + ", total=" + total + ", size=" + size
				+ ", distance=" + distance + ", points=" + points + "]";
	}
	
}
