<meta charset="utf-8">
<style>
	#deviceToolssss button {
		margin: 20px 20px;
	}
	
	#deviceCarList tbody input[type="checkbox"] {
		border-color: #3f9fff;
	}
</style>
<div class="container-fluid-910 backgroundColor">
	<form name="carEditForm">
		<input type="hidden" name="carNo" value="${car.carNo}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车辆编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="control-label key"><span>*</span>车牌号：</label>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo" value="${car.carPlateNo}" readonly />
							<span name="carPlateNo"></span>
						</td>
						<!--<td> 
							<label class="control-label key"><span>*</span>租赁类型：</label>
						</td>
						<td>
							<select name="leaseType" class="form-control val">
								<option value="">请选择</option>
								<option value="1" <#if car.leaseType=1>selected="selected"</#if> >分时</option>
								<option value="2" <#if car.leaseType=2>selected="selected"</#if> >长租</option>
							</select>
						</td>-->
						<td>
							<input type="hidden" name="deviceId" id="deviceId" value="${car.deviceId}" />
							<label class="control-label key"><span>*</span>终端序列号：</label>
						</td>
						<td>
							<input class="form-control val" name="deviceSn" id="deviceSn" value="${car.deviceSn}" readonly/>
							<span name="deviceId"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class="control-label key"><span>*</span>运营城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								<#list cities as citie>
									<option <#if car.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" > ${citie.itemValue}
									</option>
								</#list>
							</select>
						</td>
						<td><label class="control-label key">所在场站：</label></td>
						<td>
							<select name="locationParkNo" class="form-control val">
								<#list parks as parks>
									<option <#if park.parkNo==parks.parkNo> selected </#if> value="${parks.parkNo}" > ${parks.parkName}
									</option>
								</#list>
							</select>
							<span name="locationParkNo">
						</td>
						
					</tr>
					<tr>
					
						<td><label class="control-label key"><span>*</span>品牌：</label>
						</td>
						<td>
							<select name="carBrandId" class="form-control val">
								<#list carBrands as brand>
									<option <#if car.carBrandId==brand.carBrandId>selected</#if> value="${brand.carBrandId}" > ${brand.carBrandName}
									</option>
								</#list>
							</select>
						</td>
						<td>
							<label class="control-label key"><span>*</span>车型系列：</label>
						</td>
						<td>
							<select name="carModelId" class="form-control val" id="carModelIdEdit">
								<#list carmodels as cmodel>
									<option <#if car.carModelId==cmodel.carSeriesId>selected</#if> value="${cmodel.carSeriesId}" > ${cmodel.carSeriesName}
									</option>
								</#list>
							</select>
						</td>

					</tr>
					<tr>

						<td>
							<label class="control-label key"><span>*</span>座位数：</label>
						</td>
						<td class="seatEdit">
							<input class="form-control val" readonly="readonly" name="seaTing" value="${car.seaTing}" /><span name="seaTing"></span>
						</td>
						<td>
							<label class="control-label key"><span>*</span>动力类型：</label>
						</td>
						<td>
							<select name="vehicleType" class="form-control val">
								<option value="1" <#if carStatus.vehicleType=1>selected="selected"</#if> >电量</option>
								<option value="2" <#if carStatus.vehicleType=2>selected="selected"</#if> >燃油</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label key"><span>*</span>车辆颜色：</label>
						</td>
						<td>
							<select name="carColor" class="form-control val">
								<#list carcolors as carcolor>
									<option <#if car.carColor==carcolor.itemValue>selected</#if> value="${carcolor.itemValue}" > ${carcolor.itemValue}
									</option>
								</#list>
							</select>
						</td>
						<td>
							<label class="control-label key">加盟商：</label>
						</td>
						<td>
							<input type="hidden" id="franchiseeNo" name="franchiseeNo" value="" />
							<input class="form-control val" name="franchiseeName" value="${franchisee.franchiseeName}" readonly="readonly" />
						</td>
					</tr>

					<tr>
						<td><label class="control-label key"><span>*</span>上线状态：</label>
						</td>
						<td> <label class="control-label val" readonly="readonly">
                                        <#if car.onlineStatus==1>
                                          已上线
                            <#else>
                                          未上线
                                        </#if>
                                     </lable></td>
 	<td><label class="control-label key"><#-- <span>*</span>  -->上线时间：</label></td>
						<td> <label class="control-label val" readonly="readonly">
                                          <#if car.onlineStatusUpdateTime?exists>
                                              ${car.onlineStatusUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
                                          </#if>
                                          </lable></td>
 </tr>
 <tr>
 	<td><label class="control-label key"><#-- <span>*</span>  -->使用状态：</label></td>
						<td><label class="control-label val" readonly="readonly">
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
                                                </lable></td>
 	<td><label class="control-label key"><#-- <span>*</span>  -->使用状态更新时间：</label></td>
						<td> <label class="control-label val" readonly="readonly">
                                                <#if car.usedStatusUpdateTime ?exists>
                                                    ${car.usedStatusUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
                                                </#if>
                                            </lable></td>
 </tr>
  <tr>
 	<td> <label class="control-label key"><#-- <span>*</span>  -->电/油量（%）：</label></td>
						<td><label class="control-label val" readonly="readonly">
                                         <#if carStatus.power??>
                                                              ${carStatus.power}%
                                                      </#if>
                                                  </lable></td>
 	<td><label class="control-label key"><#-- <span>*</span>  -->可续航里程（Km）：</label></td>
						<td><label class="control-label  val" readonly="readonly">
                                                       <#if carStatus.rangeMileage?? >
                                                            ${carStatus.rangeMileage} Km
                                                        </#if>
                                                      </label></td>
					</tr>
					<tr>
						<td><label class="control-label key"><#-- <span>*</span>  -->行驶里程（Km）：</label></td>
						<td><label class="control-label val" readonly="readonly">
                                                  <#if carStatus.mileage?? >
                                                          ${carStatus.mileage} Km
                                                      </#if>
                                                  </lable></td>
 	                <td>
							<label class="control-label key"><span></span>车辆识别码：</label>
						</td>
						<td>
							<input class="form-control val" name="carIdNo" value="${car.carIdNo}" />
							<span name="carIdNo"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class="control-label key"><span></span>发动机号：</label>
						</td>
						<td>
							<input class="form-control val" name="engineNo" value="${car.engineNo}" />
							<span name="engineNo"></span>
						</td>
						<td><label class="control-label key"><span></span>保险公司：</label>
						</td>
						<td>
							<select name="insuranceCompany" class="form-control val">
								<option value="">请选择</option>
								<option value="1" <#if car.insuranceCompany=1>selected="selected"</#if> >太平洋保险</option>
								<option value="2" <#if car.insuranceCompany=2>selected="selected"</#if> >平安保险</option>
							</select>
						</td>
					</tr>
					<tr>

						<td>
							<label class="control-label key"><span></span>投保日期：</label>
						</td>
						<td>
							<input class="datepicker form-control val" name="enrollmentDate" value="${car.enrollmentDate?string('yyyy-MM-dd')}" />
						</td>
						<td>
							<label class="control-label key"><span></span>检验有效期：</label>
						</td>
						<td>
							<input class="datepicker form-control val" name="validityDate" value="${car.validityDate?string('yyyy-MM-dd')}" />
							<span name="validityDate">
						</td>
					</tr>
					
					<tr>
						
						<td><label class="control-label key"><span>*</span>车主：</label>
						</td>
						<td>
							<select name="carOwnerId" class="form-control val">
								<#list carOwnerList as carOwner>
									<option <#if car.carOwnerId==carOwner.ownerId>selected</#if> value="${carOwner.ownerId}"> ${carOwner.ownerName}
									</option>
								</#list>
							</select>
							<span name="carOwnerId"></span>
						</td>
						<td>
							<label class=" control-label key"><span></span>有效保险期：</label>
						</td>
						<td>
							<input class="datepicker form-control" name="effectiveInsurancePeriod" value="${car.effectiveInsurancePeriod?string('yyyy-MM-dd')}" />
							<span name="effectiveInsurancePeriod"></span>
						</td>
					</tr>
					<tr>

						<td><label class="control-label key"><#-- <span>*</span>  -->绑定终端：</label></td>
						<td>
							<#if car.deviceSn!="" &&car.deviceSn??>
								<input type="button" class="carOnBtn editCarDevice btn btn-default" data-id="${car.carNo}" value="解绑终端">
								<#else>
									<input type="button" class="carOnBtn editCarDevice btn btn-default" data-id="${car.carNo}" value="绑定终端">
							</#if>
						</td>
						<td><label class="control-label key"><#-- <span>*</span>  -->终端状态：</label></td>
						<td><label class="control-label val">
                                                          <#if car.deviceStatus==1>
                                                              在线
                                                          </#if>
                                                          <#if car.deviceStatus==2>
                                                              节能
                                                          </#if>
                                                          <#if car.deviceStatus==3>
                                                              待机
                                                          </#if>
                                                          <#if car.deviceStatus==4>
                                                              离线
                                                          </#if>
                                                          <lable></td>
					</tr>
 
					
					<tr class="add-car-last-tr">
						<td class="photo-line-height">
							<label class="control-label key"><span></span>车辆照片：</label>
						</td>
						<td class="odd-td">
							<input name="carPhotoUrl1" type="hidden" value="${car.carPhotoUrl1}" />
							<div class="img-td-upload" style="background-image: url(${imagePath!''}/${car.carPhotoUrl1});" id="carPicUrlImg">
								<div id="editCarPhoto" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div>
						</td>
						<td class="photo-line-height">
							<label class=" control-label key"><span></span>车辆证件照片：</label>
						</td>
						<td class="odd-td">
							<input name="carDocPhotoUrl1" type="hidden" value="${car.carDocPhotoUrl1}" />
							<div class="img-td-upload" style="background-image: url(${imagePath!''}/${car.carDocPhotoUrl1});" id="carzjPicUrlImg">
								<div id="editzjCarPhoto" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div>
						</td>

					</tr>
					<tr>
						<td>
							<label class="control-label key"><#-- <span>*</span>  -->创建日期：</label>
						</td>
						<td>
							<label class="control-label val" readonly="readonly">
                                                  ${car.createTime?string('yyyy-MM-dd HH:mm:ss')}
                                                  </label>
						</td>
						<td>
							<label class="control-label key"><#-- <span>*</span>  -->更新日期：</label>
						</td>
						<td>
							<label class="control-label val" readonly="readonly">
                                                  ${car.updateTime?string('yyyy-MM-dd HH:mm:ss')}
                                                  </label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCar" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCarB" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<div class="modal" id="deviceCarModal">
	<div class="modal-dialog" style="width:750px;">
		<div class="modal-content">
			<div class="with-border">
				<div class="title-new-details">选择终端</div>
			</div>
			<div class="">
				<!--定义操作列按钮模板-->
				<script id="deviceCarBtnTpl" type="text/x-handlebars-template">
					{{#each func}}
					<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
				</script>
				<div class="box">
					<div class="box-body box-body-change-padding">
						<table id="deviceCarList" class="table table-bordered table-hover" width="100%">
						</table>
					</div>
					<!-- /.box-body -->
					<div class="carRedPacketAddParkTool-bullet" id="deviceToolssss">
					</div>
				</div>
				<!-- /.box -->
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 弹出框-->
<div class="modal fade" id="careditModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">上传图片</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="carphotoForm">
					<div class="form-group">
						<label class="col-md-3 control-label">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder="" name="carPhotoUrl1" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">车辆图片：</label>
						<div class="col-md-8">
							<div id="carFineUploaderAs"><span name="parkErrorInfo"></span></div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="editCarPhotoBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div class="modal fade" id="carzjeditModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">上传图片</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="carzjphotoForm">
					<div class="form-group">
						<label class="col-md-3 control-label">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder="" name="carDocPhotoUrl1" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">车辆证件图片：</label>
						<div class="col-md-8">
							<div id="carzhFineUploaderAs"><span name="parkErrorInfo2"></span></div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="editCarzjPhotoBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"carEdit": "res/js/car/car_edit"
			}
		});
		require(["carEdit"], function(carEdit) {
			carEdit.init();
		});

		var config = new Object();
		config.uploadId = "carFineUploaderAs";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit = 1;
		config.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
		config.sizeLimit = 1000 * 1024 * 1024;
		config.minSizeLimit = 5 * 5;
		config.formName = "carphotoForm";
		//文件回显inputName
		config.inputName = "carPhotoUrl1";
		//错误提示span标签name
		config.spanName = "parkErrorInfo";
		require.config({
			paths: {
				"upload": "res/js/resource/uploadFile"
			}
		});
		require(["upload"], function(upload) {
			upload.init(config);
		});

		var cg = new Object();
		cg.uploadId = "carzhFineUploaderAs";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		cg.storePath = "car_doc";
		cg.itemLimit = 1;
		cg.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
		cg.sizeLimit = 1000 * 1024 * 1024;
		cg.minSizeLimit = 5 * 5;
		cg.formName = "carzjphotoForm";
		//文件回显inputName
		cg.inputName = "carDocPhotoUrl1";
		//错误提示span标签name
		cg.spanName = "parkErrorInfo2";
		require.config({
			paths: {
				"upload": "res/js/resource/uploadFile"
			}
		});
		require(["upload"], function(upload) {
			upload.init(cg);
		});

		$(".datepicker").datetimepicker({
			language: "zh-CN",
			autoclose: true,
			todayBtn: true,
			minuteStep: 5,
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>