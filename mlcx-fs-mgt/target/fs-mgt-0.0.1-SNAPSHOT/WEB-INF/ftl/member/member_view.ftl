<meta charset="utf-8">

<body>
	<style>
		.con {
			margin-top: 20px;
		}
	</style>
	<div class="container-fluid backgroundColor">

		<div class="row hzlist">
			<form name="memberListForm">

				<table class="tab-table table table-border table-responsive">
					<thead class="tab-thead">
						<tr>
							<th colspan="4">会员信息详情</th>
						</tr>
					</thead>
					<tbody class="tab-tbody">
						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->头像：</label>
							</td>
							<td colspan="3" class="rightImg">
								<img src="${imagePath!''}/${member.memberPhotoUrl}" width="100" height="80" />
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->姓名：</label>
							</td>
							<td>
								<label class="control-label val">${member.memberName}</label>
							</td>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->昵称：</label>
							</td>
							<td>
								<label class="control-label val">${member.memberNick}</label>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->手机号：</label>
							</td>
							<td>
								<label class="control-label val">${member.mobilePhone}</label>
							</td>
							<#if member.invitationCode!=null>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->邀请码：</label>
								</td>
								<td>
									<label class="control-label val">${member.invitationCode}</label>
								</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->注册邀请人：</label>
							</td>
							<td>
								<#if memberReferee.memberName !=null>
									<label class="control-label val"> ${memberReferee.memberName}</label>
									<#else>
										<label class="control-label val"> ${memberReferee.mobilePhone}</label>
								</#if>
							</td>
							</#if>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->邀请人数：</label>
							</td>
							<td>
								<label class="control-label val">
                					<input type="hidden" id="memberNo" value="${member.memberNo}"/>
                					<input type="button" class="input-click-con" id="mbs"  value="${mbs}"/>
            					</label>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->性别：</label>
							</td>
							<td>
								<label class="control-label val">
								<#if member.sex==0>
								女
								<#elseif member.sex==1>
								男
								<#else>
								未知
								</#if>
							</label>
							</td>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->身份证号：</label>
							</td>
							<td>
								<label class="control-label val">${member.idCard}</label>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->档案编号：</label>
							</td>
							<td>
								<label class="control-label val">${member.licenseId}</label>
							</td>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->驾驶证有效期至：</label>
							</td>
							<td>
								<#if member.expirationDate?exists>
									<label class="control-label val">${member.expirationDate?string('yyyy-MM-dd')}</label>
								</#if>
							</td>
						</tr>
						<tr>
							<#if member.memberType==1>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->会员类型：</label>
								</td>
								<td>
									<label class="control-label val">
										普通
	                				</label>
								</td>
								<#else>
							<td>
								<label class=" control-label key"><span>*</span>会员类型：</label>
							</td>
							<td>
								<label class="control-label val">
										集团
	                				</label>
							</td>

							<td>
								<label class=" control-label key"><#-- <span>*</span> -->集团名称：</label>
							</td>
							<td>
								<label class="control-label val">
										${member.companyName}
	                				</label>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>员工类型：</label>
							</td>
							<td>
								<label class="control-label val">
	                					<#if companyPerson.personType==1>
	                					员工
	                					<#elseif companyPerson.personType==0>
	                					非员工
	                					<#else>
	                					</#if>
	                				</label>
							</td>
							</#if>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->会员等级：</label>
							</td>
							<td>
								<label class="control-label val">
                				${member.levelName}
                			</label>
							</td>
						</tr>
						
						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->会员积分：</label>
							</td>
							<td>
								<label class="control-label val">
                				${member.memberPointsValues}
                			</label>
							</td>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->邀请码：</label>
							</td>
							<td>
								<label class="control-label val">
                				${member.invitationCode}
                			</label>
							</td>
						</tr>
						<tr>

							<td>
								<label class=" control-label key"><#-- <span>*</span> -->所属集团：</label>
							</td>
							<td>
								<label class="control-label val">
                				${member.companyName}
                			</label>
							</td>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->认证情况：</label>
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
								<label class=" control-label key"><#-- <span>*</span> -->注册时间：</label>
							</td>
							<td>
								<label class="control-label val">
                				<#if member.registerTime?exists>
                					${member.registerTime?string('yyyy-MM-dd HH:mm:ss')}
                				</#if>
                			</label>
							</td>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->状态：</label>
							</td>
							<td>
								<label class="control-label val">
                				<#if member.isBlacklist==0>
								正常
								<#elseif member.isBlacklist==1>
                				黑名单
                				</#if>
                			</label>
							</td>
						</tr>

						<tr>
							<td>
								<label class=" control-label key"><#-- <span>*</span> -->驾驶证照片：</label>
							</td>
							<td>
								<img style="height: 90px;" src="${imagePath!''}/${member.driverLicensePhotoUrl1}" width="200" />
							</td>

							<td>
								<label class=" control-label key"><#-- <span>*</span> -->手持证件照：</label>
							</td>
							<td>
								<img style="height: 90px;" src="${imagePath!''}/${member.idCardPhotoUrl}" width="200" />
							</td>
						</tr>
						<tr>
							<#if member.paperName??>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->${member.paperName}：</label>
								</td>
								<td colspan="3">
									<img class="imgEnlarge" src="${imagePath!''}/${member.paperUrl}" width="200" />
								</td>
						</tr>
						<#else>
							</#if>
							<tr>
								<td>
									<label class="col-sm-3 control-label key"><span>*</span>审核结果：</label>
								</td>
								<td>
									<label class="control-label val">
                				<#if member.censorStatus==0>
								未认证
								<#elseif member.censorStatus==1>
                				已认证
                				<#elseif member.censorStatus==1>
                				待认证
                				<#else>
                				未通过
                				</#if>
                			</label>
								</td>
								<td>
									<label class=" control-label key" style="margin-left:28px;"><#-- <span>*</span> -->审核人：</label>
								</td>
								<td>
									<label class="control-label val">${sysUser.realName} </label>
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key" style="margin-left:16px;"><#-- <span>*</span> -->审核备注：</label>
								</td>
								<td colspan="3">
									<textarea rows="1" cols="100" readonly>${member.cencorMemo}</textarea>
								</td>
							</tr>
							<tr>

								<td>
									<label class=" control-label key"><#-- <span>*</span> -->剩余押金：</label>
								</td>
								<td>
									<label class="control-label val">${member.residueDeposit}元</label>
								</td>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->欠款金额：</label>
								</td>
								<td>
									<input type="hidden" id="memberNoPayAmountview" value="${member.memberNo}">
									<input type="button" id="memberPayAmountview" class="input-click-con" value="${member.noPayAmount}元">
								</td>
							</tr>
							<tr>

								<td>
									<label class=" control-label key"><#-- <span>*</span> -->会员订单：</label>
								</td>
								<td>
									<label class="control-label val">
                					<input type="hidden" id="memberNo" value="${member.memberNo}">
                					<input type="button" class="input-click-con" id="memberOrder"  value="${member.orderNum}笔">
            					</label>
								</td>
								<td>
									<label class=" control-label key"><#-- <span>*</span> -->违章记录：</label>
								</td>
								<td>
									<input type="hidden" id="memberillegalNumNoview" value="${member.memberNo}">
									<input type="button" class="input-click-con" id="viewllegalNum" value="${member.illegalNum}笔">
								</td>
							</tr>

							<tr>

								<td>
									<label class=" control-label key"><#-- <span>*</span> -->审核时间：</label>
								</td>
								<td>
									<label class="control-label val">${member.cencorTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
								<td>
									<label class=" control-label key"><span>*</span>创建时间：</label>
								</td>
								<td>
									<label class="control-label val">${member.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
							</tr>

							<tr>

								<td>
									<label class=" control-label key"><span>*</span>更新时间：</label>
								</td>
								<td>
									<label class="control-label val">${member.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</td>
								<td></td>
								<td></td>
							</tr>
					</tbody>
					<tfoot class="tab-tfoot">
						<tr style="text-align: center;">
							<td colspan="4"><button type="button" id="closeMember" class="btn-new-type-edit">
                关闭
            </button></td>

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
					"memberEdit": "res/js/member/member_edit"
				}
			});
			require(["memberEdit"], function(memberEdit) {
				memberEdit.init();
			});
		});
	</script>
</body>