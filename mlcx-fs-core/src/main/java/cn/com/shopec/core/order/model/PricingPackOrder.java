package cn.com.shopec.core.order.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 套餐订单表 数据实体类
 */
public class PricingPackOrder extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//套餐订单编号
	private String packOrderNo;
	//会员编号
	private String memberNo;
	//会员姓名
	private String memberName;
	//手机号
	private String mobilePhone;
	//套餐编号
	private String packageId;
	//套餐名称
	private String packageName;
	//套餐金额
	private Double packAmount;
	//套餐时长(以分钟为单位，库里只存数值)
	private Integer packMinute;
	//套餐订单总金额
	private Double packOrderAmount;
	//已使用套餐金额
	private Double useredOrderAmount;
	//剩余金额
	private Double remainOrderAmount;
	//已用时长
	private Integer userdMinute;
	//剩余时长
	private Integer residueMinute;
	//应付金额
	private Double payableAmount;
	//支付状态（0、未支付，1、已支付，2、部分支付-预留）
	private Integer payStatus;
	//支付方式(1、微信支付2、支付宝3、银行卡转账4、其他)
	private Integer paymentMethod;
	//外部支付流水号（支付宝、微信等）
		private String paymentFlowNo;
		//内部支付流水号
		private String partTradeFlowNo;
	//是否可用（0，不可用，1、可用；默认1）
	private Integer isAvailable;
	private Date vailableTime1;
	// 时间范围起（查询用）
	private Date vailableTime1Start;
	// 时间范围止（查询用）
	private Date vailableTime1End;	
	private Date vailableTime2;
	// 时间范围起（查询用）
	private Date vailableTime2Start;
	// 时间范围止（查询用）
	private Date vailableTime2End;	
	//发票id
	private String invoiceId;
	//发票编号
	private String invoiceNo;
	//发票状态
	private Integer invoiceStatus;
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
	private Integer operatorType;
	//当前时间（用于判断是否在套餐有效时间内）  zln
	private Date nowTime;
	/*Auto generated properties end*/
	//套餐类型(1.赠送类套餐，2.销售套餐)
		private Integer packageType;
	//订单套餐抵扣判断时间：开始
	private Date startTime;
	//订单套餐抵扣判断时间：结束
	private Date endTime;
	//套餐总数(报表)
	private String count;
	//套餐数(报表)）
	private String sum;
	//时间(报表)
	private String weeks;
	//套餐查询时间
	private Date yearNs;
	private Date monthNs;
	//查询时间（报表）
	private String timeDay;
	private String timeMonth;
	private Date createTimeStartMonth;
	private Date createTimeEndMonth;
	
	/**
	 * 是否为充值(0：否，1：是)
	 */
	private Integer isRecharge;
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return packOrderNo;
	}
	
	public Integer getPackageType() {
		return packageType;
	}
	
	

	public String getPaymentFlowNo() {
		return paymentFlowNo;
	}

	public void setPaymentFlowNo(String paymentFlowNo) {
		this.paymentFlowNo = paymentFlowNo;
	}

	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}

	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
	}

	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}

	public String getPackOrderNo(){
		return packOrderNo;
	}
	
	public void setPackOrderNo(String packOrderNo){
		this.packOrderNo = packOrderNo;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public String getPackageId(){
		return packageId;
	}
	
	public void setPackageId(String packageId){
		this.packageId = packageId;
	}
	
	public String getPackageName(){
		return packageName;
	}
	
	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
	
	public Double getPackAmount(){
		return packAmount;
	}
	
	public void setPackAmount(Double packAmount){
		this.packAmount = packAmount;
	}
	
	public Integer getPackMinute(){
		return packMinute;
	}
	
	public void setPackMinute(Integer packMinute){
		this.packMinute = packMinute;
	}
	
	public Integer getUserdMinute(){
		return userdMinute;
	}
	
	public void setUserdMinute(Integer userdMinute){
		this.userdMinute = userdMinute;
	}
	
	public Double getPayableAmount(){
		return payableAmount;
	}
	
	public void setPayableAmount(Double payableAmount){
		this.payableAmount = payableAmount;
	}
	
	public Integer getPayStatus(){
		return payStatus;
	}
	
	public void setPayStatus(Integer payStatus){
		this.payStatus = payStatus;
	}
	
	public Integer getPaymentMethod(){
		return paymentMethod;
	}
	
	public void setPaymentMethod(Integer paymentMethod){
		this.paymentMethod = paymentMethod;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public Date getVailableTime1(){
		return vailableTime1;
	}
	
	public void setVailableTime1(Date vailableTime1){
		this.vailableTime1 = vailableTime1;
	}
	
	public Date getVailableTime1Start(){
		return vailableTime1Start;
	}
	
	public void setVailableTime1Start(Date vailableTime1Start){
		this.vailableTime1Start = vailableTime1Start;
	}
	
	public Date getVailableTime1End(){
		return vailableTime1End;
	}
	
	public void setVailableTime1End(Date vailableTime1End){
		this.vailableTime1End = vailableTime1End;
	}	
	
	public Date getVailableTime2(){
		return vailableTime2;
	}
	
	public void setVailableTime2(Date vailableTime2){
		this.vailableTime2 = vailableTime2;
	}
	
	public Date getVailableTime2Start(){
		return vailableTime2Start;
	}
	
	public void setVailableTime2Start(Date vailableTime2Start){
		this.vailableTime2Start = vailableTime2Start;
	}
	
	public Date getVailableTime2End(){
		return vailableTime2End;
	}
	
	public void setVailableTime2End(Date vailableTime2End){
		this.vailableTime2End = vailableTime2End;
	}	
	
	public String getInvoiceId(){
		return invoiceId;
	}
	
	public void setInvoiceId(String invoiceId){
		this.invoiceId = invoiceId;
	}
	
	public String getInvoiceNo(){
		return invoiceNo;
	}
	
	public void setInvoiceNo(String invoiceNo){
		this.invoiceNo = invoiceNo;
	}
	
	public Integer getInvoiceStatus(){
		return invoiceStatus;
	}
	
	public void setInvoiceStatus(Integer invoiceStatus){
		this.invoiceStatus = invoiceStatus;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	public Double getPackOrderAmount() {
		return packOrderAmount;
	}

	public void setPackOrderAmount(Double packOrderAmount) {
		this.packOrderAmount = packOrderAmount;
	}

	public Double getUseredOrderAmount() {
		return useredOrderAmount;
	}

	public void setUseredOrderAmount(Double useredOrderAmount) {
		this.useredOrderAmount = useredOrderAmount;
	}

	public Double getRemainOrderAmount() {
		return remainOrderAmount;
	}

	public void setRemainOrderAmount(Double remainOrderAmount) {
		this.remainOrderAmount = remainOrderAmount;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	@Override
	public String toString() {
		return "PricingPackOrder [packOrderNo=" + packOrderNo + ", memberNo=" + memberNo + ", memberName=" + memberName
				+ ", mobilePhone=" + mobilePhone + ", packageId=" + packageId + ", packageName=" + packageName
				+ ", packAmount=" + packAmount + ", packMinute=" + packMinute + ", userdMinute=" + userdMinute
				+ ", residueMinute=" + residueMinute + ", payableAmount=" + payableAmount + ", payStatus=" + payStatus
				+ ", paymentMethod=" + paymentMethod + ", paymentFlowNo=" + paymentFlowNo + ", partTradeFlowNo="
				+ partTradeFlowNo + ", isAvailable=" + isAvailable + ", vailableTime1=" + vailableTime1
				+ ", vailableTime1Start=" + vailableTime1Start + ", vailableTime1End=" + vailableTime1End
				+ ", vailableTime2=" + vailableTime2 + ", vailableTime2Start=" + vailableTime2Start
				+ ", vailableTime2End=" + vailableTime2End + ", invoiceId=" + invoiceId + ", invoiceNo=" + invoiceNo
				+ ", invoiceStatus=" + invoiceStatus + ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", operatorId="
				+ operatorId + ", operatorType=" + operatorType + ", nowTime=" + nowTime + ", packageType="
				+ packageType + ", startTime=" + startTime + ", endTime=" + endTime + ", count=" + count + ", sum="
				+ sum + ", weeks=" + weeks + ", yearNs=" + yearNs + ", monthNs=" + monthNs + ", timeDay=" + timeDay
				+ ", timeMonth=" + timeMonth + ", createTimeStartMonth=" + createTimeStartMonth
				+ ", createTimeEndMonth=" + createTimeEndMonth + "]";
	}

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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public Date getYearNs() {
		return yearNs;
	}

	public void setYearNs(Date yearNs) {
		this.yearNs = yearNs;
	}

	public Date getMonthNs() {
		return monthNs;
	}

	public void setMonthNs(Date monthNs) {
		this.monthNs = monthNs;
	}

	public String getTimeDay() {
		return timeDay;
	}

	public void setTimeDay(String timeDay) {
		this.timeDay = timeDay;
	}

	public String getTimeMonth() {
		return timeMonth;
	}

	public void setTimeMonth(String timeMonth) {
		this.timeMonth = timeMonth;
	}

	public Integer getResidueMinute() {
		return residueMinute;
	}

	public void setResidueMinute(Integer residueMinute) {
		this.residueMinute = residueMinute;
	}

	public Date getCreateTimeStartMonth() {
		return createTimeStartMonth;
	}

	public void setCreateTimeStartMonth(Date createTimeStartMonth) {
		this.createTimeStartMonth = createTimeStartMonth;
	}

	public Date getCreateTimeEndMonth() {
		return createTimeEndMonth;
	}

	public void setCreateTimeEndMonth(Date createTimeEndMonth) {
		this.createTimeEndMonth = createTimeEndMonth;
	}

	public Integer getIsRecharge() {
		return isRecharge;
	}

	public void setIsRecharge(Integer isRecharge) {
		this.isRecharge = isRecharge;
	}


	
	
}
