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
	<form name="noticeEditFrom">
		<input type="hidden" name="noticeNo" value="${notice.noticeNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">公告编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>公告类型：</label>
						</td>
						<td>
							<select name="noticeType" class="form-control val">
									 <option  value="1" <#if notice.noticeType==1>selected="selected"</#if> >公告广告</option>
								</select>
								<span name="noticeTypeEdit"></span>
						</td>
							
							<td>
								<label class=" control-label key"><span>*</span>公告名称：</label>
							</td>
							<td>
								<input class="form-control val" name="noticeName" value="${notice.noticeName}"/>
								<span name="noticeNameEdit"></span>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>外部链接：</label>
						</td>
						<td>
							<input class="form-control val" name="externalLinkUrl" value="${notice.externalLinkUrl}"/>
							<span name="externalLinkUrlEdit"></span>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>公告展示类型：</label>
						</td>
						<td>
							<label><input name="noticeMemberType" type="radio" <#if  notice.noticeMemberType==1>checked</#if> value="1" />商家端 </label>
                                    <label><input name="noticeMemberType" type="radio" <#if  notice.noticeMemberType==0>checked</#if>value="0" />客户端 </label>
						</td>

					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key"><span>*</span>公告排名：</label>
						</td>
						<td>
							<input class="form-control val" name="ranking"  onkeyup="this.value=this.value.replace(/\D/g,'')" value="${notice.ranking}"/>
							<span name="rankingEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>公告图片：</label>
						</td>
						<td class="odd-td">
							<input name="noticePicUrl" type="hidden" value="${notice.noticePicUrl}"/>
							<div class="img-td-upload" style="background-image: url(${imagePath!''}/${notice.noticePicUrl});" id="noticePicUrlImgEdit">
								<div id="editNoticePicUrlButton" class="add-img-position">
									<h3 class="icon">+</h3>
									<h3 class="text">添加图片</h3>
								</div>
							</div>
							<span name="noticePicUrlEdit"></span>	
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>内容：</label>
						</td>
						<td colspan="3">
							<textarea name="text1" class="form-control val" id="editText1" title="Contents">${notice.text1}</textarea>
							<span name="text1Edit"></span>
						</div>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editNotice" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditNotice" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="noticePicUrlEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">公告图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="noticePicUrlEditForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="noticePicUrl" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">公告图片：</label>
                           <div class="col-md-8">
                                <div id="noticePicUploaderEdit"><span name="noticePicEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editNoticePicBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"noticeEdit":"res/js/marketing/notice_edit"}});
		require(["noticeEdit"], function (noticeEdit){
			noticeEdit.init();
		});  
		var config=new Object();
		config.uploadId="noticePicUploaderEdit";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(公告图片)
		config.storePath = "advert_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "noticePicUrlEditForm";
		//文件回显inputName
		config.inputName = "noticePicUrl";
		//错误提示span标签name
		config.spanName = "noticePicEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		$('#editText1').summernote({
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