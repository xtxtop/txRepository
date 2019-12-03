<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargerAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增充电桩</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<input type="hidden" name="cityName" id="cityName" value="" />
						<select name="cityId" id="cityId" class="form-control val">
							<option value="">请选择</option> <#list cities as citie>
							<option value="${citie.dataDictItemId}">${citie.itemValue}</option>
							</#list>
						</select>
						<span id="cityNameYz"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>场站名称：</label>
						</td>
						<td>
							<select name="parkNo" id="parkNode" class="form-control val">
						<option value="">请选择</option>
						 <#list parkList as pa>
							<option value="${pa.parkNo}">${pa.parkName}</option> </#list>
						</select>
						<span id="parkNameYz"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>品牌：</label>
						</td>
						<td>
							<select name="chargerBrandName" class="form-control val">
						<option value="">请选择</option>
							<#list brandList as br>
							<option value="${br.itemValue}">${br.itemValue}</option>
							</#list>
						</select>
                        <span id="chargerBrandNameYz"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>型号：</label>
						</td>
						<td>
							<select name="chargerModelName" class="form-control val">
						<option value="">请选择</option>
							<#list modelList as mo>
							<option value="${mo.itemValue}">${mo.itemValue}</option> </#list>
						</select>
						<span id="chargerModelNameYz"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>功率：</label>
						</td>
						<td>
							<input class="form-control val" name="chargerPower" placeholder="请输入功率大小（W）"/>
							<label style="position:absolute;right:25px;top:0;">W</label>
						    <span id="chargerPowerYz"></span>
						</td>
								<td>
									<label class=" control-label key"><span>*</span>设备串号：</label>
								</td>
								<td>
									<input class="form-control val" name="chargerSn" placeholder="请输入设备串号"/>
									<span id="chargerSnYz"></span>
								</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>电桩类型：</label>
						</td>
						<td>
							<select name="chargerType" class="form-control val">
						    <option value="">请选择</option>
							<option value="1">慢充</option>
							<option value="2">快充</option>
						</select>
						<span id="chargerTypeYz"></span>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<select name="isAvailable" class="form-control val">
							<option value="">请选择</option>
							<option value="0">不可用</option>
							<option value="1">可用</option>
						</select>
						<span id="isAvailableYz"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCharger" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCharger" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- /.modal -->


<div class="modal fade" id="checkUserModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<!--定义操作列按钮模板-->
			<script id="checkWorkerBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
			<div class="box">

				<div class="box-header">
					<!-- <h3 class="box-title">数据列</h3> -->
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="checkWorkerLists"
						class="table table-bordered table-striped table-hover"
						width="100%">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"chargerAdd" : "res/js/charger/charger_add"
			}
		});
		require([ "chargerAdd" ], function(chargerAdd) {
			chargerAdd.init();
		});
	});
	$(function() {
		$(".datepicker").datepicker({
			language : "zh-CN",
			autoclose : true,//选中之后自动隐藏日期选择框
			clearBtn : true,//清除按钮
			todayBtn : "linked",//今日按钮
			format : "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>