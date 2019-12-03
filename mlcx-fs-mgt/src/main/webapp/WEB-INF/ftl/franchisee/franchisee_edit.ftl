<meta charset="utf-8">

<div class="container-fluid backgroundColor">
	<form name="franchiseeEditForm">
		<div class="row hzlist">
			<input type="hidden" name="franchiseeNo" value="${franchisee.franchiseeNo}"  />
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">加盟商编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>加盟商名称：</label>
						</td>
						<td>
							<input class="form-control val" name="franchiseeName" value="${franchisee.franchiseeName}"  />
							<span name="franchiseeNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>加盟商全称：</label>
						</td>
						<td>
							<input class="form-control val" name="franchiseeFullName" value="${franchisee.franchiseeFullName}" />
							<span name="franchiseeFullNameEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>分润比例（按车）：</label>
						</td>
						<td>
							<input class="form-control val" name="carProportion" value="${franchisee.carProportion}"/>
							<span name="carProportionEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>分润比例（按场站）：</label>
						</td>
						<td>
							<input class="form-control val" name="parkProportion" value="${franchisee.parkProportion}"/>
							<span name="parkProportionEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>联系人：</label>
						</td>
						<td>
							<input class="form-control val" name="contacts" value="${franchisee.contacts}"/>
							<span name="contactsEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>联系人手机：</label>
						</td>
						<td>								
							 <input class="form-control val" name="contactsPhone" value="${franchisee.contactsPhone}"/>
							<span name="contactsPhoneEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>联系人邮箱：</label>
						</td>
						<td>
                            <input class="form-control val" name="mailbox" value="${franchisee.mailbox}"/>
                            <span name="mailboxEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>备注：</label>
						</td>
						<td>
							<textarea class="form-control val" name="memo" >${franchisee.memo}</textarea>
							<span name="memoEdit"></span>
						</td>
					</tr>
					<tr class="add-car-last-tr">
						<td class="photo-line-height">
							<label class="  control-label key">相关证件：</label>
			                <input name="franchiseePhotoUrl1" type="hidden" value="${franchisee.franchiseePhotoUrl1}"/>
						</td>
						<td class="odd-td">
						<div class="img-td-upload" style="background-image: url(${imagePath!''}/${franchisee.franchiseePhotoUrl1});"  id="franchiseePicUrlImg">
						    <div id="editFranchiseePhoto" class="add-img-position">
						    	<h3 class="icon">+</h3>
						    	<h3 class="text">添加图片</h3>
						    </div>
						</div>
						<span name="franchiseePhotoUrl1Edit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="editFranchisee" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closefranchiseeEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
      
    </tr>
  </tfoot>
  </table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="franchiseeEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="franchiseephotoForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="franchiseePhotoUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="franchiseeFineUploader"><span name="franchiseeErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editFranchiseePhotoBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
$(function(){
	  require.config({paths: {"franchiseeEdit":"res/js/franchisee/franchisee_edit"}});
		require(["franchiseeEdit"], function (franchiseeEdit){
			franchiseeEdit.init();
		});  
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        
        var config=new Object();
		config.uploadId="franchiseeFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "member_doc";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "franchiseephotoForm";
		//文件回显inputName
		config.inputName = "franchiseePhotoUrl1";
		//错误提示span标签name
		config.spanName = "franchiseeErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
        
});
</script>