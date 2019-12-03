<meta charset="utf-8">
<style>
    .memo{
      line-height:30px; 
      resize:none;
    }
</style>
<div class="container-fluid backgroundColor">
	<form name="memberRechargeAddForm">
		<input type="hidden" name="memberNos" value="${memberNos}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">手工充值</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">充值会员：</label>
						</td>
						<td>
							<input class="form-control val" value="${memberNames}" readonly placeholder="请输入充值会员名称"/>
							<span id="packageIdAdd"></span>
						</td>
						<td>
							<label class=" control-label key">调整金额：</label>
						</td>
						<td>
							<input class="form-control val" name="packAmount" placeholder="请输入金额"/>
							<span id="packAmountAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key reason">备注：</label>
						</td>
						<td>
						<textarea class="form-control val memo" name="memo" placeholder="请填写备注"></textarea>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addMemberRecharge" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeMemberRecharge" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
    	require.config({paths: {"memberRechargeAdd":"res/js/member/member_recharge_add"}});
		require(["memberRechargeAdd"], function (memberRechargeAdd){
			memberRechargeAdd.init();
		});
	   }); 
    </script>