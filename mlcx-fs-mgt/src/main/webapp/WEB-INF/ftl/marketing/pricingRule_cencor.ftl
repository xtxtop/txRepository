<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingRuleCencorForm">
		<input type="hidden" name="ruleNo" value="${pricingRule.ruleNo}"/>
		<input type="hidden" name="ruleName" value="${pricingRule.ruleName}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">计费规则审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>名称：</label>
						</td>
						<td>
							 <label class="control-label">${pricingRule.ruleName}</lable>
						</td>
						<td>
							<label class=" control-label"><span>*</span>城市：</label>
						</td>
						<td>
							  <label class="control-label">${pricingRule.cityName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>车型：</label>
						</td>
						<td>
						<label class="control-label">${pricingRule.carModelName}</lable>
						<td>
							<label class=" control-label"><span>*</span>按时间计费：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.priceOfMinute}元/分钟</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>起步价：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.baseFee}元</lable>
						</td>
                         <td>
							<label class=" control-label key"><span>*</span>工作日按时间计费：</label>
						</td>
						<td class="btn-btnA-con">
							 <input class="form-control val" name="priceOfMinute" value="${pricingRule.priceOfMinute}"/>
							<div class="btn-btnA">元/分钟</div>
							<span name="priceOfMinuteEdit"></span>
						</td>
					</tr>
						<#if pricingRule.companyId!=null&&pricingRule.companyId!=''>
					<tr>
						
						<td>
							<label class=" control-label"><span>*</span>集团名称：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.companyName}</lable>
						</td>
						<td>
							<label class=" control-label"><span>*</span>折扣：</label>
						</td>
						<td>
							 <label class="control-label">${pricingRule.discount}</lable>
						</td>
						</#if>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>计费封顶：</label>
						</td>
						<td class="btn-btnA-con">
							<label class="control-label">${pricingRule.billingCapPerDay}元/天</lable>
						</td>
						<td>
							<label class=" control-label"><span>*</span>是否标准计费：</label>
						</td>
						<td>
							<label class="control-label">
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
							<label class=" control-label"><span>*</span>优先级：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.priority}</lable>
						</td>
						<td>
							<label class=" control-label"><span>*</span>有效日期：</label>
						</td>
						<td>
							 <label class="control-label"> ${pricingRule.availableTime1?string('yyyy-MM-dd HH:mm:ss')}至${pricingRule.availableTime2?string('yyyy-MM-dd HH:mm:ss')}</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>编辑人：</label>
						</td>
						<td>
							<label class="control-label">${sysUser.userName}</lable>
						</td>
						<td>
							<label class=" control-label"><span>*</span>状态：</label>
						</td>
						<td>
							 <label class="control-label">
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
							<label class=" control-label"><span>*</span>审核状态：</label>
						</td>
						<td>
							<div class="" id="cencorStatusId">
							    <label class="">
	                               <input type="radio" name="cencorStatus" <#if  pricingRule.cencorStatus==1>checked</#if> value="1"> 已审核
	                            </label>
	                            <label class="">
	                               <input type="radio" name="cencorStatus"  <#if  pricingRule.cencorStatus==3>checked</#if> value="3">不通过
                                </label>
							</div>
						</td>
						<td>
							<label class=" control-label reason"><span>*</span>审核备注：</label>
						</td>
						<td>
							<textarea class="form-control"   name="cencorMemo"></textarea>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label"><span>*</span>创建时间：</label>
						</td>
						<td>
						<label class="control-label">${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}<label>
						</td>
						<td>
							<label class=" control-label"><span>*</span>更新时间：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}<label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="EditCencorPricingRule" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCencorPricingRule" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingRuleCencor":"res/js/marketing/pricingRule_cencor"}});
		require(["pricingRuleCencor"], function (pricingRuleCencor){
			pricingRuleCencor.init();
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