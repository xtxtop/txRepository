<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargingPileEditForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑充电桩</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>充电桩编码：</label>
						</td>
						<td>
							<input class="form-control val" name="chargingPileNo" value="${cp.chargingPileNo }"
							 onkeyup="this.value=this.value.replace(/[^\w\.\/]/ig,'')"
							 placeholder="请输入编码(长度为不超过16的非中文)" maxlength="16" readonly="readonly"/>
						    <span id="chargingPileNoEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>充电桩名称：</label>
						</td>
						<td>
							<input class="form-control val" name="chargingPileName" value="${cp.chargingPileName }" placeholder="请输入充电桩名称"/>
						    <span id="chargingPileNameEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>终端编码：</label>
						</td>
						<td>
							<input class="form-control val" name="terminalNo" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" value="${cp.terminalNo }" placeholder="请输入终端编码"/>
							<span id="terminalNoEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>所属场站：</label>
						</td>
						<td>
							<select name="stationNo" class="form-control val">
								<option value="">请选择</option>
							 <#list csList as pa>
							   <#if pa.isAvailable==1>
								<option value="${pa.stationNo}" <#if cp.stationNo==pa.stationNo>selected</#if> >${pa.stationName}</option>
							     </#if>
							 </#list>
							</select>
							<span id="stationNoEdit"></span>
						</td>
					</tr>
					<tr>
					·	<td>
							<label class=" control-label key"><span>*</span>电桩类型：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="electricityType"  value="0" <#if cp.electricityType==0>checked="checked"</#if> >慢充</input>
	                            <input type="radio" name="electricityType"  value="1" <#if cp.electricityType==1>checked="checked"</#if> >快充</input>
								<span id="electricityTypeEdit"></span>
							</div>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="status"  value="1" <#if cp.status==1>checked="checked"</#if> >有效</input>
								<input type="radio" name="status"  value="0" <#if cp.status==0>checked="checked"</#if> >无效</input>
								<span id="statusEdit"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否免费：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="ischarging"  value="1" <#if cp.ischarging==1>checked="checked"</#if> >否</input>
								<input type="radio" name="ischarging"  value="0" <#if cp.ischarging==0>checked="checked"</#if> >是</input>
								<span id="ischargingEdit"></span>
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
								<option value="${bs.schemeNo}" <#if cp.billingScheme==bs.schemeNo>selected</#if> >${bs.schemeName}</option>
							 	</#if>
							 	</#list>
							</select>
							<span id="billingSchemeEdit"></span>
						</td>
					</tr>

                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>功率范围：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="kw" value="${cp.kw}" placeholder="请输入范围，并拿“-”隔开"/>
                            <span id="kwNoAdd"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>输入电压范围：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="voltageIn" value="${cp.voltageIn}" placeholder="请输入范围，并拿“-”隔开"/>
                            <span id="voltageInAdd"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>输出电压范围：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="voltageOut" value="${cp.voltageOut}" placeholder="请输入范围，并拿“-”隔开"/>
                            <span id="voltageOutAdd"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>支持的国际标准：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="iso" value="${cp.iso}" placeholder="请输入国际标准，并拿“,”隔开"/>
                            <span id="isoAdd"></span>
                        </td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="savechargingPileEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closechargingPileEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
			<script id="chargingPileEditBtnTpl" type="text/x-handlebars-template">
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
					<table id="chargingPileEditList"
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
				"chargingPileEdit" : "res/js/ml/chargingPile_edit"
			}
		});
		require([ "chargingPileEdit" ], function(chargingPileEdit) {
			chargingPileEdit.init();
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