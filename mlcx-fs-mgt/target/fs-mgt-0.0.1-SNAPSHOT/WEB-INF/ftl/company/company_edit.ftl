<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="companyEditForm">
		<input type="hidden" name="companyId" value="${company.companyId}" readonly/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">集团编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;公司名称：</label>
						</td>
						<td>
							<input class="form-control val" name="companyName" value="${company.companyName}" maxlength="15" />
							<span id="companyNameEdit"></span>

						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
					<input type="hidden" id="cityName" name="cityName" value="${company.cityName}">
						</td>
						<td>
							<select name="cityId" class="form-control val" onchange="selectSetValue('cityId','cityName')" id="cityId">
							<option value="">请选择</option>
							<#if cities?exists>
								<#list cities as city>
									<option value="${city.dataDictItemId}" <#if city.dataDictItemId==company.cityId>selected=selected</#if> >${city.itemValue}</option>
							</#list>
							</#if>
						</select>
							<span id="cityIdEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;联系人：</label>
						</td>
						<td>
							<input class="form-control val" name="contactPerson" value="${company.contactPerson}" maxlength="10" />
							<span id="contactPersonEdit"></span>
						</td>

						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;联系电话：</label>
						</td>
						<td>
							<input class="form-control val" name="contactTel" value="${company.contactTel}" />
							<span id="contactTelEdit"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;联系邮箱：</label>
						</td>
						<td>
							<input class="form-control val" name="email" value="${company.email}" />
							<span id="emailEdit"></span>
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
					<tr>
						<td colspan="2"><button type="button" id="saveEditCompany" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditCompany" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
		$(function() {
			require.config({
				paths: {
					"companyEdit": "res/js/company/company_edit"
				}
			});
			require(["companyEdit"], function(companyEdit) {
				companyEdit.init();
			});
		});
		$(function() {
			$(".datepicker").datepicker({
				language: "zh-CN",
				autoclose: true, //选中之后自动隐藏日期选择框
				clearBtn: true, //清除按钮
				todayBtn: true, //今日按钮
				format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
			});
		});
	</script>