<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="dataDictCatEditForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">数据字典项详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">数据字典分类代码：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItem.dataDictCatCode}</label>
						</td>
						<td>
							<label class=" control-label key">数据字典项值：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItem.itemValue}</label>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key">数据字典父项值：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItemp.itemValue}</label>
						</td>
						<td>
							<label class=" control-label key">是否可用：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if dataDictItem.isAvailable==1>
								可用
								<#else>
								不可用
								</#if>
							</label>
						</td>
						
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key">是否删除：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if dataDictItem.isDeleted==1>
								已删除
								<#else>
								未删除
								</#if>
								</div>
							</label>
						</td>
						<td>
							<label class=" control-label key">创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItem.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItem.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key">扩展信息1：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItem.info1}</label>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">扩展信息2：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItem.info2}</label>
						</td>
						<td>
							<label class=" control-label key">备注：</label>
						</td>
						<td>
							<label class="control-label val">${dataDictItem.memo}</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeViewDataDictItem" class="btn-new-type-edit">
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
	  require.config({paths: {"dataDictItem":"res/js/system/dataDictItem"}});
		require(["dataDictItem"], function (dataDictItem){
			dataDictItem.init();
		});  
	});
</script>