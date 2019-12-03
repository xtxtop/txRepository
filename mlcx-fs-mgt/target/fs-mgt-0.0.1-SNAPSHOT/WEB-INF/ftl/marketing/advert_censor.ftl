<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="advertCensorForm">
		<input type="hidden" name="advertNo" value="${advert.advertNo}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">活动审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="control-label key">*&nbsp;&nbsp;活动编号：</label>
						</td>
						<td>
							<label class="control-label val">${advert.advertNo}</label>
						</td>
						<td>
							<label class="control-label key">*&nbsp;&nbsp;活动类型：</label>
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
							<label class=" control-label key">*&nbsp;&nbsp;活动名称：</label>
						</td>
						<td>
							 <label class="control-label val">${advert.advertName}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;跳转类型：</label>
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
						<#if advert.nativeUrlType??>
						<td>
							<label class=" control-label key"><span></span>&nbsp;内部链接跳转路径</label>
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
								<label class=" control-label key">*&nbsp;&nbsp;活动排名：</label>
							</td>
							<td>
								 <label class="control-label val">${advert.ranking}</label>
							</td>
						
					</tr>
					
					<tr>
						<#if advert.advertPicUrl!=null&&advert.advertPicUrl!=''>
						<td>
							<label class=" control-l<label class=" control-label key">*&nbsp;&nbsp;活动图片：</label>
						</td>
						<td>
							<label class="control-label val">
                				<img src="${imagePath!''}/${advert.advertPicUrl}" width="320" height="200"/>
                				</label>
						</td>
						</#if>
						<#if advert.linkUrl!=null&&advert.linkUrl!=''>
						<td>
							<label class=" control-label key">*&nbsp;&nbsp;跳转链接：</label>
						</td>
						<td>
							<label class="control-label val">${advert.linkUrl}</label>
						</td>
						</#if>
						
					</tr>
					<tr>
					<#if advert.text1!=null&&advert.text1!=''>
							<td>
								<label class=" control-label key">*&nbsp;&nbsp;内容：</label>
							</td>
							<td>
								<div>
							${advert.text1}
							</div>
							</td>
							</#if>
							<td>
								<label class=" control-label key">*&nbsp;&nbsp;审核状态：</label>
							</td>
							<td>
								 <input type="radio" name="censorStatus"  value="1" checked="checked">通过</input>
                                <input type="radio" name="censorStatus"  value="3" >不通过</input>
							</td>
								
					</tr>
					<tr>
					<td>
									<label class=" control-label key reason">*&nbsp;&nbsp;审核备注：</label>
								</td>
								<td>
									<textarea class="form-control val" rows="6"  name="censorMemo" >${advert.censorMemo}</textarea>
									<span id="censorMemoCensor"></span>
									</td>
						<td>
							<label class=" control-label key">*&nbsp;&nbsp;创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${advert.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						
					</tr>
					<tr>
					<td>
							<label class=" control-label key">*&nbsp;&nbsp;更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${advert.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="censorAdvert" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAdvertCensor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"advertCensor":"res/js/marketing/advert_censor"}});
		require(["advertCensor"], function (advertCensor){
			advertCensor.init();
		});  
	    
	});
	
</script>