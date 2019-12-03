<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargingGunAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增充电枪</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>场站：</label>
						</td>
						<td>
							<input type="hidden" name="stationNo" value="">
							<select name="stationName" id="stationNoId" class="form-control val">
							<option value="">请选择</option>
						 		<#list stationList as pa>
						 		   <#if pa.isAvailable==1>
									<option value="${pa.stationNo}">${pa.stationName}</option> 
								    </#if>
								</#list>
							</select>
							<span id="stationNameAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电桩：</label>
						</td>
						<td>
							<select name="chargingPileNo" id="chargingPileId" class="form-control val">
								<option value="">请选择</option>
								<#list stationList as br>
									<option value="${br.chargingPileNo}">${br.chargingPileName}</option>
								</#list>
						</select>
                        <span id="chargingPileAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>工作状态：</label>
						</td>
						<td>
							<select name="workStatus" class="form-control val">
								<option value="">请选择</option>
								<option value="1">告警</option>
								<option value="2">待机</option>
								<option value="3">工作</option>
								<option value="4">离线</option>
								<option value="5">完成</option>
								<option value="6">正在操作充电桩</option>
								<option value="7">预约中</option>
						</select>
						<span id="workStatusAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电枪编码：</label>
						</td>
						<td>
							<input type="text" class="form-control val" maxlength="2" name="chargingGunCode" value="" placeholder="请输入充电枪编码"/>
							<span id="chargingGunCodeAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveChargerGun" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddChargerGun" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
				"chargerGunAdd" : "res/js/ml/chargerGun/chargerGun_add"
			}
		});
		require([ "chargerGunAdd" ], function(chargerGunAdd) {
			chargerGunAdd.init();
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