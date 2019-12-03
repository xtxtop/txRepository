<meta charset="utf-8">
<body>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>历史开票详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-8">
					<form  class="form-horizontal">
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;开票类型：</label>
							<div class="col-sm-6">
							    <label class="control-label">
							    <#if invoice.invoiceType==1>增值税普通发票纸质版
							    <#elseif invoice.invoiceType==2>增值税专用发票
							    <#elseif invoice.invoiceType==3>增值税普通发票电子版
							    </#if>
							    </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;开票金额：</label>
							<div class="col-sm-6">
							   <label class="control-label">${invoice.invoiceAmount?string(',###.##')}元</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;开票抬头：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.invoiceTitle}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;订单号：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.bizObjId}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;收件人：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.name}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;联系手机：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.phone}</label>
							</div>
						</div>
						<#if invoice.invoiceType==1>
							<div class="form-group col-md-6">
								<label class="col-sm-3 control-label">&nbsp;&nbsp;收货地址：</label>
								<div class="col-sm-6">
								<label class="control-label">${invoice.address}</label>
								</div>
							</div>
						</#if>
						<#if invoice.invoiceType==3>
							<div class="form-group col-md-6">
								<label class="col-sm-3 control-label">&nbsp;&nbsp;邮箱地址：</label>
								<div class="col-sm-6">
								<label class="control-label">${invoice.emailAddress}</label>
								</div>
							</div>
						</#if>
						<#if invoice.invoiceType==2>
						<div class="form-group col-md-6">
								<label class="col-sm-3 control-label">&nbsp;&nbsp;收货地址：</label>
								<div class="col-sm-6">
								<label class="control-label">${invoice.address}</label>
								</div>
							</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;纳税人识别号：</label>
							<div class="col-sm-6">
							    <label class="control-label">${invoice.taxpayerNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;开户行：</label>
							<div class="col-sm-6">
							   <label class="control-label">${invoice.accountBank}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;账号：</label>
							<div class="col-sm-6">
							   <label class="control-label">${invoice.accountNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;地址：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.companyAddress}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;电话：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.tel}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;纳税人认定通知书：</label>
							<div class="col-sm-6">
							<label class="control-label"><img src="${imagePath!''}/${invoice.taxpayerNoticePic}"/></label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;增值税专用发票开票资料：</label>
							<div class="col-sm-6">
							<label class="control-label"><img src="${imagePath!''}/${invoice.invoiceInfo}"/></label>
							</div>
						</div>
						</#if>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;开票时间：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;开票状态：</label>
							<div class="col-sm-6">
							<label class="control-label">
							<#if invoice.invoiceStatus==0>未开票
							<#elseif invoice.invoiceStatus==1>已开票
							</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;发票号：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.invoiceNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;开票操作人：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.invoiceOperatorName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-6">
							<label class="control-label">${invoice.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						</form>			
                		<div class="form-group">
                			<div class="col-sm-4" style="margin-bottom:20px;">
                				<button id="closeInvoiceHistoryView" class="btn btn-default pull-right navbar-btn btn-flat carRecord-operate-save btncolora">
                				        <i class="glyphicon glyphicon-remove"></i>关闭
                				</button>
                			</div>
                		</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
	$(function(){
	   require.config({paths: {"invoicerHistory":"res/js/finace/invoiceHistory_list"}});
		require(["invoicerHistory"], function (invoicerHistory){
			invoicerHistory.init();
		});  
	});
</script>
</body>
