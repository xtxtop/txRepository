<meta charset="utf-8">

  <div class="container-fluid">
      <div class="row">
       <div class="col-xs-12">
       
       <div class="box">
        <div class="box-header">
         <!-- <h3 class="box-title">结果集</h3> -->
        </div><!-- /.box-header -->
        <div class="box-body">
         <table id="wareStockInItemD" class="table table-bordered table-striped table-hover dataTable no-footer" width="100%" role="grid" aria-describedby="orderLogList_info" style="width: 100%;">
			<thead><tr role="row">
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%;">原厂编号</th>
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%;">商品名称</th>
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%;">规格</th>
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%;">品牌</th>
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%;">送货数</th>
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%;">实收数</th>
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%">物料单价</th>
			<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 12%;">采购单编号</th>
			</tr></thead>
			<tbody>
			<#list wareStockIn.wareStockInItemList as item>
			<tr role="row" class="odd">
				<td>${item.mfrCode}</td>
				<td>${item.itemName}</td>
				<td>${item.skuSalesProps}</td>
				<td>${item.brandName}</td>
				<td>${item.receivableQuantity}</td>
				<td>${item.receivedQuantity}</td>
				<td><#if item.price==0><#else>${item.price}</#if></td>
				<td>${item.poNo}</td>
			</tr>
			</#list>
			</tbody>
		</table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
