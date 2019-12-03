<meta charset="utf-8">
<style>
	.key {
		font-size: 1.5rem;
		font-weight: 500;
		color: #414550;
	}
	
	.val {
		font-size: 1.5rem;
		font-weight: 500;
		color: #a0a7af;
		line-height: 15px;
	}
	
	.form-group {
		white-space: nowrap;
	}
	
	label {
		text-align: right !important;
	}
	
	.val1 {
		margin-left: 100px !important;
		text-align: center;
	}
</style>
<div class="container-fluid backgroundColor" id="scollOo">
	<form name="carAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车辆新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class="control-label key"><span>*</span>车牌号：</label></td>
						<td> <input class="form-control val" name="carPlateNo" placeholder="请输入车牌号" />
							<div><span name="carPlateNo"></td>
						<!--<td><label class=" control-label key"><span>*</span>租赁类型：</label>
						</td>
						<td>
							<select name="leaseType" class="form-control val">
								<option value="1">分时</option>
							</select>
						</td>-->
						<td>
						
							<label class="control-label key"><span>*</span>终端序列号：</label>
						</td>
						<td>
						<input type="hidden" name="deviceId" id="deviceId" />
							<input class="form-control val" name="deviceSn" id="deviceSn" readonly placeholder="请输入终端序列号" />
							<input type="button" class="btn btn-default carOnBtn addcarDevice" value="绑定终端">
							<span name="deviceId"></span>
						</td>
					</tr>

					<tr>
						<td> <label class="control-label key"><span>*</span>城市：</label></td>
						<td>
							<select name="cityId" class="form-control val">
								<option value="">请选择</option>
								<#list cities as citie>
									<option value="${citie.dataDictItemId}">
										${citie.itemValue}
									</option>
								</#list>
							</select>
						</td>

						<td><label class=" control-label key">所在场站：</label></td>
						<td>
							<select name="locationParkNo" class="form-control val">

							</select>
							<div><span name="locationParkNo">
						</td>
					</tr>
					
					
					
					<tr>
					
					<td><label class=" control-label key"><span>*</span>品牌：</label>
						</td>
						<td>
							<select name="carBrandId" class="form-control val">
								<#list carBrands as brand>
									<option value="${brand.carBrandId}">
										${brand.carBrandName}
									</option>
								</#list>
							</select>
						</td>
						<td> <label class="control-label key"><span>*</span>车型系列：</label></td>
						<td><select name="carModelId" class="form-control val" id="carModelId">
                        <#list carmodels as cmodel>
                            <option  value="${cmodel.carSeriesId}" >
                                ${cmodel.carSeriesName}
                            </option>
                        </#list>
                    </select>
                    <span name="carModelIdAdd"></span>
						</td>
						
						
					</tr>

						<tr>

							<td><label class="control-label key"><span>*</span>座位数：</label>
							</td>
							<td class="seat"><input class="form-control val" name="seaTing" readonly="readonly" value="${dad.seaTing}" /></td>
							<td><label class=" control-label key"><span>*</span>动力类型：</label></td>
							<td>
								<select name="vehicleType" class="form-control val">
									<option value="1">电量</option>
									<option value="2">燃油</option>
								</select>
							</td>

						</tr>
					</tr>
					<tr>
						<td><label class=" control-label key">车辆颜色：</label></td>
						<td>
							<select name="carColor" class="form-control val">
								<#list carcolors as carcolor>
									<option value="${carcolor.itemValue}">
										${carcolor.itemValue}
									</option>
								</#list>
							</select><span name="ruleName"></span></td>
						<td><label class="control-label key">加盟商：</label></td>
						<td class="btn-btnA-con">
							<div>
								<input type="hidden" name="franchiseeId" />
								<input type="text" class="form-control val" name="franchiseeName" readonly="readonly" placeholder="请选择加盟商" />
							</div>
							<div class="btn-btnA">
								<input type="button" class="btn btn-info" id="franchiseeAdd" value="选择">
							</div>
						</td>

					</tr>
					<tr>
						<td><label class=" control-label key">车辆识别码：</label></td>
						<td>
							<input class="form-control val" name="carIdNo" placeholder="请输入车辆识别码" /><span name="carIdNo"></span>
						</td>
						<td><label class=" control-label key">发动机号：</label></td>
						<td><input class="form-control val" name="engineNo" placeholder="请输入发动机号" /><span name="engineNo"></span></td>
					</tr>
					<tr>

						<td><label class=" control-label key">保险公司：</label></td>
						<td>
							<select name="insuranceCompany" class="form-control val">
								<option value="">请选择</option>
								<option value="1">太平洋保险</option>
								<option value="2">平安保险</option>
							</select>
						</td>
						<td><label class=" control-label key">投保日期：</label></td>
						<td><input class="datepicker form-control val" name="enrollmentDate" placeholder="请选择投保日期" /><span name="enrollmentDate"></span></td>
					</tr>

					<tr>
						<td><label class="control-label key">有效保险期：</label></td>
						<td><input class="datepicker form-control val" name="effectiveInsurancePeriod" placeholder="请选择有效保险期" /><span name="effectiveInsurancePeriod"></span></td>

						<td><label class=" control-label key">检验有效期：</label></td>
						<td><input class="datepicker form-control val" name="validityDate" /><span name="validityDate"></td>
						
					</tr>
					<tr class="add-car-last-tr">
						<td class="photo-line-height">
							<label class=" control-label key">车辆照片：</label>
							<input name="carPhotoUrl1" type="hidden" value="${dad.carSeriresPic}" />
						</td>
						<td class="odd-td">
							<div class="img-td-upload" id="carPicUrlImg">
								<div id="addCarPhoto" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div>
							<span name="carPhotoUrl1"></span>
						</td>

						<td class="photo-line-height">
							<label class=" control-label key">车辆证件照片：</label>
							<input name="carDocPhotoUrl1" type="hidden" />
						</td>
						<td class="odd-td">

							<div class="img-td-upload" id="carzjPicUrlImg">
								<div id="addzjCarPhoto" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div>
							<span name="carDocPhotoUrl1"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addCar" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCaradd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
			</div>
	</form>
	</div>
	<!-- 加盟商 -->
	<div class="modal" id="franchiseeModel">
		<div class="modal-dialog" style="width:750px;">
			<div class="modal-content">
				<form name="franchiseeSerachForm">
					<div class="with-border">
						<div class="title-new-details">选择加盟商</div>
						<div class="pull-down-menu-car">
							<div class="parkNo-frombg">
								<span>加盟商编号</span>
								<input class="form-control-input" name="franchiseeNo" id="franchiseeNo" value="" placeholder="请输入加盟商编号">
							</div>
						</div>
					</div>
					<!-- /.box-header -->

					<div class="box-body box-body-change-padding">
					</div>
					<!-- /.box-body -->
					<!--修改-->
					<div class="box-bullet">
						<div class="box-footer">
							<!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
							<button type="button" class="btn-new-type" id="franchiseeSearch">确定</button>
							<button type="reset" class="btn-new-type">清空</button>
						</div>
						<!-- /.box-footer -->
					</div>
				</form>
				<div class="border-bullet">
					<!--定义操作列按钮模板-->
					<script id="franchiseeBtnTplAdd" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
					<div class="box">

						<div class="box-header">
							<!-- <h3 class="box-title">数据列</h3> -->
						</div>
						<!-- /.box-header -->
						<div class="box-body box-body-change-padding">
							<table id="franchiseeListAdd" class="table table-bordered table-hover" width="100%">
							</table>
						</div>
						<!-- /.box-body -->
						<div class="carRedPacketAddParkTool-bullet" id="franchiseeToolssssAdd">
						</div>
					</div>
					<!-- /.box -->
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>

	<div class="modal" id="deviceCarModalAdd">
		<div class="modal-dialog" style="width:750px;">
			<div class="modal-content">
				<div class="with-border">
					<div class="title-new-details">选择终端</div>
				</div>
				<div class="">
					<!--定义操作列按钮模板-->
					<script id="deviceCarBtnTplAdd" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
					<div class="box">
						<div class="box-body box-body-change-padding">
							<table id="deviceCarListAdd" class="table table-bordered table-hover" width="100%">
							</table>
						</div>
						<!-- /.box-body -->
						<div class="carRedPacketAddParkTool-bullet" id="deviceToolssssAdd">
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
	<div class="modal fade" id="carAddModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">上传图片</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="carphotoForm">
						<div class="form-group">
							<label class="col-md-3 control-label val">图片URL：</label>
							<div class="col-md-8">
								<input type="text" class="form-control val" placeholder="" name="carPhotoUrl1" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label val">车辆图片：</label>
							<div class="col-md-8">
								<div id="carFineUploader"><span name="parkErrorInfo"></span></div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="addCarPhotoBtn" value="确定">
					</div>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<div class="modal fade" id="carzjAddModal">
		<div class="modal-dialog">

			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">上传图片</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="carzjphotoForm">
						<div class="form-group">
							<label class="col-md-3 control-label val">图片URL：</label>
							<div class="col-md-8">
								<input type="text" class="form-control val" placeholder="" name="carDocPhotoUrl1" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label val">车辆证件图片：</label>
							<div class="col-md-8">
								<div id="carzhFineUploader"><span name="parkErrorInfo2"></span></div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="addCarzjPhotoBtn" value="确定">
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
					"carAdd": "res/js/car/car_add"
				}
			});
			require(["carAdd"], function(carAdd) {
				carAdd.init();
			});

			var config = new Object();
			config.uploadId = "carFineUploader";
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
			cg.uploadId = "carzhFineUploader";
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
				minView: "month",
				autoclose: true,
				clearBtn: true, //清除按钮
				todayBtn: true,
				minuteStep: 5,
				startDate: moment(new Date()).format("YYYY-MM"),
				format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
			});
		});
	</script>
	<!--设置滚动条滚动时，时间框隐藏-->
	<script type="text/javascript" src="res/js/common/dateScroll.js"></script>