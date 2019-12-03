<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkingVoAddForm">
        <div class="col-md-12">
        <#if (pliesNumberList?size>0) >
         <div class="col-md-9">
        <#else>
         <div class="col-md-12">
        </#if>
		<div class="row hzlist">
			<table class="table">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">停车场详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span></span>停车场编号：</label>
						</td>
						<td><label class="control-label val">
								${parkingVo.parkingNo} </label></td>
						<td><label class=" control-label key"><span></span>停车场名称：</label>
						</td>
						<td><label class="control-label val">
								${parkingVo.parkingName} </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>运营城市：</label>
						</td>
						<td><label class="control-label val">
								${parkingVo.operatingCityName} </label></td>
						<td><label class=" control-label key"><span></span>地址：</label>
						</td>
						<td><label class="control-label val">
								${parkingVo.provinceName}${parkingVo.cityname}${parkingVo.districtName}${parkingVo.addrStreet} </label></td>
					</tr>
					<tr>
					   <td><label class=" control-label key"><span></span>坐标：</label>
                        </td>
                        <td><label class="control-label val">
                                                          经度：${parkingVo.longitude} 纬度：${parkingVo.latitude} </label></td>
                        <td><label class=" control-label key"><span></span>状态：</label>
                        </td>
                        <td><label class="control-label val">
                            <#if parkingVo.parkingStatus==0>
                                                                        启用
                             <#elseif parkingVo.parkingStatus==1>
                                                                            停用
                            <#else>
                            --
                            </#if>
                              </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key">类型：</label></td>
						<td><label class="control-label val"> <#if
								parkingVo.parkingType==0> 闸机 <#elseif parkingVo.parkingType==1>
								地锁 <#else> 无设备 </#if> </label></td>
								<td><label class=" control-label key"><span></span>在线状态：</label>
                        </td>
                        <td><label class="control-label val"> <#if
                                parkingVo.onlineStatus==0> 在线 <#else> 离线 </#if> </label></td>
					</tr>
					<tr>
                        <td><label class=" control-label key"><span></span>地下停车场层数：</label>
                        </td>
                        <td><label class="control-label val">
                                ${parkingVo.undergroundNumber} </label></td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>地下停车场总车位数：</label>
                        </td>
                        <td><label class="control-label val">
                                ${parkingVo.undergroundParkingSpaceNumber} </label></td>
                        <td><label class=" control-label key"><span></span>地下停车场剩余车位：</label>
                        </td>
                        <td><label class="control-label val">
                                ${parkingVo.undergroundSurplusSpaceNumber} </label></td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span></span>地面停车场总车位数：</label>
                        </td>
                        <td><label class="control-label val">
                                ${parkingVo.groundParkingSpaceNumber} </label></td>
                        <td><label class=" control-label key"><span></span>地面停车场剩余车位：</label>
                        </td>
                        <td><label class="control-label val">
                                ${parkingVo.groundSurplusSpaceNumber} </label></td>
                    </tr>
					<tr>
						<td><label class=" control-label key"><span></span>楼层停车场层数：</label>
                        </td>
                        <td><label class="control-label val">
                                ${parkingVo.pliesNumber} </label></td>
						<td><label class=" control-label key"><span></span>多入口层数：</label>
                        </td>
                        <td><label class="control-label val">
                                ${parkingVo.muchPliesNumber} </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>楼层停车场总车位数：</label>
						</td>
						<td><label class="control-label val">
								${parkingVo.parkingSpaceNumber} </label></td>
						<td><label class=" control-label key"><span></span>楼层停车场剩余车位：</label>
						</td>
						<td><label class="control-label val">
								${parkingVo.surplusSpaceNumber} </label></td>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>使用状态：</label>
						</td>
						<td><label class="control-label val"> <#if
								parkingVo.parkingStatus==0> 启用 <#else> 停用 </#if> </label></td>
						<td><label class=" control-label key">标签：</label></td>
						<td><#list clabel as cl> <label class="">
								${cl.labelName}</label> </#list>
							<div></div></td>
					</tr>
					<tr>
						<td><label class=" control-label key">服务：</label></td>
						<td><#list cmatching as cm> <label class="">
								${cm.matchingName}</label> </#list>
						</td>
						<td><label class=" control-label key">停车场图片：</label></td>
						<td><label class="control-label val"> <img
								src="${imagePath!''}/${parkingVo.fileUrl}" width="280"
								height="180" />
						</label></td>
					</tr>
					</tr>
					<tr>
						<td><label class=" control-label key"><span></span>营业时间：</label>
						</td>
						<td><label class="control-label val">
								${parkingVo.businessHours} </label></td>
						<td><label class=" control-label key"><span></span>节假日营业：</label>
						</td>
						<td><label class="control-label val"> <#if
								parkingVo.isBusinessFestival==1> 是 </#if> <#if
								parkingVo.isBusinessFestival==0> 否 </#if> </label></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeparkingView"
								class="btn-new-type-edit">关闭</button></td>
					</tr>
				</tfoot>
			</table>
			</div>
            </div>
             <#if (pliesNumberList?size>0) >
	         <div class="col-md-3">
            <div class="row hzlist">
                    <div style="height: 28px; text-align: center; padding-top: 3%;"> 地下停车场车位分布</div>     
                    <table class="table" style="margin-top: 5%;">
                        <thead class="tab-thead">
                    <tr style="width: 100%;">
                        <th style="text-align: center;width: 30%;">层数</th>
                        <th style="text-align: center;width: 35%;">总车位</th>
                        <th style="text-align: center;width: 35%;">剩余车位</th>
                    </tr>
                     </thead>
                        <tbody>
                            <#list pliesNumberList as pn>
                             <#if pn.spaceType==1>
                                <tr>
                                <td>${pn.pliesNumber }</td>
                                <td>${pn.parkingSpaceNumber }</td>
                                <td>${pn.surplusSpaceNumber }</td>
                                </tr>
                                </#if>
                            </#list>
                        
                        </tbody>
                    </table>
             </div>
               <div class="row hzlist">
                    <div style="height: 28px; text-align: center; padding-top: 3%;"> 地面停车场车位分布</div>     
                    <table class="table" style="margin-top: 5%;">
                        <thead class="tab-thead">
                    <tr style="width: 100%;">
                        <th style="text-align: center;width: 30%;">层数</th>
                        <th style="text-align: center;width: 35%;">总车位</th>
                        <th style="text-align: center;width: 35%;">剩余车位</th>
                    </tr>
                     </thead>
                        <tbody>
                            <#list pliesNumberList as pn>
                                <#if pn.spaceType==2>
                                <tr>
                                <td>${pn.pliesNumber }</td>
                                <td>${pn.parkingSpaceNumber }</td>
                                <td>${pn.surplusSpaceNumber }</td>
                                </tr>
                                </#if>
                            </#list>
                        
                        </tbody>
                    </table>
             </div>
               <div class="row hzlist">
                    <div style="height: 28px; text-align: center; padding-top: 3%;"> 楼层停车场车位分布</div>     
                    <table class="table" style="margin-top: 5%;">
                        <thead class="tab-thead">
                    <tr style="width: 100%;">
                        <th style="text-align: center;width: 30%;">层数</th>
                        <th style="text-align: center;width: 35%;">总车位</th>
                        <th style="text-align: center;width: 35%;">剩余车位</th>
                    </tr>
                     </thead>
                        <tbody>
                            <#list pliesNumberList as pn>
                             <#if pn.spaceType==3>
                                <tr>
                                <td>${pn.pliesNumber }</td>
                                <td>${pn.parkingSpaceNumber }</td>
                                <td>${pn.surplusSpaceNumber }</td>
                                </tr>
                                </#if>
                            </#list>
                        
                        </tbody>
                    </table>
             </div>
            </div>
	        
	        </#if>
            
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		$("#closeparkingView").click(function() {
			closeTab("停车场详情");

		});
	});
</script>