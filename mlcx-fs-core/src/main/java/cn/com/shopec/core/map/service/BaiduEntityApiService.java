package cn.com.shopec.core.map.service;

import java.util.List;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.map.model.Column;
import cn.com.shopec.core.map.model.Entity;
import cn.com.shopec.core.map.model.EntityResultInfo;

/**
 * 百度操作Entity接口的service
 *
 */
public interface BaiduEntityApiService {

	/**
	 * 添加Entity属性
	 * 
	 * @param entity
	 *            参数说明ak，必选service_id，必选，column_key属性名称，必选，最多创建5个属性字段，
	 *            同一个service下entity的column_key不能重复。column_desc属性描述，可选。is_search可选
	 * @return
	 */
	public ResultInfo<EntityResultInfo> addColumn(Column column);
	
	public ResultInfo<EntityResultInfo> deleteColumn(Column column);
	
	public ResultInfo<EntityResultInfo> listColumn();

	/**
	 * 添加entity对象
	 * 
	 * @param entity,isContainSKey(是否包含属性值，0、不包含，1、包含)
	 * 
	 * @return
	 */
	public ResultInfo<EntityResultInfo> addEntity(Entity entity,Integer isContainSKey);
	
	public ResultInfo<EntityResultInfo> deleteEntity(Entity entity);
	
	public ResultInfo<EntityResultInfo> updateEntity(Entity entity);
	
	public ResultInfo<EntityResultInfo> ListEntity(List<Entity> entities);
}
