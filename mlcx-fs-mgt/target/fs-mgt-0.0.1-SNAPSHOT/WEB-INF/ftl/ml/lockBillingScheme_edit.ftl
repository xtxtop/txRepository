<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="lockBillingSchemeEditForm">
	<input type="hidden" name="lockSchemeNo" value="${cp.lockSchemeNo }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑地锁计费方案</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>地锁计费方案名称：</label>
						</td>
						<td>
							<input class="form-control val" name="lockSchemeName" value="${cp.lockSchemeName }" placeholder="请输入地锁计费方案名称"/>
						    <span id="lockSchemeNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>免费时长：</label>
						</td>
						<td>
							<input class="form-control val" name="freeTime" value="${cp.freeTime }" placeholder="请输入免费时长(分钟)"/>
							<span id="freeTimeEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>单位时间：</label>
						</td>
						<td>
							<input class="form-control val" name="unitTime" value="${cp.unitTime }" placeholder="请输入单位时间(分钟)"/>
							<span id="unitTimeEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>超时金额：</label>
						</td>
						<td>
							<input class="form-control val" name="overtimePrice" value="${cp.overtimePrice }" placeholder="超时金额(元/单位时间)"/>
							<span id="overtimePriceEdit"></span>
						</td>
					</tr>
					<tr>
					·	<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<div class="col-sm-7 val">
								<input type="radio" name="status"  value="1" <#if cp.status==1>checked="checked"</#if> >启用</input>
	                            <input type="radio" name="status"  value="0" <#if cp.status==0>checked="checked"</#if> >停用</input>
								<span id="statusEdit"></span>
							</div>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="savelockBillingSchemeEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closelockBillingSchemeEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- /.modal -->


<div class="modal fade" id="checkUserModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<!--定义操作列按钮模板-->
			<script id="lockBillingSchemeEditBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
			<div class="box">
				<div class="box-header">
					<!-- <h3 class="box-title">数据列</h3> -->
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="lockBillingSchemeEditList"
						class="table table-bordered table-striped table-hover"
						width="100%">
					</table>
				</div>
				<!-- /.box-body -->
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
			paths : {
				"lockBillingSchemeEdit" : "res/js/ml/lockBillingScheme_edit"
			}
		});
		require([ "lockBillingSchemeEdit" ], function(lockBillingSchemeEdit) {
			lockBillingSchemeEdit.init();
		});
	});
	$(function() {
		$(".datepicker").datepicker({
			language : "zh-CN",
			autoclose : true,//选中之后自动隐藏日期选择框
			clearBtn : true,//清除按钮
			todayBtn : "linked",//今日按钮
			format : "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>