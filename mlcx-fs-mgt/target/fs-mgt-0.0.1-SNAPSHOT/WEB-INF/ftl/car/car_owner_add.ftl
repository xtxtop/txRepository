<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carOwnerAddForm" class="form-horizontal">
		
		<div class="row hzlist">
			
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车主新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>名称：</label></td>
						<td><input class="form-control val" name="ownerName"  maxlength="15"  placeholder="请输入名称"/><span id="ownerNameAdd"></span></td>
						<td><label class=" control-label key"><span>*</span>全称：</label></td>
						<td>
							<input class="form-control val" name="ownerFullName"  maxlength="15"  placeholder="请输入全称"/>
							<span id="ownerFullNameAdd"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>类型：</label></td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="ownerType"  value="2" checked="checked">个人</input>
                                <input type="radio" name="ownerType"  value="1" >公司</input>
							</div>
						</td>
						<td><label class=" control-label key"><span>*</span>联系人：</label></td>
						<td><input class="form-control val" name="contactPerson"  placeholder="请输入联系人"/><span id="contactPersonAdd"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key">联系人电话：</label></td>
						<td><input class="form-control val" name="contactTel"  placeholder="请输入联系人电话"/><span id="contactTelAdd"></span></td>
						<td><label class=" control-label key"><span>*</span>联系人手机：</label></td>
						<td> <input class="form-control val" name="contactPhone"  placeholder="请输入联系人手机"/><span id="contactPhoneAdd"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>联系人邮箱：</label></td>
						<td><input class="form-control val" name="contactEmail"  placeholder="请输入联系人邮箱"/><span id="contactEmailAdd"></span></td>
						<td><label class=" control-label key">身份证号码：</label></td>
						<td><input class="form-control val" name="idCardNo"  placeholder="请输入身份证号码"/><span id="idCardNoAdd"></span></td>
					</tr>
						<tr id="sz">
						<td><label class=" control-label key">工商营业执照：</label></td>
						<td><input class="form-control val" name="bizLicenseNo"  placeholder="请输入工商营业执照"/><span id="bizLicenseNoAdd"></span></td>
						<td><label class=" control-label key">组织机构代码证：</label></td>
						<td><input class="form-control val" name="organizationCode"  placeholder="请输入组织机构代码证"/><span id="organizationCodeAdd"></span></td>
					</tr>
						<tr id="sz1">
						<td><label class=" control-label key">税务登记证：</label></td>
						<td><input class="form-control val" name="taxRegistration"  placeholder="请输入税务登记证"/><span id="taxRegistrationAdd"></span></td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="addCarOwner" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarOwneradd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"carOwnerAdd":"res/js/car/car_owner_add"}});
		require(["carOwnerAdd"], function (carOwnerAdd){
			carOwnerAdd.init();
		});  
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
});
</script>