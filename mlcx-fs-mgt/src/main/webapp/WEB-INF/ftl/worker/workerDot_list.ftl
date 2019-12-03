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
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="workerDotSearchForm">
         <input type="hidden" value="${workerId}" name="workerId">
        <div class="box-body">
         <div class="row pull-down-menu">
          <div class="col-md-3">
                <div class="frombg">
                    <span>场站名称</span><input type="text" class="form-control"  name="parkName" placeholder="">
                </div>
          </div>
         
          <div class="col-md-3">
                  <div class="box-footer">
                     <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
                     <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"><i class="hziconfont icons-qingchu iconbtn"></i>清空</button>
                     <button type="button" class="btn btn-default pull-right btn-flat btncolora"  id="workerDotSearchafter"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>确认</button>
                   </div>
          </div>
          
         </div><!-- /.box-body -->

         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="workerDotTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="workerDotList" class="table table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    
    <!-- 模态框（Modal） -->
<div class="modal fade" id="workerDotModalAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					场站信息
				</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped">
  
  <thead>
    <tr>
      <th>场站编号</th>
      <th>场站名称</th>
    </tr>
  </thead>
  <tbody id="parkInfoMs">
    
  </tbody>
</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消
				</button>
				<button type="button" class="btn btn-primary" id="addWorkerDots">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
    
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"workerDot":"res/js/worker/workerDot_list"}});
		require(["workerDot"], function (workerDot){
			workerDot.init();
		});  
	   }); 
    </script>
