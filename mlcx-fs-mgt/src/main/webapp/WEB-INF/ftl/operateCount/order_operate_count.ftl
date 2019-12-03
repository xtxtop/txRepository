<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />

<body>
<div class="container-fluid">
	<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
	  <!-- Nav tabs -->
	  <ul id="orderCountTabs" class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#orderCountDay" aria-controls="orderCountDay" role="tab" data-toggle="tab">按日统计</a></li>
	    <li role="presentation"><a href="#orderCountWeek" aria-controls="orderCountWeek" role="tab" data-toggle="tab">按周统计</a></li>
	    <li role="presentation"><a href="#orderCountMonth" aria-controls="orderCountMonth" role="tab" data-toggle="tab">按月统计</a></li>
	   
	  </ul>
	    <!-- Tab panes -->
	  <div class="tab-content">
	    <div role="tabpanel" class="tab-pane active" id="orderCountDay">
	    
         <form class="form-horizontal" name="orderSerachDayForm">
          
	         <input type="hidden" name="parkNameTempString"  id="parkNameTempString" />
         
          <div class="row pull-down-menu">
	    	<div class="col-md-2">
			        <div class="frombg">
                     <span style="width:40%">时间开始</span><input style="width:56%;background:none;" class="datetimepicker form-control" name="startTimeDay" style="background:#FFFFFF" readonly/>
                 </div>
			</div>
            <div class="col-md-2">
                  <div class="frombg">
                       <span style="width:40%">时间结束</span> <input style="width:56%;background:none;" class="datetimepicker form-control" name="endTimeDay" style="background:#FFFFFF" readonly/>
                   </div> 
            </div>
            <!--修改-->
            <div class="col-md-3">
                    <div class="box-footer">


                           <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="orderSearchDay" style="background:#2b94fd">确定</button>
                             <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="background:#fa6e30">清空</button>
         </div>
            </div>
            <div class="col-md-2">
                    <div class="box-footer">
                         <button type="button" class="btn btn-default pull-right btn-flat btncolorb" id="orderOpenBMDay"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>选择场站</button>
                    </div>
            </div>
           </div>
         </form>
                         
         			   
	        <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr><td><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：各部门每天与按天合计的订单数统计（取订单数前5的场站）</span></td></tr>
                        	
                        	<tr>
                        		<td><div id="orderOperateCountDay" style="width: 600px;height:320px;"></div>  </td>
                        		<td><div id="orderOperateDayCount" style="width: 600px;height:320px;"></div> </td>
                        	</tr>
                        </table>
         			     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
            
            </br>    
            
	        <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr><td><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：各部门每天与按天合计的订单金额统计（取订单数前5的场站）</span></td></tr>
                        	<tr>
                        		<td><div id="orderOperateJECountDay" style="width: 600px;height:320px;"></div>  </td>
                        		<td><div id="orderOperateJEDayCount" style="width: 600px;height:320px;"></div> </td>
                        	</tr>
                        </table>
         			     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
            
            </br>    
	    </div>
	    <div role="tabpanel" class="tab-pane" id="orderCountWeek">
	    
          <form class="form-horizontal" name="orderSerachWeekForm">
          
	         <input type="hidden" name="parkNameWeekTempString"  id="parkNameWeekTempString" />
         
          <div class="row pull-down-menu">
	    	<div class="col-md-2">
			        <div class="frombg">
                     <span style="width:40%">时间开始</span><input style="width:56%;background:none;" class="datetimepicker form-control" name="startTimeWeek" style="background:#FFFFFF" readonly/>
                 </div>
			</div>
            <div class="col-md-2">
                  <div class="frombg">
                       <span style="width:40%">时间结束</span> <input style="width:56%;background:none;" class="datetimepicker form-control" name="endTimeWeek" style="background:#FFFFFF" readonly/>
                   </div> 
            </div>
            <!--修改-->
            <div class="col-md-3">
                    <div class="box-footer">

                         <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="orderSearchWeek" style="background:#2b94fd">确定</button>
                          <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"  style="background:#fa6e30">清空</button>
                     </div>
            </div> 
            <div class="col-md-2">
                    <div class="box-footer">
                         <button type="button" class="btn btn-default pull-right btn-flat btncolorb" id="orderOpenBMWeek"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>选择场站</button>
                    </div>
            </div>
           </div>
         </form>
                  
                  
	        <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr><td><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：各部门每周与按周合计的订单数统计（取订单数前5的场站）</span></td></tr>
                        	<tr>
                        		<td><div id="orderOperateCountWeek" style="width: 600px;height:320px;"></div> </td>
                        		<td><div id="orderOperateWeekCount" style="width: 600px;height:320px;"></div> </td>
                        	</tr>
                        </table>
         			     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
            
            </br>    
            
	        <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr><td><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：各部门每周与按周合计的订单金额统计（取订单数前5的场站）</span></td></tr>
                        	<tr>
                        		<td><div id="orderOperateJECountWeek" style="width: 600px;height:320px;"></div>  </td>
                        		<td><div id="orderOperateJEWeekCount" style="width: 600px;height:320px;"></div> </td>
                        	</tr>
                        </table>
         			     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
             
            </br>    
	    </div>
	    <div role="tabpanel" class="tab-pane" id="orderCountMonth">
	    
          <form class="form-horizontal" name="orderSerachMonthForm">
          
	         <input type="hidden" name="parkNameMonthTempString"  id="parkNameMonthTempString" />
         
          <div class="row pull-down-menu">
	    	<div class="col-md-2">
			        <div class="frombg">
                     <span style="width:40%">时间开始</span><input style="width:56%;background:none;" class="datetimepicker form-control" name="startTimeMonth" style="background:#FFFFFF" readonly/>
                 </div>
			</div>
            <div class="col-md-2">
                  <div class="frombg">
                       <span style="width:40%">时间结束</span> <input style="width:56%;background:none;" class="datetimepicker form-control" name="endTimeMonth" style="background:#FFFFFF" readonly/>
                   </div> 
            </div>
            <!--修改-->
            <div class="col-md-3">
                    <div class="box-footer">

                         <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="orderSearchMonth" style="background:#2b94fd">确定</button>
                           <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb" style="background:#fa6e30">清空</button>
                     </div>
            </div> 
            <div class="col-md-2">
                    <div class="box-footer">
                         <button type="button" class="btn btn-default pull-right btn-flat btncolorb" id="orderOpenBMMonth"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>选择场站</button>
                    </div>
            </div>
           </div>
         </form>
                  
                  
	        <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr><td><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：各部门每月与按月合计的订单数统计（取订单数前5的场站）</span></td></tr>
                        	<tr>
                        		<td><div id="orderOperateCountMonth" style="width: 600px;height:320px;"></div> </td>
                        		<td><div id="orderOperateMonthCount" style="width: 600px;height:320px;"></div> </td>
                        	</tr>
                        </table>
         			     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
            
            </br>    
            
	        <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr><td><span style="font-weight:bold;font-size:20px;color:#4A4AFF;">说明：各部门每月与按月合计的订单金额统计（取订单数前5的场站）</span></td></tr>
                        	<tr>
                        		<td><div id="orderOperateJECountMonth" style="width: 600px;height:320px;"></div>  </td>
                        		<td><div id="orderOperateJEMonthCount" style="width: 600px;height:320px;"></div> </td>
                        	</tr>
                        </table>
         			     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
             
              
            </br>  
	    </div>
 




    <!-- 弹出框-->
    <div class="modal fade" id="orderOperateCountDayModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择部门</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="orderOperateCountDayForm"> 
                        
                        <table>
									<tr>
							<#list parkList as parkList>
								<#if parkList_index%4==0>
									<td><input type='checkbox' class='sysUser-sysRole-chk' name='parkName' value="${parkList.parkName}" >${parkList.parkName}</td> 
									</tr>
								<#else>	
									<td><input type='checkbox' class='sysUser-sysRole-chk' name='parkName' value="${parkList.parkName}" >${parkList.parkName}</td> 
								</#if>
							</#list>
                        </table>
                     
                        <div class="modal-footer">
		                     <button type="button" class="btn btn-default pull-right sure btncolora"  id="orderOperateCountDayBtn"><i class="glyphicon glyphicon-check"></i>确定</button>

		                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    

    <!-- 弹出框-->
    <div class="modal fade" id="orderOperateCountWeekModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择部门</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="orderOperateCountWeekForm"> 
                        
                        <table>
									<tr>
							<#list parkList as parkList>
								<#if parkList_index%4==0>
									<td><input type='checkbox' class='sysUser-sysRole-chk' name='parkNameWeek' value="${parkList.parkName}" >${parkList.parkName}</td> 
									</tr>
								<#else>	
									<td><input type='checkbox' class='sysUser-sysRole-chk' name='parkNameWeek' value="${parkList.parkName}" >${parkList.parkName}</td> 
								</#if>
							</#list>
                        </table>
                     
                        <div class="modal-footer">
		                     <button type="button" class="btn btn-default pull-right sure btncolora"  id="orderOperateCountWeekBtn"><i class="glyphicon glyphicon-check"></i>确定</button>

		                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    

    <!-- 弹出框-->
    <div class="modal fade" id="orderOperateCountMonthModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择部门</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="orderOperateCountMonthForm"> 
                        
                        <table>
									<tr>
							<#list parkList as parkList>
								<#if parkList_index%4==0>
									<td><input type='checkbox' class='sysUser-sysRole-chk' name='parkNameMonth' value="${parkList.parkName}" >${parkList.parkName}</td> 
									</tr>
								<#else>	
									<td><input type='checkbox' class='sysUser-sysRole-chk' name='parkNameMonth' value="${parkList.parkName}" >${parkList.parkName}</td> 
								</#if>
							</#list>
                        </table>
                     
                        <div class="modal-footer">
		                     <button type="button" class="btn btn-default pull-right sure btncolora"  id="orderOperateCountMonthBtn"><i class="glyphicon glyphicon-check"></i>确定</button>

		                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    

    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"orderOperateCount":"res/js/operateCount/order_operate_count"}});
		require(["orderOperateCount"], function (orderOperateCount){
			orderOperateCount.init();
		});  
	});
 
 
   $(function () {
        $(".datetimepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
    
</script>
