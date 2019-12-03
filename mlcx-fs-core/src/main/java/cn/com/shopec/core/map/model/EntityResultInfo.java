package cn.com.shopec.core.map.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作entity对象后，百度api返回结果对象封装
 *
 */
public class EntityResultInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210297176102077900L;

	//状态码,返回状态，0为成功 
	private Integer status;
	//响应信息 ,对status的中文描述 
	private String message;
	// 本次检索总结果条数
	private Integer total;
	//本页返回的结果条数
	private Integer size;
	//entity对象属性集合
	private List<Column> columns = new ArrayList<Column>();
	//entity对应的查询结果属性
	private List<Entities> entities = new ArrayList<Entities>();
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
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<Entities> getEntities() {
		return entities;
	}
	public void setEntities(List<Entities> entities) {
		this.entities = entities;
	}
	@Override
	public String toString() {
		return "EntityResultInfo [status=" + status + ", message=" + message + ", total=" + total + ", size=" + size
				+ ", columns=" + columns + ", entities=" + entities + "]";
	}
	
}
