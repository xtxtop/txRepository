<meta charset="utf-8">

  <div class="container-fluid">
      <div class="row">
       <div class="col-xs-12">
        <!--定义操作列按钮模板-->
       <script id="wareStockTakingBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-header">
        </div><!-- /.box-header -->
        <div class="box-body">
         <table id="wareStockTakingList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row --><br>
     <!--
     <button type="button" id="exportStockTaking" class="btn btn-default btn-flat">打印点仓纸</button><br><br> 
     <button type="button" id="addWareStockTaking" class="btn btn-default btn-flat">录入</button> 
     -->
    </div><!-- /.container-fluid -->
    <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
