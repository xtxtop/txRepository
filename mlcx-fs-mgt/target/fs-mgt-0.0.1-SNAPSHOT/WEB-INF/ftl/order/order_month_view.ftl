<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-12 control-label"><h4>日租订单详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-11 form-horizontal">
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;客户名称：</label>
							<div class="col-sm-7">
							<label class="control-label">
							   ${order.memberName}
							 </label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;手机号：</label>
							<div class="col-sm-7">
							<label class="control-label">
							      ${order.mobilePhone}
							       </label>
							</div>
						</div>
																													
						 <div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.cityName}
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;订单编号：</label>
							<div class="col-sm-7">
							<label class="control-label">
							   ${order.orderNo}
							   </label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;订单状态：</label>
							<div class="col-sm-7">
							<label class="control-label">
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
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;订单金额：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.orderAmount}元
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;折扣金额：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.discountAmount}元
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;冲账金额：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.strikeBalanceAmount}元
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label">附加服务费：</label>
							<div class="col-sm-7">
								<label class="control-label">${order.serviceCharge?string(',###.##')}元</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-5 control-label"><span></span>&nbsp;&nbsp;应支付金额：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.payableAmount}元
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;支付状态：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.payStatus==0>
							未支付
							<#else>
							已支付
							</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;支付方式：</label>
							<div class="col-sm-7">
							<label class="control-label">
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
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;支付时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.paymentTime??>
							${order.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>	
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-5 control-label"><span></span>&nbsp;&nbsp;支付流水号：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.paymentFlowNo}
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-5 control-label"><span></span>&nbsp;&nbsp;实际取车点：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.actualTakeLoacton}
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-5 control-label"><span></span>&nbsp;&nbsp;实际还车点：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.actualTerminalParkName}
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;预约时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.appointmentTime??>
							${order.appointmentTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-5 control-label"><span></span>&nbsp;&nbsp;订单开始时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.startBillingTime??>
							${order.startBillingTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;结束时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.finishTime??>
							${order.finishTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;订单时长：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.orderDuration}
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;是否违章：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.isIllegal==1>
							有
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;是否事故：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.isAccident==1>
							有
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;事故时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.recordAccidentTime??>
							${order.recordAccidentTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;是否故障：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.isFault==1>
							有
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;故障时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.recordFaultTime??>
							${order.recordFaultTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;订单备注：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.orderMemo!="">
							${order.orderMemo}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;取消时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.cancelTime??>
							${order.cancelTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;订单开票：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.isNeedInvoice==1>
							开票
							<#else>
							不开票
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;开票时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.invoiceTime??>
							${order.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>		
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;发票号：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.invioceNo!="">
							${order.invioceNo}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;订单来源：</label>
							<div class="col-sm-7">
							<label class="control-label">
							分时租赁APP
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;备注：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.memo!="">
							${order.memo}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>		
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.createTime??>
							${order.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-7">
							<label class="control-label">
							<#if order.updateTime??>
							${order.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;保险费用：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${order.insurance}元
							</label>
							</div>
						</div>

					</div>	
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <!-- <button type="button" id="closeOrderDay" class="btn btn-default pull-right btncolora" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button> -->
                    <!--<button type="button" id="saveOrderCarFault" class="btn btn-default pull-right" >记录故障</button>
                    <button type="button" id="saveOrderCarAccident" class="btn btn-default pull-right" >记录事故</button>-->
                    <!-- <#if order.orderStatus==2&&order.payStatus==0>
                    <button type="button" id="closeOrderDayStatus" class="btn btn-default pull-right"  data-id="${order.orderNo}">结束订单</button>
                    </#if> -->
                    </div>
                </div>
</div>

       <div class="modal fade"  id="onOrderModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                  <form class="form-horizontal" name="onForm"> 
                   <input type="hidden"  name="carPlateNo" value="${order.carPlateNo}">
			       <input type="hidden"  name="carNo" value="${order.carNo}">
			       <input type="hidden"  name="carModelId" value="${order.carModelId}">
			       <input type="hidden"  name="cityId" value="${order.cityId}">
			       <input type="hidden"  name="cityName" value="${order.cityName}">
			       <input type="hidden"  name="recordFaultTime" value="${order.recordFaultTime}">
			       <input type="hidden"  name="documentNo" value="${order.orderNo}">
			       <input type="hidden"  name="driverId" value="${order.memberNo}">
			       <input type="hidden"  name="driverName" value="${order.memberName}">
			     </form> 
			   </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <div class="modal fade" id="offOrderModal">
        <div class="modal-dialog">
            <div class="modal-content">
                  <div class="modal-body">
                   <form class="form-horizontal" name="offForm"> 
				   <input type="hidden"  name="carPlateNo" value="${order.carPlateNo}">
			       <input type="hidden"  name="carNo" value="${order.carNo}">
			       <input type="hidden"  name="carBrandId" value="${car.carBrandId}">
			       <input type="hidden"  name="carBrandName" value="${car.carBrandName}">
			       <input type="hidden"  name="insuranceCompany" value="${car.insuranceCompany}">
			       <input type="hidden"  name="carModelId" value="${order.carModelId}">
			       <input type="hidden"  name="carModelName" value="${order.carModelName}">
			       <input type="hidden"  name="cityId" value="${order.cityId}">
			       <input type="hidden"  name="cityName" value="${order.cityName}">
			       <input type="hidden"  name="recordAccidentTime" value="${order.recordAccidentTime}">
			       <input type="hidden"  name="documentNo" value="${order.orderNo}">
			       <input type="hidden"  name="driverId" value="${order.memberNo}">
			       <input type="hidden"  name="driverName" value="${order.memberName}">
			       </form> 
			     </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
<script type="text/javascript">
   
</script>
