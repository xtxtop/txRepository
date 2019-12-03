<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="smsTemplateAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">短信模板新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label for="inputEmail3" class=" control-label key">短信模板类型：</label></td>
						<td>
							 <select class="form-control val" id="CustomeremplateType" name="templateType">
                                  <option value="1">注册</option>
                                  <option value="2">修改密码</option>
                                  <option value="3">车辆信息异常</option>
                                  <option value="4">会员审核</option>
                                  <option value="5">运行日报</option>
                                  <option value="6">退款通知</option>
								</select>  
							<span name="templateTypeAdd"></span>
						</td>
						<td>
						    <label for="inputEmail3" class=" control-label key">短信内容：</label>	
						</td>
						<td>
							<textarea class="form-control val" rows="6" id="CustomeremplateContent" name="templateContent"></textarea>
						    <span name="templateContentAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addSmsTemplate" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeSmsTemplateAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"smsTemplateAdd":"res/js/system/smsTemplate_add"}});
		require(["smsTemplateAdd"], function (smsTemplateAdd){
			smsTemplateAdd.init();
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