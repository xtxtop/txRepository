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
       
        <form class="form-horizontal" name="statisticsSearchForm"> 
       <div class="box-body">
        <div class="row">
         <div class="col-md-2">客户/公司名称<input type="text" class="form-control" name="customerName" placeholder=""></div>
          <div class="col-md-2">年份
          <select class="form-control" name="statYear" id="statYear">
                  
           </select> 
          </div>
         </div>
        </div><!-- /.box-body -->
        <!--修改-->
        <div class="box-footer">
         <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
         <button type="reset" class="btn btn-default pull-right btn-flat"  style="background:#fa6e30">清空</button>
         <button type="button" class="btn btn-default pull-right btn-flat" id="statisticsSearch_btn_sub_order" style="background:#2b94fd">确定</button>

        </div><!-- /.box-footer -->
        </form>
        
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
<div id="container" style="width:850px;height:500px;margin:0 auto"></div>
</div>
 
<script type="text/javascript" src="${basePath!'' }/res/js/statistics/highcharts.js"></script>
<script type="text/javascript" src="${basePath!'' }/res/js/statistics/exporting.js"></script>
<script type="text/javascript" src="${basePath!'' }/res/js/statistics/main.js"></script>
 
