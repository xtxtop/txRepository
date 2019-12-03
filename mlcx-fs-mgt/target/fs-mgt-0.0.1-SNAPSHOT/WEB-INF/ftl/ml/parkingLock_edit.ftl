<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkingLockEditForm">
	<input type="hidden" name="parkingLockNo" value="${cp.parkingLockNo }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑地锁</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>地锁名称：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingLockName" value="${cp.parkingLockName }" placeholder="请输入地锁名称"/>
						    <span id="parkingLockNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>地锁编码：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingLockCode" value="${cp.parkingLockCode }" placeholder="请输入地锁编码"/>
						    <span id="parkingLockCodeEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>地锁序列号：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingLockSerialNumber" value="${cp.parkingLockSerialNumber }" placeholder="请输入地锁序列号"/>
							<span id="parkingLockSerialNumberEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>产品类型：</label>
						</td>
						<td>
							<div class="col-sm-9 val">
								<input type="radio" name="parkingLockType"  value="0" <#if cp.parkingLockType==0>checked="checked"</#if> >网关版</input>
	                            <input type="radio" name="parkingLockType"  value="1" <#if cp.parkingLockType==1>checked="checked"</#if> >UDP版</input>
								<span id="parkingLockTypeEdit"></span>
							</div>
						</td>
					</tr>
					<tr>
					·	<td>
							<label class=" control-label key"><span>*</span>使用类型：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="parkingLockUsertype"  value="0" <#if cp.parkingLockUsertype==0>checked="checked"</#if> >停车</input>
	                            <input type="radio" name="parkingLockUsertype"  value="1" <#if cp.parkingLockUsertype==1>checked="checked"</#if> >充电</input>
								<span id="parkingLockUsertypeEdit"></span>
							</div>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="parkingLockStatus"  value="1" <#if cp.parkingLockStatus==1>checked="checked"</#if> >降下</input>
								<input type="radio" name="parkingLockStatus"  value="0" <#if cp.parkingLockStatus==0>checked="checked"</#if> >升起</input>
								<span id="parkingLockStatusEdit"></span>
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
									<option value="${pa.stationNo}" <#if cp.stationNo==pa.stationNo>selected</#if> >${pa.stationName}</option>
								 </#list>
							</select>
							<span id="stationNoEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电桩：</label>
						</td>
						<td>
							<select name="chargingPileNo" id="chargingPileNo" class="form-control val">
									<option value="">请选择</option>
									<#if cp.chargingPileNo??>
		                                <option value="${cp.chargingPileNo}" selected="selected">${cp.chargingPileName}</option>
		                            </#if>
							</select>
							<span id="chargingPileNoEdit"></span>
						</td>
					</tr>
					<tr>
					   <td>
                            <label class=" control-label key"><span>*</span>可用状态：</label>
                        </td>
                        <td>
                            <select name="activeCondition" id="activeCondition_edit" class="form-control val">
                                    <option value="0" <#if cp.activeCondition==0>selected</#if>>启用</option>
                                    <option value="1" <#if cp.activeCondition==1>selected</#if>>停用</option>
                            </select>
                        </td>
						<td>
							<label class=" control-label key"><span>*</span>计费方案：</label>
						</td>
						<td>
							<select name="parkingLockChargingNo" class="form-control val">
								<option value="">请选择</option>
							 	<#list bsList as bs>
								<option value="${bs.lockSchemeNo}" <#if cp.parkingLockChargingNo==bs.lockSchemeNo>selected</#if> >${bs.lockSchemeName}</option>
							 	</#list>
							</select>
							<span id="parkingLockChargingNoEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveparkingLockEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeparkingLockEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
			<script id="parkingLockEditBtnTpl" type="text/x-handlebars-template">
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
					<table id="parkingLockEditList"
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
				"parkingLockEdit" : "res/js/ml/parkingLock_edit"
			}
		});
		require([ "parkingLockEdit" ], function(parkingLockEdit) {
			parkingLockEdit.init();
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