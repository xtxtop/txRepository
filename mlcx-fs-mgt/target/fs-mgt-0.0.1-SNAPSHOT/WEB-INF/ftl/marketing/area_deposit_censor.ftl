<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="areaDepositForm">
		<input type="hidden" name="id" value="${areaDeposit.id}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">地区应缴押金审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">*地区名称：</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.addrRegion}</label>
						</td>
						<td>
							<label class="control-label key"><span>*</span>应缴押金：</label>
						</td>
						<td>
							 <label class="control-label val">${areaDeposit.depositAmount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*审核状态：</label>
						</td>
						<td>
							<input type="radio" name="censorStatus"  value="1" checked="checked">通过</input>
                                <input type="radio" name="censorStatus"  value="3" >不通过</input>
						</td>
						<td>
							<label class=" control-label key">*审核备注：</label>
						</td>
						<td>
							 <textarea class="form-control val" rows="6"  name="censorMemo" >${areaDeposit.censorMemo}</textarea>
						<span id="censorMemoCensor"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key">*更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${areaDeposit.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="censorAreaDeposit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAreaDeposit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"areaDepositCensor":"res/js/marketing/area_deposit_censor"}});
		require(["areaDepositCensor"], function (areaDepositCensor){
			areaDepositCensor.init();
		});  
	    
	});
	
</script>
