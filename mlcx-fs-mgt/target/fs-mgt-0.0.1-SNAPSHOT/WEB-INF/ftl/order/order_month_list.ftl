<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
       <div class="box box-default">
        <div class="box-header with-border">
         <h3 class="box-title">查询</h3>
         <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="orderMonthSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu">
         <div class="col-md-2">
                <div class="frombg">
                    <span>订单编号</span><input type="text" class="form-control" id="orderNo" name="orderNo" placeholder="">
                </div>
          </div>
          <div class="col-md-2">
            <div class="frombg">
                <span>订单状态</span><select class="form-control" name="orderStatus">
                       <option value="">全部</option>
                       <option value="1">已提车</option>
                       <option value="2">已还车</option>
                       <option value="3">已结束</option>
                       <option value="4">已取消</option>
                      </select>
            </div>
          </div>
           <div class="col-md-2">
                    <div class="frombg">
                         <span>支付状态</span><select class="form-control" name="payStatus">
                           <option value="">全部</option>
                           <option value="0">未支付</option>
                           <option value="1">已支付</option>
                           <!--<option value="2">未结算</option>-->
                          </select>
                    </div>
          </div>
          
          
          
          </div><!-- /.row -->
           <div class="row pull-down-menu">
          
         
          <div class="col-md-3">
                  <div class="box-footer">
                     <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
                     <button type="button" class="btn btn-default pull-right btn-flat btncolora"  id="orderMonthSearchafter" style="background:#2b94fd;margin-right:-2px !important">确定</button>
                      <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb" style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
                   </div>
          </div>
          
         </div><!-- /.box-body -->

         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="orderMonthTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="orderMonthList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"orderMonth":"res/js/order/order_month_list"}});
		require(["orderMonth"], function (orderMonth){
			orderMonth.init();
		});  
	   }); 
    </script>
