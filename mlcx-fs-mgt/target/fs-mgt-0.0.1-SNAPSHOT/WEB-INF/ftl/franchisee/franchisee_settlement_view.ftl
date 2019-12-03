<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="FranchiseeSettleViewForm">
		<input type="hidden" id="franchiseeNo" name="franchiseeNo" value="${franchiseeSettle.franchiseeNo}">
						<input type="hidden" id="orderNo" name="orderNo" value="${franchiseeSettle.orderNo}">
						<input type="hidden" id="settleDay" name="settleDay" value="${franchiseeSettle.settleDay}">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">加盟商结算单详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>加盟商名称：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeSettle.franchiseeName}</label>
						</td>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->加盟商编号：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeSettle.franchiseeNo}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->结算周期：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeSettle.settleDay}</label>
						</td>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->总订单数：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeSettle.orderCount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->总订单金额：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeSettle.orderAmount}</label>
						</td>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->车辆分润订单数：</label>
						</td>
						<td>								
							 <label class="control-label val">${franchiseeSettle.carshareOrderCount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->车辆分润订单总金额：</label>
						</td>
						<td>
                          <label class="control-label val">${franchiseeSettle.carshareOrderAmount}</label>
						</td>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->车辆分润总金额：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeSettle.carShareAmount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->场站分润订单数：</label>
						</td>
						<td>
							<label class="control-label val">${franchiseeSettle.parkshareOrderCount}</label>
						</td>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->场站分润订单总金额：</label>
						</td>
						<td>
                           <label class="control-label val">${franchiseeSettle.parkshareOrderAmount}</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><#-- <span>*</span> -->场站分润总金额：</label>
						</td>
						<td>
							<label class="control-label val">
								<label class="control-label val">${franchiseeSettle.parkShareAmount}</label>
							</label>
						</td>
						<td></td>
						<td></td>
					</tr>
					
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
     <td colspan="2"><button type="button" id="queryThisFranchiseeRecord" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                查看本期收益明细
            </button></td>
      <td colspan="2"><button type="button" id="closeView" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
      
    </tr>
  </tfoot>
  </table>
		</div>

	</form>
</div>

<!-- 本期收益列表 -->
<div class="modal" id="earningsModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">查看本期收益明细</div>
				</div>
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="earningsModalTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body box-body-change-padding">
			         <table id="earningsList" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="franchiseeSettle">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>


<script type="text/javascript">
$(function(){
	  require.config({paths: {"franchiseeSettleView":"res/js/franchisee/franchisee_settlement_view"}});
		require(["franchiseeSettleView"], function (franchiseeSettleView){
			franchiseeSettleView.init();
		});  
	});  

</script>