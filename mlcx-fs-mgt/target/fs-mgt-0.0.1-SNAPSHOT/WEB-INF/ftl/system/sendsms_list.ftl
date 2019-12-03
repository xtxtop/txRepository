<meta charset="utf-8">
<style>
.frombg span{
white-space: nowrap;
}
.frombg .form-control{
	width:50% !important;
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
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />
  <div class="container-fluid">
   <div class="row">
    <div class="col-md-12 pd10">
        <h4 class="box-title">查询</h4>
        <div class="box-tools pull-right">
          <#--<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>-->
       </div>
       <hr>
     <div class="box box-default">
      <div class="with-border">
       <!-- /.box-tools -->
       </div><!-- /.box-header -->
       <form class="form-horizontal" name="sendsmsSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
         	<div class="col-md-6">
         	        <div class="frombg">
                        <span>手机号码</span><input type="text" class="form-control" name="mobilePhone" placeholder="">
                    </div>
         	</div>
          	<div class="col-md-6">
          	        <div class="frombg">
                        <span>消息类型</span><select class="form-control" name="templateType">
                                                  <option value="">全部</option>
                                                  <option value="1">注册</option>
                                                  <option value="2">修改密码</option>
                                                </select>
                    </div>
          	 </div>

         </div>
         </div><!-- /.box-body -->
         <!--修改-->
                 <div class="col-md-12" style='float:right !important;'>
                      <div class="box-footer">
                           <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->

                           <button type="button" class="btn btn-default pull-right btn-flat flatten flatten btnColorA" id="sendsmsSearch" style="background:#2b94fd;margin-right:-2px !important">确定</button>
                           <button type="reset" class="btn btn-default pull-right btn-flat flatten flatten btnDefault"  style="background:#fa6e30;float:right;ma rgin-right:20px !important">清空</button>
                       </div>
               </div>
	    </form>
        <!-- /.box-footer -->
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="sendsmsBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
           <div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
       <div class="box">
        <div class="box-body">
         <table id="sendsms" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->

    <!-- 弹出框-->
    <div class="modal fade" id="sendsmsModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">商城短信信息</h4>
                </div>
                <div class="modal-body">
                       <form class="form-horizontal" name="sendsmsForm"> 
                     <input type="hidden" id="customersmsId" name="smsId">
                     <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">接收人id：</label>
                            <div class="col-sm-8">
                                <input class="form-control" rows="6"  name="receiverId"></textarea>
                            </div>
                        </div> 
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">手机号：</label>
                            <div class="col-sm-8">
                                <input class="form-control" rows="6"  name="mobilePhone"></textarea>
                            </div>
                        </div> 
                    		 <div class="form-group">
                    		  <label for="inputEmail3" class="col-sm-3 control-label">短信模板类型：</label>
	                            <div class="col-sm-8">
	                                <label class="radio-inline">
	                                    <input type="radio" name="emplateType" value="0" id="emplateTyperadio" checked > 非默认模板
	                                </label>
	                                <label class="radio-inline">
	                                    <input type="radio" name="emplateType" value="1" id="emplateTypeRadio"> 默认模板
	                                </label>
	                            </div>
	                         </div>
                           <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">短信模板类型：</label>
                            <div class="col-sm-8">
                                <select class="form-control" id="customeremplateType" name="emplateType">
                                  <option value="">选择模板</option>
                                  <option value="0">系统消息</option>
                                  <option value="1">注册</option>
                                  <option value="2">修改密码</option>
                                  <option value="3">订单通知</option>
								</select>  
                            </div>
                      	  </div>
                      	  <div class="form-group" id="templateContent">
                            <label for="inputEmail3" class="col-sm-3 control-label">短信模板内容：</label>
                            <div class="col-sm-8" >
                               <select multiple class="form-control" id="emplateId" name="emplateId">
                                    <option value="">选择模板内容</option>
                                </select> 
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">短信内容：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" rows="6" id="smsContent" name="smsContent"></textarea>
                            </div>
                        </div>  
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">清空</button>
                       <input type="button" class="btn btn-default pull-right" id="sendsmsEditBtn" value="保存">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  <script type="text/javascript">
    $(function(){
	  require.config({paths: {"sendsms":"res/js/system/sendsms_list"}});
		require(["sendsms"], function (sendsms){
			sendsms.init();
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
