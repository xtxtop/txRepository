<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="checkPlanEditForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">巡检计划编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>编号：</label>
						</td>
						<td>
							<input class="form-control val" name="checkPlanNo" value="${checkPlan.checkPlanNo}"  readonly/>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option  <#if checkPlan.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>计划日期：</label>
						</td>
						<td>
								<input class="datepicker form-control val" name="planDate" value="${checkPlan.planDate?string('yyyy-MM-dd')}"  readonly/>
							<span name="planDate"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>计划巡检场站：</label>
						</td>
						<td>
							<input class="form-control val" name="parkName" id="parkNames" value="${checkPlan.parkName}" readonly/>
							<input type="hidden" name="parkNo" id="parkNos" value="${checkPlan.parkNo}"/>
							<button type="button" class="btn btn-default" id="checkAddParkBtn">场站列表</button>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>计划巡检项：</label>
						</td>
						<td>
							<#list items as item>
							<label>
		                    <input type="checkbox" class="butcheck" value="${item.dataDictItemId}"><span>${item.itemValue}</span>
							</label>
				            </#list>
				             <input type="hidden" name="checkItemId" id="checkItemId" value="${checkPlan.checkItemId}">
							 <input type="hidden" name="checkItem" id="checkItem" value="${checkPlan.checkItem}">
						</td>
						<td>
							<label class=" control-label key"><span>*</span>计划巡检人员：</label>
						</td>
						<td>
							<input class="form-control val" name="workerName" id="workerName"  value="${checkPlan.workerName}" readonly/>
							<button type="button" class="btn btn-default" id="checkAddUserBtn">人员列表</button>
							<input type="hidden" name="workerId" id="workerIds"  value="${checkPlan.workerId}" >
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">实际开始时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control val" name="actualStartTime" value="${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}"  readonly/>
							<span name="actualStartTime"></span>
						</td>
						<td>
							<label class=" control-label key">实际完成时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control val" name="actualEndTime" value="${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}" id="actualEndTime"  readonly/>
							<span name="actualEndTime"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<select name="planStatus" class="form-control val" id="planStatus" readonly>
									 <option  <#if checkPlan.planStatus==0>selected</#if> value="1" >待处理</option>
									 <option  <#if checkPlan.planStatus==1>selected</#if> value="2" >处理中</option>
									 <option  <#if checkPlan.planStatus==2>selected</#if> value="3" >已完成</option>
									 <option  <#if checkPlan.planStatus==3>selected</#if> value="4" >已取消</option>
								</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>巡检结果：</label>
						</td>
						<td>
							<label class=" control-label val">
							   <a class="checkPlan-result-detail" data-url="/checkResult/toCheckResult.do?checkPlanNo=${checkPlan.checkPlanNo}">巡检结果</a>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>创建时间：</label>
						</td>
						<td>
						<label class="control-label val">
							${checkPlan.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">
							${checkPlan.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editCheckPlan" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditCheckPlan" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!--选择场站-->
<div class="modal fade" id="checkParkModal" tabindex="-1" role="dialog">
	<div class="modal-dialog">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择场站</div>
				</div>
		       <!--定义操作列按钮模板-->
		       <script id="checkParkBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body box-body-change-padding">
			         <table id="parkLists" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="parkToolss">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!--选择计划巡检绑定人员-->
<div class="modal fade" id="checkUserModal">
	<div class="modal-dialog">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择计划巡检绑定人员</div>
				</div>
		       <!--定义操作列按钮模板-->
		       <script id="checkWorkerBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body box-body-change-padding">
			         <table id="checkWorkerLists" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="checkWorkerTools">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkPlanEdit":"res/js/resource/checkPlan_edit"}});
		require(["checkPlanEdit"], function (checkPlanEdit){
			checkPlanEdit.init();
		});  
	});    
      $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: "linked",//今日按钮
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