<meta charset="utf-8">
<style>
 .img-td-upload .add-img-position {
    padding-top: 0px;
}
</style>
<div class="container-fluid backgroundColor">
	<form name="carBrandEditForm">
		<input type="hidden" name="carBrandId" value="${carBrand.carBrandId}">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">品牌编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>品牌名称：</label>
						</td>
						<td> 
							<input class="form-control" name="carBrandName"  value="${carBrand.carBrandName}"/>
						    <span name="carBrandNameEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>logo图片：</label>
						</td>
						<td class="odd-td">
						<input type="hidden" name="logoPic" value="${carBrand.logoPic}"/> 
						<div class="add-car-img" style="background-image: url(${imagePath!''}/${carBrand.logoPic});" id="carBrandPicUrlImgEdit">
						    <div id="editCarBrandPhoto" class="add-img-position">
						    	<span class="icon">+</span>
						    </div>
						</div>
							<span name="logoPicEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span></span>名称简写：</label>
						</td>
						<td>
							<input class="form-control" name="brandShortName" value="${carBrand.brandShortName}"/>
							<span name="brandShortNameEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span></span>英文名称：</label>
						</td>
						<td>
							<input class="form-control" name="engName" value="${carBrand.engName}" />
							<span name="engNameEdit"></span>
						</td>
					</tr>
					<!--<tr>
						<td>
							<label class=" control-label"><span>*</span>官网地址：</label>
						</td>
						<td>
							<input class="form-control" name="webSite" value="${carBrand.webSite}" />
							<span name="webSiteEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>所属公司：</label>
						</td>
						<td>
							<input class="form-control" name="owerCompany" value="${carBrand.owerCompany}" />
							<span name="owerCompanyEdit"></span>
						</td>
					</tr>
					<tr>
						<td><label class=" control-label"><span>*</span>公司地址：</label></td>
						<td>
							<input class="form-control" name="companyAddr" value="${carBrand.companyAddr}" />
							<span name="companyAddrEdit"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>品牌介绍：</label>
						</td>
						<td>
							<input class="form-control" name="brandInfo" value="${carBrand.brandInfo}" />
							<span name="brandInfoEdit"></span>
						</td>
					</tr>-->
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="carBrandEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarBrandEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
      
    </tr>
  </tfoot>
  </table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="carBrandEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="carBrandPhotoEditForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="carBrandPhotoUrl1Edit" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val1">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="carBrandFineEditUploader"><span name="carBrandErrorEditInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editCarBrandPhotoBtn" value="确定">
					</div>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carBrandEdit":"res/js/car/car_brand_edit"}});
		require(["carBrandEdit"], function (carBrandEdit){
			carBrandEdit.init();
		});  
	});
	var config=new Object();
		config.uploadId="carBrandFineEditUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "carBrandPhotoEditForm";
		//文件回显inputName
		config.inputName = "carBrandPhotoUrl1Edit";
		//错误提示span标签name
		config.spanName = "carBrandErrorEditInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
	
	    
</script>