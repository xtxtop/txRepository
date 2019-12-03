<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkingLockAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增地锁</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>地锁名称：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingLockName" placeholder="请输入地锁名称"/>
						    <span id="parkingLockNameAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>地锁编码：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingLockCode" placeholder="请输入地锁编码"/>
						    <span id="parkingLockCodeAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>地锁序列号：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingLockSerialNumber" placeholder="请输入地锁序列号"/>
							<span id="parkingLockSerialNumberAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>产品类型：</label>
						</td>
						<td>
							<div class="col-sm-9 val">
								<input type="radio" name="parkingLockType"  value="0" checked="checked">网关版</input>
	                            <input type="radio" name="parkingLockType"  value="1" >UDP版</input>
								<span id="parkingLockTypeAdd"></span>
							</div>
						</td>
					</tr>
					<tr>
					·	<td>
							<label class=" control-label key"><span>*</span>使用类型：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="parkingLockUsertype"  value="0" checked="checked">停车</input>
	                            <input type="radio" name="parkingLockUsertype"  value="1" >充电</input>
								<span id="parkingLockUsertypeAdd"></span>
							</div>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="parkingLockStatus"  value="1" checked="checked">降下</input>
								<input type="radio" name="parkingLockStatus"  value="0" >升起</input>
								<span id="parkingLockStatusAdd"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>场站名称：</label>
						</td>
						<td>
							<select name="stationNo" id="stationNo" class="form-control val">
									<option value="">请选择</option>
								 <#list csList as pa>
									<option value="${pa.stationNo}">${pa.stationName}</option>
								 </#list>
							</select>
							<span id="stationNoAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电桩：</label>
						</td>
						<td>
							<select name="chargingPileNo" id="chargingPileNo" class="form-control val">
									<option value="">请选择</option>
							</select>
							<span id="chargingPileNoAdd"></span>
						</td>
					</tr>
					<tr>
					   <td>
                            <label class=" control-label key"><span>*</span>可用状态：</label>
                        </td>
                        <td>
                            <select name="activeCondition" id="activeCondition_add" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                            </select>
                            <span id="activeConditionAdd"></span>
                        </td>
						<td>
							<label class=" control-label key"><span>*</span>计费方案：</label>
						</td>
						<td>
							<select name="parkingLockChargingNo" class="form-control val">
								<option value="">请选择</option>
							 	<#list bsList as bs>
								<option value="${bs.lockSchemeNo}">${bs.lockSchemeName}</option>
							 	</#list>
							</select>
							<span id="parkingLockChargingNoAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveparkingLockAdd" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeparkingLockAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
			<script id="parkingLockAddBtnTpl" type="text/x-handlebars-template">
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
					<table id="parkingLockAddList"
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
				"parkingLockAdd" : "res/js/ml/parkingLock_add"
			}
		});
		require([ "parkingLockAdd" ], function(parkingLockAdd) {
			parkingLockAdd.init();
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