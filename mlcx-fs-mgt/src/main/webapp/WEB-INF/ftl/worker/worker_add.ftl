<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="addWorkerForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增调度员</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								<option value="">
									请选择
								</option>
								<#list cities as citie>
									<option value="${citie.dataDictItemId}">
										${citie.itemValue}
									</option>
								</#list>
							</select>
							<span name="cityIdDd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>片区：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="hidden" name="regionId" />
							<input type="text" class="form-control val" name="regionName" readonly="readonly" placeholder="请选择片区"/>
							<input type="button" class="btn btn-info btn-btnA" id="regionIdAdd" value="选择">
							<span name="regionIdDd"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>工号：</label>
						</td>
						<td>
							<input class="form-control val" name="empNo" placeholder="请输入工号"/>
							<span name="empNo"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>姓名：</label>
						</td>
						<td>
							<input class="form-control val" name="workerName" placeholder="请输入姓名"/>
							<span name="workerName"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>手机：</label>
						</td>
						<td>
							<input class="form-control val" name="mobilePhone" placeholder="请输入手机号码"/>
							<span name="mobilePhone"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>密码：</label>
						</td>
						<td>
							<input class="form-control val" name="password" placeholder="请输入密码"/>
							<span name="password"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>调度端App在线状态：</label>
						</td>
						<td>
							<select name="appIsOnline" class="form-control val">
								<option value="0">离线</option>
								<option value="1">在线</option>
							</select>
						</td>
						<td colspan="2"></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addWorker" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeWorker" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!--调度员片区列表弹出-->
	<div class="modal" id="regionAddModal">
		<div class="modal-dialog" style="width:750px;">
			<div class="modal-content">
				<div class="with-border">
					<div class="title-new-details">选择片区</div>
				</div>
				<div class="">
					<!--定义操作列按钮模板-->
					<script id="regionBtnTplAdd" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
					<div class="box">
						<div class="box-header">
							<!-- <h3 class="box-title">数据列</h3> -->
						</div>
						<!-- /.box-header -->
						<div class="box-body box-body-change-padding">
							<table id="regionListAdd" class="table table-bordered table-striped table-hover" width="100%">
							</table>
						</div>
						<!-- /.box-body -->
						<div class="carRedPacketAddParkTool-bullet" id="regionToolssssAdd">
					</div>
					</div>
					<!-- /.box -->
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>

	<script type="text/javascript">
		$(function() {
			require.config({
				paths: {
					"workerAdd": "res/js/worker/worker_add"
				}
			});
			require(["workerAdd"], function(workerAdd) {
				workerAdd.init();
			});
		});
	</script>