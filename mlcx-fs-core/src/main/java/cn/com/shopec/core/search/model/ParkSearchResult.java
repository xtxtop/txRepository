package cn.com.shopec.core.search.model;

import java.io.Serializable;
import java.util.List;

/**
 * 场站搜索结果
 *
 */
public class ParkSearchResult implements Serializable {

	private static final long serialVersionUID = -7812315822694602737L;

	private long total; //匹配的总记录数
	
	private int pageNo; //当前分页号(从1开始)
	
	private int pageRows; //每页记录数
	
	private long costTime; //搜索总耗时(单位为毫秒)
	
	private List<ParkSearchResultDoc> parks; //搜索到的文档集合
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageRows() {
		return pageRows;
	}

	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	public List<ParkSearchResultDoc> getParks() {
		return parks;
	}

	public void setParks(List<ParkSearchResultDoc> parks) {
		this.parks = parks;
	}

	@Override
	public String toString() {
		return "ParkSearchResult [total=" + total + ", pageNo=" + pageNo + ", pageRows=" + pageRows + ", costTime="
				+ costTime + ", parks=" + parks + "]";
	}
	
	
}
