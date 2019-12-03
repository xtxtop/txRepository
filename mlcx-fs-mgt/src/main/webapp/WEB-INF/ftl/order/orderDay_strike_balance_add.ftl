<meta charset="utf-8">
<body>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-9">
						<div class="form-group">
							<label class="col-sm-12 control-label key"><h4>日租订单冲账</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8">
	 			<form name="orderDayStrikeBalanceAddForm" class="form-horizontal">
	 			<input type="hidden" name="orderNo" value="${order.orderNo}"/>
	 			<input type="hidden" name="memberId" value="${order.memberNo}"/>
				<input type="hidden" name="memberName" value="${order.memberName}"/>
				<input type="hidden" name="orderAmount" value="${order.orderAmount}"/>
				<input type="hidden" name="payAmount" value="${order.payableAmount}"/>
						<div class="form-group">
							<label class="col-sm-3 control-label key">订单编号：</label>
							<div class="col-sm-4">
							   <label class="control-la valbel">${order.orderNo}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">客户名称：</label>
							<div class="col-sm-4">
								<label class="control-labe vall">${order.memberName}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">订单金额：</label>
							<div class="col-sm-4">
								<label class="control-labe vall">${order.orderAmount?string(',###.##')}元</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">附加服务费：</label>
							<div class="col-sm-4">
								<label class="control-labe vall">${serviceFeeAmount?string(',###.##')}元</label>
							</div>
						</div>																								
						 <div class="form-group">
							<label class="col-sm-3 control-label key">应付金额：</label>
							<div class="col-sm-4">
								<label class="control-labe vall">${order.payableAmount?string(',###.##')}元</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">已冲账金额：</label>
							<div class="col-sm-4">
								<label class="control-labe vall">${order.strikeBalanceAmount?string(',###.##')}元</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">冲账金额：</label>
							<div class="col-sm-9">
								<label class="control-labe vall"><input type="text" name="strikeBalanceAmount" />元</label>
							</div>
						</div>
						<div class="form-group">
                			<label class="col-sm-3 c keyontrol-label">冲账原因：</label>
                			<div class="col-sm-9">
                				<label class=" valcontrol-label">
                				<select class="form-control" name="strikeBalanceReason">
           							<option value="1">故障</option>
           							<option value="2">没电</option>
          						</select>
                			</div>
                		</div>		
                		<div class="form-group">
                			<label class="col-sm-3 c keyontrol-label">冲账备注：</label>
                			<div class="col-sm-9">
	                			<label class=" valcontrol-label">
	                				 <textarea class="form-control" rows="6"  name="strikeBalanceMemo" ></textarea>
	                			</label>
                			</div>
                		</div>	
						</form>
                		<div class="form-group">
                			<div class="col-sm-6">
                				<button type="button" id="closeOrderDayStrikeBalanceAdd" class="btn btn-default pull-right " >关闭</button>
							<button type="button" id="addOrderDayStrikeBalance" class="btn btn-default pull-right " >保存</button>  		
                			</div>
                		</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
    $(function(){
    require.config({paths: {"orderDayStrikeBalanceAdd":"res/js/order/orderDay_strike_balance_add"}});
		require(["orderDayStrikeBalanceAdd"], function (orderDayStrikeBalanceAdd){
			orderDayStrikeBalanceAdd.init();
		});
	   }); 
    </script>
</body>
