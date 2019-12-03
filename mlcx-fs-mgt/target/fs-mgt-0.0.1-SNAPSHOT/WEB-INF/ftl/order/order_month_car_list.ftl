<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
       
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
     <input type="hidden" name="orderNoCarNs" value="${orderNo}">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="orderMonthCarTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="orderMonthCarList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"orderMonthCar":"res/js/order/order_month_car_list"}});
		require(["orderMonthCar"], function (orderMonthCar){
			orderMonthCar.init();
		});  
	   }); 
    </script>
