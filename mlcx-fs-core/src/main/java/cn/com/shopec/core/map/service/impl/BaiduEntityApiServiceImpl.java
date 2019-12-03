package cn.com.shopec.core.map.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.utils.httpClient.HttpClientUtil;
import cn.com.shopec.core.map.model.Column;
import cn.com.shopec.core.map.model.Entity;
import cn.com.shopec.core.map.model.EntityResultInfo;
import cn.com.shopec.core.map.service.BaiduEntityApiService;

/**
 * 终端设备服务器接口的service实现
 *
 */
@Service
public class BaiduEntityApiServiceImpl implements BaiduEntityApiService {
	
	private static final Log log = LogFactory.getLog(BaiduEntityApiServiceImpl.class);
	//百度ak
	@Value("${ak}")
	private String AK;
	// 鹰眼服务service_id
	@Value("${serviceId}")
	private Integer SERVICEID;
	//添加entity属性uri
	private final String addColumnURI = "http://api.map.baidu.com/trace/v2/entity/addcolumn";
	//删除entity属性uri
	private final String deleteColumnURI = "http://api.map.baidu.com/trace/v2/entity/deletecolumn"; 
	//查询entity属性uri
	private final String listColumnURI = "http://api.map.baidu.com/trace/v2/entity/listcolumn"; 
	//添加entity对象uri
	private final String addEntityURI = "http://api.map.baidu.com/trace/v2/entity/add";
	//删除entity对象uri
	private final String deleteEntityURI = "http://api.map.baidu.com/trace/v2/entity/delete";
	//修改entity对象uri
	private final String updateEntityURI = "http://api.map.baidu.com/trace/v2/entity/update";
	//查询entity对象uri
	private final String listEntityURI = "http://api.map.baidu.com/trace/v2/entity/list";
	
