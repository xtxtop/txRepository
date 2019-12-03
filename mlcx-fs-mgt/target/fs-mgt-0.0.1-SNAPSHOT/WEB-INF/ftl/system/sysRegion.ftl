<meta charset="utf-8">

<style>
.seach-input-item pull-left .form-control{
	width:50% !important;
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
       <div class="search-input-content">
        <form class="form-horizontal" name="sysRegionSearchForm">
        <div class="seach-input-border-box">
         <div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>地区名称</span>
                    <input type="text" class="form-control" name="regionName" placeholder="请输入地区名称">
                </div>
                <div class="seach-input-item pull-left">
                    <span>邮政编码</span>
                    <input type="text" class="form-control" name="postCode" placeholder="请输入邮政编码">
                </div>
                  <div class="seach-input-item pull-left">
                      <span>是否可用</span>
                      <select class="form-control" name="isAvailable">
                                              <option value="">全部</option>
                                              <option value="1">可用</option>
                                              <option value="0">不可用</option>
                                             </select>
                  </div>

        </div> 
        <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="sysRegionSearchafter">确定</button>
					</div>
        </div>
        <!-- /.box-body -->
         </form>
        <!-- /.box-footer -->
        <!-- /.box-body -->
       </div>
       <!-- /.box -->
       <!-- </div> -->
      </div>
     </div>
      <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="sysRegionTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="sysRegionList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->

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
        <div class="modal fade" id="mySysRegionModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">添加/编辑地区管理</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" name="sysRegionForm">
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-3 control-label key">地区名称：</label>
                                <div class="col-sm-8">
                                    <input type="hidden" name="regionId" id="formRegionId">
                                    <input type="text" maxlength="40" class="form-control val" id="formRegionName" name="regionName" placeholder="请输入地区名称">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-3 control-label key">邮政编码：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control val" id="formPostCode" name="postCode" placeholder="请输入邮政编码">
                                </div>
                            </div>  
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-3 control-label key">级别：</label>
                                <div class="col-sm-8">
                                    <input type="number" class="form-control val" id="formLevel" name="level" placeholder="请输入级别编号">
                                </div>
                            </div>                           
                             <div class="form-group">
                                <label for="formParentName" class="col-sm-3 control-label key">父级地区：</label>
                                 <div class="col-sm-8" id="sysRegionTreeSel" style="">
                                   
                                </div>
                                <input type="hidden" id="formRegionParentId" name="parentId">
                            
                            </div>
                            <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否可用：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isAvailable" id="sysRegionformIsAvailable1" value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isAvailable" id="sysRegionformIsAvailable2" value="0"> 否
                                </label>                                    
                            </div>
                        </div> 
                    <div class="modal-footer">
                        <button type="button" style="width: 95px; height: 32px;margin-right: 50px !important;color: #3f9fff;background: #fff;border-color: #3f9fff;" class="btn btn-default pull-right"  id="sysRegionResetBtn">重置</button>
                        <button type="button" style="width: 95px; height: 32px; background:#2b94fd;" class="btn btn-default pull-right btncolora"  id="sysRegionEditBtn">保存</button>
                    </div>              
                        </form>
                    </div>
                   
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
       
      </div>
     </div>
    </div>

    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
