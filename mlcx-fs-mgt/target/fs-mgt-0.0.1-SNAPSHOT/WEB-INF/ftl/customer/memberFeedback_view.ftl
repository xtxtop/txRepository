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
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;客户：</label>
						</td>
						<td>
							 <label class="control-label val">${customerFeedback.memberName}</label>
						</td>
						<td>
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;手机号：</label>
						</td>
						<td>
							 <label class="control-label val">${customerFeedback.mobilePhone}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;内容：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="6"  name="content" disabled>${customerFeedback.content}</textarea>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;提交时间：</label>
						</td>
						<td>
							 <label class="control-label val">${customerFeedback.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
							<td>
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;类型：</label>
						</td>
						<td>
							<label class="control-label val">
								 <#if customerFeedback.feedbackType==1>咨询
								 <#elseif customerFeedback.feedbackType==2>投诉
								 <#elseif customerFeedback.feedbackType==3>建议
                                 </#if> 
							</label>
						<td>
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${customerFeedback.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closememberFeedbackView" class="btn-new-type-edit">
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
	  require.config({paths: {"customerFeedback":"res/js/customer/memberFeedback_list"}});
		require(["memberFeedback"], function (memberFeedback){
			memberFeedback.init();
		});  
	});
</script>