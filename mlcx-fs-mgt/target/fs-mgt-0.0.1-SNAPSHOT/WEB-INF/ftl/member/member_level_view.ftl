<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<!--<form name="companyPersonDetailForm">-->
		<input type="hidden" name="companyId" value="${company.companyId}" readonly/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员等级详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;等级名称：</label>
						</td>
						<td>
							<label class="control-label val">${memberLevel.levelName}</label>

						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;等级折扣率：</label>
						</td>
						<td>
							<label class="control-label val">${memberLevel.levelDiscount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;晋升所需消费额：</label>
						</td>
						<td>
							<label class="control-label val">${memberLevel.upgradePoint}</label>
						</td>

						<td>
							<label class="col-sm-6 control-label reason key"><span></span>&nbsp;&nbsp;备注：</label>
						</td>
						<td>
							<label class="control-label val">${memberLevel.memo}</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;是否可用：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if memberLevel.isAvailable==1>可用
									<#elseif memberLevel.isAvailable==0>不可用
								</#if>
							</label>
						</td>
						<td>
						</td>
						<td>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeMemberLevelView" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	<!--</form>-->
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberLevel":"res/js/member/member_level_list"}});
		require(["memberLevel"], function (memberLevel){
			memberLevel.init();
		});  
	});    
</script>