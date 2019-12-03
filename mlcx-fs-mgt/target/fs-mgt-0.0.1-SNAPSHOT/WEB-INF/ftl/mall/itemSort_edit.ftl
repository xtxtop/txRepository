<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="itemSortEditFrom">
		<input type="hidden" name="sortNo" value="${itemSort.sortNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">商品分类编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;商品分类：</label>
						</td>
						<td>
							<input class="form-control val" name="sortName" value="${itemSort.sortName}"/>
							<span name="sortNameEdit"></span>
						</td>
						<td>
							<label for="formParentSortName" class="col-sm-3 control-label key">父级分类：</label>
						</td>
						<td>
							<div class="col-sm-8" id="itemSortTreeSelEdit"></div>
                            <input type="hidden" id="formParentSortNo" name="parentSortNo" value="${itemSort.parentSortNo}">
						    <span name="parentSortNoEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editItemSort" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditItemSort" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"itemSortEdit":"res/js/mall/itemSort_edit"}});
		require(["itemSortEdit"], function (itemSortEdit){
			itemSortEdit.init();
		});  
    });
</script>