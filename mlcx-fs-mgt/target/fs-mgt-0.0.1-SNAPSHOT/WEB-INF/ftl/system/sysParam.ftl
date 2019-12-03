<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%  !important;
}
.seach-input-item pull-left .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (min-width:992px) and (max-width:1190px) {
.row .seach-input-item pull-left{
	font-size: 0rem !important;
}
.seach-input-item pull-left .form-control{
	width:120px !important;
}
}
#sysParamTools{
margin-right:8px;
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
 <div class="container-fluid">
      <div class="search-input-wrapper">
      <div class="clearfix search-input-header">
			<span class="pull-left">查询</span>
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
       <div class="search-input-content">
        <form class="form-horizontal" name="sysParamSearchForm">
        <div class="seach-input-border-box">
        	<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="sysParamSearchafter">确定</button>
					</div>
         <div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>参数名称</span><input type="text" class="form-control" name="paramName" placeholder="">
                </div>
                <div class="seach-input-item pull-left">
                    <span>参数键</span><input type="text" class="form-control" name="paramKey" placeholder="">
                </div>
                <div class="seach-input-item pull-left">
                    <span>是否可配置</span><select class="form-control" name="isConfigurable">
                                               <option value="">全部</option>
                                               <option value="1">是</option>
                                               <option value="0">否</option>
                                            </select>
                </div>

          </div><!-- /.row -->
         </div><!-- /.box-body -->
     
          </form>
         <!-- /.box-footer -->
        </div><!-- /.box -->        
       </div><!-- /.col -->





      <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="sysParamTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="sysParamList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->

    <!-- 弹出框-->
    <style>
        .key {
            font-size: 1.5rem;
            font-weight: 500;
            color: #414550;
            line-height: 15px;
        }

        .val {
            font-size: 1.5rem;
            font-weight: 500;
            color: #a0a7af;
            line-height: 15px;
        }
        
    </style>
    <div class="modal fade" id="mySysParamModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加/编辑系统参数</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="sysParamForm">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数名称：</label>
                            <div class="col-sm-8">
                                <input type="hidden" name="paramId" id="formParamId">
                                <input type="text" maxlength="30" class="form-control val" id="formParamName" name="paramName" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数键：</label>
                            <div class="col-sm-8">
                                <input type="text" maxlength="30" class="form-control val" id="formParamKey" name="paramKey" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数值：</label>
                            <div class="col-sm-8">
                                <input type="text" maxlength="500" class="form-control val" id="formParamValue" name="paramValue" placeholder="">
                            </div>
                        </div>
                         <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数描述：</label>
                            <div class="col-sm-8">
                                <input type="text" maxlength="100" class="form-control val" id="sysParamformMemo" name="memo" placeholder="">
                            </div>
                        </div>                            
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否可配置：</label>
                            <div class="col-sm-8">
                                <label class="radio-inline val">
                                    <input type="radio" name="isConfigurable" id="formIsConfigurable1" value="1" checked="checked"> 是
                                </label>
                                <label class="radio-inline val">
                                    <input type="radio" name="isConfigurable" id="formIsConfigurable2" value="0"> 否
                                </label>                                    
                            </div>
                        </div>    
                <div class="modal-footer">
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" class="btn btn-default pull-right btncolorb" id="sysParamResetBtn">重置</button>
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" class="btn btn-default pull-right btncolora" id="sysParamEditBtn">保存</button>
                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
