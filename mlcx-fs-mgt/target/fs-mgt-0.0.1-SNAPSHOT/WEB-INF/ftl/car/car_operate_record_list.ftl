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
.seach-input-item pull-left .form-control{
	margin-right:10%；
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
		</div>
		<div class="search-input-content">
         <form name="carOperateRecordSearchForm">
      <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details car-status close-content clearfix">
                  <div class="seach-input-item pull-left">
                      <span>车牌号</span><input class="form-control" name="carPlateNo" value="${carPlateNo}">
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>发送时间起</span><input class="datetimepicker form-control" name="createTimeStart" style="background:#FFFFFF" readonly/>
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>至</span><input class="datetimepicker form-control" name="createTimeEnd" style="background:#FFFFFF" readonly/>
                  </div>
              <div class="seach-input-item pull-left">
                 <span>指令类型</span><select class="form-control" name="operateType">
					<option value="">全部</option>
					<option value="1">关门</option>
					<option value="2">开门</option>
					<option value="3">关闭动力</option>
					<option value="4">开启动力</option>
					<option value="5">寻车</option>
                  </select>
             </div>
              
          </div>
             <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="carOperateRecordSearch">确定</button>
					</div>
      </div><!-- /.box-header -->
       
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="carOperateRecordBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="carOperateRecordList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"carOperateRecord":"res/js/car/car_operate_record_list"}});
		require(["carOperateRecord"], function (carOperateRecord){
			carOperateRecord.init();
		});  
	});
     $(function () {
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            minView: "month",
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
     
</script>
