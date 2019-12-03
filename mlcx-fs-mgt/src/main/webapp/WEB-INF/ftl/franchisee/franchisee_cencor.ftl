<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="saveCencorFranchiseeForm">
		<input type="hidden" value="${franchisee.franchiseeNo}" name="franchiseeNo">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">加盟商审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>加盟商名称：</label>
						</td>
						<td>
							<label class="control-label val">${franchisee.franchiseeName}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>加盟商全称：</label>
						</td>
						<td>
							<label class="control-label val">${franchisee.franchiseeFullName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>分润比例（按车）：</label>
						</td>
						<td>
							<label class="control-label val">${franchisee.carProportion}%</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>分润比例（按场站）：</label>
						</td>
						<td>
							<label class="control-label val">${franchisee.parkProportion}%</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>联系人：</label>
						</td>
						<td>
							<label class="control-label val">${franchisee.contacts}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>联系人手机：</label>
						</td>
						<td>								
							<label class="control-label val">${franchisee.contactsPhone}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>联系人邮箱：</label>
						</td>
						<td>
                           <label class="control-label val">${franchisee.mailbox}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if franchisee.censorStatus==0>
								未审核
								<#elseif franchisee.censorStatus==1>
								已通过
								<#elseif franchisee.censorStatus==2>
								未通过
								<#else>
								未知
								</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->备注：</label>
						</td>
						<td>
							<textarea rows="3" cols="50" readonly>${franchisee.memo}</textarea>
						</td>
						<td>
							<label class="  control-label key">相关证件：</label>
						</td>
						<td>
                            <img class="imgEnlarge" src="${imagePath!''}/${franchisee.franchiseePhotoUrl1}" width="200" />
						</td>
					</tr>
					<tr>
						<td><label class=" control-label key">*审核结果：</label></td>
						<td>
							<label class="control-label val">
								<input name="censorStatus" type="radio" value="1" checked="checked" />通过
							</label> 
			      			<label class="control-label val">
			      				<input name="censorStatus" type="radio" value="2" />不通过
			      			</label> 
						</td>
						<td>
							<label class=" control-label key reason">*审核备注：</label>
						</td>
						<td>
							<label class="control-label val">
								<textarea  name="censorMemo" cols="30" rows="5"></textarea>
							</label>
						    <span name="cencorMemoNs"></span>
						</td>
					</tr>
					
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="saveCencorFranchisee" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeFranchiseeCencor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	
		 require.config({paths: {"franchiseeEdit":"res/js/franchisee/franchisee_edit"}});
			require(["franchiseeEdit"], function (franchiseeEdit){
				franchiseeEdit.init();
			});  
	
	
	
	});
</script>