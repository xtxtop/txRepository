<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="depositZhimaReductionEditFrom">
		<input class="form-control val" name="id" value="${depositZhimaReduction.id}" readonly style="display: none"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">芝麻分扣免押金编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>芝麻分：</label>
						</td>
						<td>
							<input class="form-control val" name="zhimaScore" value="${depositZhimaReduction.zhimaScore}"/>
							<span name="zhimaScoreEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>减免金额(元)：</label>
						</td>
						<td>
							 <input class="form-control val" name="reductionAmount" value="${depositZhimaReduction.reductionAmount}"/>
							 <span name="reductionAmountEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editDepositZhimaReduction" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditDepositZhimaReduction" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"depositZhimaReductionEdit":"res/js/marketing/depositZhimaReduction_edit"}});
		require(["depositZhimaReductionEdit"], function (depositZhimaReductionEdit){
			depositZhimaReductionEdit.init();
		});  
	});    
</script>
