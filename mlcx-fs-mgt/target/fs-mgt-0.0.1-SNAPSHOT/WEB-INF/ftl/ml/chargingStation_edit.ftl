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
	<form name="chargingStationEditForm">
		<input type="hidden" name="stationNo"
			value="${ChargingStationVo.stationNo }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑充电站</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>充电站名称：</label>
						</td>
						<td><input class="form-control val" name="stationName"
							onclick="formVerify('stationName_edit_station')" maxlength="20"
							placeholder="请输入充电站名称" value="${ChargingStationVo.stationName }" />
							<span id="stationName_edit_station_mlcx_verify"></span></td>
						<td><label class=" control-label key">类型：</label></td>
						<td><select class="form-control val" name="stationType"
							onclick="formVerify('stationType_edit_station')">
								<option value="1"<#if ChargingStationVo.stationType==1>selected="selected"</#if>>精品站</option>
                                <option value="2" <#if ChargingStationVo.stationType==2>selected="selected"</#if>>超级站</option>
                                <option value="3" <#if ChargingStationVo.stationType==3>selected="selected"</#if>>普通站</option>
                             </select>
                              <span id="stationType_edit_station_mlcx_verify"></span></td>
					</tr>
					<tr>
					   <td><label class=" control-label key"><span>*</span>运营城市：</label></td>
                        <td><select class="form-control val" name="operatingCityNo"
                            onclick="formVerify('operatingCityNo_edit_station')"
                            id="operatingCityNo_edit_station"
                            onchange="operatingCityNoChangeEdit(this)">
                                <#list operatingCity as oc>
                                <#if oc.enableStatus==1>
                                <option value="${oc.operatingCityNo}" <#if oc.operatingCityNo==ChargingStationVo.operatingCityNo> selected</#if>>${oc.operatingCityName}</option>
                                </#if>
                                </#list>
                        </select> <span id="operatingCityNo_edit_station_mlcx_verify"></span></td>
						<td><label class=" control-label key "
							style="white-space: nowrap;"><span>*</span>所在地区：</label></td>
						<td style="vertical-align: middle;">
							<div style="width: 100%; display: inline-flex;" onclick="formVerify('provinceId_edit_station')">
							<span id="provinceIdEdits">
								<select class="col-sm-4" name="provinceId" id="provinceIdEdit" onclick="formVerify('provinceId_edit_station')"
									onchange="getResultCityEditchargingStation(this.value)"
									style="width: 90px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
									<option value="">请选择省份</option> <#list plist as province>
									<option
										value="${province.regionId}" <#if
                                        ChargingStationVo.provinceId==province.regionId>selected</#if>> ${province.regionName}</option> </#list>
								</select></span> <span id="itemcityEdit"> <#if
									ChargingStationVo.cityId!=0&&ChargingStationVo.cityId!=null><select
									class="col-sm-4" name="cityId" id="cityId_edit"
									style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;"
									onchange="getResultCountryEditchargingStation(this.value)">
										<#list plists2 as pr>
										<option
											value="${pr.regionId}" <#if
                                            ChargingStationVo.cityId==pr.regionId>selected</#if>> ${pr.regionName}</option> </#list>
								</select> </#if>
								</span> <span id="countrycityEdit">
								<#if plists3&&(plists3?size>0)>
								<#if ChargingStationVo.districtId!=0&&ChargingStationVo.districtId!=null> <select
									class="col-sm-4" name="districtId" id="districtIdEdit"
									style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
										<#list plists3 as pr>
										<option
											value="${pr.regionId}" <#if
                                            ChargingStationVo.districtId==pr.regionId>selected</#if>> ${pr.regionName}</option> </#list>
								</select> </#if></#if>
								</span>
							</div> <span id="provinceId_edit_station_mlcx_verify"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>坐标：</label>
						</td>
						<td><input type="hidden" name="ploygonPoints" /> <div onclick="formVerify('longitude_edit_station')"> 经度：<input onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
							style="width: 73px;border-radius:5px; height:28px;border:1px solid gray;outline: none;" class="" name="longitude"  value="${ChargingStationVo.longitude }"/></br> 纬度：<input
							onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
							style="width: 73px;border-radius:5px;height:28px;border:1px solid gray;outline: none;" class="" name="latitude"  value="${ChargingStationVo.latitude }"/> 
							<!-- <button type="button" id="chargingSearchGC_edit" onclick="formVerify('longitude_edit_station')"
								class="btn btn-default pull-right ">地图选点</button> --></div> <span
							id="longitude_edit_station_mlcx_verify"></span></td>
                        <td><label class=" control-label key"><span>*</span>详细街道：</label>
                        </td> 
                        <td><input type="hidden" name="lngLat" /><input class="form-control val" name="addrStreet" id="addrStreet" onclick="formVerify('addrStreet_edit_station')"
                            placeholder="可通过地图选点获取地址"  value="${ChargingStationVo.addrStreet }"/> 
                            <span id="addrStreet_edit_station_mlcx_verify"></span></td>
					</tr>
                    <tr>
                        <td><label class=" control-label key">节假日营业：</label>
                        </td>
                        <td><label><input name="isBusinessFestival"
                                type="radio" value="1" <#if ChargingStationVo.isBusinessFestival==1>checked </#if>/>是 </label> <label><input
                                name="isBusinessFestival" type="radio" value="0" <#if ChargingStationVo.isBusinessFestival==0>checked </#if>/>否 </label></td>
                        <td><label class=" control-label key">营业时间：</label>
                        </td>
                        <td class="btn-btnA-con">
                        <div class="jeitem">
                            <div class="jeinpbox"><input type="text" class="jeinput" id="editBusinessHours" placeholder="请选择时间段" name="businessHours" value="${ChargingStationVo.businessHours }"></div>
                        </div>
                        </td>
                    </tr>
                    <tr onclick="formVerify('label_edit_station')">
                       <td>
                       <input type="hidden" name="label_all" value="${ChargingStationVo.label }">
                       <label class=" control-label key"><span>*</span>标签：</label></td>
                        <td>
                        <#list clabel as cl>
                        <#if cl.enableStatus==1&&cl.labelType==1>
                           <label class=""> <input type="checkbox" class="butcheck"name="label"
                                value="${cl.labelId}">${cl.labelName}</label> 
                          </#if>
                        </#list>
                            <div>
                                <span id="label_edit_station_mlcx_verify"></span>
                            </div></td>
                         <td><label class=" control-label key"><span>*</span>充电站电费：</label></td>
                        <td class="btn-btnA-con"><input class="form-control val" onclick="formVerify('electricPrice_edit_station')" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            name="electricPrice"  placeholder="请输入场站电费" value="${ChargingStationVo.electricPrice }">
                            <span class="btn-btnA">元/度</span> <span id="electricPrice_edit_station_mlcx_verify"></span>
                        </td>
                    </tr>
					<tr onclick="formVerify('supportedServices_edit_station')">
					   <td>
					   <input type="hidden" name="supportedServices_all" value="${ChargingStationVo.supportedServices }">
					   <label class=" control-label key"><span>*</span>周边配套：</label>
					   </td>
                         <td>
                        <#list cmatching as cm>
                        <#if cm.enableStatus==1&& cm.matchingType==1>
                           <label class=""> <input type="checkbox" class="butcheck"name="supportedServices"
                                value="${cm.matchingId}">${cm.matchingName}</label> 
                        </#if>
                        </#list>
                        
                            <div>
                                <span id="supportedServices_edit_station_mlcx_verify"></span>
                            </div></td>
						<td ><label class=" control-label key"><span>*</span>充电站图片[320px*200px]：</label>
							<input name="stationUrl" type="hidden" value="${ChargingStationVo.stationUrl}" onclick="formVerify('stationUrl_edit_station')"/></td>
						<td class="odd-td" rowspan="2">
							<div class="img-td-upload" id="chargingStationPicUrlImg" style="background-image: url(${imagePath!''}/${ChargingStationVo.stationUrl});">
								<div id="EditchargingStationPhotoButton" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div> <span id="stationUrl_edit_station_mlcx_verify"></span>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button"
								id="EditchargingStation" class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="EditclosechargingStation"
								class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- 弹出框-->
