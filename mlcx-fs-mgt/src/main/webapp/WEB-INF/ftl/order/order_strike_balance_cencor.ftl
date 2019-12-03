<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="orderStrikeBalanceCencorForm">
		<input type="hidden" name="strikeBalanceNo" value="${orderStrikeBalance.strikeBalanceNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">订单冲账审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">冲账单号：</label>
						</td>
						<td>
							<label class="control-label val"> ${orderStrikeBalance.strikeBalanceNo}</label></label>
						</td>
						<td>
							<label class=" control-label key">订单编号：</label>
						</td>
						<td>
							 <label class="control-label val"> ${orderStrikeBalance.orderNo}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">客户名称：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.memberName}</label>
						</td>
						<td>
							<label class=" control-label key">订单金额：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.orderAmount?string(',###.##')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">应付金额：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.payAmount?string(',###.##')}</label>
						</td>
						<td>
							<label class=" control-label key">冲账金额：</label>
						</td>
						<td>
							<label class="control-label val">${orderStrikeBalance.strikeBalanceAmount?string(',###.##')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">冲账原因：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if orderStrikeBalance.strikeBalanceReason==1>故障
                				<#elseif orderStrikeBalance.strikeBalanceReason==2>没电</#if>
                				</label>
						</td>
						<td>
							<label class=" control-label key">冲账备注：</label>
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
								<input type="radio" name="censorStatus"  value="1" checked="checked">通过</input>
                                <input type="radio" name="censorStatus"  value="2" >不通过</input>
								</label>						</td>
						<td>
							<label class=" control-label key">审核备注：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="6"  name="censorMemo" >${orderStrikeBalance.censorMemo}</textarea>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="cencorOrderStrikeBalance" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeOrderStrikeBalanceCencor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"orderStrikeBalanceCencor":"res/js/order/order_strike_balance_cencor"}});
		require(["orderStrikeBalanceCencor"], function (orderStrikeBalanceCencor){
			orderStrikeBalanceCencor.init();
		});  
	});
    </script>