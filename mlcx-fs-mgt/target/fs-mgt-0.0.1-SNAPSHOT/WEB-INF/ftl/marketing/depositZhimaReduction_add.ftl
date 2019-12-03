<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="depositZhimaReductionAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">芝麻分减免押金新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>芝麻分：</label>
						</td>
						<td>
							<input class="form-control val" name="zhimaScore" placeholder="请输入芝麻分"/>
							<span name="zhimaScoreAdd"></span>
						</td>
						<td  class="form-group" id="moneyDiv">
							<label class=" control-label key"><span>*</span>减免金额(元)：</label>
						</td>
						<td  class="form-group" id="moneyDiv">
							 <input class="form-control val" name="reductionAmount" maxlength="6" placeholder="请输入满减金额"/>
							 <span name="reductionAmountAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addDepositZhimaReduction" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddDepositZhimaReduction" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"depositZhimaReductionAdd":"res/js/marketing/depositZhimaReduction_add"}});
		require(["depositZhimaReductionAdd"], function (depositZhimaReductionAdd){
			depositZhimaReductionAdd.init();
		});  
	});    
</script>
