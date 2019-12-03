<meta charset="utf-8">
<div class="container-fluid backgroundColor">
    <form name="driverEditForm">
        <input class="form-control val"   type="hidden"    name="loveCarNo" maxlength="20" value="${cLoveCar.loveCarNo}"/>
        <div class="row hzlist">
            <table class="tab-table table table-border table-responsive">
                <thead class="tab-thead">
                <tr>
                    <th colspan="4">驾驶员编辑</th>
                </tr>
                </thead>
                <tbody class="tab-tbody">
                <tr>
                    <td>
                        <label class=" control-label key"><span>*</span>会员编号：</label>
                    </td>
                    <td>
                        <input class="form-control val" name="memberNo" maxlength="20" value="${cLoveCar.memberNo}"/>
                        <span name="driverNameEdit"></span>
                    </td>
                    <td>
                        <label class=" control-label key"><span>*</span>车辆品牌：</label>

                    </td>
                    <td>
                        <input class="form-control val" name="vehicleBrand" maxlength="20" value="${cLoveCar.vehicleBrand}" onblur="getBirthdayFromIdCard()" />
                        <span name="driverCardEdit"></span>
                    </td>
                </tr>



                <tr>

                    <td>
                        <label class=" control-label key"><span>*</span>车辆型号：</label>
                    </td>
                    <td>
                        <input class="form-control val" name="vehicleModel" maxlength="20" value="${cLoveCar.vehicleModel}"/>
                        <span name="driverPhoneEdit"></span>

                    </td>

                    <td class="photo-line-height idCardUrl">
                        <label class=" control-label key"><span>*</span>行驶证照片：</label>
                        <input name="drivingLicense" type="hidden"  value="${cLoveCar.drivingLicense}"/>
                    </td>
                    <td class="odd-td idCardUrl">
                        <div class="img-td-upload" id="sfzPicUrlImgEdit" style="background-image: url(${imagePath!''}/${cLoveCar.drivingLicense});">
                            <div id="idCardUrlButtonEdit" class="Edit-img-position">
                                <h3 class="icon">+</h3>
                                <h3 class="text">添加图片</h3>
                            </div>
                        </div>
                        <span name="driverLicensePhotoUrlEdit"></span>
                    </td>
                </tr>



                <tr>
                    <td class="photo-line-height idCardUrl">
                        <label class=" control-label key"><span>*</span>行驶证附件照片：</label>
                        <input name="drivingLicenseCopy" type="hidden"  value="${cLoveCar.drivingLicenseCopy}"/>
                    </td>
                    <td class="odd-td idCardUrl">
                        <div class="img-td-upload" id="sfzNegativePicUrlImgEdit" style="background-image: url(${imagePath!''}/${cLoveCar.drivingLicenseCopy});">
                            <div id="idCardUrlNegativeButtonEdit" class="Edit-img-position">
                                <h3 class="icon">+</h3>
                                <h3 class="text">添加图片</h3>
                            </div>
                        </div>
                        <span name="driverLicenseNegativePhotoUrlEdit"></span>
                    </td>


                </tr>


                </tbody>
                <tfoot class="tab-tfoot">
                <tr>
                    <td colspan="2"><button type="button" id="saveEditDriver" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                        保存
                    </button></td>
                    <td colspan="2"><button type="button" id="closeEditDriver" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                        关闭
                    </button></td>

                </tr>
                </tfoot>
            </table>
        </div>
    </form>
</div>


<!--驾照上传-->
	<div class="modal fade" id="driverLicensePhotoUrlEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">行驶证上传</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="driverLicensePhotoUrlEditForm">
                        <div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control val" placeholder="" name="drivingLicense" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">行驶证图片：</label>
                            <div class="col-md-8">
                                <div id="driverLicensePhotoUrlUploaderEdit"><span name="driverLicensePhotoUrlssEdit"></span></div>
                            </div>
                        </div>
                    </form>
                    <div class="modal-footer">
                        <input type="button" class="btn btn-default pull-right sure" id="EditDriverLicensePhotoUrl" value="确定">
                    </div>
                </div>

            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

<!--驾照反面上传-->
	<div class="modal fade" id="driverLicenseNegativePhotoUrlEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">行驶证反面上传</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="driverLicenseNegativePhotoUrlEditForm">
                        <div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control val" placeholder="" name="drivingLicenseCopy" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">行驶证反面图片：</label>
                            <div class="col-md-8">
                                <div id="driverLicenseNegativePhotoUrlUploaderEdit"><span name="driverLicenseNegativePhotoUrlssEdit"></span></div>
                            </div>
                        </div>
                    </form>
                    <div class="modal-footer">
                        <input type="button" class="btn btn-default pull-right sure" id="EditDriverLicenseNegativePhotoUrl" value="确定">
                    </div>
                </div>

            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>





<script type="text/javascript">
    $(function(){
        require.config({paths: {"loveCarEdit":"res/js/ml/loveCar_edit"}});
        require(["loveCarEdit"], function (loveCarEdit){
            loveCarEdit.init();
        });

        var config1 = new Object();
        config1.uploadId = "driverLicensePhotoUrlUploaderEdit";
        //storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
        config1.storePath = "member_doc";
        config1.itemLimit = 1;
        config1.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
        config1.sizeLimit = 500 * 1024;
        config1.minSizeLimit = 10 * 1024;
        config1.formName = "driverLicensePhotoUrlEditForm";
        //文件回显inputName
        config1.inputName = "drivingLicense";
        //错误提示span标签name
        config1.spanName = "driverLicensePhotoUrlssEdit";
        require.config({
            paths: {
                "upload": "res/js/resource/uploadFile"
            }
        });
        require(["upload"], function(upload) {
            upload.init(config1);
        });

        var config2 = new Object();
        config2.uploadId = "driverLicenseNegativePhotoUrlUploaderEdit";
        //storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
        config2.storePath = "member_doc";
        config2.itemLimit = 1;
        config2.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
        config2.sizeLimit = 500 * 1024;
        config2.minSizeLimit = 10 * 1024;
        config2.formName = "driverLicenseNegativePhotoUrlEditForm";
        //文件回显inputName
        config2.inputName = "drivingLicenseCopy";
        //错误提示span标签name
        config2.spanName = "driverLicenseNegativePhotoUrlssEdit";
        require.config({
            paths: {
                "upload": "res/js/resource/uploadFile"
            }
        });
        require(["upload"], function(upload) {
            upload.init(config2);
        });

    });
    $(function () {
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            clearBtn: true,//清除按钮
            format: "yyyy-mm-dd hh:ii"//日期格式
        });
    });

</script>