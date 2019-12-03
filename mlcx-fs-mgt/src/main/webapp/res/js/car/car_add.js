define([],function() {	
var carAdd = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			var form = $("form[name=carAddForm]");
			
			//加盟商
			$("#franchiseeAdd").click(function(){
				$("#franchiseeModel").modal("show");
				carAdd.pageFranchisee();
			});
			//加盟商列表查询
			$("#franchiseeSearch").click(function(){
				carAdd.pageFranchisee();
	        });
			
			//根据车辆 加载 图片
			if($("input[name=carPhotoUrl1]").val()!=null && $("input[name=carPhotoUrl1]").val() !="" ){
       		 	$("#carPicUrlImg").css("backgroundImage","url("+carAdd.imgPath+"/"+$("input[name=carPhotoUrl1]").val()+")");
			}
			//解绑弹出层
			$("#deviceCarModalAdd").on("hidden.bs.modal", function() {  
				
            });
			
			//解绑终端
			$(".addcarDevice").click(function(){
				var id=$(this).data("id");
				if($(this).val()=="解绑终端"){
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要解绑此终端吗？", function(result) {
						if(result){
							carAdd.getSelected(id);
						}						
					});
				}else{
					$("#deviceCarModalAdd").modal({
						 show:true,
						 backdrop:'static'
					});
					carAdd.pageDeViceCar();
				}
				
				
            });
			//弹出加盟商
			/*$("#franchiseeAdd").click(function(){
				var id=$(this).data("id");
					$("#franchiseeNoAdd").modal({
						 show:true,
						 backdrop:'static'
					});
					carAdd.franchiseeCar();
				
            });*/
			//上传图片
			$("#addCarPhoto").click(function(){
				$("#carAddModal").modal("show");
			});
			$("#addzjCarPhoto").click(function(){
				$("#carzjAddModal").modal("show");
			});
			
			//新增图片回填
			$("#addCarPhotoBtn").click(function(){
				var form=$("form[name=carphotoForm]");
				var img=form.find("input[name=carPhotoUrl1]").val();
				if(img!=""){
					var form1=$("form[name=carAddForm]");
					form1.find("input[name=carPhotoUrl1]").val(img);
//					form1.find("#carPicUrlImg").attr("src",carAdd.imgPath+"/"+img);
					$("#carPicUrlImg").css("backgroundImage","url("+carAdd.imgPath+"/"+img+")");
					$("#carAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			$("#addCarzjPhotoBtn").click(function(){
				var form=$("form[name=carzjphotoForm]");
				var img=form.find("input[name=carDocPhotoUrl1]").val();
				if(img!=""){
					var form1=$("form[name=carAddForm]");
					form1.find("input[name=carDocPhotoUrl1]").val(img);
					$("#carzjPicUrlImg").css("backgroundImage","url("+carAdd.imgPath+"/"+img+")");
					$("#carzjAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
			//增加提交
			$("#addCar").click(function(){
				carAdd.saveCar();
			});
			//增加页面关闭
			$("#closeCaradd").click(function(){
				closeTab("新增车辆");
			});
			
		//选择城市后匹配相应的场站
			form.find("select[name='cityId']").change(function(){
				var cityId = form.find("select[name='cityId']").find("option:selected").val();
				$.ajax({
					 type: "post",
		             url: carAdd.appPath+"/car/getCityPark.do",
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
		             url: carAdd.appPath+"/car/getCarModleByBrand.do",
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
		            		 carAdd.getdataDictItemId();
		                 }else{
		                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
		                 }
		             }
				});
				
				
			});
			
			
			//  汽车型号 变化 根据汽车型号 匹配 对应座位数 和 图片
			form.find("select[name='carModelId']").change(function(){
				var carSeriesId = $("#carModelId option:selected").val();
				$.ajax({
					 type: "post",
		             url: carAdd.appPath+"/car/getCarModle.do",
		             data: {carSeriesId:carSeriesId},
		             success: function(res){
		            	 if(res.code=="1"){
		            		 var data = res.data;
		            		 form.find(".seat").html("");
		            		 var seaTing;
		            		 if(data.seaTing ==null){
		            			  seaTing="<input class='form-control val' name='seaTing' readonly='readonly'/> "; 
		            		 }else{
		            			 seaTing="<input class='form-control val' name='seaTing' value='"+data.seaTing+"'readonly='readonly'/> ";
		            		 }
		            		 form.find(".seat").html(seaTing);
		            		 if(data.carSeriresPic==null){
		            			 $("input[name=carPhotoUrl1]").attr("value","");
		            			 $("img[name=carPicUrlImg]").attr("src"," ");
		            		 }else{
		            			 $("input[name=carPhotoUrl1]").attr("value",data.carSeriresPic);
		            			 $("img[name=carPicUrlImg]").attr("src",carAdd.imgPath+"/"+data.carSeriresPic);
		            		 }
		            		
		                 }else{
		                	 form.find(".seat").html("");
		            		 seaTing="<input class='form-control' name='seaTing'readonly='readonly'/> ";
		            		 form.find(".seat").html(seaTing);
	            			 $("img[name=carPicUrlImg]").attr("src"," ");
	            			 $("input[name=carPhotoUrl1]").attr("value","");
		                 }
		             }
				});
				
				
				
			});
			
			
		},
		//解除锁定
		getSelected:function(ids){
			$.ajax({
         		url: carAdd.appPath + "/car/editCar.do?carNo="+ids, 
         		data:{"deviceId":""},
         		success: function(data){
         			if(data.code=="1"){
         				var form = $("form[name=carAddForm]");
         				form.find("input[name='deviceSn']").val("");
         				form.find("input[name='deviceId']").val("");
         				$(".addcarDevice").val("绑定终端");
         				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "解绑成功，请重新绑定！");
         			}else{
         				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "解绑失败,此车辆编号不存在！");
         			}
         		}
         	});
		},
		// 汽车品牌 变化 根据汽车型号 匹配 对应座位数 和 图片
		getdataDictItemId:function(){
			var form = $("form[name=carAddForm]");
			var carSeriesId = $("#carModelId option:selected").val();
			$.ajax({
				 type: "post",
	             url: carAdd.appPath+"/car/getCarModle.do",
	             data: {carSeriesId:carSeriesId},
	             success: function(res){
	            	 if(res.code=="1"){
	            		 var data = res.data;
	            		 form.find(".seat").html("");
	            		 var seaTing;
	            		 if(data.seaTing ==null){
	            			 seaTing="<input class='form-control val' name='seaTing' readonly='readonly'/> "; 
	            		 }else{
	            			 seaTing="<input class='form-control val' name='seaTing' value='"+data.seaTing+"'readonly='readonly'/> ";
	            		 }
	            		 if(data.carSeriresPic==null){
	            			 $("input[name=carPhotoUrl1]").attr("value","");
	            			 $("img[name=carPicUrlImg]").attr("src"," ");
	            		 }else{
	            			 $("input[name=carPhotoUrl1]").attr("value",data.carSeriresPic);
	            			 $("img[name=carPicUrlImg]").attr("src",carAdd.imgPath+"/"+data.carSeriresPic);
	            		 }
	            		 form.find(".seat").html(seaTing);
	                 }
	            	 else{
	                	 form.find(".seat").html("");
	            		 seaTing="<input class='form-control' name='seaTing'readonly='readonly'/> ";
	            		 form.find(".seat").html(seaTing);
           			 $("img[name=carPicUrlImg]").attr("src"," ");
           			 $("input[name=carPhotoUrl1]").attr("value","");
           			
	                	
	                 }
	             }
			});
			
		},
		//绑定判断
		getSelectedRow:function(ids){
			var deviceId=ids;
			$.ajax({
         		url: carAdd.appPath+"/device/getDevice.do?terminalDeviceNo="+ids, 
         		success: function(res){
         			if(res.code=="0"){
         				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "此终端已绑定，请重新选择！")
         			}else{
         				debugger
         				var form = $("form[name=carAddForm]");
         				form.find("input[name='deviceSn']").val(res.data.deviceSn);
         				form.find("input[name='deviceId']").val(res.data.terminalDeviceNo);
         				$("#deviceCarModalAdd").modal("hide");
         				$(".modal-backdrop").hide();
         			}
         		}
         	});
		},
		
		//加盟商列表
		pageFranchisee:function(){
			var form = $("form[name=franchiseeSerachForm]");
			var franchiseeNo = form.find("input[name='franchiseeNo']").val();
			var franchiseeBtnTpl = $("#franchiseeBtnTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(franchiseeBtnTpl);
			var table = $('#franchiseeListAdd').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : carAdd.appPath+"/franchisee/pageListAuditfranchisee.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "franchiseeNo" : franchiseeNo
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
				{ "title":"","data": "franchiseeNo","width":"5px"},
				{
					"title" : "加盟商编号",
					"data" : "franchiseeNo"
				},{
					"title" : "加盟商名称",
					"data" : "franchiseeName"
				}],
				"dom" : "<'row'<''><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#franchiseeToolssssAdd").empty().append('<button type="button"  class="btn-new-type sysworkerMgCar-batch-adddel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue sysworkerMgCar-batch-addclose">关闭</button>');
						$(".sysworkerMgCar-batch-adddel").on("click",function(){
							var ids=[];
							var franchiseeName="";
							var len=$('#franchiseeListAdd tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择加盟商！")
							}else{
								$("#franchiseeListAdd tbody").find("input:checked").each(function(){
									
		        					ids.push($(this).val());
		        					
		        					franchiseeName = $(this).next('input').val();
		        					
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "加盟商只能选择一个！")
	        					}else{
	        						var form = $("form[name=carAddForm]");
	        	     				form.find("input[name='franchiseeId']").val(ids);
	        	     				form.find("input[name='franchiseeName']").val(franchiseeName);
	        	     				$("#franchiseeModel").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#franchiseeListAdd thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#franchiseeListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#franchiseeListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgCar-batch-addclose").on("click",function(){
							$("#franchiseeModel").modal("hide");
							$(".modal-backdrop").hide();
							$('#franchiseeListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
					   {
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="franchiseeNo" value="' + full.franchiseeNo + '"><input type="hidden" value="' + full.franchiseeName + '">';
						}
					   }
					   ]
			});
		},
		
		
		
		//解绑弹出层
		pageDeViceCar:function(){
			var form = $("form[name=carAddForm]");
			//var cityId = form.find("select[name='cityId']").find("option:selected").val();
			var deviceBtnTpl = $("#deviceCarBtnTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(deviceBtnTpl);
			var table = $('#deviceCarListAdd').dataTable({
				"bFilter":false,
				"retrieve": true,//DataTables初始化选项在初始化之后不能被改变
				"ajax" : {
					"type" : "POST",
					"url" : carAdd.appPath+"/device/pageListDeviceByCar.do",
					"data" : function(d) {
						return $.extend({}, {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
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
						$(this).find("thead tr th:first-child").prepend('');
						$("#deviceToolssssAdd").empty().append('<button type="button"  class="btn-new-type sysDeviceCar-batch-adddel">绑定终端</button><button type="button"  class="btn-new-type btn-new-type-blue sysDeviceCar-batch-addclose">关闭</button>');
//						$("#deviceToolssssAdd").append('');
						$(".sysDeviceCar-batch-adddel").on("click",function(){
							var ids=[];
							var len=$('#deviceCarListAdd tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择终端！")
							}else{
								$("#deviceCarListAdd tbody").find("input:checked").each(function(){
									
		        					ids.push($(this).val());
		        					
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "终端只能选择一个！")
	        					}else{
	        						carAdd.getSelectedRow(ids);
	        					}
							}
							
							$('#deviceCarListAdd thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#deviceCarListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#deviceCarListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysDeviceCar-batch-addclose").on("click",function(){
							$("#deviceCarModalAdd").modal("hide");
							$(".modal-backdrop").hide();
							 $('#deviceCarListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
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
		
		
/*		//加盟商弹出层
		franchiseeCar:function(){
			var deviceBtnTpl = $("#franchiseeCarBtnTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(deviceBtnTpl);
			var table = $('#franchiseeAddList').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : carAdd.appPath+"/franchisee/pageListFranchisee.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length
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
				{ "title":"","data": "franchiseeNo","width":"5px"},
				{
					"title" : "加盟商编号",
					"data" : "franchiseeNo"
				},{
					"title" : "加盟商名称",
					"data" : "franchiseeName"
				},{
					"title" : "分润比例(按车)",
					"data" : "carProportion"
				},
				{
					"title" : "分润比例(按场站)",
					"data" : "parkProportion"
				} ],
				"dom" : "<'row'<'#deviceToolssssAdd.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#franchiseeToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm franchiseeCar-batch-adddel">确定</button>');
						$("#franchiseeToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm franchiseeCar-batch-addclose">关闭</button>');
						$(".sysDeviceCar-batch-adddel").on("click",function(){
							var ids=[];
							var len=$('#franchiseeAddList tbody input[type="checkbox"]:checked');
//							if(len.length==0){
//								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择！")
//							}
							//else{
								$("#franchiseeAddList tbody").find("input:checked").each(function(){
									
		        					ids.push($(this).val());
		        					
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "加盟商只能选择一个！")
	        					}else{
	        						carAdd.getSelectedRow(ids);
	        					}
							//}
							
							$('#franchiseeAddList thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#franchiseeAddList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#franchiseeAddList tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".franchiseeCar-batch-addclose").on("click",function(){
							$("#franchiseeNoAdd").modal("hide");
							$(".modal-backdrop").hide();
							 $('#franchiseeAddList tbody input[type="checkbox"]:checked').prop("checked",false);
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
		},*/
		
		
		
		
		 saveCar:function() {
			var form = $("form[name=carAddForm]");
			 //获取加盟商编号
			var franchiseeId = form.find("input[name='franchiseeId']").val();
			debugger

			form.ajaxSubmit({
				url : carAdd.appPath + "/car/addCar.do",
				type : "post",
				data :{"franchiseeNo": franchiseeId},
				success : function(res) {
					debugger
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("车辆增加");
							$("#carList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					form.find("span[name='carPlateNo']").empty();
					form.find("span[name='carOwnerName']").empty();
					form.find("span[name='carIdNo']").empty();
					form.find("span[name='engineNo']").empty();
					form.find("span[name='validityDate']").empty();
					form.find("span[name='carPhotoUrl1']").empty();
					form.find("span[name='carDocPhotoUrl1']").empty();
					form.find("span[name='carModelIdAdd']").empty();
					form.find("span[name='locationParkNo']").empty();
//					$("span[name='onlineStatusUpdateTime']").empty();
//					$("span[name='usedStatusUpdateTime']").empty();
//					$("span[name='power']").empty();
//					$("span[name='mileage']").empty();
					form.find("span[name='enrollmentDate']").empty();
					form.find("span[name='deviceId']").empty();
					form.find("span[name='effectiveInsurancePeriod']").empty();
					if (form.find("input[name='carPlateNo']").val()=="") {
						form.find("span[name='carPlateNo']").append("<font color='red'>请输入车牌号！</font>");
						return false;
					}
					if(form.find("input[name='carPlateNo']").val()!=""&&!/^[\u4E00-\u9FA5][\da-zA-Z]{6,7}$/.test(form.find("input[name='carPlateNo']").val())){
						form.find("span[name='carPlateNo']").append("<font color='red'>车牌号格式不正确，比如：粤J123501</font>");
						return false;
					}
					if (form.find("input[name='deviceId']").val()=="") {
						form.find("span[name='deviceId']").append("<font color='red'>请绑定终端编号！</font>");
						return false;
					}
//					if (form.find("select[name='locationParkNo']").val()==null||form.find("select[name='locationParkNo']").val()=="") {
//						form.find("span[name='locationParkNo']").append("<font color='red'>请选择场站！</font>");
//						return false;
//					}
					if (form.find("select[name='carModelId']").val()=="" || form.find("select[name='carModelId']").val()==null) {
						form.find("span[name='carModelIdAdd']").append("<font color='red'>请添加型号</font>");
						return false;
					}
					//判断座位数 是否为空 大于等于1的正整数
					if (form.find("input[name='seaTing']").val()=="") {
						form.find("span[name='seaTing']").append("<font color='red'>座位数不能为空 请到数据字典添加座位数！</font>");
						return false;
					}
					
					
					/*if (form.find("input[name='carOwnerName']").val()=="") {
						form.find("span[name='carOwnerName']").append("<font color='red'>请输入车主！</font>");
						return false;
					}*/
/*					if (form.find("input[name='carIdNo']").val()=="") {
						form.find("span[name='carIdNo']").append("<font color='red'>请输入车辆识别码！</font>");
						return false;
					}*/
					if (form.find("input[name='carIdNo']").val()!=""&&!/^[A-Z0-9]{17}$/.test(form.find("input[name='carIdNo']").val())) {
						$("span[name='carIdNo']").append("<font color='red'>车辆识别码格式不正确，必须是17位的数字和大写字母组合！</font>");
						return false;
					}
/*					if (form.find("input[name='engineNo']").val()=="") {
						form.find("span[name='engineNo']").append("<font color='red'>请输入发动机号！</font>");
						return false;
					}*/
					if (form.find("input[name='engineNo']").val()!=""&&!/^[0-9A-Z]{6,10}$/.test(form.find("input[name='engineNo']").val())) {
						form.find("span[name='engineNo']").append("<font color='red'>发动机号为6~9位字母数字组合或者纯数字！</font>");
						return false;
					}	
					if (form.find("input[name='engineNo']").val()!=""&&/^[A-Z]{6,10}$/.test(form.find("input[name='engineNo']").val())) {
						form.find("span[name='engineNo']").append("<font color='red'>发动机号为6~9位字母数字组合或者纯数字！</font>");
						return false;
					}	
//					if (form.find("input[name='validityDate']").val()=="") {
//						$("span[name='validityDate']").append("<font color='red'>请输入检验有效期！</font>");
//						return false;
//					}
					
//					if (form.find("input[name='onlineStatusUpdateTime']").val()=="") {
//						$("span[name='onlineStatusUpdateTime']").append("<font color='red'>请输入上线时间！</font>");
//						return false;
//					}
//					if (form.find("input[name='usedStatusUpdateTime']").val()=="") {
//						$("span[name='usedStatusUpdateTime']").append("<font color='red'>请输入使用状态更新时间！</font>");
//						return false;
//					}
//					if (form.find("input[name='power']").val()=="") {
//						$("span[name='power']").append("<font color='red'>请输入电量！</font>");
//						return false;
//					}
//					if (form.find("input[name='mileage']").val()=="") {
//						$("span[name='mileage']").append("<font color='red'>请输入里程！</font>");
//						return false;
//					}
					
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
return carAdd
})
