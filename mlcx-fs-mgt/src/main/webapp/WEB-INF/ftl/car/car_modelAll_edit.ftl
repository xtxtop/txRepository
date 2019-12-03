<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>车型编辑</h4></label>
						</div>
					</div>
				</div>
           <div class="row hzlist col-md-12 pull-right" style="margin-bottom:50px !important;">
					<div class="col-md-8">
	 				<form name="carModelAllEditForm" class="form-horizontal">
	 					<input type="hidden" name="carSeriesId" value="${carSeries.carSeriesId}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label"><span>*</span>&nbsp;&nbsp;车系名称：</label>
							<div class="col-sm-4">
								<input class="form-control" name="carSeriesName" value="${carSeries.carSeriesName}"/><br/> <span name="carSeriesNameEdit"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><span>*</span>&nbsp;&nbsp;所属品牌：</label>
							<div class="col-sm-4">
								<select class="form-control" name="carBrandId">
									<option value="">全部</option>
									<#if carBrandList?exists>
                                        <#list carBrandList as carBrand>
                                            <option value="${carBrand.carBrandId}" <#if carSeries.carBrandId==carBrand.carBrandId>selected=selected</#if>>${carBrand.carBrandName}</option>
                                        </#list>
                                    </#if>
								</select></br>
								<span name="carBrandIdEdit"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><span>*</span>&nbsp;&nbsp;车系图片：</label>
							<div class="col-sm-4">
								 <input type="hidden" name="carSeriresPic" value="${carSeries.carSeriresPic}"/><br/> 
								 <img src="${imagePath!''}/${carSeries.carSeriresPic}" width="320" height="200" name="carSeriesPicUrlImg" />
                    			 <button type="button" id="editCarModelAllPhoto" class="btn btn-info btn-default col-sm-3">上传图片</button>
							</div>
							<div><span name="carSeriesPicEdit"></span></div>
						</div>
                		</form>	
                		<div class="form-group">
                			<div class="col-sm-6" style="margin-bottom:20px;">
                				<button id="closeCarModelAllEdit" class="btn btn-default pull-right navbar-btn btn-flat btncolorb">
                				        <i class="glyphicon glyphicon-remove"></i>关闭
                				</button>
                				<button id="editCarModelAll" class="btn btn-default pull-right navbar-btn btn-flat btncolora">
                				        <i class="glyphicon glyphicon-check"></i>保存
                				</button>
                			</div>
                		</div>	
					</div>	
        		</div><!-- /.row -->
</div>
<!-- 弹出框-->
    <div class="modal fade" id="carModelAllEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="carModelAllPhotoEditForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="carSeriesPhotoUrl1Edit" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val1">车系图片：</label>
                           <div class="col-md-8">
                                <div id="carModelAllFineEditUploader"><span name="carSeriesEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editCarModelAllPhotoBtn" value="确定">
					</div>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carModelAllEdit":"res/js/car/car_modelAll_edit"}});
		require(["carModelAllEdit"], function (carModelAllEdit){
			carModelAllEdit.init();
		});  
	});
	var config=new Object();
		config.uploadId="carModelAllFineEditUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "carModelAllPhotoEditForm";
		//文件回显inputName
		config.inputName = "carSeriesPhotoUrl1Edit";
		//错误提示span标签name
		config.spanName = "carSeriesEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});

</script>
