<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="reportingAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">违停详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-reporting key"><span></span>违停编号：</label>
						</td>
						<td><label class="control-reporting val">
								${reporting.reportingNo} </label></td>
							<td><label class=" control-reporting key"><span></span>上报时间：</label>
                        </td>
                        <td><label class="control-reporting val">
                                ${reporting.createTime?string('yyyy-MM-dd HH:mm:ss')}
                        </label></td>
					</tr>
					<tr>
						<td><label class=" control-reporting key"><span></span>会员名称：</label>
						</td>
						<td><label class="control-reporting val">
								${reporting.memberName} </label></td>
						<td><label class=" control-reporting key"><span></span>会员电话：</label>
						</td>
						<td><label class="control-reporting val">
								${reporting.mobilePhone} </label></td>
					
					</tr>
					<tr>
					    <td><label class=" control-reporting key"><span></span>地锁编号：</label>
                        </td>
                        <td><label class="control-reporting val">${reporting.lockNo}   </label></td>
                         <td><label class=" control-reporting key"><span></span>地锁名称：</label>
                        </td>
                        <td><label class="control-reporting val">${reporting.lockName}   </label></td>
					</tr>
					<#if (doc?size>0)>
					   <tr>
					       <td><label class=" control-reporting key"><span></span>违停图片</label>
                            </td><td colspan="3"> 
					   <#list doc as d>
					       <div style="width: 33%;float:left;">
						       <label class="control-label val">
                               <img src="${d.fileUrl}" width="230" height="180"/>
                               </label>
                           </div>
					   </#list>
					   </td>
                      </tr>
					</#if>
					<tr>
						<td><label class=" control-reporting key"><span></span>备注：</label>
                        </td>
                        <td colspan="3"><label class="control-reporting val"> 
                        <textarea class="form-control val" rows="3" cols="50" style="resize:none;" disabled="disabled">${reporting.remark }</textarea>
                         </label>  </td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button"
								id="closereportingView" onclick="closeTab();" class="btn-new-type-edit">
								关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
