<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carIllegalForm" class="form-horizontal">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">违章详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>车牌号：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.carPlateNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>型号：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.carModelName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>城市：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.cityName}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>违章时间：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.illegalTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>违章地点：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.illegalLocation}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>违章类型：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if carIllegal.illegalType==1>
								未系安全带
                				<#elseif carIllegal.illegalType==2>
                				压禁止标线
                				<#elseif carIllegal.illegalType==3>
                				违停
                				<#elseif carIllegal.illegalType==4>
                				闯红灯
                				<#elseif carIllegal.illegalType==5>
                				不服从指挥
                				<#elseif carIllegal.illegalType==6>
                				超速行驶
                				<#elseif carIllegal.illegalType==7>
                				未设警告标志
                				<#elseif carIllegal.illegalType==8>
                				未停车让行
                				<#elseif carIllegal.illegalType==9>
                				未保持车距
                				<#elseif carIllegal.illegalType==10>
                				未按道行驶
                				</#if>
                				</select>
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>罚款金额：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.illegalFines}元</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>处理状态：</label>
						</td>
						<td>
							<label class="control-label val">
                					<#if carIllegal.processingStatus==0>
                					未处理
                					<#elseif carIllegal.processingStatus==1>
                					处理中
                					<#elseif carIllegal.processingStatus==2>
                					已处理
                					</#if>
                				</label>
						</td>
					</tr>
					<tr>
						<td>
                                <label class=" control-label key"><span></span>缴费状态：</label>
						</td>
						<td>
                                <label class="control-label val">
                				<#if carIllegal.paymentStatus==0>
                					未缴款
                					<#elseif carIllegal.paymentStatus==1>
                					已缴款
                				</#if>
                				</label>
						</td>
						<td>
                                <label class=" control-label key"><span></span>用车类型：</label>
						</td>
						<td>
                                <label class="control-label val">
                				<#if carIllegal.useCarType==1>
                					订单用车
                					<#elseif carIllegal.useCarType==2>
                					调度用车
                				</#if>
                				</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>单据号：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.documentNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>用车人：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.driverName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>处理机构：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.processingAgency}</label>
						</td>
						<td>
							<label class=" control-label reason key"><span></span>备注：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.memo}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label reason key"><span></span>违章内容：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.illegalDetail}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td></td>
						<td></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
				<td colspan="4"><button type="button" id="closeCarIllegalVeiw" class="btn-new-type-edit">
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
	  require.config({paths: {"carIllegalEdit":"res/js/car/car_illegal_edit"}});
		require(["carIllegalEdit"], function (carIllegalEdit){
			carIllegalEdit.init();
		});  
	});
</script>