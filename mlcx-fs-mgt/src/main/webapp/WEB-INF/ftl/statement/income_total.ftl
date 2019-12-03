<meta charset="utf-8">

  <div class="container-fluid">
   <div class="search-input-wrapper">
  <div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
     <div class="search-input-content">
      <!-- /.box-header -->
       <form name="moneySearchForm">
     	<div class="seach-input-border-box">
        <div class="seach-input-details seach-input-details-change close-content clearfix">
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>对账周期</span>
                    <input class="datepicker form-control" name="startTime"  value="${startTime}" placeholder="请输入对账周期开始时间"/>
                </div>
		       <div class="seach-input-item seach-input-item-change pull-left">
                   <span>至</span>
                   <input class="datepicker form-control" name="endTime" value="${endTime}" placeholder="请输入对账周期结束时间"/>
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
        	<div id="moneyTools">
        		<button type="button" class="btn-new-type moneyTools-operate-export" id="moneyTools-operate-export">导出</button>
        	</div>
        </div>
        <form name="moneyDetailForm">
         <table id="moneyList" class="table table-hover table-bettween-far dataTable" width="100%" border="1">
         	<thead>
	 			<tr role="row">
	 				<th class="sorting_disabled"></th>
	   				<th class="sorting_disabled">订单收入</th>
	   				<th class="sorting_disabled">实收订单</th>
	   				<th class="sorting_disabled">押金</th>
	   				<th class="sorting_disabled">套餐</th>
	   				<th class="sorting_disabled">订单手续费</th>
	   				<th class="sorting_disabled">押金手续费</th>
	   				<th class="sorting_disabled">套餐手续费</th>
	   				<th class="sorting_disabled">退款</th>
	  			</tr>
			</thead>
			<tbody>
	  			<tr role="row" class="odd">
	  				<td>微信</td>
	   				<td></td>
	   				<td><span name="orderWxAmount"></span></td>
	   				<td><span name="depositWxAmount"></span></td>
	   				<td><span name="priceWxAmount"></span></td>
	   				<td><span name="orderWxCharge"></span></td>
	   				<td><span name="depositWxCharge"></span></td>
	   				<td><span name="priceWxCharge"></span></td>
	   				<td><span name="depositWxRefund"></span></td>
	  			</tr>
	  			<tr role="row" class="even">
	  				<td>支付宝</td>
	   				<td></td>
	   				<td><span name="orderAlipayAmount"></span></td>
	   				<td><span name="depositAlipayAmount"></span></td>
	   				<td><span name="priceAlipayAmount"></span></td>
	   				<td><span name="orderAlipayCharge"></span></td>
	   				<td><span name="depositAlipayCharge"></span></td>
	   				<td><span name="priceAlipayCharge"></span></td>
	   				<td><span name="depositAlipayRefund"></span></td>
	  			</tr>
	  			<tr role="row" class="odd">
	  				<td>合计</td>
	   				<td><span name="orderIncomeTotal"></span></td>
	   				<td><span name="orderAmountTotal"></span></td>
	   				<td><span name="depositAmountTotal"></span></td>
	   				<td><span name="priceAmountTotal"></span></td>
	   				<td><span name="orderChargeTotal"></span></td>
	   				<td><span name="depositChargeTotal"></span></td>
	   				<td><span name="priceChargeTotal"></span></td>
	   				<td><span name="depositRefundTotal"></span></td>
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
	  require.config({paths: {"money":"res/js/statement/income_total"}});
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
