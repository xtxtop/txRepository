<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="redeemCodeAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增兑换码</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control val" name="redName" id="redName" placeholder="请输入名称"/>
							<span name="redNameAdd"></span>
						</td>
						<td hidden="hidden">
							<label class=" control-label key"><span>*</span>兑换次数：</label>
						</td>
						<td hidden="hidden">
							<input class="form-control val" name="availableTimes" id="availableTimes" placeholder="请输入兑换次数"/>
							<span name="availableTimesAdd"></span>
						</td>
						<td>
							<label class=" control-label reason key"><span>*</span>优惠方案：</label>
						</td>
						<td>
							<button type="button" class="btn btn-default" id="checkAddPlanBtnOfRedeemCodeAdd">方案列表</button>
							<input type="hidden" name="planNo" id="planNo" />
							<input type="hidden" name="planName" id="planName" />
							<input type="hidden" name="tempinput" />
							<span name="planNoAdd"></span>
						</td>
					</tr>
					<tr>
						
						<td class="form-group availableTime-2">
							<label class=" control-label key"><span>*</span>有效日期：</label>
						</td>
						<td class="form-group availableTime-2">
							<input id="datetimepicker1" class="form-control val" name="availableTime1" placeholder="请选择开始时间"/> 至
							<input id="datetimepicker2" class="form-control val" name="availableTime2" placeholder="请选择结束时间"/>
							<div><span name="availableTime1Add"></span></div>
							<div><span name="availableTime2Add"></span></div>
						</td>
                        <td>
							<label class=" control-label reason key">备注：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="6" name="remark" placeholder="请填写备注"></textarea>
							<input type="hidden" name="couponPlanString" value="" />
							<span name="remarkAdd"></span>
						</td>
					</tr>
					<tr class="planNosModal"></tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addRedeemCode" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddRedeemCode" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<div class="modal fade" id="couponPlanModalAdd" tabindex="-1" role="dialog">
	<div class="modal-dialog" style="width:750px;">
		<div class="modal-content">
			<div class="with-border">
					<div class="title-new-details">选择方案列表</div>
				</div>
			<!--定义操作列按钮模板-->
			<script id="couponPlanBtnTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body box-body-change-padding">
					<table id="couPonPlanListsOfRedeemCodeaAdd" class="table table-bordered table-striped table-hover" width="100%">
					</table>
				</div>
				<!-- /.box-body -->
				 <div class="carRedPacketAddParkTool-bullet" id="couponPlanToolsss">
					</div>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"redeemCodeAdd": "res/js/marketing/redeemCode_add"
			}
		});
		require(["redeemCodeAdd"], function(redeemCodeAdd) {
			redeemCodeAdd.init();
		});

		$("#datetimepicker1").datetimepicker({
			language: "zh-CN",
			clearBtn: true, //清除按钮
			autoclose: true, //选中之后自动隐藏日期选择框
			minView: "month",
			todayBtn: true,
			minuteStep: 5,
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});

		$("#datetimepicker2").datetimepicker({
			language: "zh-CN",
			clearBtn: true, //清除按钮
			autoclose: true, //选中之后自动隐藏日期选择框
			minView: "month",
			todayBtn: true,
			minuteStep: 5,
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});

	});
</script>