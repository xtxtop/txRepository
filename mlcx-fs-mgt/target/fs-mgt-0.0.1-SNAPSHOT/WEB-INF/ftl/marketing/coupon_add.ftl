<meta charset="utf-8">
<style>
	.memo {
		line-height: 30px;
		resize: none;
	}
</style>
<div class="container-fluid backgroundColor">
	<form name="couponAddFrom">
		<input type="hidden" name="memberNos" value="${memberNos}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">发放优惠券</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>方案标题：</label>
						</td>
						<td>
							<input class="form-control val" name="title" id="title" readonly placeholder="请输入方案标题" />
							<input type="hidden" name="planNo" id="planNo" />
							<button type="button" class="btn btn-default" id="checkAddPlanBtn">方案列表</button>
							<span name="planNoAdd"></span>
						</td>
						<td>
							<label class=" control-label key">优惠券类型：</label>
						</td>
						<td>
							<input class="form-control val" name="couponTypeName" readonly placeholder="请输入优惠卷类型" />
							<input type="hidden" name="couponType" />
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">优惠类型：</label>
						</td>
						<td>
							<input class="form-control val" name="couponMethodName" readonly placeholder="请输入优惠类型" />
							<input type="hidden" name="couponMethod" />
					    </td>
					    <td>
							<label class="couponMethod-1 control-label key">折扣率：</label>
						</td>
						<td>
							<input class="form-control couponMethod-1 val" name="discount" readonly placeholder="请输入折扣率" />
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">有效天数：</label>
						</td>
						<td>
							<input class="form-control val" name="availableDays" readonly placeholder="请输入有效天数" />
						</td>
						<td>
							<label class=" control-label key">发放数量：</label>
						</td>
						<td>
							<input class="form-control val" name="sendQuantity" placeholder="请输入发放数量" onkeyup="this.value=this.value.replace(/\D/g,'')" value="1"/>
						</td>
					</tr>
					<tr  class="form-group  couponMethod-2" hidden="hidden">
					<td>
					<label class=" control-label key">直减金额：</label>
					</td>
					<td>
					<input class="form-control val" name="discountAmount" readonly placeholder="请输入直减金额" />
					</td>
					<td colspan="2"></td>
					</tr>
					
					<tr  class="form-group   availableTime-2" hidden="hidden">
					<td>
					<label class=" control-label key">有效日期：</label>
					</td>
					<td>
					<input class="datetimepicker form-control val " name="vailableTime1" data-format="dd-MM-yyyy hh:mm:ss" disabled /> 至
							<input class="datetimepicker form-control val " name="vailableTime2" data-format="dd-MM-yyyy hh:mm:ss" disabled />
					</td>
					<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addCoupon" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCoupon" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<div class="modal fade" id="couponModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">方案列表</div>
				</div>
		       <!--定义操作列按钮模板-->
		       <script id="couponBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body box-body-change-padding">
			         <table id="couponLists" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="couponToolss">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
    
    
    
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"couponAdd":"res/js/marketing/coupon_add"}});
		require(["couponAdd"], function (couponAdd){
			couponAdd.init();
		});  
		
		$(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            minView: "month",
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
		
    });
</script>