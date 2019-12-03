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
         <form class="form-horizontal" name="pricingRuleDayCustomizedDateSearchForm">
         <input type="hidden" value="${ruleNo}" name="ruleNo">
        <div class="box-body">
         <div class="row pull-down-menu">
         
          <div class="col-md-3">
                 <div class="frombg">
                     <span>自定义时间</span><input class="datepicker form-control" name="availableTime1Start" placeholder=""/>
                 </div>
           </div>
           <div class="col-md-3">
                 <div class="frombg">
                     <span>至</span><input class="datepicker form-control" name="availableTime2Start" placeholder=""/>
                 </div>
          </div>

          <!--修改-->
          <div class="col-md-3">
                   <div class="box-footer">


                        <button type="reset" class="btn btn-default pull-right btn-flat btncolorb" style="background:#fa6e30" style="background:#2b94fd">
                                                   清空
                         </button>
                          <button type="button" class="btn btn-default pull-right btn-flat btncolora" id="pricingRuleDayCustomizedDateSearchafter">
                                                      确定
                           </button>
                   </div>
           </div>
         </div>

         </div><!-- /.box-body -->
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="pricingRuleDayCustomizedDateTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="pricingRuleDayCustomizedDateList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    
         

     
    
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"pricingRuleDayCustomizedDate":"res/js/marketing/pricingRuleDayCustomizedDate_list"}});
		require(["pricingRuleDayCustomizedDate"], function (pricingRuleDayCustomizedDate){
			pricingRuleDayCustomizedDate.init();
		});
		$("input[name='customizedDateStr']").datepicker({
            language: "zh-CN",
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            multidate:true,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        }); 
		$(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });    
	   }); 
    </script>
