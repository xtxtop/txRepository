define(
    [],
    function() {
        var loveCarEdit = {
            appPath : getPath("app"),
            imgPath:getPath("img"),

            init : function() {
                var form = $("form[name=driverEditForm]");
                //添加提交
                $("#saveEditDriver").click(function(){
                    loveCarEdit.Editdriver();
                });
                //返回
                $("#closeEditDriver").click(function(){
                    closeTab("驾驶员编辑");

                });


                //驾照上传图片
                $("#idCardUrlButtonEdit").click(function(){
                    $("#driverLicensePhotoUrlEditModal").modal("show");
                });
                //驾照的回填
                $("#EditDriverLicensePhotoUrl").click(function(){
                    var form=$("form[name=driverLicensePhotoUrlEditForm]");
                    var img=form.find("input[name=drivingLicense]").val();
                    if(img!=""){
                        var form1=$("form[name=driverEditForm]");
                        form1.find("input[name=drivingLicense]").val(img);
                        form1.find("#sfzPicUrlImgEdit").css("background-image","url("+loveCarEdit.imgPath+'/'+img+")");
                        $("#driverLicensePhotoUrlEditModal").modal("hide");
                    }else{
                        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
                    }

                });


                $("#idCardUrlNegativeButtonEdit").click(function(){
                    $("#driverLicenseNegativePhotoUrlEditModal").modal("show");
                });
                //驾照的回填
                $("#EditDriverLicenseNegativePhotoUrl").click(function(){
                    var form=$("form[name=driverLicenseNegativePhotoUrlEditForm]");
                    var img=form.find("input[name=drivingLicenseCopy]").val();
                    if(img!=""){
                        var form1=$("form[name=driverEditForm]");
                        form1.find("input[name=drivingLicenseCopy]").val(img);
                        form1.find("#sfzNegativePicUrlImgEdit").css("background-image","url("+loveCarEdit.imgPath+'/'+img+")");
                        $("#driverLicenseNegativePhotoUrlEditModal").modal("hide");
                    }else{
                        bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
                    }

                });
            },

            Editdriver:function() {
                var form = $("form[name=driverEditForm]");
                form.ajaxSubmit({
                    url : loveCarEdit.appPath + "/cLoveCar/toLoveCarUpdate.do",
                    type : "post",
                    success : function(res) {
                        var msg = res.msg;
                        var code = res.code;
                        if (code == "1") {
                            bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
                                closeTab("驾驶员编辑");
                                $("#loveCarMengLongList").DataTable().ajax.reload(function(){});
                            });
                        } else {
                            bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
                        }
                    },
                    beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
                        form.find("span[name='driverNameEdit']").empty();
                        form.find("span[name='driverCardEdit']").empty();
                        form.find("span[name='driverPhoneEdit']").empty();
                        form.find("span[name='driverLicensePhotoUrlEdit']").empty();
                        form.find("span[name='driverLicenseNegativePhotoUrlEdit']").empty();
                        if (form.find("input[name='memberNo']").val()=="") {
                            form.find("span[name='driverNameEdit']").append("<font color='red'>请输入会员编号！</font>");
                            return false;
                        }

                        if (form.find("input[name='vehicleBrand']").val()=="") {
                            form.find("span[name='driverCardEdit']").append("<font color='red'>请输入车辆品牌！</font>");
                            return false;
                        }


                        if (form.find("input[name='vehicleModel']").val()=="") {
                            form.find("span[name='driverPhoneEdit']").append("<font color='red'>请输入车辆型号！</font>");
                            return false;
                        }

                        if (form.find("input[name='drivingLicense']").val()=="") {
                            form.find("span[name='driverLicensePhotoUrlEdit']").append("<font color='red'>请输入行驶证照片！</font>");
                            return false;
                        }

                        if (form.find("input[name='driverLicenseNegativePhotoUrl']").val()=="") {
                            form.find("span[name='driverLicenseNegativePhotoUrlEdit']").append("<font color='red'>请输入行驶证反面照片！</font>");
                            return false;
                        }
                    }
                });
            }
        }
        return loveCarEdit;
    })
