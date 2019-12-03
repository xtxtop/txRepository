<meta charset="utf-8">
<style>
   .max-height{
          width:100%;
          max-height:200px;
   }
</style>
<div class="container-fluid backgroundColor">
	<form name="invoiceViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">发票开票详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>开票类型：</label>
						</td>
						<td>
							<label class="control-label val">
							    <#if invoice.invoiceType==1>增值税普通发票纸质版
							    <#elseif invoice.invoiceType==2>增值税专用发票
							    <#elseif invoice.invoiceType==3>增值税普通发票电子版
							    </#if>
							    </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>开票金额：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.invoiceAmount?string(',###.##')}元</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>开票抬头：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.invoiceTitle}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>订单号：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.bizObjId}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>收件人：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.name}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>联系手机：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.phone}</label>
						</td>
					</tr>
					<#if invoice.invoiceType==1>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>收货地址：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.address}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>开票时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if invoice.invoiceStatus==1>
							${invoice.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
						</td>
					</tr>
					</#if>
						<#if invoice.invoiceType==3>
						<tr>
							<td>
							<label class=" control-label key"><span></span>邮箱地址：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.emailAddress}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>开票时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if invoice.invoiceStatus==1>
							${invoice.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
						</td>
						</tr>
						</#if>
						<#if invoice.invoiceType==2>
							<tr>
								<td>
								<label class=" control-label key"><span></span>收货人地址：</label>
							</td>
							<td>
								 <label class="control-label val">${invoice.address}</label>
							</td>
							<td>
								<label class=" control-label key"><span></span>纳税人识别号：</label>
							</td>
							<td>
								<label class="control-label val">${invoice.taxpayerNo}</label>
							</td>
							</tr>
							<tr>
								<td>
								<label class=" control-label key"><span></span>开户行：</label>
							</td>
							<td>
								<label class="control-label val">${invoice.accountBank}</label>
							</td>
							<td>
								<label class=" control-label key"><span></span>账号：</label>
							</td>
							<td>
								<label class="control-label val">${invoice.accountNo}</label>
							</td>
							</tr>
							<tr>
								<td>
								<label class=" control-label key"><span></span>地址：</label>
							</td>
							<td>
								<label class="control-label val">${invoice.companyAddress}</label>
							</td>
							<td>
								<label class=" control-label key"><span></span>电话：</label>
							</td>
							<td>
								<label class="control-label val">${invoice.tel}</label>
							</td>
							</tr>
							
							
							<tr>
								<td>
								<label class=" control-label key"><span></span>纳税人认定通知书：</label>
							</td>
							<td>
								<label class="control-label val"><img class="max-height" src="${imagePath!''}/${invoice.taxpayerNoticePic}"/></label>
							</td>
							<td>
							<label class=" control-label key"><span></span>增值税专用发票开票资料：</label>
							</td>
							<td>
								<label class="control-label val"><img class="max-height" src="${imagePath!''}/${invoice.invoiceInfo}"/></label>
							</td>
							</tr>
							</#if>
					<tr>
						<td>
							<label class=" control-label key"><span></span>开票时间：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if invoice.invoiceStatus==1>
							${invoice.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>开票状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if invoice.invoiceStatus==0>未开票
							<#elseif invoice.invoiceStatus==1>已开票
							</#if>
							</label>
						</td>
					</tr>
					<#if invoice.invoiceStatus==1>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>发票号：</label>
						</td>
						<td>
						<label class="control-label val">${invoice.invoiceNo}</label>	
						</td>
						
						<td>
							<label class=" control-label key"><span></span>开票操作人：</label>
						</td>
						<td>
						<label class="control-label val">
							<#if invoice.invoiceStatus==1>
							${invoice.invoiceOperatorName}
							</#if>
							</label>
						</td>
					</tr>
					</#if>
					<tr>
						<td>
							<label class=" control-label key"><span></span>开票操作人：</label>
						</td>
						<td>
						<label class="control-label val">
							<#if invoice.invoiceStatus==1>
							${invoice.invoiceOperatorName}
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>创建日期：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>更新日期：</label>
						</td>
						<td>
							<label class="control-label val">${invoice.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeInvoiceView" class="btn-new-type-edit">
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
	  require.config({paths: {"invoice":"res/js/finace/invoice_list"}});
		require(["invoice"], function (invoice){
			invoice.init();
		});  
	});
</script>