<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="checkPlanAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">巡检计划新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>计划日期：</label>
						</td>
						<td>
							<input class="datepicker form-control val" name="planDate" readonly placeholder="请输入计划日期"/>
							<span name="planDate"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>计划巡检场站：</label>
						</td>
						<td>
								<input class="form-control val" name="parkName" id="parkNames" readonly placeholder="请输入计划巡检场站"/>
								<input type="hidden" name="parkNo" id="parkNos"/> 
							<button type="button" class="btn btn-default" id="checkAddParkBtn">场站列表</button>
							<span name="parkName"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>计划巡检人员：</label>
						</td>
						<td>
							<input class="form-control val" name="workerName" id="workerName" readonly placeholder="请输入计划巡检人员"/>
							<button type="button" class="btn btn-default" id="checkAddUserBtn">人员列表</button>
							<input type="hidden" name="workerId" id="workerId">
							<span name="workerName"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>计划巡检项：</label>
						</td>
						<td>
							<#list items as item>
							<label class="">
		                    <input type="checkbox" class="butcheck" value="${item.dataDictItemId}"><span>${item.itemValue}</span>
							</label>	
				            </#list> 
				            <span name="checkItemId"></span>
				             <input type="hidden" name="checkItemId" id="checkItemId">
							 <input type="hidden" name="checkItem" id="checkItem">
						</td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCheckPlan" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCheckPlan" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
				<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body box-body-change-padding">
					<table id="checkWorkerLists" class="table table-bordered table-hover" width="100%">
					</table>
				</div>
				<!-- /.box-body -->
				<div class="carRedPacketAddParkTool-bullet" id="checkWorkerTools">
				</div>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkPlanAdd":"res/js/resource/checkPlan_add"}});
		require(["checkPlanAdd"], function (checkPlanAdd){
			checkPlanAdd.init();
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
    });
</script>