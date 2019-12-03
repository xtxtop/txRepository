<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="matchingAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">配套服务详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-matching key"><span></span>配套服务ID：</label>
						</td>
						<td><label class="control-matching val">
								${CMatching.matchingId} </label></td>
						<td><label class=" control-matching key"><span></span>配套服务名称：</label>
						</td>
						<td><label class="control-matching val">
								${CMatching.matchingName} </label></td>
					</tr>
					<tr>
						<td><label class=" control-matching key"><span></span>状态：</label>
						</td>
						<td><label class="control-matching val"> <#if
								CMatching.enableStatus==1> 启用 <#else> 停用 </#if> </label></td>
						 <td><label class=" control-label key">服务图片：</label></td>
                        <td><label class="control-label val">
                               <img src="${imagePath!''}/${CMatching.matchingPicUrl}" width="100" height="80"/>
                               </label></td>
					</tr>
					<tr>	
						<td><label class=" control-matching key"><span></span>创建时间：</label>
						</td>
						<td><label class="control-matching val">
								${CMatching.createTime?string('yyyy-MM-dd HH:mm:ss')}
						</label></td>
						<td><label class=" control-matching key">修改时间：</label></td>
                        <td><label class="control-matching val">
                                ${CMatching.updateTime?string('yyyy-MM-dd HH:mm:ss')}
                               </label></td>
					</tr>
					<tr>
						<td><label class=" control-matching key">操作人：</label></td>
                        <td colspan="3"><label class="control-matching val">
                                ${CMatching.userName} </matching></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closematchingView" onclick="closeTab();" class="btn-new-type-edit">
								关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
