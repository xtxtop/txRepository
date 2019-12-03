<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="companyDetailForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">集团详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;公司名称：</label>
						</td>
						<td>
							 <label class="control-label val">${company.companyName}</label>

						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<label class="control-label val">${company.cityName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;联系人：</label>
						</td>
						<td>
							<label class="control-label val">${company.contactPerson}</label>
						</td>

						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;联系电话：</label>
						</td>
						<td>
							<label class="control-label val">${company.contactTel}</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;联系邮箱：</label>
						</td>
						<td>
							<label class="control-label val">${company.email}</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if company.cencorStatus==1>
								未审核
								<#elseif company.cencorStatus==2>
                				待审核
                				<#elseif company.cencorStatus==3>
                				已审核
                				<#else>
                				不通过
                				</#if>
                			</label>
						</td>
					</tr>
					<tr>
					<td>
						<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;审核时间：</label>
					</td>
					<td>
						<label class="control-label val">
								<#if company.cencorTime?exists>
								${company.cencorTime?string('yyyy-MM-dd HH:mm:ss')}
								</#if>
							</label>
					</td>
					<td>
						<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;状态：</label>
					</td>
					<td>
						<label class="control-label val">
                				<#if company.companyStatus==1>
								启用
								<#elseif company.companyStatus==2>
                				停用
                				</#if>
                			</label>
					</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;启用时间：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if company.enableTime?exists>
                				${company.enableTime?string('yyyy-MM-dd HH:mm:ss')}
                				</#if>
                				</label>
						</td>
						<td></td>
						<td></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeCompanyView" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
$(function(){
	  require.config({paths: {"companyEdit":"res/js/company/company_edit"}});
		require(["companyEdit"], function (companyEdit){
			companyEdit.init();
		});  
	});
</script>