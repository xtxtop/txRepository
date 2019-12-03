<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="merchantEditForm">
		<input type="hidden" name="merchantId" value="${merchant.merchantId}"/>
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
							<input class="form-control val" name="merchantName" value="${merchant.merchantName}"/>
							<span name="merchantNameEdit"></span>
						</td>
					<td>
						<label class=" control-label key"><span>*</span>联系人：</label>
					</td>
					<td>
						<input class="form-control val" name="cantactPerson" value="${merchant.merchantName}"/>
						<span name="cantactPersonEdit"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>联系邮箱：</label>
						</td>
						<td>
							<input class="form-control val" name="eMail" value="${merchant.eMail}"/>
							<span name="eMailEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>联系电话：</label>
						</td>
						<td>
							<input class="form-control val" name="mobilePhone" value="${merchant.mobilePhone}"/>
							<span name="mobilePhoneEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>租赁商地址：</label>
						</td>
						<td class="clearfix">
							 <div class="col-sm-7">
		                        <select class="" name="addrRegion1Id" onchange="getResultCityEdit(this.value)" style="height:34px;border: 1px solid #ccc;">
		                       		<option value="">全部</option>
		                       		<#list provinces as province>
			                        	<option value="${province.regionId}" <#if province.regionId==merchant.addrRegion1Id>selected</#if> >
			                        		${province.regionName}
			                        	</option>
		                       		</#list>
		                        </select>

		                        <span id="merchantCitiesEdit">
		                         	<select class="" name="addrRegion2Id" style="height:34px;border: 1px solid #ccc;" onchange="getResultDistrictsEdit(this.value)">
		                         		<option value="">全部</option>
		                         		<#list cities as city>
				                        	<option value="${city.regionId}" <#if city.regionId==merchant.addrRegion2Id>selected</#if> >
				                        		${city.regionName}
				                        	</option>
		                       			</#list>
		                       		</select>
		                        </span>

		                       <span id="merchantDistrictsEdit">
			                         <select class="" name="addrRegion3Id" style="height:34px;border: 1px solid #ccc;">
				                       	<option value="">全部</option>
				                       	<#list districts as district>
				                        	<option value="${district.regionId}" <#if district.regionId==merchant.addrRegion3Id>selected</#if> >
				                        		${district.regionName}
				                        	</option>
		                       			</#list>
			                        </select>
		                        </span>
		                       </div>
		                        <span name="addrRegionEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>街道：</label>
						</td>
						<td>
							<input class="form-control val" name="addrStreet" value="${merchant.addrStreet}"/>
							<span name="addrStreetEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否支持异地还车：</label>
						</td>
						<td>
							<select class="col-sm-7" name="isOffsiteReturncar" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
			                        <option value="">请选择</option>
			                        <option value="0" <#if merchant.isOffsiteReturncar==0>selected</#if> >不支持</option>
			                        <option value="1" <#if merchant.isOffsiteReturncar==1>selected</#if> >支持</option>
		                        </select>
		                    <span name="isOffsiteReturncarEdit"></span>
						</td>
						    <td>
							<label class=" control-label key">分润比(商家占总金额比例)：</label>
						</td>
						<td>
							<input class="form-control val" name="profitRatio" value="${merchant.profitRatio}"/>对账时优先使用此值，否则使用系统参数
							<span name="profitRatioEdit"></span>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
					    <td colspan="2"><button type="button" id="editMerchant"   class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                 保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditMerchant" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  	require.config({paths: {"merchantEdit":"res/js/dailyrental/merchant/merchant_edit"}});
		require(["merchantEdit"], function (merchantEdit){
			merchantEdit.init();
		});
	}); 
</script>