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
	<form name="parkingEditForm">
		<div class="col-md-12">
			<div class="col-md-9" id="editParkingOne">
				<input type="hidden" name="parkingNo"
					value="${parkingVo.parkingNo }">
				<div class="row hzlist">
					<table class="table">
						<thead class="tab-thead">
							<tr>
								<th colspan="4">编辑停车场</th>
							</tr>
						</thead>
						<tbody class="tab-tbody">
							<tr>
								<td><label class=" control-label key"><span>*</span>停车场名称：</label>
								</td>
								<td><input class="form-control val" name="parkingName"
									onclick="formVerify('parkingName_edit_parking')" maxlength="20"
									placeholder="请输入停车场名称" value="${parkingVo.parkingName }" /> <span
									id="parkingName_edit_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key">停车场类型：</label></td>
								<td><select class="form-control val" name="parkingType"
									onclick="formVerify('parkingType_edit_parking')">
										<option value="1"<#if
											parkingVo.parkingType==1>selected="selected"</#if>>地锁</option>
										<option value="2"<#if
											parkingVo.parkingType==2>selected="selected"</#if>>无设备</option>
										<option value="0"<#if
											parkingVo.parkingType==0>selected="selected"</#if>>闸机</option>
								</select> <span id="parkingType_edit_parking_mlcx_verify"></span></td>
							</tr>
							<tr>
								<td><label class=" control-label key"><span>*</span>运营城市：</label></td>
								<td><select class="form-control val" name="operatingCityNo"
									onclick="formVerify('operatingCityNo_edit_parking')"
									id="operatingCityNo_edit_parking"
									onchange="operatingCityNoChangeEdit_parking(this)">
										<#list operatingCity as oc> <#if oc.enableStatus==1>
										<option value="${oc.operatingCityNo}"<#if
											oc.operatingCityNo==parkingVo.operatingCityNo>
											selected</#if>>${oc.operatingCityName}</option> </#if> </#list>
								</select> <span id="operatingCityNo_edit_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key "
									style="white-space: nowrap;"><span>*</span>所在地区：</label></td>
								<td style="vertical-align: middle;">
									<div style="width: 100%; display: inline-flex;">
										<span id="provinceIdEdits_parking"> <select
											class="col-sm-4" name="provinceId"
											id="provinceIdEdit_parking"
											onclick="formVerify('provinceId_edit_parking')"
											onchange="getResultCityEditparking_parking(this.value)"
											style="width: 90px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
												<option value="">请选择省份</option> <#list plist as province>
												<option value="${province.regionId}"<#if
													parkingVo.provinceId==province.regionId>selected</#if>>
													${province.regionName}</option> </#list>
										</select></span> <span id="itemcityEdit_parking"> <#if
											parkingVo.cityId!=0&&parkingVo.cityId!=null><select
											class="col-sm-4" name="cityId" id="cityId_edit_parking"
											style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;"
											onchange="getResultCountryEditparking_parking(this.value)">
												<#list plists2 as pr>
												<option value="${pr.regionId}"<#if
													parkingVo.cityId==pr.regionId>selected</#if>>
													${pr.regionName}</option> </#list>
										</select> </#if>
										</span> <span id="countrycityEdit_parking"> <#if plists3&&(plists3?size>0)>
											 <#if parkingVo.districtId!=0&&parkingVo.districtId!=null> <select
											class="col-sm-4" name="districtId"
											id="districtIdEdit_parking"
											style="width: 70px; height: 34px; border: 1px solid #ccc; margin-right: 5px; padding-right: 0px; padding-left: 0px;">
												<#list plists3 as pr>
												<option value="${pr.regionId}"<#if
													parkingVo.districtId==pr.regionId>selected</#if>>
													${pr.regionName}</option> </#list>
										</select> </#if></#if>
										</span>
									</div> <span id="provinceId_edit_parking_mlcx_verify"></span>
								</td>
							</tr>
							<tr>
								<td><label class=" control-label key"><span>*</span>坐标：</label>
								</td>
								<td><input type="hidden" name="ploygonPoints" />
									<div onclick="formVerify('longitude_edit_parking')">
										经度：<input
											style="width: 73px; border-radius: 5px; height: 28px; border: 1px solid gray; outline: none;"
											name="longitude" value="${parkingVo.longitude }" /></br>纬度：<input
											style="width: 73px; border-radius: 5px; height: 28px; border: 1px solid gray; outline: none;"
											name="latitude" value="${parkingVo.latitude }" />
										<!--  <button type="button" id="chargingSearchGC_edit_parking" onclick="formVerify('longitude_edit_parking')"
                                class="btn btn-default pull-right ">地图选点</button> -->
									</div> <span id="longitude_edit_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key"><span>*</span>详细街道：</label>
								</td>
								<td><input type="hidden" name="lngLat" /><input
									class="form-control val" name="addrStreet" id="addrStreet"
									onclick="formVerify('addrStreet_edit_parking')"
									placeholder="可通过地图选点获取地址" value="${parkingVo.addrStreet }" /> <span
									id="addrStreet_edit_parking_mlcx_verify"></span></td>
							</tr>
							<tr>
                                <td><label class=" control-label key">地下停车场层数：</label></td>
                                <td><input type="number" min="1" class="form-control val clickButton" 
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    onblur="undergroundNumberChangeEdit()" value="${parkingVo.undergroundNumber }"
                                    onclick="formVerify('undergroundNumber_edit_parking')" readonly="readonly"
                                    name="undergroundNumber" placeholder="请输入层数"><span
                                    id="undergroundNumber_edit_parking_mlcx_verify"></span></td>
                                <td> <button id="button" class="btn btn-warning pull-left" type="button" style="margin-left: 10px !important" onclick="clickButton()">修改</button></td>
                            </tr>
                            <tr>
                                <td><label class=" control-label key">地下停车场总车位：</label></td>
                                <td><input type="number" min="1" class="form-control val clickButton"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"value="${parkingVo.undergroundParkingSpaceNumber }"
                                    onclick="formVerify('undergroundParkingSpaceNumber_edit_parking')" readonly="readonly"
                                    onblur="undergroundNumberChangeEdit()" name="undergroundParkingSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="undergroundParkingSpaceNumber_edit_parking_mlcx_verify"></span></td>
                                <td><label class=" control-label key">地下停车场剩余车位：</label></td>
                                <td><input type="number" min="1" class="form-control val clickButton"value="${parkingVo.undergroundSurplusSpaceNumber }"
                                    onclick="formVerify('undergroundSurplusSpaceNumber_edit_parking')"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')" readonly="readonly"
                                    onblur="undergroundNumberChangeEdit()" name="undergroundSurplusSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="undergroundSurplusSpaceNumber_edit_parking_mlcx_verify"></span></td>
                            </tr>
                            <tr>
                              <td><label class=" control-label key">地面停车场总车位：</label></td>
                                <td>
                                <input type="number" min="1" class="form-control val clickButton" value="${parkingVo.groundParkingSpaceNumber }"
                                    onclick="formVerify('groundParkingSpaceNumber_edit_parking')"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')" readonly="readonly"
                                    onblur="groundNumberChangeEdit()" name="groundParkingSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="groundParkingSpaceNumber_edit_parking_mlcx_verify"></span></td>
                                <td><label class=" control-label key">地面停车场剩余车位：</label></td>
                                <td><input type="number" min="1" class="form-control val clickButton" readonly="readonly"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"value="${parkingVo.groundSurplusSpaceNumber }"
                                    onclick="formVerify('groundSurplusSpaceNumber_edit_parking')"
                                    onblur="groundNumberChangeEdit()" name="groundSurplusSpaceNumber"
                                    placeholder="请输入车位数"> <span
                                    id="groundSurplusSpaceNumber_edit_parking_mlcx_verify"></span></td>
                            </tr>
							<tr>
								<td><label class=" control-label key"><span>*</span>楼层停车场层数：</label></td>
								<td><input type="number" min="1" class="form-control val clickButton"
									onblur="typeChangeEdit()"
									onclick="formVerify('pliesNumber_edit_parking')"
									name="pliesNumber"
									onkeyup="this.value=this.value.replace(/\D/g,'')" readonly="readonly"
									value="${parkingVo.pliesNumber }" placeholder="请输入层数">
									<span id="pliesNumber_edit_parking_mlcx_verify"></span></td>
							   <td><label class=" control-label key">多入口层数：</label></td>
                                 <td><input type="text"  class="form-control val clickButton" value="${parkingVo.muchPliesNumber }""
                                    name="muchPliesNumber" onblur="typeChangeEdit()" readonly="readonly" placeholder="请输入多入口层数,并以','隔开">
                                    </td>
							</tr>
							<tr>
								<td><label class=" control-label key"><span>*</span>楼层停车场总车位：</label></td>
								<td><input type="number" min="1" class="form-control val clickButton"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									onblur="typeChangeEdit()" readonly="readonly"
									onclick="formVerify('parkingSpaceNumber_edit_parking')"
									value="${parkingVo.parkingSpaceNumber }"
									name="parkingSpaceNumber" placeholder="请输入车位数"> <span
									id="parkingSpaceNumber_edit_parking_mlcx_verify"></span></td>
								<td><label class=" control-label key"><span>*</span>楼层停车场剩余车位：</label></td>
								<td><input type="number" min="1" class="form-control val clickButton"
									onkeyup="this.value=this.value.replace(/\D/g,'')" readonly="readonly"
									onblur="typeChangeEdit()"
									onclick="formVerify('surplusSpaceNumber_edit_parking')"
									value="${parkingVo.surplusSpaceNumber }"
									name="surplusSpaceNumber" placeholder="请输入车位数"> <span
									id="surplusSpaceNumber_edit_parking_mlcx_verify"></span></td>
							</tr>
							<tr>
								<td><label class=" control-label key">是否开放：</label></td>
								<td><label><input name="isPublic" type="radio"
										value="1"<#if parkingVo.isPublic==1>checked </#if>
										/>是 </label> <label><input name="isPublic" type="radio"
										value="0"<#if parkingVo.isPublic==0>checked
										</#if>/>否 </label></td>
								<td><label class=" control-label key"><span>*</span>在线状态：</label></td>
								<td><select class="form-control val" name="onlineStatus"
									onclick="formVerify('onlineStatus_edit_parking')">
										<option value="0"<#if
											parkingVo.onlineStatus==0>selected="selected"</#if>>在线</option>
										<option value="1"<#if
											parkingVo.onlineStatus==1>selected="selected"</#if>>离线</option>
								</select> <span id="onlineStatus_edit_parking_mlcx_verify"></span></td>
							</tr>
							<tr>
								<td><label class=" control-label key">节假日营业：</label></td>
								<td><label><input name="isBusinessFestival"
										type="radio" value="1"<#if
										parkingVo.isBusinessFestival==1>checked </#if>/>是 </label> <label><input
										name="isBusinessFestival" type="radio" value="0"<#if
										parkingVo.isBusinessFestival==0>checked </#if>/>否 </label></td>
								<td><label class=" control-label key">营业时间：</label></td>
								<td class="btn-btnA-con">
									<div class="jeitem">
										<div class="jeinpbox">
											<input type="text" class="jeinput"
												id="editBusinessHours_parking" placeholder="请选择时间段"
												name="businessHours" value="${parkingVo.businessHours }">
										</div>
									</div>
								</td>
							</tr>
							<tr onclick="formVerify('label_edit_parking')">
								<td><input type="hidden" name="label_all"
									value="${parkingVo.label }"> <label
									class=" control-label key"><span>*</span>标签：</label></td>
								<td><#list clabel as cl> <#if
									cl.enableStatus==1&&cl.labelType==2> <label class=""> <input
										type="checkbox" class="butcheck" name="label"
										value="${cl.labelId}">${cl.labelName}</label> </#if> </#list>
									<div>
										<span id="label_edit_parking_mlcx_verify"></span>
									</div>
								</td>
								<td><label class=" control-label key"><span>*</span>计费规则：</label></td>
								<td class="btn-btnA-con"><select name="billingSchemeNo"
									class="form-control val" id="billingSchemeNo_parking"
									onclick="formVerify('billingSchemeNo_edit_parking')">
										<option value="">请选择</option> <#list billingScheme as bill>
										<option value="${bill.parkBillingNo }"<#if
											parkingVo.billingSchemeNo==bill.parkBillingNo>selected
											</#if>>${bill.parkBillingName }</option> </#list>
								</select> <span id="billingSchemeNo_edit_parking_mlcx_verify"></span></td>
							</tr>
							<tr onclick="formVerify('supportedServices_edit_parking')">
								<td><input type="hidden" name="supportedServices_all"
									value="${parkingVo.supportedServices }"> <label
									class=" control-label key"><span>*</span>周边配套：</label></td>
								<td><#list cmatching as cm> <#if cm.enableStatus==1&&
									cm.matchingType==2> <label class=""> <input
										type="checkbox" class="butcheck" name="supportedServices"
										value="${cm.matchingId}">${cm.matchingName}</label> </#if>
									</#list>

									<div>
										<span id="supportedServices_edit_parking_mlcx_verify"></span>
									</div>
								</td>
								<td><label class=" control-label key"><span>*</span>停车场图片[200px*140px]：</label>
									<input name="parkingUrl" type="hidden"
									value="${parkingVo.fileUrl}"
									onclick="formVerify('fileUrl_edit_parking')" /></td>
								<td class="odd-td" rowspan="2">
									<div class="img-td-upload" id="parkingPicUrlImg_parking"
										style="background-image: url(${imagePath!''}/${parkingVo.fileUrl});">
										<div id="EditparkingPhotoButton_parking"
											class="add-img-position">
											<h3 class="icon">+</h3>
											<h3 class="text">添加图片</h3>
										</div>
									</div> <span id="fileUrl_edit_parking_mlcx_verify"></span>
								</td>
							</tr>

						</tbody>
						<tfoot class="tab-tfoot">
							<tr>
								<td colspan="2"><button type="button" id="Editparking"
										class="btn-new-type-edit pull-right"
										style="margin-right: 10px !important">保存</button></td>
								<td colspan="2"><button type="button" id="Editcloseparking"
										class="btn-new-type-edit pull-left"
										style="margin-left: 10px !important">关闭</button></td>

							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div class="col-md-3" id="editParkingTwo">
			<div class="row hzlist">
                    <div style="height: 28px; text-align: center; padding-top: 3%;">
                                                                                         地下停车场车位分布</div>
                    <table class="table" style="margin-top: 5%;">
                        <thead class="tab-thead">
                            <tr style="width: 100%;">
                                <th style="text-align: center; width: 30%;">层数</th>
                                <th style="text-align: center; width: 35%;">总车位</th>
                                <th style="text-align: center; width: 35%;">剩余车位</th>
                            </tr>
                        </thead>
                        <tbody class="" id="under_spaceNumEdit">
                            <#list pliesNumberList as pn>
                            <#if pn.spaceType==1>
                            <tr>
                                <td style="width: 24%; text-align: center;"><label>
                                <input type="text" class="form-control val " id="under_editSpace_${pn_index+1}" onblur="under_blurSpace(this)" value="${pn.pliesNumber }"></label>
                                    <input type="hidden" name="under_spaceNumber"
                                    id="under_parking_surplus_${pn_index+1}"
                                    value="${pn.parkingPliesNo },${pn.pliesNumber },${pn.parkingSpaceNumber},${pn.surplusSpaceNumber}">
                                </td>
                                <td><input type="number" min="1" id="under_parking_${pn_index+1}"
                                    onblur="under_blurSpace(this)"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    class="form-control val under_pliesNumbers"
                                    name="under_parkingSpaceNumbers" placeholder="请输入车位数"
                                    value='${pn.parkingSpaceNumber }'></td>
                                <td><input type="number" min="1"
                                    max='${pn.parkingSpaceNumber }' id="under_surplus_${pn_index+1}"
                                    onblur="under_blurSpace(this)"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    class="form-control val under_pliesNumber" name="under_surplusSpaceNumbers"
                                    placeholder="请输入车位数" value='${pn.surplusSpaceNumber }'></td>
                            </tr>
                            </#if>
                            </#list>
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
                                <th style="text-align: center; width: 35%;">剩余车位</th>
                            </tr>
                        </thead>
                        <tbody class="" id="ground_spaceNumEdit">
                            <#list pliesNumberList as pn>
                            <#if pn.spaceType==2>
                            <tr>
                                <td style="width: 24%; text-align: center;"><label>
                                <input type="text" class="form-control val " id="ground_editSpace_${pn_index+1}" onblur="ground_blurSpace(this)" value="${pn.pliesNumber }"></label>
                                    <input type="hidden" name="ground_spaceNumber"
                                    id="ground_parking_surplus_${pn_index+1}"
                                    value="${pn.parkingPliesNo },${pn.pliesNumber },${pn.parkingSpaceNumber},${pn.surplusSpaceNumber}">
                                </td>
                                <td><input type="number" min="1" id="ground_parking_${pn_index+1}"
                                    onblur="ground_blurSpace(this)"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    class="form-control val ground_pliesNumbers"
                                    name="ground_parkingSpaceNumbers" placeholder="请输入车位数"
                                    value='${pn.parkingSpaceNumber }'></td>
                                <td><input type="number" min="1"
                                    max='${pn.parkingSpaceNumber }' id="ground_surplus_${pn_index+1}"
                                    onblur="ground_blurSpace(this)"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')"
                                    class="form-control val ground_pliesNumber" name="ground_surplusSpaceNumbers"
                                    placeholder="请输入车位数" value='${pn.surplusSpaceNumber }'></td>
                            </tr>
                            </#if>
                            </#list>
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
								<th style="text-align: center; width: 35%;">剩余车位</th>
							</tr>
						</thead>
						<tbody class="" id="spaceNumEdit">
							<#list pliesNumberList as pn>
							<#if pn.spaceType==3>
							<tr>
								<td style="width: 24%; text-align: center;"><label>
								<input type="text" class="form-control val " id="editSpace_${pn_index+1}" onblur="blurSpace(this)" value="${pn.pliesNumber }"></label>
									<input type="hidden" name="spaceNumber"
									id="parking_surplus_${pn_index+1}"
									value="${pn.parkingPliesNo },${pn.pliesNumber },${pn.parkingSpaceNumber},${pn.surplusSpaceNumber}">
								</td>
								<td><input type="number" min="1" id="parking_${pn_index+1}"
									onblur="blurSpace(this)"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									class="form-control val pliesNumbers"
									name="parkingSpaceNumbers" placeholder="请输入车位数"
									value='${pn.parkingSpaceNumber }'></td>
								<td><input type="number" min="1"
									max='${pn.parkingSpaceNumber }' id="surplus_${pn_index+1}"
									onblur="blurSpace(this)"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									class="form-control val pliesNumber" name="surplusSpaceNumbers"
									placeholder="请输入车位数" value='${pn.surplusSpaceNumber }'></td>
							</tr>
							</#if>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
			<span id="plies_edit_parking_mlcx_verify" style="display: none;"></span>
		</div>
