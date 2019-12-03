<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		.seach-input-item pull-left .form-control {
			width: 45% !important;
			margin-right: 20%;
		}
		.pull-down-menu span {
			width: 20%;
		}
	}
	
	@media only screen and (max-width:768px) {
		.row .sorting_disabled {
			font-size: 1.1rem !important;
		}
	}
	
	@media only screen and (min-width:768px) and (max-width:1024px) {
		.row .sorting_disabled {
			font-size: 1.2rem !important;
		}
		.seach-input-item pull-left .form-control {
			margin-right: 10%；
		}
	}
	
	@media only screen and (min-width:1024px) and (max-width:1366px) {
		.row .sorting_disabled {
			font-size: 1.3rem !important;
		}
	}
	
	.other {
		margin: 0 !important;
		padding: 0 !important;
	}
	
	.float {
		float: right;
	}
	
	.floatLeft {
		float: left;
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
   <div class="container-fluid">
     <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
                <form class="form-horizontal" name="deviceUploadpkgLogDetailSearchForm">
	        	<input type="hidden" name="pageNo"/>
	            <input type="hidden" name="pageSize" value="${pageSize}"/>
	            <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details car-status close-content clearfix">
	                        <div class="seach-input-item pull-left">
	                        	<span>车牌号</span><input type="text" class="form-control" name="carPlateNo" value="${carPlateNo}" readonly/>
	                        </div>
	                        <div class="seach-input-item pull-left">
	           					<span>设备序列号</span><input type="text" class="form-control" name="deviceSn" value="${deviceSn}" readonly/>
	                        </div>
	                        <div class="seach-input-item pull-left">
	                        	<span>上报时间</span><input class="datepicker form-control" name="createTimeStart"  value="${createTimeStart?string("yyyy-MM-dd HH:mm:ss")}"/>
	                        </div>
	                        <div class="seach-input-item pull-left">
				                <span>至</span><input class="datepicker form-control"name="createTimeEnd"  value="${createTimeEnd?string("yyyy-MM-dd HH:mm:ss")}"/>
	                        </div>
	                </div>
			
			        <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="deviceUploadpkgLogViewSearchForm">确定</button>
						<button type="reset" id="reportLogSearchForm" class="btn-new-type">导出原日记</button>


					</div>
	            </div>
		    	</form>
			</div>
		</div>
	</div>
    <!-- /.box-header -->        
      	
         <div class="row show-table-content">
	      <div class="col-xs-12 pd10">
	        <div>
	        	<button type="button" class="btn-new-type" id="reportBtn">导出</button>
	        	<button type="button" class="btn-new-type" id="nextBtn">下一页</button>
	        </div>
               
        	<!-- tabs -->
			<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
		  		<!-- Nav tabs -->
		  		<ul id="deviceLogViewTabs" class="nav nav-tabs">
			    	<li class="active"><a href="#txtTab" aria-controls="txtTab" role="tab" data-toggle="tab">文本</a></li>
			    	<li><a href="#listTab" aria-controls="listTab" role="tab" data-toggle="tab">列表</a></li>
		 		</ul>
        
			    <!-- Tab panes begin -->
				 <div class="tab-content">
				     <div class="tab-pane active" id="txtTab">
					 	<textarea rows="20" cols="100" style="width: 100%;" id="deviceLogTxtData"></textarea>
			      	 </div>
				     <div class="tab-pane" id="listTab">
			         <table id="deviceUploadpkgLogViewTable" class="table table-hover" width="100%"  border='1'>
				         <thead>
				         	<tr role="row">
					         	<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 153px;">车牌号</th>
					         	<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 210px;">设备序列号</th>
					         	<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 294px;">操作指令</th>
					         	<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 146px;">日志原文</th>
					         	<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 162px;">中文日志</th>
					         	<th class="sorting_disabled" rowspan="1" colspan="1" style="width: 282px;">上报时间</th>
				         	</tr>
				         </thead>
				         <tbody>
				         	
				         </tbody>
				       </table>
			      	</div>
	      		</div>
		    	<!-- Tab panes end -->
        	</div><!-- /.tabs -->
        </div><!-- /.col -->
    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
    	require.config({paths: {"deviceUploadpkgLogDetail":"res/js/device/device_log_view"}});
			require(["deviceUploadpkgLogDetail"], function(deviceUploadpkgLogDetail){
				deviceUploadpkgLogDetail.init();
			});  
	   }); 
	   
	   $(".datepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,//清除按钮
            todayBtn: true,
            minuteStep: 5,
            startDate: moment(new Date()).format("YYYY-MM HH:mm:ss"),
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    </script>
   
        