<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />

<body>
<div class="container-fluid">
	<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
	  <!-- Nav tabs -->
	  <ul id="itemTabs" class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#orderDetail" aria-controls="orderDetail" role="tab" data-toggle="tab">发货单详情</a></li>
	  </ul>
	    <!-- Tab panes -->
	  <div class="tab-content">
	    <div role="tabpanel" class="tab-pane active" id="orderDetail">
           <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <div class="box-header with-border">
                         <h3 class="box-title">订单信息</h3>
                             <div class="box-tools pull-right">
                              <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                              </button>
                             </div>
                             <!-- /.box-tools -->
                        </div>
                        <!-- /.box-header -->
       
                        <div class="box-body">
                             <div class="row" name="orderInfo">
                                <div class="col-md-3">订单编号：<span name="orderNo"></span></div>          
                                <div class="col-md-3">下单时间：<span name="createTime"></span></div>
                                <div class="col-md-3">支付状态：<span name="payStatus"></span></div>
                              </div> 
                         </div><!-- /.box-body -->  
                     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row -->   
             <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <div class="box-header with-border">
                         <h3 class="box-title">货品信息</h3>
                             <div class="box-tools pull-right">
                              <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                              </button>
                             </div>
                             <!-- /.box-tools -->
                        </div>
                        <!-- /.box-header -->
       
                        <div class="box-body">
                             <div class="row">
                                <table id="orderItemList" class="table table-bordered table-striped table-hover" width="100%">
                                </table>
                              </div> 
                         </div><!-- /.box-body -->  
                     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
             <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <div class="box-header with-border">
                         <h3 class="box-title">收货人信息</h3>
                             <div class="box-tools pull-right">
                              <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                              </button>
                             </div>
                             <!-- /.box-tools -->
                        </div>
                        <!-- /.box-header -->
       
                        <div class="box-body" id="goodsItem">
                            
                         </div><!-- /.box-body -->  
                     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
	    </div>
	  </div>
	  
	</div>
</div>

    <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
</body>
</html>