<div class="modal fade" id="chargingStationPhotoEditModal">
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
				<form class="form-horizontal" name="chargingStationphotoFormEdit">
					<div class="form-group">
						<label class="col-md-3 control-label val">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder=""
								name="chargingStationPicUrlEdit" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label val">充电站图片：</label>
						<div class="col-md-8">
							<div id="chargingStationFineUploaderEdit">
								<span name="chargingStationErrorInfo"></span>
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="EditchargingStationPhotoBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- 地图 -->
<div class="modal fade" id="storeEditMapModel">
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
					获取坐标:&nbsp;<span id="coordinate_edit"></span>
				</h4>
				<h4 class="modal-title" id="myModalLabel">
					选择地址 :&nbsp; <span id="Editress"></span>
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="storeEditMapForm">
					<div id="searchStoreMapEdit"
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
					"chargingStationEdit" : "res/js/ml/chargingStation_edit"
				}
			});
			require([ "chargingStationEdit" ], function(chargingStationEdit) {
				chargingStationEdit.init();
			});
			var config = new Object();
			config.uploadId = "chargingStationFineUploaderEdit";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			config.storePath = "park_photo";
			config.itemLimit = 1;
			config.allowedExtensions = [ "jpeg", "jpg", "gif", "png" ];
			config.sizeLimit = 500 * 1024;
			config.minSizeLimit = 10 * 1024;
			config.formName = "chargingStationphotoFormEdit";
			//文件回显inputName
			config.inputName = "chargingStationPicUrlEdit";
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
			jeDate("#editBusinessHours", {
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