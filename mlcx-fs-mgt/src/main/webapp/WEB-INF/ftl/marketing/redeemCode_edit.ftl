<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>兑换码编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
						<form  class="form-horizontal" name="redeemCodeEditFrom">
						<input type="hidden" name="redCode" value="${redeemCode.redCode}"/>
						<div class="form-group col-md-8">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="redName" value="${redeemCode.redName}"/>
							</div>
							<div class="col-sm-7"><span name="redNameEdit"></span></div>
						</div>
						<div class="form-group col-md-8 availableTime-2">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;有效日期：</label>
							<div class="col-sm-7">
							<input id="datetimepicker1" class="form-control" name="availableTime1" value="${redeemCode.availableTime1?string('yyyy-MM-dd')}"/>
							 至<input id="datetimepicker2" class="form-control" name="availableTime2" value="${redeemCode.availableTime2?string('yyyy-MM-dd')}"/>
							</div>
							<div><span name="availableTime1Edit"></span></div>
							<div><span name="availableTime2Edit"></span></div>
						</div>
						<div class="form-group col-md-8">
							<label class="col-sm-3 control-label reason key">&nbsp;&nbsp;备注：</label>
							<div class="col-sm-7">
							<textarea class="form-control val" rows="6"  name="remark">${redeemCode.remark}</textarea>
							</div>
							<input type="hidden" name="couponPlanString" value = ""/>
							<div style="margin-top:7px;"><span name="remarkEdit"></span></div>
						</div>	
					   </form>	
					   
					   <div class="form-group col-md-8">
							<label class="col-sm-3 control-label reason key"><span>*</span>&nbsp;&nbsp;优惠方案：</label>
							<div class="col-sm-3">
								<button type="button" class="btn btn-default" id="checkAddPlanBtnOfRedeemCodeEdit">方案列表</button>
								<input type="hidden" name="planNo" id="planNo"  value="${planNos}"/>
								<input type="hidden" name="planName" id="planName" value="${planTitle}"/>
								<input type="hidden" name="tempinput"/>
							</div>
							<div style="margin-top:7px;"><span name="planNoAdd"></span></div>
						</div>	
					 	<#list redeemCode.redeemCouponPlans as plan>
					 		<div class = "planNosModal">
					  			<div class="form-group col-md-8"><label class="col-sm-3 control-label reason key"></label>
								<div class="col-sm-7 planNoModal">
									<span class = "plan-no" hidden="true">${plan.planNo}</span>
									<span class = "plan-name">${plan.planTitle}</span>
									兑换数量：
									<input class="form-control val" value ="${plan.redQutity}"/>
								</div>
								<div style="margin-top:7px;"></div>
							</div>
	                    </#list>
					</div>	
        		</div><!-- /.row -->
                <div class="form-group col-md-8">
                    <div class="col-sm-8" style="margin-bottom:20px;">
                    <button type="button" id="closeEditRedeemCode" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; margin-right: 50px !important;color: #3f9fff;background: #fff;border-color: #3f9fff;">
                            	关闭
                    </button> 
                    <button type="button" id="editRedeemCode" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; background:#2b94fd;">
                            	保存
                    </button> 																				
                    </div>	
                </div>
			</div>

<div class="modal fade" id="couponPlanModalEdit" tabindex="-1" role="dialog">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择方案列表</div>
				</div>
		       <!--定义操作列按钮模板-->
		       <script id="couponPlanBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body box-body-change-padding">
			         <table id="couPonPlanListsOfRedeemCodeEdit" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="couponPlanToolss">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script type="text/javascript">
	$(function(){
	  require.config({paths: {"redeemCodeEdit":"res/js/marketing/redeemCode_edit"}});
		require(["redeemCodeEdit"], function (redeemCodeEdit){
			redeemCodeEdit.init();
		});  
    });
	
	/* $(".datetimepicker").datetimepicker({
        language: "zh-CN",
        minView: "month",
        autoclose: true,
        todayBtn: true,
        minuteStep: 5,
        format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
	 */
  	$("#datetimepicker1").datetimepicker({
         language: "zh-CN",
         autoclose: true,
         minView: "month",
         todayBtn: true,
         minuteStep: 5,
         format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
     });
	 
	$("#datetimepicker2").datetimepicker({
         language: "zh-CN",
         autoclose: true,
         minView: "month",
         todayBtn: true,
         minuteStep: 5,
         format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
     });
</script>
