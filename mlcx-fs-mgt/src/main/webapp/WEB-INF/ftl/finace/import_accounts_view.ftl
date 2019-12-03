<meta charset="utf-8">
<div class="container-fluid" id="scollOo">
   <div class="row">
		<div class="col-md-12">
			<div class="form-group compiletitle">
				<label class="col-sm-2 control-label"><h4>手动导入账单</h4></label>
			</div>
		</div>
	</div>
     <form name="carAddForm">
        	<div class="col-md-8 form-horizontal">
                <div class="form-group">
					<label class="col-sm-3 control-label">*&nbsp;&nbsp;账单类型：</label>
					<div class="col-sm-4">
						<label class="radio-inline">
								<input type="radio" name="accountsType" id="accountsType" value="1" checked="checked">支付宝
                         </label>
                         <label class="radio-inline">
								<input type="radio" name="accountsType" id="accountsType" value="2">微信
                         </label>   
					</div>
				</div>
                <div class="form-group">
					<label class="col-sm-3 control-label">*&nbsp;&nbsp;选择日期：</label>
					<div class="col-sm-4">
						 <input class="datepicker form-control" name="time"/>
					</div>
					<div style="margin-top:7px;"><span name="timeError"></span></div>
				</div>
        </div><!-- /.row -->
    </form>
    <div class="form-group">
        <div class="col-sm-7" style="margin-bottom:20px;">
            <button type="button" id="closeImportAccount" class="btn btn-default pull-right sure btncolorb" >
                <i class="glyphicon glyphicon-remove"></i>关闭
            </button>
            <button type="button" id="importAccount" class="btn btn-default pull-right sure btncolora" >
                <i class="glyphicon glyphicon-check"></i>导入
            </button>
        </div>
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="importAccountModal" data-backdrop="static">   
  <div class="modal-dialog" >
      <div class="modal-content">
         <div class="modal-header">
            	正在导入请稍后......
         </div>
      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<script type="text/javascript">
$(function(){
	  require.config({paths: {"importAccounts":"res/js/finace/import_accounts"}});
		require(["importAccounts"], function (carAdd){
			carAdd.init();
		});  
		
        $(".datepicker").datetimepicker({
            language: "zh-CN",
            minView: "month",
            autoclose: true,
            clearBtn: true,//清除按钮
            todayBtn: true,
            minuteStep: 5,
            startDate: moment(new Date()).format("YYYY-MM"),
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
});
</script>
<!--设置滚动条滚动时，时间框隐藏-->
<script type="text/javascript" src="res/js/common/dateScroll.js"></script> 



