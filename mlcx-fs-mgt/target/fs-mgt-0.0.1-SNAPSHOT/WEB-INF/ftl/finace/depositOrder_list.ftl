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
		</div>
		<div class="pull-right moreButtonNew" id="moreDepositOrderSearch">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
         <form name="depositOrderSearchForm">
      <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
                  <div class="seach-input-item pull-left">
                      <span>会员名称</span>
                      <input class="datepicker form-control" name="memberName"  placeholder="请输入会员名称"/>
                   </div>
                  <div class="seach-input-item pull-left">
                      <span>客户手机</span>
                      <input class="form-control" name="mobilePhone"  value ="${mobilePhone}" placeholder="请输入客户手机">
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>缴纳方式</span>
                      <select class="form-control" name="paymentMethod">
                      <option value="">全部</option>
                      <option value="1">支付宝</option>
                      <option value="2">微信</option>
                      <option value="3">微信公众号</option>
                  </select>
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>退款状态</span><select class="form-control" name="refundStatus">
                      <option value="">全部</option>
                      <option value="0">未退款</option>
                      <option value="1">已退款</option>
                      <option value="2">退款失败</option>
                  </select>
                  </div>
                  <div class="seach-input-item pull-left">
                        <span>退款方式</span>
                        <select class="form-control" name="refundMethod">
                          <option value="">全部</option>
                      <option value="1">支付宝</option>
                    <option value="2">微信</option>
                      <option value="3">微信公众号</option>
                      <option value="4">线下退款</option>
                  </select>
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>支付流水号</span>
                      <input class="datepicker form-control" name="partTradeFlowNo"  placeholder="请输入支付流水号"/>
                   </div>
         
      </div><!-- /.box-header -->
      				<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="depositOrderSearch">确定</button>
					</div>
        </div><!-- /.box-body -->
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="depositOrderBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="depositOrderList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"depositOrder":"res/js/finace/depositOrder_list"}});
		require(["depositOrder"], function (depositOrder){
			depositOrder.init();
		});  
	});
</script>
