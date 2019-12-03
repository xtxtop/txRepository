<meta charset="utf-8">

<body>
	<style>
		.con {
			margin-top: 20px;
		}
	</style>
	<div class="container-fluid backgroundColor">

		<div class="row hzlist">
			<form name="accountBalanceListForm">

				<table class="tab-table table table-border table-responsive">
					<thead class="tab-thead">
						<tr>
							<th colspan="4">会员详情</th>
						</tr>
					</thead>
					<tbody class="tab-tbody">
						<tr>
						  <td>
                                <label class=" control-label key">姓名：</label>
                            </td>
                            <td>
                                <label class="control-label val">${accountBalance.memberName}</label>
                            </td>
							<td>
								<label class=" control-label key">头像：</label>
							</td>
							<td class="rightImg">
								<img src="${imagePath!''}/${accountBalance.memberPhotoUrl}" width="100" height="80" />
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key">昵称：</label>
							</td>
							<td>
								<label class="control-label val">${accountBalance.memberNick}</label>
							</td>
							<td>
								<label class=" control-label key">手机号：</label>
							</td>
							<td>
								<label class="control-label val">${accountBalance.mobilePhone}</label>
							</td>
						</tr>
						<tr>
							<td>
								<label class=" control-label key">性别：</label>
							</td>
							<td>
								<label class="control-label val">
								<#if accountBalance.sex==0>
								女
								<#elseif accountBalance.sex==1>
								男
								<#else>
								未知
								</#if>
							</label>
							</td>
							<td>
								<label class=" control-label key">身份证号：</label>
							</td>
							<td>
								<label class="control-label val">${accountBalance.idCard}</label>
							</td>
						</tr>
						<tr>
						  <td>
                                <label class=" control-label key">充电余额：</label>
                            </td>
                            <td>
                                <label class="control-label val">${accountBalance.chargingBalance}</label>
                            </td>
                            <td>
                                <label class=" control-label key">停车余额：</label>
                            </td>
                            <td>
                                <label class="control-label val">${accountBalance.stopBalance}</label>
                            </td>
						</tr>
						<tr>
						<td>
                                <label class=" control-label key">会员类型：</label>
                            </td>
                            <td>
                                <label class="control-label val">
                                <#if accountBalance.memberType==1>
                                                                                  普通会员
                                <#elseif accountBalance.memberType==2>
                                                                                集团会员         
                                <#else>
                                --
                                </#if>
                            </label>
                            </td>
						      <td>
                                <label class=" control-label key">注册时间：</label>
                            </td>
                            <td>
                                <label class="control-label val">${accountBalance.registerTime?string('yyyy-MM-dd HH:mm:ss')}</label>
                            </td>
						</tr>
					</tbody>
					<tfoot class="tab-tfoot">
						<tr style="text-align: center;">
							<td colspan="4"><button type="button" onclick="closeTab();" id="closeaccountBalance" class="btn-new-type-edit">
                关闭
            </button></td>

						</tr>
					</tfoot>
				</table>

			</form>

		</div>
	</div>
</body>