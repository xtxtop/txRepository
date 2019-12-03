package cn.com.shopec.core.dailyrental.search.model;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索结果
 *
 */
public class SearchResult implements Serializable {

	private static final long serialVersionUID = -7812315822694602737L;

	private long total; // 匹配的总记录数

	private int pageNo; // 当前分页号(从1开始)

	private int pageRows; // 每页记录数

	private long costTime; // 搜索总耗时(单位为毫秒)

	private List<SolrDoc> datas; // 搜索到的文档集合

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

	public List<SolrDoc> getDatas() {
		return datas;
	}

	public void setDatas(List<SolrDoc> datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "SearchResult [total=" + total + ", pageNo=" + pageNo + ", pageRows=" + pageRows + ", costTime="
				+ costTime + ", datas=" + datas + "]";
	}

}
