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
	<form name="parkingAddForm">
		<div class="col-md-12">
			<div class="col-md-9" id="addParkingOne">
				<div class="row hzlist">
					<table class="table">
						<thead class="tab-thead">
							<tr>
								<th colspan="4">新增停车场</th>
							</tr>
						</thead>
						<tbody class="tab-tbody">
							<tr>
								<td><label class=" control-label key"><span>*</span>停车场名称：</label>
								</td>
								<td><input class="form-control val" name="parkingName"
									maxlength="20" placeholder="请输入停车场名称"
									onclick="formVerify('parkingName_add_parking')" /> <span
									id="parkingName_add_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key"><span>*</span>停车场类型：</label></td>
								<td><select class="form-control val" name="parkingType"
									onclick="formVerify('parkingType_add_parking')">
										<option value="">请选择</option>
										<option value="0">闸机</option>
										<option value="1">地锁</option>
										<option value="2">无设备</option>
								</select> <span id="parkingType_add_parking_mlcx_verify"></span></td>
							</tr>
							<tr>
								<td><label class=" control-label key"><span>*</span>运营城市：</label></td>
								<td><select class="form-control val" name="operatingCityNo"
									onclick="formVerify('operatingCityNo_add_parking')"
									id="operatingCityNo_add_parking"
									onchange="operatingCityNoChangeAdd_parking(this)">
										<option value="">请选择</option> <#list operatingCity as oc> <#if
										oc.enableStatus==1>
										<option value="${oc.operatingCityNo}">${oc.operatingCityName}</option>
										</#if> </#list>
								</select> <span id="operatingCityNo_add_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key "
									style="white-space: nowrap;"><span>*</span>所在地区：</label></td>
								<td style="vertical-align: middle;">
									<div style="width: 100%; display: inline-flex;">
										<span id="provinceId_parkingAdd"> <select
											class="col-sm-4" name="provinceId" id="provinceId_parking"
											onchange="getResultCityAddparking(this.value)"
											onclick="formVerify('provinceId_parking_add_parking')"
											style="width: 90px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
												<option value="">请选择省份</option> <#list plist as province>
												<option value="${province.regionId}">
													${province.regionName}</option> </#list>
										</select></span> <span id="itemcityAdd_parking"> <select
											class="col-sm-4" name="cityId" id="cityId_parking"
											style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;"
											onchange="getResultCountryAddparking(this.value)">
												<#list plists2 as pr>
												<option value="${pr.regionId}">${pr.regionName}</option>
												</#list>
										</select>
										</span> <span id="countrycityAdd_park"> <select
											class="col-sm-4" name="districtId" id="districtId_parking"
											style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
												<#list plists3 as pr>
												<option value="${pr.regionId}">${pr.regionName}</option>
												</#list>
										</select>
										</span>
									</div> <span id="provinceId_parking_add_parking_mlcx_verify"></span>
								</td>
							</tr>
							<tr>
								<td><label class=" control-label key"><span>*</span>坐标：</label>
								</td>
								<td><input type="hidden" name="ploygonPoints" />
									<div onclick="formVerify('longitude_add_parking')">
										经度：<input onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
											style="width: 73px; border-radius: 5px; height: 28px; border: 1px solid gray; outline: none;"
											name="longitude" /></br> 纬度：<input onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
											style="width: 73px; border-radius: 5px; height: 28px; border: 1px solid gray; outline: none;"
											name="latitude" />
										<!-- <button type="button" id="chargingSearchGC_parking"
											onclick="formVerify('longitude_add_parking')"
											class="btn btn-default pull-right ">地图选点</button> -->
									</div> <span id="longitude_add_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key"><span>*</span>详细街道：</label>
								</td>
								<td><input type="hidden" name="lngLat" /><input
									class="form-control val" name="addrStreet" id="addrStreet"
									placeholder="可通过地图选点获取地址"
									onclick="formVerify('addrStreet_add_parking')" /> <span
									id="addrStreet_add_parking_mlcx_verify"></span></td>
							</tr>
							<tr>
                                <td><label class=" control-label key">地下停车场层数：</label></td>
                                <td><input type="number" min="1" class="form-control val" 
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    onblur="undergroundNumberChangeAdd()"
                                    onclick="formVerify('undergroundNumber_add_parking')"
                                    name="undergroundNumber" placeholder="请输入层数"> <span
                                    id="undergroundNumber_add_parking_mlcx_verify"></span></td>
                            </tr>
                            <tr>
                                <td><label class=" control-label key">地下停车场总车位：</label></td>
                                <td><input type="number" min="1" class="form-control val"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')" valuwe
                                    onclick="formVerify('undergroundParkingSpaceNumber_add_parking')"
                                    onblur="undergroundNumberChangeAdd()" name="undergroundParkingSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="undergroundParkingSpaceNumber_add_parking_mlcx_verify"></span></td>
                                <td><label class=" control-label key">地下停车场剩余车位：</label></td>
                                <td><input type="number" min="1" class="form-control val"
                                    onclick="formVerify('undergroundSurplusSpaceNumber_add_parking')"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    onblur="undergroundNumberChangeAdd()" name="undergroundSurplusSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="undergroundSurplusSpaceNumber_add_parking_mlcx_verify"></span></td>
                            </tr>
                            <tr>
                              <td><label class=" control-label key">地面停车场总车位：</label></td>
                                <td>
                                <input type="number" min="1" class="form-control val"
                                    onclick="formVerify('groundParkingSpaceNumber_add_parking')"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    onblur="groundNumberChangeAdd()" name="groundParkingSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="groundParkingSpaceNumber_add_parking_mlcx_verify"></span></td>
                                <td><label class=" control-label key">地面停车场剩余车位：</label></td>
                                <td><input type="number" min="1" class="form-control val"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    onclick="formVerify('groundSurplusSpaceNumber_add_parking')"
                                    onblur="groundNumberChangeAdd()" name="groundSurplusSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="groundSurplusSpaceNumber_add_parking_mlcx_verify"></span></td>
                            </tr>
							<tr>
								<td><label class=" control-label key">楼层停车场层数：</label></td>
								<td><input type="number" min="1" class="form-control val" 
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									onblur="typeChangeAdd()"
									onclick="formVerify('pliesNumber_add_parking')"
									name="pliesNumber" placeholder="请输入层数"> <span
									id="pliesNumber_add_parking_mlcx_verify"></span></td>
							     <td><label class=" control-label key">多入口层数：</label></td>
							     <td><input type="text"  class="form-control val" 
                                    name="muchPliesNumber" onblur="typeChangeAdd()" placeholder="请输入多入口层数,并以','隔开">
                                    </td>
							</tr>
							<tr>
								<td><label class=" control-label key">楼层停车场总车位：</label></td>
								<td><input type="number" min="1" class="form-control val"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									onclick="formVerify('parkingSpaceNumber_add_parking')"
									onblur="typeChangeAdd()" name="parkingSpaceNumber"
									placeholder="请输入车位数"> <span
									id="parkingSpaceNumber_add_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key">楼层停车场剩余车位：</label></td>
								<td><input type="number" min="1" class="form-control val"
									onclick="formVerify('surplusSpaceNumber_add_parking')"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									onblur="typeChangeAdd()" name="surplusSpaceNumber"
									placeholder="请输入车位数"> <span
									id="surplusSpaceNumber_add_parking_mlcx_verify"></span></td>
							</tr>
							<tr>
								<td><label class=" control-label key">是否开放：</label></td>
								<td><label><input name="isPublic" type="radio"
										value="1" checked />是 </label> <label><input name="isPublic"
										type="radio" value="0" />否 </label></td>
								<td><label class=" control-label key"><span>*</span>在线状态：</label></td>
								<td><select class="form-control val" name="onlineStatus"
									onclick="formVerify('onlineStatus_add_parking')">
										<option value="0">在线</option>
										<option value="1">离线</option>
								</select> <span id="onlineStatus_add_parking_mlcx_verify"></span></td>
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
											<input type="text" class="jeinput" id="businessHoursTime"
												placeholder="请选择时间段" name="businessHours"
												value="00:00 至 23:59" readonly="readonly">
										</div>
									</div>
								</td>
							</tr>
							<tr onclick="formVerify('label_add_parking')">
								<td><label class=" control-label key"><span>*</span>标签：</label></td>
								<td><#list clabel as cl> <#if
									cl.enableStatus==1&&cl.labelType==2> <label class=""> <input
										type="checkbox" class="butcheck" name="label"
										value="${cl.labelId}">${cl.labelName}</label> </#if> </#list>
									<div>
										<span id="label_add_parking_mlcx_verify"></span>
									</div>
								</td>
								<td><label class=" control-label key"><span>*</span>计费规则：</label></td>
								<td class="btn-btnA-con"><select name="billingSchemeNo"
									class="form-control val" id="billingSchemeNo_parking"
									onclick="formVerify('billingSchemeNo_add_parking')">
										<option value="">请选择</option> <#list billingScheme as bill>
										<#if bill.status==1>
										<option value="${bill.parkBillingNo }">${bill.parkBillingName }</option>
										</#if>
										</#list>
								</select> <span id="billingSchemeNo_add_parking_mlcx_verify"></span></td>
							</tr>
							<tr onclick="formVerify('supportedServices_add_parking')">
								<td><label class=" control-label key"><span>*</span>周边配套：</label></td>
								<td><#list cmatching as cm> <#if cm.enableStatus==1&&
									cm.matchingType==2> <label class=""> <input
										type="checkbox" class="butcheck" name="supportedServices"
										value="${cm.matchingId}">${cm.matchingName}</label> </#if>
									</#list>
									<div>
										<span id="supportedServices_add_parking_mlcx_verify"></span>
									</div>
								</td>
								<td rowspan="2"><label class=" control-label key"><span>*</span>停车场图片[200px*140px]：</label>
									<input name="parkingUrl" type="hidden" /></td>
								<td class="odd-td">
									<div class="img-td-upload" id="parkingPicUrlImg">
										<div id="addparkingPhotoButton" class="add-img-position"
											onclick="formVerify('parkingUrl_add_parking')">
											<h3 class="icon">+</h3>
											<h3 class="text">添加图片</h3>
										</div>
									</div> <span id="parkingUrl_add_parking_mlcx_verify"></span>
								</td>
							</tr>
						</tbody>
						<tfoot class="tab-tfoot">
							<tr>
								<td colspan="2"><button type="button" id="addparking"
										class="btn-new-type-edit pull-right"
										style="margin-right: 10px !important">保存</button></td>
								<td colspan="2"><button type="button" id="addcloseparking"
										class="btn-new-type-edit pull-left"
										style="margin-left: 10px !important">关闭</button></td>

							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div class="col-md-3" id="addParkingTwo">
				<div class="row hzlist">
					<div style="height: 28px; text-align: center; padding-top: 3%;">
						地下停车场车位分布</div>
					<table class="table" style="margin-top: 5%;">
						<thead class="tab-thead">
							<tr style="width: 100%;">
								<th style="text-align: center; width: 30%;">层数</th>
								<th style="text-align: center; width: 35%;">总车位</th>
							</tr>
						</thead>
						<tbody class="" id="undergroundNumberAdd">
						</tbody>
					</table>
				</div>
				<div class="row hzlist">
                    <div style="height: 28px; text-align: center; padding-top: 3%;">
                        地面停车场车位分布</div>
                    <table class="table" style="margin-top: 5%;">
                        <thead class="tab-thead">
                            <tr style="width: 100%;">
                                <th style="text-align: center; width: 30%;">层数</th>
                                <th style="text-align: center; width: 35%;">总车位</th>
                            </tr>
                        </thead>
                        <tbody class="" id="groundNumberAdd">
                        </tbody>
                    </table>
                </div>
                    <div class="row hzlist">
                    <div style="height: 28px; text-align: center; padding-top: 3%;">
                       楼层停车场车位分布</div>
                    <table class="table" style="margin-top: 5%;">
                        <thead class="tab-thead">
                            <tr style="width: 100%;">
                                <th style="text-align: center; width: 30%;">层数</th>
                                <th style="text-align: center; width: 35%;">总车位</th>
                            </tr>
                        </thead>
                        <tbody class="" id="spaceNumAdd">
                        </tbody>
                    </table>
                </div>
			</div>
			<span id="plies_add_parking_mlcx_verify" style="display: none;"></span>
		</div>
	</form>
