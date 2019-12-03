<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">计费规则详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>名称：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.ruleName}</lable>
						</td>
						<td>
							<label class=" control-label"><span>*</span>城市：</label>
						</td>
						<td>
							 <label class="control-label val">${pricingRule.cityName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>起步价：</label>
						</td>
						<td>
						<label class="control-label val">${pricingRule.baseFee}元</lable>
					    </td>
						<td>
							<label class=" control-label key"><span></span>车型：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.carModelName}</lable>
						</td>
					</tr>
					
					<tr>
						<#if pricingRule.companyId!=null&&pricingRule.companyId!=''>
						<td>
							<label class=" control-label key"><span></span>集团名称：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.companyName}</lable>
						</td>
						</#if>
                         <td>
							<label class=" control-label key"><span></span>是否标准计费：</label>
						</td>
						<td>
							 <label class="control-label val">
							<#if pricingRule.isStandardBilling==1>
							是
							<#else>
							否
							</#if>
							</lable>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>优先级：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.priority}</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>有效日期：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.availableTime1?string('yyyy-MM-dd hh:mm:ss')}至${pricingRule.availableTime2?string('yyyy-MM-dd HH:mm:ss')}</lable>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>编辑人：</label>
						</td>
						<td>
							<label class="control-label val">${sysUser.userName}</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if pricingRule.isAvailable==1>
							启用
							<#else>
							停用
							</#if>
							</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if pricingRule.cencorStatus==0>
							未审核
							</#if>
							<#if pricingRule.cencorStatus==1>
							已审核
							</#if>
							<#if pricingRule.cencorStatus==2>
							待审核
							</#if>
							<#if pricingRule.cencorStatus==3>
							未通过
							</#if>
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>审核日期：</label>
						</td>
						<td>
							<label class="control-label val">
								${pricingRule.cencorTime?string('yyyy-MM-dd HH:mm:ss')} 
							</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>审核人：</label>
						</td>
						<td>
							<label class="control-label val"> ${sysUser1.userName}</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}</lable>
						</td>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>工作日按时间计费：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.priceOfMinute}元/分钟</lable>
						</td>
					</tr>
					<tr>
						<td>
							 <label class=" control-label key"><span></span>工作日按里程计费：</label>
						</td>
						<td>
						<label class="control-label val">${pricingRule.priceOfKm}元/公里</lable>
						</td>
						<td>
							 <label class=" control-label key"><span></span>工作日计费封顶：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.billingCapPerDay}元/天</lable>
						</td>
					</tr>
					 <#if orderCaculateType==1>
					<tr>
						<td>
							 <label class=" control-label key"><span></span>周末按时间计费：</label>
						</td>
						<td>
							 <label class="control-label val">${pricingRule.priceOfMinuteOrdinary}元/分钟</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>周末按里程计费：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.priceOfKmOrdinary}元/公里</lable>
						</td>
					</tr>
					<tr>
						<td>
						<label class=" control-label key"><span></span>周末计费封顶：</label>
						</td>
						<td>
							<label class="control-label val">${pricingRule.billingCapPerDayOrdinary}元/天</lable>
						</td>
						<td colspan="2"></td>
					</tr>
					</#if>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
      <td colspan="4"><button type="button" id="closeViewpricingRule" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRule":"res/js/marketing/pricingRule_list"}});
		require(["pricingRule"], function (pricingRule){
			pricingRule.init();
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
         $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>