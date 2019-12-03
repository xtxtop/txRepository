<meta charset="utf-8">

  <div class="container-fluid">
       <div class="col-xs-12">
       <div class="box">
        <div class="box-header">
         <!-- <h3 class="box-title">结果集</h3> -->
        </div><!-- /.box-header -->
        <div class="box-body">
         <table id="wareStockOutItem" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <!-- 弹出框-->
    <div class="modal fade" id="wareStockOutItemModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定发货</h4>
                </div>
                <div class="modal-body">
                       <form class="form-horizontal" name="wareStockOutItemForm"> 
                     <input type="hidden"  name="orderId">
                           <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">出库单编号：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"  id="stockOutNo" name="stockOutNo" disabled placeholder="">
                            </div>
                            <label for="inputEmail3" class="col-sm-3 control-label">物流公司：</label>
                            <div class="col-sm-8">
                            <select class="form-control" id="companyName"></select>
                            </div>
                           </div>
                        </div> 
                              <div class="modal-footer">
                       <input type="button" class="btn btn-default pull-right sure" id="wareStockOutItemBtn" value="确定">
()
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
