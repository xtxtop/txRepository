define([],function() {
var carEdit = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			var form = $("form[name=carEditForm]");
			//解绑弹出层
			$("#deviceCarModal").on("hidden.bs.modal", function() {

            });

			//解绑终端
			$(".editCarDevice").click(function(){
				carEdit.pageDeViceCar();
				var id=$(this).data("id");
				if($(this).val()=="解绑终端"){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要解绑此终端吗？", function(result) {
						if(result){
							//carEdit.getSelected(id);
							form.find("input[name='deviceSn']").val("");
	         				form.find("input[name='deviceId']").val("");
	         				$(".editCarDevice").val("绑定终端");
						}
					});
				}else{
					$("#deviceCarModal").modal({
						 show:true,
						 backdrop:'static'
					});
				}


            });


			//上传图片
			$("#editCarPhoto").click(function(){
				$("#careditModal").modal("show");
			});
			$("#editzjCarPhoto").click(function(){
				$("#carzjeditModal").modal("show");
			});

			//新增图片回填
			$("#editCarPhotoBtn").click(function(){
				var form=$("form[name=carphotoForm]");
				var img=form.find("input[name=carPhotoUrl1]").val();
				if(img!=""){
					var form1=$("form[name=carEditForm]");
					form1.find("input[name=carPhotoUrl1]").val(img);
					form1.find("img[name=carPicUrlImg]").attr("src",carEdit.imgPath+"/"+img);
					$("#careditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			$("#editCarzjPhotoBtn").click(function(){
				var form=$("form[name=carzjphotoForm]");
				var img=form.find("input[name=carDocPhotoUrl1]").val();
				if(img!=""){
					var form1=$("form[name=carEditForm]");
					form1.find("input[name=carDocPhotoUrl1]").val(img);
					form1.find("#carzjPicUrlImg").css("background-image","url("+carEdit.imgPath+'/'+img+")");
					$("#carzjeditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});


			//编辑提交
			$("#saveCar").click(function(){
				carEdit.editCar();
			});
			//关闭车辆详情编辑页
			$("#closeCarB").click(function(){
				closeTab("车辆编辑");
            });

			//选择城市后匹配相应的场站
			form.find("select[name='cityId']").change(function(){
				var cityId = form.find("select[name='cityId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: carEdit.appPath+"/car/getCityPark.do",
		             data: {cityId:cityId},
		             success: function(res){
		            	 var listParks = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='locationParkNo']").html("");
		            		 var option = "";
		            		 for(var i=0;i<listParks.length;i++){
		            			 option+="<option  value='"+listParks[i].parkNo+"'> "+listParks[i].parkName+" </option>";
		              		 }
		            		 form.find("select[name='locationParkNo']").html(option);
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		                 }
		             }
				});
				
				
			});
			
			
			
			
			//选择车辆品牌后匹配相应的车型
			form.find("select[name='carBrandId']").change(function(){
				var brandId = form.find("select[name='carBrandId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: carEdit.appPath+"/car/getCarModleByBrand.do",
		             data: {brandId:brandId},
		             success: function(res){
		            	 var carSeries = res.data;
		            	 if(res.code=="1"){
		            		 form.find("select[name='carModelId']").html("");
		            		 var option = "";
		            		 for(var i=0;i<carSeries.length;i++){
		            			 option+="<option  value='"+carSeries[i].carSeriesId+"'> "+carSeries[i].carSeriesName+" </option>";
		              		 }
		            		 form.find("select[name='carModelId']").html(option);
		            		 carEdit.getdataDictItemIdEdit();
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
		                 }
		             }
				});

			});

				//  汽车型号 变化 根据汽车型号 匹配 对应座位数 和 图片
				form.find("select[name='carModelId']").change(function(){
					var carSeriesId =  $("#carModelIdEdit option:selected").val();
					 var seaTing;
					$.ajax({
						 type: "post",
			             url: carEdit.appPath+"/car/getCarModle.do",
			             data: {carSeriesId:carSeriesId},
			             success: function(res){
			            	 if(res.code=="1"){
			            		 var data = res.data;
			            		 if(data.seaTing ==null){
			            			 seaTing="<input class='form-control' name='seaTing' readonly='readonly'/><span name='seaTing'></span>";
			            		 }else{
			            			 seaTing="<input class='form-control' name='seaTing' value='"+data.seaTing+"'readonly='readonly'/><span name='seaTing'></span>";
			            		 }
			            		 if(data.carSeriresPic==null){
			            			 $("input[name=carPhotoUrl1]").attr("value","");
			            			 $("img[name=carPicUrlImg]").attr("src"," ");
			            		 }else{
			            			 $("input[name=carPhotoUrl1]").attr("value",data.carSeriresPic);
			            			 $("img[name=carPicUrlImg]").attr("src",carEdit.imgPath+"/"+data.carSeriresPic);
			            		 }
			            		 form.find(".seatEdit").html(seaTing);

			                 }else{
			                	 form.find(".seatEdit").html("");
			            		 seaTing="<input class='form-control' name='seaTing'readonly='readonly'/><span name='seaTing'></span> ";
			            		 form.find(".seatEdit").html(seaTing);
		            			 $("img[name=carPicUrlImg]").attr("src"," ");
		            			 $("input[name=carPhotoUrl1]").attr("value","");


			                 }
			             }
					});



				});

		},
		// 汽车品牌 变化 根据汽车型号 匹配 对应座位数 和 图片
		getdataDictItemIdEdit:function(){
			var form = $("form[name=carEditForm]");
			var carSeriesId = $("#carModelIdEdit option:selected").val();
			 var seaTing;
			$.ajax({
				 type: "post",
	             url: carEdit.appPath+"/car/getCarModle.do",
	             data: {carSeriesId:carSeriesId},
	             success: function(res){
	            	 if(res.code=="1"){
	            		 var data = res.data;
	            		 form.find(".seatEdit").html("");
	            		 if(data.seaTing ==null){
	            			 seaTing="<input class='form-control' name='seaTing' readonly='readonly'/><span name='seaTing'> ";
	            		 }else{
	            			 seaTing="<input class='form-control' name='seaTing' value='"+data.seaTing+"'readonly='readonly'/><span name='seaTing'> ";
	            		 }
	            		 if(data.carSeriresPic==null){
	            			 $("input[name=carPhotoUrl1]").attr("value","");
//	            			 $("img[name=carPicUrlImg]").attr("src"," ");
	            			 $("#carPicUrlImg").css("background-image","url('')");
	            		 }else{
	            			 $("input[name=carPhotoUrl1]").attr("value",data.carSeriresPic);
	            			 $("#carPicUrlImg").css("background-image","url("+carEdit.imgPath+'/'+data.carSeriresPic+")");
	            		 }
	            		 form.find(".seatEdit").html(seaTing);
	                 }
	            	 else{
	                	 form.find(".seatEdit").html("");
	            		 var seaTing = "";
	            		 seaTing="<input class='form-control' name='seaTing'readonly='readonly'/><span name='seaTing'> ";
	            		 form.find(".seatEdit").html(seaTing);
//	            		 $("img[name=carPicUrlImg]").attr("src"," ");
	            		 $("#carPicUrlImg").css("background-image","url('')");
	           			 $("input[name=carPhotoUrl1]").attr("value","");


	                 }
	             }
			});

		},
		//解除锁定
		getSelected:function(ids){
			$.ajax({
         		url: carEdit.appPath + "/car/editCarDevice.do?carNo="+ids,
         		data:{"deviceId":""},
         		success: function(data){
         			if(data.code=="1"){
         				var form = $("form[name=carEditForm]");
         				form.find("input[name='deviceSn']").val("");
         				form.find("input[name='deviceId']").val("");
         				$(".editCarDevice").val("绑定终端");
         				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "解绑成功，请重新绑定！");


         			}else{
         				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "解绑失败,此车辆编号不存在！");
         			}
         		}
         	});
		},
		//绑定判断
		getSelectedRow:function(ids){
			var deviceId=ids;
			$.ajax({
         		url: carEdit.appPath+"/device/getDevice.do?terminalDeviceNo="+ids,
         		success: function(res){
         			if(res.code=="0"){
         				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此终端已绑定，请重新选择！")
         			}else{
         				$("#deviceCarModal").modal("hide");
         				var form = $("form[name=carEditForm]");
         				form.find("input[name='deviceSn']").val(res.data.deviceSn);
         				form.find("input[name='deviceId']").val(res.data.terminalDeviceNo);
         			}
         		}
         	});
		},
		//解绑弹出层
		pageDeViceCar:function(){
			var form = $("form[name=carEditForm]");
			//var cityId = form.find("select[name='cityId']").find("option:selected").val();
			var deviceBtnTpl = $("#deviceCarBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(deviceBtnTpl);
			var table = $('#deviceCarList').dataTable({
				searching : false,
				destroy : true,
				"ajax" : {
					"type" : "POST",
					"url" : carEdit.appPath+"/device/pageListDeviceByCar.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "isAvailable":1
										 
										});
					},
					"dataSrc" : function(json) {
						json.recordsTotal = json.rowCount;
						json.recordsFiltered = json.rowCount;
						json.data = json.data;
						return json.data;
					},
					error : function(xhr, error, thrown) {
					}
				},
				"columns" : [
				{ "title":"","data": "terminalDeviceNo","width":"5px"},
				{
					"title" : "终端编号",
					"data" : "terminalDeviceNo"
				},{
					"title" : "终端序列号",
					"data" : "deviceSn"
				},{
					"title" : "SIM卡号",
					"data" : "simCardNo"
				}, {
					"title" : "城市",
					"data" : "cityName"
				}, {
					"title" : "绑定车辆",
					"data" : "carPlateNo"
				},
				{
					"title" : "启用状态",
					"data" : "isAvailable"
				} ],
				"dom" : "<'row'<''><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$("#deviceToolssss").empty().append('<button type="button"  class="btn-new-type sysDeviceCar-batch-del">绑定终端</button><button type="button"  class="btn-new-type btn-new-type-blue  sysDeviceCar-batch-close">关闭</button>');
//						$("#deviceToolssss").append('');
//						$("#deviceToolssss").css({'position':'absolute','right':'-10%','bottom':'0',});
						$(".sysDeviceCar-batch-del").on("click",function(){
							var ids=[];
							var len=$('#deviceCarList tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择终端！")
							}else{
								$("#deviceCarList tbody").find("input:checked").each(function(){
		        					ids.push($(this).val());

		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "终端只能选择一个！")
	        					}else{
	        						carEdit.getSelectedRow(ids);
	        					}
							}

							$('#deviceCarList thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#deviceCarList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#deviceCarList tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});


						$(".sysDeviceCar-batch-close").on("click",function(){
							$("#deviceCarModal").modal("hide");
							 $('#deviceCarList tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
					   {
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="terminalDeviceNo" value="' + full.terminalDeviceNo + '">';
						}
					   },
					   {
							"targets" : [6],
							"render" : function(a,
									b, c, d) {
								var available;//是否启用（0、停用，1、启用）
								if(c.isAvailable==0){
									available="停用";
								}else if(c.isAvailable==1){
									available="启用";
								}
								return available;
							}
						}
					   ]
			});
		},
 editCar:function() {
	var form = $("form[name=carEditForm]");
	form.ajaxSubmit({
		url : carEdit.appPath + "/car/editCar.do",
		type : "post",
		success : function(res) {
			var msg = res.msg;
			var code = res.code;
			if (code == "1") {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
					closeTab("车辆编辑");
					$("#carList").DataTable().ajax.reload(function(){});
				});
			} else {
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败！"+msg);
			}
		},
		beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
			form.find("span[name='carPlateNo']").empty();
			form.find("span[name='seaTing']").empty();
			form.find("span[name='carOwnerId']").empty();
			form.find("span[name='carIdNo']").empty();
			form.find("span[name='engineNo']").empty();
			form.find("span[name='validityDate']").empty();
			form.find("span[name='onlineStatusUpdateTime']").empty();
			form.find("span[name='usedStatusUpdateTime']").empty();
			form.find("span[name='power']").empty();
			form.find("span[name='mileage']").empty();
			form.find("span[name='enrollmentDate']").empty();
			form.find("span[name='deviceId']").empty();
			form.find("span[name='effectiveInsurancePeriod']").empty();
			if (form.find("input[name='carPlateNo']").val()=="") {
				form.find("span[name='carPlateNo']").append("<font color='red'>请输入车牌号！</font>");
				return false;
			}
//			if (form.find("select[name='locationParkNo']").val()==null||form.find("select[name='locationParkNo']").val()=="") {
//				form.find("span[name='locationParkNo']").append("<font color='red'>请选择场站！</font>");
//				return false;
//			}
			if (form.find("select[name='carModelId']").val()=="" || form.find("select[name='carModelId']").val()==null) {
				form.find("span[name='carModelIdAdd']").append("<font color='red'>请添加型号</font>");
				return false;
			}
			//判断座位数 是否为空 大于等于1的正整数
			if (form.find("input[name='seaTing']").val()=="") {
				form.find("span[name='seaTing']").append("<font color='red'>座位数不能为空 请到数据字典添加座位数！</font>");
				return false;
			}
			if(form.find("input[name='seaTing']").val()!=""&&!/^\+?[1-9]\d*$/.test(form.find("input[name='seaTing']").val())){
				form.find("span[name='seaTing']").append("<font color='red'>座位数不正确 必须是大于0的正整数</font>");
				return false;
			}

			if (form.find("input[name='carOwnerId']").val()=="") {
				form.find("span[name='carOwnerId']").append("<font color='red'>请输入车主！</font>");
				return false;
			}
/*			if (form.find("input[name='carIdNo']").val()=="") {
				$("span[name='carIdNo']").append("<font color='red'>请输入车辆识别码！</font>");
				return false;
			}
			if (form.find("input[name='engineNo']").val()=="") {
				$("span[name='engineNo']").append("<font color='red'>请输入发动机号！</font>");
				return false;
			}
*/
			if (form.find("input[name='carIdNo']").val()!=""&&!/^[A-Z0-9]{17}$/.test(form.find("input[name='carIdNo']").val())) {
				form.find("span[name='carIdNo']").append("<font color='red'>车辆识别码格式不正确，必须是17位的数字和大写字母组合！</font>");
				return false;
			}
			if (form.find("input[name='engineNo']").val()!=""&&!/^[0-9A-Z]{6,10}$/.test(form.find("input[name='engineNo']").val())) {
				form.find("span[name='engineNo']").append("<font color='red'>发动机号为6~9位字母数字组合或者纯数字！</font>");
				return false;
			}
			if (form.find("input[name='engineNo']").val()!=""&&/^[A-Z]{6,10}$/.test(form.find("input[name='engineNo']").val())) {
				form.find("span[name='engineNo']").append("<font color='red'>发动机号为6~9位字母数字组合或者纯数字！</font>");
				return false;
			}
//			if (form.find("input[name='validityDate']").val()=="") {
//				$("span[name='validityDate']").append("<font color='red'>请输入检验有效期！</font>");
//				return false;
//			}
			if (form.find("input[name='onlineStatusUpdateTime']").val()=="") {
				form.find("span[name='onlineStatusUpdateTime']").append("<font color='red'>请输入上线时间！</font>");
				return false;
			}
			if (form.find("input[name='usedStatusUpdateTime']").val()=="") {
				form.find("span[name='usedStatusUpdateTime']").append("<font color='red'>请输入使用状态更新时间！</font>");
				return false;
			}
			if (form.find("input[name='power']").val()=="") {
				form.find("span[name='power']").append("<font color='red'>请输入电量！</font>");
				return false;
			}
			if (form.find("input[name='mileage']").val()=="") {
				form.find("span[name='mileage']").append("<font color='red'>请输入里程！</font>");
				return false;
			}
			if (form.find("input[name='deviceId']").val()=="") {
				form.find("span[name='deviceId']").append("<font color='red'>请绑定终端编号！</font>");
				return false;
			}
/*
 			if (form.find("input[name='enrollmentDate']").val()=="") {
				$("span[name='enrollmentDate']").append("<font color='red'>请输入投保日期！</font>");
				return false;
			}
			if (form.find("input[name='effectiveInsurancePeriod']").val()=="") {
				$("span[name='effectiveInsurancePeriod']").append("<font color='red'>请输入有效保险期！</font>");
				return false;
			}
*/
		}
	});
},
		 saveCar:function() {
			var form = $("form[name=carEditForm]");
			form.ajaxSubmit({
				url : carEdit.appPath + "/car/editCar.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("车辆增加");
							$("#carList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					form.find("span[name='carPlateNo']").empty();
					form.find("span[name='seaTing']").empty();
					form.find("span[name='carOwnerName']").empty();
					form.find("span[name='carIdNo']").empty();
					form.find("span[name='engineNo']").empty();
					form.find("span[name='validityDate']").empty();
					form.find("span[name='onlineStatusUpdateTime']").empty();
					form.find("span[name='usedStatusUpdateTime']").empty();
					form.find("span[name='power']").empty();
					form.find("span[name='mileage']").empty();
					form.find("span[name='enrollmentDate']").empty();
					form.find("span[name='deviceId']").empty();
					form.find("span[name='effectiveInsurancePeriod']").empty();
					form.find("span[name='carPhotoUrl1']").empty();
					form.find("span[name='carDocPhotoUrl1']").empty();
					if (form.find("input[name='carPlateNo']").val()=="") {
						form.find("span[name='carPlateNo']").append("<font color='red'>请输入车牌号！</font>");
						return false;
					}
					if (form.find("select[name='locationParkNo']").val()==null||form.find("select[name='locationParkNo']").val()=="") {
						form.find("span[name='locationParkNo']").append("<font color='red'>请选择场站！</font>");
						return false;
					}
					//判断座位数 是否为空 大于等于1的正整数
					if (form.find("input[name='seaTing']").val()=="") {
						form.find("span[name='seaTing']").append("<font color='red'>请输入座位数！</font>");
						return false;
					}
					if(form.find("input[name='seaTing']").val()!=""&&!/^\+?[1-9]\d*$/.test(form.find("input[name='seaTing']").val())){
						form.find("span[name='seaTing']").append("<font color='red'>座位数不正确 必须是大于0的正整数</font>");
						return false;
					}
					if (form.find("input[name='carOwnerName']").val()=="") {
						form.find("span[name='carOwnerName']").append("<font color='red'>请输入车主！</font>");
						return false;
					}
/*					if (form.find("input[name='carIdNo']").val()=="") {
						$("span[name='carIdNo']").append("<font color='red'>请输入车辆识别码！</font>");
						return false;
					}
					if (form.find("input[name='engineNo']").val()=="") {
						$("span[name='engineNo']").append("<font color='red'>请输入发动机号！</font>");
						return false;
					}*/
					if (form.find("input[name='carIdNo']").val()!=""&&!/^[A-Z0-9]{17}$/.test(form.find("input[name='carIdNo']").val())) {
						form.find("span[name='carIdNo']").append("<font color='red'>车辆识别码格式不正确，必须是17位的数字和大写字母组合！</font>");
						return false;
					}
					if (form.find("input[name='validityDate']").val()=="") {
						form.find("span[name='validityDate']").append("<font color='red'>请输入检验有效期！</font>");
						return false;
					}
					if (form.find("input[name='onlineStatusUpdateTime']").val()=="") {
						form.find("span[name='onlineStatusUpdateTime']").append("<font color='red'>请输入上线时间！</font>");
						return false;
					}
					if (form.find("input[name='usedStatusUpdateTime']").val()=="") {
						form.find("span[name='usedStatusUpdateTime']").append("<font color='red'>请输入使用状态更新时间！</font>");
						return false;
					}
					if (form.find("input[name='power']").val()=="") {
						form.find("span[name='power']").append("<font color='red'>请输入电量！</font>");
						return false;
					}
					if (form.find("input[name='mileage']").val()=="") {
						form.find("span[name='mileage']").append("<font color='red'>请输入里程！</font>");
						return false;
					}
					if (form.find("input[name='deviceId']").val()=="") {
						form.find("span[name='deviceId']").append("<font color='red'>请绑定终端编号！</font>");
						return false;
					}
/*					if (form.find("input[name='enrollmentDate']").val()=="") {
						$("span[name='enrollmentDate']").append("<font color='red'>请输入投保日期！</font>");
						return false;
					}
					if (form.find("input[name='effectiveInsurancePeriod']").val()=="") {
						$("span[name='effectiveInsurancePeriod']").append("<font color='red'>请输入有效保险期！</font>");
						return false;
					}
					if (form.find("input[name='carPhotoUrl1']").val()=="") {
						$("span[name='carPhotoUrl1']").append("<font color='red'>请上传车辆照片！</font>");
						return false;
					}
					if (form.find("input[name='carDocPhotoUrl1']").val()=="") {
						$("span[name='carDocPhotoUrl1']").append("<font color='red'>请车辆证件照片！</font>");
						return false;
					}*/
				}
			});
		}
}
return carEdit
})
