package cn.com.shopec.core.map.model;

import java.io.Serializable;

/**
 * 操作entity属性对象的封装
 *
 */
public class Column implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7252556605275663820L;
	//属性名称
	private String column_key;
	//属性描述
	private String column_desc;
	//属性值
	private String column_value;
	//属性创建时间
	private String create_time;
	//属性更新时间
	private String modify_time;
	//是否为检索字段 1代表检索字段，0代表非检索字段。最多设置2个检索字段，且字段一经创建，此属性将不可更改。
	private Integer is_search;
	public String getColumn_key() {
		return column_key;
	}
	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}
	public String getColumn_value() {
		return column_value;
	}
	public void setColumn_value(String column_value) {
		this.column_value = column_value;
	}
	public String getColumn_desc() {
		return column_desc;
	}
	public void setColumn_desc(String column_desc) {
		this.column_desc = column_desc;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getIs_search() {
		return is_search;
	}
	public void setIs_search(Integer is_search) {
		this.is_search = is_search;
	}
	@Override
	public String toString() {
		return "Column [column_key=" + column_key + ", column_desc=" + column_desc + ", column_value=" + column_value
				+ ", create_time=" + create_time + ", modify_time=" + modify_time + ", is_search=" + is_search + "]";
	}
	
}
