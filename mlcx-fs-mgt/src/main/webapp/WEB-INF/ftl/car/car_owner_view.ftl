<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carOwnerViewForm" class="form-horizontal">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车主详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span></span>名称：</label></td>
						<td><label class="control-label val">${carOwner.ownerName}</label></td>
						<td><label class=" control-label key"><span></span>全称：</label></td>
						<td>
							<label class="control-label val"> ${carOwner.ownerFullName}</label>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>类型：</label></td>
						<td>
							<label class="control-label val">
								 <#if carOwner.ownerType==2>个人
								 <#elseif carOwner.ownerType==1>公司 
                                 </#if> 
							</label>
						</td>
						<td><label class=" control-label key"><span></span>联系人：</label></td>
						<td><label class="control-label val">${carOwner.contactPerson}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key">联系人电话：</label></td>
						<td> <label class="control-label val"> ${carOwner.contactTel}</label></td>
						<td><label class=" control-label key"><span>*</span>联系人号码：</label></td>
						<td><label class="control-label val">${carOwner.contactPhone} </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>联系人邮箱：</label></td>
						<td><label class="control-label val"> ${carOwner.contactEmail}</label></td>
						<td><label class=" control-label key">身份证号码：</label></td>
						<td><label class="control-label val">${carOwner.idCardNo}</label></td>
					</tr>
						<tr id="szView" <#if carOwner.ownerType==1>style="display:block"
						<#elseif carOwner.ownerType==2>style="display:none"
						</#if>>
						<td><label class=" control-label key">工商营业执照：</label></td>
						<td><label class="control-label val"> ${carOwner.bizLicenseNo}</label></td>
						<td><label class=" control-label key">组织机构代码证：</label></td>
						<td><label class="control-label val"> ${carOwner.organizationCode}</label></td>
					</tr>
						<tr id="szView" <#if carOwner.ownerType==1>style="display:block"
						<#elseif carOwner.ownerType==2>style="display:none"
						</#if>>
						<td><label class=" control-label key">税务登记证：</label></td>
						<td><label class="control-label val"> ${carOwner.taxRegistration}</label></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>审核状态：</label></td>
						<td>
							<label class="control-label val">
							    <#if carOwner.cencorStatus==0>未审核
							    <#elseif carOwner.cencorStatus==1>已审核
							    <#elseif carOwner.cencorStatus==3>未通过
							    </#if>
							    </label>
						</td>
						<td><label class=" control-label reason key"><span></span>审核备注：</label></td>
						<td><label class="control-label val">${carOwner.cencorMemo}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>编辑人：</label></td>
						<td><label class="control-label val"> ${carOwner.operatorId}</label></td>
						<td><label class=" control-label key"><span></span>审核人：</label></td>
						<td><label class="control-label val">${carOwner.cencorId}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>更新时间：</label></td>
						<td><label class="control-label val">${carOwner.cencorTime?string('yyyy-MM-dd HH:mm:ss')}</label></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="4" style="text-align: center;"><button type="button" id="closeCarOwnerView" class="btn-new-type-edit">
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
	  require.config({paths: {"carOwner":"res/js/car/car_owner_list"}});
		require(["carOwner"], function (carOwner){
			carOwner.init();
		});  
	});
</script>