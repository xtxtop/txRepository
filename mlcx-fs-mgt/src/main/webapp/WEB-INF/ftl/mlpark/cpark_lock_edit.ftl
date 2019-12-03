<meta charset="utf-8">

<div class="container-fluid backgroundColor">
	<form name="cParkLockEditForm" class="form-horizontal">
			<input type="hidden" name="parkLockNo" value="${cParkLock.parkLockNo}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑地锁</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label"><span>*</span>地锁编号：</label></td>
						<td><input class="form-control" name="parkingLockCode"  placeholder="请输入地锁编号" value="${cParkLock.parkingLockCode}" disabled="disabled"/> <span name="parkingLockCodeEdit"></span></td>
						<td><label class=" control-label"><span>*</span>地锁名称：</label></td>
						<td><input class="form-control" name="parkingLockName"  placeholder="请输入地锁名称" value="${cParkLock.parkingLockName}"/> <span name="parkingLockNameEdit"></span></td>
					</tr><tr>
						<td><label class=" control-label"><span>*</span>地锁序列号：</label></td>
						<td><input class="form-control" name="parkingLockSerialNumber"  placeholder="请输入地锁名称" value="${cParkLock.parkingLockSerialNumber}" /> <span name="parkingLockSerialNumberEdit"></span></td>
					   <td><label class="control-label key"><span>*</span>停车场：</label></td>
                        <td class="btn-btnA-con">
                            <div>
                                <input type="hidden" name="parkingNo" value="${cParkLock.parkingNo}" />
                                <input type="text" class="form-control val" name="parkingName" value="${parkingName}" readonly="readonly" placeholder="请选择停车场" />
                            </div>
                            <div class="btn-btnA">
                                <input type="button" class="btn btn-info" id="parkingNoEdit" value="选择">
                            </div>
                            <span name="parkingNoEdit"></span>
                        </td>
					</tr>
					<tr id="lockEdit">
					   <td><label class=" control-label"><span>*</span>停车场分布：</label></td>
					   <td>
					       <input type="radio" name="spaceType"  value="3" onclick="editclickSpaceType(this)" <#if cParkLock.spaceType==3>checked="checked"</#if>>楼层
					       <input type="radio" name="spaceType"  value="2" onclick="editclickSpaceType(this)" <#if cParkLock.spaceType==2>checked="checked"</#if>>地面
					       <input type="radio" name="spaceType"  value="1" onclick="editclickSpaceType(this)" <#if cParkLock.spaceType==1>checked="checked"</#if>>地下
					   </td>
					   <td><label class=" control-label"><span>*</span>层数：</label></td>
					   <td>
					       <select class="form-control"  name="pliesNumberNo" >
					           <#list cPliesNumber as p>
					               <option value="${p.parkingPliesNo}" <#if p.parkingPliesNo==cParkLock.pliesNumberNo>selected="selected"</#if>>${p.pliesNumber}</option>
					           </#list>
					       </select>
					   </td>
                    </tr>
					<tr>	
						<td><label class=" control-label"><span>*</span>车位号：</label></td>
						<td><input class="form-control" name="spaceNo"  placeholder="请输入车位号" value="${cParkLock.spaceNo}" />
						 <span name="spaceNoEdit"></span></td>
					       <td><label class=" control-label"><span>*</span>地锁使用状态：</label></td>
                        <td>
                                <select class="form-control"  name="lockStatus">
                                    <option value="1" <#if cParkLock.lockStatus=1>selected="selected"</#if>>空闲</option>
                                    <option value="2" <#if cParkLock.lockStatus=2>selected="selected"</#if>>预约</option>
                                    <option value="0" <#if cParkLock.lockStatus=0>selected="selected"</#if>>占用</option>
                             
					
					</tr><tr>
							<td><label class=" control-label"><span>*</span>地锁类型：</label></td>
						<td>
			                    <select class="form-control"  name="parkingLockType">
				                    <option value="0" <#if cParkLock.parkingLockType=0>selected="selected"</#if> >网关版</option>
									<option value="1" <#if cParkLock.parkingLockType=1>selected="selected"</#if> >UDP版</option>
			                    </select>
								<span name="parkingLockTypeEdit"></span>
						</td>
								<td><label class=" control-label"><span>*</span>升降状态：</label></td>
						<td>
							 <select class="form-control"  name="parkingLockStatus">
		                    	<option value="0" <#if cParkLock.parkingLockStatus=0>selected="selected"</#if> >升</option>
								<option value="1" <#if cParkLock.parkingLockStatus=1>selected="selected"</#if> >降</option>
		                    </select>
								<span name="parkingLockStatusEdit"></span>
						</td>
						</tr><tr>
								<td><label class=" control-label"><span>*</span>地锁状态：</label></td>
						<td>
							 <select class="form-control"  name="activeCondition" placeholder="">
		                    	<option value="0" <#if cParkLock.activeCondition=0>selected="selected"</#if> >可用</option>
								<option value="1" <#if cParkLock.activeCondition=1>selected="selected"</#if> >不可用</option>
		                    </select>
								<span name="activeConditionEdit"></span>
						</td>
						<td><label class=" control-label"><span>*</span>在线状态：</label></td>
						<td>
							  <select class="form-control"  name="onlineStatus" placeholder="">
		                    	<option value="0" <#if cParkLock.onlineStatus=0>selected="selected"</#if> >在线</option>
								<option value="1" <#if cParkLock.onlineStatus=1>selected="selected"</#if> >离线</option>
		                    </select>
								<span name="onlineStatusEdit"></span>
						</td>
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="EditcParkLock" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closecParkLockEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
      
    </tr>
  </tfoot>
  </table>
		</div>
	</form>
