<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargingPileAddForm">
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
							<label class=" control-label key"><span>*</span>充电桩编码：</label>
						</td>
						<td>
							<input class="form-control val" name="chargingPileNo" 
							 onkeyup="this.value=this.value.replace(/[^\w\.\/]/ig,'')"
							 maxlength="16" placeholder="请输入编码(长度为不超过16的非中文)"/>
						    <span id="chargingPileNoAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电桩名称：</label>
						</td>
						<td>
							<input class="form-control val" name="chargingPileName" placeholder="请输入充电桩名称"/>
						    <span id="chargingPileNameAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>终端编码：</label>
						</td>
						<td>
							<input class="form-control val" name="terminalNo" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" placeholder="请输入终端编码"/>
							<span id="terminalNoAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>场站名称：</label>
						</td>
						<td>
							<select name="stationNo" class="form-control val">
								<option value="">请选择</option>
							 <#list csList as pa>
							     <#if pa.isAvailable==1>
								<option value="${pa.stationNo}">${pa.stationName}</option>
								</#if>
							 </#list>
							</select>
							<span id="stationNoAdd"></span>
						</td>
					</tr>
					<tr>
					·	<td>
							<label class=" control-label key"><span>*</span>电桩类型：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="electricityType"  value="0" checked="checked">慢充</input>
	                            <input type="radio" name="electricityType"  value="1" >快充</input>
								<span id="electricityTypeAdd"></span>
							</div>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="status"  value="1" checked="checked">有效</input>
								<input type="radio" name="status"  value="0" >无效</input>
								<span id="statusAdd"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否免费：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="ischarging"  value="1" checked="checked">否</input>
								<input type="radio" name="ischarging"  value="0" >是</input>
								<span id="ischargingAdd"></span>
							</div>
						</td>
						<td>
							<label class=" control-label key">计费方案：</label>
						</td>
						<td>
							<select name="billingScheme" class="form-control val">
								<option value="">请选择</option>
							 	<#list bsList as bs>
							 	<#if bs.status==1>
								<option value="${bs.schemeNo}">${bs.schemeName}</option>
							 	</#if>
							 	</#list>
							</select>
							<span id="billingSchemeAdd"></span>
						</td>
					</tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>功率范围：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="kw" placeholder="请输入范围，并拿“-”隔开"/>
                            <span id="kwNoAdd"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>输入电压范围：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="voltageIn" placeholder="请输入范围，并拿“-”隔开"/>
                            <span id="voltageInAdd"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>输出电压范围：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="voltageOut" placeholder="请输入范围，并拿“-”隔开"/>
                            <span id="voltageOutAdd"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>支持的国际标准：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="iso" placeholder="请输入国际标准，并拿“,”隔开"/>
                            <span id="isoAdd"></span>
                        </td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="savechargingPileAdd" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closechargingPileAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
			<script id="chargingPileAddBtnTpl" type="text/x-handlebars-template">
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
					<table id="chargingPileAddList"
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
				"chargingPileAdd" : "res/js/ml/chargingPile_add"
			}
		});
		require([ "chargingPileAdd" ], function(chargingPileAdd) {
			chargingPileAdd.init();
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