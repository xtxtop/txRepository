<meta charset="utf-8">
<style>
.daterangepicker .calendar-date {
	border: 1px solid #ddd;
	padding: 4px;
	border-radius: 4px;
	background: #fff;
	display: none;
}
.daterangepicker{
    width: 185px;
    }
   .ranges{
    width:167px;
   }
   .applyBtn {
    width: 54px;
   }
   .cancelBtn  {
    width: 54px;
   }
</style>
<div class="container-fluid backgroundColor">
	<form name="operatingCityAddForm">
	   <input type="hidden" name="cityName" id="add_operatingCity_city_name">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增运营城市</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
				    <tr>
				        <td><label class=" control-label key "
                            style="white-space: nowrap;"><span>*</span>运营城市：</label></td>
                        <td style="vertical-align: middle;">
                            <div style="width: 100%; display: inline-flex;">
                                <select class="col-sm-4" name="provinceId" id="provinceId_operatingCity"
                                    onchange="getResultCityAddoperatingCity(this.value)" onclick="formVerify('provinceId_add_peratingCity')"
                                    style="width: 90px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
                                    <option value="">请选择省份</option> <#list plist as province>
                                    <option
                                        value="${province.regionId}" > ${province.regionName}</option> </#list>
                                </select> <span id="itemcityAdd_operatingCity" style="display: none;">  <select
                                    class="col-sm-4" name="cityId_operatingCity" id="cityId_operatingCity" onchange="cityId_operatingCity_change()"
                                    style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
                                        <#list plists2 as pr>
                                        <option
                                            value="${pr.regionId}" > ${pr.regionName}</option> </#list>
                                </select> 
                            </div> <span id="provinceId_add_peratingCity_mlcx_verify"></span>
                        </td>
                        <td><label class=" control-label key"><span>*</span>坐标：</label>
                        </td>
                        <td><input style="width: 55px; height: 34px;" name="longitude" readonly="readonly"/> N <input
                            style="width: 55px; height: 34px;" name="latitude" readonly="readonly"/> E
                            <button type="button"  onclick="getLonAndLat()"
                                class="btn btn-default pull-right ">获取经纬度</button> 
                            <span id="longitude_add_operatingCity_mlcx_verify"></span></td>
				    </tr>
				
					<tr>
						<td><label class=" control-label key"><span>*</span>运营城市名称：</label>
						</td>
						<td><input class="form-control val" name="operatingCityName"
							maxlength="20" placeholder="请输入运营城市名称" readonly="readonly"/> 
						</td>
						<td><label class=" control-label key"><span>*</span>是否启用：</label>
                        </td>
                        <td><select name="enableStatus" class="form-control val">
                                <option value="1">启用</option>
                                <option value="0">停用</option>
                        </select> </td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addoperatingCity"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="addcloseoperatingCity" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"operatingCityAdd" : "res/js/ml/operatingCity_add"
			}
		});
		require([ "operatingCityAdd" ], function(operatingCityAdd) {
			operatingCityAdd.init();
		});
		$(".datetimepicker").datetimepicker({
			language : "zh-CN",
			autoclose : true,
			clearBtn : true, //清除按钮
			todayBtn : true,
			minuteStep : 5,
			format : "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
