<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12">
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
         <form class="form-horizontal" name="orderPowerLogSearchForm">
         <input type="hidden" name="orderNo" value="${orderNo}"/>
        <div class="box-body">
         <div class="row">
         
          <div class="col-md-2">操作类型<select class="form-control" name="cantrolType">
           <option value="">全部</option>
           <option value="0">关闭</option>
           <option value="1">开启</option>         
          </select>
          </div>
          
          
          
           
        
         
         <div class="row pull-down-menu">
          <div class="col-md-3">
                <div class="frombg">
                    <span>操作开始时间</span><input class="datepicker form-control" name="createTimeStart"  placeholder=""/>
                </div>
		  </div>
		  <div class="col-md-3">
		       <div class="frombg">
                   <span>操作结束时间</span><input class="datepicker form-control" name="createTimeEnd"  placeholder=""/>
               </div>
		  </div>
		  </div>
         </div><!-- /.box-body -->
         
         </form>
           <div class="box-footer col-md-12" style='float:left'>
               <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
               <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="orderSearchafter" style="background:#2b94fd;margin-right:-2px !important">确定</button>
               <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"  style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
           </div>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
       <div class="row" style="width: 100%; height: 25px; background: #f1f5f8"></div>
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="orderPowerLogTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-header">
         <!-- <h3 class="box-title">数据列</h3> -->
        </div><!-- /.box-header -->
        <div class="box-body">
         <table id="orderPowerLogList" class="table table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>

    </div><!-- /.container-fluid -->
    
    
         
  
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"orderPowerLog":"res/js/order/order_powerLog_list"}});
		require(["orderPowerLog"], function (orderPowerLog){
			orderPowerLog.init();
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
