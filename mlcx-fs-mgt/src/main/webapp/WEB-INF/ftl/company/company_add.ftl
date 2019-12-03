<meta charset="utf-8">
<div class="container-fluid backgroundColor" id="scollOo">
	<form name="companyAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">集团添加</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;集团名称：</label>
						</td>
						<td> 
							<input class="form-control val" name="companyName" maxlength="15" placeholder="请输入集团名称"/>
							<span id="companyNameAdd"></span>
							
						</td>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;城市：</label>
							<input type="hidden" id="cityName" name="cityName"/>
						</td>
						<td>
							<select name="cityId" class="form-control val" onchange="selectSetValue('cityId','cityName')" id="cityId">
							    	<option value="">请选择</option>
							    	<#if cities?exists>
							    		<#list cities as city>
							    			<option value="${city.dataDictItemId}">${city.itemValue}</option>
							    		</#list>
							    	</#if>
								</select>
								<span id="cityIdAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;联系人：</label>
						</td>
						<td>
							<input class="form-control val" name="contactPerson" value="" maxlength="10"  placeholder="请输入联系人"/>
							<span id="contactPersonAdd"></span>
						</td>
						
						<td> 
						  <label class="col-sm-3 control-label key">*&nbsp;&nbsp;联系电话：</label>	
						</td>
						<td>
							<input class="form-control val" name="contactTel"  placeholder="请输入联系电话"/>
                   <span id="contactTelAdd"></span>
						</td>
						
						
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key">*&nbsp;&nbsp;联系邮箱：</label>
						</td>
						<td>
							<input class="form-control val" name="email"  placeholder="请输入联系邮箱"/>
							<span id="emailAdd"></span>
						</td>
						<td consapn="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveAddCompany" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCompany" class="btn-new-type-edit pull-left" style="margin-left:10px !important">
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
	  require.config({paths: {"companyAdd":"res/js/company/company_add"}});
		require(["companyAdd"], function (companyAdd){
			companyAdd.init();
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