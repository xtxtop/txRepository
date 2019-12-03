<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkDayAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增门店</th>
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
                                         <option value="${merchant.merchantId}" >
                                                ${merchant.merchantName}
                                         </option>
                                     </#list>
                                    </select>
                            <span name="merchantIdAdd"></span>
						</td>
					<td>
						<label class=" control-label key"><span>*</span>门店名称：</label>
					</td>
					<td>
						<input class="form-control val"  placeholder="请输入门店名称" name="parkName" maxlength="20"/>
						<span name="parkNameAdd"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>联系人姓名：</label>
						</td>
						<td>
							<input class="form-control val"  placeholder="请输入联系人姓名" name="cantactPerson" maxlength="20"/>
							<span name="cantactPersonAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>联系人电话：</label>
						</td>
						<td>
							<input class="form-control val"  placeholder="请输入联系人电话" name="mobilePhone" maxlength="20"/>
							<span name="mobilePhoneAdd"></span
						</td>
					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key"><span>*</span>图片：</label>
						    <input name="parkDayPhoto" type="hidden"/>
						</td>
						<td class="odd-td">
			    <div class="img-td-upload"  id="parkPicUrlImg">
					<div id="addParkPhotoButton" class="add-img-position">
						<h3 class="icon">+</h3>
						<h3 class="text">添加图片</h3>
					</div>
				</div>
				<span name="parkDayPhotoAdd"></span>
				</td>
						<td> 
						    <input type="hidden" name="longitude"/>
                            <input type="hidden" name="latitude"/>
                            <input type="hidden" name="fullAddr"/>
                            <label class=" control-label key">要查询的地址：</label>
						</td>
						<td>
							<input class="form-control val"  placeholder="请输入要查询的地址" type="text" id="searchAddressAdd">
							<button type="button" id="searchParkDayAddBtn" class="btn btn-default">搜索</button>
							<span id="parkDayFullAddrAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
					    <td colspan="2"><button type="button" id="addParkDay"   class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                 保存
            </button></td>
						<td colspan="2"><button type="button" id="addParkDayClose" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
              关闭 
            </button></td>
						
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	                       
</div>

<!-- 弹出框-->
    <div class="modal fade" id="parkDayPhotoAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">门店上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="parkDayPhotoForm" >
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="parkDayPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">门店图片：</label>
                           <div class="col-md-8">
                                <div id="parkDayAddFineUploader"><span name="parkDayErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right"
							id="addParkDayPhotoBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
    
    <!-- 弹出框-->
    <div class="modal fade" id="loadMapContent">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">地图</h4>
                </div>
                <div class="modal-body">
                     <div style="height:300px" id="parkDayAddMap">/div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
    
<script type="text/javascript">
  $(function () {
      require.config({paths: {"parkDayAdd":"res/js/dailyrental/parkday/parkDay_add"}});
		require(["parkDayAdd"], function (parkDayAdd){
			parkDayAdd.init();
		});
		 var config=new Object();
		config.uploadId="parkDayAddFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "park_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "parkDayPhotoForm";
		//文件回显inputName
		config.inputName = "parkDayPicUrl1";
		//错误提示span标签name
		config.spanName = "parkDayErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		}); 
       $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,//清除按钮
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        $('.business_hours').daterangepicker({
				timePicker:true,
				format:'HH:mm',
				
			});
    });
</script>