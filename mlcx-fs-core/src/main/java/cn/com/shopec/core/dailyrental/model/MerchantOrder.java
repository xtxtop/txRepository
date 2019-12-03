package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 商家订单表 数据实体类
 */
public class MerchantOrder extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//商家订单id
	private String merchantOrderId;
	//商家id
	private String merchantId;
	//订单编号
	private String orderNo;
	//会员编号
	private String memberNo;
	//会员姓名
	private String memberName;
	//车型id
	private String carModelId;
	//车型名称
	private String carModelName;
	//取车门店编号
	private String takeCarParkNo;
	//取车门店名称
	private String takeCarParkName;
	//取车门店编号
	private String returnCarParkNo;
	//还车门店名称
	private String returnCarParkName;
	//商家订单状态（0:待签订单，1：接受订单，2：拒绝订单，7、待换车（交车前换车）,3：交车，4：换车，5：验车 6：取消 ）
	private Integer merchantOrderStatus;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//换车后车型id
	private String replaceCarModelId;
	//换车后车型名称
	private String replaceCarModelName;
	//更新后车辆编号
	private String replaceCarNo;
	//更换后车牌号
	private String replaceCarPlateNo;
	//换车后追加金额
	private Double additionalAmount;
	//原车是否下线(1 是 0否)
	private Integer isOnline;
	//换车备注
	private String replaceCarMemo;
	//换车凭证图片1
	private String replaceCarProofUrl1;
	//换车凭证图片2
	private String replaceCarProofUrl2;
	//换车凭证图片2
	private String replaceCarProofUrl3;
	//拒单理由类型（取数据字典）
	private String refuseOrderType;
	//拒单详细说明
	private String refuseOrderDetail;
	//交车凭证图片1
	private String confirmCarProofUrl1;
	//交车凭证图片2
	private String confirmCarProofUrl2;
	//交车凭证图片3
	private String confirmCarProofUrl3;
	//交车说明
	private String confirmCarDetail;
	//验车状态(0、非正常 1、 正常)
	private String checkCarStatus;
	//附加服务类型(清洗，维修，事故)
	private String additionalService;
	//验车详细说明
	private String checkCarDetail;
	//验证凭证图片1
	private String checkCarProofUrl1;
	//验证凭证图片2
	private String checkCarProofUrl2;
	//验证凭证图片3
	private String checkCarProofUrl3;
	//超时费用
	private Double overtimeAmount;
	//服务附加费
	private Double additionalServiceFee;
	//指派人（1、平台自动指派 2、后台人工操作指派）
	private Integer assignee;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private String operatorId;
	//操作人类型
	private Integer operatorType;
	
	//查询状态(1:待处理,2:验车,3:完成)
	private Integer queryStatus;
	//查询城市
	private String cityId;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return merchantOrderId;
	}
	
	public String getMerchantOrderId(){
		return merchantOrderId;
	}
	
	public void setMerchantOrderId(String merchantOrderId){
		this.merchantOrderId = merchantOrderId;
	}
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
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
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
	}
	
	public String getCarModelName(){
		return carModelName;
	}
	
	public void setCarModelName(String carModelName){
		this.carModelName = carModelName;
	}
	
	public String getTakeCarParkNo(){
		return takeCarParkNo;
	}
	
	public void setTakeCarParkNo(String takeCarParkNo){
		this.takeCarParkNo = takeCarParkNo;
	}
	
	public String getTakeCarParkName(){
		return takeCarParkName;
	}
	
	public void setTakeCarParkName(String takeCarParkName){
		this.takeCarParkName = takeCarParkName;
	}
	
	public String getReturnCarParkNo(){
		return returnCarParkNo;
	}
	
	public void setReturnCarParkNo(String returnCarParkNo){
		this.returnCarParkNo = returnCarParkNo;
	}
	
	public String getReturnCarParkName(){
		return returnCarParkName;
	}
	
	public void setReturnCarParkName(String returnCarParkName){
		this.returnCarParkName = returnCarParkName;
	}
	
	public Integer getMerchantOrderStatus(){
		return merchantOrderStatus;
	}
	
	public void setMerchantOrderStatus(Integer merchantOrderStatus){
		this.merchantOrderStatus = merchantOrderStatus;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public void setCarNo(String carNo){
		this.carNo = carNo;
	}
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
	}
	
	public String getReplaceCarModelId(){
		return replaceCarModelId;
	}
	
	public void setReplaceCarModelId(String replaceCarModelId){
		this.replaceCarModelId = replaceCarModelId;
	}
	
	public String getReplaceCarModelName(){
		return replaceCarModelName;
	}
	
	public void setReplaceCarModelName(String replaceCarModelName){
		this.replaceCarModelName = replaceCarModelName;
	}
	
	public String getReplaceCarNo(){
		return replaceCarNo;
	}
	
	public void setReplaceCarNo(String replaceCarNo){
		this.replaceCarNo = replaceCarNo;
	}
	
	public String getReplaceCarPlateNo(){
		return replaceCarPlateNo;
	}
	
	public void setReplaceCarPlateNo(String replaceCarPlateNo){
		this.replaceCarPlateNo = replaceCarPlateNo;
	}
	
	public Double getAdditionalAmount(){
		return additionalAmount;
	}
	
	public void setAdditionalAmount(Double additionalAmount){
		this.additionalAmount = additionalAmount;
	}
	
	public Integer getIsOnline(){
		return isOnline;
	}
	
	public void setIsOnline(Integer isOnline){
		this.isOnline = isOnline;
	}
	
	public String getReplaceCarMemo(){
		return replaceCarMemo;
	}
	
	public void setReplaceCarMemo(String replaceCarMemo){
		this.replaceCarMemo = replaceCarMemo;
	}
	
	public String getReplaceCarProofUrl1(){
		return replaceCarProofUrl1;
	}
	
	public void setReplaceCarProofUrl1(String replaceCarProofUrl1){
		this.replaceCarProofUrl1 = replaceCarProofUrl1;
	}
	
	public String getReplaceCarProofUrl2(){
		return replaceCarProofUrl2;
	}
	
	public void setReplaceCarProofUrl2(String replaceCarProofUrl2){
		this.replaceCarProofUrl2 = replaceCarProofUrl2;
	}
	
	public String getReplaceCarProofUrl3(){
		return replaceCarProofUrl3;
	}
	
	public void setReplaceCarProofUrl3(String replaceCarProofUrl3){
		this.replaceCarProofUrl3 = replaceCarProofUrl3;
	}
	
	public String getRefuseOrderType(){
		return refuseOrderType;
	}
	
	public void setRefuseOrderType(String refuseOrderType){
		this.refuseOrderType = refuseOrderType;
	}
	
	public String getRefuseOrderDetail(){
		return refuseOrderDetail;
	}
	
	public void setRefuseOrderDetail(String refuseOrderDetail){
		this.refuseOrderDetail = refuseOrderDetail;
	}
	
	public String getConfirmCarProofUrl1(){
		return confirmCarProofUrl1;
	}
	
	public void setConfirmCarProofUrl1(String confirmCarProofUrl1){
		this.confirmCarProofUrl1 = confirmCarProofUrl1;
	}
	
	public String getConfirmCarProofUrl2(){
		return confirmCarProofUrl2;
	}
	
	public void setConfirmCarProofUrl2(String confirmCarProofUrl2){
		this.confirmCarProofUrl2 = confirmCarProofUrl2;
	}
	
	public String getConfirmCarProofUrl3(){
		return confirmCarProofUrl3;
	}
	
	public void setConfirmCarProofUrl3(String confirmCarProofUrl3){
		this.confirmCarProofUrl3 = confirmCarProofUrl3;
	}
	
	public String getConfirmCarDetail(){
		return confirmCarDetail;
	}
	
	public void setConfirmCarDetail(String confirmCarDetail){
		this.confirmCarDetail = confirmCarDetail;
	}
	
	public String getCheckCarDetail(){
		return checkCarDetail;
	}
	
	public void setCheckCarDetail(String checkCarDetail){
		this.checkCarDetail = checkCarDetail;
	}
	
	public String getCheckCarProofUrl1(){
		return checkCarProofUrl1;
	}
	
	public void setCheckCarProofUrl1(String checkCarProofUrl1){
		this.checkCarProofUrl1 = checkCarProofUrl1;
	}
	
	public String getCheckCarProofUrl2(){
		return checkCarProofUrl2;
	}
	
	public void setCheckCarProofUrl2(String checkCarProofUrl2){
		this.checkCarProofUrl2 = checkCarProofUrl2;
	}
	
	public String getCheckCarProofUrl3(){
		return checkCarProofUrl3;
	}
	
	public void setCheckCarProofUrl3(String checkCarProofUrl3){
		this.checkCarProofUrl3 = checkCarProofUrl3;
	}
	
	public Double getOvertimeAmount(){
		return overtimeAmount;
	}
	
	public void setOvertimeAmount(Double overtimeAmount){
		this.overtimeAmount = overtimeAmount;
	}
	
	public String getCheckCarStatus() {
		return checkCarStatus;
	}

	public void setCheckCarStatus(String checkCarStatus) {
		this.checkCarStatus = checkCarStatus;
	}

	public String getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(String additionalService) {
		this.additionalService = additionalService;
	}

	public Double getAdditionalServiceFee(){
		return additionalServiceFee;
	}
	
	public void setAdditionalServiceFee(Double additionalServiceFee){
		this.additionalServiceFee = additionalServiceFee;
	}
	
	public Integer getAssignee(){
		return assignee;
	}
	
	public void setAssignee(Integer assignee){
		this.assignee = assignee;
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
	
	
	public Integer getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(Integer queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "MerchantOrder [merchantOrderId=" + merchantOrderId + ", merchantId=" + merchantId + ", orderNo="
				+ orderNo + ", memberNo=" + memberNo + ", memberName=" + memberName + ", carModelId=" + carModelId
				+ ", carModelName=" + carModelName + ", takeCarParkNo=" + takeCarParkNo + ", takeCarParkName="
				+ takeCarParkName + ", returnCarParkNo=" + returnCarParkNo + ", returnCarParkName=" + returnCarParkName
				+ ", merchantOrderStatus=" + merchantOrderStatus + ", carNo=" + carNo + ", carPlateNo=" + carPlateNo
				+ ", replaceCarModelId=" + replaceCarModelId + ", replaceCarModelName=" + replaceCarModelName
				+ ", replaceCarNo=" + replaceCarNo + ", replaceCarPlateNo=" + replaceCarPlateNo + ", additionalAmount="
				+ additionalAmount + ", isOnline=" + isOnline + ", replaceCarMemo=" + replaceCarMemo
				+ ", replaceCarProofUrl1=" + replaceCarProofUrl1 + ", replaceCarProofUrl2=" + replaceCarProofUrl2
				+ ", replaceCarProofUrl3=" + replaceCarProofUrl3 + ", refuseOrderType=" + refuseOrderType
				+ ", refuseOrderDetail=" + refuseOrderDetail + ", confirmCarProofUrl1=" + confirmCarProofUrl1
				+ ", confirmCarProofUrl2=" + confirmCarProofUrl2 + ", confirmCarProofUrl3=" + confirmCarProofUrl3
				+ ", confirmCarDetail=" + confirmCarDetail + ", checkCarStatus=" + checkCarStatus
				+ ", additionalService=" + additionalService + ", checkCarDetail=" + checkCarDetail
				+ ", checkCarProofUrl1=" + checkCarProofUrl1 + ", checkCarProofUrl2=" + checkCarProofUrl2
				+ ", checkCarProofUrl3=" + checkCarProofUrl3 + ", overtimeAmount=" + overtimeAmount
				+ ", additionalServiceFee=" + additionalServiceFee + ", assignee=" + assignee + ", createTime="
				+ createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd="
				+ updateTimeEnd + ", operatorId=" + operatorId + ", operatorType=" + operatorType + ", queryStatus="
				+ queryStatus + "]";
	}
	
	
}
