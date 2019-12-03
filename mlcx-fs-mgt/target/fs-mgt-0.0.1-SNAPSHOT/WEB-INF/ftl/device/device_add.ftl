<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="deviceAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">终端设备添加</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>终端序列号：</label>
						</td>
						<td> 
							<input class="form-control val" name="deviceSn" maxlength="20" placeholder="请输入终端序列号"/>
						    <span id="deviceSnAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>品牌：</label>
							<input type="hidden" id="brandName" name="brandName"/>
						</td>
						<td>
							<select name="brandId" class="form-control val" onchange="selectSetValue('brandId','brandName')" id="brandId">
							    	<option value="">请选择</option>
							    	<#if brands?exists>
							    		<#list brands as brand>
							    			<option value="${brand.dataDictItemId}">${brand.itemValue}</option>
							    		</#list>
							    	</#if>
							    </select>
							 <span id="brandIdAdd"></span>   
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>型号：</label>
							<input type="hidden" id="deviceModel" name="deviceModel">
						</td>
						<td>
							<select name="deviceModelId" class="form-control val" onchange="selectSetValue('deviceModelId','deviceModel')" id="deviceModelId">
							    	<option value="">请选择</option>
							    	<#if deviceModels?exists>
							    		<#list deviceModels as model>
							    			<option value="${model.dataDictItemId}">${model.itemValue}</option>
							    		</#list>
							    	</#if>
							    </select>
							 <span id="deviceModelIdAdd"></span>  
						</td>
						<td>
							<label class=" control-label key"><span>*</span>MAC地址：</label>
						</td>
						<td>
							<input class="form-control val" name="macAddr" value="" placeholder="请输入MAC地址"/>
							<span id="macAddrAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">蓝牙名称：</label>
						</td>
						<td>
							<input class="form-control val" name="bluetoothName" value="" placeholder="请输入蓝牙名称"/>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>SIM卡号：</label>
						</td>
						<td>
							<input class="form-control val" name="simCardNo" maxlength="20" placeholder="请输入SIM卡号"/>
							<span id="simCardNoAdd"></span>
						</td>
					</tr>
					<tr>
					
						<td>
						<label class=" control-label key">城市：</label>
							<input type="hidden" id="cityName" name="cityName">	
						</td>
						<td>
							<select name="cityId" class="form-control val" onchange="selectSetValue('cityId','cityName')" id="cityId">
							    	<option value="">请选择</option>
							    	<#if cities?exists>
							    		<#list cities as city>
							    			<option value="${city.dataDictItemId}">${city.itemValue}</option>
							    		</#list>
							    	</#if>
								</select>
							<span id="cityIdAdd"></span>	
						</td>
						<td colspan="2"></td>
						<!--
					
					
					<td>
							<label class=" control-label key"><span>*</span>终端状态：</label>
						</td>
						<td>
							<select name="deviceStatus" class="form-control val">
                					<option value="">请选择</option>
                					<option value="1">在线</option>
                					<option value="2">节能</option>
                					<option value="3">待机</option>
                					<option value="4">离线</option>
                					<option value="5">休眠</option>
                				</select>
                			<span id="deviceStatusAdd"></span>
						</td>
					-->
						
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="saveAddDevice" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeAddDevice" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"deviceAdd":"res/js/device/device_add"}});
		require(["deviceAdd"], function (deviceAdd){
			deviceAdd.init();
		});  
	});
    $(function () {
        $(".datetimepicker").datetimepicker({
	        language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            clearBtn: true,//清除按钮
            format: "yyyy-mm-dd hh:ii:ss"//日期格式
        });
    });
</script>