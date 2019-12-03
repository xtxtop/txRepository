package cn.com.shopec.core.marketing.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 套餐产品表 数据实体类
 */
public class PricingPackage extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//套餐编号
	private String packageNo;
	//套餐名称
	private String packageName;
	//城市id(具体数值来自数据字典)
	private String cityId;
	//城市名称
	private String cityName;
	//销售价   买家应付金额
	private Double price;
	//套餐时长
	private Integer minutes;
	//套餐金额	买家收到可用金额
	private Double packAmount;
	//是否为公务用车套餐,0：是，1：否
	private Integer isOfficial;
	//套餐下发方式，0手动下发，1按月定时发送，2按星期定时发送
	private Integer issueType;
	//集团id
	private String companyId;
	//是否限制购买次数
	private Integer isLimitTimes;
	//购买次数(只有IS_LIMIT_TIMES为1时才有效)
	private Integer buyTimes;
	//有效天数
	private Integer availableDays;
	//上下架状态（0下架，1上架）
	private Integer isAvailable;
	//上下架原因
	private String availableReason;
	//上下架时间
	private Date availabelUpdateTime;
	//上下架时间 时间范围起（查询用）
	private Date availabelUpdateTimeStart;
	//上下架时间 时间范围止（查询用）
	private Date availabelUpdateTimeEnd;	
	//审核状态（0、未审核，1、已审核，2、审核不通过）
	private Integer cencorStatus;
	//审核备注
	private String cencorMemo;
	//审核时间
	private Date cencorTime;
	//审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;	
	//审核人id
	private String cencorId;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private String operatorId;
	//操作人类型
	private Integer operatorType;
	//套餐类型(1.赠送类套餐，2.销售套餐 4.订单分享赠送套餐)
	private Integer packageType;
	//套餐每日抵扣上限
	private Integer deductionCeiling;
	//套餐销售额总计（报表）
	private Double sum;
	//套餐销售数量（报表）
	private Long count;
	//报表查询时间
	private String time;
	//是否删除
	private Integer isDeleted;
	//是否查询的手工扣费的默认套餐(0.不查，1.查 只针对列表查询用)
	private Integer isRecharge;
	//报表数据
	private List<Long> counts = new ArrayList<Long>();
	private List<Double> sums = new ArrayList<Double>();
	private List<String> times = new ArrayList<String>();
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return packageNo;
	}
	
	public String getAvailableReason() {
		return availableReason;
	}

	public void setAvailableReason(String availableReason) {
		this.availableReason = availableReason;
	}

	public String getPackageNo(){
		return packageNo;
	}
	
	public void setPackageNo(String packageNo){
		this.packageNo = packageNo;
	}
	
	public String getPackageName(){
		return packageName;
	}
	
	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
	
	
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public Double getPrice(){
		return price;
	}
	
	public void setPrice(Double price){
		this.price = price;
	}
	
	public Integer getMinutes(){
		return minutes;
	}
	
	public void setMinutes(Integer minutes){
		this.minutes = minutes;
	}
	
	public Double getPackAmount() {
		return packAmount;
	}

	public void setPackAmount(Double packAmount) {
		this.packAmount = packAmount;
	}

	public Integer getAvailableDays(){
		return availableDays;
	}
	
	public void setAvailableDays(Integer availableDays){
		this.availableDays = availableDays;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public Date getAvailabelUpdateTime(){
		return availabelUpdateTime;
	}
	
	public void setAvailabelUpdateTime(Date availabelUpdateTime){
		this.availabelUpdateTime = availabelUpdateTime;
	}
	
	public Date getAvailabelUpdateTimeStart(){
		return availabelUpdateTimeStart;
	}
	
	public void setAvailabelUpdateTimeStart(Date availabelUpdateTimeStart){
		this.availabelUpdateTimeStart = availabelUpdateTimeStart;
	}
	
	public Date getAvailabelUpdateTimeEnd(){
		return availabelUpdateTimeEnd;
	}
	
	public void setAvailabelUpdateTimeEnd(Date availabelUpdateTimeEnd){
		this.availabelUpdateTimeEnd = availabelUpdateTimeEnd;
	}	
	
	public Integer getCencorStatus(){
		return cencorStatus;
	}
	
	public void setCencorStatus(Integer cencorStatus){
		this.cencorStatus = cencorStatus;
	}
	
	public String getCencorMemo(){
		return cencorMemo;
	}
	
	public void setCencorMemo(String cencorMemo){
		this.cencorMemo = cencorMemo;
	}
	
	public Date getCencorTime(){
		return cencorTime;
	}
	
	public void setCencorTime(Date cencorTime){
		this.cencorTime = cencorTime;
	}
	
	public Date getCencorTimeStart(){
		return cencorTimeStart;
	}
	
	public void setCencorTimeStart(Date cencorTimeStart){
		this.cencorTimeStart = cencorTimeStart;
	}
	
	public Date getCencorTimeEnd(){
		return cencorTimeEnd;
	}
	
	public void setCencorTimeEnd(Date cencorTimeEnd){
		this.cencorTimeEnd = cencorTimeEnd;
	}	
	
	public String getCencorId(){
		return cencorId;
	}
	
	public void setCencorId(String cencorId){
		this.cencorId = cencorId;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
    
	public Integer getPackageType() {
		return packageType;
	}

	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}
	
	public Integer getDeductionCeiling() {
		return deductionCeiling;
	}

	public void setDeductionCeiling(Integer deductionCeiling) {
		this.deductionCeiling = deductionCeiling;
	}

	@Override
	public String toString() {
		return "PricingPackage [packageNo=" + packageNo + ", packageName=" + packageName + ", cityId=" + cityId
				+ ", cityName=" + cityName + ", price=" + price + ", minutes=" + minutes + ", availableDays="
				+ availableDays + ", isAvailable=" + isAvailable + ", availableReason=" + availableReason
				+ ", availabelUpdateTime=" + availabelUpdateTime + ", availabelUpdateTimeStart="
				+ availabelUpdateTimeStart + ", availabelUpdateTimeEnd=" + availabelUpdateTimeEnd + ", cencorStatus="
				+ cencorStatus + ", cencorMemo=" + cencorMemo + ", cencorTime=" + cencorTime + ", cencorTimeStart="
				+ cencorTimeStart + ", cencorTimeEnd=" + cencorTimeEnd + ", cencorId=" + cencorId + ", createTime="
				+ createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd="
				+ updateTimeEnd + ", operatorId=" + operatorId + ", operatorType=" + operatorType + "]";
	}

	public Integer getIsOfficial() {
		return isOfficial;
	}

	public void setIsOfficial(Integer isOfficial) {
		this.isOfficial = isOfficial;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getIsLimitTimes() {
		return isLimitTimes;
	}

	public void setIsLimitTimes(Integer isLimitTimes) {
		this.isLimitTimes = isLimitTimes;
	}

	public Integer getBuyTimes() {
		return buyTimes;
	}

	public void setBuyTimes(Integer buyTimes) {
		this.buyTimes = buyTimes;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<Long> getCounts() {
		return counts;
	}

	public void setCounts(List<Long> counts) {
		this.counts = counts;
	}

	public List<Double> getSums() {
		return sums;
	}

	public void setSums(List<Double> sums) {
		this.sums = sums;
	}

	public List<String> getTimes() {
		return times;
	}

	public void setTimes(List<String> times) {
		this.times = times;
	}

	public Integer getIsRecharge() {
		return isRecharge;
	}

	public void setIsRecharge(Integer isRecharge) {
		this.isRecharge = isRecharge;
	}

	
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	
}
