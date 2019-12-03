<meta charset="utf-8">

  <div class="container-fluid">
   
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="dirtyWordBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-header">
         <!-- <h3 class="box-title">结果集</h3> -->
        </div><!-- /.box-header -->
        <div class="box-body">
         <table id="dirtyWord" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->

    <!-- 弹出框-->
    <div class="modal fade" id="dirtyWordModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">商城黑词信息添加</h4>
                </div>
                <div class="modal-body">
                       <form class="form-horizontal" name="dirtyWordForm"> 
                        <input type="hidden"  name="wordId">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">黑词内容：</label>
                            <div class="col-sm-8">
                            <textarea class="form-control" rows="12"  name="content"></textarea>
                            <!--文本编辑器-->
                            </div>
                        </div>
                        <!--修改-->
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">清空</button>
                       <input type="button" class="btn btn-default pull-right" id="dirtyWordEditBtn" value="确认">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
