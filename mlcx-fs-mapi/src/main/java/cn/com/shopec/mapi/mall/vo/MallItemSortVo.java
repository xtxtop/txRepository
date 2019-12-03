package cn.com.shopec.mapi.mall.vo;

public class MallItemSortVo {

	// 分类编号
	private String sortNo;

	// 父级分类编号
	private String parentSortNo;

	// 分类名称
	private String sortName;

	// 分类级别
	private Integer sortLevel;

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getParentSortNo() {
		return parentSortNo;
	}

	public void setParentSortNo(String parentSortNo) {
		this.parentSortNo = parentSortNo;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Integer getSortLevel() {
		return sortLevel;
	}

	public void setSortLevel(Integer sortLevel) {
		this.sortLevel = sortLevel;
	}

}
