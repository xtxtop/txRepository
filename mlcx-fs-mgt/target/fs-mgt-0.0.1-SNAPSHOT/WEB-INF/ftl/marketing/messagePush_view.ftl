<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="messagePushViewForm">
		<input type="hidden" name="id" value="${messagePush.id}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">消息推送详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;推送方式：</label>
						</td>
						<td>
							<label class="control-label val">
							   	<#if messagePush.pushPattern==1>多个用户
                                <#elseif messagePush.pushPattern==2>全部用户
                                </#if>
							</label>
						</td>
						<#if messagePush.pushPattern==1>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;推送会员：</label>
						</td>
						<td>
							 <label class="control-label val">${messagePush.memberName}</label>
						</td>
						</#if>
						<td colspan="2"></td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;标题：</label>
						</td>
						<td>
							<label class="control-label val">${messagePush.title}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;内容：</label>
						</td>
						<td>
							<textarea name="content" class="form-control val" disabled="disabled">${messagePush.content}</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;推送状态：</label>
						</td>
						<td>
							<label class="control-label val">
	                                <#if messagePush.pushStatus==0>未推送
	                                <#elseif messagePush.pushStatus==1>已推送
	                                </#if>
                                </label>
						</td>
						 <#if messagePush.pushStatus==1>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;推送时间：</label>
						</td>
						<td>
							<label class="control-label val">${messagePush.pushTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						</#if>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${messagePush.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeMessagePushView" class="btn-new-type-edit">
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
	  require.config({paths: {"messagePush":"res/js/marketing/messagePush_list"}});
		require(["messagePush"], function (messagePush){
			messagePush.init();
		});  
	});
</script>
