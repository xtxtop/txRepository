<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<div class="row">
		<div class="col-md-12">
			<div class="form-group compiletitle">
				<label class="col-sm-2 control-label"><h4>车辆详情</h4></label>
			</div>
		</div>
	</div>

	<div class="row hzlist">
		<div class="col-md-12 form-horizontal">
			<!-- tabs -->
			<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
				<!-- Nav tabs -->
				<ul id="carViewTabs" class="nav nav-tabs">
					<li class="active">
						<a href="#baseTab" aria-controls="#baseTab" role="tab" data-toggle="tab">基础信息</a>
					</li>
					<li>
						<a href="#operateAndStatusTab" aria-controls="#operateAndStatusTab" role="tab" data-toggle="tab">运营及状态</a>
					</li>
					<li>
						<a href="#insuranceTab" aria-controls="#insuranceTab" role="tab" data-toggle="tab">保险及车检</a>
					</li>
				</ul>
				<!-- Tab panes begin -->
				<div class="tab-content">
					<!-- 基本信息 -->
					<div class="tab-pane active" id="baseTab">
						<div class="col-md-12 form-horizontal">
							<table class="tab-table table table-border table-responsive">
								<thead class="tab-thead">
									<tr>
										<th colspan="4">基础信息</th>
									</tr>
								</thead>
								<tbody class="tab-tbody">

									<tr>
										<td><label class=" control-label key"><span></span>车牌：</label></td>
										<td>
											<label class="control-label val">
												${car.carPlateNo}
			       								<input type="hidden" name="viewCarPlateNo" id="viewCarPlateNo" value="${car.carPlateNo}">
												</label>
										</td>
										<td><label class=" control-label key"><span></span>品牌：</label></td>
										<td>
											<label class="control-label val">
												${car.carBrandName}
												</label>
										</td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>型号：</label>
										</td>
										<td><label class="control-label val">
												${car.carModelName}
												</label></td>
										<td><label class=" control-label key"><span></span>颜色：</label></td>
										<td><label class="control-label val">
												${car.carColor}
												</label></td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>座位数：</label></td>
										<td><label class="control-label val">
												${car.seaTing}
												</label></td>
										<td><label class=" control-label key"><span></span>发动机号：</label>
										</td>
										<td>
											<label class="control-label val">
												${car.engineNo}
												</label>
										</td>
									</tr>
									<tr class="add-car-last-tr">
										<td><label class=" control-label key"><span></span>车辆识别码：</label></td>
										<td><label class="control-label val">
												${car.carIdNo}
												</label></td>
										<td><label class=" control-label key"><span></span>车辆二维码：</label></td>
                                          <td class="odd-td">
														<div class="img-td-upload" style="background-image: url(${imagePath!''}/${car.qrcodePicUrl});"></div>
										  </td>
									</tr>
									<tr class="add-car-last-tr">
										<td><label class=" control-label key"><span></span>车辆照片：</label></td>
                                        <td class="odd-td">
														<div class="img-td-upload" style="background-image: url(${imagePath!''}/${car.carPhotoUrl1});"></div>
										  </td>
										<td><label class=" control-label key"><span></span>车辆证件照片：</label></td>
                                         <td class="odd-td">
														<div class="img-td-upload" style="background-image: url(${imagePath!''}/${car.carDocPhotoUrl1});"></div>
										  </td>
									</tr>
								</tbody>
							</table>

						</div>
					</div>
					<!-- 运营及状态 -->
					<div class="tab-pane" id="operateAndStatusTab">
						<div class="col-md-12 form-horizontal">
							
							
							<table class="tab-table table table-border table-responsive">
								<thead class="tab-thead">
									<tr>
										<th colspan="4">运营及状态</th>
									</tr>
								</thead>
								<tbody class="tab-tbody">

									<tr>
										<td><label class=" control-label key"><span></span>城市：</label></td>
										<td>
											<label class="control-label val">
												${car.cityName}
												</label>
										</td>
										<td><label class=" control-label key"><span></span>加盟商：</label></td>
										<td>
											<label class="control-label val">
												${franchisee.franchiseeName}
												</label>
										</td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>所属场站：</label>
										</td>
										<td><label class="control-label val">
												${park.parkName}
												</label></td>
										<td><label class=" control-label key"><span></span>使用状态：</label></td>
										<td><label class="control-label val">
						                            <#if carStatus.userageStatus==0>
						                               	 空闲
						                            </#if>
						                            <#if carStatus.userageStatus==1>
						                                                                                                                已预占
						                            </#if>
						                            <#if carStatus.userageStatus==2>
						                               	 订单中
						                            </#if>
						                            <#if carStatus.userageStatus==3>
						                               	调度中
						                            </#if>
												</label></td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>点/熄火状态：</label></td>
										<td><label class="control-label val">
						                            <#if carStatus.carStatus==1>
						                                                                                                                已启动
						                            </#if>
						                            <#if carStatus.carStatus==2>
						                               	 已熄火
						                            </#if>
												</label></td>
										<td><label class=" control-label key"><span></span>车辆类型：</label>
										</td>
										<td>
											<label class="control-label val">
						                            <#if carStatus.vehicleType==1>
						                                                 电量车
						                            </#if>
						                            <#if carStatus.vehicleType==2>
						                               	燃油车
						                            </#if>
												</label>
										</td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>最后点/熄火时间：</label></td>
										<td><label class="control-label val">
													<#if carStatus.csLastReportingTime??>
													${carStatus.csLastReportingTime?string('yyyy-MM-dd HH:mm:ss')}
													</#if>
												</label></td>
										<td><label class=" control-label key"><span></span>电/油量：</label></td>
										<td><label class="control-label val">
												${carStatus.power}
												</label></td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>电瓶电压：</label></td>
										<td><label class="control-label val">
												${carStatus.auxBatteryVoltage}
												</label></td>
										<td><label class=" control-label key"><span></span>续航里程：</label></td>
										<td><label class="control-label val">
												${carStatus.rangeMileage}
												</label></td>
									</tr>
									
									<tr>
										<td><label class=" control-label key"><span></span>行驶里程：</label></td>
										<td><label class="control-label val">
												${carStatus.mileage}
												</label></td>
										<td><label class=" control-label key"><span></span>上/下线状态：</label></td>
										<td><label class="control-label val">
						                            <#if car.onlineStatus==1>
						                                                                                                               上线
						                            </#if>
						                            <#if car.onlineStatus==0>
						                               	下线
						                            </#if>
												</label></td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>上/下线时间：</label></td>
										<td><label class="control-label val">
													<#if car.onlineStatusUpdateTime??>
													${car.onlineStatusUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
													</#if>
												</label></td>
										<td><label class=" control-label key"><span></span>上/下线操作人：</label></td>
										<td><label class="control-label val">
													${car.workerName}
												</label></td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>GPS：</label></td>
										<td><label class="control-label val">
													<span id="gpsAddress"></span>
												</label></td>
										<td><label class=" control-label key"><span></span>最后GPS上报时间：</label></td>
										<td><label class="control-label val">
													<#if carStatus.gpsLastReportingTime??>
													${carStatus.gpsLastReportingTime?string('yyyy-MM-dd HH:mm:ss')}
													</#if>
												</label></td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>终端编号：</label></td>
										<td><label class="control-label val">
													${carStatus.deviceNo}
												</label></td>
										<td><label class=" control-label key"><span></span>终端在线：</label></td>
										<td><label class="control-label val">
						                            <#if carStatus.isOnline==1>
														在线
													<#else>
						                            	不在线
													</#if>
												</label></td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>最后通信时间：</label></td>
										<td><label class="control-label val">
													<#if carStatus.gpsLastReportingTime??>
													${carStatus.gpsLastReportingTime?string('yyyy-MM-dd HH:mm:ss')}
													</#if>
												</label></td>
										<td></td>
										<td></td>
									</tr>
									
								</tbody>
							</table>
		
						</div>
					</div>
					<!-- 保险及车检 -->
					<div class="tab-pane" id="insuranceTab">
						<div class="col-md-12 form-horizontal">
						
						<table class="tab-table table table-border table-responsive">
								<thead class="tab-thead">
									<tr>
										<th colspan="4">保险及车检</th>
									</tr>
								</thead>
								<tbody class="tab-tbody">
									<tr>
										<td><label class=" control-label key"><span></span>保险公司：</label></td>
										<td>
											<label class="control-label val">
													${car.insuranceCompany}
												</label>
										</td>
										<td><label class=" control-label key"><span></span>投保日期：</label></td>
										<td>
											<label class="control-label val">
					                            	<#if car.enrollmentDate??>
					                                    ${car.enrollmentDate?string('yyyy-MM-dd')}
					                                </#if>
												</label>
										</td>
									</tr>
									<tr>
										<td><label class=" control-label key"><span></span>保险有效期：</label>
										</td>
										<td><label class="control-label val">
					                            	<#if car.effectiveInsurancePeriod??>
					                                    ${car.effectiveInsurancePeriod?string('yyyy-MM-dd')}
					                                </#if>
												</label></td>
										<td><label class=" control-label key"><span></span>检验有效期：</label></td>
										<td><label class="control-label val">
					                            	<#if car.validityDate??>
					                                    ${car.validityDate?string('yyyy-MM-dd')}
					                                </#if>
												</label></td>
									</tr>
								</tbody>
							</table>
						
						
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"car": "res/js/car/car_list"
			}
		});
		require(["car"], function(car) {
			car.init();
		});

		//通过baiduMap API获取街道名称
		var map = new BMap.Map("allmap");
		var point = new BMap.Point($ {
			dongitude
		}, $ {
			latitude
		});
		var gc = new BMap.Geocoder();
		gc.getLocation(point, function(rs) {
			var addComp = rs.addressComponents;
			address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
			$("#gpsAddress").text(address);
		});

	});
</script>