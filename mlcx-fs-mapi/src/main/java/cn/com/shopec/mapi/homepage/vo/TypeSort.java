package cn.com.shopec.mapi.homepage.vo;

public class TypeSort {

	private String subId;
	private String subName;

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public TypeSort(String subId, String subName) {
		super();
		this.subId = subId;
		this.subName = subName;
	}

}
