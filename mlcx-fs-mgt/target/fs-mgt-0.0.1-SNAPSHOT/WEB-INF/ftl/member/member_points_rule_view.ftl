<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员积分规则详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>积分业务类型：</label>
						</td>
						<td>
							<label class="control-label val">
									<#if memberPointsRule.businessType==1>订单支付
									<#elseif memberPointsRule.businessType==2>套餐支付</#if>
								</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>积分类型：</label>
						</td>
						<td>
							<label class="control-label val">
									<#if memberPointsRule.pointsType==0>会员经验积分
									<#elseif memberPointsRule.pointsType==1>可用于消费的积分</#if>
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>规则所对应的积分值：</label>
						</td>
						<td>
							<label class="control-label val">${memberPointsRule.pointsValue}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>是否是默认规则：</label>
						</td>
						<td>
							<label class="control-label val">
									<#if memberPointsRule.isDefault==0>非默认
									<#elseif memberPointsRule.isDefault==1>默认
									</#if>
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>是否可用：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if memberPointsRule.isAvailable==1>可用
									<#elseif memberPointsRule.isAvailable==0>不可用
								</#if>
							</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeMemberPointsRuleView" class="btn-new-type-edit">
                关闭
            </button></td>
					</tr>
				</tfoot>
			</table>
		</div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberPointsRule":"res/js/member/member_points_rule_list"}});
		require(["memberPointsRule"], function (memberPointsRule){
			memberPointsRule.init();
		});  
	});    
</script>