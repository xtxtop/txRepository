<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="companyPersonDetailForm">
		<input type="hidden" name="companyId" value="${company.companyId}" readonly/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">集团人员详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							 <label class="control-label val">${companyPerson.cityName}</label>

						</td>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;集团名称：</label>
						</td>
						<td>
							<label class="control-label val">${companyPerson.companyName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;姓名：</label>
						</td>
						<td>
							<label class="control-label val">${companyPerson.name}</label>
						</td>

						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;类型：</label>
						</td>
						<td>
							<label class="control-label val">
									<#if companyPerson.personType==1>
									   员工
									<#elseif companyPerson.personType==0>
									  非员工
	                				</#if>
                				</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;手机号：</label>
						</td>
						<td>
							<label class="control-label val">${companyPerson.mobilePhone}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;导入时间：</label>
						</td>
						<td>
							<label class="control-label val">
								   ${companyPerson.importTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
						</td>
					</tr>
					<tr>
					<td>
						<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;注册情况：</label>
					</td>
					<td>
						<label class="control-label val">
								    <#if companyPerson.registerStatus==0>
									   未注册
									<#elseif companyPerson.registerStatus==1>
									  已注册
	                				</#if>
							    </label>
					</td>
					<td>
					</td>
					<td>
					</td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeCompanyPerson" class="btn-new-type-edit">
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
			require.config({paths: {"companyPerson":"res/js/company/company_person_list"}});
			  require(["companyPerson"], function (companyPerson){
					companyPerson.init();
			});  
		});
</script>