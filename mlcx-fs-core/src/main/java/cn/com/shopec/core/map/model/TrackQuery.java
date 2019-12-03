package cn.com.shopec.core.map.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 鹰眼轨迹对象track
 *
 */
public class TrackQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7225322042578482383L;
	//起始时间 ,必选
	private Date startTime;
	//结束时间 必选,结束时间不超过当前时间，不能早于起始时间，且与起始时间差在24小时之内。
	private Date endTime;
	//entity唯一标识,必选
	private String entityName;
	//simple_return 可选，默认值是0：返回完整结果；simple_return=1时，仅返回轨迹经纬度和速度；simple_return=2时，仅返回里程。
	private Integer simpleReturn;
	//返回结果的排序规则 ,可选，默认值是0：返回轨迹点按loc_time从大到小排序；当设为1时，则反之。
	private Integer sortType;
	//是否返回纠偏后轨迹 ,可选，默认值是0。0：关闭轨迹纠偏，返回原始轨迹,1：打开轨迹纠偏，返回纠偏后轨迹。
	private Integer isProcessed;
	//	need_denoise：去噪，默认为1
	private Integer needDenoise;
	//	need_vacuate：抽稀，默认为1
	private Integer needVacuate;
	//	need_mapmatch：绑路,默认值为1
	private Integer needMapmatch;
	//分页索引 int(1到2^21-1)默认值为1 ,可选，与page_size一起计算从第几条结果返回，代表返回第几页。
	private Integer pageIndex;
	//page_size 分页大小 ,int(1-5000)默认值为100,可选字段，返回结果最大个数与page_index一起计算从第几条结果返回，代表返回结果中每页有几条记录。 
	private Integer pageSize = 1000;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Integer getSimpleReturn() {
		return simpleReturn;
	}
	public void setSimpleReturn(Integer simpleReturn) {
		this.simpleReturn = simpleReturn;
	}
	public Integer getSortType() {
		return sortType;
	}
	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}
	public Integer getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(Integer isProcessed) {
		this.isProcessed = isProcessed;
	}
	public Integer getNeedDenoise() {
		return needDenoise;
	}
	public void setNeedDenoise(Integer needDenoise) {
		this.needDenoise = needDenoise;
	}
	public Integer getNeedVacuate() {
		return needVacuate;
	}
	public void setNeedVacuate(Integer needVacuate) {
		this.needVacuate = needVacuate;
	}
	public Integer getNeedMapmatch() {
		return needMapmatch;
	}
	public void setNeedMapmatch(Integer needMapmatch) {
		this.needMapmatch = needMapmatch;
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
	@Override
	public String toString() {
		return "TrackQuery [startTime=" + startTime + ", endTime=" + endTime + ", entityName=" + entityName
				+ ", simpleReturn=" + simpleReturn + ", sortType=" + sortType + ", isProcessed=" + isProcessed
				+ ", needDenoise=" + needDenoise + ", needVacuate=" + needVacuate + ", needMapmatch=" + needMapmatch
				+ ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + "]";
	}
	
}
