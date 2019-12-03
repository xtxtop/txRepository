<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="orderAssignForm">
		<input type="hidden" name="orderNo" value="${order.orderNo}" />
		<input type="hidden" name="carModelId" value="${order.carModelId}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">指派租赁商</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;订单编号：</label>
						</td>
						<td>
							<label class="control-label val">
							   ${order.orderNo}
							   </label>
						</td>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;客户名称：</label>
						</td>
						<td>
							<label class="control-label val">
							   ${order.memberName}
							  </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;手机号：</label>
						</td>
						<td>
							<label class="control-label val">
							      ${order.mobilePhone}
							      </label>
						</td>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<label class="control-label val">
							${order.cityName}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;开始时间：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if order.appointmentTakeTime??>
									${order.appointmentTakeTime?string('yyyy-MM-dd HH:mm:ss')}
								</#if>
								</label>
						</td>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;结束时间：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if order.appointmentReturnTime??>
									${order.appointmentReturnTime?string('yyyy-MM-dd HH:mm:ss')}
								</#if>
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;租车起点：</label>
						</td>
						<td>
							<label class="control-label val">
								${startParkDayName}
								</label>
						</td>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;还车地点：</label>
						</td>
						<td>
							<label class="control-label val">
								${returnParkDayName}
								</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeOrderDayEdit" class="btn-new-type-edit">
                返回
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	
	 <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="merchantInventoryTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="merchantInventoryList" class="table table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
</div>
</div>



<script type="text/javascript">
    $(function () {
     require.config({paths: {"orderAssign":"res/js/dailyrental/orderday/order_day_assign"}});
		require(["orderAssign"], function (orderAssign){
			orderAssign.init();
		});  
    });
</script>