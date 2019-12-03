<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="orderStrikeBalanceViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">订单冲账详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-7 control-label key"><span></span>冲账单号：</label>
						</td>
						<td>
							<label class="control-label val"> ${orderStrikeBalance.strikeBalanceNo}</label></label>
						</td>
						<td>
							<label class=" control-label key"><span></span>订单编号：</label>
						</td>
						<td>
							 <label class="control-label val"> ${orderStrikeBalance.orderNo}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>客户名称：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.memberName}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>订单金额：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.orderAmount?string(',###.##')}元</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>应付金额：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.payAmount?string(',###.##')}元</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>冲账金额：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.strikeBalanceAmount?string(',###.##')}元</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>冲账原因：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if orderStrikeBalance.strikeBalanceReason==1>故障
                				<#elseif orderStrikeBalance.strikeBalanceReason==2>没电</#if>
                				</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>冲账备注：</label>
						</td>
						<td>
							<label class="control-label val">
	                				${orderStrikeBalance.strikeBalanceMemo}
	                			</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>提交人：</label>
						</td>
						<td>
							<label class="control-label val">
                				${orderStrikeBalance.submitName}
                				</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>提交时间：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.submitTtime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if orderStrikeBalance.censorStatus==0>未审核
								<#elseif orderStrikeBalance.censorStatus==1>已审核
								<#elseif orderStrikeBalance.censorStatus==2>未通过
								</#if></label>
						</td>
						<td>
							<label class=" control-label key"><span></span>审核人：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.censorPersonName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>审核时间：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.censorTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>审核备注：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.censorMemo}</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeOrderStrikeBalanceView" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>


<script type="text/javascript">
    $(function(){
    require.config({paths: {"orderStrikeBalance":"res/js/order/order_strike_balance_list"}});
		require(["orderStrikeBalance"], function (orderStrikeBalance){
			orderStrikeBalance.init();
		});
	   }); 
    </script>