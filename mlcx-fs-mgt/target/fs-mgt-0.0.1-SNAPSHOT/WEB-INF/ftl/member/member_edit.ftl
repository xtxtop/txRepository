<meta charset="utf-8">

<body>
	<style>
		.con {
			margin-top: 20px;
		}
	</style>
	<div class="container-fluid backgroundColor">

		<div class="row hzlist">
			<form name="memberForm">
                <input type="hidden" name="memberNo" value="${member.memberNo}" />
				<table class="tab-table table table-border table-responsive">
					<thead class="tab-thead">
						<tr>
							<th colspan="4">会员信息编辑</th>
						</tr>
					</thead>
					<tbody class="tab-tbody">
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>头像：</label>
							</td>
							<td colspan="3" class="odd-td">
						 <input name="memberPhotoUrl" type="hidden" />
						<div class="col-sm-3 add-car-img" style="background-image: url(${imagePath!''}/${member.memberPhotoUrl});" id="memberPhotoUrlImg">
						    <div id="addMemberPhotoButton" class="add-img-position">
						    	<span class="icon">+</span>
						    </div>
						</div>
						</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>姓名：</label>
							</td>
							<td>
								<input class="form-control val" name="memberName" value="${member.memberName}" readonly/>
								<span id="memberNameEdit"></span>
							</td>
							<td>
								<label class=" control-label key">昵称：</label>
							</td>
							<td>
								<input class="form-control val" name="memberNick" value="${member.memberNick}" />
								<span id="memberNickEdit"></span>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>性别：</label>
							</td>
							<td>
								<select name="sex" class="form-control val">
								<option value="">请选择</option>
								<option value="0" <#if member.sex=0>selected="selected"</#if> >女</option>
								<option value="1" <#if member.sex=1>selected="selected"</#if> >男</option>
								<option value="" <#if member.sex==null>selected="selected"</#if> >未知</option>
							</select>
							<span id="sexEdit"></span>
							</td>
								<td>
									<label class=" control-label key">手机号：</label>
								</td>
								<td>
								<input class="form-control val" readonly name="mobilePhone" value="${member.mobilePhone}" />
									<!--<label class="control-label val">${member.mobilePhone}</label> -->
							
							<span id="mobilePhoneEdit"></span>
								</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>身份证号：</label>
							</td>
							<td>
								<input class="form-control val" name="idCard" value="${member.idCard}" readonly/>
								<span id="idCardEdit"></span>
							</td>
							<td>
								<label class=" control-label key"><span>*</span>会员类型：</label>
							</td>
							<td>
								<select name="memberType" class="form-control val">
								<option value="1" <#if member.memberType==1>selected</#if>>普通会员</option>
								<option value="2" <#if member.memberType==2>selected</#if>>集团会员</option>
							</select>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>集团名称：</label>
							</td>
							<td>
								<select name="companyId" class="form-control val">
								<option value="" <#if member.companyId==null||member.companyId==''>selected</#if>>请选择</option>
								<#list companyList as company>
									<option value="${company.companyId}" <#if member.companyId==company.companyId>selected</#if>> ${company.companyName}
									</option>
								</#list>
							</select>
							<span name="companyIdEdit"></span>
							</td>
							<td>
								<label class=" control-label key"><span>*</span>驾驶证有效期至：</label>
							</td>
							<td>
								<input class="datetimepicker form-control val" name="expirationDate" <#if member.expirationDate?exists> value="${member.expirationDate?string('yyyy-MM-dd')}"
							</#if>
							/>
							<span id="expirationDateEdit"></span>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>档案编号：</label>
							</td>
							<td>
								<input class="form-control val" name="licenseId" value="${member.licenseId}" readonly/>
							</td>
							<#if member.invitationCode!=null>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->邀请码：</label>
							</td>
							<td>
								<label class="control-label val">${member.invitationCode}</label>
							</td>
							</#if>
						</tr>
						<tr>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->邀请人数：</label>
								</td>
								<td>
									<label class="control-label val invitePerson">
                					<input type="hidden" id="EditmemberNo" value="${member.memberNo}"/>
                					<input type="button" id="Editmbs"  value="${mbs}" class="btn btn-default" />
            					</label>
								</td>

									<td>
										<label class=" control-label key">会员积分：</label>
									</td>
									<td>
										<label class="control-label val">
	                				${member.memberPointsValues}
	                			</label>
									</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key">会员等级：</label>
							</td>
							<td>
								<label class="control-label val">
                				${member.levelName}
                			</label>
							</td>
							<td>
								<label class=" control-label key">认证情况：</label>
							</td>
							<td>
								<label class="control-label val">
                				<#if member.censorStatus==0>
								未认证
								<#elseif member.censorStatus==1>
                				已认证
                				<#elseif member.censorStatus==2>
                				待认证
                				<#else>
                				未通过
                				</#if>
                			</label>
							</td>
						</tr>

						<tr>
							<td>
								<label class=" control-label key">注册时间：</label>
							</td>
							<td>
								<label class="control-label val">
                			<#if member.registerTime?exists>
                				${member.registerTime?string('yyyy-MM-dd HH:mm:ss')}
                			</#if>
                			</label>
							</td>
							<td>
								<label class=" control-label key">状态：</label>
							</td>
							<td>
								<label class="control-label val" >
                				<#if member.isBlacklist==0>
								正常
								<#elseif member.isBlacklist==1>
                				黑名单
                				</#if>
                			</label>
							</td>
						</tr>

						<tr class="add-car-last-tr">
							<td class="photo-line-height">
								<label class=" control-label key"><#-- <span>*</span> -->驾驶证照片：</label>
							</td>
							<td class="odd-td">
                      	<input name="driverLicensePhotoUrl1" type="hidden" />
						<div class="img-td-upload" style="background-image: url(${imagePath!''}/${member.driverLicensePhotoUrl1});"  id="driverLicensePhotoUrl1Img">
						    <div id="addDriverLicensePhotoUrl1Button" class="add-img-position">
						    	<h3 class="icon">+</h3>
						    	<h3 class="text">添加图片</h3>
						    </div>
						</div>
						</td>

							<td class="photo-line-height">
								<label class=" control-label key"><#-- <span>*</span> -->手持证件照：</label>
							</td>
							<td class="odd-td">
                      	<input name="idCardPhotoUrl" type="hidden" />
						<div class="img-td-upload" style="background-image: url(${imagePath!''}/${member.idCardPhotoUrl});"  id="idCardPhotoUrlImg">
						    <div id="addIdCardPhotoUrlButton" class="add-img-position">
						    	<h3 class="icon">+</h3>
						    	<h3 class="text">添加图片</h3>
						    </div>
						</div>
						</td>
						</tr>
						<tr>
							<#if member.paperName??>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->${member.paperName}：</label>
								</td>
								<td colspan="3">
									<img width="200" name="paperUrlImg" src="${imagePath!''}/${member.paperUrl}" />
								<button type="button" id="addpaperUrlImgButton" class="btn btn-default">上传图片</button>
								<input name="paperUrl" type="hidden" />
								</td>
						</tr>
						<#else>
							</#if>
							<tr>
								<td>
									<label class=" control-label key">剩余押金：</label>
								</td>
								<td>
									<label class="control-label val">${member.residueDeposit}元</label>
								</td>
								<td>
                                  <label class=" control-label key">欠款金额：</label>
								</td>
								<td>
                                 <input type="hidden" id="memberNoPayAmount" value="${member.memberNo}">
				<input type="button" id="memberPayAmount" value="${member.noPayAmount}元" class="btn btn-default">
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key">违章记录：</label>
								</td>
								<td>
									<input type="hidden" id="memberillegalNumNo" value="${member.memberNo}"> 
				<input type="button" id="EditillegalNum" value="${member.illegalNum}笔" class="btn btn-default">
								</td>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->会员订单：</label>
								</td>
								<td>
									<label class="control-label val">
                  <input type="hidden" id="EditmemberNo" value="${member.memberNo}">
                  <input type="button" id="EditmemberOrder"  value="${member.orderNum}笔" class="btn btn-default">
              </label>
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span>*</span>创建时间：</label>
								</td>
								<td>
									<label class="control-label">${member.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
								<td>
									<label class=" control-label key"><span>*</span>更新时间：</label>
								</td>
								<td>
									<label class="control-label">${member.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
							</tr>
					</tbody>
					<tfoot class="tab-tfoot">
						<tr>
							<td colspan="2"><button type="button" id="saveMember" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeMember" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

						</tr>
					</tfoot>
				</table>

			</form>

		</div>
	</div>
<!--会员头像上传 弹出框-->
	<div class="modal fade" id="memberPhotoUrlEditModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">会员头像上传</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="memberPhotoUrlEditForm">
						<div class="form-group">
							<label class="col-md-3 control-label">图片URL：</label>
							<div class="col-md-8">
								<input type="text" class="form-control val" placeholder="" name="memberPhotoUrl1" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">头像：</label>
							<div class="col-md-8">
								<div id="memberPhotoUploader"><span name="memberPhotoErrorInfo"></span></div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="addMemberPhotoBtn" value="确定">
					</div>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!--驾驶证图片1上传弹出框-->
	<div class="modal fade" id="DriverLicensePhotoUrl1EditModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">驾驶证图片上传</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="driverLicensePhotoUrl1EditForm">
						<div class="form-group">
							<label class="col-md-3 control-label">图片URL：</label>
							<div class="col-md-8">
								<input type="text" class="form-control val" placeholder="" name="driverLicensePhotoUrl1" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">头像：</label>
							<div class="col-md-8">
								<div id="driverLicensePhotoUrl1Uploader"><span name="driverLicensePhotoUrl1ErrorInfo"></span></div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="addDriverLicensePhotoUrl1Btn" value="确定">
					</div>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!--手持证件照上传弹出框-->
	<div class="modal fade" id="idCardPhotoUrlEditModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">手持证件照上传</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="idCardPhotoUrlEditForm">
						<div class="form-group">
							<label class="col-md-3 control-label">图片URL：</label>
							<div class="col-md-8">
								<input type="text" class="form-control val" placeholder="" name="idCardPhotoUrl" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">头像：</label>
							<div class="col-md-8">
								<div id="idCardPhotoUrlUploader"><span name="idCardPhotoUrlErrorInfo"></span></div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="addidCardPhotoUrlBtn" value="确定">
					</div>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!--其他证件弹出框-->
	<div class="modal fade" id="addpaperUrlImg">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">其他证件照上传</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="paperUrlError">
						<div class="form-group">
							<label class="col-md-3 control-label">图片URL：</label>
							<div class="col-md-8">
								<input type="text" class="form-control val" placeholder="" name="paperUrl" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">其他证件：</label>
							<div class="col-md-8">
								<div id="idpaperUrlUploader"><span name="paperUrlErrorInfo"></span></div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="addpaperUrlBtn" value="确定">
					</div>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!--驾驶证图片2上传弹出框-->
	<div class="modal fade" id="DriverLicensePhotoUrl2EditModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">驾驶证图片上传</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="driverLicensePhotoUrl2EditForm">
						<div class="form-group">
							<label class="col-md-3 control-label">图片URL：</label>
							<div class="col-md-8">
								<input type="text" class="form-control val" placeholder="" name="driverLicensePhotoUrl2" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">头像：</label>
							<div class="col-md-8">
								<div id="driverLicensePhotoUrl2Uploader"><span name="driverLicensePhotoUrl2ErrorInfo"></span></div>
							</div>
						</div>

					</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure" id="addDriverLicensePhotoUrl2Btn" value="确定">
					</div>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!--
<script type="text/javascript" src="${basePath!'' }/res/js/member/member_edit.js"></script>
-->
	<script type="text/javascript">
		$(function() {
			require.config({
				paths: {
					"memberEdit": "res/js/member/member_edit"
				}
			});
			require(["memberEdit"], function(memberEdit) {
				memberEdit.init();
			});
			var config = new Object();
			config.uploadId = "memberPhotoUploader";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			config.storePath = "member_icon";
			config.itemLimit = 1;
			config.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
			config.sizeLimit = 500 * 1024;
			config.minSizeLimit = 10 * 1024;
			config.formName = "memberPhotoUrlEditForm";
			//文件回显inputName
			config.inputName = "memberPhotoUrl1";
			//错误提示span标签name
			config.spanName = "memberPhotoErrorInfo";
			require.config({
				paths: {
					"upload": "res/js/resource/uploadFile"
				}
			});
			require(["upload"], function(upload) {
				upload.init(config);
			});

			var config1 = new Object();
			config1.uploadId = "driverLicensePhotoUrl1Uploader";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			config1.storePath = "member_doc";
			config1.itemLimit = 1;
			config1.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
			config1.sizeLimit = 500 * 1024;
			config1.minSizeLimit = 10 * 1024;
			config1.formName = "driverLicensePhotoUrl1EditForm";
			//文件回显inputName
			config1.inputName = "driverLicensePhotoUrl1";
			//错误提示span标签name
			config1.spanName = "driverLicensePhotoUrl1ErrorInfo";
			require.config({
				paths: {
					"upload": "res/js/resource/uploadFile"
				}
			});
			require(["upload"], function(upload) {
				upload.init(config1);
			});

			var IdCard = new Object();
			IdCard.uploadId = "idCardPhotoUrlUploader";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			IdCard.storePath = "member_doc";
			IdCard.itemLimit = 1;
			IdCard.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
			IdCard.sizeLimit = 500 * 1024;
			IdCard.minSizeLimit = 10 * 1024;
			IdCard.formName = "idCardPhotoUrlEditForm";
			//文件回显inputName
			IdCard.inputName = "idCardPhotoUrl";
			//错误提示span标签name
			IdCard.spanName = "idCardPhotoUrlErrorInfo";
			require.config({
				paths: {
					"upload": "res/js/resource/uploadFile"
				}
			});
			require(["upload"], function(upload) {
				upload.init(IdCard);
			});

			//其他证件
			var paperUrlCard = new Object();
			paperUrlCard.uploadId = "idpaperUrlUploader";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			paperUrlCard.storePath = "member_doc";
			paperUrlCard.itemLimit = 1;
			paperUrlCard.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
			paperUrlCard.sizeLimit = 500 * 1024;
			paperUrlCard.minSizeLimit = 10 * 1024;
			paperUrlCard.formName = "paperUrlError";
			//文件回显inputName
			paperUrlCard.inputName = "paperUrl";
			//错误提示span标签name
			paperUrlCard.spanName = "paperUrlErrorInfo";
			require.config({
				paths: {
					"upload": "res/js/resource/uploadFile"
				}
			});
			require(["upload"], function(upload) {
				upload.init(paperUrlCard);
			});

			var config2 = new Object();
			config2.uploadId = "driverLicensePhotoUrl2Uploader";
			//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
			config2.storePath = "member_doc";
			config2.itemLimit = 1;
			config2.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
			config2.sizeLimit = 500 * 1024;
			config2.minSizeLimit = 10 * 1024;
			config2.formName = "driverLicensePhotoUrl2EditForm";
			//文件回显inputName
			config2.inputName = "driverLicensePhotoUrl2";
			//错误提示span标签name
			config2.spanName = "driverLicensePhotoUrl2ErrorInfo";
			require.config({
				paths: {
					"upload": "res/js/resource/uploadFile"
				}
			});
			require(["upload"], function(upload) {
				upload.init(config2);
			});
		});
		$(function() {
			$(".datetimepicker").datetimepicker({
				language: "zh-CN",
				autoclose: true, //选中之后自动隐藏日期选择框
				clearBtn: true, //清除按钮
				todayBtn: true, //今日按钮
				format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
			});
		});
	</script>