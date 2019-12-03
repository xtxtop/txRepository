<meta charset="utf-8">
<style>
	#mapLctNsAdd {width:100%;height:500px;}
</style>
<div class="container-fluid backgroundColor">
	<form name="addWorkerOrderForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">调度工单添加</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>调度类型：</label>
						</td>
						<td>
							<label class="">
								  <input type="checkbox" name="type" value="0"> 调度
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="1"> 救援 
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="2"> 清洁 
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="3"> 加油
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="4"> 维保
								</label>
								<span name="typeAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>起点：</label>
						</td>
						<td>
							<input type="text" class="form-control val"  name="startParkName" placeholder="请输入起点"/>
								<input type="hidden"   name="startParkNo"/>
								<span name="startParkNoAdd"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>调度员：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="hidden"  name="workerId"/>
								<input type="text" class="form-control val"  name="workerName" readonly="readonly" placeholder="请选择调度员"/>
								<input type="button" class="btn btn-info btn-btnA"  id="workerMgAdd" value="选择">
							<span name="workerIdAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>车牌号：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="text" class="form-control val" name="carPlateNo" readonly="readonly" placeholder="请选择车牌号"/>
								<input type="hidden"  name="carNo"/>
								<input type="button" class="btn btn-info btn-btnA"  id="carPlateNoMgAdd" value="选择">
							<span name="carNoAdd"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label reason key"><span>*</span>计划完成：</label>
						</td>
						<td>
							<input class="form-control val datetimepicker" name="planTime" placeholder="请选择计划完成时间"/>
							<span name="planTimeAdd"></span>	
						</td>
						<td>
							<label class=" control-label reason key"><span></span>备注：</label>
						</td>
						<td>
						    <input class="form-control val" name="memo" placeholder="请填写备注"/>
							<span name="memoAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>终点：</label>
						</td>
						<td>
							<div class="">
							<input type="button" class="btn btn-success"  id="parkZyAdd" value="场站">
							<input type="button" class="btn btn-success"  id="mapZyAdd" value="地图">
							<div id="parkMhAdd">
							<select name="terminalParkNo" id="pkNsAdd" class="form-control val" >
								 <#list parkList as p>
									 <option value="${p.parkNo}" >
				                            ${p.parkName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							<div style="display:none;" id="mapMhAdd">
							
								<input type="text" class="form-control val"  name="terminalParkName"/> 
								<input type="button" class="btn btn-info addorderMonthCar"  id="mapLocationBdAdd" value="地图选址">
							
							</div>
							</div>
							<span name="terminalParkNoAdd"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addWorkerOrder" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeWorkerOrderAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="mapLocation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					选择地址  <br/> <span id="lctNsAdd"></span>
				</h4>
			</div>
			<div class="modal-body">
				
				 <div id="mapLctNsAdd" tabindex="0"></div>
  				 
   			</div>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">确定
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
<!--选择车牌号模态框-->
<div class="modal" id="carModalMgAdd">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择车牌号</div>
				</div>
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="carMgBtnTplAdd" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body box-body-change-padding">
			         <table id="carMgListAdd" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="carMgToolssssAdd">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>

<!--选择调度员模态框-->
<div class="modal" id="workerMgAddModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择调度员</div>
				</div>
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="workerMgBtnTplAdd" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body box-body-change-padding">
			         <table id="workerMgListAdd" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="workerMgToolssssAdd">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>

<script type="text/javascript" src="${basePath!'' }/res/js/workerOrder/workerOrder_add.js"></script>
<script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            startDate:new Date(), 
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        
        
        
        $("#mapLocationBdAdd").click(function(){
        	$("#lctNsAdd").empty();
        	$("input[name=terminalParkName]").empty();
        	$("#mapLocation").modal("show");
        	setTimeout(function(){
        		var map = new BMap.Map("mapLctNsAdd");
				var point = new BMap.Point(114.065537,22.549583);
				map.centerAndZoom(point,12);
			  map.enableScrollWheelZoom();
			  
			 var geolocation = new BMap.Geolocation();
				geolocation.getCurrentPosition(function(r){
					if(this.getStatus() == BMAP_STATUS_SUCCESS){
						var mk = new BMap.Marker(r.point);
						map.addOverlay(mk);
						map.panTo(r.point);
						
					}
					       
				},{enableHighAccuracy: true})
			  
				var geoc = new BMap.Geocoder();    

				map.addEventListener("click", function(e){        
					var pt = e.point;
					geoc.getLocation(pt, function(rs){
						var addComp = rs.addressComponents;
						$("#lctNsAdd").html(addComp.city+addComp.district+addComp.street+addComp.streetNumber);
						$("input[name=terminalParkName]").val(addComp.city+addComp.district+addComp.street+addComp.streetNumber);
					});        
				});
        	},200);
				
			
				
		});
        
    });
</script>