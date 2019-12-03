<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		. .form-control {
			width: 45% !important;
			margin-right: 20%;
		}
		.pull-down-menu span {
			width: 20%;
		}
	}
	
	@media only screen and (max-width:768px) {
		.row .sorting_disabled {
			font-size: 1.1rem !important;
		}
	}
	
	@media only screen and (min-width:768px) and (max-width:1024px) {
		.row .sorting_disabled {
			font-size: 1.2rem !important;
		}
	}
	
	@media only screen and (min-width:1024px) and (max-width:1366px) {
		.row .sorting_disabled {
			font-size: 1.3rem !important;
		}
	}
	
	.other {
		margin: 0 !important;
		padding: 0 !important;
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
   <div class="container-fluid">
     <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
         <form class="form-horizontal" name="orderDayRefundSearchForm">
       <div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>订单编号</span>
                    <input type="text" class="form-control" id="orderNo" name="orderNo" placeholder="请输入订单编号">
                </div>
             	<div class="seach-input-item pull-left">
               	 <span>手机号码</span>
               	 <input type="text" class="form-control" name="mobilePhone" placeholder="请输入手机号码">
             	</div>
                 <div class="seach-input-item pull-left">
                     <span>支付方式</span><select class="form-control" name="paymentMethod">
                       <option value="">全部</option>
                       <option value="1">支付宝</option>
                       <option value="2">微信</option>
                      </select>
                </div>
                 <div class="seach-input-item pull-left">
                     <span>退款状态</span><select class="form-control" name="refundStatus">
                       <option value="">全部</option>
                       <option value="0">未退款</option>
                       <option value="1">已退款</option>
                       <option value="2">退款失败</option>
                      </select>
                </div>
          </div><!-- /.row -->
          <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="orderDayRefundSearchafter">确定</button>
					</div>
         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="orderDayRefundTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="orderDayRefundList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>

    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"orderDayRefund":"res/js/dailyrental/finance/order_day_refund_list"}});
		require(["orderDayRefund"], function (orderDayRefund){
			orderDayRefund.init();
		}); 
	   }); 
    </script>
