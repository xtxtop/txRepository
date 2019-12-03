<meta charset="utf-8">
<style>
.seach-input-item pull-left .form-control{
	width:50% !important;
}
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.seach-input-item pull-left .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
white-space: nowrap;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
white-space: nowrap;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
  <div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
       <form name="moneySearchForm">
<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>对账周期</span><input class="datepicker form-control" name="startTime"  value="${startTime}" placeholder=""/>
                </div>
		       <div class="seach-input-item seach-input-item-change pull-left">
                   <span>至</span><input class="datepicker form-control" name="endTime" value="${endTime}" placeholder=""/>
               </div>

		</div><!-- /.row -->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="moneySearch">确定</button>
					</div>
        </div><!-- /.box-body -->
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="moneyBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
        <div class="row">
        	<div id="moneyTools" class="col-xs-12">
        		<button type="button" class="btn-new-type btn-new-type-change-float-right moneyTools-operate-export" id="moneyTools-operate-export"  style="float: right;">导出</button>
        		<button type="button" class="btn-new-type btn-new-type-change-float-right moneyTools-operate-import" id="moneyTools-operate-import" style="float: right;">导入转账数据</button>
        	</div>
        </div>
        <form name="moneyDetailForm">
         <table id="moneyList" class="table table-hover table-bettween-far" width="100%" border="1">
         	<thead>
	 			<tr role="row">
	 				<th class="sorting_disabled" rowspan="1" colspan="1" ></th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" ></th>
	   				<th class="sorting_disabled" rowspan="1" colspan="3" >交易收入</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >退款</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="3" >手续费</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >手续费退还</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >合计</th>
	  			</tr>
	  			<tr role="row">
	 				<th class="sorting_disabled" rowspan="1" colspan="1" ></th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" ></th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >订单</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >套餐</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >押金</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" ></th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >订单</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >套餐</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" >押金</th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" ></th>
	   				<th class="sorting_disabled" rowspan="1" colspan="1" ></th>
	  			</tr>
			</thead>
			<tbody>
	  			<tr role="row" class="odd">
	  				<td rowspan="3" colspan="1">支付宝</td>
	   				<td>应收</td>
	   				<td><span name="orderAlipayAmount"></span></td>
	   				<td><span name="priceAlipayAmount"></span></td>
	   				<td><span name="depositAlipayAmount"></span></td>
	   				<td><span name="depositAlipayRefund"></span></td>
	   				<td><span name="orderAlipayCharge"></span></td>
	   				<td><span name="priceAlipayCharge"></span></td>
	   				<td><span name="depositAlipayCharge"></span></td>
	   				<td>0.00</td>
	   				<td><span name="alipayAmount"></span></td>
	  			</tr>
	  			<tr role="row" class="even">
	  				<td>实收</td>
	  				<td><span name="orderAlipayAmountWhole"></span></td>
	  				<td><span name="priceAlipayAmountWhole"></span></td>
	  				<td><span name="depositAlipayAmountWhole"></span></td>
	  				<td><span name="depositAlipayRefundWhole"></span></td>
	  				<td><span name="orderAlipayChargeWhole"></span></td>
	  				<td><span name="priceAlipayChargeWhole"></span></td>
	  				<td><span name="depositAlipayChargeWhole"></span></td>
	  				<td>0.00</td>
	  				<td><span name="awhole"></span></td>
	  			</tr>
	  			<tr role="row" class="odd">
	  				<td>异常</td>
	  				<td><span name="orderAlipayAmountTest"></span></td>
	  				<td><span name="priceAlipayAmountTest"></span></td>
	  				<td><span name="depositAlipayAmountTest"></span></td>
	  				<td><span name="depositAlipayRefundTest"></span></td>
	  				<td><span name="orderAlipayChargeTest"></span></td>
	  				<td><span name="priceAlipayChargeTest"></span></td>
	  				<td><span name="depositAlipayChargeTest"></span></td>
	  				<td>0.00</td>
	  				<td><span name="asw"></span></td>
	  			</tr>
	  			<tr role="row" class="even">
	  				<td rowspan="3" colspan="1">微信</td>
	   				<td>应收</td>
	   				<td><span name="orderWxAmount"></span></td>
	   				<td><span name="priceWxAmount"></span></td>
	   				<td><span name="depositWxAmount"></span></td>
	   				<td><span name="depositWxRefund"></span></td>
	   				<td><span name="orderWxCharge"></span></td>
	   				<td><span name="priceWxCharge"></span></td>
	   				<td><span name="depositWxCharge"></span></td>
	   				<td><span name="chargeRefund"></span></td>
	   				<td><span name="wxAmount"></span></td>
	  			</tr>
	  			<tr role="row" class="odd">
	  				<td>实收</td>
	  				<td><span name="orderWxAmountWhole"></span></td>
	  				<td><span name="priceWxAmountWhole"></span></td>
	  				<td><span name="depositWxAmountWhole"></span></td>
	  				<td><span name="depositWxRefundWhole"></span></td>
	  				<td><span name="orderWxChargeWhole"></span></td>
	  				<td><span name="priceWxChargeWhole"></span></td>
	  				<td><span name="depositWxChargeWhole"></span></td>
	  				<td><span name="chargeRefundWhole"></span></td>
	  				<td><span name="awholeWx"></span></td>
	  			</tr>
	  			<tr role="row" class="even">
	  				<td>异常</td>
	  				<td><span name="orderWxAmountTest"></span></td>
	  				<td><span name="priceWxAmountTest"></span></td>
	  				<td><span name="depositWxAmountTest"></span></td>
	  				<td><span name="depositWxRefundTest"></span></td>
	  				<td><span name="orderWxChargeTest"></span></td>
	  				<td><span name="priceWxChargeTest"></span></td>
	  				<td><span name="depositWxChargeTest"></span></td>
	  				<td><span name="chargeRefundTest"></span></td>
	  				<td><span name="aswWx"></span></td>
	  			</tr>
	  		</tbody>
         </table>
         </form>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"money":"res/js/statement/money_list"}});
		require(["money"], function (money){
			money.init();
		});  
	});
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: "linked",//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
