<meta charset="utf-8">
<style>
	.key {
		font-size: 1.5rem;
		font-weight: 500;
		color: #414550;
		line-height: 15px;
	}
	
	.val {
		font-size: 1.5rem;
		font-weight: 500;
		color: #a0a7af;
		line-height: 15px;
	}
</style>
<div class="container-fluid backgroundColor">
	<!--<div class="row">
		<div class="col-md-12">
			<div class="form-group compiletitle">
				<label class="col-sm-12 control-label key"><h4>订单详情</h4></label>
			</div>
		</div>
	</div>-->
	<div class="row hzlist">
		<div class="col-md-12 form-horizontal">
			<!-- tabs -->
			<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
				<!-- Nav tabs -->
				<ul id="orderViewTabs" class="nav nav-tabs">
					<li class="active">
						<a href="#baseTab" aria-controls="baseTab" role="tab" data-toggle="tab">基础信息</a>
					</li>
					<li>
						<a href="#useCarTab" aria-controls="useCarTab" role="tab" data-toggle="tab">用车信息</a>
					</li>
					<li>
						<a href="#costTab" aria-controls="costTab" role="tab" data-toggle="tab">费用信息</a>
					</li>
					<li>
						<a href="#payTab" aria-controls="payTab" role="tab" data-toggle="tab">支付信息</a>
					</li>
					<li>
						<a href="#carPhotoTab" aria-controls="carPhotoTab" role="tab" data-toggle="tab">取还车辆照片</a>
					</li>
					<li>
						<a href="#violationAccidentTab" aria-controls="violationAccidentTab" role="tab" data-toggle="tab">违章事故</a>
					</li>
					<li>
						<a href="#billingTab" aria-controls="billingTab" role="tab" data-toggle="tab">开票信息</a>
					</li>
					<li>
						<a href="#commentsTab" aria-controls="commentsTab" role="tab" data-toggle="tab">评价信息</a>
					</li>
				</ul>
				<!-- Tab panes begin -->
				<div class="tab-content">
					<div class="tab-pane active" id="baseTab">
						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">基础信息</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<tr>
									<td>
										<label class=" control-label key"><span></span>订单号：</label>
									</td>
									<td>
										<label class="control-label val">
											   ${order.orderNo}
			       								<input type="hidden"  name="orderNo" value="${order.orderNo}">
							</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>客户：</label>
									</td>
									<td>
										<label class="control-label val">
											   ${order.memberName}
							</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>手机：</label>
									</td>
									<td>
										<label class="control-label val">
											      ${order.mobilePhone}
											       </label>
									</td>
									<td>
										<label class=" control-label key"><span></span>订单状态：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.orderStatus==0>
											已提交
											</#if>
											<#if order.orderStatus==1>
											已预约
											</#if>
											<#if order.orderStatus==2>
											已计费
											</#if>
											<#if order.orderStatus==3>
											已结束
											</#if>
											<#if order.orderStatus==4>
											已取消
											</#if>
											</label>
									</td>
								</tr>

								<tr>
									<td>
										<label class=" control-label key"><span></span>支付状态：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.payStatus==0>
											未支付
											<#else>
											已支付
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>城市：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.cityName}
											</label>
									</td>
								</tr>

								<tr>
									<td>
										<label class=" control-label key"><span></span>车型：</label>
									</td>
									<td>
										<label class="control-label val">
												${car.carModelName}
												</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>车牌：</label>
									</td>
									<td>
										<label class="control-label val">
												${order.carPlateNo}
												</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>集团：</label>
									</td>
									<td>
										<label class="control-label val">
												${order.companyName}
												</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>取消时间：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.cancelTime??>
											${order.cancelTime?string('yyyy-MM-dd HH:mm:ss')}
											<#else>
											无
											</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>取消方式：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.finishType==0>
											会员取消
											</#if>
											<#if order.finishType==1>
											客服取消
											</#if>
											<#if order.finishType==2>
											自动取消
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>订单备注：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.orderMemo!="">
											${order.orderMemo}
											<#else>
											无
											</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>订单来源：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.orderSource=="0">
												微信
											<#elseif order.orderSource=="2">
												iso
											<#elseif order.orderSource=="3">
												安卓
											<#else>
												app
											</#if>
											</label>
									</td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>

					<!--用车信息-->
					<div class="tab-pane" id="useCarTab">
						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">用车信息</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<tr>
									<td>
										<label class=" control-label key"><span></span>预约时间：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.appointmentTime??>
											${order.appointmentTime?string('yyyy-MM-dd HH:mm:ss')}
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>开始时间：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.startBillingTime??>
											${order.startBillingTime?string('yyyy-MM-dd HH:mm:ss')}
											</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>结束时间：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.finishTime??>
											${order.finishTime?string('yyyy-MM-dd HH:mm:ss')}
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>取车点：</label>
									</td>
									<td>
										<label class="control-label val">
												<#if order.isTakeInPark == 0>
													${order.actualTakeLoacton}
												<#else>
													${order.startAddress}
												</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>还车点：</label>
									</td>
									<td>
										<label class="control-label val">
												<#if order.isReturnInPark == 0>
													${park2.parkName}
												<#else>
													${order.terminalAddress}
												</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>订单时长：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.orderDuration}
											</label>
									</td>
								</tr>

								<tr>
									<td>
										<label class=" control-label key"><span></span>里程：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.orderMileage}
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>计费触发方式：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if RuleType==1>
											自定义时间点作为日期分割
											<#else>
											订单计费开始往后顺延24小时为一天
											</#if>
											</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<!--费用信息-->
					<div class="tab-pane" id="costTab">

						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">费用信息</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<tr>
									<td>
										<label class=" control-label key"><span></span>总金额：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.orderAmount}元
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>时长费：</label>
									</td>
									<td>
										<label class="control-label val">
											${minutesAmount}元
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>里程费：</label>
									</td>
									<td>
										<label class="control-label val">
											${mileageAmount}元
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>起步价：</label>
									</td>
									<td>
										<label class="control-label val">
											${BaseFee}元
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>取车附加费：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.discountAmount??>
											${park1.serviceFeeGet?string(',###.##')}元
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>还车附加费：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.discountAmount??>
											${park2.serviceFeeBack?string(',###.##')}元
											</#if>
											</label>
									</td>
								</tr>

								<tr>
									<td>
										<label class=" control-label key"><span></span>结束订单附加费：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.discountAmount??>
											${serviceFeeAmount?string(',###.##')}元
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>异地还车费：</label>
									</td>
									<td>
										<label class="control-label val">
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>不计免赔费：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.regardlessFranchise?string(',###.##')}元
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>优惠：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.discountAmount??>
											${order.discountAmount?string(',###.##')}元
											</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>余额抵扣：</label>
									</td>
									<td>
										<label class="control-label val">
												${order.packAmountDiscountAmount}元
											</label>

									</td>
									<#if order.glodBeansDedutionAmount??>
										<td>
											<label class=" control-label key"><span></span>金豆抵扣金额：</label>
										</td>
										<td>
											<label class="control-label val">
											${order.glodBeansDedutionAmount?string(',###.##')}元
											</label>
										</td>
										<#else>
									</#if>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>会员折扣：</label>
									</td>
									<td>
										<label class="control-label val">
												${order.memberLevelDiscountAmount}元
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>应支付：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.payableAmount}元
											</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<!--支付信息-->
					<div class="tab-pane" id="payTab">

						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">支付信息</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<tr>
									<td>
										<label class=" control-label key"><span></span>支付状态：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.payStatus==0>
											未支付
											<#else>
											已支付
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>应支付：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.payableAmount}元
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>支付时间：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.paymentTime??>
											${order.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>支付方式：</label>
									</td>
									<td>
										<label class="control-label val">
											   <#if order.paymentMethod==1>
													支付宝
												<#elseif order.paymentMethod==2>
					                				微信
					                			<#elseif order.paymentMethod==3>
					                				银行卡转账
					                			<#else>
					                				其他
					                			</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>流水号：</label>
									</td>
									<td>
										<label class="control-label val">
											${order.paymentFlowNo}
											</label>
									</td>
									<td>
									</td>
									<td>
									</td>
								</tr>
							</tbody>
						</table>

					</div>

					<!--取还车辆照片-->
					<div class="tab-pane" id="carPhotoTab">

						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">取还车辆照片</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<tr>
									<td>
										<label class=" control-label key"><#-- <span>*</span> -->取车时车辆照片备注：</label>
									</td>
									<td>
										<textarea rows="2" cols="30" readonly>${order.carTakeMemo}</textarea>
									</td>
									<td>
										<label class=" control-label key"><#-- <span>*</span> -->还车时车辆照片备注：</label>
									</td>
									<td>
										<textarea rows="2" cols="30" readonly>${order.carBackMemo}</textarea>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>取车时车辆照片1：</label>
									</td>
									<td>
										<label class="control-label val">
								                        <img src="${imagePath!''}/${carTakeUrl1}" width="200"/>
								                        </label>
									</td>
									<td>
										<label class=" control-label key"><span></span>还车时车辆照片1：</label>
									</td>
									<td>
										<label class="control-label val">
								                        <img src="${imagePath!''}/${carBackUrl1}" width="200"/>
								                        </label>
									</td>
									
								</tr>
							
								<tr>
								    <td>
									<label class=" control-label key"><span></span>取车时车辆照片2：</label>
									</td>
									<td>
										<label class="control-label val">
							                        <img src="${imagePath!''}/${carTakeUrl2}" width="200"/>
							                        </label>
									</td>
									<td>
									<label class=" control-label key"><span></span>还车时车辆照片2：</label>
									</td>
									<td>
										<label class="control-label val">
							                        <img src="${imagePath!''}/${carBackUrl2}" width="200"/>
							                        </label>
									</td>
								
								</tr>
									<tr>
									<td>
									<label class=" control-label key"><span></span>取车时车辆照片3：</label>
									</td>
									<td>
										<label class="control-label val">
							                        <img src="${imagePath!''}/${carTakeUrl3}" width="200"/>
							                        </label>
									</td>
									<td>
									<label class=" control-label key"><span></span>还车时车辆照片3：</label>
									</td>
									<td>
										<label class="control-label val">
							                        <img src="${imagePath!''}/${carBackUrl3}" width="200"/>
							                        </label>
									</td>
									
								</tr>
								<tr>
									<td>
									<label class=" control-label key"><span></span>取车时车辆照片4：</label>
									</td>
									<td>
										<label class="control-label val">
							                        <img src="${imagePath!''}/${carTakeUrl4}" width="200"/>
							                        </label>
									</td>
									<td>
									<label class=" control-label key"><span></span>还车时车辆照片4：</label>
									</td>
									<td>
										<label class="control-label val">
							                        <img src="${imagePath!''}/${carBackUrl4}" width="200" />
							                        </label>
									</td>
								</tr>
							</tbody>
						</table>

					</div>

					<!--违章事故-->
					<div class="tab-pane" id="violationAccidentTab">

						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">违章事故</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<tr>
									<td>
										<label class=" control-label key"><span></span>车辆违章：</label>
									</td>
									<td>
										<label class="control-label val">
											   <#if order.isIllegal==1>
													<span id="queryIllegalList">有</span>
					                			<#else>
													无
					                			</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>车辆故障：</label>
									</td>
									<td>
										<label class="control-label val">
											   <#if order.isFault==1>
													<span id="queryFaultList">有</span>
					                			<#else>
													无
					                			</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>车辆事故：</label>
									</td>
									<td>
										<label class="control-label val">
											   <#if order.isAccident==1>
													<span id="queryAccidentList">有</span>
					                			<#else>
													无
					                			</#if>
											</label>
									</td>
									<td>
									</td>
									<td>
									</td>
								</tr>
							</tbody>
						</table>

					</div>

					<!--开票信息-->
					<div class="tab-pane" id="billingTab">

						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">开票信息</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<tr>
									<td>
										<label class=" control-label key"><span></span>申请时间：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if invoiceCreateTime??>
											${invoiceCreateTime?string('yyyy-MM-dd HH:mm:ss')}
											<#else>
											无
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>开票类型：</label>
									</td>
									<td>
										<label class="control-label val">
											   <#if invoiceType==1>
													增值税普通发票纸质版
												<#elseif invoiceType==2>
					                				增值税专用发票
					                			<#elseif invoiceType==3>
					                				增值税普通发票电子版
					                			<#else>
													无
					                			</#if>
											</label>
									</td>
								</tr>
								<tr>
									<td>
										<label class=" control-label key"><span></span>开票时间：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.invoiceTime??>
											${order.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
											<#else>
											无
											</#if>
											</label>
									</td>
									<td>
										<label class=" control-label key"><span></span>发票号：</label>
									</td>
									<td>
										<label class="control-label val">
											<#if order.invioceNo!="">
											${order.invioceNo}
											<#else>
											无
											</#if>
											</label>
									</td>
								</tr>
							</tbody>
						</table>

					</div>

					<!--评价信息-->
					<div class="tab-pane" id="commentsTab">

						<table class="tab-table table table-border table-responsive">
							<thead class="tab-thead">
								<tr>
									<th colspan="4">评价信息</th>
								</tr>
							</thead>
							<tbody class="tab-tbody">
								<#list commentsList as comments>
									<tr>
										<td>
											<label class=" control-label key"><span></span>评价星级：</label>
										</td>
										<td>
											<label class="control-label val">
													<#if comments.score!="">
													${comments.score}
													<#else>
													无
													</#if>
												</label>
										</td>
										<td>
											<label class=" control-label key"><span></span>评价内容：</label>
										</td>
										<td>
											<label class="control-label val">
													<#if comments.content!="">
													${comments.content}
													<#else>
													无
													</#if>
												</label>
										</td>
									</tr>
									</#list>
							</tbody>
						</table>

					</div>
				</div>
				<!-- Tab panes end -->
			</div>
			<!-- /.tabs -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>

<div class="modal fade" id="onOrderModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<form class="form-horizontal" name="onForm">
					<input type="hidden" name="carPlateNo" value="${order.carPlateNo}">
					<input type="hidden" name="carNo" value="${order.carNo}">
					<input type="hidden" name="carModelId" value="${order.carModelId}">
					<input type="hidden" name="cityId" value="${order.cityId}">
					<input type="hidden" name="cityName" value="${order.cityName}">
					<input type="hidden" name="recordFaultTime" value="${order.recordFaultTime}">
					<input type="hidden" name="documentNo" value="${order.orderNo}">
					<input type="hidden" name="driverId" value="${order.memberNo}">
					<input type="hidden" name="driverName" value="${order.memberName}">
				</form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="offOrderModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<form class="form-horizontal" name="offForm">
					<input type="hidden" name="carPlateNo" value="${order.carPlateNo}">
					<input type="hidden" name="carNo" value="${order.carNo}">
					<input type="hidden" name="carBrandId" value="${car.carBrandId}">
					<input type="hidden" name="carBrandName" value="${car.carBrandName}">
					<input type="hidden" name="insuranceCompany" value="${car.insuranceCompany}">
					<input type="hidden" name="carModelId" value="${order.carModelId}">
					<input type="hidden" name="carModelName" value="${order.carModelName}">
					<input type="hidden" name="cityId" value="${order.cityId}">
					<input type="hidden" name="cityName" value="${order.cityName}">
					<input type="hidden" name="recordAccidentTime" value="${order.recordAccidentTime}">
					<input type="hidden" name="documentNo" value="${order.orderNo}">
					<input type="hidden" name="driverId" value="${order.memberNo}">
					<input type="hidden" name="driverName" value="${order.memberName}">
				</form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"orderView": "res/js/order/order_view"
			}
		});
		require(["orderView"], function(orderView) {
			orderView.init();
		});
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd hh:mm:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>