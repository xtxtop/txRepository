<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>车型编辑</h4></label>
						</div>
					</div>
				</div>
            <form name="carModelEditForm">
            <input type="hidden" value="${model.carModelName}" name="carModelNameGd" />
            <input type="hidden" value="${model.carBrandId}" name="carBrandIdGd" />
            <input value="${model.carModelId}" type="hidden" name="carModelId">
                <div class="row hzlist">
                <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;品牌：</label>
                        <div class="col-sm-7">
                            <select name="carBrandId" class="form-control">
                                <#list lb as l>
                                    <option <#if l.carBrandId==model.carBrandId>selected </#if>  value="${l.carBrandId}"   >
                                        ${l.carBrandName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-sm-7"><span id="carBrandIdEd"></span></div>
                    </div>
                
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;型号：</label>
                        <div class="col-sm-7">
                            <input class="form-control" value="${model.carModelName}" name="carModelName" />
                        </div>
                        <div class="col-sm-7"><span id="carModelNameEd"></span></div>
                    </div>
                    
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;类别：</label>
                        <div class="col-sm-7">
                            <select name="carType" class="form-control">
                                    <option <#if model.carType==1>selected </#if>  value="1" >经济型</option>
                                    <option <#if model.carType==2>selected </#if> value="2" >商务型</option>
                                    <option <#if model.carType==3>selected </#if> value="3" >豪华型</option>
                                    <option <#if model.carType==4>selected </#if> value="4" >6至15座商务</option>
                                    <option <#if model.carType==5>selected </#if> value="5" >SUV</option>
                            </select>
                        </div>
                    </div>

                </div>
                <div class="row hzlist">
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;座位数：</label>
                        <div class="col-sm-7">
                            <input class="form-control" value="${model.seatNumber}" name="seatNumber" />
                        </div>
                        <div class="col-sm-7"><span id="seatNumberEd"></span></div>
                    </div>
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;档位：</label>
                        <div class="col-sm-7">
                            <select name="stall" class="form-control">
                                    <option <#if model.stall==1>selected </#if>  value="1" >手动 </option>
                                    <option <#if model.stall==2>selected </#if> value="2" >自动 </option>
                            </select>
                        </div>
                        <div class="col-sm-7"><span id="ModelLocationEd"></span></div>
                    </div>
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;箱型：</label>
                        <div class="col-sm-7">
                            <input class="form-control" value="${model.boxType}" name="boxType" />
                        </div>
                        <div class="col-sm-7"><span id="boxTypeEd"></span></div>
                    </div>

                </div>
				<div class="row hzlist">
                        <div class="form-group col-md-4">
                            <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;排量：</label>
                            <div class="col-sm-7">
                                 <input class="form-control" value="${model.displacement}" name="displacement" />
                            </div>
                            <div class="col-sm-7"><span id="displacementEd"></span></div>
                        </div>
                       <!--  <div class="form-group col-md-4">
                            <label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;保证金额：</label>
                            <div class="col-sm-7">
                                <input class="form-control" value="" name="bond" />
                            </div>
                            <div class="col-sm-7"><span id="bondEd"></span></div>
                        </div> -->

        		</div>
        		<div class="row hzlist">
                       <div class="form-group col-md-12">
                			<label class="col-sm-1 control-label"><span></span>&nbsp;&nbsp;车辆照片：</label>
                			<div class="col-sm-9">
                				<img src="${imagePath!''}/${model.carPhotoUrl1}" width="320" height="200" name="carPicUrlImg"/>
                				<button type="button" id="editCarPhotoUs" class="btn btn-info btn-default">上传图片</button>
                			</div>
                			<input name="carPhotoUrl1" type="hidden" value="${model.carPhotoUrl1}"/>
                		</div>
        		</div>
        		<!-- /.row -->
            </form>
            <div class="form-group">
                <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeEditCarModel" class="btn btn-deModel pull-right btnDefault" >
                           <i class="glyphicon glyphicon-remove"></i>关闭
                    </button>
                    <button type="button" id="saveCarModelEdit" class="btn btn-deModel pull-right btnColorA" >
                           <i class="glyphicon glyphicon-check"></i>保存
                    </button>
                </div>
            </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="careditModalUs">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="carphotoFormEdUs">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="carPhotoUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="carFineUploaderAsUs"><span name="parkErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editCarPhotoBtnUs" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carModelEdit":"res/js/car/car_model_edit"}});
		require(["carModelEdit"], function (carModelEdit){
			carModelEdit.init();
		});  
		
		var config=new Object();
		config.uploadId="carFineUploaderAsUs";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "carphotoFormEdUs";
		//文件回显inputName
		config.inputName = "carPhotoUrl1";
		//错误提示span标签name
		config.spanName = "parkErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});	
	});
	   
</script>
