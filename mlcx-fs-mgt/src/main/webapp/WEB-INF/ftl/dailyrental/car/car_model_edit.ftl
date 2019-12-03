<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carModelEditForm">
		<input type="hidden" name="carModelId" value="${model.carModelId}">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车型编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>车型名称：</label>
						</td>
						<td>
							<input class="form-control" value="${model.carModelName}" name="carModelName" />
							<span name="carModelNameEdit"></span>
						</td>
					<td>
						<label class=" control-label"><span>*</span>车辆品牌：</label>
					</td>
					<td>
						<select name="carBrandId" class="form-control" onchange="selectBrandValueEdit('carModelBrandIdEdit')" id="carModelBrandIdEdit">
                            	<option value="">全部</option>
                                <#list lb as l>
                                    <option <#if l.carBrandId==model.carBrandId>selected=selected</#if>  value="${l.carBrandId}">
                                        ${l.carBrandName}
                                    </option>
                                </#list>
                            </select>
                        <span name="carBrandIdEdit"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>所属车系：</label>
                        <input type="hidden" name="carSeriesName" readonly/>
						</td>
						<td>
							<select class="form-control" name="carSeriesId" onchange="selectSeriesValueEdit('carModelSeriesIdEdit')" id="carModelSeriesIdEdit">
								<option value="">全部</option>
								<option value="${model.carSeriesId}" selected=selected>${model.carSeriesName}</option>
							</select>
							<span name="carSeriesIdEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>年代名称：</label>
							 <input type="hidden" name="carPeriodName" readonly/>
						</td>
						<td>
							<select class="form-control" name="carPeriodId">
								<option value="">全部</option>
								<option value="${model.carPeriodId}" selected=selected>${model.carPeriodName}</option>
							</select>
							<span name="carPeriodIdEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>配置款型：</label>
						</td>
						<td>
							<input class="form-control" name="configModel" value="${model.configModel}"/>
							<span name="configModelEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>适用类型：</label>
						</td>
						<td>
							<select name="carType" class="form-control">
                            	<option value="">全部</option>
                                <option <#if model.carType==1>selected </#if>  value="1" >经济型</option>
                                <option <#if model.carType==2>selected </#if> value="2" >商务型</option>
                                <option <#if model.carType==3>selected </#if> value="3" >豪华型</option>
                                <option <#if model.carType==4>selected </#if> value="4" >6至15座商务</option>
                                <option <#if model.carType==5>selected </#if> value="5" >SUV</option>
                            </select>
                            <span name="carTypeEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>车身：</label>
						</td>
						<td>
							<select class="form-control" name="boxType">
                            	<option  value="" >全部 </option>
                            	<option value="1" <#if model.boxType==1>selected </#if> >两厢</option>
                            	<option value="2" <#if model.boxType==2>selected </#if> >三厢</option>
                            </select>
                            <span name="boxTypeEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>座位数：</label>
						</td>
						<td>
							<select class="form-control" name="seatNumber">
                           		<option  value="" >全部 </option>
                            	<option value="1" <#if model.seatNumber==1>selected </#if> >2座</option>
                            	<option value="2" <#if model.seatNumber==2>selected </#if> >4座</option>
                            	<option value="3" <#if model.seatNumber==3>selected </#if> >5座</option>
                            	<option value="4" <#if model.seatNumber==4>selected </#if> >7座</option>
                            </select>
                            <span name="seatNumberEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>变速箱：</label>
						</td>
						<td>
							<select name="gearBox" class="form-control">
                            	<option value="">全部</option>
                                <option <#if model.gearBox==1>selected </#if> value="1" >手动 </option>
                                <option <#if model.gearBox==2>selected </#if> value="2" >自动 </option>
                                <option <#if model.gearBox==3>selected </#if> value="3" >手自一体 </option>
                            </select>
                            <span name="gearBoxEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>排量：</label>
						</td>
						<td>
							<input class="form-control" value="${model.displacement}" name="displacement" />
							<span name="displacementEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							 <label class=" control-label"><span>*</span>吨位：</label>
						</td>
						<td>
							<input class="form-control" name="tons" value="${model.tons}"/>
                                 <label style="position:absolute;right:25px;top:0;">吨</label>
                            <span name="tonsEdit"></span>
						</td>
						 <#if model.carModelInfo??>
						<td>
							<label class=" control-label"><span>*</span>款型描述：</label>
						</td>
						<td>
							<input class="form-control" name="carModelInfo" value="${model.carModelInfo}"/>
							<span name="carModelInfoEdit"></span>
						</td>
						</#if>
					</tr>
					<tr class="add-car-last-tr">
						<td>
							<input name="carModelUrl" type="hidden" value="${model.carModelUrl}"/>
                			<label class=" control-label"><span>*</span>车辆照片：</label>
						</td>
						<td class="odd-td">
			    <div class="img-td-upload" style="background-image: url(${imagePath!''}/${model.carModelUrl});" id="carPicUrlImg">
					<div id="editCarModelPhoto" class="add-img-position">
						<h3 class="icon">+</h3>
						<h3 class="text">添加图片</h3>
					</div>
				</div>
				<span name="carModelUrlEdit"></span>
				</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCarModelEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditCarModel" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="carModeleditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="carModelPhotoFormEdit">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="carPhotoUrl" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="carModelFineEditUploader"><span name="carModelEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editCarPhotoBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carModelEdit":"res/js/dailyrental/car/car_model_edit"}});
		require(["carModelEdit"], function (carModelEdit){
			carModelEdit.init();
		});  
		
		var config=new Object();
		config.uploadId="carModelFineEditUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "carModelPhotoFormEdit";
		//文件回显inputName
		config.inputName = "carPhotoUrl";
		//错误提示span标签name
		config.spanName = "carModelEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});	
	});
	   
</script>