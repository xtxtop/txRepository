<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargerViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">充电桩详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;编号：</label>
						</td>
						<td>
							<div class="col-sm-6 val">
							    ${charger.chargerNo}
							</div>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<div class="col-sm-6 val">
								${charger.cityName}
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;场站名称：</label>
						</td>
						<td>
							<div class="col-sm-6 val">
							    ${charger.parkName}
							</div>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;品牌：</label>
						</td>
						<td>
							<div class="col-sm-6 val">
								${charger.chargerBrandName}
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;型号</label>
						</td>
						<td>
							<div class="col-sm-6 val">
							   ${charger.chargerModelName}
							</div>
						</td>
								<td>
									<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;功率：</label>
								</td>
								<td>
									<div class="col-sm-6 val">
								${charger.chargerPower}W
								<#--<label class="control-label val"></label>-->
							</div>
								</td>
					</tr>
					<tr>
						<td>
								<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备串号：</label>
						</td>
						<td>
							<div class="col-sm-6 val">
								${charger.chargerSn}
							</div>
						</td>

						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp; 电桩类型：</label>
						</td>
						<td>
							<div class="col-sm-6 val">
								<#if charger.chargerType=1>
									快充
								<#else>
									慢充
								</#if> 
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;状态：</label>
						</td>
						<td>
							<div class="col-sm-4 val">
								<#if charger.isAvailable=1>
									启用
								<#else>
									停用
								</#if> 
							</div>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeCharger" class="btn-new-type-edit">
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
      require.config({paths: {"charger":"res/js/charger/charger_list"}});
		require(["charger"], function (charger){
			charger.init();
		});
		});
 </script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>