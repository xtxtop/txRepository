<meta charset="utf-8">

<div class="container-fluid backgroundColor">
	<form name="carSeriesAddForm" class="form-horizontal">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增车系</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label"><span>*</span>车型系列：</label></td>
						<td><input class="form-control" name="carSeriesName"  placeholder="请输入车系名称"/> <span name="carSeriesNameAdd"></span></td>
						<td><label class=" control-label"><span>*</span>品牌：</label></td>
						<td>
							<select class="form-control" name="carBrandId">
									<option value="">全部</option>
									<#if carBrandList?exists>
                                        <#list carBrandList as carBrand>
                                            <option value="${carBrand.carBrandId}">${carBrand.carBrandName}</option>
                                        </#list>
                                    </#if>
								</select>
								<span name="carBrandIdAdd"></span>
						</td>
					</tr>
					<tr class="add-car-last-tr">
						<td><label class=" control-label"><span>*</span>默认座位数：</label></td>
						<td><input class="form-control" name="seaTing"  placeholder="请输入座位数"/><span name="seaTingAdd"></span></td>
						
						<td><label class=" control-label"><span>*</span>默认图片：</label></td>
					
						<td class="odd-td">
						 <input type="hidden" name="carSeriresPic">
						<div class="img-td-upload" id="carSeriesPicUrlImg">
						    <div id="addCarSeriesPhoto" class="add-img-position">
						    	<h3 class="icon">+</h3>
						    	<h3 class="text">添加图片</h3>
						</div>
							
						</div><span name="carSeriesPicAdd"></span>
						</td>	
					</tr>
				</tbody>
				 <tfoot class="tab-tfoot">
    <tr>
      <td colspan="2"><button type="button" id="addCarSeries" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeCarSeriesAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>
      
    </tr>
  </tfoot>
  </table>
		</div>
	</form>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="carSeriesAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="carSeriesPhotoForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="carSeriesPhotoUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val1">车系图片：</label>
                           <div class="col-md-8">
                                <div id="carSeriesFineUploader"><span name="carSeriesErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addCarSeriesPhotoBtn" value="确定">
					</div>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carSeriesAdd":"res/js/car/car_series_add"}});
		require(["carSeriesAdd"], function (carSeriesAdd){
			carSeriesAdd.init();
		});  
	});
	var config=new Object();
		config.uploadId="carSeriesFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "carSeriesPhotoForm";
		//文件回显inputName
		config.inputName = "carSeriesPhotoUrl1";
		//错误提示span标签name
		config.spanName = "carSeriesErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});

</script>