	/**
	 * 添加对象属性
	 * 注意：当返回ResultInfo的code值为1时，不代表添加成功，要进一步getData判断EntityResultInfo中的status值（0为成功）或者message值
	 */
	@Override
	public ResultInfo<EntityResultInfo> addColumn(Column column) throws JsonSyntaxException {
		ResultInfo<EntityResultInfo> resultInfo = new ResultInfo<EntityResultInfo>();
		if(column!=null){
			if(column.getColumn_key()==null||"".equals(column.getColumn_key())){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("columnKey为空");
			}else if(column.getColumn_desc()==null||"".equals(column.getColumn_desc())){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("columnDesc为空");
			}else{
				String param = "";
				if(column.getIs_search()!=null&&!"".equals(column.getIs_search())){
					param = this.addColumnURI+"?ak="+AK+"&service_id="+SERVICEID+"&column_key="+column.getColumn_key()+"&column_desc="+column.getColumn_desc()+"&is_search"+column.getIs_search();
				}else{
					param = this.addColumnURI+"?ak="+AK+"&service_id="+SERVICEID+"&column_key="+column.getColumn_key()+"&column_desc="+column.getColumn_desc();
				}
				String result = HttpClientUtil.get(param);
				Gson gson = new Gson();
				EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(entityResultInfo);
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("column为空");
		}
		return resultInfo;
	}
	@Override
	public ResultInfo<EntityResultInfo> deleteColumn(Column column) {
		ResultInfo<EntityResultInfo> resultInfo = new ResultInfo<>();
		if(column!=null){
			if(column.getColumn_key()==null||"".equals(column.getColumn_key())){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("columnKey为空");
			}else{
				String param = this.deleteColumnURI+"?ak="+AK+"&service_id="+SERVICEID+"&column_key="+column.getColumn_key();
				String result = HttpClientUtil.get(param);
				Gson gson = new Gson();
				EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(entityResultInfo);
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("column为空");
		}
		return resultInfo;
	}
	@Override
	public ResultInfo<EntityResultInfo> listColumn() {
		ResultInfo<EntityResultInfo> resultInfo = new ResultInfo<>();
		String param = this.listColumnURI+"?ak="+AK+"&service_id="+SERVICEID;
		String result = "";
		try {
			result = HttpClientUtil.get(param);
			Gson gson = new Gson();
			EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(entityResultInfo);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(result);
			log.error("listColumn异常"+result);
		}
		return resultInfo;
	}
	/**
	 * 添加对象属性
	 * 注意：当返回ResultInfo的code值为1时，不代表添加成功，要进一步getData判断EntityResultInfo中的status值（0为成功）或者message值
	 */
	@Override
	public ResultInfo<EntityResultInfo> addEntity(Entity entity,Integer isContainSKey) {
		ResultInfo<EntityResultInfo> resultInfo = new ResultInfo<>();
		if(entity!=null){
			if(entity.getEntityName()==null||"".equals(entity.getEntityName())){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("entityName为空");
			}else if(isContainSKey==1){
				if(entity.getColumn()==null||entity.getColumn().size()==0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("column为空");
				}else{
					String columnKey = "";
					for(Column c:entity.getColumn()){
						columnKey+=c.getColumn_key()+"="+c.getColumn_value()+"&";
					}
					columnKey=columnKey.substring(0,columnKey.length()-1);
					String param = this.addEntityURI+"?ak="+AK+"&service_id="+SERVICEID+"&entity_name="+entity.getEntityName()+"&"+columnKey;
					String result = HttpClientUtil.get(param);
					Gson gson = new Gson();
					EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(entityResultInfo);
				}
			}else{
				String param = this.addEntityURI+"?ak="+AK+"&service_id="+SERVICEID+"&entity_name ="+entity.getEntityName();
				String result = HttpClientUtil.get(param);
				Gson gson = new Gson();
				EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(entityResultInfo);
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("entity为空");
		}
		return resultInfo;
	}
	@Override
	public ResultInfo<EntityResultInfo> deleteEntity(Entity entity) {
		ResultInfo<EntityResultInfo> resultInfo = new ResultInfo<>();
		if(entity!=null){
			if(entity.getEntityName()==null||"".equals(entity.getEntityName())){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("entityName为空");
			}else{
				String param = this.deleteEntityURI+"?ak="+AK+"&service_id="+SERVICEID+"&entity_name="+entity.getEntityName();
				String result = HttpClientUtil.get(param);
				Gson gson = new Gson();
				EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(entityResultInfo);
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("entity为空");
		}
		return resultInfo;
	}
	@Override
	public ResultInfo<EntityResultInfo> updateEntity(Entity entity) {
		ResultInfo<EntityResultInfo> resultInfo = new ResultInfo<>();
		if(entity!=null){
			if(entity.getEntityName()==null||"".equals(entity.getEntityName())){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("entityName为空");
			}else{
				String param = this.updateEntityURI+"?ak="+AK+"&service_id="+SERVICEID+"&entity_name="+entity.getEntityName();
				String result = HttpClientUtil.get(param);
				Gson gson = new Gson();
				EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(entityResultInfo);
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("entity为空");
		}
		return resultInfo;
	}
	@Override
	public ResultInfo<EntityResultInfo> ListEntity(List<Entity> entities) {
		ResultInfo<EntityResultInfo> resultInfo = new ResultInfo<>();
		StringBuffer sb = new StringBuffer();
		String columnKey = "";
		if(entities!=null&&entities.size()>0){
			for(Entity entity:entities){
				if(entity.getEntityName()!=null&&!"".equals(entity.getEntityName())){
					sb.append(entity.getEntityName()+",");
				}
				if(entity.getColumn()!=null&&entity.getColumn().size()>0){
					for(Column c:entity.getColumn()){
						if(c.getIs_search()!=null&&c.getIs_search()==1){
							columnKey+=c.getColumn_key()+"="+c.getColumn_desc()+",";
						}
					}
					if(columnKey.length()>0){
						columnKey=columnKey.substring(0,columnKey.length()-1);
					}
				}
			}
		}
		if(sb.length()>0){
			sb.delete(sb.length()-1, sb.length());
		}
		String entity_names = sb.toString();
		String str [] = entity_names.split(",");
		String param = "";
		if(str.length>0&&!"".equals(columnKey)){
			param = this.listEntityURI+"?ak="+AK+"&service_id="+SERVICEID+"&entity_names="+entity_names;
		}else if(str.length==0&&!"".equals(columnKey)){
			param = this.listEntityURI+"?ak="+AK+"&service_id="+SERVICEID+"&"+columnKey;
		}else if(str.length>0&&!"".equals(columnKey)){
			param = this.listEntityURI+"?ak="+AK+"&service_id="+SERVICEID+"&entity_names="+entity_names+"&"+columnKey;
		}else{
			param = this.listEntityURI+"?ak="+AK+"&service_id="+SERVICEID;
		}
		String result = "";
		try {
			result = HttpClientUtil.get(param);
			Gson gson = new Gson();
			EntityResultInfo entityResultInfo = gson.fromJson(result,EntityResultInfo.class);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(entityResultInfo);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(result);
			log.error("listColumn异常"+result);
		}
		return resultInfo;
	}
	public static void main(String[] args) {
		BaiduEntityApiServiceImpl b = new BaiduEntityApiServiceImpl();
		b.ListEntity(null);
	}
}
