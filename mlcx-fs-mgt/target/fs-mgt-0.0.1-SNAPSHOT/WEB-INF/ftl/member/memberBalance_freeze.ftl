<meta charset="utf-8">
<body>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-4 control-label"><h4><div id="tabTitleName">冻结/解冻余额</div></h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
	 				<form name="memberBalanceFreezeForm" class="form-horizontal">
	 					<input value = "${isFreeze}" name = "isFreeze" type = "hidden">
	 					<input value = "${memberBalance.memberNo}" name = memberNo type = "hidden">
	 					<div class="form-group col-md-6">
							<label class="col-sm-3 control-label"><span>*</span>&nbsp;&nbsp;会员编号：</label>
							<div class="col-sm-7">
							   <label class="control-label">${memberBalance.memberNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label"><span>*</span>&nbsp;&nbsp;会员姓名：</label>
							<div class="col-sm-7">
							   <label class="control-label">${memberBalance.memberName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label"><span>*</span>&nbsp;&nbsp;账户余额：</label>
							<div class="col-sm-7">
							   <label class="control-label">${memberBalance.balance}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label"><span>*</span>&nbsp;&nbsp;当前冻结状态：</label>
							<div class="col-sm-7">
							   <label class="control-label">
							   <#if memberBalance.isFreeze==0>
							 	  未冻结
							   </#if>
							   <#if memberBalance.isFreeze==1>
							 	  已冻结
							   </#if>
							</label>
							</div>
						</div>
						<#if memberBalance.isFreeze==1>
							<div class="form-group col-md-6">
								<label class="col-sm-3 control-label"><span>*</span>&nbsp;&nbsp;冻结人：</label>
								<div class="col-sm-7">
								   <label class="control-label">${memberBalance.freezePersonName}</label>
								</div>
							</div>
							
							<#if memberBalance.freezePerson??>
							<div class="form-group col-md-6">
								<label class="col-sm-3 control-label reason"><span>*</span>&nbsp;&nbsp;冻结理由：</label>
								<div class="col-sm-7">
								   <label class="control-label">${memberBalance.freezeReason}</label>
								</div>
							</div>
							</#if>
							<#if  memberBalance.freezeTime??>
		                        <div class="form-group col-md-6">
									<label class="col-sm-3 control-label"><span>*</span>&nbsp;&nbsp;冻结时间：</label>
									<div class="col-sm-7">
									    <label class="control-label">${memberBalance.freezeTime?string('yyyy-MM-dd  HH:mm:ss')}</label>
									</div>
								</div>
							</#if>
						</#if>
						<#if  memberBalance.updateTime??>
	                        <div class="form-group col-md-6">
								<label class="col-sm-3 control-label"><span>*</span>&nbsp;&nbsp;更新时间：</label>
								<div class="col-sm-7">
								    <label class="control-label">${memberBalance.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
								</div>
							</div>
						</#if>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label reason">&nbsp;&nbsp;<#if isFreeze==1>冻结</#if><#if isFreeze==0>解冻</#if>理由：</label>
							<div class="col-sm-7">
								<textarea class="form-control"   name="freezeReason"></textarea>
							</div>
						</div>
  					</form>					
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button id="closeMembeerBalance" class="btn btn-default pull-right navbar-btn btn-flat  btncolora">
                                <i class="glyphicon glyphicon-remove"></i>关闭
                        </button>
	                    <button id="updateMembeerBalance" class="btn btn-default pull-right navbar-btn btn-flat  btncolorb" >
	                            <i class="glyphicon glyphicon-check"></i><#if isFreeze==1>冻结</#if><#if isFreeze==0>解冻</#if>
	                    </button>
                    </div>
                </div>	
			</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberBalanceRecord":"res/js/member/memberBalance_freeze"}});
		require(["memberBalanceRecord"], function (memberBalance){
			memberBalance.init();
		});  
	});
</script>
</body>
