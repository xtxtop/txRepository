<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="memberPointsRuleAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员积分规则添加</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>积分业务类型：</label>
						</td>
						<td>
							<select name="businessType" class="form-control val">
									<option value="">请选择</option>
									<option value="1">订单支付</option>
									<option value="2">套餐支付</option>
									<option value="3">会员分享</option>
								</select>
							<span name="businessTypeAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>积分类型：</label>
						</td>
						<td>
							 <select name="pointsType" class="form-control val">
									<option value="">请选择</option>
									<option value="0">会员经验积分</option>
									<option value="1">可用于消费的积分</option>
									<option value="2">会员分享积分</option>
								</select>
							<span name="pointsTypeAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>规则所对应的积分值：</label>
						</td>
						<td>
							<input class="form-control val" name="pointsValue" placeholder="请输入规则所对应的积分值"/>
							<span name="pointsValueAdd"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveMemberPointsRule" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeMemberPointsRule" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"memberPointsRuleAdd":"res/js/member/member_points_rule_add"}});
		require(["memberPointsRuleAdd"], function (memberPointsRuleAdd){
			memberPointsRuleAdd.init();
		}); 
	});    
</script>