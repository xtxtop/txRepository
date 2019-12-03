<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="memberPointsRuleEditForm">
		<input type="hidden" name="ruleId" value="${memberPointsRule.ruleId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员积分规则编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>积分业务类型：</label>
						</td>
						<td>
							<select name="businessType" class="form-control val" disabled>
									<option value="1" <#if memberPointsRule.businessType==1>selected="selected"</#if>>订单支付</option>
									<option value="2" <#if memberPointsRule.businessType==2>selected="selected"</#if>>套餐支付</option>
									<option value="3" <#if memberPointsRule.businessType==3>selected="selected"</#if>>会员分享</option>
								</select>
							<span name="businessTypeEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>积分类型：</label>
						</td>
						<td>
							 <select name="pointsType" class="form-control val">
									<option value="0" <#if memberPointsRule.pointsType==0>selected="selected"</#if>>会员经验积分</option>
									<option value="1" <#if memberPointsRule.pointsType==1>selected="selected"</#if>>可用于消费的积分</option>
									<option value="2" <#if memberPointsRule.pointsType==2>selected="selected"</#if>>会员分享积分</option>
								</select>
							<span name="pointsTypeEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>规则所对应的积分值：</label>
						</td>
						<td>
							<input class="form-control val" name="pointsValue" value="${memberPointsRule.pointsValue}"/>
							<span name="pointsValueEdit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editMemberPointsRule" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeMemberPointsRuleEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"memberPointsRuleEdit":"res/js/member/member_points_rule_edit"}});
		require(["memberPointsRuleEdit"], function (memberPointsRuleEdit){
			memberPointsRuleEdit.init();
		}); 
	});    
</script>