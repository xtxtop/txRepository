<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carOwnerCencorForm" class="form-horizontal">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车主信息审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class="col-sm-4 control-label"><span>*</span>名称：</label></td>
						<td> <label class="control-label">${carOwner.ownerFullName}</label</td>
						<td><label class=" control-label key"><span></span>全称：</label></td>
						<td>
							<label class="control-label val"> ${carOwner.ownerFullName}</label>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>类型：</label></td>
						<td>
							<label class="control-label">
							 	 <#if carOwner.ownerType==2>个人
							 	 <#elseif carOwner.ownerType==1>公司
							 	 </#if> 
							</label>
						</td>
						<td><label class=" control-label key"><span></span>联系人：</label></td>
						<td><label class="control-label">${carOwner.contactPerson}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key">联系人电话：</label></td>
						<td><label class="control-label">${carOwner.contactTel}</label></td>
						<td><label class=" control-label key"><span>*</span>联系人号码：</label></td>
						<td><label class="control-label">${carOwner.contactPhone}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span>*</span>联系人邮箱：</label></td>
						<td><label class="control-label">${carOwner.contactEmail}</label></td>
						<td><label class=" control-label key">身份证号码：</label></td>
						<td><label class="control-label">${carOwner.idCardNo}</label></td>
					</tr>
						<tr id="szView" <#if carOwner.ownerType==1>style="display:block"
						<#elseif carOwner.ownerType==2>style="display:none"
						</#if>>
						<td><label class=" control-label key">工商营业执照：</label></td>
						<td><label class="control-label">${carOwner.bizLicenseNo}</label></td>
						<td><label class=" control-label key">组织机构代码证：</label></td>
						<td><label class="control-label">${carOwner.organizationCode}</label></td>
					</tr>
						<tr id="szView" <#if carOwner.ownerType==1>style="display:block"
						<#elseif carOwner.ownerType==2>style="display:none"
						</#if>>
						<td><label class=" control-label key">税务登记证：</label></td>
						<td> <label class="control-label">${carOwner.taxRegistration}</label></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>审核状态：</label></td>
						<td>
							<input type="radio" name="cencorStatus"  value="1" checked="checked">通过</input>
                                <input type="radio" name="cencorStatus"  value="3" >不通过</input>
						</td>
						<td><label class=" control-label reason key"><span></span>审核备注：</label></td>
						<td><textarea class="form-control" rows="2" cols="80" name="cencorMemo" >${carOwner.cencorMemo}</textarea><span id="cencorMemoCencor"></span></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>编辑人：</label></td>
						<td><label class="control-label">${carOwner.contactPerson}</label></td>
						<td><label class="col-sm-4 control-label"><span>*</span>创建时间：</label></td>
						<td><label class="control-label">${carOwner.createTime?string('yyyy-MM-dd HH:mm:ss')}</label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>更新时间：</label></td>
						<td><label class="control-label">${carOwner.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
     <td colspan="2"><button type="button" id="cencorCarOwner" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarOwnerCencor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"carOwnerCencor":"res/js/car/car_owner_cencor"}});
		require(["carOwnerCencor"], function (carOwnerCencor){
			carOwnerCencor.init();
		});
	});
</script>
</body>