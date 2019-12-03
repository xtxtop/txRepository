<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="advertCensorForm">
		<input type="hidden" name="advertNo" value="${advert.advertNo}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">广告审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class="control-label key">&nbsp;&nbsp;广告编号：</label>
						</td>
						<td><label class="control-label val">${advert.advertNo}</label>
						</td>
						<td><label class="control-label key">&nbsp;&nbsp;广告类型：</label>
						</td>
						<td><label class="control-label val"> <#if
								advert.type==1> 总首页 <#elseif advert.type==2> 充电桩首页 <#elseif
								advert.type==3> 无人停车<#elseif advert.type==4> 充电站列表 <#elseif
								advert.type==5> 个人中心 <#elseif advert.type==6> 地锁订单列表 <#elseif
								advert.type==7>地锁订单详情 <#elseif advert.type==8> 地锁订单评论 <#elseif
								advert.type==9> 充电订单列表 <#elseif advert.type==10> 充电订单详情<#elseif
								advert.type==11> 充电订单评论 <#elseif advert.type==12> 我的钱包 <#elseif
								advert.type==13> 充值记录 <#elseif
                                advert.type==14> 添加爱车 <#elseif
                                advert.type==15> 充电站详情<#elseif
                                advert.type==16> 停车场详情<#elseif
                                advert.type==17> 停车场列表<#elseif
                                advert.type==18> 地锁预约</#if></label></td>
					</tr>
					<tr>
						<td><label class=" control-label key">&nbsp;&nbsp;广告名称：</label>
						</td>
						<td><label class="control-label val">${advert.advertName}</label>
						</td>
						<td><label class=" control-label key">&nbsp;&nbsp;广告排名：</label>
						</td>
						<td><label class="control-label val">${advert.ranking}</label>
						</td>

					</tr>
					<#if advert.advertType!=5>
					<tr>
						<#if advert.linkType==0>
						<td><label class=" control-label key">&nbsp;&nbsp;跳转链接：</label>
						</td>
						<td colspan="3"><label class=" val">${advert.linkUrl}</label>
						</td> <#else> </#if>

					</tr>
					</#if>
					<tr>

						<td><label class=" control-label key">&nbsp;&nbsp;审核状态：</label>
						</td>
						<td><input type="radio" name="censorStatus" value="1"
							checked="checked">通过</input> <input type="radio"
							name="censorStatus" value="3">不通过</input></td>

						<td><label class=" control-label key">&nbsp;&nbsp;更新时间：</label>
						</td>
						<td><label class="control-label val">${advert.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>

					</tr>


					<tr>
						<td><label class=" control-label key reason"><span>*</span>&nbsp;&nbsp;审核备注：</label>
						</td>
						<td><textarea class="form-control val" rows="6"
								name="censorMemo">${advert.censorMemo}</textarea> <span
							id="censorMemoCensor"></span></td>
						<td><label class=" control-label key">&nbsp;&nbsp;创建时间：</label>
						</td>
						<td><label class="control-label val">${advert.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>

					</tr>


					<#if advert.advertType!=5> <#if advert.linkType==1>
					<tr id="advertPicUrlViewMengLong">
						<td><label class=" control-label key">&nbsp;&nbsp;内容：</label>
						</td>
						<td colspan="3">
							<div>${advert.text}</div>
						</td>

					</tr>
					<#else> </#if>
					<tr id="advertPicUrlCensorMengLong">
						<td><label class=" control-label class="control-labelkey">&nbsp;&nbsp;广告图片：</label>
						</td>
						<td colspan="3"><label class="control-label val"> <img
								src="${imagePath!''}/${advert.advertPicUrl}" width="320"
								height="200" />
						</label></td>
					</tr>
					</#if>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="censorAdvert"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button" id="closeAdvertCensor"
								class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"advertCensor" : "res/js/ml/advert/advert_censor"
			}
		});
		require([ "advertCensor" ], function(advertCensor) {
			advertCensor.init();
		});

	});
</script>