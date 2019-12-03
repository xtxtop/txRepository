package cn.com.shopec.mgt.monitor.vo;

import java.io.Serializable;
import java.util.List;


public class EntityNames implements Serializable{
	
	private static final long serialVersionUID = 858773864852998603L;
	
	private List<String> entityNames;

	public List<String> getEntityNames() {
		return entityNames;
	}

	public void setEntityNames(List<String> entityNames) {
		this.entityNames = entityNames;
	}
	
}
