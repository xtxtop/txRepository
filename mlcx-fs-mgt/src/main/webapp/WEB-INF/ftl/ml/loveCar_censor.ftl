<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="loveCarCensorForm">
		<input type="hidden" name="loveCarNo" value="${cLoveCar.loveCarNo}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">爱车审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="control-label key">&nbsp;&nbsp;会员编号：</label>
						</td>
						<td>
							<label class="control-label val">${cLoveCar.memberNo}</label>
						</td>

                        <td>
                            <label class="control-label key">&nbsp;&nbsp;车辆品牌：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cLoveCar.vehicleBrand}</label>
                        </td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">&nbsp;&nbsp;车辆型号：</label>
						</td>
						<td>
							 <label class="control-label val">${cLoveCar.vehicleModel}</label>
						</td>
                        <td>
                            <label class=" control-label key">&nbsp;&nbsp;审核状态：</label>
                        </td>
                        <td>
                            <input type="radio" name="censorStatus"  value="1" checked="checked">通过</input>
                            <input type="radio" name="censorStatus"  value="3" >不通过</input>
                        </td>

					</tr>
					<tr>
							<td>
								<label class=" control-label key">&nbsp;&nbsp;更新时间：</label>
							</td>
							<td>
							<label class="control-label val">${cLoveCar.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</td>
                        <td>
                            <label class=" control-label key">&nbsp;&nbsp;创建时间：</label>
                        </td>
                        <td>
                            <label class="control-label val">${cLoveCar.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
                        </td>
								
					</tr>

					<tr>
						<td>
							<label class=" control-label class=" control-label key">行驶证图片：</label>
						</td>
						<td>
							<label class="control-label val">
                				<img src="${imagePath!''}/${cLoveCar.drivingLicense}" width="200" height="200"/>
                				</label>
						</td>

                        <td>
                            <label class=" control-label class=" control-label key">行驶证附件图片：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                                <img src="${imagePath!''}/${cLoveCar.drivingLicenseCopy}" width="200" height="200"/>
                            </label>
                        </td>

					</tr>
                    <tr>
                        <td><label class=" control-label key reason"><span>*</span>&nbsp;&nbsp;审核备注：</label>
                        </td>
                        <td><textarea class="form-control val" rows="6"
                                      name="censorMemo">${cLoveCar.censorMemo}</textarea>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="loveCarCensor" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeloveCarCensor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"loveCarCensor":"res/js/ml/loveCar_censor"}});
		require(["loveCarCensor"], function (loveCarCensor){
            loveCarCensor.init();
		});  
	    
	});
	
</script>