<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="cLoveCarAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员爱车详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span></span>会员爱车编号：</label>
						</td>
						<td><label class="control-label val">
								${cLoveCar.loveCarNo} </label></td>
						<td><label class=" control-label key"><span></span>会员编号：</label>
						</td>
						<td><label class="control-label val">
								${cLoveCar.memberNo} </label></td>
					</tr>

                    <tr>
                        <td><label class=" control-label key"><span></span>车辆品牌：</label>
                        </td>
                        <td><label class="control-label val">
						${cLoveCar.vehicleBrand} </label></td>
                        <td><label class=" control-label key"><span></span>车辆型号：</label>
                        </td>
                        <td><label class="control-label val">
						${cLoveCar.vehicleModel} </label></td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>审核状态：</label>
                        </td>
                        <td><label class="control-label val"> <#if
						cLoveCar.censorStatus==1> 已审核 </#if> <#if
						cLoveCar.censorStatus==2> 待审核 </#if><#if
						cLoveCar.censorStatus==0> 未审核 </#if> </label></td>

                        <td><label class="photo-line-height">行驶证图片：</label></td>
                        <td ><label class="odd-td">
                            <img src="${imagePath!''}/${cLoveCar.drivingLicense}"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td><label class="photo-line-height">行驶证附件图片：</label></td>
                        <td ><label class="odd-td">
                            <img src="${imagePath!''}/${cLoveCar.drivingLicenseCopy}"/>
                        </label></td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closechargingStationView" onclick="closeTab();" class="btn-new-type-edit">
								关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
