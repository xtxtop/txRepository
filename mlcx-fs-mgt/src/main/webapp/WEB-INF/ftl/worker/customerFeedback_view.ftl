<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="customerFeedbackViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">问题反馈信息详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">*调度员：</label>
						</td>
						<td>
							<label class="control-label val">${customerFeedback.memberName}</label>
						</td>
						<td>
							<label class=" control-label key">*手机号：</label>
						</td>
						<td>
							 <label class="control-label val">${customerFeedback.mobilePhone}</label>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key">*内容：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="6"  name="content" disabled>${customerFeedback.content}</textarea>
						</td>
						<td>
							<label class=" control-label key">*提交时间：</label>
						</td>
						<td>
							<label class="control-label val">${customerFeedback.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key">*类型：</label>
						</td>
						<td>
							<label class="control-label val">
								 <#if customerFeedback.feedbackType==1>咨询
								 <#elseif customerFeedback.feedbackType==2>投诉
								 <#elseif customerFeedback.feedbackType==3>建议
                                 </#if> 
							</label>
						</td>
						<#if customerFeedback.photoUrl1!=null&&customerFeedback.photoUrl1!=''>
						<td>
							<label class=" control-label key">*相关图片1：</label>
						</td>
						<td>
							<label class="control-label val">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl1}" width="200" />
                				</label>
						</td>
						</#if>
					</tr>
					<tr>
						<#if customerFeedback.photoUrl2!=null&&customerFeedback.photoUrl2!=''>
						<td>
							<label class=" control-label key">*相关图片2：</label>
						</td>
						<td>
							<label class="control-label val">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl2}" width="200" />
                				</label>
						</td>
						</#if>
						<#if customerFeedback.photoUrl3!=null&&customerFeedback.photoUrl3!=''>
						<td>
							<label class=" control-label key">*相关图片3：</label>
						</td>
						<td>
							<label class="control-label val">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl3}" width="200" />
                				</label>
						</td>
						</#if>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${customerFeedback.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td></td>
						<td></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeCustomerFeedbackWorkerView" class="btn-new-type-edit">
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
	  require.config({paths: {"customerFeedbackWorker":"res/js/worker/customerFeedback_list"}});
		require(["customerFeedbackWorker"], function (customerFeedbackWorker){
			customerFeedbackWorker.init();
		});  
	});
</script>