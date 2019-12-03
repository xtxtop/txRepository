<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 100%  !important;
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
		</div>
     <div class="search-input-content">
        <form class="form-horizontal" name="smsTemplateSearchForm"> 
       <div class="seach-input-border-box">
        <div class="seach-input-details smsTemplate close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>短信模板类型</span>
                    <select class="form-control" name="templateType">
                                                     <option value="">全部</option>
                                                     <option value="1">注册</option>
                                                     <option value="2">修改密码</option>
                                                     <option value="3">车辆信息异常</option>
                                                     <option value="4">会员审核</option>
                                                     <option value="5">运行日报</option>
                                                     <option value="6">退款提示</option>
                                                </select>
                </div>
        </div><!-- /.box-body -->
        <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="smsTemplateSearch">确定</button>
					</div>
       </div>
        </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="smsTemplateBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="smsTemplate" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
   <script type="text/javascript">
    $(function(){
	  require.config({paths: {"smsTemplate":"res/js/system/smsTemplate_list"}});
		require(["smsTemplate"], function (smsTemplate){
			smsTemplate.init();
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
