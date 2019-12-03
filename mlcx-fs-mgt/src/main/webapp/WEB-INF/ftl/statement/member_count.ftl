<meta charset="utf-8">

  <div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
		<div class="search-input-content">
       <form name="memberCountSearchForm">
      	<div class="seach-input-border-box">
        <div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>对账周期</span><input class="datepicker form-control" name="startTime"  value="${startTime}" placeholder=""/>
                </div>
		        <div class="seach-input-item pull-left">
                    <span>至</span><input class="datepicker form-control" name="endTime" value="${endTime}" placeholder=""/>
                </div>

		</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="memberCountSearch">确定</button>
					</div>
        </div><!-- /.box-body -->
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <div class="box">
        <div class="box-body">
         <table id="memberCount" class="table table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"memberCount":"res/js/statement/member_count"}});
		require(["memberCount"], function (memberCount){
			memberCount.init();
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
