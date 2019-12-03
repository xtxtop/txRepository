<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carDetailForm" class="form-horizontal">
		<input type="hidden" name="faultId" value="${carFault.faultId}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">故障详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>车牌号：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.carPlateNo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>型号：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.carModelName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>城市：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.cityName}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>故障时间：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.recordFaultTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>故障地点：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.faultLocation}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>故障等级：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if carFault.faultLevel==0>
							 一级
							</#if>
							<#if carFault.faultLevel==1>
							 二级
							</#if>
							<#if carFault.faultLevel==2>
							 三级
							</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>处理状态：</label>
						</td>
						<td>
							<label class="control-label val">
						    <#if carFault.processingStatus==0>
							未处理
							</#if>
							<#if carFault.processingStatus==1>
							 处理中
							</#if>
							<#if carFault.processingStatus==2>
							 已处理
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>用车类型：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if carFault.useCarType==1>
							订单用车
							</#if>
							<#if carFault.useCarType==2>
							 调度用车
							</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>单据号：</label></td>
						<td>
							<label class="control-label val">${carFault.documentNo}</label>
						</td>
						<td><label class=" control-label key"><span>*</span>用车人：</label></td>
						<td>
							<label class="control-label val">${carFault.driverName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label reason key">备注：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.memo}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>创建日期：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>更新日期：</label>
						</td>
						<td>
							<label class="control-label val">${carFault.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeCarFault" class="btn-new-type-edit">
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
	  require.config({paths: {"carFaultEdit":"res/js/car/car_Fault_edit"}});
		require(["carFaultEdit"], function (carFaultEdit){
			carFaultEdit.init();
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