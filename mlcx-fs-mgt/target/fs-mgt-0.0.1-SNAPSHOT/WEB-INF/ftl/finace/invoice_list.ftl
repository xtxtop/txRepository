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
		<div class="pull-right moreButtonNew" id="moreInvoiceSearch">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
         <form name="invoiceSearchForm">
				<!--修改 -->
				<div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
                  <div class="seach-input-item pull-left">
                      <span>订单编号</span>
                      <input class="form-control" name="bizObjId" placeholder="请输入订单编号">
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>会员手机号</span>
                      <input class="form-control" name="phone" placeholder="请输入会员手机号">
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>开票时间</span>
                      <input class="datepicker form-control" name="invoiceTimeStart1"  placeholder="请选择开始时间"/>
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>至</span>
                      <input class="datepicker form-control" name="invoiceTimeEnd1"  placeholder="请选择结束时间"/>
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>开票状态</span>
                      <select class="form-control" name="invoiceStatus">
                      <option value="">全部</option>
                      <option value="0" <#if cencorStatus!=null && cencorStatus==0>selected</#if> >未开</option>
                      <option value="1">已开</option>
                  </select>
                  </div>
                    <div class="seach-input-item pull-left">
                        <span>对账周期</span><select class="form-control" name="invoiceTimeLead">
                                                  <option value="">全部</option>
                                                  <#list invoiceTimeList as lead>
                                                  <option value="${lead[12..21]}_${lead[23..32]}">${lead}</option>
                                                  </#list>
                                                </select>
                    </div>
              
          </div><!-- /.box-header -->
          			<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="invoiceSearch">确定</button>
					</div>
        </div><!-- /.box-body -->
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="invoiceBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="invoiceList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <!-- 弹出框   审核操作-->
    <div class="modal fade" id="invoiceBrowseModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">纳税人认定通知书</h4>
                </div>
                <div class="modal-body" >
                       <form class="form-horizontal" name="memberCompanyInfoEditForm"> 
                             <img id="invoiceBrowseImg" style="width: 500px;height: 600px;"/>
                              <div class="modal-footer">
                       <input type="button" class="btn btn-default pull-right sure" id="invoiceBrowseModalClose"  value="关闭">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"invoice":"res/js/finace/invoice_list"}});
		require(["invoice"], function (invoice){
			invoice.init();
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
