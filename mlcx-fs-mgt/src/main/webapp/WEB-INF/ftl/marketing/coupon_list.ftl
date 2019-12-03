<meta charset="utf-8">
<style>
.seach-input-item pull-left .form-control{
	width:50% !important;
}
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
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
   <div class="container-fluid">
     <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="moreCouponList">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
           <form class="form-horizontal" name="couponSearchForm">
       <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
                    <div class="seach-input-item pull-left">
                        <span>优惠券号</span>
                        <input type="text" class="form-control" name="couponNo" placeholder="请输入优惠券号">
                    </div>
                    <div class="seach-input-item pull-left">
                        <span>方案标题</span>
                        <input type="text" class="form-control" name="title" placeholder="请输入方案标题">
                    </div>
                    <div class="seach-input-item pull-left">
                        <span>会员名称</span>
                        <input type="text" class="form-control" name="memberName" placeholder="请输入会员名称">
                    </div>
                    <div class="seach-input-item pull-left">
                        <span>手机</span>
                        <input type="text" class="form-control" name="mobilePhone" placeholder="请输入手机号码">
                    </div>
                         <div class="seach-input-item pull-left">
                   <span>发放方式</span>
                   <select class="form-control" name="issueMethod">
                       <option value="">全部</option>
                       <option value="1">系统注册</option>
                       <option value="2">活动奖励</option>
                       <option value="3">手动发放</option>
                   </select>
               </div>
                <div class="seach-input-item pull-left">
                    <span>启用状态</span>
                    <select class="form-control" name="isAvailable">
	                     <option value="">全部</option>
	                     <option value="0">停用</option>
	                     <option value="1">启用</option>
                    </select>
                </div>
                <div class="seach-input-item pull-left">
                    <span>使用状态</span>
                    <select class="form-control" name="usedStatus">
                    	<option value="">全部</option>
                        <option  value="0" >未使用</option>
                        <option  value="1" >已使用</option>
                   </select>
                </div>
                  <div class="seach-input-item pull-left">
                      <span>发放日期</span>
                      <input class="datepicker form-control" name="issueTimeStart" placeholder="请选择发放日期"/>
                  </div>
                 <div class="seach-input-item pull-left">
                     <span>至</span>
                     <input class="datepicker form-control" name="issueTimeEnd" placeholder="请选择结束日期"/>
                 </div>
            </div>
            		<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="couponSearchafter">确定</button>
					</div>
        </div><!-- /.box-body -->
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="couponTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="couponList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"coupon":"res/js/marketing/coupon_list"}});
		require(["coupon"], function (coupon){
			coupon.init();
		});
		$(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
	   });
    </script>










		.btn-new-type-change-float-right{
float:right;
}