</div>
</form>
</div>
<!-- 弹出框-->
<div class="modal fade" id="parkingPhotoEditModal">
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
				<form class="form-horizontal" name="parkingphotoFormEdit">
					<div class="form-group">
						<label class="col-md-3 control-label val">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder=""
								name="parkingPicUrlEdit" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label val">停车场图片：</label>
						<div class="col-md-8">
							<div id="parkingFineUploaderEdit">
								<span name="parkingErrorInfo"></span>
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="EditparkingPhotoBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- 地图 -->
<div class="modal fade" id="storeEditMapModel_parking">
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
					获取坐标:&nbsp;<span id="coordinate_edit_parking"></span>
				</h4>
				<h4 class="modal-title" id="myModalLabel">
					选择地址 :&nbsp; <span id="Editress_parking"></span>
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="storeEditMapForm">
					<div id="searchStoreMapEdit_parking"
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
					"parkingEdit" : "res/js/mlpark/parking_edit"
				}
			});
			require([ "parkingEdit" ], function(parkingEdit) {
				parkingEdit.init();
			});
			var config = new Object();
			config.uploadId = "parkingFineUploaderEdit";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			config.storePath = "park_photo";
			config.itemLimit = 1;
			config.allowedExtensions = [ "jpeg", "jpg", "gif", "png" ];
			config.sizeLimit = 500 * 1024;
			config.minSizeLimit = 10 * 1024;
			config.formName = "parkingphotoFormEdit";
			//文件回显inputName
			config.inputName = "parkingPicUrlEdit";
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
			jeDate("#editBusinessHours_parking", {
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