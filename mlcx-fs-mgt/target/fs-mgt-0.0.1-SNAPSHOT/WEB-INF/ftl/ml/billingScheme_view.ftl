<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="billingSchemeAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">计费方案详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span></span>计费方案编号：</label>
						</td>
						<td><label class="control-label val">
								${billingScheme.schemeNo} </label></td>
						<td><label class=" control-label key"><span></span>计费方案名称：</label>
						</td>
						<td><label class="control-label val">
								${billingScheme.schemeName} </label></td>
					</tr>
					<tr>
                        <td><label class=" control-label key"><span></span>计费方案版本：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.schemeVersions} </label></td>
                        <td><label class=" control-label key"><span></span>状态：</label>
                        </td>
                        <td><label class="control-label val">
                            <#if billingScheme.status==1>
                                                                         有效
                            <#else>
                                                                        无效
                            </#if>
                             </label></td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>生效时间：</label>
                        </td>
                        <td><label class="control-label val">
                            <#if billingScheme.effectiveDate>
                                ${billingScheme.effectiveDate?string('yyyy-MM-dd')}
                             <#else>
                             --
                             </#if>
                            </label></td>
                        <td><label class=" control-label key"><span></span>失效时间：</label>
                        </td>
                        <td><label class="control-label val">
                         <#if billingScheme.invalidDate>
                                ${billingScheme.invalidDate?string('yyyy-MM-dd')} 
                         <#else>
                            --
                         </#if>
                         </label></td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>预冻结金额(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.advanceFrozenMoney} </label></td>
                        <td><label class=" control-label key"><span></span>最小冻结金额(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.minFrozenMoney} </label></td>
                    </tr>
                    <tr>
                        <!-- <td><label class=" control-label key"><span></span>时段数：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.timeNum} </label></td> -->
                        <td><label class=" control-label key"><span></span>尖时段电价(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.opintPrice} </label></td>
                        <td><label class=" control-label key"><span></span>峰时段电价(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.peakPrice} </label></td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>平时段电价(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.flatPrice} </label></td>
                        <td><label class=" control-label key"><span></span>谷时段电价(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.valleyPrice} </label></td>
                     </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>预约费率(%)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.orderedRate} </label></td>
                        <td><label class=" control-label key"><span></span>服务费(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.serviceCharge} </label></td>
                     </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>告警金额(元)：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.warmingPrice} </label></td>
                        <td><label class=" control-label key"><span></span>登记日期：</label>
                        </td>
                        <td><label class="control-label val">
                                ${billingScheme.createTime?string('yyyy-MM-dd HH:mm:ss')} </label></td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closebillingSchemeView" onclick="closeTab();"  class="btn-new-type-edit">
								关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
