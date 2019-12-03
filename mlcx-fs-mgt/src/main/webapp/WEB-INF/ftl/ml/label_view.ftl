<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="labelAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">标签详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span></span>标签ID：</label>
						</td>
						<td><label class="control-label val">
								${label.labelId} </label></td>
						<td><label class=" control-label key"><span></span>标签名称：</label>
						</td>
						<td><label class="control-label val">
								${label.labelName} </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>状态：</label>
						</td>
						<td><label class="control-label val"> <#if
								label.enableStatus==1> 启用 <#else> 停用 </#if> </label></td>
						<td><label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td><label class="control-label val">
								${label.createTime?string('yyyy-MM-dd HH:mm:ss')}
						</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key">操作人：</label></td>
                        <td><label class="control-label val">
                                ${label.userName} </label></td>
						<td><label class=" control-label key">修改时间：</label></td>
                        <td><label class="control-label val">
                                ${label.updateTime?string('yyyy-MM-dd HH:mm:ss')}
                               </label></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closelabelView" onclick="closeTab();" class="btn-new-type-edit">
								关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
