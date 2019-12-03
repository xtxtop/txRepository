<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="companyCencorForm">
		<input type="hidden" name="companyId" value="${company.companyId}" readonly/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">集团审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-3 control-label key">公司名称：</label>
						</td>
						<td>
							 <label class="control-label val">${company.companyName}</label>

						</td>
						<td>
							<label class="col-sm-3 control-label key">城市：</label>
						</td>
						<td>
							<label class="control-label val">${company.cityName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">联系人：</label>
						</td>
						<td>
							<label class="control-label val">${company.contactPerson}</label>
						</td>

						<td>
							<label class="col-sm-3 control-label key">联系电话：</label>
						</td>
						<td>
							<label class="control-label val">${company.contactTel}</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">联系邮箱：</label>
						</td>
						<td>
							<label class="control-label val">${company.email}</label>
						</td>
						<td>
							<label class="col-sm-3 control-label key">审核状态：</label>
						</td>
						<td>
							<label><input name="cencorStatus" type="radio" value="3" checked="checked" />通过 </label> 
			      			<label><input name="cencorStatus" type="radio" value="4" />不通过</label> 
						</td>
					</tr>
					<tr>
					<td>
						<label class="col-sm-3 control-label key">状态：</label>
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
					<td>
						<label class="col-sm-3 control-label key">启用时间：</label>
					</td>
					<td>
						<label class="control-label val">
                				<#if company.enableTime?exists>
                				${company.enableTime?string('yyyy-MM-dd HH:mm:ss')}
                				</#if>
                				</label>
					</td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCencorCompany" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCencorCompany" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                返回
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"companyCencor":"res/js/company/company_cencor"}});
		require(["companyCencor"], function (companyCencor){
			companyCencor.init();
		});  
	});
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>