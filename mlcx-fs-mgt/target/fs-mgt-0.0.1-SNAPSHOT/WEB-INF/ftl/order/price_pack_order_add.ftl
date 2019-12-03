<meta charset="utf-8">
<style>
	.memo {
		line-height: 30px;
		resize: none;
	}
</style>
<div class="container-fluid backgroundColor">
	<form name="pricePackOrderAddForm">
		<input type="hidden" name="memberNos" value="${memberNos}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">套餐订单发放</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">套餐名称：</label>
							<input type="hidden" id="packageName" name="packageName"/>
						</td>
						<td>
							<select name="packageId" class="form-control val" onchange="selectSetValue('packageId','packageName')" id="packageId">
							    	<option value="">请选择</option>
							    	<#if pricePackageList?exists>
							    		<#list pricePackageList as package>
							    			<option value="${package.packageNo}">${package.packageName}</option>
							    		</#list>
							    	</#if>
							    </select>
							<span id="packageIdAdd"></span>
						</td>
						<td>
							<label class=" control-label key">发放数量：</label>
						</td>
						<td>
							<input class="form-control val" placeholder="请输入发放数量" name="sendQuantity" onkeyup="this.value=this.value.replace(/\D/g,'')" value="1"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">套餐金额：</label>
						</td>
						<td><input class="form-control val" name="packAmount" readonly  placeholder="请输入套餐金额"/></td>
					   <td>
							<label class=" control-label key">有效截止日期：</label>
							
					    </td>
					    <td>
								<input class="datetimepicker form-control val"  placeholder="请选择有效截至日期" name="vailableTime2" />
							<span id="vailableTime2Add"></span>
					    </td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addPricingPackOrder" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closePricingPackOrder" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
    	require.config({paths: {"pricePackOrderAdd":"res/js/order/price_pack_order_add"}});
		require(["pricePackOrderAdd"], function (pricePackOrderAdd){
			pricePackOrderAdd.init();
		});
	   }); 
	$(function () {
        $("input[name='vailableTime2']").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            startDate:new Date(), 
            minuteStep: 5,
            clearBtn: true,//清除按钮
            format: "yyyy-mm-dd hh:ii:ss"//日期格式
        }); 
    });
    </script>