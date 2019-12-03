<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员积分记录详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>积分业务类型：</label>
						</td>
						<td>
							<label class="control-label val">
									<#if memberPointsRecord.businessType==1>订单支付
									<#elseif memberPointsRecord.businessType==2>套餐支付
									</#if>
								</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>积分类型：</label>
						</td>
						<td>
							<label class="control-label val">
									<#if memberPointsRecord.pointsType==0>会员经验积分
									<#elseif memberPointsRecord.pointsType==1>可用于消费的积分</#if>
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>操作类型：</label>
						</td>
						<td>
							<label class="control-label val">
									<#if memberPointsRecord.opType==0>扣除/使用积分
									<#elseif memberPointsRecord.opType==1>增加/获得积分</#if>
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>积分值：</label>
						</td>
						<td>
							<label class="control-label val">${memberPointsRecord.pointsValue}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>业务数据：</label>
						</td>
						<td>
							<label class="control-label val">${memberPointsRecord.businessData}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>会员姓名：</label>
						</td>
						<td>
							<label class="control-label val">${memberPointsRecord.memberName}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label reason key"><span></span>备注：</label>
						</td>
						<td>
							<label class="control-label val">
									${memberPointsRecord.recordMemo}
								</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeMemberPointsRecordView" class="btn-new-type-edit">
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
	  require.config({paths: {"memberPointsRecord":"res/js/member/member_points_record_list"}});
		require(["memberPointsRecord"], function (memberPointsRecord){
			memberPointsRecord.init();
		});  
	});    
</script>