<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="editWorkerForm">
		 <input type="hidden" name="workerNo" value="${worker.workerNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">调度员修改</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							 <select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option value="${citie.dataDictItemId}" <#if worker.cityId==citie.dataDictItemId>selected="selected"</#if> >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;片区：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="hidden"  name="regionId" value="${worker.regionId}" />
								<input type="text" class="form-control val"  name="regionName"   value="${worker.regionName}" readonly="readonly"/>
								<input type="button" class="btn btn-info btn-btnA"  id="regionIdEdd" value="选择">
						    <span name="regionIdEd"></span>
						</td>
					</tr>
					<tr>
						

						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;工号：</label>
						</td>
						<td>
							 <input class="form-control val" name="empNo" value="${worker.empNo}"/>
							<span name="empNoEdit"></span>
						</td>
                        <td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;姓名：</label>
						</td>
						<td>
							 <input class="form-control val" name="workerName" value="${worker.workerName}"/>
							 <span name="workerNameEdit"></span>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;手机：</label>
						</td>
						<td>
							<input class="form-control val" name="mobilePhone" value="${worker.mobilePhone}"/>
							<span name="mobilePhoneEdit"></span>
						</td>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;密码：</label>
						</td>
						<td>
							<input class="form-control val" name="password" value=""/>
							<span name="passwordEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;调度端App在线状态：</label>
						</td>
						<td>
							<select name="appIsOnline" class="form-control val">
									<option value="0" <#if worker.appIsOnline==0>selected="selected"</#if>>离线</option>
									<option value="1" <#if worker.appIsOnline==1>selected="selected"</#if>>在线</option>
							</select>
						</td>
						<td></td>
						<td></td>
					</tr>
					

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editWorker" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeWorkerEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!--调度员片区列表弹出-->
<div class="modal" id="regionEddModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择终端</div>
				</div>
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="regionBtnTplEdd" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body box-body-change-padding">
			         <table id="regionListEdd" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			    <div class="carRedPacketAddParkTool-bullet" id="regionToolssssEdd">
					</div> 
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"workerEdit":"res/js/worker/worker_edit"}});
		require(["workerEdit"],function (workerEdit){
			workerEdit.init();
		});  
	});    
</script>