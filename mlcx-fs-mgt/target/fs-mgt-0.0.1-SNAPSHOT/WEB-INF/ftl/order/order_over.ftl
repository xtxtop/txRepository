<meta charset="utf-8">
<div class="container-fluid backgroundColor">
<form name="orderEditForm" class="form-horizontal">
						<input type="hidden" name="flag" value="${flag}"/>
						<input type="hidden" value="${order.orderNo}" name="orderNo"></input>
		<input name="isPloygon" type="hidden" value='${isPloygon}' />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">订单信息</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">*订单编号：</label>
						</td>
						<td>
							<label class="control-label val">
							   ${order.orderNo}
							   </label>
							</div>
						</td>
						<td>
							<label class=" control-label key">*客户名称：</label>
						</td>
						<td>
							<label class="control-label val">
							   ${order.memberName}
							  </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*手机号：</label>
						</td>
						<td>
							<label class="control-label val">
							      ${order.mobilePhone}
							      </label>
							</div>
						</td>
						<td>
							<label class=" control-label key">*车牌号：</label>
						</td>
						<td>
							<label class="control-label val">
								${order.carPlateNo}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*城市：</label>
						</td>
						<td>
						<label class="control-label val">
							${order.cityName}
							</label>
						</td>
						<td>
							<label class=" control-label key">*订单状态：</label>
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
							<label class=" control-label key">*订单金额：</label>
						</td>
						<td>
							<label class="control-label val">
							${order.orderAmount}
							</label>
						</td>
						<td>
							<label class=" control-label key">*预约取车点：</label>
						</td>
						<td>
							<label class="control-label val">
							${order.paymentFlowNo}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*预约还车点：</label>
						</td>
						<td>
							<label class="control-label val">
							${order.paymentFlowNo}
							</label>
						</td>
						<td>
							<label class=" control-label key">*当前时长：</label>
						</td>
						<td>
							<label class="control-label val">
							${order.paymentFlowNo}
							</label>
						</td>
					</tr>

					<tr>
						<td>
							<label class=" control-label key">*是否事故：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if order.isAccident==1>
							有
							<#else>
							无
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key">*事故时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if order.recordAccidentTime!="">
							${order.recordAccidentTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*是否故障：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if order.isFault==1>
							有
							<#else>
							无
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key">*故障时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if order.recordFaultTime!="">
							${order.recordFaultTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*创建日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${order.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td>
							<label class=" control-label key">*结束原因类型：</label>
						</td>
						<td>
							<label><input name="isCarFault" type="checkbox" value="1" />故障 </label> 
								<label><input name="isCarAccident" type="checkbox" value="1" />事故 </label> 
						</td>
					</tr>
					<tr>
							<td>
								<label class=" control-label key">*结束原因：</label>
							</td>
							<td>
							<textarea class="form-control val"   name="finishReason"></textarea>
							<span name="finishReason"></span>
						</td>
						<td>
							<label class=" control-label key">*附加费用(元)：</label>
							</td>
							<td>
								<input class="form-control val" name="additionFees"  />
								<span name="additionFees"></span>
						</td>
						
					</tr>
					<tr>
					<td>
							<label class=" control-label key">*附加费用原因：</label>
							</td>
							<td>
								 <input class="form-control val" name="additionReason"  />
								 <span name="additionReason"></span>
						</td>
						<td>
							<label class=" control-label key">*当前车辆位置：</label>
							</td>
							<td>
								<input class="form-control val" name="currentCarLocation"  />
								<input type="button" class="btn btn-default" id="changeCarLocation" value="修改位置" >
								<span name="currentCarLocation"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*坐标最后上报时间：</label>
							</td>
							<td>
	                          <div class=" val">
                                 <#if carStatus.lastReportingTime??>
		                        ${carStatus.lastReportingTime?string('yyyy-MM-dd HH:mm:ss')}
		                        <#else>
		                        </#if> 
                            </div>
                            </td>
							<td>
								 <label class=" control-label key">*坐标：</label>
							</td>
							<td>
								 <input  name="longitude" value="${carStatus.longitude}" style="width:40%"/>
				    				<input  name="latitude" value="${carStatus.latitude}" style="width:40%;"/>
				    			<span name="longitude"></span><span name="latitude"></span>
				    			
							</td>
					</tr>
					<input type="hidden" name="finishType" value="1">
					  </form>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveOrderEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeOrderEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
		</form>
</div>
<!-- 弹出框 获取坐标地址 操作-->
    <div class="modal fade" id="carLocationSearchGCModal" style="height:100%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">修改位置</h4>
                </div>
                <div class="modal-body">
                       <form class="form-horizontal" name="carLocationSearchGCForm"> 
                     <div id="searchCarGCMap">
                            
                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label key">&nbsp;&nbsp;要查询的地址：</label>
							<div class="col-sm-6">
							 <input class="form-control val" type="text" name="gcAddress" >
							</div>
                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label key">&nbsp;&nbsp;坐标：</label>
							<div class="col-sm-6">
							 <input class="form-control val" name="gcPoint" readonly>
							</div>
							<button type="button" class="btn btn-default " id="searchCarPoint">查询</button>
                        </div>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal" style="width: 95px; height: 32px;  margin-right: 50px !important">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="carLocationSearchGCBtn" style="width: 95px; height: 32px; background:#2b94fd;" value="保存">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
    $(function () {
     require.config({paths: {"orderOver":"res/js/order/order_over"}});
		require(["orderOver"], function (orderOver){
			orderOver.init();
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