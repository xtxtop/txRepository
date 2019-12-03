<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="deviceParameterForm">
	<input type="hidden" name="terminalDeviceNo"  value="${device.terminalDeviceNo}" />
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
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;数据库终端序列号：</label>
						</td>
						<td> 
							<label class="control-label val">${device.deviceSn}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;返回终端序列号：</label>
						</td>
						<td>
							<input type="text" name="deviceSn"  value="${device.deviceSn}" />
							
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;车牌号：</label>
						</td>
						<td>
							<input type="text" name="carPlateNo"  value="${device.carPlateNo}" />
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;平台域名或Ip：</label>
						</td>
						<td>
							<input type="text" name="domainName"  value="${domainName}" />
						</td>
					</tr>
					<tr>
					
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;终端型号：</label>
						</td>
						<td>
							<input type="text" name="deviceModel"  value="${deviceModel}" />
							
						</td>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;终端供应商：</label>
						</td>
						<td>
							<input type="text" name="brandName"  value="${brandName}" />
							
						</td>
					
					</tr>
					<tr>
					
						<td>
							<label class="col-sm-5 control-label key">&nbsp;&nbsp;ICCID：</label>
						</td>
						<td>
							<input type="text" name="iccid"  value="${iccid}" />
						</td>
						<td>
							<label class="col-sm-5 control-label key">&nbsp;&nbsp;MSISDN</label>
						</td>
						<td>
							<input type="text" name="msisdn"  value="${msisdn}" />
							
						</td>
					</tr>
					<tr>
						<td>
						<label class="col-sm-4 control-label key">&nbsp;&nbsp;终端软件版本号：</label>
						</td>
						<td>
							<input type="text" name="softwareVersion"  value="${softwareVersion}" />
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;终端硬件版本号：</label>
						</td>
						<td>
							<input type="text" name="hardwareVersion"  value="${hardwareVersion}" />
						</td>
						
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;VIN：</label>
						</td>
						<td>
							<input type="text" name="vin"  value="${vin}" />
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;终端租赁方式：</label>
						</td>
						<td>
							<select name="leaseMode" class="form-control val">
										<option  value="1" <#if leaseMode==1>selected="selected"</#if> >非租赁模式</option>
										<option  value="2" <#if leaseMode==2>selected="selected"</#if> >租赁模式</option>
							</select>
							
						</td>
						
					</tr>
					
				
					
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;平台端口：</label>
						</td>
						<td>
							<input type="text" name="port"  value="${port}" />
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;实时数据上频率：</label>
						</td>
						<td>
							<input type="text" name="frequency"  value="${frequency}" />
						</td>
						
					</tr>
					
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr style="text-align:center">
      <td colspan="2"><button type="button" id="saveDeviceParameter" class="btn-new-type-edit">
                重置终端参数
            </button></td>
      <td colspan="2"><button type="button" id="closeDeviceParameter" class="btn-new-type-edit">
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
      require.config({paths: {"deviceParameter":"res/js/device/device_parameter"}});
		require(["deviceParameter"], function (deviceParameter){
			deviceParameter.init();
		});
		});
 </script>