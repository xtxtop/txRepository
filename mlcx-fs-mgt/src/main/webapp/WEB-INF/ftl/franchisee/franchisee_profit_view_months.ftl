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
<div class="container-fluid backgroundColor">
	<form name="memberListForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">加盟商收益统计详情（按月度）</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;加盟商名称：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeProfitVo.franchiseeName}</label>
						</td>
						<td>
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;月份：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeProfitVo.year}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;订单数：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeProfitVo.orderNumber}</label>
						</td>
						<td>
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;订单金额：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeProfitVo.orderAmount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;分润额：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeProfitVo.profitAmount}</label>
						</td>
						<td>
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;车辆分润：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeProfitVo.carProfit}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;场站分润：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeProfitVo.parkProfit}</label>
						</td>
						<td>
						</td>
						<td>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeFranchiseeMonthView" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>

	</form>
</div>

<script type="text/javascript">
	$(function() {
		$("#closeFranchiseeMonthView").click(function() {
			closeTab("查看明细(按月)");
		});

	});
</script>