package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 充电记录 数据实体类
 */
public class ChargingRecord extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//充电记录编号
	private String recordNo;
	//充电桩编号
	private String chargingPileNo;
	//充电枪编号
	private String chargingGunNo;
	//记录时间
	private Date recordTime;
	//记录时间 时间范围起（查询用）
	private Date recordTimeStart;
	//记录时间 时间范围止（查询用）
	private Date recordTimeEnd;	
	//交易流水号
	private String serialNumber;
	//应用卡号
	private String cardNumber;
	//开始时间
	private Date startTime;
	//开始时间 时间范围起（查询用）
	private Date startTimeStart;
	//开始时间 时间范围止（查询用）
	private Date startTimeEnd;	
	//结束时间
	private Date finishTime;
	//结束时间 时间范围起（查询用）
	private Date finishTimeStart;
	//结束时间 时间范围止（查询用）
	private Date finishTimeEnd;	
	//尖电量
	private String opintStart;
	//尖金额
	private Double opintFinish;
	//峰电量
	private String peakStart;
	//峰金额
	private Double peakFinish;
	//平电量
	private String flatStart;
	//平金额
	private Double flatFinish;
	//谷电量
	private String alleyStart;
	//谷金额
	private Double valleyFinish;
	//总起电量
	private String totalStart;
	//总止电量
	private String totalFinish;
	//总金额
	private Double totalMoney;
	//充电方式(1、电子钱包 2、有卡在线 3、二维码 4、门禁卡 5、连接电缆线充电)
	private Integer rechargeMode;
	//扣款标示(0.M1卡成功  2.M1卡失败)
	private Integer payFlag;
	//扣款前钱包余额
	private Double moneyBeforePay;
	//扣款后钱包余额
	private Double moneyAfterPay;
	//计费模型版本号
	private String schemeNo;
	//充电结束原因(1-16 全局故障,17-32 运行故障,33-40 BMS关机原因,41余额不足,42网络通信超时,43手动结束充电,44BMS主动结束充电,45未知原因)
	private String overReason;
	//车VIN号
	private String vin;
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	//创建日期
	private Date createTime;
	//创建日期 时间范围起（查询用）
	private Date createTimeStart;
	//创建日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新日期
	private Date updateTime;
	//更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	//更新日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return recordNo;
	}
	
	public String getRecordNo(){
		return recordNo;
	}
	
	public void setRecordNo(String recordNo){
		this.recordNo = recordNo;
	}
	
	public String getChargingPileNo(){
		return chargingPileNo;
	}
	
	public void setChargingPileNo(String chargingPileNo){
		this.chargingPileNo = chargingPileNo;
	}
	
	public String getChargingGunNo(){
		return chargingGunNo;
	}
	
	public void setChargingGunNo(String chargingGunNo){
		this.chargingGunNo = chargingGunNo;
	}
	
	public Date getRecordTime(){
		return recordTime;
	}
	
	public void setRecordTime(Date recordTime){
		this.recordTime = recordTime;
	}
	
	public Date getRecordTimeStart(){
		return recordTimeStart;
	}
	
	public void setRecordTimeStart(Date recordTimeStart){
		this.recordTimeStart = recordTimeStart;
	}
	
	public Date getRecordTimeEnd(){
		return recordTimeEnd;
	}
	
	public void setRecordTimeEnd(Date recordTimeEnd){
		this.recordTimeEnd = recordTimeEnd;
	}	
	
	public String getSerialNumber(){
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber){
		this.serialNumber = serialNumber;
	}
	
	public String getCardNumber(){
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber){
		this.cardNumber = cardNumber;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getStartTimeStart(){
		return startTimeStart;
	}
	
	public void setStartTimeStart(Date startTimeStart){
		this.startTimeStart = startTimeStart;
	}
	
	public Date getStartTimeEnd(){
		return startTimeEnd;
	}
	
	public void setStartTimeEnd(Date startTimeEnd){
		this.startTimeEnd = startTimeEnd;
	}	
	
	public Date getFinishTime(){
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime){
		this.finishTime = finishTime;
	}
	
	public Date getFinishTimeStart(){
		return finishTimeStart;
	}
	
	public void setFinishTimeStart(Date finishTimeStart){
		this.finishTimeStart = finishTimeStart;
	}
	
	public Date getFinishTimeEnd(){
		return finishTimeEnd;
	}
	
	public void setFinishTimeEnd(Date finishTimeEnd){
		this.finishTimeEnd = finishTimeEnd;
	}	
	
	public String getOpintStart(){
		return opintStart;
	}
	
	public void setOpintStart(String opintStart){
		this.opintStart = opintStart;
	}
	
	public Double getOpintFinish(){
		return opintFinish;
	}
	
	public void setOpintFinish(Double opintFinish){
		this.opintFinish = opintFinish;
	}
	
	public String getPeakStart(){
		return peakStart;
	}
	
	public void setPeakStart(String peakStart){
		this.peakStart = peakStart;
	}
	
	public Double getPeakFinish(){
		return peakFinish;
	}
	
	public void setPeakFinish(Double peakFinish){
		this.peakFinish = peakFinish;
	}
	
	public String getFlatStart(){
		return flatStart;
	}
	
	public void setFlatStart(String flatStart){
		this.flatStart = flatStart;
	}
	
	public Double getFlatFinish(){
		return flatFinish;
	}
	
	public void setFlatFinish(Double flatFinish){
		this.flatFinish = flatFinish;
	}
	
	public String getAlleyStart(){
		return alleyStart;
	}
	
	public void setAlleyStart(String alleyStart){
		this.alleyStart = alleyStart;
	}
	
	public Double getValleyFinish(){
		return valleyFinish;
	}
	
	public void setValleyFinish(Double valleyFinish){
		this.valleyFinish = valleyFinish;
	}
	
	public String getTotalStart(){
		return totalStart;
	}
	
	public void setTotalStart(String totalStart){
		this.totalStart = totalStart;
	}
	
	public String getTotalFinish(){
		return totalFinish;
	}
	
	public void setTotalFinish(String totalFinish){
		this.totalFinish = totalFinish;
	}
	
	public Double getTotalMoney(){
		return totalMoney;
	}
	
	public void setTotalMoney(Double totalMoney){
		this.totalMoney = totalMoney;
	}
	
	public Integer getRechargeMode(){
		return rechargeMode;
	}
	
	public void setRechargeMode(Integer rechargeMode){
		this.rechargeMode = rechargeMode;
	}
	
	public Integer getPayFlag(){
		return payFlag;
	}
	
	public void setPayFlag(Integer payFlag){
		this.payFlag = payFlag;
	}
	
	public Double getMoneyBeforePay(){
		return moneyBeforePay;
	}
	
	public void setMoneyBeforePay(Double moneyBeforePay){
		this.moneyBeforePay = moneyBeforePay;
	}
	
	public Double getMoneyAfterPay(){
		return moneyAfterPay;
	}
	
	public void setMoneyAfterPay(Double moneyAfterPay){
		this.moneyAfterPay = moneyAfterPay;
	}
	
	public String getSchemeNo(){
		return schemeNo;
	}
	
	public void setSchemeNo(String schemeNo){
		this.schemeNo = schemeNo;
	}
	
	public String getOverReason(){
		return overReason;
	}
	
	public void setOverReason(String overReason){
		this.overReason = overReason;
	}
	
	public String getVin(){
		return vin;
	}
	
	public void setVin(String vin){
		this.vin = vin;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "ChargingRecord ["
		 + "recordNo = " + recordNo + ", chargingPileNo = " + chargingPileNo + ", chargingGunNo = " + chargingGunNo + ", recordTime = " + recordTime + ", recordTimeStart = " + recordTimeStart + ", recordTimeEnd = " + recordTimeEnd
		 + ", serialNumber = " + serialNumber + ", cardNumber = " + cardNumber + ", startTime = " + startTime + ", startTimeStart = " + startTimeStart + ", startTimeEnd = " + startTimeEnd + ", finishTime = " + finishTime + ", finishTimeStart = " + finishTimeStart + ", finishTimeEnd = " + finishTimeEnd
		 + ", opintStart = " + opintStart + ", opintFinish = " + opintFinish + ", peakStart = " + peakStart + ", peakFinish = " + peakFinish
		 + ", flatStart = " + flatStart + ", flatFinish = " + flatFinish + ", alleyStart = " + alleyStart + ", valleyFinish = " + valleyFinish
		 + ", totalStart = " + totalStart + ", totalFinish = " + totalFinish + ", totalMoney = " + totalMoney + ", rechargeMode = " + rechargeMode
		 + ", payFlag = " + payFlag + ", moneyBeforePay = " + moneyBeforePay + ", moneyAfterPay = " + moneyAfterPay + ", schemeNo = " + schemeNo
		 + ", overReason = " + overReason + ", vin = " + vin + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
