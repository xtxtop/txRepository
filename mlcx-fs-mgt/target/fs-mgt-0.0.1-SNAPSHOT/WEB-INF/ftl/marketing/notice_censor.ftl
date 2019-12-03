<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="noticeCensorForm">
		<input type="hidden" name="noticeNo" value="${notice.noticeNo}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">公告审核</th>
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
					<td  colspan="3">
						<label class="control-label val">
                				<img src="${imagePath!''}/${notice.noticePicUrl}" width="200" height="180"/>
                				</label>
					</td>
					</#if>
						
					</tr>
					<tr>
					<#if notice.linkUrl!=null&&notice.linkUrl!=''>
						<td>
							<label class=" control-label key">*跳转链接：</label>
						</td>
						<td colspan="3">
							<label class="control-label val">${notice.linkUrl}</label>
						</td>
						</#if>
					</tr>
					<tr>
						<#if notice.text1!=null&&notice.text1!=''>
					<td>
						<label class="col-sm-2 control-label key">*内容：</label>
					</td>
					<td  colspan="3">
						<div class="col-sm-8">
							${notice.text1}
							</div>
					</td>
					</#if>
					
					</tr>
					<tr>
					<td>
						<label class=" control-label key">*审核状态：</label>
					</td>
					<td>
						<input type="radio" name="censorStatus"  value="1" checked="checked">通过</input>
                                <input type="radio" name="censorStatus"  value="3" >不通过</input>
					</td>
						<td>
							<label class=" control-label key">*创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${notice.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						
					</tr>
					<tr>
					<td>
							<label class=" control-label key">*更新时间：</label>
						</td>
						<td>
							 <label class="control-label val">${notice.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key reason">*审核备注：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="6"  name="censorMemo" >${notice.censorMemo}</textarea>
							<span id="censorMemoCensor"></span>
						</td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="censorNotice" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeNoticeCensor" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"noticeCensor":"res/js/marketing/notice_censor"}});
		require(["noticeCensor"], function (noticeCensor){
			noticeCensor.init();
		});  
	    
	});
	
</script>