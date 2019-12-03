<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carDayViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">服务范围详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
<label class=" control-label key"><span></span>&nbsp;&nbsp;名称：</label>
						</td>
						<td>
							 <label class="control-label val">${dayCarArea.areaName}</label>
						</td>
					<td>
						<label class=" control-label key"><span></span>&nbsp;&nbsp;区域：</label>
					</td>
					<td>
						<label class="control-label val">${dayCarArea.addrRegion1Name}${dayCarArea.addrRegion2Name}${dayCarArea.addrRegion3Name}</label>
					</td>
					</tr>
				</tbody>
				</tfoot>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closedayCarModelVo" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	                       
</div>

<script type="text/javascript">
$("#closedayCarModelVo").click(function(){
	closeTab("服务范围详情");
});
</script>