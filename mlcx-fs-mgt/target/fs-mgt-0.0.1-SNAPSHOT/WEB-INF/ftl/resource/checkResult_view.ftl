<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">巡检结果详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检计划号：</label>
						</td>
						<td>
							<label class="control-label">
								${checkPlan.checkPlanNo}
								</label>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;计划日期：</label>
						</td>
						<td>
							<label class="control-label">
							${checkPlan.planDate?string('yyyy-MM-dd')}
							 </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检开始时间：</label>
						</td>
						<td>
							<label class="control-label">
								${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}
							  </label>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检完成时间：</label>
						</td>
						<td>
							<label class="control-label">
								${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}
							    </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检场地：</label>
						</td>
						<td>
							<label class="control-label">
								${checkPlan.parkName}
								</label>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检项目：</label>
						</td>
						
						<td>
							<label class="control-label">
								${checkPlan.checkItem}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;设备编号：</label>
						</td>
						<td>
							<label class="control-label">
								${checkResult.deviceNo}
								</label>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;结果：</label>
						</td>
						<td>
							<label class="control-label">
							<#if checkResult.checkResult==0>
							异常
							<#else>
							正常
							</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;异常简述：</label>
						</td>
						<td>
							<label class="control-label">
								${checkResult.abnormalBrief}
								</label>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;异常详述：</label>
						</td>
						<td>
							<label class="control-label">
								${checkResult.abnormalDetail}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;创建日期：</label>
						</td>
						<td>
							<label class="control-label">
								${checkResult.createTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;更新日期：</label>
						</td>
						<td>
							 <label class="control-label">
								${checkResult.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							 </label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeViewcheckResult" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
</div>


<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkResultEdit":"res/js/resource/checkResult_edit"}});
		require(["checkResultEdit"], function (checkResultEdit){
			checkResultEdit.init();
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