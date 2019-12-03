<meta charset="utf-8">
<style>
	#mapLctNsEdit{width:100%;height:500px;}
</style>
<div class="container-fluid backgroundColor">
	<form name="editWorkerOrderForm">
		<input type="hidden" name="workerOrderNo" value="${workerOrder.workerOrderNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">调度工单编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">工 单 编 号：</label>
						</td>
						<td>
							<div class=" val">
							    ${workerOrder.workerOrderNo}
							</div>
						</td>
						<td>
							<label class=" control-label key">*调度类型：</label>
						</td>
						<td>
							<label class="">
								  <input type="checkbox" name="type" value="0" <#if workerOrder.type?index_of("0")!=-1> checked="checked" </#if> > 调度
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="1" <#if workerOrder.type?index_of("1")!=-1> checked="checked" </#if>> 救援 
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="2" <#if workerOrder.type?index_of("2")!=-1> checked="checked" </#if>> 清洁 
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="3" <#if workerOrder.type?index_of("3")!=-1> checked="checked" </#if>> 加油
								</label>
								<label class="">
								  <input type="checkbox" name="type" value="4" <#if workerOrder.type?index_of("4")!=-1> checked="checked" </#if>> 维保
								</label>
								<span name="typeEdit"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key">*调度员：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="hidden"  name="workerId" value="${workerOrder.workerId}"/>
							<input type="text" class="form-control val" value="${workerOrder.workerName}"  name="workerName" readonly="readonly"/>
							<input type="button" class="btn btn-info"  id="workerMgEdit" value="选择">	
							<span name="workerIdEdit"></span>
						</td>
						<td>
							<label class=" control-label key">*车牌号：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="text" class="form-control val" value="${workerOrder.carPlateNo}" name="carPlateNo" readonly="readonly"/>
							<input type="hidden"  name="carNo" value="${workerOrder.carNo}"/>
							<input type="button" class="btn btn-info"  id="carPlateNoMgEdit" value="选择">	
							<span name="carNoEdit"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key">*起点：</label>
						</td>
						<td>
							<input type="text" class="form-control val" value="${workerOrder.startParkName}"  name="startParkName"/>
								<input type="hidden"  value="${workerOrder.startParkNo}" name="startParkNo"/>
							<span name="startParkNoEdit"></span>	
						</td>
						<td>
							<label class=" control-label key">*终点：</label>
						</td>
						<td>
							<div class=" val">
							<input type="button" class="btn btn-success"  id="parkZyEdit" value="场站">
							<input type="button" class="btn btn-success"  id="mapZyEdit" value="地图">
							
							<#if (workerOrder.terminalParkNo)??>
							
							<div id="parkMhEdit">
							<select name="terminalParkNo" id="pkNsEdit" class="form-control val" >
								 <#list parkList as p>
									 <option value="${p.parkNo}" <#if p.parkNo==workerOrder.terminalParkNo>selected="selected"</#if>>
				                            ${p.parkName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							
							<div  id="mapMhEdit" style="display:none;">
							
								<input type="text" class="form-control val" value="${workerOrder.terminalParkName}"  name="terminalParkName"/> 
								<input type="button" class="btn btn-info"   id="mapLocationBdEdit" value="地图选址">
							
							</div>
							
							
							<#else>
							<div id="parkMhEdit" style="display:none;">
							<select name="terminalParkNo" id="pkNsEdit" class="form-control val" >
								 <#list parkList as p>
									 <option value="${p.parkNo}" <#if p.parkNo==workerOrder.terminalParkNo>selected="selected"</#if>>
				                            ${p.parkName}
				                     </option>
				                 </#list>  
								</select>
							</div>
							
							<div  id="mapMhEdit" >
							
								<input type="text" class="form-control val" value="${workerOrder.terminalParkName}"  name="terminalParkName"/> 
								<input type="button" class="btn btn-info"  id="mapLocationBdEdit" value="地图选址">
							
							</div>
							
							</#if>
							
							</div>
							<span name="terminalParkNoEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label">*计划完成：</label>
						</td>
						<td>
							<input class="form-control val datetimepicker" value="${workerOrder.planTime?string('yyyy-MM-dd HH:mm:ss')}" name="planTime"/>
							<span name="planTimeEdit"></span>
						</td>
						<td>
							<label class=" control-label key">下发时间：</label>
						</td>
						<td>
							<#if workerOrder.sendTime?exists>
								${workerOrder.sendTime?string('yyyy-MM-dd')}
							</#if>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">开始时间：</label>
						</td>
						<td>
							<#if workerOrder.startTime?exists>
								${workerOrder.startTime?string('yyyy-MM-dd')}
							 </#if>
						</td>
						<td>
							<label class=" control-label key">完成时间：</label>
						</td>
						<td>
							<#if workerOrder.finishTime?exists>
								${workerOrder.finishTime?string('yyyy-MM-dd')}
							</#if>	
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">审核状态：</label>
						</td>
						<td>
							<#if workerOrder.cencorStatus==0>
								    未审核
								<#elseif workerOrder.cencorStatus==1>
                				    已审核
                				<#elseif workerOrder.cencorStatus==2>
                				    未通过
                				</#if>
						</td>
						<td>
							<label class=" control-label key reason">审核备注：</label>
						</td>
						<td>
							<div class=" val">
								${workerOrder.cencorMemo}
							</div>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label key">工单状态：</label>
						</td>
						<td>
							<#if workerOrder.workOrderStatus==0>
								   未下发
								<#elseif workerOrder.workOrderStatus==1>
                				    已下发
                				<#elseif workerOrder.workOrderStatus==2>
                				    调度中
                				<#elseif workerOrder.workOrderStatus==3>
                				    已结束
                				<#elseif workerOrder.workOrderStatus==4>
                				    已取消
                				</#if>
						</td>
						<td>
							<label class=" control-label key reason">备注：</label>
						</td>
						<td>
							<div class=" val">
                				${workerOrder.memo}
                			</div>
                			<span name="memoEdit"></span>
						</td>
						
					</tr>
						<tr>
							<td>
							<label class=" control-label key">创建时间：</label>
						</td>
						<td>
							<div class="val">
                				${workerOrder.createTime?string('yyyy-MM-dd HH:mm:ss')}
                			</div>
						</td>
						<td>
							<label class=" control-label key">更新时间：</label>
						</td>
						<td>
							<div class="val">
                				${workerOrder.updateTime?string('yyyy-MM-dd HH:mm:ss')}
                			</div>
						</td>
						
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveWorkerOrder" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeWorkerOrderEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!--选择车牌号-->
<div class="modal" id="carModalMgEdit">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择车牌号</div>
				</div>
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="carMgBtnTplEdit" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body box-body-change-padding">
			         <table id="carMgListEdit" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="carMgToolssssEdit">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<!--选择调度员-->
<div class="modal" id="workerMgEditModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择调度员</div>
				</div>
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="workerMgBtnTplEdit" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body  box-body-change-padding">
			         <table id="workerMgListEdit" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			    <div class="carRedPacketAddParkTool-bullet" id="workerMgToolssssEdit">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="mapLocationEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					选择地址  <br/> <span id="lctNsEdit"></span>
				</h4>
			</div>
			<div class="modal-body">
				
				 <div id="mapLctNsEdit" tabindex="0"></div>
  				 
   			</div>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">确定
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->

<script type="text/javascript">
   $(function () {
    	require.config({
		paths: {
			"workerOrderEdit":"res/js/workerOrder/workerOrder_edit"
		}
		});
		require(["workerOrderEdit"], function(workerOrderEdit) {
			workerOrderEdit.init();
		});
    
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
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        
        
    });
</script>
