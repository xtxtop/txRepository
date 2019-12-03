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
		<div class="pull-right moreButtonNew" id="moreDepositRefundSearch">
			<div class="up-triangle">
		</div>
		<div class="up-box">
			<span>更多</span>
			<i class="fa fa-angle-down click-name"></i>
		</div>
		</div>
		<div class="search-input-content">
         <form name="depositRefundSearchForm">
      <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
                  <div class="seach-input-item pull-left">
                      <span>客户手机</span><input class="form-control" name="mobilePhone" placeholder="请输入客户手机">
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>申请时间</span><input class="datepicker form-control" name="applyTime1"  placeholder="请选择申请时间"/>
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>至</span><input class="datepicker form-control" name="applyTime2"  placeholder="请选择结束时间"/>
                  </div>
                  <div class="seach-input-item pull-left">
                      <span>审核状态</span><select class="form-control" name="cencorStatus">
                      <option value="">全部</option>
                      <option value="1">已审核</option>
                      <option value="0"<#if cencorStatus!=null && cencorStatus==0>selected</#if> >未审核</option>
                      <option value="1">已通过</option>
                      <option value="2">待审核</option>
                      <option value="3">未通过</option>
                  </select>
                  </div>
			    <div class="seach-input-item pull-left">
                    <span>退款状态</span><select class="form-control" name="refundStatus">
                                              <option value="">全部</option>
                                              <option value="1">已退款</option>
                                              <option value="0">未退款</option>
                                              <option value="2">退款失败</option>
                                            </select>
                </div>
                    <div class="seach-input-item pull-left">
                        <span>退款流水号</span><input class="form-control" name="refundFlowNo" placeholder="请输入退款流水号">
                    </div>
                   <div class="seach-input-item pull-left">
                       <span>退款时间</span><input class="datepicker form-control" name="refundTime1"  placeholder="请选择退款时间"/>
                   </div>
                   <div class="seach-input-item pull-left">
                       <span>至</span><input class="datepicker form-control" name="refundTime2"  placeholder="请选择退款结束时间"/>
                   </div>
                   <div class="seach-input-item pull-left">
                       <span>退款方式</span>
                       <select class="form-control" name="refundMethod">
                           <option value="">全部</option>
                           <option value="1">支付宝</option>
                           <option value="2">微信</option>
                           <option value="4">线下退款</option>
                       </select>
                   </div>
          </div>
          			<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="depositRefundSearch">确定</button>
					</div>
      </div><!-- /.box-header -->
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="depositRefundBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="depositRefundList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <!-- 弹出框   审核操作-->
    <div class="modal fade" id="depositRefundBrowseModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">纳税人认定通知书</h4>
                </div>
                <div class="modal-body" >
                       <form class="form-horizontal" name="memberCompanyInfoEditForm"> 
                             <img id="depositRefundBrowseImg" style="width: 500px;height: 600px;"/>
                              <div class="modal-footer">
                       <input type="button" class="btn btn-default pull-right sure" id="depositRefundBrowseModalClose"  value="关闭">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     <!-- 弹出框   审核操作-->
    <div class="modal fade" id="depositRefundMemoModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">线下退款</h4>
                </div>
                <div class="modal-body" >
                       <form class="form-horizontal" name="depositRefundMemoForm">
                       <input type="hidden" name="refundNo" /> 
                       <div class="form-group">
							<label class="col-sm-3 control-label reason">*&nbsp;&nbsp;备注：</label>
							<div class="col-sm-8">
							    <textarea class="form-control" rows="6"  name="refundMemo" ></textarea>
							</div>
						</div>
                              <div class="modal-footer">
                              <button type="button" id="closeDepositRefundMemo" class="btn btn-default pull-right sure ">关闭</button>
							<button type="button" id="memoDepositRefund" class="btn btn-default pull-right sure " style="margin-right:20px">提交</button>  		
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"depositRefund":"res/js/finace/depositRefund_list"}});
		require(["depositRefund"], function (depositRefund){
			depositRefund.init();
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
