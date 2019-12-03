<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">日租订单详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key "><span></span>订单编号：</label>
						<td>
							<label class="control-label val ">
							   ${orderDay.orderNo}
							   </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>客户名称：</label>
						</td>
						<td>
							<label class="control-label val ">
							   ${orderDay.memberName}
							 </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>手机号：</label>
						</td>
						<td>
							<label class="control-label val ">
							      ${orderDay.mobilePhone}
							       </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>车牌号：</label>
						</td>
						<td>
							<label class="control-label val ">
								${orderDay.carPlateNo}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>车型：</label>
						</td>
						<td>
							<label class="control-label val ">
								${carModel.carModelName}
								</label>
						</td>
						<td>
							<label class=" control-label key "><span></span>城市：</label>
						</td>
						<td>
						<label class="control-label val ">
							${orderDay.cityName}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>订单状态：</label>
						</td>
						<td>
							<label class="control-label val ">
							<#if orderDay.orderStatus==0>
							已提交
							</#if>
							<#if orderDay.orderStatus==1>
							已预约
							</#if>
							<#if orderDay.orderStatus==2>
							已计费
							</#if>
							<#if orderDay.orderStatus==3>
							已结束
							</#if>
							<#if orderDay.orderStatus==4>
							已取消
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key "><span></span>订单金额：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.orderAmount}元
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>折扣金额：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.discountAmount}元
							</label>
						</td>
						<td>
							<label class=" control-label key ">附加服务费：</label>
						</td>
						<td>
							<label class="control-labe vall ">${orderDay.serviceCharge?string(',###.##')}元</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>应支付金额：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.payableAmount}元
							</label>
						</td>
						<td>
							<label class=" control-label key "><span></span>未到店取车违约金额：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.cancelAmount}元
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>送车上门未取车违约金：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.takeCarDoorAmount}元
							</label>
						</td>
						<td>
							<label class=" control-label key "><span></span>退还费用：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.returnAmount}元
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>超时服务费：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.overtimeCharge}元
							</label>
						</td>
						<td>
							<label class=" control-label key "><span></span>提前还车违约金：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.serviceFeeAmount}元
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>支付状态：</label>
						</td>
						<td>
							<label class="control-label val ">
							<#if orderDay.payStatus==0>
							未支付
							<#else>
							已支付
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key "><span></span>支付方式：</label>
						</td>
						<td>
							<label class="control-label val ">
							   <#if orderDay.paymentMethod==1>
									支付宝
								<#elseif orderDay.paymentMethod==2>
	                				微信
	                			<#elseif orderDay.paymentMethod==3>
	                				银行卡转账
	                			<#else>
	                				其他
	                			</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key "><span></span>支付时间：</label>
						</td>
						<td>
							<label class="control-label val ">
							<#if orderDay.paymentTime??>
							${orderDay.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key "><span></span>支付流水号：</label>
						</td>
						<td>
							<label class="control-label val ">
							${orderDay.paymentFlowNo}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>实际取车点：</label>
						</td>
						<td>
							<label class="control-label val">
							${orderDay.actualTakePakeName}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>实际还车点：</label>
						</td>
						<td>
							<label class="control-label val">
							${orderDay.actualTerminalParkName}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>预约时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if orderDay.appointmentTime??>
							${orderDay.appointmentTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>订单开始时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if orderDay.actualTakeTime??>
							${orderDay.actualTakeTime?string('yyyy-MM-dd HH:mm:ss')}
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
							<#if orderDay.actualReturnTime??>
							${orderDay.actualReturnTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>订单时长：</label>
						</td>
						<td>
							<label class="control-label val">
							${orderDay.orderDuration}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>是否违章：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if orderDay.isIllegal==1>
							有
							<#else>
							无
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>是否事故：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if orderDay.isAccident==1>
							有
							<#else>
							无
							</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>是否故障：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if orderDay.isFault==1>
							有
							<#else>
							无
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>取消时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if orderDay.cancelTime??>
							${orderDay.cancelTime?string('yyyy-MM-dd HH:mm:ss')}
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
								<#if orderDay.orderSource=="1">
									ios
								<#elseif orderDay.orderSource=="2">
									安卓
								<#elseif orderDay.orderSource=="3">
									H5
								</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>保险费用：</label>
						</td>
						<td>
							<label class="control-label val">
							${orderDay.insurance}元
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>异地还车费：</label>
						</td>
						<td>
							<label class="control-label val">
							${orderDay.taxiFare}元
							</label>
						</td>
						<#if merchantOrder.merchantOrderStatus==2>
						<td>
							<label class="col-sm-5 control-label key"><span></span>商家名称：</label>
						</td>
						<td>
							<label class="control-label val">
								${merchant.merchantName}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>接单状态：</label>
						</td>
						<td>
							<label class="control-label val">
								拒单
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>拒单理由：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if merchantOrder.refuseOrderType==1>
									无可用车辆
								<#elseif merchantOrder.refuseOrderType==2>
									车辆故障
								</#if>
								</label>
						</td>
						</#if>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeOrderDay" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
    $(function () {
     require.config({paths: {"orderDayView":"res/js/dailyrental/orderday/order_day_view"}});
		require(["orderDayView"], function (orderDayView){
			orderDayView.init();
		});  
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd hh:mm:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>