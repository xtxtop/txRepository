package cn.com.shopec.core.map.model;

import java.io.Serializable;

public class Entities implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8140081237191795748L;
	//	realtime_point 	实时轨迹信息 
//	entity_name 	entity名称，其唯一标识 	string 	
//	modify_time 	entity属性修改时间 	格式化时间 	该时间为服务端时间
//	create_time 	entity创建时间 	格式化时间 	该时间为服务端时间
//	<column-key>car_plate_no 	开发者自定义的entity属性信息 	string 
	private String entity_name;
	private String modify_time;
	private String create_time;
	private String car_plate_no;
	public String getEntity_name() {
		return entity_name;
	}
	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCar_plate_no() {
		return car_plate_no;
	}
	public void setCar_plate_no(String car_plate_no) {
		this.car_plate_no = car_plate_no;
	}
	@Override
	public String toString() {
		return "Entities [entity_name=" + entity_name + ", modify_time=" + modify_time + ", create_time=" + create_time
				+ ", car_plate_no=" + car_plate_no + "]";
	}
	
}
