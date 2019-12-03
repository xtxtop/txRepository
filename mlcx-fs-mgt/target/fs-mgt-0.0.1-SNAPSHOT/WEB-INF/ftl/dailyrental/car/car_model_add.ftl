<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carModelAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车型新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>车型名称：</label>
						</td>
						<td>
							<input class="form-control" name="carModelName" placeholder="请输入车型名称" />
							<span name="carModelNameAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>所属品牌：</label>
							<input type="hidden" name="carBrandName" readonly/>
						</td>
						<td>
							<select name="carBrandId" class="form-control" onchange="selectBrandValueAdd('carModelBrandIdAdd')" id="carModelBrandIdAdd">
								<option value="">全部 </option>
								<#list lb as l>
									<option value="${l.carBrandId}">
										${l.carBrandName}
									</option>
								</#list>
							</select>
							<span name="carBrandIdAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>所属车系：</label>
							<input type="hidden" name="carSeriesName" readonly/>
						</td>
						<td>
							<select class="form-control" name="carSeriesId" onchange="selectSeriesValueAdd('carModelSeriesIdAdd')" id="carModelSeriesIdAdd">
								<option value="">全部</option>
							</select>
							<span name="carSeriesIdAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>年代名称：</label>
							<input type="hidden" name="carPeriodName" readonly/>
						</td>
						<td>
							<select class="form-control" name="carPeriodId">
								<option value="">全部</option>
							</select>
							<span name="carPeriodIdAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>配置款型：</label>
						</td>
						<td>
							<input class="form-control" name="configModel" placeholder="请输入配置款型" />
							<span name="configModelAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>适用类型：</label>
						</td>
						<td>
							<select name="carType" class="form-control">
								<option value="">全部 </option>
								<option value="1">经济型</option>
								<option value="2">商务型</option>
								<option value="3">豪华型</option>
								<option value="4">6至15座商务</option>
								<option value="5">SUV</option>
							</select>
							<span name="carTypeAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>车身：</label>
						</td>
						<td>
							<select class="form-control" name="boxType">
								<option value="">全部 </option>
								<option value="1">两箱</option>
								<option value="2">三箱</option>
							</select>
							<span name="boxTypeAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>座位数：</label>
						</td>
						<td>
							<select class="form-control" name="seatNumber">
								<option value="">全部 </option>
								<option value="1">2座</option>
								<option value="2">4座</option>
								<option value="3">5座</option>
								<option value="4">7座</option>
							</select>
							<span name="seatNumberAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>变速箱：</label>
						</td>
						<td>
							<select name="gearBox" class="form-control">
								<option value="">全部 </option>
								<option value="1">手动 </option>
								<option value="2">自动 </option>
								<option value="3">手自一体 </option>
							</select>
							<span name="gearBoxAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>排量：</label>
						</td>
						<td>
							<input class="form-control" name="displacement" placeholder="请输入排量" />
							<span name="displacementAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>吨位：</label>
						</td>
						<td>
							<input class="form-control" name="tons" placeholder="请输入吨位" />
							<label style="position:absolute;right:25px;top:0;">吨</label>
							<span name="tonsAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>款型描述：</label>
						</td>
						<td>
							<input class="form-control" name="carModelInfo" placeholder="请输入款型描述" />
							<span name="carModelInfoAdd"></span>
						</td>
					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class="  control-label"><span>*</span>车型照片：</label>
							<input name="carModelUrl" type="hidden" value="" />
						</td>
						<td class="odd-td">
							<div class="img-td-upload" id="carPicUrlImgAdd">
								<div id="addCarModelUrl" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div>
							<span name="carModelUrlAdd"></span>
						</td>

						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCarModel" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCarModel" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
<div class="modal fade" id="carModelAddModel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">上传图片</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="carModelPhotoAddForm">
					<div class="form-group">
						<label class="col-md-3 control-label">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder="" name="carModelPhotoUrl" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">车辆图片：</label>
						<div class="col-md-8">
							<div id="carModelFineUploaderAdd"><span name="carModelAddErrorInfo"></span></div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="addCarModelPhotoBtn" value="确定">
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
				"carModelAdd": "res/js/dailyrental/car/car_model_add"
			}
		});
		require(["carModelAdd"], function(carModelAdd) {
			carModelAdd.init();
		});

		var config = new Object();
		config.uploadId = "carModelFineUploaderAdd";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit = 1;
		config.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
		config.sizeLimit = 1000 * 1024 * 1024;
		config.minSizeLimit = 5 * 5;
		config.formName = "carModelPhotoAddForm";
		//文件回显inputName
		config.inputName = "carModelPhotoUrl";
		//错误提示span标签name
		config.spanName = "carModelAddErrorInfo";
		require.config({
			paths: {
				"upload": "res/js/resource/uploadFile"
			}
		});
		require(["upload"], function(upload) {
			upload.init(config);
		});

	});
</script>