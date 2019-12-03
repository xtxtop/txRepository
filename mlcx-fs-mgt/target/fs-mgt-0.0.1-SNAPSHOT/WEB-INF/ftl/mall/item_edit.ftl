<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="itemEditFrom">
		<input type="hidden" name="itemNo" value="${item.itemNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">商品编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>商品名称：</label>
						</td>
						<td>
							<input class="form-control val" name="itemName" value="${item.itemName}"/>
							<span name="itemNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>积分：</label>
						</td>
						<td>
							<input class="form-control val" name="points" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${item.points}"/>
							<span name="pointsAdd"></span>
						</td>
					</tr>
						<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key"><span>*</span>商品图片：</label>
						</td>
						<td class="odd-td">
						<input name="picUrl" type="hidden" value="${item.picUrl}"/>
						<div class="img-td-upload" style="background-image: url(${imagePath!''}/${item.picUrl});" id="picUrlImgEdit">
							<div id="editPicUrlButton" class="add-img-position">
							 <h3 class="icon">+</h3>
							 <h3 class="text">添加图片</h3>
							</div>
						</div>
						<span name="picUrlEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>商品分类：</label>
						</td>
						<td>
							<div class="" id="itemSortTreeSelItemEdit">
							</div>
	                        <input type="hidden" id="formSortNo" name="sortNo" value="${item.sortNo}">
	                        <span name="sortNoEdit"></span>
						</td>
						
					</tr>
					<tr>
				
						<td>
							<label class=" control-label key"><span>*</span>数量：</label>
						</td>
						<td>
							<input class="form-control val" name="num" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${item.num}"/>
							<span name="numAdd"></span>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
							<td>
							<label class=" control-label key"><span>*</span>描述：</label>
						</td>
						<td colspan="3">
							<textarea name="content" class="form-control val" id="editContent" title="Contents">${item.content}</textarea>
							<span name="contentEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editItem" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditItem" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="picUrlEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">商品图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="picUrlEditForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="picUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">商品图片：</label>
                           <div class="col-md-8">
                                <div id="picUploaderEdit"><span name="picEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editPicBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"itemEdit":"res/js/mall/item_edit"}});
		require(["itemEdit"], function (itemEdit){
			itemEdit.init();
		});  
		var config=new Object();
		config.uploadId="picUploaderEdit";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,item_photo(商品图片)
		config.storePath = "item_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "picUrlEditForm";
		//文件回显inputName
		config.inputName = "picUrl1";
		//错误提示span标签name
		config.spanName = "picEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		$('#editContent').summernote({
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