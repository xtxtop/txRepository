<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="depositRefundCencorForm">
		<input type="hidden" name="refundNo" value="${depositRefund.refundNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">退款审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">客户名称：</label>
						</td>
						<td>
							 <label class="control-label val">${depositRefund.memberName}
							    </label>
						</td>
						<td>
							<label class=" control-label key">客户手机号：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.mobilePhone}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">退款金额：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.refundAmount?string(',###.##')}</label>
						<td>
							<label class=" control-label key">审核结果：</label>
						</td>
						<td>
							<input type="radio" name="cencorStatus"  value="1" checked="checked">通过</input>
                                <input type="radio" name="cencorStatus"  value="3" >不通过</input>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">审核通过时请确认：</label>
						</td>
						<td>
							<label class="checkbox-inline">
		                    	<input type="checkbox"  class="butcheck" value="确认无欠费"> 确认无欠费
								</label>
								<br/>
								<label class="checkbox-inline">
								<input type="checkbox"  class="butcheck" value="确认无未处理的违章">确认无未处理的违章
								</label>
								<br/>
								<label class="checkbox-inline">
								<input type="checkbox" class="butcheck" value="确认车辆无损坏">确认车辆无损坏
								</label>
								<br/>
							 <input type="hidden" name="cencorConfirmItem" id="cencorConfirmItem" value="${depositRefund.cencorConfirmItem}">	
						</td>
						<td>
							<label class=" control-label key">审核备注：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="6"  name="cencorMemo" >${depositRefund.cencorMemo}</textarea>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="cencorDepositRefund" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeDepositRefundCencor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"depositRefundCencor":"res/js/finace/depositRefund_cencor"}});
		require(["depositRefundCencor"], function (depositRefundCencor){
			depositRefundCencor.init();
		});  
	});
</script>