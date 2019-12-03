<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="checkResultAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">巡检结果新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>巡检计划号：</label>
						</td>
						<td>
							<input class=" form-control val" placeholder="请输入巡检计划号" name="checkPlanNo" id="checkPlanNos"/>
							<span name="checkPlanNo"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>计划日期：</label>
						</td>
						<td>
							<input class="datepicker form-control val" placeholder="请选择计划日期"  name="planDate" readonly style="background-color:#fff;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>巡检开始时间：</label>
						</td>
						<td>
								<input class="datetimepicker form-control val" placeholder="请选择计划开始时间"  name="actualStartTime" readonly/>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>巡检完成时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control val" placeholder="请选择巡检计划完成时间" name="actualEndTime" readonly />
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>巡检场地：</label>
						</td>
						<td>
							<input type="hidden" name="parkNo"/>
							<input class=" form-control val" placeholder="请输入巡检场地" name="parkName" readonly/>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>巡检项目：</label>
						</td>
						<td>
							<input type="hidden" name="checkItemId"/>
							<input class=" form-control val" placeholder="请输入巡检项目" name="checkItem" readonly/>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>设备编号：</label>
						</td>
						<td>
							<input class=" form-control val" name="deviceNo" placeholder="请输入设备编号"/>
							<span name="deviceNo"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>结果：</label>
						</td>
						<td>
							<select name="checkResult" class="form-control val">
									 <option  value="0" >异常</option>
									 <option  value="1" >正常</option>
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>异常简述：</label>
						</td>
						<td>
							<input class=" form-control val" name="abnormalBrief" placeholder="请填写异常简述"/>
							<span name="abnormalBrief"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>异常详述：</label>
						</td>
						<td>
							<textarea class="form-control val" name="abnormalDetail" placeholder="请填写异常详述"></textarea>
							<span name="abnormalDetail"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="savecheckResult" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddcheckResult" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"checkResultAdd":"res/js/resource/checkResult_add"}});
		require(["checkResultAdd"], function (checkResultAdd){
			checkResultAdd.init();
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