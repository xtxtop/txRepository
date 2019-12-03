<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form >
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">商品详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>商品分类：</label>
						</td>
						<td>
							<label class="control-label val">
							   ${item.sortName}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>商品名称：</label>
						</td>
						<td>
							 <label class="control-label val">${item.itemName}</label>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>积分：</label>
						</td>
						<td>
							<label class="control-label val">${item.points}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>数量：</label>
						</td>
						<td>
							<label class="control-label val">${item.num}</label>
						</td>
						
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${item.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
								<#if item.content!=null&&item.content!=''>
						<td>
							<label class=" control-label key"><span></span>描述：</label>
						</td>
						<td>
							<div class="">
							${item.content}
							</div>
						</td>
						</#if>
						<td colspan="2"></td>
					</tr>
					<tr>
				
						<#if item.picUrl!=null&&item.picUrl!=''>
						<td>
							<label class=" control-label key"><span></span>商品图片：</label>
						</td>
						<td>
							<label class="control-label val">
                				<img src="${imagePath!''}/${item.picUrl}" width="200" height="180"/>
                				</label>
						</td>
						</#if>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeItemView" class="btn-new-type-edit">
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
	  require.config({paths: {"item":"res/js/marketing/item_list"}});
		require(["item"], function (item){
			item.init();
		});  
	});
</script>