<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="noticeViewForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">公告详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key">*公告编号：</label>
						</td>
						<td>
							<label class="control-label val">${notice.noticeNo}</label>
						</td>
						<td>
							<label class=" control-label key">*公告类型：</label>
						</td>
						<td>
							 <label class="control-label val">
							   <#if notice.noticeType==1>
							 	  公告广告
							   </#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">*公告名称：</label>
						</td>
						<td>
							 <label class="control-label val">${notice.noticeName}</label>
						</td>

						<td>
							<label class=" control-label key">*公告排名：</label>
						</td>
						<td>
							<label class="control-label val">${notice.ranking}</label>
						</td>
					</tr>
					<tr>
						<#if notice.noticePicUrl!=null&&notice.noticePicUrl!=''>
						<td>
						<label class=" control-label key">*公告图片：</label>
					</td>
					<td>
						<label class="control-label val">
                				<img src="${imagePath!''}/${notice.noticePicUrl}" width="200" height="180"/>
                				</label>
					</td>
					</#if>
						<#if notice.linkUrl!=null&&notice.linkUrl!=''>
						<td>
							<label class=" control-label key">*跳转链接：</label>
						</td>
						<td>
							<label class="control-label val">${notice.linkUrl}</label>
						</td>
						</#if>
					</tr>
					<tr>
						<#if notice.text1!=null&&notice.text1!=''>
					<td>
						<label class="col-sm-2 control-label key">*内容：</label>
					</td>
					<td>
						<div class="col-sm-7">
							${notice.text1}
							</div>
					</td>
					</#if>
						<#if notice.censorMemo!=null&&notice.censorMemo!=''>
					<td>
						<label class=" control-label key">*审核备注：</label>
					</td>
					<td>
						<textarea class="form-control val" rows="6"  name="censorMemo" disabled>${notice.censorMemo}</textarea>
					</td>
					</#if>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>审核状态：</label>
						</td>
						<td>
							 <label class="control-label val">
                                <#if notice.censorStatus==0>未审核
                                <#elseif notice.censorStatus==1>已审核
                                <#elseif notice.censorStatus==2>待审核
                                <#elseif notice.censorStatus==3>未通过
                                </#if>
							  </label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>公告展示类型：</label>
						</td>
						<td>
							 <#if notice.noticeMemberType==1>
									商家端
									<#else>
									客户端
									</#if>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							 <label class="control-label val">${notice.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${notice.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closenoticeView" class="btn-new-type-edit">
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
	  require.config({paths: {"notice":"res/js/marketing/notice_list"}});
		require(["notice"], function (notice){
			notice.init();
		});  
	});
</script>