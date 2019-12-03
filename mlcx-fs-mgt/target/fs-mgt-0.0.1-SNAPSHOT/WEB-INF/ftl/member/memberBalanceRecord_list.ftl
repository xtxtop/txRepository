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
        <div class="box-body">
         <form class="form-horizontal" name="memberBalanceRecordSearchForm">
        <div class="box-body">
			<div class="row pull-down-menu">
			<div class="col-md-2">
                <div class="frombg">
                    <span>会员编号</span><input  class="form-control" name="memberNo" placeholder=""  value="${memberBalanceRecord.memberNo}">
                </div>
          	</div>
				<div class="col-md-2">
			      <div class="frombg">
	                    <span>类型</span>
	                    <select class="form-control" name="type">
							<option value="">全部</option>
	                       	<option value="1">充值</option>
	                       	<option value="2">赠送</option>
	                       	<option value="3">抵扣</option>
	                       	<option value="4">返还</option>
						</select>
	                </div>
				</div>
	          
				<div class="col-md-2">
                  	<div class="frombg">
                      <span>交易日期</span><input class="datepicker form-control" name="createTimeStart" placeholder=""/>
                  	</div>
	            </div>
	            <div class="col-md-2">
	                 <div class="frombg">
	                     <span>至</span> <input class="datepicker form-control" name="createTimeEnd" placeholder=""/>
	                 </div>
	           </div>
	           <!--修改-->
	          	<div class="col-md-2">
					<div class="box-footer">


	                    <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb" style="background:#fa6e30">清空</button>
	                      <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="memberBalanceRecordSearch" style="background:#2b94fd">确定</button>
	                </div>
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
       <script id="memberBalanceRecordTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
       </script>
       <div class="box">
        <div class="box-body">
         <table id="memberBalanceRecordList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <!--
    <script type="text/javascript" src="${basePath!'' }/res/js/member/main.js"></script>
    -->
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberBalanceRecord":"res/js/member/memberBalanceRecord_list"}});
		require(["memberBalanceRecord"], function (memberBalanceRecord){
			memberBalanceRecord.init();
		});  
	});    
	$(".datepicker").datepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: 'linked',//今日按钮
        format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
</script>
