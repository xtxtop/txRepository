<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="customerFeedbackViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">客服反馈信息详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">客户：</label>
						</td>
						<td>
							<label class="control-label val">${pa.memberName}</label>
						</td>
						<td>
							<label class=" control-label">*手机号：</label>
						</td>
						<td>
							<label class="control-label val">${pa.mobilePhone}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*内容：</label>
						</td>
						<td>
						<textarea class="form-control val" rows="6"  name="content" disabled>${pa.memo}</textarea>
						<td>
							<label class=" control-label key">*提交时间：</label>
						</td>
						<td>
							 <label class="control-label val">${pa.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">更新时间：</label>
						</td>
						<td>
							 <label class="control-label val">${pa.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeparkOpeningView" class="btn-new-type-edit">
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
	  require.config({paths: {"customerFeedback":"res/js/resource/parkOpening_list"}});
		require(["parkOpening"], function (parkOpening){
			parkOpening.init();
		});  
	});
</script>