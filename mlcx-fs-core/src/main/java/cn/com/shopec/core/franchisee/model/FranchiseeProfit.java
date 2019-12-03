package cn.com.shopec.core.franchisee.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 加盟商收益表 数据实体类
 */
public class FranchiseeProfit extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 加盟商收益编号
	private String franchiseeProfitNo;
	// 加盟商编号
	private String franchiseeNo;
	// 加盟商名称
	private String franchiseeName;
	// 订单编号
	private String orderNo;
	// 订单金额
	private Double orderAmount;
	// 收入金额（扣除优惠）
	private Double incomeAmount;
	// 收益类型，0：车辆，1：场站，2：车辆和场站
	private Integer profitType;
	// 分润比例（按车）
	private Double carProportion;
	//车辆或者场站编号
	private String carOrParkNo;
	// 分润比例（按场站）
	private Double parkProportion;
	// 收益金额（车）
	private Double carProfit;
	// 收益金额（场站）
	private Double parkProfit;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	//查询时间
	private String queryTime;
	//场站名称
	private String actualTakeLoacton;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return franchiseeProfitNo;
	}

	public String getFranchiseeProfitNo() {
		return franchiseeProfitNo;
	}

	public void setFranchiseeProfitNo(String franchiseeProfitNo) {
		this.franchiseeProfitNo = franchiseeProfitNo;
	}

	public String getFranchiseeNo() {
		return franchiseeNo;
	}

	public void setFranchiseeNo(String franchiseeNo) {
		this.franchiseeNo = franchiseeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getProfitType() {
		return profitType;
	}

	public void setProfitType(Integer profitType) {
		this.profitType = profitType;
	}

	public Double getCarProportion() {
		return carProportion;
	}

	public void setCarProportion(Double carProportion) {
		this.carProportion = carProportion;
	}

	public Double getParkProportion() {
		return parkProportion;
	}

	public void setParkProportion(Double parkProportion) {
		this.parkProportion = parkProportion;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "FranchiseeProfit [" + "franchiseeProfitNo = " + franchiseeProfitNo + ", franchiseeNo = " + franchiseeNo
				+ ", orderNo = " + orderNo + ", orderAmount = " + orderAmount + ", profitType = " + profitType
				+ ", carProportion = " + carProportion + ", parkProportion = " + parkProportion + ", createTime = "
				+ createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
				+ ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = "
				+ updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId + "]";
	}

	public Double getCarProfit() {
		return carProfit;
	}

	public void setCarProfit(Double carProfit) {
		this.carProfit = carProfit;
	}

	public Double getParkProfit() {
		return parkProfit;
	}

	public void setParkProfit(Double parkProfit) {
		this.parkProfit = parkProfit;
	}

	public Double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public String getFranchiseeName() {
		return franchiseeName;
	}

	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	public String getCarOrParkNo() {
		return carOrParkNo;
	}

	public void setCarOrParkNo(String carOrParkNo) {
		this.carOrParkNo = carOrParkNo;
	}

	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	public String getActualTakeLoacton() {
		return actualTakeLoacton;
	}

	public void setActualTakeLoacton(String actualTakeLoacton) {
		this.actualTakeLoacton = actualTakeLoacton;
	}

}
