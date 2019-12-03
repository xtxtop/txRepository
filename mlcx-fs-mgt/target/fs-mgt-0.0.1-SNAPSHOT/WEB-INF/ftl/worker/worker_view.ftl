<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="itemForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">调度员详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<label class="control-label val">
							    	${worker.cityName}
							    </label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>片区：</label>
						</td>
						<td>
							 <label class="control-label val">
							    	${worker.regionName}
							    </label>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>工号：</label>
						</td>
						<td>
							<label class="control-label val">
							    	${worker.empNo}
							    </label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>姓名：</label>
						</td>
						<td>
							<label class="control-label val">
							    	${worker.workerName}
							    </label>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>手机：</label>
						</td>
						<td>
							<label class="control-label val">
								    ${worker.mobilePhone}
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>调度端App在线状态：</label>
						</td>
						<td>
							<label class="control-label val">
								   <#if worker.appIsOnline=1>
								             在线
								   <#else>
								             离线
								    </#if> 
								 </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>相关工单：</label>
						</td>
						<td>
							<label class="control-label val">
									<input id="workerNo" type="hidden" value="${worker.workerNo}"/>
								   <a href="javascript:void" id="workerOrderDetail">查看工单</a>
								 </label>
						</td>
						<td></td>
						<td></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeWorker" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
$(function(){
	  require.config({paths: {"worker":"res/js/worker/worker"}});
		require(["worker"], function (worker){
			worker.init();
		});  
	}); 
</script>