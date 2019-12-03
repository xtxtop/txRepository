<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="advertViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">活动详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;活动编号：</label>
						</td>
						<td>
							 <label class="control-label val">${advert.advertNo}</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;活动类型：</label>
						</td>
						<td>
							<label class="control-label val">
							   <#if advert.advertType==1>
							 	  活动广告
							   </#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;活动名称：</label>
						</td>
						<td>
							<label class="control-label val">${advert.advertName}</label>
						</td>

						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;跳转类型：</label>
						</td>
						<td>
							 <#if advert.jumpType==1>
								内部链接
							 </#if>
							  <#if advert.jumpType==2>
								外部链接
							 </#if>
							  <#if advert.jumpType==3>
								文章内容
							 </#if>
							 <span name="jumpTypeAdd"></span>
						</td>

					</tr>
					<tr>
						<#if advert.jumpType==1>
						<td>
							<label class="col-sm-3 control-label key"><span></span>&nbsp;内部链接跳转路径</label>
						</td>
						<td>
							<#if advert.nativeUrlType==1>
								邀请分享
							 </#if>
							  <#if advert.nativeUrlType==2>
								交纳押金
							 </#if>
							  <#if advert.nativeUrlType==3>
								充值余额
							 </#if>
							 <#if advert.nativeUrlType==4>
								身份认证
							 </#if>
						</td>
						<#else>
						</#if>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;活动排名：</label>
						</td>
						<td>
							<label class="control-label val">${advert.ranking}</label>
						</td>
					</tr>
					<tr>
						<#if advert.advertPicUrl!=null&&advert.advertPicUrl!=''>
					<td>
						<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;活动图片：</label>
					<td colspan="3">
						 <label class="control-label val">
                				<img src="${imagePath!''}/${advert.advertPicUrl}" width="200" height="180"/>
                				</label>
					</td>
					</#if>
				  
					</tr>
					<tr>
							<#if advert.linkUrl!=null&&advert.linkUrl!=''&& advert.jumpType==2 || advert.jumpType==3>
					<td>
						<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;跳转链接：</label>
					</td>
					<td colspan="3">
						<label class="control-label val">${advert.linkUrl}</label>
					</td>
					<#else>
						</#if>
					</tr>
					<tr>
						<#if advert.text1!=null&&advert.text1!='' && advert.jumpType==3>
						<td>
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;内容：</label>
						</td>
						<td colspan="3">
							<div>
							${advert.text1}
							</div>
						</td>
						<#else>
						</#if>
					</tr>
					<tr>
						
						
						<#if advert.censorMemo!=null&&advert.censorMemo!=''>
						<td>
							<label class="col-sm-4 control-label reason key"><span></span>&nbsp;&nbsp;审核备注：</label>
						</td>
						<td colspan="3">
							<textarea class="form-control val" rows="1"  name="censorMemo" disabled>${advert.censorMemo}</textarea>
						</td>
						</#if>
					</tr>
					<tr>
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
						<td>
							 <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;启动app活动页展示：</label>
						</td>
						<td>
							<#if advert.isStartAdvert==1>
									是
									<#else>
									否
									</#if>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;活动页展示类型：</label>
						</td>
						<td>
							<#if advert.advertMemberType==1>
									商家端
									<#else>
									客户端
									</#if>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${advert.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${advert.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td colspan="2"></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeAdvertView" class="btn-new-type-edit">
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
	  require.config({paths: {"advert":"res/js/marketing/advert_list"}});
		require(["advert"], function (advert){
			advert.init();
		});  
	});
</script>