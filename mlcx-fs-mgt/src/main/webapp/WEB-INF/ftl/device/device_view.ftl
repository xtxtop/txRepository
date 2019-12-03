<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="deviceForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">终端设备详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;终端序列号：</label>
						</td>
						<td> 
							<label class="control-label val"> ${device.deviceSn}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;品牌：</label>
						</td>
						<td>
							<label class="control-label val"> ${device.brandName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;型号：</label>
						</td>
						<td>
							<label class="control-label val">${device.deviceModel}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;MAC地址：</label>
						</td>
						<td>
							<label class="control-label val">${device.macAddr}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-5 control-label key">&nbsp;&nbsp;蓝牙名称：</label>
						</td>
						<td>
							<label class="control-label val">${device.bluetoothName}</label>
						</td>
						<td>
							<label class="col-sm-5 control-label key">&nbsp;&nbsp;SIM卡号：</label>
						</td>
						<td>
							<label class="control-label val">${device.simCardNo}</label>
						</td>
					</tr>
					<tr>
						<td>
						<label class="col-sm-4 control-label key">&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<label class="control-label val">${device.cityName}</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;绑定车辆：</label>
						</td>
						<td>
							<label class="control-label val">${device.carPlateNo}</label>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;绑定时间：</label>
						</td>
						<td>
							<label class="control-label val">${device.carPlateNo}</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;绑定时间：</label>
						</td>
						<td>
							<label class="control-label val">${device.bindingTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						
					</tr>
					
					<!--
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;终端状态：</label>
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
                				<#else>
                				休眠
                				</#if>
	                			</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;信号强度：</label>
						</td>
						<td>
							<label class="control-label val">
                			<#if device.signalStrengthLevel ?exists>
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
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;最后上报时间：</label>
						</td>
						<td>
							<label class="control-label val">${device.lastReportingTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;版本号：</label>
						</td>
						<td>
							<label class="control-label val">${device.versionNumber}</label>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${device.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${device.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr style="text-align:center">
      <td colspan="4"><button type="button" id="closeDevice" class="btn-new-type-edit">
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
      require.config({paths: {"device":"res/js/device/device_list"}});
		require(["device"], function (device){
			device.init();
		});
		});
 </script>