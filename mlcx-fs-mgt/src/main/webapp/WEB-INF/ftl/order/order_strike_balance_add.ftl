<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="orderStrikeBalanceAddForm">
			 	<input type="hidden" name="orderNo" value="${order.orderNo}"/>
	 			<input type="hidden" name="memberId" value="${order.memberNo}"/>
				<input type="hidden" name="memberName" value="${order.memberName}"/>
				<input type="hidden" name="orderAmount" value="${order.orderAmount}"/>
				<input type="hidden" name="payAmount" value="${order.payableAmount}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">订单冲账</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label">订单编号：</label>
						</td>
						<td>
							<label class="control-label">${order.orderNo}</label>
						</td>
						<td>
							<label class=" control-label">客户名称：</label>
						</td>
						<td>
							<label class="control-label">${order.memberName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">订单金额：</label>
						</td>
						<td>
							<label class="control-label">${order.orderAmount?string(',###.##')}元</label>
						</td>
						<td>
							<label class=" control-label">附加服务费：</label>
						</td>
						<td>
							<label class="control-label">${serviceFeeAmount?string(',###.##')}元</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label key">应付金额：</label>
						</td>
						<td>
							<label class="control-label">${order.payableAmount?string(',###.##')}元</label>
						</td>
						<td>
							<label class="control-label">已冲账金额：</label>
						</td>
						<td>
							<label class="control-label">${order.strikeBalanceAmount?string(',###.##')}元</label>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label">冲账金额：</label>
						</td>
						<td>
							<label class="control-label"><input type="text" name="strikeBalanceAmount" />元</label>
						</td>
						<td>
							<label class=" control-label">冲账原因：</label>
						</td>
						<td>
							<label class="control-label">
                				<select class="form-control" name="strikeBalanceReason">
           							<option value="1">故障</option>
           							<option value="2">没电</option>
          						</select>
                				</label>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label key">冲账备注：</label>
						</td>
						<td>
							<label class="control-label">
	                				 <textarea class="form-control" rows="6"  name="strikeBalanceMemo" ></textarea>
	                			</label>
						</td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addOrderStrikeBalance" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeOrderStrikeBalanceAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
    require.config({paths: {"orderStrikeBalanceAdd":"res/js/order/order_strike_balance_add"}});
		require(["orderStrikeBalanceAdd"], function (orderStrikeBalanceAdd){
			orderStrikeBalanceAdd.init();
		});
	   }); 
    </script>