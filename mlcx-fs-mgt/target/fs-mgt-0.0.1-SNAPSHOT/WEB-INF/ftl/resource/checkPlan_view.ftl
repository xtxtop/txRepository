<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">巡检计划详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>编号：</label>
						</td>
						<td>
							<label class=" control-label val">
							   ${checkPlan.checkPlanNo}
							   </label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<label class=" control-label val">
				               ${checkPlan.cityName}
				               </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>计划日期：</label>
						</td>
						<td>
								 <label class=" control-label val">
								${checkPlan.planDate?string('yyyy-MM-dd')}
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>计划巡检场站：</label>
						</td>
						<td>
							<label class=" control-label val">
							${checkPlan.parkName}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>计划巡检项：</label>
						</td>
						<td>
							<label class=" control-label val">
							${checkPlan.checkItem}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>计划巡检人员：</label>
						</td>
						
						<td>
							<label class=" control-label val">
							${checkPlan.workerName}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>实际开始时间：</label>
						</td>
						<td>
							<label class=" control-label val">
							  ${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}
							  </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>实际完成时间：</label>
						</td>
						<td>
							<label class=" control-label val">
							    ${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}
							  </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>状态：</label>
						</td>
						<td>
							<label class=" control-label val">
									 <#if checkPlan.planStatus==1>待处理</#if>
									 <#if checkPlan.planStatus==2>处理中</#if> 
									 <#if checkPlan.planStatus==3>已完成</#if> 
									 <#if checkPlan.planStatus==4>已取消</#if>  
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>巡检结果：</label>
						</td>
						<td>
							<label class=" control-label val">
							        <a class="checkPlan-result-detail" data-url="/checkResult/toCheckResult.do?checkPlanNo=${checkPlan.checkPlanNo}">巡检结果</a>
							  </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							<label class=" control-label val">
							${checkPlan.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
							 <label class=" control-label val">
							${checkPlan.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closecheckPlan" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
</div>


<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkPlan":"res/js/resource/checkPlan_list"}});
		require(["checkPlan"], function (checkPlan){
			checkPlan.init();
		});  
	});    
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
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>