<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkDayEditForm">
		<input type="hidden" name="parkId" value="${parkDay.parkId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑门店</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>租赁商：</label>
						</td>
						<td>
							<select name="merchantId" class="form-control val">
                                     <option value="" >请选择</option>
                                     <#list merchants as merchant>
                                         <option value="${merchant.merchantId}" <#if parkDay.merchantId==merchant.merchantId>selected==selected</#if> >
                                                ${merchant.merchantName}
                                         </option>
                                     </#list>
                                    </select>
                            <span name="merchantIdEdit"></span>
						</td>
					<td>
						<label class=" control-label key"><span>*</span>门店名称：</label>
					</td>
					<td>
						<input class="form-control val" name="parkName" maxlength="20" value="${parkDay.parkName}"/>
						<span name="parkNameEdit"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>联系人姓名：</label>
						</td>
						<td>
							 <input class="form-control val" name="cantactPerson" maxlength="20" value="${parkDay.cantactPerson}"/>
							 <span name="cantactPersonEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>联系人电话：</label>
						</td>
						<td>
							<input class="form-control val" name="mobilePhone" maxlength="20" value="${parkDay.mobilePhone}"/>
							<span name="mobilePhoneEdit"></span>
						</td>
					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key"><span>*</span>图片：</label>
                                <input name="parkDayPhoto" type="hidden" value="${parkDay.parkDayPhoto}"/>
						</td>
						<td class="odd-td">
			    <div class="img-td-upload" style="background-image: url(${imagePath!''}/${parkDay.parkDayPhoto});" id="parkPicUrlImg">
					<div id="editParkPhotoButton" class="add-img-position">
						<h3 class="icon">+</h3>
						<h3 class="text">添加图片</h3>
					</div>
				</div>
				<span name="parkDayPhotoEdit"></span>
				</td>
						<td> 
						    <input type="hidden" name="longitude" value="${parkDay.longitude}"/>
                            <input type="hidden" name="latitude" value="${parkDay.latitude}"/>
                            <input type="hidden" name="fullAddr" value="${parkDay.addrStreet}"/>
                            <label class=" control-label key">要查询的地址：</label>
						</td>
						<td>
							<input class="form-control val" type="text" id="searchAddressEdit" value="${parkDay.addrStreet}">
							<button type="button" id="searchParkDayEditBtn" class="btn btn-default">搜索</button>
						    <span id="pardDayFullAddrEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
					<td colspan="2"><button type="button" id="saveParkDayEdit"   class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                 保存
            </button></td>
						<td colspan="2"><button type="button" id="closeParkDayEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
              关闭 
            </button></td>
						
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	                       
</div>

<!-- 弹出框-->
     <div class="modal fade" id="parkDayPhotoEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">门店上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="parkDayPhotoEditForm" >
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="parkDayPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">门店图片：</label>
                           <div class="col-md-8">
                                <div id="parkDayEditFineUploader"><span name="parkDayEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right"
							id="editParkDayPhotoBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
    <!-- 弹出框-->
    <div class="modal fade" id="loadMapContentEdit">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">地图</h4>
                </div>
                <div class="modal-body">
                     <div style="height:300px" id="parkDayEditMap">/div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
    
<script type="text/javascript">
    $(function () {
      require.config({paths: {"parkDayEdit":"res/js/dailyrental/parkday/parkDay_edit"}});
		require(["parkDayEdit"], function (parkDayEdit){
			parkDayEdit.init();
			//详情页面地图展示
			parkDayEdit.creatMaker();
			parkDayEdit.clickAndMarker();
		});
		var config=new Object();
		config.uploadId="parkDayEditFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "park_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=50* 1024;
		config.formName= "parkDayPhotoEditForm";
		//文件回显inputName
		config.inputName = "parkDayPicUrl1";
		//错误提示span标签name
		config.spanName = "parkDayEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
      $('.business_hours').daterangepicker({
			timePicker:true,
			format:'HH:mm',

		});
    });
</script>