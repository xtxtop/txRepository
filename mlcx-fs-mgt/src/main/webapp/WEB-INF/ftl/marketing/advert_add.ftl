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
	<form name="advertAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增活动</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>活动类型：</label>
						</td>
						<td>
							<select name="advertType" class="form-control val">
				              <option value="">请选择</option>
									 <option  value="1" >活动广告</option>
								</select>
							<span name="advertTypeAdd"></span>	
						</td>
						<td>
							<label class=" control-label key"><span>*</span>活动名称：</label>
						</td>
						<td>
							<input class="form-control val" name="advertName" placeholder="请输入活动名称"/>
							<span name="advertNameAdd"></span>
							</td>
					</tr>
					

					
					<tr>
						
							<td>
								<label class=" control-label key"><span>*</span>启动app活动页展示：</label>
							</td>
							<td>
								<label><input name="isStartAdvert" type="radio" value="1" />是 </label>
                                    <label><input name="isStartAdvert" type="radio" value="0" checked/>否 </label>							
							</td>
						<td>
							<label class=" control-label key"><span>*</span>活动页展示类型：</label>
						</td>
						<td>
							<select name="advertMemberType" class="form-control val">
								  		<option value="">请选择</option>
										<option  value="0">客户端</option>
										<option  value="1">商家端</option>
										<option  value="2">营销端</option>
									</select>
							<span name="advertMemberTypeAdd"></span>
						</td>
					</tr>
					
					<tr class="add-car-last-tr">
						
						<td>
							<label class=" control-label key"><span>*</span>活动排名：</label>
						</td>
						<td>
							<input class="form-control val" placeholder="请输入活动排名" name="ranking" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" />
							<span name="rankingAdd"></span>
						</td>
						<td>
								<label class=" control-label key"><span>*</span>活动图片：</label>
						</td>
						<td class="odd-td">
						<input name="advertPicUrl" type="hidden"/>
						<div class="img-td-upload" id="advertPicUrlImg">
							<div id="addAdvertPicUrlButton" class="add-img-position">
								<h3 class="icon">+</h3>
								<h3 class="text">添加图片</h3>
							</div>
						</div>
						<span name="advertPicUrlAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>跳转类型：</label>
						</td>
						<td>
							<select name="jumpType" class="form-control val" id="jumpTypeId">
				                    <option value="">请选择</option>
									 <option  value="1" >内部链接</option>
									  <option  value="2" >外部链接</option>
									   <option  value="3" >文章内容</option>
								</select>
							<span name="jumpTypeAdd"></span>
						</td>

						<td class="form-group nativeUrlType">
							<label class=" control-label key"><span></span>内部链接跳转路径：</label>
						</td>
						<td class="form-group nativeUrlType">
							<select name="nativeUrlType" class="form-control val">
							  		<option value="">请选择</option>
									 <option  value="1" >邀请分享</option>
									  <option  value="2" >交纳押金</option>
									   <option  value="3" >充值余额</option>
									    <option  value="4" >身份认证</option>
								</select>
							<span name="nativeUrlTypeAdd"></span>
						</td>
                           <td class="form-group LinkUrl">
							<label class=" control-label key"><span></span>外部链接：</label>
						</td>
						<td class="form-group LinkUrl">
							<input class="form-control val" name="externalLinkUrl" />
							<span name="externalLinkUrlAdd"></span>
						</td>
						<td colspan="2"></td>
						
						
					</tr>
					<tr class="form-group">
								<td>
									<label class=" control-label key"><span>*</span>内容：</label>
								</td>
								<td colspan="3">
									<textarea name="text1" class="form-control val" id="addText1" title="Contents"></textarea>
									<span name="text1Add"></span>
									</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addAdvert" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddAdvert" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="advertPicUrlAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">活动图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="advertPicUrlAddForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-6">
                               <input type="text" class="form-control val" placeholder="" name="advertPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">活动图片：</label>
                           <div class="col-md-6">
                                <div id="advertPicUploader"><span name="advertPicErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addAdvertPicBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"advertAdd":"res/js/marketing/advert_add"}});
		require(["advertAdd"], function (advertAdd){
			advertAdd.init();
		});  
		var config=new Object();
		config.uploadId="advertPicUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(活动图片)
		config.storePath = "advert_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "advertPicUrlAddForm";
		//文件回显inputName
		config.inputName = "advertPicUrl1";
		//错误提示span标签name
		config.spanName = "advertPicErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		$('#addText1').summernote({
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