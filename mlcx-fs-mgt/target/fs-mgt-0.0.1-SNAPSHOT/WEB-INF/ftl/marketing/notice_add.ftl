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
	<form name="noticeAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">套餐产品新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>公告类型：</label>
						</td>
						<td>
							<select name="noticeType" class="form-control val">
				              <option value="">请选择</option>
									 <option  value="1" >公告广告</option>
								</select>
								<span name="noticeTypeAdd"></span>
						</td>
							
							<td>
								<label class=" control-label key"><span>*</span>公告名称：</label>
							</td>
							<td>
								<input class="form-control val" name="noticeName" placeholder="请输入公告名称"/>
								<span name="noticeNameAdd"></span>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>外部链接：</label>
						</td>
						<td>
							<input class="form-control val" name="externalLinkUrl" placeholder="请输入外部链接"/>
							<span name="externalLinkUrlAdd"></span>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>公告展示类型：</label>
						</td>
						<td>
							<label><input name="noticeMemberType" type="radio" value="1" />商家端</label>
                                    <label><input name="noticeMemberType" type="radio" value="0" checked/>客户端 </label>
						</td>

					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key"><span>*</span>公告排名：</label>
						</td>
						<td>
							<input class="form-control val" placeholder="请输入公告排名" name="ranking" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
							<span name="rankingAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>公告图片：</label>
						</td>
						<td class="odd-td">
						<input name="noticePicUrl" type="hidden"/>	
						<div class="img-td-upload" id="noticePicUrlImg">
							<div id="addnoticePicUrlButton" class="add-img-position">
								<h3 class="icon">+</h3>
								<h3 class="text">添加图片</h3>
							</div>
						</div>
						<span name="noticePicUrlAdd"></span>	
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>内容：</label>
						</td>
						<td colspan="3">
							<textarea name="text1" class="form-control val" id="addNoticeText1" title="Contents"></textarea>
							<span name="text1Add"></span>
						</div>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addnotice" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddnotice" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="noticePicUrlAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">公告图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="noticePicUrlAddForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="noticePicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">公告图片：</label>
                           <div class="col-md-8">
                                <div id="noticePicUploader"><span name="noticePicErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addnoticePicBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"noticeAdd":"res/js/marketing/notice_add"}});
		require(["noticeAdd"], function (noticeAdd){
			noticeAdd.init();
		});  
		var config=new Object();
		config.uploadId="noticePicUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(公告图片)
		config.storePath = "advert_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "noticePicUrlAddForm";
		//文件回显inputName
		config.inputName = "noticePicUrl1";
		//错误提示span标签name
		config.spanName = "noticePicErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		$('#addNoticeText1').summernote({
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