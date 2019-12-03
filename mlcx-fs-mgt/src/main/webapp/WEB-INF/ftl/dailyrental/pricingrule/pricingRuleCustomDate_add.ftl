<meta charset="utf-8">
<div class="container-fluid backgroundColor" id="scollOo">
	<form name="pricingRuleCustomDateAddForm">
		<input type="hidden" name="ruleId" value="${customizedDate.ruleId}">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增自定义计费</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label">名称：</label>
						</td>
						<td> 
						<label class="control-label">${customizedDate.ruleName}</label>
						</td>
						<td>
							<label class=" control-label">车辆型号：</label>
						</td>
						<td>
							 <label class="control-label">${customizedDate.carModelName}</label>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label"><span>*</span>自定义日期：</label>
						</td>
						<td>
							<input class="form-control datetimepicker" name="customizedDate" />
							<span name="customizedDateAdd"></span>
						</td>
						
						<td>
							<label class=" control-label"><span>*</span>自定义计费(元/天)：</label>
						</td>
						<td>
							<input class="form-control" name="priceOfDayCustomized" />
							<span name="priceOfDayCustomizedAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addPricingRuleCustomDate" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddPricingRuleCustomDate" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingRuleCustomDateAdd":"res/js/dailyrental/pricingrule/pricingRuleCustomDate_add"}});
		require(["pricingRuleCustomDateAdd"], function (pricingRuleCustomDateAdd){
			pricingRuleCustomDateAdd.init();
		});  
	});    
      $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
         $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            minView: "month",
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>