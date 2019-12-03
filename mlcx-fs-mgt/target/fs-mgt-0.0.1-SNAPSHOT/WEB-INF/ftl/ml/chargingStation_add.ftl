<meta charset="utf-8">
<style>
.daterangepicker .calendar-date {
	border: 1px solid #ddd;
	padding: 4px;
	border-radius: 4px;
	background: #fff;
	display: none;
}

.daterangepicker {
	width: 185px;
}

.ranges {
	width: 167px;
}

.applyBtn {
	width: 54px;
}

.cancelBtn {
	width: 54px;
}
</style>
<div class="container-fluid backgroundColor">
	<form name="chargingStationAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增充电站</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>充电站名称：</label>
						</td>
						<td><input class="form-control val" name="stationName"
							maxlength="20" placeholder="请输入充电站名称"
							onclick="formVerify('stationName_add_station')" /> <span
							id="stationName_add_station_mlcx_verify"></span></td>
						<td><label class=" control-label key">类型：</label></td>
						<td><select class="form-control val" name="stationType"
							onclick="formVerify('stationType_add_station')">
								<option value="3">普通站</option>
								<option value="1">精品站</option>
								<option value="2">超级站</option>
						</select> <span id="stationType_add_station_mlcx_verify"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>运营城市：</label></td>
						<td><select class="form-control val" name="operatingCityNo"
							onclick="formVerify('operatingCityNo_add_station')"
							id="operatingCityNo_add_station"
							onchange="operatingCityNoChangeAdd(this)">
								<option value="">请选择</option> <#list operatingCity as oc> <#if
								oc.enableStatus==1>
								<option value="${oc.operatingCityNo}">${oc.operatingCityName}</option>
								</#if> </#list>
						</select> <span id="operatingCityNo_add_station_mlcx_verify"></span></td>
						<td><label class=" control-label key "
							style="white-space: nowrap;"><span>*</span>所在地区：</label></td>
						<td style="vertical-align: middle;"
							onclick="formVerify('provinceId_add_station')">
							<div style="width: 100%; display: inline-flex;">
								<span id="provinceIdAdd"> <select class="col-sm-4"
									name="provinceId" id="provinceId"
									onchange="getResultCityAddchargingStation(this.value)"
									onclick="formVerify('provinceId_add_station')"
									style="width: 90px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
										<option value="">请选择省份</option> <#list plist as province>
										<option value="${province.regionId}">
											${province.regionName}</option> </#list>
								</select></span> <span id="itemcityAdd"> <select class="col-sm-4"
									name="cityId" id="cityId"
									style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;"
									onchange="getResultCountryAddchargingStation(this.value)">
										<#list plists2 as pr>
										<option value="${pr.regionId}">${pr.regionName}</option>
										</#list>
								</select>
								</span> <span id="countrycityAdd"> <select class="col-sm-4"
									name="districtId" id="districtId"
									style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
										<#list plists3 as pr>
										<option value="${pr.regionId}">${pr.regionName}</option>
										</#list>
								</select>
								</span>
							</div> <span id="provinceId_add_station_mlcx_verify"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>坐标：</label>
						</td>
						<td><input type="hidden" name="ploygonPoints" />
							<div onclick="formVerify('longitude_add_station')">
								经度：<input onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
									style="width: 73px; height: 28px; border-radius: 5px; border: 1px solid gray; outline: none;"
									class="" name="longitude" /></br> 纬度：<input onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
									style="width: 73px; height: 28px; border-radius: 5px; border: 1px solid gray; outline: none;"
									class="" name="latitude" />
								<!-- <button type="button" id="chargingSearchGC"
									onclick="formVerify('longitude_add_station')"
									class="btn btn-default pull-right ">地图选点</button> -->
							</div> <span id="longitude_add_station_mlcx_verify"></span></td>
						<td><label class=" control-label key"><span>*</span>详细街道：</label>
						</td>
						<td><input type="hidden" name="lngLat" /><input
							class="form-control val" name="addrStreet" id="addrStreet"
							placeholder="请输入详细地址"
							onclick="formVerify('addrStreet_add_station')" /> <span
							id="addrStreet_add_station_mlcx_verify"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key">节假日营业：</label></td>
						<td><label><input name="isBusinessFestival"
								type="radio" value="1" checked />是 </label> <label><input
								name="isBusinessFestival" type="radio" value="0" />否 </label></td>
						<td><label class=" control-label key">营业时间：</label></td>
						<td class="btn-btnA-con">
							<div class="jeitem">
								<div class="jeinpbox">
									<input type="text" class="jeinput" id="test10C"
										placeholder="请选择时间段" name="businessHours"
										value="00:00 至 23:59">
								</div>
							</div>
						</td>
					</tr>
					<tr onclick="formVerify('label_add_station')">
						<td><label class=" control-label key"><span>*</span>标签：</label></td>
						<td><#list clabel as cl> <#if
							cl.enableStatus==1&&cl.labelType==1> <label class=""> <input
								type="checkbox" class="butcheck" name="label"
								value="${cl.labelId}">${cl.labelName}</label> </#if> </#list>
							<div>
								<span id="label_add_station_mlcx_verify"></span>
							</div>
						</td>
						<td><label class=" control-label key"><span>*</span>充电站电费：</label></td>
						<td class="btn-btnA-con"><input class="form-control val"
							name="electricPrice" placeholder="请输入场站电费"
							onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
							onclick="formVerify('electricPrice_add_station')"> <span
							class="btn-btnA">元/度</span> <span
							id="electricPrice_add_station_mlcx_verify"></span></td>
					</tr>
					<tr onclick="formVerify('supportedServices_add_station')">
						<td><label class=" control-label key"><span>*</span>周边配套：</label></td>
						<td><#list cmatching as cm> <#if cm.enableStatus==1&&
							cm.matchingType==1> <label class=""> <input
								type="checkbox" class="butcheck" name="supportedServices"
								value="${cm.matchingId}">${cm.matchingName}</label> </#if>
							</#list>
							<div>
								<span id="supportedServices_add_station_mlcx_verify"></span>
							</div>
						</td>
						<td rowspan="2"><label class=" control-label key"><span>*</span>充电站图片[320px*200px]：</label>
							<input name="stationUrl" type="hidden" /></td>
						<td class="odd-td">
							<div class="img-td-upload" id="chargingStationPicUrlImg">
								<div id="addchargingStationPhotoButton" class="add-img-position"
									onclick="formVerify('stationUrl_add_station')">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div> <span id="stationUrl_add_station_mlcx_verify"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addchargingStation"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="addclosechargingStation" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- 弹出框-->
