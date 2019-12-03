<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.seach-input-item pull-left .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}
.other{
margin:0 !important;
padding:0 !important;
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
  <div class="container-fluid">
   <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
		<div class="search-input-content">
       <form name="memberFeedbackSearchForm">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
            <div class="seach-input-item pull-left">
                <span>客户</span>
                <input class="form-control" name="memberName" placeholder="请输入客户名称">
            </div>
            <div class="seach-input-item pull-left">
                <span>手机号</span>
                <input class="form-control" name="mobilePhone" placeholder="请输入手机号">
            </div>
            <div class="seach-input-item pull-left">
                <span>提交时间</span>
                <input class="datepicker form-control" name="createTimeStart"  placeholder="请选择提交时间"/>
            </div>
            <div class="seach-input-item pull-left">
                <span>至</span>
                <input class="datepicker form-control" name="createTimeEnd"  placeholder="请选择结束时间"/>
            </div>
    </div><!-- /.row -->
           <div class="seach-input-border-box">
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="memberFeedbackSearch">确定</button>
					</div>
        </div><!-- /.box-body -->
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="memberFeedbackBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="memberFeedbackList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"memberFeedback":"res/js/customer/memberFeedback_list"}});
		require(["memberFeedback"], function (memberFeedback){
			memberFeedback.init();
		});  
	});
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
