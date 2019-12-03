<meta charset="utf-8">
<style>
.btn-new {
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: normal;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
}

.btn-sm-new {
	height: 30px;
	padding: 5px 10px;
	font-size: 12px;
	line-height: 1.5;
	border-radius: 3px;
}

.btn-default-new {
	color: #333;
	background-color: #fff;
	border-color: #ccc;
}
</style>
<div class="container-fluid backgroundColor">
	<form name="advertMengLongAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增广告</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>广告类型：</label>
						</td>
						<td><select name="type" class="form-control val" style="width: 220px;"
							id="advertAdd_Type" onchange="advertAdd_Type_Change(this)">
								<!--<#list dataTypeList as list>
                                     <option  value="" >${list.itemValue}</option>
                                     </#list>-->
						</select> <span name="typeAdd"></span></td>
						<td><label class=" control-label key"><span>*</span>广告名称：</label>
						</td>
						<td><input class="form-control val" name="advertName" style="width: 220px;"
							placeholder="请输入活动名称" /> <span name="advertNameAdd"></span></td>
					</tr>
					<tr class="add-car-last-tr"  id="advert_add_tr">
					   <td>
                            <label class=" control-label key"><span>*</span>区域类型：</label>
                        </td>
                        <td colspan="3">
                            <select name="advertType" style="width: 220px;" class="form-control val" id="advertAdd_AdvertType"
                            onchange="advertAdd_advertType_Change(this)">
                                </select>
                            <span name="advertTypeAdd"></span>  
                        </td>
					</tr>
					<tr class="add-car-last-tr">
						<td><label class=" control-label key"><span >*</span>区域位置：</label>
						</td>
						<td><select class="form-control" name="advertPosition" onchange="advertPosition_add_change(this)" id="advertAdd_AdvertPosition" style="width: 220px;">
								<!--<#list dataPositionList as list>
										<option value="">${list.itemValue}</option>
									</#list>-->
						</select>
						<span name="advertPositionAdd"></span>
						</td>
						<td><label class=" control-label key"><span>*</span>广告排名：</label>
						</td>
						<td><input class="form-control val" placeholder="请输入广告排名"
							name="ranking" maxlength="11"
							onkeyup="this.value=this.value.replace(/\D/g,'')" style="width: 220px;"/> <span
							name="rankingAdd"></span></td>
					</tr>
					<tr class="advertPicUrlAddMengLong">
						<td><label class=" control-label key">跳转地址选择：</label>
						</td>
						<td id="linkTypeRadioAddMengLong" ><input type="radio" name="linkType" value="0" checked />外部链接
							<input type="radio" name="linkType" value="1" />内容

						<td><label class=" control-label key"><span id="advertPosition_add_change_span">*</span>广告图片：</label>
						</td>
						<td class="odd-td" ><input name="advertPicUrl" type="hidden" />
							<div class="img-td-upload" id="advertMengLongPicUrlImg">
								<div id="addAdvertMengLongPicUrlButton" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">广告图片</h3>
								</div>
							</div> <span name="advertPicUrlAdd"></span></td>

					</tr>
					<tr id="linkUrlAddMengLong" class="advertPicUrlAddMengLong">

						<td class="form-group LinkUrl"><label
							class=" control-label key">外部链接：</label></td>
						<td class="form-group LinkUrl" colspan="3"><input
							class="form-control val" name="linkUrl" /> <span
							name="linkUrlAdd"></span></td>

					</tr>
					<tr class="form-group advertPicUrlAddMengLong" id="textMengLong">

						<td><label class=" control-label key">内容：</label>
						</td>
						<td colspan="3"><textarea name="text"
								class="form-control val" id="addTextMengLong" title="Contents"></textarea>
							<span name="textAdd"></span></td>

					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button"
								id="saveAddAdvertMengLong" class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="closeAddAdvertMengLong" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>


<div class="modal fade" id="carModelList" tabindex="-1" role="dialog">
	<div class="modal-dialog" style="width: 750px;">
		<div class="modal-content">
			<div class="with-border">
				<div class="title-new-details">选择车型列表</div>
			</div>
			<!--定义操作列按钮模板-->
			<script id="carModelBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
			<div class="box">
				<div class="box-body box-body-change-padding">
					<input type="hidden" name="dataTemp" />
					<table id="carModelLists" class="table table-bordered table-hover"
						width="100%">
					</table>
				</div>
				<!-- /.box-body -->
				<div class="carRedPacketAddParkTool-bullet" id="carModelTools">
				</div>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->



<!-- 弹出框-->
<div class="modal fade" id="advertMengLongPicUrlAddModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">广告图片上传</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="advertMengLongPicUrlAddForm">
					<div class="form-group">
						<label class="col-md-3 control-label val">图片URL：</label>
						<div class="col-md-6">
							<input type="text" class="form-control val" placeholder=""
								name="advertPicUrl1" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label val">广告图片：</label>
						<div class="col-md-6">
							<div id="advertPicUploader">
								<span name="advertPicErrorInfo"></span>
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="addAdvertMengLongPicBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 选择车型 -->
<div class="modal" id="carModelAdd">
	<div class="modal-dialog" style="width: 750px;">
		<div class="modal-content">
			<div class="with-border">
				<div class="title-new-details">选择车型</div>
			</div>
			<div class="">
				<!--定义操作列按钮模板-->
				<script id="carModelAddTpl" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
				<div class="box">
					<div class="box-body box-body-change-padding">
						<table id="carModelAddList"
							class="table table-bordered table-hover" width="100%">
						</table>
					</div>
					<!-- /.box-body -->
					<div class="carRedPacketAddParkTool-bullet" id="carModelsAdd">
					</div>
				</div>
				<!-- /.box -->
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"advertAdd" : "res/js/ml/advert/advert_add"
			}
		});
		require([ "advertAdd" ], function(advertAdd) {
			advertAdd.init();
		});
		var config = new Object();
		config.uploadId = "advertPicUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）major_photo (专业图片）car_photo （车辆照片）textbooks (教科书）,advert_photo(活动图片)
		config.storePath = "advert_photo";
		config.itemLimit = 1;
		config.allowedExtensions = [ "jpeg", "jpg", "gif", "png" ];
		config.sizeLimit = 500 * 1024;
		config.minSizeLimit = 10 * 1024;
		config.formName = "advertMengLongPicUrlAddForm";
		//文件回显inputName
		config.inputName = "advertPicUrl1";
		//错误提示span标签name
		config.spanName = "advertPicErrorInfo";
		require.config({
			paths : {
				"upload" : "res/js/resource/uploadFile"
			}
		});
		require([ "upload" ], function(upload) {
			upload.init(config);
		});

		$('#addTextMengLong').summernote({
			height : 300,
			lang: 'zh-CN'
		});

		$('.note-editor button').removeClass("btn");
		$('.note-editor button').addClass("btn-new");
		$('.note-editor button').removeClass("btn-default");
		$('.note-editor button').addClass("btn-default-new");
		$('.note-editor button').removeClass("btn-sm");
		$('.note-editor button').addClass("btn-sm-new");

	});
</script>