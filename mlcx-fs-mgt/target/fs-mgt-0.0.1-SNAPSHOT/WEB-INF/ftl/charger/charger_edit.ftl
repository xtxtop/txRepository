<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="chargerEditForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">充电桩编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;编号：</label>
						</td>
						<td>
							<label class="control-label val">${charger.chargerNo}</label>
							<input type="hidden" name="chargerNo" value="${charger.chargerNo}"/>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option <#if charger.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;场站名称：</label>
						</td>
						<td>
							<select name="parkNo" class="form-control val">
						 <#list listParks as pa>
							<option <#if charger.parkNo==pa.parkNo>selected</#if> value="${pa.parkNo}">${pa.parkName}</option> </#list>
						</select>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;品牌：</label>
						</td>
						<td>
							<select name="chargerBrandName" class="form-control val">
								<#list chargerBrands as chargerBrand>
									<option value="${chargerBrand.itemValue}" <#if chargerBrand.itemValue=charger.chargerBrandName>selected="selected"</#if> >${chargerBrand.itemValue}</option>
								</#list>
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;型号</label>
						</td>
						<td>
							<select name="chargerModelName" class="form-control val">
								<#list chargerModels as chargerModel>
									<option value="${chargerModel.itemValue}" <#if chargerModel.itemValue=charger.chargerModelName>selected="selected"</#if> >${chargerModel.itemValue}</option>
								</#list>
								</select>
						</td>
								<td>
									<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;功率：</label>
								</td>
								<td>
									<input class="form-control val" name="chargerPower" value="${charger.chargerPower}"/><label style="position:absolute;right:25px;top:10px;">W</label>
										<span id="chargerPowerYzEd"></span>
								</td>
					</tr>
					<tr>
						<td>
								<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备串号：</label>
						</td>
						<td>
							<input class="form-control val" name="chargerSn" value="${charger.chargerSn}"/>
						<span id="chargerSnYzEd"></span>
						</td>

						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp; 电桩类型：</label>
						</td>
						<td>
							<select name="chargerType" class="form-control val">
									<option value="1" <#if charger.chargerType=1>selected="selected"</#if> >慢充</option>
									<option value="2" <#if charger.chargerType=2>selected="selected"</#if> >快充</option>
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;状态：</label>
						</td>
						<td>
							<select name="isAvailable" class="form-control val">
									<option value="0" <#if charger.isAvailable=0>selected="selected"</#if> >停用</option>
									<option value="1" <#if charger.isAvailable=1>selected="selected"</#if> >启用</option>
								</select>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">
							${charger.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							<label class="control-label val">
							${charger.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editCharger" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeChargerEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
      require.config({paths: {"chargerEdit":"res/js/charger/charger_edit"}});
		require(["chargerEdit"], function (chargerEdit){
			chargerEdit.init();
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