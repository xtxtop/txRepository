<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="merchantUserEditForm">
		<input type="hidden" name="userNo" value="${merchantUser.userNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑商家用户</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>租赁商名称：</label>
						</td>
						<td>
							<select name="merchantId" class="form-control val" <#if merchantUser.censorStatus==1>disabled</#if> >
	                             <option value="" >请选择</option>
	                             <#list merchants as merchant>
	                                 <option value="${merchant.merchantId}" <#if merchantUser.merchantId==merchant.merchantId>selected=selected</#if> >
	                                        ${merchant.merchantName}
	                                 </option>
	                             </#list>
                             </select>
                            <span name="merchantId"></span>
						</td>
					<td>
						<label class=" control-label key"><span>*</span>用户名：</label>
					</td>
					<td>
						<input class="form-control val" name="userName" value="${merchantUser.userName}" <#if merchantUser.censorStatus==1>readonly</#if> />
						<span name="userName"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>密码：</label>
						</td>
						<td>
							<input class="form-control val" name="password" value="${merchantUser.password}"/>
							<span name="password"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>联系电话：</label>
						</td>
						<td>
							<input class="form-control val" name="mobilePhone" value="${merchantUser.mobilePhone}" <#if merchantUser.censorStatus==1>readonly</#if> />
							<span name="mobilePhone"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>性别：</label>
						</td>
						<td>
							<select name="sex" class="form-control val" <#if merchantUser.censorStatus==1>disabled</#if>>
	                             	<option value="" >请选择</option>
	                                <option value="0" <#if merchantUser.sex==0>selected=selected</#if> >女</option>
	                                <option value="1" <#if merchantUser.sex==1>selected=selected</#if> >男</option>
                             	</select>
                            <span name="sex"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
	                             <option value="" >请选择</option>
	                             <#list cities as city>
	                                 <option value="${city.dataDictItemId}" <#if city.dataDictItemId==merchantUser.cityId>selected=selected</#if>>
	                                      ${city.itemValue}
	                                 </option>
	                             </#list>
                             </select>
                            <span name="cityId"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
					    <td colspan="2"><button type="button" id="editMerchantUser"   class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                 保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditMerchantUser" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  	require.config({paths: {"merchantUserEdit":"res/js/dailyrental/merchant/merchant_user_edit"}});
		require(["merchantUserEdit"], function (merchantUserEdit){
			merchantUserEdit.init();
		});
	}); 
</script>