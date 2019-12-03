<meta charset="utf-8">

  <div class="container-fluid">
  <div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
  <ul id="orderCountTabNs" class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#orderCountDayNs" aria-controls="orderCountDayNs" role="tab" data-toggle="tab">按日统计</a></li>
	    <li role="presentation"><a href="#orderCountWeekNs" aria-controls="orderCountWeekNs" role="tab" data-toggle="tab">按周统计</a></li>
	    <li role="presentation"><a href="#orderCountMonthNs" aria-controls="orderCountMonthNs" role="tab" data-toggle="tab">按月统计</a></li>
	  
	  </ul>
   <div class="row">
    <div class="col-md-12 pd10">
     <div class="box box-default">
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="tab-content backgroundColor">
       <div role="tabpanel" class="tab-pane active" id="orderCountDayNs" style="width:100%;white-space:nowrap;">
       
       <form class="form-horizontal" name="orderSerachDayNsForm">
          <div class="row pull-down-menu">
			        <div class="seach-input-item pull-left">
                     <span >开始时间</span><input  class="dayTime form-control" name="createTimeStart" style="background:#FFFFFF" readonly/>
                 </div>
                  <div class="seach-input-item pull-left">
                       <span >结束时间</span> <input  class="dayTime form-control" name="createTimeEnd" style="background:#FFFFFF" readonly/>
                   </div> 
            <!--修改-->
                    <div class="box-footer-btn">
                         <button type="button" class="btn-new-type" id="orderSearchDayNs">确定</button>
                          <button type="reset" class="btn-new-type">清空</button>
                     </div>
           </div>
         </form>
       
       
     	<div class="row " >
     	<div class="">
                        <!-- <span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：订单每天销售的数量于金额统计</span> -->
              </div>
		<div class="col-sm-6 Jq_resize_box">
			 	<div id="orderEportNs" style="width: 600px;height:320px;"></div>
		</div>
	
		<div class="col-sm-6 Jq_resize_box">
			 	<div id="orderEportNso" style="width: 600px;height:320px;margin-right: 5%;margin-left: -20px;"></div>
		</div>
	</div> 
	
	<div class="row" >
	<div class="">
                       <!--  <span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：订单按天合计销售的数量于金额统计</span> -->
              </div>
		<div class="col-sm-6 Jq_resize_box">
			 	<div id="orderEportNsMs" style="width: 600px;height:320px;"></div>
		</div>
	
		<div class="col-sm-6 Jq_resize_box">
			 	<div id="orderEportNsoMs" style="width: 600px;height:320px;margin-right: 5%;margin-left: -20px;"></div>
		</div>
	</div> 
	
	</div>
	
      
       <div role="tabpanel" class="tab-pane" id="orderCountWeekNs" style="width:100%;white-space:nowrap;">
       
       <form class="form-horizontal" name="orderCountWeekNsForm">
          <div class="row pull-down-menu">
			        <div class="seach-input-item pull-left">
                     <span >开始时间</span><input  class="dayTime form-control" name="createTimeStartWeek" style="background:#FFFFFF" readonly/>
                 </div>
                  <div class="seach-input-item pull-left">
                       <span >结束时间</span> <input  class="dayTime form-control" name="createTimeEndWeek" style="background:#FFFFFF" readonly/>
                   </div> 
            <!--修改-->
                    <div class="box-footer-btn">
                         <button type="button" class="btn-new-type" id="orderSearchWeekNs">确定</button>
                         <button type="reset" class="btn-new-type">清空</button>
                     </div>
           </div>
         </form>
       
       
     	<div class="row " >
     	
     	<div class="col-md-6 Jq_resize_box">
     	<div class="">
                       <!--  <span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：订单每周销售的数量于金额统计</span> -->
              </div>
              <div class="">
                  
   			   <div id="orderEportWeekNs" style="width: 600px;height:320px;"></div> 
      
              </div>
     	 </div>
     	 <div class="col-md-6 Jq_resize_box">
              <div class="">
                  
   			   <div id="orderEportWeekNso" style="width: 600px;height:320px;margin-right: 5%;margin-left: -20px;"></div> 
      
              </div>
     	 </div>
     	
	</div> 
	
	<div class="row" >
	<div class="">
                       <!--  <span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：订单每天销售的数量于金额统计</span> -->
              </div>
	<div class="col-md-6 Jq_resize_box">
	
              <div class="">
                  
   			   <div id="orderEportWeekNsMs" style="width: 600px;height:320px;"></div> 
      
              </div>
      </div>
		<div class="col-md-6 Jq_resize_box">
              <div class="">
                  
   			   <div id="orderEportWeekNsoMs" style="width: 600px;height:320px;margin-right: 5%;margin-left: -20px;"></div> 
      
              </div>
      </div>
	</div> 
	
	</div>
	
	<div role="tabpanel" class="tab-pane" id="orderCountMonthNs" style="width:100%;white-space:nowrap;">
       
       <form class="form-horizontal" name="orderCountMonthNsForm">
          <div class="row pull-down-menu">
			        <div class="seach-input-item pull-left">
                     <span >开始时间</span><input  class="monthTime form-control" name="createTimeStartMonth" style="background:#FFFFFF" readonly/>
                 </div>
                  <div class="seach-input-item pull-left">
                       <span >结束时间</span> <input  class="monthTime form-control" name="createTimeEndMonth" style="background:#FFFFFF" readonly/>
                   </div> 
            <!--修改-->
                    <div class="box-footer-btn">
                         <button type="button" class="btn-new-type" id="orderSearchMonthNs">确定</button>
                          <button type="reset" class="btn-new-type">清空</button>
                     </div>
           </div>
         </form>
       
       
     	<div class="row" >
     	
     	<div class="col-md-6 Jq_resize_box">
     	<div class="">
                       <!--  <span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：订单每月销售的数量于金额统计</span> -->
              </div>
              <div class="">
                  
   			   <div id="orderEportMonthNs" style="width: 600px;height:320px;"></div> 
      
              </div>
     	 </div>
     	 <div class="col-md-6 Jq_resize_box">
     	 
              <div class="">
                  
   			   <div id="orderEportMonthNso" style="width: 600px;height:320px;margin-right: 5%;margin-left: -20px;"></div> 
      
              </div>
     	 </div>
     	
	</div> 
	
	<div class="row" >
	
	<div class="col-md-6">
	<div class="">
                       <!--  <span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：订单按月统计销售的数量于金额统计</span> -->
              </div>
              <div class="">
                  
   			   <div id="orderEportMonthNsMs" style="width: 600px;height:320px;"></div> 
      
              </div>
      </div>
		<div class="col-md-6">
              <div class="">
                  
   			   <div id="orderEportMonthNsoMs" style="width: 600px;height:320px;margin-right: 5%;margin-left: -20px;"></div> 
      
              </div>
      </div>
	</div> 
	
	</div>
      
	</div>
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
   
   
<script type="text/javascript" src="${basePath!'' }/res/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"orderEportForm": "res/js/reportForm/orderEportForm"
			}
		});
		require(["orderEportForm"], function(orderEportForm) {
			orderEportForm.init();
		});

	 
	});
</script>
<script type="text/javascript">  

    $(".yearTime").datetimepicker({
    	  format: 'yyyy',  
          weekStart: 1,  
          autoclose: true,  
          startView: 4,  
          minView: 4,  
          forceParse: false,  
          language: 'zh-CN'  
    });


$('.monthTime').datetimepicker({  
    format: 'yyyy-mm',  
     weekStart: 1,  
     autoclose: true,  
     startView: 3,  
     minView: 3,  
     forceParse: false,  
     language: 'zh-CN'  
}); 

$('.dayTime').datetimepicker({  
	 language: "zh-CN",
	 minView: "month",
     autoclose: true,//选中之后自动隐藏日期选择框
     clearBtn: true,//清除按钮
     todayBtn: 'linked',//今日按钮
     format: "yyyy-mm-dd"  
}); 


</script> 
