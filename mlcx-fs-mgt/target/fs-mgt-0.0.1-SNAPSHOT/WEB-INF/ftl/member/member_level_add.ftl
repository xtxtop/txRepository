<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="memberLevelAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">会员等级添加</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>等级名称：</label>
						</td>
						<td>
							 <input class="form-control val" name="levelName" placeholder="请输入等级名称"/>
                             <span name="levelNameAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>等级折扣率：</label>
						</td>
						<td>
							<input class="form-control val" name="levelDiscount" placeholder="请输入等级折扣率"/>
							<span name="levelDiscountAdd"></span>
						</td>
					</tr>
					<tr>
						

						<td>
							<label class=" control-label key"><span>*</span>晋升所需消费额：</label>
						</td>
						<td>
							<input class="form-control val" name="upgradePoint" placeholder="请输入晋升所需消费额"/>
							<span name="upgradePointAdd"></span>
						</td>
                        <td>
							<label class=" control-label reason key"><span>*</span>备注：</label>
						</td>
						<td>
							<textarea name="memo" rows="1" cols="40" placeholder="请填写备注"></textarea>
							<span name="memoAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否可用：</label>
						</td>
						<td>
							<select name="isAvailable" class="form-control val">
									<option value="">请选择</option>
									<option value="0">不可用</option>
									<option value="1">可用</option>
								</select>
								<span name="isAvailableAdd"></span>
						</td>
						<td colspan="2"></td>
					</tr>
					

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveMemberLevel" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeMemberLevel" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"memberLevelAdd":"res/js/member/member_level_add"}});
		require(["memberLevelAdd"], function (memberLevelAdd){
			memberLevelAdd.init();
		}); 
	});    
</script>