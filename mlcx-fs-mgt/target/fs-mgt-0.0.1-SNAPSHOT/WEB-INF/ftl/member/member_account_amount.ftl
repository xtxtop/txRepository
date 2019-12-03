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
	width:35% !important;
	margin-right:10%;
}
.pull-down-menu span {
    width: 35%;
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
        <!-- /.box-header -->
         <form class="form-horizontal" name="memberAccountAmountSerachForm">
        <div class="seach-input-border-box">
        <input  class="form-control" name="amountOrderBy" value="desc" type="hidden">
         <div class="seach-input-details seach-input-details-change close-content clearfix">
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>姓名</span><input  class="form-control" name="memberName" placeholder="请输入姓名">
                </div>
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>手机</span><input  class="form-control" name="mobilePhone" placeholder="请输入手机号码">
                </div>

          </div><!-- /.box-body -->
        	     	<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="memberAccountAmountSearch">确定</button>
					</div>
         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="memberAccountAmountBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="memberAccountAmountList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberAccountAmount":"res/js/member/member_account_amount"}});
		require(["memberAccountAmount"], function (memberAccountAmount){
			memberAccountAmount.init();
		});  
	});    
</script>
