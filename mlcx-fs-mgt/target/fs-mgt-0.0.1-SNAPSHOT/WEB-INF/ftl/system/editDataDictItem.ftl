<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="dataDictItemEditForm">
		<input type="hidden" name="dataDictItemId" value="${dataDictItem.dataDictItemId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">数据字典项编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">数据字典分类代码：</label>
						</td>
						<td>
							 <select name="dataDictCatCode" class="form-control val">
							    	<option value="">请选择</option>
							    	<#if dataDictCatList?exists>
							    		<#list dataDictCatList as dataDictCat>
							    			<option value="${dataDictCat.dataDictCatCode}" 
							    			<#if dataDictCat.dataDictCatCode==dataDictItem.dataDictCatCode>
							    			selected=selected
							    			</#if>>${dataDictCat.dataDictCatName}</option>
							    		</#list>
							    	</#if>
							    </select>
							 <span id="dataDictItemCodeEdit"></span>  
						</td>
					<td>
						<label class=" control-label key">数据字典项父级项：</label>
					</td>
					<td>
						<select name="parentCatCode" class="form-control val" id="parentCatCodeEdit">
							    	<option value="">请选择</option>
							    	<#if dataDictItemList?exists>
							    		<#list dataDictItemList as dictItem>
							    			<option value="${dictItem.dataDictItemId}" 
							    			<#if dataDictItemp.dataDictCatCode==dictItem.dataDictCatCode>
							    			selected=selected</#if>>${dictItem.dataDictCatCode}</option>
							    		</#list>
							    	</#if>
							    </select>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">数据字典项父级项值：</label>
						</td>
						<td>
							<select name="parentItemId" class="form-control val" id="parentItemIdEdit">
							    <option value="">请选择</option>
							    	<#if carBrands?exists>
							    		<#list carBrands as carBrands>
							    			<option value="${dictItem.dataDictItemId}" 
							    			<#if dataDictItemp.itemValue==carBrands.itemValue>
							    			selected=selected</#if>>${carBrands.itemValue}</option>
							    		</#list>
							    	</#if>
							    </select>
						</td>
						<td>
							<label class=" control-label key">数据字典项值：</label>
						</td>
						<td>
							<input class="form-control val" name="itemValue" value="${dataDictItem.itemValue}"/>
							<span id="itemValueEdit"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key">扩展信息1：</label>
						</td>
						<td>
							<input class="form-control val" name="info1" value="${dataDictItem.info1}"/>
							<span id="info1Edit"></span>
						</td>
						<td>
							<label class=" control-label key">扩展信息2：</label>
						</td>
						<td>
							<input class="form-control val" name="info2" value="${dataDictItem.info2}"/>
							<button type="button" id="editinfo2" class="btn btn-info">上传文件</button>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">备注：</label>
						</td>
						<td>
							<textarea class="form-control val" name="memo">${dataDictItem.memo}</textarea>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveEditDataDictItem" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditDataDictItem" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<div class="modal fade" id="info2AddModalEdit">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传文件</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="infoForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">地址URL：</label>
                            <div class="col-md-8">
                             <input type="text" class="form-control val" placeholder="" name="info2" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">文件选择：</label>
                           <div class="col-md-8">
                                <div id="info2zhFineUploader"><span name="parkErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right btncolora" style="background:#2b94fd;margin-right:-2px !important"
							id="info2AddWEdit" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"dataDictItemEdit":"res/js/system/dataDictItemEdit"}});
		require(["dataDictItemEdit"], function (dataDictItemEdit){
			dataDictItemEdit.init();
		}); 
		
		
		var config=new Object();
		config.uploadId="info2zhFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png","txt"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "infoForm";
		//文件回显inputName
		config.inputName = "info2";
		//错误提示span标签name
		config.spanName = "parkErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		}); 
		 
	});
</script>