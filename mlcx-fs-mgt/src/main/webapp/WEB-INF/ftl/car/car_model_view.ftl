<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>车型详情</h4></label>
						</div>
					</div>
				</div>
            <form name="carModelEditForm">
                <div class="row hzlist">
                <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label key">品牌：</label>
                        <div class="col-sm-7">
                            <label class="control-label val">${model.carBrandName}</label>
                        </div>
                    </div>
                
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;型号：</label>
                        <div class="col-sm-7">
                        <label class="control-label val">${model.carModelName}</label>
                        </div>
                    </div>
                    
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;类别：</label>
                        <div class="col-sm-7">
                        <label class="control-label val">
                        <#if model.carType==1>经济型</#if>
                        <#if model.carType==2>商务型</#if>
                        <#if model.carType==3>豪华型</#if>
                        <#if model.carType==4>6至15座商务</#if>
                        <#if model.carType==5>SUV</#if>
                        </label>
                        </div>
                    </div>

                </div>
                <div class="row hzlist">
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;座位数：</label>
                        <div class="col-sm-7">
                        <label class="control-label val">${model.seatNumber}</label>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;档位：</label>
                        <div class="col-sm-7">
                         <label class="control-label val">
                        <#if model.stall==1>手动 </#if>
                        <#if model.stall==2>自动</#if>
                        </label>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;箱型：</label>
                        <div class="col-sm-7">
                         <label class="control-label val">${model.boxType}</label>
                        </div>
                    </div>

                </div>
				<div class="row hzlist">
                        <div class="form-group col-md-4">
                            <label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;排量：</label>
                            <div class="col-sm-7">
                            <label class="control-label val">${model.displacement}</label>
                            </div>
                        </div>

        		</div>
        		<div class="row hzlist">
                       <div class="form-group col-md-12">
                			<label class="col-sm-1 control-label key"><span></span>&nbsp;&nbsp;车辆照片：</label>
                			<div class="col-sm-9 val">
                				<img src="${imagePath!''}/${model.carPhotoUrl1}" width="320" height="200" name="carPicUrlImg"/>
                			</div>
                		</div>
        		</div>
        		<!-- /.row -->
            </form>
            <div class="form-group">
                <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeEditCarModelVo" class="btn btn-deModel pull-right btncolorb" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                           关闭
                    </button>
                </div>
            </div>
</div>


<script type="text/javascript">
$("#closeEditCarModelVo").click(function(){
	closeTab("车型详情");
});
</script>
