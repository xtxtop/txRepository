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
	<form name="matchingEditForm">
	   <input type="hidden" name="matchingId" value="${CMatching.matchingId }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑配套服务</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>配套服务名称：</label>
						</td>
						<td><input class="form-control val" name="matchingName" onclick="formVerify('matchingName_edit_matching')"
							maxlength="20" placeholder="请输入配套服务名称" value="${CMatching.matchingName }" /> <span id="matchingName_edit_matching_mlcx_verify"></span>

						</td>
						<td><label class=" control-label key"><span>*</span>是否启用：</label>
                        </td>
                        <td>
                            <select name="enableStatus" class="form-control val">
                                <option value="1" <#if CMatching.enableStatus==1>selected="selected"</#if>>启用</option>
                                <option value="0" <#if CMatching.enableStatus==0>selected="selected"</#if>>停用</option>
                        </select>
                        </td>
					</tr>
					<tr>
					<td><label class=" control-label key"><span>*</span>类型：</label>
                        </td>
                        <td>
                            <select name="matchingType" class="form-control val">
                                <option value="1" <#if CMatching.matchingType==1>selected="selected"</#if>>充电桩配套</option>
                                <option value="2" <#if CMatching.matchingType==2>selected="selected"</#if>>停车场配套</option>
                        </select>
                        </td>
                       <td><label class=" control-label key"><span>*</span>图片[21px*21px]：</label>
                            <input name="matchingPicUrl" type="hidden" value="${CMatching.matchingPicUrl}"/></td>
                        <td class="odd-td">
                            <div class="img-td-upload"  id="matchingPicUrlEditImg" style="background-image: url(${imagePath!''}/${CMatching.matchingPicUrl});width: 108px;height: 108px;">
                                <div id="editmatchingPicUrlPhotoButton" style="padding-top:23px;" class="add-img-position" onclick="formVerify('matchingPicUrl_edit_matching')">
                                    <h3 class="icon">+</h3>
                                    <h3 class="text">添加图片</h3>
                                </div>
                            </div> <span id="matchingPicUrl_edit_matching_mlcx_verify"></span>
                        </td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="Editmatching"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="Editclosematching" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- 弹出框-->
<div class="modal fade" id="matchingPicUrlPhotoEditModal">
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
                <form class="form-horizontal" name="matchingPicUrlEditphotoForm">
                    <div class="form-group">
                        <label class="col-md-3 control-label val">图片URL：</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" placeholder=""
                                name="matchingPicUrlEdit" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label val">图片：</label>
                        <div class="col-md-8">
                            <div id="matchingPicUrlEditFineUploader">
                                <span name="matchingPicUrlErrorInfo"></span>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure"
                        id="editmatchingPicUrlPhotoBtn" value="确定">
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
				"matchingEdit" : "res/js/ml/matching_edit"
			}
		});
		require([ "matchingEdit" ], function(matchingEdit) {
			matchingEdit.init();
		});
		var config = new Object();
        config.uploadId = "matchingPicUrlEditFineUploader";
        //storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
        config.storePath = "park_photo";
        config.itemLimit = 1;
        config.allowedExtensions = [ "jpeg", "jpg", "gif", "png" ];
        config.sizeLimit = 500 * 1024;
        config.formName = "matchingPicUrlEditphotoForm";
        //文件回显inputName
        config.inputName = "matchingPicUrlEdit";
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
</script>
