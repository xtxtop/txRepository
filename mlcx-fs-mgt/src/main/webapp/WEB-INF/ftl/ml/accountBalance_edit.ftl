<meta charset="utf-8">
<style>
.daterangepicker .calendar-date {
	border: 1px solid #ddd;
	padding: 4px;
	border-radius: 4px;
	background: #fff;
	display: none;
}
.daterangepicker{
    width: 185px;
    }
   .ranges{
    width:167px;
   }
   .applyBtn {
    width: 54px;
   }
   .cancelBtn  {
    width: 54px;
   }
</style>
<div class="container-fluid backgroundColor">
	<form name="accountBalanceEditForm">
	<input type="hidden" name="accountBalanceNo" value="${member.accountBalanceNo }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑余额</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>会员编号：</label>
						</td> 
						<td><input class="form-control val" name="memberNo"  value="${member.memberNo }" readonly="readonly"/>
							
						</td>
						<td><label class=" control-label key"><span>*</span>会员名称：</label>
                        </td>
                        <td>
                         <#if member.memberName>
                        <input type="text" class="form-control val" readonly="readonly"
                              name="memberName" value="${member.memberName}"/> 
                          <#else>
                          <input type="text" class="form-control val" readonly="readonly"
                              name="memberName" value="--"/>
                            </#if>   
                        </td>
					</tr>
					       <td><label class=" control-label key"><span>*</span>手机号：</label>
                        </td> 
                        <td><input class="form-control val" name="mobilePhone"  value="${member.mobilePhone }" readonly="readonly"/>
                            
                        </td>
                        <td><label class=" control-label key"><span>*</span>会员类型：</label>
                        </td>
                        <td>
                        <#if member.memberType==1>
                        <input type="text" class="form-control val" 
                             name="memberType" value="普通会员" readonly="readonly"/> 
                       <#else>
                       <input type="text" class="form-control val" 
                             name="memberType" value="集团会员" readonly="readonly"/> 
                       </#if>
                        </td>
                    </tr>
					<tr>
                        <td><label class=" control-label key"><span>*</span>充电余额：</label>
                        </td> <td>
                            <input type="text" class="form-control val" onclick="formVerify('chargingBalance_edit_accountBalance')"
                             placeholder="请输入充电余额" name="chargingBalance" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" value="${member.chargingBalance}"/> 
                        <span id="chargingBalance_edit_accountBalance_mlcx_verify"></span>
                        </td>
                        <td><label class=" control-label key"><span>*</span>停车余额：</label>
                        </td>
                        <td>
                        <input type="text" class="form-control val" onclick="formVerify('stopBalance_edit_accountBalance')"
                             placeholder="请输入停车余额" name="stopBalance" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" value="${member.stopBalance}"/> 
                        <span id="stopBalance_edit_accountBalance_mlcx_verify"></span>
                        </td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editaccountBalance"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="editcloseaccountBalance" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"accountBalanceEdit" : "res/js/ml/accountBalance_edit"
			}
		});
		require([ "accountBalanceEdit" ], function(accountBalanceEdit) {
			accountBalanceEdit.init();
		});
	});
