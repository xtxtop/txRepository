<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
       <div class="box box-default">
        <div class="box-header with-border">
         <h3 class="box-title">查询</h3>
         <hr>
         <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="carExtraCostsSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu">
          <div class="col-md-3">
                <div class="frombg">
                    <span>型号</span><input type="text" class="form-control"  name="carExtraCostsNameck" placeholder="">
                </div>
          </div>
           <div class="col-md-3">
                <div class="frombg">
                    <span>品牌</span><select class="form-control" name="carBrandId">
                                                <option value="" selected="selected">全部</option>
                                                <#list lb as l>
		                                    <option value="${l.carBrandId}" >
		                                        ${l.carBrandName}
		                                    </option>
		                               		 </#list>
                                               </select>
                </div>
          </div>
          
          <div class="col-md-3">
                <div class="frombg">
                    <span>类别</span><select class="form-control" name="carType">
                                                <option value="" selected="selected">全部</option>
                                                <option  value="1" >经济型</option>
			                                    <option  value="2" >商务型</option>
			                                    <option  value="3" >豪华型</option>
			                                    <option  value="4" >6至15座商务</option>
			                                    <option  value="5" >SUV</option>
                                               </select>
                </div>
          </div>
           <!--修改-->
          <div class="col-md-3">
                  <div class="box-footer">
                     <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
                     <!-- <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"><i class="hziconfont icons-qingchu iconbtn"></i>清空</button>-->

                     <!-- <button type="button" class="btn btn-default pull-right btn-flat btncolora"  id="carExtraCostsSearchafter"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>确认</button>-->
                  <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault" style="background:#fa6e30">清空</button>
                 <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA"  id="carExtraCostsSearchafter" style="background:#2b94fd">确定</button>

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
       <script id="carExtraCostsTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="carExtraCostsList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"carExtraCosts":"res/js/car/car_extra_costs_list"}});
		require(["carExtraCosts"], function (carExtraCosts){
			carExtraCosts.init();
		});  
	   }); 
    </script>
