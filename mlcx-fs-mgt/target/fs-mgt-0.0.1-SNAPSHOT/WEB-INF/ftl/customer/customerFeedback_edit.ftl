<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="customerFeedbackEditForm">
		<input type="hidden" name="feedbackNo" value="${customerFeedback.feedbackNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">客服回复信息编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label">*客户：</label>
						</td>
						<td>
							<label class="control-label">${customerFeedback.memberName}</label>
						</td>
						<td>
							<label class=" control-label">*手机号：</label>
						</td>
						<td>
							<label class="control-label">${customerFeedback.mobilePhone}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*内容：</label>
						</td>
						<td>
							<textarea class="form-control" rows="6"  name="content" disabled>${customerFeedback.content}</textarea>
						<td>
							<label class=" control-label">*相关图片1：</label>
						</td>
						<td>
							<label class="control-label">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl1}" width="200"/>
                				</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*相关图片2：</label>
						</td>
						<td>
							<label class="control-label">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl2}" width="200"/>
                				</label>
						</td>
						<td>
							<label class=" control-label">*相关图片3：</label>
						</td>
						<td>
							<label class="control-label">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl2}" width="200"/>
                				</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*提交时间：</label>
						</td>
						<td>
							<label class="control-label">${customerFeedback.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label">*类型：</label>
						</td>
						<td>
							<label class="control-label">
								 <#if customerFeedback.feedbackType==1>咨询
								 <#elseif customerFeedback.feedbackType==2>投诉
								 <#elseif customerFeedback.feedbackType==3>建议
                                 </#if> 
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*回复状态：</label>
						</td>
						<td>
							<label class="control-label">
								 <#if customerFeedback.replyStatus==0>未回复
								 <#elseif customerFeedback.feedbackType==1>已回复
                                 </#if> 
							</label>
						</td>
						<td>
							<label class=" control-label">*客服回复内容：</label>
						</td>
						<td>
							<textarea class="form-control" rows="6"  name="replyContent" >${customerFeedback.replyContent}</textarea>
							<span name="replyContentEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*更新时间：</label>
						</td>
						<td>
							<label class="control-label">${customerFeedback.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editCustomerFeedback" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCustomerFeedbackEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"customerFeedbackEdit":"res/js/customer/customerFeedback_edit"}});
		require(["customerFeedbackEdit"], function (customerFeedbackEdit){
			customerFeedbackEdit.init();
		});  
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
});
</script>