<meta charset="utf-8">
<style>
.seach-input-item pull-left .form-control{
	width:50% !important;
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
        <!-- /.box-header -->
         <form class="form-horizontal" name="sysPermissionSearchForm">
        <div class="seach-input-border-box">
         <div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>权限名称</span><input type="text" class="form-control" id="permName" name="permName" placeholder="请输入权限名称">
                </div>
                <div class="seach-input-item pull-left">
                    <span>模块名称</span> <input type="text" class="form-control" id="moduleName" name="moduleName" placeholder="请输入模块名称">
                </div>
                <div class="seach-input-item pull-left">
                    <span>是否可用</span>
                    <select class="form-control" name="isAvailable">
                                            <option value="">全部</option>
                                            <option value="1">可用</option>
                                            <option value="0">不可用</option>
                                           </select>
                </div>
         </div><!-- /.box-body -->
         <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="sysPermissionSearchafter">确定</button>
					</div>
         </div>

         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="sysPermissionTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="sysPermissionList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
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
    <div class="modal fade" id="mySysPermissionModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加/编辑权限管理</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="sysPermissionForm">
                        <div class="form-group">
                            <label for="formModuleName" class="col-sm-3 control-label key">模快名称：</label>
                            <div class="col-sm-8">
                                <input type="hidden" name="permId" id="permId">
                                <input type="text"  placeholder="请输入模块名称" class="form-control val" id="formModuleName" name="moduleName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="formPermName" class="col-sm-3 control-label key">权限名称：</label>
                            <div class="col-sm-8">
                                <input type="text" placeholder="请输入权限名称" class="form-control val" id="formPermName" name="permName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">权限类型：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" name="permType" id="formPermType1" value="0"> 访问路径url
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="permType" id="formPermType2" value="1"> 页面标签
                                </label>                                    
                            </div>
                        </div>    
                        <div class="form-group">
                            <label for="formPermResource" class="col-sm-3 control-label key">权限资源：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control val" id="formPermResource" name="permResource" placeholder="请输入权限资源">
                            </div>
                        </div>                             
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否是菜单：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" name="isMenu" id="sysPermissionformIsMenu1" value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="isMenu" id="sysPermissionformIsMenu2" value="0"> 否
                                </label>                                    
                            </div>
                        </div>
                        <!-- 是否是菜单-->
                        <div id="sysPermissionDivShow">
                            <div class="form-group">
                                <label for="inputPassword3" class="col-sm-3 control-label key">是否显示：</label>
                                <div class="col-sm-8">
                                    <label class="radio-inline">
                                        <input type="radio" name="isMenuShow" id="sysPermissionformIsMenuShow1" value="1"> 是
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="isMenuShow" id="sysPermissionformIsMenuShow2" value="0"> 否
                                    </label>                                    
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="formMenuName" class="col-sm-3 control-label key">菜单名称：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control val" id="formMenuName" name="menuName" placeholder="请填写菜单名称">
                                </div>
                            </div>
                         </div>
                         <div class="form-group">
                            <label for="formLevel" class="col-sm-3 control-label key">层级：</label>
                            <div class="col-sm-8">
                                <input type="number" min="1" max="1000" class="form-control val" id="formLevel" name="level" placeholder="请输入层级编号">
                            </div>
                        </div>
                         <div class="form-group">
                            <label for="formParentName" class="col-sm-3 control-label key">父节点：</label>
                             <div class="col-sm-8" id="sysPermissionTreeSel" style="">
                               
                            </div>
                            <input type="hidden" id="formParentId" name="parentId">
                            
                        </div>
                          <div class="form-group">
                            <label for="formPermResource" class="col-sm-3 control-label key">排序：</label>
                            <div class="col-sm-8">
                                <input type="number" min="1" max="1000" class="form-control val" id="formRanking" name="ranking" placeholder="请输入排序编号">
                            </div>
                        </div>

                         <div class="form-group">
                            <label for="formMemo" class="col-sm-3 control-label key">备注：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control val" id="sysPermissionformMemo" name="memo" placeholder="请填写备注信息">
                            </div>
                        </div>                            
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否可用：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" name="isAvailable" id="sysPermissionformIsAvailable1" value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="isAvailable" id="sysPermissionformIsAvailable2" value="0"> 否
                                </label>                                    
                            </div>
                        </div>    
                <div class="modal-footer">
                    <button type="button" style="width: 95px; height: 32px; color: #3f9fff;background: #fff;border-color: #3f9fff;margin-right: 50px !important;" class="btn btn-default pull-right"  id="sysPermissionResetBtn">重置</button>
                    <button type="button" style="width: 95px; height: 32px; background:#2b94fd;" class="btn btn-default pull-right btncolora"  id="sysPermissionEditBtn">保存</button>
                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
