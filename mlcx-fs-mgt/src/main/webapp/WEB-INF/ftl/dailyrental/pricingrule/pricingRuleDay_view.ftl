<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingRuleViewCustomDateForm">
		<input type="hidden" name="ruleId" value="${pricingRule.ruleId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">日租计费规则详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;名称：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.ruleName}</lable>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;车型：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.carModelName}</lable>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class="col-sm-4 control-label"><span>*</span>工作日计费：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.priceOfDay}元/天</lable>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>周末计费：</label>
						</td>
						<td>
							 <label class="control-label">${pricingRule.priceOfDayOrdinary}元/天</lable>
						</td>
						
					</tr>
					<tr>
						
						<td>
							<label class="col-sm-4 control-label"><span>*</span>不计免赔：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.regardlessFranchise}元/天</lable>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>保险费：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.insuranceAmount}元/天</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;状态：</label>
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
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;审核状态：</label>
						</td>
						<td>
							<label class="control-label">
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
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;审核日期：</label>
						</td>
						<td>
							<label class="control-label">
								${pricingRule.cencorTime?string('yyyy-MM-dd HH:mm:ss')} 
							</lable>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;审核人：</label>
						</td>
						<td>
							 <label class="control-label"> ${sysUser.userName}</lable>
						</td>
					</tr>
					<tr>
						<td>
						<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;审核备注：</label>
						</td>
						<td>
							<label class="control-label"> ${pricingRule.cencorMemo}</lable>
						</td>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							<label class="control-label">${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}</lable>
						</td>
						<td></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeViewpricingRuleDay" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
			        		<div class="row">
			      <div class="col-xs-12">
			       <!--定义操作列按钮模板-->
			       <script id="pricingRuleViewCustomDateTpl" type="text/x-handlebars-template">
			        {{#each func}}
			        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
			        {{/each}}
			       </script>
			       <div class="box">
			        <div class="box-body">
			         <table id="pricingRuleViewCustomDateList" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			       </div><!-- /.box -->
			      </div>
			    </div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleDayView":"res/js/dailyrental/pricingrule/pricingRuleDay_view"}});
		require(["pricingRuleDayView"], function (pricingRuleDayView){
			pricingRuleDayView.init();
		});  
    });
</script>