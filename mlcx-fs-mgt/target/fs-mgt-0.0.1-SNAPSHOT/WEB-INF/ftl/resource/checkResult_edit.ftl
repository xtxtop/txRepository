<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="checkResultEditForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">巡检结果编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检计划号：</label>
							<input type="hidden" name="checkResultId" value="${checkResult.checkResultId}"/>
						</td>
						<td>
							<input class=" form-control" name="checkPlanNo" value="${checkPlan.checkPlanNo}" readonly/>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;计划日期：</label>
						</td>
						<td>
							<input class="datepicker form-control" name="planDate" value="${checkPlan.planDate?string('yyyy-MM-dd')}" readonly/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检开始时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control" name="actualStartTime" value="${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}" readonly/>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检完成时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control" name="actualEndTime" value="${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}" readonly/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检场地：</label>
						</td>
						<td>
							<input class=" form-control" name="parkName" value="${checkPlan.parkName}" readonly/>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;巡检项目：</label>
						</td>
						<td>
							<input class=" form-control" name="checkItem" value="${checkPlan.checkItem}" readonly/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;设备编号：</label>
						</td>
						<td>
							<input class=" form-control" name="deviceNo" value="${checkResult.deviceNo}"/>
							<span name="deviceNo"></span>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;结果：</label>
						</td>
						<td>
							<select name="checkResult" class="form-control">
									 <option <#if checkResult.checkResult==0>selected</#if> value="0" >异常</option>
									 <option <#if checkResult.checkResult==1>selected</#if> value="1" >正常</option>
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;异常简述：</label>
						</td>
						<td>
							<input class=" form-control" name="abnormalBrief" value="${checkResult.abnormalBrief}"/>
							<span name="abnormalBrief"></span>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;异常详述：</label>
						</td>
						<td>
							<textarea class="form-control" name="abnormalDetail">${checkResult.abnormalDetail}</textarea>
							<span name="abnormalDetail"></span>
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
					<tr>
						<td colspan="2"><button type="button" id="editcheckResult" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditcheckResult" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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