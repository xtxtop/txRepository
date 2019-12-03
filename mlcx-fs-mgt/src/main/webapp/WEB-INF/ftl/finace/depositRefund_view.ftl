<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="invoiceViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">退款详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;客户名称：</label>
						</td>
						<td>
							 <label class="control-label val">${depositRefund.memberName}
							    </label>
						</td>
						<td>
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;客户手机号：</label>
						</td>
						<td>
							 <label class="control-label val">${depositRefund.mobilePhone}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;申请时间：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.applyTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核状态：</label>
						</td>
						<td>
							<label class="control-label val">
							   <#if depositRefund.cencorStatus==0>未审核
							   <#elseif depositRefund.cencorStatus==1>已审核
							   <#elseif depositRefund.cencorStatus==3>未通过
							   </#if>
							   </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款状态：</label>
						</td>
						<td>
							<label class="control-label val">
							   <#if depositRefund.refundStatus==0>未退款
							   <#elseif depositRefund.refundStatus==1>已退款
							   </#if>
							   </label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款流水号：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.refundFlowNo}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款方法：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if depositRefund.refundMethod==1>支付宝
							   <#elseif depositRefund.refundMethod==2 || depositRefund.refundMethod==3>微信
							   <#elseif depositRefund.refundMethod==4>线下退款
							   </#if>
							</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款时间：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.refundTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建日期：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新日期：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
<<<<<<< .mine
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款金额：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.refundAmount?string(',###.##')}</label>
						</td>
					<td colspan="2"></td>
					</tr>
					<#if depositRefund.refundStatus==1>
					<tr>
							
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;操作人：</label>
						</td>
						<td>
							<label class="control-label val">${operatorName}</label>
						</td>
						
						<td colspan="2"></td>
					</tr>
					</#if>
					<#if depositRefund.cencorStatus!=0>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核备注：</label>
						</td>
						<td>
							<label class="control-label val">${depositRefund.cencorMemo}</label>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核人：</label>
						</td>
						<td>
							<label class="control-label val">${cencorName}</label>
						</td>
					</tr>
					</#if>
					
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeDepositRefundView" class="btn-new-type-edit">
                关闭
            </button></td>
||||||| .r6906
							</div>
						</div>
  					</form>					
=======
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;客户端申请退款理由：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.refundGrounds}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;客户端申请退款备注：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.refundGroundsMemo}</label>
							</div>
						</div>
  					</form>					
>>>>>>> .r6919

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>


<script type="text/javascript">
	$(function(){
	  require.config({paths: {"depositRefund":"res/js/finace/depositRefund_list"}});
		require(["depositRefund"], function (depositRefund){
			depositRefund.init();
		});  
	});
</script>