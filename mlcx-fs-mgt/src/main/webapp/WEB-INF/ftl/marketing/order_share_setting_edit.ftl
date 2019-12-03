<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="orderShareSettingEditFrom">
		<input type="hidden" name="orderShareSettingNo" value="${orderShareSetting.orderShareSettingNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑订单分享页配置</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>订单分享名称：</label>
						</td>
						<td>
							<input class="form-control val" name="orderShareName" value="${orderShareSetting.orderShareName}" />
							<span name="orderShareNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>分享链接的内容：</label>
						</td>
						<td>
							<input class="form-control val" name="orderShareMemo" value="${orderShareSetting.orderShareMemo}"/>
							<span name="orderShareMemoEdit"></span>
						</td>
					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key"><span>*</span>订单分享页图片：</label>
						</td>
						<td class="odd-td">
						<input name="orderSharePicUrl" type="hidden" value="${orderShareSetting.orderSharePicUrl}"/>
						<div class="img-td-upload" style="background-image: url(${imagePath!''}/${orderShareSetting.orderSharePicUrl});" id="orderSharePicUrlImg">
							<div id="orderSharePicUrlEditButton" class="add-img-position">
								<h3 class="icon">+</h3>
								<h3 class="text">添加图片</h3>
							</div>
						</div>
													<span name="orderSharePicUrlEdit"></span>
													</td>
								<td>
							<label class=" control-label key"><span>*</span>订单分享icon图片：</label>
						</td>
						<td class="odd-td">
						<input name="orderShareIconUrl" type="hidden" value="${orderShareSetting.orderShareIconUrl}"/>
						<div class="img-td-upload" style="background-image: url(${imagePath!''}/${orderShareSetting.orderShareIconUrl});" id="orderShareIconUrlImg">
							<div id="orderShareIconUrlEditButton" class="add-img-position">
							<h3 class="icon">+</h3>
							<h3 class="text">添加图片</h3>
							</div>
						</div>
							<span name="orderShareIconUrlEdit"></span>
						</td>
					</tr>
					<tr>
				<td>
							<label class=" control-label key"><span>*</span>订单分享页url：</label>
						</td>
						<td colspan="3">
							<input class="form-control val" name="linkUrl" value="${orderShareSetting.linkUrl}" />
							<span name="linkUrlEdit"></span>
						</td>
					</tr>
					<tr>
							<td>
							<label class=" control-label key"><span>*</span>分享页内容：</label>
						</td>
						<td colspan="3">
							<textarea name="orderShareContent" class="form-control val" id="orderShareContentEditText1" title="Contents">${orderShareSetting.orderShareContent}</textarea>
							<span>备注：订单分享的有效天数，请使用days,订单分享后可被领取最大次数请使用num，到时以系统参数配置为准</span>
							<span name="orderShareContentEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editOrderShareSetting" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeOrderShareSettingEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
<div class="modal fade" id="orderShareSettingEditModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">订单分享页图片上传</h4>
            </div>
            <div class="modal-body">
                 <form class="form-horizontal"  name="orderShareSettingEditPicForm">
				<div class="form-group">
                        <label class="col-md-3 control-label val">图片URL：</label>
                        <div class="col-md-8">
                           <input type="text" class="form-control val" placeholder="" name="orderShareSettingEditUrl1" readonly>
                        </div>
                 </div>
                 <div class="form-group">
                       <label class="col-md-3 control-label val">分享页图片：</label>
                       <div class="col-md-8">
                            <div id="orderShareSettingEditUploader"><span name="orderShareSettingEditErrorInfo"></span></div>
                       </div>
                </div>
			</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="orderShareSettingEditPicBtn" value="确定">
				</div>
            </div>
           
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" id="orderShareSettingEditIconModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">订单分享页图片上传</h4>
            </div>
            <div class="modal-body">
                 <form class="form-horizontal"  name="orderShareSettingEditIconForm">
				<div class="form-group">
                        <label class="col-md-3 control-label val">图片URL：</label>
                        <div class="col-md-8">
                           <input type="text" class="form-control val" placeholder="" name="orderShareSettingEditIconUrl1" readonly>
                        </div>
                 </div>
                 <div class="form-group">
                       <label class="col-md-3 control-label val">分享页图片：</label>
                       <div class="col-md-8">
                            <div id="orderShareSettingEditIconUploader"><span name="orderShareSettingEditIconErrorInfo"></span></div>
                       </div>
                </div>
			</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="orderShareSettingEditIconBtn" value="确定">
				</div>
            </div>
           
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"orderShareSettingEdit":"res/js/marketing/order_share_setting_edit"}});
		require(["orderShareSettingEdit"], function (orderShareSettingEdit){
			orderShareSettingEdit.init();
		});  
		var config=new Object();
		config.uploadId="orderShareSettingEditUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(活动图片)
		config.storePath = "orderShare_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "orderShareSettingEditPicForm";
		//文件回显inputName
		config.inputName = "orderShareSettingEditUrl1";
		//错误提示span标签name
		config.spanName = "orderShareSettingEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		var configEditIcon=new Object();
		configEditIcon.uploadId="orderShareSettingEditIconUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(活动图片)
		configEditIcon.storePath = "orderShare_photo";
		configEditIcon.itemLimit=1;
		configEditIcon.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		configEditIcon.sizeLimit=500 * 1024;
		configEditIcon.minSizeLimit=10* 1024;
		configEditIcon.formName= "orderShareSettingEditIconForm";
		//文件回显inputName
		configEditIcon.inputName = "orderShareSettingEditIconUrl1";
		//错误提示span标签name
		config.spanName = "orderShareSettingEditIconErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(configEditIcon);
		});
		
		$('#orderShareContentEditText1').summernote({
        	height: 300
      	});
	    $('.note-editor button').removeClass("btn");
	    $('.note-editor button').addClass("btn-new");
	    $('.note-editor button').removeClass("btn-default");
	    $('.note-editor button').addClass("btn-default-new");
	    $('.note-editor button').removeClass("btn-sm");
	    $('.note-editor button').addClass("btn-sm-new");
      
    });
</script>