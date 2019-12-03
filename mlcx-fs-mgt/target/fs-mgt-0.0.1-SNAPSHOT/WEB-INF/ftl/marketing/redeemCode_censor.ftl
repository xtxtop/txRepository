<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="redeemCodeCensorForm">
		<input type="hidden" name="redCode" value="${redeemCode.redCode}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">兑换码审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>名称：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.redName}</label>
						</td>
						<td>
							<label class=" control-label key">&nbsp;&nbsp;兑换码：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.redCode}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">&nbsp;&nbsp;有效日期：</label>
						</td>
						<td>
							 <label class="control-label val">${redeemCode.availableTime1?string('yyyy-MM-dd ')}  至  ${redeemCode.availableTime2?string('yyyy-MM-dd ')} </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${redeemCode.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key">&nbsp;&nbsp;审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
	                               <input type="radio" name="censorStatus"value="1" checked="checked"> 已审核
                              </label>
                             <label class="control-label val">
	                               <input type="radio" name="censorStatus" value="2">不通过
                              </label>
						</td>
						<td>
							<label class=" control-label key reason">&nbsp;&nbsp;审核备注：</label>
						</td>
						<td>
							<label class="control-label val">
									<textarea class="form-control val" name="censorMemo" style="height: 150px"></textarea>
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label reason key"><span>*</span>&nbsp;&nbsp;优惠方案：</label>
						</td>
						<td colspan="3">
							<table id="couPonPlanLists_censor" class="table table-bordered table-striped table-hover"></table>
							<span name="planNoAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editRedeemCodeCensor" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeRedeemCodeCensor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"redeemCodeCensor":"res/js/marketing/redeemCode_censor"}});
		require(["redeemCodeCensor"], function (redeemCodeCensor){
			redeemCodeCensor.init();
		});  
	}); 
</script>