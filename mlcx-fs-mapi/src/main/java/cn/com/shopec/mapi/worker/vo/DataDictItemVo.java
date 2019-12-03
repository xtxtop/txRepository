package cn.com.shopec.mapi.worker.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 数据字典的明细项表 数据实体类
 */
public class DataDictItemVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//数据字典项id
	private String dataDictItemId;
	//字典项的值
	private String itemValue;
	public String getDataDictItemId() {
		return dataDictItemId;
	}
	public void setDataDictItemId(String dataDictItemId) {
		this.dataDictItemId = dataDictItemId;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	@Override
	public String toString() {
		return "DataDictItemVo [dataDictItemId=" + dataDictItemId + ", itemValue=" + itemValue + "]";
	}
	
	
	
}
