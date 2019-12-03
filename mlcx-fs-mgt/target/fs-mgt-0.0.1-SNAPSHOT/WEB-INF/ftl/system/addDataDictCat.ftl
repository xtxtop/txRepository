<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="dataDictCatAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">数据字典分类新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">数据字典分类代码：</label>
						</td>
						<td>
							<input class="form-control val" name="dataDictCatCode"  placeholder="请完善信息"/>
							<span id="dataDictCatCodeAdd"></span>
						</td>
					<td>
						<label class=" control-label key">数据字典分类名称：</label>
					</td>
					<td>
						<input class="form-control val" name="dataDictCatName"  placeholder="请完善信息"/>
						<span id="dataDictCatNameAdd"></span>
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
							    		<#list dataDictCatList as dataDictCat>
							    			<option value="${dataDictCat.dataDictCatCode}">${dataDictCat.dataDictCatName}</option>
							    		</#list>
							    	</#if>
							    </select>
							<span id="parentCatCodeAdd"></span>
						</td>
						<td>
							<label class=" control-label key">备注：</label>
						</td>
						<td>
							<textarea class="form-control val" name="memo"  placeholder="请完善信息"/>
							<span id="memoAdd"></span>
						</td>

					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveAddDataDictCat" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddDataDictCat" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"dataDictCatAdd":"res/js/system/dataDictCatAdd"}});
		require(["dataDictCatAdd"], function (dataDictCatAdd){
			dataDictCatAdd.init();
		});  
	});
</script>