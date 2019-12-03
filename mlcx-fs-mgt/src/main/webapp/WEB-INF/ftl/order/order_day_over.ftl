<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="col-sm-12 control-label"><h4>日租订单信息</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8 form-horizontal">
					    <div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;订单编号：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							   ${order.orderNo}
							   </label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;客户名称：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							   ${order.memberName}
							  </label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;手机号：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							      ${order.mobilePhone}
							      </label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;车牌号：</label>
							<div class="col-sm-4">
							<label class="control-label val">
								${order.carPlateNo}
								</label>
							</div>
						</div>		
						 <div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;城市：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							${order.cityName}
							</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;订单状态：</label>
							<div class="col-sm-4">
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
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;订单金额：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							${order.orderAmount}
							</label>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;预约取车点：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							${order.paymentFlowNo}
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;预约还车点：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							${order.paymentFlowNo}
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;当前时长：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							${order.paymentFlowNo}
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;是否事故：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							<#if order.isAccident==1>
							有
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;事故时间：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							<#if order.recordAccidentTime!="">
							${order.recordAccidentTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;是否故障：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							<#if order.isFault==1>
							有
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;故障时间：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							<#if order.recordFaultTime!="">
							${order.recordFaultTime?string('yyyy-MM-dd HH:mm:ss')}
							<#else>
							无
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							${order.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>	
						<form name="orderEditFormDay" class="form-horizontal">
						<input type="hidden" value="${order.orderNo}" name="orderNo"></input>
						<!-- <div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;结束类型：</label>
							<div class="col-sm-4">
								<select name="finishType" class="form-control val">
									<option value="1">事故</option>
									<option value="2">故障</option>
									<option value="0">正常结束</option>
								</select>
							</div>
						</div>-->
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;结束原因类型：</label>
							<div class="col-sm-4 val">
								<label><input name="isCarFault" type="checkbox" value="1" />故障 </label> 
								<label><input name="isCarAccident" type="checkbox" value="1" />事故 </label> 
							</div>
						</div>
						<div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;结束原因：</label>
                            <div class="col-sm-4">
                                 <textarea class="form-control val"   name="finishReason"></textarea>
                            </div>
                            <div style="margin-top:7px;"><span name="finishReason"></span></div>
                        </div>
                        <div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;当前车辆位置：</label>
                            <div class="col-sm-4">
                                   <input class="form-control val" name="currentCarLocation"  />
                            </div>
                            <div style="margin-top:7px;"><span name="currentCarLocation"></span></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;坐标：</label>
                            <div class="col-sm-4">
                                  <input  name="longitude" value="${carStatus.longitude}" style="width:40%"/>
				    				&nbsp;&nbsp;<input  name="latitude" value="${carStatus.latitude}" style="width:40%;"/>
                            </div>
                            <div style="margin-top:7px;"><span name="longitude"><span name="latitude"></span></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;坐标最后上报时间：</label>
                            <div class="col-sm-4">
                                 <#if carStatus.lastReportingTime??>
		                        ${carStatus.lastReportingTime?string('yyyy-MM-dd HH:mm:ss')}
		                        <#else>
		                        </#if> 
                            </div>
                        </div>
		  	 		 
          				 
		  	</div>
                      
                         <div class="form-group" style="margin-top: 57px;">
                            <label class="col-sm-3 control-label key">&nbsp;&nbsp;</label>
                            <div class="col-sm-4 val">
                             <input type="button" id="changeCarDayLocation" value="修改位置" >
                            </div>
                        </div>
                         <div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;附加费用：</label>
                            <div class="col-sm-4">
                                   <input class="form-control val" name="additionFees"  />
                            </div>
                            <div style="margin-top:7px;">元<span name="additionFees"></span></div>
                        </div>
                         <div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;附加费用原因：</label>
                            <div class="col-sm-4">
                                   <input class="form-control val" name="additionReason"  />
                            </div>
                            <div style="margin-top:7px;"><span name="additionReason"></span></div>
                        </div>
                        <input type="hidden" name="finishType" value="1">
                        </form>
                		<div class="form-group">
							<div class="col-sm-9">
							  <button id="closeOrderDayEdit" class="btn btn-default pull-right navbar-btn btn-flat carIllegalEdit-operate-close" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">返回</button>
					          <button id="saveOrderDayEdit" class="btn btn-default pull-right navbar-btn btn-flat carIllegalEdit-operate-save" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">提交</button>
						   </div>	
						</div>
					</div>	
        		</div><!-- /.row -->
</div>
<!-- 弹出框 获取坐标地址 操作-->
    <div class="modal fade" id="carLocationSearchGCModalDay" style="height:100%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">修改位置</h4>
                </div>
                <div class="modal-body">
                       <form class="form-horizontal" name="carLocationSearchGCDayForm"> 
                     <div id="searchCarGCMapDay">
                            
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
							<button type="button" class="btn btn-default " id="searchCarPointDay">查询</button>
                        </div>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="carLocationSearchGCBtnDay" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" value="保存">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
    $(function () {
     require.config({paths: {"orderDayOver":"res/js/order/order_day_over"}});
		require(["orderDayOver"], function (orderDayOver){
			orderDayOver.init();
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
