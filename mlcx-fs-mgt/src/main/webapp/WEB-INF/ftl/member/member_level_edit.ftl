<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="memberLevelEditForm">
		<input type="hidden" name="memberLevelId" value="${memberLevel.memberLevelId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员等级编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>等级名称：</label>
						</td>
						<td>
							 <input class="form-control val" name="levelName" value="${memberLevel.levelName}"/>
                             <span name="levelNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>等级折扣率：</label>
						</td>
						<td>
							<input class="form-control val" name="levelDiscount" value="${memberLevel.levelDiscount}"/>
							<span name="levelDiscountEdit"></span>
						</td>
					</tr>
					<tr>
						

						<td>
							<label class=" control-label key"><span>*</span>晋升所需消费额：</label>
						</td>
						<td>
							<input class="form-control val" name="upgradePoint" value="${memberLevel.upgradePoint}"/>
							<span name="upgradePointEdit"></span>
						</td>
                        <td>
							<label class=" control-label reason key"><span>*</span>备注：</label>
						</td>
						<td>
							<textarea rows="1" name="memo">${memberLevel.memo}</textarea>
							<span name="memoEdit"></span>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span>*</span>是否可用：</label>
						</td>
						<td>
							<select name="isAvailable" class="form-control val">
									<option value="0" <#if memberLevel.isAvailable==0>selected="selected"</#if>>不可用</option>
									<option value="1" <#if memberLevel.isAvailable==1>selected="selected"</#if>>可用</option>
								</select>
								<span name="isAvailableEdit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
					

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editMemberLevel" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeMemberLevelEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"memberLevelEdit":"res/js/member/member_level_edit"}});
		require(["memberLevelEdit"], function (memberLevelEdit){
			memberLevelEdit.init();
		}); 
	});    
</script>