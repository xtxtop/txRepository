<meta charset="utf-8">

  <div class="container-fluid">
     <div class="row">
    <div class="col-md-12">
     <div class="box box-default">
      <div class="box-header with-border">
       <h3 class="box-title">查询</h3>
       <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
       </div><!-- /.box-tools -->
       </div><!-- /.box-header -->
       <form class="form-horizontal" name="purchaseOrderYSearchForm"> 
       <div class="box-body">
        <div class="row">
         <div class="col-md-2">采购单编号<input type="text" class="form-control" name="poNo" placeholder=""></div>
         <div class="col-md-2">客户公司<input type="text" class="form-control" name="sellerName" placeholder=""></div>
         </div>
        </div><!-- /.box-body -->
        <!--修改-->
        <div class="box-footer">
         <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
         <button type="reset" class="btn btn-default pull-right btn-flat" style="background:#fa6e30">清空</button>
         <button type="button" class="btn btn-default pull-right btn-flat" id="purchaseOrderY" style="background:#2b94fd">确定</button>

        </div><!-- /.box-footer -->
        </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="purchaseOrderBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-header">
         <!-- <h3 class="box-title">结果集</h3> -->
        </div><!-- /.box-header -->
        <div class="box-body" >
         <table id="purchaseOrderfindAll" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
     <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
