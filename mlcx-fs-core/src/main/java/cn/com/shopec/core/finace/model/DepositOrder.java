package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 押金支付订单表 数据实体类
 */
public class DepositOrder extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//押金支付编号
	private String depositOrderNo;
	//会员编号
	private String memberNo;
	//押金金额
	private Double depositAmount;
	//应付金额
	private Double payableAmount;
	//支付状态（0、未支付，1、已支付，默认0）
	private Integer payStatus;
	//支付方式（1、支付宝、2、微信）
	private Integer paymentMethod;
	//外部支付流水号（支付宝、微信等）
	private String paymentFlowNo;
	//内部支付流水号
	private String partTradeFlowNo;
	//支付时间
	private Date paymentTime;
	//支付时间 时间范围起（查询用）
	private Date paymentTimeStart;
	//支付时间 时间范围止（查询用）
	private Date paymentTimeEnd;	
	//剩余押金金额
	private Double remainAmount;
	//已扣除押金金额
	private Double deductedAmount;
	//已退押金金额
	private Double refundAmount;
	//已冻结押金金额（申请退款后会有冻结，退款完成后会解冻，并将冻结金额加入已退款金额）
	private Double frozenAmount;
	//可用状态（1、可用，0、不可用，默认0）
	private Integer isAvailable;
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
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	//交押金的地区
	private String addrRegion;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return depositOrderNo;
	}
	
	public String getDepositOrderNo(){
		return depositOrderNo;
	}
	
	public void setDepositOrderNo(String depositOrderNo){
		this.depositOrderNo = depositOrderNo;
	}
	
	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}

	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
	}

	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public Double getDepositAmount(){
		return depositAmount;
	}
	
	public void setDepositAmount(Double depositAmount){
		this.depositAmount = depositAmount;
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
	
	public String getPaymentFlowNo(){
		return paymentFlowNo;
	}
	
	public void setPaymentFlowNo(String paymentFlowNo){
		this.paymentFlowNo = paymentFlowNo;
	}
	
	public Date getPaymentTime(){
		return paymentTime;
	}
	
	public void setPaymentTime(Date paymentTime){
		this.paymentTime = paymentTime;
	}
	
	public Date getPaymentTimeStart(){
		return paymentTimeStart;
	}
	
	public void setPaymentTimeStart(Date paymentTimeStart){
		this.paymentTimeStart = paymentTimeStart;
	}
	
	public Date getPaymentTimeEnd(){
		return paymentTimeEnd;
	}
	
	public void setPaymentTimeEnd(Date paymentTimeEnd){
		this.paymentTimeEnd = paymentTimeEnd;
	}	
	
	public Double getRemainAmount(){
		return remainAmount;
	}
	
	public void setRemainAmount(Double remainAmount){
		this.remainAmount = remainAmount;
	}
	
	public Double getDeductedAmount(){
		return deductedAmount;
	}
	
	public void setDeductedAmount(Double deductedAmount){
		this.deductedAmount = deductedAmount;
	}
	
	public Double getRefundAmount(){
		return refundAmount;
	}
	
	public void setRefundAmount(Double refundAmount){
		this.refundAmount = refundAmount;
	}
	
	public Double getFrozenAmount(){
		return frozenAmount;
	}
	
	public void setFrozenAmount(Double frozenAmount){
		this.frozenAmount = frozenAmount;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
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
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	public String getAddrRegion() {
		return addrRegion;
	}

	public void setAddrRegion(String addrRegion) {
		this.addrRegion = addrRegion;
	}
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	

	@Override
	public String toString() {
		return "DepositOrder [depositOrderNo=" + depositOrderNo + ", memberNo=" + memberNo + ", depositAmount="
				+ depositAmount + ", payableAmount=" + payableAmount + ", payStatus=" + payStatus + ", paymentMethod="
				+ paymentMethod + ", paymentFlowNo=" + paymentFlowNo + ", partTradeFlowNo=" + partTradeFlowNo
				+ ", paymentTime=" + paymentTime + ", paymentTimeStart=" + paymentTimeStart + ", paymentTimeEnd="
				+ paymentTimeEnd + ", remainAmount=" + remainAmount + ", deductedAmount=" + deductedAmount
				+ ", refundAmount=" + refundAmount + ", frozenAmount=" + frozenAmount + ", isAvailable=" + isAvailable
				+ ", createTime=" + createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorType=" + operatorType + ", operatorId=" + operatorId
				+ "]";
	}
}
