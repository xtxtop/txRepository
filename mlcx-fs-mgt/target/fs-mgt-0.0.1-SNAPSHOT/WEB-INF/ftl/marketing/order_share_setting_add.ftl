<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="orderShareSettingAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增订单分享页配置</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>订单分享名称：</label>
						</td>
						<td>
							<input class="form-control val" name="orderShareName" placeholder="请输入订单分享名称"/>
							<span name="orderShareNameAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>分享链接的内容：</label>
						</td>
						<td>
							<input class="form-control val" name="orderShareMemo" placeholder="请输入分享链接的内容"/>
							<span name="orderShareMemoAdd"></span>
						</td>
					</tr>
					<tr class="add-car-last-tr">
						
						<td>
							<label class=" control-label key"><span>*</span>订单分享页图片：</label>
						</td>
						<td class="odd-td">
						<input name="orderSharePicUrl" type="hidden"/>
						<div class="img-td-upload" id="orderSharePicUrlImg">
							<div id="orderSharePicUrlAddButton" class="add-img-position">
								<h3 class="icon">+</h3>
								<h3 class="text">添加图片</h3>
							</div>
						</div>
						<span name="orderSharePicUrlAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>订单分享icon图片：</label>
						</td>
						<td class="odd-td">
						<input name="orderShareIconUrl" type="hidden"/>
						<div class="img-td-upload" id="orderShareIconUrlImg">
							<div id="orderShareIconUrlAddButton" class="add-img-position">
								<h3 class="icon">+</h3>
								<h3 class="text">添加图片</h3>
							</div>
						</div>
						<span name="orderShareIconUrlAdd"></span>
						</td>
					</tr>
					<tr>
					<td>
							<label class=" control-label key"><span>*</span>订单分享页url：</label>
						</td>
						<td>
							<input class="form-control val" name="linkUrl" placeholder="请输入订单分享url"/>
							<span name="linkUrlAdd"></span>
						</td>
						
						<td colspan="2"></td>
					</tr>
					<tr>
					<td>
							<label class=" control-label key"><span>*</span>分享页内容：</label>
						</td>
						<td colspan="3">
							<textarea name="orderShareContent" class="form-control val" id="orderShareContentAddText1" title="Contents"></textarea>
							<span>备注：订单分享的有效天数，请使用days,订单分享后可被领取最大次数请使用num，到时以系统参数配置为准</span>
							<span name="orderShareContentAdd"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addOrderShareSetting" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeOrderShareSettingAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
<div class="modal fade" id="orderShareSettingAddModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">订单分享页图片上传</h4>
            </div>
            <div class="modal-body">
                 <form class="form-horizontal"  name="orderShareSettingAddPicForm">
				<div class="form-group">
                        <label class="col-md-3 control-label val">图片URL：</label>
                        <div class="col-md-8">
                           <input type="text" class="form-control val" placeholder="" name="orderShareSettingAddUrl1" readonly>
                        </div>
                 </div>
                 <div class="form-group">
                       <label class="col-md-3 control-label val">分享页图片：</label>
                       <div class="col-md-8">
                            <div id="orderShareSettingAddUploader"><span name="orderShareSettingAddErrorInfo"></span></div>
                       </div>
                </div>
			</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="orderShareSettingAddPicBtn" value="确定">
				</div>
            </div>
           
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="modal fade" id="orderShareSettingAddIconModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">订单分享Icon图片上传</h4>
            </div>
            <div class="modal-body">
                 <form class="form-horizontal"  name="orderShareSettingAddIconForm">
				<div class="form-group">
                        <label class="col-md-3 control-label val">图片URL：</label>
                        <div class="col-md-8">
                           <input type="text" class="form-control val" placeholder="" name="orderShareSettingAddIconUrl1" readonly>
                        </div>
                 </div>
                 <div class="form-group">
                       <label class="col-md-3 control-label val">分享icon图片：</label>
                       <div class="col-md-8">
                            <div id="orderShareSettingAddIconUploader"><span name="orderShareSettingAddIconErrorInfo"></span></div>
                       </div>
                </div>
			</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure"
						id="orderShareSettingAddIconBtn" value="确定">
				</div>
            </div>
           
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"orderShareSettingAdd":"res/js/marketing/order_share_setting_add"}});
		require(["orderShareSettingAdd"], function (orderShareSettingAdd){
			orderShareSettingAdd.init();
		});  
		var config=new Object();
		config.uploadId="orderShareSettingAddUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(活动图片)
		config.storePath = "orderShare_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "orderShareSettingAddPicForm";
		//文件回显inputName
		config.inputName = "orderShareSettingAddUrl1";
		//错误提示span标签name
		config.spanName = "orderShareSettingAddErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		var configIcon=new Object();
		configIcon.uploadId="orderShareSettingAddIconUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(活动图片)
		configIcon.storePath = "orderShare_photo";
		configIcon.itemLimit=1;
		configIcon.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		configIcon.sizeLimit=500 * 1024;
		configIcon.minSizeLimit=10* 1024;
		configIcon.formName= "orderShareSettingAddIconForm";
		//文件回显inputName
		configIcon.inputName = "orderShareSettingAddIconUrl1";
		//错误提示span标签name
		configIcon.spanName = "orderShareSettingAddIconErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(configIcon);
		});
		
		$('#orderShareContentAddText1').summernote({
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