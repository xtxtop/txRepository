<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="deviceEditForm">
		<input type="hidden" name="terminalDeviceNo"  value="${device.terminalDeviceNo}" readonly/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">终端设备编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>终端序列号：</label>
						</td>
						<td> 
							<input type="text" name="deviceSn"  value="${device.deviceSn}" readonly/>
							<input type ="button" class="btn btn-info" id="sss" onClick="test();" value="更改"/>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>品牌：</label>
							<input type="hidden" id="brandName" name="brandName" value="${device.brandName}"/>
						</td>
						<td>
							<select name="brandId" class="form-control val" onchange="selectSetValue('brandId','brandName')" id="brandId">
							    	<option value="">请选择</option>
							    	<#if brands?exists>
							    		<#list brands as brand>
							    			<option value="${brand.dataDictItemId}" <#if brand.dataDictItemId==device.brandId>selected=selected</#if> >${brand.itemValue}</option>
							    		</#list>
							    	</#if>
							    </select>
							 <span id="brandIdEdit"></span>   
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>型号：</label>
							<input type="hidden" id="deviceModel" name="deviceModel" value="${device.deviceModel}">
						</td>
						<td>
							<select name="deviceModelId" class="form-control val" onchange="selectSetValue('deviceModelId','deviceModel')" id="deviceModelId">
							    	<option value="">请选择</option>
							    	<#if deviceModels?exists>
							    		<#list deviceModels as model>
							    			<option value="${model.dataDictItemId}" <#if model.dataDictItemId==device.deviceModelId>selected=selected</#if> >${model.itemValue}</option>
							    		</#list>
							    	</#if>
							    </select>
							<span id="deviceModelIdEdit"></span> 
						</td>
						<td>
							<label class=" control-label key"><span>*</span>MAC地址：</label>
						</td>
						<td>
							<input class="form-control val" name="macAddr" value="${device.macAddr}"/>
								<#-- <label class="control-label val">${device.macAddr}</label>  -->
							<span id="macAddrEdit"></span>	
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">蓝牙名称：</label>
						</td>
						<td>
							<input class="form-control val" name="bluetoothName" value="${device.bluetoothName}"/>
						</td>
						<td>
							<label class=" control-label key">SIM卡号：</label>
						</td>
						<td>
							<input class="form-control val" name="simCardNo" value="${device.simCardNo}" maxlength="20"/>
							<span id="simCardNoEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
						<label class=" control-label key">城市：</label>
							
						</td>
						<td>
						<#if device.carPlateNo??>
							<input type="hidden" id="cityId" name="cityId" value="${device.cityId}">
							<div class="col-sm-6 ">
								<label class="control-label val">${device.cityName}</label>
							</div>
							<input type="hidden" id="cityName" name="cityName" value="${device.cityName}">
							<#else>
							
							<select name="cityId" class="form-control val" onchange="selectSetValue('cityId','cityName')" id="cityId">
							    	<option value="">请选择</option>
							    	<#if cities?exists>
							    		<#list cities as city>
							    			<option value="${city.dataDictItemId}" <#if city.dataDictItemId==device.cityId>selected=selected</#if> >${city.itemValue}</option>
							    		</#list>
							    	</#if>
								</select>
							<span id="cityIdEdit"></span>	
						</td>
						</#if>
						<td>
							<label class=" control-label key">绑定时间：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if device.bindingTime?exists>
								${device.bindingTime?string('yyyy-MM-dd')}
								</#if>
							</label>
						</td>
					</tr>
					<!--
					
					<tr>
						<td>
							<label class=" control-label key">终端状态：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if device.deviceStatus==1>
								在线
								<#elseif device.deviceStatus==2>
                				节能
                				<#elseif device.deviceStatus==3>
                				待机
                				<#elseif device.deviceStatus==4>
                				离线
                				<#elseif device.deviceStatus==5 >
                				休眠
                				</#if>
                			</label>
						</td>
						<td>
							<label class=" control-label key">信号强度：</label>
						</td>
						<td>
							<#if device.signalStrengthLevel ?exists>
                				<label>
                				<#if device.signalStrengthLevel==1>
								非常差
								<#elseif device.signalStrengthLevel==2>
                				差
                				<#elseif device.signalStrengthLevel==3>
                				一般
                				<#elseif device.signalStrengthLevel==4>
                				好
                				<#else>
                				非常好
                				</#if>
                				</label>
                			</#if>
						</td>
					</tr>
					
					
					
					-->
					
					<tr>
						<td>
							<label class=" control-label key">最后上报时间：</label>
						</td>
						<td>
							<label class="control-label val">
                				<#if device.lastReportingTime?exists>
                				${device.lastReportingTime?string('yyyy-MM-dd HH:mm:ss')}
                				</#if>
                				</label>
						</td>
						<td>
							<label class=" control-label key">版本号：</label>
						</td>
						<td>
							<label class="control-label val">${device.versionNumber}</label>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${device.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${device.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="saveDevice" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeDevice" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"deviceEdit":"res/js/device/device_edit"}});
		require(["deviceEdit"], function (deviceEdit){
			deviceEdit.init();
		});
	});
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>