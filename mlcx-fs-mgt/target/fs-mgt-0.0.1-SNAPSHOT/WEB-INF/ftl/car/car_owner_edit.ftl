<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carOwnerEditForm" class="form-horizontal">
		<input type="hidden" name="ownerId" value="${carOwner.ownerId}"/>
		<div class="row hzlist">
			
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车主信息编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td width="20%"><label class=" control-label key"><span>*</span>名称：</label></td>
						<td width="30%"><input class="form-control val" name="ownerName" value="${carOwner.ownerName}"/><span id="ownerNameEdit"></span></td>
						<td width="20%"><label class=" control-label key"><span>*</span>全称：</label></td>
						<td width="30%">
							<input class="form-control val" name="ownerFullName" value="${carOwner.ownerFullName}"/>
								<span id="ownerFullNameEdit"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>类型：</label></td>
						<td>
						<div style="float: left;">
								<input type="radio" name="ownerType"  value="2" <#if carOwner.ownerType==2>checked="checked"</#if>>个人</input>
                                <input type="radio" name="ownerType"  value="1" <#if carOwner.ownerType==1>checked="checked"</#if>>公司</input>
							</div>
						</td>
						<td><label class=" control-label key"><span>*</span>联系人：</label></td>
						<td><input class="form-control val" name="contactPerson" value="${carOwner.contactPerson}"/><span id="contactPersonEdit"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key">联系人电话：</label></td>
						<td><input class="form-control val" name="contactTel" value="${carOwner.contactTel}"/><span id="contactTelEdit"></span></td>
						<td><label class=" control-label key"><span>*</span>联系人手机：</label></td>
						<td><input class="form-control val" name="contactPhone" value="${carOwner.contactPhone}"/><span id="contactPhoneEdit"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>联系人邮箱：</label></td>
						<td><input class="form-control val" name="contactEmail" value="${carOwner.contactEmail}"/><span id="contactEmailEdit"></span></td>
						<td><label class=" control-label key">身份证号码：</label></td>
						<td> <input class="form-control val" name="idCardNo" value="${carOwner.idCardNo}"/><span id="idCardNoEdit"></span></td>
					</tr>
					<tr id="szEdit">
						<td><label class=" control-label key">工商营业执照：</label></td>
						<td><input class="form-control val" name="bizLicenseNo" value="${carOwner.bizLicenseNo}" /><span id="bizLicenseNoEdit"></span></td>
						<td><label class=" control-label key">组织机构代码证：</label></td>
						<td><input class="form-control val" name="organizationCode" value="${carOwner.organizationCode}" /><span id="organizationCodeEdit"></span></td>
					</tr>
					<tr id="szEditNext">
						<td><label class=" control-label key">税务登记证：</label></td>
						<td><input class="form-control val" name="taxRegistration" value="${carOwner.taxRegistration}"/></span><span id="taxRegistrationEdit"></span></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><label class=" control-label key">审核状态：</label></td>
						<td><label class="control-label val">
							    <#if carOwner.cencorStatus==0>未审核
							    <#elseif carOwner.cencorStatus==1>已审核
							    <#elseif carOwner.cencorStatus==3>未通过
							    </#if>
							    </label></td>
						<td><label class=" control-label reason key">审核备注：</label></td>
						<td><label class="control-label val">${carOwner.cencorMemo}</label></td>
					</tr>
					
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="editCarOwner" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarOwnerEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"carOwnerEdit":"res/js/car/car_owner_edit"}});
		require(["carOwnerEdit"], function (carOwnerEdit){
			carOwnerEdit.init();
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