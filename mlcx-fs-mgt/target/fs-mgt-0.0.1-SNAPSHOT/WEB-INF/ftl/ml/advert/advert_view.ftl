<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="advertMengLongViewForm">
	   <input type="hidden" value="${advert.type }" id="typeView">
	   <input type="hidden" value="${advert.advertType }" id="advertTypeView">
	   <input type="hidden" value="${advert.advertPosition }" id="advertPositionView">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">广告详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;广告编号：</label>
						</td>
						<td>
							 <label class="control-label val">${advert.advertNo}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;广告名称：</label>
						</td>
						<td>
							<label class="control-label val">${advert.advertName}</label>
						</td>
                      </tr>
                      <tr >
                       <td>
                            <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;广告类型：</label>
                        </td>
                        <td>
                            <label class="control-label key">
                              <span id="type_view" style="color:#2d2c2b;"></span>
                            </label>
                        </td>
                        <td>
                            <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;广告排名：</label>
                        </td>
                        <td>
                            <label class="control-label val">${advert.ranking}</label>
                        </td>
					</tr>
					   <tr id="advert_view_tr">
                        <td>
                            <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;区域类型：</label>
                        </td>
                        <td colspan="3">
                             <label control-label key">
                                <span id="advertType_view"></span></label>
                        </td>
                     <tr>
                        <td>
                            <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;区域位置：</label>
                        </td>
                        <td>
                             <label control-label key">
                                <span id="advertPosition_view"></span></label>
                        </td>
                        <td>
                        <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;审核状态：</label>
                        </td>
                        <td>
                            <label class="control-label val">
                                <#if advert.censorStatus==0>未审核
                                <#elseif advert.censorStatus==1>已审核
                                <#elseif advert.censorStatus==2>待审核
                                <#elseif advert.censorStatus==3>未通过
                                </#if></label>
                        </td>
                    </tr>
                    <#if advert.advertType!=5>
					 <#if advert.linkType==0>
					<tr id="advertPicUrlViewMengLong">
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;跳转链接：</label>
						</td>
						<td colspan="3">
							<label class="control-label val">${advert.linkUrl}</label>
						</td>
					</tr>
					<#else>
					</#if>
					</#if>
					 <#if advert.linkType==2>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;选择车型：</label>
						</td>
						<td colspan="3">
							<label class="control-label val">${advert.carModelName}</label>
						</td>
						
					</tr>
					<#else>
					</#if>
					
					<tr>
						<#if advert.censorMemo!=null&&advert.censorMemo!=''>
						<td>
                            <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;审核备注：</label>
						</td>
						<td colspan="3">
							<textarea class="form-control val" rows="1"  name="censorMemo" disabled>${advert.censorMemo}</textarea>
						</td>
						</#if>
					</tr>
					<tr id="advertPicUrlViewMengLong">
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;广告图片：</label>
						<td colspan="3">
						 	<label class="control-label val">
                				<img src="${imagePath!''}/${advert.advertPicUrl}" width="200" height="180"/>
                			</label>
						</td>
					</tr>
					
					<tr>
						
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${advert.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${advert.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					  <#if advert.advertType!=5>
					 <#if advert.linkType==1>
					<tr id="advertPicUrlViewMengLong">
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;内容：</label>
						</td>
						<td colspan="3">
							<div>
							${advert.text}
							</div>
						</td>
					</tr>
					<#else>
					</#if>
					</#if>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" onclick="closeTab();" id="closeAdvertView" onclick="closeTab();" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
