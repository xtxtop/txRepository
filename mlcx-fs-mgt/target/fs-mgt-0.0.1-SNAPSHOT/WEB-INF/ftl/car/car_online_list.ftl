<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12">
       <div class="box box-default">
        <div class="box-header with-border">
         <h3 class="box-title">查询</h3>
         <hr>
         <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="carOnlineSearchForm">
         <input type="hidden" name="carNo" value="${carNo}"/>
        <div class="box-body">
         <div class="row">
         
          <div class="col-md-2">操作类型<select class="form-control" name="opType">
           <option value="">全部</option>
           <option value="0">下线</option>
           <option value="1">上线</option>         
          </select></div>
          
          
            <div class="col-md-2">上线理由
	    	  <select class="form-control" id="updownWhy" name="updownWhy">
		        	   <#list carUpWhy as updownWhy>
                            <option   <#if updownWhy.itemValue==updownWhy.itemValue>selected</#if> value="${updownWhy.itemValue}"> 
                            	${updownWhy.itemValue}
                            </option>
                        </#list>        
		             <option value="" selected = "selected">全部</option>
	          </select>
	      </div>
	      
	      <div class="col-md-2">下线理由
	    	  <select class="form-control" id="updownWhy" name="updownWhy">
		        	   <#list carDownWhy as updownWhy>
                            <option   <#if updownWhy.itemValue==updownWhy.itemValue>selected</#if> value="${updownWhy.itemValue}"> 
                            	${updownWhy.itemValue}
                            </option>
                        </#list>        
		             <option value="" selected = "selected">全部</option>
	          </select>
	      </div>
	      
            <div class="row pull-down-menu">
	          <div class="col-md-3">
	                <div class="frombg">
	                    <span>车牌号</span><input type="text" class="form-control" id="carPlateNo" name="carPlateNo" placeholder="">
	                </div>
	          </div>
           </div>
           
         <div class="row pull-down-menu">
	          <div class="col-md-3">
	                <div class="frombg">
	                    <span>操作人</span><input type="text" class="form-control" id="userName" name="userName" placeholder="">
	                </div>
	          </div>
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
           <!--修改-->
         <div class="box-footer">
	         <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
	         <button type="reset" class="btn btn-default pull-right btn-flat flatten" style="background:#fa6e30">清空</button>
	         <button type="button" class="btn btn-default pull-right btn-flat flatten" id="carOnlineSearchafter"  style="background:#2b94fd">确定</button>
         </div>
         
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="carOnlineTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-header">
         <!-- <h3 class="box-title">数据列</h3> -->
        </div><!-- /.box-header -->
        <div class="box-body">
         <table id="carOneLineList" class="table table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>

    </div><!-- /.container-fluid -->
    
    
         
  
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"carOneLine":"res/js/car/car_online_list"}});
		require(["carOneLine"], function (carOneLine){
			carOneLine.init();
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
