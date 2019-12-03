<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="merchantAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">增加租赁商</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>租赁商名称：</label>
						</td>
						<td>
							<input class="form-control val" name="merchantName"  placeholder="请输入租赁商名称"/>
							<span name="merchantNameAdd"></span>
						</td>
					<td>
						<label class=" control-label key"><span>*</span>联系人：</label>
					</td>
					<td>
						<input class="form-control val" name="cantactPerson"  placeholder="请输入联系人"/>
						<span name="cantactPersonAdd"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>联系邮箱：</label>
						</td>
						<td>
							<input class="form-control val" name="eMail"  placeholder="请输入联系邮箱"/>
							<span name="eMailAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>联系电话：</label>
						</td>
						<td>
							<input class="form-control val" name="mobilePhone"  placeholder="请输入联系电话"/>
							<span name="mobilePhoneAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>租赁商地址：</label>
						</td>
						<td class="clearfix">
							 <div class="col-sm-8">
		                        <select class="" name="addrRegion1Id" onchange="getResultCity(this.value)" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
		                       		<option value="">全部</option>
		                       		<#list provinces as province>
			                        	<option value="${province.regionId}" >
			                        		${province.regionName}
			                        	</option>
		                       		</#list>
		                        </select>

		                        <span id="merchantCities">
		                         	<select class="" name="addrRegion2Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;" onchange="getResultDistricts(this.value)">
		                         		<option value="">全部</option>
		                       		</select>
		                        </span>

		                       <span id="merchantDistricts">
			                         <select class="" name="addrRegion3Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
				                       	<option value="">全部</option>
			                        </select>
		                        </span>
		                       </div>
		                        <span name="addrRegionAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>街道：</label>
						</td>
						<td>
							<input class="form-control val" name="addrStreet"  placeholder="请输入街道"/>
							<span name="addrStreetAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否支持异地还车：</label>
						</td>
						<td>
							<select class="" name="isOffsiteReturncar" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
			                        <option value="">请选择</option>
			                        <option value="0">不支持</option>
			                        <option value="1">支持</option>
		                        </select>
		                    <span name="isOffsiteReturncarAdd"></span>
						</td>
						    <td>
							<label class=" control-label key">分润比(商家占总金额比例)：</label>
						</td>
						<td>
							<input class="form-control val" name="profitRatio"  placeholder="请输入分润比(商家占总金额比例)"/>对账时优先使用此值，否则使用系统参数
						    <span name="profitRatioAdd"></span>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
					    <td colspan="2"><button type="button" id="addMerchant"   class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                 保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddMerchant" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  	require.config({paths: {"merchantAdd":"res/js/dailyrental/merchant/merchant_add"}});
		require(["merchantAdd"], function (merchantAdd){
			merchantAdd.init();
		});
	}); 
</script>