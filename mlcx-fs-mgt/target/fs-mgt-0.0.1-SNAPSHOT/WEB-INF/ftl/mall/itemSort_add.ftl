<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="itemSortAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增商品分类</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>商品分类：</label>
						</td>
						<td>
							<input class="form-control val" name="sortName" placeholder="请输入商品分类"/>
							<span name="sortNameAdd"></span>
						</td>
						<td>
							<label for="formParentSortName" class=" control-label key">父级分类：</label>
						</td>
						<td>
							<div class="" id="itemSortTreeSelAdd"></div>
                            <input type="hidden" id="formParentSortNo" name="parentSortNo">
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addItemSort" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddItemSort" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"itemSortAdd":"res/js/mall/itemSort_add"}});
		require(["itemSortAdd"], function (itemSortAdd){
			itemSortAdd.init();
		});  
    });
</script>