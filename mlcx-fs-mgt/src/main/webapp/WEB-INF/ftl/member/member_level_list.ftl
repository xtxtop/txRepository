<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%  !important;
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
         <form class="form-horizontal" name="memberLevelSerachForm">
        <div class="seach-input-border-box">
         <div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>等级名称</span>
                    <input class="form-control" name="levelName" placeholder="请输入等级名称">
                </div>
                <div class="seach-input-item pull-left">
                    <span>是否可用</span>
                    <select class="form-control" name="isAvailable">
                                     	                                  <option value="">全部</option>
                                     	                                  <option value="1">可用</option>
                                     	                                  <option value="0">不可用</option>
                                     									</select>
                </div>
			    <div class="seach-input-item pull-left">
                    <span>创建时间</span>
                    <input class="datetimepicker form-control" name="createTimeStart" readonly placeholder="请选择创建时间"/>
                </div>
                    <div class="seach-input-item pull-left">
                        <span>至</span>
                        <input class="datetimepicker form-control" name="createTimeEnd" style="background:#FFFFFF" readonly placeholder="请选择结束时间"/>
                    </div>
          </div><!-- /.box-body -->
        	<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="memberLevelSearch">确定</button>
					</div>
         </div>
         <!--修改-->
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="memberLevelBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="memberLevelList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberLevel":"res/js/member/member_level_list"}});
		require(["memberLevel"], function (memberLevel){
			memberLevel.init();
		});  
	});    
   $(function () {
        $(".datetimepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
