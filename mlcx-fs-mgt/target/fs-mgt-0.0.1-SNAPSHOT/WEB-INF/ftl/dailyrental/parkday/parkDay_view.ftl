<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">门店详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>门店名称：</label>
						</td>
						<td>
							<label class="control-label val">
							    ${parkDay.parkName}
							    </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>城市：</label>
						</td>
						<td>
							<label class="control-label val">
								${parkDay.cityName}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>租赁商：</label>
						</td>
						<td>
							<label class="control-label val">
								${parkDay.merchantName}
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>联系人姓名：</label>
						</td>
						<td>
							<label class="control-label val">
								${parkDay.cantactPerson}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>联系人电话：</label>
						</td>
						<td>
							<label class="control-label val">
								${parkDay.mobilePhone}
								</label>
						</td>
						<#if parkDay.isVailable==1>
						<td>
							<label class=" control-label key"><span></span>状态：</label>
						</td>
						<td>
							<label class="control-label val">
							 启用
							</label>
						</td>
						<#else>
						<td>
							<label class=" control-label key"><span></span>状态：</label>
						</td>
						<td>
							<label class="control-label val">
							 停用
							</label>
						</td>
						</#if>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>图片：</label>
						</td>
						<td>
							<label class="control-label val">
                				<img src="${imagePath!''}/${parkDay.parkDayPhoto}" width="200" height="180"/>
                				</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>详细地址：</label>
						</td>
						<td>
							<label class="control-label val">
								${parkDay.addrStreet}
								</label>
						</td>
					</tr>
					<tr>
					<td>
					<label class=" control-label key">门店位置：</label>
					</td>
						<td colspan="3">
							<input type="hidden" id="parkDayLongitudeView" value="${parkDay.longitude}"/>
                        <input type="hidden" id="parkDayLatitudeView" value="${parkDay.latitude}"/>
                        <input type="hidden" id="parkDayAddrStreetView" value="${parkDay.addrStreet}"/>
								<div id="parkDayViewMap"style="height:500px;">
								</div>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeParkDay" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
</div>

 <script type="text/javascript">
    $(function () {
      require.config({paths: {"parkDay":"res/js/dailyrental/parkday/parkDay_list"}});
		require(["parkDay"], function (parkDay){
			parkDay.init();
			//详情页面地图展示
			parkDay.creatMaker();
		});
    });
</script>