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
         <form class="form-horizontal" name="orderDaySearchForm">
       <div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>订单编号</span>
                    <input type="text" class="form-control" id="orderNo" name="orderNo" placeholder="请输入订单编号">
                </div>
            <div class="seach-input-item pull-left">
                <span>订单状态</span><select class="form-control" name="orderStatus">
                       <option value="">全部</option>
                       <!--<option value="0">已完成</option>-->
                       <option value="1">已预约</option>
                       <option value="2">已计费</option>
                       <option value="3">已结束</option>
                       <option value="4">已取消</option>
                      </select>
            </div>
          <input type="hidden"  id = "takeCarDoorAmount"  value="${takeCarDoorAmount}"/>
           <input type="hidden"  id = "cancelAmount"  value="${cancelAmount}"/>
                    <div class="seach-input-item pull-left">
                         <span>支付状态</span>
                         <select class="form-control" name="payStatus">
                           <option value="">全部</option>
                           <option value="0">未支付</option>
                           <option value="1">已支付</option>
                           <!--<option value="2">未结算</option>-->
                          </select>
                    </div>
                 <div class="seach-input-item pull-left">
                    <span>手机号码</span>
                    <input type="text" class="form-control" name="mobilePhone" placeholder="请输入手机号码">
                </div>
                 <div class="seach-input-item pull-left">
                    <span>商家名称</span>
                    <select class="form-control" name="merchantId" placeholder="">
                    	 <option value="">全部</option>
                    	 <#list merchantList as merchant>
                            <option value="${merchant.merchantId}" >
                                ${merchant.merchantName}
                            </option>
                         </#list>
                    </select>		
                </div>
          </div><!-- /.row -->
          <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="orderDaySearchafter">确定</button>
					</div>
         </div>
         </form>
       </div><!-- /.box -->
	  </div><!-- /.col -->	
    <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="orderDayTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="orderDayList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>

    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"orderDay":"res/js/dailyrental/orderday/order_day_list"}});
		require(["orderDay"], function (orderDay){
			orderDay.init();
		}); 
	   }); 
    </script>