<div class="modal fade" id="chargingStationPhotoAddModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">充电站上传图片</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="chargingStationphotoForm">
					<div class="form-group">
						<label class="col-md-3 control-label val">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder=""
								name="chargingStationPicUrl" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label val">充电站图片：</label>
						<div class="col-md-8">
							<div id="chargingStationFineUploader">
								<span name="chargingStationErrorInfo"></span>
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="addchargingStationPhotoBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- 地图 -->
<div class="modal fade" id="storeAddMapModel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div style="float: right; color: blue; margin: 20px;">
				<button type="button" class="btn-new-type" data-dismiss="modal">确定
				</button>
			</div>
			<div class="modal-header">

				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					获取坐标:&nbsp;<span id="coordinate"></span>
				</h4>
				<h4 class="modal-title" id="myModalLabel">
					选择地址 :&nbsp; <span id="address"></span>
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="storeAddMapForm">
					<div id="searchStoreMap"
						style="width: 1024px; height: 500px; overflow: hidden;"></div>

				</form>

			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
	<!-- /.modal -->
	<script type="text/javascript">
		$(function() {
			require.config({
				paths : {
					"chargingStationAdd" : "res/js/ml/chargingStation_add"
				}
			});
			require([ "chargingStationAdd" ], function(chargingStationAdd) {
				chargingStationAdd.init();
			});
			var config = new Object();
			config.uploadId = "chargingStationFineUploader";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			config.storePath = "park_photo";
			config.itemLimit = 1;
			config.allowedExtensions = [ "jpeg", "jpg", "gif", "png" ];
			config.sizeLimit = 500 * 1024;
			config.minSizeLimit = 10 * 1024;
			config.formName = "chargingStationphotoForm";
			//文件回显inputName
			config.inputName = "chargingStationPicUrl";
			//错误提示span标签name
			config.spanName = "parkErrorInfo";
			require.config({
				paths : {
					"upload" : "res/js/resource/uploadFile"
				}
			});
			require([ "upload" ], function(upload) {
				upload.init(config);
			});
			$(".datetimepicker").datetimepicker({
				language : "zh-CN",
				autoclose : true,
				clearBtn : true, //清除按钮
				todayBtn : true,
				minuteStep : 5,
				format : "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
			});
			$('.business_hours').daterangepicker({
				timePicker : true,
				format : 'HH:mm',
			});
			jeDate("#test10C", {
				format : "hh:mm",
				multiPane : false,
				range : " 至 "
			});
		});
	</script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript"
		src="res/dep/baidu-DrawingManager/DrawingManager_min.js"></script>
	<link rel="stylesheet"
		href="res/dep/baidu-DrawingManager/DrawingManager_min.css" />