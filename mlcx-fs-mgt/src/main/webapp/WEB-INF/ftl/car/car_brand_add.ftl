<meta charset="utf-8">
<style>
.add-car-img{
		background-size:100%;
		background-position:center;
		background-repeat:no-repeat;
		border:solid 1px #ddd;
		width:80px;
		height:80px;
		border-radius:50%;
		margin:0 auto;
		line-height:80px;
		text-align:center;
	}
	.add-img-position>.icon{
	    font-size: 50px;
	    color:#3f9fff;
	}
</style>
<div class="container-fluid backgroundColor">
	<form name="carBrandAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增品牌</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label"><span>*</span>品牌名称：</label>
						</td>
						<td>
							<input class="form-control" name="carBrandName" placeholder="请输入品牌信息"/> 
							<span name="carBrandNameAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>logo图片：</label>
						</td>
						<td>
								 <input type="hidden" name="logoPic"/>
								 <!--<img src="" width="200" name="carBrandPicUrlImg" />
                    			 <button type="button" id="" class="btn btn-info btn-default">上传图片</button>
                    			 -->
						   <div class="add-car-img" id="carBrandPicUrlImg">
						      <div id="addCarBrandPhoto" class="add-img-position">
						    	<span class="icon">+</span>
						      </div>
						   </div>
						   <span name="logoPicAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span></span>名称简写：</label>
						</td>
						<td>
							<input class="form-control" name="brandShortName" placeholder="请输入名称简写"/>
							<span name="brandShortNameAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span></span>英文名称：</label>
						</td>
						<td>
							<input class="form-control" name="engName" placeholder="请输入英文名称" />
							<span name="engNameAdd"></span>
						</td>
					</tr>
					<!--<tr>
						<td>
							<label class=" control-label"><span>*</span>官网地址：</label>
						</td>
						<td>
							<input class="form-control" name="webSite"  placeholder="请输入官网地址"/><span name="webSiteAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>所属公司：</label>
						</td>
						<td>								
							<input class="form-control" name="owerCompany"  placeholder="请输入所属公司"/>
							<span name="owerCompanyAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span>*</span>公司地址：</label>
						</td>
						<td>
                            <input class="form-control" name="companyAddr"  placeholder="请输入公司地址"/>
                            <span name="companyAddrAdd"></span>
						</td>
						<td>
							<label class=" control-label"><span>*</span>品牌介绍：</label>
						</td>
						<td>
							<input class="form-control" name="brandInfo" placeholder="请输入品牌介绍"/>
							<span name="brandInfoAdd"></span>
						</td>
					</tr>-->
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="addCarBrandAdd" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarBrandAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
      
    </tr>
  </tfoot>
  </table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="carBrandAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="carBrandPhotoForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="carBrandPhotoUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="carBrandFineUploader"><span name="carBrandErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addCarBrandPhotoBtn" value="确定">
					</div>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carBrandAdd":"res/js/car/car_brand_add"}});
		require(["carBrandAdd"], function (carBrandAdd){
			carBrandAdd.init();
		});  
	});
	var config=new Object();
		config.uploadId="carBrandFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "carBrandPhotoForm";
		//文件回显inputName
		config.inputName = "carBrandPhotoUrl1";
		//错误提示span标签name
		config.spanName = "carBrandErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});

</script>