</div>
<!-- 弹出框-->
<div class="modal fade" id="parkingPhotoAddModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">停车场上传图片</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="parkingphotoForm">
					<div class="form-group">
						<label class="col-md-3 control-label val">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder=""
								name="parkingPicUrl" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label val">停车场图片：</label>
						<div class="col-md-8">
							<div id="parkingFineUploader">
								<span name="parkingErrorInfo"></span>
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="addparkingPhotoBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- 地图 -->
<div class="modal fade" id="storeAddMapModel_parking">
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
					获取坐标:&nbsp;<span id="coordinate_parking"></span>
				</h4>
				<h4 class="modal-title" id="myModalLabel">
					选择地址 :&nbsp; <span id="address_parking"></span>
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="storeAddMapForm">
					<div id="searchStoreMap_parking"
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
					"parkingAdd" : "res/js/mlpark/parking_add"
				}
			});
			require([ "parkingAdd" ], function(parkingAdd) {
				parkingAdd.init();
			});
			var config = new Object();
			config.uploadId = "parkingFineUploader";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			config.storePath = "park_photo";
			config.itemLimit = 1;
			config.allowedExtensions = [ "jpeg", "jpg", "gif", "png" ];
			config.sizeLimit = 500 * 1024;
			config.minSizeLimit = 10 * 1024;
			config.formName = "parkingphotoForm";
			//文件回显inputName
			config.inputName = "parkingPicUrl";
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
			jeDate("#businessHoursTime", {
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