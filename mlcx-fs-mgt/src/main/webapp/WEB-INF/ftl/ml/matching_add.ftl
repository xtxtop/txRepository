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
	<form name="matchingAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增配套服务</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>配套服务名称：</label>
						</td>
						<td><input class="form-control val" name="matchingName"
							maxlength="20" placeholder="请输入配套服务名称" onclick="formVerify('matchingName_add_matching')"/> <span id="matchingName_add_matching_mlcx_verify"></span>

						</td>
						<td><label class=" control-label key"><span>*</span>是否启用：</label>
                        </td>
                        <td><select name="enableStatus" class="form-control val">
                                <option value="1">启用</option>
                                <option value="0">停用</option>
                        </select> </td>
					</tr>
					<tr>
					<td><label class=" control-label key"><span>*</span>类型：</label>
                        </td>
                        <td><select name="matchingType" class="form-control val">
                                <option value="1">充电桩配套</option>
                                <option value="2">停车场配套</option>
                        </select> </td>
					   <td><label class=" control-label key"><span>*</span>图片[21px*21px]：</label>
                            <input name="matchingPicUrl" type="hidden" /></td>
                        <td class="odd-td">
                            <div class="img-td-upload" id="matchingPicUrlImg" style="width: 108px;height: 108px;">
                                <div id="addmatchingPicUrlPhotoButton" style="padding-top:23px;" class="add-img-position" onclick="formVerify('matchingPicUrl_add_matching')">
                                    <h3 class="icon">+</h3>
                                    <h3 class="text">添加图片</h3>
                                </div>
                            </div> <span id="matchingPicUrl_add_matching_mlcx_verify"></span>
                        </td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addmatching"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="addclosematching" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- 弹出框-->
<div class="modal fade" id="matchingPicUrlPhotoAddModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">配套服务上传图片</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" name="matchingPicUrlphotoForm">
                    <div class="form-group">
                        <label class="col-md-3 control-label val">图片URL：</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" placeholder=""
                                name="matchingPicUrl" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label val">图片：</label>
                        <div class="col-md-8">
                            <div id="matchingPicUrlFineUploader">
                                <span name="matchingPicUrlErrorInfo"></span>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure"
                        id="addmatchingPicUrlPhotoBtn" value="确定">
                </div>
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
				"matchingAdd" : "res/js/ml/matching_add"
			}
		});
		require([ "matchingAdd" ], function(matchingAdd) {
			matchingAdd.init();
		});
		var config = new Object();
        config.uploadId = "matchingPicUrlFineUploader";
        //storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
        config.storePath = "park_photo";
        config.itemLimit = 1;
        config.allowedExtensions = [ "jpeg", "jpg", "gif", "png" ];
        config.sizeLimit = 500 * 1024;
        config.formName = "matchingPicUrlphotoForm";
        //文件回显inputName
        config.inputName = "matchingPicUrl";
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
	});
