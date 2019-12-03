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
       <form class="form-horizontal" name="wareStockOutSearchForm"> 
       <div class="box-body">
        <div class="row">
         <div class="col-md-2">出库单号<input type="text" class="form-control" name="stockOutNo" placeholder=""></div>
         <div class="col-md-2">销售订单编号<input type="text" class="form-control" name="orderNo" placeholder=""></div>
         <div class="col-md-2">客户/公司名称<input type="text" class="form-control" name="buyerName" placeholder=""></div>
         </div>
        </div><!-- /.box-body -->
        <!--修改-->
        <div class="box-footer">
         <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->

         <button type="button" class="btn btn-default pull-right btn-flat" id="wareStockOutSearch" style="background:#2b94fd">确定</button>
          <button type="reset" class="btn btn-default pull-right btn-flat"  style="background:#fa6e30">清空</button>
        </div><!-- /.box-footer -->
        </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="wareStockOutBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-header">
         <!-- <h3 class="box-title">结果集</h3> -->
        </div><!-- /.box-header -->
        <div class="box-body">
         <table id="wareStockOut" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    
     <!-- 弹出框-->
    <div class="modal fade" id="wareStockOutModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">生成出库单</h4>
                </div>
                <div class="modal-body">
                       <form class="form-horizontal" name="wareStockOutForm"> 
                     <input type="hidden"  name="orderId">
                           <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">出库单编号：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"  name="stockOutNo" placeholder="">
                            </div>
                        </div> 
                              <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right sure" data-dismiss="modal">清空</button>
                       <input type="button" class="btn btn-default pull-right sure" id="wareStockOutEditBtn" value="保存">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
