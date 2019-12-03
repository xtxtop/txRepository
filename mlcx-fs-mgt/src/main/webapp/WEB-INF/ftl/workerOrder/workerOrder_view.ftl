<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="itemForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">调度工单详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>工单编号：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.workerOrderNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>调度类型：</label>
						</td>
						<td>
							<label class="control-label val">  
								${workerOrder.type}
                				</label>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>起点：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.startParkName}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>终点：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.terminalParkName}</label>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>车牌号：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.carPlateNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>调度员：</label>
						</td>
						<td>
						    <label class="control-label val">${workerOrder.workerName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>下发时间：</label>
						</td>
						<td>
			<label class="control-label val">${workerOrder.sendTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>开始时间：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.startTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>完成时间：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.finishTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>审核状态：</label>
						</td>
						<td>
							<label class="control-label val">	<#if workerOrder.cencorStatus==0>
								    未审核
								<#elseif workerOrder.cencorStatus==1>
                				    已审核
                				<#elseif workerOrder.cencorStatus==2>
                				    未通过
                				</#if>
                				</label>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label reason key"><span>*</span>审核备注：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.cencorMemo}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>工单状态：</label>
						</td>
						<td>
							<label class="control-label val">  <#if workerOrder.workOrderStatus==0>
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
                				</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label reason key"><span>*</span>备注：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.memo}</label>
						</td>
						<td>
							<label class=" control-label reason key"><span>*</span>计划完成：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.planTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${workerOrder.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<#if workerOrder.sendReasonPicUrl1?? && workerOrder.sendReasonPicUrl1 != "">
						<td>
							<label class=" control-label key"><span></span>下发原因图片1：</label>
						</td>
						<td>
							<label class="control-label val">
		             				<img src="${imagePath!''}/${workerOrder.sendReasonPicUrl1}"  width="200"/>
		             			</label>
						</td>
						</#if>
		            	<#if workerOrder.sendReasonPicUrl2?? && workerOrder.sendReasonPicUrl2 != "">
						<td>
							<label class=" control-label key"><span></span>下发原因图片2：</label>
						</td>
						<td>
							<label class="control-label val">
		             				<img src="${imagePath!''}/${workerOrder.sendReasonPicUrl2}"  width="200"/>
		             			</label>
						</td>
						</#if>
					</tr>
					<tr>
		            	<#if workerOrder.sendReasonPicUrl3?? && workerOrder.sendReasonPicUrl3 != "">
						<td>
							<label class=" control-label key"><span></span>下发原因图片3：</label>
						</td>
						<td>
							<label class="control-label val">
		             				<img src="${imagePath!''}/${workerOrder.sendReasonPicUrl3}"  width="200"/>
		             			</label>
						</td>
						</#if>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeWorkerOrderView" class="btn-new-type-edit">
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
      require.config({paths: {"workerOrder":"res/js/workerOrder/workerOrder"}});
		require(["workerOrder"], function (workerOrder){
			workerOrder.init();
		});
		});
</script>