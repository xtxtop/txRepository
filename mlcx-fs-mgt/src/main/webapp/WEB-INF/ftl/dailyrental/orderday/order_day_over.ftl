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
							${order.startParkDayName}
							</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;预约还车点：</label>
							<div class="col-sm-4">
							<label class="control-label val">
							${order.returnParkDayName}
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
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;结束原因：</label>
                            <div class="col-sm-4">
                                 <textarea class="form-control val"   name="finishReason"></textarea>
                            </div>
                            <div style="margin-top:7px;"><span name="finishReason"></span></div>
                        </div>
                        <div>
          				 
		  	</div>
                      
                         <div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;附加费用：</label>
                            <div class="col-sm-4">
                                   <input class="form-control val" name="serviceFeeAmount"  />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label key">*&nbsp;&nbsp;附加费用原因：</label>
                            <div class="col-sm-4">
                                   <input class="form-control val" name="additionReason"  />
                            </div>
                            <div style="margin-top:7px;"><span name="additionReason"></span></div>
                        </div>
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

<script type="text/javascript">
    $(function () {
     require.config({paths: {"orderDayOver":"res/js/dailyrental/orderday/order_day_over"}});
		require(["orderDayOver"], function (orderDayOver){
			orderDayOver.init();
		});  
    });
</script>
