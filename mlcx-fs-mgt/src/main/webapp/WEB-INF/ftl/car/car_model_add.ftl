<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>车型新增</h4></label>
						</div>
					</div>
				</div>
            <form name="carModelAddForm">
                <div class="row hzlist">
                <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>品牌：</label>
                        <div class="col-sm-7">
                            <select name="carBrandId" class="form-control">
                                <#list lb as l>
                                    <option value="${l.carBrandId}" >
                                        ${l.carBrandName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-sm-7"><span id="carBrandIdAdd"></span></div>
                    </div>
                
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>型号：</label>
                        <div class="col-sm-7">
                            <input class="form-control" name="carModelName" />
                        </div>
                        <div class="col-sm-7"><span id="carModelNameAdd"></span></div>
                    </div>
                    
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>类别：</label>
                        <div class="col-sm-7">
                            <select name="carType" class="form-control">
                                    <option  value="1" >经济型</option>
                                    <option  value="2" >商务型</option>
                                    <option  value="3" >豪华型</option>
                                    <option  value="4" >6至15座商务</option>
                                    <option  value="5" >SUV</option>
                                
                            </select>
                        </div>
                    </div>

                </div>
                <div class="row hzlist">
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>座位数：</label>
                        <div class="col-sm-7">
                            <input class="form-control" name="seatNumber" />
                        </div>
                        <div class="col-sm-7"><span id="seatNumberAdd"></span></div>
                    </div>
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>档位：</label>
                        <div class="col-sm-7">
                            <select name="stall" class="form-control">
                                    <option  value="1" selected="selected">手动 </option>
                                    <option  value="2" >自动 </option>
                            </select>
                        </div>
                        <div class="col-sm-7"><span id="ModelLocationAdd"></span></div>
                    </div>
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label"><span>*</span>箱型：</label>
                        <div class="col-sm-7">
                            <input class="form-control" name="boxType" />
                        </div>
                        <div class="col-sm-7"><span id="boxTypeAdd"></span></div>
                    </div>

                </div>
				<div class="row hzlist">
                        <div class="form-group col-md-4">
                            <label class="col-sm-4 control-label"><span>*</span>排量：</label>
                            <div class="col-sm-7">
                                 <input class="form-control" name="displacement" />
                            </div>
                            <div class="col-sm-7"><span id="displacementAdd"></span></div>
                        </div>
                        <!-- <div class="form-group col-md-4">
                            <label class="col-sm-4 control-label"><span>*</span>保证金额：</label>
                            <div class="col-sm-7">
                                <input class="form-control" name="bond" />
                            </div>
                            <div class="col-sm-7"><span id="bondAdd"></span></div>
                        </div> -->

        		</div><!-- /.row -->
        		<div class="row hzlist">
            <div class="form-group col-md-10 carPhotoUrl1">
                <label class="col-sm-3 col-xs-3 control-label">车辆照片：</label>
                <input name="carPhotoUrl1" type="hidden" value=""/>
                <div class="col-sm-8">
                    <img src="" width="320" height="200" name="carPicUrlImg" />
                    <button type="button" id="addCarPhotoUs" class="btn btn-info btn-default">上传图片</button>
                <div style="margin-top:7px;"><span name="carPhotoUrl1"></span></div>
                </div>
            </div>
            
        </div>
            </form>
            <div class="form-group">
                <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeAddCarModel" class="btn btn-deModel pull-right " >
                           <i class="glyphicon glyphicon-remove"></i>关闭
                    </button>
                    <button type="button" id="saveCarModel" class="btn btn-deModel pull-right " >
                           <i class="glyphicon glyphicon-check"></i>保存
                    </button>
                </div>
            </div>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="carAddModalUs">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="carphotoFormUs">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="carPhotoUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="carFineUploaderUs"><span name="parkErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addCarPhotoBtnUs" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carModelAdd":"res/js/car/car_model_add"}});
		require(["carModelAdd"], function (carModelAdd){
			carModelAdd.init();
		});  
		
		var config=new Object();
		config.uploadId="carFineUploaderUs";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "carphotoFormUs";
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
