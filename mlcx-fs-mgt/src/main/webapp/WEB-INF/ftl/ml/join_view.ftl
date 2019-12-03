<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="joinAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">加盟详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-join key"><span></span>加盟编号：</label>
						</td>
						<td><label class="control-join val">
								${join.joinNo} </label></td>
								    <td><label class=" control-join key"><span></span>加盟类型：</label>
                        </td>
                        <td><label class="control-join val"> <#if
                                join.joinType==1> 充电桩 <#else>停车场 </#if> </label></td>
					</tr>
					<tr>
						<td><label class=" control-join key"><span></span>会员名称：</label>
						</td>
						<td><label class="control-join val">
								${join.memberName} </label></td>
						<td><label class=" control-join key"><span></span>会员电话：</label>
						</td>
						<td><label class="control-join val">
								${join.mobilePhone} </label></td>
					
					</tr>
					<tr>
						<td><label class=" control-join key"><span></span>加盟时间：</label>
						</td>
						<td><label class="control-join val">
								${join.createTime?string('yyyy-MM-dd HH:mm:ss')}
						</label></td>
						<td><label class=" control-join key"><span></span>备注：</label>
                        </td>
                        <td><label class="control-join val"> 
                        <textarea class="form-control val" rows="3" cols="50" style="resize:none;" disabled="disabled">${join.remark }</textarea>
                         </label>  </td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closejoinView" onclick="closeTab();" class="btn-new-type-edit">
								关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
