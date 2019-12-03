<meta charset="utf-8">

<body>
	<style>
		
		.con {
			margin-top: 20px;
		}
	</style>
	<div class="container-fluid backgroundColor">

		<div class="row hzlist">
			<form name="memberCencorForm">
				<input type="hidden" name="memberNo" value="${member.memberNo}" />
				<input type="hidden" id="memberNextNo" name="memberNextNo" value="${memberNextNo}" />
				<input type="hidden" id="memberLowNo" name="memberLowNo" value="${memberLowNo}" />

				<input type="hidden" id="typeUp" value="${typeUp}" />
				<input type="hidden" id="typeDown" value="${typeDown}" />
				<table class="tab-table table table-border table-responsive">
					<thead class="tab-thead">
						<tr>
							<th colspan="4">会员信息审核</th>
						</tr>
					</thead>
					<tbody class="tab-tbody">
						<tr>
							<td>
								<label class=" control-label key">*姓名：</label>
							</td>
							<td>
								<input class="form-control val" name="memberName" value="${member.memberName}" readonly/>
								<span id="memberNameCencor"></span>
							</td>
							<td>
								<label class=" control-label key">*手机号：</label>
							</td>
							<td>
								<label class="control-label val">${member.mobilePhone}</label>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key">*性别：</label>
							</td>
							<td>
								<select name="sex" class="form-control val">
									<option value="">请选择</option>
									<option value="0" <#if member.sex=0>selected="selected"</#if> >女</option>
									<option value="1" <#if member.sex=1>selected="selected"</#if> >男</option>
									<option value="" <#if member.sex==null>selected="selected"</#if> >未知</option>
								</select>
								<span id="sexCencor"></span>
							</td>
							<td>
								<label class=" control-label key">*身份证号：</label>
							</td>
							<td>
								<input class="form-control val" name="idCard" value="${member.idCard}" readonly/>
								<span id="idCardCencor"></span>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key">*驾驶证档案号：</label>
							</td>
							<td>
								<input class="form-control val" name="licenseid" value="${member.licenseId}" readonly/>
								<span id="licenseidCencor"></span>
							</td>
							<td>
								<label class=" control-label key">*驾驶证有效期至：</label>
							</td>
							<td>
								<input class="datetimepicker form-control val" name="expirationDate" <#if member.expirationDate?exists> value="${member.expirationDate?string('yyyy-MM-dd')}"
								</#if>
								/>
								<span id="expirationDateCencor"></span>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key">驾驶证照片：</label>
							</td>
							<td>
								<img class="imgEnlarge" src="${imagePath!''}/${member.driverLicensePhotoUrl1}" width="200" />
							</td>
							<td>
								<label class=" control-label key">手持证件照：</label>
							</td>
							<td>
								<img class="imgEnlarge" src="${imagePath!''}/${member.idCardPhotoUrl}" width="200" />
							</td>
						</tr>

						<tr>
							<#if member.paperName??>
								<td>
									<label class=" control-label key">${member.paperName}：</label>
								</td>
								<td>
									<img src="${imagePath!''}/${member.paperUrl}" width="200" />
								</td>
								<#else>
							</#if>

							<td>
								<label class=" control-label key">*审核结果：</label>
							</td>
							<td>
								<label class="control-label val"><input name="censorStatus" type="radio" value="1" checked="checked" />通过 </label>
								<label class="control-label val"><input name="censorStatus" type="radio" value="3" />不通过</label>
							</td>
							<td>
								<label class=" control-label key reason" id="cencoeMemoLabel">审核备注：</label>
							</td>
							<td>
								<label class="control-label val">
								<textarea id ="cencorMemo" name="cencorMemo" cols="40" rows="2"></textarea>
								</label>
								<span id="cencorMemoCencor"></span>
							</td>
						</tr>

					</tbody>
					<tfoot class="tab-tfoot">
						<tr style="text-align:center;">

							<td colspan="4">
								<button type="button" id="closeCencorMember" class="btn-new-type-edit ">
                返回
            </button>
								<button type="button" id="downCencorMember" class="btn-new-type-edit">
                下一个
            </button>
								<button type="button" id="upCencorMember" class="btn-new-type-edit">
                上一个
            </button>
								<button type="button" id="saveCencorMember" class="btn-new-type-edit">
                提交
            </button>
							</td>
						</tr>
					</tfoot>
				</table>
			</form>

		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			require.config({
				paths: {
					"memberCencor": "res/js/member/member_cencor"
				}
			});
			require(["memberCencor"], function(memberCencor) {
				memberCencor.init();
			});
		});
		$(function() {
			$(".datetimepicker").datetimepicker({
				minView: "month",
				language: "zh-CN",
				autoclose: true, //选中之后自动隐藏日期选择框
				clearBtn: true, //清除按钮
				todayBtn: true, //今日按钮
				format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
			});
		});
	</script>