<meta charset="utf-8">
<body>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-4 control-label"><h4>会员余额详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
	 				<form name="memberBalanceViewForm" class="form-horizontal">
	 					<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;会员编号：</label>
							<div class="col-sm-7">
							   <label class="control-label val">${memberBalance.memberNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;会员姓名：</label>
							<div class="col-sm-7">
							   <label class="control-label val">${memberBalance.memberName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;账户余额：</label>
							<div class="col-sm-7">
							   <label class="control-label val">${memberBalance.balance}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;冻结状态：</label>
							<div class="col-sm-7">
							   <label class="control-label val">
							   <#if memberBalance.isFreeze==0>
							 	  未冻结
							   </#if>
							   <#if memberBalance.isFreeze==1>
							 	  已冻结
							   </#if>
							</label>
							</div>
						</div>
						<#if memberBalance.freezePersonName??>
							<div class="form-group col-md-6">
								<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;<#if memberBalance.isFreeze==1>冻结</#if><#if memberBalance.isFreeze==0>解冻</#if>人：</label>
								<div class="col-sm-7">
								   <label class="control-label val">${memberBalance.freezePersonName}</label>
								</div>
							</div>
						</#if>
						<#if memberBalance.freezePerson??>
							<div class="form-group col-md-6">
								<label class="col-sm-3 control-label reason key"><span></span>&nbsp;&nbsp;<#if memberBalance.isFreeze==1>冻结</#if><#if memberBalance.isFreeze==0>解冻</#if>理由：</label>
								<div class="col-sm-7">
								   <label class="control-label val">${memberBalance.freezeReason}</label>
								</div>
							</div>
						</#if>
						<#if  memberBalance.freezeTime??>
	                        <div class="form-group col-md-6">
								<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;<#if memberBalance.isFreeze==1>冻结</#if><#if memberBalance.isFreeze==0>解冻</#if>时间：</label>
								<div class="col-sm-7">
								    <label class="control-label val">${memberBalance.freezeTime?string('yyyy-MM-dd  HH:mm:ss')}</label>
								</div>
							</div>
						</#if>
						<#if  memberBalance.updateTime??>
	                        <div class="form-group col-md-6">
								<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
								<div class="col-sm-7">
								    <label class="control-label val">${memberBalance.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</div>
							</div>
						</#if>
  					</form>					
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button id="closeCouponView" class="btn btn-default pull-right navbar-btn btn-flat memberBalance-operate-close btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                                关闭
                        </button>
                    </div>
                </div>	
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberBalance":"res/js/member/memberBalance_list"}});
		require(["memberBalance"], function (memberBalance){
			memberBalance.init();
		});  
	});
</script>
</body>