</div>
<!-- 停车场 -->
	<div class="modal" id="parkingModelEdit">
		<div class="modal-dialog" style="width:750px;">
			<div class="modal-content">
				<form name="parkingSerachFormEdit">
					<div class="with-border">
						<div class="title-new-details">选择停车场</div>
						<div class="pull-down-menu-cParkLock">
							<div class="parkNo-frombg">
								<span>停车场名称</span>
								<input class="form-control-input" name="parkingName" id="parkingName" value="" placeholder="请输入停车场名称">
							</div>
						</div>
					</div>
					<!-- /.box-header -->

					<div class="box-body box-body-change-pEditing">
					</div>
					<!-- /.box-body -->
					<!--修改-->
					<div class="box-bullet">
						<div class="box-footer">
							<!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
							<button type="button" class="btn-new-type" id="parkingSearch">确定</button>
							<button type="reset" class="btn-new-type">清空</button>
						</div>
						<!-- /.box-footer -->
					</div>
				</form>
				<div class="border-bullet">
					<!--定义操作列按钮模板-->
					<script id="parkingBtnTplEdit" type="text/x-handlebars-template">
						{{#each func}}
						<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
					</script>
					<div class="box">

						<div class="box-header">
							<!-- <h3 class="box-title">数据列</h3> -->
						</div>
						<!-- /.box-header -->
						<div class="box-body box-body-change-pEditing">
							<table id="parkingListEdit" class="table table-bordered table-hover" width="100%">
							</table>
						</div>
						<!-- /.box-body -->
						<div class="cParkLockRedPacketEditParkTool-bullet" id="parkingToolssssEdit">
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
	$(function(){
	  require.config({paths: {"cParkLockEdit":"res/js/mlpark/cpark_lock_edit"}});
		require(["cParkLockEdit"], function (cParkLockEdit){
			cParkLockEdit.init();
		});  
	});
	var config=new Object();
		config.uploadId="cParkLockFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）cParkLock_photo （车辆照片）cParkLock_doc  （车辆证件）
		config.storePath = "cParkLock_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "cParkLockPhotoForm";
		//文件回显inputName
		config.inputName = "cParkLockPhotoUrl1";
		//错误提示span标签name
		config.spanName = "cParkLockErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});

</script>