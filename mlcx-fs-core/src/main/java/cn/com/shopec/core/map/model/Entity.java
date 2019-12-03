package cn.com.shopec.core.map.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 鹰眼轨迹对象entity
 *
 */
public class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6069771558737217539L;
	//entity对象名称entity_name，作为其唯一标识。仅支持中文、英文大小字母、英文下划线"_"、英文横线"-"和数字。同一service服务中entity_name不可重复。 
	private String entityName;
	//活跃时间 active_time	UNIX时间戳  可选，指定该字段时,返回从该时间点之后仍有位置变动的entity。
	private Long activeTime;
    //return_type 返回结果的类型 int 可选。0代表返回全部结果，1代表只返回entity_name字段。默认值为0。
	private Integer returnType;
    //page_index 	分页索引 	int(1到2^32-1) 可选，默认值为1。page_index与page_size一起计算从第几条结果返回，代表返回第几页。
	private Integer pageIndex;
    //page_size 	分页大小 	int(1-1000) 可选，默认值为100。page_size与page_index一起计算从第几条结果返回，代表返回结果中每页有几条记录。
	private Integer pageSize;
	//自定义属性column_key 自定义属性字段名称 ,最多创建5个属性字段，同一个service下entity的column_key不能重复。 
	private List<Column> column;
	//is_search 取值0,1 可选。1代表检索字段，0代表非检索字段。最多设置2个检索字段，且字段一经创建，此属性将不可更改。
	private Integer isSearch; 
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Long getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Long activeTime) {
		this.activeTime = activeTime;
	}
	public Integer getReturnType() {
		return returnType;
	}
	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<Column> getColumn() {
		return column;
	}
	public void setColumn(List<Column> column) {
		this.column = column;
	}
	public Integer getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(Integer isSearch) {
		this.isSearch = isSearch;
	}
	@Override
	public String toString() {
		return "Entity [entityName=" + entityName + ", activeTime=" + activeTime + ", returnType=" + returnType
				+ ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", column=" + column + ", isSearch=" + isSearch + "]";
	}
}
