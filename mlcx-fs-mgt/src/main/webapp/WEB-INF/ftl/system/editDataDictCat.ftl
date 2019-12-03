<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="dataDictCatEditForm">
		<input type="hidden" name="dataDictItemId" value="${dataDictItem.dataDictItemId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">数据字典分类编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">数据字典分类代码：</label>
						</td>
						<td>
							<input class="form-control val" name="dataDictCatCode" value="${dataDictCat.dataDictCatCode}" readonly/>
							<span id="dataDictCatCodeEdit"></span>
						</td>
					<td>
						<label class=" control-label key">数据字典分类名称：</label>
					</td>
					<td>
						<select name="parentCatCode" class="form-control val" id="parentCatCodeEdit">
							    	<option value="">请选择</option>
							   <input class="form-control val" name="dataDictCatName" value="${dataDictCat.dataDictCatName}"/>
							   <span id="dataDictCatNameEdit"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">父分类代码：</label>
						</td>
						<td>
							<select name="parentCatCode" class="form-control val">
							    	<option value="">请选择</option>
							    	<#if dataDictCatList?exists>
							    		<#list dataDictCatList as dictCat>
							    			<option value="${dictCat.dataDictCatCode}" <#if dataDictCat.parentCatCode==dictCat.dataDictCatCode>selected=selected</#if>>${dictCat.dataDictCatName}</option>
							    		</#list>
							    	</#if>
							    </select>
							<span id="parentCatCodeEdit"></span>
						</td>
						<td>
							<label class=" control-label key">是否可用：</label>
						</td>
						<td>
							<input type="radio" name="isAvailable" value="1" <#if dataDictCat.isAvailable==1>checked=checked</#if>/>可用
								<input type="radio" name="isAvailable" value="0" <#if dataDictCat.isAvailable==0>checked=checked</#if>/>不可用
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key">备注：</label>
						</td>
						<td>
							<textarea class="form-control val" name="memo">${dataDictCat.memo}</textarea>
							<span id="memoEdit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveEditDataDictCat" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditDataDictCat" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"dataDictCatEdit":"res/js/system/dataDictCatEdit"}});
		require(["dataDictCatEdit"], function (dataDictCatEdit){
			dataDictCatEdit.init();
		});  
	});
</script>