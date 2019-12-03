<meta charset="utf-8">

   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
       <div class="box box-default">
        <div class="box-header with-border">
         <h3 class="box-title">查询</h3>
         <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
         </div>
         <hr>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
        <form class="form-horizontal" name="sysOpLogUserSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu">
          <div class="col-md-2">
                <div class="frombg">
                    <span>操作模快</span><input type="text" class="form-control" name="moduleName" placeholder="">
                </div>
          </div>
          <div class="col-md-3">
                <div class="frombg">
                    <span>操作时间</span> <div class="input-group">
                                                    <div class="input-group-addon">
                                                     <i class="fa fa-calendar"></i>
                                                    </div>
                                                 <input type="text" class="form-control pull-right" id="sysOpLogDate" name="createTime">
                                                 </div>
                </div>

          </div>              
          </div>
         </div><!-- /.box-body -->                
	      <div class="box-footer">
	         <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
	         <button type="reset" class="btn btn-default pull-right btn-flat">重置</button>
	         <button type="button" class="btn btn-default pull-right btn-flat" id="sysOpLogSearchafter">确认</button>
	     </div>
	     
	      </form>
	      <!-- /.box-footer -->
        </div><!-- / box-->
       </div><!-- /.col -->
      </div><!-- /.row -->
      <div class="row">
	      <div class="col-xs-12">
	       <!--定义操作列按钮模板-->
	       <script id="sysOpLogTpl" type="text/x-handlebars-template">
           {{#each func}}
           <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
           {{/each}}
       	   </script>
	       <div class="box">
	        <div class="box-header">
	         <!-- <h3 class="box-title">数据列</h3> -->
	        </div><!-- /.box-header -->
	        <div class="box-body">
	         <table id="sysOpLogUserList" class="table table-bordered table-striped table-hover" width="100%">
	         </table>
	        </div><!-- /.box-body -->
	       </div><!-- /.box -->
	      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
   <!-- 弹出框-->
    <div class="modal fade" id="mySysOpLogModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">用户管理</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="sysOpLogForm">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">参数名称：</label>
                            <div class="col-sm-8">
                                <input type="hidden" name="userId" id="formUserId">
                                <input type="text" class="form-control" id="formParamName" name="paramName" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">参数键：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="formParamKey" name="paramKey" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">参数值：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="formParamValue" name="paramValue" placeholder="">
                            </div>
                        </div>
                         <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">参数描述：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="formMemo" name="memo" placeholder="">
                            </div>
                        </div>                            
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label">是否可配置：</label>
                            <div class="col-sm-8">
                                <label class="radio-inline">
                                    <input type="radio" name="isConfigurable" id="formIsConfigurable1" value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="isConfigurable" id="formIsConfigurable2" value="0"> 否
                                </label>                                    
                            </div>
                        </div>    
                           <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-right"  id="sysOpLogResetBtn">清空</button>
                    <input type="button" class="btn btn-default pull-right" id="sysOpLogEditBtn" value="保存" >
                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
