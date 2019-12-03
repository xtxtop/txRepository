<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="orderShareSettingViewFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">订单分享页配置详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;订单分享名称：</label>
						</td>
						<td>
							<input class="form-control val" readonly value="${orderShareSetting.orderShareName}" />
						</td>
						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;分享链接的内容：</label>
						</td>
						<td>
							<input class="form-control val" readonly value="${orderShareSetting.orderShareMemo}" />
						</td>
					</tr>
					<tr>
						
						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;订单分享页图片：</label>
						</td>
						<td>
							<img width="100" height="80" name="orderSharePicUrl" src="${imagePath!''}/${orderShareSetting.orderSharePicUrl}"/>
						</td>
						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;订单分享icon图片：</label>
						</td>
						<td>
							<img width="100" height="80" name="orderShareIconUrl" src="${imagePath!''}/${orderShareSetting.orderShareIconUrl}"/>
						</td>
						
					</tr>
					<tr>
						
						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;订单分享页url：</label>
						</td>
						<td colspan="3">
							<input class="form-control val" readonly value="${orderShareSetting.linkUrl}" />
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;内容：</label>
						</td>
						<td colspan="3">
							<label class="control-label val">${orderShareSetting.orderShareContent}</label>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeOrderShareSettingView" class="btn-new-type-edit">
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
	  require.config({paths: {"orderShareSetting":"res/js/marketing/order_share_setting_list"}});
		require(["orderShareSetting"], function (orderShareSetting){
			orderShareSetting.init();
		});  
    });
</script>