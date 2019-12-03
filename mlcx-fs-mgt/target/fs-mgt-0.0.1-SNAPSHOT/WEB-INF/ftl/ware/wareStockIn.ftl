<meta charset="utf-8">

   <div class="container-fluid">
      <div class="row">
	      <div class="col-xs-12">
	       <!--定义操作列按钮模板-->
	       <script id="wareStockInTpl" type="text/x-handlebars-template">
           {{#each func}}
           <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
           {{/each}}
       	   </script>
	       <div class="box">
	        <div class="box-header">
	         <!-- <h3 class="box-title">数据列</h3> -->
	        </div><!-- /.box-header -->
	        <div class="box-body">
	         <table id="wareStockInList" class="table table-bordered table-striped table-hover" width="100%">
	         
	         </table>
	        </div><!-- /.box-body -->
	       </div><!-- /.box -->
	      </div><!-- /.col -->
     </div><!-- /.row -->
      <button type="button" id="addWareStock" class="btn btn-default btn-flat">录入到仓库</button> 
     <input type="hidden" id="WareStockInOrder" ></input>
    </div><!-- /.container-fluid -->
    <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
