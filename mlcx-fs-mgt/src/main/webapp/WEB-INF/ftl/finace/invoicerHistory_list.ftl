<meta charset="utf-8">
<style>
.frombg span{
white-space: nowrap;
}
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.frombg .form-control{
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
white-space: nowrap;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
white-space: nowrap;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}
.even>td{
   white-space: nowrap;
}
.odd>td{
   white-space: nowrap;
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
   <div class="row">
    <div class="col-md-12 pd10">
        <h4 class="box-title">查询</h4>
               <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool more">
              更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>
          </button>
       </div><!-- /.box-tools -->
       <hr>
     <div class="box box-default">
         <form name="invoicerHistorySearchForm">
      <div class="with-border">

          <div class="row pull-down-menu col-md-11" style="margin-left: 1px; background: #f1f5f8;">
              <div class="col-md-3">
                  <div class="frombg">
                      <span>订单编号</span><input class="form-control" name="bizObjId" placeholder="">
                  </div>
              </div>
              <div class="col-md-3">
                  <div class="frombg">
                      <span>手机号</span><input class="form-control" name="phone" placeholder="">
                  </div>
              </div>
              <div class="col-md-3">
                  <div class="frombg">
                      <span>开票时间</span><input class="datepicker form-control" name="invoiceTimeStart1"  placeholder=""/>
                  </div>
              </div>
              <div class="col-md-3">
                  <div class="frombg">
                      <span>至</span><input class="datepicker form-control" name="invoiceTimeEnd1"  placeholder=""/>
                  </div>
              </div>
          </div>
          </div><!-- /.box-header -->
       <div class="box-body">
           <div class="row pull-down-menu col-md-11 other searching">

		  <div class="col-md-3">
		        <div class="frombg">
                      <span>开票抬头</span><input class="form-control" name="invoiceTitle" placeholder="">
                </div>
		  </div>
          <div class="col-md-3">
                <div class="frombg">
                      <span>对账周期</span><select class="form-control" name="invoiceTimeLead">
                                              <option value="">全部</option>
                                              <#list invoiceTimeList as lead>
                                              <option value="${lead[12..21]}_${lead[23..32]}">${lead}</option>
                                              </#list>
                                            </select>
                </div>
           </div>


		</div>
		<div class="row" style="margin-top: 25px;">
        <!-- <div class="col-md-3">对账周期<input class="datepicker form-control" name="invoicerHistoryTime"  placeholder=""/></div>-->
         </div><!-- /.row -->
        </div><!-- /.box-body -->

             <script>
                 $(".other").hide();
                 $(document).ready(function(){
                     var state = 0; //hide
                     $(".more").click(function(){
                         if (state == 0){
                             $(".other").show();

                             $(this).html('收起<img src="res/img/arrow-up.png" width="15" style="margin-left: 2px;"/>');
                             state = 1;
                         } else {
                             $(".other").hide();
                             $(this).html('更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>');
                             state = 0;
                         }
                     })
                 })
             </script>
 <div class="col-md-12" style='float:right'>
              <div class="box-footer">


                       <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="invoicerHistorySearch" style="background:#2b94fd;margin-right:-2px !important"> 确定</button>
                      <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"   style="background:#fa6e30;float:right;margin-right:20px !important"> 清空</button>
              </div><!-- /.box-footer -->
          </div>
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row" style="width: 100%; height: 15px; background: #f1f5f8"></div>
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="invoicerHistoryBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="invoicerHistoryList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <!-- 弹出框   审核操作-->
    <div class="modal fade" id="invoicerHistoryBrowseModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">纳税人认定通知书</h4>
                </div>
                <div class="modal-body" >
                       <form class="form-horizontal" name="memberCompanyInfoEditForm"> 
                             <img id="invoicerHistoryBrowseImg" style="width: 500px;height: 600px;"/>
                              <div class="modal-footer">
                       <input type="button" class="btn btn-default pull-right sure" id="invoicerHistoryBrowseModalClose"  value="关闭">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"invoicerHistory":"res/js/finace/invoiceHistory_list"}});
		require(["invoicerHistory"], function (invoicerHistory){
			invoicerHistory.init();
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
